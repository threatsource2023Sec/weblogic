package weblogic.management.provider;

import java.lang.annotation.Annotation;
import java.rmi.UnknownHostException;
import java.security.AccessController;
import javax.inject.Inject;
import javax.inject.Provider;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.logging.Loggable;
import weblogic.management.ManagementLogger;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.internal.ConfigLogger;
import weblogic.protocol.ProtocolHandlerAdmin;
import weblogic.protocol.URLManagerService;
import weblogic.protocol.configuration.ChannelHelperService;
import weblogic.rmi.extensions.ConnectEvent;
import weblogic.rmi.extensions.ConnectListener;
import weblogic.rmi.extensions.ConnectMonitor;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.server.ServiceFailureException;

@Service
public class MSIService implements ConnectListener {
   @Inject
   private Provider runtimeAccess;
   @Inject
   private Provider connectMonitor;
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConfigurationRuntime");
   private boolean adminServerAvailable = true;
   private boolean registered = false;
   private String cmdURL = null;
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   private MSIService() {
   }

   public synchronized void doPostParseInitialization(DomainMBean domain) throws ServiceFailureException {
      if (!this.isAdminServerAvailable()) {
         this.cmdURL = ManagementService.getPropertyService(kernelId).getAdminHost();
         String serverName = ManagementService.getPropertyService(kernelId).getServerName();
         ServerMBean server = domain.lookupServer(serverName);
         if (server == null) {
            ServerMBean[] s = domain.getServers();
            String tmp = "{";

            for(int i = 0; i < s.length; ++i) {
               if (i > 0) {
                  tmp = tmp + ",";
               }

               tmp = tmp + s[i].getName();
            }

            tmp = tmp + "}";
            Loggable l = ManagementLogger.logServerNameDoesNotExistLoggable(serverName, tmp);
            l.log();
            throw new ServiceFailureException(l.getMessage());
         } else if (server.isManagedServerIndependenceEnabled() && !Boolean.getBoolean("weblogic.msi.disabled")) {
            ConfigLogger.logStartingIndependentManagerServer();
            ((ConnectMonitor)this.connectMonitor.get()).addConnectListener(this);
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Added connect listener");
            }

         } else {
            Loggable loggable = ConfigLogger.logMSINotEnabledLoggable(serverName);
            loggable.log();
            throw new ServiceFailureException(loggable.getMessage());
         }
      }
   }

   public synchronized void setAdminServerAvailable(boolean available) {
      this.adminServerAvailable = available;
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Set admin server available to " + available);
      }

   }

   public synchronized boolean isAdminServerAvailable() {
      return this.adminServerAvailable;
   }

   private String[] getAllAdminBinaryURLs() {
      String protocol = null;
      String adminBinaryURL = ManagementService.getPropertyService(kernelId).getAdminBinaryURL();
      int idx;
      if (adminBinaryURL != null && (idx = adminBinaryURL.indexOf("://")) != -1) {
         protocol = adminBinaryURL.substring(0, idx);
      }

      String[] adminURLs = ManagementService.getPropertyService(kernelId).getAllAdminHttpUrls();
      if (adminURLs == null) {
         adminURLs = new String[]{adminBinaryURL};
      } else {
         for(int i = 0; i < adminURLs.length; ++i) {
            String url = adminURLs[i];
            String httpProtocol = url.substring(0, url.indexOf("://"));
            adminURLs[i] = protocol != null ? url.replace(httpProtocol + "://", protocol + "://") : getURLManagerService().normalizeToAdminProtocol(url);
         }
      }

      return adminURLs;
   }

   private static URLManagerService getURLManagerService() {
      return (URLManagerService)GlobalServiceLocator.getServiceLocator().getService(URLManagerService.class, new Annotation[0]);
   }

   public boolean isAdminRequiredButNotSpecifiedOnBoot() {
      if (this.isAdminServerAvailable()) {
         return false;
      } else {
         boolean hasAdmin = getChannelHelperService().isAdminServerAdminChannelEnabled();
         String url = this.getAdminURL();
         return !hasAdmin && url != null;
      }
   }

   private String getAdminURL() {
      try {
         RuntimeAccess rt = (RuntimeAccess)this.runtimeAccess.get();
         return getURLManagerService().findURL(rt.getAdminServerName(), ProtocolHandlerAdmin.PROTOCOL_ADMIN);
      } catch (UnknownHostException var2) {
         return null;
      }
   }

   public void onConnect(ConnectEvent event) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("onConnect event, server = " + event.getServerName());
      }

      ManagementService.getPropertyService(kernelId).waitForChannelServiceReady();
      RuntimeAccess rt = (RuntimeAccess)this.runtimeAccess.get();
      if (rt != null) {
         if (event.getServerName().equals(rt.getAdminServerName())) {
            if (this.isAdminRequiredButNotSpecifiedOnBoot()) {
               String adminURL = getURLManagerService().normalizeToHttpProtocol(this.getAdminURL());
               ConfigLogger.logAdminRequiredButNotSpecified(this.cmdURL, adminURL);
            } else {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("onConnect setting admin server available.");
               }

               this.setAdminServerAvailable(true);
               ((ConnectMonitor)this.connectMonitor.get()).removeConnectListener(this);
            }
         }
      }
   }

   private static ChannelHelperService getChannelHelperService() {
      return (ChannelHelperService)GlobalServiceLocator.getServiceLocator().getService(ChannelHelperService.class, new Annotation[0]);
   }
}
