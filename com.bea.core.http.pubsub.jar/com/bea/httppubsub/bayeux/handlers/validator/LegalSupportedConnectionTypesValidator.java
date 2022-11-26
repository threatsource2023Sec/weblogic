package com.bea.httppubsub.bayeux.handlers.validator;

import com.bea.httppubsub.bayeux.BayeuxConstants;
import com.bea.httppubsub.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

public class LegalSupportedConnectionTypesValidator extends AbstractValidator {
   private String[] supportedConnectionTypes;

   public String[] getSupportedConnectionTypes() {
      return this.supportedConnectionTypes;
   }

   public void setSupportedConnectionTypes(String[] supportedConnectionTypes) {
      this.supportedConnectionTypes = supportedConnectionTypes;
   }

   public void doValidate() {
      if (this.logger.isDebugEnabled()) {
         this.logger.debug("Checking requested supportedConnectionTypes from client -> [" + StringUtils.arrayToString(this.supportedConnectionTypes) + "]");
      }

      List invalidSupportedConnectionTypes = new ArrayList();
      String[] result = this.supportedConnectionTypes;
      int var3 = result.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String connectionType = result[var4];
         if (!BayeuxConstants.isValidSupportedConnectionType(connectionType)) {
            invalidSupportedConnectionTypes.add(connectionType);
         }
      }

      result = (String[])invalidSupportedConnectionTypes.toArray(new String[invalidSupportedConnectionTypes.size()]);
      if (this.logger.isDebugEnabled() && result.length > 0) {
         this.logger.debug("Invalid supportedConnectionTypes from client found -> [" + StringUtils.arrayToString(result) + "]");
      }

      if (invalidSupportedConnectionTypes.size() > 0) {
         this.validateFailure();
         this.errorCode = 402;
         this.errorArgs.addAll(invalidSupportedConnectionTypes);
      }

   }
}
