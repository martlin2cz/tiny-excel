package cz.martlin.tinyexcel.core.source.impls;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.ValueRange;

import cz.martlin.tinyexcel.core.data.Table;
import cz.martlin.tinyexcel.core.data.Tablesbook;
import cz.martlin.tinyexcel.core.source.common.CommonSourceImpl;
import cz.martlin.tinyexcel.core.source.common.CommonTablesSource;
import cz.martlin.tinyexcel.core.sources.GSheetsAPI;

public class GSgeetsTablesheetsSource extends CommonTablesSource<Spreadsheet> {

	public GSgeetsTablesheetsSource(CommonSourceImpl<Spreadsheet> impl) {
		super(new GSpreadsheetsTablesheetsSourceImpl());
	}

	public static class GSpreadsheetsTablesheetsSourceImpl implements
			CommonSourceImpl<Spreadsheet> {

		private final GSheetsAPI api;

		public GSpreadsheetsTablesheetsSourceImpl() {
			this.api = new GSheetsAPI();
		}

		@Override
		public String doGetIdentifierPattern() {
			return "([a-zA-Z0-9])+";
		}

		@Override
		public Spreadsheet doLoadExternal(String identifier) throws IOException {
			String spreadsheetId = identifier;
			Sheets service = api.obtainService(spreadsheetId);

			Spreadsheet spreadsheet = service.spreadsheets().get(identifier)
					.execute();
			return spreadsheet;
		}

		@Override
		public void doSaveExternal(String identifier, Spreadsheet spreadsheet)
				throws IOException {
			// TODO okay?
		}

		@Override
		public Tablesbook doExtractFrom(String identifier,
				Spreadsheet spreadsheet) throws IOException {

			Tablesbook tablesbook = new Tablesbook();

			for (Sheet sheet : spreadsheet.getSheets()) {
				String name = sheet.getProperties().getTitle();
				Table table = doExtractFrom(identifier, spreadsheet, name);
				tablesbook.add(name, table);
			}

			return tablesbook;
		}

		private Table doExtractFrom(String spreadsheetId,
				Spreadsheet spreadsheet, String sheetName) throws IOException {
			Sheets service = api.obtainService(spreadsheetId);

			String range = sheetName;
			ValueRange response = service.spreadsheets().values()
					.get(spreadsheetId, range).execute();

			Table table = doExtractFrom(response);
			return table;
		}

		private Table doExtractFrom(ValueRange response) {
			List<List<Object>> values = response.getValues();
			
			int rows = values.size();
			int cols = values.get(0).size();
			
			Table table = new Table(rows, cols);
			
			for (int i = 0; i < rows; i++) {
				List<Object> row = values.get(i);
				for (int j = 0; j < cols; j++) {
					Object cell = row.get(j);
					String value = String.valueOf(cell);
					table.set(i, j, value);
				}	
			}
			
			return table;
		}

		@Override
		public Spreadsheet doPutInto(String identifier, Tablesbook tablesbook,
				Spreadsheet spreadsheet) throws IOException {
			

			for (Sheet sheet : spreadsheet.getSheets()) {
				String name = sheet.getProperties().getTitle();
				Table table = tablesbook.getTable(name);
				doPutInto(identifier, table, name);
			}

			return spreadsheet;
		}

		private void doPutInto(String spreadsheetId, Table table, String sheetName) throws IOException {
			int rows = table.getRows();
			int cols = table.getCols();
			
			List<List<Object>> values = new ArrayList<>(rows);
			for (int i = 0; i < rows; i++) {
				List<Object> cells = new ArrayList<>(cols);
				for (int j = 0; j < cols; j++) {
					String value = table.get(i, j);
					cells.add(value);
				}	
				values.add(cells);
			}
			ValueRange range = new ValueRange();
			range.setValues(values);

			Sheets service = api.obtainService(spreadsheetId);

			String rangeStr = sheetName;
			service.spreadsheets().values()
					.get(spreadsheetId, rangeStr).execute();
			//TODO push range into service

		}

	}

}
