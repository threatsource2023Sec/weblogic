package weblogic.ejb.container.internal;

import java.lang.reflect.Method;
import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import javax.ejb.ConcurrentAccessException;
import javax.ejb.EJBHome;
import javax.ejb.EJBMetaData;
import javax.ejb.EJBObject;
import javax.ejb.EnterpriseBean;
import javax.ejb.FinderException;
import javax.ejb.Handle;
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
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.BeanManager;
import weblogic.ejb.container.interfaces.EntityBeanInfo;
import weblogic.ejb.container.interfaces.InvalidationBeanManager;
import weblogic.ejb.container.interfaces.QueryCache;
import weblogic.ejb.container.manager.BaseEntityManager;
import weblogic.ejb.container.manager.TTLManager;
import weblogic.ejb.container.persistence.spi.CMPBeanDescriptor;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.ejb20.interfaces.QueryHandler;
import weblogic.ejb20.internal.EJBMetaDataImpl;
import weblogic.ejb20.internal.PreparedQueryImpl;
import weblogic.ejb20.internal.QueryImpl;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.logging.Loggable;
import weblogic.transaction.AppSetRollbackOnlyException;
import weblogic.transaction.RollbackException;
import weblogic.utils.StackTraceUtilsClient;
import weblogic.utils.collections.SoftHashMap;

public abstract class EntityEJBHome extends BaseEJBHome implements EJBHome, QueryHandler {
   public static final int SCALAR_FINDER = 1;
   public static final int ENUM_FINDER = 2;
   public static final int COLL_FINDER = 4;
   private static final DebugLogger runtimeLogger;
   private EJBActivator ejbActivator = new EJBActivator(this);
   private boolean isNoObjectActivation = false;
   private Method findByPrimaryKeyMethod;
   public MethodDescriptor md_createQuery = null;
   public MethodDescriptor md_prepareQuery = null;
   private BaseEntityManager entityManager = null;
   private Map eoMap = Collections.synchronizedMap(new SoftHashMap());
   // $FF: synthetic field
   static final boolean $assertionsDisabled;
   static final long serialVersionUID = 2725249742448634513L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.ejb.container.internal.EntityEJBHome");
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
   static final JoinPoint _WLDF$INST_JPFLD_5;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_5;
   static final JoinPoint _WLDF$INST_JPFLD_6;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_6;

   public EntityEJBHome(Class eoClass) {
      super(eoClass);
      this.isNoObjectActivation = !EntityEJBObject_Activatable.class.isAssignableFrom(eoClass);
   }

