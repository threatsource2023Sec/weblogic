package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.Utilities;
import com.bea.core.jatmi.common.ntrace;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import weblogic.wtc.WTCLogger;

public final class tsession implements ApplicationToMonitorInterface {
   private InetAddress wsnaddr_ip;
   private int wsnaddr_port;
   private boolean isinited = false;
   private boolean isterm = false;
   private int wsc_sign_ah;
   private int wsc_sign_bh;
   private int cmplimit = Integer.MAX_VALUE;
   private int encbits;
   private int tstamp;
   private int auth_type;
   private int wsflags;
   private Socket wsh_socket;
   private DataOutputStream wsh_ostream;
   private DataInputStream wsh_istream;
   private atn gssatn;
   private String wsh_principal_name;
   private int wscpid;
   private int cltid = -1;
   private int reqst_gen = 1;
   private int tmsndprio = 50;
   private rsession rcv_place;
   private Thread thr_place;
   private unsol unsol_hndlr;
   private long txtime;
   private long transtart;
   private int srv_protocol;
   private byte[] ws65_session_key;
   private byte[] ws65_ticket;
   private static final int WSCAMCLT = 1;
   private static final int WSCAUTH = 2;
   private static final int WSCENCDEC = 4;
   private static final int WSCINTRAN = 8;
   private static final int WSCLOGGED = 16;
   private static final int WSCTHR = 32;
   private static final int WSCBEGIN = 64;
   private static final int WSCIGN = 256;
   private static final int WSCDIP = 512;
   private static final int WSCSIG = 1024;
   private static final int WSCSYSCLNT = 2048;
   private static final int WSCSHM = 4096;
   private static final int WSCENCRYPT = 8192;
   private static final int WSCTRANTIME = 16384;
   private static final int WSCABRTONLY = 32768;
   private static final int WSCKEEPALIVE = 65536;
   private static final int WSCDECRYPTEDMHDR = 131072;
   private static final int WSCCALLPROCESBUF = 262144;
   private static final int TM_PRIORANGE = 100;
   private static final int TM_SENDBASE = 536870912;
   private static final int INITIAL_ATN_SIZE = 4000;

   public tsession(InetAddress ip, int port) {
      this.wsnaddr_ip = ip;
      this.wsnaddr_port = port;
      this.auth_type = -1;
      this.wscpid = (int)System.currentTimeMillis();
   }

   public tsession(InetAddress ip, int port, atn gssimpl) {
      this.wsnaddr_ip = ip;
      this.wsnaddr_port = port;
      this.auth_type = -1;
      this.gssatn = gssimpl;
      this.wscpid = (int)System.currentTimeMillis();
   }

