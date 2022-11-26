package oracle.jrockit.jfr;

import java.lang.ref.WeakReference;

class ClassRef extends WeakReference {
   private final int hashcode;

   public ClassRef(Class referent) {
      super(referent);
      this.hashcode = referent.hashCode();
   }

   public boolean equals(Object obj) {
      if (obj instanceof Class) {
         Class c = (Class)obj;
         return c == this.get();
      } else {
         return super.equals(obj);
      }
   }

   public int hashCode() {
      return this.hashcode;
   }
}
