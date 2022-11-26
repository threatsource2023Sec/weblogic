package com.bea.core.repackaged.springframework.core.convert.support;

import com.bea.core.repackaged.springframework.core.convert.ConversionService;
import com.bea.core.repackaged.springframework.core.convert.converter.Converter;
import com.bea.core.repackaged.springframework.core.convert.converter.ConverterRegistry;
import com.bea.core.repackaged.springframework.core.convert.converter.GenericConverter;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.nio.charset.Charset;
import java.util.Currency;
import java.util.Locale;
import java.util.UUID;

public class DefaultConversionService extends GenericConversionService {
   @Nullable
   private static volatile DefaultConversionService sharedInstance;

   public DefaultConversionService() {
      addDefaultConverters(this);
   }

   public static ConversionService getSharedInstance() {
      DefaultConversionService cs = sharedInstance;
      if (cs == null) {
         Class var1 = DefaultConversionService.class;
         synchronized(DefaultConversionService.class) {
            cs = sharedInstance;
            if (cs == null) {
               cs = new DefaultConversionService();
               sharedInstance = cs;
            }
         }
      }

      return cs;
   }

   public static void addDefaultConverters(ConverterRegistry converterRegistry) {
      addScalarConverters(converterRegistry);
      addCollectionConverters(converterRegistry);
      converterRegistry.addConverter((GenericConverter)(new ByteBufferConverter((ConversionService)converterRegistry)));
      converterRegistry.addConverter((Converter)(new StringToTimeZoneConverter()));
      converterRegistry.addConverter((Converter)(new ZoneIdToTimeZoneConverter()));
      converterRegistry.addConverter((Converter)(new ZonedDateTimeToCalendarConverter()));
      converterRegistry.addConverter((GenericConverter)(new ObjectToObjectConverter()));
      converterRegistry.addConverter((GenericConverter)(new IdToEntityConverter((ConversionService)converterRegistry)));
      converterRegistry.addConverter((GenericConverter)(new FallbackObjectToStringConverter()));
      converterRegistry.addConverter((GenericConverter)(new ObjectToOptionalConverter((ConversionService)converterRegistry)));
   }

   public static void addCollectionConverters(ConverterRegistry converterRegistry) {
      ConversionService conversionService = (ConversionService)converterRegistry;
      converterRegistry.addConverter((GenericConverter)(new ArrayToCollectionConverter(conversionService)));
      converterRegistry.addConverter((GenericConverter)(new CollectionToArrayConverter(conversionService)));
      converterRegistry.addConverter((GenericConverter)(new ArrayToArrayConverter(conversionService)));
      converterRegistry.addConverter((GenericConverter)(new CollectionToCollectionConverter(conversionService)));
      converterRegistry.addConverter((GenericConverter)(new MapToMapConverter(conversionService)));
      converterRegistry.addConverter((GenericConverter)(new ArrayToStringConverter(conversionService)));
      converterRegistry.addConverter((GenericConverter)(new StringToArrayConverter(conversionService)));
      converterRegistry.addConverter((GenericConverter)(new ArrayToObjectConverter(conversionService)));
      converterRegistry.addConverter((GenericConverter)(new ObjectToArrayConverter(conversionService)));
      converterRegistry.addConverter((GenericConverter)(new CollectionToStringConverter(conversionService)));
      converterRegistry.addConverter((GenericConverter)(new StringToCollectionConverter(conversionService)));
      converterRegistry.addConverter((GenericConverter)(new CollectionToObjectConverter(conversionService)));
      converterRegistry.addConverter((GenericConverter)(new ObjectToCollectionConverter(conversionService)));
      converterRegistry.addConverter((GenericConverter)(new StreamConverter(conversionService)));
   }

   private static void addScalarConverters(ConverterRegistry converterRegistry) {
      converterRegistry.addConverterFactory(new NumberToNumberConverterFactory());
      converterRegistry.addConverterFactory(new StringToNumberConverterFactory());
      converterRegistry.addConverter(Number.class, String.class, new ObjectToStringConverter());
      converterRegistry.addConverter((Converter)(new StringToCharacterConverter()));
      converterRegistry.addConverter(Character.class, String.class, new ObjectToStringConverter());
      converterRegistry.addConverter((Converter)(new NumberToCharacterConverter()));
      converterRegistry.addConverterFactory(new CharacterToNumberFactory());
      converterRegistry.addConverter((Converter)(new StringToBooleanConverter()));
      converterRegistry.addConverter(Boolean.class, String.class, new ObjectToStringConverter());
      converterRegistry.addConverterFactory(new StringToEnumConverterFactory());
      converterRegistry.addConverter((Converter)(new EnumToStringConverter((ConversionService)converterRegistry)));
      converterRegistry.addConverterFactory(new IntegerToEnumConverterFactory());
      converterRegistry.addConverter((Converter)(new EnumToIntegerConverter((ConversionService)converterRegistry)));
      converterRegistry.addConverter((Converter)(new StringToLocaleConverter()));
      converterRegistry.addConverter(Locale.class, String.class, new ObjectToStringConverter());
      converterRegistry.addConverter((Converter)(new StringToCharsetConverter()));
      converterRegistry.addConverter(Charset.class, String.class, new ObjectToStringConverter());
      converterRegistry.addConverter((Converter)(new StringToCurrencyConverter()));
      converterRegistry.addConverter(Currency.class, String.class, new ObjectToStringConverter());
      converterRegistry.addConverter((Converter)(new StringToPropertiesConverter()));
      converterRegistry.addConverter((Converter)(new PropertiesToStringConverter()));
      converterRegistry.addConverter((Converter)(new StringToUUIDConverter()));
      converterRegistry.addConverter(UUID.class, String.class, new ObjectToStringConverter());
   }
}
