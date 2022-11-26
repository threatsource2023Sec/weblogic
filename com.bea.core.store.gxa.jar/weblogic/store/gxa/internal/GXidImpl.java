package weblogic.store.gxa.internal;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.StreamCorruptedException;
import javax.transaction.xa.Xid;
import weblogic.store.gxa.GXid;
import weblogic.transaction.XIDFactory;

public final class GXidImpl extends GXid implements Externalizable {
   private static final byte EXTVERSION = 1;
   private static final int VERSION_MASK = 15;
   static final long serialVersionUID = 68757062319679355L;
   private byte[] gtrid;
   private byte[] bqual;
   private transient int hashCode;
   private int formatId;
   private transient Xid originalXid;

   public GXidImpl() {
   }

   public GXidImpl(Xid xid) {
      this.gtrid = xid.getGlobalTransactionId();
      this.bqual = xid.getBranchQualifier();
      this.formatId = xid.getFormatId();
      this.originalXid = xid;
   }

   public Xid getXAXid() {
      return this.originalXid;
   }

   public boolean equals(Object obj) {
      if (obj instanceof GXidImpl) {
         GXidImpl x = (GXidImpl)obj;
         if (x.gtrid.length == this.gtrid.length) {
            for(int i = 0; i < this.gtrid.length; ++i) {
               if (x.gtrid[i] != this.gtrid[i]) {
                  return false;
               }
            }

            return true;
         } else {
            return false;
         }
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public int hashCode() {
      if (this.hashCode != 0) {
         return this.hashCode;
      } else {
         for(int i = this.gtrid.length - 1; i >= 0; --i) {
            this.hashCode += this.gtrid[i] & 255;
         }

         if (this.hashCode == 0) {
            this.hashCode = 1;
         }

         return this.hashCode;
      }
   }

   public void writeExternal(ObjectOutput oo) throws IOException {
      oo.writeByte(1);
      oo.writeInt(this.formatId);
      oo.writeByte((byte)this.gtrid.length);
      oo.write(this.gtrid);
      if (this.bqual == null) {
         oo.writeByte(-1);
      } else {
         oo.writeByte((byte)this.bqual.length);
         oo.write(this.bqual);
      }

   }

   public void readExternal(ObjectInput oi) throws IOException {
      int flags = oi.readByte();
      int vrsn = flags & 15;
      if (vrsn != 1) {
         throw versionIOException(vrsn, 1, 1);
      } else {
         this.formatId = oi.readInt();
         int len = oi.readByte();
         if (len < 0) {
            throw new IOException("Stream corrupted.");
         } else {
            this.gtrid = new byte[len];
            oi.readFully(this.gtrid);
            len = oi.readByte();
            if (len < -1) {
               throw new IOException("Stream corrupted.");
            } else {
               if (len > -1) {
                  this.bqual = new byte[len];
                  oi.readFully(this.bqual);
               }

               this.originalXid = XIDFactory.createXID(this.formatId, this.gtrid, this.bqual);
            }
         }
      }
   }

   private static StreamCorruptedException versionIOException(int version, int minExpectedVersion, int maxExpectedVersion) {
      return new StreamCorruptedException("Unsupported class version " + version + ".  Expected a value between " + minExpectedVersion + " and " + maxExpectedVersion + " inclusive." + (version > minExpectedVersion ? "  Possible attempt to access newer version then current version." : "  Possible attempt to access unsupported older version."));
   }

   public String toString() {
      return this.originalXid.toString();
   }

   String toString(boolean includeBranchQualifier) {
      return XIDFactory.xidToString(this.originalXid, includeBranchQualifier);
   }
}
