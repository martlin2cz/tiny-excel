package cz.martlin.tinyexcel.core.sources;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.ValueRange;

import cz.martlin.tinyexcel.core.misc.TablesbookSourceException;
import cz.martlin.tinyexcel.core.source.ListOfListOfStringsSource;

public class GoogleSheetsSource extends ListOfListOfStringsSource<Spreadsheet, ValueRange> {
	private final  Sheets service = null; //TODO here
	
	@Override
	public Iterable<String> listTablesNames(String identifier, Spreadsheet spreadsheet)  throws IOException {
		
		
		 List<Sheet> sheets = spreadsheet.getSheets();
		 return sheets.stream()//
				 .map((s) -> s.getProperties().getTitle())//
				 .collect(Collectors.toList());
		 
	}

	@Override
	public ValueRange loadTable(String identifier, Spreadsheet spreadsheet, String sheetName) throws IOException {
		return getRangeOfSheet(identifier, sheetName);
	}

	
	@Override
	public int inferRowsNumberFromInternal(String identifier, ValueRange sheetRange) throws IOException {
		return sheetRange.getValues().size();
	}

	@Override
	public int inferColumnsNumberFromInternal(String identifier, ValueRange sheetRange) throws IOException {
		//FIXME will crash when empty (handle it)
		return sheetRange.getValues().get(0).size(); 
	}

	@Override
	public Iterable<List<String>> listRows(String identifier, ValueRange sheetRange) {
		// FIXME getValues may contain String - or even Double or Boolean 
		return (List<List<String>>) (Object) sheetRange.getValues();	 
	}


	@Override
	public Spreadsheet obtainTablesbookInternal(String identifier) throws IOException {
		Spreadsheet spreadsheet = service.spreadsheets().get(identifier).execute();
		return spreadsheet;
	}

	@Override
	public void addTableToTablesbook(String identifier, ValueRange tableInternal) {
		// ok ignore
	}

	@Override
	public ValueRange createTableInternal(String identifier, String sheetName, int rows, int cols) throws IOException {
		return getRangeOfSheet(identifier, sheetName);
	}

	private ValueRange getRangeOfSheet(String identifier, String sheetName) throws IOException {
		ValueRange range = service.spreadsheets().values().get(identifier, sheetName).execute();
		return range;
	}

	@Override
	public void addRowToTable(String identifier, ValueRange tableInternal, int rowIndex, List<String> rowInternal) {
		// ok ignore
		
	}

	@Override
	public Spreadsheet loadInternal(String identifier) throws IOException {
		 Spreadsheet spreadsheet = service.spreadsheets().get(identifier).execute();
		return spreadsheet;
	}

	@Override
	public void saveInternal(String identifier, Spreadsheet internal) throws Exception {
		// TODO Auto-generated method stub
		
	}

	


}
