package weblogic.wtc.gwt;

import com.bea.core.jatmi.common.ntrace;
import com.bea.core.jatmi.internal.TCLicenseManager;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Timer;
import weblogic.wtc.WTCLogger;
import weblogic.wtc.jatmi.EngineSecError;
import weblogic.wtc.jatmi.InvokeSvc;
import weblogic.wtc.jatmi.PasswordUtils;
import weblogic.wtc.jatmi.TPException;
import weblogic.wtc.jatmi.TdomTcb;
import weblogic.wtc.jatmi.TuxXidRply;
import weblogic.wtc.jatmi.TypedCArray;
import weblogic.wtc.jatmi.UserTcb;
import weblogic.wtc.jatmi.atn;
import weblogic.wtc.jatmi.atncredtdom;
import weblogic.wtc.jatmi.atnctxtdom;
import weblogic.wtc.jatmi.atntdom65;
import weblogic.wtc.jatmi.dsession;
import weblogic.wtc.jatmi.rdsession;
import weblogic.wtc.jatmi.tcm;
import weblogic.wtc.jatmi.tfmh;
import weblogic.wtc.jatmi.tplle;

public final class gwdsession extends dsession {
   public gwdsession(Timer tsd, InetAddress ip, int port, int myUid, TuxXidRply anXidRply) {
      super(tsd, ip, port, myUid, anXidRply, WTCService.canUseBetaFeatures());
   }

   public gwdsession(Timer tsd, InetAddress ip, int port, atn gssimpl, int myUid, TuxXidRply anXidRply) {
      super(tsd, ip, port, gssimpl, myUid, anXidRply, WTCService.canUseBetaFeatures());
   }

   public gwdsession(Timer tsd, InetAddress[] ip, int[] port, atn gssimpl, InvokeSvc invoke, int myUid, TuxXidRply anXidRply) {
      super(tsd, ip, port, gssimpl, invoke, myUid, anXidRply, WTCService.canUseBetaFeatures());
   }

   public gwdsession(Timer tsd, InetAddress ip, int port, atn gssimpl, InvokeSvc invoke, int myUid, TuxXidRply anXidRply) {
      super(tsd, ip, port, gssimpl, invoke, myUid, anXidRply, WTCService.canUseBetaFeatures());
   }

   public gwdsession(Timer tsd, Socket tdom_socket, atn gssimpl, InvokeSvc invoke, int myUid, boolean sslEnabled, TuxXidRply anXidRply) throws IOException {
      super(tsd, tdom_socket, gssimpl, invoke, myUid, anXidRply, sslEnabled, WTCService.canUseBetaFeatures());
   }

