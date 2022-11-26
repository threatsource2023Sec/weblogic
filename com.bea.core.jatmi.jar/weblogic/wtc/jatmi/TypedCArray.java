package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.Utilities;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class TypedCArray extends StandardTypes implements TypedBuffer {
   private static final long serialVersionUID = 5248957728968004859L;
   public byte[] carray;
   public int sendSize;

   public TypedCArray() {
      super("CARRAY", 16);
   }

   public TypedCArray(int length) {
      super("CARRAY", 16);
      this.carray = new byte[length];
      this.sendSize = length;
   }

   public int getSendSize() {
      return this.sendSize;
   }

   public void setSendSize(int aSendSize) {
      this.sendSize = aSendSize;
   }

   public void _tmpresend(DataOutputStream encoder) throws TPException, IOException {
      if (this.carray != null && this.sendSize >= 1 && this.sendSize <= this.carray.length) {
         encoder.write(this.carray, 0, this.sendSize);
      } else {
         throw new TPException(4, "Invalid TypedCArray");
      }
   }

   public void _tmpostrecv(DataInputStream decoder, int recv_size) throws TPException, IOException {
      if (recv_size == 0) {
         throw new TPException(4, "Invalid recieved size when receiving CARRAY (0)");
      } else {
         this.carray = new byte[recv_size];
         this.sendSize = recv_size;
         Utilities.readByteArray(decoder, this.carray, 0, recv_size);
      }
   }
}
