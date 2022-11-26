package weblogic.messaging.interception.internal;

import weblogic.messaging.interception.exceptions.InterceptionServiceException;
import weblogic.messaging.interception.interfaces.AssociationInfo;

public class AssociationInfoImpl implements AssociationInfo {
   private Association association = null;

   AssociationInfoImpl(Association association) {
      this.association = association;
   }

   public String getInterceptionPointType() throws InterceptionServiceException {
      return this.association.getInterceptionPointType();
   }

   public String[] getInterceptionPointName() throws InterceptionServiceException {
      return this.association.getInterceptionPointName();
   }

   public String getProcessorType() throws InterceptionServiceException {
      return this.association.getProcessorType();
   }

   public String getProcessorName() throws InterceptionServiceException {
      return this.association.getProcessorName();
   }

   public boolean hasProcessor() throws InterceptionServiceException {
      return this.association.hasProcessor();
   }

   public boolean isActivated() throws InterceptionServiceException {
      return this.association.isActivated();
   }

   public boolean isProcessorShutdown() throws InterceptionServiceException {
      return this.association.isProcessorShutdown();
   }

   public long getTotalMessagesCount() throws InterceptionServiceException {
      return this.association.getTotalMessagesCount();
   }

   public long getContinueMessagesCount() throws InterceptionServiceException {
      return this.association.getContinueMessagesCount();
   }

   public long getInProgressMessagesCount() throws InterceptionServiceException {
      return this.association.getInProgressMessagesCount();
   }

   public long getProcessorRegistrationTime() throws InterceptionServiceException {
      return this.association.getProcessorRegistrationTime();
   }
}
