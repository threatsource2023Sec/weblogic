package weblogic.messaging.dispatcher;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.common.internal.PeerInfoableObjectOutput;
import weblogic.jms.common.JMSUtilities;

public class Response implements Externalizable {
   static final long serialVersionUID = -4057384450154825617L;
   public static final boolean CHECK = false;
   private static final byte EXTVERSION = 1;
   private static final byte VERSION_MASK = 31;
   private static final byte CORRELATION_ID_MASK = 32;
   private static final byte PAYLOAD_MASK = 64;
   private PeerInfo peerInfo;
   private static SimpleDateFormat sdf = new SimpleDateFormat("(EEE MMM dd, HH:mm:ss.SSS)");

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeByte(1);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int vrsn = in.readByte();
      if ((vrsn & 31) != 1) {
         throw JMSUtilities.versionIOException(vrsn, 1, 1);
      } else {
         if ((vrsn & 32) != 0) {
            in.readLong();
         }

         if ((vrsn & 64) != 0) {
            in.readObject();
         }

      }
   }

   public static void instanceOf(Object theObject, Class theClass) {
      if (!theClass.isInstance(theObject)) {
         if (theObject != null || weblogic.jms.dispatcher.VoidResponse.class != theClass) {
            throw new AssertionError("" + theObject + " is not an instance of " + theClass.getName());
         }
      }
   }

   public final void setPeerInfo(PeerInfo peerInfo) {
      this.peerInfo = peerInfo;
   }

   public final ObjectOutput getVersionedStream(ObjectOutput oo) {
      if (oo instanceof PeerInfoable) {
         return oo;
      } else {
         assert this.peerInfo != null;

         return new PeerInfoableObjectOutput(this.peerInfo, oo);
      }
   }

   private static String timeString() {
      return sdf.format(new Date(System.currentTimeMillis()));
   }

   private String responseName() {
      return this.responseThreadTime() + " " + this.getClass().getName() + ", " + this;
   }

   private String responseThreadTime() {
      return "@" + timeString() + " Response Thread:" + Thread.currentThread().getName();
   }

   public String toDbgString() {
      return this.responseName();
   }

   static {
      sdf.setTimeZone(TimeZone.getDefault());
   }
}
