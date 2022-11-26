package com.bea.httppubsub.bayeux.handlers;

import com.bea.httppubsub.AuthenticatedUser;
import com.bea.httppubsub.BayeuxMessage;
import com.bea.httppubsub.Channel;
import com.bea.httppubsub.Client;
import com.bea.httppubsub.LocalClient;
import com.bea.httppubsub.PubSubSecurityException;
import com.bea.httppubsub.PubSubServer;
import com.bea.httppubsub.PubSubServerException;
import com.bea.httppubsub.Transport;
import com.bea.httppubsub.bayeux.errors.ErrorFactory;
import com.bea.httppubsub.bayeux.handlers.validator.Validator;
import com.bea.httppubsub.bayeux.messages.AbstractBayeuxMessage;
import com.bea.httppubsub.bayeux.messages.Advice;
import com.bea.httppubsub.internal.BayeuxHandler;
import com.bea.httppubsub.internal.InternalClient;
import com.bea.httppubsub.internal.NullTransport;
import java.util.Arrays;
import java.util.List;
import weblogic.diagnostics.debug.DebugLogger;

public abstract class AbstractBayeuxHandler implements BayeuxHandler {
   protected final DebugLogger bayeuxLogger = DebugLogger.getDebugLogger("DebugPubSubBayeux");
   protected final DebugLogger clientLogger = DebugLogger.getDebugLogger("DebugPubSubClient");
   protected Advice reconnectWithHandshake;
   protected Advice noReconnect;
   protected Advice retryReconnect;
   protected Advice reconnectInterval;
   protected Advice multiFrameReconnectInterval;
   private PubSubServer pubSubServer;
   private ErrorFactory errorFactory;
   private ClientRetriever clientRetriever;
   private List availableSupportedConnectionTypesInServer = null;

   protected AbstractBayeuxHandler() {
   }

   public PubSubServer getPubSubServer() {
      return this.pubSubServer;
   }

   public void setPubSubServer(PubSubServer pubSubServer) {
      this.pubSubServer = pubSubServer;
      this.reconnectWithHandshake = new Advice();
      this.reconnectWithHandshake.setReconnect(Advice.RECONNECT.handshake);
      this.reconnectWithHandshake.setInterval(this.getReconnectInterval());
      this.noReconnect = new Advice();
      this.noReconnect.setReconnect(Advice.RECONNECT.none);
      this.noReconnect.setInterval(this.getReconnectInterval());
      this.retryReconnect = new Advice();
      this.retryReconnect.setReconnect(Advice.RECONNECT.retry);
      this.retryReconnect.setInterval(this.getReconnectInterval());
      this.reconnectInterval = new Advice();
      this.reconnectInterval.setInterval(this.getReconnectInterval());
      this.multiFrameReconnectInterval = new Advice();
      this.multiFrameReconnectInterval.setInterval(this.getReconnectInterval());
      if (this.getMultiFrameReconnectInterval() != -1) {
         this.multiFrameReconnectInterval.setInterval(this.getMultiFrameReconnectInterval());
         this.multiFrameReconnectInterval.setMultipleClients(true);
      }

   }

   public ErrorFactory getErrorFactory() {
      return this.errorFactory;
   }

   public void setErrorFactory(ErrorFactory errorFactory) {
      this.errorFactory = errorFactory;
   }

   protected Channel findOrCreateChannel(Client client, String url) throws PubSubSecurityException {
      return this.getPubSubServer().findOrCreateChannel(client, url);
   }

   protected Channel findChannel(String url) {
      return this.getPubSubServer().findChannel(url);
   }

   protected int getClientTimeout() {
      return this.pubSubServer.getClientTimeout();
   }

   protected boolean isMultiFrameSupported() {
      return this.pubSubServer.isMultiFrameSupported();
   }

   public void initialize() {
      this.clientRetriever = new ClientRetriever(this.getPubSubServer());
   }

   public void destroy() {
   }

   public final void handle(AbstractBayeuxMessage message, Transport transport) throws PubSubServerException {
      if (message == null) {
         throw new IllegalArgumentException("Message cannot be null.");
      } else if (transport == null) {
         throw new IllegalArgumentException("Transport cannot be null.");
      } else if (message.getClient() instanceof LocalClient && transport instanceof NullTransport && BayeuxMessage.TYPE.PUBLISH.equals(message.getType())) {
         this.doHandle(message, transport);
      } else {
         message.setClient((Client)null);
         BayeuxMessage.TYPE messageType = message.getType();
         if (!BayeuxMessage.TYPE.HANDSHAKE.equals(messageType)) {
            String clientId = message.getClientId();
            InternalClient client;
            String errorMessage;
            if (clientId == null) {
               client = (InternalClient)this.clientRetriever.retrieveClientFromHttpSession(message.getHttpSession());
               if (client == null) {
                  if (messageType == BayeuxMessage.TYPE.PUBLISH && this.pubSubServer.isAllowPublishDirectly()) {
                     client = (InternalClient)this.createClient();
                  } else if (messageType == BayeuxMessage.TYPE.PUBLISH && !this.pubSubServer.isAllowPublishDirectly()) {
                     errorMessage = this.errorFactory.getError(407);
                     message.setSuccessful(false);
                     message.setError(errorMessage);
                     message.setTimestamp(System.currentTimeMillis());
                     client = (InternalClient)this.createClient();
                     message.setClient(client);
                     return;
                  }
               }
            } else {
               client = (InternalClient)this.clientRetriever.retrieveClientFromPubSubServer(clientId);
               if (client == null) {
                  client = (InternalClient)this.clientRetriever.retrieveClientFromHttpSession(message.getHttpSession());
               }
            }

            if (client == null) {
               errorMessage = this.errorFactory.getError(401, clientId);
               message.setSuccessful(false);
               message.setError(errorMessage);
               message.setTimestamp(System.currentTimeMillis());
               message.setAdvice(this.reconnectWithHandshake);
               client = (InternalClient)this.createClient();
               message.setClient(client);
               return;
            }

            client.updateLastAccessTime();
            client.setAuthenticatedUser((AuthenticatedUser)transport);
            message.setClient(client);
         }

         this.doHandle(message, transport);
      }
   }

   protected synchronized List getAvailableSupportedConnectionTypesInServer() {
      if (this.availableSupportedConnectionTypesInServer == null) {
         this.availableSupportedConnectionTypesInServer = Arrays.asList(this.getPubSubServer().getSupportedConnectionTypes());
      }

      return this.availableSupportedConnectionTypesInServer;
   }

   protected Client createClient() {
      return this.getPubSubServer().getClientManager().createClient();
   }

   protected Client findClient(String clientId) {
      return this.getPubSubServer().getClientManager().findClient(clientId);
   }

   protected void registerClient(Client client) {
      this.getPubSubServer().getClientManager().addClient(client);
   }

   protected void unregisterClient(Client client) {
      this.getPubSubServer().getClientManager().removeClient(client);
   }

   protected String constructErrorMessageIfNecessary(Validator validator) {
      return validator.isPassed() ? "" : this.errorFactory.getError(validator.getErrorCode(), validator.getErrorArguments());
   }

   private int getReconnectInterval() {
      return this.pubSubServer.getReconnectInterval(false);
   }

   private int getMultiFrameReconnectInterval() {
      return this.pubSubServer.getReconnectInterval(true);
   }

   protected abstract void doHandle(AbstractBayeuxMessage var1, Transport var2) throws PubSubServerException;
}
