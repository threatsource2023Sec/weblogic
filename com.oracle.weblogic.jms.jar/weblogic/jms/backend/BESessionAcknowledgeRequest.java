package weblogic.jms.backend;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ListIterator;
import javax.transaction.Transaction;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.dispatcher.Request;
import weblogic.jms.dispatcher.VoidResponse;
import weblogic.messaging.dispatcher.Response;
import weblogic.messaging.kernel.KernelRequest;

public final class BESessionAcknowledgeRequest extends Request implements Externalizable {
   static final long serialVersionUID = -496965679629368939L;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private static final int ACKNOWLEDGE_POLICY_MASK = 65280;
   private static final int ACKNOWLEDGE_POLICY_SHIFT = 8;
   public static final int ACK_IN_PROGRESS = 11000;
   public static final int ACK_COMPLETED = 11001;
   private int acknowledgePolicy;
   private long lastSequenceNumber;
   private transient KernelRequest kernelRequest;
   private transient Transaction transaction;
   private transient ListIterator iterator;

   public BESessionAcknowledgeRequest(JMSID sessionId, long lastSequenceNumber) {
      super(sessionId, 13072);
      this.lastSequenceNumber = lastSequenceNumber;
   }

   long getLastSequenceNumber() {
      return this.lastSequenceNumber;
   }

   public int remoteSignature() {
      return 35;
   }

   public boolean isServerToServer() {
      return true;
   }

   public Response createResponse() {
      return VoidResponse.THE_ONE;
   }

   void setIterator(ListIterator iterator) {
      this.iterator = iterator;
   }

   ListIterator getIterator() {
      return this.iterator;
   }

   void setKernelRequest(KernelRequest kr) {
      this.kernelRequest = kr;
   }

   KernelRequest getKernelRequest() {
      return this.kernelRequest;
   }

   Transaction getTransaction() {
      return this.transaction;
   }

   void setTransaction(Transaction tran) {
      this.transaction = tran;
   }

   boolean isTransactional() {
      return this.transaction != null;
   }

   public BESessionAcknowledgeRequest() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      int mask = 1 | this.acknowledgePolicy << 8;
      out.writeInt(mask);
      super.writeExternal(out);
      out.writeLong(this.lastSequenceNumber);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int mask = in.readInt();
      int version = mask & 255;
      if (version != 1) {
         throw JMSUtilities.versionIOException(version, 1, 1);
      } else {
         super.readExternal(in);
         this.lastSequenceNumber = in.readLong();
         this.acknowledgePolicy = (mask & '\uff00') >>> 8;
      }
   }
}