   public void setup(BeanInfo bi, BeanManager beanManager) throws WLDeploymentException {
      super.setup(bi, beanManager);
      this.entityManager = (BaseEntityManager)beanManager;
      EntityBeanInfo ebi = (EntityBeanInfo)this.beanInfo;
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

   public EJBObject allocateEOInstance() {
      EntityEJBObject eo = null;

      try {
         eo = (EntityEJBObject)this.eoClass.newInstance();
         return eo;
      } catch (InstantiationException var3) {
         throw new AssertionError(var3);
      } catch (IllegalAccessException var4) {
         throw new AssertionError(var4);
      }
   }

   public EJBObject allocateEO() {
      throw new AssertionError("Cannot allocate an entity EJBObject without a primary key.");
   }

   public EJBObject allocateEO(Object pk) {
      if (this.getIsNoObjectActivation()) {
         EntityEJBObject eo = (EntityEJBObject)this.eoMap.get(pk);
         if (eo == null) {
            eo = (EntityEJBObject)this.allocateEOInstance();
            eo.setEJBHome(this);
            eo.setPrimaryKey(pk);
            eo.setBeanManager(this.getBeanManager());
            eo.setBeanInfo(this.getBeanInfo());
            this.eoMap.put(pk, eo);
         }

         return eo;
      } else {
         EntityEJBObject_Activatable eo = (EntityEJBObject_Activatable)this.allocateEOInstance();
         eo.setEJBHome(this);
         eo.setPrimaryKey(pk);
         eo.setBeanManager(this.getBeanManager());
         eo.setBeanInfo(this.getBeanInfo());
         eo.setActivator(this.ejbActivator);
         return eo;
      }
   }

   public void releaseEO(Object pk) {
      this.eoMap.remove(pk);
   }

   public void undeploy() {
      if (this.getIsNoObjectActivation()) {
         Collection ejbObjects = this.eoMap.values();
         Iterator var2 = ejbObjects.iterator();

         while(var2.hasNext()) {
            EJBObject eo = (EJBObject)var2.next();
            this.unexport(eo);
         }
      }

      this.unexportEJBActivator(this.ejbActivator, this.eoClass);
      super.undeploy();
   }

   public boolean getIsNoObjectActivation() {
      return this.isNoObjectActivation;
   }

   protected EJBMetaData getEJBMetaDataInstance() {
      EntityBeanInfo ebi = (EntityBeanInfo)this.beanInfo;
      Class pkClass = ebi.getPrimaryKeyClass();
      if (ebi.isUnknownPrimaryKey() && !ebi.getIsBeanManagedPersistence()) {
         CMPBeanDescriptor bd = ebi.getCMPInfo().getCMPBeanDescriptor(ebi.getEJBName());
         pkClass = bd.getPrimaryKeyClass();
      }

      return new EJBMetaDataImpl(this, ebi.getHomeInterfaceClass(), ebi.getRemoteInterfaceClass(), pkClass, false, false);
   }

   protected EJBObject create(MethodDescriptor md, Method createMethod, Method postCreateMethod, Object[] args) throws Exception {
      LocalHolder var11;
      if ((var11 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var11.argsCapture) {
            var11.args = new Object[5];
            Object[] var10000 = var11.args;
            var10000[0] = this;
            var10000[1] = md;
            var10000[2] = createMethod;
            var10000[3] = postCreateMethod;
            var10000[4] = args;
         }

         InstrumentationSupport.createDynamicJoinPoint(var11);
         InstrumentationSupport.preProcess(var11);
         var11.resetPostBegin();
      }

      EJBObject var34;
      try {
         EJBObject var33;
         try {
            EJBRuntimeUtils.pushEnvironment(this.beanManager.getEnvironmentContext());
            if (debugLogger.isDebugEnabled()) {
               debug("Creating a bean from md: " + md);
            }

            InvocationWrapper wrap = this.preHomeInvoke(md, new EJBContextHandler(md, args));
            EJBObject eo = null;
            Throwable ee = null;

            try {
               if (!$assertionsDisabled && wrap.getInvokeTx() == null) {
                  throw new AssertionError();
               }

               eo = this.getBeanManager().remoteCreate(wrap, createMethod, postCreateMethod, args);
               wrap.setPrimaryKey(eo.getPrimaryKey());
            } catch (InternalException var27) {
               InternalException ie = var27;
               ee = var27.detail;
               if (this.deploymentInfo.getExceptionInfo(createMethod, ee).isAppException()) {
                  throw (Exception)ee;
               }

               this.handleSystemException(wrap, var27);
               throw new AssertionError("Should never have reached here");
            } catch (Throwable var28) {
               Throwable th = var28;
               ee = var28;
               this.handleSystemException(wrap, var28);
               throw new AssertionError("Should never reach here");
            } finally {
               this.postHomeInvoke(wrap, ee);
            }

            var33 = eo;
         } finally {
            EJBRuntimeUtils.popEnvironment();
         }

         var34 = var33;
      } catch (Throwable var31) {
         if (var11 != null) {
            var11.th = var31;
            var11.ret = null;
            InstrumentationSupport.postProcess(var11);
         }

         throw var31;
      }

      if (var11 != null) {
         var11.ret = var34;
         InstrumentationSupport.postProcess(var11);
      }

      return var34;
   }

   public void remove(MethodDescriptor md, Handle h) throws RemoteException, RemoveException {
      LocalHolder var9;
      if ((var9 = LocalHolder.getInstance(_WLDF$INST_JPFLD_1, _WLDF$INST_JPFLD_JPMONS_1)) != null) {
         if (var9.argsCapture) {
            var9.args = new Object[3];
            Object[] var10000 = var9.args;
            var10000[0] = this;
            var10000[1] = md;
            var10000[2] = h;
         }

         InstrumentationSupport.createDynamicJoinPoint(var9);
         InstrumentationSupport.preProcess(var9);
         var9.resetPostBegin();
      }

      try {
         ManagedInvocationContext mic = this.beanInfo.setCIC();
         Throwable var4 = null;

         try {
            this.validateHandleFromHome(h);
            EJBObject eo = h.getEJBObject();
            Object pk = eo.getPrimaryKey();
            this.remove(md, eo, pk);
         } catch (Throwable var21) {
            var4 = var21;
            throw var21;
         } finally {
            if (mic != null) {
               if (var4 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var20) {
                     var4.addSuppressed(var20);
                  }
               } else {
                  mic.close();
               }
            }

         }
      } catch (Throwable var23) {
         if (var9 != null) {
            var9.th = var23;
            InstrumentationSupport.postProcess(var9);
         }

         throw var23;
      }

      if (var9 != null) {
         InstrumentationSupport.postProcess(var9);
      }

   }

