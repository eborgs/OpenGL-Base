package eborgs.opengl.vertex;

import org.lwjgl.opengl.GL15;

import eborgs.opengl.GLBuffer;

public class GLVertexBuffer extends GLBuffer {
	
	private GLVertexAttribute[] attributes;

	public GLVertexBuffer(String name, int glBuffer, int glUsage, GLVertexAttribute... attributes) {
		super(name, glBuffer, GL15.GL_ARRAY_BUFFER, glUsage);
		this.attributes = attributes;
	}

	public GLVertexBuffer(String name, int glBuffer, int glUsage, int attributes) {
		this(name, glBuffer, glUsage, new GLVertexAttribute[attributes]);
	}

	public GLVertexBuffer setAttribute(int index, GLVertexAttribute attribute) {
		attributes[index] = attribute;
		return this;
	}

	public GLVertexAttribute[] getAttributes() {
		return attributes;
	}

}
