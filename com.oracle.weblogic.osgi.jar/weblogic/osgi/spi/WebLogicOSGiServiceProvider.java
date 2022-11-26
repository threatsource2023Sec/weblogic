package weblogic.osgi.spi;

import org.osgi.framework.launch.Framework;
import weblogic.management.configuration.OsgiFrameworkMBean;
import weblogic.osgi.OSGiException;

public interface WebLogicOSGiServiceProvider {
   void provideServices(Framework var1, OsgiFrameworkMBean var2) throws OSGiException;

   void stopProvidingServices(OsgiFrameworkMBean var1);
}
