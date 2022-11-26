package weblogic.management.mbeanservers.runtime.internal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.provider.RuntimeAccess;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(5)
public class PlatformServerService extends AbstractServerService {
   @Inject
   @Named("RuntimeAccessService")
   private ServerService dependency;
   @Inject
   private Provider runtimeAccessProvider;
   private static DebugLogger debug = DebugLogger.getDebugLogger("DebugJMXRuntime");

   public void start() throws ServiceFailureException {
      if (((RuntimeAccess)this.runtimeAccessProvider.get()).getDomain().getJMX().isPlatformMBeanServerUsed()) {
         System.setProperty("javax.management.builder.initial", "weblogic.management.jmx.mbeanserver.WLSMBeanServerBuilder");
         if (debug.isDebugEnabled()) {
            debug.debug("Set system property javax.management.builder.initial=weblogic.management.jmx.mbeanserver.WLSMBeanServerBuilder");
         }
      }

   }
}