   public void remove(MethodDescriptor md, Object pk) throws RemoteException, RemoveException {
      LocalHolder var4;
      if ((var4 = LocalHolder.getInstance(_WLDF$INST_JPFLD_2, _WLDF$INST_JPFLD_JPMONS_2)) != null) {
         if (var4.argsCapture) {
            var4.args = new Object[3];
            Object[] var10000 = var4.args;
            var10000[0] = this;
            var10000[1] = md;
            var10000[2] = pk;
         }

         InstrumentationSupport.createDynamicJoinPoint(var4);
         InstrumentationSupport.preProcess(var4);
         var4.resetPostBegin();
      }

      try {
         EJBObject eo = null;
         this.remove(md, (EJBObject)eo, pk);
      } catch (Throwable var6) {
         if (var4 != null) {
            var4.th = var6;
            InstrumentationSupport.postProcess(var4);
         }

         throw var6;
      }

      if (var4 != null) {
         InstrumentationSupport.postProcess(var4);
      }

   }

   private void remove(MethodDescriptor md, EJBObject eo, Object pk) throws RemoteException, RemoveException {
      try {
         EJBRuntimeUtils.pushEnvironment(this.beanManager.getEnvironmentContext());
         InvocationWrapper wrap = this.preHomeInvoke(md, new EJBContextHandler(md, new Object[]{pk}));
         Throwable ee = null;

         try {
            wrap.setPrimaryKey(pk);

            try {
               this.getBeanManager().remove(wrap);
            } catch (InternalException var20) {
               if (!(var20.getCause() instanceof NoSuchEJBException) && !(var20.getCause() instanceof ConcurrentAccessException)) {
                  throw var20;
               }

               throw new InternalException(var20.getMessage(), var20.getCause().getCause());
            }
         } catch (InternalException var21) {
            ee = var21.detail;
            if (this.deploymentInfo.getExceptionInfo(md.getMethod(), ee).isAppException()) {
               Exception removeException = (Exception)ee;
               if (removeException instanceof RemoveException) {
                  throw (RemoveException)removeException;
               }

               throw new AssertionError("Invalid Exception thrown from remove: " + StackTraceUtilsClient.throwable2StackTrace(removeException));
            }

            this.handleSystemException(wrap, var21);
            throw new AssertionError("Should never have reached here");
         } catch (Throwable var22) {
            ee = var22;
            this.handleSystemException(wrap, var22);
            throw new AssertionError("Should never reach here");
         } finally {
            this.postHomeInvoke(wrap, ee);
            if (this.getIsNoObjectActivation()) {
               this.unexport(eo, false);
            }

         }
      } finally {
         EJBRuntimeUtils.popEnvironment();
      }

   }

