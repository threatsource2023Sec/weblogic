package weblogic.ejb.container.manager;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.CreateException;
import javax.ejb.DuplicateKeyException;
import javax.ejb.EJBLocalObject;
import javax.ejb.EJBObject;
import javax.ejb.EnterpriseBean;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.FinderException;
import javax.ejb.NoSuchEntityException;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.TimedObject;
import javax.ejb.Timer;
import javax.naming.Context;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.ejb.EJBObjectEnum;
import weblogic.ejb.OptimisticConcurrencyException;
import weblogic.ejb.PreparedQuery;
import weblogic.ejb.WLQueryProperties;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.InternalException;
import weblogic.ejb.container.cache.CacheKey;
import weblogic.ejb.container.cmp.rdbms.RDBMSBean;
import weblogic.ejb.container.cmp.rdbms.RDBMSM2NSet;
import weblogic.ejb.container.cmp.rdbms.RDBMSPersistenceManager;
import weblogic.ejb.container.interfaces.BaseEJBLocalHomeIntf;
import weblogic.ejb.container.interfaces.BaseEJBRemoteHomeIntf;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.CachingManager;
import weblogic.ejb.container.interfaces.EntityBeanInfo;
import weblogic.ejb.container.interfaces.EntityTxManager;
import weblogic.ejb.container.interfaces.ISecurityHelper;
import weblogic.ejb.container.interfaces.InvalidationBeanManager;
import weblogic.ejb.container.interfaces.PoolIntf;
import weblogic.ejb.container.interfaces.WLEJBContext;
import weblogic.ejb.container.interfaces.WLEnterpriseBean;
import weblogic.ejb.container.interfaces.WLEntityBean;
import weblogic.ejb.container.internal.AllowedMethodsHelper;
import weblogic.ejb.container.internal.EJBRuntimeUtils;
import weblogic.ejb.container.internal.EntityEJBContextImpl;
import weblogic.ejb.container.internal.EntityEJBHome;
import weblogic.ejb.container.internal.EntityEJBLocalHome;
import weblogic.ejb.container.internal.InvocationWrapper;
import weblogic.ejb.container.internal.SecurityHelper;
import weblogic.ejb.container.internal.TransactionService;
import weblogic.ejb.container.internal.entity.EntityTxManagerImpl;
import weblogic.ejb.container.monitoring.EJBCacheRuntimeMBeanImpl;
import weblogic.ejb.container.monitoring.EntityEJBRuntimeMBeanImpl;
import weblogic.ejb.container.persistence.spi.CMPBean;
import weblogic.ejb.container.persistence.spi.CMPBeanDescriptor;
import weblogic.ejb.container.persistence.spi.CMPBeanManager;
import weblogic.ejb.container.persistence.spi.EloWrapper;
import weblogic.ejb.container.persistence.spi.EoWrapper;
import weblogic.ejb.container.persistence.spi.PersistenceManager;
import weblogic.ejb.container.persistence.spi.RSInfo;
import weblogic.ejb.container.pool.EntityPool;
import weblogic.ejb.spi.EJBRuntimeHolder;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.ejb20.interfaces.PrincipalNotFoundException;
import weblogic.ejb20.utils.OrderedSet;
import weblogic.j2ee.MethodInvocationHelper;
import weblogic.logging.Loggable;
import weblogic.management.ManagementException;
import weblogic.management.runtime.EntityEJBRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityManager;
import weblogic.utils.StackTraceUtilsClient;

public abstract class BaseEntityManager extends BaseEJBManager implements CachingManager {
   private static final AuthenticatedSubject KERNEL_ID;
   private PoolIntf entityPool;
   protected boolean findersLoadBean = false;
   protected boolean isBeanManagedPersistence;
   protected boolean uses20CMP;
   protected PersistenceManager persistence;
   protected RDBMSPersistenceManager rdbmsPersistence;
   private boolean isReentrant;
   private boolean isCascadeDelete;
   protected boolean enableBatchOperations = false;
   protected boolean orderDatabaseOperations = false;
   protected boolean isOptimistic = false;
   private Method findByPrimaryKeyMethod;
   private Method isModifiedMethod;
   protected volatile EntityBean reflectionTargetLocal;
   protected volatile EntityBean reflectionTargetRemote;
   private Map bmMap;
   private final Set parentBeanManagerSet = new HashSet();
   private final Set childBeanManagerSet = new HashSet();
   private final Set many2ManyCmrFieldInsertSet = new HashSet();
   private final Set notNullableParentBeanManagerSet = new HashSet();
   private final Set notNullableChildBeanManagerSet = new HashSet();
   private boolean cycleExists = false;
   protected EJBCacheRuntimeMBeanImpl cacheRTMBean;
   protected Class[] extraPostCreateExceptions = new Class[]{CreateException.class};
   private boolean isReadOnly = false;
   private boolean initialized = false;
   private AuthenticatedSubject fileDesc;
   // $FF: synthetic field
   static final boolean $assertionsDisabled;
   static final long serialVersionUID = 1304228197390601516L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.ejb.container.manager.BaseEntityManager");
   static final DelegatingMonitor _WLDF$INST_FLD_EJB_Diagnostic_Database_Access_Around_High;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;
   static final JoinPoint _WLDF$INST_JPFLD_1;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_1;
   static final JoinPoint _WLDF$INST_JPFLD_2;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_2;

   public BaseEntityManager(EJBRuntimeHolder runtime) {
      super(runtime);
   }

   public void setup(BaseEJBRemoteHomeIntf remoteHome, BaseEJBLocalHomeIntf localHome, BeanInfo i, Context environmentContext, ISecurityHelper sh) throws WLDeploymentException {
      super.setup(remoteHome, localHome, i, environmentContext, sh);
      EntityBeanInfo ebi = (EntityBeanInfo)i;
      if (5 == ebi.getConcurrencyStrategy()) {
         this.isReadOnly = true;
      }

      this.isBeanManagedPersistence = ebi.getIsBeanManagedPersistence();
      this.beanClass = ebi.getGeneratedBeanClass();
      Class pkClass = ebi.getPrimaryKeyClass();
      CMPBeanDescriptor rtMBean;
      if (ebi.isUnknownPrimaryKey() && !ebi.getIsBeanManagedPersistence()) {
         rtMBean = ebi.getCMPInfo().getCMPBeanDescriptor(ebi.getEJBName());
         pkClass = rtMBean.getPrimaryKeyClass();
      }

      try {
         this.findByPrimaryKeyMethod = ebi.getGeneratedBeanInterface().getMethod("ejbFindByPrimaryKey", pkClass);
      } catch (NoSuchMethodException var13) {
         throw new AssertionError(var13);
      }

      rtMBean = null;

      EntityEJBRuntimeMBeanImpl rtMBean;
      try {
         rtMBean = new EntityEJBRuntimeMBeanImpl(i, this.getEJBComponentRuntime(), this instanceof ExclusiveEntityManager, this.getTimerManager());
         this.setEJBRuntimeMBean(rtMBean);
         this.addEJBRuntimeMBean(rtMBean);
         this.cacheRTMBean = (EJBCacheRuntimeMBeanImpl)((EntityEJBRuntimeMBean)this.getEJBRuntimeMBean()).getCacheRuntime();
      } catch (ManagementException var12) {
         Loggable l = EJBLogger.logFailedToCreateRuntimeMBeanLoggable();
         throw new WLDeploymentException(l.getMessageText(), var12);
      }

      this.perhapsSetupTimerManager(rtMBean.getTimerRuntime());
      this.txManager = new EntityTxManagerImpl(this);
      this.isReentrant = ebi.isReentrant();
      if (!this.isBeanManagedPersistence) {
         this.findersLoadBean = ebi.getCMPInfo().findersLoadBean();
      }

      if (!this.isBeanManagedPersistence) {
         this.uses20CMP = ebi.getCMPInfo().uses20CMP();
      } else {
         this.uses20CMP = false;
      }

      String isModifiedMethodName = ebi.getIsModifiedMethodName();
      if (isModifiedMethodName == null) {
         this.isModifiedMethod = null;
      } else {
         try {
            Class intf = ebi.getGeneratedBeanInterface();
            this.isModifiedMethod = intf.getMethod(isModifiedMethodName, (Class[])null);
         } catch (NoSuchMethodException var14) {
            if (!this.uses20CMP) {
               throw new AssertionError(var14);
            }
         }
      }

      this.persistence = this.setupPM(ebi);
      if (!this.initialized && this.uses20CMP && this.persistence instanceof RDBMSPersistenceManager) {
         RDBMSBean rbean = ((RDBMSPersistenceManager)this.persistence).getRDBMSBean();
         this.isCascadeDelete = rbean.isCascadeDelete();
      }

      if (this.uses20CMP) {
         this.isOptimistic = ebi.isOptimistic();
         if (this.persistence instanceof RDBMSPersistenceManager) {
            this.rdbmsPersistence = (RDBMSPersistenceManager)this.persistence;
            this.enableBatchOperations = this.rdbmsPersistence.getEnableBatchOperations();
            this.orderDatabaseOperations = this.rdbmsPersistence.getOrderDatabaseOperations();
         }
      }

      if (ebi.getRunAsPrincipalName() != null) {
         try {
            this.fileDesc = sh.getSubjectForPrincipal(ebi.getRunAsPrincipalName());
         } catch (PrincipalNotFoundException var11) {
            throw new WLDeploymentException(var11.getMessage(), var11);
         }
      }

      this.entityPool = new EntityPool((EntityEJBHome)remoteHome, (EntityEJBLocalHome)localHome, this, i, ((EntityEJBRuntimeMBean)this.getEJBRuntimeMBean()).getPoolRuntime());
      this.initialized = true;
   }

   public Object getInvokeTxOrThread() {
      Transaction tx = TransactionService.getTransaction();
      return tx == null ? Thread.currentThread() : tx;
   }

   private PersistenceManager setupPM(EntityBeanInfo ebi) throws WLDeploymentException {
      Transaction txStartedHere = null;

      PersistenceManager var17;
      try {
         if (TransactionService.getTransaction() == null) {
            TransactionService.getTransactionManager().begin();
            txStartedHere = TransactionService.getTransaction();
         }

         PersistenceManager pm = ebi.getPersistenceManager();
         pm.setup(this);
         if (txStartedHere != null) {
            TransactionService.getTransactionManager().commit();
            txStartedHere = null;
         }

         var17 = pm;
      } catch (WLDeploymentException var14) {
         throw var14;
      } catch (Exception var15) {
         Loggable l = EJBLogger.logPersistenceManagerSetupErrorLoggable();
         throw new WLDeploymentException(l.getMessageText(), var15);
      } finally {
         if (txStartedHere != null) {
            try {
               TransactionService.getTransactionManager().rollback();
            } catch (Exception var13) {
            }
         }

      }

      return var17;
   }

   public void setBMMap(Map bmMap) {
      this.bmMap = bmMap;
   }

   protected EnterpriseBean createNewBeanInstance() throws IllegalAccessException, InstantiationException {
      return (EnterpriseBean)this.beanClass.newInstance();
   }

   public EntityBean createBean(EJBObject eo, EJBLocalObject elo) throws InternalException {
      EntityBean bean = null;
      this.pushEnvironment();
      MethodInvocationHelper.pushMethodObject(this.beanInfo);
      if (this.fileDesc != null) {
         SecurityHelper.pushRunAsSubject(KERNEL_ID, this.fileDesc);
      }

      Thread thread = Thread.currentThread();
      ClassLoader clSave = thread.getContextClassLoader();
      thread.setContextClassLoader(this.beanInfo.getModuleClassLoader());

      try {
         EntityContext ctx = new EntityEJBContextImpl((Object)null, this, (EntityEJBHome)this.remoteHome, (EntityEJBLocalHome)this.localHome, eo, elo);
         AllowedMethodsHelper.pushMethodInvocationState(1);

         try {
            bean = (EntityBean)this.allocateBean();
         } finally {
            AllowedMethodsHelper.popMethodInvocationState();
         }

         ((WLEJBContext)ctx).setBean(bean);

         try {
            bean.setEntityContext(ctx);
         } catch (Exception var18) {
            throw new InternalException("Error during setEntityContext: ", var18);
         }

         ((WLEnterpriseBean)bean).__WL_setEJBContext(ctx);
         if (!this.isBeanManagedPersistence) {
            if (this.uses20CMP) {
               if (!$assertionsDisabled && this.bmMap == null) {
                  throw new AssertionError();
               }

               if (!$assertionsDisabled && this.persistence == null) {
                  throw new AssertionError();
               }

               try {
                  ((CMPBean)bean).__WL_setup(this.bmMap, this.persistence);
               } catch (Throwable var17) {
                  EJBRuntimeUtils.throwInternalException("Error initializing CMP", var17);
               }
            } else {
               ((CMPBean)bean).__WL_setup((Map)null, this.persistence);
            }

            ((CMPBean)bean).__WL_setConnected(true);
         }
      } finally {
         thread.setContextClassLoader(clSave);
         if (this.fileDesc != null) {
            SecurityHelper.popRunAsSubject(KERNEL_ID);
         }

         MethodInvocationHelper.popMethodObject(this.beanInfo);
         this.popEnvironment();
      }

      return bean;
   }

