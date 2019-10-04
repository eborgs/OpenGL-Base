package eborgs.opengl.texture;

import eborgs.opengl.GLLog;
import eborgs.opengl.GLObject;
import eborgs.opengl.GLObjectType;
import eborgs.opengl.GLViewport;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;

public class GLFrameBuffer extends GLObject {

	private final int glFrameBuffer;

	public final int width;
	public final int height;

	private int maxColorAttachments;
	private GLTexture[] colorAttachments;

	private boolean hasDepthBuffer;
	private int depthBuffer;

	private int status;

	public GLFrameBuffer(String name, int width, int height) {
		super(GLObjectType.GL_FRAMEBUFFER, name);
		this.width = width;
		this.height = height;
		this.glFrameBuffer = createFrameBuffer();
	}

	private int createFrameBuffer() {
		maxColorAttachments = GL11.glGetInteger(GL30.GL_MAX_COLOR_ATTACHMENTS);
		colorAttachments = new GLTexture[maxColorAttachments];
		return GL30.glGenFramebuffers();
	}

	@Override
	protected void deleteInternal() {
		deleteColorAttachments();
		if (hasDepthBuffer) {
			GL30.glDeleteRenderbuffers(depthBuffer);
		}
		GL30.glDeleteFramebuffers(glFrameBuffer);
	}

	private void deleteColorAttachments() {
		for (int i = 0; i < maxColorAttachments; i++) {
			GLTexture texture = colorAttachments[i];
			colorAttachments[i] = null;
			if (texture != null) {
				texture.deleteNonShared();
			}
		}
	}

	public void bind() {
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, glFrameBuffer);
	}

	public static void bindNone() {
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
	}

	public void beginRender() {
		GLViewport.pushViewport();
		bind();
		GLViewport.setViewport(0, 0, width, height);
	}

	public void endRender() {
		bindNone();
		GLViewport.popViewPort();
	}

	private void setColorAttachmentInternal(int attachment, int glTexture) {
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, glFrameBuffer);
		GL32.glFramebufferTexture(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0 + attachment, glTexture, 0);

		status = GL30.glCheckFramebufferStatus(GL30.GL_FRAMEBUFFER);
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);

		checkCompleteness("GL_COLOR_ATTACHMENT" + "[" + attachment + "]", status);
	}

	public GLFrameBuffer setColorAttachment(int attachment, GLTexture texture) {
		if (getColorAttachment(attachment) != null) {
			throw new IllegalArgumentException("Attachment already in use");
		}
		if (texture == null) {
			throw new NullPointerException("texture == null");
		}
		colorAttachments[attachment] = texture;
		setColorAttachmentInternal(attachment, texture.glTexture);
		return this;
	}

	public GLTexture getColorAttachment(int attachment) {
		if (attachment < 0 || attachment > maxColorAttachments) {
			throw new ArrayIndexOutOfBoundsException("attachment < 0 || attachment > " + maxColorAttachments);
		}
		return colorAttachments[attachment];
	}

	public GLFrameBuffer createColorAttachment(int attachment, GLTextureParams params) {
		if (getColorAttachment(attachment) != null) {
			throw new IllegalArgumentException("Attachment alrady in use");
		}
		String name = this + ":color_attachment[" + attachment + "]";
		// Possible resource leak on 'texture' if 'setColorAttachment' throws and exception.
		// Should no happen if class is used only synchronous.
		GLTexture texture = GLRGBATextureLoader.create(name, width, height, params);
		setColorAttachment(attachment, texture);
		return this;
	}

	public GLTexture removeColorAttachment(int attachment) {
		GLTexture texture = getColorAttachment(attachment);
		if (texture == null) {
			return null;
		}
		colorAttachments[attachment] = null;
		// TODO Is this how to remove a color attachment?
		setColorAttachmentInternal(attachment, 0);
		return texture;
	}

	public void deleteColorAttachment(int attachment) {
		GLTexture texture = removeColorAttachment(attachment);
		if (texture != null) {
			texture.deleteNonShared();
		}
	}

	public GLFrameBuffer createDepthBufferAttachment() {
		if (hasDepthBuffer) {
			return this;
		}
		hasDepthBuffer = true;
		depthBuffer = GL30.glGenRenderbuffers();

		GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, depthBuffer);
		GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, GL11.GL_DEPTH_COMPONENT, width, height);
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, glFrameBuffer);
		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL30.GL_RENDERBUFFER, depthBuffer);

		status = GL30.glCheckFramebufferStatus(GL30.GL_FRAMEBUFFER);
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);

		checkCompleteness("GL_DEPTH_ATTACHMENT", status);
		return this;
	}

	private void checkCompleteness(String name, int status) {
		// We only check specifically for a few of the different status return values.
		String statusMessage = null;
		switch (status) {
		case GL30.GL_FRAMEBUFFER_COMPLETE:
			return;
		case GL30.GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT:
			statusMessage = "GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT";
			break;
		case GL30.GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER:
			statusMessage = "GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER";
			break;
		case GL30.GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT:
			statusMessage = "GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT";
			break;
		default:
			statusMessage = "Incomplete";
		}
		GLLog.log.severe(this + ": " + name + ": " + statusMessage);
	}

	/**
	 * Creates a new frame buffer with
	 */
	public static GLFrameBuffer create(String name, int width, int height, GLTextureParams params) {
		return new GLFrameBuffer(name, width, height).createColorAttachment(0, params);
	}

}
