package weblogic.ejb.container.internal;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import javax.ejb.AccessLocalException;
import javax.ejb.ConcurrentAccessException;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;
import javax.ejb.EnterpriseBean;
import javax.ejb.FinderException;
import javax.ejb.NoSuchEJBException;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.RemoveException;
import javax.transaction.Transaction;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfo;
import weblogic.diagnostics.instrumentation.ValueHandlingInfo;
import weblogic.ejb.PreparedQuery;
import weblogic.ejb.Query;
import weblogic.ejb.WLQueryProperties;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.InternalException;
import weblogic.ejb.container.interfaces.BaseEJBLocalObjectIntf;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.BeanManager;
import weblogic.ejb.container.interfaces.EntityBeanInfo;
import weblogic.ejb.container.interfaces.InvalidationBeanManager;
import weblogic.ejb.container.interfaces.LocalQueryHandler;
import weblogic.ejb.container.interfaces.QueryCache;
import weblogic.ejb.container.manager.BaseEntityManager;
import weblogic.ejb.container.manager.TTLManager;
import weblogic.ejb.container.persistence.spi.CMPBeanDescriptor;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.logging.Loggable;
import weblogic.transaction.AppSetRollbackOnlyException;
import weblogic.transaction.RollbackException;
import weblogic.utils.StackTraceUtilsClient;

public abstract class EntityEJBLocalHome extends BaseEJBLocalHome implements LocalQueryHandler {
   public static final int SCALAR_FINDER = 1;
   public static final int ENUM_FINDER = 2;
   public static final int COLL_FINDER = 4;
   private static final DebugLogger runtimeLogger;
   private Method findByPrimaryKeyMethod;
   private BaseEntityManager entityManager = null;
   public MethodDescriptor md_createQuery = null;
   public MethodDescriptor md_prepareQuery = null;
   // $FF: synthetic field
   static final boolean $assertionsDisabled;
   static final long serialVersionUID = -1093372510598827270L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.ejb.container.internal.EntityEJBLocalHome");
   static final DelegatingMonitor _WLDF$INST_FLD_EJB_Diagnostic_Home_Remove_Around_Medium;
   static final DelegatingMonitor _WLDF$INST_FLD_EJB_Diagnostic_Database_Access_Around_High;
   static final DelegatingMonitor _WLDF$INST_FLD_EJB_Diagnostic_Home_Create_Around_Medium;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;
   static final JoinPoint _WLDF$INST_JPFLD_1;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_1;
   static final JoinPoint _WLDF$INST_JPFLD_2;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_2;
   static final JoinPoint _WLDF$INST_JPFLD_3;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_3;
   static final JoinPoint _WLDF$INST_JPFLD_4;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_4;

   public EntityEJBLocalHome(Class eoClass) {
      super(eoClass);
   }

   public void setup(BeanInfo bi, BeanManager beanManager) throws WLDeploymentException {
      EntityBeanInfo ebi = (EntityBeanInfo)bi;
      super.setup(bi, beanManager);
      this.entityManager = (BaseEntityManager)beanManager;
      Class beanIntf = ebi.getGeneratedBeanInterface();
      Class pkClass = ebi.getPrimaryKeyClass();
      if (ebi.isUnknownPrimaryKey() && !ebi.getIsBeanManagedPersistence()) {
         CMPBeanDescriptor bd = ebi.getCMPInfo().getCMPBeanDescriptor(ebi.getEJBName());
         pkClass = bd.getPrimaryKeyClass();
      }

      try {
         this.findByPrimaryKeyMethod = beanIntf.getMethod("ejbFindByPrimaryKey", pkClass);
      } catch (NoSuchMethodException var7) {
         throw new AssertionError(var7);
      }
   }

   public BaseEJBLocalObjectIntf allocateELO() {
      throw new AssertionError("Must have pk for entity beans");
   }

   public BaseEJBLocalObjectIntf allocateELO(Object pk) {
      EntityEJBLocalObject elo = null;

      try {
         elo = (EntityEJBLocalObject)this.eloClass.newInstance();
      } catch (InstantiationException var4) {
         throw new AssertionError(var4);
      } catch (IllegalAccessException var5) {
         throw new AssertionError(var5);
      }

      elo.setBeanInfo(this.getBeanInfo());
      elo.setEJBLocalHome(this);
      elo.setPrimaryKey(pk);
      elo.setBeanManager(this.getBeanManager());
      elo.setIsEJB30ClientView(false);
      return elo;
   }

   protected EJBLocalObject create(MethodDescriptor md, Method createMethod, Method postCreateMethod, Object[] args) throws Exception {
      return this.create(md, createMethod, postCreateMethod, args, (Object)null);
   }

