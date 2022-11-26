package com.googlecode.cqengine.query.parser.common.valuetypes;

import com.googlecode.cqengine.query.parser.common.ValueParser;

public class CharacterParser extends ValueParser {
   public Character parse(Class valueType, String stringValue) {
      if (stringValue.length() != 1) {
         throw new IllegalArgumentException();
      } else {
         return stringValue.charAt(0);
      }
   }
}
