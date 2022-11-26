package jnr.ffi;

public abstract class ObjectReferenceManager {
   public static ObjectReferenceManager newInstance(Runtime runtime) {
      return runtime.newObjectReferenceManager();
   }

   /** @deprecated */
   @Deprecated
   public Pointer newReference(Object object) {
      return this.add(object);
   }

   /** @deprecated */
   @Deprecated
   public void freeReference(Pointer reference) {
      this.remove(reference);
   }

   /** @deprecated */
   @Deprecated
   public Object getObject(Pointer reference) {
      return this.get(reference);
   }

   public abstract Pointer add(Object var1);

   public abstract boolean remove(Pointer var1);

   public abstract Object get(Pointer var1);
}