   protected EJBLocalObject create(MethodDescriptor md, Method createMethod, Method postCreateMethod, Object[] args, Object var5) throws Exception {
      LocalHolder var12;
      if ((var12 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var12.argsCapture) {
            var12.args = new Object[6];
            Object[] var10000 = var12.args;
            var10000[0] = this;
            var10000[1] = md;
            var10000[2] = createMethod;
            var10000[3] = postCreateMethod;
            var10000[4] = args;
            var10000[5] = var5;
         }

         InstrumentationSupport.createDynamicJoinPoint(var12);
         InstrumentationSupport.preProcess(var12);
         var12.resetPostBegin();
      }

      EJBLocalObject var36;
      try {
         EJBLocalObject var35;
         try {
            EJBRuntimeUtils.pushEnvironment(this.beanManager.getEnvironmentContext());
            if (debugLogger.isDebugEnabled()) {
               debug("[EntityEJBHome] Creating a bean from md: " + md);
            }

            EJBLocalObject elo = null;
            InvocationWrapper wrap = this.preHomeInvoke(md, new EJBContextHandler(md, args));
            Throwable ee = null;

            try {
               if (!$assertionsDisabled && wrap.getInvokeTx() == null) {
                  throw new AssertionError();
               }

               BeanManager manager = this.getBeanManager();
               elo = manager.localCreate(wrap, createMethod, postCreateMethod, args);
               wrap.setPrimaryKey(elo.getPrimaryKey());
            } catch (InternalException var28) {
               InternalException ie = var28;
               ee = var28.detail;
               if (this.deploymentInfo.getExceptionInfo(createMethod, ee).isAppException()) {
                  throw (Exception)ee;
               }

               this.handleSystemException(wrap, var28);
               throw new AssertionError("Should never have reached here");
            } catch (Throwable var29) {
               Throwable th = var29;
               ee = var29;
               this.handleSystemException(wrap, var29);
               throw new AssertionError("Should never have reached here");
            } finally {
               this.postHomeInvoke(wrap, ee);
            }

            var35 = elo;
         } finally {
            EJBRuntimeUtils.popEnvironment();
         }

         var36 = var35;
      } catch (Throwable var32) {
         if (var12 != null) {
            var12.th = var32;
            var12.ret = null;
            InstrumentationSupport.postProcess(var12);
         }

         throw var32;
      }

      if (var12 != null) {
         var12.ret = var36;
         InstrumentationSupport.postProcess(var12);
      }

      return var36;
   }

   public void remove(MethodDescriptor md, Object pk) throws RemoveException, EJBException {
      LocalHolder var9;
      if ((var9 = LocalHolder.getInstance(_WLDF$INST_JPFLD_1, _WLDF$INST_JPFLD_JPMONS_1)) != null) {
         if (var9.argsCapture) {
            var9.args = new Object[3];
            Object[] var10000 = var9.args;
            var10000[0] = this;
            var10000[1] = md;
            var10000[2] = pk;
         }

         InstrumentationSupport.createDynamicJoinPoint(var9);
         InstrumentationSupport.preProcess(var9);
         var9.resetPostBegin();
      }

      try {
         try {
            EJBRuntimeUtils.pushEnvironment(this.beanManager.getEnvironmentContext());
            InvocationWrapper wrap = this.preHomeInvoke(md, EJBContextHandler.EMPTY);
            Throwable ee = null;

            try {
               wrap.setPrimaryKey(pk);

               try {
                  this.getBeanManager().remove(wrap);
               } catch (InternalException var28) {
                  if (!(var28.getCause() instanceof NoSuchEJBException) && !(var28.getCause() instanceof ConcurrentAccessException)) {
                     throw var28;
                  }

                  throw new InternalException(var28.getMessage());
               }
            } catch (InternalException var29) {
               ee = var29.detail;
               if (this.deploymentInfo.getExceptionInfo(md.getMethod(), ee).isAppException()) {
                  Exception removeException = (Exception)ee;
                  if (removeException instanceof EJBException) {
                     throw (EJBException)removeException;
                  }

                  if (removeException instanceof RemoveException) {
                     throw (RemoveException)removeException;
                  }

                  throw new AssertionError("Invalid Exception thrown from remove: " + StackTraceUtilsClient.throwable2StackTrace(removeException));
               }

               this.handleSystemException(wrap, var29);
               throw new AssertionError("Should never have reached here");
            } catch (Throwable var30) {
               ee = var30;
               this.handleSystemException(wrap, var30);
               throw new AssertionError("Should never have reached here");
            } finally {
               this.postHomeInvoke(wrap, ee);
            }
         } finally {
            EJBRuntimeUtils.popEnvironment();
         }
      } catch (Throwable var33) {
         if (var9 != null) {
            var9.th = var33;
            InstrumentationSupport.postProcess(var9);
         }

         throw var33;
      }

      if (var9 != null) {
         InstrumentationSupport.postProcess(var9);
      }

   }

   protected Object findByPrimaryKey(MethodDescriptor md, Object pk) throws Exception {
      return this.findByPrimaryKey(md, pk, (Object)null);
   }

