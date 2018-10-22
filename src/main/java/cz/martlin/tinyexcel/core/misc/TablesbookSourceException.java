package cz.martlin.tinyexcel.core.misc;

import java.io.IOException;

public class TablesbookSourceException extends IOException {

	private static final long serialVersionUID = 8739102246269666894L;

	public TablesbookSourceException(String message, Exception e) {
		super(message, e);
	}

}
