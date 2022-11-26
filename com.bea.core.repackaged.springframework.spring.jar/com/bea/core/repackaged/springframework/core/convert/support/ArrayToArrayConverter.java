package com.bea.core.repackaged.springframework.core.convert.support;

import com.bea.core.repackaged.springframework.core.convert.ConversionService;
import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.core.convert.converter.ConditionalGenericConverter;
import com.bea.core.repackaged.springframework.core.convert.converter.GenericConverter;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

final class ArrayToArrayConverter implements ConditionalGenericConverter {
   private final CollectionToArrayConverter helperConverter;
   private final ConversionService conversionService;

   public ArrayToArrayConverter(ConversionService conversionService) {
      this.helperConverter = new CollectionToArrayConverter(conversionService);
      this.conversionService = conversionService;
   }

   public Set getConvertibleTypes() {
      return Collections.singleton(new GenericConverter.ConvertiblePair(Object[].class, Object[].class));
   }

   public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
      return this.helperConverter.matches(sourceType, targetType);
   }

   @Nullable
   public Object convert(@Nullable Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
      if (this.conversionService instanceof GenericConversionService) {
         TypeDescriptor targetElement = targetType.getElementTypeDescriptor();
         if (targetElement != null && ((GenericConversionService)this.conversionService).canBypassConvert(sourceType.getElementTypeDescriptor(), targetElement)) {
            return source;
         }
      }

      List sourceList = Arrays.asList(ObjectUtils.toObjectArray(source));
      return this.helperConverter.convert(sourceList, sourceType, targetType);
   }
}
