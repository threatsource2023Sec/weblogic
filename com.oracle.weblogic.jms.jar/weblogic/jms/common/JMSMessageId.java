package weblogic.jms.common;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.jms.JMSIDFactories;
import weblogic.messaging.common.MessageIDFactory;
import weblogic.messaging.common.MessageIDImpl;

public final class JMSMessageId extends MessageIDImpl {
   static final long serialVersionUID = 3784286757441851850L;
   private static MessageIDFactory messageIDFactory;

   public static JMSMessageId create() {
      return new JMSMessageId(true);
   }

   private JMSMessageId(boolean ignored) {
      super(messageIDFactory);
   }

   public JMSMessageId(int seed, long timestamp, int counter) {
      super(seed, timestamp, counter);
   }

   public JMSMessageId(JMSMessageId messageId, int differentiator) {
      super(messageId, differentiator);
   }

   public int getSeed() {
      return this.seed;
   }

   public long getTimestamp() {
      return this.timestamp;
   }

   public int getCounter() {
      return this.counter;
   }

   public boolean equals(Object o) {
      return !(o instanceof JMSMessageId) ? false : super.equals(o);
   }

   public JMSMessageId() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      super.writeExternal(out);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      super.readExternal(in);
   }

   static {
      messageIDFactory = JMSIDFactories.messageIDFactory;
   }
}
