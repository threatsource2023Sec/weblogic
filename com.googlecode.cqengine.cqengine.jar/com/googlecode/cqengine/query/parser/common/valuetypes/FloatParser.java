package com.googlecode.cqengine.query.parser.common.valuetypes;

import com.googlecode.cqengine.query.parser.common.ValueParser;

public class FloatParser extends ValueParser {
   public Float parse(Class valueType, String stringValue) {
      return Float.valueOf(stringValue);
   }
}
