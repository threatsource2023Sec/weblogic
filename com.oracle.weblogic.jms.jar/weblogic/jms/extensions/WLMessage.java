package weblogic.jms.extensions;

import javax.jms.JMSException;
import javax.jms.Message;
import org.w3c.dom.Document;

public interface WLMessage extends Message, WLAcknowledgeInfo {
   /** @deprecated */
   @Deprecated
   long getJMSDeliveryTime() throws JMSException;

   /** @deprecated */
   @Deprecated
   void setJMSDeliveryTime(long var1) throws JMSException;

   /** @deprecated */
   @Deprecated
   int getJMSRedeliveryLimit() throws JMSException;

   /** @deprecated */
   @Deprecated
   void setJMSRedeliveryLimit(int var1) throws JMSException;

   Document getJMSMessageDocument() throws JMSException;

   boolean getDDForwarded();

   String getUnitOfOrder();

   void setSAFSequenceName(String var1);

   String getSAFSequenceName();

   void setSAFSeqNumber(long var1);

   long getSAFSeqNumber();
}
