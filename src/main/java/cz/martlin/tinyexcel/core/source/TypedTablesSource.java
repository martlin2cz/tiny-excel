package cz.martlin.tinyexcel.core.source;

import cz.martlin.tinyexcel.core.data.Tablesbook;
import cz.martlin.tinyexcel.core.misc.TablesbookSourceException;

public abstract class TypedTablesSource<T> implements BaseTablesSource {

	public Tablesbook loadTablesbook(String identifier) throws TablesbookSourceException {
		try {
			T internal = loadInternal(identifier);
			return convertFromInternal(identifier, internal);
		} catch (Exception e) {
			throw new TablesbookSourceException("Cannot load tablesbook", e);
		}
	}

	public abstract Tablesbook convertFromInternal(String identifier, T internal) throws Exception;

	public abstract T loadInternal(String identifier) throws Exception;

	public void saveTablesbook(String identifier, Tablesbook tablesbook) throws TablesbookSourceException {
		try {
			T internal = convertToInternal(identifier, tablesbook);
			saveInternal(identifier, internal);
		} catch (Exception e) {
			throw new TablesbookSourceException("Cannot load tablesbook", e);
		}
	}

	public abstract T convertToInternal(String identifier, Tablesbook tablesbook) throws Exception;

	public abstract void saveInternal(String identifier, T internal) throws Exception;

}
