package cz.martlin.tinyexcel.core.data;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Tablesbook {
	private final Map<String, Table> tables;

	public Tablesbook() {
		super();
		this.tables = new LinkedHashMap<>();
	}

	public Map<String, Table> getTables() {
		return tables;
	}
	
	public void add(String name, Table table) {
		tables.put(name, table);
	}

	public Set<String> getTablesNames() {
		return new LinkedHashSet<>(tables.keySet());
	}

	public Table getTable(String name) {
		return tables.get(name);
	}
	
	//TODO data class
}
