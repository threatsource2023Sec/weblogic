package com.bea.core.repackaged.springframework.core.convert.support;

import com.bea.core.repackaged.springframework.core.convert.converter.Converter;
import com.bea.core.repackaged.springframework.core.convert.converter.ConverterFactory;
import com.bea.core.repackaged.springframework.core.convert.converter.ConverterRegistry;
import com.bea.core.repackaged.springframework.core.convert.converter.GenericConverter;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.Iterator;
import java.util.Set;

public final class ConversionServiceFactory {
   private ConversionServiceFactory() {
   }

   public static void registerConverters(@Nullable Set converters, ConverterRegistry registry) {
      if (converters != null) {
         Iterator var2 = converters.iterator();

         while(var2.hasNext()) {
            Object converter = var2.next();
            if (converter instanceof GenericConverter) {
               registry.addConverter((GenericConverter)converter);
            } else if (converter instanceof Converter) {
               registry.addConverter((Converter)converter);
            } else {
               if (!(converter instanceof ConverterFactory)) {
                  throw new IllegalArgumentException("Each converter object must implement one of the Converter, ConverterFactory, or GenericConverter interfaces");
               }

               registry.addConverterFactory((ConverterFactory)converter);
            }
         }
      }

   }
}
