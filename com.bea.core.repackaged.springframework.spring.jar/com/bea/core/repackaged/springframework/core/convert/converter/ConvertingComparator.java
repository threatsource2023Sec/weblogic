package com.bea.core.repackaged.springframework.core.convert.converter;

import com.bea.core.repackaged.springframework.core.convert.ConversionService;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.comparator.Comparators;
import java.util.Comparator;
import java.util.Map;

public class ConvertingComparator implements Comparator {
   private final Comparator comparator;
   private final Converter converter;

   public ConvertingComparator(Converter converter) {
      this(Comparators.comparable(), converter);
   }

   public ConvertingComparator(Comparator comparator, Converter converter) {
      Assert.notNull(comparator, (String)"Comparator must not be null");
      Assert.notNull(converter, (String)"Converter must not be null");
      this.comparator = comparator;
      this.converter = converter;
   }

   public ConvertingComparator(Comparator comparator, ConversionService conversionService, Class targetType) {
      this(comparator, new ConversionServiceConverter(conversionService, targetType));
   }

   public int compare(Object o1, Object o2) {
      Object c1 = this.converter.convert(o1);
      Object c2 = this.converter.convert(o2);
      return this.comparator.compare(c1, c2);
   }

   public static ConvertingComparator mapEntryKeys(Comparator comparator) {
      return new ConvertingComparator(comparator, Map.Entry::getKey);
   }

   public static ConvertingComparator mapEntryValues(Comparator comparator) {
      return new ConvertingComparator(comparator, Map.Entry::getValue);
   }

   private static class ConversionServiceConverter implements Converter {
      private final ConversionService conversionService;
      private final Class targetType;

      public ConversionServiceConverter(ConversionService conversionService, Class targetType) {
         Assert.notNull(conversionService, (String)"ConversionService must not be null");
         Assert.notNull(targetType, (String)"TargetType must not be null");
         this.conversionService = conversionService;
         this.targetType = targetType;
      }

      @Nullable
      public Object convert(Object source) {
         return this.conversionService.convert(source, this.targetType);
      }
   }
}
