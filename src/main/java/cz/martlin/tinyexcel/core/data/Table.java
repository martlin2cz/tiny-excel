package cz.martlin.tinyexcel.core.data;

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
		//FIXME will crash when table empty
		return cells[0].length;
	}
	
	public String get(int row, int col) {
		return cells[row][col];
	}
	
	public void set(int row, int col, String value) {
		cells[row][col] = value;
	}
	
	//TODO data class
	
}
