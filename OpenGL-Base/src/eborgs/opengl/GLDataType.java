package eborgs.opengl;

import org.lwjgl.opengl.GL11;

public enum GLDataType {

	FLOAT(4, GL11.GL_FLOAT),

	INT(4, GL11.GL_INT),
	BYTE(1, GL11.GL_BYTE),
	SHORT(2, GL11.GL_SHORT),

	UNSIGNED_INT(4, GL11.GL_UNSIGNED_INT),
	UNSIGNED_BYTE(1, GL11.GL_UNSIGNED_BYTE),
	UNSIGNED_SHORT(2, GL11.GL_UNSIGNED_SHORT);

	public final int size;
	public final int glType;

	GLDataType(int size, int glType) {
		this.size = size;
		this.glType = glType;
	}

}