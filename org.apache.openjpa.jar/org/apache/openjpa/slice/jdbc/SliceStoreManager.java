package org.apache.openjpa.slice.jdbc;

import org.apache.openjpa.jdbc.kernel.JDBCStoreManager;
import org.apache.openjpa.slice.Slice;

public class SliceStoreManager extends JDBCStoreManager {
   private final Slice _slice;

   public SliceStoreManager(Slice slice) {
      this._slice = slice;
   }

   public Slice getSlice() {
      return this._slice;
   }

   public String getName() {
      return this._slice.getName();
   }
}
