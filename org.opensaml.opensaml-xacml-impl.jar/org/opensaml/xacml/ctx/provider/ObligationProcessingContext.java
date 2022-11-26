package org.opensaml.xacml.ctx.provider;

import org.opensaml.xacml.ctx.ResultType;

public class ObligationProcessingContext {
   private ResultType result;

   public ObligationProcessingContext(ResultType authzResult) {
      if (authzResult == null) {
         throw new IllegalArgumentException("Authorization request result may not be null");
      } else {
         this.result = authzResult;
      }
   }

   public ResultType getAuthorizationDecisionResult() {
      return this.result;
   }
}
