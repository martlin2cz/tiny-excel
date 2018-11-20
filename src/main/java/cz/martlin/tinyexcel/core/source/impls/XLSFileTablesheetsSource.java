package cz.martlin.tinyexcel.core.source.impls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cz.martlin.tinyexcel.core.data.Table;
import cz.martlin.tinyexcel.core.data.Tablesbook;
import cz.martlin.tinyexcel.core.source.common.CommonSourceImpl;
import cz.martlin.tinyexcel.core.source.common.CommonTablesSource;

public class XLSFileTablesheetsSource extends CommonTablesSource<XSSFWorkbook> {

	public XLSFileTablesheetsSource() {
		super(new XLSFileSourceImpl());
	}

	public static class XLSFileSourceImpl implements
			CommonSourceImpl<XSSFWorkbook> {

		@Override
		public String doGetIdentifierPattern() {
			return "$(.+)\\.((xls)|(xlsx)|(XLS)|(XLSX))^";
		}

		@Override
		public XSSFWorkbook doLoadExternal(String identifier)
				throws IOException {

			File file = new File(identifier);
			FileInputStream fins = new FileInputStream(file);
			XSSFWorkbook workbook = new XSSFWorkbook(fins);
			return workbook;
			// TODO doAfterLoad -> close workbook
		}

		@Override
		public void doSaveExternal(String identifier, XSSFWorkbook workbook)
				throws IOException {

			File file = new File(identifier);
			FileOutputStream fous = new FileOutputStream(file);
			workbook.write(fous);
			workbook.close();
		}

		@Override
		public Tablesbook doExtractFrom(String identifier, XSSFWorkbook workbook)
				throws IOException {
			Tablesbook tablesbook = new Tablesbook();

			int sheets = workbook.getNumberOfSheets();
			for (int i = 0; i < sheets; i++) {
				XSSFSheet sheet = workbook.getSheetAt(i);
				String name = sheet.getSheetName();
				Table table = doExtractFrom(sheet);
				tablesbook.add(name, table);
			}

			return tablesbook;
		}

		private Table doExtractFrom(XSSFSheet sheet) {
			int rows = sheet.getPhysicalNumberOfRows();
			int cols = sheet.getRow(0).getPhysicalNumberOfCells();

			Table table = new Table(rows, cols);
			for (int i = 0; i < rows; i++) {
				XSSFRow row = sheet.getRow(i);

				for (int j = 0; j < cols; j++) {
					XSSFCell cell = row.getCell(j);

					String value = cell.getStringCellValue();
					table.set(i, j, value);
				}
			}

			return table;
		}

		@Override
		public XSSFWorkbook doPutInto(String identifier, Tablesbook tablesbook,
				XSSFWorkbook workbook) throws IOException {
			
			int sheets = workbook.getNumberOfSheets();
			for (int i = 0; i < sheets; i++) {
				XSSFSheet sheet = workbook.getSheetAt(i);
				String name = sheet.getSheetName();
				Table table = tablesbook.getTable(name);
				doPutInto(sheet, table);
			}

			return workbook;
		}

		private void doPutInto(XSSFSheet sheet, Table table) {
			int rows = sheet.getPhysicalNumberOfRows();
			int cols = sheet.getRow(0).getPhysicalNumberOfCells();

			for (int i = 0; i < rows; i++) {
				XSSFRow row = sheet.getRow(i);

				for (int j = 0; j < cols; j++) {
					XSSFCell cell = row.getCell(j);

					String value = table.get(i, j);
					cell.setCellValue(value);
				}
			}
		}

	}

}
