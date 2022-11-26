package weblogic.messaging.kernel.internal;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.store.ObjectHandler;

public interface Persistable {
   void writeToStore(ObjectOutput var1, ObjectHandler var2) throws IOException;

   void readFromStore(ObjectInput var1, ObjectHandler var2, KernelImpl var3) throws IOException;
}
