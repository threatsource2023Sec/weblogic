package com.bea.core.jatmi.internal;

import com.bea.core.jatmi.common.ntrace;
import com.bea.core.jatmi.config.TuxedoConnectorRAP;
import com.bea.core.jatmi.intf.TuxedoLoggable;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import weblogic.wtc.gwt.OatmialServices;
import weblogic.wtc.gwt.TxEnd;
import weblogic.wtc.jatmi.ReqXidOid;
import weblogic.wtc.jatmi.TPException;
import weblogic.wtc.jatmi.TdomTcb;
import weblogic.wtc.jatmi.TuxXidRply;
import weblogic.wtc.jatmi.Txid;
import weblogic.wtc.jatmi.gwatmi;
import weblogic.wtc.jatmi.tfmh;

public final class TuxedoXA implements XAResource {
   private static final int DEFAULT_TIMEOUT = 600;
   private Xid myXid;
   private OatmialServices tos;
   private TuxXidRply myRplyObj;
   private TxEnd myTxEndObj;
   private int myTimeout = 600;
   private String myRegistryName = null;

   public TuxedoXA() {
      this.tos = ConfigHelper.getTuxedoServices();
      this.myRplyObj = new TuxXidRply();
   }

   public TuxedoXA(OatmialServices aTos) {
      this.tos = aTos;
      this.myRplyObj = new TuxXidRply();
   }

   public TuxedoXA(OatmialServices aTos, TxEnd aTxEnd) {
      this.tos = aTos;
      this.myRplyObj = new TuxXidRply();
      this.myTxEndObj = aTxEnd;
   }

