package weblogic.jms.backend;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.jms.common.JMSConsumerReceiveResponse;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.dispatcher.Request;
import weblogic.messaging.dispatcher.Response;
import weblogic.messaging.kernel.KernelRequest;
import weblogic.transaction.internal.TransactionImpl;

public final class BEConsumerReceiveRequest extends Request implements Externalizable {
   static final long serialVersionUID = -4180296985320716407L;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   static final int START = 0;
   static final int CONTINUE = 1;
   static final int PAGEIN = 2;
   private long timeout;
   private transient TransactionImpl transaction;
   private transient KernelRequest kernelRequest;

   public BEConsumerReceiveRequest(JMSID consumerId, long timeout) {
      super(consumerId, 11025);
      this.timeout = timeout;
   }

   long getTimeout() {
      return this.timeout;
   }

   void setTransaction(TransactionImpl transaction) {
      this.transaction = transaction;
   }

   TransactionImpl getTransaction() {
      return this.transaction;
   }

   boolean isTransactional() {
      return this.transaction != null;
   }

   public int remoteSignature() {
      return 35;
   }

   public boolean isServerToServer() {
      return true;
   }

   public Response createResponse() {
      return new JMSConsumerReceiveResponse();
   }

   public BEConsumerReceiveRequest() {
   }

   void setKernelRequest(KernelRequest kr) {
      this.kernelRequest = kr;
   }

   KernelRequest getKernelRequest() {
      return this.kernelRequest;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeInt(1);
      super.writeExternal(out);
      out.writeLong(this.timeout);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int mask = in.readInt();
      int version = mask & 255;
      if (version != 1) {
         throw JMSUtilities.versionIOException(version, 1, 1);
      } else {
         super.readExternal(in);
         this.timeout = in.readLong();
      }
   }
}
