package eborgs.opengl.texture;

import eborgs.opengl.GLColor4f;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;

public class GLRGBATextureLoader {

	public static GLTexture create(String name, int width, int height, GLTextureParams params) {
		return create(name, (ByteBuffer) null, width, height, params);
	}

	public static GLTexture load(String name, String file) throws IOException {
		return load(name, file, GLTextureParams.DEFAULT);
	}

	public static GLTexture load(String name, String file, GLTextureParams params) throws IOException {
		return create(name, GLRGBADataLoader.load(file), params);
	}

	public static GLTexture load(String name, File file, GLTextureParams params) throws IOException {
		return create(name, GLRGBADataLoader.load(file), params);
	}

	public static GLTexture load(String name, InputStream in, GLTextureParams params) throws IOException {
		return create(name, GLRGBADataLoader.load(in), params);
	}

	public static GLTexture load(String name, BufferedImage image, GLTextureParams params) {
		return create(name, GLRGBADataLoader.load(image), params);
	}

	public static GLTexture create(String name, GLColor4f color, int width, int height, GLTextureParams params) {
		return create(name, GLRGBADataLoader.create(width, height, color), params);
	}

	public static GLTexture create(String name, GLRGBAData data, GLTextureParams params) {
		return create(name, data.buffer, data.width, data.height, params);
	}

	public static GLTexture create(String name, ByteBuffer buffer, int width, int height, GLTextureParams params) {
		if (params == null) {
			throw new NullPointerException("params == null");
		}
		int glTexture = GL11.glGenTextures();

		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, glTexture);

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, params.wrapMode.wrapS());
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, params.wrapMode.wrapT());
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, params.filter.min());
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, params.filter.mag());

		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);

		if (params.filter.mipmap()) {
			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
		}
		return new GLTexture(name, glTexture, GL11.GL_TEXTURE_2D, width, height);
	}

}
