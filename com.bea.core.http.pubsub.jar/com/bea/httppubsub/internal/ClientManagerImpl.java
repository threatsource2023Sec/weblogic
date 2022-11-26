package com.bea.httppubsub.internal;

import com.bea.httppubsub.Channel;
import com.bea.httppubsub.Client;
import com.bea.httppubsub.ClientManager;
import com.bea.httppubsub.LocalClient;
import com.bea.httppubsub.PubSubServer;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.work.WorkManagerFactory;

public class ClientManagerImpl implements ClientManager {
   private static final DebugLogger logger = DebugLogger.getDebugLogger("DebugPubSubClient");
   private static final int CLIENT_ID_LENGTH = 15;
   private static final char[] ALPHANUM = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
   private static final int SCAVANGE_INTERVAL_MIN = 2000;
   private static final String TIMEMANGER_PREFIX = "com.bea.httppubsub.clientmanager.timemanager.";
   private final SecureRandom random = new SecureRandom();
   private final ConcurrentHashMap clients = new ConcurrentHashMap();
   private final ConcurrentHashMap browserClientsMap = new ConcurrentHashMap();
   private int scavangeInterval;
   private TimerManager tm = null;
   private int clientTimeout;
   private PubSubServer server;
   private boolean startTimeListener = false;
   private ChannelPersistenceManagerBuilder persistManagerBuilder = null;

   public void init() {
      if (this.server == null) {
         throw new IllegalArgumentException("PubSubServer has not been set.");
      } else {
         if (this.server instanceof RegistrablePubSubServer) {
            ((RegistrablePubSubServer)this.server).registerClientManager(this);
         }

         if (this.server.getContext() != null) {
            this.persistManagerBuilder = this.server.getContext().getChannelPersistManagerBuilder();
         }

         this.clientTimeout = this.server.getClientTimeout();
         this.scavangeInterval = this.clientTimeout / 10;
         if (this.scavangeInterval > 2000) {
            this.scavangeInterval = 2000;
         }

         if (this.startTimeListener) {
            this.startScavenger();
         }

      }
   }

   public void destroy() {
      this.stopScavenger();
      this.clients.clear();
      this.browserClientsMap.clear();
   }

   public void setPubSubServer(PubSubServer server) {
      this.server = server;
   }

   public void setStartTimeListener(boolean startTimeListener) {
      this.startTimeListener = startTimeListener;
   }

   public Client createClient() {
      return this.generate();
   }

   public LocalClient createLocalClient() {
      String clientId = this.generateClientId();
      return new LocalClientImpl(clientId);
   }

   public void addClient(Client client) {
      if (client == null) {
         throw new IllegalArgumentException("Client cannot be null.");
      } else {
         if (logger.isDebugEnabled()) {
            logger.debug("Register client " + client.getId());
         }

         Client c = (Client)this.clients.putIfAbsent(client.getId(), client);
         if (this.server.isMultiFrameSupported()) {
            if (c == null) {
               String browserId = client.getBrowserId();
               if (browserId == null) {
                  return;
               }

               synchronized(this.browserClientsMap) {
                  Set clientIds = (Set)this.browserClientsMap.get(browserId);
                  if (clientIds == null) {
                     clientIds = new HashSet();
                     this.browserClientsMap.put(browserId, clientIds);
                  }

                  ((Set)clientIds).add(client.getId());
                  if (((Set)clientIds).size() > 1) {
                     Iterator var6 = ((Set)clientIds).iterator();

                     while(var6.hasNext()) {
                        String id = (String)var6.next();
                        ((Client)this.clients.get(id)).setMultiFrame(true);
                     }
                  }
               }
            }

         }
      }
   }

   public Client findClient(String clientId) {
      return (Client)this.clients.get(clientId);
   }

   public void removeClient(Client client) {
      if (client == null) {
         throw new IllegalArgumentException("Client cannot be null.");
      } else {
         if (logger.isDebugEnabled()) {
            logger.debug("Unregister client " + client.getId());
         }

         this.clients.remove(client.getId());
         if (this.server.isMultiFrameSupported()) {
            String browserId = client.getBrowserId();
            if (browserId != null) {
               synchronized(this.browserClientsMap) {
                  Set clientIds = (Set)this.browserClientsMap.get(browserId);
                  if (clientIds != null) {
                     clientIds.remove(client.getId());
                     if (clientIds.size() == 0) {
                        this.browserClientsMap.remove(browserId);
                     }

                     if (clientIds.size() == 1) {
                        Iterator var5 = clientIds.iterator();

                        while(var5.hasNext()) {
                           String id = (String)var5.next();
                           ((Client)this.clients.get(id)).setMultiFrame(false);
                        }
                     }
                  }

               }
            }
         }
      }
   }

   void startScavenger() {
      TimerListener tunscav = new TimerListener() {
         public void timerExpired(Timer timer) {
            long now = System.currentTimeMillis();
            Iterator var4 = ClientManagerImpl.this.clients.entrySet().iterator();

            while(var4.hasNext()) {
               Map.Entry entry = (Map.Entry)var4.next();
               InternalClient client = (InternalClient)entry.getValue();
               if (!client.hasTransportPending() && client.getLastAccessTime() + (long)ClientManagerImpl.this.clientTimeout < now) {
                  this.doDisconnect(client);
               }
            }

         }

         private void doDisconnect(Client client) {
            if (ClientManagerImpl.logger.isDebugEnabled()) {
               ClientManagerImpl.logger.debug("Disconnect client " + client.getId());
            }

            boolean hasPersistentSubscription = false;
            Iterator var3 = client.getChannelSubscriptions().iterator();

            while(var3.hasNext()) {
               String channelUrl = (String)var3.next();
               Channel channel = ClientManagerImpl.this.server.findChannel(channelUrl);
               if (channel != null && channel.isPersistentChannel() || ClientManagerImpl.this.persistManagerBuilder != null && ClientManagerImpl.this.persistManagerBuilder.containsPersistenceChannel(channelUrl)) {
                  hasPersistentSubscription = true;
               }

               if (channel != null) {
                  channel.unsubscribe(client);
               } else if (ClientManagerImpl.logger.isDebugEnabled()) {
                  ClientManagerImpl.logger.debug("unsubscribe client " + client.getId() + " from channel " + channelUrl + ", but cannot find the channel from server");
               }
            }

            if (hasPersistentSubscription && ClientManagerImpl.this.server instanceof PubSubServerImpl) {
               ClientPersistenceManager cmp = ((PubSubServerImpl)ClientManagerImpl.this.server).getClientPersistenceManager();
               cmp.storeClientRecord(client);
            }

            ClientManagerImpl.this.removeClient(client);
         }
      };
      this.tm = TimerManagerFactory.getTimerManagerFactory().getTimerManager("com.bea.httppubsub.clientmanager.timemanager." + this.server.getName(), WorkManagerFactory.getInstance().getSystem());
      this.tm.scheduleAtFixedRate(tunscav, 0L, (long)this.scavangeInterval);
   }

   private void stopScavenger() {
      if (!this.tm.isStopped()) {
         this.tm.stop();
      }

   }

   private Client generate() {
      String clientId = this.generateClientId();
      return new ClientImpl(clientId);
   }

   private String generateClientId() {
      StringBuffer result = new StringBuffer();

      for(int i = 0; i < 15; ++i) {
         result.append(ALPHANUM[this.random.nextInt(ALPHANUM.length)]);
      }

      return result.toString();
   }

   public Map getClients() {
      return this.clients;
   }
}
