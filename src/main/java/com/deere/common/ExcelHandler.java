package com.deere.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelHandler<T> {

	Class<T> clazz;
	SimpleDateFormat sdf = new SimpleDateFormat(Constants.dfyyMMdd);

	public ExcelHandler(Class<T> clazz) {

		this.clazz = clazz;
	}

	@SuppressWarnings("unchecked")
	public void exportExcel(String title, Collection<T> dataset, OutputStream out) {

		try {

			Iterator<T> iterator = dataset.iterator();

			if (!iterator.hasNext() || (title == null) || (out == null)) {
				throw new Exception("Please Choose Correct File");

			}
			// 取得实际泛型类

			T tObject = iterator.next();
			Class<T> clazz = (Class<T>) tObject.getClass();

			HSSFWorkbook workbook = new HSSFWorkbook();

			// 生成一个表格
			HSSFSheet sheet = workbook.createSheet(title);

			// 设置表格默认列宽度为20个字节
			sheet.setDefaultColumnWidth(20);

			// 生成一个样式
			HSSFCellStyle style = workbook.createCellStyle();

			// 设置标题样式
			// style = ExcelStyle.setHeadStyle(workbook, style);

			// 得到所有字段
			Field filed[] = tObject.getClass().getDeclaredFields();

			// 标题

			List<String> exportfieldtile = new ArrayList<String>();
			// 导出的字段的get方法

			List<Method> methodObj = new ArrayList<Method>();

			// 遍历整个filed

			for (Field field : filed) {
				ExcelAnnotation excelAnnotation = field.getAnnotation(ExcelAnnotation.class);
				// 如果设置了annottion

				if (excelAnnotation != null) {
					String exprot = excelAnnotation.field();

					// 添加到标题
					exportfieldtile.add(exprot);

					// 添加到需要导出的字段的方法
					String fieldname = field.getName();

					String getMethodName = "get" + fieldname.substring(0, 1).toUpperCase() + fieldname.substring(1);
					Method getMethod = clazz.getMethod(getMethodName, new Class[] {});

					methodObj.add(getMethod);
				}

			}

			// 产生表格标题行
			HSSFRow row = sheet.createRow(0);

			for (int i = 0; i < exportfieldtile.size(); i++) {
				HSSFCell cell = row.createCell(i);

				cell.setCellStyle(style);
				HSSFRichTextString text = new HSSFRichTextString(exportfieldtile.get(i));

				cell.setCellValue(text);
			}

			// 循环整个集合

			int index = 0;
			iterator = dataset.iterator();

			while (iterator.hasNext()) {
				// 从第二行开始写，第一行是标题

				index++;
				row = sheet.createRow(index);

				T t = iterator.next();
				for (int k = 0; k < methodObj.size(); k++) {

					HSSFCell cell = row.createCell(k);
					Method getMethod = methodObj.get(k);
					Object value = getMethod.invoke(t, new Object[] {});
					// String textValue = getValue(value);
					if (value instanceof Integer) {
						cell.setCellValue((Integer) value);
					} else {
						cell.setCellValue(getValue(value));
					}
				}

			}
			workbook.write(out);

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public String getValue(Object value) {

		String textValue = "";
		if (value == null) {

			return textValue;
		}

		if (value instanceof Boolean) {
			boolean bValue = (Boolean) value;

			textValue = "Yes";
			if (!bValue) {

				textValue = "No";
			}

		} else if (value instanceof Date) {
			Date date = (Date) value;

			textValue = sdf.format(date);

		} else {
			textValue = value.toString();

		}
		return textValue;

	}

	public Sheet getSheet(File file, String suffix) throws Exception {

		InputStream inputStream = new FileInputStream(file);

		Sheet sheet = null;
		if (suffix.equals(Constants.excelXLSX)) {
			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workBook = new XSSFWorkbook(inputStream);
			sheet = workBook.getSheetAt(0);
		} else if (suffix.equals(Constants.excelXLS)) {
			// Create Workbook instance holding reference to .xls file
			HSSFWorkbook workBook = new HSSFWorkbook(inputStream);
			sheet = workBook.getSheetAt(0);
		}

		inputStream.close();
		return sheet;

	}

	public Collection<T> importExcel(File file, String pattern) {

		Collection<T> dist = new ArrayList<T>();
		try {

			/**
			 * 类反射得到调用方法
			 */
			// 得到目标目标类的所有的字段列表

			Field[] fields = clazz.getDeclaredFields();
			// 将所有标有Annotation的字段，也就是允许导入数据的字段,放入到一个map中

			Map<String, Method> fieldMap = new HashMap<String, Method>();
			// 循环读取所有字段

			for (Field field : fields) {
				// 得到单个字段上的Annotation

				ExcelAnnotation excelAnnotation = field.getAnnotation(ExcelAnnotation.class);
				// 如果标识了Annotationd

				if (excelAnnotation != null) {
					String fieldName = field.getName();

					// 构造设置了Annotation的字段的Setter方法
					String setMethodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

					// 构造调用的method
					Method setMethod = clazz.getMethod(setMethodName, new Class[] { field.getType() });

					// 将这个method以Annotaion的名字为key来存入
					fieldMap.put(excelAnnotation.field(), setMethod);

				}
			}

			/**
			 * 
			 * excel的解析开始
			 */

			// 将传入的File构造为FileInputStream;
			FileInputStream inputStream = new FileInputStream(file);

			// 得到工作表

			// 得到第一页
			Sheet sheet = getSheet(file, pattern);

			// 得到第一面的所有行
			Iterator<Row> row = sheet.rowIterator();

			/**
			 * 
			 * 标题解析
			 */

			// 得到第一行，也就是标题行
			Row titleRow = row.next();

			// 得到第一行的所有列
			Iterator<Cell> cellTitle = titleRow.cellIterator();

			// 将标题的文字内容放入到一个map中
			Map<Integer, String> titleMap = new HashMap<Integer, String>();

			// 从标题第一列开始
			int i = 0;

			// 循环标题所有的列
			while (cellTitle.hasNext()) {

				Cell cell = cellTitle.next();
				String value = cell.getStringCellValue();

				titleMap.put(i, value);
				i++;

			}

			/**
			 * 解析内容行
			 */
			while (row.hasNext()) {

				// 标题下的第一行
				Row rown = row.next();

				// 行的所有列
				Iterator<Cell> cellBody = rown.cellIterator();

				// 得到传入类的实例
				T tObject = clazz.newInstance();

				// 遍历一行的列
				// int col = 0;
				for (int col = 0; col < titleMap.size(); col++) {
					try {

						Cell cell = rown.getCell(col);

						// 这里得到此列的对应的标题
						String titleString = titleMap.get(col);

						// 如果这一列的标题和类中的某一列的Annotation相同，那么则调用此类的的set方法，进行设值
						if (fieldMap.containsKey(titleString)) {

							Method setMethod = fieldMap.get(titleString);

							// 得到setter方法的参数

							Type[] types = setMethod.getGenericParameterTypes();
							// 只要一个参数

							String xclass = String.valueOf(types[0]);
							// 判断参数类型

							if ("class java.lang.String".equals(xclass)) {
								setMethod.invoke(tObject, cell2String(cell));

							} else if ("class java.util.Date".equals(xclass)) {
								String value = cell2String(cell);
								if (StringUtils.isNotEmpty(value)) {
									setMethod.invoke(tObject,
											DateUtils.parseDate(cell2String(cell), Constants.pattern));
								}

							} else if ("class java.lang.Boolean".equals(xclass)) {
								Boolean boolName = true;

								if ("否".equals(cell.getStringCellValue())) {
									boolName = false;

								}
								setMethod.invoke(tObject, boolName);

							} else if ("class java.lang.Integer".equals(xclass)) {
								String cellStr =cell2String(cell);
								if(cellStr=="")
									cellStr="0";
								setMethod.invoke(tObject, new Integer(cellStr));

							} else if ("class java.lang.Long".equals(xclass)) {
								setMethod.invoke(tObject, new Long(cell.getStringCellValue()));

							} else {
								//

							}

						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				dist.add(tObject);

			}

		} catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();
			return null;

		}
		return dist;

	}

	private String cell2String(Cell cell) {
		Object obj = "";
		// CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
		if (cell != null) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				obj = cell.getRichStringCellValue().getString();
				break;
			case Cell.CELL_TYPE_NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					obj = new SimpleDateFormat(Constants.dfyyMMdd).format(cell.getDateCellValue());// new JDateTime();
				} else {
					obj = new DecimalFormat("#").format(cell.getNumericCellValue());
				}
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				obj = cell.getBooleanCellValue();
				break;
			case Cell.CELL_TYPE_FORMULA:
				FormulaEvaluator evaluator = cell.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
				evaluator.evaluateFormulaCell(cell);
				CellValue cellValue = evaluator.evaluate(cell);
				obj = cellValue.getStringValue();
				break;
			default:
				obj = "";
			}
		}
		return String.valueOf(obj).trim();
	}
}
