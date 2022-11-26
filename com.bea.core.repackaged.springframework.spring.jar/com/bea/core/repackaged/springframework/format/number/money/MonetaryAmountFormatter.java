package com.bea.core.repackaged.springframework.format.number.money;

import com.bea.core.repackaged.springframework.format.Formatter;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.Locale;
import javax.money.MonetaryAmount;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;

public class MonetaryAmountFormatter implements Formatter {
   @Nullable
   private String formatName;

   public MonetaryAmountFormatter() {
   }

   public MonetaryAmountFormatter(String formatName) {
      this.formatName = formatName;
   }

   public void setFormatName(String formatName) {
      this.formatName = formatName;
   }

   public String print(MonetaryAmount object, Locale locale) {
      return this.getMonetaryAmountFormat(locale).format(object);
   }

   public MonetaryAmount parse(String text, Locale locale) {
      return this.getMonetaryAmountFormat(locale).parse(text);
   }

   protected MonetaryAmountFormat getMonetaryAmountFormat(Locale locale) {
      return this.formatName != null ? MonetaryFormats.getAmountFormat(this.formatName, new String[0]) : MonetaryFormats.getAmountFormat(locale, new String[0]);
   }
}
