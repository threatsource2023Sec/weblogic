package com.bea.core.repackaged.springframework.core.convert.support;

import com.bea.core.repackaged.springframework.core.CollectionFactory;
import com.bea.core.repackaged.springframework.core.convert.ConversionService;
import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.core.convert.converter.ConditionalGenericConverter;
import com.bea.core.repackaged.springframework.core.convert.converter.GenericConverter;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

final class CollectionToCollectionConverter implements ConditionalGenericConverter {
   private final ConversionService conversionService;

   public CollectionToCollectionConverter(ConversionService conversionService) {
      this.conversionService = conversionService;
   }

   public Set getConvertibleTypes() {
      return Collections.singleton(new GenericConverter.ConvertiblePair(Collection.class, Collection.class));
   }

   public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
      return ConversionUtils.canConvertElements(sourceType.getElementTypeDescriptor(), targetType.getElementTypeDescriptor(), this.conversionService);
   }

   @Nullable
   public Object convert(@Nullable Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
      if (source == null) {
         return null;
      } else {
         Collection sourceCollection = (Collection)source;
         boolean copyRequired = !targetType.getType().isInstance(source);
         if (!copyRequired && sourceCollection.isEmpty()) {
            return source;
         } else {
            TypeDescriptor elementDesc = targetType.getElementTypeDescriptor();
            if (elementDesc == null && !copyRequired) {
               return source;
            } else {
               Collection target = CollectionFactory.createCollection(targetType.getType(), elementDesc != null ? elementDesc.getType() : null, sourceCollection.size());
               if (elementDesc == null) {
                  target.addAll(sourceCollection);
               } else {
                  Iterator var8 = sourceCollection.iterator();

                  while(var8.hasNext()) {
                     Object sourceElement = var8.next();
                     Object targetElement = this.conversionService.convert(sourceElement, sourceType.elementTypeDescriptor(sourceElement), elementDesc);
                     target.add(targetElement);
                     if (sourceElement != targetElement) {
                        copyRequired = true;
                     }
                  }
               }

               return copyRequired ? target : source;
            }
         }
      }
   }
}
