package com.bea.core.repackaged.springframework.core.convert.support;

import com.bea.core.repackaged.springframework.core.convert.ConversionService;
import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.core.convert.converter.ConditionalGenericConverter;
import com.bea.core.repackaged.springframework.core.convert.converter.GenericConverter;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

final class ArrayToStringConverter implements ConditionalGenericConverter {
   private final CollectionToStringConverter helperConverter;

   public ArrayToStringConverter(ConversionService conversionService) {
      this.helperConverter = new CollectionToStringConverter(conversionService);
   }

   public Set getConvertibleTypes() {
      return Collections.singleton(new GenericConverter.ConvertiblePair(Object[].class, String.class));
   }

   public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
      return this.helperConverter.matches(sourceType, targetType);
   }

   @Nullable
   public Object convert(@Nullable Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
      return this.helperConverter.convert(Arrays.asList(ObjectUtils.toObjectArray(source)), sourceType, targetType);
   }
}
