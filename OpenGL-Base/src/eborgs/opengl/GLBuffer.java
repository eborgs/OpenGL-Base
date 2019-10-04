package eborgs.opengl;

import org.lwjgl.opengl.GL15;

public class GLBuffer extends GLObject {

	public final int glBuffer;
	public final int glTarget;
	public final int glUsage;

	public GLBuffer(String name, int glBuffer, int glTarget, int glUsage) {
		super(GLObjectType.GL_BUFFER, name);
		this.glBuffer = glBuffer;
		this.glTarget = glTarget;
		this.glUsage = glUsage;
	}

	@Override
	protected void deleteInternal() {
		GL15.glDeleteBuffers(glBuffer);
	}

}
