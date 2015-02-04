package com.deere.service;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.deere.common.Constants;
import com.deere.common.ExcelHandler;
import com.deere.common.Utils;
import com.deere.exception.GenericException;

public class DataImportService<T, E> {

	private Class<T> dtoType;
	private Class<E> modelType;

	public DataImportService() {
	}

	public DataImportService(Class<T> dtoType, Class<E> modelType) {
		this.dtoType = dtoType;
		this.modelType = modelType;
		// this.genericDao = GenericDao<E>.class;
	}

	public List<E> importSpreadsheet(File file, String suffix) throws GenericException {
		// String batchNo = String.valueOf(Calendar.getInstance().getTimeInMillis());
		// importSpreadsheet(file, suffix, batchNo);
		// int a = 1/0;

		if ((null == file) || (null == suffix)) {
			throw new GenericException(Constants.ERR_CODE_IMS_01, "File object or file suffix is null");
		}

		ExcelHandler<T> excelHandler = new ExcelHandler<T>(dtoType);
		List<T> dataList = (List<T>) excelHandler.importExcel(file, suffix);
		int row = 1;
		List<E> mList = new ArrayList<E>();
		try {
			for (T t : dataList) {
				E e = modelType.newInstance();
				BeanUtils.copyProperties(t, e);
				Method getId = Utils.findGetId(modelType);
				Object id = getId.invoke(e);
				if ((id != null) && (id.toString() != "")) {
					mList.add(e);
				} else {
					getId = Utils.findGetId(dtoType);
					id = getId.invoke(t);
					if ((id != null) && (id.toString() != "")) {
						mList.add(e);
					}
				}
				row++;
			}
		} catch (Exception ex) {
			throw new GenericException(Constants.ERR_CODE_IMS_02, "Please verifiy row "+file.getName() + row, ex);
		}
		return mList;
	}


}