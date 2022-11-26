package weblogic.logging.jms;

import javax.transaction.xa.Xid;

public class JMSMessageForwardLogRecord extends JMSMessageLogRecord {
   public JMSMessageForwardLogRecord(long eventTimeMillisStamp, long eventTimeNanoStamp, String logMsg, String jmsDest, String jmsMsgId, String jmsCorrelationId, String user, String subscriptionName, Xid xid) {
      super(eventTimeMillisStamp, eventTimeNanoStamp, logMsg, jmsDest, jmsMsgId, jmsCorrelationId, user, subscriptionName, xid);
   }

   public String getJMSMessageState() {
      return JMSMessageLogRecord.JMSMessageStates[11];
   }
}
