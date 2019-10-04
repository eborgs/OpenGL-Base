package eborgs.opengl.vertex;

import eborgs.opengl.GLBufferLoader;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.opengl.GL15;

public final class GLVertexBufferLoader {

	private GLVertexBufferLoader() {
	}

	public static GLVertexBuffer create(String name, int glUsage, int attributes, ByteBuffer data) {
		int glBuffer = GLBufferLoader.create(GL15.GL_ARRAY_BUFFER, glUsage, data);
		return new GLVertexBuffer(name, glBuffer, glUsage, attributes);
	}

	public static GLVertexBuffer create(String name, int glUsage, int attributes, ShortBuffer data) {
		int glBuffer = GLBufferLoader.create(GL15.GL_ARRAY_BUFFER, glUsage, data);
		return new GLVertexBuffer(name, glBuffer, glUsage, attributes);
	}

	public static GLVertexBuffer create(String name, int glUsage, int attributes, IntBuffer data) {
		int glBuffer = GLBufferLoader.create(GL15.GL_ARRAY_BUFFER, glUsage, data);
		return new GLVertexBuffer(name, glBuffer, glUsage, attributes);
	}

	public static GLVertexBuffer create(String name, int glUsage, int attributes, FloatBuffer data) {
		int glBuffer = GLBufferLoader.create(GL15.GL_ARRAY_BUFFER, glUsage, data);
		return new GLVertexBuffer(name, glBuffer, glUsage, attributes);
	}

}
