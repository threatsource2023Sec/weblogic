package weblogic.management.runtime;

import weblogic.messaging.saf.SAFException;

public interface WSRMRemoteEndpointRuntimeMBean extends SAFRemoteEndpointRuntimeMBean {
   void closeConversations(String var1) throws SAFException;

   SAFConversationRuntimeMBean[] getConversations();

   long getConversationsCurrentCount();

   long getConversationsHighCount();

   long getConversationsTotalCount();
}
