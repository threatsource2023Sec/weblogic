package com.bea.core.repackaged.springframework.jca.support;

import com.bea.core.repackaged.springframework.beans.factory.FactoryBean;
import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.lang.Nullable;
import javax.resource.ResourceException;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ManagedConnectionFactory;

public class LocalConnectionFactoryBean implements FactoryBean, InitializingBean {
   @Nullable
   private ManagedConnectionFactory managedConnectionFactory;
   @Nullable
   private ConnectionManager connectionManager;
   @Nullable
   private Object connectionFactory;

   public void setManagedConnectionFactory(ManagedConnectionFactory managedConnectionFactory) {
      this.managedConnectionFactory = managedConnectionFactory;
   }

   public void setConnectionManager(ConnectionManager connectionManager) {
      this.connectionManager = connectionManager;
   }

   public void afterPropertiesSet() throws ResourceException {
      if (this.managedConnectionFactory == null) {
         throw new IllegalArgumentException("Property 'managedConnectionFactory' is required");
      } else {
         if (this.connectionManager != null) {
            this.connectionFactory = this.managedConnectionFactory.createConnectionFactory(this.connectionManager);
         } else {
            this.connectionFactory = this.managedConnectionFactory.createConnectionFactory();
         }

      }
   }

   @Nullable
   public Object getObject() {
      return this.connectionFactory;
   }

   public Class getObjectType() {
      return this.connectionFactory != null ? this.connectionFactory.getClass() : null;
   }

   public boolean isSingleton() {
      return true;
   }
}
