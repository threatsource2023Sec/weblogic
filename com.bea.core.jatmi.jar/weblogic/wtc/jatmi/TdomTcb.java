package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.Utilities;
import com.bea.core.jatmi.common.ntrace;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class TdomTcb extends tcb {
   private static final long serialVersionUID = -8185841036254593802L;
   private int lle_length;
   private int send_len;
   private int recv_len;
   private byte[] send_buf;
   private byte[] recv_buf;
   private int opcode = 0;
   private int reqid = 0;
   private int convid = 0;
   private int seqnum = 0;
   private int info = 0;
   private int flag = 0;
   private int tpevent = 0;
   private int diagnostic = 0;
   private int tpurcode = 0;
   private int errdetail = 0;
   private String service = null;
   private int msgprio = 50;
   private int features = 0;
   private String sending_domain;
   private int tm_release;
   private int dom_protocol;
   private int lle_flags;
   private int security_type;
   private transient byte[] myScratch;
   public static final int GWNW_XATMI_CALL = 1;
   public static final int GWNW_XATMI_REPLY = 2;
   public static final int GWNW_XATMI_FAILURE = 3;
   public static final int GWNW_XATMI_CONNECT = 4;
   public static final int GWNW_XATMI_DATA = 5;
   public static final int GWNW_XATMI_DISCON = 6;
   public static final int GWNW_XATMI_PREPARE = 7;
   public static final int GWNW_XATMI_READY = 8;
   public static final int GWNW_XATMI_COMMIT = 9;
   public static final int GWNW_XATMI_DONE = 10;
   public static final int GWNW_XATMI_COMPLETE = 11;
   public static final int GWNW_XATMI_ROLLBACK = 12;
   public static final int GWNW_XATMI_HEURISTIC = 13;
   public static final int GWNW_XATMI_PRE_NW_ACALL1 = 14;
   public static final int GWNW_XATMI_PRE_NW_ACALL1_RPLY = 15;
   public static final int GWNW_XATMI_PRE_NW_ACALL2 = 16;
   public static final int GWNW_XATMI_PRE_NW_ACALL2_RPLY = 17;
   public static final int GWNW_XATMI_PRE_NW_ACALL3 = 18;
   public static final int GWNW_XATMI_PRE_NW_ACALL3_RPLY = 19;
   public static final int GWNW_XATMI_PRE_NW_LLE = 20;
   public static final int GWNW_XATMI_PRE_NW_LLE_RPLY = 21;
   public static final int GWNW_XATMI_SEC_EXCHG_RQST = 22;
   public static final int GWNW_XATMI_SEC_EXCHG = 22;
   public static final int GWNW_XATMI_DMQUERY = 23;
   public static final int GWNW_XATMI_RDONLY = 24;
   private static final String GWNW_XATMI_NONE_STRING = "GWNW_XATMI_NONE";
   private static final String GWNW_XATMI_CALL_STRING = "GWNW_XATMI_CALL";
   private static final String GWNW_XATMI_REPLY_STRING = "GWNW_XATMI_REPLY";
   private static final String GWNW_XATMI_FAILURE_STRING = "GWNW_XATMI_FAILURE";
   private static final String GWNW_XATMI_CONNECT_STRING = "GWNW_XATMI_CONNECT";
   private static final String GWNW_XATMI_DATA_STRING = "GWNW_XATMI_DATA";
   private static final String GWNW_XATMI_DISCON_STRING = "GWNW_XATMI_DISCON";
   private static final String GWNW_XATMI_PREPARE_STRING = "GWNW_XATMI_PREPARE";
   private static final String GWNW_XATMI_READY_STRING = "GWNW_XATMI_READY";
   private static final String GWNW_XATMI_COMMIT_STRING = "GWNW_XATMI_COMMIT";
   private static final String GWNW_XATMI_DONE_STRING = "GWNW_XATMI_DONE";
   private static final String GWNW_XATMI_COMPLETE_STRING = "GWNW_XATMI_COMPLETE";
   private static final String GWNW_XATMI_ROLLBACK_STRING = "GWNW_XATMI_ROLLBACK";
   private static final String GWNW_XATMI_HEURISTIC_STRING = "GWNW_XATMI_HEURISTIC";
   private static final String GWNW_XATMI_PRE_NW_ACALL1_STRING = "GWNW_XATMI_PRE_NW_ACALL1";
   private static final String GWNW_XATMI_PRE_NW_ACALL1_RPLY_STRING = "GWNW_XATMI_PRE_NW_ACALL1_RPLY";
   private static final String GWNW_XATMI_PRE_NW_ACALL2_STRING = "GWNW_XATMI_PRE_NW_ACALL2";
   private static final String GWNW_XATMI_PRE_NW_ACALL2_RPLY_STRING = "GWNW_XATMI_PRE_NW_ACALL2_RPLY";
   private static final String GWNW_XATMI_PRE_NW_ACALL3_STRING = "GWNW_XATMI_PRE_NW_ACALL3";
   private static final String GWNW_XATMI_PRE_NW_ACALL3_RPLY_STRING = "GWNW_XATMI_PRE_NW_ACALL3_RPLY";
   private static final String GWNW_XATMI_PRE_NW_LLE_STRING = "GWNW_XATMI_PRE_NW_LLE";
   private static final String GWNW_XATMI_PRE_NW_LLE_RPLY_STRING = "GWNW_XATMI_PRE_NW_LLE_RPLY";
   private static final String GWNW_XATMI_SEC_EXCHG_STRING = "GWNW_XATMI_SEC_EXCHG";
   private static final String GWNW_XATMI_DMQUERY_STRING = "GWNW_XATMI_DMQUERY";
   private static final String GWNW_XATMI_RDONLY_STRING = "GWNW_XATMI_RDONLY";
   public static final int GW_NULLIDX = -1;
   public static final int DMSEC_NONE = 0;
   public static final int DMSEC_APP_PW = 1;
   public static final int DMSEC_DM_PW = 2;
   public static final int DMSEC_USER_PW = 3;
   public static final int DMSEC_CLEAR = 4;
   public static final int DMSEC_SAFE = 5;
   public static final int DMSEC_PRIVATE = 6;
   public static final int DMSEC_UNKNOWN = -1;
   public static final int DMSEC_FAILED = -2;
   public static final int DIAG_KEEPALIVE_RQST = 0;
   public static final int DIAG_KEEPALIVE_RPLY = 1;
   public static final int CHLG_LEN = 8;
   public static final int GW_CONVINIT = 1;
   public static final int GW_CONVRESP = 2;
   public static final int GW_CONVCONNECTED = 4;
   public static final int GW_HEURISTIC_MIX = 8;
   public static final int GW_HEURISTIC_HAZARD = 16;
   public static final int GW_NONEWBRANCH = 32;
   public static final int GW_ROLLBACKFROMSUBORDINATE = 64;
   public static final int GWT_FEATURE_SUPPORTED = 27;
   public static final int GWT_FEATURE_APPKEEPALIVE = 1;
   public static final int GWT_FEATURE_MBSTRING = 2;
   public static final int GWT_FEATURE_SNP_SA_3DES = 4;
   public static final int GWT_FEATURE_XA_1PC = 8;
   public static final int GWT_FEATURE_XA_RDONLY = 16;
   public static final int GWT_FEATURE_XDR64_COMPAT = 32768;
   public static final int GWT_FEATURE_XA_COMMXID = 4096;
   public static final int GWT_FEATURE_WTC = 65536;
   private static int GWT_FEATURE_SUPPORTED_RUNTIME = 27;

   public TdomTcb() {
      super((short)7);
      this.convid = -1;
   }

   public TdomTcb(int op, int req, int flags, String sv) {
      super((short)7);
      this.opcode = op;
      this.reqid = req;
      this.flag = flags;
      this.service = sv;
      this.convid = -1;
   }

   public static void setRuntimeFeatureSupported(int f) {
      GWT_FEATURE_SUPPORTED_RUNTIME |= f;
   }

   public static int getRuntimeFeatureSupported() {
      return GWT_FEATURE_SUPPORTED_RUNTIME;
   }

   public int getConvId() {
      return this.convid;
   }

   public void setConvId(int cid) {
      this.convid = cid;
   }

   public int get_opcode() {
      return this.opcode;
   }

   public void set_opcode(int op) {
      this.opcode = op;
   }

   public void set_msgprio(int mp) {
      this.msgprio = mp;
   }

   public void unset_msgprio(int mp) {
      this.msgprio = 50;
   }

   public int get_msgprio() {
      return this.msgprio;
   }

   public String get_sending_domain() {
      return this.sending_domain;
   }

   public String getSendingDom() {
      return this.sending_domain;
   }

   public void set_sending_domain(String op) {
      this.sending_domain = op;
   }

   public int get_info() {
      return this.info;
   }

   public void set_info(int f) {
      this.info = f;
   }

   public int get_convid() {
      return this.convid;
   }

   public void set_convid(int cd) {
      this.convid = cd;
   }

   public int get_seqnum() {
      return this.seqnum;
   }

   public void set_seqnum(int cd) {
      this.seqnum = cd;
   }

   public int get_reqid() {
      return this.reqid;
   }

   public void set_reqid(int rq) {
      this.reqid = rq;
   }

   public int get_flag() {
      return this.flag;
   }

   public void set_flag(int flags) {
      this.flag = flags;
   }

   public int get_tpevent() {
      return this.tpevent;
   }

   public void set_tpevent(int te) {
      this.tpevent = te;
   }

   public int get_errdetail() {
      return this.errdetail;
   }

   public void set_errdetail(int ed) {
      this.errdetail = ed;
   }

   public String get_service() {
      return this.service;
   }

   public void set_service(String sv) {
      this.service = sv;
   }

   public int getTpurcode() {
      return this.tpurcode;
   }

   public void set_tpurcode(int tu) {
      this.tpurcode = tu;
   }

   public int get_diagnostic() {
      return this.diagnostic;
   }

   public void set_diagnostic(int te) {
      this.diagnostic = te;
   }

   public int get_tm_release() {
      return this.tm_release;
   }

   public void set_tm_release(int rn) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TdomTcb/set_tm_release " + rn);
      }

      this.tm_release = rn;
      if (traceEnabled) {
         ntrace.doTrace("]/TdomTcb/set_tm_release");
      }

   }

   public int get_dom_protocol() {
      return this.dom_protocol;
   }

   public void set_dom_protocol(int dp) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TdomTcb/set_dom_protocol " + dp);
      }

      this.dom_protocol = dp;
      if (traceEnabled) {
         ntrace.doTrace("]/TdomTcb/set_dom_protocol");
      }

   }

   public int get_lle_flags() {
      return this.lle_flags;
   }

   public void set_lle_flags(int lln) {
      this.lle_flags = lln;
   }

   public void setSendSecPDU(byte[] buf, int len) {
      this.send_buf = buf;
      this.send_len = len;
   }

   public int getLenSendSecPDU() {
      return this.send_len;
   }

   public byte[] getBufSendSecPDU() {
      return this.send_buf;
   }

   public void setRecvSecPDU(byte[] buf, int len) {
      this.recv_buf = buf;
      this.recv_len = len;
   }

   public int getLenRecvSecPDU() {
      return this.recv_len;
   }

   public byte[] getBufRecvSecPDU() {
      return this.recv_buf;
   }

   public int get_security_type() {
      return this.security_type;
   }

   public void set_security_type(int st) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TdomTcb/set_security_type " + st);
      }

      this.security_type = st;
      if (traceEnabled) {
         ntrace.doTrace("]/TdomTcb/set_security_type");
      }

   }

   public void setLLELength(int len) {
      this.lle_length = len;
   }

   public int getLLELength() {
      return this.lle_length;
   }

   public int getDomProtocol() {
      return this.dom_protocol;
   }

   public void setFeaturesSupported(int f) {
      this.features = f;
   }

   public int getFeaturesSupported() {
      return this.features;
   }

   public boolean prepareForCache() {
      this.lle_length = 0;
      this.send_len = 0;
      this.recv_len = 0;
      this.send_buf = null;
      this.recv_buf = null;
      this.opcode = 0;
      this.reqid = 0;
      this.convid = -1;
      this.seqnum = 0;
      this.info = 0;
      this.flag = 0;
      this.tpevent = 0;
      this.diagnostic = 0;
      this.tpurcode = 0;
      this.errdetail = 0;
      this.service = null;
      this.msgprio = 50;
      this.features = 0;
      return true;
   }

   public void _tmpresend(DataOutputStream encoder, tch myheader) throws TPException, IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TdomTcb/_tmpresend/" + myheader);
      }

      int calculated_size = myheader.getHeaderLen();
      int output_data_size = false;
      calculated_size += 64 + Utilities.xdr_length_string(this.service);
      myheader.setLen(calculated_size);
      if (ntrace.isTraceEnabled(32)) {
         ntrace.doTrace("------ presend TDOM TCB ------------");
         ntrace.doTrace("opcode: " + print_opcode(this.opcode) + ", reqid: " + this.reqid + ", convid: " + this.convid + ", seqnum: " + this.seqnum);
         ntrace.doTrace("info: " + this.info + ", flag: " + this.flag + ", tpevent: " + this.tpevent + ", diagnostic: " + this.diagnostic);
         ntrace.doTrace("tpurcode: " + this.tpurcode + ", errdetail: " + this.errdetail + ", msgprio: " + this.msgprio);
         ntrace.doTrace("service: " + this.service);
      }

      try {
         if (traceEnabled) {
            ntrace.doTrace("/TdomTcb/_tmpresend/opcode=" + this.opcode + "/reqid=" + this.reqid);
         }

         encoder.writeInt(this.opcode);
         encoder.writeInt(this.reqid);
         encoder.writeInt(this.convid);
         encoder.writeInt(this.seqnum);
         encoder.writeInt(this.info);
         encoder.writeInt(this.flag);
         encoder.writeInt(this.tpevent);
         encoder.writeInt(this.diagnostic);
         encoder.writeInt(this.tpurcode);
         encoder.writeInt(this.errdetail);
         encoder.writeInt(0);
         encoder.writeInt(this.msgprio);
         this.msgprio = 50;
         encoder.writeInt(0);
         encoder.writeInt(0);
         encoder.writeInt(0);
         encoder.writeInt(0);
         Utilities.xdr_encode_string(encoder, this.service);
      } catch (IOException var9) {
         if (traceEnabled) {
            ntrace.doTrace("*]/TdomTcb/_tmpresend/10" + var9);
         }

         throw var9;
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TdomTcb/_tmpresend/20");
      }

   }

   public final int _tmpostrecv(DataInputStream decoder, int recv_size, int hint_index) throws IOException {
      if (this.myScratch == null) {
         this.myScratch = new byte[150];
      }

      this.opcode = decoder.readInt();
      this.reqid = decoder.readInt();
      this.convid = decoder.readInt();
      this.seqnum = decoder.readInt();
      this.info = decoder.readInt();
      this.flag = decoder.readInt();
      this.tpevent = decoder.readInt();
      this.diagnostic = decoder.readInt();
      this.tpurcode = decoder.readInt();
      this.errdetail = decoder.readInt();
      decoder.readInt();
      this.msgprio = decoder.readInt();
      decoder.readInt();
      decoder.readInt();
      decoder.readInt();
      decoder.readInt();
      this.service = Utilities.xdr_decode_string(decoder, this.myScratch);
      if (ntrace.isTraceEnabled(32)) {
         ntrace.doTrace("------ postrecv TDOM TCB ------------");
         ntrace.doTrace("opcode: " + print_opcode(this.opcode) + ", reqid: " + this.reqid + ", convid: " + this.convid + ", seqnum: " + this.seqnum);
         ntrace.doTrace("info: " + this.info + ", flag: " + this.flag + ", tpevent: " + this.tpevent + ", diagnostic: " + this.diagnostic);
         ntrace.doTrace("tpurcode: " + this.tpurcode + ", errdetail: " + this.errdetail + ", msgprio: " + this.msgprio);
         ntrace.doTrace("service: " + this.service);
      }

      return 0;
   }

   public static String print_opcode(int opc) {
      switch (opc) {
         case 0:
            return "GWNW_XATMI_NONE";
         case 1:
            return "GWNW_XATMI_CALL";
         case 2:
            return "GWNW_XATMI_REPLY";
         case 3:
            return "GWNW_XATMI_FAILURE";
         case 4:
            return "GWNW_XATMI_CONNECT";
         case 5:
            return "GWNW_XATMI_DATA";
         case 6:
            return "GWNW_XATMI_DISCON";
         case 7:
            return "GWNW_XATMI_PREPARE";
         case 8:
            return "GWNW_XATMI_READY";
         case 9:
            return "GWNW_XATMI_COMMIT";
         case 10:
            return "GWNW_XATMI_DONE";
         case 11:
            return "GWNW_XATMI_COMPLETE";
         case 12:
            return "GWNW_XATMI_ROLLBACK";
         case 13:
            return "GWNW_XATMI_HEURISTIC";
         case 14:
            return "GWNW_XATMI_PRE_NW_ACALL1";
         case 15:
            return "GWNW_XATMI_PRE_NW_ACALL1_RPLY";
         case 16:
            return "GWNW_XATMI_PRE_NW_ACALL2";
         case 17:
            return "GWNW_XATMI_PRE_NW_ACALL2_RPLY";
         case 18:
            return "GWNW_XATMI_PRE_NW_ACALL3";
         case 19:
            return "GWNW_XATMI_PRE_NW_ACALL3_RPLY";
         case 20:
            return "GWNW_XATMI_PRE_NW_LLE";
         case 21:
            return "GWNW_XATMI_PRE_NW_LLE_RPLY";
         case 22:
            return "GWNW_XATMI_SEC_EXCHG";
         case 23:
            return "GWNW_XATMI_DMQUERY";
         case 24:
            return "GWNW_XATMI_RDONLY";
         default:
            return new String("Unknown opcode " + opc);
      }
   }

   public String toString() {
      return new String(super.toString() + ":" + print_opcode(this.opcode) + ":" + this.reqid + ":" + this.convid + ":" + this.seqnum + ":" + this.info + ":" + this.flag + ":" + this.tpevent + ":" + this.diagnostic + ":" + this.tpurcode + ":" + this.errdetail + ":" + this.msgprio + ":" + this.service);
   }
}
