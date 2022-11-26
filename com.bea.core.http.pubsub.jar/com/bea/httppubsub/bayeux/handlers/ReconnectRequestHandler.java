package com.bea.httppubsub.bayeux.handlers;

import com.bea.httppubsub.PubSubServerException;
import com.bea.httppubsub.Transport;
import com.bea.httppubsub.bayeux.handlers.validator.AvailableConnectionTypeValidator;
import com.bea.httppubsub.bayeux.handlers.validator.LegalConnectionTypeValidator;
import com.bea.httppubsub.bayeux.handlers.validator.ValidatorSuite;
import com.bea.httppubsub.bayeux.messages.ReconnectMessage;
import com.bea.httppubsub.internal.InternalClient;
import javax.servlet.http.HttpSession;

public class ReconnectRequestHandler extends AbstractBayeuxHandler {
   protected void doHandle(ReconnectMessage message, Transport transport) throws PubSubServerException {
      String connectionType = message.getConnectionType();
      InternalClient client = (InternalClient)message.getClient();
      if (this.bayeuxLogger.isDebugEnabled()) {
         this.bayeuxLogger.debug("Check connectionType in RECONNECT request -> [" + connectionType + "]");
      }

      if (connectionType != null) {
         LegalConnectionTypeValidator legalConnectionTypeValidator = new LegalConnectionTypeValidator();
         AvailableConnectionTypeValidator availableConnectionTypeValidator = new AvailableConnectionTypeValidator();
         ValidatorSuite validator = new ValidatorSuite();
         validator.setValidators(legalConnectionTypeValidator, availableConnectionTypeValidator);
         legalConnectionTypeValidator.setConnectionType(connectionType);
         availableConnectionTypeValidator.setConnectionType(connectionType);
         availableConnectionTypeValidator.setAvailableConnectionTypesInServer(this.getAvailableSupportedConnectionTypesInServer());
         validator.validate();
         if (!validator.isPassed()) {
            message.setSuccessful(false);
            message.setError(this.getErrorFactory().getError(validator.getErrorCode(), validator.getErrorArguments()));
            message.setTimestamp(System.currentTimeMillis());
            return;
         }
      } else if (this.bayeuxLogger.isDebugEnabled()) {
         this.bayeuxLogger.debug("No connectionType specified, ignore.");
      }

      message.setSuccessful(true);
      message.setError("");
      message.setTimestamp(System.currentTimeMillis());
      HttpSession httpSession = message.getHttpSession();
      if (httpSession != null) {
         httpSession.setAttribute("Client_In_Http_Session", client);
      }

      if (client.isMultiFrame()) {
         message.setAdvice(this.multiFrameReconnectInterval);
      } else {
         message.setAdvice(this.reconnectInterval);
      }

   }
}
