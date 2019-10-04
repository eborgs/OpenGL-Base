package eborgs.opengl.vertex;

import eborgs.resource.Resource;

public class GLVertexData extends Resource {

	public final int first;
	public final int count;

	public final GLVertexArray vertices;

	public GLVertexData(GLVertexArray vertices) {
		this(0, -1, vertices);
	}

	public GLVertexData(int first, int count, GLVertexArray vertices) {
		this.first = first;
		this.count = count;
		this.vertices = vertices;
	}

	public void bind() {
		vertices.bind();
	}

	public void draw() {
		if (count == -1) {
			vertices.draw();
		} else {
			vertices.draw(first, count);
		}
	}

	public void bindAndDraw() {
		if (count == -1) {
			vertices.bind().draw();
		} else {
			vertices.bind().draw(first, count);
		}
	}

	@Override
	protected void deleteInternal() {
		vertices.deleteNonShared();
	}

}
