package org.apache.openjpa.kernel;

import org.apache.openjpa.lib.util.UUIDGenerator;
import org.apache.openjpa.meta.ClassMetaData;

public class UUIDHexSeq implements Seq {
   private static final UUIDHexSeq _instance = new UUIDHexSeq();
   private String _last = null;

   public static UUIDHexSeq getInstance() {
      return _instance;
   }

   private UUIDHexSeq() {
   }

   public void setType(int type) {
   }

   public synchronized Object next(StoreContext ctx, ClassMetaData meta) {
      this._last = UUIDGenerator.nextHex();
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
