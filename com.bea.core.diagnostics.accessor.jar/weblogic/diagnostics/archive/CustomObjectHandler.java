package weblogic.diagnostics.archive;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.store.ObjectHandler;

public final class CustomObjectHandler implements ObjectHandler {
   private ClassLoader classLoader = CustomObjectHandler.class.getClassLoader();

   public Object readObject(ObjectInput in) throws ClassNotFoundException, IOException {
      ClassLoader clSave = Thread.currentThread().getContextClassLoader();

      Object var3;
      try {
         Thread.currentThread().setContextClassLoader(this.classLoader);
         var3 = in.readObject();
      } finally {
         Thread.currentThread().setContextClassLoader(clSave);
      }

      return var3;
   }

   public void writeObject(ObjectOutput out, Object obj) throws IOException {
      out.writeObject(obj);
   }
}
