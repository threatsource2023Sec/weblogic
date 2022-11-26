package com.sun.faces.application;

import com.sun.faces.util.MetadataWrapperMap;
import com.sun.faces.util.Util;
import java.util.HashMap;
import java.util.Map;

public class ApplicationInstanceFactoryMetadataMap extends MetadataWrapperMap {
   public ApplicationInstanceFactoryMetadataMap(Map toWrap) {
      super(toWrap);
   }

   public boolean hasAnnotations(String key) {
      Object objResult = ((Map)this.getMetadata().get(key)).get(ApplicationInstanceFactoryMetadataMap.METADATA.hasAnnotations);
      return objResult != null ? (Boolean)objResult : false;
   }

   public void scanForAnnotations(String key, Class value) {
      this.onPut((String)key, value);
   }

   protected Object onPut(String key, Object value) {
      if (value instanceof Class) {
         ((Map)this.getMetadata().computeIfAbsent(key, (e) -> {
            return new HashMap();
         })).put(ApplicationInstanceFactoryMetadataMap.METADATA.hasAnnotations, Util.classHasAnnotations((Class)value));
      }

      return null;
   }

   public static enum METADATA {
      hasAnnotations;
   }
}
