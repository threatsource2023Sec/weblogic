package org.apache.openjpa.lib.rop;

import java.util.List;
import org.apache.commons.lang.exception.NestableRuntimeException;
import org.apache.openjpa.lib.util.Closeable;

public class ListResultObjectProvider implements ResultObjectProvider {
   private final List _list;
   private int _idx = -1;

   public ListResultObjectProvider(List list) {
      this._list = list;
   }

   public List getDelegate() {
      return this._list;
   }

   public boolean supportsRandomAccess() {
      return true;
   }

   public void open() throws Exception {
   }

   public Object getResultObject() throws Exception {
      return this._list.get(this._idx);
   }

   public boolean next() throws Exception {
      return this.absolute(this._idx + 1);
   }

   public boolean absolute(int pos) throws Exception {
      if (pos >= 0 && pos < this._list.size()) {
         this._idx = pos;
         return true;
      } else {
         return false;
      }
   }

   public int size() throws Exception {
      return this._list.size();
   }

   public void reset() throws Exception {
      this._idx = -1;
   }

   public void close() throws Exception {
      if (this._list instanceof Closeable) {
         try {
            ((Closeable)this._list).close();
         } catch (Exception var2) {
         }
      }

   }

   public void handleCheckedException(Exception e) {
      throw new NestableRuntimeException(e);
   }
}
