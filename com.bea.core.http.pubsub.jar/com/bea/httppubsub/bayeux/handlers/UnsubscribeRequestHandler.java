package com.bea.httppubsub.bayeux.handlers;

import com.bea.httppubsub.Channel;
import com.bea.httppubsub.PubSubServerException;
import com.bea.httppubsub.Transport;
import com.bea.httppubsub.bayeux.handlers.validator.ClientConnectedValidator;
import com.bea.httppubsub.bayeux.handlers.validator.ValidatorSuite;
import com.bea.httppubsub.bayeux.messages.UnsubscribeMessage;
import com.bea.httppubsub.internal.InternalClient;
import javax.servlet.http.HttpSession;

public class UnsubscribeRequestHandler extends AbstractBayeuxHandler {
   protected void doHandle(UnsubscribeMessage message, Transport transport) throws PubSubServerException {
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
         Channel.ChannelPattern pattern = Channel.ChannelPattern.ITSELF;
         if (subscription.endsWith("/*")) {
            pattern = Channel.ChannelPattern.IMMEDIATE_SUBCHANNELS;
         } else if (subscription.endsWith("/**")) {
            pattern = Channel.ChannelPattern.ALL_SUBCHANNELS;
         }

         Channel subscriptionChannel = this.findChannel(subscription);
         if (subscriptionChannel == null) {
            message.setSuccessful(false);
            message.setError(this.getErrorFactory().getError(504, subscription));
            message.setTimestamp(System.currentTimeMillis());
         } else {
            subscriptionChannel.unsubscribe(client, pattern);
            HttpSession httpSession = message.getHttpSession();
            if (httpSession != null) {
               if (this.clientLogger.isDebugEnabled()) {
                  this.clientLogger.debug("Update client [ " + client.getId() + " ] in session");
               }

               httpSession.setAttribute("Client_In_Http_Session", client);
            }

            message.setSuccessful(true);
            message.setError("");
            message.setTimestamp(System.currentTimeMillis());
         }
      }
   }
}
