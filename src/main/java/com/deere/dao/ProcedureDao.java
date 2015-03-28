package com.deere.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface ProcedureDao {
	public void callProc(String proc, List<String> para);
	public List callProcR(String proc, List<String> para);
//	public ResultSet callProcr(String proc, List<String> para) throws SQLException;
}
