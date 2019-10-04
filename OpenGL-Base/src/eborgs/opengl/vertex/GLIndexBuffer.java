package eborgs.opengl.vertex;

import org.lwjgl.opengl.GL15;

import eborgs.opengl.GLBuffer;
import eborgs.opengl.GLDataType;

public class GLIndexBuffer extends GLBuffer {

	public final GLDataType indexType;

	public GLIndexBuffer(String name, int glBuffer, int glUsage, GLDataType indexType) {
		super(name, glBuffer, GL15.GL_ELEMENT_ARRAY_BUFFER, glUsage);
		this.indexType = indexType;
	}

}
