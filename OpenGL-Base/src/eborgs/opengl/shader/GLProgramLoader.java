package eborgs.opengl.shader;

import eborgs.opengl.GLLog;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;

public final class GLProgramLoader {

	private static char[] readAllBuffer = new char[1024];

	private GLProgramLoader() {
	}

	public static GLProgram load(String name, String vertFileName, String geomFileName, String fragFileName)
			throws IOException, GLShaderException {
		File vertFile = vertFileName != null ? new File(vertFileName) : null;
		File geomFile = geomFileName != null ? new File(geomFileName) : null;
		File fragFile = fragFileName != null ? new File(fragFileName) : null;

		return load(name, vertFile, geomFile, fragFile);
	}

	public static GLProgram load(String name, File vertFile, File geomFile, File fragFile) throws IOException, GLShaderException {
		BufferedInputStream vertIn = null;
		BufferedInputStream geomIn = null;
		BufferedInputStream fragIn = null;
		try {
			vertIn = vertFile != null ? new BufferedInputStream(new FileInputStream(vertFile)) : null;
			geomIn = geomFile != null ? new BufferedInputStream(new FileInputStream(geomFile)) : null;
			fragIn = fragFile != null ? new BufferedInputStream(new FileInputStream(fragFile)) : null;

			return load(name, vertIn, geomIn, fragIn);
		} finally {
			if (vertIn != null) {
				vertIn.close();
			}
			if (geomIn != null) {
				geomIn.close();
			}
			if (fragIn != null) {
				fragIn.close();
			}
		}
	}

	public static GLProgram load(String name, InputStream vertIn, InputStream geomIn, InputStream fragIn) throws IOException,
			GLShaderException {
		String vertSource = vertIn != null ? readShader(vertIn) : null;
		String geomSource = geomIn != null ? readShader(geomIn) : null;
		String fragSource = fragIn != null ? readShader(fragIn) : null;

		return create(name, vertSource, geomSource, fragSource);
	}

	public static String readShader(InputStream in) throws IOException {
		return readAll(in, "UTF-8");
	}

	public static String readAll(InputStream in, String charsetName) throws IOException {
		InputStreamReader reader = new InputStreamReader(in, charsetName);
		StringBuilder sb = new StringBuilder();

		char[] buffer = readAllBuffer;
		for (int count = 0; (count = reader.read(buffer)) != -1;) {
			sb.append(buffer, 0, count);
		}
		return sb.toString();
	}

	public static GLProgram create(String name, String vertSource, String geomSource, String fragSource) throws GLShaderException {
		if (vertSource == null) {
			throw new IllegalArgumentException("vertSource == null");
		}
		if (fragSource == null) {
			throw new IllegalArgumentException("fragSource == null");
		}
		StringBuilder infoLogBuilder = new StringBuilder();

		int glProgram = GL20.glCreateProgram();

		final int NO_SHADER = 0;

		int vertShader = NO_SHADER;
		int geomShader = NO_SHADER;
		int fragShader = NO_SHADER;

		try {
			vertShader = vertSource != null ? compileVertShader(name, vertSource, infoLogBuilder) : NO_SHADER;
			geomShader = geomSource != null ? compileGeomShader(name, geomSource, infoLogBuilder) : NO_SHADER;
			fragShader = fragSource != null ? compileFragShader(name, fragSource, infoLogBuilder) : NO_SHADER;

			if (vertShader != NO_SHADER) {
				GL20.glAttachShader(glProgram, vertShader);
			}
			if (geomShader != NO_SHADER) {
				GL20.glAttachShader(glProgram, geomShader);
			}
			if (fragShader != NO_SHADER) {
				GL20.glAttachShader(glProgram, fragShader);
			}

			GL20.glLinkProgram(glProgram);

			int linkStatus = GL20.glGetProgrami(glProgram, GL20.GL_LINK_STATUS);
			int infoLogLen = GL20.glGetProgrami(glProgram, GL20.GL_INFO_LOG_LENGTH);
			String infoLog = GL20.glGetProgramInfoLog(glProgram, infoLogLen);

			if (infoLog != null && infoLog.length() != 0) {
				infoLogBuilder.append(infoLog).append(System.lineSeparator());
			}
			if (linkStatus != GL11.GL_TRUE) {
				throw new GLShaderException(name + ": Program link error: " + infoLogBuilder.toString());
			}

		} catch (GLShaderException e) {
			GL20.glDeleteProgram(glProgram);
			throw new GLShaderException(name + ": Failed to create shader program:", e);

		} finally {
			// TODO Program may be deleted when we detach shaders
			if (vertShader != NO_SHADER) {
				GL20.glDetachShader(glProgram, vertShader);
				GL20.glDeleteShader(vertShader);
			}
			if (geomShader != NO_SHADER) {
				GL20.glDetachShader(glProgram, geomShader);
				GL20.glDeleteShader(geomShader);
			}
			if (fragShader != NO_SHADER) {
				GL20.glDetachShader(glProgram, fragShader);
				GL20.glDeleteShader(fragShader);
			}
		}
		if (infoLogBuilder.length() > 0) {
			GLLog.log.info(infoLogBuilder.toString());
		}
		return new GLProgram(name, glProgram);
	}

	private static int compileVertShader(String name, String source, StringBuilder infoLogBuilder) throws GLShaderException {
		return compileShader(name + ":vertex", GL20.GL_VERTEX_SHADER, source, infoLogBuilder);
	}

	private static int compileGeomShader(String name, String source, StringBuilder infoLogBuilder) throws GLShaderException {
		return compileShader(name + ":geometry", GL32.GL_GEOMETRY_SHADER, source, infoLogBuilder);
	}

	private static int compileFragShader(String name, String source, StringBuilder infoLogBuilder) throws GLShaderException {
		return compileShader(name + ":fragment", GL20.GL_FRAGMENT_SHADER, source, infoLogBuilder);
	}

	private static int compileShader(String name, int type, String source, StringBuilder infoLogBuilder) throws GLShaderException {
		int shader = GL20.glCreateShader(type);

		GL20.glShaderSource(shader, source);
		GL20.glCompileShader(shader);

		int compStatus = GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS);
		int infoLogLen = GL20.glGetShaderi(shader, GL20.GL_INFO_LOG_LENGTH);
		String infoLog = GL20.glGetShaderInfoLog(shader, infoLogLen);

		if (infoLog != null && infoLog.length() > 0) {
			infoLogBuilder.append(name).append(":\n").append(infoLog).append('\n');
		}
		if (compStatus != GL11.GL_TRUE) {
			throw new GLShaderException("Shader compilation error: " + infoLogBuilder.toString());
		}
		return shader;
	}

}
