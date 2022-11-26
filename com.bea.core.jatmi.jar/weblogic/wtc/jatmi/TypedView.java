package weblogic.wtc.jatmi;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class TypedView extends StandardTypes implements TypedBuffer {
   private static final long serialVersionUID = -607969884185172853L;
   private boolean buildWith64Bit = false;
   private boolean use64BitsLong = false;

   public TypedView() {
      super("VIEW", 19);
   }

   public TypedView(String viewname) {
      super("VIEW", viewname, 19);
   }

   public TypedView(String viewname, boolean val) {
      super("VIEW", viewname, 19);
      this.buildWith64Bit = val;
   }

   public abstract void _tmpresend(DataOutputStream var1) throws TPException, IOException;

   public abstract void _tmpostrecv(DataInputStream var1, int var2) throws TPException, IOException;

   public abstract boolean containsOldView();

   public void setUse64BitsLong(boolean val) {
      this.use64BitsLong = val;
   }

   public boolean getUse64BitsLong() {
      return this.use64BitsLong;
   }

   public boolean getBuiltWith64Bit() {
      return this.buildWith64Bit;
   }
}
