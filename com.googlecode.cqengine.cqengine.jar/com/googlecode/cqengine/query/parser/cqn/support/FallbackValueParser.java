package com.googlecode.cqengine.query.parser.cqn.support;

import com.googlecode.cqengine.query.parser.common.ValueParser;
import java.lang.reflect.Method;

public class FallbackValueParser extends ValueParser {
   final StringParser stringParser;

   public FallbackValueParser(StringParser stringParser) {
      this.stringParser = stringParser;
   }

   protected Object parse(Class valueType, String stringValue) {
      try {
         stringValue = this.stringParser.parse(String.class, stringValue);
         Method valueOf = valueType.getMethod("valueOf", String.class);
         return valueType.cast(valueOf.invoke((Object)null, stringValue));
      } catch (Exception var4) {
         throw new IllegalStateException("Failed to parse value using a valueOf() method in class '" + valueType.getName() + "': " + stringValue, var4);
      }
   }
}
