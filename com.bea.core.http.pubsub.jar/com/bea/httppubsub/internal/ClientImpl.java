package com.bea.httppubsub.internal;

import com.bea.httppubsub.AuthenticatedUser;
import com.bea.httppubsub.BayeuxMessage;
import com.bea.httppubsub.Transport;
import com.bea.httppubsub.bayeux.messages.AbstractBayeuxMessage;
import com.bea.httppubsub.bayeux.messages.Advice;
import com.bea.httppubsub.bayeux.messages.DeliverEventMessage;
import com.bea.httppubsub.bayeux.messages.ReconnectMessage;
import com.bea.httppubsub.util.StringUtils;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.work.WorkAdapter;

public class ClientImpl extends WorkAdapter implements InternalClient {
   private static final DebugLogger logger = DebugLogger.getDebugLogger("DebugPubSubClient");
   private static final long serialVersionUID = -7312795767661058158L;
   private final String clientId;
   private final ConcurrentLinkedQueue subscribedMessages;
   private transient Transport reconnectTransport = null;
   private AbstractBayeuxMessage reconnectMessage = null;
   private volatile boolean scheduled = false;
   private final Set subscribedChannels;
   private long lastAccessTime;
   private boolean connected;
   private boolean isCommentFiltered;
   private String browserId;
   private boolean multiFrame;
   private AtomicLong publishedMessagesCount = new AtomicLong(0L);
   private transient AuthenticatedUser authenticatedUser;
   private String overloadError;

   ClientImpl(String clientId) {
      if (StringUtils.isEmpty(clientId)) {
         throw new IllegalArgumentException("Client ID cannot be empty when constructing Client.");
      } else {
         if (logger.isDebugEnabled()) {
            logger.debug("Client [ " + clientId + " ] created");
         }

         this.clientId = clientId;
         this.lastAccessTime = System.currentTimeMillis();
         this.subscribedChannels = new LinkedHashSet();
         this.subscribedMessages = new ConcurrentLinkedQueue();
      }
   }

   public String getId() {
      return this.clientId;
   }

   public long getLastAccessTime() {
      return this.lastAccessTime;
   }

   public Set getChannelSubscriptions() {
      return new LinkedHashSet(this.subscribedChannels);
   }

   public long getPublishedMessageCount() {
      return this.publishedMessagesCount.get();
   }

   public void addPublishedMessagesCount() {
      this.publishedMessagesCount.incrementAndGet();
   }

   public boolean isConnected() {
      return this.connected;
   }

   public void setConnected(boolean connected) {
      this.connected = connected;
   }

   public void setCommentFilterRequired(boolean isCommentFiltered) {
      this.isCommentFiltered = isCommentFiltered;
   }

   public void addSubscribedChannel(String channel) {
      synchronized(this.subscribedChannels) {
         this.subscribedChannels.add(channel);
      }
   }

   public void removeSubscribedChannel(String channel) {
      synchronized(this.subscribedChannels) {
         this.subscribedChannels.remove(channel);
      }
   }

   public void addSubscribedMessage(DeliverEventMessage message) {
      this.subscribedMessages.offer(message);
   }

   public boolean send(Transport transport, List messages) throws IOException {
      transport.setCommentFiltered(this.isCommentFiltered);
      boolean hasHandShake = false;
      Iterator msg = messages.iterator();

      while(msg.hasNext()) {
         AbstractBayeuxMessage message = (AbstractBayeuxMessage)msg.next();
         if ((message.getType() == BayeuxMessage.TYPE.RECONNECT || message.getType() == BayeuxMessage.TYPE.CONNECT) && messages.size() == 1 && this.subscribedMessages.isEmpty() && message.isSuccessful() && !transport.isNormalPolling() && !this.isMultiFrame()) {
            this.reconnectMessage = message;
            this.reconnectTransport = transport;
            return true;
         }

         if (message.getType() == BayeuxMessage.TYPE.HANDSHAKE) {
            hasHandShake = true;
            break;
         }

         if (message.getType() == BayeuxMessage.TYPE.DISCONNECT && !this.isScheduled()) {
            synchronized(this) {
               if (this.hasTransportPending() && !this.isScheduled()) {
                  this.setScheduled(true);
                  Advice advice = new Advice();
                  advice.setReconnect(Advice.RECONNECT.none);
                  this.reconnectMessage.setAdvice(advice);
                  this.reconnectTransport.send(Arrays.asList(this.reconnectMessage));
               }
               break;
            }
         }
      }

      if (hasHandShake) {
         transport.send(messages);
         return false;
      } else {
         msg = null;

         BayeuxMessage msg;
         while((msg = (BayeuxMessage)this.subscribedMessages.poll()) != null) {
            messages.add((AbstractBayeuxMessage)msg);
         }

         transport.send(messages);
         return false;
      }
   }

