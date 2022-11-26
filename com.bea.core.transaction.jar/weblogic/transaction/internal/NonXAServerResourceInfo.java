package weblogic.transaction.internal;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.AccessController;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import javax.transaction.SystemException;
import javax.transaction.xa.Xid;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.transaction.loggingresource.LoggingResource;
import weblogic.transaction.loggingresource.LoggingResourceException;
import weblogic.transaction.nonxa.NonXAException;
import weblogic.transaction.nonxa.NonXAResource;

final class NonXAServerResourceInfo extends ServerResourceInfo {
   private NonXAResource nonXAResource;
   private static final AuthenticatedSubject kernelID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   NonXAServerResourceInfo(String aName) {
      super(aName);
      this.rd = NonXAResourceDescriptor.getOrCreate(aName);
   }

   NonXAServerResourceInfo(ResourceDescriptor ard) {
      super(ard);
      this.setNonXAResource((NonXAResource)null);
   }

   NonXAServerResourceInfo(NonXAResource nxar) {
      this.setNonXAResource(nxar);
      this.rd = NonXAResourceDescriptor.getOrCreate(nxar);
      this.setName(this.rd.getName());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer(100);
      sb.append("NonXAServerResourceInfo[").append(this.getName()).append("]=(");
      sb.append(super.toString());
      sb.append(",nonXAResource=").append(this.getNonXAResource());
      sb.append(")");
      return sb.toString();
   }

   NonXAServerResourceInfo getSameResource(NonXAResource nxar) {
      return nxar == this.nonXAResource ? this : null;
   }

   private void writeLLRRecord(ServerTransactionImpl tx) throws SystemException {
      boolean debugWrite = TxDebug.JTA2PC.isDebugEnabled() || TxDebug.JTANonXA.isDebugEnabled() || TxDebug.JTALLR.isDebugEnabled();
      tx.checkLLR("LLRFailBeforeLogWrite");

      byte[] buf;
      SystemException se;
      try {
         PlatformHelper.UnsyncByteArrayOutputStream bos = PlatformHelper.getPlatformHelper().newUnsyncByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(bos);
         StoreLogDataOutputImpl sos = new StoreLogDataOutputImpl(oos);
         sos.writeInt(90);
         tx.writeExternal(sos);
         oos.flush();
         bos.flush();
         buf = bos.toByteArray();
      } catch (IOException var16) {
         se = new SystemException("internal err:" + var16.toString());
         se.initCause(var16);
         if (debugWrite) {
            this.debug("logging resource persist tx xid=" + tx.getXid(), se);
         }

         throw se;
      } catch (RuntimeException var17) {
         var17.printStackTrace();
         if (debugWrite) {
            this.debug("logging resource persist tx xid=" + tx.getXid(), var17);
         }

         throw var17;
      }

      tx.setProperty("LLR_TX_WRITE", "");

      try {
         LoggingResource lr = this.getLoggingResource();
         tx.setLoggingResourceInfo(this);
         lr.writeXARecord(tx.getXID(), buf);
         tx.checkLLR("LLRFailAfterLogWrite");
      } catch (LoggingResourceException var13) {
         se = new SystemException(var13.toString());
         se.initCause(var13);
         if (debugWrite) {
            this.debug("logging resource persist tx xid=" + tx.getXid(), var13);
         }

         throw se;
      } catch (RuntimeException var14) {
         var14.printStackTrace();
         if (debugWrite) {
            this.debug("logging resource persist tx xid=" + tx.getXid(), var14);
         }

         throw var14;
      } finally {
         tx.setProperty("LLR_TX_WRITE", (Serializable)null);
      }

      if (debugWrite) {
         this.debug("logging resource persist tx xid=" + tx.getXid() + " size=" + buf.length);
      }

   }

