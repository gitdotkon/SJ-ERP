package com.deere.dao;

import java.util.List;

public interface ProcedureDao {
	public void callProc(String proc, List<String> para);
}
