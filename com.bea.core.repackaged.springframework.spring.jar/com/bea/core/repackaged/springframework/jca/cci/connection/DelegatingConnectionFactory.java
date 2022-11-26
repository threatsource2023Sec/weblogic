package com.bea.core.repackaged.springframework.jca.cci.connection;

import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import javax.naming.NamingException;
import javax.naming.Reference;
import javax.resource.ResourceException;
import javax.resource.cci.Connection;
import javax.resource.cci.ConnectionFactory;
import javax.resource.cci.ConnectionSpec;
import javax.resource.cci.RecordFactory;
import javax.resource.cci.ResourceAdapterMetaData;

public class DelegatingConnectionFactory implements ConnectionFactory, InitializingBean {
   @Nullable
   private ConnectionFactory targetConnectionFactory;

   public void setTargetConnectionFactory(@Nullable ConnectionFactory targetConnectionFactory) {
      this.targetConnectionFactory = targetConnectionFactory;
   }

   @Nullable
   public ConnectionFactory getTargetConnectionFactory() {
      return this.targetConnectionFactory;
   }

   protected ConnectionFactory obtainTargetConnectionFactory() {
      ConnectionFactory connectionFactory = this.getTargetConnectionFactory();
      Assert.state(connectionFactory != null, "No 'targetConnectionFactory' set");
      return connectionFactory;
   }

   public void afterPropertiesSet() {
      if (this.getTargetConnectionFactory() == null) {
         throw new IllegalArgumentException("Property 'targetConnectionFactory' is required");
      }
   }

   public Connection getConnection() throws ResourceException {
      return this.obtainTargetConnectionFactory().getConnection();
   }

   public Connection getConnection(ConnectionSpec connectionSpec) throws ResourceException {
      return this.obtainTargetConnectionFactory().getConnection(connectionSpec);
   }

   public RecordFactory getRecordFactory() throws ResourceException {
      return this.obtainTargetConnectionFactory().getRecordFactory();
   }

   public ResourceAdapterMetaData getMetaData() throws ResourceException {
      return this.obtainTargetConnectionFactory().getMetaData();
   }

   public Reference getReference() throws NamingException {
      return this.obtainTargetConnectionFactory().getReference();
   }

   public void setReference(Reference reference) {
      this.obtainTargetConnectionFactory().setReference(reference);
   }
}
