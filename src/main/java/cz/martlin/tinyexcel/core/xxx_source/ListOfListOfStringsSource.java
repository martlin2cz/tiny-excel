package cz.martlin.tinyexcel.core.xxx_source;

import java.util.ArrayList;
import java.util.List;

public abstract class ListOfListOfStringsSource<TBT, TT> extends RowsColsLoopingTablesSource<TBT, TT, List<String>, String> {



	@Override
	public String convertCellFromExternal(String identifier, String externalCell, TT externalTable,
			List<String> externalRow) {
		
		return externalCell;
	}

	@Override
	public Iterable<String> listCells(String identifier, TT externalTable, List<String> externalRow) {
		return externalRow;
	}

	



	@Override
	public List<String> createRowExternal(String identifier, TT tableExternal, int rowIndex, int cols) {
		return new ArrayList<>(cols);
	}

	@Override
	public String createCellExternal(String identifier, TT tableExternal, List<String> rowExternal, int rowIndex,
			int colIndex, String cellValue) {
		return cellValue;
	}



	@Override
	public void addCellToRow(String identifier, TT tableExternal, List<String> rowExternal, int rowIndex, int colIndex,
			String cellExternal) {
		
		rowExternal.add(cellExternal);
	}






}
