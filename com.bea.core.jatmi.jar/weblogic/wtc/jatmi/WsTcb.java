package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.Utilities;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import weblogic.wtc.WTCLogger;

public final class WsTcb extends tcb {
   private static final long serialVersionUID = -2214313148829304530L;
   private int flag;
   private int tpevent;
   private int _TPException;
   private int _tpurcode;
   private int _Uunixerr;
   private int _tperrordetail;
   private int connindx;
   private int sendmtype;
   private int rplymtype;
   private int appkey;
   private int clt_id;
   private int WSop;
   private int rtn_val;
   private int[] flds;
   private String rqustsrvc;
   private int opcode_flags;
   private String type;
   private static final int FIXED_WS_SIZE = 80;
   private static final int NUM_FLDS = 7;
   private static final int MAXSRVCNAME = 15;
   private static final int NULLMTYPE = 0;
   static final int TPCONV = 1024;

   public WsTcb() {
      super((short)3);
      this.flds = new int[7];
   }

   public WsTcb(int opcode) {
      super((short)3);
      this.WSop = opcode & 1073741823;
      this.opcode_flags = opcode & -1073741824;
      this.flds = new int[7];
   }

   public WsTcb(int opcode, String rqst_srvc) {
      super((short)3);
      this.WSop = opcode & 1073741823;
      this.opcode_flags = opcode & -1073741824;
      this.flds = new int[7];
      if (rqst_srvc != null) {
         this.rqustsrvc = new String(rqst_srvc);
      }

   }

   public WsKey get_key() {
      return new WsKey(this.WSop, this.flds[6]);
   }

   public int get_ws_sigahead() {
      return this.flds[3];
   }

   public int get_ws_sigbehind() {
      return this.flds[4];
   }

   public int get_ws_cmplimit() {
      return this.flds[1];
   }

   public int get_ws_encbits() {
      return this.flds[2];
   }

   public int get_ws_rtn_val() {
      return this.rtn_val;
   }

   public int get_ws_TPException() {
      return this._TPException;
   }

   public int get_ws_tpurcode() {
      return this._tpurcode;
   }

   public int get_ws_Uunixerr() {
      return this._Uunixerr;
   }

   public int get_ws_tperrordetail() {
      return this._tperrordetail;
   }

   public int get_opcode() {
      return this.WSop;
   }

   public int get_CLTID() {
      return this.clt_id;
   }

   public int get_HANDLE() {
      return this.flds[0];
   }

   public int get_REQGEN() {
      return this.flds[6];
   }

   public String get_type() {
      return this.type;
   }

   public int get_flds(int i) {
      return this.flds[i];
   }

   public boolean wsrplywanted() {
      return this.rplymtype != 0 && (this.flag & 1024) == 0;
   }

   public void set_HANDLE(int handle) {
      this.flds[0] = handle;
   }

   public void set_TIMEOUT(int time) {
      this.flds[1] = time;
   }

   public void set_WSFLAGS(int wsflags) {
      this.flds[3] = wsflags;
   }

   public void set_NETTIMEOUT(int nettimeout) {
      this.flds[4] = nettimeout;
   }

   public void set_REQGEN(int reqgen) {
      this.flds[6] = reqgen;
   }

   public void set_CLTID(int cltid) {
      this.clt_id = cltid;
   }

   public void set_FLAG(int flag) {
      this.flag = flag;
   }

   public void set_PRIO(int prio) {
      this.sendmtype = prio;
   }

   public void set_ws_TPException(int err) {
      this._TPException = err;
   }

   public void set_ws_tpurcode(int err) {
      this._tpurcode = err;
   }

   public void set_ws_Uunixerr(int err) {
      this._Uunixerr = err;
   }

   public void set_ws_tperrordetail(int err) {
      this._tperrordetail = err;
   }

   public int get_WSFLAGS() {
      return this.flds[3];
   }

   public void set_type(String type) {
      this.type = type;
   }

   public boolean prepareForCache() {
      return false;
   }

   public void _tmpresend(DataOutputStream encoder, tch myheader) throws TPException, IOException {
      int calculated_size = myheader.getHeaderLen();
      int output_data_size = false;
      calculated_size += 80;
      if (this.rqustsrvc == null) {
         calculated_size += 4;
      } else {
         calculated_size += 4 + Utilities.roundup4(this.rqustsrvc.length());
      }

      myheader.setLen(calculated_size);
      encoder.writeInt(this.flag);
      encoder.writeInt(this.tpevent);
      encoder.writeInt(this._TPException);
      encoder.writeInt(this._tpurcode);
      encoder.writeInt(this._Uunixerr);
      encoder.writeInt(this._tperrordetail);
      encoder.writeInt(this.connindx);
      encoder.writeInt(this.sendmtype);
      encoder.writeInt(this.rplymtype);
      encoder.writeInt(this.appkey);
      encoder.writeInt(this.clt_id);
      encoder.writeInt(this.WSop | this.opcode_flags);
      encoder.writeInt(this.rtn_val);

      int lcv;
      for(lcv = 0; lcv < 7; ++lcv) {
         encoder.writeInt(this.flds[lcv]);
      }

      if (this.rqustsrvc == null) {
         encoder.writeInt(0);
      } else {
         byte[] rqustsrvcBytes = Utilities.getEncBytes(this.rqustsrvc);
         encoder.writeInt(rqustsrvcBytes.length);
         encoder.write(rqustsrvcBytes);
         int pad_bytes = Utilities.roundup4(rqustsrvcBytes.length) - rqustsrvcBytes.length;

         for(lcv = 0; lcv < pad_bytes; ++lcv) {
            encoder.writeByte(0);
         }
      }

   }

