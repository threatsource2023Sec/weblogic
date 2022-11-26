package org.apache.openjpa.lib.rop;

import java.util.NoSuchElementException;
import org.apache.openjpa.lib.util.Localizer;

public class RangeResultObjectProvider implements ResultObjectProvider {
   private static final Localizer _loc = Localizer.forPackage(RangeResultObjectProvider.class);
   private final ResultObjectProvider _delegate;
   private final int _startIdx;
   private final int _endIdx;
   private int _idx = -1;

   public RangeResultObjectProvider(ResultObjectProvider delegate, long startIdx, long endIdx) {
      if (endIdx == Long.MAX_VALUE) {
         endIdx = 2147483647L;
      }

      this._delegate = delegate;
      if (startIdx <= 2147483647L && endIdx <= 2147483647L) {
         this._startIdx = (int)startIdx;
         this._endIdx = (int)endIdx;
      } else {
         throw new IllegalArgumentException(_loc.get("range-too-high", String.valueOf(startIdx), String.valueOf(endIdx)).getMessage());
      }
   }

   public boolean supportsRandomAccess() {
      return this._delegate.supportsRandomAccess();
   }

   public void open() throws Exception {
      this._delegate.open();
   }

   public Object getResultObject() throws Exception {
      if (this._idx >= this._startIdx && this._idx < this._endIdx) {
         return this._delegate.getResultObject();
      } else {
         throw new NoSuchElementException(String.valueOf(this._idx));
      }
   }

   public boolean next() throws Exception {
      while(true) {
         if (this._idx < this._startIdx - 1) {
            if (this._delegate.supportsRandomAccess()) {
               this._idx = this._startIdx - 1;
               if (this._delegate.absolute(this._startIdx - 1)) {
                  continue;
               }

               return false;
            }

            ++this._idx;
            if (this._delegate.next()) {
               continue;
            }

            return false;
         }

         if (this._idx >= this._endIdx - 1) {
            return false;
         }

         ++this._idx;
         return this._delegate.next();
      }
   }

   public boolean absolute(int pos) throws Exception {
      this._idx = pos + this._startIdx;
      return this._idx >= this._endIdx ? false : this._delegate.absolute(this._idx);
   }

   public int size() throws Exception {
      int size = this._delegate.size();
      if (size == Integer.MAX_VALUE) {
         return size;
      } else {
         size = Math.min(this._endIdx, size) - this._startIdx;
         return size < 0 ? 0 : size;
      }
   }

   public void reset() throws Exception {
      this._idx = -1;
      this._delegate.reset();
   }

   public void close() throws Exception {
      this._delegate.close();
   }

   public void handleCheckedException(Exception e) {
      this._delegate.handleCheckedException(e);
   }
}
