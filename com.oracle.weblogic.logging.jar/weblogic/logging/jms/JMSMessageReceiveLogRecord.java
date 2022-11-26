package weblogic.logging.jms;

import javax.transaction.xa.Xid;

public class JMSMessageReceiveLogRecord extends JMSMessageLogRecord {
   public JMSMessageReceiveLogRecord(long eventTimeMillisStamp, long eventTimeNanoStamp, String logMsg, String jmsDest, String jmsMsgId, String jmsCorrelationId, String user, String subscriptionName, Xid xid) {
      super(eventTimeMillisStamp, eventTimeNanoStamp, logMsg, jmsDest, jmsMsgId, jmsCorrelationId, user, subscriptionName, xid);
   }

   public String getJMSMessageState() {
      return JMSMessageLogRecord.JMSMessageStates[1];
   }
}
