package cz.martlin.tinyexcel.core.source;

import java.io.IOException;

import cz.martlin.tinyexcel.core.data.Tablesbook;

/**
 * The base tool for extraction of tablesbooks from some external
 * representation, and pushing back the tablesbooks back to it.
 * 
 * @author jasekm1
 *
 * @param <TBT>
 */
public interface BaseTablesbookExtractor<TBT> {

	public Tablesbook extractFrom(String identifier, TBT external)
			throws IOException;

	public TBT putInto(String identifier, Tablesbook tablesbook, TBT external)
			throws IOException;

}
