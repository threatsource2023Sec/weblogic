package org.stringtemplate.v4;

import java.util.Formatter;
import java.util.Locale;

public class NumberRenderer implements AttributeRenderer {
   public String toString(Object o, String formatString, Locale locale) {
      if (formatString == null) {
         return o.toString();
      } else {
         Formatter f = new Formatter(locale);
         f.format(formatString, o);
         return f.toString();
      }
   }
}
