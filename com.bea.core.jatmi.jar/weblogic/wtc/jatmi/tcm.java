package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.Utilities;
import com.bea.core.jatmi.common.ntrace;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import weblogic.wtc.WTCLogger;

public final class tcm implements Serializable {
   private static final long serialVersionUID = -929861011534414844L;
   public tch header;
   public tcb body;
   private transient tch header_cache;
   private transient tcb body_cache;
   private transient TuxedoArrayOutputStream myWriteStream;
   private transient DataOutputStream myEncoder;
   private boolean dumpData = false;
   private boolean use64BitsLong = false;
   private int send_udata_len = 0;
   private int recv_udata_len = 0;

   public tcm() {
   }

   public tcm(short type, tcb body) {
      this.header = new tch(type);
      this.body = body;
   }

   public short getType() {
      return this.header == null ? -1 : this.header.getType();
   }

   public int read_tcm(DataInputStream decoder, int hint_index) throws IOException {
      this.header = new tch();
      this.header.read_tch(decoder);
      short type = this.header.getType();
      short type = false;
      int read_bytes = Utilities.roundup4(this.header.getLen());
      int recv_size = this.header.getLen() - this.header.getHeaderLen();
      UserTcb udata = new UserTcb(hint_index);
      if (this.use64BitsLong) {
         udata.setUse64BitsLong(true);
      }

      udata.dumpData(this.dumpData);
      switch (udata._tmpostrecv(decoder, recv_size, hint_index)) {
         case -2:
            return -read_bytes;
         case -1:
         default:
            return 0;
         case 0:
            this.recv_udata_len = udata.user_data_len;
            this.body = udata;
            return read_bytes;
      }
   }

   public int read_tcm(DataInputStream decoder, tch gotten_header) throws IOException {
      if (this.header_cache != null) {
         this.header = this.header_cache;
         this.header.myClone(gotten_header);
         this.header_cache = null;
      } else {
         this.header = new tch(gotten_header);
      }

      short type = this.header.getType();
      int recv_size = this.header.getLen() - this.header.getHeaderLen();
      switch (type) {
         case 2:
            TranTcb tmmsg_tran;
            if (this.body_cache != null) {
               tmmsg_tran = (TranTcb)this.body_cache;
               this.body_cache = null;
            } else {
               tmmsg_tran = new TranTcb();
            }

            tmmsg_tran._tmpostrecv(decoder, recv_size, -1);
            this.body = tmmsg_tran;
            break;
         case 3:
            WsTcb tmmsg_ws;
            if (this.body_cache != null) {
               tmmsg_ws = (WsTcb)this.body_cache;
               this.body_cache = null;
            } else {
               tmmsg_ws = new WsTcb();
            }

            tmmsg_ws._tmpostrecv(decoder, recv_size, -1);
            this.body = tmmsg_ws;
            break;
         case 4:
            UnsolTcb tmmsg_unsol;
            if (this.body_cache != null) {
               tmmsg_unsol = (UnsolTcb)this.body_cache;
               this.body_cache = null;
            } else {
               tmmsg_unsol = new UnsolTcb();
            }

            tmmsg_unsol._tmpostrecv(decoder, recv_size, -1);
            this.body = tmmsg_unsol;
            break;
         case 5:
            ComposHdrTcb tmmsg_compos_hdr;
            if (this.body_cache != null) {
               tmmsg_compos_hdr = (ComposHdrTcb)this.body_cache;
               this.body_cache = null;
            } else {
               tmmsg_compos_hdr = new ComposHdrTcb();
            }

            tmmsg_compos_hdr._tmpostrecv(decoder, recv_size, -1);
            this.body = tmmsg_compos_hdr;
            break;
         case 6:
            ComposFmlTcb tmmsg_compos_fml;
            if (this.body_cache != null) {
               tmmsg_compos_fml = (ComposFmlTcb)this.body_cache;
               this.body_cache = null;
            } else {
               tmmsg_compos_fml = new ComposFmlTcb();
            }

            tmmsg_compos_fml._tmpostrecv(decoder, recv_size, -1);
            this.body = tmmsg_compos_fml;
            break;
         case 7:
            TdomTcb tmmsg_tdom;
            if (this.body_cache != null) {
               tmmsg_tdom = (TdomTcb)this.body_cache;
               this.body_cache = null;
            } else {
               tmmsg_tdom = new TdomTcb();
            }

            tmmsg_tdom._tmpostrecv(decoder, recv_size, -1);
            this.body = tmmsg_tdom;
            break;
         case 8:
         case 11:
         case 12:
         case 13:
         case 14:
         default:
            decoder.skipBytes(recv_size);
            WTCLogger.logErrorUnknownTcmType(type);
            if ((gotten_header.getFlags() & 512) != 0) {
               return 0;
            }
            break;
         case 9:
            RouteTcb tmmsg_route;
            if (this.body_cache != null) {
               tmmsg_route = (RouteTcb)this.body_cache;
               this.body_cache = null;
            } else {
               tmmsg_route = new RouteTcb(0);
            }

            tmmsg_route._tmpostrecv(decoder, recv_size, -1);
            this.body = tmmsg_route;
            break;
         case 10:
            TdomTranTcb tmmsg_tdom_tran;
            if (this.body_cache != null) {
               tmmsg_tdom_tran = (TdomTranTcb)this.body_cache;
               this.body_cache = null;
            } else {
               tmmsg_tdom_tran = new TdomTranTcb();
            }

            tmmsg_tdom_tran._tmpostrecv(decoder, recv_size, -1);
            this.body = tmmsg_tdom_tran;
            break;
         case 15:
            new AaaTcb();
            AaaTcb tmmsg_aaa;
            if (this.body_cache != null) {
               tmmsg_aaa = (AaaTcb)this.body_cache;
               this.body_cache = null;
            } else {
               tmmsg_aaa = new AaaTcb();
            }

            tmmsg_aaa._tmpostrecv(decoder, recv_size, -1);
            this.body = tmmsg_aaa;
            break;
         case 16:
            CalloutTcb tmmsg_callout;
            if (this.body_cache != null) {
               tmmsg_callout = (CalloutTcb)this.body_cache;
               this.body_cache = null;
            } else {
               tmmsg_callout = new CalloutTcb();
            }

            tmmsg_callout._tmpostrecv(decoder, recv_size, -1);
            this.body = tmmsg_callout;
            break;
         case 17:
            TdomValsTcb tmmsg_tdom_vals;
            if (this.body_cache != null) {
               tmmsg_tdom_vals = (TdomValsTcb)this.body_cache;
               this.body_cache = null;
            } else {
               tmmsg_tdom_vals = new TdomValsTcb();
            }

            tmmsg_tdom_vals._tmpostrecv(decoder, recv_size, -1);
            this.body = tmmsg_tdom_vals;
            break;
         case 18:
            CodesetTcb tmmsg_codeset;
            if (this.body_cache != null) {
               tmmsg_codeset = (CodesetTcb)this.body_cache;
               this.body_cache = null;
            } else {
               tmmsg_codeset = new CodesetTcb();
            }

            tmmsg_codeset._tmpostrecv(decoder, recv_size, -1);
            this.body = tmmsg_codeset;
            break;
         case 19:
            new MetaTcb();
            MetaTcb tmmsg_meta;
            if (this.body_cache != null) {
               tmmsg_meta = (MetaTcb)this.body_cache;
               this.body_cache = null;
            } else {
               tmmsg_meta = new MetaTcb();
            }

            tmmsg_meta._tmpostrecv(decoder, recv_size, -1);
            this.body = tmmsg_meta;
      }

      return Utilities.roundup4(this.header.getLen());
   }

