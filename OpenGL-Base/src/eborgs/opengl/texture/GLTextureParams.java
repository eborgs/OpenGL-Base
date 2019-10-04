package eborgs.opengl.texture;

import org.lwjgl.opengl.GL11;

public class GLTextureParams {

	public final GLFilter filter;

	public final GLWrapMode wrapMode;

	public GLTextureParams() {
		this(GLFilter.LINEAR);
	}

	public GLTextureParams(GLFilter filter) {
		this(filter, GLWrapMode.REPEAT);
	}

	public GLTextureParams(GLFilter filter, GLWrapMode wrapMode) {
		this.filter = filter;
		this.wrapMode = wrapMode;
	}

	public static enum GLFilter {

		LINEAR(false, GL11.GL_LINEAR, GL11.GL_LINEAR),

		NEAREST(false, GL11.GL_NEAREST, GL11.GL_NEAREST),

		NEAREST_MIPMAP_NEAREST(true, GL11.GL_NEAREST_MIPMAP_NEAREST, GL11.GL_NEAREST),

		LINEAR_MIPMAP_LINEAR(true, GL11.GL_LINEAR_MIPMAP_LINEAR, GL11.GL_LINEAR),

		LINEAR_MIPMAP_NEAREST(true, GL11.GL_LINEAR_MIPMAP_NEAREST, GL11.GL_NEAREST),

		NEAREST_MIPMAP_LINEAR(true, GL11.GL_NEAREST_MIPMAP_LINEAR, GL11.GL_LINEAR);

		GLFilter(boolean mipmap, int min, int mag) {
			this.mipmap = mipmap;

			this.min = min;
			this.mag = mag;
		}

		private boolean mipmap;

		private int min;
		private int mag;

		public boolean mipmap() {
			return mipmap;
		}

		public int min() {
			return min;
		}

		public int mag() {
			return mag;
		}

	}

	public static enum GLWrapMode {

		REPEAT(GL11.GL_REPEAT, GL11.GL_REPEAT),
		
		CLAMP(GL11.GL_CLAMP, GL11.GL_CLAMP);

		private int wrapS;
		private int wrapT;

		GLWrapMode(int wrapS, int wrapT) {
			this.wrapS = wrapS;
			this.wrapT = wrapT;
		}

		protected int wrapS() {
			return wrapS;
		}

		protected int wrapT() {
			return wrapT;
		}

	}

	public static final GLTextureParams DEFAULT = new GLTextureParams(GLFilter.LINEAR, GLWrapMode.REPEAT);

	public static final GLTextureParams LINEAR = new GLTextureParams(GLFilter.LINEAR);

	public static final GLTextureParams NEAREST = new GLTextureParams(GLFilter.NEAREST);

	public static final GLTextureParams LINEAR_REPEAT = new GLTextureParams(GLFilter.LINEAR, GLWrapMode.REPEAT);

	public static final GLTextureParams LINEAR_CLAMP = new GLTextureParams(GLFilter.LINEAR, GLWrapMode.CLAMP);

	public static final GLTextureParams NEAREST_REPEAT = new GLTextureParams(GLFilter.NEAREST, GLWrapMode.REPEAT);

	public static final GLTextureParams NEAREST_CLAMP = new GLTextureParams(GLFilter.NEAREST, GLWrapMode.CLAMP);

	public static final GLTextureParams LINEAR_MIPMAP = new GLTextureParams(GLFilter.LINEAR_MIPMAP_LINEAR);

	public static final GLTextureParams NEAREST_MIPMAP = new GLTextureParams(GLFilter.NEAREST_MIPMAP_NEAREST);

}
