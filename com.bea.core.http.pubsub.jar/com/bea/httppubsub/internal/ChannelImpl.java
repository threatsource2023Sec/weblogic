package com.bea.httppubsub.internal;

import com.bea.httppubsub.BayeuxMessage;
import com.bea.httppubsub.Channel;
import com.bea.httppubsub.Client;
import com.bea.httppubsub.PubSubLogger;
import com.bea.httppubsub.PubSubSecurityException;
import com.bea.httppubsub.bayeux.messages.DeliverEventMessage;
import com.bea.httppubsub.bayeux.messages.EventMessageImpl;
import com.bea.httppubsub.security.ChannelAuthorizationManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.collections.CombinedIterator;

public class ChannelImpl implements InternalChannel {
   private static final DebugLogger logger = DebugLogger.getDebugLogger("DebugPubSubChannel");
   protected String name;
   private String starName;
   private String starStarName;
   protected ChannelEventPublisher eventPublisher;
   private Channel parent;
   private CopyOnWriteArrayList children;
   protected CopyOnWriteArrayList clients;
   protected CopyOnWriteArrayList singleWildClients;
   protected CopyOnWriteArrayList doubleWildClients;
   private AtomicLong publishedMessagesCount;
   private ChannelAuthorizationManager authManager;
   private ChannelPersistenceManager channelPersistenceManager;
   private ChannelPersistenceManagerBuilder cpmBuilder;
   private boolean isPersistentChannel;

   public ChannelImpl(ChannelEventPublisher eventPublisher, String channelName) {
      this.children = new CopyOnWriteArrayList();
      this.clients = new CopyOnWriteArrayList();
      this.singleWildClients = new CopyOnWriteArrayList();
      this.doubleWildClients = new CopyOnWriteArrayList();
      this.publishedMessagesCount = new AtomicLong(0L);
      this.authManager = null;
      this.channelPersistenceManager = NullChannelPersistenceManager.INSTANCE;
      this.cpmBuilder = null;
      this.isPersistentChannel = false;
      this.parent = null;
      this.name = channelName;
      if ("/".equals(this.name)) {
         this.starName = "/*";
         this.starStarName = "/**";
      } else {
         this.starName = this.name + "/*";
         this.starStarName = this.name + "/**";
      }

      this.eventPublisher = eventPublisher;
      if (logger.isDebugEnabled()) {
         logger.debug("Channel " + channelName + " is created.");
      }

   }

   public ChannelImpl(ChannelEventPublisher eventPublisher, Channel parent, String name) {
      this(eventPublisher, name);
      this.parent = parent;
      parent.addSubChannel(this);
   }

   public void setAuthorizationManager(ChannelAuthorizationManager authManager) {
      this.authManager = authManager;
   }

   public ChannelAuthorizationManager getChannelAuthorizationManager() {
      return this.authManager;
   }

   public void setChannelPersistenceManager(ChannelPersistenceManager persistManager) {
      this.channelPersistenceManager = persistManager;
   }

   public ChannelPersistenceManager getChannelPersistenceManager() {
      return this.channelPersistenceManager;
   }

   public ChannelPersistenceManagerBuilder getChannelPersistManBuilder() {
      return this.cpmBuilder;
   }

   public void setChannelPersistManBuilder(ChannelPersistenceManagerBuilder cpmBuilder) {
      this.cpmBuilder = cpmBuilder;
      if (cpmBuilder != null) {
         this.isPersistentChannel = cpmBuilder.isPersistenceChannel(this.name);
      }

   }

   public boolean hasPermission(Client client, Channel.ChannelPattern pattern, ChannelAuthorizationManager.Action operation) {
      switch (pattern) {
         case ITSELF:
            return this.authManager.hasPermission(client, this.getChannelURL(pattern), operation);
         case IMMEDIATE_SUBCHANNELS:
            return this.hasImmediatePermission(client, pattern, operation);
         case ALL_SUBCHANNELS:
            return this.hasRecursivePermission(client, pattern, operation) && this.authManager.hasPermission(client, this.getName() + "/__WL_CHANNEL/__WL_CHANNEL", operation);
         default:
            return true;
      }
   }

   private boolean hasImmediatePermission(Client client, Channel.ChannelPattern pattern, ChannelAuthorizationManager.Action operation) {
      if (this.isMetaChannel()) {
         return true;
      } else {
         Iterator var4 = this.children.iterator();

         Channel child;
         do {
            if (!var4.hasNext()) {
               return this.authManager.hasPermission(client, this.getName() + "/__WL_CHANNEL", operation);
            }

            child = (Channel)var4.next();
         } while(this.authManager.hasPermission(client, child.getName(), operation));

         return false;
      }
   }

   private boolean hasRecursivePermission(Client client, Channel.ChannelPattern pattern, ChannelAuthorizationManager.Action operation) {
      if (this.isMetaChannel()) {
         return true;
      } else if (!this.hasImmediatePermission(client, pattern, operation)) {
         return false;
      } else {
         Iterator var4 = this.children.iterator();

         Channel child;
         do {
            if (!var4.hasNext()) {
               return true;
            }

            child = (Channel)var4.next();
         } while(((ChannelImpl)child).hasPermission(client, Channel.ChannelPattern.getPattern(child.getName() + "/**"), operation));

         return false;
      }
   }

