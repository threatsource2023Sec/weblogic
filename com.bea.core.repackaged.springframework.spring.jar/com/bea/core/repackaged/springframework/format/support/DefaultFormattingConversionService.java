package com.bea.core.repackaged.springframework.format.support;

import com.bea.core.repackaged.springframework.core.convert.support.DefaultConversionService;
import com.bea.core.repackaged.springframework.format.FormatterRegistry;
import com.bea.core.repackaged.springframework.format.datetime.DateFormatterRegistrar;
import com.bea.core.repackaged.springframework.format.datetime.joda.JodaTimeFormatterRegistrar;
import com.bea.core.repackaged.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import com.bea.core.repackaged.springframework.format.number.NumberFormatAnnotationFormatterFactory;
import com.bea.core.repackaged.springframework.format.number.money.CurrencyUnitFormatter;
import com.bea.core.repackaged.springframework.format.number.money.Jsr354NumberFormatAnnotationFormatterFactory;
import com.bea.core.repackaged.springframework.format.number.money.MonetaryAmountFormatter;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.StringValueResolver;

public class DefaultFormattingConversionService extends FormattingConversionService {
   private static final boolean jsr354Present;
   private static final boolean jodaTimePresent;

   public DefaultFormattingConversionService() {
      this((StringValueResolver)null, true);
   }

   public DefaultFormattingConversionService(boolean registerDefaultFormatters) {
      this((StringValueResolver)null, registerDefaultFormatters);
   }

   public DefaultFormattingConversionService(@Nullable StringValueResolver embeddedValueResolver, boolean registerDefaultFormatters) {
      if (embeddedValueResolver != null) {
         this.setEmbeddedValueResolver(embeddedValueResolver);
      }

      DefaultConversionService.addDefaultConverters(this);
      if (registerDefaultFormatters) {
         addDefaultFormatters(this);
      }

   }

   public static void addDefaultFormatters(FormatterRegistry formatterRegistry) {
      formatterRegistry.addFormatterForFieldAnnotation(new NumberFormatAnnotationFormatterFactory());
      if (jsr354Present) {
         formatterRegistry.addFormatter(new CurrencyUnitFormatter());
         formatterRegistry.addFormatter(new MonetaryAmountFormatter());
         formatterRegistry.addFormatterForFieldAnnotation(new Jsr354NumberFormatAnnotationFormatterFactory());
      }

      (new DateTimeFormatterRegistrar()).registerFormatters(formatterRegistry);
      if (jodaTimePresent) {
         (new JodaTimeFormatterRegistrar()).registerFormatters(formatterRegistry);
      } else {
         (new DateFormatterRegistrar()).registerFormatters(formatterRegistry);
      }

   }

   static {
      ClassLoader classLoader = DefaultFormattingConversionService.class.getClassLoader();
      jsr354Present = ClassUtils.isPresent("javax.money.MonetaryAmount", classLoader);
      jodaTimePresent = ClassUtils.isPresent("org.joda.time.LocalDate", classLoader);
   }
}
