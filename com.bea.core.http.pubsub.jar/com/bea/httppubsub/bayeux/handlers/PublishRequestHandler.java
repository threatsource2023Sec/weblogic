package com.bea.httppubsub.bayeux.handlers;

import com.bea.httppubsub.Channel;
import com.bea.httppubsub.Client;
import com.bea.httppubsub.LocalClient;
import com.bea.httppubsub.PubSubLogger;
import com.bea.httppubsub.PubSubSecurityException;
import com.bea.httppubsub.PubSubServer;
import com.bea.httppubsub.PubSubServerException;
import com.bea.httppubsub.Transport;
import com.bea.httppubsub.bayeux.messages.DeliverEventMessage;
import com.bea.httppubsub.bayeux.messages.EventMessageImpl;
import com.bea.httppubsub.internal.ChannelId;
import com.bea.httppubsub.internal.InternalChannel;
import com.bea.httppubsub.internal.InternalClient;
import com.bea.httppubsub.internal.MessageFilterChain;
import com.bea.httppubsub.internal.NullTransport;
import com.bea.httppubsub.internal.PubSubServerImpl;
import com.bea.httppubsub.security.ChannelAuthorizationManager;

public class PublishRequestHandler extends AbstractBayeuxHandler {
   protected void doHandle(EventMessageImpl message, Transport transport) throws PubSubServerException {
      InternalClient client = (InternalClient)message.getClient();
      String url = message.getChannel();
      Channel publishChannel = this.findOrCreateChannel(client, url);
      if (!(client instanceof LocalClient) || !(transport instanceof NullTransport)) {
         client.addPublishedMessagesCount();
         this.handleMessage(message);
         this.doSecurityChecking(publishChannel, message.getClient(), url);
      }

      this.publish(publishChannel, message, transport);
   }

   protected void publish(Channel channel, EventMessageImpl message, Transport transport) throws PubSubSecurityException {
      DeliverEventMessage messageToPublish = new DeliverEventMessage(message);
      String url = message.getChannel();
      ChannelId cid = ChannelId.newInstance(url);
      Channel.ChannelPattern type = cid.getChannelPattern();
      channel.publish(messageToPublish, type);
      message.setSuccessful(true);
   }

   protected void handleMessage(EventMessageImpl message) {
      String channel = message.getChannel();
      PubSubServer pubSubServer = this.getPubSubServer();
      if (pubSubServer instanceof PubSubServerImpl) {
         MessageFilterChain chain = ((PubSubServerImpl)pubSubServer).getMessageFilterChain(channel);
         if (chain != null) {
            chain.handleMessage(message);
            if (this.bayeuxLogger.isDebugEnabled()) {
               this.bayeuxLogger.debug("Message is transfered to: [ " + message.toJSONRequestString() + " ]");
            }
         }
      }

   }

   protected void doSecurityChecking(Channel channel, Client client, String urlPattern) throws PubSubSecurityException {
      if (channel instanceof InternalChannel) {
         ChannelAuthorizationManager authManager = ((InternalChannel)channel).getChannelAuthorizationManager();
         if (authManager != null && !((InternalChannel)channel).hasPermission(client, Channel.ChannelPattern.getPattern(urlPattern), ChannelAuthorizationManager.Action.PUBLISH)) {
            String username = "null";
            if (client != null && client.getAuthenticatedUser() != null) {
               username = client.getAuthenticatedUser().getUserName();
            }

            throw new PubSubSecurityException(PubSubLogger.logClientHasNoPermissionPublishLoggable(username, urlPattern).getMessageText());
         }
      }

   }
}
