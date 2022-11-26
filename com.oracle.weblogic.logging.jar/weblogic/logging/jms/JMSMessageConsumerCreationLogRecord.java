package weblogic.logging.jms;

import javax.transaction.xa.Xid;

public class JMSMessageConsumerCreationLogRecord extends JMSMessageLogRecord {
   private String selector = null;

   public JMSMessageConsumerCreationLogRecord(long eventTimeMillisStamp, long eventTimeNanoStamp, String jmsDest, String user, String subscriptionName, String selector) {
      super(eventTimeMillisStamp, eventTimeNanoStamp, (String)null, jmsDest, (String)null, (String)null, user, subscriptionName, (Xid)null);
      this.selector = selector;
   }

   public String getJMSMessageState() {
      return JMSMessageLogRecord.JMSMessageStates[8];
   }

   public String getSelector() {
      return this.selector;
   }
}
