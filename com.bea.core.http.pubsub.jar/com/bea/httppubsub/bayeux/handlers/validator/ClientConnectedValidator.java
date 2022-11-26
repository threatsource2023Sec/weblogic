package com.bea.httppubsub.bayeux.handlers.validator;

import com.bea.httppubsub.internal.InternalClient;

public class ClientConnectedValidator extends AbstractValidator {
   private InternalClient client;

   public InternalClient getClient() {
      return this.client;
   }

   public void setClient(InternalClient client) {
      this.client = client;
   }

   public void doValidate() {
      if (!this.client.isConnected()) {
         this.validateFailure();
         this.errorCode = 404;
         this.errorArgs.add(this.client.getId());
      }

   }
}
