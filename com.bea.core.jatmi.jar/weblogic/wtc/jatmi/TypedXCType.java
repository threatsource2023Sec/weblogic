package weblogic.wtc.jatmi;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class TypedXCType extends StandardTypes implements TypedBuffer {
   private static final long serialVersionUID = 4928409254371359223L;

   public TypedXCType() {
      super("X_C_TYPE", 21);
   }

   public TypedXCType(String viewname) {
      super("X_C_TYPE", viewname, 21);
   }

   public abstract void _tmpresend(DataOutputStream var1) throws TPException, IOException;

   public abstract void _tmpostrecv(DataInputStream var1, int var2) throws TPException, IOException;
}
