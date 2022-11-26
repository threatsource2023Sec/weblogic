package org.apache.openjpa.lib.rop;

import java.util.Comparator;
import org.apache.commons.lang.exception.NestableRuntimeException;

public class MergedResultObjectProvider implements ResultObjectProvider {
   private static final byte UNOPENED = 0;
   private static final byte OPENED = 1;
   private static final byte VALUE = 2;
   private static final byte DONE = 3;
   private final ResultObjectProvider[] _rops;
   private final Comparator _comp;
   private final byte[] _status;
   private Object[] _values;
   private Object[] _orderValues;
   private Object _cur;
   private int _size;

   public MergedResultObjectProvider(ResultObjectProvider[] rops) {
      this(rops, (Comparator)null);
   }

   public MergedResultObjectProvider(ResultObjectProvider[] rops, Comparator comp) {
      this._cur = null;
      this._size = -1;
      this._rops = rops;
      this._comp = comp;
      this._status = new byte[rops.length];
      this._values = comp == null ? null : new Object[rops.length];
      this._orderValues = comp == null ? null : new Object[rops.length];
   }

   public boolean supportsRandomAccess() {
      return false;
   }

   public void open() throws Exception {
      int len = this._comp != null ? this._rops.length : 1;

      for(int i = 0; i < len; ++i) {
         this._rops[i].open();
         this._status[i] = 1;
      }

   }

   public boolean absolute(int pos) throws Exception {
      throw new UnsupportedOperationException();
   }

   public int size() throws Exception {
      if (this._size != -1) {
         return this._size;
      } else {
         int total;
         for(total = 0; total < this._status.length; ++total) {
            if (this._status[total] == 0) {
               this._rops[total].open();
               this._status[total] = 1;
            }
         }

         total = 0;

         for(int i = 0; i < this._rops.length; ++i) {
            int size = this._rops[i].size();
            if (size == Integer.MAX_VALUE) {
               total = size;
               break;
            }

            total += size;
         }

         this._size = total;
         return this._size;
      }
   }

   public void reset() throws Exception {
      for(int i = 0; i < this._rops.length; ++i) {
         if (this._status[i] != 0) {
            this._rops[i].reset();
         }
      }

      this.clear();
   }

   public void close() throws Exception {
      Exception err = null;

      for(int i = 0; i < this._rops.length; ++i) {
         try {
            if (this._status[i] != 0) {
               this._rops[i].close();
            }
         } catch (Exception var4) {
            if (err == null) {
               err = var4;
            }
         }
      }

      this.clear();
      if (err != null) {
         throw err;
      }
   }

   private void clear() {
      this._cur = null;

      for(int i = 0; i < this._rops.length; ++i) {
         this._status[i] = 1;
         if (this._values != null) {
            this._values[i] = null;
         }

         if (this._orderValues != null) {
            this._orderValues[i] = null;
         }
      }

   }

   public void handleCheckedException(Exception e) {
      if (this._rops.length == 0) {
         throw new NestableRuntimeException(e);
      } else {
         this._rops[0].handleCheckedException(e);
      }
   }

   public boolean next() throws Exception {
      boolean hasValue = false;

      int least;
      for(least = 0; least < this._status.length; ++least) {
         switch (this._status[least]) {
            case 0:
               this._rops[least].open();
               this._status[least] = 1;
            case 1:
               if (this._rops[least].next()) {
                  if (this._comp == null) {
                     this._cur = this._rops[least].getResultObject();
                     return true;
                  }

                  hasValue = true;
                  this._status[least] = 2;
                  this._values[least] = this._rops[least].getResultObject();
                  this._orderValues[least] = this.getOrderingValue(this._values[least], least, this._rops[least]);
               } else {
                  this._status[least] = 3;
               }
               break;
            case 2:
               hasValue = true;
         }
      }

      if (this._comp != null && hasValue) {
         least = -1;
         Object orderVal = null;

         for(int i = 0; i < this._orderValues.length; ++i) {
            if (this._status[i] == 2 && (least == -1 || this._comp.compare(this._orderValues[i], orderVal) < 0)) {
               least = i;
               orderVal = this._orderValues[i];
            }
         }

         this._cur = this._values[least];
         this._values[least] = null;
         this._orderValues[least] = null;
         this._status[least] = 1;
         return true;
      } else {
         return false;
      }
   }

   public Object getResultObject() throws Exception {
      return this._cur;
   }

   protected Object getOrderingValue(Object val, int idx, ResultObjectProvider rop) {
      return val;
   }
}
