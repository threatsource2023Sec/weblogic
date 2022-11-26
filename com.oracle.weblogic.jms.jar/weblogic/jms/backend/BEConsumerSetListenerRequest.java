package weblogic.jms.backend;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.jms.common.JMSConsumerSetListenerResponse;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.dispatcher.Request;
import weblogic.messaging.dispatcher.Response;

public final class BEConsumerSetListenerRequest extends Request implements Externalizable {
   static final long serialVersionUID = 3829823272672918810L;
   private boolean hasListener;
   private long lastSequenceNumber;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private static final int HAS_LISTENER_MASK = 256;

   public BEConsumerSetListenerRequest(JMSID consumerId, boolean hasListener, long lastSequenceNumber) {
      super(consumerId, 11281);
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
      return 34;
   }

   public boolean isServerToServer() {
      return true;
   }

   public Response createResponse() {
      return new JMSConsumerSetListenerResponse();
   }

   public BEConsumerSetListenerRequest() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      int mask = 1;
      if (this.hasListener) {
         mask |= 256;
      }

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
         this.hasListener = (mask & 256) != 0;
      }
   }
}
