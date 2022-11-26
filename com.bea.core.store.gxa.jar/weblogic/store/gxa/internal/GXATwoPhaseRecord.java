package weblogic.store.gxa.internal;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.store.PersistentHandle;

public final class GXATwoPhaseRecord implements Externalizable {
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 65535;
   private static final int COMMITTED_FLAG = 65536;
   static final long serialVersionUID = 19085803045935935L;
   private GXidImpl gxid;
   private PersistentHandle persistentHandle;
   private boolean isCommitted;
   private long timeStamp;

   public GXATwoPhaseRecord(GXidImpl gxid, boolean isCommitted) {
      this.gxid = gxid;
      this.isCommitted = isCommitted;
      this.timeStamp = System.currentTimeMillis();
   }

   public GXATwoPhaseRecord() {
   }

   GXidImpl getGXid() {
      return this.gxid;
   }

   boolean isCommitted() {
      return this.isCommitted;
   }

   long getTimeStamp() {
      return this.timeStamp;
   }

   void setPersistentHandle(PersistentHandle persistentHandle) {
      this.persistentHandle = persistentHandle;
   }

   PersistentHandle getPersistentHandle() {
      return this.persistentHandle;
   }

   public void writeExternal(ObjectOutput oo) throws IOException {
      int flags = 1;
      if (this.isCommitted) {
         flags |= 65536;
      }

      oo.writeInt(flags);
      this.gxid.writeExternal(oo);
      oo.writeLong(this.timeStamp);
   }

   public void readExternal(ObjectInput oi) throws IOException, ClassNotFoundException {
      int flags = oi.readInt();
      int version = flags & '\uffff';
      if (version != 1) {
         throw new IOException("Unexpected version " + version + ", expected " + 1);
      } else {
         this.gxid = new GXidImpl();
         this.gxid.readExternal(oi);
         this.timeStamp = oi.readLong();
         this.isCommitted = (flags & 65536) != 0;
      }
   }
}
