package weblogic.xml.jaxp;

import java.security.AccessController;
import weblogic.management.configuration.ServerDebugMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.xml.registry.EntityCache;
import weblogic.xml.registry.RegistryEntityResolver;
import weblogic.xml.registry.XMLRegistry;
import weblogic.xml.registry.XMLRegistryException;
import weblogic.xml.util.Debug;

public class XMLContext {
   static ServerMBean serverConfigMBean = null;
   static Debug.DebugFacility dbg = null;

   static ServerMBean getServerConfigMBean() throws XMLRegistryException {
      if (serverConfigMBean == null) {
         AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
         serverConfigMBean = ManagementService.getRuntimeAccess(kernelId).getServer();
         if (serverConfigMBean == null) {
            dbg.println("Can't get serverConfigMBean");
            throw new XMLRegistryException("ServerConfigMBean can't be null!");
         }
      }

      return serverConfigMBean;
   }

   public static void init() {
      try {
         ServerDebugMBean serverDebugMBean = null;
         serverDebugMBean = getServerConfigMBean().getServerDebug();
         if (serverDebugMBean != null) {
            Debug.DebugFacility var10003 = getDebug();
            var10003.getClass();
            serverDebugMBean.addPropertyChangeListener(new Debug.DebugFacility.DebugListener(var10003));
            getDebug().setMBean(serverDebugMBean);
         } else {
            dbg.pe("Can't get serverDebugMBean. User settings through mbean will be ignored.");
         }
      } catch (Exception var1) {
         dbg.px(var1, "Failure setting serverDebugMBean.", 1, 2);
      }

   }

   public static Debug.DebugFacility getDebug() {
      return dbg;
   }

   public static RegistryEntityResolver getResolver() throws XMLRegistryException {
      return new RegistryEntityResolver();
   }

   public static XMLRegistry[] getRegistries() throws XMLRegistryException {
      return (new RegistryEntityResolver()).getRegistryPath();
   }

   public static EntityCache getCache(XMLRegistry registry) throws XMLRegistryException {
      return registry.getCache();
   }

   static {
      Debug.DebugSpec debugSpec = Debug.getDebugSpec();
      debugSpec.name = "xml.jaxp";
      debugSpec.prefix = "JAXP";
      dbg = Debug.makeDebugFacility(debugSpec);
   }
}
