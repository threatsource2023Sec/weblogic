package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.Utilities;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class TypedTGIOP extends StandardTypes implements TypedBuffer {
   public byte[] tgiop;
   public int send_size;

   public TypedTGIOP() {
      super("TGIOP", 10);
   }

   public TypedTGIOP(int length) {
      super("TGIOP", 10);
      this.tgiop = new byte[length];
      this.send_size = length;
   }

   public TypedTGIOP(byte[] msg, int length) {
      super("TGIOP", 10);
      this.tgiop = new byte[length];

      for(int lcv = 0; lcv < length; ++lcv) {
         this.tgiop[lcv] = msg[lcv];
      }

      this.send_size = length;
   }

   public int get_send_size() {
      return this.send_size;
   }

   public void set_send_size(int send_size) {
      this.send_size = send_size;
   }

   public void _tmpresend(DataOutputStream encoder) throws TPException, IOException {
      if (this.tgiop != null && this.send_size >= 1 && this.send_size <= this.tgiop.length) {
         encoder.write(this.tgiop, 0, this.send_size);
      } else {
         throw new TPException(4, "Invalid TGIOP");
      }
   }

   public void _tmpostrecv(DataInputStream decoder, int recv_size) throws TPException, IOException {
      if (recv_size == 0) {
         throw new TPException(4, "Invalid recieved size when receiving CARRAY (0)");
      } else {
         this.tgiop = new byte[recv_size];
         this.send_size = recv_size;
         Utilities.readByteArray(decoder, this.tgiop, 0, recv_size);
      }
   }
}
