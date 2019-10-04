package eborgs;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import eborgs.opengl.GLColor4f;
import eborgs.opengl.GLDataType;
import eborgs.opengl.GLDisplay;
import eborgs.opengl.GLException;
import eborgs.opengl.GLViewport;
import eborgs.opengl.shader.GLProgram;
import eborgs.opengl.shader.GLProgramLoader;
import eborgs.opengl.texture.GLFrameBuffer;
import eborgs.opengl.texture.GLRGBATextureLoader;
import eborgs.opengl.texture.GLTexture;
import eborgs.opengl.texture.GLTextureParams;
import eborgs.opengl.vertex.GLIndexBufferLoader;
import eborgs.opengl.vertex.GLVertexArray;
import eborgs.opengl.vertex.GLVertexAttribute;
import eborgs.opengl.vertex.GLVertexBufferLoader;
import eborgs.resource.Resource;

public class Test {

	private static String vertShader = ""
			+ "#version 400 core\n"
			+ "out vec2 frag_TexCoords;"
			+ "layout(location = 0) in  vec2 vert_Position;"
			+ "void main() {"
			+ "    gl_Position    = vec4(vert_Position, 0.0, 1.0);"
			+ "    frag_TexCoords = gl_Position.xy / gl_Position.w / 2.0 + 0.5;"
			+ "}";

	private static String fragShader = ""
			+ "#version 400 core\n"
			+ "layout(location = 0) out vec4 out_Color;"
			+ "in vec2 frag_TexCoords;"
			+ "uniform sampler2D texture0;"
			+ "void main() {"
			+ "    out_Color = texture(texture0, frag_TexCoords);"
			+ "}";

	public static void main(String[] args) {
		try {
			GLDisplay.create("Test", 800, 600, false, true);

			GLProgram program = GLProgramLoader.create("Test", vertShader, null, fragShader);

			GLFrameBuffer frameBuffer = GLFrameBuffer.create("Test", 800, 600, GLTextureParams.NEAREST);

			GLTexture texture0 = GLRGBATextureLoader.create("Test", GLColor4f.red, 800, 600, GLTextureParams.LINEAR);

			ByteBuffer indexBuffer = BufferUtils.createByteBuffer(6);
			indexBuffer.put((byte) 0).put((byte) 2).put((byte) 3);
			indexBuffer.put((byte) 0).put((byte) 3).put((byte) 1);
			indexBuffer.flip();

			FloatBuffer positionBuffer = BufferUtils.createFloatBuffer(4 * 2);
			positionBuffer.put(-1f).put(-1f);
			positionBuffer.put(+1f).put(-1f);
			positionBuffer.put(-1f).put(+1f);
			positionBuffer.put(+1f).put(+1f);
			positionBuffer.flip();

			GLVertexArray vertexArray = new GLVertexArray("Test", 6, GL11.GL_TRIANGLES);

			vertexArray.setIndexBuffer(GLIndexBufferLoader.create("Test", GL15.GL_STATIC_DRAW, indexBuffer));

			vertexArray.addVertexBuffer(GLVertexBufferLoader.create("Test", GL15.GL_STATIC_DRAW, 1, positionBuffer)
					.setAttribute(0, new GLVertexAttribute(2, GLDataType.FLOAT, false, "vert_Position")));

			Resource.printResources(System.out);

			while (!Display.isCloseRequested()) {
				if (Display.wasResized()) {
					GLViewport.setViewport(0, 0, GLDisplay.getWidth(), GLDisplay.getHeight());
				}
				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

				program.bind();
				program.setUniform1i("texture0", 0);

				frameBuffer.beginRender();
				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

				texture0.bind();
				vertexArray.bind().draw();
				frameBuffer.endRender();

				frameBuffer.getColorAttachment(0).bind();
				vertexArray.bind().draw();

				Display.update();
				Display.sync(60);
			}

		} catch (LWJGLException | GLException e) {
			e.printStackTrace();

		} finally {
			Resource.deleteAll();
			GLDisplay.destroy();
		}
	}

}
