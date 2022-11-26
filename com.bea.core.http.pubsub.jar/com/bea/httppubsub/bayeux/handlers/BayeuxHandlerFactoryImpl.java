package com.bea.httppubsub.bayeux.handlers;

import com.bea.httppubsub.BayeuxMessage;
import com.bea.httppubsub.PubSubServer;
import com.bea.httppubsub.PubSubServerException;
import com.bea.httppubsub.bayeux.errors.ErrorFactory;
import com.bea.httppubsub.descriptor.WeblogicPubsubBean;
import com.bea.httppubsub.internal.BayeuxHandler;
import com.bea.httppubsub.internal.BayeuxHandlerFactory;
import com.bea.httppubsub.internal.ChannelId;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class BayeuxHandlerFactoryImpl implements BayeuxHandlerFactory {
   private final Map metaHandlers;
   private PublishRequestHandler defaultPublishHandler;
   private ServiceRequestHandler serviceRequestHandler;

   public BayeuxHandlerFactoryImpl(PubSubServer pubSubServer, ErrorFactory errorFactory, WeblogicPubsubBean configuration) throws PubSubServerException {
      this.metaHandlers = this.initMetaHandlers(pubSubServer, errorFactory);
      this.defaultPublishHandler = this.initDefaultPublishRequestHandler(pubSubServer, errorFactory);
      this.serviceRequestHandler = this.initServiceRequestHandlder(pubSubServer, errorFactory, configuration);
   }

   public BayeuxHandler getMessageHandler(BayeuxMessage message) {
      if (message == null) {
         throw new IllegalArgumentException("Message cannot be null.");
      } else {
         BayeuxMessage.TYPE messageType = message.getType();
         if (messageType == BayeuxMessage.TYPE.PUBLISH) {
            String channel = message.getChannel();
            return (BayeuxHandler)(channel.startsWith("/service/") ? this.serviceRequestHandler : this.doGetPublishRequestHandler(ChannelId.newInstance(channel)));
         } else {
            return (BayeuxHandler)this.metaHandlers.get(messageType);
         }
      }
   }

   public void destroy() {
      Iterator var1 = this.metaHandlers.values().iterator();

      while(var1.hasNext()) {
         BayeuxHandler metaHandler = (BayeuxHandler)var1.next();
         metaHandler.destroy();
      }

      this.metaHandlers.clear();
      this.defaultPublishHandler.destroy();
      this.serviceRequestHandler.destroy();
      this.defaultPublishHandler = null;
      this.serviceRequestHandler = null;
   }

   protected BayeuxHandler doGetPublishRequestHandler(ChannelId cid) {
      return this.defaultPublishHandler;
   }

   private Map initMetaHandlers(PubSubServer pubSubServer, ErrorFactory errorFactory) {
      Map handlers = new LinkedHashMap();
      HandshakeRequestHandler handshakeHandler = new HandshakeRequestHandler();
      handshakeHandler.setErrorFactory(errorFactory);
      handshakeHandler.setPubSubServer(pubSubServer);
      ConnectRequestHandler connectHandler = new ConnectRequestHandler();
      connectHandler.setErrorFactory(errorFactory);
      connectHandler.setPubSubServer(pubSubServer);
      ReconnectRequestHandler reconnectHandler = new ReconnectRequestHandler();
      reconnectHandler.setErrorFactory(errorFactory);
      reconnectHandler.setPubSubServer(pubSubServer);
      DisconnectRequestHandler disconnectHandler = new DisconnectRequestHandler();
      disconnectHandler.setErrorFactory(errorFactory);
      disconnectHandler.setPubSubServer(pubSubServer);
      SubscribeRequestHandler subscribeHandler = new SubscribeRequestHandler();
      subscribeHandler.setErrorFactory(errorFactory);
      subscribeHandler.setPubSubServer(pubSubServer);
      UnsubscribeRequestHandler unsubscribeHandler = new UnsubscribeRequestHandler();
      unsubscribeHandler.setErrorFactory(errorFactory);
      unsubscribeHandler.setPubSubServer(pubSubServer);
      handlers.put(BayeuxMessage.TYPE.HANDSHAKE, handshakeHandler);
      handlers.put(BayeuxMessage.TYPE.CONNECT, connectHandler);
      handlers.put(BayeuxMessage.TYPE.RECONNECT, reconnectHandler);
      handlers.put(BayeuxMessage.TYPE.DISCONNECT, disconnectHandler);
      handlers.put(BayeuxMessage.TYPE.SUBSCRIBE, subscribeHandler);
      handlers.put(BayeuxMessage.TYPE.UNSUBSCRIBE, unsubscribeHandler);
      Iterator var10 = handlers.values().iterator();

      while(var10.hasNext()) {
         BayeuxHandler handler = (BayeuxHandler)var10.next();
         handler.initialize();
      }

      return handlers;
   }

   private PublishRequestHandler initDefaultPublishRequestHandler(PubSubServer pubSubServer, ErrorFactory errorFactory) {
      PublishRequestHandler handler = new PublishRequestHandler();
      handler.setErrorFactory(errorFactory);
      handler.setPubSubServer(pubSubServer);
      handler.initialize();
      return handler;
   }

   private ServiceRequestHandler initServiceRequestHandlder(PubSubServer pubSubServer, ErrorFactory errorFactory, WeblogicPubsubBean configuration) throws PubSubServerException {
      ServiceRequestHandler handler = new ServiceRequestHandler(configuration);
      handler.setErrorFactory(errorFactory);
      handler.setPubSubServer(pubSubServer);
      handler.initialize();
      return handler;
   }
}