   protected Object findByPrimaryKey(MethodDescriptor md, Object pk, Object var3) throws Exception {
      LocalHolder var11;
      if ((var11 = LocalHolder.getInstance(_WLDF$INST_JPFLD_2, _WLDF$INST_JPFLD_JPMONS_2)) != null) {
         if (var11.argsCapture) {
            var11.args = new Object[4];
            Object[] var10000 = var11.args;
            var10000[0] = this;
            var10000[1] = md;
            var10000[2] = pk;
            var10000[3] = var3;
         }

         InstrumentationSupport.createDynamicJoinPoint(var11);
         InstrumentationSupport.preProcess(var11);
         var11.resetPostBegin();
      }

      Object var39;
      try {
         if (!$assertionsDisabled && md == null) {
            throw new AssertionError();
         }

         if (pk == null) {
            throw new ObjectNotFoundException("Primary key was null!");
         }

         Object var7;
         try {
            EJBRuntimeUtils.pushEnvironment(this.beanManager.getEnvironmentContext());
            Object eloOrPojo = null;
            InvocationWrapper wrap = this.preHomeInvoke(md, EJBContextHandler.EMPTY);
            Throwable ee = null;

            try {
               wrap.setPrimaryKey(pk);
               eloOrPojo = this.getBeanManager().localFindByPrimaryKey(wrap, pk);
            } catch (InternalException var34) {
               ee = var34.detail;
               if (this.deploymentInfo.getExceptionInfo(this.findByPrimaryKeyMethod, ee).isAppException()) {
                  throw (Exception)ee;
               }

               this.handleSystemException(wrap, var34);
               throw new AssertionError("Should never have reached here");
            } catch (Throwable var35) {
               ee = var35;
               this.handleSystemException(wrap, var35);
               throw new AssertionError("Should never have reached here");
            } finally {
               if (wrap.getInvokeTx() == null && !((EntityBeanInfo)this.beanInfo).getIsBeanManagedPersistence()) {
                  try {
                     this.getBeanManager().beforeCompletion(wrap);
                     this.getBeanManager().afterCompletion(wrap);
                  } catch (InternalException var33) {
                     if (EJBRuntimeUtils.isAppException(md.getMethod(), var33)) {
                        throw (RemoveException)var33.detail;
                     }

                     this.handleSystemException(wrap, var33);
                     throw new AssertionError("Should never have reached here");
                  }
               }

               this.postHomeInvoke(wrap, ee);
            }

            var7 = eloOrPojo;
         } finally {
            EJBRuntimeUtils.popEnvironment();
         }

         var39 = var7;
      } catch (Throwable var38) {
         if (var11 != null) {
            var11.th = var38;
            var11.ret = null;
            InstrumentationSupport.postProcess(var11);
         }

         throw var38;
      }

      if (var11 != null) {
         var11.ret = var39;
         InstrumentationSupport.postProcess(var11);
      }

      return var39;
   }

   protected InvocationWrapper preEntityHomeInvoke(MethodDescriptor md) throws EJBException {
      InvocationWrapper wrap = null;
      EJBRuntimeUtils.pushEnvironment(this.beanManager.getEnvironmentContext());

      try {
         wrap = this.preHomeInvoke(md, EJBContextHandler.EMPTY);
      } catch (EJBException var10) {
         EJBRuntimeUtils.popEnvironment();
         throw var10;
      }

      try {
         if (!$assertionsDisabled && wrap.getInvokeTx() == null) {
            throw new AssertionError();
         } else {
            EnterpriseBean bean = this.getBeanManager().preHomeInvoke(wrap);
            wrap.setBean(bean);
            if (debugLogger.isDebugEnabled()) {
               debug("Manager.preHomeInvoke returned a bean:" + bean);
            }

            return wrap;
         }
      } catch (Throwable var9) {
         Throwable th = var9;

         try {
            if (debugLogger.isDebugEnabled()) {
               debug("Manager's preHomeInvoke threw " + th);
            }

            this.handleSystemException(wrap, th);
            throw new AssertionError("Should never reach here");
         } finally {
            EJBRuntimeUtils.popEnvironment();
            this.postHomeInvokeCleanup(wrap);
         }
      }
   }

   protected int postEntityHomeInvokeTxRetry(InvocationWrapper wrap, Throwable ee, int txRetryCount) throws Exception {
      int nextTxRetryCount = this.getNextTxRetryCount(txRetryCount);
      boolean retry = this.postEntityHomeInvoke1(nextTxRetryCount, wrap, ee);
      if (!retry) {
         nextTxRetryCount = -1;
      }

      if (nextTxRetryCount >= 0) {
         this.retryEntityHomePreInvoke(wrap);
         ee = null;
      }

      if (debugLogger.isDebugEnabled()) {
         debug("postEntityHomeInvokeTxRetry returning with nextTxRetryCount  = " + nextTxRetryCount + "\n");
      }

      return nextTxRetryCount;
   }

