package weblogic.store;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class ByteBufferObjectHandler implements ObjectHandler {
   public void writeObject(ObjectOutput out, Object obj) throws IOException {
      throw new AssertionError();
   }

   public Object readObject(ObjectInput in) throws ClassNotFoundException, IOException {
      throw new AssertionError();
   }
}
