package com.bea.core.repackaged.springframework.format;

import java.text.ParseException;
import java.util.Locale;

@FunctionalInterface
public interface Parser {
   Object parse(String var1, Locale var2) throws ParseException;
}