   private boolean postEntityHomeInvoke1(int nextTxRetryCount, InvocationWrapper wrap, Throwable ee) throws Exception {
      MethodDescriptor md = wrap.getMethodDescriptor();
      if (debugLogger.isDebugEnabled()) {
         debug("[BaseEJBLocalHome] postHomeInvoke1 called with nextTxRetryCount = " + nextTxRetryCount + "\n wrap:" + wrap + " Exception: " + ee + " on: " + this);
         if (null != ee) {
            ee.printStackTrace();
         }
      }

      Transaction invokeTx = wrap.getInvokeTx();
      Transaction callerTx = wrap.getCallerTx();
      Method m = md.getMethod();
      if (ee != null && !this.deploymentInfo.getExceptionInfo(m, ee).isAppException()) {
         wrap.setSystemExceptionOccured();

         try {
            this.getBeanManager().destroyInstance(wrap, ee);
         } catch (InternalException var10) {
            this.handleSystemException(wrap, ee);
            throw new AssertionError("Should never be reached");
         }

         this.handleSystemException(wrap, ee);
         throw new AssertionError("Should never be reached");
      } else {
         if (wrap.getInvokeTx() == null) {
            try {
               this.getBeanManager().beforeCompletion(wrap);
               this.getBeanManager().afterCompletion(wrap);
            } catch (InternalException var14) {
               if (EJBRuntimeUtils.isAppException(m, var14)) {
                  throw EJBRuntimeUtils.asEJBException("Error during bean cleanup ", var14.detail);
               }

               this.handleSystemException(wrap, var14);
               throw new AssertionError("Should never have reached here");
            }
         }

         if (wrap.runningInOurTx()) {
            if (wrap.hasRolledBack()) {
               if (debugLogger.isDebugEnabled()) {
                  debug(" our tx marked for Rollback, attempt to rollback ");
               }

               try {
                  invokeTx.rollback();
                  if (debugLogger.isDebugEnabled()) {
                     debug(" Rollback succeeded. ");
                  }
               } catch (Exception var11) {
                  EJBLogger.logErrorDuringRollback1(invokeTx.toString(), StackTraceUtilsClient.throwable2StackTrace(var11));
               }

               if (wrap.isSystemRollback()) {
                  if (debugLogger.isDebugEnabled()) {
                     debug(" system Rollback, throw exception");
                  }

                  this.destroyInstanceAfterFailedCommitOrRollback(wrap, ee, false);
                  Throwable th = ((weblogic.transaction.Transaction)invokeTx).getRollbackReason();
                  throw EJBRuntimeUtils.asEJBException("Transaction Rolledback.", th);
               }

               return this.isTxRetry(nextTxRetryCount);
            }

            try {
               if (ee != null && this.beanInfo.isEJB30() && this.deploymentInfo.getExceptionInfo(m, ee).isRollback()) {
                  if (debugLogger.isDebugEnabled()) {
                     debug(ee.getClass().getName() + " is thrown, attempt to rollback ");
                  }

                  try {
                     invokeTx.rollback();
                     if (debugLogger.isDebugEnabled()) {
                        debug(" Rollback succeeded. ");
                     }
                  } catch (Exception var12) {
                     EJBLogger.logErrorDuringRollback1(invokeTx.toString(), StackTraceUtilsClient.throwable2StackTrace(var12));
                  }

                  return this.isTxRetry(nextTxRetryCount);
               }

               if (debugLogger.isDebugEnabled()) {
                  debug("Committing tx: " + invokeTx);
               }

               invokeTx.commit();
               if (debugLogger.isDebugEnabled()) {
                  debug("Committing our tx: SUCCESS\n");
               }
            } catch (Exception var15) {
               if (var15 instanceof RollbackException) {
                  if (debugLogger.isDebugEnabled()) {
                     debug("Committing our tx: ROLLBACK\n");
                  }

                  RollbackException rbe = (RollbackException)var15;
                  if (EJBRuntimeUtils.isOptimisticLockException(rbe.getNested()) || rbe.getNested() != null && rbe.getNested() instanceof AppSetRollbackOnlyException) {
                     return this.isTxRetry(nextTxRetryCount);
                  }

                  this.destroyInstanceAfterFailedCommitOrRollback(wrap, var15, true);
                  throw EJBRuntimeUtils.asEJBException("Error committing transaction:", var15);
               }

               this.destroyInstanceAfterFailedCommitOrRollback(wrap, var15, true);
               throw EJBRuntimeUtils.asEJBException("Error committing transaction:", var15);
            }
         } else if (wrap.runningInCallerTx() && ee != null && this.beanInfo.isEJB30() && this.deploymentInfo.getExceptionInfo(m, ee).isRollback()) {
            if (debugLogger.isDebugEnabled()) {
               debug(" caller tx marked for Rollback, attempt to rollback ");
            }

            try {
               callerTx.setRollbackOnly();
               if (debugLogger.isDebugEnabled()) {
                  debug(" SetRollbackOnly succeeded. ");
               }
            } catch (Exception var13) {
               EJBLogger.logExcepDuringSetRollbackOnly(var13);
            }
         }

         return false;
      }
   }

   private boolean isTxRetry(int txRetryCount) {
      if (txRetryCount >= 0) {
         if (debugLogger.isDebugEnabled()) {
            debug(" nextTxRetryCount " + txRetryCount + " retry Tx");
         }

         return true;
      } else {
         if (debugLogger.isDebugEnabled()) {
            debug(" nextTxRetryCount " + txRetryCount + " do not retry Tx");
         }

         return false;
      }
   }

   private void destroyInstanceAfterFailedCommitOrRollback(InvocationWrapper wrap, Throwable th, boolean commit) {
      try {
         this.getBeanManager().destroyInstance(wrap, th);
      } catch (InternalException var6) {
         Transaction invokeTx = wrap.getInvokeTx();
         if (commit) {
            EJBLogger.logErrorDuringCommit(invokeTx.toString(), StackTraceUtilsClient.throwable2StackTrace(var6));
         } else {
            EJBLogger.logErrorDuringRollback(invokeTx.toString(), StackTraceUtilsClient.throwable2StackTrace(var6));
         }
      }

   }

