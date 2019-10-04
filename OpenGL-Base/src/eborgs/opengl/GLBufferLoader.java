package eborgs.opengl;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.opengl.GL15;

public final class GLBufferLoader {

	private GLBufferLoader() {
	}

	public static int create(int target, int usage, ByteBuffer data) {
		int buffer = GL15.glGenBuffers();
		GL15.glBindBuffer(target, buffer);
		GL15.glBufferData(target, data, usage);
		GL15.glBindBuffer(target, 0);
		return buffer;
	}

	public static int create(int target, int usage, ShortBuffer data) {
		int buffer = GL15.glGenBuffers();
		GL15.glBindBuffer(target, buffer);
		GL15.glBufferData(target, data, usage);
		GL15.glBindBuffer(target, 0);
		return buffer;
	}

	public static int create(int target, int usage, IntBuffer data) {
		int buffer = GL15.glGenBuffers();
		GL15.glBindBuffer(target, buffer);
		GL15.glBufferData(target, data, usage);
		GL15.glBindBuffer(target, 0);
		return buffer;
	}

	public static int create(int target, int usage, FloatBuffer data) {
		int buffer = GL15.glGenBuffers();
		GL15.glBindBuffer(target, buffer);
		GL15.glBufferData(target, data, usage);
		GL15.glBindBuffer(target, 0);
		return buffer;
	}

}
