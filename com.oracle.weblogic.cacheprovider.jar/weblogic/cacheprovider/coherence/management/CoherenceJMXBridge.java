package weblogic.cacheprovider.coherence.management;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import weblogic.management.ManagementException;
import weblogic.management.runtime.ApplicationRuntimeMBean;
import weblogic.management.runtime.CoherenceClusterRuntimeMBean;
import weblogic.management.runtime.EJBComponentRuntimeMBean;
import weblogic.management.runtime.WebAppComponentRuntimeMBean;

public class CoherenceJMXBridge {
   private Map runtimeMBeanByLoader = new ConcurrentHashMap();
   private ObjectName sysScopedClusterObjectName;
   private MBeanServer sysScopedMBeanServer;
   private boolean isSystemScoped;

   public void registerApplicationRuntimeMBean(ClassLoader loader, ApplicationRuntimeMBean appRTMBean) throws ManagementException {
      this.runtimeMBeanByLoader.put(loader, new RuntimeMBeanEntry(appRTMBean));
      if (this.isSystemScoped) {
         appRTMBean.setCoherenceClusterRuntime(new CoherenceClusterRuntimeMBeanImpl(this.sysScopedClusterObjectName, this.sysScopedMBeanServer, appRTMBean));
      }

   }

   public void unRegisterApplicationRuntimeMBean(ClassLoader loader, ApplicationRuntimeMBean appRTMBean) throws ManagementException {
      this.runtimeMBeanByLoader.remove(loader);
   }

   public void registerWebAppComponentRuntimeMBean(ClassLoader loader, WebAppComponentRuntimeMBean[] webappCompRTMBeans) throws ManagementException {
      this.runtimeMBeanByLoader.put(loader, new RuntimeMBeanEntry(webappCompRTMBeans));
      if (this.isSystemScoped) {
         WebAppComponentRuntimeMBean[] var3 = webappCompRTMBeans;
         int var4 = webappCompRTMBeans.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            WebAppComponentRuntimeMBean mbean = var3[var5];
            mbean.setCoherenceClusterRuntime(new CoherenceClusterRuntimeMBeanImpl(this.sysScopedClusterObjectName, this.sysScopedMBeanServer, mbean));
         }
      }

   }

   public void unRegisterWebAppComponentRuntimeMBean(ClassLoader loader, WebAppComponentRuntimeMBean[] webappCompRTMBeans) throws ManagementException {
      this.runtimeMBeanByLoader.remove(loader);
   }

   public void registerEJBComponentRuntimeMBean(ClassLoader loader, EJBComponentRuntimeMBean ejbCompRTMBean) throws ManagementException {
      this.runtimeMBeanByLoader.put(loader, new RuntimeMBeanEntry(ejbCompRTMBean));
      if (this.isSystemScoped) {
         ejbCompRTMBean.setCoherenceClusterRuntime(new CoherenceClusterRuntimeMBeanImpl(this.sysScopedClusterObjectName, this.sysScopedMBeanServer, ejbCompRTMBean));
      }

   }

   public void unRegisterEJBComponentRuntimeMBean(ClassLoader loader, EJBComponentRuntimeMBean ejbCompRTMBean) throws ManagementException {
      this.runtimeMBeanByLoader.remove(loader);
   }

   public void clusterStarted(ClassLoader loader, ObjectName objName, MBeanServer mbeanServer) throws ManagementException {
      RuntimeMBeanEntry impl = (RuntimeMBeanEntry)this.runtimeMBeanByLoader.get(loader);
      if (impl != null) {
         impl.setCoherenceClusterRuntimeMBean(objName, mbeanServer);
      } else if (!this.isSystemScoped) {
         this.sysScopedClusterObjectName = objName;
         this.sysScopedMBeanServer = mbeanServer;
         this.isSystemScoped = true;
         Iterator itr = this.runtimeMBeanByLoader.values().iterator();

         while(itr.hasNext()) {
            RuntimeMBeanEntry entry = (RuntimeMBeanEntry)itr.next();
            entry.setCoherenceClusterRuntimeMBean(objName, mbeanServer);
         }
      }

   }

   public void shutdown(ClassLoader loader) {
      Iterator itr = this.runtimeMBeanByLoader.keySet().iterator();

      while(itr.hasNext()) {
         ClassLoader child = (ClassLoader)itr.next();
         if (this.isChildOfLoader(loader, child)) {
            itr.remove();
         }
      }

   }

   private boolean isChildOfLoader(ClassLoader parent, ClassLoader child) {
      if (child == parent) {
         return true;
      } else {
         for(ClassLoader tmp = child.getParent(); tmp != null; tmp = tmp.getParent()) {
            if (tmp == parent) {
               return true;
            }
         }

         return false;
      }
   }

   private class RuntimeMBeanEntry {
      ApplicationRuntimeMBean appRuntimeMBean;
      WebAppComponentRuntimeMBean[] webAppRuntimeMBeans;
      EJBComponentRuntimeMBean ejbRuntimeMBean;

      private RuntimeMBeanEntry(ApplicationRuntimeMBean mbean) {
         this.appRuntimeMBean = mbean;
      }

      private RuntimeMBeanEntry(WebAppComponentRuntimeMBean[] mbeans) {
         this.webAppRuntimeMBeans = mbeans;
      }

      private RuntimeMBeanEntry(EJBComponentRuntimeMBean mbean) {
         this.ejbRuntimeMBean = mbean;
      }

      private void setCoherenceClusterRuntimeMBean(ObjectName objName, MBeanServer mbeanServer) throws ManagementException {
         CoherenceClusterRuntimeMBean clusterMBean = null;
         if (this.appRuntimeMBean != null) {
            clusterMBean = new CoherenceClusterRuntimeMBeanImpl(objName, mbeanServer, this.appRuntimeMBean);
            this.appRuntimeMBean.setCoherenceClusterRuntime(clusterMBean);
         } else if (this.webAppRuntimeMBeans != null) {
            WebAppComponentRuntimeMBean[] var4 = this.webAppRuntimeMBeans;
            int var5 = var4.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               WebAppComponentRuntimeMBean mbean = var4[var6];
               clusterMBean = new CoherenceClusterRuntimeMBeanImpl(objName, mbeanServer, mbean);
               mbean.setCoherenceClusterRuntime(clusterMBean);
            }
         } else if (this.ejbRuntimeMBean != null) {
            clusterMBean = new CoherenceClusterRuntimeMBeanImpl(objName, mbeanServer, this.ejbRuntimeMBean);
            this.ejbRuntimeMBean.setCoherenceClusterRuntime(clusterMBean);
         }

      }

      // $FF: synthetic method
      RuntimeMBeanEntry(ApplicationRuntimeMBean x1, Object x2) {
         this((ApplicationRuntimeMBean)x1);
      }

      // $FF: synthetic method
      RuntimeMBeanEntry(WebAppComponentRuntimeMBean[] x1, Object x2) {
         this((WebAppComponentRuntimeMBean[])x1);
      }

      // $FF: synthetic method
      RuntimeMBeanEntry(EJBComponentRuntimeMBean x1, Object x2) {
         this((EJBComponentRuntimeMBean)x1);
      }
   }
}