   private void retryEntityHomePreInvoke(InvocationWrapper wrap) throws Exception {
      if (debugLogger.isDebugEnabled()) {
         debug("retryEntityHomePreInvoke entered \n");
      }

      EnterpriseBean bean = null;

      try {
         wrap.enforceTransactionPolicy();
         bean = this.getBeanManager().preHomeInvoke(wrap);
      } catch (Throwable var4) {
         if (debugLogger.isDebugEnabled()) {
            debug("Could not retry preInvoke " + var4);
         }

         this.handleSystemException(wrap, var4);
         throw new AssertionError("Should never reach here");
      }

      wrap.setBean(bean);
   }

   public void postEntityHomeInvokeCleanup(InvocationWrapper wrap, Throwable ee) throws Exception {
      try {
         if (ee != null) {
            Method m = wrap.getMethodDescriptor().getMethod();
            if (!EJBRuntimeUtils.isAppException(m, ee)) {
               if (ee instanceof Exception) {
                  throw (Exception)ee;
               }

               if (!$assertionsDisabled && !(ee instanceof Exception)) {
                  throw new AssertionError();
               }

               throw EJBRuntimeUtils.asEJBException("EJB encountered System Exception: ", ee);
            }

            EJBRuntimeUtils.throwException(ee);
         }
      } finally {
         EJBRuntimeUtils.popEnvironment();
         super.postHomeInvokeCleanup(wrap);

         try {
            this.getBeanManager().postHomeInvoke(wrap);
         } catch (Exception var9) {
            EJBLogger.logExcepInMethod(wrap.getMethodDescriptor().getMethod().getName(), var9);
         }

      }

   }

   public int getRetryOnRollbackCount(InvocationWrapper wrap) {
      return wrap.getMethodDescriptor().getRetryOnRollbackCount();
   }

   private int getNextTxRetryCount(int txRetryCount) {
      --txRetryCount;
      return txRetryCount;
   }

   public Query createQuery() throws EJBException {
      if (this.md_createQuery == null) {
         Loggable l = EJBLogger.logdynamicQueriesNotEnabledLoggable();
         throw new AccessLocalException(l.getMessage());
      } else {
         InvocationWrapper.newInstance(this.md_createQuery).checkMethodPermissionsLocal(EJBContextHandler.EMPTY);
         return new LocalQueryImpl(this);
      }
   }

   public PreparedQuery prepareQuery(String ejbql) throws EJBException {
      return this.prepareQuery(ejbql, (Properties)null);
   }

   public PreparedQuery prepareQuery(String ejbql, Properties props) throws EJBException {
      PreparedQuery pQuery = null;
      this.md_prepareQuery = this.md_createQuery;
      if (this.md_prepareQuery == null) {
         Loggable l = EJBLogger.logdynamicQueriesNotEnabledLoggable();
         throw new AccessLocalException(l.getMessage());
      } else {
         InvocationWrapper.newInstance(this.md_prepareQuery).checkMethodPermissionsLocal(EJBContextHandler.EMPTY);

         try {
            pQuery = new LocalPreparedQueryImpl(ejbql, this, props);
         } catch (FinderException var7) {
            weblogic.i18n.logging.Loggable log = EJBLogger.logErrorPrepareQueryLoggable(var7.getMessage());
            EJBException ee = new EJBException(log.getMessage(), var7);
            ee.initCause(var7);
            throw ee;
         }

         if (runtimeLogger.isDebugEnabled()) {
            runtimeLogger.debug("\nReturning PreparedQuery: " + pQuery);
         }

         return pQuery;
      }
   }

   public Query createSqlQuery() throws EJBException {
      if (this.md_createQuery == null) {
         Loggable l = EJBLogger.logdynamicQueriesNotEnabledLoggable();
         throw new AccessLocalException(l.getMessage());
      } else {
         InvocationWrapper.newInstance(this.md_createQuery).checkMethodPermissionsLocal(EJBContextHandler.EMPTY);
         return new LocalQueryImpl(this, true);
      }
   }

   public String nativeQuery(String ejbql) throws EJBException {
      Loggable l;
      if (this.getBeanManager() == null) {
         l = EJBLogger.logExecuteNativeQueryUseNonWeblogicEntitymanagerLoggable(ejbql);
         throw new EJBException(l.getMessage());
      } else if (this.md_createQuery == null) {
         l = EJBLogger.logdynamicQueriesNotEnabledLoggable();
         throw new AccessLocalException(l.getMessage());
      } else {
         InvocationWrapper.newInstance(this.md_createQuery).checkMethodPermissionsLocal(EJBContextHandler.EMPTY);

         try {
            return this.getBeanManager().nativeQuery(ejbql);
         } catch (FinderException var5) {
            weblogic.i18n.logging.Loggable log = EJBLogger.logEntityErrorObtainNativeQueryLoggable(var5.getMessage());
            EJBException ee = new EJBException(log.getMessage(), var5);
            ee.initCause(var5);
            throw ee;
         }
      }
   }

