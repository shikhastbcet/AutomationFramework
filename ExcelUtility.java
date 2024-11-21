package utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtility {
	private String filePath;

	public ExcelUtility(String filePath) {
		this.filePath = filePath;
		System.out.println("======================== ExcelUtility ====================================");
	}

	public List<Object[]> getTestData(String sheetName) {
		List<Object[]> data = new ArrayList<>();

		try (FileInputStream fis = new FileInputStream(filePath); Workbook workbook = new XSSFWorkbook(fis)) {

			Sheet sheet = workbook.getSheet(sheetName);
			int rows = sheet.getPhysicalNumberOfRows();

			for (int i = 1; i < rows; i++) { // Start from row 1 to skip headers
				Row row = sheet.getRow(i);
				if (row == null)
					continue; // Skip empty rows

				String runStatus = getStringCellValue(row.getCell(3)); // RunStatus
				String controlStatus = getStringCellValue(row.getCell(4)); // Control

				if ((runStatus.equalsIgnoreCase("Yes") || runStatus.equalsIgnoreCase("Y"))
						&& (controlStatus.equalsIgnoreCase("v") || controlStatus.equalsIgnoreCase("c")
								|| controlStatus.equalsIgnoreCase("t"))) {
					
					String SrNo = getStringCellValue(row.getCell(0)); // Module
					String module = getStringCellValue(row.getCell(1)); // Module
					String pageName = getStringCellValue(row.getCell(2)); // Page_Name
					String control = getStringCellValue(row.getCell(4)); // Control
					String propertyName = getStringCellValue(row.getCell(5)); // PropertyName
					String propertyValue = getStringCellValue(row.getCell(6)); // PropertyValue
					String dataField = getStringCellValue(row.getCell(7)); // DataField
					String action = getStringCellValue(row.getCell(8)); // Action
					// Print extracted values for debugging
//                    System.out.println("Row " +( i++) + " - Module: " + module + 
//                                       ", PageName: " + pageName + 
//                                       ", Control: " + control + 
//                                       ", PropertyName: " + propertyName + 
//                                       ", PropertyValue: " + propertyValue + 
//                                       ", DataField: " + dataField + 
//                                       ", Action: " + action);
					data.add(new Object[] { module, pageName, control, propertyName, propertyValue, dataField, action,
							SrNo });
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return data;
	}

	private String getStringCellValue(Cell cell) {
		return (cell != null) ? cell.toString().trim() : ""; // Return empty string if cell is null
	}
}
