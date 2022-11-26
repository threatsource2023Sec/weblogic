package com.bea.core.repackaged.springframework.core.convert.support;

import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.core.convert.converter.ConditionalGenericConverter;
import com.bea.core.repackaged.springframework.core.convert.converter.GenericConverter;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Set;

final class FallbackObjectToStringConverter implements ConditionalGenericConverter {
   public Set getConvertibleTypes() {
      return Collections.singleton(new GenericConverter.ConvertiblePair(Object.class, String.class));
   }

   public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
      Class sourceClass = sourceType.getObjectType();
      if (String.class == sourceClass) {
         return false;
      } else {
         return CharSequence.class.isAssignableFrom(sourceClass) || StringWriter.class.isAssignableFrom(sourceClass) || ObjectToObjectConverter.hasConversionMethodOrConstructor(sourceClass, String.class);
      }
   }

   @Nullable
   public Object convert(@Nullable Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
      return source != null ? source.toString() : null;
   }
}
