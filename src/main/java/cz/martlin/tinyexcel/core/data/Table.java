package cz.martlin.tinyexcel.core.data;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Table {
	private final String[][] cells;

	public Table(int rows, int cols) {
		super();
		this.cells = new String[rows][cols];
	}

	public String[][] getCells() {
		return cells;
	}

	public int getRows() {
		return cells.length;
	}

	public int getCols() {
		// FIXME will crash when table empty
		return cells[0].length;
	}

	public String get(int row, int col) {
		return cells[row][col];
	}

	public void set(int row, int col, String value) {
		cells[row][col] = value;
	}

	// /////////////////////////////////////////////////////////////////////////////////////
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(cells);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Table other = (Table) obj;
		if (!Arrays.deepEquals(cells, other.cells))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Table" + Arrays.stream(cells) //
				.map((r) -> Arrays.toString(r)) //
				.collect(Collectors.joining(";"));
	}

}
