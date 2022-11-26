package weblogic.jms.frontend;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.jms.common.JMSConsumerSetListenerResponse;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.dispatcher.Request;
import weblogic.messaging.dispatcher.Response;

public final class FEConsumerSetListenerRequest extends Request implements Externalizable {
   static final long serialVersionUID = 7973089431545348286L;
   private boolean hasListener;
   private long lastSequenceNumber;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private static final int HAS_LISTENER_MASK = 256;
   private static final int SEQUENCE_NUMBER_MASK = 512;
   static final int START = 0;
   static final int CONTINUE = 1;

   public FEConsumerSetListenerRequest(JMSID consumerId, boolean hasListener, long lastSequenceNumber) {
      super(consumerId, 3594);
      this.hasListener = hasListener;
      this.lastSequenceNumber = lastSequenceNumber;
   }

   boolean getHasListener() {
      return this.hasListener;
   }

   long getLastSequenceNumber() {
      return this.lastSequenceNumber;
   }

   public int remoteSignature() {
      return 18;
   }

   public Response createResponse() {
      return new JMSConsumerSetListenerResponse();
   }

   public FEConsumerSetListenerRequest() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out = this.getVersionedStream(out);
      int mask = 1;
      if (this.hasListener) {
         mask |= 256;
      }

      if (this.lastSequenceNumber != 0L) {
         mask |= 512;
      }

      out.writeInt(mask);
      super.writeExternal(out);
      if (this.lastSequenceNumber != 0L) {
         out.writeLong(this.lastSequenceNumber);
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int mask = in.readInt();
      int version = mask & 255;
      if (version != 1) {
         throw JMSUtilities.versionIOException(version, 1, 1);
      } else {
         super.readExternal(in);
         if ((mask & 512) != 0) {
            this.lastSequenceNumber = in.readLong();
         }

         this.hasListener = (mask & 256) != 0;
      }
   }
}
