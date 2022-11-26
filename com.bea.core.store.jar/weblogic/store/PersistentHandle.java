package weblogic.store;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.store.internal.PersistentHandleImpl;

public abstract class PersistentHandle implements Externalizable {
   public static final long serialVersionUID = 270777966764419545L;

   public static PersistentHandle read(ObjectInput oi) throws IOException {
      PersistentHandle ret = new PersistentHandleImpl();

      try {
         ret.readExternal(oi);
         return ret;
      } catch (ClassNotFoundException var3) {
         throw new AssertionError(var3);
      }
   }

   public static void write(ObjectOutput oo, PersistentHandle h) throws IOException {
      h.writeExternal(oo);
   }
}
