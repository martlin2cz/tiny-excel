package cz.martlin.tinyexcel.core.source;

import java.io.IOException;

import cz.martlin.tinyexcel.core.data.Tablesbook;
import cz.martlin.tinyexcel.core.misc.TablesbookSourceException;

/**
 * Simple, but still abstract, implementation of loader-saver. Works with
 * so-called "external representation", a data structure simply (and nativly)
 * supported by the source. By BaseTablesbookextracter it converts it to the
 * tiny-excel's Tablesbook.
 * 
 * @author jasekm1
 *
 * @param <TBT>
 *            the external type of the tablesbook
 */
public abstract class AbstractSimpleLoaderSaver<TBT> implements
		BaseTablesbookLoaderSaver {

	private final BaseTablesbookExtractor<TBT> extracter;

	public AbstractSimpleLoaderSaver(BaseTablesbookExtractor<TBT> extracter) {
		super();
		this.extracter = extracter;
	}

	public Tablesbook load(String identifier) throws IOException {
		try {
			TBT external = loadExternal(identifier);
			return extractTablesbookFromExternal(identifier, external);
		} catch (Exception e) {
			throw new TablesbookSourceException("Cannot load tablesbook", e);
		}
	}

	public abstract TBT loadExternal(String identifier) throws IOException;

	private Tablesbook extractTablesbookFromExternal(String identifier,
			TBT external) throws IOException {
		return extracter.extractFrom(identifier, external);
	}

	public void save(String identifier, Tablesbook tablesbook)
			throws IOException {
		try {
			TBT oldExternal = loadExternal(identifier);
			TBT newExternal = putTablesbookIntoExternal(identifier, tablesbook, oldExternal);
			saveExternal(identifier, newExternal);
		} catch (Exception e) {
			throw new TablesbookSourceException("Cannot load tablesbook", e);
		}
	}

	public abstract void saveExternal(String identifier, TBT external)
			throws IOException;

	public TBT putTablesbookIntoExternal(String identifier,
			Tablesbook tablesbook, TBT external) throws IOException {
		return extracter.putInto(identifier, tablesbook, external);
	}

}
