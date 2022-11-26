package weblogic.messaging.saf.internal;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.server.SequenceData;
import weblogic.messaging.saf.SAFConversationInfo;
import weblogic.messaging.saf.common.SAFConversationInfoImpl;

public class SAFSequenceData extends SequenceData {
   static final long serialVersionUID = -2611753600731762875L;
   private static final int EXTVERSION1 = 1;
   private static final int VERSION_MASK = 255;
   private static final int _HASINFO = 256;
   private static final int _HASLASTMSGSEQUENCENUMBER = 512;
   private SAFConversationInfo info;
   private long lastMsgSequenceNumber = Long.MAX_VALUE;

   public SAFSequenceData(SAFConversationInfo info) {
      this.info = info;
      this.setUnitOfOrder(info.getConversationName());
   }

   public SAFSequenceData() {
   }

   public SAFConversationInfo getConversationInfo() {
      return this.info;
   }

   public void setConversationInfo(SAFConversationInfo info) {
      this.info = info;
   }

   public synchronized long getLastMsgSequenceNumber() {
      return this.lastMsgSequenceNumber;
   }

   public synchronized void setLastMsgSequenceNumber(long lastMsgSequenceNumber) {
      this.lastMsgSequenceNumber = lastMsgSequenceNumber;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof SAFSequenceData)) {
         return false;
      } else {
         SequenceData sequenceData = (SequenceData)o;
         if (this.info != null && !this.info.equals(((SAFSequenceData)o).getConversationInfo())) {
            return false;
         } else if (((SAFSequenceData)o).getConversationInfo() != null && !((SAFSequenceData)o).getConversationInfo().equals(this.info)) {
            return false;
         } else {
            return this.lastMsgSequenceNumber == ((SAFSequenceData)o).getLastMsgSequenceNumber();
         }
      }
   }

   public int hashCode() {
      int result = super.hashCode();
      if (this.info != null) {
         result |= this.info.hashCode();
      }

      if (this.lastMsgSequenceNumber != Long.MAX_VALUE) {
         result = (int)((long)result | this.lastMsgSequenceNumber);
      }

      return result;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      int flags = 1;
      super.writeExternal(out);
      if (this.info != null) {
         flags |= 256;
      }

      if (this.lastMsgSequenceNumber != Long.MAX_VALUE) {
         flags |= 512;
      }

      out.writeInt(flags);
      if (this.info != null) {
         this.info.writeExternal(out);
      }

      if (this.lastMsgSequenceNumber != 0L) {
         out.writeLong(this.lastMsgSequenceNumber);
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      super.readExternal(in);
      int flags = in.readInt();
      int vrsn = flags & 255;
      if (vrsn != 1) {
         throw JMSUtilities.versionIOException(vrsn, 1, 1);
      } else {
         if ((flags & 256) != 0) {
            this.info = new SAFConversationInfoImpl();
            this.info.readExternal(in);
         }

         if ((flags & 512) != 0) {
            this.lastMsgSequenceNumber = in.readLong();
         }

      }
   }
}
