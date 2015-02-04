package com.deere.service.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.deere.exception.GenericException;

public class ExcelService {
	// public static String reportPath = "C:\\report";
	public static String reportPath = "C:\\report\\" + Calendar.getInstance().get(Calendar.YEAR) + "年\\"
			+ (Calendar.getInstance().get(Calendar.MONTH) + 1) + "月\\"
			+ Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "日";

	public XSSFWorkbook getWorkBook(String fileName) throws GenericException {
		InputStream is = null;
		try {
			is = new FileInputStream(this.getClass().getClassLoader().getResource(fileName).getPath());// 模板文件路径需要修改
		} catch (FileNotFoundException e) {
			// 找不到模板文件
			throw new GenericException("SOR00102", "找不到模板或模板格式错误", e);
		}
		XSSFWorkbook xSSFWorkbook = null;
		try {
			xSSFWorkbook = new XSSFWorkbook(is);
		} catch (IOException e) {
			// 找不到模板文件
			throw new GenericException("SOR00102", "找不到模板或模板格式错误", e);
		}

		return xSSFWorkbook;
	}

	public XSSFSheet getSheet(XSSFWorkbook workBook, int index) {
		return workBook.getSheetAt(index);
	}

	public XSSFSheet getSheet(String fileName, int index) throws GenericException {
		XSSFWorkbook workBook = getWorkBook(fileName);
		return workBook.getSheetAt(index);
	}

	public File saveWorkbook(XSSFWorkbook xSSFWorkbook, String excelName) throws GenericException {
		File file = new File(reportPath);
		// 可以在不存在的目录中创建文件夹。诸如：a\\b,既可以创建多级目录。
		file.mkdirs();
		OutputStream out;
		File rFile = null;
		try {
			// [经销商员工ID]_[YYYYMMDD]_[BOP_销售信息收集与分析表(Conversion Rate).xlsm]的报表文件，保存到[reportPath]

			String excelpath = reportPath + "\\" + excelName;
			out = new FileOutputStream(excelpath);
			xSSFWorkbook.write(out);
			out.close();
			rFile = new File(excelpath);
		} catch (Exception e) {
			throw new GenericException("SOR00104", "生成文件错误", e);
		}
		return rFile;
	}
}
