package weblogic.deploy.api.spi.deploy.mbeans;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.enterprise.deploy.spi.DeploymentManager;
import javax.management.AttributeChangeNotification;
import javax.management.AttributeChangeNotificationFilter;
import javax.management.MBeanServerConnection;
import javax.management.Notification;
import javax.management.NotificationListener;
import weblogic.deploy.api.internal.SPIDeployerLogger;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.spi.DeploymentOptions;
import weblogic.deploy.api.spi.WebLogicDeploymentManager;
import weblogic.deploy.api.spi.exceptions.ServerConnectionException;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DomainMBean;

public abstract class MBeanCache implements Serializable {
   private static final long serialVersionUID = 1L;
   private static final boolean ddebug = Debug.isDebug("internal");
   private static final boolean debug = Debug.isDebug("deploy");
   private boolean cachingEnabled = true;
   private transient List mbeans = null;
   private boolean stale = true;
   protected transient WebLogicDeploymentManager dm;
   protected transient DomainMBean currDomain;
   protected Object listener;
   protected String[] listenType = null;
   private String lastDeploymentScope = null;
   private String currentDeploymentScope = null;
   private static final String PARTITION_PREFIX = "partition_";
   private static final String RESOURCE_GROUP_TEMPLATE_PREFIX = "rgt_";
   private static final String DOMAIN = "domain";

   MBeanCache(WebLogicDeploymentManager dm) {
      this.dm = dm;
   }

   public synchronized List getMBeans() throws ServerConnectionException {
      return this.getMBeans((DeploymentOptions)null);
   }

   public synchronized List getMBeans(DeploymentOptions options) throws ServerConnectionException {
      this.invalidateCacheIfScopeDoesNotMatch(options);
      if (this.stale) {
         try {
            ConfigurationMBean[] mb = this.getTypedMBeans(options);
            this.mbeans = new ArrayList();

            for(int i = 0; i < mb.length; ++i) {
               if (mb[i] != null) {
                  this.mbeans.add(mb[i]);
               }
            }

            this.stale = !this.cachingEnabled;
            if (ddebug) {
               Debug.say("Returning " + this.mbeans.size() + " mbeans: ");
            }
         } catch (Throwable var4) {
            throw new ServerConnectionException(SPIDeployerLogger.connectionError(), var4);
         }
      }

      return this.mbeans;
   }

   protected void invalidateCacheIfScopeDoesNotMatch(DeploymentOptions options) {
      if (options == null) {
         this.currentDeploymentScope = "domain";
      } else if (options.getPartition() != null) {
         this.currentDeploymentScope = "partition_" + options.getPartition();
      } else if (options.getResourceGroupTemplate() != null) {
         this.currentDeploymentScope = "rgt_" + options.getResourceGroupTemplate();
      } else {
         this.currentDeploymentScope = "domain";
      }

      if (this.lastDeploymentScope != null && !this.currentDeploymentScope.equals(this.lastDeploymentScope)) {
         this.stale = true;
      }

      this.lastDeploymentScope = this.currentDeploymentScope;
   }

   protected void addNotificationListener() {
      if (this.dm.getServerConnection().getMBeanServerConnection() == null) {
         DescriptorBean bean = this.currDomain;
         this.listener = new MyListener(false);
         bean.addPropertyChangeListener((PropertyChangeListener)this.listener);
      } else {
         this.listener = new MyListener(true);

         try {
            MBeanServerConnection runtimeConnection = this.dm.getServerConnection().getRuntimeServerConnection();
            if (runtimeConnection == null) {
               if (debug || ddebug) {
                  Debug.say("Disabling mbean caching since we do not runtime mbean server connection");
               }

               this.cachingEnabled = false;
               return;
            }

            if (debug || ddebug) {
               Debug.say("Adding notification listener for " + this);
            }

            runtimeConnection.addNotificationListener(this.currDomain.getObjectName(), (NotificationListener)this.listener, new MyJMXFilter(), (Object)null);
            if (debug || ddebug) {
               Debug.say("Added notification listener for " + this);
            }
         } catch (Exception var2) {
            if (debug || ddebug) {
               Debug.say("Disabling mbean caching due to: " + var2.toString());
            }

            this.cachingEnabled = false;
         }
      }

   }

   protected void removeNotificationListener() {
      if (this.dm.getServerConnection().getMBeanServerConnection() == null) {
         DescriptorBean bean = this.currDomain;
         if (this.listener != null) {
            bean.removePropertyChangeListener((PropertyChangeListener)this.listener);
         }
      } else {
         try {
            this.currDomain.removeNotificationListener((NotificationListener)this.listener);
         } catch (Exception var2) {
         }
      }

   }

   public abstract ConfigurationMBean[] getTypedMBeans();

   public abstract ConfigurationMBean[] getTypedMBeans(DeploymentOptions var1);

   public ConfigurationMBean getMBean(String name) throws ServerConnectionException {
      return this.getMBean((DeploymentOptions)null, name);
   }

   public ConfigurationMBean getMBean(DeploymentOptions options, String name) throws ServerConnectionException {
      Iterator var3 = this.getMBeans(options).iterator();

      ConfigurationMBean mb;
      do {
         if (!var3.hasNext()) {
            return null;
         }

         mb = (ConfigurationMBean)var3.next();
      } while(!mb.getName().equals(name));

      return mb;
   }

   public synchronized void reset() {
      if (ddebug) {
         Debug.say("Resetting cache: " + this.getClass().getName());
      }

      this.mbeans = null;
      this.stale = true;
   }

   public void close() {
      this.reset();
      this.removeNotificationListener();
   }

   public boolean isStale() {
      return this.stale;
   }

   public DeploymentManager getDM() {
      return this.dm;
   }

   public class MyJMXFilter extends AttributeChangeNotificationFilter implements Serializable {
      private static final long serialVersionUID = 7201439898187309867L;

      public MyJMXFilter() {
         if (MBeanCache.this.listenType != null) {
            this.disableAllAttributes();

            for(int i = 0; i < MBeanCache.this.listenType.length; ++i) {
               this.enableAttribute(MBeanCache.this.listenType[i]);
            }
         }

      }
   }

   public class MyListener implements PropertyChangeListener, NotificationListener, Serializable {
      private static final long serialVersionUID = 1L;
      private Vector props = new Vector();
      private boolean jmx;

      public MyListener(boolean jmx) {
         this.jmx = jmx;
         if (MBeanCache.this.listenType != null) {
            for(int i = 0; i < MBeanCache.this.listenType.length; ++i) {
               this.props.add(MBeanCache.this.listenType[i]);
            }

         }
      }

      public void propertyChange(PropertyChangeEvent pce) {
         if (MBeanCache.ddebug) {
            Debug.say("Got change event for " + pce.getPropertyName());
         }

         if (this.props.contains(pce.getPropertyName())) {
            MBeanCache.this.reset();
         }

      }

      public void handleNotification(Notification n, Object o) {
         if (n instanceof AttributeChangeNotification) {
            if (MBeanCache.ddebug) {
               Debug.say("Got change event for prop: " + ((AttributeChangeNotification)n).getAttributeName());
            }

            if (this.props.contains(((AttributeChangeNotification)n).getAttributeName())) {
               MBeanCache.this.reset();
            }

         }
      }
   }
}
