package weblogic.logging.jms;

import javax.transaction.xa.Xid;

public class JMSMessageConsumerDestroyLogRecord extends JMSMessageLogRecord {
   public JMSMessageConsumerDestroyLogRecord(long eventTimeMillisStamp, long eventTimeNanoStamp, String jmsDest, String user, String subscriptionName) {
      super(eventTimeMillisStamp, eventTimeNanoStamp, (String)null, jmsDest, (String)null, (String)null, user, subscriptionName, (Xid)null);
   }

   public String getJMSMessageState() {
      return JMSMessageLogRecord.JMSMessageStates[9];
   }
}
