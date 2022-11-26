package weblogic.wtc.gwt;

import com.bea.core.jatmi.common.ntrace;
import com.bea.core.jatmi.config.TuxedoConnectorRAP;
import com.bea.core.jatmi.internal.ConfigHelper;
import com.bea.core.jatmi.internal.TCTransactionHelper;
import com.bea.core.jatmi.internal.TuxedoXid;
import com.bea.core.jatmi.intf.TCTask;
import com.bea.core.jatmi.intf.TuxedoLoggable;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import weblogic.wtc.WTCLogger;
import weblogic.wtc.jatmi.MetaTcb;
import weblogic.wtc.jatmi.MetaTcmHelper;
import weblogic.wtc.jatmi.ReqXidMsg;
import weblogic.wtc.jatmi.ReqXidOid;
import weblogic.wtc.jatmi.TPException;
import weblogic.wtc.jatmi.TdomTcb;
import weblogic.wtc.jatmi.TdomTranTcb;
import weblogic.wtc.jatmi.TuxXidRply;
import weblogic.wtc.jatmi.Txid;
import weblogic.wtc.jatmi.dsession;
import weblogic.wtc.jatmi.gwatmi;
import weblogic.wtc.jatmi.tcm;
import weblogic.wtc.jatmi.tfmh;

class OatmialUnknownXidHandler implements TCTask {
   private TuxXidRply myRplyXidObj;
   private String myName;

   public OatmialUnknownXidHandler(TuxXidRply theRplyXidObj) {
      this.myRplyXidObj = theRplyXidObj;
   }

   public int execute() {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/OatmialUnknownXidHandler/execute/" + Thread.currentThread());
      }

      ReqXidMsg myReqXidMsg = null;
      tfmh tmmsg = null;
      TdomTcb tmmsg_tdom = null;
      TdomTranTcb tmmsg_tdomtran = null;
      tfmh new_tmmsg = null;
      TdomTcb new_tmmsg_tdom = null;
      TdomTranTcb new_tmmsg_tdomtran = null;
      int new_opcode = false;
      Txid myTxid = null;
      gwatmi myAtmi = null;
      TuxedoXid tuxedoXid = null;
      Xid importedXid = null;
      OatmialServices tos = WTCService.getOatmialServices();
      XAResource wlsXaResource = null;
      int addFlags = 0;
      TuxedoLoggable tl = null;
      ReqXidOid myOid = null;
      int rtn = true;
      WTCStatisticsRuntimeMBeanImpl myWTCStat = (WTCStatisticsRuntimeMBeanImpl)WTCService.getWTCService().getWTCStatisticsRuntimeMBean();
      myReqXidMsg = this.myRplyXidObj.get_reply(true);
      if ((tmmsg = myReqXidMsg.getReply()) == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/OatmialUnknownXidHandler/10/");
         }

