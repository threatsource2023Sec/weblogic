package com.googlecode.cqengine.query.parser.common.valuetypes;

import com.googlecode.cqengine.query.parser.common.ValueParser;

public class ByteParser extends ValueParser {
   public Byte parse(Class valueType, String stringValue) {
      return Byte.valueOf(stringValue);
   }
}
