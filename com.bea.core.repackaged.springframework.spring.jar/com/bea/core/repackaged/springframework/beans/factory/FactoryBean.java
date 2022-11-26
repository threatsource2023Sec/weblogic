package com.bea.core.repackaged.springframework.beans.factory;

import com.bea.core.repackaged.springframework.lang.Nullable;

public interface FactoryBean {
   @Nullable
   Object getObject() throws Exception;

   @Nullable
   Class getObjectType();

   default boolean isSingleton() {
      return true;
   }
}
