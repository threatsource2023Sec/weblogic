package weblogic.iiop.messages;

import weblogic.iiop.ior.IOR;
import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;

public final class IORAddressingInfo {
   private final int selected_profile_index;
   private final IOR ior;

   public IORAddressingInfo(CorbaInputStream is) {
      this.selected_profile_index = is.read_long();
      this.ior = new IOR(is);
   }

   public void write(CorbaOutputStream os) {
      os.write_long(this.selected_profile_index);
      this.ior.write(os);
   }

   public IOR getIor() {
      return this.ior;
   }
}
