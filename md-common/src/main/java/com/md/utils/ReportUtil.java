/**
 * ReportUtil.java <br>
 * com.wjs.utils <br>
 * Copyright (c) 2016.
 */
package com.md.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * 报表工具类
 * <p>
 *
 * @version  V1.0.0
 */
public class ReportUtil {

	public static void exportExcel( HSSFWorkbook workbook, String headTitle, int sheetNum, String sheetTitle, List headList, List listMain, List listDBParameter ) {
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet();
		workbook.setSheetName(sheetNum, sheetTitle);
		// 设置表格默认列宽度为20个字节
		sheet.setDefaultColumnWidth((short) 20);
		//表头样式
		CellStyle titleStyle = workbook.createCellStyle();
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		Font titleFont = workbook.createFont();
		titleFont.setFontHeightInPoints((short) 20);
		titleFont.setBoldweight((short) 700);
		titleStyle.setFont(titleFont);
		// 指定当单元格内容显示不下时自动换行
		titleStyle.setWrapText(true);

		// 列头样式
		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headerStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
		Font headerFont = workbook.createFont();
		headerFont.setFontHeightInPoints((short) 12);
		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headerStyle.setFont(headerFont);

		// 合并单元格样式
		CellStyle custumCellStyle = workbook.createCellStyle();
		custumCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 居中  
		custumCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直


		// 产生表格标题行
		HSSFRow titleRow = sheet.createRow(0);
		titleRow.createCell(0).setCellValue(headTitle);
		titleRow.getCell(0).setCellStyle(titleStyle);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headList.size() - 1));

		HSSFRow row = sheet.createRow(1);
		for ( int i = 0 ; i < headList.size() ; i++ ) {
			HSSFCell cell = row.createCell((short) i);
			cell.setCellStyle(headerStyle);
			HSSFRichTextString text = new HSSFRichTextString(headList.get(i).toString());
			cell.setCellValue(text.toString());
		}

		// 遍历集合数据，产生数据行
		Map<String, Integer> rowMap = new HashMap<String, Integer>();
		int rowNum = 1;

		// ============== 1.找出每行需要合并的数量============begin
		for ( int x = 0 ; x < listMain.size() ; x++ ) {
			Map perExcelRow = (Map) listMain.get(x);
			String repeatKey = String.valueOf(perExcelRow.get(listDBParameter.get(0).toString()));
			//System.out.println("当前行：" + currentRow + "  的值是：" + repeatKey);
			if ( rowMap.containsKey(repeatKey) ) {
				rowNum++;
			} else {
				rowNum = 0;
			}

			rowMap.put(repeatKey, rowNum);
		}

		// ===============================================end

		ArrayList<String> list = new ArrayList<String>();
		for ( int i = 0 ; i < listMain.size() ; i++ ) {
			Map perExcelRow = (Map) listMain.get(i);
			row = sheet.createRow(i + 2);
			row.setHeightInPoints(20);
			for ( int j = 0 ; j < listDBParameter.size() ; j++ ) {
				HSSFCell rowcell0 = row.createCell(j);
				Object DBGetI = perExcelRow.get(listDBParameter.get(j).toString()) != null ? perExcelRow.get(listDBParameter.get(j).toString()) : "暂无";
				//System.out.print(perExcelRow.get(listDBParameter.get(j).toString()) + "     ");
				if ( DBGetI instanceof Double ) {
					rowcell0.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					rowcell0.setCellValue(Double.parseDouble(String.valueOf(DBGetI)));
				} else {
					rowcell0.setCellValue(DBGetI.toString());
					rowcell0.setCellStyle(custumCellStyle);
				}
			}

			// 根据首列的值合并单元格
			String currentKey = String.valueOf(perExcelRow.get(listDBParameter.get(0).toString()));
			int currnetRow = row.getRowNum();
			if ( rowMap.containsKey(currentKey) && (Integer.parseInt(rowMap.get(currentKey) + "")) > 0 && !list.contains(currentKey) ) {
				int num = (Integer.parseInt(rowMap.get(currentKey) + ""));
				//System.out.println("当前行：" + (rowIndex) + "  对应的值是：" + currentKey + " 需要合并的数量是：" + num);
				//  四个参数分别是：首行、最后一行、首列、最后一列。     
				sheet.addMergedRegion(new CellRangeAddress(currnetRow, currnetRow + num, 0, 0));

				HSSFCell myCell = sheet.getRow(currnetRow).getCell(0);
				myCell.setCellStyle(custumCellStyle);

				list.add(currentKey);
			}
		}

	}


	/**
	* 	此方法用于下载报表
	* @param fileName       文件名
	* @param sheetName       sheet页名
	* @param titleList      存放第一列的title
	* @param listMain       存放分条显示的数据
	* @param listDBParameter 存放sql中的别名
	* @param countMap        存放合计数据
	* @param response      
	* TODO:titleList 和 listDBParameter 可以用一个MAP来标记
	*/
	public static void downLoadXLS( String fileName, String sheetName, List titleList, List listMain, List listDBParameter, Map countMap, HttpServletResponse response ) {
		try {
			fileName = URLEncoder.encode(fileName + ".xls", "UTF-8");
		} catch ( UnsupportedEncodingException e1 ) {
			e1.printStackTrace();
		}

		HSSFWorkbook wb = new HSSFWorkbook();

		HSSFSheet sheet = wb.createSheet(sheetName);

		// 设置第一行的行高
		HSSFRow row0 = sheet.createRow(0);
		row0.setHeightInPoints(25);

		//添加第一行标题
		for ( int i = 0 ; i < titleList.size() ; i++ ) {
			sheet.setColumnWidth(i, 15 * 256); //设置每列宽度15
			HSSFCell row0cell0 = row0.createCell(i);
			row0cell0.setCellValue(titleList.get(i).toString());
		}

		//添加每条信息
		for ( int i = 0 ; i < listMain.size() ; i++ ) {
			Map perExcelRow = (Map) listMain.get(i);
			HSSFRow row = sheet.createRow(i + 1);
			row.setHeightInPoints(20);
			for ( int j = 0 ; j < listDBParameter.size() ; j++ ) {
				HSSFCell rowcell0 = row.createCell(j);
				Object DBGetI = perExcelRow.get(listDBParameter.get(j).toString()) != null ? perExcelRow.get(listDBParameter.get(j).toString()) : "暂无";
				if ( DBGetI instanceof Double ) {
					rowcell0.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					rowcell0.setCellValue(Double.parseDouble(String.valueOf(DBGetI)));
				} else {
					rowcell0.setCellValue(DBGetI.toString());
				}
			}
		}
		if ( countMap != null ) {
			HSSFRow rowLast = sheet.createRow(listMain.size() + 2);
			int flag = 0;
			for ( Object key : countMap.keySet() ) {
				flag++;
				HSSFCell cell2 = rowLast.createCell(flag);
				cell2.setCellValue(key.toString());
				flag++;
				HSSFCell cell3 = rowLast.createCell(flag);
				cell3.setCellValue(StringUtils.isNotBank(countMap, key.toString()));

			}
		}

		OutputStream outputStream = null;
		try {
			response.reset();
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			response.setContentType("application/octet-stream;charset=UTF-8");
			outputStream = new BufferedOutputStream(response.getOutputStream());
			wb.write(outputStream);
			outputStream.flush();

		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			try {
				outputStream.close();
			} catch ( IOException e ) {
				e.printStackTrace();
			}
		}
	}
}


class Student {

	private String name;
	private int age;
	private Date birthday;
	private float height;
	private double weight;
	private boolean sex;


	public String getName() {
		return name;
	}


	public void setName( String name ) {
		this.name = name;
	}


	public Integer getAge() {
		return age;
	}


	public Date getBirthday() {
		return birthday;
	}


	public void setBirthday( Date birthday ) {
		this.birthday = birthday;
	}


	public float getHeight() {
		return height;
	}


	public void setHeight( float height ) {
		this.height = height;
	}


	public double getWeight() {
		return weight;
	}


	public void setWeight( double weight ) {
		this.weight = weight;
	}


	public boolean isSex() {
		return sex;
	}


	public void setSex( boolean sex ) {
		this.sex = sex;
	}


	public void setAge( Integer age ) {
		this.age = age;
	}
}