package weblogic.server.channels;

import java.io.IOException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.provider.ManagementService;
import weblogic.protocol.ProtocolHandlerAdmin;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.configuration.ChannelHelper;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.AbstractServerService;
import weblogic.server.ServiceFailureException;
import weblogic.t3.srvr.AdminPortLifeCycleService;

@Service
@Named
@RunLevel(10)
public class AdminPortService extends AbstractServerService implements AdminPortLifeCycleService {
   private List adminChannelIds = new ArrayList();
   private static AdminPortService singleton;
   private boolean serverSocketsBound = false;
   private static final String ADMIN_PORT_PREPEND = "<AdminPortService>: ";

   private static void setSingleton(AdminPortService oneOnly) {
      singleton = oneOnly;
   }

   public AdminPortService() {
      setSingleton(this);
   }

   public void start() throws ServiceFailureException {
      AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      if (ManagementService.getRuntimeAccess(kernelId).getServer().getListenersBindEarly()) {
         this.createAndBindServerSockets();
      }

   }

   public synchronized void createAndBindServerSockets() throws ServiceFailureException {
      if (!this.serverSocketsBound) {
         if (ChannelHelper.isLocalAdminChannelEnabled()) {
            if (this.isDebugEnabled()) {
               p("start()");
            }

            try {
               if (this.isDebugEnabled()) {
                  ChannelService.dumpTables();
               }

               List adminChannels = ChannelService.findInboundServerChannels(ProtocolHandlerAdmin.PROTOCOL_ADMIN);
               if (this.isDebugEnabled()) {
                  p("admin channels: " + adminChannels);
               }

               Collection discriminantChannelsList = new ArrayList();
               Iterator var3 = adminChannels.iterator();

               while(true) {
                  if (!var3.hasNext()) {
                     ServerSocketManager.getInstance().createAndBindAllServerSockets(discriminantChannelsList);
                     break;
                  }

                  ServerChannel sc = (ServerChannel)var3.next();
                  List channelsList = ChannelService.getDiscriminantSet(sc.getListenerKey());
                  discriminantChannelsList.add(channelsList);
                  this.adminChannelIds.add(sc.getListenerKey());
               }
            } catch (IOException var6) {
               throw new ServiceFailureException(var6);
            }

            this.serverSocketsBound = true;
         } else if (this.isDebugEnabled()) {
            p("start() skipped - no admin channels");
         }

      }
   }

   public void closeServerSocket(ServerChannel sc) throws IOException {
      String channelId = sc.getListenerKey();
      this.adminChannelIds.remove(channelId);
      ServerSocketManager.getInstance().closeServerSocket(channelId);
   }

   public void addServerSocket(List channels) throws IOException {
      if (channels != null && !channels.isEmpty()) {
         ServerSocketManager.getInstance().createBindAndEnableServerSocket(channels);
         this.adminChannelIds.add(ServerSocketManager.getInstance().channelIdFor(channels));
      }

   }

   public void updateServerSocket(ServerChannel sc) throws IOException {
      String channelId = sc.getListenerKey();
      this.adminChannelIds.remove(channelId);
      ServerSocketManager.getInstance().updateServerSocket(sc.getListenerKey());
      this.adminChannelIds.add(channelId);
   }

   public void enableServerSockets() throws ServiceFailureException {
      if (this.isDebugEnabled()) {
         p("enableServerSockets()");
      }

      if (!this.serverSocketsBound) {
         this.createAndBindServerSockets();
      }

      ServerSocketManager.getInstance().enableServerSockets(this.adminChannelIds);
   }

   public void stop() {
      this.halt();
   }

   public synchronized void halt() {
      if (this.isDebugEnabled()) {
         p("halt()");
      }

      Iterator var1 = this.adminChannelIds.iterator();

      while(var1.hasNext()) {
         String channelId = (String)var1.next();
         ServerSocketManager.getInstance().closeServerSocket(channelId);
      }

      this.serverSocketsBound = false;
   }

   public synchronized boolean isServerSocketsBound() {
      return this.serverSocketsBound;
   }

   private boolean isDebugEnabled() {
      return ListenThreadDebugLogger.isDebugEnabled();
   }

   private static void p(String s) {
      ListenThreadDebugLogger.debug("<AdminPortService>: " + s);
   }
}
