package eborgs.opengl.texture;

import java.nio.ByteBuffer;

public class GLRGBAData {

	public ByteBuffer buffer;

	public int width;
	public int height;

	public GLRGBAData(ByteBuffer buffer, int width, int height) {
		this.buffer = buffer;
		
		this.width = width;
		this.height = height;
	}

}
