package com.bea.core.repackaged.springframework.beans.factory.config;

import com.bea.core.repackaged.springframework.beans.factory.FactoryBean;
import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.core.io.support.PropertiesLoaderSupport;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFactoryBean extends PropertiesLoaderSupport implements FactoryBean, InitializingBean {
   private boolean singleton = true;
   @Nullable
   private Properties singletonInstance;

   public final void setSingleton(boolean singleton) {
      this.singleton = singleton;
   }

   public final boolean isSingleton() {
      return this.singleton;
   }

   public final void afterPropertiesSet() throws IOException {
      if (this.singleton) {
         this.singletonInstance = this.createProperties();
      }

   }

   @Nullable
   public final Properties getObject() throws IOException {
      return this.singleton ? this.singletonInstance : this.createProperties();
   }

   public Class getObjectType() {
      return Properties.class;
   }

   protected Properties createProperties() throws IOException {
      return this.mergeProperties();
   }
}
