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

	// /////////////////////////////////////////////////////////////////////////////////////

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tables == null) ? 0 : tables.hashCode());
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
		Tablesbook other = (Tablesbook) obj;
		if (tables == null) {
			if (other.tables != null)
				return false;
		} else if (!tables.equals(other.tables))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Tablesbook" + tables;
	}

}
