package com.deere.common;

import java.util.List;

public interface DataCollection<T> {
	public List<T> getData(int start, int end);
}