   public String getDatabaseProductName() throws EJBException {
      Loggable l;
      if (this.getBeanManager() == null) {
         l = EJBLogger.logExecuteGetDatabaseProductnameUseNonWeblogicEntityManagerLoggable();
         throw new EJBException(l.getMessage());
      } else if (this.md_createQuery == null) {
         l = EJBLogger.logdynamicQueriesNotEnabledLoggable();
         throw new AccessLocalException(l.getMessage());
      } else {
         InvocationWrapper.newInstance(this.md_createQuery).checkMethodPermissionsLocal(EJBContextHandler.EMPTY);

         try {
            return this.getBeanManager().getDatabaseProductName();
         } catch (FinderException var4) {
            weblogic.i18n.logging.Loggable log = EJBLogger.logErrorCallGetdatabaseProductNameLoggable(var4.getMessage());
            EJBException ee = new EJBException(log.getMessage(), var4);
            ee.initCause(var4);
            throw ee;
         }
      }
   }

   public String getDatabaseProductVersion() throws EJBException {
      Loggable l;
      if (this.getBeanManager() == null) {
         l = EJBLogger.logExecuteGetDatabaseProductnameUseNonWeblogicEntityManagerLoggable();
         throw new EJBException(l.getMessage());
      } else if (this.md_createQuery == null) {
         l = EJBLogger.logdynamicQueriesNotEnabledLoggable();
         throw new AccessLocalException(l.getMessage());
      } else {
         InvocationWrapper.newInstance(this.md_createQuery).checkMethodPermissionsLocal(EJBContextHandler.EMPTY);

         try {
            return this.getBeanManager().getDatabaseProductVersion();
         } catch (FinderException var4) {
            weblogic.i18n.logging.Loggable log = EJBLogger.logErrorCallGetdatabaseProductNameLoggable(var4.getMessage());
            EJBException ee = new EJBException(log.getMessage(), var4);
            ee.initCause(var4);
            throw ee;
         }
      }
   }

   public Object executeQuery(String query, WLQueryProperties props, boolean isSelect, boolean isSql) throws FinderException, EJBException {
      LocalHolder var11;
      if ((var11 = LocalHolder.getInstance(_WLDF$INST_JPFLD_3, _WLDF$INST_JPFLD_JPMONS_3)) != null) {
         if (var11.argsCapture) {
            var11.args = InstrumentationSupport.toSensitive(5);
         }

         InstrumentationSupport.createDynamicJoinPoint(var11);
         InstrumentationSupport.preProcess(var11);
         var11.resetPostBegin();
      }

      Object var10000;
      label323: {
         label324: {
            try {
               Loggable log;
               if (this.getBeanManager() == null) {
                  log = EJBLogger.logNonWeblogicEntityManagerExecuteQueryLoggable(query);
                  throw new EJBException(log.getMessage());
               }

               if (props.getEnableQueryCaching() && !this.getBeanManager().isReadOnly()) {
                  log = EJBLogger.logQueryCacheNotSupportReadWriteBeanLoggable();
                  throw new FinderException(log.getMessage());
               }

               MethodDescriptor md = (MethodDescriptor)this.md_createQuery.clone();
               md.getTransactionPolicy().setTxAttribute(props.getTransaction());
               md.getTransactionPolicy().setIsolationLevel(props.getIsolationLevel());
               Object ret = null;
               InvocationWrapper wrap = this.preHomeInvoke(md, EJBContextHandler.EMPTY);
               Method findMethod = md.getMethod();

               label313: {
                  Object var9;
                  label312: {
                     try {
                        label331: {
                           if (props.getEnableQueryCaching()) {
                              ret = ((TTLManager)this.getBeanManager()).getFromQueryCache(query, props.getMaxElements(), wrap.isLocal());
                              if (ret != null) {
                                 if (ret != QueryCache.NULL_VALUE) {
                                    var9 = ret;
                                    break label331;
                                 }

                                 var9 = null;
                                 break label312;
                              }
                           }

                           if (isSql) {
                              ret = this.getBeanManager().dynamicSqlQuery(query, (Object[])null, props, findMethod, wrap.isLocal(), isSelect ? ResultSet.class : Collection.class);
                           } else {
                              ret = this.getBeanManager().dynamicQuery(query, (Object[])null, props, findMethod, wrap.isLocal(), isSelect);
                           }
                           break label313;
                        }
                     } catch (InternalException var20) {
                        if (var20.detail instanceof FinderException) {
                           throw (FinderException)var20.detail;
                        }

                        this.handleSystemException(wrap, var20);
                        break label313;
                     } catch (Throwable var21) {
                        this.handleSystemException(wrap, var21);
                        throw new AssertionError("Should never reach here");
                     } finally {
                        this.postHomeInvoke(wrap, (Throwable)null);
                     }

                     var10000 = var9;
                     break label324;
                  }

                  var10000 = var9;
                  break label323;
               }

               var10000 = ret;
            } catch (Throwable var23) {
               if (var11 != null) {
                  var11.th = var23;
                  var11.ret = null;
                  InstrumentationSupport.postProcess(var11);
               }

               throw var23;
            }

            if (var11 != null) {
               var11.ret = var10000;
               InstrumentationSupport.postProcess(var11);
            }

            return var10000;
         }

         if (var11 != null) {
            var11.ret = var10000;
            InstrumentationSupport.postProcess(var11);
         }

         return var10000;
      }

      if (var11 != null) {
         var11.ret = var10000;
         InstrumentationSupport.postProcess(var11);
      }

      return var10000;
   }