   public final int _tmpostrecv(DataInputStream decoder, int recv_size, int hint_index) throws IOException {
      if (recv_size < 80) {
         WTCLogger.logErrorRecvSize(recv_size);
         return -1;
      } else {
         recv_size -= 80;
         this.flag = decoder.readInt();
         this.tpevent = decoder.readInt();
         this._TPException = decoder.readInt();
         this._tpurcode = decoder.readInt();
         this._Uunixerr = decoder.readInt();
         this._tperrordetail = decoder.readInt();
         this.connindx = decoder.readInt();
         this.sendmtype = decoder.readInt();
         this.rplymtype = decoder.readInt();
         this.appkey = decoder.readInt();
         this.clt_id = decoder.readInt();
         int opcode = decoder.readInt();
         this.WSop = opcode & 1073741823;
         this.opcode_flags = opcode & -1073741824;
         this.rtn_val = decoder.readInt();

         int lcv;
         for(lcv = 0; lcv < 7; ++lcv) {
            this.flds[lcv] = decoder.readInt();
         }

         int r_length = decoder.readInt();
         recv_size -= 4;
         int r_padded_length = Utilities.roundup4(r_length);
         byte[] typebyte = new byte[r_length];

         for(lcv = 0; lcv < r_length; ++lcv) {
            typebyte[lcv] = decoder.readByte();
         }

         for(recv_size -= r_length; lcv < r_padded_length; ++lcv) {
            decoder.readByte();
         }

         recv_size -= r_padded_length;
         if (recv_size > 0) {
            decoder.skipBytes(recv_size);
         }

         this.rqustsrvc = Utilities.getEncString(typebyte);
         return 0;
      }
   }

   public final int _tmpostrecv65(DataInputStream decoder, int recv_size, int hint_index) throws IOException {
      char[] rqsvc = new char[16];
      char[] typebuf = new char[8];
      this.flag = decoder.readInt();
      this.tpevent = decoder.readInt();
      this._TPException = decoder.readInt();
      this._tpurcode = decoder.readInt();
      this._Uunixerr = decoder.readInt();

      int lcv;
      for(lcv = 0; lcv < 4; ++lcv) {
         decoder.readInt();
         decoder.readInt();
         decoder.readInt();
         decoder.readInt();
         decoder.readInt();
         decoder.readInt();
         decoder.readInt();
         decoder.readInt();
      }

      this._tperrordetail = 0;
      this.rqustsrvc = Utilities.xdr_decode_vector_string(decoder, 16, rqsvc);
      this.connindx = decoder.readInt();
      this.sendmtype = decoder.readInt();
      decoder.skipBytes(4);
      this.rplymtype = decoder.readInt();
      decoder.skipBytes(4);
      this.appkey = decoder.readInt();
      decoder.skipBytes(32);
      decoder.skipBytes(64);
      this.clt_id = decoder.readInt();
      int opcode = decoder.readInt();
      this.WSop = opcode & 1073741823;
      this.opcode_flags = opcode & -1073741824;
      this.rtn_val = decoder.readInt();

      for(lcv = 0; lcv < 7; ++lcv) {
         this.flds[lcv] = decoder.readInt();
      }

      this.type = Utilities.xdr_decode_vector_string(decoder, 8, typebuf);
      return 0;
   }

   public void _tmpresend65(DataOutputStream encoder) throws TPException, IOException {
      int output_data_size = false;
      encoder.writeInt(this.flag);
      encoder.writeInt(this.tpevent);
      encoder.writeInt(this._TPException);
      encoder.writeInt(this._tpurcode);
      encoder.writeInt(this._Uunixerr);

      int lcv;
      for(lcv = 0; lcv < 4; ++lcv) {
         encoder.writeInt(0);
         encoder.writeInt(0);
         encoder.writeInt(0);
         encoder.writeInt(0);
         encoder.writeInt(0);
         encoder.writeInt(0);
         encoder.writeInt(0);
         encoder.writeInt(0);
      }

      Utilities.xdr_encode_vector_string(encoder, this.rqustsrvc, 16);
      encoder.writeInt(this.connindx);
      encoder.writeInt(this.sendmtype);
      encoder.writeInt(0);
      encoder.writeInt(this.rplymtype);
      encoder.writeInt(0);
      encoder.writeInt(this.appkey);

      for(lcv = 0; lcv < 8; ++lcv) {
         encoder.writeInt(0);
      }

      for(lcv = 0; lcv < 16; ++lcv) {
         encoder.writeInt(0);
      }

      encoder.writeInt(this.clt_id);
      encoder.writeInt(this.WSop | this.opcode_flags);
      encoder.writeInt(this.rtn_val);

      for(lcv = 0; lcv < 7; ++lcv) {
         encoder.writeInt(this.flds[lcv]);
      }

   }
}
