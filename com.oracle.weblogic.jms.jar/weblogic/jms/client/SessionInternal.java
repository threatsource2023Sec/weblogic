package weblogic.jms.client;

import javax.jms.JMSException;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSPushEntry;
import weblogic.jms.common.MessageImpl;
import weblogic.jms.extensions.MDBTransaction;
import weblogic.jms.extensions.WLQueueSession;
import weblogic.jms.extensions.WLSession;
import weblogic.jms.extensions.WLTopicSession;

public interface SessionInternal extends WLSession, WLQueueSession, WLTopicSession, MDBTransaction, ClientRuntimeInfo, MMessageAsyncSession {
   void checkSAFClosed() throws JMSException;

   JMSID getJMSID();

   void setPipelineGeneration(int var1);

   void pushMessage(MessageImpl var1, JMSPushEntry var2);

   int consumersCount();

   int producersCount();

   String getPartitionName();
}
