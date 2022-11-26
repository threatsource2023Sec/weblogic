package org.python.icu.text;

import java.io.ObjectStreamException;
import java.text.FieldPosition;
import java.text.ParsePosition;
import org.python.icu.util.CurrencyAmount;
import org.python.icu.util.Measure;
import org.python.icu.util.ULocale;

class CurrencyFormat extends MeasureFormat {
   static final long serialVersionUID = -931679363692504634L;
   private NumberFormat fmt;
   private final transient MeasureFormat mf;

   public CurrencyFormat(ULocale locale) {
      this.setLocale(locale, locale);
      this.mf = MeasureFormat.getInstance(locale, MeasureFormat.FormatWidth.WIDE);
      this.fmt = NumberFormat.getCurrencyInstance(locale.toLocale());
   }

   public Object clone() {
      CurrencyFormat result = (CurrencyFormat)super.clone();
      result.fmt = (NumberFormat)this.fmt.clone();
      return result;
   }

   public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
      if (!(obj instanceof CurrencyAmount)) {
         throw new IllegalArgumentException("Invalid type: " + obj.getClass().getName());
      } else {
         CurrencyAmount currency = (CurrencyAmount)obj;
         this.fmt.setCurrency(currency.getCurrency());
         return this.fmt.format((Object)currency.getNumber(), toAppendTo, pos);
      }
   }

   public CurrencyAmount parseObject(String source, ParsePosition pos) {
      return this.fmt.parseCurrency(source, pos);
   }

   public StringBuilder formatMeasures(StringBuilder appendTo, FieldPosition fieldPosition, Measure... measures) {
      return this.mf.formatMeasures(appendTo, fieldPosition, measures);
   }

   public MeasureFormat.FormatWidth getWidth() {
      return this.mf.getWidth();
   }

   public NumberFormat getNumberFormat() {
      return this.mf.getNumberFormat();
   }

   private Object writeReplace() throws ObjectStreamException {
      return this.mf.toCurrencyProxy();
   }

   private Object readResolve() throws ObjectStreamException {
      return new CurrencyFormat(this.fmt.getLocale(ULocale.ACTUAL_LOCALE));
   }
}
