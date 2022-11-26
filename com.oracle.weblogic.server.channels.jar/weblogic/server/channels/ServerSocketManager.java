package weblogic.server.channels;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.channels.spi.SelectorProvider;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import weblogic.kernel.KernelStatus;
import weblogic.protocol.ServerChannel;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.ServerLogger;

public class ServerSocketManager {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private final HashMap serverSocketsMap = new HashMap();
   private ServerListenThread serverListenThread;
   private static ServerSocketManager SINGLETON;

   private ServerSocketManager() throws IOException {
      this.ensureInitialized();
   }

   public static ServerSocketManager getInstance() {
      return SINGLETON;
   }

   String channelIdFor(List channels) {
      assert channels != null && !channels.isEmpty();

      return ((ServerChannel)channels.get(0)).getListenerKey();
   }

   public void createAndBindAllServerSockets(Collection channelLists) throws IOException {
      this.createAndBindServerSockets(channelLists, true);
   }

   public void createAndBindAnyServerSockets(Collection channelCollection) throws IOException {
      this.createAndBindServerSockets(channelCollection, false);
   }

   private void createAndBindServerSockets(Collection channelCollection, boolean bindAll) throws IOException {
      int started = 0;
      Iterator var4 = channelCollection.iterator();

      while(var4.hasNext()) {
         List discriminantChannelList = (List)var4.next();

         try {
            this.createBindAndEnableServerSocket(discriminantChannelList);
            ++started;
         } catch (IOException var10) {
            String channelName = ((ServerChannel)discriminantChannelList.get(0)).getChannelName();
            if (bindAll) {
               throw new IOException(ServerLogger.getAdminChannelFailure(channelName), var10);
            }

            InetAddress listenAddress = ((ServerChannel)discriminantChannelList.get(0)).getInetAddress();
            int port = ((ServerChannel)discriminantChannelList.get(0)).getPort();
            if (ListenThreadDebugLogger.isDebugEnabled()) {
               ListenThreadDebugLogger.debug("Can not bind server socket for channel " + channelName + " to" + (listenAddress == null ? "IP_ANY" : listenAddress.getHostAddress()) + ":" + port, var10);
            }

            ServerLogger.logUnableToCreateSocket(listenAddress == null ? "IP_ANY" : listenAddress.getHostAddress(), port, var10, channelName);
         }
      }

      if (started == 0) {
         throw new IOException(ServerLogger.getBindFailure());
      } else {
         if (started < channelCollection.size()) {
            ServerLogger.logListenPortsNotOpenTotally();
         }

      }
   }

   void createBindAndEnableServerSocket(List discriminantChannelList) throws IOException {
      ServerSocketWrapper serverSocketWrapper = this.createServerSocketWrapper(discriminantChannelList);
      serverSocketWrapper.bind();
      this.serverSocketsMap.put(this.channelIdFor(discriminantChannelList), serverSocketWrapper);
      this.serverListenThread.registerForAccept(serverSocketWrapper);
   }

   private ServerSocketWrapper createServerSocketWrapper(List channelList) throws IOException {
      Collections.sort(channelList);
      ServerChannel[] channels = (ServerChannel[])channelList.toArray(new ServerChannel[0]);
      return (ServerSocketWrapper)(!channels[0].supportsTLS() ? new ServerSocketWrapper(channels) : new ServerSocketWrapperJSSE(channels));
   }

   void enableServerSockets(Collection channelIds) {
      List socketWrappers = new ArrayList();
      Iterator var3 = channelIds.iterator();

      while(var3.hasNext()) {
         String channelId = (String)var3.next();
         socketWrappers.add(this.serverSocketsMap.get(channelId));
      }

      this.serverListenThread.registerForAccept((Collection)socketWrappers);
   }

   void updateServerSocket(String channelId) throws IOException {
      ServerSocketWrapper serverSocket = (ServerSocketWrapper)this.serverSocketsMap.remove(channelId);
      if (serverSocket != null) {
         this.serverListenThread.close(serverSocket);
      }

      List channels = ChannelService.getDiscriminantSet(channelId);
      if (channels != null && !channels.isEmpty()) {
         serverSocket = this.createServerSocketWrapper(channels);
         serverSocket.bind();
         this.serverSocketsMap.put(channelId, serverSocket);
         this.serverListenThread.registerForAccept(serverSocket);
      }

   }

   void closeServerSocket(String channelId) {
      ServerSocketWrapper serverSocket = (ServerSocketWrapper)this.serverSocketsMap.remove(channelId);
      if (serverSocket != null) {
         this.serverListenThread.close(serverSocket);
      }

   }

   public void createClientListener(ServerChannel sc) {
      if (KernelStatus.isServer()) {
         throw new IllegalStateException("createClientListener() called in a server");
      } else {
         ServerSocketWrapper serverSocket = null;

         try {
            ServerListenThread serverListenThread = new ServerListenThread(SelectorProvider.provider().openSelector());
            serverListenThread.start();
            ArrayList channels = new ArrayList();
            channels.add(sc);
            serverSocket = this.createServerSocketWrapper(channels);
            serverSocket.bind();
            serverListenThread.registerForAccept(serverSocket);
         } catch (IOException var5) {
            InetAddress listenAddress = serverSocket.getListenAddress();
            ServerLogger.logUnableToCreateSocket(listenAddress == null ? "IP_ANY" : listenAddress.getHostAddress(), serverSocket.getLocalPort(), var5, serverSocket.getChannelName());
         }

      }
   }

   synchronized void ensureInitialized() throws IOException {
      if (this.serverListenThread == null) {
         this.serverListenThread = new ServerListenThread(SelectorProvider.provider().openSelector());
         this.serverListenThread.start();
      }
   }

   static {
      try {
         SINGLETON = new ServerSocketManager();
      } catch (IOException var2) {
         RuntimeException re = new RuntimeException(var2.getMessage());
         re.initCause(var2);
         throw re;
      }
   }
}
