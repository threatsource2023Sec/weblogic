package com.bea.core.repackaged.springframework.core.convert.support;

import com.bea.core.repackaged.springframework.core.CollectionFactory;
import com.bea.core.repackaged.springframework.core.convert.ConversionService;
import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.core.convert.converter.ConditionalGenericConverter;
import com.bea.core.repackaged.springframework.core.convert.converter.GenericConverter;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class MapToMapConverter implements ConditionalGenericConverter {
   private final ConversionService conversionService;

   public MapToMapConverter(ConversionService conversionService) {
      this.conversionService = conversionService;
   }

   public Set getConvertibleTypes() {
      return Collections.singleton(new GenericConverter.ConvertiblePair(Map.class, Map.class));
   }

   public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
      return this.canConvertKey(sourceType, targetType) && this.canConvertValue(sourceType, targetType);
   }

   @Nullable
   public Object convert(@Nullable Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
      if (source == null) {
         return null;
      } else {
         Map sourceMap = (Map)source;
         boolean copyRequired = !targetType.getType().isInstance(source);
         if (!copyRequired && sourceMap.isEmpty()) {
            return sourceMap;
         } else {
            TypeDescriptor keyDesc = targetType.getMapKeyTypeDescriptor();
            TypeDescriptor valueDesc = targetType.getMapValueTypeDescriptor();
            List targetEntries = new ArrayList(sourceMap.size());
            Iterator var9 = sourceMap.entrySet().iterator();

            while(true) {
               Object sourceKey;
               Object sourceValue;
               Object targetKey;
               Object targetValue;
               do {
                  if (!var9.hasNext()) {
                     if (!copyRequired) {
                        return sourceMap;
                     }

                     Map targetMap = CollectionFactory.createMap(targetType.getType(), keyDesc != null ? keyDesc.getType() : null, sourceMap.size());
                     Iterator var16 = targetEntries.iterator();

                     while(var16.hasNext()) {
                        MapEntry entry = (MapEntry)var16.next();
                        entry.addToMap(targetMap);
                     }

                     return targetMap;
                  }

                  Map.Entry entry = (Map.Entry)var9.next();
                  sourceKey = entry.getKey();
                  sourceValue = entry.getValue();
                  targetKey = this.convertKey(sourceKey, sourceType, keyDesc);
                  targetValue = this.convertValue(sourceValue, sourceType, valueDesc);
                  targetEntries.add(new MapEntry(targetKey, targetValue));
               } while(sourceKey == targetKey && sourceValue == targetValue);

               copyRequired = true;
            }
         }
      }
   }

   private boolean canConvertKey(TypeDescriptor sourceType, TypeDescriptor targetType) {
      return ConversionUtils.canConvertElements(sourceType.getMapKeyTypeDescriptor(), targetType.getMapKeyTypeDescriptor(), this.conversionService);
   }

   private boolean canConvertValue(TypeDescriptor sourceType, TypeDescriptor targetType) {
      return ConversionUtils.canConvertElements(sourceType.getMapValueTypeDescriptor(), targetType.getMapValueTypeDescriptor(), this.conversionService);
   }

   @Nullable
   private Object convertKey(Object sourceKey, TypeDescriptor sourceType, @Nullable TypeDescriptor targetType) {
      return targetType == null ? sourceKey : this.conversionService.convert(sourceKey, sourceType.getMapKeyTypeDescriptor(sourceKey), targetType);
   }

   @Nullable
   private Object convertValue(Object sourceValue, TypeDescriptor sourceType, @Nullable TypeDescriptor targetType) {
      return targetType == null ? sourceValue : this.conversionService.convert(sourceValue, sourceType.getMapValueTypeDescriptor(sourceValue), targetType);
   }

   private static class MapEntry {
      @Nullable
      private final Object key;
      @Nullable
      private final Object value;

      public MapEntry(@Nullable Object key, @Nullable Object value) {
         this.key = key;
         this.value = value;
      }

      public void addToMap(Map map) {
         map.put(this.key, this.value);
      }
   }
}
