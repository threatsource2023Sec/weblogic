package com.bea.core.repackaged.springframework.jmx.support;

import com.bea.core.repackaged.springframework.aop.TargetSource;
import com.bea.core.repackaged.springframework.aop.framework.ProxyFactory;
import com.bea.core.repackaged.springframework.aop.target.AbstractLazyCreationTargetSource;
import com.bea.core.repackaged.springframework.beans.factory.BeanClassLoaderAware;
import com.bea.core.repackaged.springframework.beans.factory.DisposableBean;
import com.bea.core.repackaged.springframework.beans.factory.FactoryBean;
import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.CollectionUtils;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class MBeanServerConnectionFactoryBean implements FactoryBean, BeanClassLoaderAware, InitializingBean, DisposableBean {
   @Nullable
   private JMXServiceURL serviceUrl;
   private Map environment = new HashMap();
   private boolean connectOnStartup = true;
   @Nullable
   private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();
   @Nullable
   private JMXConnector connector;
   @Nullable
   private MBeanServerConnection connection;
   @Nullable
   private JMXConnectorLazyInitTargetSource connectorTargetSource;

   public void setServiceUrl(String url) throws MalformedURLException {
      this.serviceUrl = new JMXServiceURL(url);
   }

   public void setEnvironment(Properties environment) {
      CollectionUtils.mergePropertiesIntoMap(environment, this.environment);
   }

   public void setEnvironmentMap(@Nullable Map environment) {
      if (environment != null) {
         this.environment.putAll(environment);
      }

   }

   public void setConnectOnStartup(boolean connectOnStartup) {
      this.connectOnStartup = connectOnStartup;
   }

   public void setBeanClassLoader(ClassLoader classLoader) {
      this.beanClassLoader = classLoader;
   }

   public void afterPropertiesSet() throws IOException {
      if (this.serviceUrl == null) {
         throw new IllegalArgumentException("Property 'serviceUrl' is required");
      } else {
         if (this.connectOnStartup) {
            this.connect();
         } else {
            this.createLazyConnection();
         }

      }
   }

   private void connect() throws IOException {
      Assert.state(this.serviceUrl != null, "No JMXServiceURL set");
      this.connector = JMXConnectorFactory.connect(this.serviceUrl, this.environment);
      this.connection = this.connector.getMBeanServerConnection();
   }

   private void createLazyConnection() {
      this.connectorTargetSource = new JMXConnectorLazyInitTargetSource();
      TargetSource connectionTargetSource = new MBeanServerConnectionLazyInitTargetSource();
      this.connector = (JMXConnector)(new ProxyFactory(JMXConnector.class, this.connectorTargetSource)).getProxy(this.beanClassLoader);
      this.connection = (MBeanServerConnection)(new ProxyFactory(MBeanServerConnection.class, connectionTargetSource)).getProxy(this.beanClassLoader);
   }

   @Nullable
   public MBeanServerConnection getObject() {
      return this.connection;
   }

   public Class getObjectType() {
      return this.connection != null ? this.connection.getClass() : MBeanServerConnection.class;
   }

   public boolean isSingleton() {
      return true;
   }

   public void destroy() throws IOException {
      if (this.connector != null && (this.connectorTargetSource == null || this.connectorTargetSource.isInitialized())) {
         this.connector.close();
      }

   }

   private class MBeanServerConnectionLazyInitTargetSource extends AbstractLazyCreationTargetSource {
      private MBeanServerConnectionLazyInitTargetSource() {
      }

      protected Object createObject() throws Exception {
         Assert.state(MBeanServerConnectionFactoryBean.this.connector != null, "JMXConnector not initialized");
         return MBeanServerConnectionFactoryBean.this.connector.getMBeanServerConnection();
      }

      public Class getTargetClass() {
         return MBeanServerConnection.class;
      }

      // $FF: synthetic method
      MBeanServerConnectionLazyInitTargetSource(Object x1) {
         this();
      }
   }

   private class JMXConnectorLazyInitTargetSource extends AbstractLazyCreationTargetSource {
      private JMXConnectorLazyInitTargetSource() {
      }

      protected Object createObject() throws Exception {
         Assert.state(MBeanServerConnectionFactoryBean.this.serviceUrl != null, "No JMXServiceURL set");
         return JMXConnectorFactory.connect(MBeanServerConnectionFactoryBean.this.serviceUrl, MBeanServerConnectionFactoryBean.this.environment);
      }

      public Class getTargetClass() {
         return JMXConnector.class;
      }

      // $FF: synthetic method
      JMXConnectorLazyInitTargetSource(Object x1) {
         this();
      }
   }
}
