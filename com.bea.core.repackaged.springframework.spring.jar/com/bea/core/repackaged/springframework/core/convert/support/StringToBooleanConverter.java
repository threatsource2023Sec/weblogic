package com.bea.core.repackaged.springframework.core.convert.support;

import com.bea.core.repackaged.springframework.core.convert.converter.Converter;
import java.util.HashSet;
import java.util.Set;

final class StringToBooleanConverter implements Converter {
   private static final Set trueValues = new HashSet(4);
   private static final Set falseValues = new HashSet(4);

   public Boolean convert(String source) {
      String value = source.trim();
      if (value.isEmpty()) {
         return null;
      } else {
         value = value.toLowerCase();
         if (trueValues.contains(value)) {
            return Boolean.TRUE;
         } else if (falseValues.contains(value)) {
            return Boolean.FALSE;
         } else {
            throw new IllegalArgumentException("Invalid boolean value '" + source + "'");
         }
      }
   }

   static {
      trueValues.add("true");
      trueValues.add("on");
      trueValues.add("yes");
      trueValues.add("1");
      falseValues.add("false");
      falseValues.add("off");
      falseValues.add("no");
      falseValues.add("0");
   }
}
