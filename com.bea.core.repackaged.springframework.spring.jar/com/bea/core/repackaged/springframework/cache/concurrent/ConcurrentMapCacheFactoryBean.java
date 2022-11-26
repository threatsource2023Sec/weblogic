package com.bea.core.repackaged.springframework.cache.concurrent;

import com.bea.core.repackaged.springframework.beans.factory.BeanNameAware;
import com.bea.core.repackaged.springframework.beans.factory.FactoryBean;
import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.util.concurrent.ConcurrentMap;

public class ConcurrentMapCacheFactoryBean implements FactoryBean, BeanNameAware, InitializingBean {
   private String name = "";
   @Nullable
   private ConcurrentMap store;
   private boolean allowNullValues = true;
   @Nullable
   private ConcurrentMapCache cache;

   public void setName(String name) {
      this.name = name;
   }

   public void setStore(ConcurrentMap store) {
      this.store = store;
   }

   public void setAllowNullValues(boolean allowNullValues) {
      this.allowNullValues = allowNullValues;
   }

   public void setBeanName(String beanName) {
      if (!StringUtils.hasLength(this.name)) {
         this.setName(beanName);
      }

   }

   public void afterPropertiesSet() {
      this.cache = this.store != null ? new ConcurrentMapCache(this.name, this.store, this.allowNullValues) : new ConcurrentMapCache(this.name, this.allowNullValues);
   }

   @Nullable
   public ConcurrentMapCache getObject() {
      return this.cache;
   }

   public Class getObjectType() {
      return ConcurrentMapCache.class;
   }

   public boolean isSingleton() {
      return true;
   }
}