   public Object executePreparedQuery(String sql, PreparedQuery pquery, Map arguments, Map flattenedArguments, boolean isSelect) throws FinderException, EJBException {
      LocalHolder var12;
      if ((var12 = LocalHolder.getInstance(_WLDF$INST_JPFLD_4, _WLDF$INST_JPFLD_JPMONS_4)) != null) {
         if (var12.argsCapture) {
            var12.args = InstrumentationSupport.toSensitive(6);
         }

         InstrumentationSupport.createDynamicJoinPoint(var12);
         InstrumentationSupport.preProcess(var12);
         var12.resetPostBegin();
      }

      Object var10000;
      try {
         this.md_prepareQuery = this.md_createQuery;
         MethodDescriptor md = (MethodDescriptor)this.md_prepareQuery.clone();
         md.getTransactionPolicy().setTxAttribute(pquery.getTransaction());
         md.getTransactionPolicy().setIsolationLevel(((WLQueryProperties)pquery).getIsolationLevel());
         Object ret = null;
         InvocationWrapper wrap = this.preHomeInvoke(md, EJBContextHandler.EMPTY);
         Method findMethod = md.getMethod();

         try {
            ret = this.getBeanManager().executePreparedQuery(sql, wrap, findMethod, isSelect, arguments, flattenedArguments, pquery);
         } catch (InternalException var21) {
            if (var21.detail instanceof FinderException) {
               throw (FinderException)var21.detail;
            }

            this.handleSystemException(wrap, var21);
         } catch (Throwable var22) {
            this.handleSystemException(wrap, var22);
            throw new AssertionError("Should never reach here");
         } finally {
            this.postHomeInvoke(wrap, (Throwable)null);
         }

         var10000 = ret;
      } catch (Throwable var24) {
         if (var12 != null) {
            var12.th = var24;
            var12.ret = null;
            InstrumentationSupport.postProcess(var12);
         }

         throw var24;
      }

      if (var12 != null) {
         var12.ret = var10000;
         InstrumentationSupport.postProcess(var12);
      }

      return var10000;
   }

   protected Object finder(MethodDescriptor md, Object[] args, int finderType) throws Exception {
      Object var8;
      try {
         if (!$assertionsDisabled && md == null) {
            throw new AssertionError();
         }

         EJBRuntimeUtils.pushEnvironment(this.beanManager.getEnvironmentContext());
         Object ret = null;
         Method findMethod = md.getMethod();
         InvocationWrapper wrap = this.preHomeInvoke(md, new EJBContextHandler(md, args));
         Throwable ee = null;

         try {
            if (md.getMethodId() != null) {
               if (!$assertionsDisabled && !(this.getBeanManager() instanceof TTLManager)) {
                  throw new AssertionError();
               }

               if (!$assertionsDisabled && finderType == 2) {
                  throw new AssertionError();
               }

               ret = ((TTLManager)this.getBeanManager()).getFromQueryCache(md.getMethodId(), args, wrap.isLocal());
            }

            if (ret != null) {
               if (ret == QueryCache.NULL_VALUE) {
                  ret = null;
               }
            } else if (finderType == 1) {
               ret = this.getBeanManager().localScalarFinder(wrap, findMethod, args);
            } else if (finderType == 2) {
               ret = this.getBeanManager().enumFinder(wrap, findMethod, args);
            } else {
               if (finderType != 4) {
                  throw new AssertionError("Unexpected value: " + finderType);
               }

               ret = this.getBeanManager().collectionFinder(findMethod, args, wrap.isLocal());
            }
         } catch (InternalException var29) {
            ee = var29.detail;
            if (this.deploymentInfo.getExceptionInfo(findMethod, ee).isAppException()) {
               throw (Exception)ee;
            }

            this.handleSystemException(wrap, var29);
         } catch (Throwable var30) {
            ee = var30;
            this.handleSystemException(wrap, var30);
         } finally {
            if (wrap.getInvokeTx() == null && !((EntityBeanInfo)this.beanInfo).getIsBeanManagedPersistence()) {
               try {
                  this.getBeanManager().beforeCompletion(ret);
                  this.getBeanManager().afterCompletion(ret);
               } catch (InternalException var28) {
                  if (EJBRuntimeUtils.isAppException(md.getMethod(), var28)) {
                     throw (RemoveException)var28.detail;
                  }

                  this.handleSystemException(wrap, var28);
                  throw new AssertionError("Should never have reached here");
               }
            }

            this.postHomeInvoke(wrap, ee);
         }

         var8 = ret;
      } finally {
         EJBRuntimeUtils.popEnvironment();
      }

      return var8;
   }

