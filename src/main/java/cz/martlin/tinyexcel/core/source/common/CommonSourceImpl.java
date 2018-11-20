package cz.martlin.tinyexcel.core.source.common;

import java.io.IOException;

import cz.martlin.tinyexcel.core.data.Tablesbook;

/**
 * The common template for the tablesbooks sources. Provides all the ending
 * methods. Use CommonTablesSource to pack it all together and get complete
 * source.
 * 
 * @author jasekm1
 *
 * @param <TBT>
 *            type of the tablesbook in the native source.
 */
public interface CommonSourceImpl<TBT> {

	/**
	 * Returns pattern matching this soruce's identifier.
	 * 
	 * @return
	 */
	public abstract String doGetIdentifierPattern();

	/**
	 * Loads the external representation of the tablesbook with given id.
	 * 
	 * @param identifier
	 * @return
	 * @throws IOException
	 */
	public abstract TBT doLoadExternal(String identifier) throws IOException;

	/**
	 * Saves the given external representation.
	 * 
	 * @param identifier
	 * @param external
	 */
	public abstract void doSaveExternal(String identifier, TBT external)
			throws IOException;

	/**
	 * Extracts the tablesbook from the given external representation.
	 * 
	 * @param identifier
	 * @param external
	 * @return
	 */
	public abstract Tablesbook doExtractFrom(String identifier, TBT external)
			throws IOException;

	/**
	 * Puts given tablesbook into the given external representation.
	 * 
	 * @param identifier
	 * @param tablesbook
	 * @param external
	 * @return
	 */
	public abstract TBT doPutInto(String identifier, Tablesbook tablesbook,
			TBT external) throws IOException;

}