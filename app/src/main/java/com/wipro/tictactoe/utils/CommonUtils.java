package com.wipro.tictactoe.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class CommonUtils {

	public static Integer findMin(Collection<Integer> list) {

		// check list is empty or not
		if (list == null || list.size() == 0) {
			return Integer.MAX_VALUE;
		}

		// create a new list to avoid modification
		// in the original list
		List<Integer> sortedlist = new ArrayList<>(list);

		// sort list in natural order
		Collections.sort(sortedlist);

		// first element in the sorted list
		// would be minimum
		return sortedlist.get(0);
	}

	public static <T, E> Set<T> getKeysByValue(Map<T, E> map, E value) {
	    return map.entrySet()
	              .stream()
	              .filter(entry -> Objects.equals(entry.getValue(), value))
	              .map(Map.Entry::getKey)
	              .collect(Collectors.toSet());
	}
}
