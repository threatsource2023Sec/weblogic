package weblogic.messaging.saf.internal;

import weblogic.messaging.saf.SAFException;
import weblogic.messaging.saf.common.AgentDeliverRequest;
import weblogic.messaging.saf.common.AgentDeliverResponse;

public interface ReceivingAgent extends Agent {
   AgentDeliverResponse deliver(AgentDeliverRequest var1) throws SAFException;
}
