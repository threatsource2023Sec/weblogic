package org.python.icu.text;

import java.text.FieldPosition;
import org.python.icu.impl.SimpleFormatterImpl;
import org.python.icu.impl.StandardPlural;

class QuantityFormatter {
   private final SimpleFormatter[] templates;

   public QuantityFormatter() {
      this.templates = new SimpleFormatter[StandardPlural.COUNT];
   }

   public void addIfAbsent(CharSequence variant, String template) {
      int idx = StandardPlural.indexFromString(variant);
      if (this.templates[idx] == null) {
         this.templates[idx] = SimpleFormatter.compileMinMaxArguments(template, 0, 1);
      }
   }

   public boolean isValid() {
      return this.templates[StandardPlural.OTHER_INDEX] != null;
   }

   public String format(double number, NumberFormat numberFormat, PluralRules pluralRules) {
      String formatStr = numberFormat.format(number);
      StandardPlural p = selectPlural(number, numberFormat, pluralRules);
      SimpleFormatter formatter = this.templates[p.ordinal()];
      if (formatter == null) {
         formatter = this.templates[StandardPlural.OTHER_INDEX];

         assert formatter != null;
      }

      return formatter.format(formatStr);
   }

   public SimpleFormatter getByVariant(CharSequence variant) {
      assert this.isValid();

      int idx = StandardPlural.indexOrOtherIndexFromString(variant);
      SimpleFormatter template = this.templates[idx];
      return template == null && idx != StandardPlural.OTHER_INDEX ? this.templates[StandardPlural.OTHER_INDEX] : template;
   }

   public static StandardPlural selectPlural(double number, NumberFormat numberFormat, PluralRules rules) {
      String pluralKeyword;
      if (numberFormat instanceof DecimalFormat) {
         pluralKeyword = rules.select(((DecimalFormat)numberFormat).getFixedDecimal(number));
      } else {
         pluralKeyword = rules.select(number);
      }

      return StandardPlural.orOtherFromString(pluralKeyword);
   }

   public static StandardPlural selectPlural(Number number, NumberFormat fmt, PluralRules rules, StringBuffer formattedNumber, FieldPosition pos) {
      UFieldPosition fpos = new UFieldPosition(pos.getFieldAttribute(), pos.getField());
      fmt.format((Object)number, formattedNumber, fpos);
      PluralRules.FixedDecimal fd = new PluralRules.FixedDecimal(number.doubleValue(), fpos.getCountVisibleFractionDigits(), fpos.getFractionDigits());
      String pluralKeyword = rules.select(fd);
      pos.setBeginIndex(fpos.getBeginIndex());
      pos.setEndIndex(fpos.getEndIndex());
      return StandardPlural.orOtherFromString(pluralKeyword);
   }

   public static StringBuilder format(String compiledPattern, CharSequence value, StringBuilder appendTo, FieldPosition pos) {
      int[] offsets = new int[1];
      SimpleFormatterImpl.formatAndAppend(compiledPattern, appendTo, offsets, value);
      if (pos.getBeginIndex() != 0 || pos.getEndIndex() != 0) {
         if (offsets[0] >= 0) {
            pos.setBeginIndex(pos.getBeginIndex() + offsets[0]);
            pos.setEndIndex(pos.getEndIndex() + offsets[0]);
         } else {
            pos.setBeginIndex(0);
            pos.setEndIndex(0);
         }
      }

      return appendTo;
   }
}
