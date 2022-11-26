package com.bea.httppubsub.bayeux.handlers;

import com.bea.httppubsub.AuthenticatedUser;
import com.bea.httppubsub.Client;
import com.bea.httppubsub.PubSubServerException;
import com.bea.httppubsub.Transport;
import com.bea.httppubsub.bayeux.handlers.validator.AvailableSupportedConnectionTypesValidator;
import com.bea.httppubsub.bayeux.handlers.validator.ValidatorSuite;
import com.bea.httppubsub.bayeux.messages.HandshakeMessage;
import com.bea.httppubsub.internal.InternalClient;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpSession;

public class HandshakeRequestHandler extends AbstractBayeuxHandler {
   private static final String VERSION = "1.0";
   private static final String MINIMUM_VERSION = "0.1";

   protected void doHandle(HandshakeMessage message, Transport transport) throws PubSubServerException {
      if (message.getClient() != null) {
         throw new IllegalArgumentException("Client must be null when handshake.");
      } else {
         InternalClient client = (InternalClient)this.createClient();
         message.updateClient(client);
         if (this.isMultiFrameSupported()) {
            client.setBrowserId(transport.getBrowserId());
         }

         if (message.getSupportedConnectionTypes().length == 0) {
            if (this.bayeuxLogger.isDebugEnabled()) {
               this.bayeuxLogger.debug("No supportedConnectionTypes specifed by client, use server's available supportedConnectionTypes.");
               this.bayeuxLogger.debug("According to Bayeux Protocol 1.0, client MUST specify supportedConnectionTypes in handshake.");
            }

            List availableSupportedConnectionTypesInServer = this.getAvailableSupportedConnectionTypesInServer();
            message.setSupportedConnectionTypes((String[])availableSupportedConnectionTypesInServer.toArray(new String[availableSupportedConnectionTypesInServer.size()]));
         }

         AvailableSupportedConnectionTypesValidator availableSupportedConnectionTypesValidator = new AvailableSupportedConnectionTypesValidator();
         ValidatorSuite validator = new ValidatorSuite();
         validator.setValidators(availableSupportedConnectionTypesValidator);
         availableSupportedConnectionTypesValidator.setSupportedConnectionTypes(message.getSupportedConnectionTypes());
         availableSupportedConnectionTypesValidator.setAvailableConnectionTypesInServer(this.getAvailableSupportedConnectionTypesInServer());
         validator.validate();
         if (!validator.isPassed()) {
            message.setSuccessful(false);
            message.setError(this.getErrorFactory().getError(validator.getErrorCode(), validator.getErrorArguments()));
            List availableSupportedConnectionTypesInServer = this.getAvailableSupportedConnectionTypesInServer();
            message.setSupportedConnectionTypes((String[])availableSupportedConnectionTypesInServer.toArray(new String[availableSupportedConnectionTypesInServer.size()]));
            message.setMinimumVersion("0.1");
            message.setVersion("1.0");
            message.setAdvice(this.noReconnect);
         } else {
            Object[] generatedObjectsByValidatorSuite = (Object[])((Object[])validator.getGeneratedObject());
            String[] matchedSupportedConnectionTypesForClient = (String[])((String[])generatedObjectsByValidatorSuite[0]);
            this.registerClient(client);
            client.setOverloadError(this.getErrorFactory().getError(505));
            HttpSession httpSession = message.getHttpSession();
            if (httpSession != null) {
               if (this.clientLogger.isDebugEnabled()) {
                  this.clientLogger.debug("Save client [ " + client.getId() + " ] in session");
                  this.clientLogger.debug("Update client [ " + client.getId() + " ] in sesson's attribute " + "Clients_From_Same_Browser");
               }

               httpSession.setAttribute("Client_In_Http_Session", client);
               List clients = (List)httpSession.getAttribute("Clients_From_Same_Browser");
               if (clients == null) {
                  clients = new ArrayList();
               }

               Iterator var10 = ((List)clients).iterator();

               while(var10.hasNext()) {
                  Client c = (Client)var10.next();
                  this.registerClient(c);
               }

               ((List)clients).add(client);
               httpSession.setAttribute("Clients_From_Same_Browser", clients);
            }

            if (message.shouldCommentFilter()) {
               if (this.bayeuxLogger.isDebugEnabled()) {
                  this.bayeuxLogger.debug("client requests response message to be commented.");
               }

               client.setCommentFilterRequired(true);
            }

            message.setMinimumVersion("0.1");
            message.setSuccessful(true);
            message.setSupportedConnectionTypes(matchedSupportedConnectionTypesForClient);
            message.setVersion("1.0");
            message.setAuthSuccessful(true);
            message.setTimestamp(System.currentTimeMillis());
            message.setAdvice(this.reconnectInterval);
            client.setAuthenticatedUser((AuthenticatedUser)transport);
         }
      }
   }
}
