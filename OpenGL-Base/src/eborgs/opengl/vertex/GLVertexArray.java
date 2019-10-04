package eborgs.opengl.vertex;

import java.io.PrintStream;

import eborgs.opengl.GLLog;
import eborgs.opengl.GLObject;
import eborgs.opengl.GLObjectType;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class GLVertexArray extends GLObject {

	private final int glVertexArray;

	public final int count;
	public final int mode;

	private int maxAttributes;
	private int attributeCount;
	private int attributeIndexCount;

	private int vertexBufferCount;

	private boolean indexed;
	private int indexType;
	private int indexSize;

	private GLVertexAttribute[] attributes;
	private GLVertexBuffer[] vertexBuffers;
	private GLIndexBuffer indexBuffer;

	public GLVertexArray(String name, int count, int mode) {
		super(GLObjectType.GL_VERTEX_ARRAY, name);
		this.count = count;
		this.mode = mode;
		this.glVertexArray = createVertexArray();
	}

	private int createVertexArray() {
		maxAttributes = GL11.glGetInteger(GL20.GL_MAX_VERTEX_ATTRIBS);
		vertexBuffers = new GLVertexBuffer[maxAttributes];
		attributes = new GLVertexAttribute[maxAttributes];
		return GL30.glGenVertexArrays();
	}

	@Override
	protected void deleteInternal() {
		if (indexBuffer != null) {
			indexBuffer.deleteNonShared();
			indexBuffer = null;
		}
		for (int i = 0; i < vertexBufferCount; i++) {
			GLVertexBuffer vertexBuffer = vertexBuffers[i];

			vertexBuffers[i] = null;
			vertexBuffer.deleteNonShared();
		}
		GL30.glDeleteVertexArrays(glVertexArray);
	}

	public GLVertexArray setIndexBuffer(GLIndexBuffer buffer) {
		if (indexBuffer != null && indexBuffer != buffer) {
			indexBuffer.deleteNonShared();
		}
		indexBuffer = buffer;
		indexed = buffer != null;
		indexType = buffer != null ? buffer.indexType.glType : 0;
		indexSize = buffer != null ? buffer.indexType.size : 0;

		GL30.glBindVertexArray(glVertexArray);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indexed ? buffer.glBuffer : 0);
		return this;
	}

	public GLVertexArray addVertexBuffer(GLVertexBuffer buffer) {
		if (buffer.glBuffer == 0) {
			GLLog.log.warning(this + ": Added zero vertex buffer: " + buffer);
			return this;
		}
		int offset = 0;
		int stride = 0;
		int indexCount = 0;
		for (GLVertexAttribute attribute : buffer.getAttributes()) {
			indexCount += (1 + (attribute.size - 1) / 4);
			stride += attribute.size * attribute.dataType.size;
		}
		if (attributeIndexCount + indexCount > maxAttributes) {
			GLLog.log.warning(this + ": Max attribute indix (" + maxAttributes + ") exceeded by " + buffer);
			printAttributes(System.err);
			return this;
		}
		GL30.glBindVertexArray(glVertexArray);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, buffer.glBuffer);

		for (GLVertexAttribute attribute : buffer.getAttributes()) {
			int index = attributeIndexCount;
			int size = attribute.size;
			int type = attribute.dataType.glType;
			
			if (attribute.integerType) {
				GL30.glVertexAttribIPointer(index, size, type, stride, offset);
			} else {
				GL20.glVertexAttribPointer(index, size, type, attribute.normalize, stride, offset);
			}
			offset += size * attribute.dataType.size;
			attributeIndexCount += (1 + (size - 1) / 4);

			attributes[attributeCount++] = attribute;
		}
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

		vertexBuffers[vertexBufferCount++] = buffer;
		return this;
	}

	public void printAttributes(PrintStream printStream) {
		StringBuilder sb = new StringBuilder();
		String newLine = System.lineSeparator();
		sb.append(this).append(": ").append(attributeCount).append('/').append(maxAttributes).append(" attribute(s):")
				.append(newLine);
		int index = 0;
		for (GLVertexAttribute attribute : attributes) {
			if (attribute == null) {
				break;
			}
			sb.append(index++).append(" - ").append(attribute).append(newLine);
			int indexCount = (attribute.size + 3) / 4;
			for (int i = 0; i < indexCount - 1; i++) {
				sb.append(index++).append(" - ...").append(newLine);
			}
		}
		printStream.println(sb.toString());
	}

	public GLVertexArray bind() {
		GL30.glBindVertexArray(glVertexArray);
		for (int i = 0, len = attributeIndexCount; i < len; i++) {
			GL20.glEnableVertexAttribArray(i);
		}
		return this;
	}

	public void unbind() {
		for (int i = 0, len = attributeIndexCount; i < len; i++) {
			GL20.glDisableVertexAttribArray(i);
		}
		GL30.glBindVertexArray(0);
	}

	public GLVertexArray draw() {
		if (indexed) {
			GL11.glDrawElements(mode, count, indexType, 0);
		} else {
			GL11.glDrawArrays(mode, 0, count);
		}
		return this;
	}

	public GLVertexArray draw(int first, int count) {
		assert (first >= 0);
		assert (count >= 0);
		assert (first + count <= count);
		if (indexed) {
			GL11.glDrawElements(mode, count, indexType, first * indexSize);
		} else {
			GL11.glDrawArrays(mode, first, count);
		}
		return this;
	}

}
