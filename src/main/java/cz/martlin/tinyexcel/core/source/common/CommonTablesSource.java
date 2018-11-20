package cz.martlin.tinyexcel.core.source.common;

import java.io.IOException;

import cz.martlin.tinyexcel.core.data.Tablesbook;
import cz.martlin.tinyexcel.core.misc.TablesbookSourceException;
import cz.martlin.tinyexcel.core.source.AbstractSimpleLoaderSaver;
import cz.martlin.tinyexcel.core.source.BaseTablesbookExtractor;
import cz.martlin.tinyexcel.core.source.DefaultTablesSource;

/**
 * An common table source. Aall the requests delegates to provided
 * CommonSourceImpl instance.
 * 
 * @author jasekm1
 *
 * @param <TBT>
 */
public class CommonTablesSource<TBT> extends DefaultTablesSource {

	private final CommonSourceImpl<TBT> impl;

	public CommonTablesSource(CommonSourceImpl<TBT> impl) {
		super(new CommonTablesLoaderSaver<>(impl));

		this.impl = impl;
	}

	@Override
	public String getIdentifierPattern() {
		return impl.doGetIdentifierPattern();
	}

	/**
	 * Common loader-saver. All the methods are delegated to CommonSourceImpl.
	 * 
	 * @author jasekm1
	 *
	 * @param <TBT>
	 */
	public static class CommonTablesLoaderSaver<TBT> extends
			AbstractSimpleLoaderSaver<TBT> {

		private final CommonSourceImpl<TBT> impl;

		public CommonTablesLoaderSaver(CommonSourceImpl<TBT> impl) {
			super(new CommonTablesbookExtractor<>(impl));

			this.impl = impl;
		}

		@Override
		public TBT loadExternal(String identifier) throws IOException {
			return impl.doLoadExternal(identifier);
		}

		@Override
		public void saveExternal(String identifier, TBT external)
				throws IOException {
			impl.doSaveExternal(identifier, external);
		}

	}

	/**
	 * Common extractor. All the methods are delegated to CommonSourceImpl.
	 * 
	 * @author jasekm1
	 *
	 * @param <TBT>
	 */
	public static class CommonTablesbookExtractor<TBT> implements
			BaseTablesbookExtractor<TBT> {

		private final CommonSourceImpl<TBT> impl;

		public CommonTablesbookExtractor(CommonSourceImpl<TBT> impl) {
			super();

			this.impl = impl;
		}

		@Override
		public Tablesbook extractFrom(String identifier, TBT external) throws IOException {
			return impl.doExtractFrom(identifier, external);
		}

		@Override
		public TBT putInto(String identifier, Tablesbook tablesbook,
				TBT external) throws IOException {
			return impl.doPutInto(identifier, tablesbook, external);

		}

	}

}
