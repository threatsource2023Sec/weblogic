package com.bea.core.repackaged.springframework.jmx.access;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.beans.factory.DisposableBean;
import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.jmx.JmxException;
import com.bea.core.repackaged.springframework.jmx.MBeanServerNotFoundException;
import com.bea.core.repackaged.springframework.jmx.support.NotificationListenerHolder;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.CollectionUtils;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXServiceURL;

public class NotificationListenerRegistrar extends NotificationListenerHolder implements InitializingBean, DisposableBean {
   protected final Log logger = LogFactory.getLog(this.getClass());
   private final ConnectorDelegate connector = new ConnectorDelegate();
   @Nullable
   private MBeanServerConnection server;
   @Nullable
   private JMXServiceURL serviceUrl;
   @Nullable
   private Map environment;
   @Nullable
   private String agentId;
   @Nullable
   private ObjectName[] actualObjectNames;

   public void setServer(MBeanServerConnection server) {
      this.server = server;
   }

   public void setEnvironment(@Nullable Map environment) {
      this.environment = environment;
   }

   @Nullable
   public Map getEnvironment() {
      return this.environment;
   }

   public void setServiceUrl(String url) throws MalformedURLException {
      this.serviceUrl = new JMXServiceURL(url);
   }

   public void setAgentId(String agentId) {
      this.agentId = agentId;
   }

   public void afterPropertiesSet() {
      if (this.getNotificationListener() == null) {
         throw new IllegalArgumentException("Property 'notificationListener' is required");
      } else if (CollectionUtils.isEmpty((Collection)this.mappedObjectNames)) {
         throw new IllegalArgumentException("Property 'mappedObjectName' is required");
      } else {
         this.prepare();
      }
   }

   public void prepare() {
      if (this.server == null) {
         this.server = this.connector.connect(this.serviceUrl, this.environment, this.agentId);
      }

      try {
         this.actualObjectNames = this.getResolvedObjectNames();
         if (this.actualObjectNames != null) {
            if (this.logger.isDebugEnabled()) {
               this.logger.debug("Registering NotificationListener for MBeans " + Arrays.asList(this.actualObjectNames));
            }

            ObjectName[] var1 = this.actualObjectNames;
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
               ObjectName actualObjectName = var1[var3];
               this.server.addNotificationListener(actualObjectName, this.getNotificationListener(), this.getNotificationFilter(), this.getHandback());
            }
         }

      } catch (IOException var5) {
         throw new MBeanServerNotFoundException("Could not connect to remote MBeanServer at URL [" + this.serviceUrl + "]", var5);
      } catch (Exception var6) {
         throw new JmxException("Unable to register NotificationListener", var6);
      }
   }

   public void destroy() {
      try {
         if (this.server != null && this.actualObjectNames != null) {
            ObjectName[] var1 = this.actualObjectNames;
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
               ObjectName actualObjectName = var1[var3];

               try {
                  this.server.removeNotificationListener(actualObjectName, this.getNotificationListener(), this.getNotificationFilter(), this.getHandback());
               } catch (Exception var9) {
                  if (this.logger.isDebugEnabled()) {
                     this.logger.debug("Unable to unregister NotificationListener", var9);
                  }
               }
            }
         }
      } finally {
         this.connector.close();
      }

   }
}
