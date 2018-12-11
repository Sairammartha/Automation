package framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.poi.common.usermodel.Hyperlink;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelConnect {

	public static XSSFWorkbook workbook;
	public static XSSFSheet spreadsheet;
	public static XSSFCell objCell;
	public static String strSheetName, strFilePath, cellValue;
	public static int intRowCount, intColCount;

	public static XSSFWorkbook getWorkbookObject() {
		File inputfile = new File(strFilePath);
		if (!inputfile.exists()) {
			System.out.println("File does not Exists : " + strFilePath);
			return null;
		}
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(inputfile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			workbook = new XSSFWorkbook(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return workbook;
	}

	public static XSSFSheet getSheetObject() {

		if (sheetExists() > -1) {
			spreadsheet = workbook.getSheet(strSheetName);
			getRowCount();
			getColumnCount();
			return spreadsheet;
		} else {
			System.out.println("'" + strSheetName + "' Sheet doesnot exist in Excel file : " + strFilePath);
			return null;
		}
	}

	public static int sheetExists() {
		int intSheet = -1;
		try {
		int intSheets = workbook.getNumberOfSheets();
		for (int intI = 0; intI < intSheets; intI++) {
			String strCurrentSheetName = workbook.getSheetName(intI);
			if (strCurrentSheetName.equalsIgnoreCase(strSheetName)) {
				intSheet = intI;
				break;
			}
		}
		}catch (Exception e){
			System.out.println("Sheet doesnot exist in Excel file");
			e.printStackTrace();

		}
		return intSheet;
	}

	public static int getRowCount() {
		// spreadsheet = getSheetObject();
		intRowCount = spreadsheet.getLastRowNum();
		return intRowCount;
	}

	public static int getColumnCount() {
		// spreadsheet = getSheetObject();
		intColCount = spreadsheet.getRow(0).getLastCellNum();
		return intColCount;
	}

	public static Set<HashMap<String, String>> readExcelData(String strFilePath1, String strSheetName1) {
		strFilePath = strFilePath1;
		strSheetName = strSheetName1;
		try {
			Set<HashMap<String, String>> inputDetails = new LinkedHashSet<HashMap<String, String>>();
			HashMap<String, String> excelRecord = new HashMap<>();
			getWorkbookObject();
			getSheetObject();
			DataFormatter dataFormatter = new DataFormatter();
			List<String> excelHeader = new LinkedList<String>();
			for (int i = 0; i < intColCount; i++) {
				// defectHeader[i] = spreadsheet1.getRow(0).getCell(i).toString();
				cellValue = dataFormatter.formatCellValue(spreadsheet.getRow(0).getCell(i));
				excelHeader.add(cellValue);
			}
			for (int iRow = 1; iRow <= intRowCount; iRow++) {
				for (int j = 0; j < intColCount; j++) {
					cellValue = dataFormatter.formatCellValue(spreadsheet.getRow(iRow).getCell(j));
					excelRecord.put(excelHeader.get(j), cellValue);
				}
				inputDetails.add((HashMap<String, String>) excelRecord.clone());
			}
			return inputDetails;
		} catch (Exception e) {
			System.out.println("'" + strSheetName + "'Facing issue while reading the data from Excel file : " + strFilePath);
			e.printStackTrace();
			return  null;
		}
	}
	public static void saveExcelSheet(String reportPath) {
		// objWorkbook = new XSSFWorkbook(new FileInputStream(strFilePath));
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(reportPath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			workbook.write(fos);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void writeDataInExcel(XSSFWorkbook objWorkbook, String strSheetName, int intRow, int intCol,
			String strText) {
		try {
			XSSFSheet objSheet = objWorkbook.getSheet(strSheetName);
			XSSFRow Row1 = objSheet.getRow(intRow - 1);

			if (Row1 == null) {
				objCell = objSheet.createRow(intRow - 1).createCell(intCol - 1);
				objCell.setCellValue(strText);
				// excelconnect.objCell=Cell1;
			} else {
				objCell = Row1.getCell(intCol - 1);
				if (objCell == null) {
					objCell = Row1.createCell(intCol - 1);
					objCell.setCellValue(strText);
					// excelconnect.objCell=Cell1;

				} else {
					objCell.setCellValue(strText);
					// excelconnect.objCell=Cell1;
				}
			}
			ExcelConnect.saveExcelSheet(Main.strReportPath);
		}
		catch (Exception e){
			System.out.println("'" + strSheetName + "'Facing issue while writing the data to Excel file : " + strFilePath);
			e.printStackTrace();
		}
	}

	public static void setHyperLink(String screenShotLink) {
		try{
		CellStyle hlinkstyle = workbook.createCellStyle();
		Font hlinkfont = workbook.createFont();
		hlinkfont.setUnderline(Font.U_SINGLE);
		hlinkfont.setColor(IndexedColors.BLUE.index);
		// hlinkstyle.setFont(hlinkfont);
		hlinkstyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
		hlinkstyle.setFont(hlinkfont);
		hlinkstyle.setWrapText(false);

		CreationHelper createHelper = workbook.getCreationHelper();
		Hyperlink hyperLink = createHelper.createHyperlink(Hyperlink.LINK_URL);
		hyperLink.setAddress(screenShotLink);

		objCell.setHyperlink((org.apache.poi.ss.usermodel.Hyperlink) hyperLink);
		objCell.setCellStyle(hlinkstyle);
		}catch (Exception e){
			System.out.println("Unable to set the Hyperlink");
			e.printStackTrace();
		}
	}
}
