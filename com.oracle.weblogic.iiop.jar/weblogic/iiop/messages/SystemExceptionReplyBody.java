package weblogic.iiop.messages;

import weblogic.iiop.protocol.CorbaInputStream;

final class SystemExceptionReplyBody {
   private byte[] exception_id;
   private int minor_code_value;

   public void read(CorbaInputStream is) {
      this.exception_id = is.read_octet_sequence();
      this.minor_code_value = is.read_long();
   }
}
