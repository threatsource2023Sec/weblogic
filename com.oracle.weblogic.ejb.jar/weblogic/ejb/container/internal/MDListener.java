package weblogic.ejb.container.internal;

import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.util.LinkedList;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.naming.Context;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import weblogic.deployment.jms.MDBSession;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.NonDestructiveRuntimeException;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.interfaces.MessageDrivenBeanInfo;
import weblogic.ejb.container.interfaces.MessageDrivenManagerIntf;
import weblogic.ejb.container.interfaces.PoolIntf;
import weblogic.ejb.container.monitoring.EJBTransactionRuntimeMBeanImpl;
import weblogic.ejb.spi.JmsMessageDrivenBean;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.j2ee.MethodInvocationHelper;
import weblogic.jms.client.WLSessionImpl;
import weblogic.jms.extensions.MDBTransaction;
import weblogic.management.runtime.MessageDrivenEJBRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityManager;
import weblogic.security.service.SecurityServiceManager;
import weblogic.transaction.RollbackException;
import weblogic.transaction.TransactionManager;
import weblogic.utils.StackTraceUtilsClient;
import weblogic.work.WorkManager;

final class MDListener implements MessageListener, Runnable {
   private static final DebugLogger DEBUG_LOGGER;
   private static final AuthenticatedSubject KERNEL_ID;
   private final JMSConnectionPoller connectionPoller;
   private final AuthenticatedSubject runAsSubject;
   private final Context envContext;
   private final EJBTransactionRuntimeMBeanImpl txMBean;
   private final LinkedList listenerList;
   private final LinkedList messageList;
   private final MDListener parent;
   private final MessageDrivenBeanInfo info;
   private final MessageDrivenEJBRuntimeMBean rtMBean;
   private final MessageDrivenManagerIntf manager;
   private final PoolIntf pool;
   private final WorkManager wm;
   private final TransactionManager tranManager;
   private final boolean isTransacted;
   private final int numChildren;
   private final boolean useAcknowledgePreviousMode;
   private final boolean isDestinationOnRemoteDomain;
   private final String txName;
   private final boolean syncNoTranMode;
   private volatile MDBSession wrappedSession;
   private volatile Session session;
   private MDMessage mDMessage;
   private Throwable executeException;
   private Transaction transaction;
   private boolean rolledBack;
   private boolean recovered;
   private int numWaiters;
   private boolean detached;

   MDListener(MDListener parent, JMSConnectionPoller cp, int numChildren, Session session, MDBSession wrappedSession, int acknowledgeMode, MessageDrivenEJBRuntimeMBean mb, MessageDrivenBeanInfo mdbi, MessageDrivenManagerIntf manager, boolean useAcknowledgePreviousMode, boolean isDestinationInRemoteDomain, WorkManager wm, AuthenticatedSubject runAsSubject, boolean syncNoTranMode) {
      this.info = mdbi;
      this.manager = manager;
      this.pool = manager.getPool();
      this.isTransacted = this.info.isOnMessageTransacted();
      this.parent = parent;
      this.envContext = manager.getEnvironmentContext();
      this.session = session;
      this.wrappedSession = wrappedSession;
      this.rtMBean = mb;
      this.txMBean = (EJBTransactionRuntimeMBeanImpl)mb.getTransactionRuntime();
      this.useAcknowledgePreviousMode = useAcknowledgePreviousMode;
      this.isDestinationOnRemoteDomain = isDestinationInRemoteDomain;
      this.tranManager = TransactionService.getWeblogicTransactionManager();
      this.wm = wm;
      this.syncNoTranMode = syncNoTranMode;
      this.detached = session == null && parent == null;
      this.connectionPoller = cp;
      this.runAsSubject = runAsSubject;
      this.numChildren = numChildren;
      if (numChildren > 0) {
         this.listenerList = new LinkedList();

         for(int j = 0; j < numChildren; ++j) {
            this.putListener(new MDListener(this, this.connectionPoller, 0, (Session)null, (MDBSession)null, acknowledgeMode, this.rtMBean, this.info, manager, useAcknowledgePreviousMode, isDestinationInRemoteDomain, wm, runAsSubject, syncNoTranMode));
         }
      } else {
         this.listenerList = null;
      }

      if (acknowledgeMode == 2 && !this.isTransacted) {
         this.messageList = new LinkedList();
      } else {
         this.messageList = null;
      }

      this.txName = "[EJB " + this.info.getBeanClassName() + "." + this.info.getOnMessageMethodInfo().getSignature() + "]";
   }

