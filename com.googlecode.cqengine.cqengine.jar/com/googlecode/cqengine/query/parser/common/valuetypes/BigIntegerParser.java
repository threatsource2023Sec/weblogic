package com.googlecode.cqengine.query.parser.common.valuetypes;

import com.googlecode.cqengine.query.parser.common.ValueParser;
import java.math.BigInteger;

public class BigIntegerParser extends ValueParser {
   public BigInteger parse(Class valueType, String stringValue) {
      return new BigInteger(stringValue);
   }
}
