package cz.martlin.tinyexcel.core.source;

import java.io.IOException;

import cz.martlin.tinyexcel.core.data.Tablesbook;

/**
 * The base (abstract) tool for loading and saving tablesbooks. Performs the
 * save and load by the particular way given by the source specification.
 * 
 * @author jasekm1
 *
 */
public interface BaseTablesbookLoaderSaver {

	/**
	 * Loads the tablesbook.
	 * 
	 * @param identifier
	 * @return
	 * @throws IOException
	 */
	public Tablesbook load(String identifier) throws IOException;

	/**
	 * Saves data into the tablesbook.
	 * 
	 * @param identifier
	 * @param tablesbook
	 * @throws IOException
	 */
	public void save(String identifier, Tablesbook tablesbook)
			throws IOException;

}
