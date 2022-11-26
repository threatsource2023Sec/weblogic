package weblogic.jms.backend;

import java.util.HashMap;
import javax.jms.JMSException;
import weblogic.management.configuration.JMSConnectionConsumerMBean;
import weblogic.management.utils.GenericBeanListener;

public class BEConnectionConsumerRuntimeDelegate {
   private JMSConnectionConsumerMBean mbean;
   private BEConnectionConsumerCommon consumer;
   private static final HashMap connectionConsumerSignatures = new HashMap();
   private GenericBeanListener ccListener;

   BEConnectionConsumerRuntimeDelegate(BEConnectionConsumerCommon consumer, JMSConnectionConsumerMBean mbean) throws JMSException {
      this.mbean = mbean;
      this.consumer = consumer;
      consumer.setName(mbean.getName());
      this.ccListener = new GenericBeanListener(mbean, consumer, connectionConsumerSignatures);
   }

   void close() {
      if (this.ccListener != null) {
         this.ccListener.close();
      }

   }

   static {
      connectionConsumerSignatures.put("MessagesMaximum", Integer.TYPE);
   }
}