   public void setWSH_PRINCIPAL_NAME(String wshname) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/tsession/setWSH_PRINCIPAL_NAME/" + wshname);
      }

      this.wsh_principal_name = new String(wshname);
      if (traceEnabled) {
         ntrace.doTrace("]/tsession/setWSH_PRINCIPAL_NAME/10/");
      }

   }

   public synchronized unsol tpsetunsol(unsol hndlr) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/tsession/tpsetunsol/" + hndlr);
      }

      unsol ret = this.unsol_hndlr;
      this.unsol_hndlr = hndlr;
      if (this.rcv_place != null) {
         this.rcv_place.set_unsol_hndlr(hndlr);
      }

      if (traceEnabled) {
         ntrace.doTrace("]/tsession/tpsetunsol/10/" + ret);
      }

      return ret;
   }

   public synchronized int tpchkauth() throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/tsession/tpchkauth/");
      }

      if (this.isterm) {
         if (traceEnabled) {
            ntrace.doTrace("*]/tsession/tpchkauth/10/");
         }

         throw new TPException(9, "Tuxedo session has been terminated");
      } else if (this.auth_type != -1) {
         if (traceEnabled) {
            ntrace.doTrace("]/tsession/tpchkauth/20/" + this.auth_type);
         }

         return this.auth_type;
      } else {
         metahdr mhdr = new metahdr(true);
         mhdr.mprotocol = 42;
         mhdr.mtype = 5308465;
         mhdr.size = 32;
         mhdr.flags = 1;
         mhdr.qaddr = -1;
         mhdr.mid = -2;

         try {
            label156: {
               Socket wsl_socket = new Socket(this.wsnaddr_ip, this.wsnaddr_port);
               DataOutputStream wsl_output = new DataOutputStream(wsl_socket.getOutputStream());
               mhdr.write_metahdr(wsl_output);
               DataInputStream wsl_input = new DataInputStream(wsl_socket.getInputStream());
               tfmh msg = new tfmh(0);
               int ret = msg.read_tfmh(wsl_input);
               this.srv_protocol = msg.getProtocol();
               if (ret == 1 && msg.read_ws_65_tfmh(wsl_input, true) != 0) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/tsession/tpchkauth/30/");
                  }

                  throw new TPException(12, "Unable to read WSL packet");
               }

               if (ret < 0) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/tsession/tpchkauth/30/");
                  }

                  throw new TPException(12, "Unable to read WSL packet");
               }

               wsl_socket.close();
               if (msg.getProtocol() < 46) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/tsession/tpchkauth/40/");
                  }

                  throw new TPException(9, "weblogic.wtc.jatmi only able to speak to 6.5 (and above) WSL protocol" + msg.getProtocol());
               }

               if (msg.user == null) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/tsession/tpchkauth/50/");
                  }

                  throw new TPException(12, "User data from WSL invalid");
               }

               UserTcb utcm;
               if ((utcm = (UserTcb)msg.user.body) != null && utcm.user_data != null) {
                  if (!utcm.user_data.getType().equals("wsrpcrq")) {
                     if (traceEnabled) {
                        ntrace.doTrace("*]/tsession/tpchkauth/70/");
                     }

                     throw new TPException(12, "Invalid type from WSL " + ((UserTcb)msg.user.body).user_data.getType());
                  }

                  WSRPCRQ wsrpcrq = (WSRPCRQ)utcm.user_data;
                  WsTcb tmmsg_ws;
                  if (msg.ws != null && (tmmsg_ws = (WsTcb)msg.ws.body) != null) {
                     if (tmmsg_ws.get_ws_rtn_val() == -1) {
                        if (traceEnabled) {
                           ntrace.doTrace("*]/tsession/tpchkauth/90/");
                        }

                        throw new TPException(tmmsg_ws.get_ws_TPException(), tmmsg_ws.get_ws_Uunixerr(), tmmsg_ws.get_ws_tpurcode(), tmmsg_ws.get_ws_tperrordetail(), "Unable to establish connection on native site");
                     }

                     this.wsc_sign_ah = tmmsg_ws.get_ws_sigahead();
                     this.wsc_sign_bh = tmmsg_ws.get_ws_sigbehind();
                     this.cmplimit = tmmsg_ws.get_ws_cmplimit();
                     if ((this.encbits = tmmsg_ws.get_ws_encbits()) != 1) {
                        if (traceEnabled) {
                           ntrace.doTrace("*]/tsession/tpchkauth/100/");
                        }

                        throw new TPException(12, "Do not yet support LLE!");
                     }

                     this.tstamp = wsrpcrq.timestamp;
                     this.auth_type = wsrpcrq.auth_type;
                     switch (wsrpcrq.notify) {
                        case 1:
                           this.wsflags |= 1024;
                           break;
                        case 2:
                           this.wsflags |= 512;
                           break;
                        case 64:
                           this.wsflags |= 32;
                           break;
                        default:
                           this.wsflags |= 256;
                     }

                     if ((wsrpcrq.options & 32) != 0) {
                        this.wsflags |= 16;
                     }

                     if ((wsrpcrq.options & 67108864) != 0) {
                        this.wsflags |= 4096;
                     }

                     this.wsh_socket = new Socket(wsrpcrq.wsh_addr, wsrpcrq.port);
                     this.wsh_ostream = new DataOutputStream(this.wsh_socket.getOutputStream());
                     this.wsh_istream = new DataInputStream(this.wsh_socket.getInputStream());
                     break label156;
                  }

                  if (traceEnabled) {
                     ntrace.doTrace("*]/tsession/tpchkauth/80/");
                  }

                  throw new TPException(12, "WS TCM is not present");
               }

               if (traceEnabled) {
                  ntrace.doTrace("*]/tsession/tpchkauth/60/");
               }

               throw new TPException(12, "utcm invalid");
            }
         } catch (IOException var11) {
            if (traceEnabled) {
               ntrace.doTrace("*]/tsession/tpchkauth/110/" + var11);
            }

            throw new TPException(12, "Unable to get authentication level");
         }

         if (traceEnabled) {
            ntrace.doTrace("]/tsession/tpchkauth/120/" + this.auth_type);
         }

         return this.auth_type;
      }
   }

   private WsTcb alloc_WS(int opcode) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/tsession/alloc_WS/" + opcode);
      }

      WsTcb ret = this.alloc_WS(opcode, (String)null);
      if (traceEnabled) {
         ntrace.doTrace("]/tsession/alloc_WS/10/" + ret);
      }

      return ret;
   }

   private synchronized WsTcb alloc_WS(int opcode, String svc) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/tsession/alloc_WS/" + opcode + "/" + svc);
      }

      if ((this.wsflags & 64) != 0) {
         long current_time = System.currentTimeMillis() / 1000L;
         long diff = current_time - this.transtart;
         if (diff >= this.txtime) {
            if (traceEnabled) {
               ntrace.doTrace("*]/tsession/alloc_WS/10/");
            }

            throw new TPException(13);
         }

         this.txtime -= diff;
         opcode |= Integer.MIN_VALUE;
      }

      WsTcb tmmsg_ws;
      if (svc == null) {
         tmmsg_ws = new WsTcb(opcode);
      } else {
         tmmsg_ws = new WsTcb(opcode, svc);
      }

      tmmsg_ws.set_HANDLE(this.wscpid);
      tmmsg_ws.set_CLTID(this.cltid);
      switch (opcode & 1073741823) {
         default:
            tmmsg_ws.set_REQGEN(this.reqst_gen);
            if (this.reqst_gen < 64000) {
               ++this.reqst_gen;
            } else {
               this.reqst_gen = 1;
            }
         case 1:
         case 7:
            tmmsg_ws.set_WSFLAGS(this.wsflags);
            if ((this.wsflags & 64) != 0) {
               tmmsg_ws.set_TIMEOUT((int)this.txtime);
               this.wsflags &= -65;
               this.txtime = 0L;
            }

            if (traceEnabled) {
               ntrace.doTrace("]/tsession/alloc_WS/20/" + tmmsg_ws);
            }

            return tmmsg_ws;
      }
   }

   private synchronized void _wsc_drop() {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/tsession/_wsc_drop/");
      }

      this.isterm = true;
      if (this.wsh_socket == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/tsession/_wsc_drop/10/");
         }

      } else {
         try {
            this.wsh_ostream = null;
            this.wsh_istream = null;
            this.wsh_socket.close();
            this.wsh_socket = null;
         } catch (IOException var3) {
            WTCLogger.logIOEbadWscSocketClose(var3.getMessage());
         }

         if (traceEnabled) {
            ntrace.doTrace("]/tsession/_wsc_drop/20/");
         }

      }
   }

   public synchronized void tpinit(TPINIT tpinfo) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/tsession/tpinit/");
      }

      atncred mygsscred = null;
      atncontext mygssctx = null;
      TPINIT tpinitbuf = null;
      if (this.isinited) {
         if (traceEnabled) {
            ntrace.doTrace("*]/tsession/tpinit/10/");
         }

         throw new TPException(9, "Can not init object more than once");
      } else if (this.isterm) {
         if (traceEnabled) {
            ntrace.doTrace("*]/tsession/tpinit/20/");
         }

         throw new TPException(9, "Tuxedo session has been terminated");
      } else {
         int authcode = this.tpchkauth();
         Object mygss;
         if (this.gssatn == null) {
            mygss = new atntd(tpinfo, this.tstamp);
         } else {
            mygss = this.gssatn;
         }

         try {
            if (tpinfo == null) {
               mygsscred = ((atn)mygss).gssAcquireCred("");
            } else if (tpinfo.no_usrpasswd) {
               mygsscred = ((atn)mygss).gssAcquireCred(tpinfo.usrname);
            } else if (tpinfo.use_string_usrpasswd) {
               mygsscred = ((atn)mygss).gssAcquireCred(tpinfo.usrname, tpinfo.usrpasswd);
            } else {
               mygsscred = ((atn)mygss).gssAcquireCred(tpinfo.usrname, tpinfo.data);
            }
         } catch (EngineSecError var25) {
            if (traceEnabled) {
               ntrace.doTrace("*]/tsession/tpinit/20/");
            }

            throw new TPException(8, "Unable to acquire credentials (" + var25.errno + ")");
         }

         WsTcb tmmsg_ws;
         tcm user;
         tcm ws;
         tfmh tmmsg;
         tfmh tmmsg_in;
         WsTcb tmmsg_ws_in;
         WSRPCRQ wsrpcrq;
         if (this.srv_protocol <= 46) {
            if (authcode != 0 && tpinfo != null) {
               wsrpcrq = new WSRPCRQ();
               user = new tcm((short)0, new UserTcb(wsrpcrq));
               tmmsg_ws = this.alloc_WS(6);
               ws = new tcm((short)3, tmmsg_ws);
               tmmsg = new tfmh(11, user, 0);
               tmmsg.ws = ws;

               try {
                  if (tmmsg.write_ws_65_tfmh(this.wsh_ostream, this.cmplimit, authcode, tpinfo) != 0) {
                     if (traceEnabled) {
                        ntrace.doTrace("*]/tsession/tpinit/25/");
                     }

                     throw new TPException(12, "challenge write failed");
                  }

                  byte[] snd_challenge = tmmsg.get_ws_challenge();
                  tmmsg_in = new tfmh(0);
                  if (tmmsg_in.read_ws_65_tfmh(this.wsh_istream, false) != 0) {
                     if (traceEnabled) {
                        ntrace.doTrace("*]/tsession/tpinit/27/");
                     }

                     throw new TPException(12, "challenge reply read failed");
                  }

                  if (tmmsg_in.read_challenge(tpinfo.passwd) != 0) {
                     if (traceEnabled) {
                        ntrace.doTrace("*]/tsession/tpinit/28/");
                     }

                     throw new TPException(12, "challenge reply read failed");
                  }

                  if (!Arrays.equals(snd_challenge, tmmsg_in.challenge)) {
                     if (traceEnabled) {
                        ntrace.doTrace("]/tsession/tpinit/90/return -1");
                     }

                     throw new TPException(12, "challenge reply read failed");
                  }

                  tmmsg_ws_in = (WsTcb)tmmsg_in.ws.body;
                  if (this.cltid == -1) {
                     this.cltid = tmmsg_ws_in.get_CLTID();
                  }

                  this.ws65_session_key = tmmsg_in.get_ws65_session_key();
                  this.ws65_ticket = tmmsg_in.get_ws65_ticket();
                  wsrpcrq = new WSRPCRQ();
                  user = new tcm((short)0, new UserTcb(wsrpcrq));
                  tmmsg_ws = this.alloc_WS(13);
                  ws = new tcm((short)3, tmmsg_ws);
                  tmmsg = new tfmh(11, user, 0);
                  tmmsg.ws = ws;
                  tmmsg_ws.set_CLTID(this.cltid);
                  tmmsg.set_ws65_session_key(this.ws65_session_key);
                  tmmsg.set_ws65_ticket(this.ws65_ticket);
                  tmmsg.set_timestamp(this.tstamp);
                  if (tmmsg.write_ws_65_tfmh(this.wsh_ostream, this.cmplimit, authcode, tpinfo) != 0) {
                     if (traceEnabled) {
                        ntrace.doTrace("*]/tsession/tpinit/25/");
                     }

                     throw new TPException(12, "challenge write failed");
                  }

                  tmmsg_in = new tfmh(0);
                  if (tmmsg_in.read_ws_65_tfmh(this.wsh_istream, false) != 0) {
                     if (traceEnabled) {
                        ntrace.doTrace("*]/tsession/tpinit/27/");
                     }

                     throw new TPException(12, "ticket reply read failed");
                  }
               } catch (IOException var23) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/tsession/tpinit/100/");
                  }

                  throw new TPException(7, "Write failed: " + var23);
               }
            }
         } else {
            try {
               mygssctx = ((atn)mygss).gssGetContext(mygsscred, this.wsh_principal_name);
            } catch (EngineSecError var22) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/tsession/tpinit/30/");
               }

               throw new TPException(8, "Unable to get context (" + var22.errno + ")");
            }

            TypedCArray output_token = new TypedCArray(4000);
            user = new tcm((short)0, new UserTcb(output_token));

            int send_size;
            try {
               send_size = ((atn)mygss).gssInitSecContext(mygssctx, (byte[])null, 0, output_token.carray);
               output_token.setSendSize(send_size);
            } catch (EngineSecError var28) {
               if (var28.errno != -3005) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/tsession/tpinit/50/");
                  }

                  throw new TPException(8, "Security violation (" + var28.errno + ")");
               }

               output_token.carray = new byte[var28.needspace];

               try {
                  send_size = ((atn)mygss).gssInitSecContext(mygssctx, (byte[])null, 0, output_token.carray);
                  output_token.setSendSize(send_size);
               } catch (EngineSecError var24) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/tsession/tpinit/40/");
                  }

                  throw new TPException(8, "Security violation (" + var24.errno + ")");
               }
            }

            tmmsg_ws = this.alloc_WS(25);
            ws = new tcm((short)3, tmmsg_ws);
            tmmsg = new tfmh(16, user, 0);
            tmmsg.ws = ws;
            tmmsg.user = user;

            while(send_size > 0) {
               TypedCArray input_token;
               try {
                  if (tmmsg.write_tfmh(this.wsh_ostream, this.cmplimit) != 0) {
                     if (traceEnabled) {
                        ntrace.doTrace("*]/tsession/tpinit/60/");
                     }

                     throw new TPException(12, "Atn write failed");
                  }

                  tmmsg_in = new tfmh(0);
                  if (tmmsg_in.read_tfmh(this.wsh_istream) != 0) {
                     if (traceEnabled) {
                        ntrace.doTrace("*]/tsession/tpinit/70/");
                     }

                     throw new TPException(12, "Atn read failed");
                  }

                  tmmsg_ws_in = (WsTcb)tmmsg_in.ws.body;
                  if (tmmsg_ws_in.get_opcode() != 25) {
                     if (traceEnabled) {
                        ntrace.doTrace("*]/tsession/tpinit/80/");
                     }

                     throw new TPException(12, "Invalid opcode " + tmmsg_ws_in.get_opcode() + ". OWS_ATN expected");
                  }

                  if (tmmsg_in.user == null) {
                     input_token = null;
                  } else {
                     input_token = (TypedCArray)((UserTcb)tmmsg_in.user.body).user_data;
                     if (input_token.getHintIndex() != 16) {
                        if (traceEnabled) {
                           ntrace.doTrace("*]/tsession/tpinit/90/");
                        }

                        throw new TPException(12, "Invalid type " + input_token.getType() + ". TypedCArray expected.");
                     }
                  }
               } catch (IOException var30) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/tsession/tpinit/100/");
                  }

                  throw new TPException(7, "Write failed: " + var30);
               }

               if (this.cltid == -1) {
                  this.cltid = tmmsg_ws_in.get_CLTID();
                  tmmsg_ws.set_CLTID(this.cltid);
               }

               try {
                  if (input_token == null) {
                     send_size = ((atn)mygss).gssInitSecContext(mygssctx, (byte[])null, 0, output_token.carray);
                     output_token.setSendSize(send_size);
                  } else {
                     send_size = ((atn)mygss).gssInitSecContext(mygssctx, input_token.carray, input_token.getSendSize(), output_token.carray);
                     output_token.setSendSize(send_size);
                  }
               } catch (EngineSecError var29) {
                  if (var29.errno != -3005) {
                     if (traceEnabled) {
                        ntrace.doTrace("*]/tsession/tpinit/120/");
                     }

                     throw new TPException(8, "Security violation (" + var29.errno + ")");
                  }

                  output_token.carray = new byte[var29.needspace];

                  try {
                     if (input_token == null) {
                        send_size = ((atn)mygss).gssInitSecContext(mygssctx, (byte[])null, 0, output_token.carray);
                        output_token.setSendSize(send_size);
                        output_token.setSendSize(send_size);
                     } else {
                        send_size = ((atn)mygss).gssInitSecContext(mygssctx, input_token.carray, input_token.getSendSize(), output_token.carray);
                        output_token.setSendSize(send_size);
                     }
                  } catch (EngineSecError var26) {
                     if (traceEnabled) {
                        ntrace.doTrace("*]/tsession/tpinit/110/");
                     }

                     throw new TPException(8, "Security violation (" + var26.errno + ")");
                  }
               }
            }
         }

         int ret;
         if (tpinfo == null) {
            wsrpcrq = new WSRPCRQ((String)null, (String)null);
         } else {
            ret = tpinfo.flags & 71;
            if (ret != 64 && ret != 0) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/tsession/tpinit/130/");
               }

               throw new TPException(9, "Only thread notification allowed in java client");
            }

            wsrpcrq = new WSRPCRQ(tpinfo.usrname, tpinfo.cltname);
         }

         if (this.srv_protocol <= 46) {
            if (tpinfo == null) {
               tpinitbuf = new TPINIT();
            } else {
               tpinitbuf = tpinfo;
            }

            user = new tcm((short)0, new UserTcb(tpinitbuf));
            tmmsg_ws = this.alloc_WS(11);
         } else {
            user = new tcm((short)0, new UserTcb(wsrpcrq));
            tmmsg_ws = this.alloc_WS(27);
         }

         ws = new tcm((short)3, tmmsg_ws);
         if (this.srv_protocol <= 46) {
            tmmsg = new tfmh(3, user, 0);
            tmmsg.set_ws65_session_key(this.ws65_session_key);
         } else {
            tmmsg = new tfmh(11, user, 0);
         }

         tmmsg.ws = ws;
         tmmsg.user = user;

         WSRPCRQ wsrpcrq_rp;
         try {
            if (this.srv_protocol <= 46) {
               ret = tmmsg.write_ws_65_tfmh(this.wsh_ostream, this.cmplimit, authcode, tpinitbuf);
            } else {
               ret = tmmsg.write_tfmh(this.wsh_ostream, this.cmplimit);
            }

            if (ret != 0) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/tsession/tpinit/140/");
               }

               throw new TPException(12, "init write failed");
            }

            tmmsg_in = new tfmh(0);
            if (this.srv_protocol <= 46) {
               ret = tmmsg_in.read_ws_65_tfmh(this.wsh_istream, false);
            } else {
               ret = tmmsg_in.read_tfmh(this.wsh_istream);
            }

            if (ret != 0) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/tsession/tpinit/150/");
               }

               throw new TPException(12, "init read failed");
            }

            tmmsg_ws_in = (WsTcb)tmmsg_in.ws.body;
            if (tmmsg_ws_in.get_opcode() != 27 && tmmsg_ws_in.get_opcode() != 11) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/tsession/tpinit/160/");
               }

               throw new TPException(12, "Invalid opcode " + tmmsg_ws_in.get_opcode() + ". OWS_INIT/OWS_INIT6 expected");
            }

            wsrpcrq_rp = (WSRPCRQ)((UserTcb)tmmsg_in.user.body).user_data;
         } catch (IOException var27) {
            if (traceEnabled) {
               ntrace.doTrace("*]/tsession/tpinit/170/");
            }

            throw new TPException(7, "tpinit Write failed: " + var27);
         }

         if (tmmsg_ws_in.get_HANDLE() != this.wscpid) {
            this._wsc_drop();
            if (traceEnabled) {
               ntrace.doTrace("*]/tsession/tpinit/180/");
            }

            throw new TPException(12, "Received message not intended for this client");
         } else if (tmmsg_ws_in.get_ws_rtn_val() == -1) {
            this._wsc_drop();
            if (traceEnabled) {
               ntrace.doTrace("*]/tsession/tpinit/190/");
            }

            throw new TPException(tmmsg_ws_in.get_ws_TPException(), tmmsg_ws_in.get_ws_Uunixerr(), tmmsg_ws_in.get_ws_tpurcode(), tmmsg_ws_in.get_ws_tperrordetail());
         } else if (wsrpcrq_rp.get_notifyopt() != 64) {
            this._wsc_drop();
            if (traceEnabled) {
               ntrace.doTrace("*]/tsession/tpinit/200/");
            }

            throw new TPException(12, "Could not set to THREAD notification");
         } else {
            if (tpinfo != null) {
               tpinfo.flags &= -72;
               tpinfo.flags |= 64;
            }

            this.wsflags &= -1793;
            this.wsflags |= 32;
            this.cltid = tmmsg_ws_in.get_CLTID();
            this.rcv_place = new rsession(this.wsh_istream, this.wsh_ostream, this.unsol_hndlr, this.cltid, this.wscpid, this.srv_protocol);
            this.thr_place = new Thread(this.rcv_place);
            this.thr_place.start();
            this.isinited = true;
            if (traceEnabled) {
               ntrace.doTrace("]/tsession/tpinit/220/");
            }

         }
      }
   }

   private synchronized void _tmmarkabort() {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/tsession/_tmmarkabort/");
      }

      if ((this.wsflags & 8) != 0) {
         this.wsflags |= 16384;
      }

      if (traceEnabled) {
         ntrace.doTrace("]/tsession/_tmmarkabort/10/");
      }

   }

   public synchronized void tpterm() throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/tsession/tpterm/");
      }

      if (this.wsh_socket == null) {
         if (traceEnabled) {
            ntrace.doTrace("*]/tsession/tpterm/10/");
         }

         throw new TPException(9);
      } else {
         WsTcb tmmsg_ws = this.alloc_WS(12);
         tcm ws = new tcm((short)3, tmmsg_ws);
         WsKey wskey = tmmsg_ws.get_key();
         tfmh tmmsg = new tfmh(0);
         tmmsg.ws = ws;

         try {
            synchronized(this.wsh_ostream) {
               if (this.srv_protocol <= 46) {
                  tmmsg.write_ws_65_tfmh(this.wsh_ostream, this.cmplimit, 0, (TPINIT)null);
               } else {
                  tmmsg.write_tfmh(this.wsh_ostream, this.cmplimit);
               }
            }
         } catch (IOException var10) {
            if (traceEnabled) {
               ntrace.doTrace("*]/tsession/tpterm/20/");
            }

            throw new TPException(12, "tpterm network send error: " + var10);
         }

         if (this.rcv_place != null && this.rcv_place.getlist(wskey, true) == null) {
            if (traceEnabled) {
               ntrace.doTrace("*]/tsession/tpterm/30/");
            }

            throw new TPException(12, "Could not get tpterm reply");
         } else {
            this._wsc_drop();
            if (traceEnabled) {
               ntrace.doTrace("]/tsession/tpterm/40/");
            }

         }
      }
   }

   public CallDescriptor tpacall(String svc, TypedBuffer data, int flags) throws TPException {
      return this.tpacall(svc, data, flags, (TpacallAsyncReply)null);
   }

   public CallDescriptor tpacall(String svc, TypedBuffer data, int flags, TpacallAsyncReply callBack) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/tsession/tpacall/" + svc + "/" + data + "/" + flags);
      }

      if (!this.isinited) {
         if (traceEnabled) {
            ntrace.doTrace("*]/tsession/tpacall/10/");
         }

         throw new TPException(9, "Must init before tpacall");
      } else {
         if (this.rcv_place.get_is_term()) {
            this._wsc_drop();
         }

         if (this.isterm) {
            if (traceEnabled) {
               ntrace.doTrace("*]/tsession/tpacall/20/");
            }

            throw new TPException(9, "Tuxedo session has been terminated");
         } else if ((flags & -46) != 0) {
            if (traceEnabled) {
               ntrace.doTrace("*]/tsession/tpacall/30/");
            }

            throw new TPException(4);
         } else if (svc != null && !svc.equals("")) {
            WsTcb tmmsg_ws = this.alloc_WS(2, svc);
            tmmsg_ws.set_FLAG(flags);
            tmmsg_ws.set_PRIO(flags);
            tcm ws = new tcm((short)3, tmmsg_ws);
            WsKey wskey = tmmsg_ws.get_key();
            int local_wsflags = tmmsg_ws.get_WSFLAGS();
            boolean intransaction = (local_wsflags & 8) != 0 && (flags & 8) == 0;
            tfmh tmmsg;
            if (data == null) {
               tmmsg = new tfmh(0);
            } else {
               tcm user = new tcm((short)0, new UserTcb(data));
               tmmsg = new tfmh(data.getHintIndex(), user, 0);
            }

            tmmsg.ws = ws;
            SessionAcallDescriptor cd;
            synchronized(this.rcv_place.get_cd_lock()) {
               int realcd;
               if ((flags & 4) == 0) {
                  if ((realcd = this.rcv_place.reserve_cd(intransaction)) == -1) {
                     if (traceEnabled) {
                        ntrace.doTrace("*]/tsession/tpacall/50/");
                     }

                     throw new TPException(5);
                  }
               } else {
                  realcd = -1;
               }

               cd = new SessionAcallDescriptor(realcd, false);

               try {
                  synchronized(this.wsh_ostream) {
                     tmmsg.write_tfmh(this.wsh_ostream, this.cmplimit);
                  }
               } catch (IOException var23) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/tsession/tpacall/60/");
                  }

                  throw new TPException(12, "tpacall network send error: " + var23);
               }

               tfmh tmmsg_in;
               if ((tmmsg_in = this.rcv_place.getlist(wskey, true)) == null) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/tsession/tpacall/70/");
                  }

                  throw new TPException(12, "Could not get reply");
               }

               WsTcb tmmsg_ws_in = (WsTcb)tmmsg_in.ws.body;
               if (tmmsg_ws_in.get_ws_rtn_val() == -1) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/tsession/tpacall/80/");
                  }

                  throw new TPException(tmmsg_ws_in.get_ws_TPException(), tmmsg_ws_in.get_ws_Uunixerr(), tmmsg_ws_in.get_ws_tpurcode(), tmmsg_ws_in.get_ws_tperrordetail());
               }

               int thandle = tmmsg_ws_in.get_HANDLE();
               if ((flags & 4) == 0) {
                  wskey.convert_to_AREPLY(thandle, realcd);
                  if (this.rcv_place.make_cd(cd.getCd(), wskey) == -1) {
                     if (traceEnabled) {
                        ntrace.doTrace("*]/tsession/tpacall/90/");
                     }

                     throw new TPException(12, "Unable to set certificate descriptor index");
                  }
               }
            }

            if (traceEnabled) {
               ntrace.doTrace("]/tsession/tpacall/100/" + cd);
            }

            return cd;
         } else {
            if (traceEnabled) {
               ntrace.doTrace("*]/tsession/tpacall/40/");
            }

            throw new TPException(4);
         }
      }
   }

   public void tpcancel(CallDescriptor cd, int flags) throws TPException {
      throw new TPException(9, "tpcancel not yet implemented");
   }

   public Reply tpgetrply(CallDescriptor cd, int flags) throws TPException, TPReplyException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/tsession/tpgetrply/" + cd + "/" + flags);
      }

      tfmh tmmsg_in = null;
      if (!this.isinited) {
         if (traceEnabled) {
            ntrace.doTrace("*]/tsession/tpgetrply/10/");
         }

         throw new TPException(9, "Must init before tpgetrply");
      } else {
         if (this.rcv_place.get_is_term()) {
            this._wsc_drop();
         }

         if (this.isterm) {
            if (traceEnabled) {
               ntrace.doTrace("*]/tsession/tpgetrply/20/");
            }

            throw new TPException(9, "Tuxedo session has been terminated");
         } else if (cd != null && cd instanceof SessionAcallDescriptor) {
            int realcd = ((SessionAcallDescriptor)cd).getCd();
            if (realcd >= -1 && realcd < 50 && (flags & -162) == 0) {
               if (realcd == -1 && (flags & 128) == 0) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/tsession/tpgetrply/40/");
                  }

                  throw new TPException(4);
               } else if (realcd != -1 && (flags & 128) != 0) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/tsession/tpgetrply/50/");
                  }

                  throw new TPException(4);
               } else {
                  boolean block = (flags & 1) == 0;
                  WsKey wskey;
                  if (realcd != -1) {
                     if ((wskey = this.rcv_place.get_wskey(realcd)) == null) {
                        if (traceEnabled) {
                           ntrace.doTrace("*]/tsession/tpgetrply/60/");
                        }

                        throw new TPException(4);
                     }

                     tmmsg_in = this.rcv_place.getlist(wskey, block);
                  } else {
                     while((realcd = this.rcv_place.getany(block)) != -1) {
                        if ((wskey = this.rcv_place.get_wskey(realcd)) == null) {
                           realcd = -1;
                        }

                        if ((tmmsg_in = this.rcv_place.getlist(wskey, false)) == null) {
                           realcd = -1;
                        }

                        if (!block || realcd != -1) {
                           break;
                        }
                     }

                     cd = new SessionAcallDescriptor(realcd, false);
                  }

                  if (tmmsg_in == null) {
                     if (block) {
                        if (traceEnabled) {
                           ntrace.doTrace("*]/tsession/tpgetrply/70/");
                        }

                        throw new TPException(12, "Connection aborted by WSH");
                     } else {
                        if (traceEnabled) {
                           ntrace.doTrace("*]/tsession/tpgetrply/80/");
                        }

                        throw new TPException(3);
                     }
                  } else {
                     this.rcv_place.clear_wskey(realcd);
                     WsTcb ws = (WsTcb)tmmsg_in.ws.body;
                     int myTPException = ws.get_ws_TPException();
                     int myUunixerr = ws.get_ws_Uunixerr();
                     int mytpurcode = ws.get_ws_tpurcode();
                     int mytperrordetail = ws.get_ws_tperrordetail();
                     if (ws.get_ws_rtn_val() == -1 && myTPException != 11 && myTPException != 10) {
                        if (traceEnabled) {
                           ntrace.doTrace("*]/tsession/tpgetrply/90/");
                        }

                        throw new TPException(myTPException);
                     } else {
                        if (myTPException != 11 && myTPException != 10) {
                           myTPException = 0;
                        }

                        TypedBuffer tb;
                        if (tmmsg_in.user == null) {
                           tb = null;
                        } else {
                           UserTcb utcb = (UserTcb)tmmsg_in.user.body;
                           tb = utcb.user_data;
                        }

                        TuxedoReply ret = new TuxedoReply(tb, mytpurcode, (CallDescriptor)cd);
                        if (myTPException != 0) {
                           if (traceEnabled) {
                              ntrace.doTrace("*]/tsession/tpgetrply/100/throws " + myTPException);
                           }

                           throw new TPReplyException(myTPException, myUunixerr, mytpurcode, mytperrordetail, ret);
                        } else {
                           if (traceEnabled) {
                              ntrace.doTrace("]/tsession/tpgetrply/110/" + ret);
                           }

                           return ret;
                        }
                     }
                  }
               }
            } else {
               if (traceEnabled) {
                  ntrace.doTrace("*]/tsession/tpgetrply/30/");
               }

               throw new TPException(4);
            }
         } else {
            if (traceEnabled) {
               ntrace.doTrace("*]/tsession/tpgetrply/25/");
            }

            throw new TPException(4, "cd has invalid type");
         }
      }
   }

   private tfmh _tpcall_internal(String svc, tfmh tmmsg, int flags) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/tsession/_tpcall_internal/" + svc + "/" + tmmsg + "/" + flags);
      }

      WsTcb tmmsg_ws;
      tcm ws;
      WsKey wskey;
      if (tmmsg.ws == null) {
         tmmsg_ws = this.alloc_WS(5, svc);
         tmmsg_ws.set_FLAG(flags);
         tmmsg_ws.set_PRIO(this.tmsndprio);
         this.tmsndprio = 50;
         ws = new tcm((short)3, tmmsg_ws);
         tmmsg.ws = ws;
         wskey = tmmsg_ws.get_key();
      } else {
         ws = tmmsg.ws;
         tmmsg_ws = (WsTcb)ws.body;
         wskey = tmmsg_ws.get_key();
      }

      try {
         synchronized(this.wsh_ostream) {
            if (this.srv_protocol <= 46) {
               tmmsg.write_ws_65_tfmh(this.wsh_ostream, this.cmplimit, 0, (TPINIT)null);
            } else {
               tmmsg.write_tfmh(this.wsh_ostream, this.cmplimit);
            }
         }
      } catch (IOException var12) {
         if (traceEnabled) {
            ntrace.doTrace("*]/tsession/_tpcall_internal/10/");
         }

         throw new TPException(12, "_tpcall_internal network send error: " + var12);
      }

      tfmh tmmsg_in;
      if ((tmmsg_in = this.rcv_place.getlist(wskey, true)) == null) {
         if (traceEnabled) {
            ntrace.doTrace("*]/tsession/_tpcall_internal/20/");
         }

         throw new TPException(12, "_tpacall_internal Could not get reply");
      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/tsession/_tpcall_internal/30/" + tmmsg_in);
         }

         return tmmsg_in;
      }
   }

   public Reply tpcall(String svc, TypedBuffer data, int flags) throws TPException, TPReplyException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/tsession/tpcall/" + svc + "/" + data + "/" + flags);
      }

      TypedBuffer tb = null;
      if (!this.isinited) {
         if (traceEnabled) {
            ntrace.doTrace("*]/tsession/tpcall/10/");
         }

         throw new TPException(9, "Must init before tpcall");
      } else {
         if (this.rcv_place.get_is_term()) {
            this._wsc_drop();
         }

         if (this.isterm) {
            if (traceEnabled) {
               ntrace.doTrace("*]/tsession/tpcall/20/");
            }

            throw new TPException(9, "Tuxedo session has been terminated");
         } else if ((flags & -42) != 0) {
            if (traceEnabled) {
               ntrace.doTrace("*]/tsession/tpcall/30/");
            }

            throw new TPException(4);
         } else if (svc != null && !svc.equals("")) {
            tfmh tmmsg;
            if (data == null) {
               tmmsg = new tfmh(0);
            } else {
               tcm user = new tcm((short)0, new UserTcb(data));
               tmmsg = new tfmh(data.getHintIndex(), user, 0);
            }

            tfmh tmmsg_in;
            if ((tmmsg_in = this._tpcall_internal(svc, tmmsg, flags)) == null) {
               this._tmmarkabort();
               if (traceEnabled) {
                  ntrace.doTrace("*]/tsession/tpcall/50/");
               }

               throw new TPException(12, "tpcall got invalid return");
            } else {
               WsTcb tmmsg_ws_in;
               if ((tmmsg_ws_in = (WsTcb)tmmsg_in.ws.body) == null) {
                  this._tmmarkabort();
                  if (traceEnabled) {
                     ntrace.doTrace("*]/tsession/tpcall/60/");
                  }

                  throw new TPException(12, "Could not find WS TCM");
               } else {
                  int myTPException = tmmsg_ws_in.get_ws_TPException();
                  int myUunixerr = tmmsg_ws_in.get_ws_Uunixerr();
                  int mytpurcode = tmmsg_ws_in.get_ws_tpurcode();
                  int mytperrordetail = tmmsg_ws_in.get_ws_tperrordetail();
                  if (tmmsg_ws_in.get_ws_rtn_val() == -1) {
                     this._tmmarkabort();
                     if (myTPException != 11 && myTPException != 10) {
                        if (traceEnabled) {
                           ntrace.doTrace("*]/tsession/tpcall/70/");
                        }

                        throw new TPException(myTPException, myUunixerr, mytpurcode, mytperrordetail);
                     }
                  }

                  if (myTPException != 11 && myTPException != 10) {
                     myTPException = 0;
                  }

                  if (tmmsg_in.user != null) {
                     tb = ((UserTcb)tmmsg_in.user.body).user_data;
                  }

                  TuxedoReply rtn = new TuxedoReply(tb, mytpurcode, (CallDescriptor)null);
                  if (myTPException != 0) {
                     if (traceEnabled) {
                        ntrace.doTrace("*]/tsession/tpcall/80/" + rtn);
                     }

                     throw new TPReplyException(myTPException, myUunixerr, mytpurcode, mytperrordetail, rtn);
                  } else {
                     if (traceEnabled) {
                        ntrace.doTrace("]/tsession/tpcall/90/" + rtn);
                     }

                     return rtn;
                  }
               }
            }
         } else {
            if (traceEnabled) {
               ntrace.doTrace("*]/tsession/tpcall/40/");
            }

            throw new TPException(4);
         }
      }
   }

   public synchronized void tpbegin(long timeout, int flags) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/tsession/tpbegin/" + timeout + "/" + flags);
      }

      if (flags != 0) {
         if (traceEnabled) {
            ntrace.doTrace("*]/tsession/tpbegin/10/");
         }

         throw new TPException(4);
      } else if (!this.isinited) {
         if (traceEnabled) {
            ntrace.doTrace("*]/tsession/tpbegin/20/");
         }

         throw new TPException(9, "Must init before tpbegin");
      } else {
         if (this.rcv_place.get_is_term()) {
            this._wsc_drop();
         }

         if (this.isterm) {
            if (traceEnabled) {
               ntrace.doTrace("*]/tsession/tpbegin/30/");
            }

            throw new TPException(9, "Tuxedo session has been terminated");
         } else if ((this.wsflags & 8) != 0) {
            if (traceEnabled) {
               ntrace.doTrace("*]/tsession/tpbegin/40/");
            }

            throw new TPException(9, "Already in a transaction");
         } else {
            if (timeout > 2147483647L || timeout == 0L) {
               timeout = 2147483647L;
            }

            this.wsflags |= 72;
            this.txtime = timeout;
            this.transtart = System.currentTimeMillis() / 1000L;
            if (traceEnabled) {
               ntrace.doTrace("[/tsession/tpbegin/50/");
            }

         }
      }
   }

   public synchronized void tpabort(int flags) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/tsession/tpabort/" + flags);
      }

      if (flags != 0) {
         if (traceEnabled) {
            ntrace.doTrace("*]/tsession/tpabort/10/");
         }

         throw new TPException(4);
      } else if (!this.isinited) {
         if (traceEnabled) {
            ntrace.doTrace("*]/tsession/tpabort/20/");
         }

         throw new TPException(9, "Must init before tpabort");
      } else {
         if (this.rcv_place.get_is_term()) {
            this._wsc_drop();
         }

         if (this.isterm) {
            if (traceEnabled) {
               ntrace.doTrace("*]/tsession/tpabort/30/");
            }

            throw new TPException(9, "Tuxedo session has been terminated");
         } else if ((this.wsflags & 8) == 0) {
            if (traceEnabled) {
               ntrace.doTrace("*]/tsession/tpabort/40/");
            }

            throw new TPException(9, "Not in a transaction");
         } else {
            this.wsflags &= -49225;
            this.txtime = 0L;
            this.rcv_place.mkstale();
            WsTcb tmmsg_ws = this.alloc_WS(1);
            tmmsg_ws.set_FLAG(flags);
            tcm ws = new tcm((short)3, tmmsg_ws);
            WsKey wskey = tmmsg_ws.get_key();
            tfmh tmmsg = new tfmh(0);
            tmmsg.ws = ws;

            try {
               synchronized(this.wsh_ostream) {
                  tmmsg.write_tfmh(this.wsh_ostream, this.cmplimit);
               }
            } catch (IOException var12) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/tsession/tpabort/50/");
               }

               throw new TPException(12, "tpabort network send error: " + var12);
            }

            tfmh tmmsg_in;
            if ((tmmsg_in = this.rcv_place.getlist(wskey, true)) == null) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/tsession/tpabort/60/");
               }

               throw new TPException(12, "Could not get reply to abort request");
            } else {
               WsTcb tmmsg_ws_in = (WsTcb)tmmsg_in.ws.body;
               if (tmmsg_ws_in.get_ws_rtn_val() == -1) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/tsession/tpabort/70/");
                  }

                  throw new TPException(tmmsg_ws_in.get_ws_TPException(), tmmsg_ws_in.get_ws_Uunixerr(), tmmsg_ws_in.get_ws_tpurcode(), tmmsg_ws_in.get_ws_tperrordetail());
               } else {
                  if (traceEnabled) {
                     ntrace.doTrace("]/tsession/tpabort/80/");
                  }

               }
            }
         }
      }
   }

   public synchronized void tpcommit(int flags) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/tsession/tpcommit/" + flags);
      }

      if (flags != 0) {
         if (traceEnabled) {
            ntrace.doTrace("*]/tsession/tpcommit/10/");
         }

         throw new TPException(4);
      } else if (!this.isinited) {
         if (traceEnabled) {
            ntrace.doTrace("*]/tsession/tpcommit/20/");
         }

         throw new TPException(9, "Must init before tpcommit");
      } else {
         if (this.rcv_place.get_is_term()) {
            this._wsc_drop();
         }

         if (this.isterm) {
            if (traceEnabled) {
               ntrace.doTrace("*]/tsession/tpcommit/30/");
            }

            throw new TPException(9, "Tuxedo session has been terminated");
         } else if ((this.wsflags & 8) == 0) {
            if (traceEnabled) {
               ntrace.doTrace("*]/tsession/tpcommit/40/");
            }

            throw new TPException(9, "Not in a transaction");
         } else {
            if (this.rcv_place.mkstale() > 0) {
               this.wsflags |= 32768;
            }

            if ((this.wsflags & '쀀') != 0) {
               this.tpabort(0);
               if (traceEnabled) {
                  ntrace.doTrace("]/tsession/tpcommit/50/");
               }

            } else {
               this.wsflags &= -9;
               this.txtime = 0L;
               if ((this.wsflags & 64) != 0) {
                  this.wsflags &= -65;
                  if (traceEnabled) {
                     ntrace.doTrace("]/tsession/tpcommit/60/");
                  }

               } else {
                  WsTcb tmmsg_ws = this.alloc_WS(7);
                  tmmsg_ws.set_FLAG(flags);
                  tcm ws = new tcm((short)3, tmmsg_ws);
                  TranTcb tmmsg_tran = new TranTcb();
                  tmmsg_tran.set_logged(true);
                  tcm tran = new tcm((short)2, tmmsg_tran);
                  WsKey wskey = tmmsg_ws.get_key();
                  tfmh tmmsg = new tfmh(0);
                  tmmsg.ws = ws;
                  tmmsg.tran = tran;

                  try {
                     synchronized(this.wsh_ostream) {
                        tmmsg.write_tfmh(this.wsh_ostream, this.cmplimit);
                     }
                  } catch (IOException var14) {
                     if (traceEnabled) {
                        ntrace.doTrace("*]/tsession/tpcommit/70/");
                     }

                     throw new TPException(12, "tpabort network send error: " + var14);
                  }

                  tfmh tmmsg_in;
                  if ((tmmsg_in = this.rcv_place.getlist(wskey, true)) == null) {
                     if (traceEnabled) {
                        ntrace.doTrace("*]/tsession/tpcommit/80/");
                     }

                     throw new TPException(12, "Could not get reply to commit request");
                  } else {
                     WsTcb tmmsg_ws_in = (WsTcb)tmmsg_in.ws.body;
                     if (tmmsg_ws_in.get_ws_rtn_val() == -1) {
                        if (traceEnabled) {
                           ntrace.doTrace("*]/tsession/tpcommit/90/");
                        }

                        throw new TPException(tmmsg_ws_in.get_ws_TPException(), tmmsg_ws_in.get_ws_Uunixerr(), tmmsg_ws_in.get_ws_tpurcode(), tmmsg_ws_in.get_ws_tperrordetail());
                     } else {
                        if (traceEnabled) {
                           ntrace.doTrace("]/tsession/tpcommit/100/");
                        }

                     }
                  }
               }
            }
         }
      }
   }

   public byte[] tpenqueue(String qspace, String qname, EnqueueRequest ctl, TypedBuffer data, int flags) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/tsession/tpenqueue/" + qspace + "/" + qname + "/" + ctl + "/" + data + "/" + flags);
      }

      int retDiagnostic = 0;
      if (!this.isinited) {
         if (traceEnabled) {
            ntrace.doTrace("*]/tsession/tpenqueue/10/");
         }

         throw new TPException(9, "Must init before tpenqueue");
      } else {
         if (this.rcv_place.get_is_term()) {
            this._wsc_drop();
         }

         if (this.isterm) {
            if (traceEnabled) {
               ntrace.doTrace("*]/tsession/tpenqueue/20/");
            }

            throw new TPException(9, "Tuxedo session has been terminated");
         } else if ((flags & -42) == 0 && qspace != null && qname != null && qspace.length() != 0 && qname.length() != 0 && ctl != null) {
            int opcode = 1;
            if (ctl.getexp_time() != null) {
               opcode |= 65536;
            } else if ((ctl.getdelivery_qos() & 4) != 0) {
               opcode |= 65536;
            } else if ((ctl.getreply_qos() & 4) != 0) {
               opcode |= 65536;
            }

            ComposHdrTcb tmmsg_compos_hdr = new ComposHdrTcb(opcode, ctl.geturcode());
            tcm compos_hdr = new tcm((short)5, tmmsg_compos_hdr);
            ComposFmlTcb tmmsg_compos_fml = new ComposFmlTcb(qname, ctl);
            tcm compos_fml = new tcm((short)6, tmmsg_compos_fml);
            tfmh tmmsg;
            if (data == null) {
               tmmsg = new tfmh(0);
            } else {
               tcm user = new tcm((short)0, new UserTcb(data));
               tmmsg = new tfmh(data.getHintIndex(), user, 0);
            }

            tmmsg.set_TPENQUEUE(true);
            tmmsg.compos_hdr = compos_hdr;
            tmmsg.compos_fml = compos_fml;
            tfmh tmmsg_in;
            if ((tmmsg_in = this._tpcall_internal(qspace, tmmsg, flags)) == null) {
               this._tmmarkabort();
               if (traceEnabled) {
                  ntrace.doTrace("*]/tsession/tpenqueue/40/");
               }

               throw new TPException(12, "tpenqueue got invalid return from _tpcall_internal");
            } else if (tmmsg_in.compos_hdr != null && tmmsg_in.compos_fml != null) {
               WsTcb tmmsg_ws_in;
               if ((tmmsg_ws_in = (WsTcb)tmmsg_in.ws.body) == null) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/tsession/tpenqueue/60/");
                  }

                  throw new TPException(12, "Could not find WS TCM");
               } else {
                  ComposFmlTcb tmmsg_compos_fml_in = (ComposFmlTcb)tmmsg_in.compos_fml.body;
                  Integer diagnostic = tmmsg_compos_fml_in.getDiagnostic();
                  int tperrno = tmmsg_ws_in.get_ws_TPException();
                  if (tmmsg_ws_in.get_ws_rtn_val() == -1) {
                     if (tperrno == 24) {
                        if (diagnostic == null) {
                           retDiagnostic = -7;
                        } else {
                           retDiagnostic = diagnostic;
                        }
                     }

                     if (traceEnabled) {
                        ntrace.doTrace("*]/tsession/tpenqueue/70/");
                     }

                     throw new TPException(tperrno, tmmsg_ws_in.get_ws_Uunixerr(), tmmsg_ws_in.get_ws_tpurcode(), tmmsg_ws_in.get_ws_tperrordetail(), retDiagnostic);
                  } else {
                     byte[] retval = tmmsg_compos_fml_in.getMsgid();
                     if (traceEnabled) {
                        ntrace.doTrace("]/tsession/tpenqueue/80/" + retval.length);
                     }

                     return retval;
                  }
               }
            } else {
               this._tmmarkabort();
               if (traceEnabled) {
                  ntrace.doTrace("*]/tsession/tpenqueue/50/");
               }

               throw new TPException(12, "tpenqueue could not get queue information");
            }
         } else {
            this._tmmarkabort();
            if (traceEnabled) {
               ntrace.doTrace("*]/tsession/tpenqueue/30/");
            }

            throw new TPException(4);
         }
      }
   }

   public DequeueReply tpdequeue(String qspace, String qname, byte[] msgid, byte[] corrid, boolean doWait, boolean doPeek, int flags) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/tsession/tpdequeue/" + qspace + "/" + qname + "/" + Utilities.prettyByteArray(msgid) + "/" + Utilities.prettyByteArray(corrid) + "//" + doWait + "/" + doPeek + "/" + flags);
      }

      TypedBuffer tb = null;
      int retDiagnostic = 0;
      if (!this.isinited) {
         if (traceEnabled) {
            ntrace.doTrace("*]/tsession/tpdequeue/10/");
         }

         throw new TPException(9, "Must init before tpenqueue");
      } else {
         if (this.rcv_place.get_is_term()) {
            this._wsc_drop();
         }

         if (this.isterm) {
            if (traceEnabled) {
               ntrace.doTrace("*]/tsession/tpdequeue/20/");
            }

            throw new TPException(9, "Tuxedo session has been terminated");
         } else if ((flags & -42) == 0 && qspace != null && qname != null && qspace.length() != 0 && qname.length() != 0) {
            if (doPeek) {
               flags |= 8;
            }

            ComposHdrTcb tmmsg_compos_hdr = new ComposHdrTcb(2, 0);
            tcm compos_hdr = new tcm((short)5, tmmsg_compos_hdr);
            ComposFmlTcb tmmsg_compos_fml = new ComposFmlTcb(qname, msgid, corrid, doWait, doPeek);
            tcm compos_fml = new tcm((short)6, tmmsg_compos_fml);
            tfmh tmmsg = new tfmh(0);
            tmmsg.compos_hdr = compos_hdr;
            tmmsg.compos_fml = compos_fml;
            tfmh tmmsg_in;
            if ((tmmsg_in = this._tpcall_internal(qspace, tmmsg, flags)) == null) {
               this._tmmarkabort();
               if (traceEnabled) {
                  ntrace.doTrace("*]/tsession/tpdequeue/40/");
               }

               throw new TPException(12, "tpdequeue got invalid return from _tpcall_internal");
            } else if (tmmsg_in.compos_hdr != null && tmmsg_in.compos_fml != null) {
               WsTcb tmmsg_ws_in;
               if ((tmmsg_ws_in = (WsTcb)tmmsg_in.ws.body) == null) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/tsession/tpdequeue/60/");
                  }

                  throw new TPException(12, "Could not find WS TCM");
               } else {
                  ComposFmlTcb tmmsg_compos_fml_in = (ComposFmlTcb)tmmsg_in.compos_fml.body;
                  ComposHdrTcb tmmsg_compos_hdr_in = (ComposHdrTcb)tmmsg_in.compos_hdr.body;
                  int tperrno = tmmsg_ws_in.get_ws_TPException();
                  Integer diagnostic = tmmsg_compos_fml_in.getDiagnostic();
                  if (tmmsg_ws_in.get_ws_rtn_val() == -1) {
                     if (tperrno == 24) {
                        if (diagnostic == null) {
                           retDiagnostic = -7;
                        } else {
                           retDiagnostic = diagnostic;
                        }
                     }

                     if (traceEnabled) {
                        ntrace.doTrace("*]/tsession/tpdequeue/70/");
                     }

                     throw new TPException(tperrno, tmmsg_ws_in.get_ws_Uunixerr(), tmmsg_ws_in.get_ws_tpurcode(), tmmsg_ws_in.get_ws_tperrordetail(), retDiagnostic);
                  } else {
                     int tpurcode = tmmsg_ws_in.get_ws_tpurcode();
                     tmmsg_compos_fml_in = (ComposFmlTcb)tmmsg_in.compos_fml.body;
                     if (tmmsg_in.user != null) {
                        tb = ((UserTcb)tmmsg_in.user.body).user_data;
                     }

                     DequeueReply retval = new DequeueReply(tb, tpurcode, (CallDescriptor)null, tmmsg_compos_fml_in.getMsgid(), tmmsg_compos_fml_in.getCoorid(), tmmsg_compos_fml_in.getReplyQueue(), tmmsg_compos_fml_in.getFailureQueue(), new Integer(tmmsg_compos_hdr_in.getAppkey()), tmmsg_compos_fml_in.getPriority(), tmmsg_compos_fml_in.getDeliveryQualityOfService(), tmmsg_compos_fml_in.getReplyQualityOfService(), tmmsg_compos_hdr_in.getUrcode());
                     if (traceEnabled) {
                        ntrace.doTrace("*]/tsession/tpdequeue/80/" + retval);
                     }

                     return retval;
                  }
               }
            } else {
               this._tmmarkabort();
               if (traceEnabled) {
                  ntrace.doTrace("*]/tsession/tpdequeue/50/");
               }

               throw new TPException(12, "tpdequeue could not get queue information");
            }
         } else {
            this._tmmarkabort();
            if (traceEnabled) {
               ntrace.doTrace("*]/tsession/tpdequeue/30/");
            }

            throw new TPException(4);
         }
      }
   }

   public DequeueReply tpdequeue(String qspace, String qname, int flags) throws TPException {
      return this.tpdequeue(qspace, qname, (byte[])null, (byte[])null, false, false, flags);
   }

   public Conversation tpconnect(String svc, TypedBuffer data, int flags) throws TPException {
      throw new TPException(4, "Conversations not yet supported");
   }

   public int getSrvProtocol() {
      return this.srv_protocol;
   }

   public void tpsprio(int prio, int flags) throws TPException {
      if ((flags & -65) != 0) {
         throw new TPException(4, "Bad flags value");
      } else {
         if ((flags & 64) != 0) {
            if (prio >= 1 && prio <= 100) {
               this.tmsndprio = prio;
            } else {
               this.tmsndprio = 50;
            }
         } else if (prio > 100) {
            this.tmsndprio = 100;
         } else if (prio < 1) {
            this.tmsndprio = 1;
         } else {
            this.tmsndprio = prio;
         }

      }
   }
}
