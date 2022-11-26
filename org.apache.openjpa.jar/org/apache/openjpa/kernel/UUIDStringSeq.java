package org.apache.openjpa.kernel;

import org.apache.openjpa.lib.util.UUIDGenerator;
import org.apache.openjpa.meta.ClassMetaData;

public class UUIDStringSeq implements Seq {
   private static final UUIDStringSeq _instance = new UUIDStringSeq();
   private String _last = null;

   public static UUIDStringSeq getInstance() {
      return _instance;
   }

   private UUIDStringSeq() {
   }

   public void setType(int type) {
   }

   public synchronized Object next(StoreContext ctx, ClassMetaData meta) {
      this._last = UUIDGenerator.nextString();
      return this._last;
   }

   public synchronized Object current(StoreContext ctx, ClassMetaData meta) {
      return this._last;
   }

   public void allocate(int additional, StoreContext ctx, ClassMetaData meta) {
   }

   public void close() {
   }
}
