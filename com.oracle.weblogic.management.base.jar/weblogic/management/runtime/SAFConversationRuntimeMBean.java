package weblogic.management.runtime;

import weblogic.messaging.saf.SAFException;

public interface SAFConversationRuntimeMBean extends RuntimeMBean {
   String getConversationName();

   String getDestinationURL();

   String getQOS();

   void destroy() throws SAFException;
}
