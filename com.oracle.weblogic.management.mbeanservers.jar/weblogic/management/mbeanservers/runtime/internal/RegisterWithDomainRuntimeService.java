package weblogic.management.mbeanservers.runtime.internal;

import java.lang.annotation.Annotation;
import java.rmi.UnknownHostException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.management.remote.JMXServiceURL;
import org.glassfish.hk2.utilities.reflection.ReflectionHelper;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.jndi.Environment;
import weblogic.management.configuration.JMXMBean;
import weblogic.management.jmx.JMXLogger;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.protocol.ConnectMonitorFactory;
import weblogic.protocol.ServerURL;
import weblogic.protocol.URLManager;
import weblogic.protocol.URLManagerService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.AbstractServerService;
import weblogic.server.GlobalServiceLocator;
import weblogic.server.ServiceFailureException;
import weblogic.t3.srvr.EnableListenersIfAdminChannelAbsentService;

public abstract class RegisterWithDomainRuntimeService extends AbstractServerService {
   private static DebugLogger debug = DebugLogger.getDebugLogger("DebugJMXRuntime");
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static boolean doNotPingPort = Boolean.getBoolean("weblogic.management.failover.doNotPingAdminPort");
   private static long DEFAULT_INTERNAL_CONNECTION_TIMEOUT = 180000L;
   private static Long internalConnectionTimeout;
   RuntimeAccess runtimeAccess;

   public static Long getInternalConnectionTimeout() {
      return internalConnectionTimeout;
   }

   public void start() throws ServiceFailureException {
      this.runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
      if (this.isEnabled() && !this.runtimeAccess.isAdminServer()) {
         if (debug.isDebugEnabled()) {
            debug.debug("Starting MBeanServer weblogic.management.mbeanservers.runtime");
         }

         this.setupConnection();
      } else {
         if (debug.isDebugEnabled()) {
            debug.debug("Runtime MBeanServer Disabledweblogic.management.mbeanservers.runtime");
         }

      }
   }

   public void stop() throws ServiceFailureException {
      if (this.runtimeAccess == null) {
         this.runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
      }

      if (this.isEnabled() && !this.runtimeAccess.isAdminServer()) {
         try {
            ConnectMonitorFactory.unregister();
         } catch (Throwable var2) {
            if (debug.isDebugEnabled()) {
               debug.debug("Unable to unregister disconnect listener");
            }
         }

      }
   }

   private boolean isEnabled() {
      JMXMBean jmx = this.runtimeAccess.getDomain().getJMX();
      return jmx.isRuntimeMBeanServerEnabled() && (jmx.isDomainMBeanServerEnabled() || jmx.isManagementEJBEnabled());
   }

   private static URLManagerService getURLManagerService() {
      return (URLManagerService)GlobalServiceLocator.getServiceLocator().getService(URLManagerService.class, new Annotation[0]);
   }

   private void setupConnection() {
      JMXServiceURL serviceURL = null;
      String adminServerName = ManagementService.getRuntimeAccess(kernelId).getAdminServerName();
      String adminServerURL = null;

      try {
         adminServerURL = getURLManagerService().findAdministrationURL(adminServerName);
      } catch (UnknownHostException var6) {
         String serviceURLString = "<JMXServiceURL:null>";
         JMXLogger.logAdminstrationServerNotAvailable(adminServerName, serviceURLString);
         adminServerURL = this.getAdminHost();
      }

      this.registerForDisconnects(adminServerURL);
   }

   private String getAdminHost() {
      return this.convertURL(ManagementService.getPropertyService(kernelId).getAdminHost());
   }

   private String convertURL(String url) {
      if (url.indexOf("://") >= 0) {
         if (!Boolean.getBoolean("weblogic.management.failover.useSpecifiedProtocol") && url.startsWith("http")) {
            url = "t3" + url.substring(4);
         }
      } else {
         url = "t3://" + url;
      }

      return url;
   }

   private void registerForDisconnects(String adminServerURL) {
      List addedHosts = new ArrayList();
      List envList = new ArrayList();
      Environment env = new Environment();
      env.setProviderUrl(adminServerURL);
      env.setProperty("weblogic.rmi.clientTimeout", getInternalConnectionTimeout());
      env.setProperty("weblogic.jndi.requestTimeout", getInternalConnectionTimeout());
      envList.add(env);
      addedHosts.add(this.getHostFromURL(adminServerURL));
      if (debug.isDebugEnabled()) {
         debug.debug("In register for disconnect : " + adminServerURL);
      }

      String[] adminURLs = URLManager.findAllAddressesForHost(this.getAdminHost());
      if (adminURLs != null && adminURLs.length > 0) {
         for(int i = 0; i < adminURLs.length; ++i) {
            String newURL = this.convertURL(adminURLs[i]);
            boolean found = false;
            Iterator var9 = addedHosts.iterator();

            while(var9.hasNext()) {
               String host = (String)var9.next();
               if (this.getHostFromURL(newURL).equalsIgnoreCase(host)) {
                  found = true;
                  break;
               }
            }

            if (!found) {
               env = new Environment();
               env.setProviderUrl(newURL);
               env.setProperty("weblogic.rmi.clientTimeout", getInternalConnectionTimeout());
               env.setProperty("weblogic.jndi.requestTimeout", getInternalConnectionTimeout());
               envList.add(env);
               addedHosts.add(this.getHostFromURL(newURL));
               if (debug.isDebugEnabled()) {
                  debug.debug("In register for alternate disconnect : " + newURL);
               }
            }
         }
      }

      try {
         if (!ManagementService.getRuntimeAccess(kernelId).isAdminServer()) {
            List input = (List)ReflectionHelper.cast(envList);
            ConnectMonitorFactory.registerForever(input);
         }
      } catch (Throwable var11) {
         if (debug.isDebugEnabled()) {
            debug.debug("Unable to register disconnect listener : " + adminServerURL);
         }
      }

   }

   private String getHostFromURL(String url) {
      try {
         ServerURL serverURL = new ServerURL(url);
         return serverURL.getHost();
      } catch (Exception var3) {
         if (debug.isDebugEnabled()) {
            debug.debug("Error getting host from URL " + var3.getMessage());
         }

         return url;
      }
   }

   protected boolean doEarly() {
      EnableListenersIfAdminChannelAbsentService s = (EnableListenersIfAdminChannelAbsentService)GlobalServiceLocator.getServiceLocator().getService(EnableListenersIfAdminChannelAbsentService.class, new Annotation[0]);
      return s.isOpenForManagementConnectionsEarly();
   }

   static {
      internalConnectionTimeout = Long.getLong("weblogic.management.internalConnectionTimeout", DEFAULT_INTERNAL_CONNECTION_TIMEOUT);
   }
}
