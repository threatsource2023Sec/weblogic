package weblogic.wtc.jatmi;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class UnsolTcb extends tcb {
   private int opcode;
   private int datalen = 132;
   private int arg1;
   private int arg2;
   private int arg3;
   private int unsolflags;
   private static final int sizeofTCMTMUNSOLINFO = 132;
   private static final int FIXED_UNSOL_SIZE = 24;
   private static final int TMBROADCAST = 1;
   private static final int TMNOTIFY = 2;
   private static final int ALL_LMIDS = 4;
   private static final int ALL_USERS = 8;
   private static final int ALL_CLIENTS = 16;
   private static final int ALL_GROUPS = 32;
   private static final int TMUN_ACK = 64;
   private static final int TMUN_NATIVE = 128;

   public UnsolTcb() {
      super((short)4);
   }

   public void set_tpnotify(boolean ack) {
      this.unsolflags = 2;
      if (ack) {
         this.unsolflags |= 64;
      }

   }

   public void set_tpbroadcast() {
      this.unsolflags = 1;
   }

   private int roundup4(int a) {
      return a + 3 & -4;
   }

   public boolean prepareForCache() {
      this.opcode = 0;
      this.datalen = 132;
      this.arg1 = 0;
      this.arg2 = 0;
      this.arg3 = 0;
      this.unsolflags = 0;
      return true;
   }

   public void _tmpresend(DataOutputStream encoder, tch myheader) throws TPException, IOException {
      int calculated_size = myheader.getHeaderLen();
      int output_data_size = false;
      calculated_size += 24;
      encoder.writeInt(this.opcode);
      encoder.writeInt(this.datalen);
      encoder.writeInt(this.arg1);
      encoder.writeInt(this.arg2);
      encoder.writeInt(this.arg3);
      encoder.writeInt(this.unsolflags);
      if ((this.unsolflags & 64) != 0) {
         encoder.writeInt(0);
         encoder.writeInt(0);
         encoder.writeInt(0);
         encoder.writeInt(0);
         encoder.writeInt(0);
         encoder.writeInt(0);
         calculated_size += 24;
      } else {
         for(int lcv = 0; lcv < 128; ++lcv) {
            encoder.writeByte(0);
         }

         calculated_size += 128;
      }

      myheader.setLen(calculated_size);
   }

   public final int _tmpostrecv(DataInputStream decoder, int recv_size, int hint_index) throws IOException {
      int r_padded_length = this.roundup4(recv_size);
      this.opcode = decoder.readInt();
      this.datalen = decoder.readInt();
      this.arg1 = decoder.readInt();
      this.arg2 = decoder.readInt();
      this.arg3 = decoder.readInt();
      this.unsolflags = decoder.readInt();
      r_padded_length -= 24;
      decoder.skipBytes(r_padded_length);
      return 0;
   }
}