   public void invalidate(Object pk) {
      if (pk == null) {
         Loggable l = EJBLogger.logNullInvalidateParameterLoggable();
         throw new EJBException(l.getMessage());
      } else {
         try {
            ((InvalidationBeanManager)this.getBeanManager()).invalidate((Object)null, (Object)pk);
         } catch (InternalException var3) {
            throw EJBRuntimeUtils.asEJBException("EJB Exception: ", var3);
         }
      }
   }

   public void invalidate(Collection pks) {
      if (pks == null) {
         Loggable l = EJBLogger.logNullInvalidateParameterLoggable();
         throw new EJBException(l.getMessage());
      } else {
         try {
            ((InvalidationBeanManager)this.getBeanManager()).invalidate((Object)null, (Collection)pks);
         } catch (InternalException var3) {
            throw EJBRuntimeUtils.asEJBException("EJB Exception: ", var3);
         }
      }
   }

   public void invalidateAll() {
      try {
         ((InvalidationBeanManager)this.getBeanManager()).invalidateAll((Object)null);
      } catch (InternalException var2) {
         throw EJBRuntimeUtils.asEJBException("EJB Exception: ", var2);
      }
   }

   public void invalidateLocalServer(Object pk) {
      if (pk == null) {
         Loggable l = EJBLogger.logNullInvalidateParameterLoggable();
         throw new EJBException(l.getMessage());
      } else {
         ((InvalidationBeanManager)this.getBeanManager()).invalidateLocalServer((Object)null, (Object)pk);
      }
   }

   public void invalidateLocalServer(Collection pks) {
      if (pks == null) {
         Loggable l = EJBLogger.logNullInvalidateParameterLoggable();
         throw new EJBException(l.getMessage());
      } else {
         ((InvalidationBeanManager)this.getBeanManager()).invalidateLocalServer((Object)null, (Collection)pks);
      }
   }

   public void invalidateAllLocalServer() {
      ((InvalidationBeanManager)this.getBeanManager()).invalidateAllLocalServer((Object)null);
   }

   public BaseEntityManager getBeanManager() {
      return this.entityManager;
   }

   private static void debug(String s) {
      debugLogger.debug("[EntityEJBLocalHome] " + s);
   }

   static {
      _WLDF$INST_FLD_EJB_Diagnostic_Home_Remove_Around_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "EJB_Diagnostic_Home_Remove_Around_Medium");
      _WLDF$INST_FLD_EJB_Diagnostic_Database_Access_Around_High = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "EJB_Diagnostic_Database_Access_Around_High");
      _WLDF$INST_FLD_EJB_Diagnostic_Home_Create_Around_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "EJB_Diagnostic_Home_Create_Around_Medium");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "EntityEJBLocalHome.java", "weblogic.ejb.container.internal.EntityEJBLocalHome", "create", "(Lweblogic/ejb/container/internal/MethodDescriptor;Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;[Ljava/lang/Object;Ljava/lang/Object;)Ljavax/ejb/EJBLocalObject;", 144, "", "", "", InstrumentationSupport.makeMap(new String[]{"EJB_Diagnostic_Home_Create_Around_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("md", "weblogic.diagnostics.instrumentation.gathering.EJBMethodDescriptorRenderer", false, true), null, null, null, null})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Home_Create_Around_Medium};
      _WLDF$INST_JPFLD_1 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "EntityEJBLocalHome.java", "weblogic.ejb.container.internal.EntityEJBLocalHome", "remove", "(Lweblogic/ejb/container/internal/MethodDescriptor;Ljava/lang/Object;)V", 196, "", "", "", InstrumentationSupport.makeMap(new String[]{"EJB_Diagnostic_Home_Remove_Around_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("md", "weblogic.diagnostics.instrumentation.gathering.EJBMethodDescriptorRenderer", false, true), null})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_1 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Home_Remove_Around_Medium};
      _WLDF$INST_JPFLD_2 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "EntityEJBLocalHome.java", "weblogic.ejb.container.internal.EntityEJBLocalHome", "findByPrimaryKey", "(Lweblogic/ejb/container/internal/MethodDescriptor;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", 260, "", "", "", InstrumentationSupport.makeMap(new String[]{"EJB_Diagnostic_Database_Access_Around_High"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("md", "weblogic.diagnostics.instrumentation.gathering.EJBMethodDescriptorRenderer", false, true), null, null})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_2 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Database_Access_Around_High};
      _WLDF$INST_JPFLD_3 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "EntityEJBLocalHome.java", "weblogic.ejb.container.internal.EntityEJBLocalHome", "executeQuery", "(Ljava/lang/String;Lweblogic/ejb/WLQueryProperties;ZZ)Ljava/lang/Object;", 890, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_3 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Database_Access_Around_High};
      _WLDF$INST_JPFLD_4 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "EntityEJBLocalHome.java", "weblogic.ejb.container.internal.EntityEJBLocalHome", "executePreparedQuery", "(Ljava/lang/String;Lweblogic/ejb/PreparedQuery;Ljava/util/Map;Ljava/util/Map;Z)Ljava/lang/Object;", 985, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_4 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Database_Access_Around_High};
      $assertionsDisabled = !EntityEJBLocalHome.class.desiredAssertionStatus();
      runtimeLogger = EJBDebugService.cmpRuntimeLogger;
   }
}