   private void internalCommit(Xid xid, boolean doCommit, boolean onePhase, boolean isRecovery, boolean optimized) throws XAException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace(">/TuxedoXA/internalCommit/xid = " + xid + ", doCommit = " + doCommit + ", onePhase = " + onePhase + ", isRecovery = " + isRecovery + ", optimized = " + optimized);
      }

      TuxedoConnectorRAP[] thoseInvolved = null;
      boolean gotHeuristicMix = false;
      boolean gotHeuristicHazard = false;
      boolean needRelease = false;
      TuxedoLoggable tl = null;
      boolean gotRollback = false;
      int rb_fail = 0;
      int commit_fail = 0;
      boolean gotFailure = false;
      Throwable reason = null;
      if (this.tos.getOutboundRdomsAssociatedWithXid(xid) == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/TuxedoXA/internalCommit/10");
         }

      } else {
         if (doCommit) {
            if (onePhase) {
               if (!optimized) {
                  needRelease = true;
                  tl = ConfigHelper.removeXidTLogMap(xid, 2);
                  if (tl == null) {
                     tl = TCTransactionHelper.createTuxedoLoggable(xid, 2);
                     ConfigHelper.addXidTLogMap(xid, tl);
                  }

                  tl.write();
                  tl.waitForDisk();
                  if (ntrace.getTraceLevel() == 1000372) {
                     if (traceEnabled) {
                        ntrace.doTrace("/TuxedoXA/internalCommit/After commit log, sleeping 30 seconds");
                     }

                     try {
                        Thread.sleep(30000L);
                     } catch (InterruptedException var30) {
                     }

                     if (traceEnabled) {
                        ntrace.doTrace("/TuxedoXA/internalCommit/Finished sleeping");
                     }
                  }
               }
            } else if (ntrace.getTraceLevel() == 1000372) {
               if (traceEnabled) {
                  ntrace.doTrace("/TuxedoXA/internalCommit/After two-phase commit log, sleeping 30 seconds");
               }

               try {
                  Thread.sleep(30000L);
               } catch (InterruptedException var29) {
               }

               if (traceEnabled) {
                  ntrace.doTrace("/TuxedoXA/internalCommit/Two-phase Finished sleeping");
               }
            }
         }

         boolean getTsessionMode = !isRecovery;

         do {
            if ((thoseInvolved = this.tos.getOutboundRdomsAssociatedWithXid(xid)) == null) {
               this.tos.deleteOutboundRdomsAssociatedWithXid(xid);
               if (!onePhase) {
                  tl = ConfigHelper.removeXidTLogMap(xid, 1);
                  needRelease = tl != null;
                  if (needRelease && traceEnabled) {
                     ntrace.doTrace("/TuxedoXA/internalCommit/Two-phase need to release IS_READY");
                  }
               }

               if (needRelease) {
                  tl.forget();
               }

               if (gotRollback) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/TuxedoXA/internalCommit/25");
                  }

                  throw new XAException(100);
               }

               if (gotHeuristicMix) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/TuxedoXA/internalCommit/30");
                  }

                  throw new XAException(5);
               }

               if (gotHeuristicHazard) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/TuxedoXA/internalCommit/40");
                  }

                  throw new XAException(8);
               }

               if (traceEnabled) {
                  ntrace.doTrace("]/TuxedoXA/internalCommit/50");
               }

               return;
            }

            if (gotFailure) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/TuxedoXA/internalCommit/45/XAER_RMRETRY");
               }

               if (!doCommit) {
                  throw new weblogic.transaction.XAException(200, "rollback failed, retry it", (Throwable)null);
               }

               throw new weblogic.transaction.XAException(200, "commit failed, retry it", (Throwable)null);
            }

            int howManyInvolved = thoseInvolved.length;
            gwatmi[] agents = new gwatmi[howManyInvolved];
            ReqXidOid[] roids = new ReqXidOid[howManyInvolved];

            int lcv;
            label212:
            for(lcv = 0; lcv < howManyInvolved; ++lcv) {
               while((agents[lcv] = ConfigHelper.getRAPSession(thoseInvolved[lcv], getTsessionMode)) != null) {
                  try {
                     Txid aRply;
                     if ((aRply = agents[lcv].tpcommit(this.myRplyObj, xid, this.myTimeout, doCommit)) != null) {
                        roids[lcv] = new ReqXidOid(aRply, agents[lcv]);
                        continue label212;
                     }

                     if (!doCommit) {
                        roids[lcv] = null;
                        continue label212;
                     }
                  } catch (Exception var31) {
                     if (doCommit) {
                        ++commit_fail;
                     } else {
                        ++rb_fail;
                     }

                     reason = new Throwable(var31);
                     continue label212;
                  }
               }

               this.tos.setCommitRetry(xid, true);
               if (needRelease) {
               }

               if (!doCommit) {
                  ++rb_fail;
               } else {
                  ++commit_fail;
               }
            }

            for(lcv = 0; lcv < howManyInvolved; ++lcv) {
               if (roids[lcv] == null) {
                  if (!doCommit) {
                     this.tos.removeOutboundRdomFromXid(thoseInvolved[lcv], xid);
                  }
               } else {
                  tfmh tmmsg = this.myRplyObj.get_specific_reply(roids[lcv], true);
                  TdomTcb tdom = (TdomTcb)tmmsg.tdom.body;
                  int opcode = tdom.get_opcode();
                  switch (opcode) {
                     case 3:
                        if (traceEnabled) {
                           ntrace.doTrace("*/TuxedoXA/internalCommit/failed due to connection failed " + thoseInvolved[lcv].getAccessPointId());
                        }

                        gotFailure = true;
                        break;
                     case 10:
                        int info = tdom.get_info();
                        if ((info & 8) != 0) {
                           gotHeuristicMix = true;
                        }

                        if ((info & 16) != 0) {
                           gotHeuristicHazard = true;
                        }
                     case 24:
                        this.tos.removeOutboundRdomFromXid(thoseInvolved[lcv], xid);
                        break;
                     case 12:
                        if (tdom.get_diagnostic() == 13) {
                           gotFailure = true;
                           if (traceEnabled) {
                              ntrace.doTrace("]/TuxedoXA/internalCommit/12/got TPETIME");
                           }
                        } else {
                           gotRollback = true;
                           this.tos.removeOutboundRdomFromXid(thoseInvolved[lcv], xid);
                        }
                        break;
                     default:
                        if (!doCommit) {
                           this.tos.removeOutboundRdomFromXid(thoseInvolved[lcv], xid);
                        }
                  }
               }
            }

            if (commit_fail > 0) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/TuxedoXA/internalCommit/65/XAER_RMRETRY");
               }

               throw new weblogic.transaction.XAException(200, "commit failed, retry it", reason);
            }
         } while(rb_fail <= 0);

         if (traceEnabled) {
            ntrace.doTrace("*]/TuxedoXA/internalCommit/66/XAER_RMRETRY");
         }

         throw new weblogic.transaction.XAException(200, "rollback failed, retry it", reason);
      }
   }

   public void commit(Xid xid, boolean onePhase) throws XAException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoXA/commit/" + xid + "/" + onePhase);
      }

      this.outerCommit(xid, onePhase, false);
      if (traceEnabled) {
         ntrace.doTrace("]/TuxedoXA/commit/40");
      }

   }

   public void recoveryCommit(Xid xid, boolean onePhase) throws XAException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoXA/recoveryCommit/" + xid + "/" + onePhase);
      }

      this.outerCommit(xid, onePhase, true);
      if (traceEnabled) {
         ntrace.doTrace("]/TuxedoXA/recoveryCommit/40");
      }

   }

   private void outerCommit(Xid xid, boolean onePhase, boolean isRecovery) throws XAException {
      TuxedoConnectorRAP[] thoseInvolved = null;
      boolean onePhaseOptimization = false;
      boolean need_rollback = false;
      Throwable reason = null;
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoXA/outerCommit/" + xid + "/" + onePhase + "/" + isRecovery);
      }

      if (onePhase && !isRecovery) {
         thoseInvolved = this.tos.getOutboundRdomsAssociatedWithXid(xid);
         if (thoseInvolved != null && thoseInvolved.length == 1) {
            gwatmi target = ConfigHelper.getRAPSession(thoseInvolved[0], false);
            if (target != null && (target.getSessionFeatures() & 8) != 0) {
               onePhaseOptimization = true;
               if (traceEnabled) {
                  ntrace.doTrace("1PC optimization enabled");
               }
            }
         }
      }

      if (!onePhaseOptimization) {
         int prepare_reply = 0;
         PrepareOpt opt = new PrepareOpt(isRecovery);
         if (onePhase && !this.tos.getCommitRetry(xid)) {
            try {
               prepare_reply = this.internalPrepare(xid, onePhase, opt);
            } catch (XAException var15) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/TuxedoXA/outerCommit/10/" + var15);
               }

               need_rollback = true;
            } catch (Exception var16) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/TuxedoXA/outerCommit/11/" + var16);
               }

               need_rollback = true;
            }

            if (need_rollback) {
               boolean rollback_failed = false;

               try {
                  this.rollback(xid);
               } catch (Exception var14) {
                  rollback_failed = true;
                  if (traceEnabled) {
                     ntrace.doTrace(" /TuxedoXA/outerCommit/12/" + var14);
                  }

                  reason = new Throwable(var14);
               }

               if (!rollback_failed) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/TuxedoXA/outerCommit/12.5");
                  }

                  throw new XAException(100);
               }

               this.tos.deleteOutboundRdomsAssociatedWithXid(xid);
               if (traceEnabled) {
                  ntrace.doTrace("]/TuxedoXA/outerCommit/13/XAER_RMERR");
               }

               throw new weblogic.transaction.XAException(200, "prepare failed, then rollback failed either, retry rollback", reason);
            }

            if (opt.do1pc) {
               onePhaseOptimization = true;
            }
         }

         if (prepare_reply == 3) {
            if (traceEnabled) {
               ntrace.doTrace("]/TuxedoXA/outerCommit/20");
            }

            return;
         }
      }

      try {
         this.internalCommit(xid, true, onePhase, isRecovery, onePhaseOptimization);
      } catch (XAException var13) {
         if (traceEnabled) {
            ntrace.doTrace("*]/TuxedoXA/outerCommit/30/" + var13);
         }

         throw var13;
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TuxedoXA/outerCommit/40");
      }

   }

   public void end(Xid xid, int flags) throws XAException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoXA/end/" + xid + "/" + flags);
      }

      if (this.myTxEndObj != null && this.myXid != null) {
         this.myTxEndObj.end(this.myXid, flags);
      }

      this.myXid = null;
      if (traceEnabled) {
         ntrace.doTrace("]/TuxedoXA/end/10");
      }

   }

   public void forget(Xid xid) throws XAException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoXA/forget/" + xid);
      }

      this.tos.deleteOutboundRdomsAssociatedWithXid(xid);
      TuxedoLoggable tl = ConfigHelper.removeXidTLogMap(xid, -1);
      if (tl != null) {
         tl.forget();
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TuxedoXA/forget/20");
      }

   }

   public int getRealTransactionTimeout() {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      int ret = -1;
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoXA/getRealTransactionTimeout/");
      }

      if (this.myXid != null) {
         ret = TCTransactionHelper.getRealTransactionTimeout();
      }

      if (ret == -1) {
         ret = this.myTimeout;
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TuxedoXA/getRealTransactionTimeout/10/" + ret);
      }

      return ret;
   }

   public int getTransactionTimeout() throws XAException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoXA/getTransactionTimeout/");
         ntrace.doTrace("]/TuxedoXA/getTransactionTimeout/10/" + this.myTimeout);
      }

      return this.myTimeout;
   }

   public boolean isSameRM(XAResource xares) throws XAException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoXA/isSameRM/" + xares);
      }

      if (this == xares) {
         if (traceEnabled) {
            ntrace.doTrace("]/TuxedoXA/isSameRM/10/true");
         }

         return true;
      } else {
         if (xares instanceof TuxedoXA) {
            if (traceEnabled) {
               ntrace.doTrace("/TuxedoXA/isSameRM/incoming res name: " + ((TuxedoXA)xares).getRegistryName() + "/current res name: " + this.getRegistryName());
            }

            if (this.getRegistryName().equals(((TuxedoXA)xares).getRegistryName())) {
               if (traceEnabled) {
                  ntrace.doTrace("]/TuxedoXA/isSameRM/20/true");
               }

               return true;
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/TuxedoXA/isSameRM/30/false");
         }

         return false;
      }
   }

   public int prepare(Xid xid) throws XAException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoXA/prepare/" + xid);
      }

      int ret = 0;
      Exception ex = null;

      try {
         ret = this.internalPrepare(xid, false, (PrepareOpt)null);
      } catch (Exception var7) {
         if (traceEnabled) {
            ntrace.doTrace("/TuxedoXA/prepare/20/" + var7);
         }

         ex = var7;
      }

      if (ex != null) {
         try {
            this.rollback(xid);
         } catch (Exception var6) {
            if (traceEnabled) {
               ntrace.doTrace("]/TuxedoXA/prepare/30/call rollback failed, does not retry");
            }
         }

         this.tos.deleteOutboundRdomsAssociatedWithXid(xid);
         if (traceEnabled) {
            ntrace.doTrace("]/TuxedoXA/prepare/40/XA_RBROLLBACK");
         }

         throw new XAException(100);
      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/TuxedoXA/prepare/50/" + ret);
         }

         return ret;
      }
   }

   private ReqXidOid invokePrepare(Xid xid, TuxedoConnectorRAP rap) throws XAException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoXA/invokePrepare/" + rap.getAccessPoint());
      }

      gwatmi agent;
      if ((agent = ConfigHelper.getRAPSession(rap, true)) == null) {
         this.tos.removeOutboundRdomFromXid(rap, xid);
         if (traceEnabled) {
            ntrace.doTrace("]/TuxedoXA/invokePrepare/10/XA_RBCOMMFAIL");
         }

         throw new XAException(101);
      } else {
         try {
            Txid aRply = agent.tpprepare(this.myRplyObj, xid, this.myTimeout);
            if (traceEnabled) {
               ntrace.doTrace("]/TuxedoXA/invokePrepare/20");
            }

            return new ReqXidOid(aRply, agent);
         } catch (TPException var7) {
            if (traceEnabled) {
               ntrace.doTrace("]/TuxedoXA/invokePrepare/30/XA_RBCOMMFAIL");
            }

            throw new XAException(101);
         } catch (Exception var8) {
            if (traceEnabled) {
               ntrace.doTrace("]/TuxedoXA/invokePrepare/" + var8.getMessage());
               ntrace.doTrace("]/TuxedoXA/invokePrepare/40/XA_RBCOMMFAIL");
            }

            throw new XAException(101);
         }
      }
   }

   private int waiting4Prepare(Xid xid, TuxedoConnectorRAP rap, ReqXidOid roid) throws XAException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoXA/waiting4Prepare/" + rap.getAccessPoint());
      }

      tfmh tmmsg = this.myRplyObj.get_specific_reply(roid, true);
      TdomTcb tdom = (TdomTcb)tmmsg.tdom.body;
      int opcode = tdom.get_opcode();
      switch (opcode) {
         case 3:
            try {
               this.rollback(xid);
            } catch (XAException var10) {
               if (traceEnabled) {
                  ntrace.doTrace(" /TuxedoXA/waiting4Prepare/62/XAException: " + var10);
               }
            }

            this.tos.deleteOutboundRdomsAssociatedWithXid(xid);
            if (traceEnabled) {
               ntrace.doTrace("]/TuxedoXA/waiting4Prepare/50/connection teminated/XA_RBROLLBACK/" + opcode);
            }

            throw new XAException(100);
         case 12:
            try {
               this.rollback(xid);
            } catch (XAException var9) {
               if (traceEnabled) {
                  ntrace.doTrace(" /TuxedoXA/waiting4Prepare/60/XAException:" + var9);
               }
            }

            this.tos.deleteOutboundRdomsAssociatedWithXid(xid);
            if (tdom.get_diagnostic() == 13) {
               if (traceEnabled) {
                  ntrace.doTrace("]/TuxedoXA/waiting4Prepare/10/XA_RBTIMEOUT");
               }

               throw new XAException(106);
            }

            if (traceEnabled) {
               ntrace.doTrace("]/TuxedoXA/waiting4Prepare/20/XA_RBROLLBACK");
            }

            throw new XAException(100);
         case 24:
            this.tos.removeOutboundRdomFromXid(rap, xid);
         case 8:
            if (traceEnabled) {
               ntrace.doTrace("]/TuxedoXA/waiting4Prepare/40/" + (opcode == 8 ? "XA_OK" : "XA_RDONLY"));
            }

            return opcode;
         default:
            try {
               this.rollback(xid);
            } catch (XAException var11) {
               if (traceEnabled) {
                  ntrace.doTrace(" /TuxedoXA/waiting4Prepare/64/XAException: " + var11);
               }
            }

            this.tos.deleteOutboundRdomsAssociatedWithXid(xid);
            if (traceEnabled) {
               ntrace.doTrace("]/TuxedoXA/waiting4Prepare/30/XA_RBPROTO/" + opcode);
            }

            throw new XAException(105);
      }
   }

   private int internalPrepare(Xid xid, boolean onePhase, PrepareOpt opt) throws XAException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoXA/internalPrepare/" + xid + "/" + onePhase + "/" + (opt == null ? "null" : opt.isRecovery));
      }

      TuxedoConnectorRAP[] thoseInvolved = null;
      TuxedoLoggable tl = null;
      int rdonly1pc = false;
      if (opt != null) {
         opt.do1pc = false;
      }

      if ((thoseInvolved = this.tos.getOutboundRdomsAssociatedWithXid(xid)) == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/TuxedoXA/internalPrepare/10/XA_RDONLY");
         }

         return 3;
      } else {
         int howManyInvolved = thoseInvolved.length;
         ReqXidOid[] roids = new ReqXidOid[howManyInvolved];
         int end = howManyInvolved;
         if (opt != null && !opt.isRecovery && howManyInvolved > 1 && onePhase && TCResourceHelper.isTightlyCoupledTransactionsEnabled() && !TCResourceHelper.getParallelXAEnabled()) {
            if (traceEnabled) {
               ntrace.doTrace(" /TuxedoXA/internalPrepare/rdonly1pc invoked");
            }

            rdonly1pc = true;
            end = howManyInvolved - 1;
         }

         int lcv;
         for(lcv = 0; lcv < end; ++lcv) {
            roids[lcv] = this.invokePrepare(xid, thoseInvolved[lcv]);
         }

         int readdOnlyNum = 0;

         int opcode;
         for(lcv = 0; lcv < end; ++lcv) {
            opcode = this.waiting4Prepare(xid, thoseInvolved[lcv], roids[lcv]);
            if (end == howManyInvolved - 1 && opcode == 8) {
               end = howManyInvolved;
               roids[howManyInvolved - 1] = this.invokePrepare(xid, thoseInvolved[howManyInvolved - 1]);
            } else if (opcode == 24) {
               ++readdOnlyNum;
            }
         }

         if (rdonly1pc && end == howManyInvolved) {
            opcode = this.waiting4Prepare(xid, thoseInvolved[howManyInvolved - 1], roids[howManyInvolved - 1]);
            if (opcode == 24) {
               ++readdOnlyNum;
            }
         }

         if (rdonly1pc && end == howManyInvolved - 1 && readdOnlyNum == howManyInvolved - 1) {
            gwatmi target = ConfigHelper.getRAPSession(thoseInvolved[howManyInvolved - 1], false);
            if (target != null && (target.getSessionFeatures() & 8) != 0) {
               if (opt != null) {
                  opt.do1pc = true;
               }

               if (traceEnabled) {
                  ntrace.doTrace("]/TuxedoXA/internalPrepare/60/XA_OK/" + (opt != null ? opt.do1pc : false));
               }

               return 0;
            }

            roids[howManyInvolved - 1] = this.invokePrepare(xid, thoseInvolved[howManyInvolved - 1]);
            opcode = this.waiting4Prepare(xid, thoseInvolved[howManyInvolved - 1], roids[howManyInvolved - 1]);
            if (opcode == 24) {
               ++readdOnlyNum;
            }
         }

         if (readdOnlyNum == howManyInvolved) {
            if (traceEnabled) {
               ntrace.doTrace("]/TuxedoXA/internalPrepare/70/XA_RDONLY");
            }

            return 3;
         } else {
            if (!onePhase) {
               tl = TCTransactionHelper.createTuxedoLoggable(xid, 1);
               tl.write();
               ConfigHelper.addXidTLogMap(xid, tl);
               tl.waitForDisk();
            }

            if (traceEnabled) {
               ntrace.doTrace("]/TuxedoXA/internalPrepare/80/XA_OK/" + (opt != null ? opt.do1pc : false));
            }

            return 0;
         }
      }
   }

   public Xid[] recover(int flag) throws XAException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoXA/recover/" + flag);
      }

      switch (flag) {
         case 0:
         case 16777216:
            if (traceEnabled) {
               ntrace.doTrace("]/TuxedoXA/recover/10/0");
            }

            return new Xid[0];
         case 8388608:
            Xid[] ret = ConfigHelper.getRecoveredXids();
            if (traceEnabled) {
               ntrace.doTrace("]/TuxedoXA/recover/20/" + ret.length);
            }

            return ret;
         default:
            if (traceEnabled) {
               ntrace.doTrace("]/TuxedoXA/recover/30/null");
            }

            return null;
      }
   }

   public void rollback(Xid xid) throws XAException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoXA/rollback/" + xid);
      }

      try {
         this.internalCommit(xid, false, false, false, false);
      } catch (XAException var4) {
         if (traceEnabled) {
            ntrace.doTrace("*]/TuxedoXA/rollback/30/XAException:" + var4);
         }

         throw var4;
      } catch (Exception var5) {
         if (traceEnabled) {
            ntrace.doTrace("*]/TuxedoXA/rollback/31/Exception:" + var5);
         }

         throw new XAException(100);
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TuxedoXA/rollback/40");
      }

   }

   public boolean setTransactionTimeout(int seconds) throws XAException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoXA/setTransactionTimeout/" + seconds);
      }

      boolean ret;
      if (seconds <= 0) {
         this.myTimeout = 600;
         ret = false;
      } else {
         this.myTimeout = seconds;
         ret = true;
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TuxedoXA/setTransactionTimeout/10/" + ret);
      }

      return ret;
   }

   public void start(Xid xid) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoXA/start/" + xid);
      }

      this.myXid = xid;
      if (traceEnabled) {
         ntrace.doTrace("]/TuxedoXA/start/10");
      }

   }

   public void start(Xid xid, int flags) throws XAException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoXA/start/" + xid + "/" + flags);
      }

      this.myXid = xid;
      if (traceEnabled) {
         ntrace.doTrace("]/TuxedoXA/start/10");
      }

   }

   public Xid getXid() {
      return this.myXid;
   }

   public String getRegistryName() {
      if (this.myRegistryName == null) {
         this.myRegistryName = "OatmialResource";
         if (this.tos.getRMNameSuffix() != null) {
            this.myRegistryName = this.myRegistryName + this.tos.getRMNameSuffix();
         }
      }

      return this.myRegistryName;
   }
}