   public boolean hasTransportPending() {
      return this.reconnectTransport != null && this.reconnectTransport.isValid();
   }

   public AuthenticatedUser getAuthenticatedUser() {
      return this.authenticatedUser;
   }

   public void setAuthenticatedUser(AuthenticatedUser authenticatedUser) {
      this.authenticatedUser = authenticatedUser;
   }

   public boolean equals(Object obj) {
      if (!(obj instanceof ClientImpl)) {
         return false;
      } else {
         ClientImpl that = (ClientImpl)obj;
         return this.clientId.equals(that.clientId);
      }
   }

   public int hashCode() {
      return this.clientId.hashCode();
   }

   public void updateLastAccessTime() {
      this.lastAccessTime = System.currentTimeMillis();
   }

   public String toString() {
      return this.clientId;
   }

   public String getBrowserId() {
      return this.browserId;
   }

   public void setBrowserId(String browserId) {
      this.browserId = browserId;
   }

   public boolean isMultiFrame() {
      return this.multiFrame;
   }

   public void setMultiFrame(boolean multiFrame) {
      if (logger.isDebugEnabled()) {
         logger.debug("Client [ " + this.clientId + " ] is detected having other clients requesting from the same browser");
      }

      this.multiFrame = multiFrame;
   }

   public boolean isScheduled() {
      return this.scheduled || this.reconnectTransport == null;
   }

   public void setScheduled(boolean scheduled) {
      this.scheduled = scheduled;
   }

   public void run() {
      List messagesToSent = new LinkedList();
      BayeuxMessage msg = null;

      while((msg = (BayeuxMessage)this.subscribedMessages.poll()) != null) {
         messagesToSent.add(msg);
      }

      if (messagesToSent.size() == 0) {
         this.scheduled = false;
      } else {
         messagesToSent.add(0, this.reconnectMessage);

         try {
            this.reconnectTransport.send(messagesToSent);
         } catch (IOException var8) {
            for(int i = 1; i < messagesToSent.size(); ++i) {
               this.addSubscribedMessage((DeliverEventMessage)messagesToSent.get(i));
            }
         } finally {
            this.reconnectTransport = null;
            this.reconnectMessage = null;
            this.scheduled = false;
            this.lastAccessTime = System.currentTimeMillis();
         }

      }
   }

   public void setOverloadError(String message) {
      this.overloadError = message;
   }

   public Runnable overloadAction(String string) {
      return new ClientOverloadAction();
   }

   class ClientOverloadAction implements Runnable {
      AbstractBayeuxMessage overloadMessage = new ReconnectMessage();

      ClientOverloadAction() {
         this.overloadMessage.setSuccessful(false);
         if (ClientImpl.this.overloadError != null) {
            this.overloadMessage.setError(ClientImpl.this.overloadError);
         }

         Advice advice = new Advice();
         advice.setReconnect(Advice.RECONNECT.none);
         this.overloadMessage.setAdvice(advice);
      }

      public void run() {
         ClientImpl.this.subscribedMessages.clear();
         if (ClientImpl.this.hasTransportPending()) {
            try {
               ClientImpl.this.reconnectTransport.send(Arrays.asList(this.overloadMessage));
            } catch (IOException var5) {
            } finally {
               ClientImpl.this.reconnectTransport = null;
            }
         }

      }
   }
}
