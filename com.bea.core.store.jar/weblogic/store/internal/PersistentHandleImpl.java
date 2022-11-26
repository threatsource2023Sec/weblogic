package weblogic.store.internal;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.store.PersistentHandle;
import weblogic.store.PersistentStoreRuntimeException;
import weblogic.store.StoreLogger;

public final class PersistentHandleImpl extends PersistentHandle {
   private static final byte VERSION09 = 1;
   private static final byte VERSION10 = 10;
   private static final byte VERSION1034 = 11;
   static final long serialVersionUID = 3415097801897820922L;
   private int typeCode;
   private int storeHandle;

   public PersistentHandleImpl() {
   }

   PersistentHandleImpl(int typeCode, int storeHandle) {
      this.storeHandle = storeHandle;
      this.typeCode = typeCode;
   }

   private int unsyncStoreHandle() {
      return Integer.MAX_VALUE & this.storeHandle;
   }

   synchronized int getStoreHandle() {
      return this.unsyncStoreHandle();
   }

   synchronized void setTypeCode(int typeCode) {
      this.typeCode = typeCode;
   }

   synchronized int getTypeCode() {
      return this.typeCode;
   }

   synchronized boolean isDeleted() {
      return this.storeHandle < 0;
   }

   synchronized void setDeleted(boolean deleted) {
      if (deleted) {
         this.storeHandle |= Integer.MIN_VALUE;
      } else {
         this.storeHandle &= Integer.MAX_VALUE;
      }

   }

   static PersistentHandleImpl check(PersistentStoreConnectionImpl conn, PersistentHandle handle) {
      if (!(handle instanceof PersistentHandleImpl)) {
         throw new PersistentStoreRuntimeException(StoreLogger.logInvalidRecordHandleLoggable(0L));
      } else {
         PersistentHandleImpl ret = (PersistentHandleImpl)handle;
         if (ret.isDeleted()) {
            throw new PersistentStoreRuntimeException(StoreLogger.logStoreRecordAlreadyDeletedLoggable((long)ret.getStoreHandle(), conn.getStore().getName(), conn.getName()));
         } else if (ret.getTypeCode() != 0 && ret.getTypeCode() != conn.getTypeCode()) {
            throw new PersistentStoreRuntimeException(StoreLogger.logWrongConnectionForHandleLoggable());
         } else {
            return ret;
         }
      }
   }

   public synchronized void writeExternal(ObjectOutput oo) throws IOException {
      oo.writeByte(11);
      oo.writeInt(this.storeHandle);
   }

   public synchronized void readExternal(ObjectInput oi) throws IOException {
      byte version = oi.readByte();
      if (version == 1 || version >= 10 && version <= 11) {
         this.storeHandle = oi.readInt();
         if (version < 11 && this.storeHandle < 0) {
            this.storeHandle = -this.storeHandle | Integer.MIN_VALUE;
         }

      } else {
         throw new IOException("Invalid version: '" + version + "', expected version '" + 1 + "' or [" + 10 + "," + 11 + "]'.  Older stores can not read records created by later releases.");
      }
   }

   public synchronized int hashCode() {
      return this.typeCode ^ this.unsyncStoreHandle();
   }

   public synchronized boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof PersistentHandleImpl)) {
         return false;
      } else {
         PersistentHandleImpl other = (PersistentHandleImpl)o;
         return this.typeCode == other.typeCode && this.unsyncStoreHandle() == other.getStoreHandle();
      }
   }

   public synchronized String toString() {
      return this.typeCode + ":" + this.unsyncStoreHandle() + ": deleted=" + (this.storeHandle < 0);
   }
}
