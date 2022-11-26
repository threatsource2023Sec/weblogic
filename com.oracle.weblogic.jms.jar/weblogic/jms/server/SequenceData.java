package weblogic.jms.server;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.jms.common.JMSUtilities;

public class SequenceData implements Externalizable, Cloneable {
   static final long serialVersionUID = 7571220896297616034L;
   private static final int EXTVERSION1 = 65536;
   private static final int VERSION_MASK = 983040;
   private static final int PROPERTY_UOO = 1;
   private static final int SAF_STICKY_ROUTING = 4096;
   String uooName;
   boolean safStickyRouting;

   public void setSAFStickyRouting(boolean arg) {
      this.safStickyRouting = arg;
   }

   public void setUnitOfOrder(String arg) {
      this.uooName = arg;
   }

   public boolean getSAFStickyRouting() {
      return this.safStickyRouting;
   }

   public String getUnitOfOrder() {
      return this.uooName;
   }

   public SequenceData copy() {
      try {
         return (SequenceData)this.clone();
      } catch (CloneNotSupportedException var2) {
         throw new AssertionError(var2);
      }
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof SequenceData)) {
         return false;
      } else {
         SequenceData sequenceData = (SequenceData)o;
         if (this.safStickyRouting != sequenceData.safStickyRouting) {
            return false;
         } else if (this.uooName == null) {
            return sequenceData.uooName == null;
         } else {
            return this.uooName.equals(sequenceData.uooName);
         }
      }
   }

   public int hashCode() {
      int result = 0;
      if (this.safStickyRouting) {
         result = 4096;
      }

      if (this.uooName != null) {
         result |= this.uooName.hashCode();
      }

      return result;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      String stableName = this.uooName;
      int flags;
      if (stableName == null) {
         flags = 65536;
      } else {
         flags = 65537;
      }

      if (this.safStickyRouting) {
         flags = 4096;
      }

      out.writeInt(flags);
      if (stableName != null) {
         out.writeUTF(stableName);
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int flags = in.readInt();
      int vrsn = flags & 983040;
      if (vrsn != 65536) {
         throw JMSUtilities.versionIOException(vrsn, 65536, 65536);
      } else {
         this.safStickyRouting = (flags & 4096) != 0;
         if ((flags & 1) != 0) {
            this.uooName = in.readUTF();
         }

      }
   }
}
