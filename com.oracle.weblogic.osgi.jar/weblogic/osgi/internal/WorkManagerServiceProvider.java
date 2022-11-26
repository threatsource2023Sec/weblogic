package weblogic.osgi.internal;

import java.util.HashMap;
import java.util.Map;
import org.osgi.framework.launch.Framework;
import weblogic.management.configuration.OsgiFrameworkMBean;
import weblogic.osgi.OSGiException;
import weblogic.osgi.spi.WebLogicOSGiServiceProvider;

public class WorkManagerServiceProvider implements WebLogicOSGiServiceProvider {
   private final Map listeners = new HashMap();

   public void provideServices(Framework framework, OsgiFrameworkMBean bean) throws OSGiException {
      if (bean.isRegisterGlobalWorkManagers()) {
         OSGiWorkManagerRegistryListener listener = new OSGiWorkManagerRegistryListener(framework, Utilities.getCurrentCIC().getPartitionName() + "-" + bean.getName());
         listener.initialize();
         synchronized(this.listeners) {
            this.listeners.put(bean.getName(), listener);
         }
      }
   }

   public void stopProvidingServices(OsgiFrameworkMBean bean) {
      OSGiWorkManagerRegistryListener listener;
      synchronized(this.listeners) {
         listener = (OSGiWorkManagerRegistryListener)this.listeners.remove(bean.getName());
      }

      if (listener != null) {
         listener.destroy();
      }

   }
}
