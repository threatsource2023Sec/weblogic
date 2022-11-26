package org.hibernate.validator.internal.engine.messageinterpolation;

import java.util.Formatter;
import java.util.Locale;

public class FormatterWrapper {
   private final Formatter formatter;

   public FormatterWrapper(Locale locale) {
      this.formatter = new Formatter(locale);
   }

   public String format(String format, Object... args) {
      return this.formatter.format(format, args).toString();
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("FormatterWrapper");
      sb.append("{}");
      return sb.toString();
   }
}
