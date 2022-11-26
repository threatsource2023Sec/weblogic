package weblogic.security.utils;

import java.security.AccessController;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.core.base.api.AdminServerStatusService;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.protocol.ConnectMonitorFactory;
import weblogic.rmi.extensions.ConnectEvent;
import weblogic.rmi.extensions.ConnectListener;
import weblogic.rmi.extensions.ConnectMonitor;
import weblogic.rmi.extensions.DisconnectEvent;
import weblogic.rmi.extensions.DisconnectListener;
import weblogic.rmi.extensions.DisconnectMonitor;
import weblogic.rmi.extensions.DisconnectMonitorListImpl;
import weblogic.rmi.extensions.ServerDisconnectEvent;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.shared.LoggerWrapper;
import weblogic.server.AbstractServerService;
import weblogic.server.RemoteLifeCycleOperations;
import weblogic.server.ServerLifeCycleRuntime;
import weblogic.server.ServiceFailureException;
import weblogic.utils.annotation.Secure;

@Service
@Secure
@Named
@RunLevel(15)
public final class AdminServerListener extends AbstractServerService implements AdminServerStatusService, ConnectListener, DisconnectListener {
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static LoggerWrapper log = LoggerWrapper.getInstance("SecurityRealm");
   private volatile boolean isAdminServerAvailable = true;
   private RemoteLifeCycleOperations remote = null;

   public void start() throws ServiceFailureException {
      if (!ManagementService.getRuntimeAccess(kernelId).isAdminServer()) {
         this.isAdminServerAvailable = ManagementService.getRuntimeAccess(kernelId).isAdminServerAvailable();

         try {
            this.startListening();
         } catch (Exception var2) {
            throw new ServiceFailureException("unable to start listener", var2);
         }

         if (log.isDebugEnabled()) {
            log.debug("Starting AdminServerListener with state: " + this.isAdminServerAvailable);
         }
      }

   }

   public boolean isAdminServerAvailable() {
      if (log.isDebugEnabled()) {
         log.debug("AdminServerListener.isAdminServerAvailable(): " + this.isAdminServerAvailable);
      }

      return this.isAdminServerAvailable;
   }

   private void startListening() throws Exception {
      ConnectMonitor connectionMonitor = ConnectMonitorFactory.getConnectMonitor();
      connectionMonitor.addConnectListener(this);
      if (this.isAdminServerAvailable) {
         RuntimeAccess rt = ManagementService.getRuntimeAccess(kernelId);
         this.startDisconnectListener(rt);
      }

   }

   private void startDisconnectListener(RuntimeAccess rt) throws Exception {
      if (this.remote == null) {
         DisconnectMonitor monitor = DisconnectMonitorListImpl.getDisconnectMonitor();
         this.remote = ServerLifeCycleRuntime.getLifeCycleOperationsRemote(rt.getAdminServerName());
         if (log.isDebugEnabled()) {
            log.debug("AdminServerListener: addDisconnectListener()");
         }

         monitor.addDisconnectListener(this.remote, this);
      }
   }

   private void stopDisconnectListener() {
      if (this.remote != null) {
         DisconnectMonitor monitor = DisconnectMonitorListImpl.getDisconnectMonitor();

         try {
            if (log.isDebugEnabled()) {
               log.debug("AdminServerListener: removeDisconnectListener()");
            }

            monitor.removeDisconnectListener(this.remote, this);
         } catch (Exception var3) {
            if (log.isDebugEnabled()) {
               log.debug("AdminServerListener: removeDisconnectListener() - " + var3.toString(), var3);
            }
         }

         this.remote = null;
      }
   }

   private void setAdminServerAvailable(boolean state) {
      this.isAdminServerAvailable = state;
      if (log.isDebugEnabled()) {
         log.debug("AdminServerListener update to state: " + this.isAdminServerAvailable);
      }

   }

   public synchronized void onConnect(ConnectEvent event) {
      if (log.isDebugEnabled()) {
         log.debug("AdminServerListener.onConnect()");
      }

      RuntimeAccess rt = ManagementService.getRuntimeAccess(kernelId);
      String serverName = event.getServerName();
      if (log.isDebugEnabled()) {
         log.debug("AdminServerListener CONNECT: " + serverName);
      }

      if (serverName.equals(rt.getAdminServerName())) {
         try {
            this.startDisconnectListener(rt);
            this.setAdminServerAvailable(true);
         } catch (Exception var5) {
            this.setAdminServerAvailable(false);
            if (log.isDebugEnabled()) {
               log.debug("AdminServerListener: startDisconnectListener() failed - " + var5.toString(), var5);
            }
         }
      }

   }

   public synchronized void onDisconnect(DisconnectEvent event) {
      if (log.isDebugEnabled()) {
         log.debug("AdminServerListener.onDisconnect()");
      }

      RuntimeAccess rt = ManagementService.getRuntimeAccess(kernelId);
      String serverName = ((ServerDisconnectEvent)event).getServerName();
      if (log.isDebugEnabled()) {
         log.debug("AdminServerListener DISCONNECT: " + serverName);
      }

      if (serverName.equals(rt.getAdminServerName())) {
         this.setAdminServerAvailable(false);
         this.stopDisconnectListener();
      }

   }
}