   private boolean handleFailedLLRCommit(NonXAException nxa, ServerTransactionImpl tx, boolean onePhase) throws NonXAException {
      LoggingResource lr = this.getLoggingResource();
      XidImpl xid = (XidImpl)tx.getXID();
      String xidStr = xid.toString(false);

      try {
         lr.rollback(xid);
      } catch (NonXAException var14) {
      }

      String excTxt;
      if (onePhase) {
         TXLogger.logUnresolvedLLROnePhaseCommit(lr.toString(), xidStr, tx.toString(), nxa);
         excTxt = TXExceptionLogger.logUnresolvedLLROnePhaseCommitLoggable(lr.toString(), xidStr).getMessage();
      } else {
         int i = 0;

         while(true) {
            if (i >= 2) {
               TXLogger.logUnresolvedLLRTwoPhaseCommit(lr.toString(), xidStr, tx.toString(), 5, nxa);
               excTxt = TXExceptionLogger.logUnresolvedLLRTwoPhaseCommitLoggable(lr.toString(), xidStr).getMessage();
               break;
            }

            Xid bXid = this.getXIDwithBranch((XidImpl)tx.getXID());

            try {
               try {
                  tx.checkLLRRetry();
               } catch (SystemException var13) {
                  throw new LoggingResourceException(var13);
               }

               byte[] record = lr.getXARecord(bXid);
               if (record == null) {
                  tx.setRollbackReason(nxa);
                  throw nxa;
               }

               this.setCommitted();
               this.rd.tallyCompletion(this, (Exception)null);
               return true;
            } catch (LoggingResourceException var15) {
               if (i != 0) {
                  try {
                     Thread.currentThread();
                     Thread.sleep(2000L);
                  } catch (InterruptedException var12) {
                  }
               }

               ++i;
            }
         }
      }

      SystemException se = new SystemException(excTxt);
      se.initCause(nxa);
      tx.setLoggingResourceCommitFailure(se);
      return false;
   }

   void commit(ServerTransactionImpl tx, boolean onePhase, boolean forced, boolean isLocal) throws NonXAException, SystemException {
      boolean debugCommit = TxDebug.JTA2PC.isDebugEnabled() || TxDebug.JTANonXA.isDebugEnabled() || TxDebug.JTALLR.isDebugEnabled();
      if (debugCommit) {
         this.debug("commit(tx) tx=" + tx);
      }

      if (!this.testAndSetBusy()) {
         throw new SystemException("Unable to perform NonXA commit because resource is busy");
      } else {
         Exception nxaeException = null;
         NonXAResource nxar = this.getNonXAResource();
         Xid bXid = this.getXIDwithBranch((XidImpl)tx.getXID());

         try {
            if (this.isCommitted()) {
               if (forced) {
                  TXLogger.logForceCommitResourceCommitted(tx.getXid().toString(), this.getName());
               }

               return;
            }

            try {
               if (!onePhase && nxar instanceof LoggingResource) {
                  tx.isLLR = true;
                  if (!isLocal) {
                     throw new NonXAException("A logging resource can't log remotely - it must be local to the coordinator.");
                  }

                  tx.check("TMAfterPrepareBeforeTLog");
                  this.writeLLRRecord(tx);
                  synchronized(tx) {
                     if (tx.isCancelledUnsync()) {
                        throw new NonXAException("Transaction already canceled.");
                     }

                     tx.setPreparedUnsync();
                  }
               }
            } catch (NonXAException var28) {
               nxaeException = var28;
               tx.setRollbackReason(var28);
               throw var28;
            }

            tx.setProperty("LLR_TX_COMMIT", "");

            try {
               try {
                  if (nxar instanceof LoggingResource) {
                     tx.checkLLR("LLRFailBeforeCommit");
                  }

                  if (this.rd.getPartitionName() == null) {
                     nxar.commit(bXid, onePhase);
                  } else {
                     this.partitionNxrCommit(nxar, bXid, onePhase);
                  }

                  if (nxar instanceof LoggingResource) {
                     tx.checkLLR("LLRFailAfterCommit");
                  }
               } catch (NonXAException var29) {
                  throw var29;
               } catch (Throwable var30) {
                  NonXAException ne = new NonXAException(var30.toString());
                  ne.initCause(var30);
                  throw ne;
               }

               this.setCommitted();
               this.rd.tallyCompletion(this, (Exception)null);
            } catch (NonXAException var31) {
               if (var31.getMessage().indexOf("ORA-02091") != -1) {
                  tx.setRollbackReason(var31);
                  throw var31;
               }

               if (!(nxar instanceof LoggingResource)) {
                  nxaeException = var31;
                  tx.setRollbackReason(var31);
                  throw var31;
               }

               if (!this.handleFailedLLRCommit(var31, tx, onePhase)) {
                  return;
               }

               return;
            } finally {
               tx.setProperty("LLR_TX_COMMIT", (Serializable)null);
            }
         } finally {
            this.clearBusy();
            if (nxaeException != null) {
               if (forced) {
                  TXLogger.logForceCommitResourceError(bXid.toString(), this.getName(), nxaeException);
               }

               if (debugCommit) {
                  this.debug(".commit(): failed " + tx.getXid(), nxaeException);
               }
            }

         }

      }
   }

