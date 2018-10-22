package cz.martlin.tinyexcel.core.source;

import cz.martlin.tinyexcel.core.data.Tablesbook;
import cz.martlin.tinyexcel.core.misc.TablesbookSourceException;

public interface BaseTablesSource {
	public Tablesbook loadTablesbook(String identifier) throws TablesbookSourceException;

	public void saveTablesbook(String identifier, Tablesbook tablesbook) throws TablesbookSourceException;
}
