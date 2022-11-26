package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.Utilities;
import com.bea.core.jatmi.common.ntrace;
import java.security.SecureRandom;
import java.util.Arrays;
import weblogic.wtc.WTCLogger;

public final class atntdom65 implements atn {
   public atncredtdom mycred;
   protected String d65_rdom;
   protected String d65_ldom;
   protected String d65_sending_dom;
   protected String d65_rpasswd;
   protected String d65_lpasswd;
   protected String d65_app_passwd;
   protected byte[] d65_challenge;
   protected byte[] d65_session_key;
   protected byte[] d65_ticket;
   protected int d65_sec_type;
   private String d65_sec_rtn;
   private int d65_odata_sz;
   private static final byte[] bPassed = new byte[]{80, 65, 83, 83, 69, 68, 0};

   public atntdom65() {
   }

   public atntdom65(String desired_name) {
      if (desired_name != null) {
         this.d65_ldom = new String(desired_name);
      }

      this.mycred = new atncredtdom(desired_name, desired_name, desired_name.getBytes());
   }

   public atncred gssAcquireCred(String desired_name) {
      return this.mycred;
   }

   public atncred gssAcquireCred(String desired_name, String identity_proof) throws EngineSecError {
      if (!desired_name.equals(this.mycred.getDesiredName())) {
         throw new EngineSecError(-3003, "desired name <" + desired_name + "> does not match cred (" + this.mycred.getDesiredName() + ">");
      } else {
         return this.mycred;
      }
   }

   public atncred gssAcquireCred(String desired_name, byte[] identity_proof) throws EngineSecError {
      if (!desired_name.equals(this.mycred.getDesiredName())) {
         throw new EngineSecError(-3003, "desired name <" + desired_name + "> does not match cred (" + this.mycred.getDesiredName() + ">");
      } else {
         return this.mycred;
      }
   }

   public atncontext gssGetContext(atncred claimant_cred_handle, String target_name) {
      return new atnctxtdom((atncredtdom)claimant_cred_handle);
   }

   public int gssInitSecContext(atncontext context, byte[] input_token, int input_token_size, byte[] output_token) throws EngineSecError {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/atntdom65/gssInitSecContext/input_token_size " + input_token_size);
      }

      atnctxtdom ctx = (atnctxtdom)context;
      int need_space;
      switch (ctx.context_state) {
         case -1:
            ctx.context_state = 0;
         case 0:
            need_space = this.calDom65Acall2();
            if (output_token != null && output_token.length >= need_space) {
               if ((this.d65_odata_sz = this.sendDom65Acall2(output_token, 0)) == -1) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/atntdom65/gssInitSecContext/20/-1");
                  }

                  return -1;
               }

               ctx.context_state = 2;
               if (traceEnabled) {
                  ntrace.doTrace("]/atntdom65/gssInitSecContext/30/" + need_space);
               }