   public void invokeTimeoutMethod(Object bean, Timer timer, Method timeoutMethod) {
      ((TimedObject)bean).ejbTimeout(timer);
   }

   protected EJBCacheRuntimeMBeanImpl getEJBCacheRuntimeMBeanImpl() {
      return this.cacheRTMBean;
   }

   public boolean isReadOnly() {
      return this.isReadOnly;
   }

   public Class getBeanClass() {
      return this.beanClass;
   }

   public Method getFindByPrimaryKeyMethod() {
      return this.findByPrimaryKeyMethod;
   }

   public void addParentBeanManager(BaseEntityManager bm) {
      this.parentBeanManagerSet.add(bm);
   }

   public void addChildBeanManager(BaseEntityManager bm) {
      this.childBeanManagerSet.add(bm);
   }

   public void addNotNullableParentBeanManager(BaseEntityManager bm) {
      this.notNullableParentBeanManagerSet.add(bm);
   }

   public void addNotNullableChildBeanManager(BaseEntityManager bm) {
      this.notNullableChildBeanManagerSet.add(bm);
   }

   public boolean setCycleExists(Set finishedBeanManagerSet) {
      finishedBeanManagerSet.add(this);
      if (!this.cycleExists) {
         Iterator var2 = this.parentBeanManagerSet.iterator();

         while(var2.hasNext()) {
            BaseEntityManager parentBeanManager = (BaseEntityManager)var2.next();
            if (!finishedBeanManagerSet.contains(parentBeanManager)) {
               if (parentBeanManager.setCycleExists(finishedBeanManagerSet)) {
                  this.cycleExists = true;
               }
            } else {
               if (debugLogger.isDebugEnabled()) {
                  debug("cycle exists: from " + this.beanInfo.getEJBName() + " to " + parentBeanManager.getBeanInfo().getEJBName());
               }

               this.cycleExists = true;
            }
         }
      }

      finishedBeanManagerSet.remove(this);
      return this.cycleExists;
   }

   public boolean getOrderDatabaseOperations() {
      return this.orderDatabaseOperations;
   }

   public void addM2NInsertSet(String cmrf) {
      if (debugLogger.isDebugEnabled()) {
         debug(this.beanInfo.getEJBName() + "  adding cmrfield '" + cmrf + "' to M2NInsertSet");
      }

      this.many2ManyCmrFieldInsertSet.add(cmrf);
   }

   public boolean isM2NInsertSet(String cmrf) {
      return this.many2ManyCmrFieldInsertSet.contains(cmrf);
   }

   public abstract EnterpriseBean preHomeInvoke(InvocationWrapper var1) throws InternalException;

   public abstract void postHomeInvoke(InvocationWrapper var1) throws InternalException;

   public abstract void selectedForReplacement(CacheKey var1, EntityBean var2);

   public abstract void passivateAndBacktoPool(CacheKey var1, EntityBean var2);

   public abstract void loadBeanFromRS(CacheKey var1, EntityBean var2, RSInfo var3) throws InternalException;

   public abstract void enrollInTransaction(Transaction var1, CacheKey var2, EntityBean var3, RSInfo var4) throws InternalException;

   public abstract void passivateAndRelease(CacheKey var1, EntityBean var2);

   public abstract void operationsComplete(Transaction var1, Object var2);

   protected abstract List pkListToBeanList(Collection var1, Transaction var2, boolean var3);

   protected abstract void prepareVerificationForBatch(Collection var1, Transaction var2) throws InternalException;

   protected abstract Map pkListToPkBeanMap(Collection var1, Transaction var2, boolean var3);

   public void registerInsertBeanAndTxUser(Object pk, Transaction tx, WLEntityBean bean) throws InternalException {
      if (debugLogger.isDebugEnabled()) {
         debug("Setting up tx listener for batch insert for tx: " + tx);
      }

      if (tx != null) {
         this.getEntityTxManager().registerInsertBean(pk, tx);
      }

      bean.__WL_setLoadUser(this.getCurrentSubject());
   }

   private AuthenticatedSubject getCurrentSubject() {
      return SecurityManager.getCurrentSubject(KERNEL_ID);
   }

   public boolean isBeanManagedPersistence() {
      return this.isBeanManagedPersistence;
   }

   public boolean registerDeleteBean(Object pk, Transaction tx) throws InternalException {
      if (debugLogger.isDebugEnabled()) {
         debug("Setting up tx listener for batch delete for tx: " + tx);
      }

      return tx != null ? this.getEntityTxManager().registerDeleteBean(pk, tx) : false;
   }

   public void registerInsertDeletedBeanAndTxUser(Object pk, Transaction tx, WLEntityBean bean) throws InternalException {
      if (debugLogger.isDebugEnabled()) {
         debug("Setting up tx listener for batch insert removed for tx: " + tx);
      }

      if (tx != null) {
         this.getEntityTxManager().registerInsertDeletedBean(pk, tx);
      }

      bean.__WL_setLoadUser(this.getCurrentSubject());
   }

   public void registerM2NJoinTableInsert(String cmrField, Object pk, Transaction tx) throws InternalException {
      if (this.many2ManyCmrFieldInsertSet.contains(cmrField)) {
         if (debugLogger.isDebugEnabled()) {
            debug("Register M2N Join Table insert for cmrField: " + cmrField + ", pk: " + pk + ", tx: " + tx);
         }

         if (tx != null) {
            this.getEntityTxManager().registerM2NJoinTableInsert(pk, cmrField, tx);
         }

      }
   }

   public boolean needsToBeInserted(Object pk) throws SystemException, RollbackException {
      return this.getEntityTxManager().needsToBeInserted((Transaction)this.getInvokeTxOrThread(), pk);
   }

   public void addBeanToInsertStmt(PreparedStatement[] stmtArray, List finishedKeys, CMPBean bean, boolean internalFlush, boolean addBatch) throws Exception {
      if (!finishedKeys.contains(bean.__WL_getPrimaryKey())) {
         try {
            finishedKeys.add(bean.__WL_getPrimaryKey());
            if (addBatch && ((RDBMSPersistenceManager)this.persistence).isSelfRelationship()) {
               if (debugLogger.isDebugEnabled()) {
                  debug(this.beanInfo.getEJBName() + ": calling __WL_addSelfRelatedBeansToInsertStmt");
               }

               bean.__WL_addSelfRelatedBeansToInsertStmt(stmtArray, finishedKeys, internalFlush);
            }

            if (debugLogger.isDebugEnabled()) {
               debug(this.beanInfo.getEJBName() + ": adding bean to insert stmt where pk=" + bean.__WL_getPrimaryKey());
            }

            if (((RDBMSPersistenceManager)this.persistence).isFkColsNullable() && ((RDBMSPersistenceManager)this.persistence).isSelfRelationship()) {
               bean.__WL_setBeanParamsForStmtArray(stmtArray, (boolean[])null, 0, true);
               this.registerModifiedBean(bean.__WL_getPrimaryKey(), (Transaction)this.getInvokeTxOrThread());
            } else {
               bean.__WL_setBeanParamsForStmtArray(stmtArray, (boolean[])null, 0, false);
            }

            if (addBatch) {
               PreparedStatement[] var6 = stmtArray;
               int var7 = stmtArray.length;

               for(int var8 = 0; var8 < var7; ++var8) {
                  PreparedStatement stmt = var6[var8];
                  if (stmt != null) {
                     stmt.addBatch();
                  }
               }
            }
         } catch (SQLException var10) {
            EJBRuntimeUtils.throwInternalException("Error during addBatch():", var10);
         }

      }
   }

   public void executeInsertStmt(List keys, Transaction tx, Set finishedBeanManagerSet, boolean isFlushModified, boolean internalFlush, Collection flushedInsertKeys) throws InternalException {
      LocalHolder var22;
      if ((var22 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var22.argsCapture) {
            var22.args = InstrumentationSupport.toSensitive(7);
         }

         InstrumentationSupport.createDynamicJoinPoint(var22);
         InstrumentationSupport.preProcess(var22);
         var22.resetPostBegin();
      }

      label1055: {
         label1056: {
            try {
               finishedBeanManagerSet.add(this);
               if (this.parentBeanManagerSet != null) {
                  Iterator var7 = this.parentBeanManagerSet.iterator();

                  while(var7.hasNext()) {
                     BaseEntityManager parentBeanManager = (BaseEntityManager)var7.next();
                     if (!finishedBeanManagerSet.contains(parentBeanManager) && !this.notNullableChildBeanManagerSet.contains(parentBeanManager)) {
                        parentBeanManager.getEntityTxManager().executeInsertOperations((Transaction)this.getInvokeTxOrThread(), finishedBeanManagerSet, isFlushModified, internalFlush);
                     }
                  }
               }

               if (keys.isEmpty()) {
                  break label1055;
               }

               int j = false;
               Connection conn = null;
               PreparedStatement[] stmtArray = null;
               List finishedKeys = new ArrayList();
               CMPBean bean = null;
               List beans = this.pkListToBeanList(keys, tx, false);
               if (beans.isEmpty()) {
                  break label1056;
               }

               boolean beanInserted = false;

               try {
                  bean = (CMPBean)beans.get(0);
                  conn = ((RDBMSPersistenceManager)this.persistence).getConnection();
                  if (((RDBMSPersistenceManager)this.persistence).isFkColsNullable() && ((RDBMSPersistenceManager)this.persistence).isSelfRelationship()) {
                     stmtArray = bean.__WL_getStmtArray(conn, (boolean[])null, 0, true);
                  } else {
                     stmtArray = bean.__WL_getStmtArray(conn, (boolean[])null, 0, false);
                  }

                  int var17;
                  Iterator it;
                  if (this.enableBatchOperations && keys.size() > 1) {
                     it = beans.iterator();

                     while(it.hasNext()) {
                        bean = (CMPBean)it.next();
                        this.addBeanToInsertStmt(stmtArray, finishedKeys, bean, internalFlush, true);
                     }

                     if (debugLogger.isDebugEnabled()) {
                        debug(this.beanInfo.getEJBName() + ": about to execute batch create.");
                     }

                     int[] numOfRowsArray = new int[stmtArray.length];
                     PreparedStatement[] var54 = stmtArray;
                     int var59 = stmtArray.length;
                     var17 = 0;

                     while(true) {
                        if (var17 >= var59) {
                           beanInserted = true;

                           Iterator it;
                           for(it = beans.iterator(); it.hasNext(); bean.__WL_perhapsReloadOptimisticColumn()) {
                              bean = (CMPBean)it.next();
                              if (((RDBMSPersistenceManager)this.persistence).isFkColsNullable()) {
                                 bean.__WL_resetIsModifiedVars(0, conn, true);
                              } else {
                                 bean.__WL_resetIsModifiedVars(0, conn, false);
                              }
                           }

                           if (internalFlush) {
                              it = keys.iterator();

                              while(it.hasNext()) {
                                 flushedInsertKeys.add(it.next());
                              }
                           }

                           keys.clear();
                           break;
                        }

                        PreparedStatement ps = var54[var17];
                        if (ps != null) {
                           numOfRowsArray = ps.executeBatch();

                           for(int j = 0; j < numOfRowsArray.length; ++j) {
                              if (numOfRowsArray[j] == 0 || numOfRowsArray[j] == -3) {
                                 throw new Exception("Failed to CREATE Bean. Primary Key Value: '" + keys.get(j) + "'");
                              }
                           }
                        }

                        ++var17;
                     }
                  } else {
                     it = beans.iterator();

                     while(it.hasNext()) {
                        bean = (CMPBean)it.next();
                        this.addBeanToInsertStmt(stmtArray, finishedKeys, bean, internalFlush, false);
                        if (debugLogger.isDebugEnabled()) {
                           debug(this.beanInfo.getEJBName() + ": about to execute multi create.");
                        }

                        int numOfRows = false;
                        PreparedStatement[] var56 = stmtArray;
                        var17 = stmtArray.length;

                        for(int var18 = 0; var18 < var17; ++var18) {
                           PreparedStatement ps = var56[var18];
                           if (ps != null) {
                              int numOfRows = ps.executeUpdate();
                              if (numOfRows == 0) {
                                 throw new Exception("Failed to CREATE Bean. Primary Key Value: '" + bean.__WL_getPrimaryKey() + "'");
                              }
                           }
                        }

                        beanInserted = true;
                        if (((RDBMSPersistenceManager)this.persistence).isFkColsNullable()) {
                           bean.__WL_resetIsModifiedVars(0, conn, true);
                        } else {
                           bean.__WL_resetIsModifiedVars(0, conn, false);
                        }

                        bean.__WL_perhapsReloadOptimisticColumn();
                        Object pk = bean.__WL_getPrimaryKey();
                        keys.remove(pk);
                        if (internalFlush) {
                           flushedInsertKeys.add(pk);
                        }
                     }
                  }

                  it = this.notNullableChildBeanManagerSet.iterator();

                  while(it.hasNext()) {
                     BaseEntityManager childBeanManager = (BaseEntityManager)it.next();
                     childBeanManager.getEntityTxManager().executeInsertOperations((Transaction)this.getInvokeTxOrThread(), finishedBeanManagerSet, isFlushModified, internalFlush);
                  }
               } catch (SQLException var43) {
                  SQLException se = var43;
                  if ((!this.enableBatchOperations || keys.size() <= 1) && !beanInserted) {
                     Object pk = bean.__WL_getPrimaryKey();
                     if (pk != null) {
                        boolean exists = false;

                        try {
                           exists = bean.__WL_exists(pk);
                        } catch (Exception var41) {
                           EJBRuntimeUtils.throwInternalException("EJB Exception:", se);
                        } finally {
                           if (!internalFlush) {
                              this.cacheRemoveCMPBeansOnError(tx, beans);
                           }

                        }

                        if (exists) {
                           EJBRuntimeUtils.throwInternalException("EJB Exception:", new DuplicateKeyException("Bean with primary key: '" + pk + "' already exists."));
                        }
                     }

                     EJBRuntimeUtils.throwInternalException("EJB Exception:", var43);
                  } else {
                     EJBRuntimeUtils.throwInternalException("EJB Exception:", var43);
                  }
               } catch (Throwable var44) {
                  EJBLogger.logExcepInMethod1("executeInsertStmt", var44);
                  if (!internalFlush) {
                     this.cacheRemoveCMPBeansOnError(tx, beans);
                  }

                  EJBRuntimeUtils.throwInternalException("EJB Exception:", var44);
               } finally {
                  ((RDBMSPersistenceManager)this.persistence).releaseArrayResources(conn, stmtArray, (ResultSet[])null);
               }
            } catch (Throwable var46) {
               if (var22 != null) {
                  var22.th = var46;
                  InstrumentationSupport.postProcess(var22);
               }

               throw var46;
            }

            if (var22 != null) {
               InstrumentationSupport.postProcess(var22);
            }

            return;
         }

         if (var22 != null) {
            InstrumentationSupport.postProcess(var22);
         }

         return;
      }

      if (var22 != null) {
         InstrumentationSupport.postProcess(var22);
      }

   }

