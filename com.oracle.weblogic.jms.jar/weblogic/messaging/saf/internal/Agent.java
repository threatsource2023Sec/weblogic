package weblogic.messaging.saf.internal;

import java.util.HashMap;
import javax.naming.NamingException;
import weblogic.management.configuration.SAFAgentMBean;
import weblogic.messaging.saf.SAFException;

public interface Agent {
   String getName();

   void init(SAFAgentMBean var1) throws SAFException, NamingException;

   void suspend(boolean var1);

   void resume() throws SAFException;

   void setConversationInfosFromStore(HashMap var1);

   HashMap getConversationInfosFromStore();

   AgentImpl makeNewAgent();
}
