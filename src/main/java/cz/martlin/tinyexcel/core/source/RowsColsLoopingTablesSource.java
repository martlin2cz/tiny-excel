package cz.martlin.tinyexcel.core.source;

import cz.martlin.tinyexcel.core.data.Table;
import cz.martlin.tinyexcel.core.data.Tablesbook;
import cz.martlin.tinyexcel.core.misc.TablesbookSourceException;

public abstract class RowsColsLoopingTablesSource<TBT, TT, RT, CT> extends TypedTablesSource<TBT> {

	@Override
	public Tablesbook convertFromInternal(String identifier, TBT internalTablesbook) throws Exception {
		Tablesbook tablesbook = new Tablesbook();
		for (String tableName : listTablesNames(identifier, internalTablesbook)) {
			TT internalTable = loadTable(identifier, internalTablesbook, tableName);
			Table table = convertTableFromInternal(identifier, internalTable);

			tablesbook.add(tableName, table);
		}
		return tablesbook;
	}



	public abstract Iterable<String> listTablesNames(String identifier, TBT internalTablesbook) throws Exception;

	public abstract  TT loadTable(String identifier, TBT internalTablesbook, String tableName) throws Exception;
	
	private Table convertTableFromInternal(String identifier, TT internalTable) throws Exception {
		Table table = convertEmptyTableFromInternal(identifier, internalTable);
		int rowIndex = 0;

		for (RT internalRow : listRows(identifier, internalTable)) {
			int colIndex = 0;

			for (CT internalCell : listCells(identifier, internalTable, internalRow)) {
				String cellValue = convertCellFromInternal(identifier, internalCell, internalTable, internalRow);
				table.set(rowIndex, colIndex, cellValue);

				colIndex++;
			}
			rowIndex++;
		}

		return table;
	}

	private Table convertEmptyTableFromInternal(String identifier, TT internalTable) throws Exception {
		int rows = inferRowsNumberFromInternal(identifier, internalTable);
		int cols = inferColumnsNumberFromInternal(identifier, internalTable);

		return new Table(rows, cols);
	}

	public abstract int inferRowsNumberFromInternal(String identifier, TT internalTable) throws Exception;

	public abstract int inferColumnsNumberFromInternal(String identifier, TT internalTable) throws Exception;

	public abstract String convertCellFromInternal(String identifier, CT internalCell, TT internalTable, RT internalRow)
			throws Exception;

	public abstract Iterable<RT> listRows(String identifier, TT internalTable) throws Exception;

	public abstract Iterable<CT> listCells(String identifier, TT internalTable, RT internalRow) throws Exception;


	@Override
	public TBT convertToInternal(String identifier, Tablesbook tablesbook) throws Exception {
		TBT tablesbookInternal = obtainTablesbookInternal(identifier);
		for (String name : tablesbook.getTablesNames()) {
			Table table = tablesbook.getTable(name);

			TT tableInternal = convertTableToInternal(identifier, name, table);
			addTableToTablesbook(identifier, tableInternal);

		}
		return tablesbookInternal;
	}

	public abstract TBT obtainTablesbookInternal(String identifier) throws Exception;

	public abstract void addTableToTablesbook(String identifier, TT tableInternal) throws Exception;

	private TT convertTableToInternal(String identifier, String tableName, Table table) throws Exception {
		int rows = table.getRows();
		int cols = table.getCols();

		TT tableInternal = createTableInternal(identifier, tableName, rows, cols);
		for (int rowIndex = 0; rowIndex < rows; rowIndex++) {

			RT rowInternal = createRowInternal(identifier, tableInternal, rowIndex, cols);
			for (int colIndex = 0; colIndex < cols; colIndex++) {
				String cellValue = table.get(rowIndex, colIndex);

				CT cellInternal = createCellInternal(identifier, tableInternal, rowInternal, rowIndex, colIndex,
						cellValue);
				addCellToRow(identifier, tableInternal, rowInternal, rowIndex, colIndex, cellInternal);
			}
			addRowToTable(identifier, tableInternal, rowIndex, rowInternal);
		}
		return tableInternal;
	}

	public abstract TT createTableInternal(String identifier, String name, int rows, int cols) throws Exception;

	public abstract RT createRowInternal(String identifier, TT tableInternal, int rowIndex, int cols) throws Exception;

	public abstract CT createCellInternal(String identifier, TT tableInternal, RT rowInternal, int rowIndex,
			int colIndex, String cellValue) throws Exception;

	public abstract void addCellToRow(String identifier, TT tableInternal, RT rowInternal, int rowIndex, int colIndex,
			CT cellInternal) throws Exception;

	public abstract void addRowToTable(String identifier, TT tableInternal, int rowIndex, RT rowInternal)
			throws Exception;

}
