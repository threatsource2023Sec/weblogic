package com.bea.httppubsub.internal;

import com.bea.httppubsub.Transport;
import com.bea.httppubsub.bayeux.errors.ErrorFactory;
import com.bea.httppubsub.bayeux.messages.AbstractBayeuxMessage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ErrorSenderImpl implements ErrorSender {
   private final ErrorFactory errorFactory;

   public ErrorSenderImpl(ErrorFactory errorFactory) {
      if (errorFactory == null) {
         throw new IllegalArgumentException("ErrorFactory cannot be null.");
      } else {
         this.errorFactory = errorFactory;
      }
   }

   public void send(Transport transport, AbstractBayeuxMessage message, int errorCode) throws IOException {
      this.send(transport, message, errorCode, (String)null);
   }

   public void send(Transport transport, AbstractBayeuxMessage message, int errorCode, String errorMessage) throws IOException {
      this.setErrorInMessage(message, errorCode, errorMessage);
      transport.send(Arrays.asList(message));
   }

   public void send(Transport transport, List messages, int errorCode) throws IOException {
      Iterator var4 = messages.iterator();

      while(var4.hasNext()) {
         AbstractBayeuxMessage eachMessage = (AbstractBayeuxMessage)var4.next();
         this.setErrorInMessage(eachMessage, errorCode, (String)null);
      }

      transport.send(messages);
   }

   private void setErrorInMessage(AbstractBayeuxMessage message, int errorCode, String errorMsg) {
      String errorMessage = errorMsg == null ? this.errorFactory.getError(errorCode) : this.errorFactory.getError(errorCode, errorMsg);
      message.setSuccessful(false);
      message.setError(errorMessage);
      message.setTimestamp(System.currentTimeMillis());
   }
}
