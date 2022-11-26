package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.Utilities;
import com.bea.core.jatmi.common.ntrace;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.security.SecureRandom;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public final class tfmh implements Serializable {
   private static final long serialVersionUID = 6842149962692098467L;
   private metahdr mhdr = new metahdr();
   private int magic;
   private int ltype_idx;
   private int tmfmh_flag;
   private boolean isThisWS = false;
   private int wsh_protocol;
   private byte[] ws65_challenge;
   private byte[] challenge_rp;
   private byte[] ws65_ticket;
   private byte[] ws65_session_key;
   protected byte[] challenge;
   private int tstamp;
   public tcm user;
   public tcm ws;
   public tcm tran;
   public tcm unsol;
   public tcm compos_hdr;
   public tcm compos_fml;
   public tcm tdom;
   public tcm tdomtran;
   public tcm route;
   public tcm tdom_vals;
   public tcm callout;
   public tcm AAA;
   public tcm meta;
   public tcm codeset;
   private transient tcm ws_cache;
   private transient tcm tran_cache;
   private transient tcm unsol_cache;
   private transient tcm compos_hdr_cache;
   private transient tcm compos_fml_cache;
   private transient tcm tdom_cache;
   private transient tcm tdomtran_cache;
   private transient tcm tdomvals_cache;
   private transient tcm route_cache;
   private transient tcm AAA_cache;
   private transient tcm meta_cache;
   private transient tch tch_cache;
   private transient byte[] dom65_fillin;
   private transient TuxedoArrayOutputStream myWriteStream;
   private transient DataOutputStream myEncoder;
   private transient byte[] myScratch;
   private boolean dumpUData = false;
   private int userDataSize = 0;
   private boolean use64BitsLong = false;
   protected static final int TFMH_SIZE = 12;
   public static final int TM_MAGIC = -1862035368;
   public static final int TM_SMAGIC = 1938831426;
   protected static final int FMH_IN_USE = 65536;
   protected static final int FMH_EDIT = 131072;
   protected static final int FMH_STORAGE = 262144;
   protected static final int FMH_NO_USER = 524288;
   public static final int TFMH_WS_TYPE = 0;
   public static final int TFMH_DOM_TYPE = 1;
   protected static final int TMTYPELEN = 8;
   protected static final int TMSTYPELEN = 16;
   protected static final int MAXTSTRING = 78;
   protected static final int WSMAXNETADDR = 64;
   protected static final int MAXMTYPELEN = 15;
   public static final int TMSEC_TICKETLEN = 1536;
   public static final int TMSEC_KEYLEN = 8;
   public static final int MAXDOMAIN_LEN = 30;
   public static final int CHLG_LEN = 8;

   public tfmh() {
      this.magic = -1862035368;
      this.ltype_idx = -1;
      this.tmfmh_flag = 65536 | 524288;
   }

   public tfmh(int type) {
      switch (type) {
         case 0:
            this.mhdr.mprotocol = 49;
            this.isThisWS = true;
            break;
         case 1:
            this.mhdr.mprotocol = 20;
      }

      this.magic = -1862035368;
      this.ltype_idx = -1;
      this.tmfmh_flag = 65536 | 524288;
   }

   public tfmh(int ltype_idx, tcm udata) {
      this.magic = -1862035368;
      this.ltype_idx = ltype_idx;
      this.tmfmh_flag = 65536;
      this.isThisWS = true;
      this.user = udata;
   }

   public tfmh(int ltype_idx, tcm udata, int type) {
      switch (type) {
         case 0:
            this.mhdr.mprotocol = 49;
            this.isThisWS = true;
            break;
         case 1:
         default:
            this.mhdr.mprotocol = 20;
      }

      this.magic = -1862035368;
      this.ltype_idx = ltype_idx;
      this.tmfmh_flag = 65536;
      this.user = udata;
   }

   public byte[] get_ws_challenge() {
      return this.ws65_challenge;
   }

   public byte[] get_challenge_rp() {
      return this.challenge_rp;
   }

   public byte[] get_ws65_ticket() {
      return this.ws65_ticket;
   }

   public byte[] get_ws65_session_key() {
      return this.ws65_session_key;
   }

   public void set_ws65_ticket(byte[] ticket) {
      this.ws65_ticket = ticket;
   }

   public void set_ws65_session_key(byte[] session_key) {
      this.ws65_session_key = session_key;
   }

   public void set_timestamp(int timestamp) {
      this.tstamp = timestamp;
   }

   public int getUserDataSize() {
      return this.userDataSize;
   }

   public boolean prepareForCache() {
      if (!this.mhdr.prepareForCache()) {
         return false;
      } else {
         this.magic = -1862035368;
         this.ltype_idx = -1;
         this.tmfmh_flag = 65536 | 524288;
         this.user = null;
         if (this.tch_cache != null && !this.tch_cache.prepareForCache()) {
            this.tch_cache = null;
         }

         if (this.ws != null) {
            if (!this.ws.prepareForCache()) {
               this.ws = null;
            } else {
               this.ws_cache = this.ws;
               this.ws = null;
            }
         }

         if (this.tran != null) {
            if (!this.tran.prepareForCache()) {
               this.tran = null;
            } else {
               this.tran_cache = this.tran;
               this.tran = null;
            }
         }

         if (this.unsol != null) {
            if (!this.unsol.prepareForCache()) {
               this.unsol = null;
            } else {
               this.unsol_cache = this.unsol;
               this.unsol = null;
            }
         }

         if (this.compos_hdr != null) {
            if (!this.compos_hdr.prepareForCache()) {
               this.compos_hdr = null;
            } else {
               this.compos_hdr_cache = this.compos_hdr;
               this.compos_hdr = null;
            }
         }

         if (this.compos_fml != null) {
            if (!this.compos_fml.prepareForCache()) {
               this.compos_fml = null;
            } else {
               this.compos_fml_cache = this.compos_fml;
               this.compos_fml = null;
            }
         }

         if (this.tdom != null) {
            if (!this.tdom.prepareForCache()) {
               this.tdom = null;
            } else {
               this.tdom_cache = this.tdom;
               this.tdom = null;
            }
         }

         if (this.tdomtran != null) {
            if (!this.tdomtran.prepareForCache()) {
               this.tdomtran = null;
            } else {
               this.tdomtran_cache = this.tdomtran;
               this.tdomtran = null;
            }
         }

         if (this.AAA != null) {
            if (!this.AAA.prepareForCache()) {
               this.AAA = null;
            } else {
               this.AAA_cache = this.AAA;
               this.AAA = null;
            }
         }

         if (this.meta != null) {
            if (!this.meta.prepareForCache()) {
               this.meta = null;
            } else {
               this.meta_cache = this.meta;
               this.meta = null;
            }
         }

         if (this.route != null) {
            if (!this.route.prepareForCache()) {
               this.route = null;
            } else {
               this.route_cache = this.route;
               this.route = null;
            }
         }

         if (this.tdom_vals != null) {
            if (!this.tdom_vals.prepareForCache()) {
               this.tdom_vals = null;
            } else {
               this.tdomvals_cache = this.tdom_vals;
               this.tdom_vals = null;
            }
         }

         return true;
      }
   }

   public void setTMREPLY(boolean reply) {
      if (this.mhdr != null) {
         this.mhdr.setTMREPLY(reply);
      }

   }

   public void set_TPENQUEUE(boolean to_enqueue) {
      if (this.mhdr != null) {
         this.mhdr.set_TMENQUEUE(to_enqueue);
      }

   }

   public metahdr getMetahdr() {
      return this.mhdr;
   }

   public int getProtocol() {
      return this.mhdr == null ? 20 : this.mhdr.getProtocol();
   }

   private void resetWrite() {
      if (this.myWriteStream != null) {
         this.myWriteStream.reset();
      } else {
         this.myWriteStream = new TuxedoArrayOutputStream();
         this.myEncoder = new DataOutputStream(this.myWriteStream);
      }

   }

   public int write_tfmh(DataOutputStream encoder, int cmplimit) throws IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/write_tfmh/");
      }

      byte[] tcm_data = null;
      int tcm_size = 0;
      byte[] cdata = null;
      int offset = 0;
      boolean use_compressed = false;
      int calculated_size = 32;
      calculated_size += 12;

      byte[] tcm_data;
      try {
         this.resetWrite();
         int initial_size = this.myEncoder.size();
         int new_size;
         if (this.user == null) {
            this.tmfmh_flag |= 524288;
         } else {
            this.tmfmh_flag &= ~524288;
            if ((this.getMetahdr().getFlags() & 67108864) != 0) {
               this.user.setUse64BitsLong(true);
            }

            if (this.dumpUData) {
               this.user.doDump(true);
               this.user.write_tcm(this.myEncoder);
               this.user.doDump(false);
            } else {
               this.user.write_tcm(this.myEncoder);
            }

            this.userDataSize = this.user.getSendUdataLen();
            UserTcb user_tcb = (UserTcb)this.user.body;
            switch (user_tcb.getHintIndex()) {
               case 23:
                  TypedFML32 myFml32 = (TypedFML32)user_tcb.user_data;
                  if (!myFml32.hasMBStringFields()) {
                     break;
                  }
               case 26:
                  MBStringTypes mbstring = (MBStringTypes)user_tcb.user_data;
                  String mbencoding = mbstring.getMBEncoding();
                  if (mbencoding == null) {
                     mbencoding = MBEncoding.getDefaultMBEncoding();
                  }

                  this.codeset = new tcm((short)18, new CodesetTcb(mbencoding));
            }

            new_size = this.myEncoder.size();
            calculated_size += new_size - initial_size;
            tcm_size += new_size - initial_size;
            initial_size = new_size;
            if (traceEnabled) {
               ntrace.doTrace("/write_tfmh/wrote user tcm/" + calculated_size);
            }
         }

         if (this.ws != null) {
            this.ws.write_tcm(this.myEncoder);
            new_size = this.myEncoder.size();
            calculated_size += new_size - initial_size;
            tcm_size += new_size - initial_size;
            initial_size = new_size;
            if (traceEnabled) {
               ntrace.doTrace("/write_tfmh/wrote ws tcm/" + calculated_size);
            }
         }

         if (this.tran != null) {
            this.tran.write_tcm(this.myEncoder);
            new_size = this.myEncoder.size();
            calculated_size += new_size - initial_size;
            tcm_size += new_size - initial_size;
            initial_size = new_size;
            if (traceEnabled) {
               ntrace.doTrace("/write_tfmh/wrote tran tcm/" + calculated_size);
            }
         }

         if (this.AAA != null) {
            this.AAA.write_tcm(this.myEncoder);
            new_size = this.myEncoder.size();
            calculated_size += new_size - initial_size;
            tcm_size += new_size - initial_size;
            initial_size = new_size;
            if (traceEnabled) {
               ntrace.doTrace("/write_tfmh/wrote AAA tcm/" + calculated_size);
            }
         }

         if (this.meta != null) {
            this.meta.write_tcm(this.myEncoder);
            new_size = this.myEncoder.size();
            calculated_size += new_size - initial_size;
            tcm_size += new_size - initial_size;
            initial_size = new_size;
            if (traceEnabled) {
               ntrace.doTrace("/write_tfmh/wrote meta tcm/" + calculated_size);
            }
         }

         if (this.unsol != null) {
            this.unsol.write_tcm(this.myEncoder);
            new_size = this.myEncoder.size();
            calculated_size += new_size - initial_size;
            tcm_size += new_size - initial_size;
            initial_size = new_size;
            if (traceEnabled) {
               ntrace.doTrace("/write_tfmh/wrote unsol tcm/" + calculated_size);
            }
         }

         if (this.compos_hdr != null) {
            this.compos_hdr.write_tcm(this.myEncoder);
            new_size = this.myEncoder.size();
            calculated_size += new_size - initial_size;
            tcm_size += new_size - initial_size;
            initial_size = new_size;
            if (traceEnabled) {
               ntrace.doTrace("/write_tfmh/wrote compos_hdr tcm/" + calculated_size);
            }
         }

         if (this.compos_fml != null) {
            this.compos_fml.write_tcm(this.myEncoder);
            new_size = this.myEncoder.size();
            calculated_size += new_size - initial_size;
            tcm_size += new_size - initial_size;
            initial_size = new_size;
            if (traceEnabled) {
               ntrace.doTrace("/write_tfmh/wrote compos_fml tcm/" + calculated_size);
            }
         }

         if (this.tdom != null) {
            this.tdom.write_tcm(this.myEncoder);
            new_size = this.myEncoder.size();
            calculated_size += new_size - initial_size;
            tcm_size += new_size - initial_size;
            initial_size = new_size;
            if (traceEnabled) {
               ntrace.doTrace("/write_tfmh/wrote tdom tcm/" + calculated_size);
            }
         }

         if (this.tdomtran != null) {
            this.tdomtran.write_tcm(this.myEncoder);
            new_size = this.myEncoder.size();
            calculated_size += new_size - initial_size;
            tcm_size += new_size - initial_size;
            initial_size = new_size;
            if (traceEnabled) {
               ntrace.doTrace("/write_tfmh/wrote tdomtran tcm/" + calculated_size);
            }
         }

         if (this.route != null) {
            this.route.write_tcm(this.myEncoder);
            new_size = this.myEncoder.size();
            calculated_size += new_size - initial_size;
            tcm_size += new_size - initial_size;
            initial_size = new_size;
            if (traceEnabled) {
               ntrace.doTrace("/write_tfmh/wrote route tcm/" + calculated_size);
            }
         }

         if (this.tdom_vals != null) {
            this.tdom_vals.write_tcm(this.myEncoder);
            new_size = this.myEncoder.size();
            calculated_size += new_size - initial_size;
            tcm_size += new_size - initial_size;
            initial_size = new_size;
            if (traceEnabled) {
               ntrace.doTrace("/write_tfmh/wrote tdomvals tcm/" + calculated_size);
            }
         }

         if (this.codeset != null) {
            this.codeset.write_tcm(this.myEncoder);
            new_size = this.myEncoder.size();
            calculated_size += new_size - initial_size;
            tcm_size += new_size - initial_size;
            if (traceEnabled) {
               ntrace.doTrace("/write_tfmh/wrote codeset tcm/" + calculated_size);
            }
         }

         tcm_data = this.myWriteStream.getByteArrayReference();
      } catch (TPException var17) {
         if (traceEnabled) {
            ntrace.doTrace("*]/write_tfmh/10/" + var17);
         }

         throw new IOException("Error writing tfmh: " + var17);
      } catch (IOException var18) {
         if (traceEnabled) {
            ntrace.doTrace("*]/write_tfmh/20/" + var18);
         }

         throw var18;
      }

      if (calculated_size >= cmplimit) {
         if (traceEnabled) {
            ntrace.doTrace("/write_tfmh/Using compressed buffer/");
         }

         ByteArrayOutputStream compress_bstream = new ByteArrayOutputStream(calculated_size + 100);
         DataOutputStream compress_encoder = new DataOutputStream(compress_bstream);
         compress_encoder.writeInt(this.magic);
         compress_encoder.writeInt(this.ltype_idx);
         compress_encoder.writeInt(this.tmfmh_flag);
         if (tcm_data != null) {
            compress_encoder.write(tcm_data, 0, tcm_size);
         }

         byte[] tcdata = compress_bstream.toByteArray();
         int tcdata_size = compress_encoder.size();
         cdata = new byte[Utilities.roundup4(tcdata_size)];
         Deflater def = new Deflater();
         def.setInput(tcdata);

         while(offset < tcdata_size && !def.needsInput()) {
            offset += def.deflate(cdata, offset, cdata.length - offset);
         }

         def.finish();

         while(offset < tcdata_size && !def.finished()) {
            offset += def.deflate(cdata, offset, cdata.length - offset);
         }

         if (offset < tcdata_size) {
            use_compressed = true;
         }

         if (traceEnabled) {
            ntrace.doTrace("/write_tfmh/compressed size=" + offset);
         }
      }

      metahdr var10000 = this.mhdr;
      var10000.flags |= Integer.MIN_VALUE;
      if (!use_compressed) {
         if (traceEnabled) {
            ntrace.doTrace("/write_tfmh/Not compressed/calculated_size=" + calculated_size + "/");
         }

         this.mhdr.size = calculated_size;

         try {
            this.mhdr.write_metahdr(encoder);
            encoder.writeInt(this.magic);
            encoder.writeInt(this.ltype_idx);
            encoder.writeInt(this.tmfmh_flag);
            if (traceEnabled) {
               ntrace.doTrace("/write_tfmh/magic=" + this.magic + "/ltype_idx=" + this.ltype_idx + "/tmfmh_flag=" + this.tmfmh_flag);
            }

            if (tcm_data != null) {
               encoder.write(tcm_data, 0, tcm_size);
            }
         } catch (NullPointerException var16) {
            throw new IOException("Connection is down - output stream is null");
         }
      } else {
         this.mhdr.uncmprs_sz = calculated_size;
         this.mhdr.size = 32 + offset;
         var10000 = this.mhdr;
         var10000.flags |= 256;
         if (traceEnabled) {
            ntrace.doTrace("/write_tfmh/useing compressed buffer/calculated_size=" + calculated_size + "/size=" + this.mhdr.size);
         }

         this.mhdr.write_metahdr(encoder);
         if (cdata != null) {
            encoder.write(cdata, 0, offset);
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("]/write_tfmh/100/0/");
      }

      return 0;
   }

   public int write_dom_65_tfmh(DataOutputStream encoder, String sending_dom, int protocol, int cmplimit) throws IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/write_dom_65_tfmh/" + sending_dom + "/" + protocol + "/" + cmplimit);
      }

      TdomTcb tmmsg_tdom = null;
      TdomTranTcb tmmsg_tdomtran = null;
      ComposHdrTcb tmmsg_compos_hdr = null;
      ComposFmlTcb tmmsg_compos_fml = null;
      TypedBuffer our_user_data = null;
      String type = null;
      int type_encoded_size = 4;
      String subtype = null;
      int subtype_encoded_size = 4;
      int hint_index = -1;
      String service = null;
      byte[] dom65_fillin = null;
      int send_size = 0;
      boolean retvalue = true;
      int sec_len = 0;
      String tranidparent = null;
      byte[] tranid = null;
      boolean use_old_protocol = false;
      boolean compos = false;
      DataOutputStream use_encoder = null;
      boolean tryCompression = false;
      if (sending_dom != null && this.tdom != null && (tmmsg_tdom = (TdomTcb)this.tdom.body) != null && protocol <= 13) {
         if (this.tdomtran != null) {
            label339: {
               tmmsg_tdomtran = (TdomTranTcb)this.tdomtran.body;
               if ((tranidparent = tmmsg_tdomtran.getNwtranidparent()) != null && tranidparent.length() <= 32) {
                  if ((tranid = tmmsg_tdomtran.getGlobalTransactionId()) != null && tranid.length == 32) {
                     break label339;
                  }

                  if (traceEnabled) {
                     ntrace.doTrace("]/write_dom_65_tfmh/30/-1/Unknown txid format " + Utilities.prettyByteArray(tranid) + "/" + tmmsg_tdomtran);
                  }

                  return -1;
               }

               if (traceEnabled) {
                  ntrace.doTrace("]/write_dom_65_tfmh/20/-1/Invalid tranidparent " + tranidparent + "/" + tmmsg_tdomtran);
               }

               return -1;
            }
         }

         if (this.user != null) {
            our_user_data = ((UserTcb)this.user.body).user_data;
            type = our_user_data.getType();
            type_encoded_size = Utilities.xdr_length_string(type);
            subtype = our_user_data.getSubtype();
            subtype_encoded_size = Utilities.xdr_length_string(subtype);
            hint_index = our_user_data.getHintIndex();
         }

         if (this.compos_hdr != null && this.compos_fml != null) {
            compos = true;
            tmmsg_compos_hdr = (ComposHdrTcb)this.compos_hdr.body;
            tmmsg_compos_fml = (ComposFmlTcb)this.compos_fml.body;
            tmmsg_compos_hdr.setTypeAndSubtype(type, subtype, hint_index);
            type = new String("COMPOS");
            subtype = null;
            type_encoded_size = Utilities.xdr_length_string(type);
            subtype_encoded_size = Utilities.xdr_length_string(subtype);
         }

         int opcode = tmmsg_tdom.get_opcode();
         if (protocol < 13 || opcode != 1 && opcode != 5 && opcode != 4 && opcode != 6 && opcode != 2) {
            use_old_protocol = true;
         }

         int calculated_size;
         if (use_old_protocol) {
            calculated_size = 524;
         } else {
            calculated_size = 32 + Utilities.xdr_length_string(sending_dom) + 4 + 4 + 4 + Utilities.xdr_length_string(tmmsg_tdomtran == null ? null : tmmsg_tdomtran.getNwtranidparent()) + (tranid == null ? 0 : tranid.length) * 4 + type_encoded_size + subtype_encoded_size;
         }

         if (traceEnabled) {
            ntrace.doTrace("/write_dom_65_tfmh/header calculated_size=" + calculated_size);
         }

         if (compos) {
            calculated_size += tmmsg_compos_hdr.getR65size();
            calculated_size += tmmsg_compos_fml.getR65size();
         }

         switch (opcode) {
            case 1:
            case 4:
               if ((service = tmmsg_tdom.get_service()) == null) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/write_dom_65_tfmh/40/-1/");
                  }

                  return -1;
               }

               if (use_old_protocol) {
                  calculated_size += 316;
               } else {
                  calculated_size += 4 + Utilities.xdr_length_string(service);
               }
               break;
            case 2:
               calculated_size += 4;
               break;
            case 3:
            case 6:
               calculated_size += 8;
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
               break;
            case 14:
               calculated_size += this.calDom65Acall1();
               break;
            case 15:
               calculated_size += this.calDom65Acall1Rply();
               break;
            case 16:
            case 17:
            case 18:
            case 19:
               sec_len = tmmsg_tdom.getLenSendSecPDU();
               calculated_size += sec_len;
               break;
            case 20:
            case 21:
               calculated_size += this.calDom65LLE();
               break;
            default:
               if (traceEnabled) {
                  ntrace.doTrace("]/write_dom_65_tfmh/50/-1/" + opcode);
               }

               return -1;
         }

         if (traceEnabled) {
            ntrace.doTrace("/write_dom_65_tfmh/opcode calculated_size=" + calculated_size);
         }

         if (our_user_data != null) {
            try {
               ByteArrayOutputStream interior_bstream = new ByteArrayOutputStream();
               DataOutputStream interior_encoder = new DataOutputStream(interior_bstream);
               if ((this.getMetahdr().getFlags() & 67108864) != 0) {
                  ((UserTcb)this.user.body).setUse64BitsLong(true);
               }

               our_user_data._tmpresend(interior_encoder);
               send_size = interior_encoder.size();
               dom65_fillin = interior_bstream.toByteArray();
               if (this.dumpUData) {
                  ntrace.doTrace("Outbound UDATA: buffer type(" + type + ", " + subtype + ")");
                  new TDumpByte("65 User Data", dom65_fillin, -1);
               }
            } catch (TPException var38) {
               if (traceEnabled) {
                  ntrace.doTrace("]/write_dom_65_tfmh/70/" + var38);
               }

               return -1;
            } catch (IOException var39) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/write_dom_65_tfmh/80/" + var39);
               }

               throw var39;
            }

            calculated_size += send_size;
            this.userDataSize = send_size;
         }

         if (traceEnabled) {
            ntrace.doTrace("/write_dom_65_tfmh/total calculated_size=" + calculated_size);
         }

         encoder.writeInt(1938831426);
         encoder.writeInt(opcode);
         encoder.writeInt(tmmsg_tdom.get_reqid());
         encoder.writeInt(tmmsg_tdom.get_convid());
         encoder.writeInt(tmmsg_tdom.get_seqnum());
         encoder.writeInt(tmmsg_tdom.get_info());
         if (calculated_size >= cmplimit) {
            this.resetWrite();
            use_encoder = this.myEncoder;
            tryCompression = true;
         } else {
            use_encoder = encoder;
            encoder.writeInt(1);
            encoder.writeInt(calculated_size);
         }

         if (use_old_protocol) {
            Utilities.xdr_encode_vector_string(use_encoder, sending_dom, 32);
         } else {
            Utilities.xdr_encode_string(use_encoder, sending_dom);
         }

         use_encoder.writeInt(tmmsg_tdom.get_flag());
         if (tmmsg_tdomtran == null || opcode != 1 && opcode != 4) {
            use_encoder.writeInt(tmmsg_tdom.get_tpevent());
         } else {
            use_encoder.writeInt(tmmsg_tdomtran.getTransactionTimeout());
         }

         if (tmmsg_tdomtran == null) {
            use_encoder.writeInt(0);
            if (use_old_protocol) {
               Utilities.xdr_encode_vector_string(use_encoder, (String)null, 32);
               Utilities.xdr_encode_vector_string(use_encoder, (String)null, 32);
            } else {
               Utilities.xdr_encode_string(use_encoder, (String)null);
            }
         } else {
            use_encoder.writeInt(32);
            if (use_old_protocol) {
               Utilities.xdr_encode_vector_string(use_encoder, tranidparent, 32);
            } else {
               Utilities.xdr_encode_string(use_encoder, tranidparent);
            }

            for(int lcv = 0; lcv < 32; ++lcv) {
               use_encoder.writeInt(tranid[lcv] & 255);
            }
         }

         if (our_user_data == null && !compos) {
            if (use_old_protocol) {
               Utilities.xdr_encode_vector_string(use_encoder, (String)null, 8);
               Utilities.xdr_encode_vector_string(use_encoder, (String)null, 16);
            } else {
               Utilities.xdr_encode_string(use_encoder, (String)null);
               Utilities.xdr_encode_string(use_encoder, (String)null);
            }
         } else if (use_old_protocol) {
            Utilities.xdr_encode_vector_string(use_encoder, type, 8);
            Utilities.xdr_encode_vector_string(use_encoder, subtype, 16);
         } else {
            Utilities.xdr_encode_string(use_encoder, type);
            Utilities.xdr_encode_string(use_encoder, subtype);
         }

         if (traceEnabled) {
            ntrace.doTrace("/tfmh/write_dom_65_tfmh/TdomTcb=" + tmmsg_tdom);
         }

         switch (opcode) {
            case 1:
            case 4:
               use_encoder.writeInt(service.length() + 1);
               if (use_old_protocol) {
                  Utilities.xdr_encode_vector_string(use_encoder, service, 78);
               } else {
                  Utilities.xdr_encode_string(use_encoder, service);
               }
               break;
            case 2:
               use_encoder.writeInt(tmmsg_tdom.getTpurcode());
               break;
            case 3:
            case 6:
               use_encoder.writeInt(tmmsg_tdom.get_diagnostic());
               use_encoder.writeInt(tmmsg_tdom.getTpurcode());
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
               break;
            case 14:
               retvalue = this.sendDom65Acall1(use_encoder, tmmsg_tdom);
               break;
            case 15:
               retvalue = this.sendDom65Acall1Rply(use_encoder, tmmsg_tdom);
               break;
            case 16:
            case 17:
            case 18:
            case 19:
               byte[] sec_data = tmmsg_tdom.getBufSendSecPDU();
               use_encoder.write(sec_data, 0, sec_len);
               retvalue = true;
               break;
            case 20:
               retvalue = this.sendDom65LLE(use_encoder, tmmsg_tdom);
               break;
            case 21:
               retvalue = this.sendDom65LLERply(use_encoder, tmmsg_tdom);
               break;
            default:
               if (traceEnabled) {
                  ntrace.doTrace("]/write_dom_65_tfmh/90/-1/" + opcode);
               }

               return -1;
         }

         if (!retvalue) {
            if (traceEnabled) {
               ntrace.doTrace("]/write_dom_65_tfmh/100/-1/" + opcode);
            }

            return -1;
         } else {
            if (compos) {
               tmmsg_compos_hdr._tmpresend65(use_encoder);
               tmmsg_compos_fml._tmpresend65(use_encoder);
            }

            if (send_size != 0) {
               use_encoder.write(dom65_fillin, 0, send_size);
            }

            if (tryCompression) {
               if (traceEnabled) {
                  ntrace.doTrace("/write_dom_65_tfmh/Using compressed buffer/");
               }

               int offset = 0;
               byte[] tcdata = this.myWriteStream.getByteArrayReference();
               int tcdata_size = this.myWriteStream.size();
               if (tcdata_size + 32 != calculated_size) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/write_dom_65_tfmh/102/-1/assertion failed/" + tcdata_size + "/" + calculated_size);
                  }

                  return -1;
               }

               byte[] cdata = new byte[Utilities.roundup4(tcdata_size)];
               Deflater def = new Deflater();
               def.setInput(tcdata, 0, tcdata_size);

               while(offset < tcdata_size && !def.needsInput()) {
                  offset += def.deflate(cdata, offset, cdata.length - offset);
               }

               def.finish();

               while(offset < tcdata_size && !def.finished()) {
                  offset += def.deflate(cdata, offset, cdata.length - offset);
               }

               if (offset < tcdata_size) {
                  if (traceEnabled) {
                     ntrace.doTrace("/write_dom_65_tfmh/compressed size " + offset + " is smaller than " + tcdata_size + " using...");
                  }

                  encoder.writeInt(257);
                  encoder.writeInt(32 + offset);
                  encoder.write(cdata, 0, offset);
               } else {
                  if (traceEnabled) {
                     ntrace.doTrace("/write_dom_65_tfmh/compression failed, using original/");
                  }

                  encoder.writeInt(1);
                  encoder.writeInt(calculated_size);
                  encoder.write(tcdata, 0, tcdata_size);
               }
            }

            if (traceEnabled) {
               ntrace.doTrace("]/write_dom_65_tfmh/110/0/");
            }

            return 0;
         }
      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/write_dom_65_tfmh/10/-1/");
         }

         return -1;
      }
   }

   public int read_tfmh(DataInputStream decoder) throws IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/read_tfmh/");
      }

      DataInputStream use_decoder = null;
      int rc = 0;
      if (decoder == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/read_tfmh/10/-1");
         }

         return -1;
      } else {
         this.mhdr.read_metahdr(decoder);
         if (this.mhdr.size <= 32) {
            if (traceEnabled) {
               ntrace.doTrace("]/read_tfmh/20/-1");
            }

            return -1;
         } else {
            UserTcb user_tcb;
            if ((this.mhdr.flags & 256) != 0) {
               if (traceEnabled) {
                  ntrace.doTrace("/read_tfmh/got compressed buffer/size=" + this.mhdr.size + "/uncmprs_sz=" + this.mhdr.uncmprs_sz);
               }

               int offset = 0;
               int rounded_size = this.mhdr.size - 32;
               byte[] compressed_data = new byte[rounded_size];
               decoder.readFully(compressed_data);
               rounded_size = Utilities.roundup4(this.mhdr.uncmprs_sz);
               byte[] decompressed_data = new byte[rounded_size];
               Inflater inf = new Inflater();
               inf.setInput(compressed_data);

               while(!inf.finished()) {
                  try {
                     offset += inf.inflate(decompressed_data, offset, decompressed_data.length - offset);
                  } catch (DataFormatException var21) {
                     if (traceEnabled) {
                        ntrace.doTrace("]/read_tfmh/30/-1");
                     }

                     return -1;
                  }

                  if (offset >= decompressed_data.length) {
                     byte[] tmp = new byte[decompressed_data.length + 4000];
                     System.arraycopy(decompressed_data, 0, tmp, 0, decompressed_data.length);
                     decompressed_data = tmp;
                     user_tcb = null;
                  }
               }

               this.mhdr.size = offset + 32;
               this.mhdr.uncmprs_sz = 0;
               metahdr var10000 = this.mhdr;
               var10000.flags &= -257;
               DataInputStream cmprs_decoder = new DataInputStream(new ByteArrayInputStream(decompressed_data, 0, offset));
               use_decoder = cmprs_decoder;
               if (traceEnabled) {
                  ntrace.doTrace("/read_tfmh/after decompress size=" + this.mhdr.size);
               }
            } else {
               use_decoder = decoder;
            }

            if (this.isThisWS && this.mhdr.mprotocol <= 46) {
               if (traceEnabled) {
                  ntrace.doTrace("]/read_tfmh/35/1 return talking WSL/WSH");
               }

               return 1;
            } else {
               this.magic = use_decoder.readInt();
               if (this.magic != 1938831426 && this.magic != -1862035368) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/read_tfmh/40/-1");
                  }

                  return -1;
               } else {
                  this.ltype_idx = use_decoder.readInt();
                  this.tmfmh_flag = use_decoder.readInt();
                  int remaining_size = this.mhdr.size - (32 + 12);
                  tcm current_tcm;
                  int current_size;
                  if ((this.tmfmh_flag & 524288) == 0) {
                     current_tcm = new tcm();
                     if ((this.mhdr.flags & 67108864) != 0 && (this.ltype_idx == 23 || this.ltype_idx == 18 || this.ltype_idx == 24 || this.ltype_idx == 19)) {
                        boolean enable64BitsLong = Boolean.getBoolean("weblogic.wtc.use64BitsLong");
                        if (!enable64BitsLong) {
                           throw new IOException("Failed to read from remote domain that asks for support for 64-bit FLD_LONG.");
                        }

                        this.use64BitsLong = true;
                        if (traceEnabled) {
                           ntrace.doTrace("/read_tfmh/Remote domain asks to support for 64-bit FLD_LONG in TypedBuffer");
                        }
                     }

                     if (this.use64BitsLong) {
                        current_tcm.setUse64BitsLong(true);
                     }

                     if (this.dumpUData) {
                        current_tcm.doDump(true);
                        current_size = current_tcm.read_tcm(use_decoder, this.ltype_idx);
                        current_tcm.doDump(false);
                        this.dumpUData = false;
                     } else {
                        current_size = current_tcm.read_tcm(use_decoder, this.ltype_idx);
                     }

                     if (current_size == 0) {
                        if (traceEnabled) {
                           ntrace.doTrace("]/read_tfmh/50/-1");
                        }

                        return -1;
                     }

                     if (current_size < 0) {
                        rc = -2;
                        current_size = -current_size;
                     }

                     remaining_size -= current_size;
                     this.user = current_tcm;
                     this.userDataSize = current_tcm.getRecvUdataLen();
                  }

                  if (this.tch_cache == null) {
                     this.tch_cache = new tch();
                  }

                  while(remaining_size > 0) {
                     if (!this.tch_cache.prepareForCache()) {
                        this.tch_cache = new tch();
                     }

                     this.tch_cache.read_tch(use_decoder);
                     current_tcm = null;
                     switch (this.tch_cache.getType()) {
                        case 2:
                           current_tcm = this.tran_cache;
                           this.tran_cache = null;
                           break;
                        case 3:
                           current_tcm = this.ws_cache;
                           this.ws_cache = null;
                           break;
                        case 4:
                           current_tcm = this.unsol_cache;
                           this.unsol_cache = null;
                           break;
                        case 5:
                           current_tcm = this.compos_hdr_cache;
                           this.compos_hdr_cache = null;
                           break;
                        case 6:
                           current_tcm = this.compos_fml_cache;
                           this.compos_fml_cache = null;
                           break;
                        case 7:
                           current_tcm = this.tdom_cache;
                           this.tdom_cache = null;
                        case 8:
                        case 11:
                        case 12:
                        case 13:
                        case 14:
                        case 16:
                        case 18:
                        default:
                           break;
                        case 9:
                           current_tcm = this.route_cache;
                           this.route_cache = null;
                           break;
                        case 10:
                           current_tcm = this.tdomtran_cache;
                           this.tdomtran_cache = null;
                           break;
                        case 15:
                           current_tcm = this.AAA_cache;
                           this.AAA_cache = null;
                           break;
                        case 17:
                           current_tcm = this.tdomvals_cache;
                           this.tdomvals_cache = null;
                           break;
                        case 19:
                           current_tcm = this.meta_cache;
                           this.meta_cache = null;
                     }

                     if (current_tcm == null) {
                        current_tcm = new tcm();
                     }

                     if ((current_size = current_tcm.read_tcm(use_decoder, this.tch_cache)) == 0) {
                        if (traceEnabled) {
                           ntrace.doTrace("]/read_tfmh/60/-1");
                        }

                        return -1;
                     }

                     remaining_size -= current_size;
                     short current_type;
                     switch (current_type = current_tcm.getType()) {
                        case 0:
                        case 1:
                        case 8:
                        case 11:
                        case 12:
                        case 13:
                        case 14:
                        case 20:
                        default:
                           if (traceEnabled) {
                              ntrace.doTrace("]/read_tfmh/70/-1/" + current_type);
                           }

                           return -1;
                        case 2:
                           this.tran = current_tcm;
                           break;
                        case 3:
                           this.ws = current_tcm;
                           break;
                        case 4:
                           this.unsol = current_tcm;
                           break;
                        case 5:
                           this.compos_hdr = current_tcm;
                           break;
                        case 6:
                           this.compos_fml = current_tcm;
                           break;
                        case 7:
                           this.tdom = current_tcm;
                           break;
                        case 9:
                           this.route = current_tcm;
                           break;
                        case 10:
                           this.tdomtran = current_tcm;
                           break;
                        case 15:
                           this.AAA = current_tcm;
                           break;
                        case 16:
                           this.callout = current_tcm;
                           break;
                        case 17:
                           this.tdom_vals = current_tcm;
                           break;
                        case 18:
                           this.codeset = current_tcm;
                           CodesetTcb codeset_tcb = (CodesetTcb)this.codeset.body;
                           String mbencoding = codeset_tcb.getMBEncoding();
                           if (this.user != null) {
                              user_tcb = (UserTcb)this.user.body;
                              MBStringTypes udata = (MBStringTypes)user_tcb.user_data;
                              udata.setMBEncoding(mbencoding);
                              switch (user_tcb.getHintIndex()) {
                                 case 23:
                                    TypedFML32 fml32 = (TypedFML32)udata;
                                    fml32.convertMBStringFieldsFromBytes((String)null);
                                    break;
                                 case 26:
                                    TypedMBString mbstring = (TypedMBString)udata;
                                    mbstring.convertFromBytes((String)null);
                              }
                           }
                           break;
                        case 19:
                           this.meta = current_tcm;
                     }
                  }

                  if (traceEnabled) {
                     ntrace.doTrace("]/read_tfmh/80/" + rc);
                  }

                  return rc;
               }
            }
         }
      }
   }

   public int read_dom_65_tfmh(DataInputStream decoder, int protocol) throws IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/read_dom_65_tfmh/" + protocol);
      }

      TdomTranTcb tmmsg_tdomtran = null;
      ComposHdrTcb tmmsg_compos_hdr = null;
      ComposFmlTcb tmmsg_compos_fml = null;
      char[] tident2_helper = new char[78];
      String type = null;
      String subtype = null;
      boolean retvalue = true;
      int timeout = 0;
      int txidsize = false;
      String tranidparent = null;
      byte[] nwtxid = null;
      boolean use_old_protocol = false;
      DataInputStream use_decoder = null;
      if (decoder != null && protocol <= 13) {
         if (this.myScratch == null) {
            this.myScratch = new byte[150];
         }

         int magic;
         if ((magic = decoder.readInt()) != 1938831426) {
            if (traceEnabled) {
               ntrace.doTrace("]/read_dom_65_tfmh/20/-1/" + magic + "/" + 1938831426);
            }

            return -1;
         } else {
            int opcode = decoder.readInt();
            if (traceEnabled) {
               ntrace.doTrace("/tfmh/read_dom_65_tfmh/opcode=" + TdomTcb.print_opcode(opcode));
            }

            if (protocol < 13 || opcode != 1 && opcode != 5 && opcode != 4 && opcode != 6 && opcode != 2) {
               use_old_protocol = true;
            }

            TdomTcb tmmsg_tdom;
            if (opcode != 15 && opcode != 16 && opcode != 17 && opcode != 20 && opcode != 21 && opcode != 18 && opcode != 19 || (tmmsg_tdom = (TdomTcb)this.tdom.body) == null) {
               tmmsg_tdom = new TdomTcb();
            }

            tmmsg_tdom.set_opcode(opcode);
            tmmsg_tdom.set_reqid(decoder.readInt());
            tmmsg_tdom.set_convid(decoder.readInt());
            tmmsg_tdom.set_seqnum(decoder.readInt());
            tmmsg_tdom.set_info(decoder.readInt());
            this.mhdr.flags = decoder.readInt();
            this.mhdr.size = decoder.readInt();
            if ((this.mhdr.flags & 256) != 0) {
               if (traceEnabled) {
                  ntrace.doTrace("/read_dom_65_tfmh/got compressed buffer/size=" + this.mhdr.size);
               }

               int offset = 0;
               int rounded_size = this.mhdr.size - 32;
               byte[] compressed_data = new byte[rounded_size];
               decoder.readFully(compressed_data);
               rounded_size += 1000;
               byte[] decompressed_data = new byte[rounded_size];
               Inflater inf = new Inflater();
               inf.setInput(compressed_data);

               while(!inf.finished()) {
                  try {
                     offset += inf.inflate(decompressed_data, offset, decompressed_data.length - offset);
                  } catch (DataFormatException var38) {
                     if (traceEnabled) {
                        ntrace.doTrace("]/read_dom_65_tfmh/30/-1");
                     }

                     return -1;
                  }

                  if (offset >= decompressed_data.length) {
                     byte[] tmp = new byte[decompressed_data.length + 4000];
                     System.arraycopy(decompressed_data, 0, tmp, 0, decompressed_data.length);
                     decompressed_data = tmp;
                     Object var45 = null;
                  }
               }

               this.mhdr.size = offset + 32;
               this.mhdr.uncmprs_sz = 0;
               metahdr var10000 = this.mhdr;
               var10000.flags &= -257;
               DataInputStream cmprs_decoder = new DataInputStream(new ByteArrayInputStream(decompressed_data, 0, offset));
               use_decoder = cmprs_decoder;
               if (traceEnabled) {
                  ntrace.doTrace("/read_dom_65_tfmh/after decompress size=" + this.mhdr.size);
               }
            } else {
               use_decoder = decoder;
            }

            int bufsize = this.mhdr.size;
            if (traceEnabled) {
               ntrace.doTrace("/read_dom_65_tfmh/bufsize=" + bufsize + " available=" + decoder.available());
            }

            bufsize -= 32;
            String sending_domain;
            if (use_old_protocol) {
               sending_domain = Utilities.xdr_decode_vector_string(use_decoder, 32, tident2_helper);
               bufsize -= 128;
               if (sending_domain != null) {
                  tmmsg_tdom.set_sending_domain(sending_domain);
               }
            } else {
               sending_domain = Utilities.xdr_decode_string(use_decoder, this.myScratch);
               bufsize -= Utilities.xdr_length_string(sending_domain);
               if (sending_domain != null) {
                  tmmsg_tdom.set_sending_domain(sending_domain);
               }
            }

            tmmsg_tdom.set_flag(use_decoder.readInt());
            bufsize -= 4;
            if (opcode != 1 && opcode != 4) {
               tmmsg_tdom.set_tpevent(use_decoder.readInt());
            } else {
               timeout = use_decoder.readInt();
            }

            bufsize -= 4;
            int txidsize;
            if ((txidsize = use_decoder.readInt()) != 0) {
               if (txidsize > 32) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/read_dom_65_tfmh/30/-1/" + txidsize);
                  }

                  return -1;
               }

               tmmsg_tdomtran = new TdomTranTcb();
               tmmsg_tdomtran.setTransactionTimeout(timeout);
               this.tdomtran = new tcm((short)10, tmmsg_tdomtran);
            }

            bufsize -= 4;
            int lcv;
            if (tmmsg_tdomtran == null) {
               if (use_old_protocol) {
                  Utilities.xdr_decode_vector_string(use_decoder, 32, tident2_helper);
                  bufsize -= 128;
                  Utilities.xdr_decode_vector_string(use_decoder, 32, tident2_helper);
                  bufsize -= 128;
               } else {
                  tranidparent = Utilities.xdr_decode_string(use_decoder, this.myScratch);
                  bufsize -= Utilities.xdr_length_string(tranidparent);
               }
            } else {
               byte[] nwtxid;
               if (use_old_protocol) {
                  tranidparent = Utilities.xdr_decode_vector_string(use_decoder, 32, tident2_helper);
                  bufsize -= 128;
                  tmmsg_tdomtran.setNwtranidparent(tranidparent);
                  nwtxid = new byte[txidsize];

                  for(lcv = 0; lcv < txidsize; ++lcv) {
                     nwtxid[lcv] = (byte)(use_decoder.readInt() & 255);
                  }

                  while(lcv < 32) {
                     use_decoder.readInt();
                     ++lcv;
                  }

                  bufsize -= 128;
                  tmmsg_tdomtran.setGlobalTransactionId(nwtxid);
               } else {
                  tranidparent = Utilities.xdr_decode_string(use_decoder, this.myScratch);
                  bufsize -= Utilities.xdr_length_string(tranidparent);
                  tmmsg_tdomtran.setNwtranidparent(tranidparent);
                  nwtxid = new byte[txidsize];

                  for(lcv = 0; lcv < txidsize; ++lcv) {
                     nwtxid[lcv] = (byte)(use_decoder.readInt() & 255);
                  }

                  bufsize -= txidsize * 4;
                  tmmsg_tdomtran.setGlobalTransactionId(nwtxid);
               }
            }

            if (use_old_protocol) {
               type = Utilities.xdr_decode_vector_string(use_decoder, 8, tident2_helper);
               bufsize -= 32;
               subtype = Utilities.xdr_decode_vector_string(use_decoder, 16, tident2_helper);
               bufsize -= 64;
            } else {
               type = Utilities.xdr_decode_string(use_decoder, this.myScratch);
               bufsize -= Utilities.xdr_length_string(type);
               subtype = Utilities.xdr_decode_string(use_decoder, this.myScratch);
               bufsize -= Utilities.xdr_length_string(subtype);
            }

            switch (opcode = tmmsg_tdom.get_opcode()) {
               case 1:
               case 4:
                  int original_size = use_decoder.readInt() - 1;
                  String service;
                  if (use_old_protocol) {
                     service = Utilities.xdr_decode_vector_string(use_decoder, 78, tident2_helper);
                     if (service != null) {
                        tmmsg_tdom.set_service(service);
                     }

                     bufsize -= 316;
                  } else {
                     service = Utilities.xdr_decode_string(use_decoder, this.myScratch);
                     bufsize -= 4 + Utilities.xdr_length_string(service);
                     if (service != null) {
                        tmmsg_tdom.set_service(new String(service));
                     }
                  }
                  break;
               case 2:
                  tmmsg_tdom.set_tpurcode(use_decoder.readInt());
                  bufsize -= 4;
                  break;
               case 3:
               case 6:
                  tmmsg_tdom.set_diagnostic(use_decoder.readInt());
                  tmmsg_tdom.set_tpurcode(use_decoder.readInt());
                  bufsize -= 8;
                  break;
               case 5:
                  if ((tmmsg_tdom.get_flag() & 6144) != 0) {
                     tmmsg_tdom.set_diagnostic(22);
                  }
               case 7:
               case 8:
               case 9:
               case 10:
               case 11:
               case 12:
               case 13:
                  break;
               case 14:
                  retvalue = this.recvDom65Acall1(use_decoder, tmmsg_tdom);
                  bufsize -= this.calDom65Acall1();
                  break;
               case 15:
                  retvalue = this.recvDom65Acall1Rply(use_decoder, tmmsg_tdom);
                  bufsize -= this.calDom65Acall1Rply();
                  break;
               case 16:
               case 17:
               case 18:
               case 19:
                  lcv = tmmsg_tdom.getLenRecvSecPDU();
                  byte[] rawdata;
                  if (lcv < bufsize) {
                     rawdata = new byte[bufsize];
                     tmmsg_tdom.setRecvSecPDU(rawdata, bufsize);
                  } else {
                     rawdata = tmmsg_tdom.getBufRecvSecPDU();
                  }

                  int total = 0;
                  int lcv = false;

                  while((lcv = use_decoder.read(rawdata, total, bufsize - total)) != -1 && (total += lcv) < bufsize) {
                  }

                  if (lcv == -1) {
                     if (traceEnabled) {
                        ntrace.doTrace("]/read_dom_65_tfmh/55/-1/retvalue false ");
                     }

                     return -1;
                  }

                  retvalue = true;
                  bufsize = 0;
                  break;
               case 20:
                  retvalue = this.recvDom65LLE(use_decoder, tmmsg_tdom);
                  bufsize -= this.calDom65LLE();
                  break;
               case 21:
                  retvalue = this.recvDom65LLERply(use_decoder, tmmsg_tdom);
                  bufsize -= this.calDom65LLERply();
                  break;
               default:
                  if (traceEnabled) {
                     ntrace.doTrace("]/read_dom_65_tfmh/50/-1/" + opcode);
                  }

                  return -1;
            }

            if (!retvalue) {
               if (traceEnabled) {
                  ntrace.doTrace("]/read_dom_65_tfmh/60/-1/retvalue false ");
               }

               return -1;
            } else {
               this.tdom = new tcm((short)7, tmmsg_tdom);
               if (bufsize <= 0) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/read_dom_65_tfmh/70/0/" + bufsize);
                  }

                  return 0;
               } else if (type == null) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/read_dom_65_tfmh/80/-1");
                  }

                  return -1;
               } else {
                  if (traceEnabled) {
                     ntrace.doTrace("/read_dom_65_tfmh/prior compos bufsize=" + bufsize + " available=" + use_decoder.available());
                  }

                  if (type.equals("COMPOS")) {
                     tmmsg_compos_hdr = new ComposHdrTcb();
                     if (!tmmsg_compos_hdr._tmpostrecv65(use_decoder)) {
                        if (traceEnabled) {
                           ntrace.doTrace("]/read_dom_65_tfmh/90/-1");
                        }

                        return -1;
                     }

                     bufsize -= tmmsg_compos_hdr.getR65size();
                     type = tmmsg_compos_hdr.getComposType();
                     subtype = tmmsg_compos_hdr.getComposSubtype();
                     tmmsg_compos_fml = new ComposFmlTcb();
                     if (tmmsg_compos_fml._tmpostrecv65(use_decoder) != 0) {
                        if (traceEnabled) {
                           ntrace.doTrace("]/read_dom_65_tfmh/100/-1");
                        }

                        return -1;
                     }

                     bufsize -= tmmsg_compos_fml.getR65size();
                     this.compos_hdr = new tcm((short)5, tmmsg_compos_hdr);
                     this.compos_fml = new tcm((short)6, tmmsg_compos_fml);
                  }

                  if (traceEnabled) {
                     ntrace.doTrace("/read_dom_65_tfmh/post compos bufsize=" + bufsize + " available=" + use_decoder.available());
                  }

                  if (bufsize <= 0) {
                     if (traceEnabled) {
                        ntrace.doTrace("]/read_dom_65_tfmh/110/0/" + bufsize);
                     }

                     return 0;
                  } else if (type != null && type.length() != 0) {
                     TypedBuffer tb;
                     if ((tb = TypedBufferFactory.createTypedBuffer(type, subtype)) == null) {
                        if (traceEnabled) {
                           ntrace.doTrace("]/read_dom_65_tfmh/130/-1");
                        }

                        return -1;
                     } else {
                        this.userDataSize = bufsize;
                        if (this.dumpUData) {
                           ntrace.doTrace("Inbound 65 UDATA: buffer type(" + type + ", " + subtype + ")");
                           byte[] tmp = new byte[bufsize];
                           use_decoder.read(tmp);
                           ByteArrayInputStream bais = new ByteArrayInputStream(tmp);
                           use_decoder = new DataInputStream(bais);
                           new TDumpByte("User Data", tmp, -1);
                           this.dumpUData = false;
                        }

                        try {
                           tb._tmpostrecv(use_decoder, bufsize);
                        } catch (TPException var37) {
                           if (traceEnabled) {
                              ntrace.doTrace("]/read_dom_65_tfmh/140/-1/" + var37);
                           }

                           return -1;
                        }

                        this.user = new tcm((short)0, new UserTcb(tb));
                        if (traceEnabled) {
                           ntrace.doTrace("]/read_dom_65_tfmh/150/0");
                        }

                        return 0;
                     }
                  } else {
                     if (traceEnabled) {
                        ntrace.doTrace("]/read_dom_65_tfmh/120/-1");
                     }

                     return -1;
                  }
               }
            }
         }
      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/read_dom_65_tfmh/10/-1");
         }

         return -1;
      }
   }

   private int calDom65Acall1() {
      int size = 16;
      return size;
   }

   private int calDom65Acall1Rply() {
      int size = 20;
      return size;
   }

   private int calDom65LLE() {
      int size = 16;
      return size;
   }

   private int calDom65LLERply() {
      int size = 16;
      return size;
   }

   private boolean sendDom65Acall1(DataOutputStream encoder, TdomTcb msg) throws IOException {
      encoder.writeInt(81);
      encoder.writeInt(20);
      encoder.writeInt(msg.get_lle_flags());
      encoder.writeInt(TdomTcb.getRuntimeFeatureSupported());
      return true;
   }

   private boolean sendDom65Acall1Rply(DataOutputStream encoder, TdomTcb msg) throws IOException {
      encoder.writeInt(msg.get_security_type());
      encoder.writeInt(81);
      encoder.writeInt(20);
      encoder.writeInt(msg.get_lle_flags());
      encoder.writeInt(TdomTcb.getRuntimeFeatureSupported());
      return true;
   }

   private boolean sendDom65LLE(DataOutputStream encoder, TdomTcb msg) throws IOException {
      encoder.writeInt(msg.getLLELength());
      encoder.writeInt(0);
      encoder.writeInt(0);
      encoder.writeInt(0);
      return true;
   }

   private boolean sendDom65LLERply(DataOutputStream encoder, TdomTcb msg) throws IOException {
      encoder.writeInt(msg.getLLELength());
      encoder.writeInt(0);
      encoder.writeInt(0);
      encoder.writeInt(0);
      return true;
   }

   private boolean recvDom65Acall1(DataInputStream decoder, TdomTcb msg) throws IOException {
      msg.set_tm_release(decoder.readInt());
      msg.set_dom_protocol(decoder.readInt());
      msg.set_lle_flags(decoder.readInt());
      msg.setFeaturesSupported(decoder.readInt());
      return true;
   }

   private boolean recvDom65Acall1Rply(DataInputStream decoder, TdomTcb msg) throws IOException {
      msg.set_security_type(decoder.readInt());
      msg.set_tm_release(decoder.readInt());
      msg.set_dom_protocol(decoder.readInt());
      msg.set_lle_flags(decoder.readInt());
      msg.setFeaturesSupported(decoder.readInt());
      return true;
   }

   private boolean recvDom65LLE(DataInputStream decoder, TdomTcb msg) throws IOException {
      msg.setLLELength(decoder.readInt());
      decoder.readInt();
      decoder.readInt();
      decoder.readInt();
      return true;
   }

   private boolean recvDom65LLERply(DataInputStream decoder, TdomTcb msg) throws IOException {
      msg.setLLELength(decoder.readInt());
      decoder.readInt();
      decoder.readInt();
      decoder.readInt();
      return true;
   }

   public int read_ws_65_tfmh(DataInputStream decoder, boolean estcon_65) throws IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/read_ws_65_tfmh/");
      }

      WsTcb tmmsg_ws = null;
      UserTcb tmmsg_user = null;
      ComposHdrTcb tmmsg_compos_hdr = null;
      ComposFmlTcb tmmsg_compos_fml = null;
      DataInputStream cmprs_decoder = null;
      DataInputStream use_decoder = null;
      int rc = false;
      byte[] b = null;
      char[] stypebuf = new char[16];
      if (decoder == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/read_ws_65_tfmh/10/-1");
         }

         return -1;
      } else {
         metahdr var10000;
         if (!estcon_65) {
            this.mhdr.read_metahdr(decoder);
            if (this.mhdr.size <= 32) {
               if (traceEnabled) {
                  ntrace.doTrace("]/read_ws_65_tfmh/20/-1");
               }

               return -1;
            }

            int indata_size = this.mhdr.size - 32;
            byte[] indata = new byte[indata_size];
            decoder.readFully(indata);
            DataInputStream indecoder = new DataInputStream(new ByteArrayInputStream(indata, 0, indata_size));
            decoder = indecoder;
            if ((this.mhdr.flags & 256) != 0) {
               if (traceEnabled) {
                  ntrace.doTrace("/read_ws_65_tfmh/got compressed buffer/size=" + this.mhdr.size + "/uncmprs_sz=" + this.mhdr.uncmprs_sz);
               }

               int offset = 0;
               int rounded_size = this.mhdr.size - 32;
               byte[] compressed_data = new byte[rounded_size];
               indecoder.readFully(compressed_data);
               rounded_size = Utilities.roundup4(this.mhdr.uncmprs_sz);
               byte[] decompressed_data = new byte[rounded_size];
               Inflater inf = new Inflater();
               inf.setInput(compressed_data);

               while(!inf.finished()) {
                  try {
                     offset += inf.inflate(decompressed_data, offset, decompressed_data.length - offset);
                  } catch (DataFormatException var38) {
                     if (traceEnabled) {
                        ntrace.doTrace("]/read_ws_65_tfmh/30/-1");
                     }

                     return -1;
                  }

                  if (offset >= decompressed_data.length) {
                     byte[] tmp = new byte[decompressed_data.length + 4000];
                     System.arraycopy(decompressed_data, 0, tmp, 0, decompressed_data.length);
                     decompressed_data = tmp;
                     Object var40 = null;
                  }
               }

               this.mhdr.size = offset + 32;
               this.mhdr.uncmprs_sz = 0;
               var10000 = this.mhdr;
               var10000.flags &= -257;
               cmprs_decoder = new DataInputStream(new ByteArrayInputStream(decompressed_data, 0, offset));
               use_decoder = cmprs_decoder;
               if (traceEnabled) {
                  ntrace.doTrace("/read_ws_65_tfmh/after decompress size=" + this.mhdr.size);
               }
            } else {
               use_decoder = indecoder;
            }
         } else {
            use_decoder = decoder;
         }

         var10000 = this.mhdr;
         var10000.flags &= -2;
         var10000 = this.mhdr;
         var10000.flags &= Integer.MAX_VALUE;
         if (use_decoder == null) {
            if (traceEnabled) {
               ntrace.doTrace("]/read_ws_65_tfmh/40/-1");
            }

            return -1;
         } else {
            int descrim = use_decoder.readInt();
            int hdrlen = use_decoder.readInt();
            int datalen = use_decoder.readInt();
            int maxdlen = use_decoder.readInt();
            this.magic = use_decoder.readInt();
            if (this.magic != 1938831426 && this.magic != -1862035368) {
               if (traceEnabled) {
                  ntrace.doTrace("]/read_ws_65_tfmh/45/-1");
               }

               return -1;
            } else {
               int gtype_idx = use_decoder.readInt();
               this.ltype_idx = use_decoder.readInt();
               String subtype = Utilities.xdr_decode_vector_string(use_decoder, 16, stypebuf);
               tmmsg_ws = new WsTcb();
               tmmsg_ws._tmpostrecv65(use_decoder, use_decoder.available(), -1);
               this.ws = new tcm((short)3, tmmsg_ws);
               String type = tmmsg_ws.get_type();
               if (type.equals("COMPOS")) {
                  tmmsg_compos_hdr = new ComposHdrTcb();
                  if (!tmmsg_compos_hdr._tmpostrecv65(use_decoder)) {
                     if (traceEnabled) {
                        ntrace.doTrace("]/read_ws_65_tfmh/50/-1");
                     }

                     return -1;
                  }

                  type = tmmsg_compos_hdr.getComposType();
                  subtype = tmmsg_compos_hdr.getComposSubtype();
                  tmmsg_compos_fml = new ComposFmlTcb();
                  if (tmmsg_compos_fml._tmpostrecv65(use_decoder) != 0) {
                     if (traceEnabled) {
                        ntrace.doTrace("]/read_ws_65_tfmh/100/-1");
                     }

                     return -1;
                  }

                  this.compos_hdr = new tcm((short)5, tmmsg_compos_hdr);
                  this.compos_fml = new tcm((short)6, tmmsg_compos_fml);
               }

               if (traceEnabled) {
                  ntrace.doTrace("/read_ws_65_tfmh/ available=" + use_decoder.available());
               }

               if (tmmsg_ws.get_opcode() == 8 || tmmsg_ws.get_opcode() == 6 || tmmsg_ws.get_opcode() == 11 || tmmsg_ws.get_opcode() == 13) {
                  type = new String("wsrpcrq");
                  tmmsg_ws.set_type("wsrpcrq");
               }

               tmmsg_user = new UserTcb();
               if (tmmsg_ws.get_opcode() == 6) {
                  int recv_size = decoder.available();
                  this.challenge_rp = new byte[recv_size];
                  Utilities.readByteArray(decoder, this.challenge_rp, 0, recv_size);
               } else if (tmmsg_ws.get_opcode() != 13) {
                  tmmsg_user._tmpostrecv65(use_decoder, use_decoder.available(), 0, type, subtype, tmmsg_ws.get_opcode());
               }

               this.user = new tcm((short)0, tmmsg_user);
               if (tmmsg_ws.get_opcode() == 11) {
                  ((WSRPCRQ)tmmsg_user.user_data).wsinitrp_notifyopt = 64;
                  ((WSRPCRQ)tmmsg_user.user_data).wsinitrp_mchidshift = tmmsg_ws.get_flds(2);
                  ((WSRPCRQ)tmmsg_user.user_data).wsinitrp_flags = tmmsg_ws.get_flds(3);
                  ((WSRPCRQ)tmmsg_user.user_data).wsinitrp_nettimeout = tmmsg_ws.get_flds(4);
               }

               if (traceEnabled) {
                  ntrace.doTrace("/read_ws_65_tfmh/110/0");
               }

               return 0;
            }
         }
      }
   }

   public int write_ws_65_tfmh(DataOutputStream encoder, int cmplimit, int authlev, TPINIT tpinfo) throws IOException {
      int datalen = 0;
      int hdrlen = false;
      int ws_connection_msg = false;
      int ws_encryption_message = false;
      TypedBuffer our_user_data = null;
      WsTcb tmmsg_ws = null;
      TranTcb tmmsg_tran = null;
      UnsolTcb tmmsg_unsol = null;
      ComposHdrTcb tmmsg_compos_hdr = null;
      ComposFmlTcb tmmsg_compos_fml = null;
      byte[] ws65_fillin = null;
      int send_size = false;
      String type = null;
      String subtype = null;
      int type_encoded_size = true;
      int subtype_encoded_size = true;
      int hint_index = -1;
      int new_size = false;
      int initial_size = false;
      DataOutputStream use_encoder = null;
      boolean tryCompression = false;
      int usrdatalen = false;
      byte[] usrpasswd = null;
      int gtype_idx = -1;
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/write_ws_65_tfmh/");
      }

      this.resetWrite();
      byte[] tcm_data = null;
      int tcm_size = 0;
      byte[] cdata = null;
      int offset = 0;
      boolean use_compressed = false;
      int calculated_size = 32;
      calculated_size += 496;
      int type_encoded_size;
      int subtype_encoded_size;
      if (this.user != null) {
         our_user_data = ((UserTcb)this.user.body).user_data;
         type = our_user_data.getType();
         if (type.equals("wsrpcrq")) {
            type = new String("rpcrqst");
         }

         type_encoded_size = Utilities.xdr_length_string(type);
         subtype = our_user_data.getSubtype();
         subtype_encoded_size = Utilities.xdr_length_string(subtype);
         hint_index = our_user_data.getHintIndex();
      }

      int cltid = ((WsTcb)this.ws.body).get_CLTID();
      int opcode = ((WsTcb)this.ws.body).get_opcode();
      if (opcode == 12) {
         type = new String("rpcrqst");
         type_encoded_size = Utilities.xdr_length_string(type);
      }

      this.mhdr.mprotocol = 46;
      switch (opcode) {
         case 4:
         case 5:
         case 7:
         case 9:
         case 10:
         case 12:
         case 14:
         case 15:
         case 16:
         case 17:
         case 18:
         case 19:
         case 20:
         case 21:
         case 22:
         case 23:
         case 24:
         case 25:
         default:
            break;
         case 6:
            datalen = 160;
            ws_connection_msg = true;
            ws_encryption_message = true;
            break;
         case 8:
            datalen = 604;
            ws_connection_msg = true;
            break;
         case 11:
            datalen = 524;
            if (tpinfo != null && !tpinfo.no_usrpasswd) {
               if (tpinfo.use_string_usrpasswd) {
                  datalen += tpinfo.usrpasswd.length() - 4;
               } else {
                  datalen += tpinfo.data.length - 4;
               }
            }

            if (authlev != 0) {
               ws_encryption_message = true;
               if (datalen % 8 != 0) {
                  datalen += datalen % 8;
               }
            }

            ws_connection_msg = true;
            break;
         case 13:
            datalen = 1544;
            ws_connection_msg = true;
      }

      calculated_size += datalen;
      if (!ws_connection_msg) {
         this.resetWrite();
         if (this.user != null) {
            int initial_size = this.myEncoder.size();

            try {
               our_user_data._tmpresend(this.myEncoder);
            } catch (TPException var48) {
               if (traceEnabled) {
                  ntrace.doTrace("]/write_ws_65_tfmh/70/" + var48);
               }

               return -1;
            } catch (IOException var49) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/write_ws_65_tfmh/80/" + var49);
               }

               throw var49;
            }

            int new_size = this.myEncoder.size();
            datalen += new_size - initial_size;
            tcm_size = datalen;
            calculated_size += datalen;
         }

         if (this.tran != null) {
            tmmsg_tran = (TranTcb)this.tran.body;
            type = new String("TRAN");
            subtype = null;
            type_encoded_size = Utilities.xdr_length_string(type);
            subtype_encoded_size = Utilities.xdr_length_string(subtype);
            if (traceEnabled) {
               ntrace.doTrace("/write_tfmh/wrote tran tcm/" + calculated_size);
            }
         }

         if (this.unsol != null) {
            tmmsg_unsol = (UnsolTcb)this.unsol.body;
            type = new String("TRAN");
            subtype = null;
            type_encoded_size = Utilities.xdr_length_string(type);
            subtype_encoded_size = Utilities.xdr_length_string(subtype);
            if (traceEnabled) {
               ntrace.doTrace("/write_tfmh/wrote unsol tcm/" + calculated_size);
            }
         }

         if (this.compos_hdr != null && this.compos_fml != null) {
            boolean compos = true;
            tmmsg_compos_hdr = (ComposHdrTcb)this.compos_hdr.body;
            tmmsg_compos_fml = (ComposFmlTcb)this.compos_fml.body;
            tmmsg_compos_hdr.setTypeAndSubtype(type, subtype, hint_index);
            type = new String("COMPOS");
            subtype = null;
            type_encoded_size = Utilities.xdr_length_string(type);
            subtype_encoded_size = Utilities.xdr_length_string(subtype);
            calculated_size += tmmsg_compos_hdr.getR65size();
            calculated_size += tmmsg_compos_fml.getR65size();
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("/write_ws_65_tfmh/total calculated_size=" + calculated_size);
      }

      if (this.ltype_idx < 16) {
         gtype_idx = this.ltype_idx;
      }

      byte[] tcm_data = this.myWriteStream.getByteArrayReference();
      if (traceEnabled) {
         new TDumpByte("buffer", tcm_data);
      }

      if (calculated_size >= cmplimit) {
         if (traceEnabled) {
            ntrace.doTrace("/write_ws_65_tfmh/Using compressed buffer/");
         }

         ByteArrayOutputStream compress_bstream = new ByteArrayOutputStream(calculated_size + 100);
         DataOutputStream compress_encoder = new DataOutputStream(compress_bstream);
         if (tcm_data != null) {
            compress_encoder.write(tcm_data, 0, tcm_size);
         }

         byte[] tcdata = compress_bstream.toByteArray();
         int tcdata_size = compress_encoder.size();
         cdata = new byte[Utilities.roundup4(tcdata_size)];
         Deflater def = new Deflater();
         def.setInput(tcdata);

         while(offset < tcdata_size && !def.needsInput()) {
            offset += def.deflate(cdata, offset, cdata.length - offset);
         }

         def.finish();

         while(offset < tcdata_size && !def.finished()) {
            offset += def.deflate(cdata, offset, cdata.length - offset);
         }

         if (offset < tcdata_size) {
            use_compressed = true;
         }

         if (traceEnabled) {
            ntrace.doTrace("/write_ws_65_tfmh/compressed size=" + offset);
         }
      }

      metahdr var10000 = this.mhdr;
      var10000.flags &= 1;
      if (!use_compressed) {
         if (traceEnabled) {
            ntrace.doTrace("/write_ws_65_tfmh/Not compressed/calculated_size=" + calculated_size + "/");
         }

         this.mhdr.size = calculated_size;
         encoder.writeInt(this.mhdr.mtype);
         encoder.writeInt(46);
         encoder.writeInt(this.mhdr.qaddr);
         encoder.writeInt(this.mhdr.mid);
         encoder.writeInt(this.mhdr.size);
         encoder.writeInt(this.mhdr.flags);
         encoder.writeInt(this.mhdr.uncmprs_sz);
         encoder.writeInt(0);
         encoder.writeInt(1);
         encoder.writeInt(calculated_size - datalen);
         encoder.writeInt(datalen);
         encoder.writeInt(calculated_size);
         encoder.writeInt(this.magic);
         encoder.writeInt(gtype_idx);
         encoder.writeInt(this.ltype_idx);
         Utilities.xdr_encode_vector_string(encoder, subtype, 16);

         try {
            ((WsTcb)this.ws.body)._tmpresend65(encoder);
         } catch (TPException var50) {
            if (traceEnabled) {
               ntrace.doTrace("]/write_ws_65_tfmh70/" + var50);
            }

            return -1;
         } catch (IOException var51) {
            if (traceEnabled) {
               ntrace.doTrace("*]/write_ws_65_tfmh/80/" + var51);
            }

            throw var51;
         }

         Utilities.xdr_encode_vector_string(encoder, type, 8);
         if (traceEnabled) {
            ntrace.doTrace("/write_ws_65_tfmh/magic=" + this.magic + "/ltype_idx=" + this.ltype_idx + "/tmfmh_flag=" + this.tmfmh_flag);
         }

         switch (opcode) {
            case 6:
               Utilities.xdr_encode_vector_string(encoder, tpinfo.usrname, 32);
               this.write_challenge_val(encoder, tpinfo.passwd);
               break;
            case 11:
               ByteArrayOutputStream init_bstream = new ByteArrayOutputStream(datalen + 100);
               DataOutputStream init_encoder = new DataOutputStream(init_bstream);
               if (tpinfo != null) {
                  Utilities.xdr_encode_vector_string(init_encoder, tpinfo.usrname, 32);
                  Utilities.xdr_encode_vector_string(init_encoder, tpinfo.cltname, 32);
                  Utilities.xdr_encode_vector_string(init_encoder, tpinfo.passwd, 32);
                  Utilities.xdr_encode_vector_string(init_encoder, tpinfo.grpname, 32);
                  init_encoder.writeInt(tpinfo.flags);
                  if (tpinfo != null && !tpinfo.no_usrpasswd) {
                     if (tpinfo.use_string_usrpasswd) {
                        init_encoder.writeInt(tpinfo.usrpasswd.length());
                        Utilities.xdr_encode_string(init_encoder, tpinfo.usrpasswd);
                     } else {
                        init_encoder.writeInt(tpinfo.data.length);
                        init_encoder.write(tpinfo.data, 0, tpinfo.data.length);
                     }
                  } else {
                     init_encoder.writeInt(0);
                     init_encoder.writeInt(0);
                  }
               }

               byte[] tcdata = init_bstream.toByteArray();
               int tcdata_size = init_encoder.size();
               if (authlev != 0) {
                  byte[] rndup_tcdata = new byte[tcdata_size];
                  if (tcdata_size % 8 != 0) {
                     rndup_tcdata = new byte[tcdata_size + tcdata_size % 8];
                  }

                  System.arraycopy(tcdata, 0, rndup_tcdata, 0, tcdata_size);
                  byte[] cipher_text = new byte[rndup_tcdata.length];
                  TPCrypt cipher = new TPCrypt();
                  if (cipher.setKey(this.ws65_session_key) != 0 && traceEnabled) {
                     ntrace.doTrace("Invalid key");
                  }

                  if (traceEnabled) {
                     ntrace.doTrace("use session_key  to create secret key");
                  }

                  if (cipher.crypt(rndup_tcdata, cipher_text, rndup_tcdata.length, 1) != 0 && traceEnabled) {
                     ntrace.doTrace("crypt failed");
                  }

                  encoder.write(cipher_text, 0, cipher_text.length);
               } else {
                  encoder.write(tcdata, 0, tcdata_size);
               }
               break;
            case 13:
               encoder.writeInt(this.tstamp);
               encoder.writeInt(0);
               int send_buf_size = this.ws65_ticket.length + (Utilities.roundup4(this.ws65_ticket.length) - this.ws65_ticket.length);
               byte[] send_buf = new byte[send_buf_size];
               Utilities.baWriteXdrBOpaque(send_buf, 0, this.ws65_ticket, 0, this.ws65_ticket.length);
               encoder.write(send_buf, 0, send_buf_size);
         }

         if (tcm_data != null) {
            encoder.write(tcm_data, 0, tcm_size);
         }

         if (type.equals("COMPOS")) {
            if (tmmsg_compos_hdr != null) {
               tmmsg_compos_hdr._tmpresend65(encoder);
            }

            if (tmmsg_compos_fml != null) {
               tmmsg_compos_fml._tmpresend65(encoder);
            }
         }
      } else {
         this.mhdr.uncmprs_sz = calculated_size;
         this.mhdr.size = 32 + offset;
         var10000 = this.mhdr;
         var10000.flags |= 256;
         if (traceEnabled) {
            ntrace.doTrace("/write_ws_65_tfmh/using compressed buffer/calculated_size=" + calculated_size + "/size=" + this.mhdr.size);
         }

         this.mhdr.write_metahdr(encoder);
         encoder.writeInt(0);
         encoder.writeInt(1);
         encoder.writeInt(calculated_size - datalen);
         encoder.writeInt(datalen);
         encoder.writeInt(calculated_size);
         encoder.writeInt(this.magic);
         encoder.writeInt(gtype_idx);
         encoder.writeInt(this.ltype_idx);
         Utilities.xdr_encode_vector_string(encoder, subtype, 16);

         try {
            ((WsTcb)this.ws.body)._tmpresend65(encoder);
         } catch (TPException var52) {
            if (traceEnabled) {
               ntrace.doTrace("]/write_ws_65_tfmh70/" + var52);
            }

            return -1;
         } catch (IOException var53) {
            if (traceEnabled) {
               ntrace.doTrace("*]/write_ws_65_tfmh/80/" + var53);
            }

            throw var53;
         }

         if (cdata != null) {
            encoder.write(cdata, 0, offset);
         }

         if (tmmsg_compos_hdr != null) {
            tmmsg_compos_hdr._tmpresend65(encoder);
         }

         if (tmmsg_compos_fml != null) {
            tmmsg_compos_fml._tmpresend65(encoder);
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("]/write_ws_65_tfmh/100/0/");
      }

      return 0;
   }

   private int write_challenge_val(DataOutputStream encoder, String password) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/write_challenge_val/");
      }

      try {
         byte[] snd_challenge = new byte[8];
         byte[] send_buf = null;
         this.ws65_challenge = new byte[8];
         SecureRandom r = new SecureRandom();

         for(int i = 0; i < 8; ++i) {
            int ri = r.nextInt(256);
            snd_challenge[i] = (byte)ri;
            this.ws65_challenge[i] = (byte)((ri << 1) % 256);
         }

         byte[] net_challenge = Utilities.encodeByteVector(snd_challenge);
         byte[] cipher_text = new byte[net_challenge.length];
         byte[] key = new byte[8];
         TPCrypt cipher = new TPCrypt();
         cipher.pwToKey(password, key);
         if (traceEnabled) {
            ntrace.doTrace("use password " + password + " to create secret key");
         }

         cipher.crypt(net_challenge, cipher_text, net_challenge.length, 1);
         int send_buf_size = cipher_text.length + (Utilities.roundup4(cipher_text.length) - cipher_text.length);
         byte[] send_buf = new byte[send_buf_size];
         Utilities.baWriteXdrBOpaque(send_buf, 0, cipher_text, 0, cipher_text.length);
         encoder.write(send_buf, 0, send_buf_size);
         if (traceEnabled) {
            ntrace.doTrace("]/write_challenge_val/20/return ");
         }

         return 0;
      } catch (Exception var14) {
         if (traceEnabled) {
            ntrace.doTrace("]/write_challenge_val/20/return -1");
         }

         return -1;
      }
   }

   public int read_challenge(String passwd) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/read_challenge/");
      }

      try {
         this.challenge = new byte[8];
         byte[] cipher_text = new byte[40];
         byte[] clear_text = new byte[40];
         int offset = 0;
         this.ws65_ticket = new byte[1536];
         this.ws65_session_key = new byte[8];
         offset += Utilities.baReadXdrBOpaque(this.ws65_ticket, 0, this.challenge_rp, offset, 1536);
         int var10000 = offset + Utilities.baReadXdrBOpaque(cipher_text, 0, this.challenge_rp, offset, cipher_text.length);
         byte[] key = new byte[8];
         TPCrypt cipher = new TPCrypt();
         cipher.pwToKey(passwd, key);
         cipher.crypt(cipher_text, clear_text, cipher_text.length, 0);
         byte[] net_challenge = new byte[32];
         System.arraycopy(clear_text, 0, net_challenge, 0, net_challenge.length);
         System.arraycopy(clear_text, net_challenge.length, this.ws65_session_key, 0, this.ws65_session_key.length);
         this.challenge = Utilities.decodeByteVector(8, net_challenge);
         if (traceEnabled) {
            ntrace.doTrace("]/read_challenge/20/return ");
         }

         return 0;
      } catch (Exception var9) {
         if (traceEnabled) {
            ntrace.doTrace("]/read_challenge/30/return -1");
         }

         return -1;
      }
   }

   public void dumpUData(boolean b) {
      this.dumpUData = b;
   }

   public int hashCode() {
      if (this.ws == null) {
         return 0;
      } else {
         return this.ws.body == null ? 0 : this.ws.body.hashCode();
      }
   }

   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else {
         tfmh tmmsg = (tfmh)obj;
         if (tmmsg.ws == null) {
            return this.ws == null;
         } else if (this.ws == null) {
            return false;
         } else {
            WsTcb this_ws = (WsTcb)this.ws.body;
            WsTcb obj_ws = (WsTcb)tmmsg.ws.body;
            return this_ws.equals(obj_ws);
         }
      }
   }
}
