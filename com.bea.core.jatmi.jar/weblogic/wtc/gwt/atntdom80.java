package weblogic.wtc.gwt;

import com.bea.core.jatmi.common.Utilities;
import com.bea.core.jatmi.common.ntrace;
import java.security.SecureRandom;
import java.util.Arrays;
import weblogic.wtc.WTCLogger;
import weblogic.wtc.jatmi.EngineSecError;
import weblogic.wtc.jatmi.TDumpByte;
import weblogic.wtc.jatmi.TPCrypt;
import weblogic.wtc.jatmi.atn;
import weblogic.wtc.jatmi.atncontext;
import weblogic.wtc.jatmi.atncred;
import weblogic.wtc.jatmi.atncredtdom;
import weblogic.wtc.jatmi.atnctxtdom;

public final class atntdom80 implements atn {
   public atncredtdom mycred;
   protected String d80_src_name;
   protected String d80_desired_name;
   protected String d80_target_name;
   protected String d80_rpasswd;
   protected String d80_lpasswd;
   protected String d80_app_passwd;
   protected String d80_mach_type;
   protected byte[] d80_challenge;
   protected byte[] d80_session_key;
   protected byte[] d80_initiator;
   protected byte[] d80_acceptor;
   protected byte[] d80_app_data = new byte[50];
   protected int d80_sec_type;
   private String d80_sec_rtn;
   private int d80_minor_status;
   private int d80_role;
   private int d80_odata_sz;
   private static final int GSS_NAME_OBJID_SIZE = 12;
   private static final int ATNTDOM_TOKEN_TYPE_1 = 1;
   private static final int ATNTDOM_TOKEN_TYPE_2 = 2;
   private static final int ATNTDOM_TOKEN_TYPE_3 = 3;
   private static final int ATNTDOM_TOKEN_TYPE_4 = 4;
   private static final int ATNTDOM_VERSION = 1;
   private static final int MAXNAME_LEN = 512;
   private static final int MAXCHAN_LEN = 50;
   private static final int MAXDOMAIN_LEN = 30;
   private static final int GSS_C_AF_INET = 2;
   private static final int TOK1_ENC_DATA_LEN = 64;
   private static final int TOK2_ENC_DATA_LEN = 72;
   private static final int TOK3_ENC_DATA_LEN = 520;
   private static final int ERROR_TOKEN_TYPE = 1;
   private static final int ERROR_TOKEN_VERSION = 2;
   private static final int ERROR_TOKEN_CHLG = 3;
   private static final int ERROR_TOKEN_TARGET = 4;
   private static final int ERROR_TOKEN_LLEHASH = 5;
   private static final int ERROR_TOKEN_DATALEN = 6;
   private static final int ERROR_TOKEN_STRLEN = 7;
   private static final int ERROR_TOKEN_SRCNAME = 8;
   private static final int ERROR_TOKEN_RTNSTR = 9;
   private static final byte[] bPassed = new byte[]{80, 65, 83, 83, 69, 68, 0};

   public atntdom80() {
      Arrays.fill(this.d80_app_data, (byte)0);
   }

   public atntdom80(String desired_name) {
      Arrays.fill(this.d80_app_data, (byte)0);
      if (desired_name != null) {
         this.d80_desired_name = new String(desired_name);
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
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/atntdom80/gssInitSecContext/input_token_size " + input_token_size);
      }

      atnctxtdom ctx = (atnctxtdom)context;
      int need_space;
      switch (ctx.context_state) {
         case -1:
            ctx.context_state = 0;
         case 0:
            need_space = this.calDom80Token1();
            if (output_token != null && output_token.length >= need_space) {
               if ((this.d80_odata_sz = this.sendDom80Token1(output_token, 0)) == -1) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/atntdom80/gssInitSecContext/20");
                  }

                  return -1;
               }

               if (traceEnabled) {
                  ntrace.doTrace("real output size " + this.d80_odata_sz);
               }

               ctx.context_state = 2;
               if (traceEnabled) {
                  ntrace.doTrace("]/atntdom80/gssInitSecContext/30/" + need_space);
               }

               return need_space;
            }

            if (traceEnabled) {
               ntrace.doTrace("*]/atntdom80/gssInitSecContext/10/bad output_token");
            }

