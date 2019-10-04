package eborgs.opengl;

import java.util.Stack;

import org.lwjgl.opengl.GL11;

public final class GLViewport {

	private static int viewportX;
	private static int viewportY;
	private static int viewportWidth;
	private static int viewportHeight;

	private static Stack<Integer> stack = new Stack<>();

	private GLViewport() {
	}

	public static void setViewport(int x, int y, int width, int height) {
		viewportX = x;
		viewportY = y;
		viewportWidth = width;
		viewportHeight = height;
		GL11.glViewport(x, y, width, height);
	}

	public static void pushViewport() {
		stack.push(viewportX);
		stack.push(viewportY);
		stack.push(viewportWidth);
		stack.push(viewportHeight);
	}

	public static void popViewPort() {
		int height = stack.pop();
		int width = stack.pop();
		int y = stack.pop();
		int x = stack.pop();
		setViewport(x, y, width, height);
	}

}
