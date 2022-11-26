package com.bea.core.repackaged.springframework.beans.factory.config;

import com.bea.core.repackaged.springframework.beans.factory.FactoryBean;
import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;

public class YamlMapFactoryBean extends YamlProcessor implements FactoryBean, InitializingBean {
   private boolean singleton = true;
   @Nullable
   private Map map;

   public void setSingleton(boolean singleton) {
      this.singleton = singleton;
   }

   public boolean isSingleton() {
      return this.singleton;
   }

   public void afterPropertiesSet() {
      if (this.isSingleton()) {
         this.map = this.createMap();
      }

   }

   @Nullable
   public Map getObject() {
      return this.map != null ? this.map : this.createMap();
   }

   public Class getObjectType() {
      return Map.class;
   }

   protected Map createMap() {
      Map result = new LinkedHashMap();
      this.process((properties, map) -> {
         this.merge(result, map);
      });
      return result;
   }

   private void merge(Map output, Map map) {
      map.forEach((key, value) -> {
         Object existing = output.get(key);
         if (value instanceof Map && existing instanceof Map) {
            Map result = new LinkedHashMap((Map)existing);
            this.merge(result, (Map)value);
            output.put(key, result);
         } else {
            output.put(key, value);
         }

      });
   }
}