   public void addBeanToUpdateStmt(PreparedStatement[] stmtArray, boolean[] isModifiedUnion, CMPBean bean, boolean addBatch) throws Exception {
      try {
         if (debugLogger.isDebugEnabled()) {
            debug(this.beanInfo.getEJBName() + ": adding bean to update stmt where pk=" + bean.__WL_getPrimaryKey());
         }

         bean.__WL_setBeanParamsForStmtArray(stmtArray, isModifiedUnion, 1, false);
         if (addBatch) {
            PreparedStatement[] var5 = stmtArray;
            int var6 = stmtArray.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               PreparedStatement ps = var5[var7];
               if (ps != null) {
                  ps.addBatch();
               }
            }
         }
      } catch (SQLException var9) {
         EJBRuntimeUtils.throwInternalException("Error during addBatch():", var9);
      }

   }

   public void executeUpdateStmt(List keys, Transaction tx, Set finishedBeanManagerSet, boolean isFlushModified, boolean internalFlush, Collection flushedModifiedKeys) throws InternalException {
      LocalHolder var32;
      if ((var32 = LocalHolder.getInstance(_WLDF$INST_JPFLD_1, _WLDF$INST_JPFLD_JPMONS_1)) != null) {
         if (var32.argsCapture) {
            var32.args = InstrumentationSupport.toSensitive(7);
         }

         InstrumentationSupport.createDynamicJoinPoint(var32);
         InstrumentationSupport.preProcess(var32);
         var32.resetPostBegin();
      }

      label1328: {
         label1329: {
            label1330: {
               try {
                  finishedBeanManagerSet.add(this);
                  Iterator var7 = this.childBeanManagerSet.iterator();

                  while(var7.hasNext()) {
                     BaseEntityManager childBeanManager = (BaseEntityManager)var7.next();
                     if (!finishedBeanManagerSet.contains(childBeanManager)) {
                        childBeanManager.getEntityTxManager().executeUpdateOperations((Transaction)this.getInvokeTxOrThread(), finishedBeanManagerSet, isFlushModified, internalFlush);
                     }
                  }

                  if (!keys.isEmpty()) {
                     int j = false;
                     Connection conn = null;
                     PreparedStatement[] stmtArray = null;
                     Map pkToBeans = null;
                     CMPBean bean = null;
                     boolean batchingDisabled = false;
                     boolean useSendBatch = false;
                     if (keys.size() > 1) {
                        useSendBatch = this.rdbmsPersistence.perhapsUseSendBatchForOracle();
                        if (!useSendBatch) {
                           this.prepareVerificationForBatch(keys, tx);
                        }
                     }

                     pkToBeans = this.pkListToPkBeanMap(keys, tx, ((RDBMSPersistenceManager)this.persistence).isSelfRelationship());
                     if (pkToBeans.isEmpty()) {
                        break label1328;
                     }

                     boolean var55 = false;

                     PreparedStatement[] var71;
                     int var74;
                     int var77;
                     Object ps;
                     label1332: {
                        PreparedStatement[] isModifiedBean;
                        int var78;
                        int var82;
                        label1364: {
                           try {
                              var55 = true;
                              bean = (CMPBean)pkToBeans.values().iterator().next();
                              conn = ((RDBMSPersistenceManager)this.persistence).getConnection();
                              if (this.enableBatchOperations && this.isOptimistic) {
                                 batchingDisabled = this.verifyQueriesForBatching(pkToBeans.values());
                              }

                              if (batchingDisabled) {
                                 EJBLogger.logBatchingTurnedOff(this.beanInfo.getEJBName());
                              }

                              int i;
                              int i;
                              int var22;
                              PreparedStatement ps;
                              Object pk;
                              Loggable l;
                              boolean[] isModifiedBean;
                              boolean executeUpdated;
                              boolean b;
                              PreparedStatement[] var86;
                              int rowsUpdated;
                              if (this.enableBatchOperations && !batchingDisabled && keys.size() > 1) {
                                 boolean[] isModifiedUnion = null;

                                 for(Iterator it = pkToBeans.values().iterator(); it.hasNext(); isModifiedUnion = bean.__WL_getIsModifiedUnion(isModifiedUnion)) {
                                    bean = (CMPBean)it.next();
                                 }

                                 boolean isModified = false;
                                 if (isModifiedUnion != null) {
                                    isModifiedBean = isModifiedUnion;
                                    var78 = isModifiedUnion.length;

                                    for(var82 = 0; var82 < var78; ++var82) {
                                       b = isModifiedBean[var82];
                                       isModified |= b;
                                    }

                                    if (!isModified) {
                                       if (debugLogger.isDebugEnabled()) {
                                          debug("executeUpdateStmt(): nothing changed, avoided stores for update");
                                          var55 = false;
                                       } else {
                                          var55 = false;
                                       }
                                       break label1364;
                                    }
                                 }

                                 stmtArray = bean.__WL_getStmtArray(conn, isModifiedUnion, 1, false);
                                 int[] expectedUpdatedRowCount = new int[stmtArray.length];
                                 int[] numOfRowsUpdated = new int[stmtArray.length];
                                 executeUpdated = false;
                                 if (useSendBatch) {
                                    PreparedStatement[] var84 = stmtArray;
                                    i = stmtArray.length;

                                    for(rowsUpdated = 0; rowsUpdated < i; ++rowsUpdated) {
                                       PreparedStatement ps = var84[rowsUpdated];
                                       if (ps != null) {
                                          this.invokeOracleSetExecuteBatch(ps, 20);
                                       }
                                    }
                                 }

                                 Iterator it = pkToBeans.values().iterator();

                                 while(true) {
                                    while(it.hasNext()) {
                                       bean = (CMPBean)it.next();
                                       if (useSendBatch) {
                                          bean.__WL_setBeanParamsForStmtArray(stmtArray, isModifiedUnion, 1, false);

                                          for(i = 0; i < stmtArray.length; ++i) {
                                             if (stmtArray[i] != null) {
                                                rowsUpdated = stmtArray[i].executeUpdate();
                                                numOfRowsUpdated[i] += rowsUpdated;
                                                int var10002 = expectedUpdatedRowCount[i]++;
                                             }
                                          }
                                       } else {
                                          this.addBeanToUpdateStmt(stmtArray, isModifiedUnion, bean, true);
                                       }
                                    }

                                    if (debugLogger.isDebugEnabled()) {
                                       debug(this.beanInfo.getEJBName() + ": about to execute batch update.");
                                    }

                                    if (useSendBatch) {
                                       for(i = 0; i < stmtArray.length; ++i) {
                                          if (stmtArray[i] != null) {
                                             numOfRowsUpdated[i] += this.invokeOracleSendBatch(stmtArray[i]);
                                             executeUpdated = true;
                                             if (numOfRowsUpdated[i] != expectedUpdatedRowCount[i]) {
                                                if (this.isOptimistic) {
                                                   throw new OptimisticConcurrencyException("Instance/s of bean '" + this.beanInfo.getEJBName() + "' in the batch was changed by another transaction. The primary key is unknown due to Oracle driver limitation.");
                                                }

                                                throw new NoSuchEntityException("Instance/s of bean '" + this.beanInfo.getEJBName() + "' in the batch does not exist. The primary key is unknown due to Oracle driver limitation.");
                                             }
                                          }
                                       }
                                    } else {
                                       int[] numOfRowsArray = new int[stmtArray.length];
                                       var86 = stmtArray;
                                       rowsUpdated = stmtArray.length;

                                       for(var22 = 0; var22 < rowsUpdated; ++var22) {
                                          ps = var86[var22];
                                          if (ps != null) {
                                             numOfRowsArray = ps.executeBatch();
                                             executeUpdated = true;

                                             for(int j = 0; j < numOfRowsArray.length; ++j) {
                                                if (numOfRowsArray[j] == 0 || numOfRowsArray[j] == -3) {
                                                   pk = keys.get(j);
                                                   if (this.isOptimistic) {
                                                      l = EJBLogger.logoptimisticUpdateFailedLoggable(this.beanInfo.getEJBName(), pk.toString());
                                                      throw new OptimisticConcurrencyException(l.getMessageText());
                                                   }

                                                   l = EJBLogger.logbeanDoesNotExistLoggable(this.beanInfo.getEJBName(), pk.toString());
                                                   throw new NoSuchEntityException(l.getMessageText());
                                                }
                                             }
                                          }
                                       }
                                    }

                                    for(it = pkToBeans.values().iterator(); it.hasNext(); bean.__WL_perhapsReloadOptimisticColumn()) {
                                       bean = (CMPBean)it.next();
                                       bean.__WL_resetIsModifiedVars(1, conn, false);
                                       if (executeUpdated) {
                                          bean.__WL_perhapsTakeSnapshot();
                                       }
                                    }

                                    if (internalFlush) {
                                       it = keys.iterator();

                                       while(it.hasNext()) {
                                          flushedModifiedKeys.add(it.next());
                                       }
                                    }

                                    keys.clear();
                                    var55 = false;
                                    break;
                                 }
                              } else {
                                 Iterator it = pkToBeans.entrySet().iterator();

                                 while(true) {
                                    while(it.hasNext()) {
                                       Map.Entry entry = (Map.Entry)it.next();
                                       bean = (CMPBean)entry.getValue();
                                       isModifiedBean = null;
                                       isModifiedBean = bean.__WL_getIsModifiedUnion((boolean[])isModifiedBean);
                                       boolean isModified = false;
                                       if (isModifiedBean != null) {
                                          boolean[] var18 = isModifiedBean;
                                          i = isModifiedBean.length;

                                          for(i = 0; i < i; ++i) {
                                             boolean b = var18[i];
                                             isModified |= b;
                                          }

                                          if (!isModified) {
                                             if (debugLogger.isDebugEnabled()) {
                                                debug("executeUpdateStmt(): nothing changed, avoided stores for update");
                                             }
                                             continue;
                                          }
                                       }

                                       stmtArray = bean.__WL_getStmtArray(conn, isModifiedBean, 1, false);
                                       executeUpdated = false;
                                       this.addBeanToUpdateStmt(stmtArray, isModifiedBean, bean, false);
                                       if (debugLogger.isDebugEnabled()) {
                                          debug(this.beanInfo.getEJBName() + ": about to execute multi update.");
                                       }

                                       b = false;
                                       var86 = stmtArray;
                                       rowsUpdated = stmtArray.length;

                                       for(var22 = 0; var22 < rowsUpdated; ++var22) {
                                          ps = var86[var22];
                                          if (ps != null) {
                                             i = ps.executeUpdate();
                                             executeUpdated = true;
                                             if (i == 0) {
                                                pk = entry.getKey();
                                                if (this.isOptimistic) {
                                                   l = EJBLogger.logoptimisticUpdateFailedLoggable(this.beanInfo.getEJBName(), pk.toString());
                                                   throw new OptimisticConcurrencyException(l.getMessageText());
                                                }

                                                l = EJBLogger.logbeanDoesNotExistLoggable(this.beanInfo.getEJBName(), pk.toString());
                                                throw new NoSuchEntityException(l.getMessageText());
                                             }
                                          }
                                       }

                                       bean.__WL_resetIsModifiedVars(1, conn, false);
                                       if (executeUpdated) {
                                          bean.__WL_perhapsTakeSnapshot();
                                       }

                                       bean.__WL_perhapsReloadOptimisticColumn();
                                       Object pk = entry.getKey();
                                       keys.remove(pk);
                                       if (internalFlush) {
                                          flushedModifiedKeys.add(pk);
                                       }
                                    }

                                    var55 = false;
                                    break;
                                 }
                              }
                           } catch (Throwable var64) {
                              if (!(var64 instanceof OptimisticConcurrencyException)) {
                                 EJBLogger.logExcepFromStore(var64);
                              }

                              if (!internalFlush) {
                                 this.cacheRemoveCMPBeansOnError(tx, pkToBeans.values());
                              }

                              EJBRuntimeUtils.throwInternalException("Exception from ejbStore:", var64);
                              var55 = false;
                              break label1332;
                           } finally {
                              if (var55) {
                                 if (useSendBatch && stmtArray != null) {
                                    PreparedStatement[] var27 = stmtArray;
                                    int var28 = stmtArray.length;

                                    for(int var29 = 0; var29 < var28; ++var29) {
                                       PreparedStatement ps = var27[var29];
                                       if (ps != null) {
                                          try {
                                             this.invokeOracleSetExecuteBatch((PreparedStatement)ps, 1);
                                          } catch (SQLException var56) {
                                          } catch (Throwable var57) {
                                          }
                                       }
                                    }
                                 }

                                 ((RDBMSPersistenceManager)this.persistence).releaseArrayResources(conn, stmtArray, (ResultSet[])null);
                              }
                           }

                           if (useSendBatch && stmtArray != null) {
                              var71 = stmtArray;
                              var74 = stmtArray.length;

                              for(var77 = 0; var77 < var74; ++var77) {
                                 ps = var71[var77];
                                 if (ps != null) {
                                    try {
                                       this.invokeOracleSetExecuteBatch((PreparedStatement)ps, 1);
                                    } catch (SQLException var62) {
                                    } catch (Throwable var63) {
                                    }
                                 }
                              }
                           }

                           ((RDBMSPersistenceManager)this.persistence).releaseArrayResources(conn, stmtArray, (ResultSet[])null);
                           break label1330;
                        }

                        if (useSendBatch && stmtArray != null) {
                           isModifiedBean = stmtArray;
                           var78 = stmtArray.length;

                           for(var82 = 0; var82 < var78; ++var82) {
                              PreparedStatement ps = isModifiedBean[var82];
                              if (ps != null) {
                                 try {
                                    this.invokeOracleSetExecuteBatch((PreparedStatement)ps, 1);
                                 } catch (SQLException var58) {
                                 } catch (Throwable var59) {
                                 }
                              }
                           }
                        }

                        ((RDBMSPersistenceManager)this.persistence).releaseArrayResources(conn, stmtArray, (ResultSet[])null);
                        break label1329;
                     }

                     if (useSendBatch && stmtArray != null) {
                        var71 = stmtArray;
                        var74 = stmtArray.length;

                        for(var77 = 0; var77 < var74; ++var77) {
                           ps = var71[var77];
                           if (ps != null) {
                              try {
                                 this.invokeOracleSetExecuteBatch((PreparedStatement)ps, 1);
                              } catch (SQLException var60) {
                              } catch (Throwable var61) {
                              }
                           }
                        }
                     }

                     ((RDBMSPersistenceManager)this.persistence).releaseArrayResources(conn, stmtArray, (ResultSet[])null);
                     break label1330;
                  }
               } catch (Throwable var66) {
                  if (var32 != null) {
                     var32.th = var66;
                     InstrumentationSupport.postProcess(var32);
                  }

                  throw var66;
               }

               if (var32 != null) {
                  InstrumentationSupport.postProcess(var32);
               }

               return;
            }

            if (var32 != null) {
               InstrumentationSupport.postProcess(var32);
            }

            return;
         }

         if (var32 != null) {
            InstrumentationSupport.postProcess(var32);
         }

         return;
      }

      if (var32 != null) {
         InstrumentationSupport.postProcess(var32);
      }

   }

   public void addBeanToDeleteStmt(PreparedStatement[] stmtArray, List finishedKeys, boolean[] isModifiedUnion, CMPBean bean, boolean internalFlush, boolean addBatch) throws Exception {
      EntityEJBContextImpl ctx = (EntityEJBContextImpl)bean.__WL_getEntityContext();
      if (!finishedKeys.contains(ctx.__WL_getPrimaryKey())) {
         try {
            finishedKeys.add(ctx.__WL_getPrimaryKey());
            if (addBatch && ((RDBMSPersistenceManager)this.persistence).isSelfRelationship()) {
               if (debugLogger.isDebugEnabled()) {
                  debug(this.beanInfo.getEJBName() + ": calling __WL_addSelfRelatedBeansToDeleteStmt");
               }

               bean.__WL_addSelfRelatedBeansToDeleteStmt(stmtArray, finishedKeys, isModifiedUnion, internalFlush);
            }

            if (debugLogger.isDebugEnabled()) {
               debug(this.beanInfo.getEJBName() + ": adding bean to delete stmt where pk=" + bean.__WL_getPrimaryKey());
            }

            bean.__WL_setBeanParamsForStmtArray(stmtArray, isModifiedUnion, 2, false);
            if (addBatch) {
               PreparedStatement[] var8 = stmtArray;
               int var9 = stmtArray.length;

               for(int var10 = 0; var10 < var9; ++var10) {
                  PreparedStatement ps = var8[var10];
                  if (ps != null) {
                     ps.addBatch();
                  }
               }
            }
         } catch (SQLException var12) {
            EJBRuntimeUtils.throwInternalException("Error during addBatch():", var12);
         }

      }
   }

   public void executeDeleteStmt(List keys, Transaction tx, Set finishedBeanManagerSet, boolean isFlushModified, boolean internalFlush, Collection flushedDeleteKeys) throws InternalException {
      LocalHolder var30;
      if ((var30 = LocalHolder.getInstance(_WLDF$INST_JPFLD_2, _WLDF$INST_JPFLD_JPMONS_2)) != null) {
         if (var30.argsCapture) {
            var30.args = InstrumentationSupport.toSensitive(7);
         }

         InstrumentationSupport.createDynamicJoinPoint(var30);
         InstrumentationSupport.preProcess(var30);
         var30.resetPostBegin();
      }

      label1015: {
         label1016: {
            try {
               finishedBeanManagerSet.add(this);
               Iterator var7 = this.childBeanManagerSet.iterator();

               while(var7.hasNext()) {
                  BaseEntityManager childBeanManager = (BaseEntityManager)var7.next();
                  if (!finishedBeanManagerSet.contains(childBeanManager) && !this.notNullableParentBeanManagerSet.contains(childBeanManager)) {
                     childBeanManager.getEntityTxManager().executeDeleteOperations((Transaction)this.getInvokeTxOrThread(), finishedBeanManagerSet, isFlushModified, internalFlush);
                  }
               }

               if (!keys.isEmpty()) {
                  int j = false;
                  Connection conn = null;
                  PreparedStatement[] stmtArray = null;
                  List finishedKeys = new ArrayList();
                  CMPBean bean = null;
                  boolean useSendBatch = false;
                  if (keys.size() > 1) {
                     useSendBatch = this.rdbmsPersistence.perhapsUseSendBatchForOracle();
                     if (!useSendBatch) {
                        this.prepareVerificationForBatch(keys, tx);
                     }
                  }

                  List beans = this.pkListToBeanList(keys, tx, true);
                  if (beans.isEmpty()) {
                     break label1015;
                  }

                  boolean var49 = false;

                  PreparedStatement[] var63;
                  int numOfRows;
                  int var68;
                  PreparedStatement ps;
                  label1047: {
                     try {
                        List deletedList;
                        try {
                           var49 = true;
                           bean = (CMPBean)beans.get(0);
                           conn = ((RDBMSPersistenceManager)this.persistence).getConnection();
                           PreparedStatement[] var17;
                           int i;
                           int rowsUpdated;
                           PreparedStatement ps;
                           Object pk;
                           Loggable l;
                           if (this.enableBatchOperations && keys.size() > 1) {
                              boolean[] isModifiedUnion = null;

                              for(Iterator it = beans.iterator(); it.hasNext(); isModifiedUnion = bean.__WL_getIsModifiedUnion(isModifiedUnion)) {
                                 bean = (CMPBean)it.next();
                              }

                              stmtArray = bean.__WL_getStmtArray(conn, isModifiedUnion, 2, false);
                              int[] expectedUpdatedRowCount = new int[stmtArray.length];
                              int[] numOfRowsUpdated = new int[stmtArray.length];
                              if (useSendBatch) {
                                 var17 = stmtArray;
                                 i = stmtArray.length;

                                 for(rowsUpdated = 0; rowsUpdated < i; ++rowsUpdated) {
                                    ps = var17[rowsUpdated];
                                    if (ps != null) {
                                       this.invokeOracleSetExecuteBatch(ps, 20);
                                    }
                                 }
                              }

                              Iterator it = beans.iterator();

                              while(true) {
                                 while(it.hasNext()) {
                                    bean = (CMPBean)it.next();
                                    if (useSendBatch) {
                                       bean.__WL_setBeanParamsForStmtArray(stmtArray, isModifiedUnion, 2, false);

                                       for(i = 0; i < stmtArray.length; ++i) {
                                          if (stmtArray[i] != null) {
                                             rowsUpdated = stmtArray[i].executeUpdate();
                                             numOfRowsUpdated[i] += rowsUpdated;
                                             int var10002 = expectedUpdatedRowCount[i]++;
                                          }
                                       }
                                    } else {
                                       this.addBeanToDeleteStmt(stmtArray, finishedKeys, isModifiedUnion, bean, internalFlush, true);
                                    }
                                 }

                                 if (debugLogger.isDebugEnabled()) {
                                    debug(this.beanInfo.getEJBName() + ": about to execute batch delete.");
                                 }

                                 if (useSendBatch) {
                                    for(int i = 0; i < stmtArray.length; ++i) {
                                       if (stmtArray[i] != null) {
                                          numOfRowsUpdated[i] += this.invokeOracleSendBatch(stmtArray[i]);
                                          if (numOfRowsUpdated[i] != expectedUpdatedRowCount[i]) {
                                             if (this.isOptimistic) {
                                                throw new OptimisticConcurrencyException("Instance/s of bean '" + this.beanInfo.getEJBName() + "' in the batch was changed by another transaction. The primary key is unknown due to Oracle driver limitation.");
                                             }

                                             throw new NoSuchEntityException("Instance/s of bean '" + this.beanInfo.getEJBName() + "' in the batch does not exist. The primary key is unknown due to Oracle driver limitation.");
                                          }
                                       }
                                    }
                                 } else {
                                    int[] numOfRowsArray = new int[stmtArray.length];
                                    PreparedStatement[] var74 = stmtArray;
                                    rowsUpdated = stmtArray.length;

                                    for(int var76 = 0; var76 < rowsUpdated; ++var76) {
                                       PreparedStatement ps = var74[var76];
                                       if (ps != null) {
                                          numOfRowsArray = ps.executeBatch();

                                          for(int j = 0; j < numOfRowsArray.length; ++j) {
                                             if (numOfRowsArray[j] == 0 || numOfRowsArray[j] == -3) {
                                                pk = keys.get(j);
                                                if (this.isOptimistic) {
                                                   l = EJBLogger.logoptimisticUpdateFailedLoggable(this.beanInfo.getEJBName(), pk.toString());
                                                   throw new OptimisticConcurrencyException(l.getMessageText());
                                                }

                                                l = EJBLogger.logbeanDoesNotExistLoggable(this.beanInfo.getEJBName(), pk.toString());
                                                throw new NoSuchEntityException(l.getMessageText());
                                             }
                                          }
                                       }
                                    }
                                 }

                                 if (internalFlush) {
                                    it = keys.iterator();

                                    while(it.hasNext()) {
                                       flushedDeleteKeys.add(it.next());
                                    }
                                 }

                                 keys.clear();
                                 this.cacheRemoveCMPBeans(tx, beans);
                                 var49 = false;
                                 break label1047;
                              }
                           }

                           for(Iterator it = beans.iterator(); it.hasNext(); this.cacheRemoveCMPBean(tx, bean)) {
                              bean = (CMPBean)it.next();
                              deletedList = null;
                              boolean[] isModifiedBean = bean.__WL_getIsModifiedUnion((boolean[])deletedList);
                              stmtArray = bean.__WL_getStmtArray(conn, isModifiedBean, 2, false);
                              this.addBeanToDeleteStmt(stmtArray, finishedKeys, isModifiedBean, bean, internalFlush, false);
                              if (debugLogger.isDebugEnabled()) {
                                 debug(this.beanInfo.getEJBName() + ": about to execute multi delete.");
                              }

                              int numOfRows = false;
                              var17 = stmtArray;
                              i = stmtArray.length;

                              for(rowsUpdated = 0; rowsUpdated < i; ++rowsUpdated) {
                                 ps = var17[rowsUpdated];
                                 if (ps != null) {
                                    numOfRows = ps.executeUpdate();
                                    if (numOfRows == 0) {
                                       EntityEJBContextImpl ctx = (EntityEJBContextImpl)bean.__WL_getEntityContext();
                                       pk = ctx.__WL_getPrimaryKey();
                                       if (this.isOptimistic) {
                                          l = EJBLogger.logoptimisticUpdateFailedLoggable(this.beanInfo.getEJBName(), pk.toString());
                                          throw new OptimisticConcurrencyException(l.getMessageText());
                                       }

                                       l = EJBLogger.logbeanDoesNotExistLoggable(this.beanInfo.getEJBName(), pk.toString());
                                       throw new NoSuchEntityException(l.getMessageText());
                                    }
                                 }
                              }

                              Object pk = ((EntityEJBContextImpl)bean.__WL_getEntityContext()).__WL_getPrimaryKey();
                              keys.remove(pk);
                              if (internalFlush) {
                                 flushedDeleteKeys.add(pk);
                              }
                           }

                           var49 = false;
                           break label1047;
                        } catch (Throwable var56) {
                           EJBLogger.logExcepInMethod1("executeDeleteStmt", var56);
                           if (!internalFlush) {
                              this.cacheRemoveCMPBeansOnError(tx, beans);
                           } else if (flushedDeleteKeys.size() > 0) {
                              deletedList = this.pkListToBeanList(flushedDeleteKeys, tx, true);
                              this.cacheRemoveCMPBeansOnError(tx, deletedList);
                           }
                        }

                        EJBRuntimeUtils.throwInternalException("EJB Exception:", var56);
                        var49 = false;
                     } finally {
                        if (var49) {
                           if (useSendBatch && stmtArray != null) {
                              PreparedStatement[] var25 = stmtArray;
                              int var26 = stmtArray.length;

                              for(int var27 = 0; var27 < var26; ++var27) {
                                 PreparedStatement ps = var25[var27];
                                 if (ps != null) {
                                    try {
                                       this.invokeOracleSetExecuteBatch((PreparedStatement)ps, 1);
                                    } catch (SQLException var50) {
                                    } catch (Throwable var51) {
                                    }
                                 }
                              }
                           }

                           ((RDBMSPersistenceManager)this.persistence).releaseArrayResources(conn, stmtArray, (ResultSet[])null);
                        }
                     }

                     if (useSendBatch && stmtArray != null) {
                        var63 = stmtArray;
                        var68 = stmtArray.length;

                        for(numOfRows = 0; numOfRows < var68; ++numOfRows) {
                           ps = var63[numOfRows];
                           if (ps != null) {
                              try {
                                 this.invokeOracleSetExecuteBatch(ps, 1);
                              } catch (SQLException var52) {
                              } catch (Throwable var53) {
                              }
                           }
                        }
                     }

                     ((RDBMSPersistenceManager)this.persistence).releaseArrayResources(conn, stmtArray, (ResultSet[])null);
                     break label1016;
                  }

                  if (useSendBatch && stmtArray != null) {
                     var63 = stmtArray;
                     var68 = stmtArray.length;

                     for(numOfRows = 0; numOfRows < var68; ++numOfRows) {
                        ps = var63[numOfRows];
                        if (ps != null) {
                           try {
                              this.invokeOracleSetExecuteBatch(ps, 1);
                           } catch (SQLException var54) {
                           } catch (Throwable var55) {
                           }
                        }
                     }
                  }

                  ((RDBMSPersistenceManager)this.persistence).releaseArrayResources(conn, stmtArray, (ResultSet[])null);
                  break label1016;
               }
            } catch (Throwable var58) {
               if (var30 != null) {
                  var30.th = var58;
                  InstrumentationSupport.postProcess(var30);
               }

               throw var58;
            }

            if (var30 != null) {
               InstrumentationSupport.postProcess(var30);
            }

            return;
         }

         if (var30 != null) {
            InstrumentationSupport.postProcess(var30);
         }

         return;
      }

      if (var30 != null) {
         InstrumentationSupport.postProcess(var30);
      }

   }

   private void invokeOracleSetExecuteBatch(PreparedStatement pstmt, int batchValue) throws Throwable {
      if ("oracle.jdbc.OraclePreparedStatement".equals(pstmt.getClass().getName())) {
         try {
            Method meth = pstmt.getClass().getMethod("setExecuteBatch", Integer.TYPE);
            if (meth != null) {
               meth.invoke(pstmt, batchValue);
            }
         } catch (InvocationTargetException var5) {
            Throwable targetException = var5.getTargetException();
            if (targetException instanceof SQLException) {
               throw (SQLException)targetException;
            }

            throw targetException;
         }
      }

   }

   private int invokeOracleSendBatch(PreparedStatement pstmt) throws Throwable {
      if ("oracle.jdbc.OraclePreparedStatement".equals(pstmt.getClass().getName())) {
         try {
            Method meth = pstmt.getClass().getMethod("sendBatch");
            if (meth != null) {
               Object retValue = meth.invoke(pstmt);
               return (Integer)retValue;
            }
         } catch (InvocationTargetException var4) {
            Throwable targetException = var4.getTargetException();
            if (targetException instanceof SQLException) {
               throw (SQLException)targetException;
            }

            throw targetException;
         }
      }

      return 0;
   }

   public void executeM2NJoinTableInserts(Map cmrf2pkList, Transaction tx, boolean internalFlush) throws InternalException {
      if (cmrf2pkList == null) {
         if (debugLogger.isDebugEnabled()) {
            debug(this.beanInfo.getEJBName() + " no deferred M2N INSERTs.");
         }

      } else {
         Iterator it = cmrf2pkList.entrySet().iterator();

         while(true) {
            String cmrf;
            List keys;
            do {
               Map.Entry entry;
               do {
                  if (!it.hasNext()) {
                     return;
                  }

                  entry = (Map.Entry)it.next();
                  cmrf = (String)entry.getKey();
               } while(!this.many2ManyCmrFieldInsertSet.contains(cmrf));

               keys = (List)entry.getValue();
               if (debugLogger.isDebugEnabled()) {
                  debug(this.beanInfo.getEJBName() + " " + cmrf + " process deferred M2N INSERTs for " + keys.size() + " source Beans.");
               }
            } while(keys.isEmpty());

            Connection conn = null;
            PreparedStatement insertStmt = null;
            List beanList = this.pkListToBeanList(keys, tx, false);
            if (beanList.isEmpty()) {
               return;
            }

            try {
               CMPBean bean = (CMPBean)beanList.get(0);
               conn = ((RDBMSPersistenceManager)this.persistence).getConnection();
               String sqlInsert = bean.__WL_getM2NSQL(cmrf, 0);
               insertStmt = conn.prepareStatement(sqlInsert);
               if (this.enableBatchOperations && keys.size() > 1) {
                  Iterator beans = beanList.iterator();

                  Collection cmrBeans;
                  Set addSet;
                  while(beans.hasNext()) {
                     bean = (CMPBean)beans.next();
                     if (debugLogger.isDebugEnabled()) {
                        debug("set M2N Join Table INSERT for " + this.beanInfo.getEJBName() + ",\n pk '" + bean.__WL_getPrimaryKey() + "'");
                     }

                     cmrBeans = bean.__WL_getCmrBeansForCmrField(cmrf);
                     addSet = ((RDBMSM2NSet)cmrBeans).getAddSet();
                     if (addSet.size() > 0) {
                        Object thisBeanPK = ((RDBMSM2NSet)cmrBeans).getCreatorPk();
                        Iterator addSetIt = addSet.iterator();

                        while(addSetIt.hasNext()) {
                           Object otherBeanPK = addSetIt.next();
                           if (debugLogger.isDebugEnabled()) {
                              debug("setting Join Table INSERT params for thisPk: '" + thisBeanPK + "', otherBeanPK: '" + otherBeanPK + "'");
                           }

                           ((RDBMSM2NSet)cmrBeans).setAddJoinTableSQLParams(insertStmt, thisBeanPK, otherBeanPK);
                           insertStmt.addBatch();
                        }
                     }
                  }

                  if (debugLogger.isDebugEnabled()) {
                     debug(this.beanInfo.getEJBName() + ": about to execute batch M2NJoinTableInserts.");
                  }

                  insertStmt.executeBatch();
                  beans = beanList.iterator();

                  while(beans.hasNext()) {
                     bean = (CMPBean)beans.next();
                     cmrBeans = bean.__WL_getCmrBeansForCmrField(cmrf);
                     addSet = ((RDBMSM2NSet)cmrBeans).getAddSet();
                     addSet.clear();
                  }
               } else {
                  List addedBeans = new ArrayList();

                  Set addSet;
                  for(Iterator beans = beanList.iterator(); beans.hasNext(); addSet.clear()) {
                     bean = (CMPBean)beans.next();
                     if (debugLogger.isDebugEnabled()) {
                        debug("set M2N Join Table INSERT for " + this.beanInfo.getEJBName() + ", pk '" + bean.__WL_getPrimaryKey() + "'");
                     }

                     Collection cmrBeans = bean.__WL_getCmrBeansForCmrField(cmrf);
                     addSet = ((RDBMSM2NSet)cmrBeans).getAddSet();
                     if (addSet.size() > 0) {
                        addedBeans.clear();
                        Object thisBeanPK = ((RDBMSM2NSet)cmrBeans).getCreatorPk();

                        Object otherBeanPK;
                        for(Iterator addSetIt = addSet.iterator(); addSetIt.hasNext(); addedBeans.add(otherBeanPK)) {
                           otherBeanPK = addSetIt.next();
                           if (debugLogger.isDebugEnabled()) {
                              debug("setting Join Table INSERT params for thisPk: '" + thisBeanPK + "', otherBeanPK: '" + otherBeanPK + "'");
                           }

                           ((RDBMSM2NSet)cmrBeans).setAddJoinTableSQLParams(insertStmt, thisBeanPK, otherBeanPK);

                           try {
                              if (debugLogger.isDebugEnabled()) {
                                 debug(this.beanInfo.getEJBName() + ": about to execute single M2NJoinTableInsert.");
                              }

                              insertStmt.executeUpdate();
                           } catch (SQLException var26) {
                              Iterator it2 = addedBeans.iterator();

                              while(it2.hasNext()) {
                                 otherBeanPK = it2.next();
                                 addSet.remove(otherBeanPK);
                              }

                              throw var26;
                           }
                        }
                     }
                  }
               }
            } catch (SQLException var27) {
               EJBLogger.logExcepInMethod1("executeM2NJoinTableInserts", var27);
               if (!internalFlush) {
                  this.cacheRemoveCMPBeansOnError(tx, beanList);
               }

               EJBRuntimeUtils.throwInternalException("EJB Exception:", var27);
            } finally {
               ((RDBMSPersistenceManager)this.persistence).releaseResources(conn, insertStmt, (ResultSet)null);
            }
         }
      }
   }

   public void beforeCompletion(InvocationWrapper wrap) throws InternalException {
      if (wrap.getPrimaryKey() != null) {
         try {
            Collection pk = new ArrayList();
            pk.add(wrap.getPrimaryKey());
            this.beforeCompletion(pk, Thread.currentThread());
         } catch (InternalException var3) {
            throw (RuntimeException)var3.detail;
         }
      }

   }

   public void beforeCompletion(Object ret) throws InternalException {
      Collection pks = this.finderRetToPks(ret);

      try {
         this.beforeCompletion(pks, Thread.currentThread());
      } catch (InternalException var4) {
         throw (RuntimeException)var4.detail;
      }
   }

   abstract void beforeCompletion(Collection var1, Object var2) throws InternalException;

   public void afterCompletion(InvocationWrapper wrap) {
      if (wrap.getPrimaryKey() != null) {
         Collection pk = new ArrayList();
         pk.add(wrap.getPrimaryKey());
         this.afterCompletion(pk, Thread.currentThread(), 6, (Object)null);
      }

   }

   public void afterCompletion(Object ret) {
      Collection pks = this.finderRetToPks(ret);
      this.afterCompletion(pks, Thread.currentThread(), 6, (Object)null);
   }

   private boolean verifyQueriesForBatching(Collection beanList) {
      boolean batchingDisabled = false;
      int count = 0;
      if (debugLogger.isDebugEnabled()) {
         debug("perform a check to verify if the queries are fit for batching");
      }

      Collection[] nullSnapshotVariableArray = new HashSet[beanList.size()];
      Iterator beans = beanList.iterator();

      while(beans.hasNext()) {
         CMPBean bean = (CMPBean)beans.next();
         if (!this.isBeanManagedPersistence && this.uses20CMP) {
            nullSnapshotVariableArray[count++] = bean.__WL_getNullSnapshotVariables();
         }
      }

      HashSet coll;
      int i;
      for(i = 0; i < nullSnapshotVariableArray.length; ++i) {
         coll = nullSnapshotVariableArray[i];
         int size = coll.size();

         for(int j = i + 1; j < nullSnapshotVariableArray.length; ++j) {
            Collection otherColl = nullSnapshotVariableArray[j];
            if (size != otherColl.size()) {
               if (debugLogger.isDebugEnabled()) {
                  debug("the collection of snapshot variables that are null are of different sizes for bean " + this.beanInfo.getEJBName() + ", hence the queries are not suitable for batching");
               }

               batchingDisabled = true;
               break;
            }
         }
      }

      if (!batchingDisabled) {
         label63:
         for(i = 0; i < nullSnapshotVariableArray.length; ++i) {
            coll = nullSnapshotVariableArray[i];
            Iterator it = coll.iterator();

            while(true) {
               while(true) {
                  if (!it.hasNext()) {
                     continue label63;
                  }

                  String snapshotVariableName = (String)it.next();

                  for(int j = i + 1; j < nullSnapshotVariableArray.length; ++j) {
                     Collection otherColl = nullSnapshotVariableArray[j];
                     if (!otherColl.contains(snapshotVariableName)) {
                        if (debugLogger.isDebugEnabled()) {
                           debug("the snapshot variables that are null for this batch of queries are different for bean " + this.beanInfo.getEJBName() + ", hence the queries are not suitable for batching");
                        }

                        batchingDisabled = true;
                        break;
                     }
                  }
               }
            }
         }
      }

      return batchingDisabled;
   }

   abstract void afterCompletion(Collection var1, Object var2, int var3, Object var4);

   private Collection finderRetToPks(Object ret) {
      Collection pks = new ArrayList();

      try {
         if (ret instanceof EJBObject) {
            pks.add(((EJBObject)ret).getPrimaryKey());
         } else if (ret instanceof EJBLocalObject) {
            pks.add(((EJBLocalObject)ret).getPrimaryKey());
         } else if (ret instanceof CMPBean) {
            pks.add(((CMPBean)ret).__WL_getPrimaryKey());
         } else {
            Object obj;
            if (ret instanceof Enumeration) {
               Enumeration objs = (Enumeration)ret;

               while(objs.hasMoreElements()) {
                  obj = objs.nextElement();
                  if (obj instanceof EJBObject) {
                     pks.add(((EJBObject)obj).getPrimaryKey());
                  } else if (obj instanceof EJBLocalObject) {
                     pks.add(((EJBLocalObject)obj).getPrimaryKey());
                  }
               }
            } else if (ret instanceof Collection) {
               Iterator objs = ((Collection)ret).iterator();

               while(objs.hasNext()) {
                  obj = objs.next();
                  if (obj instanceof EJBObject) {
                     pks.add(((EJBObject)obj).getPrimaryKey());
                  } else if (obj instanceof EJBLocalObject) {
                     pks.add(((EJBLocalObject)obj).getPrimaryKey());
                  }
               }
            }
         }

         return pks;
      } catch (Exception var5) {
         throw (RuntimeException)var5;
      }
   }

   public abstract void flushModified(Collection var1, Transaction var2, boolean var3, Collection var4) throws InternalException;

   public PoolIntf getPool() {
      return this.entityPool;
   }

   public EntityBean getBeanFromRS(Object pk, RSInfo rsInfo) throws InternalException {
      return this.getBeanFromRS(this.getInvokeTxOrThread(), pk, rsInfo);
   }

   public abstract EntityBean getBeanFromRS(Object var1, Object var2, RSInfo var3) throws InternalException;

   public abstract void postFinderCleanup(Object var1, Collection var2, boolean var3, boolean var4);

   public EntityBean getBeanFromPool() throws InternalException {
      return (EntityBean)this.entityPool.getBean();
   }

   public EntityBean getReflectionTarget(boolean isLocal) throws InternalException {
      if (this.isBeanManagedPersistence) {
         return this.getBeanFromPool();
      } else {
         EntityBean bean;
         if (isLocal) {
            if (this.reflectionTargetLocal == null) {
               synchronized(this) {
                  if (this.reflectionTargetLocal == null) {
                     bean = ((EntityPool)this.entityPool).createBean();
                     ((WLEntityBean)bean).__WL_setIsLocal(true);
                     this.reflectionTargetLocal = bean;
                  }
               }
            }

            return this.reflectionTargetLocal;
         } else {
            if (this.reflectionTargetRemote == null) {
               synchronized(this) {
                  if (this.reflectionTargetRemote == null) {
                     bean = ((EntityPool)this.entityPool).createBean();
                     ((WLEntityBean)bean).__WL_setIsLocal(false);
                     this.reflectionTargetRemote = bean;
                  }
               }
            }

            return this.reflectionTargetRemote;
         }
      }
   }

   public void destroyReflectionTarget(EntityBean bean) {
      if (this.isBeanManagedPersistence) {
         this.destroyPooledBean(bean);
      }

   }

   public void releaseReflectionTarget(EntityBean bean) {
      if (this.isBeanManagedPersistence) {
         this.releaseBeanToPool(bean);
      }

   }

   protected void destroyPooledBean(EntityBean bean) {
      this.entityPool.destroyBean(bean);
   }

   public void destroyPooledInstance(InvocationWrapper wrap, Throwable ee) {
      this.destroyPooledBean((EntityBean)wrap.getBean());
   }

   public void releaseBeanToPool(EntityBean bean) {
      EntityEJBContextImpl ctx = (EntityEJBContextImpl)((WLEntityBean)bean).__WL_getEJBContext();
      ctx.__WL_setPrimaryKey((Object)null);
      this.entityPool.releaseBean(bean);
   }

   protected abstract void cacheRemoveBean(Transaction var1, Object var2);

   private void cacheRemoveCMPBeans(Transaction tx, List beanList) {
      Iterator beans = beanList.iterator();

      while(beans.hasNext()) {
         CMPBean bean = (CMPBean)beans.next();
         this.cacheRemoveCMPBean(tx, bean);
      }

   }

   private void cacheRemoveCMPBean(Transaction tx, CMPBean bean) {
      EntityEJBContextImpl ctx = (EntityEJBContextImpl)bean.__WL_getEntityContext();
      Object pk = ctx.__WL_getPrimaryKey();
      if (bean.__WL_getIsRemoved()) {
         bean.__WL_initialize();
         bean.__WL_setIsRemoved(false);
      }

      this.cacheRemoveBean(tx, pk);
   }

   protected abstract void cacheRemoveBeanOnError(Transaction var1, Object var2);

   private void cacheRemoveCMPBeansOnError(Transaction tx, Collection beanList) {
      Iterator beans = beanList.iterator();

      while(beans.hasNext()) {
         CMPBean bean = (CMPBean)beans.next();
         EntityEJBContextImpl ctx = (EntityEJBContextImpl)bean.__WL_getEntityContext();
         this.cacheRemoveBeanOnError(tx, ctx.__WL_getPrimaryKey());
      }

   }

   protected abstract EntityBean alreadyCached(Object var1, Object var2) throws InternalException;

   protected abstract boolean finderCacheInsert(Object var1, Object var2, EJBObject var3, EJBLocalObject var4, EntityBean var5) throws InternalException;

   protected boolean finderCacheInsert(EntityBean bean) throws InternalException {
      if (debugLogger.isDebugEnabled()) {
         debug("called BaseEntityManager.finderCacheInsert...");
      }

      Object primaryKey = ((CMPBean)bean).__WL_getPrimaryKey();
      EntityEJBContextImpl ctx = (EntityEJBContextImpl)((WLEntityBean)bean).__WL_getEJBContext();
      ctx.__WL_setPrimaryKey(primaryKey);
      if (!$assertionsDisabled && primaryKey == null) {
         throw new AssertionError();
      } else {
         EJBObject eo = null;
         EJBLocalObject elo = null;
         if (this.remoteHome != null) {
            eo = this.remoteHome.allocateEO(primaryKey);
         }

         if (this.localHome != null) {
            elo = this.localHome.allocateELO(primaryKey);
         }

         return this.finderCacheInsert(this.getInvokeTxOrThread(), primaryKey, eo, elo, bean);
      }
   }

   public Object finderCacheInsert(EntityBean bean, boolean isLocal) throws InternalException {
      if (debugLogger.isDebugEnabled()) {
         debug("called BaseEntityManager.finderCacheInsert...");
      }

      Object primaryKey = ((CMPBean)bean).__WL_getPrimaryKey();
      if (!$assertionsDisabled && primaryKey == null) {
         throw new AssertionError();
      } else {
         EntityEJBContextImpl ctx = (EntityEJBContextImpl)((WLEntityBean)bean).__WL_getEJBContext();
         ctx.__WL_setPrimaryKey(primaryKey);
         EJBObject eo = null;
         EJBLocalObject elo = null;
         if (this.remoteHome != null) {
            eo = this.remoteHome.allocateEO(primaryKey);
         }

         if (this.localHome != null) {
            elo = this.localHome.allocateELO(primaryKey);
         }

         this.finderCacheInsert(this.getInvokeTxOrThread(), primaryKey, eo, elo, bean);
         return isLocal ? elo : eo;
      }
   }

   public Object finderGetEoFromBeanOrPk(EntityBean bean, Object pk, boolean isLocal) {
      if (bean != null) {
         return isLocal ? ((EntityEJBContextImpl)((CMPBean)bean).__WL_getEntityContext()).__WL_getEJBLocalObject() : ((EntityEJBContextImpl)((CMPBean)bean).__WL_getEntityContext()).__WL_getEJBObject();
      } else {
         EJBObject eo = null;
         EJBLocalObject elo = null;
         if (this.remoteHome != null) {
            eo = this.remoteHome.allocateEO(pk);
         }

         if (this.localHome != null) {
            elo = this.localHome.allocateELO(pk);
         }

         return isLocal ? elo : eo;
      }
   }

   protected void checkForReentrant(EntityBean b, Object pk) throws InternalException {
      WLEntityBean bean = (WLEntityBean)b;
      Loggable l;
      if (this.uses20CMP && ((CMPBean)bean).__WL_getIsRemoved()) {
         l = EJBLogger.lognoSuchEntityExceptionLoggable(pk.toString());
         throw new NoSuchEntityException(l.getMessageText());
      } else if (!this.isReentrant && bean.__WL_isBusy()) {
         l = EJBLogger.logillegalReentrantCallLoggable(this.getDisplayName(), pk.toString());
         throw new InternalException(l.getMessageText());
      }
   }

   public EJBObject remoteFindByPrimaryKey(Method fpkMethod, Object pk) throws InternalException {
      return (EJBObject)this.findByPrimaryKey(this.getInvokeTxOrThread(), fpkMethod, pk, false);
   }

   public EJBLocalObject localFindByPrimaryKey(Method fpkMethod, Object pk) throws InternalException {
      return (EJBLocalObject)this.findByPrimaryKey(this.getInvokeTxOrThread(), fpkMethod, pk, true);
   }

   public EJBObject remoteFindByPrimaryKey(InvocationWrapper wrap, Object pk) throws InternalException {
      Method fpkMethod = this.getFindByPrimaryKeyMethod();
      return (EJBObject)this.findByPrimaryKey(wrap.getInvokeTxOrThread(), fpkMethod, pk, false);
   }

   public Object localFindByPrimaryKey(InvocationWrapper wrap, Object pk) throws InternalException {
      Method fpkMethod = this.getFindByPrimaryKeyMethod();
      return this.findByPrimaryKey(wrap.getInvokeTxOrThread(), fpkMethod, pk, true);
   }

   private Object findByPrimaryKey(Object invokeTxOrThread, Method fpkMethod, Object pk, boolean isLocal) throws InternalException {
      if (pk == null) {
         throw new InternalException("Null primary key during findByPrimaryKey", new ObjectNotFoundException("Primary key was null!"));
      } else {
         EJBObject eo = null;
         EJBLocalObject elo = null;
         if (this.remoteHome != null) {
            eo = this.remoteHome.allocateEO(pk);
         }

         if (this.localHome != null) {
            elo = this.localHome.allocateELO(pk);
         }

         this.cacheRTMBean.incrementCacheAccessCount();
         EntityBean cachedBean = this.alreadyCached(invokeTxOrThread, pk);
         if (cachedBean != null) {
            this.cacheRTMBean.incrementCacheHitCount();
            return isLocal ? elo : eo;
         } else {
            Object primaryKey = null;
            EntityBean reflectionTarget = this.getReflectionTarget(isLocal);
            if (!$assertionsDisabled && reflectionTarget == null) {
               throw new AssertionError();
            } else {
               try {
                  if (this.findersLoadBean) {
                     Object o = this.persistence.findByPrimaryKey(reflectionTarget, fpkMethod, pk);
                     this.postFinderCleanup(pk, (Collection)null, true, isLocal);
                     return o;
                  }

                  primaryKey = this.persistence.findByPrimaryKey(reflectionTarget, fpkMethod, pk);
                  if (primaryKey == null) {
                     Loggable l = EJBLogger.logfindByPkReturnedNullLoggable(this.getDisplayName(), (String)null);
                     throw new RemoteException(l.getMessageText());
                  }
               } catch (Throwable var11) {
                  this.destroyReflectionTarget(reflectionTarget);
                  this.handleMethodException(fpkMethod, (Class[])null, var11);
               }

               if (!$assertionsDisabled && !pk.equals(primaryKey)) {
                  throw new AssertionError("ejbFindByPrimaryKey returned" + primaryKey + " but we passed in :" + pk);
               } else {
                  this.releaseReflectionTarget(reflectionTarget);
                  return isLocal ? elo : eo;
               }
            }
         }
      }
   }

   public EJBObject remoteScalarFinder(InvocationWrapper wrap, Method finder, Object[] args) throws InternalException {
      Object txOrThread = wrap.getInvokeTxOrThread();
      return (EJBObject)this.scalarFinder(txOrThread, finder, args, false);
   }

   public EJBLocalObject localScalarFinder(InvocationWrapper wrap, Method finder, Object[] args) throws InternalException {
      Object txOrThread = wrap.getInvokeTxOrThread();
      return (EJBLocalObject)this.scalarFinder(txOrThread, finder, args, true);
   }

   public EJBObject remoteScalarFinder(Method finder, Object[] fk) throws InternalException {
      if (debugLogger.isDebugEnabled()) {
         debug("called ExclusiveEntityManager.scalarFinder...");

         for(int i = 0; i < fk.length; ++i) {
            debug("\tparam: " + i + ", " + fk[i].getClass().getName() + ", " + fk[i]);
         }
      }

      return (EJBObject)this.scalarFinder(this.getInvokeTxOrThread(), finder, fk, false);
   }

   public EJBLocalObject localScalarFinder(Method finder, Object[] fk) throws InternalException {
      if (debugLogger.isDebugEnabled()) {
         debug("called ExclusiveEntityManager.scalarFinder...");

         for(int i = 0; i < fk.length; ++i) {
            debug("\tparam: " + i + ", " + fk[i].getClass().getName() + ", " + fk[i]);
         }
      }

      return (EJBLocalObject)this.scalarFinder(this.getInvokeTxOrThread(), finder, fk, true);
   }

   public Object scalarFinder(Object txOrThread, Method finder, Object[] args, boolean isLocal) throws InternalException {
      EntityBean reflectionTarget = this.getReflectionTarget(isLocal);
      Object primaryKey = null;

      try {
         if (this.findersLoadBean) {
            Object o = this.persistence.scalarFinder(reflectionTarget, finder, args);
            this.postFinderCleanup(o, (Collection)null, false, isLocal);
            return o;
         }

         primaryKey = this.persistence.scalarFinder(reflectionTarget, finder, args);
      } catch (Throwable var9) {
         this.destroyReflectionTarget(reflectionTarget);
         this.handleMethodException(finder, (Class[])null, var9);
      }

      this.releaseReflectionTarget(reflectionTarget);
      EJBObject eo = null;
      EJBLocalObject elo = null;
      if (primaryKey == null) {
         return null;
      } else {
         if (this.remoteHome != null) {
            eo = this.remoteHome.allocateEO(primaryKey);
         }

         if (this.localHome != null) {
            elo = this.localHome.allocateELO(primaryKey);
         }

         return isLocal ? elo : eo;
      }
   }

   public Enumeration enumFinder(InvocationWrapper wrap, Method finder, Object[] args) throws InternalException {
      EntityBean reflectionTarget = this.getReflectionTarget(wrap.isLocal());
      Collection pkCol = null;
      Enumeration pkEnum = null;
      Collection eosFromFinder = null;

      Loggable l;
      try {
         if (this.findersLoadBean) {
            eosFromFinder = this.persistence.collectionFinder(reflectionTarget, finder, args);
         } else if (this.isBeanManagedPersistence) {
            pkEnum = this.persistence.enumFinder(reflectionTarget, finder, args);
            if (pkEnum == null) {
               l = EJBLogger.logfinderReturnedNullLoggable(finder.getName(), this.getDisplayName());
               throw new RemoteException(l.getMessageText());
            }
         } else {
            pkCol = this.persistence.collectionFinder(reflectionTarget, finder, args);
         }
      } catch (Throwable var9) {
         this.destroyReflectionTarget(reflectionTarget);
         this.handleMethodException(finder, (Class[])null, var9);
      }

      this.releaseReflectionTarget(reflectionTarget);
      l = null;
      Object enumeration;
      if (this.findersLoadBean) {
         enumeration = new EJBObjectEnum(eosFromFinder);
         this.postFinderCleanup((Object)null, eosFromFinder, false, wrap.isLocal());
      } else if (this.isBeanManagedPersistence) {
         enumeration = this.pkEnumToEnum(pkEnum, wrap.isLocal());
      } else {
         enumeration = this.pkCollToEnum(pkCol, wrap.isLocal());
      }

      return (Enumeration)enumeration;
   }

   public Collection collectionFinder(Method finder, Object[] args, boolean isLocal) throws InternalException {
      return this.collectionFinder(finder, args, false, isLocal);
   }

   public Collection remoteCollectionFinder(Method finder, Object[] args) throws InternalException {
      return this.collectionFinder(finder, args, false, false);
   }

   public Collection localCollectionFinder(Method finder, Object[] args) throws InternalException {
      return this.collectionFinder(finder, args, false, true);
   }

   private Collection collectionFinder(Method finder, Object[] args, boolean returnSet, boolean isLocal) throws InternalException {
      EntityBean reflectionTarget = this.getReflectionTarget(isLocal);
      Collection col = null;

      try {
         col = this.persistence.collectionFinder(reflectionTarget, finder, args);
         if (col == null) {
            Loggable l = EJBLogger.logfinderReturnedNullLoggable(finder.getName(), this.getDisplayName());
            throw new RemoteException(l.getMessageText());
         }
      } catch (Throwable var8) {
         this.destroyReflectionTarget(reflectionTarget);
         this.handleMethodException(finder, (Class[])null, var8);
      }

      this.releaseReflectionTarget(reflectionTarget);
      Collection oCol = null;
      if (this.findersLoadBean) {
         if (returnSet) {
            oCol = new OrderedSet(col);
         } else {
            oCol = new ArrayList(col);
         }

         this.postFinderCleanup((Object)null, (Collection)oCol, false, isLocal);
      } else if (returnSet) {
         oCol = this.pkCollToSet(col, isLocal);
      } else {
         oCol = this.pkCollToColl(col, isLocal);
      }

      return (Collection)oCol;
   }

   public Object executePreparedQuery(String sql, InvocationWrapper wrap, Method finder, boolean isSelect, Map arguments, Map flattenedArguments, PreparedQuery pquery) throws InternalException {
      Object ret = null;

      try {
         ret = this.rdbmsPersistence.executePreparedQuery(sql, wrap.isLocal(), isSelect, arguments, flattenedArguments, pquery);
      } catch (Throwable var10) {
         this.handleMethodException(finder, (Class[])null, var10);
      }

      return ret;
   }

   public Object dynamicSqlQuery(String sql, Object[] arguments, WLQueryProperties props, Method finder, boolean isLocal, Class resultType) throws InternalException {
      Object result = null;

      try {
         result = this.rdbmsPersistence.dynamicSqlQuery(sql, arguments, props, isLocal, resultType);
      } catch (Throwable var9) {
         this.handleMethodException(finder, (Class[])null, var9);
      }

      return result;
   }

   public Object dynamicQuery(String ejbql, Object[] arguments, WLQueryProperties props, Method finder, boolean isLocal, boolean isSelect) throws InternalException {
      Object ret = null;

      try {
         ret = this.rdbmsPersistence.dynamicQuery(ejbql, props, isLocal, isSelect);
      } catch (Throwable var9) {
         this.handleMethodException(finder, (Class[])null, var9);
      }

      return ret;
   }

   public String nativeQuery(String ejbql) throws FinderException {
      return this.rdbmsPersistence.nativeQuery(ejbql);
   }

   public String getDatabaseProductName() throws FinderException {
      return this.rdbmsPersistence.getDatabaseProductName();
   }

   public String getDatabaseProductVersion() throws FinderException {
      return this.rdbmsPersistence.getDatabaseProductVersion();
   }

   public Set remoteWrapperSetFinder(Method finder, Object[] fk) throws InternalException {
      return this.wrapperSetFinder(finder, fk, false, false);
   }

   public Set remoteWrapperSetFinder(Method finder, Object[] fk, boolean filterNulls) throws InternalException {
      return this.wrapperSetFinder(finder, fk, false, filterNulls);
   }

   public Set localWrapperSetFinder(Method finder, Object[] fk) throws InternalException {
      return this.wrapperSetFinder(finder, fk, true, false);
   }

   public Set localWrapperSetFinder(Method finder, Object[] fk, boolean filterNulls) throws InternalException {
      return this.wrapperSetFinder(finder, fk, true, filterNulls);
   }

   public Set wrapperSetFinder(Method finder, Object[] fk, boolean isLocal, boolean filterNulls) throws InternalException {
      if (debugLogger.isDebugEnabled()) {
         debug("called wrapperSetFinder...");
      }

      EntityBean reflectionTarget = this.getReflectionTarget(isLocal);
      Collection pks = null;
      Collection eosFromFinder = null;

      try {
         if (this.findersLoadBean) {
            eosFromFinder = this.persistence.collectionFinder(reflectionTarget, finder, fk);
         } else {
            pks = this.persistence.collectionFinder(reflectionTarget, finder, fk);
         }
      } catch (Throwable var9) {
         if (debugLogger.isDebugEnabled()) {
            debug("\texception thrown in setFinder: " + StackTraceUtilsClient.throwable2StackTrace(var9));
         }

         this.destroyReflectionTarget(reflectionTarget);
         this.handleMethodException(finder, (Class[])null, var9);
      }

      this.releaseReflectionTarget(reflectionTarget);
      Set oset = null;
      if (this.findersLoadBean) {
         oset = this.beanCollToObjectWrapperSet(eosFromFinder, isLocal, filterNulls);
      } else {
         oset = this.pkCollToObjectWrapperSet(pks, isLocal, filterNulls);
      }

      if (debugLogger.isDebugEnabled()) {
         debug("returning " + oset.size() + "objects from setFinder.");
      }

      return oset;
   }

   public Set remoteSetFinder(Method finder, Object[] args) throws InternalException {
      return (Set)this.collectionFinder(finder, args, true, false);
   }

   public Set localSetFinder(Method finder, Object[] args) throws InternalException {
      return (Set)this.collectionFinder(finder, args, true, true);
   }

   public void ensureDBExistence(Object pk) throws Throwable {
      EntityBean pooledBean = this.getBeanFromPool();
      ((WLEntityBean)pooledBean).__WL_setIsLocal(true);

      try {
         this.disableTransactionStatusCheck();
         this.persistence.findByPrimaryKey(pooledBean, this.getFindByPrimaryKeyMethod(), pk);
         this.releaseBeanToPool(pooledBean);
      } catch (Throwable var8) {
         this.destroyPooledBean(pooledBean);
         if (var8 instanceof InternalException) {
            Throwable detail = ((InternalException)var8).detail;
            if (detail != null) {
               throw detail;
            }
         }

         throw var8;
      } finally {
         this.enableTransactionStatusCheck();
      }

   }

   public void disableTransactionStatusCheck() throws SystemException {
      Transaction tx = TransactionService.getTransactionManager().getTransaction();
      if (tx != null) {
         weblogic.transaction.Transaction wtx = (weblogic.transaction.Transaction)tx;
         wtx.setProperty("DISABLE_TX_STATUS_CHECK", "true");
      }
   }

   public void enableTransactionStatusCheck() throws SystemException {
      Transaction tx = TransactionService.getTransactionManager().getTransaction();
      if (tx != null) {
         weblogic.transaction.Transaction wtx = (weblogic.transaction.Transaction)tx;
         wtx.setProperty("DISABLE_TX_STATUS_CHECK", (Serializable)null);
      }
   }

   protected Iterator cascadeDeleteRemove(InvocationWrapper wrap, EntityBean bean) throws InternalException {
      List listCascadeDelBeans = new ArrayList();
      List listCascadeDelBeansWithoutDBUpdate = new ArrayList();
      Map mapCascadeDelBeans = new HashMap();
      Iterator iterCascadeDelBeans = listCascadeDelBeans.iterator();
      if (!this.isCascadeDelete) {
         return iterCascadeDelBeans;
      } else if (bean instanceof CMPBean && this.uses20CMP) {
         try {
            ((CMPBean)bean).__WL_makeCascadeDelList(mapCascadeDelBeans, listCascadeDelBeans, listCascadeDelBeansWithoutDBUpdate, false);
         } catch (Exception var11) {
            EJBRuntimeUtils.throwInternalException("Error creating cascade delet list.", var11);
            throw new AssertionError("cannot reach");
         }

         if (debugLogger.isDebugEnabled()) {
            debug("mapCascadeDelBeans, ejbName- " + this.beanInfo.getEJBName());
            Iterator it = mapCascadeDelBeans.keySet().iterator();

            while(it.hasNext()) {
               debug(it.next().toString());
            }

            debug("listCascadeDelBeans, ejbName- " + this.beanInfo.getEJBName());
            it = listCascadeDelBeans.iterator();

            CMPBean b;
            while(it.hasNext()) {
               b = (CMPBean)it.next();
               b.__WL_setMethodState(16);
               debug(b.__WL_getEntityContext().getPrimaryKey().toString());
            }

            debug("listCascadeDelBeansWithoutDBUpdate, ejbName- " + this.beanInfo.getEJBName());
            it = listCascadeDelBeansWithoutDBUpdate.iterator();

            while(it.hasNext()) {
               b = (CMPBean)it.next();
               b.__WL_setMethodState(16);
               debug(b.__WL_getEntityContext().getPrimaryKey().toString());
            }
         }

         if (!this.orderDatabaseOperations) {
            PersistenceManager pm = ((CMPBean)bean).__WL_getPersistenceManager();
            if (pm instanceof RDBMSPersistenceManager) {
               ((RDBMSPersistenceManager)pm).flushModifiedBeans();
            }
         }

         iterCascadeDelBeans = listCascadeDelBeansWithoutDBUpdate.iterator();

         EntityBean iterBean;
         while(iterCascadeDelBeans.hasNext()) {
            try {
               iterBean = (EntityBean)iterCascadeDelBeans.next();
               this.getCMPBMFromBeanObj(iterBean).remove(wrap, iterBean, true);
            } catch (Exception var10) {
               EJBRuntimeUtils.throwInternalException("Error creating cascade step 2.", var10);
            }
         }

         iterCascadeDelBeans = listCascadeDelBeans.iterator();

         while(iterCascadeDelBeans.hasNext()) {
            try {
               iterBean = (EntityBean)iterCascadeDelBeans.next();
               if (iterBean == bean) {
                  return iterCascadeDelBeans;
               }

               this.getCMPBMFromBeanObj(iterBean).remove(wrap, iterBean, false);
            } catch (Exception var9) {
               EJBRuntimeUtils.throwInternalException("Error during cascade delete.", var9);
            }
         }

         return iterCascadeDelBeans;
      } else {
         return iterCascadeDelBeans;
      }
   }

   protected void cascadeDeleteRemove(InvocationWrapper wrap, EntityBean bean, Iterator iterCascadeDelBeans) throws InternalException {
      if (this.uses20CMP) {
         while(iterCascadeDelBeans.hasNext()) {
            try {
               EntityBean iterBean = (EntityBean)iterCascadeDelBeans.next();
               this.getCMPBMFromBeanObj(iterBean).remove(wrap, iterBean, false);
            } catch (Exception var5) {
               EJBRuntimeUtils.throwInternalException("Error during cascade delete", var5);
            }
         }

      }
   }

   private CMPBeanManager getCMPBMFromBeanObj(EntityBean bean) {
      RDBMSPersistenceManager pm = (RDBMSPersistenceManager)((CMPBean)bean).__WL_getPersistenceManager();
      return (CMPBeanManager)pm.getBeanManager();
   }

   public abstract void unpin(Object var1, Object var2);

   private Set beanCollToObjectWrapperSet(Collection eosFromFinder, boolean isLocal, boolean filterNulls) {
      if (!$assertionsDisabled && eosFromFinder == null) {
         throw new AssertionError();
      } else {
         Set s = new HashSet();
         Iterator it = eosFromFinder.iterator();

         while(it.hasNext()) {
            Object eo = it.next();
            if (eo == null) {
               if (!filterNulls) {
                  s.add((Object)null);
               }
            } else if (isLocal) {
               s.add(new EloWrapper((EJBLocalObject)eo));
            } else {
               s.add(new EoWrapper((EJBObject)eo));
            }
         }

         return s;
      }
   }

   public Collection pkCollToColl(Collection pks, boolean isLocal) {
      if (!$assertionsDisabled && pks == null) {
         throw new AssertionError();
      } else {
         ArrayList l = new ArrayList();
         Iterator it = pks.iterator();

         while(it.hasNext()) {
            Object primaryKey = it.next();
            if (primaryKey == null) {
               l.add((Object)null);
            } else if (isLocal) {
               EJBLocalObject elo = this.localHome.allocateELO(primaryKey);
               l.add(elo);
            } else {
               EJBObject eo = this.remoteHome.allocateEO(primaryKey);
               l.add(eo);
            }
         }

         return l;
      }
   }

   private Enumeration pkEnumToEnum(Enumeration pks, boolean isLocal) {
      if (!$assertionsDisabled && pks == null) {
         throw new AssertionError();
      } else {
         EJBObjectEnum en = new EJBObjectEnum();

         while(pks.hasMoreElements()) {
            Object primaryKey = pks.nextElement();
            if (primaryKey == null) {
               en.addElement((Object)null);
            } else if (isLocal) {
               EJBLocalObject elo = this.localHome.allocateELO(primaryKey);
               en.addElement(elo);
            } else {
               EJBObject eo = this.remoteHome.allocateEO(primaryKey);
               en.addElement(eo);
            }
         }

         return en;
      }
   }

   private Enumeration pkCollToEnum(Collection pks, boolean isLocal) {
      if (!$assertionsDisabled && pks == null) {
         throw new AssertionError();
      } else {
         EJBObjectEnum en = new EJBObjectEnum();
         Iterator iter = pks.iterator();

         while(iter.hasNext()) {
            Object primaryKey = iter.next();
            if (primaryKey == null) {
               en.addElement((Object)null);
            } else if (isLocal) {
               EJBLocalObject elo = this.localHome.allocateELO(primaryKey);
               en.addElement(elo);
            } else {
               EJBObject eo = this.remoteHome.allocateEO(primaryKey);
               en.addElement(eo);
            }
         }

         return en;
      }
   }

   private Set pkCollToSet(Collection pks, boolean isLocal) {
      if (!$assertionsDisabled && pks == null) {
         throw new AssertionError();
      } else {
         Set eoSet = new OrderedSet();
         Iterator it = pks.iterator();

         while(it.hasNext()) {
            Object primaryKey = it.next();
            if (primaryKey == null) {
               eoSet.add((Object)null);
            } else if (isLocal) {
               EJBLocalObject elo = this.localHome.allocateELO(primaryKey);
               eoSet.add(elo);
            } else {
               EJBObject eo = this.remoteHome.allocateEO(primaryKey);
               eoSet.add(eo);
            }
         }

         return eoSet;
      }
   }

   public Set pkCollToObjectWrapperSet(Collection pks, boolean isLocal) {
      return this.pkCollToObjectWrapperSet(pks, isLocal, false);
   }

   public Set pkCollToObjectWrapperSet(Collection pks, boolean isLocal, boolean filterNulls) {
      if (!$assertionsDisabled && pks == null) {
         throw new AssertionError();
      } else {
         Set s = new HashSet();
         Iterator it = pks.iterator();

         while(it.hasNext()) {
            Object primaryKey = it.next();
            if (primaryKey == null) {
               if (!filterNulls) {
                  s.add((Object)null);
               }
            } else if (isLocal) {
               EJBLocalObject elo = this.localHome.allocateELO(primaryKey);
               s.add(new EloWrapper(elo));
            } else {
               EJBObject eo = this.remoteHome.allocateEO(primaryKey);
               s.add(new EoWrapper(eo));
            }
         }

         return s;
      }
   }

   public boolean hasBeansEnrolledInTx(Transaction tx) {
      return this.getTxManager().hasListener(tx);
   }

   public void registerModifiedBean(Object pk, Transaction tx) throws InternalException {
      if (tx != null) {
         this.getEntityTxManager().registerModifiedBean(pk, tx);
      }

   }

   public void registerInvalidatedBean(Object pk, Object txOrThread) throws InternalException {
      if (txOrThread instanceof Transaction) {
         this.getEntityTxManager().registerInvalidatedBean(pk, (Transaction)txOrThread);
      } else {
         ((InvalidationBeanManager)this).invalidate(txOrThread, pk);
      }

   }

   public void unregisterModifiedBean(Object pk, Transaction tx) throws InternalException {
      if (tx != null) {
         this.getEntityTxManager().unregisterModifiedBean(pk, tx);
      }

   }

   public void flushModifiedBeans(Transaction tx) throws InternalException {
      if (tx != null) {
         this.getEntityTxManager().flushModifiedBeans(tx);
      }
   }

   public void initializePool() throws WLDeploymentException {
      this.entityPool.createInitialBeans();
   }

   public void setupTxListenerAndTxUser(Object pk, Object txOrThread, WLEntityBean bean) throws InternalException {
      this.setupTxListener(pk, txOrThread);
      bean.__WL_setLoadUser(this.getCurrentSubject());
   }

   public boolean needsRemoval(Object bean) {
      return bean == null || this.beanClass != bean.getClass();
   }

   public void beanImplClassChangeNotification() {
      this.beanClass = ((EntityBeanInfo)this.beanInfo).getGeneratedBeanClass();
      this.entityPool.reset();
      this.persistence.updateClassLoader(this.beanInfo.getClassLoader());
   }

   public void updateKeyCacheSize(int size) {
      if (this.uses20CMP && this.persistence instanceof RDBMSPersistenceManager) {
         ((RDBMSPersistenceManager)this.persistence).updateKeyCacheSize(size);
      }

   }

   protected boolean shouldStore(EntityBean bean) throws Throwable {
      return this.isModifiedMethod != null ? (Boolean)this.isModifiedMethod.invoke(bean, (Object[])null) : true;
   }

   public boolean isFlushPending(Transaction tx, Object pk) {
      return this.getEntityTxManager().isFlushPending(tx, pk);
   }

   public abstract boolean beanIsOpsComplete(Transaction var1, Object var2);

   public int passivateUnModifiedBean(Transaction tx, Object pk) {
      return !this.passivateBasicCheck(tx, pk) ? 0 : this.cachePassivateUnModifiedBean(tx, pk);
   }

   public boolean passivateLockedUnModifiedBean(Transaction tx, Object pk, EntityBean bean) {
      if (this.getEntityTxManager().isFlushPending(tx, pk)) {
         if (debugLogger.isDebugEnabled()) {
            debug(" bean is queued for a flush, cannot passivate as an unmodified bean. pk=" + pk);
         }

         return false;
      } else if (this.nonFKHolderRelationChange(bean)) {
         if (debugLogger.isDebugEnabled()) {
            debug("this bean is the non foreign key holder of a relationship that has been modified. This bean cannot be passivated.  pk=" + pk);
         }

         return false;
      } else if (this.m2NInsert(bean)) {
         if (debugLogger.isDebugEnabled()) {
            debug("this bean has had a deferred INSERT made to one of its many to many relationship member sets. This bean cannot be passivated.  pk=" + pk);
         }

         return false;
      } else {
         return true;
      }
   }

   public int passivateModifiedBean(Transaction tx, Object pk, boolean flushSuccess) {
      if (!this.passivateBasicCheck(tx, pk)) {
         return 0;
      } else {
         return !flushSuccess && !this.uses20CMP ? 0 : this.cachePassivateModifiedBean(tx, pk, flushSuccess);
      }
   }

   public boolean passivateLockedModifiedBean(Transaction tx, Object pk, boolean flushSuccess, EntityBean bean) {
      if (!flushSuccess) {
         if (this.nonFKHolderRelationChange(bean)) {
            if (debugLogger.isDebugEnabled()) {
               debug(" flush did not succeed, and this bean is the non foreign key holder of a relationship that has been modified. This bean cannot be passivated.  pk=" + pk);
            }

            return false;
         }

         if (this.m2NInsert(bean)) {
            if (debugLogger.isDebugEnabled()) {
               debug("this bean has had a deferred INSERT made to one of its many to many relationship member sets. This bean cannot be passivated.  pk=" + pk);
            }

            return false;
         }
      }

      return true;
   }

   private boolean passivateBasicCheck(Transaction tx, Object pk) {
      if (this.getVerifyReads()) {
         if (debugLogger.isDebugEnabled()) {
            debug(" bean is an optimistic concurrency bean with verify reads enabled. cannot passivate this bean, it must remain in cache. pk=" + pk);
         }

         return false;
      } else {
         return true;
      }
   }

   public boolean nonFKHolderRelationChange(EntityBean bean) {
      if (!this.uses20CMP) {
         return false;
      } else {
         return !(bean instanceof CMPBean) ? false : ((CMPBean)bean).__WL_getNonFKHolderRelationChange();
      }
   }

   public boolean m2NInsert(EntityBean bean) {
      if (!this.uses20CMP) {
         return false;
      } else {
         return !(bean instanceof CMPBean) ? false : ((CMPBean)bean).__WL_getM2NInsert();
      }
   }

   abstract boolean getVerifyReads();

   abstract int cachePassivateModifiedBean(Transaction var1, Object var2, boolean var3);

   abstract int cachePassivateUnModifiedBean(Transaction var1, Object var2);

   public void undeploy() {
      super.undeploy();
      if (this.reflectionTargetLocal != null) {
         this.releaseBeanToPool(this.reflectionTargetLocal);
      }

      if (this.reflectionTargetRemote != null) {
         this.releaseBeanToPool(this.reflectionTargetRemote);
      }

      if (this.entityPool != null) {
         this.entityPool.cleanup();
      }

   }

   public void reInitializePool() {
      this.entityPool.reInitializePool();
   }

   public EntityTxManager getEntityTxManager() {
      return (EntityTxManager)this.getTxManager();
   }

   private static void debug(String s) {
      debugLogger.debug("[BaseEntityManager] " + s);
   }

   static {
      _WLDF$INST_FLD_EJB_Diagnostic_Database_Access_Around_High = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "EJB_Diagnostic_Database_Access_Around_High");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "BaseEntityManager.java", "weblogic.ejb.container.manager.BaseEntityManager", "executeInsertStmt", "(Ljava/util/List;Ljavax/transaction/Transaction;Ljava/util/Set;ZZLjava/util/Collection;)V", 698, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Database_Access_Around_High};
      _WLDF$INST_JPFLD_1 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "BaseEntityManager.java", "weblogic.ejb.container.manager.BaseEntityManager", "executeUpdateStmt", "(Ljava/util/List;Ljavax/transaction/Transaction;Ljava/util/Set;ZZLjava/util/Collection;)V", 957, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_1 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Database_Access_Around_High};
      _WLDF$INST_JPFLD_2 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "BaseEntityManager.java", "weblogic.ejb.container.manager.BaseEntityManager", "executeDeleteStmt", "(Ljava/util/List;Ljavax/transaction/Transaction;Ljava/util/Set;ZZLjava/util/Collection;)V", 1341, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_2 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Database_Access_Around_High};
      $assertionsDisabled = !BaseEntityManager.class.desiredAssertionStatus();
      KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }
}
