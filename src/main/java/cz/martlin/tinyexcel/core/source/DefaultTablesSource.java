package cz.martlin.tinyexcel.core.source;

import java.io.IOException;

import cz.martlin.tinyexcel.core.data.Tablesbook;
import cz.martlin.tinyexcel.core.misc.TablesbookSourceException;

/**
 * The default implementation of source. Handles all the technicalities and the
 * main part - loading and saving of tableshbooks - delegates to the
 * BaseTablesbookLoaderSaver.
 */
public abstract class DefaultTablesSource implements BaseTablesSource {

	private final BaseTablesbookLoaderSaver loaderSaver;

	public DefaultTablesSource(BaseTablesbookLoaderSaver loaderSaver) {
		super();
		this.loaderSaver = loaderSaver;
	}

	@Override
	public boolean supports(String identifier) throws TablesbookSourceException {
		String identifierPattern = getIdentifierPattern();
		return identifier.matches(identifierPattern);
	}

	/**
	 * Returns regex pattern matching the identifier accepted by this source.
	 * 
	 * @return
	 */
	public abstract String getIdentifierPattern();

	@Override
	public Tablesbook loadTablesbook(String identifier)
			throws TablesbookSourceException {
		try {
			return loaderSaver.load(identifier);
		} catch (IOException e) {
			throw new TablesbookSourceException("Could not load tablesbook", e);
		}
	}

	@Override
	public void saveTablesbook(String identifier, Tablesbook tablesbook)
			throws TablesbookSourceException {
		try {
			loaderSaver.save(identifier, tablesbook);
		} catch (IOException e) {
			throw new TablesbookSourceException("Could not save tablesbook", e);
		}
	}

}
