package cz.martlin.tinyexcel.core.source;

import java.util.ArrayList;
import java.util.List;

public abstract class ListOfListOfStringsSource<TBT, TT> extends RowsColsLoopingTablesSource<TBT, TT, List<String>, String> {



	@Override
	public String convertCellFromInternal(String identifier, String internalCell, TT internalTable,
			List<String> internalRow) {
		
		return internalCell;
	}

	@Override
	public Iterable<String> listCells(String identifier, TT internalTable, List<String> internalRow) {
		return internalRow;
	}

	



	@Override
	public List<String> createRowInternal(String identifier, TT tableInternal, int rowIndex, int cols) {
		return new ArrayList<>(cols);
	}

	@Override
	public String createCellInternal(String identifier, TT tableInternal, List<String> rowInternal, int rowIndex,
			int colIndex, String cellValue) {
		return cellValue;
	}



	@Override
	public void addCellToRow(String identifier, TT tableInternal, List<String> rowInternal, int rowIndex, int colIndex,
			String cellInternal) {
		
		rowInternal.add(cellInternal);
	}






}
