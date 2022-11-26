package com.bea.staxb.runtime.internal;

import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;

final class ObjectRefTable {
   private final IdentityHashMap table = new IdentityHashMap();
   private boolean haveMultiplyRefdObj = false;
   private int idcnt;

   public boolean hasMultiplyRefdObjects() {
      return this.haveMultiplyRefdObj;
   }

   public Iterator getMultipleRefTableEntries() {
      return new MultiRefIterator(this.table);
   }

   public int incrementRefCount(Object keyobj, RuntimeBindingProperty property, boolean needs_xsi_type) {
      if (keyobj == null) {
         return 0;
      } else {
         Value val = (Value)this.table.get(keyobj);
         if (val == null) {
            val = new Value(keyobj, ++this.idcnt, property);
            this.table.put(keyobj, val);
         } else {
            assert val.cnt > 0;

            this.haveMultiplyRefdObj = true;
         }

         assert this.table.get(keyobj) == val;

         if (needs_xsi_type) {
            val.needsXsiType = true;
         }

         return ++val.cnt;
      }
   }

   public int getRefCount(Object obj) {
      if (obj == null) {
         return 0;
      } else {
         Value val = (Value)this.table.get(obj);
         return val == null ? 0 : val.cnt;
      }
   }

   public int getId(Object obj) {
      int retval = -1;
      if (obj != null) {
         Value val = (Value)this.table.get(obj);
         if (val != null && val.cnt > 1) {
            retval = val.id;
         }
      }

      return retval;
   }

   private static final class MultiRefIterator implements Iterator {
      private final Iterator baseItr;
      private Value nextValue = null;

      MultiRefIterator(IdentityHashMap table) {
         this.baseItr = table.entrySet().iterator();
         this.updateNext();
      }

      public boolean hasNext() {
         return this.nextValue != null;
      }

      public Object next() {
         Value retval = this.nextValue;

         assert retval.getCnt() > 1;

         this.updateNext();

         assert this.nextValue == null || this.nextValue.getCnt() > 1;

         return retval;
      }

      private void updateNext() {
         while(true) {
            if (this.baseItr.hasNext()) {
               Map.Entry map_entry = (Map.Entry)this.baseItr.next();
               Value val = (Value)map_entry.getValue();
               if (val.cnt <= 1) {
                  continue;
               }

               this.nextValue = val;
               return;
            }

            this.nextValue = null;
            return;
         }
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   }

   public static final class Value {
      final Object object;
      final int id;
      final RuntimeBindingProperty prop;
      int cnt;
      boolean needsXsiType;

      public int getId() {
         return this.id;
      }

      public int getCnt() {
         return this.cnt;
      }

      public RuntimeBindingProperty getProp() {
         return this.prop;
      }

      public Value(Object obj, int id, RuntimeBindingProperty prop) {
         this.object = obj;
         this.id = id;
         this.prop = prop;
      }
   }
}
