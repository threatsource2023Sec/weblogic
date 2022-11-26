package com.googlecode.cqengine.query.parser.common.valuetypes;

import com.googlecode.cqengine.query.parser.common.ValueParser;

public class LongParser extends ValueParser {
   public Long parse(Class valueType, String stringValue) {
      return Long.valueOf(stringValue);
   }
}
