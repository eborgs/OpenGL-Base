package eborgs.opengl;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public final class GLMatrix {

	public static final FloatBuffer buf16 = BufferUtils.createFloatBuffer(16);

	private static final Vector3f zRotaion = new Vector3f(0, 0, 1);

	private static final Vector3f xyzTrans = new Vector3f();
	private static final Vector3f xyzScale = new Vector3f();

	private GLMatrix() {
	}

	public static void setGLOrtho(Matrix4f mat, float left, float right, float bottom, float top) {
		float zNear = 0;
		float zFar = -1000;

		mat.m00 = 2f / (right - left);
		mat.m01 = 0f;
		mat.m02 = 0f;
		mat.m03 = 0f;

		mat.m10 = 0f;
		mat.m11 = 2f / (top - bottom);
		mat.m12 = 0f;
		mat.m13 = 0f;

		mat.m20 = 0f;
		mat.m21 = 0f;
		mat.m22 = -2f / (zFar - zNear);
		mat.m23 = 0f;

		mat.m30 = -(right + left) / (right - left);
		mat.m31 = -(top + bottom) / (top - bottom);
		mat.m32 = -(zFar + zNear) / (zFar - zNear);
		mat.m33 = 1f;
	}

	public static void copyMatrix4f(Matrix4f src, Matrix4f dst) {
		dst.load(src);
	}

	public static void scale(Matrix4f matrix, float x, float y) {
		xyzScale.set(x, y, 1f);
		matrix.scale(xyzScale);
	}

	public static void rotateZ(Matrix4f matrix, float angle) {
		matrix.rotate(angle, zRotaion);
	}

	public static void translate(Matrix4f matrix, float x, float y) {
		translate(matrix, x, y, 0f);
	}

	public static void translate(Matrix4f matrix, float x, float y, float z) {
		xyzTrans.set(x, y, z);
		translate(matrix, xyzTrans);
	}

	public static void translate(Matrix4f matrix, Vector2f translation) {
		xyzTrans.set(translation.x, translation.y, 0f);
		translate(matrix, xyzTrans);
	}

	public static void translate(Matrix4f matrix, Vector3f translation) {
		matrix.translate(translation);
	}

	public static FloatBuffer storeMatrix(Matrix matrix) {
		return storeMatrix(matrix, null);
	}

	public static FloatBuffer storeMatrix(Matrix matrix, FloatBuffer buf) {
		if (buf == null) {
			buf = BufferUtils.createFloatBuffer(16);
		}
		buf.clear();
		matrix.store(buf);
		buf.flip();
		return buf;
	}

}
