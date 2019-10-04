package eborgs.opengl;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

public final class GLDisplay {

	private static final int GL_MAJOR_VERSION = 3;
	private static final int GL_MINOR_VERSION = 3;

	private GLDisplay() {
	}

	public static void create(String title, int width, int height, boolean fullscreen, boolean resizable) throws LWJGLException {
		ContextAttribs contextAttribs = new ContextAttribs(GL_MAJOR_VERSION, GL_MINOR_VERSION);
		contextAttribs.withProfileCore(true);

		if (fullscreen) {
			Display.setDisplayModeAndFullscreen(findFullscreenDisplayMode(width, height));
		} else {
			Display.setDisplayMode(new DisplayMode(width, height));
		}
		Display.setTitle(title);
		Display.setResizable(resizable);
		Display.setVSyncEnabled(true);
		Display.create(new PixelFormat(), contextAttribs);

		GLViewport.setViewport(0, 0, width, height);
	}

	private static DisplayMode findFullscreenDisplayMode(int width, int height) throws LWJGLException {
		int bitsPerPixel = Display.getDesktopDisplayMode().getBitsPerPixel();
		int frequency = Display.getDesktopDisplayMode().getFrequency();

		DisplayMode bestDisplayMode = null;
		for (DisplayMode displayMode : Display.getAvailableDisplayModes()) {

			if (displayMode.getWidth() == width && displayMode.getHeight() == height) {
				if (displayMode.getBitsPerPixel() == bitsPerPixel) {
					if (displayMode.getFrequency() == frequency) {
						return displayMode;
					}
					bestDisplayMode = displayMode;
				}
			}
		}
		if (bestDisplayMode != null) {
			return bestDisplayMode;
		}
		throw new LWJGLException("Invalid fullscreen display mode (" + width + "x" + height + ")");
	}

	public static void destroy() {
		Display.destroy();
	}

	public static int getWidth() {
		return Display.getWidth();
	}

	public static int getHeight() {
		return Display.getHeight();
	}

}
