package cz.martlin.tinyexcel.core.source.impls;

import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeNoException;
import static org.junit.Assume.assumeNotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import cz.martlin.tinyexcel.core.data.Table;
import cz.martlin.tinyexcel.core.data.Tablesbook;
import cz.martlin.tinyexcel.core.misc.TablesbookSourceException;
import cz.martlin.tinyexcel.core.source.BaseTablesSource;

/**
 * An general superclass for all the local sources. Provides some usefull
 * utility methods, which could be used in the tests.
 * 
 * @author jasekm1
 *
 */
public class AbstractLocalSourceTest {
	/**
	 * Path (relative to the default package) pointing to the directory in the
	 * src/test/resources containing the testing files.
	 */
	private static final String TESTING_FILES_DIRECTORY = "/cz/martlin/tinyexcel/core/sources/";

	public AbstractLocalSourceTest() {
		super();
	}

	/**
	 * Copies testing file (file with given name located in
	 * {@value #TESTING_FILES_DIRECTORY}) to random temp directory. The absolute
	 * path to the created temp file is returned. If fails assumption is
	 * violated (and the exception is rethrown).
	 * 
	 * @param fileName
	 * @throws IOException
	 */
	protected String prepareTestingFile(String fileName) throws IOException {
		String path = TESTING_FILES_DIRECTORY + fileName;

		InputStream ins = this.getClass().getResourceAsStream(path);
		assumeNotNull("Testing file not found.", ins);

		File toFile;
		try {
			toFile = File.createTempFile("tiny-excel-test-file-", "-" + fileName);
		} catch (IOException e) {
			assumeNoException("Cannot create tmp file.", e);
			throw e;
		}

		Path toPath = toFile.toPath();

		try {
			Files.copy(ins, toPath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			assumeNoException("Cannot copy testing file.", e);
			throw e;
		}

		String toAbsolutePath = toFile.getAbsolutePath();
		System.out.println("Working with testing file: " + toAbsolutePath);

		return toAbsolutePath;
	}

	protected void testLoadFile(String fileName, BaseTablesSource source, Tablesbook expected)
			throws IOException, TablesbookSourceException {
				String file = prepareTestingFile(fileName);
			
				String identifier = file;
				Tablesbook actual = source.loadTablesbook(identifier);
			
				assertEquals(expected, actual);
				assertEquals(expected.toString(), actual.toString());
				
				System.out.println("EXP: " + expected.toString());
				System.out.println("ACT: " + actual.toString());
			}

	protected Tablesbook create2x3Tablesbook(final String identifier) {
		Tablesbook tablesbook = new Tablesbook();
		Table table = new Table(2, 3);
		table.set(0, 0, "foo");
		table.set(0, 1, "bar");
		table.set(0, 2, "baz");
	
		table.set(1, 0, "AUX");
		table.set(1, 1, "QUX");
		table.set(1, 2, "ZUX");
	
		tablesbook.add(identifier, table);
		return tablesbook;
	}

}