   public String getName() {
      return this.name;
   }

   public Channel getParentChannel() {
      return this.parent;
   }

   public List getSubChannels() {
      return this.children;
   }

   public List getAllSubChannels() {
      List channels = new ArrayList();
      Stack stack = new Stack();
      stack.push(this);

      while(!stack.isEmpty()) {
         Channel ch = (Channel)stack.pop();
         if (ch != this) {
            channels.add(ch);
         }

         Iterator var4 = ch.getSubChannels().iterator();

         while(var4.hasNext()) {
            Channel c = (Channel)var4.next();
            stack.push(c);
         }
      }

      return channels;
   }

   public void addSubChannel(Channel child) {
      this.children.add(child);
   }

   public void removeSubChannel(Channel child) {
      this.children.remove(child);
   }

   public List getClients(Channel.ChannelPattern pattern) {
      switch (pattern) {
         case ITSELF:
            return this.clients;
         case IMMEDIATE_SUBCHANNELS:
            return this.singleWildClients;
         case ALL_SUBCHANNELS:
            return this.doubleWildClients;
         default:
            return Collections.EMPTY_LIST;
      }
   }

   public void destroy(Client client) {
      if (logger.isDebugEnabled()) {
         logger.debug("Channel " + this.name + " is destroyed.");
      }

      Iterator var2 = this.getSubChannels().iterator();

      while(var2.hasNext()) {
         Channel channel = (Channel)var2.next();
         channel.destroy(client);
      }

      if (this.parent != null) {
         this.parent.getSubChannels().remove(this);
      }

      this.parent = null;
      this.eventPublisher = null;
      this.clients.clear();
      this.singleWildClients.clear();
      this.doubleWildClients.clear();
      this.children.clear();
      if (this.channelPersistenceManager != null) {
         this.channelPersistenceManager.destory();
      }

   }

   public void subscribe(Client client) throws PubSubSecurityException {
      this.subscribe(client, Channel.ChannelPattern.ITSELF);
   }

   public void subscribe(Client client, Channel.ChannelPattern pattern) throws PubSubSecurityException {
      if (logger.isDebugEnabled()) {
         logger.debug(client + " subscribes to channel " + this.name + pattern.toString());
      }

      String url = this.getChannelURL(pattern);
      if (this.authManager != null && !this.hasPermission(client, pattern, ChannelAuthorizationManager.Action.SUBSCRIBE)) {
         String username = "null";
         if (client != null && client.getAuthenticatedUser() != null) {
            username = client.getAuthenticatedUser().getUserName();
         }

         throw new PubSubSecurityException(PubSubLogger.logClientHasNoPermissionSubscribeLoggable(username, url).getMessageText());
      } else {
         ((InternalClient)client).addSubscribedChannel(url);
         switch (pattern) {
            case ITSELF:
               if (!this.clients.contains(client)) {
                  this.clients.add(client);
               }
               break;
            case IMMEDIATE_SUBCHANNELS:
               Set subscriptions = client.getChannelSubscriptions();
               if ((subscriptions == null || !subscriptions.contains(this.getChannelURL(Channel.ChannelPattern.ALL_SUBCHANNELS))) && !this.singleWildClients.contains(client)) {
                  this.singleWildClients.add(client);
               }
               break;
            case ALL_SUBCHANNELS:
               this.singleWildClients.remove(client);
               if (!this.doubleWildClients.contains(client)) {
                  this.doubleWildClients.add(client);
               }
         }

      }
   }

   public void unsubscribe(Client client) {
      this.unsubscribe(client, Channel.ChannelPattern.ITSELF);
      this.unsubscribe(client, Channel.ChannelPattern.IMMEDIATE_SUBCHANNELS);
      this.unsubscribe(client, Channel.ChannelPattern.ALL_SUBCHANNELS);
   }

   public void unsubscribe(Client client, Channel.ChannelPattern pattern) {
      if (logger.isDebugEnabled()) {
         logger.debug(client + " unsubscribes from channel " + this.getChannelURL(pattern));
      }

      switch (pattern) {
         case ITSELF:
            this.clients.remove(client);
            break;
         case IMMEDIATE_SUBCHANNELS:
            this.singleWildClients.remove(client);
            break;
         case ALL_SUBCHANNELS:
            this.doubleWildClients.remove(client);
      }

      ((InternalClient)client).removeSubscribedChannel(this.getChannelURL(pattern));
   }

   public void publish(Client client, BayeuxMessage message, Channel.ChannelPattern pattern) throws PubSubSecurityException {
      String url = this.getChannelURL(pattern);
      if (this.authManager != null && !this.hasPermission(client, pattern, ChannelAuthorizationManager.Action.PUBLISH)) {
         String username = "null";
         if (client != null && client.getAuthenticatedUser() != null) {
            username = client.getAuthenticatedUser().getUserName();
         }

         throw new PubSubSecurityException(PubSubLogger.logClientHasNoPermissionPublishLoggable(username, url).getMessageText());
      } else {
         this.publish(message, pattern);
      }
   }

