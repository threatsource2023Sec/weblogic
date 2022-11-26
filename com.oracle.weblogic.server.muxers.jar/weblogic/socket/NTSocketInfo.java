package weblogic.socket;

import java.io.IOException;

final class NTSocketInfo extends NativeSocketInfo {
   protected int nativeIndex = NTSocketMuxer.add(this);

   NTSocketInfo(MuxableSocket ms) throws IOException {
      super(ms);
   }

   protected String fieldsToString() {
      StringBuffer sb = new StringBuffer(200);
      sb.append(super.fieldsToString()).append(", ").append("nativeIndex = ").append(this.nativeIndex);
      return sb.toString();
   }

   protected void cleanup() {
      super.cleanup();
      NTSocketMuxer.remove(this.nativeIndex);
   }
}
