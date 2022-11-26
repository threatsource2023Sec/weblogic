package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.Utilities;
import com.bea.core.jatmi.common.ntrace;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class CodesetTcb extends tcb {
   static final long serialVersionUID = 6501324468778346952L;
   private String encode;

   public CodesetTcb() {
      super((short)18);
   }

   public CodesetTcb(String encode) {
      super((short)18);
      if (encode != null) {
         this.encode = new String(encode);
      }

   }

   public String getMBEncoding() {
      return this.encode;
   }

   public boolean prepareForCache() {
      return false;
   }

   public void _tmpresend(DataOutputStream encoder, tch myheader) throws TPException, IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/CodesetTcb/_tmpresend/" + myheader);
      }

      int calculated_size = myheader.getHeaderLen();
      calculated_size += Utilities.xdr_length_string(this.encode);
      myheader.setLen(calculated_size);

      try {
         if (traceEnabled) {
            ntrace.doTrace("/CodesetTcb/_tmpresend/10");
         }

         Utilities.xdr_encode_string(encoder, this.encode);
      } catch (IOException var6) {
         if (traceEnabled) {
            ntrace.doTrace("*]/CodesetTcb/_tmpresend/20/" + var6);
         }

         throw var6;
      }

      if (traceEnabled) {
         ntrace.doTrace("]/CodesetTcb/_tmpresend/30/");
      }

   }

   public final int _tmpostrecv(DataInputStream decoder, int recv_size, int hint_index) throws IOException {
      this.encode = Utilities.xdr_decode_string(decoder, (byte[])null);
      return 0;
   }
}
