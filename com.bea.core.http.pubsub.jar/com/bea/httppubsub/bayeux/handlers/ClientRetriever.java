package com.bea.httppubsub.bayeux.handlers;

import com.bea.httppubsub.Channel;
import com.bea.httppubsub.Client;
import com.bea.httppubsub.ClientManager;
import com.bea.httppubsub.PubSubServer;
import com.bea.httppubsub.PubSubServerException;
import com.bea.httppubsub.descriptor.ChannelBean;
import com.bea.httppubsub.descriptor.WeblogicPubsubBean;
import com.bea.httppubsub.internal.ChannelId;
import com.bea.httppubsub.internal.RegistrablePubSubServer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpSession;
import weblogic.diagnostics.debug.DebugLogger;

public class ClientRetriever {
   private static final DebugLogger logger = DebugLogger.getDebugLogger("DebugPubSubClient");
   private final PubSubServer server;
   private final List persistentChannles;

   public ClientRetriever(PubSubServer server) {
      if (server == null) {
         throw new IllegalArgumentException("PubSubServer cannot be null.");
      } else {
         this.server = server;
         this.persistentChannles = this.initializePersistentChannels(server);
      }
   }

   public Client retrieveClientFromPubSubServer(String clientId) throws PubSubServerException {
      Client client = this.retrieveClientFromServer(clientId);
      if (client != null) {
         if (logger.isDebugEnabled()) {
            logger.debug("Found client [ " + clientId + " ] from server");
         }

         return client;
      } else {
         return null;
      }
   }

   public Client retrieveClientFromHttpSession(HttpSession httpSession) throws PubSubServerException {
      Client client = this.retrieveClientFromSession(httpSession);
      if (client != null) {
         client = this.restoreClient(client);
         if (client != null) {
            this.server.getClientManager().addClient(client);
            if (logger.isDebugEnabled()) {
               logger.debug("Found client [ " + client.getId() + " ] from session");
            }
         }

         return client;
      } else {
         return null;
      }
   }

   private List initializePersistentChannels(PubSubServer server) {
      List result = new ArrayList();
      if (!(server instanceof RegistrablePubSubServer)) {
         return result;
      } else {
         WeblogicPubsubBean configuration = ((RegistrablePubSubServer)server).getConfiguration();
         ChannelBean[] channelBeans = configuration.getChannels();
         if (channelBeans != null && channelBeans.length != 0) {
            ChannelBean[] var5 = configuration.getChannels();
            int var6 = var5.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               ChannelBean channelBean = var5[var7];
               if (channelBean.getChannelPersistence() != null) {
                  result.add(ChannelId.newInstance(channelBean.getChannelPattern()));
               }
            }

            return result;
         } else {
            return result;
         }
      }
   }

   private Client retrieveClientFromServer(String clientId) {
      ClientManager clientManager = this.server.getClientManager();
      return clientManager.findClient(clientId);
   }

   private Client retrieveClientFromSession(HttpSession session) {
      return session == null ? null : (Client)session.getAttribute("Client_In_Http_Session");
   }

   private Client restoreClient(Client client) throws PubSubServerException {
      if (client == null) {
         return null;
      } else {
         long currentTime = System.currentTimeMillis();
         if (currentTime - client.getLastAccessTime() >= (long)this.server.getClientTimeout() && currentTime - client.getLastAccessTime() < (long)this.server.getPersistentClientTimeout()) {
            if (!this.containsPersistentChannel(client)) {
               return null;
            } else {
               this.subscribeChannels(client, true);
               return client;
            }
         } else if (currentTime - client.getLastAccessTime() >= (long)this.server.getPersistentClientTimeout()) {
            return null;
         } else {
            this.subscribeChannels(client, false);
            return client;
         }
      }
   }

   private boolean containsPersistentChannel(Client client) throws PubSubServerException {
      boolean result = false;
      Set channelSubscriptions = client.getChannelSubscriptions();
      Iterator var4 = channelSubscriptions.iterator();

      while(true) {
         String url;
         do {
            if (!var4.hasNext()) {
               return result;
            }

            url = (String)var4.next();
         } while(url.startsWith("/meta/"));

         Channel channel = this.server.findOrCreateChannel(client, url);
         if (channel.isPersistentChannel()) {
            return true;
         }

         Iterator var7 = this.persistentChannles.iterator();

         while(var7.hasNext()) {
            ChannelId cid = (ChannelId)var7.next();
            if (ChannelId.newInstance(url).contains(cid)) {
               return true;
            }
         }
      }
   }

   private void subscribeChannels(Client client, boolean onlyPersistent) throws PubSubServerException {
      Set channelSubscriptions = client.getChannelSubscriptions();
      Iterator var4 = channelSubscriptions.iterator();

      while(true) {
         String url;
         do {
            if (!var4.hasNext()) {
               return;
            }

            url = (String)var4.next();
         } while(url.startsWith("/meta/"));

         List channelsForSubscribe = new ArrayList();
         if (onlyPersistent) {
            Channel channel = this.server.findOrCreateChannel(client, url);
            if (channel.isPersistentChannel()) {
               channelsForSubscribe.add(url);
            } else {
               Iterator var8 = this.persistentChannles.iterator();

               while(var8.hasNext()) {
                  ChannelId cid = (ChannelId)var8.next();
                  if (ChannelId.newInstance(url).contains(cid)) {
                     channelsForSubscribe.add(cid.toUrl());
                  }
               }
            }
         } else {
            channelsForSubscribe.add(url);
         }

         if (!channelsForSubscribe.isEmpty()) {
            this.subscribeSpecificChannel(client, channelsForSubscribe);
         }
      }
   }

   private void subscribeSpecificChannel(Client client, List urls) throws PubSubServerException {
      Iterator var3 = urls.iterator();

      while(var3.hasNext()) {
         String url = (String)var3.next();
         ChannelId cid = ChannelId.newInstance(url);
         Channel targetChannel = this.server.findOrCreateChannel(client, cid.toUrl());
         targetChannel.subscribe(client, cid.getChannelPattern());
      }

   }
}
