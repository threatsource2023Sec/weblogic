package weblogic.messaging.saf.common;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.messaging.saf.utils.SAFClientUtil;

public final class SAFRemoteContext implements Externalizable {
   static final long serialVersionUID = 8077095725140746031L;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private long retryDelayBase = -1L;
   private long retryDelayMaximum = -1L;
   private long retryDelayMultiplier = -1L;
   private static final int _HASRETRYDELAYPARAMATERS = 256;
   private static final int _HASPROPERTIES = 512;
   private static final int _HASDEFAULTCOMPRESSIONTHRESHOLD = 1024;

   public SAFRemoteContext(long retryDelayBase, long retryDelayMaximum, long retryDelayMultiplier) {
      this.retryDelayBase = retryDelayBase;
      this.retryDelayMaximum = retryDelayMaximum;
      this.retryDelayMultiplier = retryDelayMultiplier;
   }

   public SAFRemoteContext() {
   }

   public long getRetryDelayBase() {
      return this.retryDelayBase;
   }

   public long getRetryDelayMaximum() {
      return this.retryDelayMaximum;
   }

   public long getRetryDelayMultiplier() {
      return this.retryDelayMultiplier;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      int flags = 1;
      if (this.retryDelayBase != 0L) {
         flags |= 256;
      }

      out.writeInt(flags);
      out.writeInt(2);
      out.writeUTF("");
      if (this.retryDelayBase != 0L) {
         out.writeLong(this.retryDelayBase);
         out.writeLong(this.retryDelayMaximum);
         out.writeLong(this.retryDelayMultiplier);
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int flags = in.readInt();
      int version = flags & 255;
      if (version != 1) {
         throw SAFClientUtil.versionIOException(version, 1, 1);
      } else {
         in.readInt();
         in.readUTF();
         if ((flags & 256) != 0) {
            this.retryDelayBase = in.readLong();
            this.retryDelayMaximum = in.readLong();
            this.retryDelayMultiplier = in.readLong();
         }

         if ((flags & 1024) != 0) {
            in.readInt();
         }

      }
   }
}
