package com.bea.httppubsub.bayeux.errors;

import com.bea.httppubsub.util.StringUtils;
import java.util.ResourceBundle;

public class ErrorFactoryImpl implements ErrorFactory {
   private static final String UNKNOWN_ERROR_MESSAGE = "Unknown Error";
   private static final String DEFAULT_ERROR_DEFINITION_BASE_NAME = "com.bea.httppubsub.bayeux.errors.ErrorDefinition";
   private final ResourceBundle errorMessages;

   public ErrorFactoryImpl() {
      this("com.bea.httppubsub.bayeux.errors.ErrorDefinition");
   }

   public ErrorFactoryImpl(String errorDefinitionBaseName) {
      String baseNameForUse = StringUtils.isEmpty(errorDefinitionBaseName) ? "com.bea.httppubsub.bayeux.errors.ErrorDefinition" : errorDefinitionBaseName;
      this.errorMessages = this.initializeErrorMessages(baseNameForUse);
   }

   public String getError(int errorCode) {
      return this.getError(errorCode);
   }

   public String getError(int errorCode, String... errorArgs) {
      String errorArgString = StringUtils.arrayToString(errorArgs);
      return errorCode + ":" + errorArgString + ":" + this.getErrorMessage(errorCode);
   }

   private ResourceBundle initializeErrorMessages(String baseName) {
      return ResourceBundle.getBundle(baseName);
   }

   private String getErrorMessage(int errorCode) {
      String errorKey = Integer.toString(errorCode);

      try {
         return this.errorMessages.getString(errorKey);
      } catch (Exception var4) {
         return "Unknown Error";
      }
   }
}