               return need_space;
            }

            if (traceEnabled) {
               ntrace.doTrace("*]/atntdom65/gssInitSecContext/10/need_space(" + need_space + ") actual space(" + output_token.length + ")");
            }

            throw new EngineSecError(-3005, need_space);
         case 1:
         case 3:
         default:
            if (traceEnabled) {
               ntrace.doTrace("]/atntdom65/gssInitSecContext/120/-1");
            }

            return -1;
         case 2:
            if (input_token != null && input_token_size >= 0) {
               if (this.recvDom65Acall2Rply(input_token, 0) < 0) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/atntdom65/gssInitSecContext/50/-1");
                  }

                  return -1;
               }

               need_space = this.calDom65Acall3();
               if (output_token != null && output_token.length >= need_space) {
                  if ((this.d65_odata_sz = this.sendDom65Acall3(output_token, 0)) == -1) {
                     if (traceEnabled) {
                        ntrace.doTrace("]/atntdom65/gssInitSecContext/70/-1");
                     }

                     return -1;
                  }

                  ctx.context_state = 4;
                  if (traceEnabled) {
                     ntrace.doTrace("]/atntdom65/gssInitSecContext/80/" + need_space);
                  }

                  return need_space;
               }

               if (traceEnabled) {
                  ntrace.doTrace("*]/atntdom65/gssInitSecContext/60/need_space(" + need_space + ") actual space(" + output_token.length + ")");
               }

               throw new EngineSecError(-3005, need_space);
            }

            if (traceEnabled) {
               ntrace.doTrace("*]/atntdom65/gssInitSecContext/40/input_token null");
            }

            throw new EngineSecError(-3002, "Bad input token for init_sec_context");
         case 4:
            if (input_token != null && input_token_size >= 0) {
               if (this.recvDom65Acall3Rply(input_token, 0) < 0) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/atntdom65/gssInitSecContext/100/-1");
                  }

                  return -1;
               } else {
                  ctx.context_state = 5;
                  if (traceEnabled) {
                     ntrace.doTrace("]/atntdom65/gssInitSecContext/110/0");
                  }

                  return 0;
               }
            } else {
               if (traceEnabled) {
                  ntrace.doTrace("*]/atntdom65/gssInitSecContext/90/bad input_token");
               }

               throw new EngineSecError(-3002, "Bad input token for init_sec_context");
            }
      }
   }

   public int gssAcceptSecContext(atncontext context, byte[] input_token, int input_token_size, byte[] output_token) throws EngineSecError {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/atntdom65/gssAcceptSecContext/input_token_size " + input_token_size);
      }

      atnctxtdom ctx = (atnctxtdom)context;
      if (input_token != null && input_token_size >= 0) {
         int need_space;
         switch (ctx.context_state) {
            case -1:
               ctx.context_state = 1;
            case 1:
               if (this.recvDom65Acall2(input_token, 0) < 0) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/atntdom65/gssAcceptSecContext/20/-1");
                  }

                  return -1;
               } else {
                  need_space = this.calDom65Acall2Rply();
                  if (output_token != null && output_token.length >= need_space) {
                     if ((this.d65_odata_sz = this.sendDom65Acall2Rply(output_token, 0)) == -1) {
                        if (traceEnabled) {
                           ntrace.doTrace("]/atntdom65/gssAcceptSecContext/40/-1");
                        }

                        return -1;
                     }

                     ctx.context_state = 3;
                     if (traceEnabled) {
                        ntrace.doTrace("]/atntdom65/gssAcceptSecContext/50/" + need_space);
                     }

                     return need_space;
                  }

                  if (traceEnabled) {
                     ntrace.doTrace("*]/atntdom65/gssAcceptSecContext/30/bad output_token");
                  }

                  throw new EngineSecError(-3005, need_space);
               }
            case 0:
            case 2:
            default:
               if (traceEnabled) {
                  ntrace.doTrace("]/atntdom65/gssAcceptSecContext/100/-1");
               }

               return -1;
            case 3:
               if (this.recvDom65Acall3(input_token, 0) < 0) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/atntdom65/gssAcceptSecContext/60/-1");
                  }

                  return -1;
               } else {
                  need_space = this.calDom65Acall3();
                  if (output_token != null && output_token.length >= need_space) {
                     if ((this.d65_odata_sz = this.sendDom65Acall3Rply(output_token, 0)) == -1) {
                        if (traceEnabled) {
                           ntrace.doTrace("]/atntdom65/gssAcceptSecContext/80/-1");
                        }

                        return -1;
                     } else {
                        ctx.context_state = 5;
                        if (traceEnabled) {
                           ntrace.doTrace("]/atntdom65/gssAcceptSecContext/90/0");
                        }

                        return 0;
                     }
                  } else {
                     if (traceEnabled) {
                        ntrace.doTrace("*]/atntdom65/gssAcceptSecContext/70/bad output_token");
                     }

                     throw new EngineSecError(-3005, need_space);
                  }
               }
         }
      } else {
         if (traceEnabled) {
            ntrace.doTrace("*]/atntdom65/gssAcceptSecContext/10/null input_token");
         }

         throw new EngineSecError(-3002, "Bad input token for init_sec_context");
      }
   }

   public int getActualPDUSendSize() {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/atntdom65/getActualPDUSendSize");
         ntrace.doTrace("]/atntdom65/getActualPDUSendSize/10/" + this.d65_odata_sz);
      }

      return this.d65_odata_sz;
   }

   public int getEstimatedPDUSendSize(atncontext context) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/atntdom65/getEstimatedPDUSendSize");
      }

      atnctxtdom ctx = (atnctxtdom)context;
      int need_space;
      switch (ctx.context_state) {
         case -1:
            ctx.context_state = 0;
         case 0:
            need_space = this.calDom65Acall2();
            break;
         case 1:
            need_space = this.calDom65Acall2Rply();
            break;
         case 2:
            need_space = this.calDom65Acall3();
            break;
         case 3:
            need_space = this.calDom65Acall3Rply();
            break;
         case 4:
         case 5:
         default:
            need_space = 0;
      }

      if (traceEnabled) {
         ntrace.doTrace("]/atntdom65/getEstimatedPDUSendSize/10/" + need_space);
      }

      return need_space;
   }

   public int getEstimatedPDURecvSize(atncontext context) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/atntdom65/getEstimatedPDURecvSize");
      }

      atnctxtdom ctx = (atnctxtdom)context;
      int need_space;
      switch (ctx.context_state) {
         case -1:
            ctx.context_state = 1;
         case 1:
            need_space = this.calDom65Acall2();
            break;
         case 0:
         case 5:
         default:
            need_space = 0;
            break;
         case 2:
            need_space = this.calDom65Acall2Rply();
            break;
         case 3:
            need_space = this.calDom65Acall3();
            break;
         case 4:
            need_space = this.calDom65Acall3Rply();
      }

      if (traceEnabled) {
         ntrace.doTrace("]/atntdom65/getEstimatedPDURecvSize/10/" + need_space);
      }

      return need_space;
   }

   public int setSecurityType(int sec_type) {
      if (sec_type != 2 && sec_type == 1) {
         return -1;
      } else {
         this.d65_sec_type = sec_type;
         return 0;
      }
   }

   public void setSrcName(String src_name) {
      if (src_name == null) {
         this.d65_sending_dom = null;
      } else {
         this.d65_sending_dom = new String(src_name);
      }

   }

   public void setDesiredName(String desired_name) {
      if (desired_name == null) {
         this.d65_ldom = null;
      } else {
         this.d65_ldom = new String(desired_name);
      }

   }

   public void setTargetName(String target_name) {
      if (target_name == null) {
         this.d65_rdom = null;
      } else {
         this.d65_rdom = new String(target_name);
      }

   }

   public void setApplicationPasswd(String app_pw) {
      this.d65_app_passwd = new String(app_pw);
   }

   public void setLocalPasswd(String passwd) {
      this.d65_lpasswd = new String(passwd);
   }

   public void setRemotePasswd(String passwd) {
      this.d65_rpasswd = new String(passwd);
   }

   public int setInitiatorAddr(byte[] initiator) {
      return 0;
   }

   public int setAcceptorAddr(byte[] acceptor) {
      return 0;
   }

   public int setApplicationData(byte[] application) {
      return 0;
   }

   public void setMachineType(String mtype) {
   }

   protected String getLocalPasswd() {
      return this.d65_sec_type == 2 ? this.d65_lpasswd : this.d65_app_passwd;
   }

   protected String getRemotePasswd() {
      return this.d65_sec_type == 2 ? this.d65_rpasswd : this.d65_app_passwd;
   }

   protected String getSendingDom() {
      return this.d65_sending_dom != null ? this.d65_sending_dom : this.d65_ldom;
   }

   private int calDom65Acall2() {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/calDom65Acall2/");
      }

      int size = 160;
      if (traceEnabled) {
         ntrace.doTrace("]/calDom65Acall2/10/" + size);
      }

      return size;
   }

   private int calDom65Acall2Rply() {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/calDom65Acall2Rply/");
      }

      int size = 1576;
      if (traceEnabled) {
         ntrace.doTrace("]/calDom65Acall2Rply/10/" + size);
      }

      return size;
   }

   private int calDom65Acall3() {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/calDom65Acall3/");
      }

      int size = 1568;
      if (traceEnabled) {
         ntrace.doTrace("]/calDom65Acall3/10/" + size);
      }

      return size;
   }

   private int calDom65Acall3Rply() {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/calDom65Acall3Rply/");
      }

      int size = 28;
      if (traceEnabled) {
         ntrace.doTrace("[/calDom65Acall3Rply/10/" + size);
      }

      return size;
   }

   private int sendDom65Acall2(byte[] out, int offset) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/sendDom65Acall2/" + this.d65_ldom);
      }

      try {
         int o = offset;
         byte[] domid = this.d65_ldom.getBytes();
         TDumpByte td = new TDumpByte();
         String password = this.getLocalPasswd();

         int i;
         for(i = 0; i < domid.length; ++i) {
            offset += Utilities.baWriteInt(domid[i], out, offset);
         }

         while(i < 32) {
            offset += Utilities.baWriteInt(0, out, offset);
            ++i;
         }

         byte[] snd_challenge = new byte[8];
         this.d65_challenge = new byte[8];
         SecureRandom r = new SecureRandom();

         for(i = 0; i < 8; ++i) {
            int ri = r.nextInt(256);
            snd_challenge[i] = (byte)ri;
            this.d65_challenge[i] = (byte)((ri << 1) % 256);
         }

         td.printDump("snd challenge", snd_challenge);
         td.printDump("reply challenge", this.d65_challenge);
         byte[] net_challenge = Utilities.encodeByteVector(snd_challenge);
         byte[] cipher_text = new byte[net_challenge.length];
         byte[] key = new byte[8];
         TPCrypt cipher = new TPCrypt();
         cipher.pwToKey(password, key);
         if (traceEnabled) {
            ntrace.doTrace("use password " + password + " to create secret key");
         }

         td.printDump("secret key", key);
         cipher.crypt(net_challenge, cipher_text, net_challenge.length, 1);
         td.printDump("Encrypted challenge", cipher_text);
         offset += Utilities.baWriteXdrBOpaque(out, offset, cipher_text, 0, cipher_text.length);
         if (traceEnabled) {
            ntrace.doTrace("]/sendDom65Acall2/20/return " + (offset - o));
         }

         return offset - o;
      } catch (Exception var16) {
         WTCLogger.logErrorDom65SendPreAcall("ACALL2");
         if (traceEnabled) {
            ntrace.doTrace("]/sendDom65Acall2/20/return -1");
         }

         return -1;
      }
   }

   private int sendDom65Acall2Rply(byte[] out, int offset) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/sendDom65Acall2Rply/" + this.d65_ldom);
      }

      try {
         int o = offset;
         byte[] tmp_challenge = new byte[8];
         TDumpByte td = new TDumpByte();

         for(int i = 0; i < tmp_challenge.length; ++i) {
            tmp_challenge[i] = (byte)((this.d65_challenge[i] << 1) % 256);
         }

         byte[] net_challenge = Utilities.encodeByteVector(tmp_challenge);
         byte[] cipher_ticket = new byte[1536];
         byte[] cipher_key = new byte[40];
         byte[] sending_dom = this.d65_sending_dom.getBytes();
         byte[] ticket = new byte[1536];
         TPCrypt cipher = new TPCrypt();
         byte[] tmpkey = cipher.randKey();
         this.d65_session_key = new byte[tmpkey.length];
         System.arraycopy(tmpkey, 0, this.d65_session_key, 0, tmpkey.length);
         td.printDump("sending dom", sending_dom);
         Arrays.fill(ticket, (byte)0);
         System.arraycopy(sending_dom, 0, ticket, 0, sending_dom.length);
         System.arraycopy(this.d65_session_key, 0, ticket, 32, this.d65_session_key.length);
         td.printDump("ticket", ticket);
         byte[] key = new byte[8];
         cipher.pwToKey(this.getLocalPasswd(), key);
         if (traceEnabled) {
            ntrace.doTrace("use password " + this.getLocalPasswd() + " to create secret key");
         }

         td.printDump("secret key", key);
         cipher.crypt(ticket, cipher_ticket, ticket.length, 1);
         byte[] clr_key = new byte[this.d65_session_key.length + net_challenge.length];
         System.arraycopy(net_challenge, 0, clr_key, 0, net_challenge.length);
         System.arraycopy(this.d65_session_key, 0, clr_key, net_challenge.length, this.d65_session_key.length);
         cipher.crypt(clr_key, cipher_key, clr_key.length, 1);
         offset += Utilities.baWriteXdrBOpaque(out, offset, cipher_ticket, 0, cipher_ticket.length);
         offset += Utilities.baWriteXdrBOpaque(out, offset, cipher_key, 0, cipher_key.length);
         if (traceEnabled) {
            ntrace.doTrace("]/sendDom65Acall2Rply/10/return " + (offset - o));
         }

         return offset - o;
      } catch (Exception var17) {
         WTCLogger.logErrorDom65SendPreAcall("ACALL2_RPLY");
         if (traceEnabled) {
            ntrace.doTrace("]/sendDom65Acall2Rply/20/return -1");
         }

         return -1;
      }
   }

   private int sendDom65Acall3(byte[] out, int offset) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/sendDom65Acall3/" + this.d65_ldom);
      }

      try {
         int o = offset;
         byte[] orig = this.d65_ldom.getBytes();
         byte[] sending_dom = new byte[32];
         byte[] cipher_text = new byte[sending_dom.length];
         TDumpByte td = new TDumpByte();
         Arrays.fill(sending_dom, (byte)0);
         System.arraycopy(orig, 0, sending_dom, 0, orig.length);
         td.printDump("sending dom", sending_dom);
         TPCrypt cipher = new TPCrypt();
         cipher.setKey(this.d65_session_key);
         cipher.crypt(sending_dom, cipher_text, sending_dom.length, 1);
         td.printDump("encrypted sending_dom", cipher_text);
         offset += Utilities.baWriteXdrBOpaque(out, offset, this.d65_ticket, 0, this.d65_ticket.length);
         offset += Utilities.baWriteXdrBOpaque(out, offset, cipher_text, 0, cipher_text.length);
         if (traceEnabled) {
            ntrace.doTrace("]/sendDom65Acall3/20/return " + (offset - o));
         }

         return offset - o;
      } catch (Exception var10) {
         WTCLogger.logErrorDom65SendPreAcall("ACALL3");
         if (traceEnabled) {
            ntrace.doTrace("]/sendDom65Acall3/20/return -1");
         }

         return -1;
      }
   }

   private int sendDom65Acall3Rply(byte[] out, int offset) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/sendDom65Acall3Rply/" + this.d65_ldom);
      }

      try {
         byte[] rtn = this.d65_sec_rtn.getBytes();
         int o = offset;
         if (traceEnabled) {
            ntrace.doTrace("security_return " + this.d65_sec_rtn);
         }

         int i;
         for(i = 0; i < rtn.length; ++i) {
            offset += Utilities.baWriteInt(rtn[i], out, offset);
         }

         while(i < 7) {
            offset += Utilities.baWriteInt(0, out, offset);
            ++i;
         }

         if (traceEnabled) {
            ntrace.doTrace("]/sendDom65Acall3Rply/10/return " + (offset - o));
         }

         return offset - o;
      } catch (Exception var7) {
         WTCLogger.logErrorDom65SendPreAcall("ACALL3_RPLY");
         if (traceEnabled) {
            ntrace.doTrace("]/sendDom65Acall3Rply/20/return -1");
         }

         return -1;
      }
   }

   private int recvDom65Acall2(byte[] in, int offset) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/recvDom65Acall2/" + this.d65_ldom);
      }

      try {
         TDumpByte td = new TDumpByte();
         byte[] clear_text = new byte[32];
         byte[] cipher_text = new byte[32];
         byte[] domid = new byte[32];
         int o = offset;
         offset += Utilities.baReadXdrBVector(domid, 0, in, offset, 32);
         offset += Utilities.baReadXdrBOpaque(cipher_text, 0, in, offset, 32);
         byte[] key = new byte[8];
         String password = this.getRemotePasswd();
         TPCrypt cipher = new TPCrypt();
         cipher.pwToKey(password, key);
         td.printDump("secret key", key);
         cipher.crypt(cipher_text, clear_text, cipher_text.length, 0);
         td.printDump("decrypted challenge", clear_text);
         this.d65_challenge = Utilities.decodeByteVector(8, clear_text);
         this.d65_sending_dom = new String(domid);
         td.printDump("domid", domid);
         if (traceEnabled) {
            ntrace.doTrace("]/recvDom65Acall2/10/return " + (offset - o));
         }

         return offset - o;
      } catch (Exception var12) {
         WTCLogger.logErrorDom65RecvPreAcall("ACALL2");
         if (traceEnabled) {
            ntrace.doTrace("]/recvDom65Acall2/20/return -1");
         }

         return -1;
      }
   }

   private int recvDom65Acall2Rply(byte[] in, int offset) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/recvDom65Acall2Rply/" + this.d65_ldom);
      }

      try {
         byte[] challenge = new byte[8];
         byte[] cipher_text = new byte[40];
         byte[] clear_text = new byte[40];
         TDumpByte td = new TDumpByte();
         int o = offset;
         this.d65_ticket = new byte[1536];
         this.d65_session_key = new byte[8];
         offset += Utilities.baReadXdrBOpaque(this.d65_ticket, 0, in, offset, 1536);
         offset += Utilities.baReadXdrBOpaque(cipher_text, 0, in, offset, cipher_text.length);
         td.printDump("cipher_text", cipher_text);
         String passwd = this.getRemotePasswd();
         byte[] key = new byte[8];
         TPCrypt cipher = new TPCrypt();
         cipher.pwToKey(passwd, key);
         td.printDump("key", key);
         cipher.crypt(cipher_text, clear_text, cipher_text.length, 0);
         td.printDump("clear_text", clear_text);
         byte[] net_challenge = new byte[32];
         System.arraycopy(clear_text, 0, net_challenge, 0, net_challenge.length);
         System.arraycopy(clear_text, net_challenge.length, this.d65_session_key, 0, this.d65_session_key.length);
         challenge = Utilities.decodeByteVector(8, net_challenge);
         td.printDump("net_challenge", net_challenge);
         td.printDump("challenge", challenge);
         td.printDump("session_key", this.d65_session_key);
         if (!Arrays.equals(this.d65_challenge, challenge)) {
            WTCLogger.logErrorChallengeFailed(this.d65_rdom);
            if (traceEnabled) {
               ntrace.doTrace("]/recvDom65Acall2Rply/10/return -1");
            }

            return -1;
         } else {
            if (traceEnabled) {
               ntrace.doTrace("]/recvDom65Acall2Rply/20/return " + (offset - o));
            }

            return offset - o;
         }
      } catch (Exception var13) {
         WTCLogger.logErrorDom65RecvPreAcall("ACALL2_RPLY");
         if (traceEnabled) {
            ntrace.doTrace("]/recvDom65Acall2Rply/30/return -1");
         }

         return -1;
      }
   }

   private int recvDom65Acall3(byte[] in, int offset) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/recvDom65Acall3/" + this.d65_ldom);
      }

      try {
         byte[] cipher_ticket = new byte[1536];
         byte[] ticket = new byte[1536];
         byte[] cipher_domid = new byte[32];
         byte[] sending_dom = new byte[32];
         TDumpByte td = new TDumpByte();
         int o = offset;
         offset += Utilities.baReadXdrBOpaque(cipher_ticket, 0, in, offset, 1536);
         offset += Utilities.baReadXdrBOpaque(cipher_domid, 0, in, offset, 32);
         String lpwd = this.getLocalPasswd();
         TPCrypt cipher = new TPCrypt();
         byte[] sec_key = new byte[8];
         cipher.pwToKey(lpwd, sec_key);
         td.printDump("secret key", sec_key);
         cipher.crypt(cipher_ticket, ticket, ticket.length, 0);
         cipher.setKey(this.d65_session_key);
         cipher.crypt(cipher_domid, sending_dom, cipher_domid.length, 0);
         td.printDump("ticket", ticket);
         td.printDump("sending dom", sending_dom);

         for(int i = 0; i < sending_dom.length; ++i) {
            if (ticket[i] != sending_dom[i]) {
               WTCLogger.logErrorDomainSecurityFailedLocal(this.d65_ldom);
               this.d65_sec_rtn = new String("FAILED");
               if (traceEnabled) {
                  ntrace.doTrace("]/recvDom65Acall3/10/return -1");
               }

               return -1;
            }

            if (ticket[i] == 0 && sending_dom[i] == 0) {
               break;
            }
         }

         this.d65_sec_rtn = new String("PASSED");
         if (traceEnabled) {
            ntrace.doTrace("]/recvDom65Acall3/20/return " + (offset - o));
         }

         return offset - o;
      } catch (Exception var14) {
         WTCLogger.logErrorDom65RecvPreAcall("ACALL3");
         if (traceEnabled) {
            ntrace.doTrace("]/recvDom65Acall3/30/return -1");
         }

         return -1;
      }
   }

   private int recvDom65Acall3Rply(byte[] in, int offset) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/recvDom65Acall3/" + this.d65_ldom);
      }

      try {
         byte[] rtn = new byte[7];
         int o = offset;
         TDumpByte td = new TDumpByte();
         offset += Utilities.baReadXdrBVector(rtn, 0, in, offset, 7);
         rtn[6] = 0;
         td.printDump("returned byte array ", rtn);
         if (!Arrays.equals(bPassed, rtn)) {
            WTCLogger.logErrorDomainSecurityFailedRemote(this.d65_rdom);
            if (traceEnabled) {
               ntrace.doTrace("]/recvDom65Acall3Rply/10/return -1");
            }

            return -1;
         } else {
            if (traceEnabled) {
               ntrace.doTrace("]/recvDom65Acall3Rply/20/return " + (offset - o));
            }

            return offset - o;
         }
      } catch (Exception var7) {
         WTCLogger.logErrorDom65RecvPreAcall("ACALL3_RPLY");
         if (traceEnabled) {
            ntrace.doTrace("]/recvDom65Acall3Rply/20/return -1");
         }

         return -1;
      }
   }
}
