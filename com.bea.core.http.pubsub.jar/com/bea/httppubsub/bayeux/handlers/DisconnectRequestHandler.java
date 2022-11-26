package com.bea.httppubsub.bayeux.handlers;

import com.bea.httppubsub.Channel;
import com.bea.httppubsub.PubSubServer;
import com.bea.httppubsub.PubSubServerException;
import com.bea.httppubsub.Transport;
import com.bea.httppubsub.bayeux.handlers.validator.ClientConnectedValidator;
import com.bea.httppubsub.bayeux.handlers.validator.ValidatorSuite;
import com.bea.httppubsub.bayeux.messages.DisconnectMessage;
import com.bea.httppubsub.internal.ChannelPersistenceManagerBuilder;
import com.bea.httppubsub.internal.ClientPersistenceManager;
import com.bea.httppubsub.internal.InternalClient;
import com.bea.httppubsub.internal.PubSubServerImpl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpSession;

public class DisconnectRequestHandler extends AbstractBayeuxHandler {
   private ChannelPersistenceManagerBuilder persistManagerBuilder = null;

   public void setPubSubServer(PubSubServer pubSubServer) {
      super.setPubSubServer(pubSubServer);
      if (pubSubServer.getContext() != null) {
         this.persistManagerBuilder = pubSubServer.getContext().getChannelPersistManagerBuilder();
      }

   }

   protected void doHandle(DisconnectMessage message, Transport transport) throws PubSubServerException {
      InternalClient client = (InternalClient)message.getClient();
      ClientConnectedValidator clientConnectedValidator = new ClientConnectedValidator();
      ValidatorSuite validator = new ValidatorSuite();
      validator.setValidators(clientConnectedValidator);
      clientConnectedValidator.setClient(client);
      validator.validate();
      if (!validator.isPassed()) {
         message.setSuccessful(false);
         message.setError(this.constructErrorMessageIfNecessary(validator));
      } else {
         boolean subscribedPersistentChannel = false;
         Set channelSubscriptions = client.getChannelSubscriptions();

         Channel channel;
         for(Iterator var8 = channelSubscriptions.iterator(); var8.hasNext(); channel.unsubscribe(client)) {
            String channelUrl = (String)var8.next();
            channel = this.findChannel(channelUrl);
            if (channel.isPersistentChannel() || this.persistManagerBuilder != null && this.persistManagerBuilder.containsPersistenceChannel(channelUrl)) {
               subscribedPersistentChannel = true;
            }
         }

         if (subscribedPersistentChannel) {
            this.storeClientRecord(client);
         }

         client.setConnected(false);
         this.unregisterClient(client);
         HttpSession httpSession = message.getHttpSession();
         if (httpSession != null) {
            if (httpSession.getAttribute("Client_In_Http_Session") != null) {
               if (this.clientLogger.isDebugEnabled()) {
                  this.clientLogger.debug("Remove client [ " + client.getId() + " ] from session");
               }

               httpSession.removeAttribute("Client_In_Http_Session");
            }

            List clients = (ArrayList)httpSession.getAttribute("Clients_From_Same_Browser");
            if (clients != null) {
               clients.remove(client);
               if (this.clientLogger.isDebugEnabled()) {
                  this.clientLogger.debug("Remove client [ " + client.getId() + " ] from session attribute " + "Clients_From_Same_Browser");
               }

               if (clients.size() > 0) {
                  httpSession.setAttribute("Clients_From_Same_Browser", clients);
               } else {
                  httpSession.removeAttribute("Clients_From_Same_Browser");
               }
            }
         }

         message.setSuccessful(true);
         message.setError("");
      }
   }

   private void storeClientRecord(InternalClient client) {
      ClientPersistenceManager cpm = ((PubSubServerImpl)this.getPubSubServer()).getClientPersistenceManager();
      if (cpm != null) {
         cpm.storeClientRecord(client);
      }

   }
}
