package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.Utilities;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class TypedXML extends StandardTypes implements TypedBuffer {
   private static final long serialVersionUID = -5405254827452750129L;
   public byte[] xmlarray;
   public int sendSize;

   public TypedXML() {
      super("XML", 25);
   }

   public TypedXML(int length) {
      super("XML", 25);
      this.xmlarray = new byte[length];
      this.sendSize = length;
   }

   public int getSendSize() {
      return this.sendSize;
   }

   public void setSendSize(int aSendSize) {
      this.sendSize = aSendSize;
   }

   public void _tmpresend(DataOutputStream encoder) throws TPException, IOException {
      if (this.xmlarray != null && this.sendSize >= 1 && this.sendSize <= this.xmlarray.length) {
         encoder.write(this.xmlarray, 0, this.sendSize);
      } else {
         throw new TPException(4, "Invalid TypedXML");
      }
   }

   public void _tmpostrecv(DataInputStream decoder, int recv_size) throws TPException, IOException {
      if (recv_size == 0) {
         throw new TPException(4, "Invalid recieved size when receiving XML (0)");
      } else {
         this.xmlarray = new byte[recv_size];
         this.sendSize = recv_size;
         Utilities.readByteArray(decoder, this.xmlarray, 0, recv_size);
      }
   }
}
