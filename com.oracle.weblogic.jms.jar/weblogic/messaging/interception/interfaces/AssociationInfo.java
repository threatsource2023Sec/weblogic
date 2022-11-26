package weblogic.messaging.interception.interfaces;

import weblogic.messaging.interception.exceptions.InterceptionServiceException;

public interface AssociationInfo {
   String getInterceptionPointType() throws InterceptionServiceException;

   String[] getInterceptionPointName() throws InterceptionServiceException;

   String getProcessorType() throws InterceptionServiceException;

   String getProcessorName() throws InterceptionServiceException;

   boolean hasProcessor() throws InterceptionServiceException;

   boolean isActivated() throws InterceptionServiceException;

   boolean isProcessorShutdown() throws InterceptionServiceException;

   long getTotalMessagesCount() throws InterceptionServiceException;

   long getContinueMessagesCount() throws InterceptionServiceException;

   long getInProgressMessagesCount() throws InterceptionServiceException;

   long getProcessorRegistrationTime() throws InterceptionServiceException;
}
