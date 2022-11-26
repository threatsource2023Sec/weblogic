package weblogic.jms.dispatcher;

import weblogic.jms.common.JMSID;

public interface Invocable extends weblogic.messaging.dispatcher.Invocable {
   JMSID getJMSID();
}
