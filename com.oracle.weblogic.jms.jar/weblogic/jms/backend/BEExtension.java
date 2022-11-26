package weblogic.jms.backend;

import javax.jms.JMSException;
import weblogic.messaging.kernel.Destination;

public interface BEExtension {
   int SEND_WAIT_FOR_COMPLETE = 1101;

   void sendExtension(BEProducerSendRequest var1) throws JMSException;

   void sequenceExtension(BEProducerSendRequest var1) throws JMSException;

   void unitOfWorkAddEvent(String var1);

   void unitOfWorkRemoveEvent(String var1);

   void groupAddEvent(String var1);

   void groupRemoveEvent(String var1);

   void restorePersistentState(Destination var1);
}
