package cz.martlin.tinyexcel.core.misc;

import java.util.List;

public class Utils {
	@FunctionalInterface
	public static interface FunctionWithException<T> {
		public void run(T item) throws Exception;
	}

	public static <T> void forEach(List<T> items,
			FunctionWithException<T> function) throws Exception {

		for (T item : items) {
			function.run(item);
		}
	}

}
