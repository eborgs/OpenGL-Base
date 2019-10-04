package eborgs.opengl.shader;

import eborgs.opengl.GLLog;
import eborgs.opengl.GLObject;
import eborgs.opengl.GLObjectType;

import java.nio.FloatBuffer;
import java.util.HashMap;

import org.lwjgl.opengl.GL20;

public class GLProgram extends GLObject implements GLShaderUniforms {

	private final int glProgram;

	private HashMap<String, Integer> uniforms = new HashMap<>();

	protected GLProgram(String name, int glProgram) {
		super(GLObjectType.GL_PROGRAM, name);
		this.glProgram = glProgram;
		readActiveUniforms();
	}

	@Override
	protected void deleteInternal() {
		GL20.glDeleteProgram(glProgram);
	}

	public void bind() {
		GL20.glUseProgram(glProgram);
	}

	public static void bindNone() {
		GL20.glUseProgram(0);
	}

	private void readActiveUniforms() {
		int count = GL20.glGetProgrami(glProgram, GL20.GL_ACTIVE_UNIFORMS);
		int maxLen = GL20.glGetProgrami(glProgram, GL20.GL_ACTIVE_UNIFORM_MAX_LENGTH);

		for (int i = 0; i < count; i++) {
			String name = GL20.glGetActiveUniform(glProgram, i, maxLen);
			int location = GL20.glGetUniformLocation(glProgram, name);
			uniforms.put(name, location);
		}
	}

	@Override
	public int getUniformLocation(String name) {
		Integer location = uniforms.get(name);
		if (location == null) {
			GLLog.log.info(this + ": No active uniform with name \"" + name + "\"");
		}
		return location != null ? location.intValue() : 0;
	}

	@Override
	public boolean hasUniform(String name) {
		return uniforms.containsKey(name);
	}

	@Override
	public void setUniform1f(int location, float x) {
		GL20.glUniform1f(location, x);
	}

	@Override
	public void setUniform2f(int location, float x, float y) {
		GL20.glUniform2f(location, x, y);
	}

	@Override
	public void setUniform3f(int location, float x, float y, float z) {
		GL20.glUniform3f(location, x, y, z);
	}

	@Override
	public void setUniform4f(int location, float x, float y, float z, float w) {
		GL20.glUniform4f(location, x, y, z, w);
	}

	@Override
	public void setUniform1i(int location, int x) {
		GL20.glUniform1i(location, x);
	}

	@Override
	public void setUniform2i(int location, int x, int y) {
		GL20.glUniform2i(location, x, y);
	}

	@Override
	public void setUniform3i(int location, int x, int y, int z) {
		GL20.glUniform3i(location, x, y, z);
	}

	@Override
	public void setUniform4i(int location, int x, int y, int z, int w) {
		GL20.glUniform4i(location, x, y, z, w);
	}

	@Override
	public void setUniformMatrix2(int location, boolean transpose, FloatBuffer buf) {
		GL20.glUniformMatrix2(location, transpose, buf);
	}

	@Override
	public void setUniformMatrix3(int location, boolean transpose, FloatBuffer buf) {
		GL20.glUniformMatrix3(location, transpose, buf);
	}

	@Override
	public void setUniformMatrix4(int location, boolean transpose, FloatBuffer buf) {
		GL20.glUniformMatrix4(location, transpose, buf);
	}

}
