package org.apache.openjpa.lib.rop;

import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import serp.util.Numbers;

public class RandomAccessResultList extends AbstractNonSequentialResultList {
   private static final int OPEN = 0;
   private static final int FREED = 1;
   private static final int CLOSED = 2;
   private ResultObjectProvider _rop = null;
   private Map _rows = null;
   private Object[] _full = null;
   private long _requests = 0L;
   private int _state = 0;
   private int _size = -1;

   public RandomAccessResultList(ResultObjectProvider rop) {
      this._rop = rop;
      this._rows = this.newRowMap();

      try {
         this._rop.open();
      } catch (RuntimeException var3) {
         this.close();
         throw var3;
      } catch (Exception var4) {
         this.close();
         this._rop.handleCheckedException(var4);
      }

   }

   protected Map newRowMap() {
      return new HashMap();
   }

   public boolean isProviderOpen() {
      return this._state == 0;
   }

   public boolean isClosed() {
      return this._state == 2;
   }

   public void close() {
      if (this._state != 2) {
         this.free();
         this._state = 2;
      }

   }

   protected Object getInternal(int index) {
      if (this._full != null) {
         return index >= this._full.length ? PAST_END : this._full[index];
      } else {
         Integer i = Numbers.valueOf(index);
         Object ret = this._rows.get(i);
         if (ret != null) {
            return ret instanceof Null ? null : ret;
         } else {
            ret = this.instantiateRow(i);
            return ret == null ? PAST_END : ret;
         }
      }
   }

   private Object instantiateRow(Integer i) {
      ++this._requests;

      try {
         if (!this._rop.absolute(i)) {
            return PAST_END;
         } else {
            Object ob = this._rop.getResultObject();
            if (ob == null) {
               ob = new Null();
            }

            this._rows.put(i, ob);
            this.checkComplete();
            return ob;
         }
      } catch (RuntimeException var3) {
         this.close();
         throw var3;
      } catch (Exception var4) {
         this.close();
         this._rop.handleCheckedException(var4);
         return null;
      }
   }

   private void checkComplete() {
      if (this._size != -1 && this._rows.size() == this._size) {
         Object[] full = new Object[this._size];
         int count = 0;

         for(Iterator itr = this._rows.keySet().iterator(); itr.hasNext(); ++count) {
            Integer key = (Integer)itr.next();
            full[key] = this._rows.get(key);
         }

         if (count == this._size) {
            this._full = full;
            this.free();
         }

      }
   }

   public int size() {
      this.assertOpen();
      if (this._size != -1) {
         return this._size;
      } else if (this._full != null) {
         return this._full.length;
      } else {
         try {
            this._size = this._rop.size();
            return this._size;
         } catch (RuntimeException var2) {
            this.close();
            throw var2;
         } catch (Exception var3) {
            this.close();
            this._rop.handleCheckedException(var3);
            return -1;
         }
      }
   }

   private void free() {
      if (this._state == 0) {
         try {
            this._rop.close();
         } catch (Exception var2) {
         }

         this._rows = null;
         this._state = 1;
      }

   }

   public Object writeReplace() throws ObjectStreamException {
      if (this._full != null) {
         return new ListResultList(Arrays.asList(this._full));
      } else {
         ArrayList list = new ArrayList();
         Iterator itr = this.iterator();

         while(itr.hasNext()) {
            list.add(itr.next());
         }

         return list;
      }
   }

   public String toString() {
      return this.getClass().getName() + "; identity: " + System.identityHashCode(this) + "; cached: " + this._rows.size() + "; requests: " + this._requests;
   }

   public int hashCode() {
      return System.identityHashCode(this);
   }

   public boolean equals(Object other) {
      return other == this;
   }

   private static class Null {
      private Null() {
      }

      // $FF: synthetic method
      Null(Object x0) {
         this();
      }
   }
}
