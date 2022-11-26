package weblogic.security.service;

import weblogic.security.shared.LoggerWrapper;
import weblogic.security.spi.PolicyCollectionHandler;
import weblogic.security.spi.Resource;

public class AuthorizationPolicyHandler {
   private static LoggerWrapper log = LoggerWrapper.getInstance("SecurityAtz");
   private PolicyCollectionHandler[] handlers;

   AuthorizationPolicyHandler(PolicyCollectionHandler[] handlers) {
      this.handlers = handlers;
   }

   public void setPolicy(Resource resource, String[] roleNames) throws ConsumptionException {
      if (log.isDebugEnabled()) {
         log.debug("AuthorizationPolicyHandler.setPolicy");
      }

      for(int i = 0; i < this.handlers.length; ++i) {
         try {
            if (this.handlers[i] != null) {
               this.handlers[i].setPolicy(resource, roleNames);
            }
         } catch (Exception var5) {
            if (log.isDebugEnabled()) {
               log.debug("AuthorizationPolicyHandler.setPolicy got an exception: " + var5.toString(), var5);
            }

            throw new ConsumptionException(var5);
         }
      }

   }

   public void setUncheckedPolicy(Resource resource) throws ConsumptionException {
      if (log.isDebugEnabled()) {
         log.debug("AuthorizationPolicyHandler.setUncheckedPolicy");
      }

      for(int i = 0; i < this.handlers.length; ++i) {
         try {
            if (this.handlers[i] != null) {
               this.handlers[i].setUncheckedPolicy(resource);
            }
         } catch (Exception var4) {
            if (log.isDebugEnabled()) {
               log.debug("AuthorizationPolicyHandler.setUncheckedPolicy got an exception: " + var4.toString(), var4);
            }

            throw new ConsumptionException(var4);
         }
      }

   }

   public void done() throws ConsumptionException {
      if (log.isDebugEnabled()) {
         log.debug("AuthorizationPolicyHandler.done");
      }

      for(int i = 0; i < this.handlers.length; ++i) {
         try {
            if (this.handlers[i] != null) {
               this.handlers[i].done();
            }
         } catch (Exception var3) {
            if (log.isDebugEnabled()) {
               log.debug("AuthorizationPolicyHandler.done got an exception: " + var3.toString(), var3);
            }

            throw new ConsumptionException(var3);
         }
      }

   }
}
