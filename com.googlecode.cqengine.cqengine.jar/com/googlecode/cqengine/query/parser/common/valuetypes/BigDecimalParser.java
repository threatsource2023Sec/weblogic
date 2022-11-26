package com.googlecode.cqengine.query.parser.common.valuetypes;

import com.googlecode.cqengine.query.parser.common.ValueParser;
import java.math.BigDecimal;

public class BigDecimalParser extends ValueParser {
   public BigDecimal parse(Class valueType, String stringValue) {
      return new BigDecimal(stringValue);
   }
}
