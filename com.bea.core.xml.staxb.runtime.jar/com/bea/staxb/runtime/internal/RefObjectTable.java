package com.bea.staxb.runtime.internal;

import java.util.HashMap;
import java.util.Map;

final class RefObjectTable {
   private final Map refTable = new HashMap();

   Object getObjectForRef(String ref) {
      assert ref != null;

      RefEntry e = this.getEntryForRef(ref);
      return e == null ? null : e.final_obj;
   }

   Object getInterForRef(String ref) {
      assert ref != null;

      RefEntry e = this.getEntryForRef(ref);
      return e == null ? null : e.inter;
   }

   RefEntry getEntryForRef(String ref) {
      assert ref != null;

      return (RefEntry)this.refTable.get(ref);
   }

   void putForRef(String ref, Object inter, Object actual_obj) {
      assert ref != null;

      this.refTable.put(ref, new RefEntry(inter, actual_obj));
   }

   void putObjectForRef(String ref, Object val) {
      assert ref != null;

      RefEntry e = (RefEntry)this.refTable.get(ref);

      assert e != null;

      assert e.inter != null;

      e.final_obj = val;
   }

   void putIntermediateForRef(String ref, Object inter) {
      assert ref != null;

      this.refTable.put(ref, new RefEntry(inter));
   }

   static final class RefEntry {
      final Object inter;
      Object final_obj;

      RefEntry(Object inter, Object final_obj) {
         this.inter = inter;
         this.final_obj = final_obj;
      }

      RefEntry(Object inter) {
         this.inter = inter;
      }
   }
}