   public boolean prepareForCache() {
      boolean retval = false;
      this.header_cache = this.header;
      this.header = null;
      this.body_cache = this.body;
      this.body = null;
      if (this.header_cache != null) {
         if (!this.header_cache.prepareForCache()) {
            this.header_cache = null;
         } else {
            retval = true;
         }
      }

      if (this.body_cache != null) {
         if (!this.body_cache.prepareForCache()) {
            this.body_cache = null;
         } else {
            retval = true;
         }
      }

      return retval;
   }

   private void resetWrite() {
      if (this.myWriteStream != null) {
         this.myWriteStream.reset();
      } else {
         this.myWriteStream = new TuxedoArrayOutputStream();
         this.myEncoder = new DataOutputStream(this.myWriteStream);
      }

   }

   public void write_tcm(DataOutputStream encoder) throws TPException, IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/write_tcm/");
      }

      int send_size = false;
      if (this.header != null && this.body != null) {
         byte[] fillin;
         int send_size;
         try {
            this.resetWrite();
            int initial_size = this.myEncoder.size();
            this.body._tmpresend(this.myEncoder, this.header);
            send_size = this.myEncoder.size() - initial_size;
            fillin = this.myWriteStream.getByteArrayReference();

            String type;
            String subtype;
            int offset;
            try {
               type = Utilities.baReadXdrString(fillin, 0);
               offset = 4;
               if (type != null) {
                  offset += Utilities.roundup4(type.length());
               }

               subtype = Utilities.baReadXdrString(fillin, offset);
               if (subtype != null) {
                  offset += Utilities.roundup4(subtype.length());
               }

               offset += 4;
            } catch (Exception var10) {
               type = null;
               subtype = null;
               offset = 0;
            }

            this.send_udata_len = fillin.length - offset;
            if (this.dumpData) {
               ntrace.doTrace("Outbound UDATA: buffer type(" + type + ", " + subtype + ")");
               new TDumpByte("User Data", fillin, offset, -1);
            }
         } catch (TPException var12) {
            if (traceEnabled) {
               ntrace.doTrace("*]/write_tcm/10/" + var12);
            }

            throw var12;
         } catch (IOException var13) {
            if (traceEnabled) {
               ntrace.doTrace("*]/write_tcm/20/" + var13);
            }

            throw var13;
         }

         try {
            this.header.write_tch(encoder);
            encoder.write(fillin, 0, send_size);
         } catch (IOException var11) {
            WTCLogger.logIOEbadTCMwrite(var11.getMessage());
            if (traceEnabled) {
               ntrace.doTrace("*]/write_tcm/30/" + var11);
            }

            throw var11;
         }

         if (traceEnabled) {
            ntrace.doTrace("]/write_tcm/40/");
         }

      } else {
         if (traceEnabled) {
            ntrace.doTrace("*]/write_tcm/10/TPESYSTEM");
         }

         throw new TPException(12, "Invalid TCM. header=" + this.header + " or body=" + this.body + " is null");
      }
   }

   public void doDump(boolean b) {
      this.dumpData = b;
   }

   public int getSendUdataLen() {
      return this.send_udata_len;
   }

   public int getRecvUdataLen() {
      return this.recv_udata_len;
   }

   public void setUse64BitsLong(boolean val) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      this.use64BitsLong = val;
      if (this.body != null && this.body.getType() == 0) {
         ((UserTcb)this.body).setUse64BitsLong(val);
      }

      if (traceEnabled) {
         ntrace.doTrace("/tcm/setUse64BitsLong/" + this.use64BitsLong);
      }

   }

   public boolean getUse64BitsLong() {
      return this.use64BitsLong;
   }
}
