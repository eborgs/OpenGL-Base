package eborgs.resource;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.LinkedList;

public abstract class Resource {

	private boolean deleted;

	public boolean shared = false;
	public boolean missing = false;

	public Resource() {
		onResourceCreated(this);
	}

	protected abstract void deleteInternal();

	public boolean isShared() {
		return shared;
	}

	public void deleteNonShared() {
		if (!isShared()) {
			delete();
		}
	}

	public void delete() {
		if (!deleted) {
			deleted = true;
			onResourceDeleted(this);
			deleteInternal();
		}
	}

	public boolean isDeleted() {
		return deleted;
	}

	@Override
	protected void finalize() throws Throwable {
		assert (deleted);
	}

	private static final LinkedList<Resource> resources = new LinkedList<>();

	private static boolean doRemove = true;

	private static void addResource(Resource resource) {
		synchronized (resources) {
			resources.add(resource);
		}
	}

	private static void removeResource(Resource resource) {
		synchronized (resources) {
			if (doRemove) {
				resources.remove(resource);
			}
		}
	}

	private static void onResourceCreated(Resource resource) {
		addResource(resource);
	}

	private static void onResourceDeleted(Resource resource) {
		removeResource(resource);
	}

	public static int getCount() {
		synchronized (resources) {
			return resources.size();
		}
	}

	public static void printResources(PrintStream printStream) {
		StringBuilder sb = new StringBuilder();
		String newLine = System.lineSeparator();
		synchronized (resources) {
			sb.append(getCount()).append(" resource(s):").append(newLine);

			for (Iterator<Resource> i = resources.iterator(); i.hasNext();) {
				sb.append(i.next().toString()).append(newLine);
			}
			printStream.println(sb.toString());
		}
	}

	public static void deleteAll() {
		synchronized (resources) {
			try {
				doRemove = false;
				for (Iterator<Resource> i = resources.iterator(); i.hasNext();) {
					Resource resource = i.next();
					try {
						resource.delete();
					} catch (Throwable t) {
						t.printStackTrace();
					}
				}
			} finally {
				doRemove = true;
			}
			resources.clear();
		}
	}
}
