package weblogic.server.channels;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.server.ServerLogger;
import weblogic.socket.MuxableSocket;
import weblogic.socket.ServerSocketMuxer;
import weblogic.socket.ServerThrottle;

class ServerListenThread extends Thread {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final String SOCKET_THREAD_NAME = "weblogic.socket.ServerListenThread";
   private Selector selector;
   private final ArrayList registerList = new ArrayList();

   ServerListenThread(Selector selector) throws IOException {
      super("weblogic.socket.ServerListenThread");
      this.selector = selector;
   }

   private boolean isDebugEnabled() {
      return ListenThreadDebugLogger.isDebugEnabled();
   }

   void close(ServerSocketWrapper serverSocketWrapper) {
      serverSocketWrapper.close();
      this.wakeup();
   }

   void registerForAccept(Collection serverSockets) {
      if (!serverSockets.isEmpty()) {
         synchronized(this.registerList) {
            this.registerList.addAll(serverSockets);
         }

         if (this.isDebugEnabled()) {
            ListenThreadDebugLogger.debug("accept batch of" + serverSockets.size());
         }

         this.wakeup();
      }
   }

   void registerForAccept(ServerSocketWrapper serverSocket) {
      synchronized(this.registerList) {
         this.registerList.add(serverSocket);
      }

      this.wakeup();
   }

   private void wakeup() {
      this.selector.wakeup();
   }

   public void run() {
      this.processSockets();
   }

   private void processSockets() {
      ArrayList al = new ArrayList(0);
      SecurityServiceManager.pushSubject(kernelId, kernelId);

      while(true) {
         while(true) {
            try {
               this.selectFrom(al);
               ArrayList muxableSockets = new ArrayList();

               while(al.size() > 0) {
                  for(int i = al.size() - 1; i >= 0; --i) {
                     if (!ServerThrottle.getServerThrottle().tryAcquirePermit()) {
                        if (muxableSockets.size() != 0) {
                           break;
                        }

                        ServerThrottle.getServerThrottle().acquireSocketPermit();
                     }

                     SelectionKey key = (SelectionKey)al.remove(i);
                     ServerSocketWrapper serverSocket = (ServerSocketWrapper)key.attachment();
                     MuxableSocket ms = serverSocket.acceptForDispatch();
                     if (ms != null) {
                        if (!ServerSocketMuxer.getMuxer().isAsyncMuxer() && ms.getSocket() != null && ms.getSocket().getChannel() != null) {
                           ms.getSocket().getChannel().configureBlocking(true);
                        }

                        if (System.getProperty("ServerListenThread.RegisterCollection") != null) {
                           muxableSockets.add(ms);
                        } else {
                           ServerSocketMuxer.getMuxer().register(ms);
                           ServerSocketMuxer.getMuxer().read(ms);
                        }
                     } else {
                        ServerThrottle.getServerThrottle().decrementOpenSocketCount();
                     }
                  }

                  if (!muxableSockets.isEmpty()) {
                     ServerSocketMuxer.getMuxer().register(muxableSockets);
                     ServerSocketMuxer.getMuxer().read(muxableSockets);
                  }
               }
            } catch (ThreadDeath var7) {
               ServerLogger.logListenThreadFailure(var7);
               SecurityServiceManager.popSubject(kernelId);
               throw var7;
            } catch (Throwable var8) {
               ServerLogger.logUncaughtThrowable(var8);
            }
         }
      }
   }

   private void selectFrom(ArrayList al) throws IOException {
      while(true) {
         if (this.registerList.size() > 0) {
            this.registerNewSockets(this.registerList);
         }

         int nSelected = this.selector.select();
         if (nSelected != 0) {
            if (this.isDebugEnabled()) {
               ListenThreadDebugLogger.debug("select returns " + nSelected + " keys");
            }

            Set sk = this.selector.selectedKeys();
            Iterator var4 = sk.iterator();

            while(var4.hasNext()) {
               SelectionKey k = (SelectionKey)var4.next();
               al.add(k);
            }

            sk.clear();
            if (al.size() != 0) {
               return;
            }
         }
      }
   }

   private void registerNewSockets(ArrayList registerList) {
      ServerSocketWrapper[] curRegisterList;
      int newSocketCount;
      synchronized(registerList) {
         newSocketCount = registerList.size();
         if (newSocketCount == 0) {
            return;
         }

         curRegisterList = (ServerSocketWrapper[])registerList.toArray(new ServerSocketWrapper[newSocketCount]);
         registerList.clear();
      }

      ServerSocketWrapper[] var3 = curRegisterList;
      newSocketCount = curRegisterList.length;

      for(int var5 = 0; var5 < newSocketCount; ++var5) {
         ServerSocketWrapper serverSocket = var3[var5];
         if (serverSocket != null) {
            try {
               serverSocket.registerForAccept(this.selector);
               serverSocket.logOpened();
            } catch (ClosedChannelException var9) {
               if (this.isDebugEnabled()) {
                  ListenThreadDebugLogger.debug("exception registering channeL: " + serverSocket + " with message: " + var9.getMessage() + ". Will remove serverSocket: " + serverSocket);
               }

               InetAddress listenAddress = serverSocket.getListenAddress();
               ServerLogger.logUnableToCreateSocket(listenAddress == null ? "IP_ANY" : listenAddress.getHostAddress(), serverSocket.getLocalPort(), var9, serverSocket.getChannelName());
            }
         }
      }

   }
}
