package com.bea.core.repackaged.springframework.core.convert.support;

import com.bea.core.repackaged.springframework.core.convert.ConversionService;
import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.core.convert.converter.ConditionalGenericConverter;
import com.bea.core.repackaged.springframework.core.convert.converter.GenericConverter;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

final class CollectionToArrayConverter implements ConditionalGenericConverter {
   private final ConversionService conversionService;

   public CollectionToArrayConverter(ConversionService conversionService) {
      this.conversionService = conversionService;
   }

   public Set getConvertibleTypes() {
      return Collections.singleton(new GenericConverter.ConvertiblePair(Collection.class, Object[].class));
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
         TypeDescriptor targetElementType = targetType.getElementTypeDescriptor();
         Assert.state(targetElementType != null, "No target element type");
         Object array = Array.newInstance(targetElementType.getType(), sourceCollection.size());
         int i = 0;
         Iterator var8 = sourceCollection.iterator();

         while(var8.hasNext()) {
            Object sourceElement = var8.next();
            Object targetElement = this.conversionService.convert(sourceElement, sourceType.elementTypeDescriptor(sourceElement), targetElementType);
            Array.set(array, i++, targetElement);
         }

         return array;
      }
   }
}
