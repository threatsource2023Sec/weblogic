package com.bea.httppubsub.bayeux.handlers;

import com.bea.httppubsub.Channel;
import com.bea.httppubsub.LocalClient;
import com.bea.httppubsub.PubSubServer;
import com.bea.httppubsub.PubSubServerException;
import com.bea.httppubsub.Transport;
import com.bea.httppubsub.bayeux.handlers.validator.ClientConnectedValidator;
import com.bea.httppubsub.bayeux.handlers.validator.ValidatorSuite;
import com.bea.httppubsub.bayeux.messages.DeliverEventMessage;
import com.bea.httppubsub.bayeux.messages.SubscribeMessage;
import com.bea.httppubsub.internal.ChannelId;
import com.bea.httppubsub.internal.ChannelImpl;
import com.bea.httppubsub.internal.ChannelPersistenceManager;
import com.bea.httppubsub.internal.ChannelPersistenceManagerBuilder;
import com.bea.httppubsub.internal.ClientPersistenceManager;
import com.bea.httppubsub.internal.InternalClient;
import com.bea.httppubsub.internal.PersistedClientRecord;
import com.bea.httppubsub.internal.PubSubServerImpl;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpSession;

public class SubscribeRequestHandler extends AbstractBayeuxHandler {
   private ChannelPersistenceManagerBuilder persistManagerBuilder = null;

   public void setPubSubServer(PubSubServer pubSubServer) {
      super.setPubSubServer(pubSubServer);
      if (pubSubServer.getContext() != null) {
         this.persistManagerBuilder = pubSubServer.getContext().getChannelPersistManagerBuilder();
      }

   }

   protected void doHandle(SubscribeMessage message, Transport transport) throws PubSubServerException {
      InternalClient client = (InternalClient)message.getClient();
      ClientConnectedValidator clientConnectedValidator = new ClientConnectedValidator();
      ValidatorSuite validator = new ValidatorSuite();
      validator.setValidators(clientConnectedValidator);
      clientConnectedValidator.setClient(client);
      validator.validate();
      if (!validator.isPassed()) {
         message.setSuccessful(false);
         message.setError(this.constructErrorMessageIfNecessary(validator));
         message.setTimestamp(System.currentTimeMillis());
      } else {
         String subscription = message.getSubscription();
         if (!subscription.startsWith("/meta/")) {
            ChannelId cid = ChannelId.newInstance(subscription);
            Channel targetChannel = this.findOrCreateChannel(client, cid.toUrl());
            targetChannel.subscribe(client, cid.getChannelPattern());
            HttpSession httpSession = message.getHttpSession();
            if (httpSession != null) {
               if (this.clientLogger.isDebugEnabled()) {
                  this.clientLogger.debug("Update client [ " + client.getId() + " ] in session");
               }

               httpSession.setAttribute("Client_In_Http_Session", client);
            }

            if (!targetChannel.isServiceChannel() && (targetChannel.isPersistentChannel() || this.persistManagerBuilder != null && this.persistManagerBuilder.containsPersistenceChannel(subscription))) {
               this.handlePersistentChannel(client, targetChannel, cid);
            }

            message.setSuccessful(true);
            message.setError("");
         } else {
            message.setSuccessful(false);
            message.setError(this.getErrorFactory().getError(450, subscription));
         }

         message.setTimestamp(System.currentTimeMillis());
      }
   }

   private void handlePersistentChannel(InternalClient client, Channel channel, ChannelId subscription) {
      if (!(client instanceof LocalClient)) {
         PubSubServerImpl server = (PubSubServerImpl)this.getPubSubServer();
         ClientPersistenceManager cpm = server.getClientPersistenceManager();
         if (cpm != null) {
            PersistedClientRecord clientRecord = cpm.fetchClientRecord(client);
            if (clientRecord != null && clientRecord.getLastAccessTime() > 0L) {
               if (channel.isPersistentChannel()) {
                  ChannelPersistenceManager chpm = ((ChannelImpl)channel).getChannelPersistenceManager();
                  this.loadEventsFromStore(client, clientRecord, chpm, subscription);
               } else if (subscription.isWild()) {
                  List cpmList = this.persistManagerBuilder.getContainedCPMList(subscription.toUrl());
                  Iterator var8 = cpmList.iterator();

                  while(var8.hasNext()) {
                     ChannelPersistenceManager chpm = (ChannelPersistenceManager)var8.next();
                     this.loadEventsFromStore(client, clientRecord, chpm, subscription);
                  }
               }
            }

         }
      }
   }

   private void loadEventsFromStore(InternalClient client, PersistedClientRecord clientRecord, ChannelPersistenceManager chpm, ChannelId subscription) {
      List events = chpm.loadEvents(clientRecord, subscription);
      Iterator var6 = events.iterator();

      while(var6.hasNext()) {
         DeliverEventMessage message = (DeliverEventMessage)var6.next();
         client.addSubscribedMessage(message);
      }

   }
}
