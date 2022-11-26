package weblogic.socket;

import java.io.IOException;

final class DevPollSocketInfo extends NativeSocketInfo {
   protected String fieldsToString() {
      StringBuffer sb = new StringBuffer(200);
      sb.append(super.fieldsToString()).append(", ").append("fd = ").append(this.fd);
      return sb.toString();
   }

   DevPollSocketInfo(MuxableSocket ms) throws IOException {
      super(ms);
   }
}