         return 0;
      } else if (tmmsg.tdom == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/OatmialUnknownXidHandler/20/Invalid reply, no TDOM TCM/");
         }

         return 0;
      } else if (tmmsg.tdomtran == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/OatmialUnknownXidHandler/30/Invalid reply, no TDOM_TRAN TCM/");
         }

         return 0;
      } else {
         myOid = myReqXidMsg.getReqXidOid();
         if (myOid != null && (myTxid = myOid.getReqXidReturn()) != null) {
            if ((myAtmi = myOid.getAtmiObject()) == null) {
               if (traceEnabled) {
                  ntrace.doTrace("]/OatmialUnknownXidHandler/50/Invalid reply, no reply domain/");
               }

               return 0;
            } else {
               tmmsg_tdom = (TdomTcb)tmmsg.tdom.body;
               tmmsg_tdomtran = (TdomTranTcb)tmmsg.tdomtran.body;
               int opcode = tmmsg_tdom.get_opcode();
               if (traceEnabled) {
                  ntrace.doTrace("/OatmialUnknownXidHandler/opcode=" + TdomTcb.print_opcode(opcode));
               }

               if (tmmsg_tdomtran == null) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/OatmialUnknownXidHandler/60.1/null transaction");
                  }

                  return 0;
               } else {
                  if ((myAtmi.getSessionFeatures() & 4096) != 0 && tmmsg.meta != null) {
                     MetaTcb tcb = (MetaTcb)tmmsg.meta.body;
                     if (tcb != null) {
                        byte[] bqual = null;
                        String transactionParent;
                        if ((transactionParent = tmmsg_tdomtran.getNwtranidparent()) != null) {
                           bqual = transactionParent.getBytes();
                        }

                        importedXid = MetaTcmHelper.getImportedXid(tcb, bqual);
                        if (traceEnabled && importedXid != null) {
                           ntrace.doTrace("/OatmialUnknownXidHandler/execute/getImportedXid/" + importedXid.toString());
                        }
                     }
                  }

                  try {
                     tuxedoXid = new TuxedoXid(tmmsg_tdomtran);
                  } catch (TPException var34) {
                     if (traceEnabled) {
                        ntrace.doTrace("]/OatmialUnknownXidHandler/60/" + var34);
                     }

                     return 0;
                  }

                  if (importedXid != null) {
                     tuxedoXid.setImportedXid(importedXid);
                  }

                  TuxedoConnectorRAP[] associatedDomains;
                  if ((associatedDomains = tos.getInboundRdomsAssociatedWithXid(tuxedoXid)) != null && (wlsXaResource = TCTransactionHelper.getXAResource()) == null) {
                     if (traceEnabled) {
                        ntrace.doTrace("Could not get interposed mamager");
                     }

                     return 0;
                  } else {
                     int i;
                     TDMRemote rdom;
                     dsession ss;
                     byte new_opcode;
                     switch (opcode) {
                        case 7:
                           if (associatedDomains == null) {
                              new_opcode = 12;
                              if (traceEnabled) {
                                 ntrace.doTrace("new opcode ROLLBACK");
                              }
                           } else {
                              int rtn;
                              try {
                                 if (traceEnabled) {
                                    ntrace.doTrace("issue PREPARE, tuxedoXid = " + tuxedoXid);
                                 }

                                 rtn = wlsXaResource.prepare(tuxedoXid);
                              } catch (XAException var37) {
                                 new_opcode = 12;
                                 if (traceEnabled) {
                                    ntrace.doTrace("opcode ROLLBACK");
                                 }
                                 break;
                              }

                              if (rtn == 3) {
                                 if ((myAtmi.getSessionFeatures() & 16) != 0) {
                                    new_opcode = 24;
                                    if (traceEnabled) {
                                       ntrace.doTrace("opcode RDONLY");
                                    }
                                 } else {
                                    new_opcode = 8;
                                    if (traceEnabled) {
                                       ntrace.doTrace("opcode READY");
                                    }
                                 }
                              } else {
                                 new_opcode = 8;
                                 if (traceEnabled) {
                                    ntrace.doTrace("opcode READY");
                                 }
                              }

                              tl = ConfigHelper.removeXidTLogMap(tuxedoXid, 3);
                              if (tl == null) {
                                 tl = TCTransactionHelper.createTuxedoLoggable(tuxedoXid, 3);
                                 ConfigHelper.addXidTLogMap(tuxedoXid, tl);
                              }

                              tl.write();
                              tl.waitForDisk();
                              if (ntrace.getTraceLevel() == 1000372) {
                                 if (traceEnabled) {
                                    ntrace.doTrace("After prepared log, sleeping 30 seconds");
                                 }

                                 try {
                                    Thread.sleep(30000L);
                                 } catch (InterruptedException var33) {
                                 }

                                 if (traceEnabled) {
                                    ntrace.doTrace("Finished sleeping");
                                 }
                              }
                           }
                           break;
                        case 8:
                           TuxedoConnectorRAP[] rdoms = tos.getOutboundRdomsAssociatedWithXid(myTxid);
                           dsession sa = (dsession)myAtmi;
                           boolean isReady = false;
                           if (rdoms != null) {
                              for(int i = 0; i < rdoms.length; ++i) {
                                 if (traceEnabled) {
                                    ntrace.doTrace("*/OatmialUnknownXidHandler/" + rdoms[i].getAccessPointId() + "/" + sa.getRemoteDomainId());
                                 }

                                 if (rdoms[i].getAccessPointId().equals(sa.getRemoteDomainId())) {
                                    isReady = true;
                                    break;
                                 }
                              }
                           } else {
                              isReady = false;
                           }

                           if (isReady) {
                              if (traceEnabled) {
                                 ntrace.doTrace("ignore this unknow xid");
                              }

                              return 0;
                           }

                           new_opcode = 12;
                           if (traceEnabled) {
                              ntrace.doTrace("opcode ROLLBACK");
                           }
                           break;
                        case 9:
                           new_opcode = 10;
                           if (traceEnabled) {
                              ntrace.doTrace("opcode DONE");
                           }

                           if (associatedDomains != null) {
                              boolean one_pc = !tos.isXidInReadyMap(tuxedoXid);
                              if (traceEnabled) {
                                 ntrace.doTrace("ONE PC =" + one_pc);
                              }

                              if (!one_pc) {
                                 tl = TCTransactionHelper.createTuxedoLoggable(tuxedoXid, 4);
                                 tl.write();
                              }

                              try {
                                 if (traceEnabled) {
                                    ntrace.doTrace("tuxedoXid = " + tuxedoXid);
                                    ntrace.doTrace("issue COMMIT");
                                 }

                                 wlsXaResource.commit(tuxedoXid, one_pc);
                                 if (myWTCStat != null) {
                                    for(i = 0; i < associatedDomains.length; ++i) {
                                       rdom = (TDMRemote)associatedDomains[i];
                                       ss = (dsession)rdom.getTsession(false);
                                       myWTCStat.updInTransactionCommittedTotalCount(ss, 1L);
                                    }
                                 }
                              } catch (XAException var35) {
                                 if (traceEnabled) {
                                    ntrace.doTrace("commit failed:" + var35);
                                 }

                                 switch (var35.errorCode) {
                                    case -7:
                                    case -6:
                                    case -5:
                                    case -3:
                                    case -2:
                                    case -1:
                                    case 0:
                                    case 1:
                                    case 2:
                                    case 3:
                                    case 4:
                                    case 7:
                                    case 8:
                                    default:
                                       addFlags = 16;
                                       if (traceEnabled) {
                                          ntrace.doTrace("flag HEURISTIC_HAZARD");
                                       }
                                    case -4:
                                       break;
                                    case 5:
                                    case 6:
                                       addFlags = 8;
                                       if (traceEnabled) {
                                          ntrace.doTrace("flag HEURISTIC_MIX");
                                       }
                                 }
                              }

                              if (!one_pc) {
                                 tl.waitForDisk();
                              }

                              if (ntrace.getTraceLevel() == 1000372) {
                                 ntrace.doTrace("After commit log, sleeping 30 seconds");

                                 try {
                                    Thread.sleep(30000L);
                                 } catch (InterruptedException var32) {
                                 }

                                 ntrace.doTrace("/OatmialUnknownXidHandler/execute/Finished sleeping");
                              }
                           }
                           break;
                        case 10:
                        case 11:
                        case 13:
                           dsession ds = (dsession)myAtmi;
                           TuxedoConnectorRAP rdom = WTCService.getWTCService().getRemoteTDomain(ds.getRemoteDomainId());
                           tos.removeOutboundRdomFromXid(rdom, myTxid);
                        default:
                           if (traceEnabled) {
                              ntrace.doTrace("]/OatmialUnknownXidHandler/70/Opcode does not need a reply");
                           }

                           return 0;
                        case 12:
                           new_opcode = 10;
                           if (traceEnabled) {
                              ntrace.doTrace("opcode DONE");
                           }

                           if (associatedDomains != null) {
                              tl = ConfigHelper.removeXidTLogMap(tuxedoXid, 3);

                              try {
                                 if (traceEnabled) {
                                    ntrace.doTrace("aborting tuxedoXid = " + tuxedoXid);
                                    ntrace.doTrace("issue ROLLBACK");
                                 }

                                 wlsXaResource.rollback(tuxedoXid);
                                 if (myWTCStat != null) {
                                    for(i = 0; i < associatedDomains.length; ++i) {
                                       rdom = (TDMRemote)associatedDomains[i];
                                       ss = (dsession)rdom.getTsession(false);
                                       myWTCStat.updInTransactionRolledBackTotalCount(ss, 1L);
                                    }
                                 }
                              } catch (XAException var36) {
                                 if (traceEnabled) {
                                    ntrace.doTrace("/OatmialUnknownXidHandler/abort failed/" + var36);
                                 }

                                 switch (var36.errorCode) {
                                    case -7:
                                    case -6:
                                    case -5:
                                    case -3:
                                    case -2:
                                    case -1:
                                    case 0:
                                    case 1:
                                    case 2:
                                    case 3:
                                    case 4:
                                    case 6:
                                    case 8:
                                    default:
                                       addFlags = 16;
                                       if (traceEnabled) {
                                          ntrace.doTrace("flag HEURISTIC_HAZARD");
                                       }
                                    case -4:
                                       break;
                                    case 5:
                                    case 7:
                                       addFlags = 8;
                                 }
                              }
                           }
                     }

                     new_tmmsg = new tfmh(1);
                     new_tmmsg_tdom = new TdomTcb(new_opcode, tmmsg_tdom.get_reqid(), 0, (String)null);
                     new_tmmsg_tdom.set_info(32 | addFlags);
                     new_tmmsg.tdom = new tcm((short)7, new_tmmsg_tdom);
                     new_tmmsg_tdomtran = new TdomTranTcb(myTxid);
                     new_tmmsg_tdomtran.setNwtranidparent(tmmsg_tdomtran.getNwtranidparent());
                     new_tmmsg.tdomtran = new tcm((short)10, new_tmmsg_tdomtran);
                     if (associatedDomains != null && new_opcode == 8) {
                        tos.addXidToReadyMap(tuxedoXid);
                     }

                     try {
                        myAtmi.send_transaction_reply(new_tmmsg);
                        if (traceEnabled) {
                           ntrace.doTrace("/OatmialUnknownXidHandler/reply to unknown transaction request sent/");
                        }
                     } catch (TPException var31) {
                        WTCLogger.logTPEsendTran(var31);
                     }

                     if (associatedDomains != null && (new_opcode == 10 || new_opcode == 24)) {
                        if (tl != null) {
                           tl.forget();
                        }

                        tos.deleteXidFromReadyMap(tuxedoXid);
                        tos.deleteInboundRdomsAssociatedWithXid(tuxedoXid);
                        ConfigHelper.removeXidTLogMap(tuxedoXid, -1);
                     }

                     if (traceEnabled) {
                        ntrace.doTrace("]/OatmialUnknownXidHandler/80/");
                     }

                     return 0;
                  }
               }
            }
         } else {
            if (traceEnabled) {
               ntrace.doTrace("]/OatmialUnknownXidHandler/40/Invalid reply, no Txid/");
            }

            return 0;
         }
      }
   }

   public void setTaskName(String tname) {
      this.myName = new String("OatmialUnknownXidHandler$" + tname);
   }

   public String getTaskName() {
      return this.myName == null ? "OatmialUnknownXidHandler$unknown" : this.myName;
   }
}
