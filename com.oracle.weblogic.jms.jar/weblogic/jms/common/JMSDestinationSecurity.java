package weblogic.jms.common;

import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.JMSResource;

public interface JMSDestinationSecurity {
   void checkSendPermission(AuthenticatedSubject var1) throws javax.jms.JMSSecurityException;

   void checkSendPermission() throws javax.jms.JMSSecurityException;

   void checkReceivePermission(AuthenticatedSubject var1) throws javax.jms.JMSSecurityException;

   void checkReceivePermission() throws javax.jms.JMSSecurityException;

   void checkBrowsePermission(AuthenticatedSubject var1) throws javax.jms.JMSSecurityException;

   void checkBrowsePermission() throws javax.jms.JMSSecurityException;

   JMSResource getJMSResourceForSend();

   JMSResource getJMSResourceForReceive();

   JMSResource getJMSResourceForBrowse();
}
