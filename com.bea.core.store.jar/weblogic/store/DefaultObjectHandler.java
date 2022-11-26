package weblogic.store;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public final class DefaultObjectHandler implements ObjectHandler {
   public static final DefaultObjectHandler THE_ONE = new DefaultObjectHandler();

   private DefaultObjectHandler() {
   }

   public void writeObject(ObjectOutput out, Object obj) throws IOException {
      out.writeObject(obj);
   }

   public Object readObject(ObjectInput in) throws ClassNotFoundException, IOException {
      return in.readObject();
   }
}
