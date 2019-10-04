package eborgs.opengl.shader;

import java.nio.FloatBuffer;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public interface GLShaderUniforms {

	public int getUniformLocation(String name);

	public boolean hasUniform(String name);

	public void setUniform1f(int location, float x);

	public void setUniform2f(int location, float x, float y);

	public void setUniform3f(int locatione, float x, float y, float z);

	public void setUniform4f(int location, float x, float y, float z, float w);

	public void setUniform1i(int location, int x);

	public void setUniform2i(int location, int x, int y);

	public void setUniform3i(int location, int x, int y, int z);

	public void setUniform4i(int location, int x, int y, int z, int w);

	public void setUniformMatrix2(int location, boolean transpose, FloatBuffer buf);

	public void setUniformMatrix3(int location, boolean transpose, FloatBuffer buf);

	public void setUniformMatrix4(int location, boolean transpose, FloatBuffer buf);

	default void setUniform2f(int location, Vector2f vec) {
		setUniform2f(location, vec.x, vec.y);
	}

	default void setUniform3f(int location, Vector3f vec) {
		setUniform3f(location, vec.x, vec.y, vec.z);
	}

	default void setUniform4f(int location, Vector4f vec) {
		setUniform4f(location, vec.x, vec.y, vec.z, vec.w);
	}

	default void setUniform1b(int location, boolean b) {
		setUniform1f(location, b ? 1f : 0f);
	}

	default void setUniform1f(String name, float x) {
		setUniform1f(getUniformLocation(name), x);
	}

	default void setUniform2f(String name, float x, float y) {
		setUniform2f(getUniformLocation(name), x, y);
	}

	default void setUniform3f(String name, float x, float y, float z) {
		setUniform3f(getUniformLocation(name), x, y, z);
	}

	default void setUniform4f(String name, float x, float y, float z, float w) {
		setUniform4f(getUniformLocation(name), x, y, z, w);
	}

	default void setUniform1i(String name, int x) {
		setUniform1f(getUniformLocation(name), x);
	}

	default void setUniform2i(String name, int x, int y) {
		setUniform2i(getUniformLocation(name), x, y);
	}

	default void setUniform3i(String name, int x, int y, int z) {
		setUniform3i(getUniformLocation(name), x, y, z);
	}

	default void setUniform4i(String name, int x, int y, int z, int w) {
		setUniform4i(getUniformLocation(name), x, y, z, w);
	}

	default void setUniformMatrix2(String name, boolean transpose, FloatBuffer buf) {
		setUniformMatrix2(getUniformLocation(name), transpose, buf);
	}

	default void setUniformMatrix3(String name, boolean transpose, FloatBuffer buf) {
		setUniformMatrix3(getUniformLocation(name), transpose, buf);
	}

	default void setUniformMatrix4(String name, boolean transpose, FloatBuffer buf) {
		setUniformMatrix4(getUniformLocation(name), transpose, buf);
	}

	default void setUniform1b(String name, boolean b) {
		setUniform1b(getUniformLocation(name), b);
	}

}
