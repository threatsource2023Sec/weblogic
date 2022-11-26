package weblogic.wtc.jatmi;

import java.util.Hashtable;

public class TypedBufferPool {
   protected Hashtable buffers = new Hashtable();

   public synchronized void put(String key, TypedBuffer value) {
      BufferRef ref = (BufferRef)this.buffers.get(key);
      if (null == ref) {
         ref = new BufferRef();
         ref.refcount = 1L;
         ref.buf = value;
         this.buffers.put(key, ref);
      } else {
         ++ref.refcount;
      }

   }

   public synchronized TypedBuffer get(String key) {
      BufferRef ref;
      for(ref = null; null == ref; ref = (BufferRef)this.buffers.get(key)) {
      }

      TypedBuffer tb = ref.buf;
      if (--ref.refcount == 0L) {
         this.buffers.remove(key);
      }

      return tb;
   }

   class BufferRef {
      public long refcount;
      public TypedBuffer buf;
   }
}
