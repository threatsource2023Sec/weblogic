package com.bea.core.repackaged.springframework.format.number.money;

import com.bea.core.repackaged.springframework.context.support.EmbeddedValueResolutionSupport;
import com.bea.core.repackaged.springframework.format.AnnotationFormatterFactory;
import com.bea.core.repackaged.springframework.format.Formatter;
import com.bea.core.repackaged.springframework.format.Parser;
import com.bea.core.repackaged.springframework.format.Printer;
import com.bea.core.repackaged.springframework.format.annotation.NumberFormat;
import com.bea.core.repackaged.springframework.format.number.CurrencyStyleFormatter;
import com.bea.core.repackaged.springframework.format.number.NumberStyleFormatter;
import com.bea.core.repackaged.springframework.format.number.PercentStyleFormatter;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.text.ParseException;
import java.util.Collections;
import java.util.Currency;
import java.util.Locale;
import java.util.Set;
import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;

public class Jsr354NumberFormatAnnotationFormatterFactory extends EmbeddedValueResolutionSupport implements AnnotationFormatterFactory {
   private static final String CURRENCY_CODE_PATTERN = "¤¤";

   public Set getFieldTypes() {
      return Collections.singleton(MonetaryAmount.class);
   }

   public Printer getPrinter(NumberFormat annotation, Class fieldType) {
      return this.configureFormatterFrom(annotation);
   }

   public Parser getParser(NumberFormat annotation, Class fieldType) {
      return this.configureFormatterFrom(annotation);
   }

   private Formatter configureFormatterFrom(NumberFormat annotation) {
      String pattern = this.resolveEmbeddedValue(annotation.pattern());
      if (StringUtils.hasLength(pattern)) {
         return new PatternDecoratingFormatter(pattern);
      } else {
         NumberFormat.Style style = annotation.style();
         if (style == NumberFormat.Style.NUMBER) {
            return new NumberDecoratingFormatter(new NumberStyleFormatter());
         } else {
            return style == NumberFormat.Style.PERCENT ? new NumberDecoratingFormatter(new PercentStyleFormatter()) : new NumberDecoratingFormatter(new CurrencyStyleFormatter());
         }
      }
   }

   private static class PatternDecoratingFormatter implements Formatter {
      private final String pattern;

      public PatternDecoratingFormatter(String pattern) {
         this.pattern = pattern;
      }

      public String print(MonetaryAmount object, Locale locale) {
         CurrencyStyleFormatter formatter = new CurrencyStyleFormatter();
         formatter.setCurrency(Currency.getInstance(object.getCurrency().getCurrencyCode()));
         formatter.setPattern(this.pattern);
         return formatter.print(object.getNumber(), locale);
      }

      public MonetaryAmount parse(String text, Locale locale) throws ParseException {
         CurrencyStyleFormatter formatter = new CurrencyStyleFormatter();
         Currency currency = this.determineCurrency(text, locale);
         CurrencyUnit currencyUnit = Monetary.getCurrency(currency.getCurrencyCode(), new String[0]);
         formatter.setCurrency(currency);
         formatter.setPattern(this.pattern);
         Number numberValue = formatter.parse(text, locale);
         return Monetary.getDefaultAmountFactory().setNumber(numberValue).setCurrency(currencyUnit).create();
      }

      private Currency determineCurrency(String text, Locale locale) {
         try {
            if (text.length() < 3) {
               return Currency.getInstance(locale);
            } else if (this.pattern.startsWith("¤¤")) {
               return Currency.getInstance(text.substring(0, 3));
            } else {
               return this.pattern.endsWith("¤¤") ? Currency.getInstance(text.substring(text.length() - 3)) : Currency.getInstance(locale);
            }
         } catch (IllegalArgumentException var4) {
            throw new IllegalArgumentException("Cannot determine currency for number value [" + text + "]", var4);
         }
      }
   }

   private static class NumberDecoratingFormatter implements Formatter {
      private final Formatter numberFormatter;

      public NumberDecoratingFormatter(Formatter numberFormatter) {
         this.numberFormatter = numberFormatter;
      }

      public String print(MonetaryAmount object, Locale locale) {
         return this.numberFormatter.print(object.getNumber(), locale);
      }

      public MonetaryAmount parse(String text, Locale locale) throws ParseException {
         CurrencyUnit currencyUnit = Monetary.getCurrency(locale, new String[0]);
         Number numberValue = (Number)this.numberFormatter.parse(text, locale);
         return Monetary.getDefaultAmountFactory().setNumber(numberValue).setCurrency(currencyUnit).create();
      }
   }
}