   boolean isTransacted() {
      return this.isTransacted;
   }

   private MDBSession getWrappedSession() {
      return this.parent == null ? this.wrappedSession : this.parent.wrappedSession;
   }

   private Session getSession() {
      return this.parent == null ? this.session : this.parent.session;
   }

   boolean isDetached() {
      return this.parent == null ? this.detached : this.parent.detached;
   }

   void detach() {
      if (this.parent == null) {
         assert this.messageList == null || this.messageList.isEmpty() : "Cannot detach a MDListener with pending message list";

         assert this.mDMessage == null : "Cannot detach a MDListener with pending message";

         this.detached = true;
         this.wrappedSession = null;
         this.session = null;
      }
   }

   void attach(Session s, MDBSession ws) {
      if (this.parent == null) {
         assert this.messageList == null || this.messageList.isEmpty() : "Cannot attach a MDListener with pending message list";

         assert this.mDMessage == null : "Cannot attach a MDListener with pending message";

         this.session = s;
         this.wrappedSession = ws;
         this.detached = false;
      }
   }

   private MDListener getListener() {
      synchronized(this.listenerList) {
         while(this.listenerList.isEmpty()) {
            try {
               ++this.numWaiters;
               this.listenerList.wait();
            } catch (InterruptedException var8) {
            } finally {
               --this.numWaiters;
            }
         }

         return (MDListener)this.listenerList.removeFirst();
      }
   }

   private void putListener(MDListener listener) {
      if (this.messageList != null && listener.mDMessage != null) {
         MDMessage mdMessage = null;
         synchronized(this.messageList) {
            if (listener.recovered) {
               listener.mDMessage.setRecovered();

               for(int i = 0; i < this.messageList.size(); ++i) {
                  if (((MDMessage)this.messageList.get(i)).isRecovered()) {
                     mdMessage = (MDMessage)this.messageList.remove(i);
                  }
               }
            } else {
               listener.mDMessage.setAcknowledged();

               while(((MDMessage)this.messageList.getFirst()).isAcknowledged()) {
                  mdMessage = (MDMessage)this.messageList.removeFirst();
                  if (this.messageList.isEmpty()) {
                     break;
                  }
               }
            }
         }

         if (mdMessage != null && !listener.recovered) {
            this.acknowledgeMessage(mdMessage);
         }
      }

      listener.mDMessage = null;
      listener.transaction = null;
      synchronized(this.listenerList) {
         this.listenerList.add(listener);
         if (this.numWaiters > 0) {
            this.listenerList.notify();
         }

      }
   }

