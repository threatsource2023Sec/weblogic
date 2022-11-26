package com.bea.core.jmspool;

import java.util.HashMap;
import java.util.Map;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;

public class PooledConnectionFactory implements QueueConnectionFactory, TopicConnectionFactory {
   private String initialContextFactory;
   private String jndiURL;
   private String jndiName;
   private int maxSessionPoolSize;
   private String poolName;
   private ConnectionFactory connectionFactory;
   private weblogic.deployment.jms.PooledConnectionFactory delegate;

   public String getInitialContextFactory() {
      return this.initialContextFactory;
   }

   public void setInitialContextFactory(String initialContextFactory) {
      if (this.connectionFactory != null) {
         this.failToSet("InitialContextFactory");
      }

      this.initialContextFactory = initialContextFactory;
   }

   public String getJNDIUrl() {
      return this.jndiURL;
   }

   public void setJNDIUrl(String jndiURL) {
      if (this.connectionFactory != null) {
         this.failToSet("JNDIUrl");
      }

      this.jndiURL = jndiURL;
   }

   public String getJNDIName() {
      return this.jndiName;
   }

   public void setJNDIName(String jndiName) {
      if (this.connectionFactory != null) {
         this.failToSet("JNDIName");
      }

      this.jndiName = jndiName;
   }

   public int getMaxSessionPoolSize() {
      return this.maxSessionPoolSize;
   }

   public void setMaxSessionPoolSize(int maxSessionPoolSize) {
      this.maxSessionPoolSize = maxSessionPoolSize;
   }

   public void setPoolName(String poolName) {
      this.poolName = poolName;
   }

   public String getPoolName() {
      return this.poolName;
   }

   public ConnectionFactory getConnectionFactory() {
      return this.connectionFactory;
   }

   public void setConnectionFactory(ConnectionFactory connectionFactory) {
      if (this.initialContextFactory != null) {
         this.failToSet("InitialContextFactory");
      }

      if (this.jndiURL != null) {
         this.failToSet("JNDIUrl");
      }

      if (this.jndiName != null) {
         this.failToSet("JNDIName");
      }

      this.connectionFactory = connectionFactory;
   }

   private void failToSet(String prop) {
      throw new IllegalArgumentException("Cannot set both '" + prop + "' and 'ConnectionFactory'");
   }

   public void start() throws JMSException {
      Map poolProps = new HashMap();
      if (this.connectionFactory == null) {
         poolProps.put("java.naming.factory.initial", this.initialContextFactory);
         poolProps.put("java.naming.provider.url", this.jndiURL);
         poolProps.put("JNDIName", this.jndiName);
      } else {
         poolProps.put("ConnectionFactory", this.connectionFactory);
      }

      if (this.maxSessionPoolSize > 0) {
         poolProps.put("MaxSessions", "" + this.maxSessionPoolSize);
      }

      if (this.poolName == null) {
         this.poolName = "JMSPool@" + System.identityHashCode(this);
      }

      this.delegate = new weblogic.deployment.jms.PooledConnectionFactory(this.poolName, 0, false, poolProps);
   }

   public QueueConnection createQueueConnection() throws JMSException {
      return this.delegate.createQueueConnection();
   }

   public QueueConnection createQueueConnection(String name, String password) throws JMSException {
      return this.delegate.createQueueConnection(name, password);
   }

   public Connection createConnection() throws JMSException {
      return this.delegate.createConnection();
   }

   public Connection createConnection(String name, String password) throws JMSException {
      return this.delegate.createConnection(name, password);
   }

   public TopicConnection createTopicConnection() throws JMSException {
      return this.delegate.createTopicConnection();
   }

   public TopicConnection createTopicConnection(String name, String password) throws JMSException {
      return this.delegate.createTopicConnection(name, password);
   }

   public JMSContext createContext() {
      throw new RuntimeException("This is a new JMS 2.0 method which has not yet been properly implemented");
   }

   public JMSContext createContext(int arg0) {
      throw new RuntimeException("This is a new JMS 2.0 method which has not yet been properly implemented");
   }

   public JMSContext createContext(String arg0, String arg1) {
      throw new RuntimeException("This is a new JMS 2.0 method which has not yet been properly implemented");
   }

   public JMSContext createContext(String arg0, String arg1, int arg2) {
      throw new RuntimeException("This is a new JMS 2.0 method which has not yet been properly implemented");
   }
}
