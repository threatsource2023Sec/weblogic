package org.apache.openjpa.lib.rop;

import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WindowResultList extends AbstractNonSequentialResultList {
   private static final int OPEN = 0;
   private static final int FREED = 1;
   private static final int CLOSED = 2;
   private final Object[] _window;
   private int _pos;
   private ResultObjectProvider _rop;
   private boolean _random;
   private int _state;
   private int _size;

   public WindowResultList(ResultObjectProvider rop) {
      this(rop, 10);
   }

   public WindowResultList(ResultObjectProvider rop, int windowSize) {
      this._pos = -1;
      this._rop = null;
      this._random = false;
      this._state = 0;
      this._size = -1;
      this._rop = rop;
      if (windowSize <= 0) {
         windowSize = 10;
      }

      this._window = new Object[windowSize];

      try {
         this._rop.open();
         this._random = this._rop.supportsRandomAccess();
      } catch (RuntimeException var4) {
         this.close();
         throw var4;
      } catch (Exception var5) {
         this.close();
         this._rop.handleCheckedException(var5);
      }

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

   public Object getInternal(int index) {
      if (index >= 0 && (this._size == -1 || index < this._size)) {
         try {
            if (index < this._pos) {
               if (!this._random || index == 0) {
                  this._rop.reset();
               }

               this._pos = -1;
            }

            if (this._pos == -1 || index >= this._pos + this._window.length) {
               int end;
               int i;
               if (this._random && index != 0) {
                  if (!this._rop.absolute(index - 1)) {
                     return PAST_END;
                  }
               } else {
                  end = this._pos == -1 ? 0 : this._pos + this._window.length;

                  for(i = end; i < index; ++i) {
                     if (!this._rop.next()) {
                        return PAST_END;
                     }
                  }
               }

               end = -1;

               for(i = 0; i < this._window.length; ++i) {
                  if (end == -1 && !this._rop.next()) {
                     end = i;
                  }

                  this._window[i] = end == -1 ? this._rop.getResultObject() : PAST_END;
               }

               this._pos = index;
               if (end != -1 && this._pos == 0) {
                  this._size = end;
                  this.free();
               }
            }

            return this._window[index - this._pos];
         } catch (RuntimeException var4) {
            this.close();
            throw var4;
         } catch (Exception var5) {
            this.close();
            this._rop.handleCheckedException(var5);
            return null;
         }
      } else {
         return PAST_END;
      }
   }

   private void free() {
      if (this._state == 0) {
         try {
            this._rop.close();
         } catch (Exception var2) {
         }

         this._state = 1;
      }

   }

   public Object writeReplace() throws ObjectStreamException {
      if (this._state != 0) {
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
