package weblogic.wtc.jatmi;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import weblogic.wtc.WTCLogger;

public final class TranTcb extends tcb {
   private boolean commit_logged = true;
   private static final int TMGCMTLOGGED = 512;
   private static final int GTRIDSIZE = 32;

   public TranTcb() {
      super((short)2);
   }

   public boolean get_logged() {
      return this.commit_logged;
   }

   public void set_logged(boolean logged) {
      this.commit_logged = logged;
   }

   public boolean prepareForCache() {
      this.commit_logged = true;
      return true;
   }

   public void _tmpresend(DataOutputStream encoder, tch myheader) throws TPException, IOException {
      int calculated_size = myheader.getHeaderLen();
      calculated_size += 32;
      myheader.setLen(calculated_size);
      encoder.writeInt(0);
      encoder.writeInt(0);
      encoder.writeInt(0);
      encoder.writeInt(0);
      encoder.writeInt(0);
      encoder.writeInt(0);
      if (this.commit_logged) {
         encoder.writeInt(512);
      } else {
         encoder.writeInt(0);
      }

      encoder.writeInt(0);
   }

   public final int _tmpostrecv(DataInputStream decoder, int recv_size, int hint_index) throws IOException {
      if (recv_size < 32) {
         WTCLogger.logErrorRecvSize(recv_size);
         return -1;
      } else {
         recv_size -= 32;
         decoder.readInt();
         decoder.readInt();
         decoder.readInt();
         decoder.readInt();
         decoder.readInt();
         decoder.readInt();
         this.commit_logged = (decoder.readInt() & 512) != 0;
         decoder.readInt();
         decoder.skipBytes(recv_size);
         return 0;
      }
   }
}
