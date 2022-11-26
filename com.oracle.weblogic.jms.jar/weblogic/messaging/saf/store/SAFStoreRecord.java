package weblogic.messaging.saf.store;

import weblogic.store.PersistentHandle;
import weblogic.store.PersistentStoreException;
import weblogic.store.PersistentStoreRecord;

public final class SAFStoreRecord {
   private final PersistentStoreRecord record;
   private SAFStoreRecord next;

   public SAFStoreRecord(PersistentStoreRecord record) {
      this.record = record;
   }

   public Object getStoreObject() throws PersistentStoreException {
      return this.record.getData();
   }

   public PersistentHandle getHandle() {
      return this.record.getHandle();
   }

   public final void setNext(SAFStoreRecord record) {
      this.next = record;
   }

   public final SAFStoreRecord getNext() {
      return this.next;
   }
}
