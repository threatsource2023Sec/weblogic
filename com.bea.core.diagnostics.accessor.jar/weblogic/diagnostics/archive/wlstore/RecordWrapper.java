package weblogic.diagnostics.archive.wlstore;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import weblogic.diagnostics.accessor.DataRecord;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.type.UnexpectedExceptionHandler;
import weblogic.store.PersistentHandle;
import weblogic.store.PersistentStoreException;

class RecordWrapper implements Externalizable {
   static final long serialVersionUID = 12345L;
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugDiagnosticArchive");
   PersistentHandle prev;
   PersistentHandle next;
   long startId;
   long endId;
   long timestamp;
   transient long endTimestamp;
   Object record;
   transient Object dataObject;
   transient boolean deleted;

   public RecordWrapper() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeObject(this.prev);
      out.writeObject(this.next);
      out.writeLong(this.startId);
      out.writeLong(this.endId);
      out.writeLong(this.timestamp);
      out.writeObject(this.record);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.prev = (PersistentHandle)in.readObject();
      this.next = (PersistentHandle)in.readObject();
      this.startId = in.readLong();
      this.endId = in.readLong();
      this.timestamp = in.readLong();
      this.record = in.readObject();
   }

   public Object getDataObject(PersistentStoreDataArchive archive) {
      if (this.dataObject != null) {
         return this.dataObject;
      } else if (this.deleted) {
         return null;
      } else {
         if (this.record instanceof PersistentHandle) {
            try {
               try {
                  this.dataObject = archive.readRecordObject((PersistentHandle)this.record);
               } catch (PersistentStoreException var5) {
                  this.deleted = true;
                  if (DEBUG.isDebugEnabled()) {
                     DEBUG.debug("RecordWrapper: could not read record=" + this.record, var5);
                  }
               }

               if (this.dataObject instanceof Snapshot) {
                  ArrayList data = (ArrayList)((Snapshot)this.dataObject).getData();
                  int size = data.size();
                  if (size > 0) {
                     DataRecord lastRecord = (DataRecord)data.get(size - 1);
                     this.endId = (Long)lastRecord.get(0);
                     this.endTimestamp = (Long)lastRecord.get(1);
                  }
               }
            } catch (Exception var6) {
               UnexpectedExceptionHandler.handle("Could not read dataObject for handle=" + this.record, var6);
            }
         } else {
            this.dataObject = this.record;
            this.endTimestamp = this.timestamp;
         }

         return this.dataObject;
      }
   }

   public void setDataObject(Object dataObject) {
      this.dataObject = dataObject;
   }

   public boolean isIdWithinRange(long start, long end) {
      boolean retVal = start <= this.startId && this.startId < end || start <= this.endId && this.endId < end || this.startId <= start && start <= this.endId || this.startId < end && end <= this.endId;
      return retVal;
   }

   public boolean isTimestampWithinRange(long start, long end) {
      boolean retVal = start <= this.timestamp && this.timestamp < end || start <= this.endTimestamp && this.endTimestamp < end || this.timestamp <= start && start <= this.endTimestamp || this.timestamp < end && end <= this.endTimestamp;
      return retVal;
   }

   public String toString() {
      return "RecordWrapper{startId=" + this.startId + " endId=" + this.endId + " timestamp=" + this.timestamp + " endTimestamp=" + this.endTimestamp + ",next=" + this.next + ",prev=" + this.prev + ",record=" + this.record + ",dataObject=" + this.dataObject + "}";
   }
}
