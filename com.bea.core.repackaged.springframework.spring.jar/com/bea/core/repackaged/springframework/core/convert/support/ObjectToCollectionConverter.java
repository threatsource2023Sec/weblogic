package com.bea.core.repackaged.springframework.core.convert.support;

import com.bea.core.repackaged.springframework.core.CollectionFactory;
import com.bea.core.repackaged.springframework.core.convert.ConversionService;
import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.core.convert.converter.ConditionalGenericConverter;
import com.bea.core.repackaged.springframework.core.convert.converter.GenericConverter;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

final class ObjectToCollectionConverter implements ConditionalGenericConverter {
   private final ConversionService conversionService;

   public ObjectToCollectionConverter(ConversionService conversionService) {
      this.conversionService = conversionService;
   }

   public Set getConvertibleTypes() {
      return Collections.singleton(new GenericConverter.ConvertiblePair(Object.class, Collection.class));
   }

   public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
      return ConversionUtils.canConvertElements(sourceType, targetType.getElementTypeDescriptor(), this.conversionService);
   }

   @Nullable
   public Object convert(@Nullable Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
      if (source == null) {
         return null;
      } else {
         TypeDescriptor elementDesc = targetType.getElementTypeDescriptor();
         Collection target = CollectionFactory.createCollection(targetType.getType(), elementDesc != null ? elementDesc.getType() : null, 1);
         if (elementDesc != null && !elementDesc.isCollection()) {
            Object singleElement = this.conversionService.convert(source, sourceType, elementDesc);
            target.add(singleElement);
         } else {
            target.add(source);
         }

         return target;
      }
   }
}
