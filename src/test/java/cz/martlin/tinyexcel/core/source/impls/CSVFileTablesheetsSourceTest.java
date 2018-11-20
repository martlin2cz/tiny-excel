package cz.martlin.tinyexcel.core.source.impls;

import java.io.IOException;

import org.apache.commons.csv.CSVFormat;
import org.junit.Test;

import cz.martlin.tinyexcel.core.data.Tablesbook;


public class CSVFileTablesheetsSourceTest extends AbstractLocalSourceTest {

	@Test
	public void testLoad() throws IOException {
		CSVFormat format = CSVFormat.DEFAULT.withDelimiter(',');
		CSVFileTablesheetsSource source = new CSVFileTablesheetsSource(format);
		String fileName = "2x3.csv";
		String identifier = fileName;
		Tablesbook expected = create2x3Tablesbook(identifier);
		testLoadFile(fileName, source, expected);
	}

}
