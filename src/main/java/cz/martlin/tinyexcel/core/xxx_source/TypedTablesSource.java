package cz.martlin.tinyexcel.core.xxx_source;

import cz.martlin.tinyexcel.core.data.Tablesbook;
import cz.martlin.tinyexcel.core.misc.TablesbookSourceException;
import cz.martlin.tinyexcel.core.source.BaseTablesSource;

public abstract class TypedTablesSource<T> implements BaseTablesSource {

	public Tablesbook loadTablesbook(String identifier) throws TablesbookSourceException {
		try {
			T external = loadExternal(identifier);
			return convertFromExternal(identifier, external);
		} catch (Exception e) {
			throw new TablesbookSourceException("Cannot load tablesbook", e);
		}
	}

	public abstract Tablesbook convertFromExternal(String identifier, T external) throws Exception;

	public abstract T loadExternal(String identifier) throws Exception;

	public void saveTablesbook(String identifier, Tablesbook tablesbook) throws TablesbookSourceException {
		try {
			T external = convertToExternal(identifier, tablesbook);
			saveExternal(identifier, external);
		} catch (Exception e) {
			throw new TablesbookSourceException("Cannot load tablesbook", e);
		}
	}

	public abstract T convertToExternal(String identifier, Tablesbook tablesbook) throws Exception;

	public abstract void saveExternal(String identifier, T external) throws Exception;

}
