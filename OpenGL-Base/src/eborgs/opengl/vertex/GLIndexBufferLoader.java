package eborgs.opengl.vertex;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.opengl.GL15;

import eborgs.opengl.GLBufferLoader;
import eborgs.opengl.GLDataType;

public final class GLIndexBufferLoader {

	private GLIndexBufferLoader() {
	}

	public static GLIndexBuffer create(String name, int glUsage, GLDataType indexType, ByteBuffer buffer) {
		int glBuffer = GLBufferLoader.create(GL15.GL_ELEMENT_ARRAY_BUFFER, glUsage, buffer);
		return new GLIndexBuffer(name, glBuffer, glUsage, indexType);
	}

	public static GLIndexBuffer create(String name, int glUsage, ByteBuffer buffer) {
		int glBuffer = GLBufferLoader.create(GL15.GL_ELEMENT_ARRAY_BUFFER, glUsage, buffer);
		return new GLIndexBuffer(name, glBuffer, glUsage, GLDataType.UNSIGNED_BYTE);
	}

	public static GLIndexBuffer create(String name, int glUsage, ShortBuffer buffer) {
		int glBuffer = GLBufferLoader.create(GL15.GL_ELEMENT_ARRAY_BUFFER, glUsage, buffer);
		return new GLIndexBuffer(name, glBuffer, glUsage, GLDataType.UNSIGNED_SHORT);
	}

	public static GLIndexBuffer create(String name, int glUsage, IntBuffer buffer) {
		int glBuffer = GLBufferLoader.create(GL15.GL_ELEMENT_ARRAY_BUFFER, glUsage, buffer);
		return new GLIndexBuffer(name, glBuffer, glUsage, GLDataType.UNSIGNED_INT);
	}
}
