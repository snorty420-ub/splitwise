package com.practice.splitwise.utilities;

import com.practice.splitwise.data.Amount;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class Utilities {
	public static <T> List<T> IterableToList(Iterable<T> iterable){
		List<T> list = new ArrayList<>();
		iterable.forEach(list::add);
		return list;
	}

	public static String mapAmountToString(double value, Currency currency) {
		return currency.toString() + " " + value;
	}

	public static Amount mapStringToAmount(String currencyString) {
		Currency currency = Currency.getInstance(currencyString.split(" ")[0]);
		return Amount.builder().currency(currency).value(Double.parseDouble(currencyString.split(" ")[1])).build();
	}
	public static double getPortionInDouble(double value) {
		double scale = Math.pow(10, 3);
		return Math.round(value * scale) / scale;
	}

	public static void printError(String s) {
		System.err.println("[ERROR]: " + s);
	}

}
