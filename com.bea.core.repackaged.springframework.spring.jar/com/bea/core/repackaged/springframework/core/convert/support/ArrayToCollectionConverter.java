package com.bea.core.repackaged.springframework.core.convert.support;

import com.bea.core.repackaged.springframework.core.CollectionFactory;
import com.bea.core.repackaged.springframework.core.convert.ConversionService;
import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.core.convert.converter.ConditionalGenericConverter;
import com.bea.core.repackaged.springframework.core.convert.converter.GenericConverter;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

final class ArrayToCollectionConverter implements ConditionalGenericConverter {
   private final ConversionService conversionService;

   public ArrayToCollectionConverter(ConversionService conversionService) {
      this.conversionService = conversionService;
   }

   public Set getConvertibleTypes() {
      return Collections.singleton(new GenericConverter.ConvertiblePair(Object[].class, Collection.class));
   }

   public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
      return ConversionUtils.canConvertElements(sourceType.getElementTypeDescriptor(), targetType.getElementTypeDescriptor(), this.conversionService);
   }

   @Nullable
   public Object convert(@Nullable Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
      if (source == null) {
         return null;
      } else {
         int length = Array.getLength(source);
         TypeDescriptor elementDesc = targetType.getElementTypeDescriptor();
         Collection target = CollectionFactory.createCollection(targetType.getType(), elementDesc != null ? elementDesc.getType() : null, length);
         int i;
         Object sourceElement;
         if (elementDesc == null) {
            for(i = 0; i < length; ++i) {
               sourceElement = Array.get(source, i);
               target.add(sourceElement);
            }
         } else {
            for(i = 0; i < length; ++i) {
               sourceElement = Array.get(source, i);
               Object targetElement = this.conversionService.convert(sourceElement, sourceType.elementTypeDescriptor(sourceElement), elementDesc);
               target.add(targetElement);
            }
         }

         return target;
      }
   }
}
