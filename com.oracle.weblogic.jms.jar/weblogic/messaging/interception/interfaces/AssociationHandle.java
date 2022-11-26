package weblogic.messaging.interception.interfaces;

import weblogic.messaging.interception.exceptions.InterceptionServiceException;

public interface AssociationHandle {
   AssociationInfo getAssociationInfo() throws InterceptionServiceException;

   void activate() throws InterceptionServiceException;

   void deActivate() throws InterceptionServiceException;
}
