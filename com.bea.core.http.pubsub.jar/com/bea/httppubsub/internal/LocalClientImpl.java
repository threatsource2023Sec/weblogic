package com.bea.httppubsub.internal;

import com.bea.httppubsub.Client;
import com.bea.httppubsub.DeliveredMessageEvent;
import com.bea.httppubsub.DeliveredMessageListener;
import com.bea.httppubsub.LocalClient;
import com.bea.httppubsub.Transport;
import com.bea.httppubsub.bayeux.messages.DeliverEventMessage;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;
import weblogic.diagnostics.debug.DebugLogger;

public class LocalClientImpl extends LocalClient implements InternalClient, Client {
   private static final DebugLogger logger = DebugLogger.getDebugLogger("DebugPubSubClient");
   private static final long serialVersionUID = -3404510111582395051L;
   private String clientId;
   private long lastAccessTime;
   private AtomicLong publishedMessagesCount = new AtomicLong(0L);
   private boolean active = true;
   private final Set subscribedChannels;
   private final Queue listeners = new ConcurrentLinkedQueue();

   protected LocalClientImpl(String clientId) {
      if (logger.isDebugEnabled()) {
         logger.debug("Local client [ " + clientId + " ] created");
      }

      this.clientId = clientId;
      this.subscribedChannels = new LinkedHashSet();
   }

   public void addSubscribedChannel(String channel) {
      synchronized(this.subscribedChannels) {
         this.subscribedChannels.add(channel);
      }
   }

   public void addSubscribedMessage(DeliverEventMessage message) {
      Iterator var2 = this.listeners.iterator();

      while(var2.hasNext()) {
         DeliveredMessageListener listener = (DeliveredMessageListener)var2.next();
         listener.onPublish(new DeliveredMessageEvent(this, message));
      }

   }

   public void registerMessageListener(DeliveredMessageListener listener) {
      this.listeners.add(listener);
   }

   public void unregisterMessageListener(DeliveredMessageListener listener) {
      this.listeners.remove(listener);
   }

   public boolean isConnected() {
      return true;
   }

   public void removeSubscribedChannel(String channel) {
      synchronized(this.subscribedChannels) {
         this.subscribedChannels.remove(channel);
      }
   }

   public void setCommentFilterRequired(boolean isCommentFilterRequired) {
   }

   public void setConnected(boolean connected) {
   }

   public String getId() {
      return this.clientId;
   }

   public long getLastAccessTime() {
      return this.lastAccessTime;
   }

   public void updateLastAccessTime() {
      this.lastAccessTime = System.currentTimeMillis();
   }

   public long getPublishedMessageCount() {
      return this.publishedMessagesCount.get();
   }

   public void addPublishedMessagesCount() {
      this.publishedMessagesCount.incrementAndGet();
   }

   public boolean isActive() {
      return this.active;
   }

   public boolean isMultiFrame() {
      return false;
   }

   public void setMultiFrame(boolean multiFrame) {
   }

   public void setBrowserId(String browserId) {
   }

   public boolean send(Transport transport, List messages) throws IOException {
      return false;
   }

   public boolean hasTransportPending() {
      return false;
   }

   public void setOverloadError(String overloadMessage) {
   }
}
