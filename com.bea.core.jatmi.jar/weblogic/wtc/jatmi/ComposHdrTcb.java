package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.Utilities;
import com.bea.core.jatmi.common.ntrace;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class ComposHdrTcb extends tcb {
   private int opcode;
   private int appkey = -1;
   private int urcode;
   private String type;
   private String subtype;
   private int hintIndex = -1;
   public static final int TPENQUEUE = 1;
   public static final int TPDEQUEUE = 2;
   public static final int TPQCAP_0 = 0;
   public static final int TPQCAP_1 = 65536;
   private static final int TPQOPMASK = 65535;
   private static final int TMCOMPOSMAGIC = 11111;
   private static final long serialVersionUID = -991110329100825412L;
   private static final int sizeofTCMTMCOMPOS = 32;

   public ComposHdrTcb() {
      super((short)5);
   }

   public ComposHdrTcb(int newopcode, int myUrcode) {
      super((short)5);
      this.opcode = newopcode;
      this.urcode = myUrcode;
   }

   public void setTypeAndSubtype(String aType, String aSubtype, int aHintIndex) {
      this.type = aType;
      this.subtype = aSubtype;
      this.hintIndex = aHintIndex;
   }

   public String getComposType() {
      return this.type;
   }

   public String getComposSubtype() {
      return this.subtype;
   }

   public boolean prepareForCache() {
      this.opcode = 0;
      this.appkey = -1;
      this.urcode = 0;
      this.type = null;
      this.subtype = null;
      this.hintIndex = -1;
      return true;
   }

   public void _tmpresend(DataOutputStream encoder, tch myheader) throws TPException, IOException {
      int calculated_size = myheader.getHeaderLen();
      calculated_size += 32;
      myheader.setLen(calculated_size);
      encoder.writeInt(0);
      encoder.writeInt(this.opcode);
      encoder.writeInt(this.appkey);
      encoder.writeInt(this.urcode);
      encoder.writeInt(0);
      encoder.writeInt(-2);
      encoder.writeInt(-1);
      encoder.writeInt(0);
   }

   public final int _tmpostrecv(DataInputStream decoder, int recv_size, int hint_index) throws IOException {
      decoder.readInt();
      this.opcode = decoder.readInt();
      this.appkey = decoder.readInt();
      this.urcode = decoder.readInt();
      decoder.readInt();
      decoder.readInt();
      decoder.readInt();
      decoder.readInt();
      return 0;
   }

   public int getUrcode() {
      return this.urcode;
   }

   public int getAppkey() {
      return this.appkey;
   }

   public int getR65size() {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/ComposHdrTcb/getR65size/");
      }

      int ret = 152;
      if (traceEnabled) {
         ntrace.doTrace("]/ComposHdrTcb/getR65size/" + ret);
      }

      return ret;
   }

   public void _tmpresend65(DataOutputStream encoder) throws IOException {
      int maxlen = this.type != null ? 20000 : 0;
      encoder.writeInt(11111);
      encoder.writeInt(0);
      encoder.writeInt(this.opcode);
      encoder.writeInt(maxlen);
      encoder.writeInt(-1);
      encoder.writeInt(this.hintIndex);
      encoder.writeInt(0);
      encoder.writeInt(maxlen);
      encoder.writeInt(this.appkey);
      encoder.writeInt(this.urcode);
      encoder.writeInt(0);
      encoder.writeInt(-2);
      encoder.writeInt(-1);
      encoder.writeInt(0);
      Utilities.xdr_encode_vector_string(encoder, this.type, 8);
      Utilities.xdr_encode_vector_string(encoder, this.subtype, 16);
   }

   public boolean _tmpostrecv65(DataInputStream decoder) throws IOException {
      char[] helper = new char[16];
      if (decoder.readInt() != 11111) {
         return false;
      } else {
         decoder.readInt();
         this.opcode = decoder.readInt();
         decoder.readInt();
         decoder.readInt();
         this.hintIndex = decoder.readInt();
         decoder.readInt();
         decoder.readInt();
         this.appkey = decoder.readInt();
         this.urcode = decoder.readInt();
         decoder.readInt();
         decoder.readInt();
         decoder.readInt();
         decoder.readInt();
         this.type = Utilities.xdr_decode_vector_string(decoder, 8, helper);
         this.subtype = Utilities.xdr_decode_vector_string(decoder, 16, helper);
         return true;
      }
   }
}
