package cz.martlin.tinyexcel.core.source.impls;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import cz.martlin.tinyexcel.core.data.Table;
import cz.martlin.tinyexcel.core.data.Tablesbook;
import cz.martlin.tinyexcel.core.misc.Utils;
import cz.martlin.tinyexcel.core.source.common.CommonSourceImpl;
import cz.martlin.tinyexcel.core.source.common.CommonTablesSource;

public class CSVFileTablesheetsSource extends
		CommonTablesSource<List<List<String>>> {
	public CSVFileTablesheetsSource(CSVFormat format) {
		super(new CSVFileSourceImpl(format));
	}

	public static class CSVFileSourceImpl implements
			CommonSourceImpl<List<List<String>>> {

		private static final String EXTENSION = "csv";
		private static final String DOT = ".";
		private static final String PATTERN = "$(.+)\\.((csv)|(CSV))^"; //TODO TESME
		private final CSVFormat format;

		public CSVFileSourceImpl(CSVFormat format) {
			this.format = format;
		}

		@Override
		public String doGetIdentifierPattern() {
			return PATTERN;
		}
		@Override
		public List<List<String>> doLoadExternal(String identifier)
				throws IOException {
			File file = new File(identifier);

			try (Reader reader = new FileReader(file)) {
				try (CSVParser parser = new CSVParser(reader, format)) {
					List<CSVRecord> records = parser.getRecords();
					return records.stream() //
							.map((r) -> recordToList(r))
							.collect(Collectors.toList());
				} catch (IOException e) {
					throw e;
				}
			} catch (IOException e) {
				throw e;
			}

		}


		@Override
		public void doSaveExternal(String identifier,
				List<List<String>> external) throws IOException {
			File file = new File(identifier);

			try (Writer writer = new FileWriter(file)) {
				try (CSVPrinter printer = new CSVPrinter(writer, format)) {
					Utils.forEach(external, (e) -> printer.printRecord(e));
				} catch (Exception e) {
					throw new IOException(e);
				}
			} catch (IOException e) {
				throw e;
			}

		}

		@Override
		public Tablesbook doExtractFrom(String identifier,
				List<List<String>> records) {
			Tablesbook tablesbook = new Tablesbook();
			int rows = records.size();
			int cols = records.get(0).size();
			Table table = new Table(rows, cols);

			for (int row = 0; row < rows; row++) {
				List<String> record = records.get(row);
				for (int col = 0; col < cols; col++) {
					String value = record.get(col);
					table.set(row, col, value);
				}
			}

			tablesbook.add(identifier, table);
			return tablesbook;
		}

		@Override
		public List<List<String>> doPutInto(String identifier,
				Tablesbook tablesbook, List<List<String>> oldRecords) {
			Table table = tablesbook.getTable(identifier);

			int rows = table.getRows();
			int cols = table.getCols();
			List<List<String>> records = new ArrayList<>(rows);
			for (int row = 0; row < rows; row++) {
				List<String> rowRecords = new ArrayList<>(cols);
				for (int col = 0; col < cols; col++) {
					String value = table.get(row, col);
					rowRecords.add(value);
				}
				records.add(rowRecords);
			}
	
			return records;
		}


		private static List<String> recordToList(CSVRecord record) {
			int count = record.size();
			List<String> list = new ArrayList<>(count);
			for (int i = 0; i < count; i++) {
				String item = record.get(i);
				list.add(item);
			}
			
			return list;
		}
		
	}

}
