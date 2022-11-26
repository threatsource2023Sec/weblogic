package weblogic.validation;

import weblogic.diagnostics.debug.DebugLogger;

public enum Jndi {
   VALIDATOR_BINDING("Validator"),
   VALIDATOR_FACTORY_BINDING("ValidatorFactory"),
   VALIDATION_CONTEXT_BINDING("_WL_internal/ValidationContext"),
   VALIDATOR("java:comp/Validator"),
   VALIDATOR_FACTORY("java:comp/ValidatorFactory"),
   VALIDATION_CONTEXT("java:comp/_WL_internal/ValidationContext");

   public String key;

   private Jndi(String aKey) {
      this.key = aKey;
      DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugValidation");
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(this.toString());
      }

   }
}
