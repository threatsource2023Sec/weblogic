package weblogic.wtc.jatmi;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class TypedXCommon extends StandardTypes implements TypedBuffer {
   private static final long serialVersionUID = 7682888211443261491L;

   public TypedXCommon() {
      super("X_COMMON", 22);
   }

   public TypedXCommon(String viewname) {
      super("X_COMMON", viewname, 22);
   }

   public abstract void _tmpresend(DataOutputStream var1) throws TPException, IOException;

   public abstract void _tmpostrecv(DataInputStream var1, int var2) throws TPException, IOException;
}
