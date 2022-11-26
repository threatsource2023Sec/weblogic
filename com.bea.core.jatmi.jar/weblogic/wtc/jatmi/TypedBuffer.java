package weblogic.wtc.jatmi;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface TypedBuffer {
   int getHintIndex();

   String getType();

   String getSubtype();

   void _tmpresend(DataOutputStream var1) throws TPException, IOException;

   void _tmpostrecv(DataInputStream var1, int var2) throws TPException, IOException;
}
