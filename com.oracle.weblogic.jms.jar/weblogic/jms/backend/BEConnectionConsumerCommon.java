package weblogic.jms.backend;

import javax.jms.ConnectionConsumer;
import javax.jms.JMSException;
import weblogic.management.configuration.JMSConnectionConsumerMBean;

public interface BEConnectionConsumerCommon extends BEConsumerCommon, ConnectionConsumer {
   void initialize(JMSConnectionConsumerMBean var1) throws JMSException;

   void start() throws JMSException;

   void stop();

   int getMessagesMaximum();

   void setMessagesMaximum(int var1);
}