   protected EJBObject findByPrimaryKey(MethodDescriptor md, Object pk) throws Exception {
      LocalHolder var10;
      if ((var10 = LocalHolder.getInstance(_WLDF$INST_JPFLD_3, _WLDF$INST_JPFLD_JPMONS_3)) != null) {
         if (var10.argsCapture) {
            var10.args = new Object[3];
            Object[] var10000 = var10.args;
            var10000[0] = this;
            var10000[1] = md;
            var10000[2] = pk;
         }

         InstrumentationSupport.createDynamicJoinPoint(var10);
         InstrumentationSupport.preProcess(var10);
         var10.resetPostBegin();
      }

      EJBObject var38;
      try {
         if (!$assertionsDisabled && md == null) {
            throw new AssertionError();
         }

         if (pk == null) {
            throw new ObjectNotFoundException("Primary key was null!");
         }

         EJBObject var6;
         try {
            EJBRuntimeUtils.pushEnvironment(this.beanManager.getEnvironmentContext());
            InvocationWrapper wrap = this.preHomeInvoke(md, new EJBContextHandler(md, new Object[]{pk}));
            EJBObject eo = null;
            Throwable ee = null;

            try {
               wrap.setPrimaryKey(pk);
               eo = this.getBeanManager().remoteFindByPrimaryKey(wrap, pk);
            } catch (InternalException var33) {
               ee = var33.detail;
               if (this.deploymentInfo.getExceptionInfo(this.findByPrimaryKeyMethod, ee).isAppException()) {
                  throw (Exception)ee;
               }

               this.handleSystemException(wrap, var33);
               throw new AssertionError("Should never have reached here");
            } catch (Throwable var34) {
               ee = var34;
               this.handleSystemException(wrap, var34);
               throw new AssertionError("Should never reach here");
            } finally {
               if (wrap.getInvokeTx() == null && !((EntityBeanInfo)this.beanInfo).getIsBeanManagedPersistence()) {
                  try {
                     this.getBeanManager().beforeCompletion(wrap);
                     this.getBeanManager().afterCompletion(wrap);
                  } catch (InternalException var32) {
                     if (EJBRuntimeUtils.isAppException(md.getMethod(), var32)) {
                        throw (RemoveException)var32.detail;
                     }

                     this.handleSystemException(wrap, var32);
                     throw new AssertionError("Should never have reached here");
                  }
               }

               this.postHomeInvoke(wrap, ee);
            }

            var6 = eo;
         } finally {
            EJBRuntimeUtils.popEnvironment();
         }

         var38 = var6;
      } catch (Throwable var37) {
         if (var10 != null) {
            var10.th = var37;
            var10.ret = null;
            InstrumentationSupport.postProcess(var10);
         }

         throw var37;
      }

      if (var10 != null) {
         var10.ret = var38;
         InstrumentationSupport.postProcess(var10);
      }

      return var38;
   }

   protected InvocationWrapper preEntityHomeInvoke(MethodDescriptor md) throws RemoteException {
      InvocationWrapper wrap = null;
      EJBRuntimeUtils.pushEnvironment(this.beanManager.getEnvironmentContext());

      try {
         wrap = this.preHomeInvoke(md, EJBContextHandler.EMPTY);
      } catch (RemoteException var10) {
         EJBRuntimeUtils.popEnvironment();
         throw var10;
      }

      Throwable th;
      try {
         if (!$assertionsDisabled && wrap.getInvokeTx() == null) {
            throw new AssertionError();
         } else {
            th = null;
            EnterpriseBean bean = this.getBeanManager().preHomeInvoke(wrap);
            wrap.setBean(bean);
            return wrap;
         }
      } catch (Throwable var9) {
         th = var9;

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
         debug("postEntityHomeInvokeTxRetry returning with nextTxRetryCount = " + nextTxRetryCount + "\n");
      }

      return nextTxRetryCount;
   }