   private TDMRemote do_accept(TDMRemote[] rdoms) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/gwdsession/do_accept/");
      }

      TDMRemote found_rdom = null;
      dsession exist_session = null;
      byte[] send_buf = null;
      byte[] recv_buf = null;
      int opcode = 0;
      int more_work = true;
      atncredtdom mygsscred = null;
      atnctxtdom mygssctx = null;
      TypedCArray myuserdata = null;
      tcm user = null;
      boolean postpone_drop = false;
      WTCService service = WTCService.getWTCService();
      DataOutputStream dom_ostream = this.get_output_stream();
      DataInputStream dom_istream = this.get_input_stream();
      tfmh initmsg = new tfmh(1);

      String ldn;
      try {
         if (initmsg.read_dom_65_tfmh(dom_istream, 10) != 0) {
            if (traceEnabled) {
               ntrace.doTrace("*]/gwdsession/do_accept/10/");
            }

            throw new TPException(4, "Could not read message from remote domain");
         }

         TdomTcb inittdom = (TdomTcb)initmsg.tdom.body;
         if (inittdom.get_opcode() != 14) {
            if (traceEnabled) {
               ntrace.doTrace("*]/gwdsession/do_accept/20/");
            }

            throw new TPException(4, "Invalid opcode");
         }

         int protocol = inittdom.get_dom_protocol();
         this.setInProtocol(protocol);
         this.setSessionFeatures(inittdom.getFeaturesSupported());
         int o_release = protocol & 31;
         if ((o_release < 13 || o_release == 14) && (protocol & 2147483616 & 20) == 0) {
            if (traceEnabled) {
               ntrace.doTrace("*]/gwdsession/do_accept/30/");
            }

            throw new TPException(4, "ERROR: Protocol level " + protocol + " is not supported!");
         }

         String sending_domain = inittdom.get_sending_domain();

         int lcv;
         for(lcv = 0; lcv < rdoms.length; ++lcv) {
            if (sending_domain.equals(rdoms[lcv].getAccessPointId())) {
               found_rdom = rdoms[lcv];
            }
         }

         if (found_rdom == null) {
            if (traceEnabled) {
               ntrace.doTrace("*]/gwdsession/do_accept/50/");
            }

            throw new TPException(9, "Unknown remote domain " + sending_domain);
         }

         if ((exist_session = (dsession)found_rdom.getTsession(false)) != null) {
            if (traceEnabled) {
               ntrace.doTrace("*]/gwdsession/do_accept/60/");
            }

            if (!exist_session.getIAddress().equals(this.getIAddress())) {
               throw new TPException(9, "Got a connection from a remote domain that I am already connected to: " + sending_domain);
            }

            if (this.get_sess_sec() == 0) {
               exist_session._dom_drop();
            } else {
               postpone_drop = true;
            }
         }

         if (!postpone_drop) {
            found_rdom.setTsession(this);
         }

         TDMLocalTDomain ldom = (TDMLocalTDomain)found_rdom.getLocalAccessPointObject();
         if (protocol <= 13 && !ldom.isInteroperate()) {
            if (traceEnabled) {
               ntrace.doTrace("*]/gwdsession/do_accept/65/");
            }

            throw new TPException(12, "Use Interoperate option to interoperate with sites older than Tuxedo 7.1");
         }

         ldn = ldom.getAccessPointId();
         String rdomname = found_rdom.getAccessPoint();
         String ldomname = ldom.getAccessPoint();
         TDMRemoteTDomain tdom = service.getRemoteTDomain(found_rdom.getAccessPointId());
         int eflags;
         if (!ldom.getMBean().getUseSSL().equals("TwoWay") && !ldom.getMBean().getUseSSL().equals("OneWay")) {
            eflags = TCLicenseManager.acceptEncryptionLevel(protocol, tdom.getMinEncryptBits(), tdom.getMaxEncryptBits(), inittdom.get_lle_flags());
         } else {
            eflags = TCLicenseManager.acceptEncryptionLevel(protocol, 0, 0, inittdom.get_lle_flags());
         }

         if (eflags == 0) {
            if (traceEnabled) {
               ntrace.doTrace("*]/gwdsession/do_accept/65/");
            }

            throw new TPException(4, "Link level encryption negotiation failure" + inittdom.get_lle_flags());
         }

         if (protocol >= 15) {
            this.setAclPolicy(tdom.getAclPolicy());
            this.setCredentialPolicy(tdom.getCredentialPolicy());
            this.setTpUserFile(tdom.getTpUsrFile());
            this.setAppKey(tdom.getAppKey());
            if (this.myAppKeySel != null) {
               if (this.myAppKeySel.equals("LDAP")) {
                  this.setUidKw(tdom.getTuxedoUidKw());
                  this.setGidKw(tdom.getTuxedoGidKw());
               } else {
                  this.setCustomAppKeyClass(tdom.getCustomAppKeyClass());
                  this.setCustomAppKeyClassParam(tdom.getCustomAppKeyClassParam());
               }
            }

            this.setAllowAnonymous(tdom.getAllowAnonymous());
            this.setDfltAppKey(tdom.getDefaultAppKey());
         }

         this.setRemoteDomainId(tdom.getAccessPointId());
         this.setKeepAlive(tdom.getKeepAlive());
         this.setKeepAliveWait(tdom.getKeepAliveWait());
         tfmh rplymsg = new tfmh(1);
         TdomTcb rplytdom = new TdomTcb(15, inittdom.get_reqid(), 0, (String)null);
         rplymsg.tdom = new tcm((short)7, rplytdom);
         this.set_local_domain_name(ldn);
         rplytdom.set_security_type(this.get_sess_sec());
         rplytdom.set_lle_flags(eflags);
         if (rplymsg.write_dom_65_tfmh(dom_ostream, ldn, 10, Integer.MAX_VALUE) != 0) {
            if (traceEnabled) {
               ntrace.doTrace("*]/gwdsession/do_accept/70/");
            }

            throw new TPException(9, "Could not get authorization parameters from remote domain");
         }

         this.setOutProtocol(protocol);
         if (traceEnabled) {
            ntrace.doTrace("...send ACALL1_RPLY to remote");
         }

         int send_size;
         int recv_size;
         tcm rcv_user;
         UserTcb rcv_tcb;
         TypedCArray tmp_ca;
         if (eflags != 1) {
            if (traceEnabled) {
               ntrace.doTrace("/gwdsession/do_accept/do LLE protocol");
            }

            if (protocol <= 13) {
               if (traceEnabled) {
                  ntrace.doTrace("/gwdsession/do_accept/use R65 release protocol");
               }

               if (rplymsg.read_dom_65_tfmh(dom_istream, 13) != 0) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/gwdsession/do_accept/71/");
                  }

                  throw new TPException(9, "Could not get authorization parameters from remote domain");
               }
            } else {
               if (traceEnabled) {
                  ntrace.doTrace("/gwdsession/do_accept/use R80 release protocol");
               }

               if (rplymsg.read_tfmh(dom_istream) != 0) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/gwdsession/do_accept/72/");
                  }

                  throw new TPException(9, "Could not get authorization parameters from remote domain");
               }

               rplytdom = (TdomTcb)rplymsg.tdom.body;
            }

            if (rplytdom.get_opcode() != 20) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/gwdsession/do_accept/73/");
               }

               throw new TPException(4, "Invalid opcode");
            }

            if (traceEnabled) {
               ntrace.doTrace("/gwdsession/do_accept/...recv LLE");
            }

            this.myLLE = new tplle();
            rcv_user = rplymsg.user;
            rcv_tcb = (UserTcb)rcv_user.body;
            tmp_ca = (TypedCArray)rcv_tcb.user_data;
            recv_buf = tmp_ca.carray;
            recv_size = recv_buf.length;
            if (traceEnabled) {
               ntrace.doTrace("recv size = " + recv_size);
            }

            lcv = -1;
            send_size = 2048;

            while(lcv < 0) {
               if (traceEnabled) {
                  ntrace.doTrace("/gwdsession/do_accept/lle buffer " + send_size);
               }

               send_buf = new byte[send_size];
               lcv = this.myLLE.crypKeyeTwo(eflags, recv_buf, send_buf, 0);
               if (lcv < 0) {
                  send_size = -lcv;
               }
            }

            if (lcv == 0) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/gwdsession/do_accept/72/");
               }

               throw new TPException(12, "Unable to generate second diffie-hellman packet");
            }

            rplytdom.setLLELength(send_size);
            rplytdom.setSendSecPDU(send_buf, send_size);
            myuserdata = new TypedCArray();
            user = new tcm((short)0, new UserTcb(myuserdata));
            myuserdata.carray = send_buf;
            rplymsg.user = user;
            myuserdata.setSendSize(lcv);
            rplytdom.set_opcode(21);
            if (protocol <= 13) {
               if (rplymsg.write_dom_65_tfmh(dom_ostream, ldn, 13, this.getCompressionThreshold()) != 0) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/gwdsession/do_accept/73/");
                  }

                  throw new TPException(12, "Unable to generate second diffie-hellman packet");
               }
            } else if (rplymsg.write_tfmh(dom_ostream, this.getCompressionThreshold()) != 0) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/gwdsession/do_accept/74/");
               }

               throw new TPException(12, "Unable to generate second diffie-hellman packet");
            }

            if (traceEnabled) {
               ntrace.doTrace("/gwdsession/do_accept/...send LLE_RPLY");
            }

            switch (this.myLLE.crypFinishTwo()) {
               case 3:
                  this.setELevel(1);
                  this.myLLE = null;
                  break;
               case 4:
                  this.setELevel(2);
                  break;
               case 5:
                  this.setELevel(32);
                  break;
               case 6:
                  this.setELevel(4);
                  break;
               default:
                  this.myLLE = null;
                  if (traceEnabled) {
                     ntrace.doTrace("*]/gwdsession/do_accept/74");
                  }

                  throw new TPException(12, "ERROR: Unexpected link level encryption failure");
            }

            this.setLLE();
         }

         if (this.get_sess_sec() != 0) {
            String lpasswd;
            String apasswd;
            String iv;
            String key;
            if (this.get_sess_sec() == 2) {
               TDMPasswd passwd = service.getTDMPasswd(ldomname, rdomname);
               iv = WTCService.getPasswordKey();
               key = WTCService.getEncryptionType();
               lpasswd = PasswordUtils.decryptPassword(iv, passwd.getLocalPasswordIV(), passwd.getLocalPassword(), key);
               apasswd = PasswordUtils.decryptPassword(iv, passwd.getRemotePasswordIV(), passwd.getRemotePassword(), key);
               if (lpasswd == null || apasswd == null) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/gwdsession/do_accept/72/");
                  }

                  throw new TPException(8, "Could not get the domain passwords");
               }

               this.setLocalPassword(lpasswd);
               this.setRemotePassword(apasswd);
            } else if (this.get_sess_sec() == 1) {
               String passwd = WTCService.getAppPasswordPWD();
               iv = WTCService.getAppPasswordIV();
               key = WTCService.getPasswordKey();
               lpasswd = WTCService.getEncryptionType();
               apasswd = PasswordUtils.decryptPassword(key, iv, passwd, lpasswd);
               if (apasswd == null) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/gwdsession/do_accept/73/");
                  }

                  throw new TPException(8, "Could not get the application passwords");
               }

               this.setApplicationPassword(apasswd);
            }

            Object mygss;
            if (this.dom_protocol <= 13) {
               mygss = new atntdom65(this.desired_name);
               ((atn)mygss).setTargetName(this.getRemoteDomainId());
            } else if (this.gssatn == null) {
               mygss = new atntdom80(this.desired_name);
            } else {
               mygss = this.gssatn;
            }

            ((atn)mygss).setSecurityType(this.security_type);
            ((atn)mygss).setSrcName(sending_domain);
            ((atn)mygss).setDesiredName(this.desired_name);
            if (this.security_type == 1) {
               ((atn)mygss).setApplicationPasswd(this.lpwd);
            } else {
               ((atn)mygss).setLocalPasswd(this.lpwd);
               ((atn)mygss).setRemotePasswd(this.rpwd);
            }

            if (this.dom_protocol >= 15) {
               myuserdata = new TypedCArray();
               user = new tcm((short)0, new UserTcb(myuserdata));
               if (this.myLLE != null) {
                  ((atn)mygss).setApplicationData(this.myLLE.getFingerprint());
               }
            }

            try {
               mygsscred = (atncredtdom)((atn)mygss).gssAcquireCred(this.desired_name, this.desired_name);
            } catch (EngineSecError var42) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/gwdsession/do_accept/90/");
               }

               throw new TPException(8, "Unable to acquire credentials <" + var42.errno + ">");
            }

            try {
               mygssctx = (atnctxtdom)((atn)mygss).gssGetContext(mygsscred, this.dom_target_name);
            } catch (EngineSecError var41) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/gwdsession/do_accept/100");
               }

               throw new TPException(8, "Unable to get security context <" + var41.errno + ">");
            }

            int more_work = 1;

            while(more_work > 0) {
               recv_size = ((atn)mygss).getEstimatedPDURecvSize(mygssctx);
               send_size = ((atn)mygss).getEstimatedPDUSendSize(mygssctx);
               if (send_size > 0) {
                  if (send_buf == null || send_buf.length < send_size) {
                     send_buf = new byte[send_size];
                  }

                  if (this.dom_protocol <= 13) {
                     switch (mygssctx.context_state) {
                        case 1:
                           opcode = 17;
                           break;
                        case 3:
                           opcode = 19;
                     }
                  }
               }

               if (recv_size > 0) {
                  if (this.dom_protocol > 13) {
                     if (rplymsg.read_tfmh(dom_istream) != 0) {
                        if (traceEnabled) {
                           ntrace.doTrace("*]/gwdsession/do_accept/120");
                        }

                        throw new TPException(4, "Could not receive security exchange from remote domain");
                     }

                     rcv_user = rplymsg.user;
                     rcv_tcb = (UserTcb)rcv_user.body;
                     tmp_ca = (TypedCArray)rcv_tcb.user_data;
                     recv_buf = tmp_ca.carray;
                     recv_size = recv_buf.length;
                     if (traceEnabled) {
                        ntrace.doTrace("recv size = " + recv_size);
                     }
                  } else {
                     if (recv_buf == null || recv_buf.length < recv_size) {
                        recv_buf = new byte[recv_size];
                        if (traceEnabled) {
                           ntrace.doTrace("/gwdsession/do_accept/recv size " + recv_size);
                        }
                     }

                     rplytdom.setRecvSecPDU(recv_buf, recv_size);
                     if (rplymsg.read_dom_65_tfmh(dom_istream, 10) != 0) {
                        if (traceEnabled) {
                           ntrace.doTrace("*]/gwdsession/do_accept/110");
                        }

                        throw new TPException(4, "Could not receive security exchange from remote domain");
                     }
                  }
               }

               try {
                  more_work = ((atn)mygss).gssAcceptSecContext(mygssctx, recv_buf, recv_size, send_buf);
               } catch (EngineSecError var44) {
                  if (var44.errno != -3005) {
                     if (traceEnabled) {
                        ntrace.doTrace("*]/gwdsession/do_accept/140/");
                     }

                     throw new TPException(8, "Security violation <" + var44.errno + ")");
                  }

                  send_buf = new byte[var44.needspace];

                  try {
                     more_work = ((atn)mygss).gssAcceptSecContext(mygssctx, recv_buf, recv_size, send_buf);
                  } catch (EngineSecError var43) {
                     if (traceEnabled) {
                        ntrace.doTrace("*]/gwdsession/do_accept/130/");
                     }

                     throw new TPException(8, "Security violation <" + var43.errno + ")");
                  }
               }

               if (more_work == -1) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/gwdsession/do_accept/145/");
                  }

                  throw new TPException(8, "Security violation");
               }

               if (send_size > 0) {
                  int actual_send_size = ((atn)mygss).getActualPDUSendSize();
                  rplytdom.setSendSecPDU(send_buf, actual_send_size);
                  if (this.dom_protocol <= 13) {
                     rplytdom.set_opcode(opcode);
                     if (rplymsg.write_dom_65_tfmh(dom_ostream, ldn, 10, this.getCompressionThreshold()) != 0) {
                        if (traceEnabled) {
                           ntrace.doTrace("*]/gwdsession/do_accept/150");
                        }

                        throw new TPException(4, "Could not send mesage to remote domain");
                     }
                  } else {
                     myuserdata.carray = send_buf;
                     rplymsg.user = user;
                     myuserdata.setSendSize(actual_send_size);
                     rplytdom.set_opcode(22);
                     if (rplymsg.write_tfmh(dom_ostream, this.getCompressionThreshold()) != 0) {
                        if (traceEnabled) {
                           ntrace.doTrace("*]/gwdsession/do_accept/160");
                        }

                        throw new TPException(4, "Could not send mesage to remote domain");
                     }
                  }
               }
            }
         } else {
            this.set_authtype(0);
         }

         if (postpone_drop) {
            found_rdom.setTsession(this);
            exist_session._dom_drop();
         }
      } catch (IOException var45) {
         if (traceEnabled) {
            ntrace.doTrace("*]/gwdsession/do_accept/80/");
         }

         throw new TPException(12, "Unable to get authentication level");
      }

      if (this.setUpTuxedoAAA() < 0) {
         if (traceEnabled) {
            ntrace.doTrace("*]/gwdsession/do_accept/85/");
         }

         throw new TPException(12, "Unable to setup authentication and auditing for Tuxedo");
      } else {
         rdsession myRecvSession = new rdsession(dom_ostream, this, this.get_invoker(), this.dom_protocol, ldn, this.get_TimeService(), this.getUnknownRplyObj(), WTCService.canUseBetaFeatures());
         myRecvSession.set_BlockTime(this.get_BlockTime());
         myRecvSession.setSessionReference(this);
         this.set_is_connected(true);
         this.set_rcv_place(myRecvSession);
         this.dmqDecision();
         WTCLogger.logInfoRemoteDomainConnected(this.getRemoteDomainId());
         if (traceEnabled) {
            ntrace.doTrace("]/gwdsession/do_accept/1000/success/" + found_rdom);
         }

         return found_rdom;
      }
   }

   public synchronized TDMRemote tpinit(TDMRemote[] rdoms) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/gwdsession/tpinit/");
      }

      TDMRemote ret = null;
      if (this.get_is_connected()) {
         if (traceEnabled) {
            ntrace.doTrace("*]/gwdsession/tpinit/10/");
         }

         throw new TPException(9, "Can not init object more than once");
      } else if (this.getIsTerminated()) {
         if (traceEnabled) {
            ntrace.doTrace("*]/dsession/tpinit/20/");
         }

         throw new TPException(9, "Domain session has been terminated");
      } else if (this.get_is_connector()) {
         if (traceEnabled) {
            ntrace.doTrace("*]/dsession/tpinit/30/");
         }

         throw new TPException(9, "We are connecting, not accepting");
      } else {
         ret = this.do_accept(rdoms);
         if (traceEnabled) {
            ntrace.doTrace("]/dsession/tpinit/30/" + ret);
         }

         return ret;
      }
   }
}
