package com.bea.core.repackaged.springframework.jmx.access;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.jmx.MBeanServerNotFoundException;
import com.bea.core.repackaged.springframework.jmx.support.JmxUtils;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.io.IOException;
import java.util.Map;
import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

class ConnectorDelegate {
   private static final Log logger = LogFactory.getLog(ConnectorDelegate.class);
   @Nullable
   private JMXConnector connector;

   public MBeanServerConnection connect(@Nullable JMXServiceURL serviceUrl, @Nullable Map environment, @Nullable String agentId) throws MBeanServerNotFoundException {
      if (serviceUrl != null) {
         if (logger.isDebugEnabled()) {
            logger.debug("Connecting to remote MBeanServer at URL [" + serviceUrl + "]");
         }

         try {
            this.connector = JMXConnectorFactory.connect(serviceUrl, environment);
            return this.connector.getMBeanServerConnection();
         } catch (IOException var5) {
            throw new MBeanServerNotFoundException("Could not connect to remote MBeanServer [" + serviceUrl + "]", var5);
         }
      } else {
         logger.debug("Attempting to locate local MBeanServer");
         return JmxUtils.locateMBeanServer(agentId);
      }
   }

   public void close() {
      if (this.connector != null) {
         try {
            this.connector.close();
         } catch (IOException var2) {
            logger.debug("Could not close JMX connector", var2);
         }
      }

   }
}
