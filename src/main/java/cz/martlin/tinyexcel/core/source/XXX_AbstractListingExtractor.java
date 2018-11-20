package cz.martlin.tinyexcel.core.source;

import cz.martlin.tinyexcel.core.data.Table;
import cz.martlin.tinyexcel.core.data.Tablesbook;

public class XXX_AbstractListingExtractor<TBT, TT, TRT, TCT> implements BaseTablesbookExtractor<TBT> {

	public XXX_AbstractListingExtractor() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Tablesbook extractFrom(String identifier, TBT external) {
		Tablesbook tablesbook = new Tablesbook();
		
		for (TT tableExternal: listTables(external, identifier)) {
			int rows = -1, cols = -1;
			Table table = new Table(rows, cols);
			for (TRT rowExternal: listRows(tableExternal, external, identifier)) {
				for (TCT cellExternal: listCells(rowExternal, tableExternal, external, identifier)) {
					String cell = convertCell(cellExternal, rowExternal, tableExternal, external, identifier);
				}	
			}	
		}
		
		
		// TODO Auto-generated method stub
		return tablesbook;
	}

	private String convertCell(TCT cellExternal, TRT rowExternal,
			TT tableExternal, TBT external, String identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	private Iterable<TCT> listCells(TRT rowExternal, TT tableExternal, TBT external,
			String identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	private Iterable<TRT> listRows(TT tableExternal, TBT external, String identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	private Iterable<TT> listTables(TBT external, String identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TBT putInto(String identifier, Tablesbook tablesbook, TBT external) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
