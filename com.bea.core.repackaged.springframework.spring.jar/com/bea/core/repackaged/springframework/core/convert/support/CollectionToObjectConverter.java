package com.bea.core.repackaged.springframework.core.convert.support;

import com.bea.core.repackaged.springframework.core.convert.ConversionService;
import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.core.convert.converter.ConditionalGenericConverter;
import com.bea.core.repackaged.springframework.core.convert.converter.GenericConverter;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

final class CollectionToObjectConverter implements ConditionalGenericConverter {
   private final ConversionService conversionService;

   public CollectionToObjectConverter(ConversionService conversionService) {
      this.conversionService = conversionService;
   }

   public Set getConvertibleTypes() {
      return Collections.singleton(new GenericConverter.ConvertiblePair(Collection.class, Object.class));
   }

   public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
      return ConversionUtils.canConvertElements(sourceType.getElementTypeDescriptor(), targetType, this.conversionService);
   }

   @Nullable
   public Object convert(@Nullable Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
      if (source == null) {
         return null;
      } else if (sourceType.isAssignableTo(targetType)) {
         return source;
      } else {
         Collection sourceCollection = (Collection)source;
         if (sourceCollection.isEmpty()) {
            return null;
         } else {
            Object firstElement = sourceCollection.iterator().next();
            return this.conversionService.convert(firstElement, sourceType.elementTypeDescriptor(firstElement), targetType);
         }
      }
   }
}
