package com.googlecode.cqengine.query.parser.common.valuetypes;

import com.googlecode.cqengine.query.parser.common.ValueParser;

public class IntegerParser extends ValueParser {
   public Integer parse(Class valueType, String stringValue) {
      return Integer.valueOf(stringValue);
   }
}
