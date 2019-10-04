package eborgs.opengl.texture;

import eborgs.opengl.GLObject;
import eborgs.opengl.GLObjectType;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class GLTexture extends GLObject {

	public final int glTexture;
	public final int glTarget;

	public final int width;
	public final int height;

	public GLTexture(String name, int glTexture, int glTarget, int width, int height) {
		super(GLObjectType.GL_TEXTURE, name);
		this.glTexture = glTexture;
		this.glTarget = glTarget;
		this.width = width;
		this.height = height;
	}

	@Override
	protected void deleteInternal() {
		GL11.glDeleteTextures(glTexture);
	}

	public void bind() {
		GL11.glBindTexture(glTarget, glTexture);
	}

	public static void setActiveUnit(int unit) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + unit);
	}

}
