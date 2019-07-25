/**
 * 
 */
package com.handson.junit;

import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;

/**
 * @author sveera
 *
 */
public class IntegerArrayConverter extends SimpleArgumentConverter {

	@Override
	protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
		if (source != null && !(source instanceof String && Integer[].class.isAssignableFrom(targetType)))
			throw new ArgumentConversionException(
					"Conversion from " + source.getClass() + " to " + targetType + " not supported.");
		return source != null ? convertToIntegerArray(((String) source).split("\\s*,\\s*")) : new Integer[0];
	}

	private Integer[] convertToIntegerArray(String[] split) {
		Integer[] integers = new Integer[split.length];
		for (int i = 0; i < split.length; i++)
			integers[i] = Integer.parseInt(split[i]);
		return integers;
	}

}
