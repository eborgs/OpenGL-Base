package eborgs.opengl;

import eborgs.resource.Resource;

public abstract class GLObject extends Resource {

	public final String name;
	public final GLObjectType type;

	public GLObject(GLObjectType type, String name) {
		this.type = type;
		this.name = name;
	}

	@Override
	public String toString() {
		return type.name() + "{" + name + "}";
	}

}
