package org.python.icu.impl.coll;

import java.util.concurrent.atomic.AtomicInteger;
import org.python.icu.util.ICUCloneNotSupportedException;

public class SharedObject implements Cloneable {
   private AtomicInteger refCount = new AtomicInteger();

   public SharedObject clone() {
      SharedObject c;
      try {
         c = (SharedObject)super.clone();
      } catch (CloneNotSupportedException var3) {
         throw new ICUCloneNotSupportedException(var3);
      }

      c.refCount = new AtomicInteger();
      return c;
   }

   public final void addRef() {
      this.refCount.incrementAndGet();
   }

   public final void removeRef() {
      this.refCount.decrementAndGet();
   }

   public final int getRefCount() {
      return this.refCount.get();
   }

   public final void deleteIfZeroRefCount() {
   }

   public static final class Reference implements Cloneable {
      private SharedObject ref;

      public Reference(SharedObject r) {
         this.ref = r;
         if (r != null) {
            r.addRef();
         }

      }

      public Reference clone() {
         Reference c;
         try {
            c = (Reference)super.clone();
         } catch (CloneNotSupportedException var3) {
            throw new ICUCloneNotSupportedException(var3);
         }

         if (this.ref != null) {
            this.ref.addRef();
         }

         return c;
      }

      public SharedObject readOnly() {
         return this.ref;
      }

      public SharedObject copyOnWrite() {
         SharedObject r = this.ref;
         if (r.getRefCount() <= 1) {
            return r;
         } else {
            SharedObject r2 = r.clone();
            r.removeRef();
            this.ref = r2;
            r2.addRef();
            return r2;
         }
      }

      public void clear() {
         if (this.ref != null) {
            this.ref.removeRef();
            this.ref = null;
         }

      }

      protected void finalize() throws Throwable {
         super.finalize();
         this.clear();
      }
   }
}