   public void onMessage(final Message message) {
      assert !this.isDetached() : "MDListener.onMessage cannot be called in detached mode";

      try {
         ManagedInvocationContext mic = this.connectionPoller.setCIC();
         Throwable var3 = null;

         try {
            EJBContextManager.pushEjbContext(this.manager.getMessageDrivenContext());
            weblogic.transaction.Transaction tx = null;
            Throwable err = null;

            try {
               if (this.isTransacted) {
                  this.tranManager.suspend();
                  int txTimeoutSeconds = this.getTransactionTimeoutSeconds();
                  if (txTimeoutSeconds < 0) {
                     txTimeoutSeconds = 0;
                  }

                  this.connectionPoller.beginTransaction(this.tranManager, this.txName, txTimeoutSeconds);
                  tx = TransactionService.getWeblogicTransaction();
                  Integer isolationLevel = this.info.getOnMessageTxIsolationLevel();
                  if (isolationLevel != null) {
                     tx.setProperty("ISOLATION LEVEL", isolationLevel);
                  }

                  final Session sessionFinalVar = this.getSession();
                  if (sessionFinalVar instanceof MDBTransaction) {
                     if (!this.isCurrentSubjectKernelIdentity() && !this.isDestinationOnRemoteDomain) {
                        ((MDBTransaction)sessionFinalVar).associateTransaction(message);
                     } else {
                        this.connectionPoller.doPrivilegedJMSAction(new PrivilegedExceptionAction() {
                           public Object run() throws Exception {
                              ((MDBTransaction)sessionFinalVar).associateTransaction(message);
                              return null;
                           }
                        });
                     }
                  }
               }
            } catch (JMSException | SystemException | NotSupportedException var123) {
               err = var123;
            }

            if (err != null) {
               this.executeException = err;

               try {
                  try {
                     this.recoverSession(this.getSession());
                  } catch (JMSException var121) {
                     if (DEBUG_LOGGER.isDebugEnabled()) {
                        this.debug("onMessage ", var121);
                        return;
                     }
                  }

                  return;
               } finally {
                  try {
                     if (tx != null) {
                        this.txMBean.incrementTransactionsRolledBack();
                        if (tx.isTimedOut()) {
                           this.txMBean.incrementTransactionsTimedOut();
                        }

                        tx.rollback();
                     }
                  } catch (SystemException var118) {
                     if (DEBUG_LOGGER.isDebugEnabled()) {
                        this.debug("onMessage ", var118);
                     }
                  } catch (Exception var119) {
                     if (DEBUG_LOGGER.isDebugEnabled()) {
                        this.debug("onMessage ", var119);
                     }
                  } finally {
                     if (this.connectionPoller.isPrintErrorMessage(err)) {
                        EJBLogger.logerrorStartingMDBTx(err.toString());
                     }

                  }

               }
            }

            this.transactionalOnMessage(message, true);
         } catch (Throwable var124) {
            var3 = var124;
            throw var124;
         } finally {
            if (mic != null) {
               if (var3 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var117) {
                     var3.addSuppressed(var117);
                  }
               } else {
                  mic.close();
               }
            }

         }
      } finally {
         EJBContextManager.popEjbContext();
      }

   }

   boolean transactionalOnMessage(Message message, boolean doCommit) {
      if (this.numChildren != 0 && doCommit) {
         MDListener listener = this.getListener();
         listener.mDMessage = new MDMessage(message, this.getSession(), this.useAcknowledgePreviousMode);
         if (this.messageList != null) {
            synchronized(this.messageList) {
               this.messageList.add(listener.mDMessage);
            }
         }

         if (this.isTransacted) {
            listener.transaction = this.tranManager.forceSuspend();
         }

         this.wm.schedule(listener);
         return false;
      } else {
         if (message != null) {
            this.mDMessage = new MDMessage(message, this.getSession(), this.useAcknowledgePreviousMode);
            if (this.messageList != null) {
               synchronized(this.messageList) {
                  this.messageList.add(this.mDMessage);
               }
            }
         }

         return this.execute(doCommit);
      }
   }

   private boolean execute(boolean doCommit) {
      assert !this.isDetached() : "MDListener.execute cannot be called in detached mode";

      boolean returnCode = false;
      boolean getBeanFailed = false;
      this.executeException = null;
      this.rolledBack = false;
      this.recovered = false;
      if (this.parent != null && this.transaction != null) {
         this.tranManager.forceResume(this.transaction);
         this.transaction = null;
      }

      weblogic.transaction.Transaction tx = TransactionService.getWeblogicTransaction();
      Object bean = null;
      InvocationWrapper iw = null;
      boolean var35 = false;

      boolean var9;
      label1147: {
         try {
            var35 = true;
            EJBRuntimeUtils.pushEnvironment(this.envContext);
            SecurityHelper.pushRunAsSubject(KERNEL_ID, this.runAsSubject);
            Thread currentThread = Thread.currentThread();
            ClassLoader clSave = currentThread.getContextClassLoader();
            currentThread.setContextClassLoader(this.info.getClassLoader());

            try {
               bean = this.pool.getBean((long)this.getTransactionTimeoutSeconds() * 1000L);
            } catch (Exception var59) {
               if (clSave != null) {
                  currentThread.setContextClassLoader(clSave);
               }

               getBeanFailed = true;
               throw var59;
            }

            try {
               MethodInvocationHelper.pushMethodObject(this.info);
               if (bean instanceof JmsMessageDrivenBean) {
                  this.getWrappedSession().reOpen();
                  ((JmsMessageDrivenBean)bean).setSession((Session)this.getWrappedSession());
               }

               iw = InvocationWrapper.newInstance(this.info.getOnMessageMethodDescriptor());
               InvocationContextStack.push(iw);
               iw.setBean(bean);
               if (this.mDMessage != null) {
                  ((MessageListener)bean).onMessage(this.mDMessage.getMessage());
               }
            } finally {
               InvocationContextStack.pop();
               MethodInvocationHelper.popMethodObject(this.info);
               if (clSave != null) {
                  currentThread.setContextClassLoader(clSave);
               }

               if (bean instanceof JmsMessageDrivenBean) {
                  ((JmsMessageDrivenBean)bean).setSession((Session)null);
                  this.getWrappedSession().close();
               }

            }

            this.pool.releaseBean(bean);
            if (tx != null) {
               switch (tx.getStatus()) {
                  case 0:
                     if (doCommit) {
                        try {
                           tx.commit();
                           this.txMBean.incrementTransactionsCommitted();
                        } catch (Exception var63) {
                           String errorMsg = null;
                           if (var63 instanceof RollbackException && EJBRuntimeUtils.isOptimisticLockException(((RollbackException)var63).getNested())) {
                              errorMsg = ((RollbackException)var63).getNested().getMessage();
                           } else {
                              errorMsg = StackTraceUtilsClient.throwable2StackTrace(var63);
                           }

                           EJBLogger.logErrorDuringCommit(tx.toString(), errorMsg);
                           this.executeException = var63;
                        }
                     } else {
                        returnCode = true;
                     }
                     break;
                  case 1:
                  case 9:
                     this.txMBean.incrementTransactionsRolledBack();
                     if (tx.isTimedOut()) {
                        this.txMBean.incrementTransactionsTimedOut();
                     }

                     try {
                        this.rolledBack = true;
                        tx.rollback();
                     } catch (Exception var52) {
                        EJBLogger.logErrorDuringRollback(tx.toString(), StackTraceUtilsClient.throwable2StackTrace(var52));
                        this.executeException = var52;
                     }

                     EJBLogger.logTxRolledbackInfo(this.info.getEJBName(), "STATUS_MARKED_ROLLBACK", tx.toString());
                  case 2:
                  case 3:
                  case 5:
                  case 6:
                  case 7:
                  case 8:
                  default:
                     break;
                  case 4:
                     this.txMBean.incrementTransactionsRolledBack();
                     if (tx.isTimedOut()) {
                        this.txMBean.incrementTransactionsTimedOut();
                     }
               }
            } else if (this.syncNoTranMode) {
               try {
                  if (this.mDMessage != null) {
                     this.mDMessage.acknowledge();
                  }
               } catch (Exception var62) {
                  if (DEBUG_LOGGER.isDebugEnabled()) {
                     this.debug("execute ", var62);
                  }

                  try {
                     this.getSession().rollback();
                  } catch (Exception var51) {
                  }
               }
            }

            this.connectionPoller.initDeliveryFailureParams();
            var9 = returnCode;
            var35 = false;
            break label1147;
         } catch (Throwable var64) {
            Throwable th = var64;
            this.executeException = var64;
            if (!getBeanFailed) {
               if (var64 instanceof NonDestructiveRuntimeException) {
                  this.pool.releaseBean(bean);
               } else {
                  this.manager.destroyInstance(iw, var64);
               }
            }

            boolean printErrorMessage = this.connectionPoller.isPrintErrorMessage(this.executeException);
            if (printErrorMessage) {
               EJBLogger.logExcepInOnMessageCallOnMDB(var64);
            }

            if (this.isTransacted) {
               try {
                  this.rolledBack = true;
                  tx.rollback();
               } catch (Exception var55) {
                  if (printErrorMessage) {
                     EJBLogger.logErrorOnRollback(var55);
                  }
               }

               this.txMBean.incrementTransactionsRolledBack();
               if (tx.isTimedOut()) {
                  this.txMBean.incrementTransactionsTimedOut();
               }

               if (printErrorMessage) {
                  EJBLogger.logTxRolledbackInfo(this.info.getEJBName(), StackTraceUtilsClient.throwable2StackTraceTruncated(var64, 10), tx.toString());
                  var35 = false;
               } else {
                  var35 = false;
               }
            } else {
               try {
                  if (tx != null && tx.getStatus() != 6) {
                     try {
                        this.rolledBack = true;
                        tx.rollback();
                     } catch (Exception var57) {
                        if (printErrorMessage) {
                           EJBLogger.logErrorOnRollback(var57);
                        }
                     }

                     this.txMBean.incrementTransactionsRolledBack();
                     if (tx.isTimedOut()) {
                        this.txMBean.incrementTransactionsTimedOut();
                     }

                     if (printErrorMessage) {
                        EJBLogger.logTxRolledbackInfo(this.info.getEJBName(), StackTraceUtilsClient.throwable2StackTraceTruncated(th, 10), tx.toString());
                     }
                  }
               } catch (Exception var58) {
               }

               try {
                  if (this.syncNoTranMode) {
                     this.getSession().recover();
                     var35 = false;
                  } else {
                     label1050: {
                        if (!this.info.isDestinationQueue()) {
                           if (!this.info.isDestinationTopic()) {
                              var35 = false;
                              break label1050;
                           }

                           if (this.parent != null) {
                              var35 = false;
                              break label1050;
                           }
                        }

                        this.recoverSession(this.getSession());
                        this.recovered = true;
                        var35 = false;
                     }
                  }
               } catch (JMSException var56) {
                  if (printErrorMessage) {
                     EJBLogger.logExceptionRecoveringJMSSession(this.info.getDisplayName(), var56);
                     var35 = false;
                  } else {
                     var35 = false;
                  }
               }
            }
         } finally {
            if (var35) {
               EJBRuntimeUtils.popEnvironment();
               SecurityHelper.popRunAsSubject(KERNEL_ID);
               if (this.syncNoTranMode) {
                  this.mDMessage = null;
                  this.transaction = null;
               } else if (this.parent != null) {
                  this.parent.putListener(this);
               } else {
                  if (this.messageList != null && this.mDMessage != null) {
                     MDMessage mdMessage = null;
                     synchronized(this.messageList) {
                        if (this.recovered) {
                           this.mDMessage.setRecovered();

                           for(int i = 0; i < this.messageList.size(); ++i) {
                              if (((MDMessage)this.messageList.get(i)).isRecovered()) {
                                 mdMessage = (MDMessage)this.messageList.remove(i);
                              }
                           }
                        } else {
                           this.mDMessage.setAcknowledged();

                           while(((MDMessage)this.messageList.getFirst()).isAcknowledged()) {
                              mdMessage = (MDMessage)this.messageList.removeFirst();
                              if (this.messageList.isEmpty()) {
                                 break;
                              }
                           }
                        }
                     }

                     if (mdMessage != null && !this.recovered) {
                        this.acknowledgeMessage(mdMessage);
                     }
                  }

                  this.mDMessage = null;
                  this.transaction = null;
               }

            }
         }

         EJBRuntimeUtils.popEnvironment();
         SecurityHelper.popRunAsSubject(KERNEL_ID);
         if (this.syncNoTranMode) {
            this.mDMessage = null;
            this.transaction = null;
         } else if (this.parent != null) {
            this.parent.putListener(this);
         } else {
            if (this.messageList != null && this.mDMessage != null) {
               MDMessage mdMessage = null;
               synchronized(this.messageList) {
                  if (this.recovered) {
                     this.mDMessage.setRecovered();

                     for(int i = 0; i < this.messageList.size(); ++i) {
                        if (((MDMessage)this.messageList.get(i)).isRecovered()) {
                           mdMessage = (MDMessage)this.messageList.remove(i);
                        }
                     }
                  } else {
                     this.mDMessage.setAcknowledged();

                     while(((MDMessage)this.messageList.getFirst()).isAcknowledged()) {
                        mdMessage = (MDMessage)this.messageList.removeFirst();
                        if (this.messageList.isEmpty()) {
                           break;
                        }
                     }
                  }
               }

               if (mdMessage != null && !this.recovered) {
                  this.acknowledgeMessage(mdMessage);
               }
            }

            this.mDMessage = null;
            this.transaction = null;
         }

         return returnCode;
      }

      EJBRuntimeUtils.popEnvironment();
      SecurityHelper.popRunAsSubject(KERNEL_ID);
      if (this.syncNoTranMode) {
         this.mDMessage = null;
         this.transaction = null;
      } else if (this.parent != null) {
         this.parent.putListener(this);
      } else {
         if (this.messageList != null && this.mDMessage != null) {
            MDMessage mdMessage = null;
            synchronized(this.messageList) {
               if (this.recovered) {
                  this.mDMessage.setRecovered();

                  for(int i = 0; i < this.messageList.size(); ++i) {
                     if (((MDMessage)this.messageList.get(i)).isRecovered()) {
                        mdMessage = (MDMessage)this.messageList.remove(i);
                     }
                  }
               } else {
                  this.mDMessage.setAcknowledged();

                  while(((MDMessage)this.messageList.getFirst()).isAcknowledged()) {
                     mdMessage = (MDMessage)this.messageList.removeFirst();
                     if (this.messageList.isEmpty()) {
                        break;
                     }
                  }
               }
            }

            if (mdMessage != null && !this.recovered) {
               this.acknowledgeMessage(mdMessage);
            }
         }

         this.mDMessage = null;
         this.transaction = null;
      }

      return var9;
   }

   private void recoverSession(final Session jmsSession) throws JMSException {
      if (!this.isCurrentSubjectKernelIdentity() && !this.isDestinationOnRemoteDomain) {
         jmsSession.recover();
      } else {
         this.connectionPoller.doPrivilegedJMSAction(new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               jmsSession.recover();
               return null;
            }
         });
      }

   }

   private void acknowledgeMessage(final MDMessage mdMessage) {
      if (this.isCurrentSubjectKernelIdentity()) {
         try {
            this.connectionPoller.doPrivilegedJMSAction(new PrivilegedExceptionAction() {
               public Object run() throws Exception {
                  mdMessage.acknowledge();
                  return null;
               }
            });
         } catch (JMSException var3) {
         }
      } else {
         mdMessage.acknowledge();
      }

   }

   private boolean isCurrentSubjectKernelIdentity() {
      AuthenticatedSubject as = SecurityManager.getCurrentSubject(KERNEL_ID);
      return SecurityServiceManager.isKernelIdentity(as) || SecurityServiceManager.isServerIdentity(as);
   }

   public void run() {
      ManagedInvocationContext mic = this.connectionPoller.setCIC();
      Throwable var2 = null;

      try {
         this.execute(true);
      } catch (Throwable var11) {
         var2 = var11;
         throw var11;
      } finally {
         if (mic != null) {
            if (var2 != null) {
               try {
                  mic.close();
               } catch (Throwable var10) {
                  var2.addSuppressed(var10);
               }
            } else {
               mic.close();
            }
         }

      }

   }

   int getTransactionTimeoutSeconds() {
      return this.info.getTransactionTimeoutSeconds();
   }

   Throwable getExecuteException() {
      return this.executeException;
   }

   boolean getRolledBack() {
      return this.rolledBack;
   }

   private void debug(String s, Throwable th) {
      DEBUG_LOGGER.debug("[MDListener] " + s, th);
   }

   static {
      DEBUG_LOGGER = EJBDebugService.invokeLogger;
      KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }

   private static final class MDMessage {
      private final Message message;
      private final Session session;
      private final boolean acknowledgePreviousMode;
      private boolean isAcknowledged;
      private boolean isRecovered;

      MDMessage(Message message, Session session, boolean useAcknowledgePreviousMode) {
         this.message = message;
         this.session = session;
         this.acknowledgePreviousMode = useAcknowledgePreviousMode;
      }

      void setAcknowledged() {
         this.isAcknowledged = true;
      }

      void setRecovered() {
         this.isRecovered = true;
      }

      boolean isAcknowledged() {
         return this.isAcknowledged;
      }

      boolean isRecovered() {
         return this.isRecovered;
      }

      Message getMessage() {
         return this.message;
      }

      void acknowledge() {
         try {
            if (!this.acknowledgePreviousMode) {
               this.message.acknowledge();
            } else {
               ((WLSessionImpl)this.session).acknowledge(this.message, 2, false);
            }
         } catch (JMSException var2) {
            EJBLogger.logStackTrace(var2);
         }

      }
   }
}
