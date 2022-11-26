package weblogic.cache;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ref.SoftReference;

public class RefWrapper implements Externalizable {
   private static final boolean nosoftrefs = Boolean.getBoolean("weblogicx.cache.nosoftreferences");
   private SoftReference softref;
   private Object hardref;

   public Object get() {
      if (nosoftrefs) {
         return this.hardref;
      } else {
         return this.softref == null ? null : this.softref.get();
      }
   }

   public RefWrapper() {
   }

   public RefWrapper(Object o) {
      if (nosoftrefs) {
         this.hardref = o;
      } else {
         this.softref = new SoftReference(o);
      }

   }

   public void writeExternal(ObjectOutput oo) throws IOException {
      oo.writeObject(this.get());
   }

   public void readExternal(ObjectInput oi) throws IOException, ClassNotFoundException {
      Object o = oi.readObject();
      if (o != null) {
         if (nosoftrefs) {
            this.hardref = o;
         } else {
            this.softref = new SoftReference(o);
         }

      }
   }
}
