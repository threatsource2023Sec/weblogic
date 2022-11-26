package com.googlecode.cqengine.query.parser.common.valuetypes;

import com.googlecode.cqengine.query.parser.common.ValueParser;

public class BooleanParser extends ValueParser {
   static final String TRUE_STR;
   static final String FALSE_STR;

   public Boolean parse(Class valueType, String stringValue) {
      if (TRUE_STR.equalsIgnoreCase(stringValue)) {
         return true;
      } else if (FALSE_STR.equalsIgnoreCase(stringValue)) {
         return false;
      } else {
         throw new IllegalStateException("Could not parse value as boolean: " + stringValue);
      }
   }

   static {
      TRUE_STR = Boolean.TRUE.toString();
      FALSE_STR = Boolean.FALSE.toString();
   }
}