            throw new EngineSecError(-3005, need_space);
         case 1:
         case 3:
         default:
            if (traceEnabled) {
               ntrace.doTrace("]/atntdom80/gssInitSecContext/120/-1");
            }

            return -1;
         case 2:
            if (input_token != null && input_token_size >= 0) {
               if (this.recvDom80Token2(input_token, 0) < 0) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/atntdom80/gssInitSecContext/50");
                  }

                  return -1;
               }

               need_space = this.calDom80Token3();
               if (output_token != null && output_token.length >= need_space) {
                  if ((this.d80_odata_sz = this.sendDom80Token3(output_token, 0)) == -1) {
                     if (traceEnabled) {
                        ntrace.doTrace("]/atntdom80/gssInitSecContext/70");
                     }

                     return -1;
                  }

                  ctx.context_state = 4;
                  if (traceEnabled) {
                     ntrace.doTrace("]/atntdom80/gssInitSecContext/80/" + need_space);
                  }

                  return need_space;
               }

               if (traceEnabled) {
                  ntrace.doTrace("*]/atntdom80/gssInitSecContext/60/bad output token");
               }

               throw new EngineSecError(-3005, need_space);
            }

            if (traceEnabled) {
               ntrace.doTrace("*]/atntdom80/gssInitSecContext/40 bad input token");
            }

            throw new EngineSecError(-3002, "Bad input token for init_sec_context");
         case 4:
            if (input_token != null && input_token_size >= 0) {
               if (this.recvDom80Token4(input_token, 0) < 0) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/atntdom80/gssInitSecContext/100/");
                  }

                  return -1;
               } else {
                  ctx.context_state = 5;
                  if (traceEnabled) {
                     ntrace.doTrace("]/atntdom80/gssInitSecContext/110/0");
                  }

                  return 0;
               }
            } else {
               if (traceEnabled) {
                  ntrace.doTrace("]/atntdom80/gssInitSecContext/90 bad input token");
               }

               throw new EngineSecError(-3002, "Bad input token for init_sec_context");
            }
      }
   }

   public int gssAcceptSecContext(atncontext context, byte[] input_token, int input_token_size, byte[] output_token) throws EngineSecError {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/atntdom80/gssAcceptSecContext/input_token_size " + input_token_size);
      }

      atnctxtdom ctx = (atnctxtdom)context;
      if (input_token != null && input_token_size >= 0) {
         int need_space;
         switch (ctx.context_state) {
            case -1:
               ctx.context_state = 1;
            case 1:
               if (this.recvDom80Token1(input_token, 0) < 0) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/atntdom80/gssAcceptSecContext/20/-1");
                  }

                  return -1;
               } else {
                  need_space = this.calDom80Token2();
                  if (output_token != null && output_token.length >= need_space) {
                     if ((this.d80_odata_sz = this.sendDom80Token2(output_token, 0)) < 0) {
                        if (traceEnabled) {
                           ntrace.doTrace("]/atntdom80/gssAcceptSecContext/40/-1");
                        }

                        return -1;
                     }

                     if (traceEnabled) {
                        ntrace.doTrace("real output size " + this.d80_odata_sz);
                     }

                     ctx.context_state = 3;
                     if (traceEnabled) {
                        ntrace.doTrace("]/atntdom80/gssAcceptSecContext/50/" + need_space);
                     }

                     return need_space;
                  }

                  if (traceEnabled) {
                     ntrace.doTrace("*]/atntdom80/gssAcceptSecContext/30/bad output token");
                  }

                  throw new EngineSecError(-3005, need_space);
               }
            case 0:
            case 2:
            default:
               if (traceEnabled) {
                  ntrace.doTrace("]/atntdom80/gssAcceptSecContext/100/-1");
               }

               return -1;
            case 3:
               if (this.recvDom80Token3(input_token, 0) < 0) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/atntdom80/gssAcceptSecContext/60/-1");
                  }

                  return -1;
               } else {
                  need_space = this.calDom80Token4();
                  if (output_token != null && output_token.length >= need_space) {
                     if ((this.d80_odata_sz = this.sendDom80Token4(output_token, 0)) < 0) {
                        if (traceEnabled) {
                           ntrace.doTrace("]/atntdom80/gssAcceptSecContext/80/-1");
                        }

                        return -1;
                     } else {
                        if (traceEnabled) {
                           ntrace.doTrace("real output size " + this.d80_odata_sz);
                        }

                        ctx.context_state = 5;
                        if (traceEnabled) {
                           ntrace.doTrace("]/atntdom80/gssAcceptSecContext/90/0");
                        }

                        return 0;
                     }
                  } else {
                     if (traceEnabled) {
                        ntrace.doTrace("*]/atntdom80/gssAcceptSecContext/70/ bad output token");
                     }

                     throw new EngineSecError(-3005, need_space);
                  }
               }
         }
      } else {
         if (traceEnabled) {
            ntrace.doTrace("*]/atntdom80/gssAcceptSecContext/10 bad input token");
         }

         throw new EngineSecError(-3002, "Bad input token for init_sec_context");
      }
   }

   public int getActualPDUSendSize() {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/atntdom80/getActualPDUSendSize");
         ntrace.doTrace("]/atntdom80/getActualPDUSendSize/10/" + this.d80_odata_sz);
      }

      return this.d80_odata_sz;
   }

   public int getEstimatedPDUSendSize(atncontext context) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/atntdom80/getEstimatedPDUSendSize");
      }

      atnctxtdom ctx = (atnctxtdom)context;
      int need_space;
      switch (ctx.context_state) {
         case -1:
            ctx.context_state = 0;
         case 0:
            need_space = this.calDom80Token1();
            break;
         case 1:
            need_space = this.calDom80Token2();
            break;
         case 2:
            need_space = this.calDom80Token3();
            break;
         case 3:
            need_space = 58;
            break;
         case 4:
         case 5:
         default:
            need_space = 0;
      }

      if (traceEnabled) {
         ntrace.doTrace("]/atntdom80/getEstimatedPDUSendSize/10/" + need_space);
      }

      return need_space;
   }

   public int getEstimatedPDURecvSize(atncontext context) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/atntdom80/getEstimatedPDURecvSize");
      }

      atnctxtdom ctx = (atnctxtdom)context;
      short need_space;
      switch (ctx.context_state) {
         case -1:
            ctx.context_state = 1;
         case 1:
            need_space = 2136;
            break;
         case 0:
         case 5:
         default:
            need_space = 0;
            break;
         case 2:
            need_space = 2632;
            break;
         case 3:
            need_space = 3624;
            break;
         case 4:
            need_space = 58;
      }

      if (traceEnabled) {
         ntrace.doTrace("]/atntdom80/getEstimatedPDURecvSize/10/" + need_space);
      }

      return need_space;
   }

   public int setSecurityType(int sec_type) {
      if (sec_type != 2 && sec_type == 1) {
         return -1;
      } else {
         this.d80_sec_type = sec_type;
         return 0;
      }
   }

   public void setSrcName(String src_name) {
      if (src_name == null) {
         this.d80_src_name = null;
      } else {
         this.d80_src_name = new String(src_name);
      }

   }

   public void setDesiredName(String desired_name) {
      if (desired_name == null) {
         this.d80_desired_name = null;
      } else {
         this.d80_desired_name = new String(desired_name);
      }

   }

   public void setTargetName(String target_name) {
      if (target_name == null) {
         this.d80_target_name = null;
      } else {
         this.d80_target_name = new String(target_name);
      }

   }

   public void setApplicationPasswd(String app_pw) {
      this.d80_app_passwd = new String(app_pw);
   }

   public void setLocalPasswd(String passwd) {
      this.d80_lpasswd = new String(passwd);
   }

   public void setRemotePasswd(String passwd) {
      this.d80_rpasswd = new String(passwd);
   }

   public int setInitiatorAddr(byte[] initiator) {
      if (initiator != null && initiator.length <= 512) {
         if (ntrace.getTraceLevel() == 1000373) {
            new TDumpByte("initiator addr", initiator);
         }

         this.d80_initiator = new byte[initiator.length];
         System.arraycopy(initiator, 0, this.d80_initiator, 0, initiator.length);
         return 0;
      } else {
         return -1;
      }
   }

   public int setAcceptorAddr(byte[] acceptor) {
      if (acceptor != null && acceptor.length <= 512) {
         if (ntrace.getTraceLevel() == 1000373) {
            new TDumpByte("acceptor addr", acceptor);
         }

         this.d80_acceptor = new byte[acceptor.length];
         System.arraycopy(acceptor, 0, this.d80_acceptor, 0, acceptor.length);
         return 0;
      } else {
         return -1;
      }
   }

   public int setApplicationData(byte[] application) {
      if (application != null && application.length <= 50) {
         System.arraycopy(application, 0, this.d80_app_data, 0, application.length);
         if (ntrace.getTraceLevel() == 1000373) {
            new TDumpByte("application data", application);
         }

         return 0;
      } else {
         return -1;
      }
   }

   public void setMachineType(String mtype) {
      if (mtype != null) {
         this.d80_mach_type = new String(mtype);
      }

   }

   protected String getLocalPasswd() {
      return this.d80_sec_type == 2 ? this.d80_lpasswd : this.d80_app_passwd;
   }

   protected String getRemotePasswd() {
      return this.d80_sec_type == 2 ? this.d80_rpasswd : this.d80_app_passwd;
   }

   protected String getSrcName() {
      return this.d80_src_name != null ? this.d80_src_name : this.d80_desired_name;
   }

   protected String getTargetName() {
      return this.d80_target_name != null ? this.d80_target_name : this.d80_desired_name;
   }

   private int calDom80Token1() {
      int size = 16 + Utilities.xdr_length_string(this.getSrcName()) + 4 + 64;
      return size;
   }

   private int calDom80Token2() {
      int size = 16 + Utilities.xdr_length_string(this.d80_desired_name) + 4 + 72;
      return size;
   }

   private int calDom80Token3() {
      int size = 540;
      return size;
   }

   private int calDom80Token4() {
      int size = 16 + Utilities.xdr_length_string(this.d80_sec_rtn);
      return size;
   }

   private int sendDom80Token1(byte[] out, int offset) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/sendDom80Token1/" + this.d80_desired_name);
      }

      try {
         int o = offset;
         byte[] plain_text = new byte[64];
         SecureRandom r = new SecureRandom();
         this.d80_challenge = new byte[8];
         Arrays.fill(plain_text, (byte)0);

         for(int i = 0; i < 8; ++i) {
            int ri = r.nextInt(256);
            plain_text[i] = (byte)ri;
            this.d80_challenge[i] = (byte)((ri << 1) % 256);
         }

         if (this.d80_app_data != null) {
            System.arraycopy(this.d80_app_data, 0, plain_text, 8, this.d80_app_data.length);
         }

         TDumpByte td = new TDumpByte();
         if (ntrace.getTraceLevel() == 1000373) {
            td.printDump("plain_text", plain_text);
            td.printDump("reply challenge", this.d80_challenge);
         }

         byte[] cipher_text = new byte[64];
         byte[] key = new byte[8];
         TPCrypt cipher = new TPCrypt();
         cipher.pwToKey(this.getLocalPasswd(), key);
         if (traceEnabled) {
            ntrace.doTrace("use password " + this.getLocalPasswd() + " to create secret key");
            if (ntrace.getTraceLevel() == 1000373) {
               td.printDump("secret key", key);
            }
         }

         cipher.crypt(plain_text, cipher_text, plain_text.length, 1);
         if (ntrace.getTraceLevel() == 1000373) {
            td.printDump("Encrypted challenge", cipher_text);
         }

         offset += Utilities.baWriteInt(1, out, offset);
         offset += Utilities.baWriteInt(1, out, offset);
         offset += Utilities.baWriteInt(0, out, offset);
         offset += Utilities.baWriteInt(0, out, offset);
         String srcName = this.getSrcName();
         if (srcName != null) {
            offset += Utilities.baWriteXdrString(out, offset, srcName);
         } else {
            offset += Utilities.baWriteInt(0, out, offset);
         }

         offset += Utilities.baWriteInt(64, out, offset);
         offset += Utilities.baWriteXdrBOpaque(out, offset, cipher_text, 0, cipher_text.length);
         if (traceEnabled) {
            ntrace.doTrace("]/sendDom80Token1/10/return " + (offset - o));
         }

         return offset - o;
      } catch (Exception var14) {
         WTCLogger.logErrorDom80SendTokenCreation(1);
         if (traceEnabled) {
            ntrace.doTrace("]/sendDom80Token1/20/return -1");
         }

         return -1;
      }
   }

   private int sendDom80Token2(byte[] out, int offset) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/sendDom80Token2/" + this.d80_desired_name);
      }

      try {
         int o = offset;
         byte[] clear_text = new byte[72];
         byte[] cipher_text = new byte[72];
         TDumpByte td = new TDumpByte();
         Arrays.fill(clear_text, (byte)0);

         for(int i = 0; i < 8; ++i) {
            clear_text[i] = (byte)((this.d80_challenge[i] << 1) % 256);
         }

         TPCrypt cipher = new TPCrypt();
         byte[] key = new byte[8];
         byte[] tmpkey = cipher.randKey();
         this.d80_session_key = new byte[tmpkey.length];
         System.arraycopy(tmpkey, 0, this.d80_session_key, 0, tmpkey.length);
         System.arraycopy(this.d80_session_key, 0, clear_text, 8, 8);
         System.arraycopy(this.d80_app_data, 0, clear_text, 16, 50);
         if (ntrace.getTraceLevel() == 1000373) {
            td.printDump("clear_text", clear_text);
         }

         if (traceEnabled) {
            ntrace.doTrace("sending dom " + this.getSrcName());
         }

         cipher.pwToKey(this.getLocalPasswd(), key);
         if (traceEnabled) {
            ntrace.doTrace("use password " + this.getLocalPasswd() + " to create secret key");
            if (ntrace.getTraceLevel() == 1000373) {
               td.printDump("secret key", key);
            }
         }

         cipher.crypt(clear_text, cipher_text, clear_text.length, 1);
         if (ntrace.getTraceLevel() == 1000373) {
            td.printDump("cipher_text", cipher_text);
         }

         offset += Utilities.baWriteInt(2, out, offset);
         offset += Utilities.baWriteInt(1, out, offset);
         offset += Utilities.baWriteInt(0, out, offset);
         offset += Utilities.baWriteInt(0, out, offset);
         offset += Utilities.baWriteXdrString(out, offset, this.d80_desired_name);
         offset += Utilities.baWriteInt(72, out, offset);
         offset += Utilities.baWriteXdrBOpaque(out, offset, cipher_text, 0, 72);
         if (traceEnabled) {
            ntrace.doTrace("]/sendDom80Token2/10/return " + (offset - o));
         }

         return offset - o;
      } catch (Exception var12) {
         WTCLogger.logErrorDom80SendTokenCreation(2);
         if (traceEnabled) {
            ntrace.doTrace("]/sendDom80Token2/20/return -1");
         }

         return -1;
      }
   }

   private int sendDom80Token3(byte[] out, int offset) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/sendDom80Token3/" + this.d80_desired_name);
      }

      try {
         int o = offset;
         int len = false;
         byte[] clear_text = new byte[520];
         TDumpByte td = new TDumpByte();
         Arrays.fill(clear_text, (byte)0);
         int len = Utilities.baWriteXdrString(clear_text, 0, this.d80_desired_name);
         if (ntrace.getTraceLevel() == 1000373) {
            td.printDump("clear_text", clear_text);
         }

         int i = (len + 7) / 8 * 8;
         byte[] buffer = new byte[i];
         byte[] cipher_text = new byte[i];
         Arrays.fill(buffer, (byte)0);
         System.arraycopy(clear_text, 0, buffer, 0, len);
         TPCrypt cipher = new TPCrypt();
         cipher.setKey(this.d80_session_key);
         cipher.crypt(buffer, cipher_text, i, 1);
         if (ntrace.getTraceLevel() == 1000373) {
            td.printDump("encrypted sending_dom", cipher_text);
         }

         offset += Utilities.baWriteInt(3, out, offset);
         offset += Utilities.baWriteInt(1, out, offset);
         offset += Utilities.baWriteInt(0, out, offset);
         offset += Utilities.baWriteInt(0, out, offset);
         offset += Utilities.baWriteInt(i, out, offset);
         offset += Utilities.baWriteXdrBOpaque(out, offset, cipher_text, 0, i);
         if (traceEnabled) {
            ntrace.doTrace("]/sendDom80Token3/10/return " + (offset - o));
         }

         return offset - o;
      } catch (Exception var12) {
         WTCLogger.logErrorDom80SendTokenCreation(3);
         if (traceEnabled) {
            ntrace.doTrace("]/sendDom80Token3/20/return -1");
         }

         return -1;
      }
   }

   private int sendDom80Token4(byte[] out, int offset) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/sendDom80Token4/" + this.d80_desired_name);
      }

      try {
         int o = offset;
         offset += Utilities.baWriteInt(4, out, offset);
         offset += Utilities.baWriteInt(1, out, offset);
         offset += Utilities.baWriteInt(0, out, offset);
         offset += Utilities.baWriteInt(0, out, offset);
         offset += Utilities.baWriteXdrString(out, offset, this.d80_sec_rtn);
         if (traceEnabled) {
            ntrace.doTrace("]/sendDom80Token4/10/ return true");
         }

         return offset - o;
      } catch (Exception var5) {
         WTCLogger.logErrorDom80SendTokenCreation(4);
         if (traceEnabled) {
            ntrace.doTrace("]/sendDom80Token4/20/return -1");
         }

         return -1;
      }
   }

   private int recvDom80Token1(byte[] in, int offset) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/recvDom80Token1/" + this.d80_desired_name);
      }

      try {
         byte[] cipher_text = new byte[64];
         TDumpByte td = new TDumpByte();
         int type = Utilities.baReadInt(in, offset);
         offset += 4;
         Utilities.baReadInt(in, offset);
         offset += 4;
         Utilities.baReadInt(in, offset);
         offset += 4;
         Utilities.baReadInt(in, offset);
         offset += 4;
         if (type != 1) {
            WTCLogger.logErrorTokenError(1, 1, type);
            if (traceEnabled) {
               ntrace.doTrace("]/recvDom80Token1/10/ERROR: Protocol error, unsupported token type " + type);
            }

            return -1;
         } else {
            this.d80_src_name = Utilities.baReadXdrString(in, offset);
            offset += 4;
            if (this.d80_src_name != null) {
               offset += Utilities.roundup4(this.d80_src_name.length());
            }

            int len = Utilities.baReadInt(in, offset);
            offset += 4;
            if (len != 64) {
               WTCLogger.logErrorTokenError(1, 6, len);
               if (traceEnabled) {
                  ntrace.doTrace("]/recvDom80Token1/30/ERROR: Protocol error, encrypted data length error " + len);
               }

               return -1;
            } else {
               offset += Utilities.baReadXdrBOpaque(cipher_text, 0, in, offset, 64);
               byte[] clear_text = new byte[64];
               byte[] key = new byte[8];
               TPCrypt cipher = new TPCrypt();
               cipher.pwToKey(this.getRemotePasswd(), key);
               cipher.crypt(cipher_text, clear_text, 64, 0);
               this.d80_challenge = new byte[8];
               System.arraycopy(clear_text, 0, this.d80_challenge, 0, 8);
               if (traceEnabled && ntrace.getTraceLevel() == 1000373) {
                  td.printDump("challenge", this.d80_challenge);
               }

               for(int i = 0; i < 50; ++i) {
                  if (clear_text[i + 8] != this.d80_app_data[i]) {
                     WTCLogger.logErrorTokenError(1, 5, 0);
                     if (traceEnabled) {
                        ntrace.doTrace("]/recvDom80Token1/40/ERROR: Protocol error, fingerprint does not match");
                     }

                     return -1;
                  }
               }

               if (ntrace.getTraceLevel() == 1000373) {
                  td.printDump("clear_text", clear_text);
               }

               if (traceEnabled) {
                  ntrace.doTrace("]/recvDom80Token1/50/ return " + offset);
               }

               return offset;
            }
         }
      } catch (Exception var14) {
         WTCLogger.logErrorDom80RecvTokenRead(1);
         if (traceEnabled) {
            ntrace.doTrace("]/recvDom80Token1/60/return -1");
         }

         return -1;
      }
   }

   private int recvDom80Token2(byte[] in, int offset) {
      byte[] cipher_text = new byte[72];
      byte[] clear_text = new byte[72];
      byte[] fingerprint = new byte[50];
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/recvDom80Token2/" + this.d80_desired_name);
      }

      try {
         int i = false;
         TDumpByte td = new TDumpByte();
         byte[] chlg = new byte[8];
         this.d80_session_key = new byte[8];
         int type = Utilities.baReadInt(in, offset);
         offset += 4;
         Utilities.baReadInt(in, offset);
         offset += 4;
         Utilities.baReadInt(in, offset);
         offset += 4;
         Utilities.baReadInt(in, offset);
         offset += 4;
         if (type != 2) {
            WTCLogger.logErrorTokenError(2, 1, type);
            if (traceEnabled) {
               ntrace.doTrace("]/recvDom80Token2/10/ERROR: Protocol error, unsupported token type " + type);
            }

            return -1;
         } else {
            String target = Utilities.baReadXdrString(in, offset);
            offset += 4;
            if (target != null) {
               offset += Utilities.roundup4(target.length());
            }

            if (!target.equals(this.d80_target_name)) {
               WTCLogger.logErrorTokenError(2, 4, 0);
               if (traceEnabled) {
                  ntrace.doTrace("]/recvDom80Token2/30/ERROR: Protocol error, unexpected target name " + target);
               }

               return -1;
            } else {
               int len = Utilities.baReadInt(in, offset);
               offset += 4;
               if (len != 72) {
                  WTCLogger.logErrorTokenError(2, 6, len);
                  if (traceEnabled) {
                     ntrace.doTrace("]/recvDom80Token2/40/ERROR: Protocol error, incorrect encrypted data len " + len);
                  }

                  return -1;
               } else {
                  offset += Utilities.baReadXdrBOpaque(cipher_text, 0, in, offset, 72);
                  byte[] key = new byte[8];
                  TPCrypt cipher = new TPCrypt();
                  cipher.pwToKey(this.getRemotePasswd(), key);
                  if (ntrace.getTraceLevel() == 1000373) {
                     td.printDump("key", key);
                  }

                  cipher.crypt(cipher_text, clear_text, 72, 0);
                  if (ntrace.getTraceLevel() == 1000373) {
                     td.printDump("clear_text", clear_text);
                  }

                  System.arraycopy(clear_text, 0, chlg, 0, 8);
                  if (!Arrays.equals(chlg, this.d80_challenge)) {
                     WTCLogger.logErrorTokenError(2, 3, 0);
                     if (traceEnabled) {
                        ntrace.doTrace("]/recvDom80Token2/50/ERROR: Protocol error, mismatched challenge value");
                     }

                     return -1;
                  } else {
                     System.arraycopy(clear_text, 8, this.d80_session_key, 0, 8);
                     if (ntrace.getTraceLevel() == 1000373) {
                        td.printDump("session_key", this.d80_session_key);
                     }

                     System.arraycopy(clear_text, 16, fingerprint, 0, 50);

                     for(int i = 0; i < 50; ++i) {
                        if (fingerprint[i] != this.d80_app_data[i]) {
                           WTCLogger.logErrorTokenError(2, 5, 0);
                           if (traceEnabled) {
                              ntrace.doTrace("]/recvDom80Token2/60/ERROR: Protocol error, fingerprint does not match");
                           }

                           return -1;
                        }
                     }

                     if (traceEnabled) {
                        ntrace.doTrace("]/recvDom80Token2/70/return " + offset);
                     }

                     return offset;
                  }
               }
            }
         }
      } catch (Exception var17) {
         WTCLogger.logErrorDom80RecvTokenRead(2);
         if (traceEnabled) {
            ntrace.doTrace("]/recvDom80Token2/80/return -1");
         }

         return -1;
      }
   }

   private int recvDom80Token3(byte[] in, int offset) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/recvDom80Token3/" + this.d80_desired_name);
      }

      try {
         byte[] cipher_text = new byte[520];
         TDumpByte td = new TDumpByte();
         int type = Utilities.baReadInt(in, offset);
         offset += 4;
         Utilities.baReadInt(in, offset);
         offset += 4;
         Utilities.baReadInt(in, offset);
         offset += 4;
         Utilities.baReadInt(in, offset);
         offset += 4;
         if (type != 3) {
            WTCLogger.logErrorTokenError(3, 1, type);
            this.d80_sec_rtn = new String("FAILED");
            if (traceEnabled) {
               ntrace.doTrace("]/recvDom80Token3/10/ERROR: Protocol error, unsupported token type " + type);
            }

            return -1;
         } else {
            int len = Utilities.baReadInt(in, offset);
            offset += 4;
            if (len <= 520 && len >= 4) {
               offset += Utilities.baReadXdrBOpaque(cipher_text, 0, in, offset, len);
               byte[] clear_text = new byte[len];
               TPCrypt cipher = new TPCrypt();
               cipher.setKey(this.d80_session_key);
               cipher.crypt(cipher_text, clear_text, len, 0);
               if (ntrace.getTraceLevel() == 1000373) {
                  td.printDump("clear_text", clear_text);
               }

               int ilen = Utilities.baReadInt(clear_text, 0);
               if (ilen >= 0 && ilen <= len - 4 && (ilen + 4 + 7) / 8 * 8 == len) {
                  String sending_dom = Utilities.baReadXdrString(clear_text, 0);
                  String src_name = this.getSrcName();
                  if (!src_name.equals(sending_dom)) {
                     WTCLogger.logErrorTokenError(3, 8, 0);
                     if (traceEnabled) {
                        ntrace.doTrace("]/recvDom80Token3/50/ERROR: Protocol error, mismatched source name " + sending_dom);
                     }

                     this.d80_sec_rtn = new String("FAILED");
                     return -1;
                  } else {
                     this.d80_sec_rtn = new String("PASSED");
                     if (traceEnabled) {
                        ntrace.doTrace("]/recvDom80Token3/60/return " + offset);
                     }

                     return offset;
                  }
               } else {
                  WTCLogger.logErrorTokenError(3, 7, ilen);
                  this.d80_sec_rtn = new String("FAILED");
                  if (traceEnabled) {
                     ntrace.doTrace("]/recvDom80Token3/40/ERROR: Protocol error, incorrect encrypted data len " + ilen);
                  }

                  return -1;
               }
            } else {
               WTCLogger.logErrorTokenError(3, 6, len);
               this.d80_sec_rtn = new String("FAILED");
               if (traceEnabled) {
                  ntrace.doTrace("]/recvDom80Token3/30/ERROR: Protocol error, incorrect encrypted data len " + len);
               }

               return -1;
            }
         }
      } catch (Exception var16) {
         WTCLogger.logErrorDom80RecvTokenRead(3);
         if (traceEnabled) {
            ntrace.doTrace("]/recvDom80Token3/70/return -1");
         }

         return -1;
      }
   }

   private int recvDom80Token4(byte[] in, int offset) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/recvDom80Token4/" + this.d80_desired_name);
      }

      try {
         int type = Utilities.baReadInt(in, offset);
         offset += 4;
         Utilities.baReadInt(in, offset);
         offset += 4;
         Utilities.baReadInt(in, offset);
         offset += 4;
         Utilities.baReadInt(in, offset);
         offset += 4;
         if (type != 4) {
            WTCLogger.logErrorTokenError(4, 1, type);
            if (traceEnabled) {
               ntrace.doTrace("]/recvDom80Token4/10/ERROR: Protocol error, unsupported token type " + type);
            }

            return -1;
         } else {
            String rtn_str = Utilities.baReadXdrString(in, offset);
            if (traceEnabled) {
               if (rtn_str == null) {
                  ntrace.doTrace("rtn_str is null");
               } else {
                  ntrace.doTrace("rtn_str " + rtn_str + " length " + rtn_str.length());
               }
            }

            if (rtn_str != null && rtn_str.equals("PASSED")) {
               offset += Utilities.roundup4(rtn_str.length()) + 4;
               if (traceEnabled) {
                  ntrace.doTrace("]/recvDom80Token4/40/return " + offset);
               }

               return offset;
            } else {
               WTCLogger.logErrorTokenError(4, 9, 0);
               if (traceEnabled) {
                  ntrace.doTrace("]/recvDom80Token4/30/ERROR: Protocol error, bad return string");
               }

               return -1;
            }
         }
      } catch (Exception var8) {
         WTCLogger.logErrorDom80RecvTokenRead(4);
         if (traceEnabled) {
            ntrace.doTrace("]/recvDom80Token4/50/return -1");
         }

         return -1;
      }
   }
}
