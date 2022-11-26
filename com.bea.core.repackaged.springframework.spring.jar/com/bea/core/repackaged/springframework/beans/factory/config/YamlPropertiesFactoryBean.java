package com.bea.core.repackaged.springframework.beans.factory.config;

import com.bea.core.repackaged.springframework.beans.factory.FactoryBean;
import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.core.CollectionFactory;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.Properties;

public class YamlPropertiesFactoryBean extends YamlProcessor implements FactoryBean, InitializingBean {
   private boolean singleton = true;
   @Nullable
   private Properties properties;

   public void setSingleton(boolean singleton) {
      this.singleton = singleton;
   }

   public boolean isSingleton() {
      return this.singleton;
   }

   public void afterPropertiesSet() {
      if (this.isSingleton()) {
         this.properties = this.createProperties();
      }

   }

   @Nullable
   public Properties getObject() {
      return this.properties != null ? this.properties : this.createProperties();
   }

   public Class getObjectType() {
      return Properties.class;
   }

   protected Properties createProperties() {
      Properties result = CollectionFactory.createStringAdaptingProperties();
      this.process((properties, map) -> {
         result.putAll(properties);
      });
      return result;
   }
}
