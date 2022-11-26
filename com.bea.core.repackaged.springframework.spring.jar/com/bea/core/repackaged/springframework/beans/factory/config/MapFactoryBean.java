package com.bea.core.repackaged.springframework.beans.factory.config;

import com.bea.core.repackaged.springframework.beans.BeanUtils;
import com.bea.core.repackaged.springframework.beans.TypeConverter;
import com.bea.core.repackaged.springframework.core.ResolvableType;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class MapFactoryBean extends AbstractFactoryBean {
   @Nullable
   private Map sourceMap;
   @Nullable
   private Class targetMapClass;

   public void setSourceMap(Map sourceMap) {
      this.sourceMap = sourceMap;
   }

   public void setTargetMapClass(@Nullable Class targetMapClass) {
      if (targetMapClass == null) {
         throw new IllegalArgumentException("'targetMapClass' must not be null");
      } else if (!Map.class.isAssignableFrom(targetMapClass)) {
         throw new IllegalArgumentException("'targetMapClass' must implement [java.util.Map]");
      } else {
         this.targetMapClass = targetMapClass;
      }
   }

   public Class getObjectType() {
      return Map.class;
   }

   protected Map createInstance() {
      if (this.sourceMap == null) {
         throw new IllegalArgumentException("'sourceMap' is required");
      } else {
         Map result = null;
         if (this.targetMapClass != null) {
            result = (Map)BeanUtils.instantiateClass(this.targetMapClass);
         } else {
            result = new LinkedHashMap(this.sourceMap.size());
         }

         Class keyType = null;
         Class valueType = null;
         if (this.targetMapClass != null) {
            ResolvableType mapType = ResolvableType.forClass(this.targetMapClass).asMap();
            keyType = mapType.resolveGeneric(0);
            valueType = mapType.resolveGeneric(1);
         }

         if (keyType == null && valueType == null) {
            ((Map)result).putAll(this.sourceMap);
         } else {
            TypeConverter converter = this.getBeanTypeConverter();
            Iterator var5 = this.sourceMap.entrySet().iterator();

            while(var5.hasNext()) {
               Map.Entry entry = (Map.Entry)var5.next();
               Object convertedKey = converter.convertIfNecessary(entry.getKey(), keyType);
               Object convertedValue = converter.convertIfNecessary(entry.getValue(), valueType);
               ((Map)result).put(convertedKey, convertedValue);
            }
         }

         return (Map)result;
      }
   }
}
