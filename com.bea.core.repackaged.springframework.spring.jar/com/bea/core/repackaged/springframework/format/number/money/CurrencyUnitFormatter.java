package com.bea.core.repackaged.springframework.format.number.money;

import com.bea.core.repackaged.springframework.format.Formatter;
import java.util.Locale;
import javax.money.CurrencyUnit;
import javax.money.Monetary;

public class CurrencyUnitFormatter implements Formatter {
   public String print(CurrencyUnit object, Locale locale) {
      return object.getCurrencyCode();
   }

   public CurrencyUnit parse(String text, Locale locale) {
      return Monetary.getCurrency(text, new String[0]);
   }
}
