package com.bea.core.repackaged.springframework.core.convert.support;

import com.bea.core.repackaged.springframework.core.CollectionFactory;
import com.bea.core.repackaged.springframework.core.convert.ConversionService;
import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.core.convert.converter.ConditionalGenericConverter;
import com.bea.core.repackaged.springframework.core.convert.converter.GenericConverter;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

final class StringToCollectionConverter implements ConditionalGenericConverter {
   private final ConversionService conversionService;

   public StringToCollectionConverter(ConversionService conversionService) {
      this.conversionService = conversionService;
   }

   public Set getConvertibleTypes() {
      return Collections.singleton(new GenericConverter.ConvertiblePair(String.class, Collection.class));
   }

   public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
      return targetType.getElementTypeDescriptor() == null || this.conversionService.canConvert(sourceType, targetType.getElementTypeDescriptor());
   }

   @Nullable
   public Object convert(@Nullable Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
      if (source == null) {
         return null;
      } else {
         String string = (String)source;
         String[] fields = StringUtils.commaDelimitedListToStringArray(string);
         TypeDescriptor elementDesc = targetType.getElementTypeDescriptor();
         Collection target = CollectionFactory.createCollection(targetType.getType(), elementDesc != null ? elementDesc.getType() : null, fields.length);
         String[] var8;
         int var9;
         int var10;
         String field;
         if (elementDesc == null) {
            var8 = fields;
            var9 = fields.length;

            for(var10 = 0; var10 < var9; ++var10) {
               field = var8[var10];
               target.add(field.trim());
            }
         } else {
            var8 = fields;
            var9 = fields.length;

            for(var10 = 0; var10 < var9; ++var10) {
               field = var8[var10];
               Object targetElement = this.conversionService.convert(field.trim(), sourceType, elementDesc);
               target.add(targetElement);
            }
         }

         return target;
      }
   }
}
