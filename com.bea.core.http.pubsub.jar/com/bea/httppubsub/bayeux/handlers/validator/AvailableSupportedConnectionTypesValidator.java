package com.bea.httppubsub.bayeux.handlers.validator;

import com.bea.httppubsub.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

public class AvailableSupportedConnectionTypesValidator extends AbstractValidator {
   private String[] supportedConnectionTypes;
   private List availableConnectionTypesInServer;

   public String[] getSupportedConnectionTypes() {
      return this.supportedConnectionTypes;
   }

   public void setSupportedConnectionTypes(String[] supportedConnectionTypes) {
      this.supportedConnectionTypes = supportedConnectionTypes;
   }

   public List getAvailableConnectionTypesInServer() {
      return this.availableConnectionTypesInServer;
   }

   public void setAvailableConnectionTypesInServer(List availableConnectionTypesInServer) {
      this.availableConnectionTypesInServer = availableConnectionTypesInServer;
   }

   public void doValidate() {
      String[] matchedSupportedConnectionTypesForClient = this.chooseProperSupportedConnectionTypesForClient();
      if (matchedSupportedConnectionTypesForClient.length == 0) {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("No matched supportedConnectionTypes found.");
         }

         this.validateFailure();
         this.errorCode = 502;
      }

      this.generatedObject = matchedSupportedConnectionTypesForClient;
   }

   private String[] chooseProperSupportedConnectionTypesForClient() {
      if (this.logger.isDebugEnabled()) {
         this.logger.debug("Available supportedConnectionTypes in server -> [" + StringUtils.arrayToString(this.availableConnectionTypesInServer.toArray(new String[this.availableConnectionTypesInServer.size()])) + "]");
         this.logger.debug("Requested supportedConnectionTypes from client -> [" + StringUtils.arrayToString(this.supportedConnectionTypes) + "]");
      }

      List matchedConnectionTypes = new ArrayList();
      String[] result = this.supportedConnectionTypes;
      int var3 = result.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String requestType = result[var4];
         if (this.availableConnectionTypesInServer.contains(requestType)) {
            matchedConnectionTypes.add(requestType);
         }
      }

      result = (String[])matchedConnectionTypes.toArray(new String[matchedConnectionTypes.size()]);
      if (this.logger.isDebugEnabled()) {
         this.logger.debug("Matched supportedConnectionTypes -> [" + StringUtils.arrayToString(result) + "]");
      }

      return result;
   }
}
