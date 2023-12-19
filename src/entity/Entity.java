package entity;

import sharedObject.IRenderable;

public abstract class Entity implements IRenderable {

	protected int z;
	protected boolean visible;

	protected Entity() {
		this.visible = true;
	}

	protected Entity(int z) {
		this.z = z;
		this.visible = true;
	}

	protected Entity(int z, boolean visible) {
		this.z = z;
		this.visible = visible;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public abstract void update();
}
