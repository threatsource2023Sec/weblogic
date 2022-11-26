package org.apache.openjpa.kernel;

import org.apache.openjpa.meta.ClassMetaData;
import serp.util.Numbers;

public class TimeSeededSeq implements Seq {
   private long _id = System.currentTimeMillis();
   private int _increment = 1;

   public void setType(int type) {
   }

   public int getIncrement() {
      return this._increment;
   }

   public void setIncrement(int increment) {
      this._increment = increment;
   }

   public synchronized Object next(StoreContext ctx, ClassMetaData meta) {
      this._id += (long)this._increment;
      return Numbers.valueOf(this._id);
   }

   public synchronized Object current(StoreContext ctx, ClassMetaData meta) {
      return Numbers.valueOf(this._id);
   }

   public void allocate(int additional, StoreContext ctx, ClassMetaData meta) {
   }

   public void close() {
   }
}
