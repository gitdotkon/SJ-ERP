package com.deere.model.pk;

import java.io.Serializable;

import com.deere.model.GenericPart;

public class BOMTreeComposeIdPk implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3553276444957991823L;
	private GenericPart parent;
	private GenericPart child;
	public GenericPart getParent() {
		return parent;
	}
	public void setParent(GenericPart parent) {
		this.parent = parent;
	}
	public GenericPart getChild() {
		return child;
	}
	public void setChild(GenericPart child) {
		this.child = child;
	}
	
}
