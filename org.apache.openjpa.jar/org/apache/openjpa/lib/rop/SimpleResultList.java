package org.apache.openjpa.lib.rop;

import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SimpleResultList extends AbstractNonSequentialResultList {
   private final transient ResultObjectProvider _rop;
   private boolean _closed = false;
   private int _size = -1;

   public SimpleResultList(ResultObjectProvider rop) {
      this._rop = rop;

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

   public boolean isProviderOpen() {
      return !this._closed;
   }

   public boolean isClosed() {
      return this._closed;
   }

   public void close() {
      if (!this._closed) {
         this._closed = true;

         try {
            this._rop.close();
         } catch (Exception var2) {
         }
      }

   }

   public Object getInternal(int index) {
      try {
         return !this._rop.absolute(index) ? PAST_END : this._rop.getResultObject();
      } catch (RuntimeException var3) {
         this.close();
         throw var3;
      } catch (Exception var4) {
         this.close();
         this._rop.handleCheckedException(var4);
         return PAST_END;
      }
   }

   public int size() {
      this.assertOpen();
      if (this._size != -1) {
         return this._size;
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

   public Object writeReplace() throws ObjectStreamException {
      if (this._closed) {
         return this;
      } else {
         List list = new ArrayList();
         Iterator itr = this.iterator();

         while(itr.hasNext()) {
            list.add(itr.next());
         }

         return list;
      }
   }
}
