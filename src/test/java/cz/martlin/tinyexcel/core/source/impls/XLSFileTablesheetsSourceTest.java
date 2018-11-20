package cz.martlin.tinyexcel.core.source.impls;

import java.io.IOException;

import org.junit.Test;

import cz.martlin.tinyexcel.core.data.Tablesbook;

public class XLSFileTablesheetsSourceTest extends AbstractLocalSourceTest {


	@Test
	public void testLoad() throws IOException {
		XLSFileTablesheetsSource source = new XLSFileTablesheetsSource();
		String fileName = "2x3.xls";
		String identifier = fileName;
		Tablesbook expected = create2x3Tablesbook(identifier);
		testLoadFile(fileName, source, expected);
		
	}


}
