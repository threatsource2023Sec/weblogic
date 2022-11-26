package weblogic.server.channels;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jvnet.hk2.annotations.Service;
import weblogic.protocol.ProtocolHandlerAdmin;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.configuration.ChannelHelper;
import weblogic.security.SecurityLogger;
import weblogic.server.GlobalServiceLocator;
import weblogic.server.ServerLogger;
import weblogic.server.ServiceFailureException;
import weblogic.t3.srvr.ChannelListenerService;

@Service
public class ChannelListenerManager implements ChannelListenerService {
   private final List serverChannelIds = new ArrayList();

   private ChannelListenerManager() {
   }

   /** @deprecated */
   @Deprecated
   public static ChannelListenerManager getInstance() {
      return ChannelListenerManager.SingletonMaker.SINGLETON;
   }

   public void createAndBindServerSockets() throws ServiceFailureException {
      List discriminantChannelsList = new ArrayList();
      Iterator var2 = ChannelService.getLocalServerChannels().values().iterator();

      while(true) {
         while(var2.hasNext()) {
            List channels = (List)var2.next();
            Iterator var4 = channels.iterator();

            while(var4.hasNext()) {
               ServerChannel sc = (ServerChannel)var4.next();
               if (!((ServerChannelImpl)sc).isImplicitChannel() && sc.getProtocol() != ProtocolHandlerAdmin.PROTOCOL_ADMIN) {
                  this.serverChannelIds.add(ServerSocketManager.getInstance().channelIdFor(channels));
                  discriminantChannelsList.add(channels);
                  break;
               }
            }
         }

         try {
            ServerSocketManager.getInstance().createAndBindAnyServerSockets(discriminantChannelsList);
         } catch (IOException var6) {
            if (!ChannelHelper.isLocalAdminChannelEnabled()) {
               SecurityLogger.logNotInitOnAnyPortInfo();
               ServiceFailureException sfe = new ServiceFailureException();
               sfe.initCause(var6);
               throw sfe;
            }
         }

         ServerLogger.logDynamicListenersEnabled();
         return;
      }
   }

   public void closeServerSockets() {
      Iterator var1 = this.serverChannelIds.iterator();

      while(var1.hasNext()) {
         String channelId = (String)var1.next();
         ServerSocketManager.getInstance().closeServerSocket(channelId);
      }

      this.serverChannelIds.clear();
   }

   public void enableServerSockets() {
      ServerSocketManager.getInstance().enableServerSockets(this.serverChannelIds);
   }

   void addServerSocket(List channels) throws IOException {
      if (channels != null && !channels.isEmpty()) {
         ServerSocketManager.getInstance().createBindAndEnableServerSocket(channels);
         this.serverChannelIds.add(ServerSocketManager.getInstance().channelIdFor(channels));
      }

   }

   void updateServerSocket(ServerChannel sc) throws IOException {
      String channelId = sc.getListenerKey();
      this.serverChannelIds.remove(channelId);
      ServerSocketManager.getInstance().updateServerSocket(channelId);
      this.serverChannelIds.add(channelId);
   }

   void closeServerSocket(ServerChannel sc) {
      String channelId = sc.getListenerKey();
      this.serverChannelIds.remove(channelId);
      ServerSocketManager.getInstance().closeServerSocket(channelId);
   }

   private static class SingletonMaker {
      private static final ChannelListenerManager SINGLETON = (ChannelListenerManager)GlobalServiceLocator.getServiceLocator().getService(ChannelListenerManager.class, new Annotation[0]);
   }
}