   final void rollback(ServerTransactionImpl tx, boolean forced, boolean isOnePhaseTransaction) {
      boolean debugRollback = TxDebug.JTA2PC.isDebugEnabled() || TxDebug.JTANonXA.isDebugEnabled() || TxDebug.JTALLR.isDebugEnabled();
      if (debugRollback) {
         this.debug("rollback(tx): " + tx);
      }

      if (this.testAndSetBusy()) {
         if (this.isRolledBack()) {
            this.clearBusy();
            if (forced) {
               TXLogger.logForceRollbackResourceRolledBack(tx.getXid().toString(), this.getName());
            }

         } else {
            boolean done = true;
            NonXAException nxaException = null;
            Xid bXid = this.getXIDwithBranch((XidImpl)tx.getXID());

            try {
               NonXAResource nxar = this.getNonXAResource();
               if (nxar == null) {
                  if (debugRollback) {
                     this.debug("rollback quitting because no NonXAResource" + tx.getXid());
                  }

                  return;
               }

               try {
                  if (this.rd.getPartitionName() == null) {
                     nxar.rollback(bXid);
                  } else {
                     this.partitionNxrRollback(nxar, bXid);
                  }

                  return;
               } catch (NonXAException var13) {
                  nxaException = var13;
                  done = false;
                  if (debugRollback) {
                     this.debug("rollback(): failed " + tx.getXid(), var13);
                  }
               }
            } finally {
               if (done) {
                  if (debugRollback) {
                     this.debug("rollback done" + tx.getXid());
                  }

                  this.setRolledBack();
                  this.rd.tallyCompletion(this, nxaException);
               } else if (debugRollback) {
                  this.debug("rollback NOT DONE" + tx.getXid());
               }

               this.clearBusy();
            }

         }
      }
   }

   void setNonXAResource(NonXAResource nxar) {
      this.nonXAResource = nxar;
   }

   boolean isEquivalentResource(NonXAResource nxar) {
      NonXAResource tmp = this.getNonXAResource();

      try {
         if (nxar != null && tmp != null) {
            return tmp == nxar || tmp.isSameRM(nxar);
         } else {
            return false;
         }
      } catch (Exception var4) {
         return false;
      }
   }

   protected String getStateAsString(int st) {
      switch (st) {
         case 1:
            return "new";
         case 7:
            return "committed";
         case 8:
            return "rolledback";
         default:
            return "**** UNKNOWN STATE *** " + st;
      }
   }

   private NonXAResource getNonXAResource() {
      if (this.nonXAResource != null) {
         return this.nonXAResource;
      } else {
         if (this.rd != null && this.rd instanceof NonXAResourceDescriptor) {
            NonXAResourceDescriptor nxard = (NonXAResourceDescriptor)this.rd;
            this.nonXAResource = nxard.getNonXAResource();
         }

         return this.nonXAResource;
      }
   }

