package weblogic.logging.jms;

import java.util.logging.LogRecord;
import javax.transaction.xa.Xid;

public abstract class JMSMessageLogRecord extends LogRecord {
   protected static final int PRODUCED = 0;
   protected static final int CONSUMED = 1;
   protected static final int EXPIRED = 2;
   protected static final int ADDED = 3;
   protected static final int RETRYEXCEEDED = 4;
   protected static final int ADMINPRODUCED = 5;
   protected static final int ADMINDELETED = 6;
   protected static final int REMOVED = 7;
   protected static final int CONSUMERCREATE = 8;
   protected static final int CONSUMERDESTROY = 9;
   protected static final int STORED = 10;
   protected static final int FORWARDED = 11;
   protected static final String[] JMSMessageStates = new String[]{"Produced", "Consumed", "Expired", "Added", "Retry exceeded", "Admin-produced", "Admin-deleted", "Removed", "ConsumerCreate", "ConsumerDestroy", "Stored", "Forwarded"};
   private Xid transactionId;
   private String diagCtxId;
   private String jmsDestinationName;
   private String jmsMessageId;
   private String jmsCorrelationId;
   private String user;
   private long eventTimeMillisStamp;
   private long eventTimeNanoStamp;
   private String subscriptionName;

   public JMSMessageLogRecord(long eventTimeMillisStamp, long eventTimeNanoStamp, String logMsg, String jmsDest, String jmsMsgId, String jmsCorrelationId, String user, String subscriptionName, Xid xid) {
      super(JMSMessageLevel.PERSISTENT_LEVEL, logMsg);
      this.eventTimeMillisStamp = eventTimeMillisStamp;
      this.eventTimeNanoStamp = eventTimeNanoStamp;
      this.jmsDestinationName = jmsDest;
      this.jmsMessageId = jmsMsgId;
      this.jmsCorrelationId = jmsCorrelationId;
      this.user = user;
      this.subscriptionName = subscriptionName;
      this.transactionId = xid;
   }

   public String getTransactionId() {
      return this.transactionId != null ? this.transactionId.toString() : null;
   }

   public long getEventTimeMillisStamp() {
      return this.eventTimeMillisStamp;
   }

   public long getEventTimeNanoStamp() {
      return this.eventTimeNanoStamp;
   }

   public String getDiagnosticContextId() {
      return this.diagCtxId;
   }

   public String getJMSDestinationName() {
      return this.jmsDestinationName;
   }

   public String getJMSMessageId() {
      return this.jmsMessageId;
   }

   public String getJMSCorrelationId() {
      return this.jmsCorrelationId;
   }

   public abstract String getJMSMessageState();

   public String getUser() {
      return this.user;
   }

   public String getDurableSubscriber() {
      return this.subscriptionName;
   }
}
