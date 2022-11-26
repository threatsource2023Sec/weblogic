package weblogic.store;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public interface ObjectHandler {
   void writeObject(ObjectOutput var1, Object var2) throws IOException;

   Object readObject(ObjectInput var1) throws ClassNotFoundException, IOException;
}
