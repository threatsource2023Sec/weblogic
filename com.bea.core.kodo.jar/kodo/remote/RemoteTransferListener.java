package kodo.remote;

import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

public interface RemoteTransferListener extends Serializable {
   void transferWrite(Object var1, ObjectOutput var2, boolean var3);

   void transferRead(Object var1, ObjectInput var2, boolean var3);
}