   LoggingResource getLoggingResource() {
      return (LoggingResource)this.getNonXAResource();
   }

   private void debug(String s) {
      this.debug(s, (Exception)null);
   }

   private void debug(String s, Exception e) {
      s = "NonXAServerResourceInfo[" + this.getName() + "]" + s;
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.JTA2PC.debug((String)s, (Throwable)e);
      } else if (TxDebug.JTANonXA.isDebugEnabled()) {
         TxDebug.JTANonXA.debug((String)s, (Throwable)e);
      } else if (TxDebug.JTALLR.isDebugEnabled()) {
         TxDebug.JTALLR.debug((String)s, (Throwable)e);
      }

   }

   void partitionNxrCommit(NonXAResource nxar, Xid xid, boolean onePhase) throws NonXAException {
      final Xid axid = xid;
      final NonXAResource anxar = nxar;
      final boolean aonePhase = onePhase;
      if (TxDebug.JTANonXA.isDebugEnabled()) {
         this.debug("partition Nonxa commit nonxar:" + nxar + " xid:" + xid + " partitionName:" + this.rd.getPartitionName());
      }

      try {
         ComponentInvocationContextManager.runAs(kernelID, this.rd.getComponentInvocationContext(), new Callable() {
            public Void call() throws NonXAException {
               anxar.commit(axid, aonePhase);
               return null;
            }
         });
      } catch (ExecutionException var9) {
         if (TxDebug.JTANonXA.isDebugEnabled()) {
            this.debug("partition Nonxa commit ExecutionException nonxar:" + nxar + " xid:" + xid + " partitionName:" + this.rd.getPartitionName() + " exception cause:" + var9.getCause());
         }

         if (var9.getCause() instanceof NonXAException) {
            NonXAException nxae = (NonXAException)var9.getCause();
            throw nxae;
         } else {
            IllegalStateException ise;
            if (var9.getCause() instanceof IllegalStateException) {
               ise = (IllegalStateException)var9.getCause();
               throw ise;
            } else if (var9.getCause() instanceof SecurityException) {
               SecurityException se = (SecurityException)var9.getCause();
               throw se;
            } else {
               ise = new IllegalStateException(var9.getCause());
               ise.initCause(var9.getCause());
               throw ise;
            }
         }
      }
   }

   void partitionNxrRollback(NonXAResource nxar, Xid xid) throws NonXAException {
      final Xid axid = xid;
      final NonXAResource anxar = nxar;
      if (TxDebug.JTANonXA.isDebugEnabled()) {
         this.debug("partition Nonxa rollback nonxar:" + nxar + " xid:" + xid + " partitionName:" + this.rd.getPartitionName());
      }

      try {
         ComponentInvocationContextManager.runAs(kernelID, this.rd.getComponentInvocationContext(), new Callable() {
            public Void call() throws NonXAException {
               anxar.rollback(axid);
               return null;
            }
         });
      } catch (ExecutionException var7) {
         if (TxDebug.JTANonXA.isDebugEnabled()) {
            this.debug("partition Nonxa rollback ExecutionException nonxar:" + nxar + " xid:" + xid + " partitionName:" + this.rd.getPartitionName() + " exception cause:" + var7.getCause());
         }

         if (var7.getCause() instanceof NonXAException) {
            NonXAException nxae = (NonXAException)var7.getCause();
            throw nxae;
         } else {
            IllegalStateException ise;
            if (var7.getCause() instanceof IllegalStateException) {
               ise = (IllegalStateException)var7.getCause();
               throw ise;
            } else if (var7.getCause() instanceof SecurityException) {
               SecurityException se = (SecurityException)var7.getCause();
               throw se;
            } else {
               ise = new IllegalStateException(var7.getCause());
               ise.initCause(var7.getCause());
               throw ise;
            }
         }
      }
   }
}