   public void publish(BayeuxMessage message, Channel.ChannelPattern pattern) throws PubSubSecurityException {
      if (logger.isDebugEnabled()) {
         logger.debug("Message " + ((BayeuxMessage)message).toJSONResponseString() + " is published to channel " + this.name + pattern.toString());
      }

      if (message instanceof EventMessageImpl) {
         message = new DeliverEventMessage((EventMessageImpl)message);
      }

      this.handleChannelPersistence((BayeuxMessage)message, pattern);
      boolean wildPublish = pattern != Channel.ChannelPattern.ITSELF;
      List iters = new ArrayList(3);
      if (this.clients.size() != 0 && !wildPublish) {
         iters.add(this.clients.iterator());
      }

      if (this.parent != null) {
         List parentClients = this.parent.getClients(Channel.ChannelPattern.IMMEDIATE_SUBCHANNELS);
         if (parentClients.size() != 0 && !wildPublish) {
            iters.add(parentClients.iterator());
         }

         parentClients = this.parent.getClients(Channel.ChannelPattern.ALL_SUBCHANNELS);
         if (parentClients.size() != 0) {
            iters.add(parentClients.iterator());
         }

         for(Channel grandParent = this.parent.getParentChannel(); grandParent != null; grandParent = grandParent.getParentChannel()) {
            List grandParentClients = grandParent.getClients(Channel.ChannelPattern.ALL_SUBCHANNELS);
            if (grandParentClients.size() != 0) {
               iters.add(grandParentClients.iterator());
            }
         }
      }

      List subChannels = pattern == Channel.ChannelPattern.IMMEDIATE_SUBCHANNELS ? this.children : (pattern == Channel.ChannelPattern.ALL_SUBCHANNELS ? this.getAllSubChannels() : Collections.EMPTY_LIST);
      Iterator var12 = ((List)subChannels).iterator();

      while(var12.hasNext()) {
         Channel channel = (Channel)var12.next();
         List clients = channel.getClients(Channel.ChannelPattern.ITSELF);
         if (clients.size() != 0) {
            iters.add(clients.iterator());
         }

         if (pattern == Channel.ChannelPattern.ALL_SUBCHANNELS) {
            List singleWildClients = channel.getClients(Channel.ChannelPattern.IMMEDIATE_SUBCHANNELS);
            List doubleWildClients = channel.getClients(Channel.ChannelPattern.ALL_SUBCHANNELS);
            if (singleWildClients.size() != 0) {
               iters.add(singleWildClients.iterator());
            }

            if (doubleWildClients.size() != 0) {
               iters.add(doubleWildClients.iterator());
            }
         }
      }

      if (wildPublish) {
         if (this.singleWildClients.size() != 0) {
            iters.add(this.singleWildClients.iterator());
         }

         if (this.doubleWildClients.size() != 0) {
            iters.add(this.doubleWildClients.iterator());
         }
      }

      Iterator it = new CombinedIterator((Iterator[])iters.toArray(new Iterator[iters.size()]));
      ChannelEvent event = new ChannelEvent((BayeuxMessage)message, it);
      this.eventPublisher.notifyClients(event);
      this.publishedMessagesCount.incrementAndGet();
   }

   private void handleChannelPersistence(BayeuxMessage message, Channel.ChannelPattern pattern) {
      if (message instanceof DeliverEventMessage) {
         DeliverEventMessage eventMessage = (DeliverEventMessage)message;
         if (this.isPersistentChannel) {
            this.channelPersistenceManager.storeEvent(eventMessage);
         } else if (pattern != Channel.ChannelPattern.ITSELF && this.cpmBuilder != null && this.cpmBuilder.hasPersistenceChannel()) {
            List cpmList = this.cpmBuilder.getContainedCPMList(eventMessage.getChannel());
            Iterator var5 = cpmList.iterator();

            while(var5.hasNext()) {
               ChannelPersistenceManager cpm = (ChannelPersistenceManager)var5.next();
               cpm.storeEvent(eventMessage);
            }
         }

      }
   }

   private String getChannelURL(Channel.ChannelPattern pattern) {
      String url = null;
      switch (pattern) {
         case ITSELF:
            url = this.name;
            break;
         case IMMEDIATE_SUBCHANNELS:
            url = this.starName;
            break;
         case ALL_SUBCHANNELS:
            url = this.starStarName;
      }

      return url;
   }

   public long getPublishedMessageCount() {
      return this.publishedMessagesCount.get();
   }

   public boolean isMetaChannel() {
      return this.name.equals("/meta") || this.name.startsWith("/meta/");
   }

   public boolean isServiceChannel() {
      return this.name.equals("/service") || this.name.startsWith("/service/");
   }

   public boolean isPersistentChannel() {
      return this.isPersistentChannel;
   }

   public int getPersistentMessageCount() {
      return this.channelPersistenceManager.getMessageCount();
   }
}
