package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.Utilities;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

public final class TypedString extends StandardTypes implements TypedBuffer, Serializable {
   private static final long serialVersionUID = -4800449954774495175L;
   private StringBuffer tstring;

   public TypedString() {
      super("STRING", 17);
      this.tstring = new StringBuffer();
   }

   public TypedString(int capacity) {
      super("STRING", 17);
      this.tstring = new StringBuffer(capacity);
   }

   public TypedString(String str) {
      super("STRING", 17);
      this.tstring = new StringBuffer(str);
   }

   public StringBuffer getStringBuffer() {
      return this.tstring;
   }

   public void _tmpresend(DataOutputStream encoder) throws TPException, IOException {
      int output_data_size = false;
      if (this.tstring == null) {
         Utilities.xdr_encode_string(encoder, (String)null);
      } else {
         Utilities.xdr_encode_string(encoder, this.tstring.toString());
      }

   }

   public void _tmpostrecv(DataInputStream decoder, int recv_size) throws TPException, IOException {
      String theString = Utilities.xdr_decode_string(decoder, (byte[])null);
      if (theString == null) {
         this.tstring = new StringBuffer();
      } else {
         this.tstring = new StringBuffer(theString);
      }

   }

   public String toString() {
      return this.tstring == null ? null : this.tstring.toString();
   }
}
