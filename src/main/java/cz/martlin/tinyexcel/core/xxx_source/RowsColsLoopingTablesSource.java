package cz.martlin.tinyexcel.core.xxx_source;

import cz.martlin.tinyexcel.core.data.Table;
import cz.martlin.tinyexcel.core.data.Tablesbook;
import cz.martlin.tinyexcel.core.misc.TablesbookSourceException;

public abstract class RowsColsLoopingTablesSource<TBT, TT, RT, CT> extends TypedTablesSource<TBT> {

	@Override
	public Tablesbook convertFromExternal(String identifier, TBT externalTablesbook) throws Exception {
		Tablesbook tablesbook = new Tablesbook();
		for (String tableName : listTablesNames(identifier, externalTablesbook)) {
			TT externalTable = loadTable(identifier, externalTablesbook, tableName);
			Table table = convertTableFromExternal(identifier, externalTable);

			tablesbook.add(tableName, table);
		}
		return tablesbook;
	}



	public abstract Iterable<String> listTablesNames(String identifier, TBT externalTablesbook) throws Exception;

	public abstract  TT loadTable(String identifier, TBT externalTablesbook, String tableName) throws Exception;
	
	private Table convertTableFromExternal(String identifier, TT externalTable) throws Exception {
		Table table = convertEmptyTableFromExternal(identifier, externalTable);
		int rowIndex = 0;

		for (RT externalRow : listRows(identifier, externalTable)) {
			int colIndex = 0;

			for (CT externalCell : listCells(identifier, externalTable, externalRow)) {
				String cellValue = convertCellFromExternal(identifier, externalCell, externalTable, externalRow);
				table.set(rowIndex, colIndex, cellValue);

				colIndex++;
			}
			rowIndex++;
		}

		return table;
	}

	private Table convertEmptyTableFromExternal(String identifier, TT externalTable) throws Exception {
		int rows = inferRowsNumberFromExternal(identifier, externalTable);
		int cols = inferColumnsNumberFromExternal(identifier, externalTable);

		return new Table(rows, cols);
	}

	public abstract int inferRowsNumberFromExternal(String identifier, TT externalTable) throws Exception;

	public abstract int inferColumnsNumberFromExternal(String identifier, TT externalTable) throws Exception;

	public abstract String convertCellFromExternal(String identifier, CT externalCell, TT externalTable, RT externalRow)
			throws Exception;

	public abstract Iterable<RT> listRows(String identifier, TT externalTable) throws Exception;

	public abstract Iterable<CT> listCells(String identifier, TT externalTable, RT externalRow) throws Exception;


	@Override
	public TBT convertToExternal(String identifier, Tablesbook tablesbook) throws Exception {
		TBT tablesbookExternal = obtainTablesbookExternal(identifier);
		for (String name : tablesbook.getTablesNames()) {
			Table table = tablesbook.getTable(name);

			TT tableExternal = convertTableToExternal(identifier, name, table);
			addTableToTablesbook(identifier, tableExternal);

		}
		return tablesbookExternal;
	}

	public abstract TBT obtainTablesbookExternal(String identifier) throws Exception;

	public abstract void addTableToTablesbook(String identifier, TT tableExternal) throws Exception;

	private TT convertTableToExternal(String identifier, String tableName, Table table) throws Exception {
		int rows = table.getRows();
		int cols = table.getCols();

		TT tableExternal = createTableExternal(identifier, tableName, rows, cols);
		for (int rowIndex = 0; rowIndex < rows; rowIndex++) {

			RT rowExternal = createRowExternal(identifier, tableExternal, rowIndex, cols);
			for (int colIndex = 0; colIndex < cols; colIndex++) {
				String cellValue = table.get(rowIndex, colIndex);

				CT cellExternal = createCellExternal(identifier, tableExternal, rowExternal, rowIndex, colIndex,
						cellValue);
				addCellToRow(identifier, tableExternal, rowExternal, rowIndex, colIndex, cellExternal);
			}
			addRowToTable(identifier, tableExternal, rowIndex, rowExternal);
		}
		return tableExternal;
	}

	public abstract TT createTableExternal(String identifier, String name, int rows, int cols) throws Exception;

	public abstract RT createRowExternal(String identifier, TT tableExternal, int rowIndex, int cols) throws Exception;

	public abstract CT createCellExternal(String identifier, TT tableExternal, RT rowExternal, int rowIndex,
			int colIndex, String cellValue) throws Exception;

	public abstract void addCellToRow(String identifier, TT tableExternal, RT rowExternal, int rowIndex, int colIndex,
			CT cellExternal) throws Exception;

	public abstract void addRowToTable(String identifier, TT tableExternal, int rowIndex, RT rowExternal)
			throws Exception;

}
