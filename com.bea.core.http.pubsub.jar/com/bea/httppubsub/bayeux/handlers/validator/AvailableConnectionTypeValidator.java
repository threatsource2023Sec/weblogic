package com.bea.httppubsub.bayeux.handlers.validator;

import java.util.List;

public class AvailableConnectionTypeValidator extends AbstractValidator {
   private String connectionType;
   private List availableConnectionTypesInServer;

   public String getConnectionType() {
      return this.connectionType;
   }

   public void setConnectionType(String connectionType) {
      this.connectionType = connectionType;
   }

   public List getAvailableConnectionTypesInServer() {
      return this.availableConnectionTypesInServer;
   }

   public void setAvailableConnectionTypesInServer(List availableConnectionTypesInServer) {
      this.availableConnectionTypesInServer = availableConnectionTypesInServer;
   }

   public void doValidate() {
      if (!this.availableConnectionTypesInServer.contains(this.connectionType)) {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("ConnectionType [" + this.connectionType + "] is not available for this server.");
         }

         this.validateFailure();
         this.errorCode = 503;
         this.errorArgs.add(this.connectionType);
      }

   }
}
