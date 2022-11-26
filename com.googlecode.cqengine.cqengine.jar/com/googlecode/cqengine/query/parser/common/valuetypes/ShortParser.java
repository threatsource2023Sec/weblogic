package com.googlecode.cqengine.query.parser.common.valuetypes;

import com.googlecode.cqengine.query.parser.common.ValueParser;

public class ShortParser extends ValueParser {
   public Short parse(Class valueType, String stringValue) {
      return Short.valueOf(stringValue);
   }
}
