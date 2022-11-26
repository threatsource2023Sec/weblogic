package com.bea.core.repackaged.springframework.core.convert.support;

import com.bea.core.repackaged.springframework.core.convert.ConversionService;
import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.core.convert.converter.ConditionalGenericConverter;
import com.bea.core.repackaged.springframework.core.convert.converter.GenericConverter;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.lang.reflect.Array;
import java.util.Collections;
import java.util.Set;

final class StringToArrayConverter implements ConditionalGenericConverter {
   private final ConversionService conversionService;

   public StringToArrayConverter(ConversionService conversionService) {
      this.conversionService = conversionService;
   }

   public Set getConvertibleTypes() {
      return Collections.singleton(new GenericConverter.ConvertiblePair(String.class, Object[].class));
   }

   public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
      return ConversionUtils.canConvertElements(sourceType, targetType.getElementTypeDescriptor(), this.conversionService);
   }

   @Nullable
   public Object convert(@Nullable Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
      if (source == null) {
         return null;
      } else {
         String string = (String)source;
         String[] fields = StringUtils.commaDelimitedListToStringArray(string);
         TypeDescriptor targetElementType = targetType.getElementTypeDescriptor();
         Assert.state(targetElementType != null, "No target element type");
         Object target = Array.newInstance(targetElementType.getType(), fields.length);

         for(int i = 0; i < fields.length; ++i) {
            String sourceElement = fields[i];
            Object targetElement = this.conversionService.convert(sourceElement.trim(), sourceType, targetElementType);
            Array.set(target, i, targetElement);
         }

         return target;
      }
   }
}
