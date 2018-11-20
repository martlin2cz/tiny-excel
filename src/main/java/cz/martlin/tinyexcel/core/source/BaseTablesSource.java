package cz.martlin.tinyexcel.core.source;

import cz.martlin.tinyexcel.core.data.Tablesbook;
import cz.martlin.tinyexcel.core.misc.TablesbookSourceException;

/**
 * The main interface of the app. The general source of tablesbooks. The
 * identifier is here meant something (some string) which uniquelly identifies
 * particullar tablesbook. Identifier might be file name, or url or even some
 * UUID with some specified format. It is important that each source
 * implementation shall recognise its own format of the identifier. The
 * identifier itself is key to the proper source.
 * 
 * @author jasekm1
 *
 */
public interface BaseTablesSource {

	/**
	 * Checks whether this source supports given identifier.
	 * 
	 * @param identifier
	 * @return
	 * @throws TablesbookSourceException
	 */
	public boolean supports(String identifier) throws TablesbookSourceException;

	/**
	 * Loads contents of the tablesbook by given identifier.
	 * 
	 * @param identifier
	 * @return
	 * @throws TablesbookSourceException
	 */
	public Tablesbook loadTablesbook(String identifier)
			throws TablesbookSourceException;

	/**
	 * Saves the given tablesbook data into tablesbook with given identifier.
	 * 
	 * @param identifier
	 * @param tablesbook
	 * @throws TablesbookSourceException
	 */
	public void saveTablesbook(String identifier, Tablesbook tablesbook)
			throws TablesbookSourceException;
}
