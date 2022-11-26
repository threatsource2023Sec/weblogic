package weblogic.deploy.service.internal.transport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import weblogic.management.deploy.internal.DeploymentManagerLogger;
import weblogic.protocol.ConnectMonitorFactory;
import weblogic.rmi.extensions.ConnectEvent;
import weblogic.rmi.extensions.ConnectListener;
import weblogic.rmi.extensions.ConnectMonitor;
import weblogic.rmi.extensions.DisconnectEvent;
import weblogic.rmi.extensions.DisconnectListener;
import weblogic.rmi.extensions.ServerDisconnectEvent;
import weblogic.work.WorkManagerFactory;

public class ServerDisconnectManager {
   private static final long DISCONNECT_TIMEOUT = (long)Integer.getInteger("weblogic.deployment.serverDisconnectTimeout", 0) * 1000L;
   private ServerConnectDisconnectListenerImpl connectDisconnectListener;

   public static ServerDisconnectManager getInstance() {
      return ServerDisconnectManager.Maker.SINGLETON;
   }

   private ServerDisconnectManager() {
      this.connectDisconnectListener = null;
   }

   public void initialize() {
      this.connectDisconnectListener = new ServerConnectDisconnectListenerImpl();
      ConnectMonitor connectionMonitor = ConnectMonitorFactory.getConnectMonitor();
      connectionMonitor.addConnectDisconnectListener(this.connectDisconnectListener, this.connectDisconnectListener);
   }

   public ServerDisconnectListener findOrCreateDisconnectListener(String serverName) {
      ServerDisconnectListener listener = this.findDisconnectListener(serverName);
      if (listener == null) {
         synchronized(this) {
            listener = this.findDisconnectListener(serverName);
            if (listener == null) {
               listener = this.createListener(serverName);
               this.connectDisconnectListener.registerListener(serverName, listener);
               return listener;
            }
         }
      }

      return listener;
   }

   public ServerDisconnectListener findDisconnectListener(String server) {
      return this.connectDisconnectListener.getRegisteredListener(server);
   }

   public void removeDisconnectListener(String server) {
      this.connectDisconnectListener.unregisterListener(server);
   }

   public void removeAll() {
      this.connectDisconnectListener.unregisterAll();
   }

   private ServerDisconnectListener createListener(String server) {
      return new ServerDisconnectListenerImpl(server);
   }

   // $FF: synthetic method
   ServerDisconnectManager(Object x0) {
      this();
   }

   private class ServerConnectDisconnectListenerImpl implements DisconnectListener, ConnectListener {
      private Map disconnectListeners;

      private ServerConnectDisconnectListenerImpl() {
         this.disconnectListeners = new HashMap();
      }

      public void onDisconnect(DisconnectEvent event) {
         if (event instanceof ServerDisconnectEvent) {
            String server = ((ServerDisconnectEvent)event).getServerName();
            this.handleOnDisconnectEvent(server, event);
         }
      }

      public void onConnect(ConnectEvent event) {
         String serverName = event.getServerName();
         this.handleOnConnectEvent(serverName, event);
      }

      private void handleOnConnectEvent(final String server, ConnectEvent event) {
         WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
            public void run() {
               ServerDisconnectListener listener = ServerConnectDisconnectListenerImpl.this.getRegisteredListener(server);
               if (listener != null) {
                  ((ServerDisconnectListenerImpl)listener).setConnected(true);
               } else {
                  listener = ServerDisconnectManager.this.createListener(server);
                  ServerConnectDisconnectListenerImpl.this.registerListener(server, listener);
               }

            }
         });
      }

      private void handleOnDisconnectEvent(final String server, final DisconnectEvent event) {
         WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
            public void run() {
               ServerDisconnectListener listener = ServerConnectDisconnectListenerImpl.this.getRegisteredListener(server);
               if (listener != null) {
                  ((ServerDisconnectListenerImpl)listener).setConnected(false);
                  listener.onDisconnect(event);
               }

            }
         });
      }

      private void unregisterAll() {
         synchronized(this) {
            this.disconnectListeners.clear();
         }
      }

      private void registerListener(String server, ServerDisconnectListener listener) {
         synchronized(this) {
            this.disconnectListeners.put(server, listener);
         }
      }

      private ServerDisconnectListener getRegisteredListener(String server) {
         synchronized(this) {
            return (ServerDisconnectListener)this.disconnectListeners.get(server);
         }
      }

      private void unregisterListener(String server) {
         synchronized(this) {
            this.disconnectListeners.remove(server);
         }
      }

      public String toString() {
         StringBuffer sb = new StringBuffer();
         sb.append(super.toString()).append("(");
         sb.append("disconnectListeners=").append(this.disconnectListeners);
         sb.append(")");
         return sb.toString();
      }

      // $FF: synthetic method
      ServerConnectDisconnectListenerImpl(Object x1) {
         this();
      }
   }

   private class ServerDisconnectListenerImpl implements ServerDisconnectListener {
      private String serverName;
      private boolean connected;
      private ArrayList allListeners;

      private ServerDisconnectListenerImpl(String server) {
         this.serverName = null;
         this.connected = true;
         this.allListeners = new ArrayList();
         this.serverName = server;
      }

      public void registerListener(DisconnectListener listener) {
         synchronized(this.allListeners) {
            this.allListeners.add(listener);
         }
      }

      public void unregisterListener(DisconnectListener listener) {
         synchronized(this.allListeners) {
            this.allListeners.remove(listener);
         }
      }

      public void onDisconnect(DisconnectEvent event) {
         if (!this.allListeners.isEmpty()) {
            if (!this.isReconnected()) {
               Iterator listeners = ((ArrayList)this.allListeners.clone()).iterator();

               while(listeners.hasNext()) {
                  DisconnectListener eachLis = (DisconnectListener)listeners.next();

                  try {
                     eachLis.onDisconnect(event);
                  } catch (Throwable var5) {
                     DeploymentManagerLogger.logDisconnectListenerError(eachLis.toString(), var5);
                  }
               }

               this.allListeners.clear();
            }
         }
      }

      protected synchronized void setConnected(boolean connected) {
         this.connected = connected;
         this.notify();
      }

      protected synchronized boolean isConnected() {
         return this.connected;
      }

      protected synchronized boolean isReconnected() {
         long totalTimeout = ServerDisconnectManager.DISCONNECT_TIMEOUT;
         long waitInterval = 5000L;
         long endTime = System.currentTimeMillis() + totalTimeout;

         while(true) {
            long timeRemaining = endTime - System.currentTimeMillis();
            if (totalTimeout == 0L || this.connected || timeRemaining <= 0L) {
               return this.connected;
            }

            long currentWaitTime = timeRemaining <= 5000L ? timeRemaining : 5000L;

            try {
               this.wait(currentWaitTime);
            } catch (InterruptedException var12) {
            }
         }
      }

      public String toString() {
         StringBuffer sb = new StringBuffer();
         sb.append(super.toString()).append("(");
         sb.append("Server = ").append(this.serverName).append(", ");
         sb.append("listeners = ").append(this.allListeners).append(")");
         return sb.toString();
      }

      // $FF: synthetic method
      ServerDisconnectListenerImpl(String x1, Object x2) {
         this(x1);
      }
   }

   static class Maker {
      private static final ServerDisconnectManager SINGLETON = new ServerDisconnectManager();
   }
}
