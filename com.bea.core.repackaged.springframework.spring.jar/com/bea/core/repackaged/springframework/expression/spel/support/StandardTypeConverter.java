package com.bea.core.repackaged.springframework.expression.spel.support;

import com.bea.core.repackaged.springframework.core.convert.ConversionException;
import com.bea.core.repackaged.springframework.core.convert.ConversionService;
import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.core.convert.support.DefaultConversionService;
import com.bea.core.repackaged.springframework.expression.TypeConverter;
import com.bea.core.repackaged.springframework.expression.spel.SpelEvaluationException;
import com.bea.core.repackaged.springframework.expression.spel.SpelMessage;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;

public class StandardTypeConverter implements TypeConverter {
   private final ConversionService conversionService;

   public StandardTypeConverter() {
      this.conversionService = DefaultConversionService.getSharedInstance();
   }

   public StandardTypeConverter(ConversionService conversionService) {
      Assert.notNull(conversionService, (String)"ConversionService must not be null");
      this.conversionService = conversionService;
   }

   public boolean canConvert(@Nullable TypeDescriptor sourceType, TypeDescriptor targetType) {
      return this.conversionService.canConvert(sourceType, targetType);
   }

   @Nullable
   public Object convertValue(@Nullable Object value, @Nullable TypeDescriptor sourceType, TypeDescriptor targetType) {
      try {
         return this.conversionService.convert(value, sourceType, targetType);
      } catch (ConversionException var5) {
         throw new SpelEvaluationException(var5, SpelMessage.TYPE_CONVERSION_ERROR, new Object[]{sourceType != null ? sourceType.toString() : (value != null ? value.getClass().getName() : "null"), targetType.toString()});
      }
   }
}
