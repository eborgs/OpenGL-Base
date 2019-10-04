package eborgs.opengl.vertex;

import eborgs.opengl.GLDataType;

public class GLVertexAttribute {

	public final int size;

	public final GLDataType dataType;

	public final boolean integerType;
	public final boolean normalize;

	public final String name;

	/**
	 * Creates a non integer type vertex attribute.
	 */
	public GLVertexAttribute(int size, GLDataType dataType, boolean normalize, String name) {
		this(size, dataType, false, normalize, name);
	}

	/**
	 * @param integerType
	 *            Specifies that the data for this attribute will be sent as an integer values to the shader.
	 */
	public GLVertexAttribute(int size, GLDataType dataType, boolean integerType, boolean normalize, String name) {
		if (size < 1) {
			throw new IllegalArgumentException("size < 1");
		}
		this.size = size;
		this.dataType = dataType;
		this.integerType = integerType;
		this.normalize = normalize;
		this.name = name;
	}

	@Override
	public String toString() {
		return name + ": size=" + size + ", type=" + dataType.name() + ", normalize=" + normalize;
	}

}
