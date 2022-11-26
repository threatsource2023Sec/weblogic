package com.oracle.injection.integration;

import javax.persistence.PersistenceProperty;
import weblogic.j2ee.descriptor.JavaEEPropertyBean;

class JavaEEPropertyBeanAdapter implements JavaEEPropertyBean {
   private final PersistenceProperty m_persistenceProperty;

   JavaEEPropertyBeanAdapter(PersistenceProperty persistenceProperty) {
      if (persistenceProperty == null) {
         throw new IllegalArgumentException("PersistenceProperty parameter cannot be null");
      } else {
         this.m_persistenceProperty = persistenceProperty;
      }
   }

   public String getName() {
      return this.m_persistenceProperty.name();
   }

   public void setName(String name) {
   }

   public String getValue() {
      return this.m_persistenceProperty.value();
   }

   public void setValue(String value) {
   }
}
