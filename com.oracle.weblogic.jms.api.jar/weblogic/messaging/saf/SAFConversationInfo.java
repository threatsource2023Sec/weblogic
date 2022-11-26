package weblogic.messaging.saf;

import java.io.Externalizable;
import weblogic.messaging.saf.common.SAFRemoteContext;

public interface SAFConversationInfo extends Externalizable {
   int OVERIDECONVERSATIONTIMEOUT = 1;
   int USEAGENTIMEOUT = 2;
   int SAFCONVERSATIONINFO = 1;
   int WSRMSAFCONVERSATIONINFO = 2;
   int WSRM_JAXWS_SAFCONVERSATIONINFO = 3;

   int getQOS();

   void setQOS(int var1);

   String getSourceURL();

   void setSourceURL(String var1);

   int getDestinationType();

   void setDestinationType(int var1);

   String getDestinationURL();

   void setDestinationURL(String var1);

   String getConversationName();

   void setConversationName(String var1);

   int getTransportType();

   void setTransportType(int var1);

   SAFRemoteContext getRemoteContext();

   void setRemoteContext(SAFRemoteContext var1);

   SAFErrorHandler getErrorHandler();

   void setErrorHandler(SAFErrorHandler var1);

   boolean isInorder();

   void setInorder(boolean var1);

   boolean isDynamic();

   void setDynamic(boolean var1);

   String getDynamicConversationName();

   void setDynamicConversationName(String var1);

   long getTimeToLive();

   void setTimeToLive(long var1);

   long getMaximumIdleTime();

   void setMaximumIdleTime(long var1);

   long getConversationTimeout();

   void setConversationTimeout(long var1);

   void setContext(Externalizable var1);

   Externalizable getContext();

   void setConversationOffer(SAFConversationInfo var1);

   SAFConversationInfo getConversationOffer();

   void setTimeoutPolicy(int var1);

   int getTimeoutPolicy();

   void setCreateConversationMessageID(String var1);

   String getCreateConversationMessageID();

   boolean isConversationAlreadyCreated();

   void setConversationAlreadyCreated(boolean var1);
}
