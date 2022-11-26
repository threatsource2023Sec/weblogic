package weblogic.diagnostics.archive.wlstore;

import java.io.Serializable;
import weblogic.store.PersistentHandle;

class IndexMetaInfo implements Serializable, Comparable {
   static final long serialVersionUID = 1234L;
   private long lowTimestamp;
   private long highTimestamp;
   private long lowRecordId;
   private long highRecordId;
   private transient PersistentHandle metaHandle;
   private PersistentHandle indexPageHandle;

   long getLowTimestamp() {
      return this.lowTimestamp;
   }

   void setLowTimestamp(long lowTimestamp) {
      this.lowTimestamp = lowTimestamp;
   }

   long getHighTimestamp() {
      return this.highTimestamp;
   }

   void setHighTimestamp(long highTimestamp) {
      this.highTimestamp = highTimestamp;
   }

   long getLowRecordId() {
      return this.lowRecordId;
   }

   void setLowRecordId(long lowRecordId) {
      this.lowRecordId = lowRecordId;
   }

   long getHighRecordId() {
      return this.highRecordId;
   }

   void setHighRecordId(long highRecordId) {
      this.highRecordId = highRecordId;
   }

   boolean hasBoundsInfo() {
      return this.lowTimestamp != 0L && this.highTimestamp != 0L && this.lowRecordId != 0L && this.highRecordId != 0L;
   }

   PersistentHandle getIndexPageHandle() {
      return this.indexPageHandle;
   }

   void setIndexPageHandle(PersistentHandle indexPageHandle) {
      this.indexPageHandle = indexPageHandle;
   }

   PersistentHandle getMetaHandle() {
      return this.metaHandle;
   }

   void setMetaHandle(PersistentHandle metaHandle) {
      this.metaHandle = metaHandle;
   }

   public int compareTo(Object o) throws ClassCastException {
      IndexMetaInfo info = (IndexMetaInfo)o;
      if (info == null) {
         return -1;
      } else if (this.lowTimestamp < info.lowTimestamp) {
         return -1;
      } else {
         return this.lowTimestamp > info.lowTimestamp ? 1 : 0;
      }
   }

   public int checkInterval(long value, boolean useTimestamp) {
      if (useTimestamp) {
         if (value < this.lowTimestamp) {
            return -1;
         }

         if (value > this.highTimestamp) {
            return 1;
         }
      } else {
         if (value < this.lowRecordId) {
            return -1;
         }

         if (value > this.highRecordId) {
            return 1;
         }
      }

      return 0;
   }

   public boolean equals(Object obj) {
      if (obj != null && obj instanceof IndexMetaInfo) {
         if (obj == this) {
            return true;
         } else {
            IndexMetaInfo info = (IndexMetaInfo)obj;
            return this.lowTimestamp == info.lowTimestamp && this.highTimestamp == info.highTimestamp && this.lowRecordId == info.lowRecordId && this.highRecordId == info.highRecordId;
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int retVal = 17;
      retVal = retVal * 31 + (int)(this.lowTimestamp & -1L);
      retVal = retVal * 31 + (int)(this.lowTimestamp >> 32);
      retVal = retVal * 31 + (int)(this.highTimestamp & -1L);
      retVal = retVal * 31 + (int)(this.highTimestamp >> 32);
      retVal = retVal * 31 + (int)(this.lowRecordId & -1L);
      retVal = retVal * 31 + (int)(this.lowRecordId >> 32);
      retVal = retVal * 31 + (int)(this.highRecordId & -1L);
      retVal = retVal * 31 + (int)(this.highRecordId >> 32);
      return retVal;
   }

   public String toString() {
      return "Meta{loTs=" + this.lowTimestamp + ",hiTs=" + this.highTimestamp + ",loId=" + this.lowRecordId + ",hiId=" + this.highRecordId + ",handle=" + this.indexPageHandle + "}";
   }
}
