package com.bea.core.repackaged.springframework.core.convert.support;

import com.bea.core.repackaged.springframework.core.convert.ConversionService;
import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.core.convert.converter.ConditionalGenericConverter;
import com.bea.core.repackaged.springframework.core.convert.converter.GenericConverter;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.lang.reflect.Array;
import java.util.Collections;
import java.util.Set;

final class ObjectToArrayConverter implements ConditionalGenericConverter {
   private final ConversionService conversionService;

   public ObjectToArrayConverter(ConversionService conversionService) {
      this.conversionService = conversionService;
   }

   public Set getConvertibleTypes() {
      return Collections.singleton(new GenericConverter.ConvertiblePair(Object.class, Object[].class));
   }

   public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
      return ConversionUtils.canConvertElements(sourceType, targetType.getElementTypeDescriptor(), this.conversionService);
   }

   @Nullable
   public Object convert(@Nullable Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
      if (source == null) {
         return null;
      } else {
         TypeDescriptor targetElementType = targetType.getElementTypeDescriptor();
         Assert.state(targetElementType != null, "No target element type");
         Object target = Array.newInstance(targetElementType.getType(), 1);
         Object targetElement = this.conversionService.convert(source, sourceType, targetElementType);
         Array.set(target, 0, targetElement);
         return target;
      }
   }
}
