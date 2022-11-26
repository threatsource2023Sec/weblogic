package com.googlecode.cqengine.query.parser.common.valuetypes;

import com.googlecode.cqengine.query.parser.common.ValueParser;

public class DoubleParser extends ValueParser {
   public Double parse(Class valueType, String stringValue) {
      return Double.valueOf(stringValue);
   }
}