   private boolean postEntityHomeInvoke1(int nextTxRetryCount, InvocationWrapper wrap, Throwable ee) throws Exception {
      MethodDescriptor md = wrap.getMethodDescriptor();
      if (debugLogger.isDebugEnabled()) {
         debug("[BaseEJBHome] postHomeInvoke1 called with nextTxRetryCount = " + nextTxRetryCount + "\n wrap:" + wrap + " Exception: " + ee + " on: " + this);
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
            } catch (InternalException var15) {
               if (EJBRuntimeUtils.isAppException(m, var15)) {
                  throw EJBRuntimeUtils.asRemoteException("Error during bean cleanup", var15.detail);
               }

               this.handleSystemException(wrap, var15);
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
                  throw EJBRuntimeUtils.asRemoteException("Transaction Rolledback.", th);
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
            } catch (Exception var14) {
               if (var14 instanceof RollbackException) {
                  if (debugLogger.isDebugEnabled()) {
                     debug("Committing our tx: ROLLBACK\n");
                  }

                  RollbackException rbe = (RollbackException)var14;
                  if (EJBRuntimeUtils.isOptimisticLockException(rbe.getNested()) || rbe.getNested() != null && rbe.getNested() instanceof AppSetRollbackOnlyException) {
                     return this.isTxRetry(nextTxRetryCount);
                  }

                  this.destroyInstanceAfterFailedCommitOrRollback(wrap, var14, true);
                  throw EJBRuntimeUtils.asRemoteException("Error committing transaction:", var14);
               }

               this.destroyInstanceAfterFailedCommitOrRollback(wrap, var14, true);
               throw EJBRuntimeUtils.asRemoteException("Error committing transaction:", var14);
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
            debug("Could not retry preInvoke because of exception: " + var4);
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

   public Query createQuery() throws RemoteException {
      if (this.md_createQuery == null) {
         Loggable l = EJBLogger.logdynamicQueriesNotEnabledLoggable();
         throw new AccessException(l.getMessageText());
      } else {
         InvocationWrapper.newInstance(this.md_createQuery).checkMethodPermissionsRemote(EJBContextHandler.EMPTY);
         return new QueryImpl(this);
      }
   }

   public Query createSqlQuery() throws RemoteException {
      if (this.md_createQuery == null) {
         Loggable l = EJBLogger.logdynamicQueriesNotEnabledLoggable();
         throw new AccessException(l.getMessageText());
      } else {
         InvocationWrapper.newInstance(this.md_createQuery).checkMethodPermissionsRemote(EJBContextHandler.EMPTY);
         return new QueryImpl(this, true);
      }
   }

   public PreparedQuery prepareQuery(String ejbql) throws RemoteException {
      return this.prepareQuery(ejbql, (Properties)null);
   }

   public PreparedQuery prepareQuery(String ejbql, Properties props) throws RemoteException {
      PreparedQuery pQuery = null;
      this.md_prepareQuery = this.md_createQuery;
      if (this.md_prepareQuery == null) {
         Loggable l = EJBLogger.logdynamicQueriesNotEnabledLoggable();
         throw new AccessException(l.getMessageText());
      } else {
         InvocationWrapper.newInstance(this.md_prepareQuery).checkMethodPermissionsRemote(EJBContextHandler.EMPTY);

         try {
            pQuery = new PreparedQueryImpl(ejbql, this, props);
         } catch (FinderException var6) {
            Loggable l = EJBLogger.logErrorPrepareQueryLoggable(var6.getMessage());
            throw new RemoteException(l.getMessageText(), var6);
         }

         if (runtimeLogger.isDebugEnabled()) {
            runtimeLogger.debug("\nReturning PreparedQuery: " + pQuery);
         }

         return pQuery;
      }
   }

   public String nativeQuery(String ejbql) throws RemoteException {
      if (this.md_createQuery == null) {
         Loggable l = EJBLogger.logdynamicQueriesNotEnabledLoggable();
         throw new AccessException(l.getMessageText());
      } else {
         InvocationWrapper.newInstance(this.md_createQuery).checkMethodPermissionsRemote(EJBContextHandler.EMPTY);

         try {
            return this.getBeanManager().nativeQuery(ejbql);
         } catch (FinderException var4) {
            Loggable log = EJBLogger.logErrorObtainNativeQueryLoggable(var4.getMessage(), var4.toString());
            throw new RemoteException(log.getMessageText(), var4);
         }
      }
   }

   public String getDatabaseProductName() throws RemoteException {
      if (this.md_createQuery == null) {
         Loggable l = EJBLogger.logdynamicQueriesNotEnabledLoggable();
         throw new AccessException(l.getMessageText());
      } else {
         InvocationWrapper.newInstance(this.md_createQuery).checkMethodPermissionsRemote(EJBContextHandler.EMPTY);

         try {
            return this.getBeanManager().getDatabaseProductName();
         } catch (FinderException var3) {
            Loggable log = EJBLogger.logErrorObtainNativeQueryLoggable(var3.getMessage(), var3.toString());
            throw new RemoteException(log.getMessageText(), var3);
         }
      }
   }

   public String getDatabaseProductVersion() throws RemoteException {
      if (this.md_createQuery == null) {
         Loggable l = EJBLogger.logdynamicQueriesNotEnabledLoggable();
         throw new AccessException(l.getMessageText());
      } else {
         InvocationWrapper.newInstance(this.md_createQuery).checkMethodPermissionsRemote(EJBContextHandler.EMPTY);

         try {
            return this.getBeanManager().getDatabaseProductVersion();
         } catch (FinderException var3) {
            Loggable log = EJBLogger.logErrorObtainNativeQueryLoggable(var3.getMessage(), var3.toString());
            throw new RemoteException(log.getMessageText(), var3);
         }
      }
   }

   public Object executeQuery(String query, WLQueryProperties props, boolean isSelect) throws FinderException, RemoteException {
      LocalHolder var4;
      if ((var4 = LocalHolder.getInstance(_WLDF$INST_JPFLD_4, _WLDF$INST_JPFLD_JPMONS_4)) != null) {
         if (var4.argsCapture) {
            var4.args = InstrumentationSupport.toSensitive(4);
         }

         InstrumentationSupport.createDynamicJoinPoint(var4);
         InstrumentationSupport.preProcess(var4);
         var4.resetPostBegin();
      }

      Object var10000;
      try {
         var10000 = this.executeQuery(query, props, isSelect, false);
      } catch (Throwable var6) {
         if (var4 != null) {
            var4.th = var6;
            var4.ret = null;
            InstrumentationSupport.postProcess(var4);
         }

         throw var6;
      }

      if (var4 != null) {
         var4.ret = var10000;
         InstrumentationSupport.postProcess(var4);
      }

      return var10000;
   }

   public Object executeQuery(String query, WLQueryProperties props, boolean isSelect, boolean isSql) throws FinderException, RemoteException {
      LocalHolder var11;
      if ((var11 = LocalHolder.getInstance(_WLDF$INST_JPFLD_5, _WLDF$INST_JPFLD_JPMONS_5)) != null) {
         if (var11.argsCapture) {
            var11.args = InstrumentationSupport.toSensitive(5);
         }

         InstrumentationSupport.createDynamicJoinPoint(var11);
         InstrumentationSupport.preProcess(var11);
         var11.resetPostBegin();
      }

      Object var10000;
      label312: {
         label313: {
            try {
               label317: {
                  if (props.getEnableQueryCaching() && !this.getBeanManager().isReadOnly()) {
                     Loggable log = EJBLogger.logQueryCacheNotSupportReadWriteBeanLoggable();
                     throw new FinderException(log.getMessageText());
                  }

                  MethodDescriptor md = (MethodDescriptor)this.md_createQuery.clone();
                  md.getTransactionPolicy().setTxAttribute(props.getTransaction());
                  md.getTransactionPolicy().setIsolationLevel(props.getIsolationLevel());
                  Object ret = null;
                  InvocationWrapper wrap = this.preHomeInvoke(md, EJBContextHandler.EMPTY);
                  Method findMethod = md.getMethod();

                  Object var9;
                  label303: {
                     label302: {
                        try {
                           if (props.getEnableQueryCaching()) {
                              ret = ((TTLManager)this.getBeanManager()).getFromQueryCache(query, props.getMaxElements(), wrap.isLocal());
                              if (ret != null) {
                                 if (ret == QueryCache.NULL_VALUE) {
                                    var9 = null;
                                    break label303;
                                 }

                                 var9 = ret;
                                 break label302;
                              }
                           }

                           if (isSql) {
                              ret = this.getBeanManager().dynamicSqlQuery(query, (Object[])null, props, findMethod, wrap.isLocal(), isSelect ? ResultSet.class : Collection.class);
                           } else {
                              ret = this.getBeanManager().dynamicQuery(query, (Object[])null, props, findMethod, wrap.isLocal(), isSelect);
                           }
                        } catch (InternalException var20) {
                           if (var20.detail instanceof FinderException) {
                              throw (FinderException)var20.detail;
                           }

                           this.handleSystemException(wrap, var20);
                        } catch (Throwable var21) {
                           this.handleSystemException(wrap, var21);
                           throw new AssertionError("Should never reach here");
                        } finally {
                           this.postHomeInvoke(wrap, (Throwable)null);
                        }

                        var10000 = ret;
                        break label317;
                     }

                     var10000 = var9;
                     break label313;
                  }

                  var10000 = var9;
                  break label312;
               }
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

   public Object executePreparedQuery(String sql, PreparedQuery pquery, Map arguments, Map flattenedArguments, boolean isSelect) throws FinderException, RemoteException {
      LocalHolder var12;
      if ((var12 = LocalHolder.getInstance(_WLDF$INST_JPFLD_6, _WLDF$INST_JPFLD_JPMONS_6)) != null) {
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
      Object ret = null;

      try {
         if (!$assertionsDisabled && md == null) {
            throw new AssertionError();
         }

         EJBRuntimeUtils.pushEnvironment(this.beanManager.getEnvironmentContext());
         InvocationWrapper wrap = this.preHomeInvoke(md, new EJBContextHandler(md, args));
         Method findMethod = md.getMethod();
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
               ret = this.getBeanManager().remoteScalarFinder(wrap, findMethod, args);
            } else if (finderType == 2) {
               ret = this.getBeanManager().enumFinder(wrap, findMethod, args);
            } else {
               if (finderType != 4) {
                  throw new AssertionError("Unexpected value: " + finderType);
               }

               ret = this.getBeanManager().collectionFinder(findMethod, args, wrap.isLocal());
            }
         } catch (InternalException var27) {
            ee = var27.detail;
            if (this.deploymentInfo.getExceptionInfo(findMethod, ee).isAppException()) {
               throw (Exception)var27.detail;
            }

            this.handleSystemException(wrap, var27);
         } catch (Throwable var28) {
            ee = var28;
            this.handleSystemException(wrap, var28);
            throw new AssertionError("Should never reach here");
         } finally {
            if (wrap.getInvokeTx() == null && !((EntityBeanInfo)this.beanInfo).getIsBeanManagedPersistence()) {
               try {
                  this.getBeanManager().beforeCompletion(ret);
                  this.getBeanManager().afterCompletion(ret);
               } catch (InternalException var26) {
                  if (EJBRuntimeUtils.isAppException(md.getMethod(), var26)) {
                     throw (RemoveException)var26.detail;
                  }

                  this.handleSystemException(wrap, var26);
                  throw new AssertionError("Should never have reached here");
               }
            }

            this.postHomeInvoke(wrap, ee);
         }
      } finally {
         EJBRuntimeUtils.popEnvironment();
      }

      return ret;
   }

   private void handleInvalidationException(InternalException ie) throws RemoteException {
      if (ie.detail != null) {
         throw new RemoteException(ie.getMessage(), ie.detail);
      } else {
         throw new RemoteException(StackTraceUtilsClient.throwable2StackTrace(ie));
      }
   }

   public void invalidate(Object pk) throws RemoteException {
      if (pk == null) {
         Loggable l = EJBLogger.logNullInvalidateParameterLoggable();
         throw new RemoteException(l.getMessageText());
      } else {
         try {
            ((InvalidationBeanManager)this.getBeanManager()).invalidate((Object)null, (Object)pk);
         } catch (InternalException var3) {
            this.handleInvalidationException(var3);
         }

      }
   }

   public void invalidate(Collection pks) throws RemoteException {
      if (pks == null) {
         Loggable l = EJBLogger.logNullInvalidateParameterLoggable();
         throw new RemoteException(l.getMessageText());
      } else {
         try {
            ((InvalidationBeanManager)this.getBeanManager()).invalidate((Object)null, (Collection)pks);
         } catch (InternalException var3) {
            this.handleInvalidationException(var3);
         }

      }
   }

   public void invalidateAll() throws RemoteException {
      try {
         ((InvalidationBeanManager)this.getBeanManager()).invalidateAll((Object)null);
      } catch (InternalException var2) {
         this.handleInvalidationException(var2);
      }

   }

   public void invalidateLocalServer(Object pk) throws RemoteException {
      if (pk == null) {
         Loggable l = EJBLogger.logNullInvalidateParameterLoggable();
         throw new RemoteException(l.getMessageText());
      } else {
         ((InvalidationBeanManager)this.getBeanManager()).invalidateLocalServer((Object)null, (Object)pk);
      }
   }

   public void invalidateLocalServer(Collection pks) throws RemoteException {
      if (pks == null) {
         Loggable l = EJBLogger.logNullInvalidateParameterLoggable();
         throw new RemoteException(l.getMessageText());
      } else {
         ((InvalidationBeanManager)this.getBeanManager()).invalidateLocalServer((Object)null, (Collection)pks);
      }
   }

   public void invalidateAllLocalServer() throws RemoteException {
      ((InvalidationBeanManager)this.getBeanManager()).invalidateAllLocalServer((Object)null);
   }

   public BaseEntityManager getBeanManager() {
      return this.entityManager;
   }

   private static void debug(String s) {
      debugLogger.debug("[EntityEJBHome] " + s);
   }

   static {
      _WLDF$INST_FLD_EJB_Diagnostic_Home_Remove_Around_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "EJB_Diagnostic_Home_Remove_Around_Medium");
      _WLDF$INST_FLD_EJB_Diagnostic_Database_Access_Around_High = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "EJB_Diagnostic_Database_Access_Around_High");
      _WLDF$INST_FLD_EJB_Diagnostic_Home_Create_Around_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "EJB_Diagnostic_Home_Create_Around_Medium");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "EntityEJBHome.java", "weblogic.ejb.container.internal.EntityEJBHome", "create", "(Lweblogic/ejb/container/internal/MethodDescriptor;Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;[Ljava/lang/Object;)Ljavax/ejb/EJBObject;", 223, "", "", "", InstrumentationSupport.makeMap(new String[]{"EJB_Diagnostic_Home_Create_Around_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("md", "weblogic.diagnostics.instrumentation.gathering.EJBMethodDescriptorRenderer", false, true), null, null, null})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Home_Create_Around_Medium};
      _WLDF$INST_JPFLD_1 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "EntityEJBHome.java", "weblogic.ejb.container.internal.EntityEJBHome", "remove", "(Lweblogic/ejb/container/internal/MethodDescriptor;Ljavax/ejb/Handle;)V", 268, "", "", "", InstrumentationSupport.makeMap(new String[]{"EJB_Diagnostic_Home_Remove_Around_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("md", "weblogic.diagnostics.instrumentation.gathering.EJBMethodDescriptorRenderer", false, true), null})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_1 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Home_Remove_Around_Medium};
      _WLDF$INST_JPFLD_2 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "EntityEJBHome.java", "weblogic.ejb.container.internal.EntityEJBHome", "remove", "(Lweblogic/ejb/container/internal/MethodDescriptor;Ljava/lang/Object;)V", 280, "", "", "", InstrumentationSupport.makeMap(new String[]{"EJB_Diagnostic_Home_Remove_Around_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("md", "weblogic.diagnostics.instrumentation.gathering.EJBMethodDescriptorRenderer", false, true), null})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_2 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Home_Remove_Around_Medium};
      _WLDF$INST_JPFLD_3 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "EntityEJBHome.java", "weblogic.ejb.container.internal.EntityEJBHome", "findByPrimaryKey", "(Lweblogic/ejb/container/internal/MethodDescriptor;Ljava/lang/Object;)Ljavax/ejb/EJBObject;", 338, "", "", "", InstrumentationSupport.makeMap(new String[]{"EJB_Diagnostic_Database_Access_Around_High"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("md", "weblogic.diagnostics.instrumentation.gathering.EJBMethodDescriptorRenderer", false, true), null})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_3 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Database_Access_Around_High};
      _WLDF$INST_JPFLD_4 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "EntityEJBHome.java", "weblogic.ejb.container.internal.EntityEJBHome", "executeQuery", "(Ljava/lang/String;Lweblogic/ejb/WLQueryProperties;Z)Ljava/lang/Object;", 912, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_4 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Database_Access_Around_High};
      _WLDF$INST_JPFLD_5 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "EntityEJBHome.java", "weblogic.ejb.container.internal.EntityEJBHome", "executeQuery", "(Ljava/lang/String;Lweblogic/ejb/WLQueryProperties;ZZ)Ljava/lang/Object;", 919, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_5 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Database_Access_Around_High};
      _WLDF$INST_JPFLD_6 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "EntityEJBHome.java", "weblogic.ejb.container.internal.EntityEJBHome", "executePreparedQuery", "(Ljava/lang/String;Lweblogic/ejb/PreparedQuery;Ljava/util/Map;Ljava/util/Map;Z)Ljava/lang/Object;", 994, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_6 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Database_Access_Around_High};
      $assertionsDisabled = !EntityEJBHome.class.desiredAssertionStatus();
      runtimeLogger = EJBDebugService.cmpRuntimeLogger;
   }
}
