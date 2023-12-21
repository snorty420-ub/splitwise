package com.practice.splitwise.utilities;

import java.util.ArrayList;
import java.util.List;

public class Utilities {
	public static <T> List<T> IterableToList(Iterable<T> iterable){
		List<T> list = new ArrayList<>();
		iterable.forEach(list::add);
		return list;
	}

	public static void printError(String s) {
		System.err.println("[ERROR]: " + s);
	}

}
