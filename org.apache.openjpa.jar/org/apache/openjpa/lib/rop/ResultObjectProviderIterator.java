package org.apache.openjpa.lib.rop;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.apache.openjpa.lib.util.Closeable;

public class ResultObjectProviderIterator implements Iterator, Closeable {
   private final ResultObjectProvider _rop;
   private Boolean _hasNext = null;
   private Boolean _open = null;

   public ResultObjectProviderIterator(ResultObjectProvider rop) {
      this._rop = rop;
   }

   public void close() {
      if (this._open == Boolean.TRUE) {
         try {
            this._rop.close();
         } catch (Exception var2) {
         }

         this._open = Boolean.FALSE;
      }

   }

   public void remove() {
      throw new UnsupportedOperationException();
   }

   public boolean hasNext() {
      if (this._open == Boolean.FALSE) {
         return false;
      } else {
         if (this._hasNext == null) {
            try {
               if (this._open == null) {
                  this._rop.open();
                  this._open = Boolean.TRUE;
               }

               this._hasNext = this._rop.next() ? Boolean.TRUE : Boolean.FALSE;
            } catch (RuntimeException var2) {
               this.close();
               throw var2;
            } catch (Exception var3) {
               this.close();
               this._rop.handleCheckedException(var3);
               return false;
            }
         }

         if (!this._hasNext) {
            this.close();
            return false;
         } else {
            return true;
         }
      }
   }

   public Object next() {
      if (!this.hasNext()) {
         throw new NoSuchElementException();
      } else {
         try {
            Object ret = this._rop.getResultObject();
            this._hasNext = null;
            return ret;
         } catch (RuntimeException var2) {
            this.close();
            throw var2;
         } catch (Exception var3) {
            this.close();
            this._rop.handleCheckedException(var3);
            return null;
         }
      }
   }

   protected void finalize() {
      this.close();
   }
}
