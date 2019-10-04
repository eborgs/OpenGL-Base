package eborgs.opengl.texture;

import eborgs.opengl.GLColor4f;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

public class GLRGBADataLoader {

	public static GLRGBAData load(String file) throws IOException {
		return load(new File(file));
	}

	public static GLRGBAData load(File file) throws IOException {
		return load(ImageIO.read(file));
	}

	public static GLRGBAData load(InputStream in) throws IOException {
		return load(ImageIO.read(in));
	}

	public static GLRGBAData load(BufferedImage data) {
		int width = data.getWidth();
		int height = data.getHeight();
		int length = width * height;

		ByteBuffer buffer = BufferUtils.createByteBuffer(length * 4);
		int[] pixels = data.getRGB(0, 0, width, height, null, 0, width);

		for (int i = 0; i < length; i++) {
			buffer.put((byte) ((pixels[i] >> 16) & 0xff)); // Red.
			buffer.put((byte) ((pixels[i] >> 8) & 0xff)); // Green.
			buffer.put((byte) ((pixels[i] >> 0) & 0xff)); // Blue.
			buffer.put((byte) ((pixels[i] >> 24) & 0xff)); // Alpha.
		}
		buffer.flip();
		return new GLRGBAData(buffer, width, height);
	}

	public static GLRGBAData create(int width, int height, GLColor4f color) {
		int length = width * height;

		ByteBuffer buffer = BufferUtils.createByteBuffer(length * 4);

		byte r = (byte) color.getRed();
		byte g = (byte) color.getGreen();
		byte b = (byte) color.getBlue();
		byte a = (byte) color.getAlpha();

		for (int i = 0; i < length; i++) {
			buffer.put(r);
			buffer.put(g);
			buffer.put(b);
			buffer.put(a);
		}
		buffer.flip();
		return new GLRGBAData(buffer, width, height);
	}

}
