package com.bea.httppubsub.bayeux.handlers.validator;

import com.bea.httppubsub.bayeux.BayeuxConstants;
import com.bea.httppubsub.util.StringUtils;

public class LegalConnectionTypeValidator extends AbstractValidator {
   private String connectionType;

   public String getConnectionType() {
      return this.connectionType;
   }

   public void setConnectionType(String connectionType) {
      this.connectionType = connectionType;
   }

   public void doValidate() {
      String connectionTypeForUse = StringUtils.defaultString(this.getConnectionType());
      if (!BayeuxConstants.isValidSupportedConnectionType(connectionTypeForUse)) {
         this.logger.debug("ConnectionType [" + connectionTypeForUse + "] is not valid.");
         this.validateFailure();
         this.errorCode = 403;
         this.errorArgs.add(connectionTypeForUse);
      }

   }
}
