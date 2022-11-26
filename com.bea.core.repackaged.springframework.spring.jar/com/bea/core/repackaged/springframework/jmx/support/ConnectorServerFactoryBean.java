package com.bea.core.repackaged.springframework.jmx.support;

import com.bea.core.repackaged.springframework.beans.factory.DisposableBean;
import com.bea.core.repackaged.springframework.beans.factory.FactoryBean;
import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.jmx.JmxException;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.CollectionUtils;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.management.JMException;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import javax.management.remote.MBeanServerForwarder;

public class ConnectorServerFactoryBean extends MBeanRegistrationSupport implements FactoryBean, InitializingBean, DisposableBean {
   public static final String DEFAULT_SERVICE_URL = "service:jmx:jmxmp://localhost:9875";
   private String serviceUrl = "service:jmx:jmxmp://localhost:9875";
   private Map environment = new HashMap();
   @Nullable
   private MBeanServerForwarder forwarder;
   @Nullable
   private ObjectName objectName;
   private boolean threaded = false;
   private boolean daemon = false;
   @Nullable
   private JMXConnectorServer connectorServer;

   public void setServiceUrl(String serviceUrl) {
      this.serviceUrl = serviceUrl;
   }

   public void setEnvironment(@Nullable Properties environment) {
      CollectionUtils.mergePropertiesIntoMap(environment, this.environment);
   }

   public void setEnvironmentMap(@Nullable Map environment) {
      if (environment != null) {
         this.environment.putAll(environment);
      }

   }

   public void setForwarder(MBeanServerForwarder forwarder) {
      this.forwarder = forwarder;
   }

   public void setObjectName(Object objectName) throws MalformedObjectNameException {
      this.objectName = ObjectNameManager.getInstance(objectName);
   }

   public void setThreaded(boolean threaded) {
      this.threaded = threaded;
   }

   public void setDaemon(boolean daemon) {
      this.daemon = daemon;
   }

   public void afterPropertiesSet() throws JMException, IOException {
      if (this.server == null) {
         this.server = JmxUtils.locateMBeanServer();
      }

      JMXServiceURL url = new JMXServiceURL(this.serviceUrl);
      this.connectorServer = JMXConnectorServerFactory.newJMXConnectorServer(url, this.environment, this.server);
      if (this.forwarder != null) {
         this.connectorServer.setMBeanServerForwarder(this.forwarder);
      }

      if (this.objectName != null) {
         this.doRegister(this.connectorServer, this.objectName);
      }

      try {
         if (this.threaded) {
            final JMXConnectorServer serverToStart = this.connectorServer;
            Thread connectorThread = new Thread() {
               public void run() {
                  try {
                     serverToStart.start();
                  } catch (IOException var2) {
                     throw new JmxException("Could not start JMX connector server after delay", var2);
                  }
               }
            };
            connectorThread.setName("JMX Connector Thread [" + this.serviceUrl + "]");
            connectorThread.setDaemon(this.daemon);
            connectorThread.start();
         } else {
            this.connectorServer.start();
         }

         if (this.logger.isInfoEnabled()) {
            this.logger.info("JMX connector server started: " + this.connectorServer);
         }

      } catch (IOException var4) {
         this.unregisterBeans();
         throw var4;
      }
   }

   @Nullable
   public JMXConnectorServer getObject() {
      return this.connectorServer;
   }

   public Class getObjectType() {
      return this.connectorServer != null ? this.connectorServer.getClass() : JMXConnectorServer.class;
   }

   public boolean isSingleton() {
      return true;
   }

   public void destroy() throws IOException {
      try {
         if (this.connectorServer != null) {
            if (this.logger.isInfoEnabled()) {
               this.logger.info("Stopping JMX connector server: " + this.connectorServer);
            }

            this.connectorServer.stop();
         }
      } finally {
         this.unregisterBeans();
      }

   }
}
