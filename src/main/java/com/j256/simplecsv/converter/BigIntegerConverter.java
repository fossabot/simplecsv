package com.j256.simplecsv.converter;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.ParseException;

import com.j256.simplecsv.processor.FieldInfo;
import com.j256.simplecsv.processor.ParseError;

/**
 * Converter for the Java String type.
 * 
 * @author graywatson
 */
public class BigIntegerConverter implements Converter<BigInteger, DecimalFormat> {

	private static final BigIntegerConverter singleton = new BigIntegerConverter();

	/**
	 * Get singleton for class.
	 */
	public static BigIntegerConverter getSingleton() {
		return singleton;
	}

	@Override
	public DecimalFormat configure(String format, long flags, Field field) {
		if (format == null) {
			return null;
		} else {
			DecimalFormat decimalFormat = new DecimalFormat(format);
			decimalFormat.setParseBigDecimal(true);
			decimalFormat.setParseIntegerOnly(true);
			return decimalFormat;
		}
	}

	@Override
	public boolean isNeedsQuotes(DecimalFormat decimalFormat) {
		return true;
	}

	@Override
	public String javaToString(FieldInfo fieldInfo, BigInteger value) {
		DecimalFormat decimalFormat = (DecimalFormat) fieldInfo.getConfigInfo();
		if (value == null) {
			return null;
		} else if (decimalFormat == null) {
			return value.toString();
		} else {
			return decimalFormat.format(value);
		}
	}

	@Override
	public BigInteger stringToJava(String line, int lineNumber, FieldInfo fieldInfo, String value, ParseError parseError)
			throws ParseException {
		DecimalFormat decimalFormat = (DecimalFormat) fieldInfo.getConfigInfo();
		if (value.isEmpty()) {
			return null;
		} else if (decimalFormat == null) {
			return new BigInteger(value);
		} else {
			BigDecimal bigDecimal = (BigDecimal) decimalFormat.parse(value);
			return bigDecimal.toBigInteger();
		}
	}
}
