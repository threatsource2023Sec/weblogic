package com.bea.core.repackaged.springframework.core.convert.support;

import com.bea.core.repackaged.springframework.core.convert.ConversionService;
import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.core.convert.converter.ConditionalGenericConverter;
import com.bea.core.repackaged.springframework.core.convert.converter.GenericConverter;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

final class CollectionToStringConverter implements ConditionalGenericConverter {
   private static final String DELIMITER = ",";
   private final ConversionService conversionService;

   public CollectionToStringConverter(ConversionService conversionService) {
      this.conversionService = conversionService;
   }

   public Set getConvertibleTypes() {
      return Collections.singleton(new GenericConverter.ConvertiblePair(Collection.class, String.class));
   }

   public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
      return ConversionUtils.canConvertElements(sourceType.getElementTypeDescriptor(), targetType, this.conversionService);
   }

   @Nullable
   public Object convert(@Nullable Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
      if (source == null) {
         return null;
      } else {
         Collection sourceCollection = (Collection)source;
         if (sourceCollection.isEmpty()) {
            return "";
         } else {
            StringBuilder sb = new StringBuilder();
            int i = 0;

            for(Iterator var7 = sourceCollection.iterator(); var7.hasNext(); ++i) {
               Object sourceElement = var7.next();
               if (i > 0) {
                  sb.append(",");
               }

               Object targetElement = this.conversionService.convert(sourceElement, sourceType.elementTypeDescriptor(sourceElement), targetType);
               sb.append(targetElement);
            }

            return sb.toString();
         }
      }
   }
}
