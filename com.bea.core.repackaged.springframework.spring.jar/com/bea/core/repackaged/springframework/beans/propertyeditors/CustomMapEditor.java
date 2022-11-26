package com.bea.core.repackaged.springframework.beans.propertyeditors;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import java.beans.PropertyEditorSupport;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class CustomMapEditor extends PropertyEditorSupport {
   private final Class mapType;
   private final boolean nullAsEmptyMap;

   public CustomMapEditor(Class mapType) {
      this(mapType, false);
   }

   public CustomMapEditor(Class mapType, boolean nullAsEmptyMap) {
      Assert.notNull(mapType, (String)"Map type is required");
      if (!Map.class.isAssignableFrom(mapType)) {
         throw new IllegalArgumentException("Map type [" + mapType.getName() + "] does not implement [java.util.Map]");
      } else {
         this.mapType = mapType;
         this.nullAsEmptyMap = nullAsEmptyMap;
      }
   }

   public void setAsText(String text) throws IllegalArgumentException {
      this.setValue(text);
   }

   public void setValue(@Nullable Object value) {
      if (value == null && this.nullAsEmptyMap) {
         super.setValue(this.createMap(this.mapType, 0));
      } else if (value == null || this.mapType.isInstance(value) && !this.alwaysCreateNewMap()) {
         super.setValue(value);
      } else {
         if (!(value instanceof Map)) {
            throw new IllegalArgumentException("Value cannot be converted to Map: " + value);
         }

         Map source = (Map)value;
         Map target = this.createMap(this.mapType, source.size());
         source.forEach((key, val) -> {
            target.put(this.convertKey(key), this.convertValue(val));
         });
         super.setValue(target);
      }

   }

   protected Map createMap(Class mapType, int initialCapacity) {
      if (!mapType.isInterface()) {
         try {
            return (Map)ReflectionUtils.accessibleConstructor(mapType).newInstance();
         } catch (Throwable var4) {
            throw new IllegalArgumentException("Could not instantiate map class: " + mapType.getName(), var4);
         }
      } else {
         return (Map)(SortedMap.class == mapType ? new TreeMap() : new LinkedHashMap(initialCapacity));
      }
   }

   protected boolean alwaysCreateNewMap() {
      return false;
   }

   protected Object convertKey(Object key) {
      return key;
   }

   protected Object convertValue(Object value) {
      return value;
   }

   @Nullable
   public String getAsText() {
      return null;
   }
}
