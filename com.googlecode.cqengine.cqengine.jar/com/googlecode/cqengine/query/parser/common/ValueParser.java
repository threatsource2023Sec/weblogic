package com.googlecode.cqengine.query.parser.common;

public abstract class ValueParser {
   public Object validatedParse(Class valueType, String stringValue) {
      try {
         return this.parse(valueType, stringValue);
      } catch (Exception var4) {
         throw new InvalidQueryException("Failed to parse as type " + valueType.getSimpleName() + ": " + stringValue, var4);
      }
   }

   protected abstract Object parse(Class var1, String var2);
}
