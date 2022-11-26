package weblogic.osgi.internal;

import java.util.HashMap;
import java.util.Map;
import org.osgi.framework.launch.Framework;
import weblogic.management.configuration.OsgiFrameworkMBean;
import weblogic.osgi.OSGiException;
import weblogic.osgi.spi.WebLogicOSGiServiceProvider;

public class DataSourceServiceProvider implements WebLogicOSGiServiceProvider {
   private final Map listeners = new HashMap();

   public void provideServices(Framework framework, OsgiFrameworkMBean bean) throws OSGiException {
      if (bean.isRegisterGlobalDataSources()) {
         OSGiDataSourceRegistryListener listener = new OSGiDataSourceRegistryListener(framework, bean.getName());
         listener.initialize();
         synchronized(this.listeners) {
            this.listeners.put(bean.getName(), listener);
         }
      }
   }

   public void stopProvidingServices(OsgiFrameworkMBean bean) {
      OSGiDataSourceRegistryListener listener;
      synchronized(this.listeners) {
         listener = (OSGiDataSourceRegistryListener)this.listeners.remove(bean.getName());
      }

      if (listener != null) {
         listener.destroy();
      }

   }
}
