package eborgs.opengl;

public class GLColor4f implements Cloneable {

	public float r;
	public float g;
	public float b;
	public float a;

	public GLColor4f() {
		this(0, 0, 0, 1);
	}

	public GLColor4f(float r, float g, float b, float a) {
		set(r, g, b, a);
	}

	public GLColor4f(GLColor4f color) {
		set(color);
	}

	public void set(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public void set(GLColor4f color) {
		this.r = color.r;
		this.g = color.g;
		this.b = color.b;
		this.a = color.a;
	}

	public int getRed() {
		return (int) (r * 255);
	}

	public int getGreen() {
		return (int) (g * 255);
	}

	public int getBlue() {
		return (int) (b * 255);
	}

	public int getAlpha() {
		return (int) (a * 255);
	}

	@Override
	public GLColor4f clone() {
		return new GLColor4f(this);
	}

	@Override
	public String toString() {
		return String.format("Color4f(%.2f, %.2f, %.2f, %.2f)", r, g, b, a);
	}

	public static final GLColor4f black = new GLColor4f(0f, 0f, 0f, 1f);

	public static final GLColor4f white = new GLColor4f(1f, 1f, 1f, 1f);

	public static final GLColor4f orange = new GLColor4f(1f, 0.5f, 0f, 1f);

	public static final GLColor4f yellow = new GLColor4f(1f, 1f, 0.05f, 1f);

	public static final GLColor4f pink = new GLColor4f(1f, 0.2f, 0.75f, 1f);

	public static final GLColor4f red = new GLColor4f(1f, 0.05f, 0.05f, 1f);

	public static final GLColor4f gray = new GLColor4f(0.5f, 0.5f, 0.5f, 1f);

	public static final GLColor4f blue = new GLColor4f(0.15f, 0.15f, 1f, 1f);

	public static final GLColor4f green = new GLColor4f(0.05f, 1f, 0.05f, 1f);

	public static final GLColor4f purple = new GLColor4f(0.75f, 0.1f, 0.9f, 1f);

	public static final GLColor4f brown = new GLColor4f(0.4f, 0.27f, 0.13f, 1f);

	public static final GLColor4f darkGray = new GLColor4f(0.2f, 0.2f, 0.2f, 1f);

	public static final GLColor4f lightGray = new GLColor4f(0.7f, 0.7f, 0.7f, 1f);

}
