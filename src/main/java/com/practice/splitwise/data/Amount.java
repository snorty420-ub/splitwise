package com.practice.splitwise.data;

import java.io.Serializable;
import java.util.Currency;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Amount implements Serializable {
	private double value;
	private Currency currency;

}