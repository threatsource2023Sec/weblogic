package com.googlecode.cqengine.query.parser.cqn.support;

import com.googlecode.cqengine.query.parser.common.ValueParser;

public class StringParser extends ValueParser {
   public String parse(Class valueType, String stringValue) {
      return stripQuotes(stringValue);
   }

   public static String stripQuotes(String stringValue) {
      int length = stringValue.length();
      return length >= 2 && stringValue.charAt(0) == '"' && stringValue.charAt(length - 1) == '"' ? stringValue.substring(1, length - 1) : stringValue;
   }
}
