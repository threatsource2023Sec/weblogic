package com.bea.httppubsub.bayeux.handlers;

import com.bea.httppubsub.PubSubServerException;
import com.bea.httppubsub.Transport;
import com.bea.httppubsub.bayeux.handlers.validator.AvailableConnectionTypeValidator;
import com.bea.httppubsub.bayeux.handlers.validator.LegalConnectionTypeValidator;
import com.bea.httppubsub.bayeux.handlers.validator.ValidatorSuite;
import com.bea.httppubsub.bayeux.messages.ConnectMessage;
import com.bea.httppubsub.internal.InternalClient;
import com.bea.httppubsub.util.StringUtils;
import java.util.List;
import javax.servlet.http.HttpSession;

public class ConnectRequestHandler extends AbstractBayeuxHandler {
   protected void doHandle(ConnectMessage message, Transport transport) throws PubSubServerException {
      String connectionType = message.getConnectionType();
      if (this.bayeuxLogger.isDebugEnabled()) {
         this.bayeuxLogger.debug("Requested connectionType from client -> [" + connectionType + "]");
      }

      InternalClient client = (InternalClient)message.getClient();
      LegalConnectionTypeValidator legalConnectionTypeValidator = new LegalConnectionTypeValidator();
      AvailableConnectionTypeValidator availableConnectionTypeValidator = new AvailableConnectionTypeValidator();
      ValidatorSuite validator = new ValidatorSuite();
      validator.setValidators(legalConnectionTypeValidator, availableConnectionTypeValidator);
      List types = this.getAvailableSupportedConnectionTypesInServer();
      legalConnectionTypeValidator.setConnectionType(StringUtils.defaultString(connectionType));
      availableConnectionTypeValidator.setConnectionType(StringUtils.defaultString(connectionType));
      availableConnectionTypeValidator.setAvailableConnectionTypesInServer(types);
      validator.validate();
      if (!validator.isPassed()) {
         message.setSuccessful(false);
         message.setError(this.getErrorFactory().getError(validator.getErrorCode(), validator.getErrorArguments()));
         message.setAdvice(this.retryReconnect);
      } else {
         if (!client.isConnected()) {
            if (this.bayeuxLogger.isDebugEnabled()) {
               this.bayeuxLogger.debug("First time connect message [ " + message + " ] from client [ " + client + " ]");
            }

            client.setConnected(true);
            transport.setNormalPolling(true);
         }

         HttpSession httpSession = message.getHttpSession();
         if (httpSession != null) {
            if (this.clientLogger.isDebugEnabled()) {
               this.clientLogger.debug("Save client [ " + client.getId() + " to session");
            }

            httpSession.setAttribute("Client_In_Http_Session", client);
         }

         message.setSuccessful(true);
         message.setError("");
         message.setTimestamp(System.currentTimeMillis());
         if (client.isMultiFrame()) {
            message.setAdvice(this.multiFrameReconnectInterval);
         } else {
            message.setAdvice(this.reconnectInterval);
         }

      }
   }
}
