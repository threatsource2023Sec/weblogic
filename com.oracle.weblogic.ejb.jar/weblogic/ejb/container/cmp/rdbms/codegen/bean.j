@start rule: main
/**
 * This code was automatically generated at @time on @date
 * by @generator -- do not edit.
 *
 * @@version @buildString
 * @@author Copyright (c) @year by BEA Systems, Inc. All Rights Reserved.
 */

@packageStatement

import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.io.IOException;

import javax.ejb.EntityContext;
import javax.ejb.EJBException;
import javax.ejb.EJBObject;
import javax.ejb.EJBLocalObject;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.NoSuchEntityException;
import javax.ejb.Handle;
import javax.ejb.HomeHandle;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NameClassPair;
import javax.rmi.PortableRemoteObject;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;

import weblogic.ejb.OptimisticConcurrencyException;

import weblogic.ejb.container.InternalException;
import weblogic.ejb.container.internal.EJBRuntimeUtils;
import weblogic.ejb.container.internal.EntityEJBContextImpl;
import weblogic.ejb.container.internal.EntityEJBLocalObject;
import weblogic.ejb.container.internal.EntityEJBObject;
import weblogic.ejb.container.interfaces.WLEnterpriseBean;
import weblogic.ejb.container.interfaces.CachingManager;
import weblogic.ejb.container.internal.QueryCachingHandler;
import weblogic.ejb.container.manager.TTLManager;
import weblogic.ejb.container.persistence.RSInfoImpl;
import weblogic.ejb.container.persistence.spi.CMPBean;
import weblogic.ejb.container.persistence.spi.CMPBeanManager;
import weblogic.ejb.container.persistence.spi.PersistenceManager;
import weblogic.ejb.container.persistence.spi.RSInfo;

import weblogic.ejb.container.cmp.rdbms.RDBMSObjectInputStream;
import weblogic.ejb.container.cmp.rdbms.RDBMSPersistenceManager;
import weblogic.ejb.container.cmp.rdbms.RDBMSUtils;
import weblogic.ejb.container.cmp.rdbms.RDBMSM2NSet;
import weblogic.ejb.container.cmp.rdbms.RDBMSSet;

import weblogic.ejb.container.cache.QueryCacheElement;
import weblogic.ejb.container.cache.QueryCacheKey;

import weblogic.ejb.container.utils.MultiMap;

import weblogic.ejb20.persistence.spi.PersistenceRuntimeException;
import weblogic.ejb20.cmp.rdbms.RDBMSException;

import weblogic.transaction.TransactionHelper;
import weblogic.transaction.TxHelper;
import weblogic.ejb.container.dd.DDConstants;

import weblogic.ejb.container.EJBLogger;
import weblogic.logging.Loggable;

import weblogic.utils.Debug;

public final class @cmpBeanClassName extends @ejb_class_name
  implements CMPBean, @simple_beanimpl_interface_name
{

  // =================================================================
  // Class variable(s)
  private static final weblogic.diagnostics.debug.DebugLogger @debugVar =
    weblogic.ejb.container.EJBDebugService.cmpRuntimeLogger;

  private static String EOL = System.getProperty("line.separator");

  @declareStaticFinderVars
  @assignStaticFinderVars

  @declareEjbSelectMethodVars
  @assignEjbSelectMethodVars
  
  // =================================================================
  // Instance variable(s)
   
  @perhapsDeclareRowSetFactoryVar

  private javax.ejb.EJBContext __WL_EJBContext;

  private int __WL_method_state;

  @declareBeanStateVar

  private boolean __WL_isRemoved = false;

  private boolean __WL_busy = false;
  
  private boolean __WL_isLocal = true;

  private boolean __WL_needsRemove;

  private Object __WL_loadUser;

  private boolean __WL_creatorOfTx;

  private boolean __WL_createAfterRemove = false;

  private boolean __WL_operationsComplete = false;

  private boolean @beanIsModifiedVar = false;

  /**
   *
   *  For WebLogic CMP20+ beans that are involved in 1-1 or 1-N
   *  relationships in which this bean does not map to a DBMS
   *  table that has the foreign key column(s) that point to 
   *  columns in the related table.
   *
   *  Indicates whether this CMP20+ bean has been involved in
   *  a relationship set change.
   *
   *  This is used when evaluating the bean's eligibility for
   *  passivation in a transaction.
   *   
   */
  private boolean __WL_nonFKHolderRelationChange = false;
  
 /**
   *
   *  For WebLogic CMP20+ beans that are involved in Many to Many
   *  relationships, this indicates whether this CMP20+ bean has 
   *  had a cached entry added to any of its Many to Many
   *  member Sets.
   *
   *  This is used when evaluating the bean's eligibility for
   *  passivation in a transaction.
   *   
   */
  private boolean __WL_m2NInsert = false;

  /**
   *  EJB3+ beans may be connected or disconnected
   *  from their datasources.
   */
  private boolean __WL_isConnected = true;


  @declareEntityContextVar
  @declareManagerVars

  @declareContainerManagedFieldVars

  @declareForeignKeyFieldVars
  @declareRelationFieldVars

  @perhapsDeclareSnapshotVars

  @declareIsModifiedVar
  @declareIsInvalidatedVar
  @declareIsLoadedVar
  @declareCmrIsLoadedVars
  // for remote relationships
  @perhapsDeclareInitialContext
  @declareHomeVars
  @perhapsDeclareTableLoadedModifiedVars

  // =================================================================
  // Constructor(s)
  public @cmpBeanClassName() @constructorExceptionList {
    super();

    __WL_initialize();

    @perhapsAssignInitialContext
  }

  // This method is called by ejbLoad, ejbPassivate, and ejbRemove
  // to initialize the persistent state of the bean and its associated
  // variables.
  public void __WL_initialize()
  {
    __WL_initialize(true);
  }

  public void __WL_initialize(boolean __WL_initSnapshotVars)
  {
    __WL_initialize_persistent(__WL_initSnapshotVars);

    @beanIsLoadedVar = false;
    @perhapsInitializeTableLoadedModifiedVars
    @perhapsInitializeModifiedBeanIsRegisteredVar
    @assignCmrIsLoadedFalse
    __WL_isRemoved = false;
	__WL_needsRemove = false;

    //cached relationship state
    @initializeRelationVars
  }

  private void __WL_initialize_persistent(boolean __WL_initSnapshotVars)
  {
    for (int @iVar = 0; @iVar < @allFieldsCount; @iVar++) {
       @isLoadedVar[@iVar] = false;
       @perhapsInitializeIsModified
    }

    @initializePersistentVars
    if (__WL_initSnapshotVars) {
      @perhapsInitializeSnapshotVars
    }

    @beanIsModifiedVar = false;
  }
  

  // ================================================================
  // Method(s)

  public boolean __WL_isBusy() { return __WL_busy; }
  public void __WL_setBusy(boolean b) { __WL_busy = b;}

  public boolean __WL_getIsLocal() { return __WL_isLocal; }
  public void __WL_setIsLocal(boolean b) { __WL_isLocal = b; }

  public int __WL_getMethodState() { return __WL_method_state; }
  public void __WL_setMethodState(int state) { __WL_method_state = state; }

  public boolean __WL_needsRemove() { return __WL_needsRemove; }
  public void __WL_setNeedsRemove(boolean b) { __WL_needsRemove = b; }

  public void __WL_setCreatorOfTx ( boolean b) { __WL_creatorOfTx = b; }
  public boolean __WL_isCreatorOfTx() { return __WL_creatorOfTx; }
  
  public javax.ejb.EJBContext __WL_getEJBContext() { return __WL_EJBContext; }
  public void __WL_setEJBContext(javax.ejb.EJBContext ctx) {
    __WL_EJBContext = ctx;
  }

  public void __WL_setLoadUser(Object o) { __WL_loadUser = o;}
  public Object __WL_getLoadUser() { return __WL_loadUser; } 

  @declareSetEntityContextMethod {
    int @oldStateVar = __WL_method_state;
    try {
      weblogic.ejb.container.internal.AllowedMethodsHelper.pushBean(this);
      __WL_method_state = STATE_SET_CONTEXT;
      super.setEntityContext(arg0);
      this.@ctxVar = arg0;
    } finally {
      __WL_method_state = @oldStateVar;
      weblogic.ejb.container.internal.AllowedMethodsHelper.popBean();
    }
  }

  public void unsetEntityContext()
    @unsetentitycontext_throws_clause
  {
    int @oldStateVar = __WL_method_state;
    try {
      weblogic.ejb.container.internal.AllowedMethodsHelper.pushBean(this);
      __WL_method_state = STATE_UNSET_CONTEXT;
      super.unsetEntityContext();
    } finally {
      __WL_method_state = @oldStateVar;
      weblogic.ejb.container.internal.AllowedMethodsHelper.popBean();
    }
  }

  public void ejbActivate()
    @activate_throws_clause
  {
    int @oldStateVar = __WL_method_state;
    try {
      weblogic.ejb.container.internal.AllowedMethodsHelper.pushBean(this);
      __WL_method_state = STATE_EJB_ACTIVATE;
      super.ejbActivate();
    } finally {
      __WL_method_state = @oldStateVar;
      weblogic.ejb.container.internal.AllowedMethodsHelper.popBean();
    }
  }


  // ================================================================
  // Getter and Setter methods.
  @declareCmpGettersAndSetters
  @declareCmrGettersAndSetters
  @declareCmrVariableGetters

  //=================================================================
  //Finder methods.
  @implementFinderMethods

  @implementGroupLoadMethods
  @implementLoadGroupFromRSMethods
  @implementLoadCMRFieldFromRSMethods

  // methods to load related beans - for Dynamic Finders
  @perhapsImplementRelCachingForDynamicFinders
  @implementLoaderMethodForDynamicFinders

  public static Object @getPKFromRSMethodName(java.sql.ResultSet @rsVar, java.lang.Integer @offsetIntObjVar, ClassLoader @classLoaderVar)
    throws java.sql.SQLException, java.lang.Exception
  {
    if (@debugEnabled) {
      @debugSay("@getPKFromRSMethodName");
    }

    @assignOffsetVar
    @declarePkVar
    @allocatePkVar
    @assignRSToPkVar
    @genIsPKNull
    return @pkVar;
  }

  public Object __WL_getPKFromRSInstance(java.sql.ResultSet @rsVar, java.lang.Integer @offsetIntObjVar, ClassLoader @classLoaderVar)
    throws java.sql.SQLException, java.lang.Exception
  {
    return @getPKFromRSMethodName(@rsVar, @offsetIntObjVar, @classLoaderVar);
  }

  //End finder methods.
  //=================================================================

  //=================================================================
  //Home methods.
  @home_methods

  //End home methods.
  //=================================================================

  //=================================================================
  // ejbSelect methods defined in this Bean's abstract class

  @implementEjbSelectMethods


  //=================================================================
  // ejbSelectInternal methods that are to run in this Bean

  @implementEjbSelectInternalMethods


  //=================================================================

  // ================================================================
  // Implementation of CMPBean
  public void __WL_setup(java.util.Map bmMap, PersistenceManager @pmVar) {
    if (@debugEnabled) {
      @debugSay("@cmpBeanClassName.setup called.");
    }

    assert (bmMap!=null);
    assert (@pmVar !=null);

    this.@pmVar = (RDBMSPersistenceManager)@pmVar; 
    this.@classLoaderVar = this.@pmVar.getClassLoader();

    assert (this.@classLoaderVar != null);

    @assignManagerVars
    @assignHomeVars
  }

  public EntityContext __WL_getEntityContext() {
    return @ctxVar;
  }

  public Object __WL_getPrimaryKey() {
    @implementGetPrimaryKey
  }

  public void __WL_setPrimaryKey(@pk_class @pkVar) {
    @implementSetPrimaryKey
  }

  public void __WL_superEjbLoad() @ejbLoadExceptionList {
    int @oldStateVar = __WL_method_state;

    try {
      weblogic.ejb.container.internal.AllowedMethodsHelper.pushBean(this);
      __WL_method_state = STATE_EJBLOAD;

      super.ejbLoad();
    } finally {
      __WL_method_state = @oldStateVar;
      weblogic.ejb.container.internal.AllowedMethodsHelper.popBean();
    }
  }

  public void __WL_superEjbStore() @ejbStoreExceptionList {
    int @oldStateVar = __WL_method_state;

    try {
      weblogic.ejb.container.internal.AllowedMethodsHelper.pushBean(this);
      __WL_method_state = STATE_EJBSTORE;
      if (@debugEnabled) {
        @debugSay("__WL_superEjbStore "+ @ctxVar.getPrimaryKey());
      }

      super.ejbStore();
    } finally {
      __WL_method_state = @oldStateVar;
      weblogic.ejb.container.internal.AllowedMethodsHelper.popBean();
    }
  }

  public void __WL_superEjbRemove(boolean initialize) @ejbRemoveExceptionList {
    @implementSuperEjbRemoveMethodBody
  }

  public void __WL_copyFrom(CMPBean otherBean, boolean __WL_initSnapshotVars) {
    @copyFromMethodBody
  }

  public void __WL_loadGroupByIndex(int index, java.sql.ResultSet rs,
    Integer offset, Object @pkVar, javax.ejb.EntityBean eb) throws Exception
  {
    switch(index) {
      @implementLoadIndexedGroupFromRSMethod
    }
  }

  public void __WL_loadCMRFieldByCmrField(String cmrField, java.sql.ResultSet rs,
    Integer offset, javax.ejb.EntityBean eb) throws Exception
  {
    @implementLoadCMRFieldFromRSMethod
  }

  public PersistenceManager __WL_getPersistenceManager() {
    return @pmVar;
  }

  public void __WL_makeCascadeDelList(java.util.Map mapCascadeDelBeans,
                                 java.util.List listCascadeDelBeans,
                                 java.util.List listCascadeDelBeansWithoutDBUpdate,
                                 boolean withoutDBUpdate)
    throws java.lang.Exception
  {
    @implementMakeCascadeDelListMethod
  }

  public void __WL_setBeanParamsForCreateArray(
    java.sql.PreparedStatement[] @stmtArrayVar, boolean woFkCols)
    throws Exception
  {
    @perhapsAssignOptimisticField
    @determineBeanParamsForCreateArray
  }

  public void __WL_setBeanParamsForUpdateArray(
    java.sql.PreparedStatement[] @stmtArrayVar,
    boolean[] @isModifiedVar,
    @pk_class @pkVar,
    int curTableIndex)
    throws Exception
  {
    // set all @stmtArrayVar when curTableIndex == -1
    @setBeanParamsForUpdateArray
  }

  public void __WL_setBeanParamsForDeleteArray(
    java.sql.PreparedStatement[] @stmtArrayVar,
    boolean[] @isModifiedVar,
    @pk_class @pkVar)
    throws Exception
  {
    @setPrimaryKeyParamsArray
  }

  public java.sql.PreparedStatement[] __WL_getStmtArray(
    java.sql.Connection @conVar,
    boolean[] @isModifiedVar,
    int operation,
    boolean woFkCols)
    throws Exception
  {
    @implementGetStmtArrayMethodBody
  }

  public void __WL_setBeanParamsForStmtArray(
    java.sql.PreparedStatement[] @stmtArrayVar,
    boolean[] @isModifiedVar,
    int operation,
    boolean woFkCols)
    throws Exception
  {
    @implementSetBeanParamsForStmtArrayMethodBody
  }

  public void __WL_setBeanParamsForBulkUpdateArray(
    java.sql.PreparedStatement[] @stmtArrayVar,
    boolean[] @isModifiedVar)
    throws Exception
  {
    @implementSetBeanParamsForBulkUpdateArrayMethodBody
  }

  public void __WL_setBeanParamsForBulkDeleteArray(
    java.sql.PreparedStatement[] @stmtArrayVar,
    boolean[] @isModifiedVar)
    throws Exception
  {
    @implementSetBeanParamsForBulkDeleteArrayMethodBody
  }

  public boolean[] __WL_getIsModifiedUnion(boolean[] @isModifiedUnionVar)
  {
    if (@isModifiedUnionVar == null) {
      @declareisModifiedUnionVar
    }

    boolean __WL_isModifiedUnionBoolean = false;
  
    @perhapsComputeModifiedUnion

    if (__WL_isModifiedUnionBoolean) {
      @perhapsUpdateOptimisticFieldInTables
    }
    
    return @isModifiedUnionVar;
  }

  public void __WL_resetIsModifiedVars(java.sql.Connection @conVar,
                                       boolean woFkCols)
    throws Exception
  {
    @perhapsImplementResetIsModifiedVarsMethodBody
  }

  public void __WL_resetIsModifiedVars(int operation,
                                     java.sql.Connection @conVar,
                                     boolean woFkCols)
    throws java.sql.SQLException, Exception
  {
    @perhapsImplementResetIsModifiedVarsMethodBodyOpnBased
  }

  public void __WL_perhapsTakeSnapshot() {
    @declareByteArrayVars
    @perhapsTakeSnapshot
  }

  public Collection __WL_getNullSnapshotVariables() {
    @perhapsGetNullSnapshotVariables
  }

  // add self related beans
  public void __WL_addSelfRelatedBeansToInsertStmt(PreparedStatement[] stmtArray,
                                                   List finishedKeys,
                                                   boolean internalFlush)
    throws Exception
  {
    @perhapsAddSelfRelatedBeansToInsertStmt
  }

  public void __WL_addSelfRelatedBeansToDeleteStmt(PreparedStatement[] stmtArray,
                                                   List finishedKeys,
                                                   boolean[] isModifiedUnion,
                                                   boolean internalFlush)
    throws Exception
  {
    @perhapsAddSelfRelatedBeansToDeleteStmt
  }

  public void __WL_perhapsReloadOptimisticColumn() throws Exception {
    @perhapsInvalidateOptimisticColumnField
  }

  public boolean @existsMethodName(Object @keyVar) {
    @implementExistsMethodBody
  }

  public void __WL_checkExistsOnMethod() throws NoSuchEntityException
  {
    @perhapsDoCheckExistsOnMethod
  }

  public void __WL_doCheckExistsOnMethod() throws NoSuchEntityException
  {
    @doCheckExistsOnMethod
  }

  public void __WL_setBeanStateValid(boolean valid) {
    @setBeanStateValidMethodBody
  }

  public boolean __WL_isBeanStateValid() {
    @isBeanStateValidMethodBody
  }

  public void __WL_setLastLoadTime(long time) {
    @perhapsSetLastLoadTimeMethodBody
  }

  public long __WL_getLastLoadTime() {
    @perhapsGetLastLoadTimeMethodBody
  }

  public boolean __WL_getIsRemoved() { return __WL_isRemoved; }
  public void __WL_setIsRemoved(boolean b) { __WL_isRemoved = b; }

  public void __WL_setCreateAfterRemove(boolean createAfterRemove) {
    __WL_createAfterRemove = createAfterRemove;
    @perhapsResetOurOptimisticColumnVariable
  }
  public void __WL_setInvalidatedBeanIsRegistered(boolean invalidationFlag) {
    @invalidatedBeanIsRegisteredVar = invalidationFlag;
  }

  public int __WL_appendVerifySqlForBatch(java.util.List verifyPk, 
                                          StringBuffer[] verifySql, 
                                          int[] verifyCount,
                                          int verifyMax)
  {
    int @oldStateVar = __WL_method_state;

    try {
      __WL_method_state = STATE_EJBSTORE;

      @perhapsAppendVerifySqlForBatch
    }
    finally {
      __WL_method_state = @oldStateVar;
    }

    return verifyMax;
  }

  public void __WL_setVerifyParamsForBatch(java.sql.Connection con,
                                           java.sql.PreparedStatement[] @stmtArrayVar, 
                                           int[] verifyCount)
    throws SQLException 
  {
    @perhapsSetVerifyParamsForBatch
  }

  public int __WL_appendVerifySql(java.util.List verifyPk, 
                             StringBuffer[] verifySql, 
                             int[] verifyCount,
                             int verifyMax)
  {
    int @oldStateVar = __WL_method_state;

    try {
      __WL_method_state = STATE_EJBSTORE;
      
      @perhapsAppendVerifySql
    }
    finally {
      __WL_method_state = @oldStateVar;
    }

    return verifyMax;
  }

  public void __WL_setVerifyParams(java.sql.Connection con,
                              java.sql.PreparedStatement[] @stmtArrayVar, 
                              int[] verifyCount)
    throws SQLException 
  {
    @perhapsSetVerifyParams
  }


  public String __WL_getM2NSQL(String cmrf, int operation)
  {
    @perhapsGetM2NSQL
  }

  public Collection __WL_getCmrBeansForCmrField(String cmrf)
  {
    @perhapsGetCmrBeansForCmrField
  }

  public void __WL_setOperationsComplete(boolean b) {
    __WL_operationsComplete = b;
  }

  public boolean __WL_getOperationsComplete() {
    return __WL_operationsComplete;
  }

  public void __WL_setNonFKHolderRelationChange(boolean b) {
    if (@debugEnabled) { 
      @debugSay("@cmpBeanClassName "+__WL_getPrimaryKey()+
      ", setting __WL_nonFKHolderRelationChange = "+b); 
    }
    __WL_nonFKHolderRelationChange = b;
  }

  public boolean __WL_getNonFKHolderRelationChange() {
    return __WL_nonFKHolderRelationChange;
  }

  public void __WL_setM2NInsert(boolean b) {
    if (@debugEnabled) { 
      @debugSay("@cmpBeanClassName "+__WL_getPrimaryKey()+
      ", setting __WL_m2NInsert = "+b); 
    }
    __WL_m2NInsert = b;
  }

  public boolean __WL_getM2NInsert() {
    return __WL_m2NInsert;
  }

  public void __WL_clearCMRFields() {
    @implementClearCMRFields
  }

  public void __WL_setLoaded(int fieldIndex, boolean value) {
    @isLoadedVar[fieldIndex] = value;
    @beanIsLoadedVar = true;
  }

  // This method is used by finders to cache related beans
  public void @cmrFieldQueryCachingMethodName(String @cmrFieldNameVar,
                                  QueryCacheKey @sourceQueryCacheKeyVar) {
    @implementCmrFieldPutInQueryCacheMethod
  }
  
  public boolean __WL_isConnected() {
    return __WL_isConnected;
  }

  public void __WL_setConnected(boolean b) {
    __WL_isConnected = b;
  }

  public boolean __WL_isModified() {
    return @beanIsModifiedVar;
  }

  // end of CMPBean
  // ================================================================


  //=================================================================
  // implementation of javax.ejb.EntityBean

  public boolean __WL_beanIsLoaded() { 
    return @beanIsLoadedVar; 
  }

  @perhapsDeclarePkCheckMethod

  @implementEjbCreateMethods

  @implementEjbPostCreateMethods

  @implementCreateMethod

  @implementEjbRemoveMethod

  @implementSetNullMethods

  @declareEjbLoadMethod
  {
    int @oldStateVar = __WL_method_state;
    try {
      weblogic.ejb.container.internal.AllowedMethodsHelper.pushBean(this);
      __WL_method_state = STATE_EJBLOAD;

      __WL_initialize();

      super.ejbLoad();
    } finally {
      __WL_method_state = @oldStateVar;
      weblogic.ejb.container.internal.AllowedMethodsHelper.popBean();
    }
  }

  @implementEjbStoreMethod

  public void __WL_store(boolean unregister) @ejbStoreExceptionList
  {
     @perhapsImplementWLStoreMethodBody
  }


  @declareEjbPassivateMethod
  {
    int @oldStateVar = __WL_method_state;
    try {
      weblogic.ejb.container.internal.AllowedMethodsHelper.pushBean(this);
      __WL_method_state = STATE_EJB_PASSIVATE;

      super.ejbPassivate();
      __WL_initialize();
    } finally {
      __WL_method_state = @oldStateVar;
      weblogic.ejb.container.internal.AllowedMethodsHelper.popBean();
    }
  }

  // Blob/Clob methods
  @perhapsDeclareSetBlobClobForOutputMethod

  // end javax.ejb.EntityBean
  //=================================================================


  public Object _WL__getCategoryValue() {
    @getCategoryValueMethodBody
  }

  private static void @debugSay(String s) {
    @debugVar.debug("[@getSimpleGeneratedBeanClassName] " + s);
  }

  private static void @debugSay(String s, Throwable th) {
    @debugVar.debug("[@getSimpleGeneratedBeanClassName] " + s, th);
  }

}


@end rule: main





@start rule: finderMethodBodyScalar
    if(@debugEnabled) {
      @debugSay("called @finderMethodName.");
    }

    java.sql.Connection __WL_con = null;
    java.sql.PreparedStatement @stmtVar = null;
    java.sql.ResultSet @rsVar = null;
    int @offsetVar = 0;
    @declareResultVar
    @perhapsDeclareRelationshipCachingPooledBeanVar
    @perhapsDeclareQueryCachingVars

    @perhapsFlushCaches

    int updateLockType = @pmVar.getUpdateLockType();

    java.lang.String @queryVar = null;

    switch(updateLockType) {
      case DDConstants.UPDATE_LOCK_AS_GENERATED:
	@generateSqlQueryOrSqlQueryForUpdateOrSqlQueryForUpdateOf;
        break;

      case DDConstants.UPDATE_LOCK_TX_LEVEL:
        @queryVar = "@finderQueryForUpdate";
        break;

      case DDConstants.UPDATE_LOCK_TX_LEVEL_NO_WAIT:
        @queryVar = "@finderQueryForUpdateNoWait";
        break;

      case DDConstants.UPDATE_LOCK_NONE:
        @queryVar = "@finderQuery";
        break;

      default:
        throw new AssertionError(
        "Unknown update lock type: '"+updateLockType+"'");
    }

    if(@debugEnabled) {
      @debugSay("Finder produced statement string " + @queryVar);
    }

    @perhapsSuspendTransaction

    try {
      __WL_con = @pmVar.getConnection(@perhapsTransactionParam);
    } 
    catch (java.lang.Exception e) {
      @pmVar.releaseResources(__WL_con, @stmtVar, @rsVar);
      throw new javax.ejb.FinderException("Couldn't get connection: " + EOL +
        e.toString() + EOL +
        RDBMSUtils.throwable2StackTrace(e));
    }

    @perhapsResumeTransaction

    try {

      @stmtVar = @conVar.prepareStatement(@queryVar);
      @resetParams
      @setFinderQueryParams
      @rsVar = @stmtVar.executeQuery();

      @perhapsAllocateRelationshipCachingPooledBeanVar
      @declarePKMapVar
      boolean @isMultiVar = false;
      while (@rsVar.next()) {
        @assignResultVar
      }

      if (@pkMapVar.size() == 0) {
        if(@debugEnabled) {
          @debugSay("Throwing FinderException because bean wasn't found.");
        }
        @findOrEjbSelectBeanNotFound
      }

      @perhapsPutInQueryCache

      @perhapsPostFinderCleanupForEagerCaching

      if (@isMultiVar || @pkMapVar.size() > 1) {
        Loggable l = EJBLogger.logfinderReturnedMultipleValuesLoggable("@finderMethodName");
        throw new javax.ejb.FinderException(l.getMessageText());
      }

      return @resultVar;
  } catch (javax.ejb.FinderException fe) {
    throw fe;
  } catch (java.sql.SQLException sqle) {
    throw new javax.ejb.FinderException(
      "Problem in @finderMethodName while preparing or executing " +
      "query: '" +
      @queryVar + "': " + EOL +
      "statement: '" +
      @stmtVar + "': " + EOL +
      sqle.toString() + EOL +
      RDBMSUtils.throwable2StackTrace(sqle));
  } catch (Exception e) {
    throw new javax.ejb.FinderException(
      "Exception raised in @finderMethodName " + EOL +
      e.toString() + EOL +
      RDBMSUtils.throwable2StackTrace(e));
  } finally {
    @pmVar.releaseResources(@conVar, @stmtVar, @rsVar);
    @perhapsRemoveRelationshipCachingPooledBeanVar
  }
@end rule: finderMethodBodyScalar

@start rule: finderMethodBodyMulti
    if(@debugEnabled) {
      @debugSay("called @finderMethodName");
    }

    java.sql.Connection __WL_con = null;
    java.sql.PreparedStatement @stmtVar = null;
    java.sql.ResultSet @rsVar = null;
    int @offsetVar = 0;
    @perhapsDeclareRelationshipCachingPooledBeanVar
    @perhapsDeclareQueryCachingVars

    @perhapsFlushCaches

    int updateLockType = @pmVar.getUpdateLockType();

    java.lang.String @queryVar = null;
    switch(updateLockType) {
      case DDConstants.UPDATE_LOCK_AS_GENERATED:
	@generateSqlQueryOrSqlQueryForUpdateOrSqlQueryForUpdateOf;
        break;

      case DDConstants.UPDATE_LOCK_TX_LEVEL:
        @queryVar = "@finderQueryForUpdate";
        break;

      case DDConstants.UPDATE_LOCK_TX_LEVEL_NO_WAIT:
        @queryVar = "@finderQueryForUpdateNoWait";
        break;

      case DDConstants.UPDATE_LOCK_NONE:
        @queryVar = "@finderQuery";
        break;

      default:
        throw new AssertionError(
        "Unknown update lock type: '"+updateLockType+"'");
    }

    if(@debugEnabled) {
      @debugSay("Finder produced statement string " + @queryVar);
    }


    @perhapsSuspendTransaction

    try {
      __WL_con = @pmVar.getConnection(@perhapsTransactionParam);
    } 
    catch (java.lang.Exception e) {
      @pmVar.releaseResources(__WL_con, @stmtVar, @rsVar);
      throw new javax.ejb.FinderException("Couldn't get connection: " + EOL +
        e.toString() + EOL +
        RDBMSUtils.throwable2StackTrace(e));
    }

    @perhapsResumeTransaction

    try {

      @stmtVar = __WL_con.prepareStatement(@queryVar);

      @perhapsSetMaxRows
      @resetParams
      @setFinderQueryParams

      @rsVar = @stmtVar.executeQuery();
    } 
    catch (java.lang.Exception e) {
      @pmVar.releaseResources(__WL_con, @stmtVar, @rsVar);
      throw new javax.ejb.FinderException(
        "Exception in @finderMethodName while preparing or executing " +
        "query: '" +
        @queryVar + "': " + EOL +
        "statement: '" + @stmtVar + "'" + EOL +
        e.toString() + EOL +
        RDBMSUtils.throwable2StackTrace(e));
    }

    try {
      @declareResultVar
      @perhapsAllocateRelationshipCachingPooledBeanVar
      @declarePKMapVar
      while (@rsVar.next()) {
        @assignResultVar
      }

      @perhapsPutInQueryCache

      @perhapsPostFinderCleanupForEagerCaching

      return @resultVar;

    } catch (java.sql.SQLException sqle) {
      throw new javax.ejb.FinderException(
        "Exception in '@finderMethodName' while using " +
        "result set: '" + @rsVar + "'" + EOL +
        sqle.toString() + EOL +
        RDBMSUtils.throwable2StackTrace(sqle));
    } catch (java.lang.Exception e) {
      throw new javax.ejb.FinderException(
        "Exception executing finder '@finderMethodName': " + EOL +
        e.toString() + EOL +
        RDBMSUtils.throwable2StackTrace(e));
    } finally {
      @perhapsRemoveRelationshipCachingPooledBeanVar
      @pmVar.releaseResources(@conVar, @stmtVar, @rsVar);
    }
@end rule: finderMethodBodyMulti

@start rule: ejbSelectFieldBodyScalar
    if(@debugEnabled) {
      @debugSay("called @finderMethodName");
    }

    java.sql.Connection __WL_con = null;
    java.sql.PreparedStatement @stmtVar = null;
    java.sql.ResultSet @rsVar = null;
    int @offsetVar = 0;
    @declareEjbSelectFieldResultVar

    @perhapsFlushCaches

    @perhapsSuspendTransaction

    try {
      __WL_con = @pmVar.getConnection(@perhapsTransactionParam);
    } 
    catch (java.lang.Exception e) {
      @pmVar.releaseResources(__WL_con, @stmtVar, @rsVar);
      throw new javax.ejb.FinderException("Couldn't get connection: " + EOL +
        e.toString() + EOL +
        RDBMSUtils.throwable2StackTrace(e));
    }

    @perhapsResumeTransaction

    java.lang.String @queryVar = "@finderQuery";
    if(@debugEnabled) {
      @debugSay("Finder produced statement string " + @queryVar);
    }

    try {      
      @stmtVar = @conVar.prepareStatement(@queryVar);

      @resetParams
      @setFinderQueryParams

      @rsVar = @stmtVar.executeQuery();

      if (@rsVar.next()) {
        @assignEjbSelectFieldResultVar

	    @checkNullForAggregateQuery

      } else {
        if(@debugEnabled) {
          @debugSay("Throwing FinderException because no results found.");
        }
        Loggable l = EJBLogger.logbeanNotFoundLoggable("@finderMethodName");
        throw new javax.ejb.ObjectNotFoundException(l.getMessage());
      }
      if (@rsVar.next()) {
        Loggable l = EJBLogger.logfinderReturnedMultipleValuesLoggable("@finderMethodName");
        throw new javax.ejb.FinderException(l.getMessageText());
      }
      return @ejbSelectFieldResultVar;
  } catch (javax.ejb.FinderException fe) {
    throw fe;
  } catch (java.sql.SQLException sqle) {
    throw new javax.ejb.FinderException(
      "Problem in @finderMethodName while preparing or executing " +
      "query: '" +
      @queryVar + "': " + EOL +
      "statement: '" +
      @stmtVar + "': " + EOL +
      sqle.toString() + EOL +
      RDBMSUtils.throwable2StackTrace(sqle));
  } catch (Exception e) {
    throw new javax.ejb.FinderException(
      "Exception raised in @finderMethodName " + EOL +
      e.toString() + EOL +
      RDBMSUtils.throwable2StackTrace(e));
  } finally {
    @pmVar.releaseResources(@conVar, @stmtVar, @rsVar);
  }
@end rule: ejbSelectFieldBodyScalar


@start rule: ejbSelectFieldBodyMulti
    if(@debugEnabled) {
      @debugSay("called @finderMethodName");
    }

    java.sql.Connection __WL_con = null;
    java.sql.PreparedStatement @stmtVar = null;
    java.sql.ResultSet @rsVar = null;
    int @offsetVar = 0;

    @perhapsFlushCaches

    @perhapsSuspendTransaction

    try {
      __WL_con = @pmVar.getConnection(@perhapsTransactionParam);
    } 
    catch (java.lang.Exception e) {
      @pmVar.releaseResources(__WL_con, @stmtVar, @rsVar);
      throw new javax.ejb.FinderException("Couldn't get connection: " + EOL +
        e.toString() + EOL +
        RDBMSUtils.throwable2StackTrace(e));
    }

    @perhapsResumeTransaction

    java.lang.String @queryVar = "@finderQuery";
    if(@debugEnabled) {
      @debugSay("Finder produced statement string " + @queryVar);
    }
      
    try {
      @stmtVar = __WL_con.prepareStatement(@queryVar);

      @perhapsSetMaxRows
      @resetParams
      @setFinderQueryParams
      @rsVar = @stmtVar.executeQuery();
    } catch (java.lang.Exception e) {
      @pmVar.releaseResources(__WL_con, @stmtVar, @rsVar);
      throw new javax.ejb.FinderException(
        "Exception in @finderMethodName while preparing or executing " +
        "statement: '" + @stmtVar + "'" + EOL +
        e.toString() + EOL +
        RDBMSUtils.throwable2StackTrace(e));
    }

    try {
      @ejbSelect_result_set_to_collection_class
    } catch (javax.ejb.FinderException fe) {
      throw fe;
    } catch (java.lang.Exception e) {
      throw new javax.ejb.FinderException(
        "Exception executing finder '@finderMethodName': " + EOL +
        e.toString() + EOL +
        RDBMSUtils.throwable2StackTrace(e));
    } finally {
      @pmVar.releaseResources(@conVar, @stmtVar, @rsVar);
    }
@end rule: ejbSelectFieldBodyMulti

@start rule: ejbSelectFieldBodyResultSet
    if(@debugEnabled) {
      @debugSay("called @finderMethodName");
    }

    java.sql.Connection __WL_con = null;
    java.sql.PreparedStatement @stmtVar = null;
    java.sql.ResultSet @rsVar = null;
    int @offsetVar = 0;

    @perhapsFlushCaches

    @perhapsSuspendTransaction

    try {
      __WL_con = @pmVar.getConnection(@perhapsTransactionParam);
    } 
    catch (java.lang.Exception e) {
      @pmVar.releaseResources(__WL_con, @stmtVar, @rsVar);
      throw new javax.ejb.FinderException("Couldn't get connection: " + EOL +
        e.toString() + EOL +
        RDBMSUtils.throwable2StackTrace(e));
    }

    @perhapsResumeTransaction

    javax.sql.RowSet @rowSetVar = null;
    
    java.lang.String @queryVar = "@finderQuery";
    if(@debugEnabled) {
      @debugSay("Finder produced statement string " + @queryVar);
    }

    try {
      @stmtVar = __WL_con.prepareStatement(@queryVar);

      @perhapsSetMaxRows
      @resetParams
      @setFinderQueryParams
      @rsVar = @stmtVar.executeQuery();

      if (@rowSetFactoryVar == null) {
        @rowSetFactoryVar = @rowSetFactoryName.newInstance();
      }
      @rowSetVar = @rowSetFactoryVar.newCachedRowSet();
      ((weblogic.jdbc.rowset.WLCachedRowSet)@rowSetVar).populate(@rsVar);

      return @rowSetVar;

    } catch (java.lang.Exception e) {
      throw new javax.ejb.FinderException(
        "Exception in @finderMethodName while preparing or executing " +
        "query: '" +
        @queryVar + "': " + EOL +
        "statement: '" + @stmtVar + "'" + EOL +
        e.toString() + EOL +
        RDBMSUtils.throwable2StackTrace(e));
    } finally {
      @pmVar.releaseResources(__WL_con, @stmtVar, @rsVar);
    }
@end rule: ejbSelectFieldBodyResultSet


@start rule: ejbSelectFieldToCollection

    java.util.ArrayList list = new java.util.ArrayList();
    if (@rsVar == null) return list;

    @perhapsDeclareDistinctFilterVar

    try {
      while (@rsVar.next()) {
        @declareEjbSelectFieldCollResultVar
        @assignEjbSelectFieldCollResultVar

        @addEjbSelectFieldToList
      }
      return list;
    }
    catch (java.sql.SQLException sqle) {
      throw new javax.ejb.FinderException(
        "Exception in '@finderMethodName' while using " +
        "result set: '" + @rsVar + "'" + EOL +
        sqle.toString() + EOL +
        RDBMSUtils.throwable2StackTrace(sqle));
    }

@end rule: ejbSelectFieldToCollection


@start rule: ejbSelectFieldToSet

    Set s = new weblogic.ejb20.utils.OrderedSet();
    try {
      while (@rsVar.next()) {
        @declareEjbSelectFieldCollResultVar
        @assignEjbSelectFieldCollResultVar

        s.add(@ejbSelectFieldResultVar);
      }
      return s;
    }
    catch (java.sql.SQLException sqle) {
      throw new javax.ejb.FinderException(
        "Exception in 'finderMethodName' while using " +
        "result set: '" + @rsVar + "'" + EOL +
        sqle.toString() + EOL +
        RDBMSUtils.throwable2StackTrace(sqle));
    }

@end rule: ejbSelectFieldToSet

@start rule: remoteReadJavaLangEOColumn
        try {
          remotePKObject = @rsVar.getObject(colnum);

          if (@rsVar.wasNull()) {
            hasMore = @rsVar.next();
            continue;
          }
        } catch (SQLException sqle) {
          @pmVar.releaseResources(@conVar, @stmtVar, @rsVar);
          throw new javax.ejb.FinderException("SQLException encountered while "+
               "attempting to read Remote Bean foreign Key as an Object " +
               sqle.toString() + EOL +
               RDBMSUtils.throwable2StackTrace(sqle));
        }
@end rule: remoteReadJavaLangEOColumn


@start rule: remoteReadJavaObjectEOColumn
        byte[] remotePKBytes = null;
        try {
          remotePKBytes = @rsVar.getBytes(colnum);
          if (@rsVar.wasNull()) {
            hasMore = @rsVar.next();
            continue;
          }
        }  catch (SQLException sqle) {
          @pmVar.releaseResources(@conVar, @stmtVar, @rsVar);
          throw new javax.ejb.FinderException("SQLException encountered while "+
               "attempting to read Remote Bean foreign Key as a Byte Array. "+ EOL +
               sqle.toString() + EOL +
               RDBMSUtils.throwable2StackTrace(sqle));
        }

        try {
          java.io.InputStream is = new java.io.ByteArrayInputStream(remotePKBytes);
          java.io.ObjectInputStream ois = new java.io.ObjectInputStream(is);
          remotePKObject = ois.readObject();
        } catch (Exception ex) {
          @pmVar.releaseResources(@conVar, @stmtVar, @rsVar);
          throw new javax.ejb.FinderException(" Exception while attempting "+
             " to deserialize Foreign Key to Remote Bean while processing "+
             " Finder @currentFinderName " + EOL +
             ex.toString() + EOL +
             RDBMSUtils.throwable2StackTrace(ex));
        }
@end rule: remoteReadJavaObjectEOColumn


@start rule: remoteFinderExec

    java.util.List remoteEOList = new java.util.ArrayList();

    // execute the remote finder and get remote EOs

    Class[] params = null;
    javax.ejb.EJBObject remoteEo = null;

    @execRemoteFinderByReturnClass

@end rule: remoteFinderExec


@start rule: remoteFinderExecCollection
      java.util.Collection c = null;
      try {
        @remoteFinderCollection
      } catch (Exception ex) {
        @pmVar.releaseResources(@conVar, @stmtVar, @rsVar);
        throw new javax.ejb.FinderException(" Exception while attempting "+
             " to run finder: @getRemoteFinderMethodName"+
             " on home: "+ @getRemoteHomeVarForFinder.getClass().getName() + EOL +
             ex.toString() + EOL +
             RDBMSUtils.throwable2StackTrace(ex));
      }

      Iterator it = c.iterator();
      while (it.hasNext()) {
        remoteEo = (javax.ejb.EJBObject) it.next();
        remoteEOList.add(remoteEo);
      }
@end rule: remoteFinderExecCollection



@start rule: remoteFinderExecEnumeration
      java.util.Enumeration e = null;
      try {
        @remoteFinderEnumeration
      } catch (Exception ex) {
        @pmVar.releaseResources(@conVar, @stmtVar, @rsVar);
        throw new javax.ejb.FinderException(" Exception while attempting "+
             " to run finder: @getRemoteFinderMethodName"+
             " on home: "+ @getRemoteHomeVarForFinder.getClass().getName() + EOL +
             ex.toString() + EOL +
             RDBMSUtils.throwable2StackTrace(ex));
      }

      while (e.hasMoreElements()) {
        remoteEo = (javax.ejb.EJBObject) e.nextElement();
        remoteEOList.add(remoteEo);
      }
@end rule: remoteFinderExecEnumeration



@start rule: remoteFinderExecSingle
      try {
        @remoteFinderSingle
      } catch (Exception ex) {
        @pmVar.releaseResources(@conVar, @stmtVar, @rsVar);
        throw new javax.ejb.FinderException(" Exception while attempting "+
             " to run finder: @getRemoteFinderMethodName"+
             " on home: "+ @getRemoteHomeVarForFinder.getClass().getName() + EOL +
             ex.toString() + EOL +
             RDBMSUtils.throwable2StackTrace(ex));
      }
      remoteEOList.add(remoteEo);
@end rule: remoteFinderExecSingle


@start rule: remoteParmToRemoteEOList

    // put the INPUT EJBObject parameter in the qualifying List

    java.util.List remoteEOList = new java.util.ArrayList();
    remoteEOList.add(@getRemoteBeanParamName);

@end rule: remoteParmToRemoteEOList


@start rule: reconcileEOListsEQ

    // now compare local and remote EOLists and qualify matching PKs

    java.util.List qualifiedList = new java.util.ArrayList();

    Iterator localIt = localEOList.iterator();

    while (localIt.hasNext()) {
      boolean matched = false;

      java.util.List localList = (java.util.List) localIt.next();

      if (localList.size() != 2) {
        @pmVar.releaseResources(@conVar, @stmtVar, @rsVar);
        throw new javax.ejb.FinderException("localEOList List does not have "+
               " a complete PK - EO pair");
      }


      @PkVarOrWLBean = ( @PkOrGenBeanClassName ) localList.get(0);

      javax.ejb.EJBObject localEO = (javax.ejb.EJBObject) localList.get(1);

      if (localEO != null) {
        Iterator remoteIt = remoteEOList.iterator();

        while (remoteIt.hasNext()) {
          javax.ejb.EJBObject remoteEO = (javax.ejb.EJBObject) remoteIt.next();

          boolean identical = false;

          try {
            identical = localEO.isIdentical(remoteEO);
          } catch (java.rmi.RemoteException re) {
             @pmVar.releaseResources(@conVar, @stmtVar, @rsVar);
             throw new javax.ejb.FinderException(" Exception while attempting "+
                " to compare localEO with remoteEO " + EOL +
                re.toString() + EOL +
                RDBMSUtils.throwable2StackTrace(re));
          }

          if (identical) {
            qualifiedList.add(@PkVarOrWLBean);
            matched = true;
            break;
          }
        }
      }
    }

@end rule: reconcileEOListsEQ


@start rule: reconcileEOListsNOTEQ

    // now compare local and remote EOLists and qualify non-matching PKs

    java.util.List qualifiedList = new java.util.ArrayList();

    Iterator localIt = localEOList.iterator();

    while (localIt.hasNext()) {

      java.util.List localList = (java.util.List) localIt.next();

      if (localList.size() != 2) {
        throw new javax.ejb.FinderException("localEOList List does not have "+
               " a complete PK - EO pair");
      }

      @PkVarOrWLBean = ( @PkOrGenBeanClassName ) localList.get(0);

      javax.ejb.EJBObject localEO = (javax.ejb.EJBObject) localList.get(1);

      if (localEO != null) {
        Iterator remoteIt = remoteEOList.iterator();
        boolean matched = false;

        while (remoteIt.hasNext()) {
          javax.ejb.EJBObject remoteEO = (javax.ejb.EJBObject) remoteIt.next();

          boolean identical = false;

          try {
            identical = localEO.isIdentical(remoteEO);
          } catch (java.rmi.RemoteException re) {
             @pmVar.releaseResources(@conVar, @stmtVar, @rsVar);
             throw new javax.ejb.FinderException(" Exception while attempting "+
                " to compare localEO with remoteEO "+ EOL +
                re.toString() + EOL +
                RDBMSUtils.throwable2StackTrace(re));
          }

          if (identical) {
            matched = true;
            break;
          }
        }
        if (matched == false) {
          qualifiedList.add(@PkVarOrWLBean);
        }
      }
    }

@end rule: reconcileEOListsNOTEQ


@start rule: finderMethodRemoteBodyCollection
    return qualifiedList;

@end rule: finderMethodRemoteBodyCollection


@start rule: finderMethodRemoteBodyScalar

    Iterator qit = qualifiedList.iterator();
    if (qit.hasNext()) {
      @PkVarOrWLBean = (@PkOrGenBeanClassName) qit.next();
    } else {
      throw new javax.ejb.FinderException(
          "no value found for '@finderMethodName'.");
    }
    return @PkVarOrWLBean;

@end rule: finderMethodRemoteBodyScalar


@start rule: implementCreateMethod
  private Object @createMethodName() throws Exception {
    if (@debugEnabled) {
      @debugSay("called @createMethodName.");
    }

    @perhapsImplementCreateMethodBody
  }
@end rule: implementCreateMethod

@start rule: implementCreateMethodBody
    @declareByteArrayVars
    java.sql.Connection @conVar = null;
    @declareStmtArrayVars
    @declareQueryArrayVars
    java.sql.ResultSet @rsVar = null;

    @declarePkVar
    boolean __WL_beanInserted = false;

    try {
      @allocatePkVar
      @copyKeyValuesToPkVar

      @conVar = @pmVar.getConnection();
      if(@debugEnabled) {
        @debugSay("@createMethodName() got connection.");
      }

      @setCreateQueryArray

      if (@debugEnabled) {
        for (int i = 0 ; i < @getTableCount ; i++) {
          @debugSay("@createMethodName() produced sqlString " + @queryArrayVar[i]);
        }
      }

      @prepareCreateStmtArray

      __WL_setBeanParamsForCreateArray(@stmtArrayVar, false);

      if(@debugEnabled) {
        @debugSay("@createMethodName() about to execute sql.");
      }

      @executeUpdateOrExecuteQuery
      __WL_beanInserted = true;

      @setBlobClobForCreate

      for (int @iVar = 0; @iVar < @isModifiedVar.length; @iVar++) {
        @isModifiedVar[@iVar] = false;
      }
      @modifiedBeanIsRegisteredVar = false;

     __WL_perhapsReloadOptimisticColumn();

      return @pkVar;
    } catch (java.sql.SQLException se) {
      @createExceptionCheckForDuplicateKey
    } finally {
      @pmVar.releaseResultSet(@rsVar);
      @pmVar.releaseArrayResources(@conVar, @stmtArrayVar, null);
   }
@end rule: implementCreateMethodBody


@start rule: implementGetStmtArrayMethodBody
    if (@debugEnabled) {
      @debugSay("called __WL_getStmtArray.");
    }

    @declareStmtArrayVars
    @declareQueryArrayVars

    if (operation == DDConstants.INSERT) {
      if (woFkCols) {
        @setCreateQueryArrayWoFkColumns
      }
      else {
        @setCreateQueryArray
      }
    } else if (operation == DDConstants.UPDATE) {
      int @countVar        = 0;
      @perhapsDeclareBlobClobCountVar
      StringBuffer sb      = new StringBuffer();

      @perhapsReloadOptimisticColumn

      @perhapsCreateSnapshotBuffer

      @setUpdateQueryArray
    } else if (operation == DDConstants.DELETE) {
      @perhapsReloadOptimisticColumn

      @perhapsCreateSnapshotBuffer

      @setRemoveQueryArray
    }

    if(@debugEnabled) {
      for (int i = 0 ; i < @getTableCount ; i++) {
        @debugSay("__WL_getStmtArray() for " + operation + " produced sqlString: " + @queryArrayVar[i]);
      }
    }

    @prepareStmtArray

    return @stmtArrayVar;
@end rule: implementGetStmtArrayMethodBody

@start rule: implementSetBeanParamsForStmtArrayMethodBody
    if (operation == DDConstants.INSERT) {
      __WL_setBeanParamsForCreateArray(@stmtArrayVar, woFkCols);
    } else if (operation == DDConstants.UPDATE) {
      __WL_setBeanParamsForBulkUpdateArray(@stmtArrayVar, @isModifiedVar);
    } else if (operation == DDConstants.DELETE) {
      __WL_setBeanParamsForBulkDeleteArray(@stmtArrayVar, @isModifiedVar);
    }
@end rule: implementSetBeanParamsForStmtArrayMethodBody

@start rule: implementSetBeanParamsForBulkUpdateArrayMethodBody
  int @oldStateVar = __WL_method_state;

  try {
    __WL_method_state = STATE_EJBSTORE;

    @perhapsReloadOptimisticColumn

    @pk_class @pkVar = (@pk_class) @ctxVar.getPrimaryKey();

    __WL_setBeanParamsForUpdateArray(@stmtArrayVar, @isModifiedVar, @pkVar, -1);
  } finally {
    __WL_method_state = @oldStateVar;
  }
@end rule: implementSetBeanParamsForBulkUpdateArrayMethodBody

@start rule: implementSetBeanParamsForUpdateArrayMethodBody
  if ((@currStmtArrayVar != null) && ((curTableIndex == @curTableIndex) || (curTableIndex == -1))) {
    int @numVar = 1;
    @setUpdateBeanParams
    @perhapsSetOptimisticColumnParam
    @setPrimaryKeyParamsUsingNum
    @perhapsSetSnapshotParameters
  }

@end rule: implementSetBeanParamsForUpdateArrayMethodBody

@start rule: implementSetBeanParamsForBulkDeleteArrayMethodBody
  int @oldStateVar = __WL_method_state;

  try {
    __WL_method_state = STATE_EJB_REMOVE;

    @perhapsReloadOptimisticColumn

    @pk_class @pkVar = (@pk_class) @ctxVar.getPrimaryKey();

    __WL_setBeanParamsForDeleteArray(@stmtArrayVar, @isModifiedVar, @pkVar);
  } finally {
    __WL_method_state = @oldStateVar;
  }
@end rule: implementSetBeanParamsForBulkDeleteArrayMethodBody


@start rule: computeModifiedUnion
    for (int i=0; i<@isModifiedVar.length; i++) {
      @isModifiedUnionVar[i] |= @isModifiedVar[i];
      __WL_isModifiedUnionBoolean |= @isModifiedVar[i];
    }
@end rule: computeModifiedUnion
  
@start rule: implementResetIsModifiedVarsMethodBody
  int @oldStateVar = __WL_method_state;

  try {
    __WL_method_state = STATE_EJBSTORE;

    int @numVar = 1;
    @pk_class @pkVar = (@pk_class) @ctxVar.getPrimaryKey();
    @setBlobClobForStore

    for (int @iVar = 0; @iVar < @isModifiedVar.length; @iVar++) {
      if ((!woFkCols) ||
          (woFkCols && (@fieldsWoFkColumns))) {
        if(@isModifiedVar[@iVar]) {
          @isModifiedVar[@iVar] = false;
          @isLoadedVar[@iVar] = true;
        }
      }
      @modifiedBeanIsRegisteredVar = false;
    }
    @perhapsResetTableModifiedVarsForBean
  } finally {
    __WL_method_state = @oldStateVar;
  }
@end rule: implementResetIsModifiedVarsMethodBody

@start rule: implementAddSelfRelatedBeansToInsertStmtMethodBody
  if ((@fieldVarForField != null) && @bmVarForField.needsToBeInserted(@fieldVarGetPrimaryKey)) {
    CMPBean @beanVar = (CMPBean) @bmVarForField.lookup(@fieldVarGetPrimaryKey);
    @bmVarForField.addBeanToInsertStmt(stmtArray, finishedKeys, @beanVar, internalFlush, true);
  }
@end rule: implementAddSelfRelatedBeansToInsertStmtMethodBody

@start rule: implementAddSelfRelatedBeansToDeleteStmtMethodBody
  if (!@fieldRemovedVarForField.isEmpty()) {
    for (Iterator i=@fieldRemovedVarForField.iterator(); i.hasNext();) {
      CMPBean @beanVar = (CMPBean) @bmVarForField.lookup(((javax.ejb.EJBLocalObject)i.next()).getPrimaryKey());
      @bmVarForField.addBeanToDeleteStmt(stmtArray, finishedKeys, isModifiedUnion, @beanVar, internalFlush, true);
    }
  }
@end rule: implementAddSelfRelatedBeansToDeleteStmtMethodBody

@start rule: implementResetIsModifiedVarsMethodBodyOpnBased
  int @oldStateVar = __WL_method_state;
  try {
    __WL_method_state = STATE_EJBSTORE;

    int @numVar = 1;
    @pk_class @pkVar = (@pk_class) @ctxVar.getPrimaryKey();
    @setBlobClobBasedOnOperation
    for (int @iVar = 0; @iVar < @isModifiedVar.length; @iVar++) {
      if ((!woFkCols) ||
          (woFkCols && (@fieldsWoFkColumns))) {
        if(@isModifiedVar[@iVar]) {
          @isModifiedVar[@iVar] = false;
          @isLoadedVar[@iVar] = true;
        }
      }
      @modifiedBeanIsRegisteredVar = false;
    }
    @perhapsResetTableModifiedVarsForBean
  } finally {
    __WL_method_state = @oldStateVar;
  }

@end rule: implementResetIsModifiedVarsMethodBodyOpnBased
    

@start rule: setBlobForOutputBody

  private void @setBlobClobForOutputMethodName(java.sql.Connection @conVar,@pk_class @pkVar)
    throws IOException, java.sql.SQLException, Exception
  {
    java.sql.PreparedStatement @stmtVar = null;
    String @queryVar = null;
    java.sql.ResultSet @rsVar = null;
    //@pk_class @pkVar = (@pk_class) @ctxVar.getPrimaryKey();

    String selectForUpdate = @pmVar.selectForUpdateOrForUpdateNowait();

    // write Blob
    try {
      if (this.@fieldNameForField == null) {
        @queryVar = "UPDATE @curTableName SET @cmpColumnForCmpField=null WHERE @idParamsSqlForCurTable";
      }
      else {
        @queryVar = "select @cmpColumnForCmpField from @curTableName where @idParamsSqlForCurTable " +
          selectForUpdate;
      }

      if (@debugEnabled)
        @debugSay(" Blob operation: "+@queryVar);

      @stmtVar = @conVar.prepareStatement(@queryVar);

      @resetParams
      @setPrimaryKeyParams

      if (this.@fieldNameForField == null) {
        @stmtVar.executeUpdate();
      }
      else {
        @rsVar = @stmtVar.executeQuery();

        java.sql.Blob lob = null;
        if (@rsVar.next()) {
          lob = @rsVar.getBlob("@cmpColumnForCmpField");
        }

	@perhapsByteArrayIsSerializedToBlob

        // CR053905 BLOB and CLOB Updates with data of length < original are incorrect.
        // The second condition is for the above CR, this is the only way to
        // make the updated lob has the correct length.

	@perhapsTruncateBlob

        if (@debugEnabled)
          @debugSay(" we now have valid Blob Handle, get BinaryOutputStream for writing.");

	OutputStream os = null;

	@writeBlobToOutputStream

        os.flush();
        os.close();

	@perhapsUpdateBlob
      }
    } catch (IOException ioe) {
      if (@debugEnabled) {
        @debugSay("IOException for Blob/Clob" + ioe.getMessage());
      }
      throw ioe;
    } catch (java.sql.SQLException se) {
      if (@debugEnabled) {
        @debugSay("SQLException for Blob/Clob" + se.getMessage());
      }
      throw se;
    } finally {
      @pmVar.releaseResources(null, @stmtVar, @rsVar);
    }
  }
@end rule: setBlobForOutputBody

@start rule: setBlobForOutputBodyForDerby

  private void @setBlobClobForOutputMethodName(java.sql.Connection @conVar,@pk_class @pkVar)
    throws java.sql.SQLException, Exception
  {
    java.sql.PreparedStatement @stmtVar = null;
    String @queryVar = null;
    java.sql.ResultSet @rsVar = null;
    //@pk_class @pkVar = (@pk_class) @ctxVar.getPrimaryKey();

    // write Blob
    try {
      if (this.@fieldNameForField == null) {
        @queryVar = "UPDATE @curTableName SET @cmpColumnForCmpField=null WHERE @idParamsSqlForCurTable";
      } else {
        @queryVar = "UPDATE @curTableName SET @cmpColumnForCmpField=? WHERE @idParamsSqlForCurTable";
        // CR053905 BLOB and CLOB Updates with data of length < original are incorrect.
        // The second condition is for the above CR, this is the only way to
        // make the updated lob has the correct length.
      }

      if (@debugEnabled)
        @debugSay(" Blob operation: "+@queryVar);

      @stmtVar = @conVar.prepareStatement(@queryVar);
      @resetParams

      if (this.@fieldNameForField != null) {
        if (@debugEnabled)
          @debugSay(" update BLOB field with: " + @queryVar);

        @setLobAsTypeParam
      }
      @setPrimaryKeyParams
      @stmtVar.executeUpdate();
    } catch (java.sql.SQLException se) {
      if (@debugEnabled) {
        @debugSay("SQLException for Blob/Clob" + se.getMessage());
      }
      throw se;
    } finally {
      @pmVar.releaseResources(null, @stmtVar, @rsVar);
    }
  }
@end rule: setBlobForOutputBodyForDerby

@start rule: convertFieldToByteArray
        // convert the field to byte[]
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(this.@fieldNameForField);
        oos.flush();

        byte[] outByteArray = baos.toByteArray();

        baos.close();
        oos.close();

@end rule: convertFieldToByteArray

@start rule: writeOracleBlobToOutputStream
        os = ((java.sql.Blob) lob).setBinaryStream(1);
        os.write(outByteArray);

@end rule: writeOracleBlobToOutputStream

@start rule: writeGenericBlobToOutputStream
        os = lob.setBinaryStream(1);

        os.write(outByteArray);

@end rule: writeGenericBlobToOutputStream

@start rule: truncateOracleBlob
        if ( (lob == null) || (lob.length() > outByteArray.length) ) {
          // Column is a NULL BLOB, to UPDATE this to a non-NULL BLOB we must first INSERT an EMPTY_BLOB

          @queryVar = "UPDATE @curTableName SET @cmpColumnForCmpField=EMPTY_BLOB() WHERE @idParamsSqlForCurTable";

          if (@debugEnabled)
            @debugSay(" read NULL BLOB or update data length < original data length, set to EMPTY_BLOB() for update "+@queryVar);

          @pmVar.releaseResources(null, @stmtVar, null);
          @stmtVar = @conVar.prepareStatement(@queryVar);
          @resetParams
          @setPrimaryKeyParams

          @stmtVar.executeUpdate();

          @queryVar = "select @cmpColumnForCmpField from @curTableName where @idParamsSqlForCurTable " +
            selectForUpdate;

          if (@debugEnabled)
            @debugSay(" now SELECT out EMPTY_BLOB(): "+@queryVar);

          @pmVar.releaseResources(null, @stmtVar, @rsVar);
          @stmtVar = @conVar.prepareStatement(@queryVar);
          @resetParams
          @setPrimaryKeyParams

          @rsVar = @stmtVar.executeQuery();

          if (@rsVar.next()) {
            lob = @rsVar.getBlob("@cmpColumnForCmpField");
          }
        }

        if (lob == null) {
          throw new SQLException(" Could not get java.sql.Blob to write to");
        }
@end rule: truncateOracleBlob

@start rule: truncateGenericBlob
	if (lob == null) {
          // no Blob was previously inserted so insert one now
          @queryVar = "UPDATE @curTableName SET @cmpColumnForCmpField=? WHERE @idParamsSqlForCurTable";

          if (@debugEnabled)
            @debugSay(" read NULL BLOB, inserting with: "+@queryVar);

          @pmVar.releaseResources(null, @stmtVar, null);
          @stmtVar = @conVar.prepareStatement(@queryVar);
          @resetParams
          @setLobAsTypeParam
          @setPrimaryKeyParams

          @stmtVar.executeUpdate();

          // we're done here
	  return;
        } else if(lob.length() > outByteArray.length) {
          lob.truncate(outByteArray.length);
        }
@end rule: truncateGenericBlob

@start rule: updateBlob

      @queryVar = "UPDATE @curTableName SET @cmpColumnForCmpField=? WHERE @idParamsSqlForCurTable";
      if (@debugEnabled)
        @debugSay(" updating blob in database: "+@queryVar);

      @stmtVar = @conVar.prepareStatement(@queryVar);
      @resetParams
      @setBlobParam
      @setPrimaryKeyParams

      @stmtVar.executeUpdate();

@end rule: updateBlob

@start rule: setClobForOutputBody

  private void @setBlobClobForOutputMethodName(java.sql.Connection @conVar,@pk_class @pkVar)
    throws IOException, java.sql.SQLException, Exception
  {
    java.sql.PreparedStatement @stmtVar = null;
    String @queryVar = null;
    java.sql.ResultSet @rsVar = null;
//    @pk_class @pkVar = (@pk_class) @ctxVar.getPrimaryKey();

    String selectForUpdate = @pmVar.selectForUpdateOrForUpdateNowait();

    // write Clob
    try {
      if (this.@fieldNameForField == null) {
        @queryVar = "UPDATE @curTableName SET @cmpColumnForCmpField=null WHERE @idParamsSqlForCurTable";
      }
      else {
        @queryVar = "select @cmpColumnForCmpField from @curTableName where @idParamsSqlForCurTable " +
          selectForUpdate;
      }

      if (@debugEnabled)
        @debugSay(" Clob operation: "+@queryVar);

      @stmtVar = @conVar.prepareStatement(@queryVar);

      @resetParams
      @setPrimaryKeyParams

      if (this.@fieldNameForField == null) {
        @stmtVar.executeUpdate();
      }
      else {
        @rsVar = @stmtVar.executeQuery();

        java.sql.Clob lob = null;
        if (@rsVar.next()) {
          lob = @rsVar.getClob("@cmpColumnForCmpField");
        }
        
        // convert the field to char[]
        char[] outCharArray = this.@fieldNameForField.toCharArray();



        // CR053905 BLOB and CLOB Updates with data of length < original are incorrect.
        // The second condition is for the above CR, this is the only way to
        // make the updated lob has the correct length.

        @perhapsTruncateClob

        if (@debugEnabled)
          @debugSay(" we now have valid Clob Handle, get CharacterOutputStream for writing.");

	Writer writer = null;

	@getClobWriter
	
        if (@debugEnabled)
          @debugSay(" we now have CharacterOutputStream for writing, now write.");

        writer.write(outCharArray);
        writer.flush();
        writer.close();

	@perhapsUpdateClob
      }
    } catch (IOException ioe) {
      if (@debugEnabled) {
        @debugSay("IOException for Blob/Clob" + ioe.getMessage());
      }
      throw ioe;
    } catch (java.sql.SQLException se) {
      if (@debugEnabled) {
        @debugSay("SQLException for Blob/Clob" + se.getMessage());
      }
      throw se;
    } finally {
      @pmVar.releaseResources(null, @stmtVar, @rsVar);
    }
  }
@end rule: setClobForOutputBody

@start rule: setClobForOutputBodyForDerby

  private void @setBlobClobForOutputMethodName(java.sql.Connection @conVar,@pk_class @pkVar)
    throws java.sql.SQLException, Exception
  {
    java.sql.PreparedStatement @stmtVar = null;
    String @queryVar = null;
    java.sql.ResultSet @rsVar = null;
//    @pk_class @pkVar = (@pk_class) @ctxVar.getPrimaryKey();

    // write Clob
    try {
      if (this.@fieldNameForField == null) {
        @queryVar = "UPDATE @curTableName SET @cmpColumnForCmpField=null WHERE @idParamsSqlForCurTable";
      } else {
        @queryVar = "UPDATE @curTableName SET @cmpColumnForCmpField=? WHERE @idParamsSqlForCurTable";
        // CR053905 BLOB and CLOB Updates with data of length < original are incorrect.
        // The second condition is for the above CR, this is the only way to
        // make the updated lob has the correct length.
      }

      if (@debugEnabled)
        @debugSay(" Clob operation: "+@queryVar);

      @stmtVar = @conVar.prepareStatement(@queryVar);
      @resetParams

      if (this.@fieldNameForField != null) {
        if (@debugEnabled)
          @debugSay(" update CLOB field with: " + @queryVar);

        @setLobAsTypeParam
      }
      @setPrimaryKeyParams
      @stmtVar.executeUpdate();
    } catch (java.sql.SQLException se) {
      if (@debugEnabled) {
        @debugSay("SQLException for Blob/Clob" + se.getMessage());
      }
      throw se;
    } finally {
      @pmVar.releaseResources(null, @stmtVar, @rsVar);
    }
  }
@end rule: setClobForOutputBodyForDerby

@start rule: truncateOracleClob
        if ( (lob == null) || (lob.length() > outCharArray.length) ) {

          // Column is a NULL CLOB, to UPDATE this to a non-NULL CLOB we must first INSERT an EMPTY_CLOB

          @queryVar = "UPDATE @curTableName SET @cmpColumnForCmpField=EMPTY_CLOB() WHERE @idParamsSqlForCurTable";

          if (@debugEnabled)
            @debugSay(" read NULL CLOB or update data length < original data length, set to EMPTY_CLOB() for update "+@queryVar);

          @pmVar.releaseResources(null, @stmtVar, null);       
          @stmtVar = @conVar.prepareStatement(@queryVar);
          @resetParams
          @setPrimaryKeyParams

          @stmtVar.executeUpdate();

          @queryVar = "select @cmpColumnForCmpField from @curTableName where @idParamsSqlForCurTable " +
            selectForUpdate;

          if (@debugEnabled)
            @debugSay(" now SELECT out EMPTY_CLOB(): "+@queryVar);

          @pmVar.releaseResources(null, @stmtVar, @rsVar);
          @stmtVar = @conVar.prepareStatement(@queryVar);
          @resetParams
          @setPrimaryKeyParams

          @rsVar = @stmtVar.executeQuery();

          if (@rsVar.next()) {
            lob = @rsVar.getClob("@cmpColumnForCmpField");
          }

          if (lob == null) {
            throw new SQLException(" Could not get java.sql.Clob to write to");
          }
       }
@end rule: truncateOracleClob

@start rule: truncateGenericClob
	if (lob == null) {
          // no Clob was previously inserted so insert one now
          @queryVar = "UPDATE @curTableName SET @cmpColumnForCmpField=? WHERE @idParamsSqlForCurTable";

          if (@debugEnabled)
            @debugSay(" read NULL CLOB, inserting with: "+@queryVar);

          @pmVar.releaseResources(null, @stmtVar, null);
          @stmtVar = @conVar.prepareStatement(@queryVar);
          @resetParams
          @setLobAsTypeParam
          @setPrimaryKeyParams

          @stmtVar.executeUpdate();

          // we're done here
	  return;
        } else if(lob.length() > outCharArray.length) {
          lob.truncate(outCharArray.length);
        }
@end rule: truncateGenericClob

@start rule: getOracleClobWriter
        writer = ((java.sql.Clob) lob).setCharacterStream(1);
@end rule: getOracleClobWriter

@start rule: getGenericClobWriter
        writer = ((java.sql.Clob) lob).setCharacterStream(1);
@end rule: getGenericClobWriter

@start rule: updateClob

      @queryVar = "UPDATE @curTableName SET @cmpColumnForCmpField=? WHERE @idParamsSqlForCurTable";
      if (@debugEnabled)
        @debugSay(" updating clob in database: "+@queryVar);

      @stmtVar = @conVar.prepareStatement(@queryVar);
      @resetParams
      @setClobParam
      @setPrimaryKeyParams

      @stmtVar.executeUpdate();

@end rule: updateClob

@start rule: setBlobForInputBody

  private void @setBlobClobForInputMethodName(java.sql.ResultSet @rsVar, @getGeneratedBeanClassName @beanVar)
    throws IOException, java.sql.SQLException, ClassNotFoundException
  {
    // read Blob
    try {
      if (@debugEnabled)
        @debugSay(" read Blob for Column: @cmpColumnForCmpField");

      java.sql.Blob lob = @rsVar.getBlob("@cmpColumnForCmpField");

      if (lob == null) {
        if (@debugEnabled)
          @debugSay(" got NULL Blob, set result field to null.");

        @beanVar.@fieldNameForField = null;
      }
      else {
        int length = (int) lob.length();
        if (length == 0) {
          if (@debugEnabled)
            @debugSay(" got zero length Blob, set field to null");

          @beanVar.@fieldNameForField = null;
        }
        else {
          byte[] inByteArray = new byte[length];

          if (@debugEnabled)
            @debugSay(" got: "+length+" length Blob, now read data.");

          InputStream is = lob.getBinaryStream();
          is.read(inByteArray);
          is.close();

	  // CR112225
          @perhapsByteArrayIsDeserializedFromBlob
        }
      }
    } catch (IOException ioe) {
      if (@debugEnabled) {
        @debugSay("IOException for Blob/Clob" + ioe.getMessage());
      }
      throw ioe;
    } catch (java.sql.SQLException se) {
      if (@debugEnabled) {
        @debugSay("SQLException for Blob/Clob" + se.getMessage());
      }
      throw se;
    }
  }
@end rule: setBlobForInputBody

@start rule: setClobForInputBody

  private void @setBlobClobForInputMethodName(java.sql.ResultSet @rsVar, @getGeneratedBeanClassName @beanVar)
    throws IOException, java.sql.SQLException, ClassNotFoundException
  {
    // read Clob
    try {
      if (@debugEnabled)
        @debugSay(" read Clob for Column: @cmpColumnForCmpField");

      java.sql.Clob lob = @rsVar.getClob("@cmpColumnForCmpField");

      if (lob == null) {
        if (@debugEnabled)
          @debugSay(" got NULL Clob, set result field to null.");

        @beanVar.@fieldNameForField = null;
      }
      else {

        int length = (int) lob.length();
        if (length == 0) {
          if (@debugEnabled)
            @debugSay(" got zero length Clob, set field to zero length String.");

          @beanVar.@fieldNameForField = new String("");
        }
        else {
          char[] inCharArray = new char[length];

          if (@debugEnabled)
            @debugSay(" got: "+length+" length Clob, now read data.");

          Reader reader = lob.getCharacterStream();
          reader.read(inCharArray);
          reader.close();

          @beanVar.@fieldNameForField = new String(inCharArray);
        }
      }
    } catch (IOException ioe) {
      if (@debugEnabled) {
        @debugSay("IOException for Blob/Clob" + ioe.getMessage());
      }
      throw ioe;
    } catch (java.sql.SQLException se) {
      if (@debugEnabled) {
        @debugSay("SQLException for Blob/Clob" + se.getMessage());
      }
      throw se;
    }
  }
@end rule: setClobForInputBody


@start rule: createMethodDuplicateKeyCheck
      //ejb wants a duplicate key exception if that was what happened
      if(__WL_beanInserted) throw se;

      if(@debugEnabled) {
        @debugSay("@createMethodName() "+
           "checking for duplicate key " + @pkVar);
      }
      boolean exists = false;
      try {
        exists = @existsMethodName(@pkVar);
      }
      catch (Exception e) {
        throw se;
      }
      if (exists) {
        Loggable l = EJBLogger.logduplicateKeyFoundLoggable(@pkVar.toString());
        throw new javax.ejb.DuplicateKeyException(
             l.getMessage());
       }
      else {
        throw se;
      }
@end rule: createMethodDuplicateKeyCheck


@start rule: implementExistsMethodBody
    if (@debugEnabled) {
      @debugSay("exists: " + @keyVar);
    }

    java.sql.Connection @conVar = null;
    java.sql.PreparedStatement @stmtVar = null;
    java.sql.ResultSet @rsVar = null;
    try {
      @pk_class @pkVar = (@pk_class) @keyVar;
      @conVar = @pmVar.getConnection();

      java.lang.String @queryVar =
        "select @firstPrimaryKeyColumn from @curTableName where @idParamsSqlForCurTable";

      @stmtVar = @conVar.prepareStatement(@queryVar);

      @resetParams
      @setPrimaryKeyParams

      @rsVar = @stmtVar.executeQuery();
      if (@rsVar.next()) {
        return true;
      }
      else {
        return false;
      }
    @standardCatch
    finally {
      @pmVar.releaseResources(@conVar, @stmtVar, @rsVar);
    }
@end rule: implementExistsMethodBody


@start rule: implementGroupLoadMethodBody
    java.sql.Connection @conVar = null;
    java.sql.PreparedStatement @stmtVar = null;
    java.sql.ResultSet @rsVar = null;
    int @offsetVar = 0;

    int updateLockType = @pmVar.getUpdateLockType();

    java.lang.String @queryVar;

    // http://bugs.beasys.com/CRView?CR=CR070260
    //
    //  optimization.

    switch(updateLockType) {
      case DDConstants.UPDATE_LOCK_AS_GENERATED:
        @queryVar = "@groupSqlNonFinderPerhapsForUpdate";
        break;

      case DDConstants.UPDATE_LOCK_TX_LEVEL:
        @queryVar = "@groupSqlNonFinderForUpdate";
        break;

      case DDConstants.UPDATE_LOCK_TX_LEVEL_NO_WAIT:
        @queryVar = "@groupSqlNonFinderForUpdateNoWait";
        break;

      case DDConstants.UPDATE_LOCK_NONE:
        @queryVar = "@groupSqlNonFinder";
        break;

      default:
        throw new AssertionError(
        "Unknown updateLockType type: '"+updateLockType+"'");
    }

    @perhapsSuspendTransaction

    try {
      if (@debugEnabled) {
        @debugSay("@loadMethodNameForGroup for pk=" + @ctxVar.getPrimaryKey());
      }

      @pk_class @pkVar = (@pk_class) @ctxVar.getPrimaryKey();

      __WL_con = @pmVar.getConnection(@perhapsTransactionParam);


      @stmtVar = @conVar.prepareStatement(@queryVar);

      @resetParams
      @setPrimaryKeyParams

      if (@debugEnabled) {
        @debugSay("@loadMethodNameForGroup for pk=" + @ctxVar.getPrimaryKey() + 
                  ", executeQuery: "+@queryVar);
      }

      @rsVar = @stmtVar.executeQuery();
      if (@rsVar.next()) {

        if (@debugEnabled) {
          @debugSay("@loadMethodNameForGroup for pk=" + @ctxVar.getPrimaryKey() + 
                    " now read columns from Results.");
        }
     
        @preReadOnlyStateChange
        @perhapsUpdateLastLoadTime
        @assignGroupColumnsToThis
        @postReadOnlyStateChange

        @beanIsLoadedVar = true;
        @perhapsVerifyOptimistic
        @perhapsSetTableLoadedVarsForGroup
      } else {
        if(@debugEnabled) {
          @debugSay("@loadMethodNameForGroup could not find primary key " +
                    @pkVar);
        }
        Loggable l = EJBLogger.lognoSuchEntityExceptionLoggable(@pkVar.toString());
        throw new NoSuchEntityException(l.getMessage());
              
      }
    } finally {
      @pmVar.releaseResources(@conVar, @stmtVar, @rsVar);
    }

    @perhapsResumeTransaction
@end rule: implementGroupLoadMethodBody


@start rule: implementStoreTable

        @perhapsResetBlobClobCountVar
        @countVar = 0;

        @constructModifiedFieldStoreColumnStrings

        @totalVar = @totalVar + @countVar + @blobClobCountVarOrZero;

        if ( (@countVar > 0) || (@blobClobCountVarOrZero > 0) ) {

          if(@conVar == null) @conVar  = @pmVar.getConnection();

	  @perhapsReloadOptimisticColumn

          if (@countVar > 0 || @needsStoreOptimisticField) {
            // we have modified non-Blob/Clob Columns

            @perhapsUpdateOptimisticField
            @perhapsConstructSnapshotPredicate

            if (sb.length() > 0) {          
              @queryVar = "UPDATE @curTableName SET " +
                          sb.toString() +
                          " WHERE @idParamsSqlForCurTable" 
                          @perhapsAddSnapshotPredicate;
              if(@debugEnabled) {
                @debugSay("WL_store sql: " + @queryVar);
              }

              @currStmtArrayVar = @conVar.prepareStatement(@queryVar);

              __WL_setBeanParamsForUpdateArray(@stmtArrayVar, @isModifiedVar, @pkVar, @curTableIndex);

              int @iVar = @currStmtArrayVar.executeUpdate();
              if (@iVar == 0) {
                @throwOperationFailedException
              }
            }
          }

          if (@blobClobCountVarOrZero > 0) {
            // we have modified Blob/Clob Columns
            @setBlobClobForStore
          }
        }
@end rule: implementStoreTable


@start rule: implementWLStoreMethodBody
    @declareByteArrayVars
    java.sql.Connection @conVar = null;
    @declareStmtArrayVars
    int @oldStateVar = __WL_method_state;


    try {
      __WL_method_state = STATE_EJBSTORE;
      if (@debugEnabled) {
        @debugSay("ejbStore "+ @ctxVar.getPrimaryKey());
      }

      try {
        weblogic.ejb.container.internal.AllowedMethodsHelper.pushBean(this);
        super.ejbStore();
      } finally {
        weblogic.ejb.container.internal.AllowedMethodsHelper.popBean();
      }

      try {
        int @numVar          = 0;
        int @countVar        = 0;
        int @totalVar       = 0;
        @perhapsDeclareBlobClobCountVar
        @pk_class @pkVar     = (@pk_class) @ctxVar.getPrimaryKey();
        String @queryVar     = null;
        StringBuffer sb      = new StringBuffer();
        @perhapsCreateSnapshotBuffer

        @wlStoreToTables

        if (@totalVar == 0) {
          if (@debugEnabled) {
            @debugSay("ejbStore: avoided a store.  ejbStore: complete");
          }
          return;
        }
        @perhapsUpdateLastLoadTimeDueToEJBStore

        @perhapsTakeSnapshot
        for (int @iVar = 0; @iVar < @isModifiedVar.length; @iVar++) {
          if (@isModifiedVar[@iVar]) {
            @isModifiedVar[@iVar] = false;
            @isLoadedVar[@iVar] = true;
          }
        }

	__WL_perhapsReloadOptimisticColumn();

        if (unregister) {
          @unregisterModifiedBean
        }
        @modifiedBeanIsRegisteredVar = false;
        @perhapsResetTableModifiedVarsForBean

        if (@debugEnabled) {
          @debugSay("ejbStore: complete");
        }
      @standardCatch
    } 
    finally {
      __WL_method_state = @oldStateVar;
      @pmVar.releaseArrayResources(@conVar, @stmtArrayVar, null);
    }
@end rule: implementWLStoreMethodBody
    
    
@start rule: implementEjbRemoveMethodBody
    java.sql.Connection @conVar = null;
    @declareStmtArrayVars
    @declareQueryArrayVars
    @perhapsCreateSnapshotBuffer
    java.sql.PreparedStatement @stmtVar = null;

    int @oldStateVar = __WL_method_state;

    try {
      __WL_method_state = STATE_EJB_REMOVE;
      int @numVar = 0;
      if (@debugEnabled) {
        @debugSay("ejbRemove " + @ctxVar.getPrimaryKey());
      }

      try {
        weblogic.ejb.container.internal.AllowedMethodsHelper.pushBean(this);
        super.ejbRemove();
      } finally {
        weblogic.ejb.container.internal.AllowedMethodsHelper.popBean();
      }

      try {
        @removeBeanFromRelationships

        @pk_class @pkVar = (@pk_class) @ctxVar.getPrimaryKey();
        @registerInvalidationBean
        @perhapsSetTableModifiedVarsForBean
        @conVar = @pmVar.getConnection();

        @perhapsReloadOptimisticColumn

        @setRemoveQueryArray

        if (@debugEnabled) {
          for (int i = 0 ; i < @getTableCount ; i++) {
            @debugSay("ejbRemove() produced sqlString " + @queryArrayVar[i]);
          }
        }

        @prepareStmtArray

        __WL_setBeanParamsForDeleteArray(@stmtArrayVar, @isModifiedVar, @pkVar);

        for (int i = 0 ; i < @getTableCount ; i++) {
          int j = @stmtArrayVar[i].executeUpdate();
          if (j == 0) {
            @throwOperationFailedException
          }
        }

        // initialize state before this instance goes back into the pool
        __WL_initialize();

      @standardCatch
    } finally {
      __WL_method_state = @oldStateVar;
      @pmVar.releaseArrayResources(@conVar, @stmtArrayVar, null);
    }
@end rule: implementEjbRemoveMethodBody

@start rule: implementSuperEjbRemoveMethodBody
    int @oldStateVar = __WL_method_state;

    try {
      __WL_method_state = STATE_EJB_REMOVE;
      if (@debugEnabled) {
        @debugSay("__WL_superEjbRemove " + @ctxVar.getPrimaryKey());
      }

      try {
        weblogic.ejb.container.internal.AllowedMethodsHelper.pushBean(this);
        super.ejbRemove();
      } finally {
        weblogic.ejb.container.internal.AllowedMethodsHelper.popBean();
      }

      @removeBeanFromRelationships

      @registerInvalidationBean
      @perhapsSetTableModifiedVarsForBean
      if(initialize)
      {
        // initialize state before this instance goes back into the pool
        if (!__WL_getIsRemoved()) __WL_initialize();
      }

    } finally {
      __WL_method_state = @oldStateVar;
    }
@end rule: implementSuperEjbRemoveMethodBody

@start rule: oneToOneCascadeDel
    relBean = @getMethodNameForField();
    if (relBean !=null) {
      @beanVar = (javax.ejb.EntityBean)
        @bmVarForField.lookup(((@EJBObjectForField) relBean).getPrimaryKey());

        @listRelBeansVar.add(@beanVar);
    }
@end rule: oneToOneCascadeDel

@start rule: oneToManyCascadeDel
    relBeans = @getMethodNameForField();
    if (relBeans !=null) {
      Iterator iterVar = relBeans.iterator();

      while (iterVar.hasNext()) {
        @EJBObjectForField relEO = (@EJBObjectForField) iterVar.next();

        @beanVar = (javax.ejb.EntityBean)
          @bmVarForField.lookup(relEO.getPrimaryKey());

        @listRelBeansVar.add(@beanVar);
      }
    }
@end rule: oneToManyCascadeDel

@start rule: implementMakeCascadeDelListMethodBody_AddThisBean

    int @oldStateVar = __WL_method_state;
    __WL_method_state = STATE_EJB_REMOVE;

    // create a unique mapKey to map beans to mapCascadeDelBeans
    // re-use weblogic.ejb.container.cache.CacheKey class since it take two
    // objects to create the key
    Object pk = @ctxVar.getPrimaryKey();
    Object beanManager = @pmVar.getBeanManager();
    weblogic.ejb.container.cache.CacheKey mapKey =
      new weblogic.ejb.container.cache.CacheKey(pk, (CachingManager)beanManager);

    // step 1:
    // keep track of all of the beans been visited is the map to detect the circular case
    if (mapCascadeDelBeans.get(mapKey) == null) {
      mapCascadeDelBeans.put(mapKey, this);
    }
    else {
      if (@debugEnabled) {
        @debugSay("Trying to add " + mapKey + " to list during cascade delete, " +
          "but it already exists in the list, a possible circular detect, ignored.");
      }
      return;
    }

    // step 2:
    // This is the place every bean bean added to the list.
    // true  if don't need a database update, db-cascade-delete is specified
    // false if do need a database update, db-cascade-detelet isn't specified
    if (withoutDBUpdate) {
      listCascadeDelBeansWithoutDBUpdate.add(this);
    } else {
      listCascadeDelBeans.add(this);
    }

    __WL_method_state = @oldStateVar;
@end rule: implementMakeCascadeDelListMethodBody_AddThisBean

@start rule: implementMakeCascadeDelListMethodBody

    int @oldStateVar = __WL_method_state;
    __WL_method_state = STATE_EJB_REMOVE;

    // create a unique mapKey to map beans to mapCascadeDelBeans
    // re-use weblogic.ejb.container.cache.CacheKey class since it take two
    // objects to create the key
    Object pk = @ctxVar.getPrimaryKey();
    Object beanManager = @pmVar.getBeanManager();
    weblogic.ejb.container.cache.CacheKey mapKey =
      new weblogic.ejb.container.cache.CacheKey(pk, (CachingManager)beanManager);

    // step 1:
    // keep track of all of the beans been visited is the map to detect the circular case
    if (mapCascadeDelBeans.get(mapKey) == null) {
      mapCascadeDelBeans.put(mapKey, this);
    }
    else {
      if (@debugEnabled) {
        @debugSay("Trying to add " + mapKey + " to list during cascade delete, " +
          "but it already exists in the list, a possible circular detect, ignored.");
      }
      return;
    }


    // step 2:
    // find all of the related beans
    // 1.  listRelBeans is list of the beans DO NOT have db-cascade-delete specified,
    //     they WILL need to do the database update.
    // 2.  listRelBeans_WithoutDBUpdate is list of the beans DO have db-cascade-delete specified,
    //     they WILL NOT need to do the database update.  This assume that "on detele cascade"
    //     is setup for the database table.
    List listRelBeans = null;
    List listRelBeans_RootBeanNotFKOwner = null;
    List listRelBeans_RootBeanFKOwner    = null;
    List listRelBeans_EachOtherFKOwner   = null;
    List listRelBeans_WithoutDBUpdate = null;
    List listRelBeans_WithoutDBUpdate_RootBeanNotFKOwner = null;
    List listRelBeans_WithoutDBUpdate_RootBeanFKOwner    = null;
    List listRelBeans_WithoutDBUpdate_EachOtherFKOwner   = null;
    Object relBean = null;
    java.util.Collection relBeans = null;
    javax.ejb.EntityBean @beanVar = null;

    @perhapsLoadDefaultGroup

    // get1N11_RelBeans_RootBeanNotHaveFK
    listRelBeans = new ArrayList();
    listRelBeans_WithoutDBUpdate = new ArrayList();
    @get1N11_RelBeans_RootBeanNotFKOwner
    listRelBeans_RootBeanNotFKOwner = listRelBeans;
    listRelBeans_WithoutDBUpdate_RootBeanNotFKOwner = listRelBeans_WithoutDBUpdate;

    // get11_RelBeans_RootBeanHaveFK
    listRelBeans = new ArrayList();
    listRelBeans_WithoutDBUpdate = new ArrayList();
    @get11_RelBeans_RootBeanFKOwner
    listRelBeans_RootBeanFKOwner = listRelBeans;
    listRelBeans_WithoutDBUpdate_RootBeanFKOwner = listRelBeans_WithoutDBUpdate;

    // get11_RelBeans_HaveEachOtherFK
    listRelBeans = new ArrayList();
    listRelBeans_WithoutDBUpdate = new ArrayList();
    @get11_RelBeans_EachOtherFKOwner
    listRelBeans_EachOtherFKOwner = listRelBeans;
    listRelBeans_WithoutDBUpdate_EachOtherFKOwner = listRelBeans_WithoutDBUpdate;


    // step 3:
    // add all of the related beans to the cascade delete bean list based on
    // the FK owner.
    // The order is:
    // 1.  List of beans the root bean don't have the FK
    // 2.  the root bean
    // 3.  List of the beans the root bean has the FK
    // 4.  List of the beans have each other's FK with the root bean
    Iterator iterRelBeans = null;

    iterRelBeans = listRelBeans_RootBeanNotFKOwner.iterator();
    while (iterRelBeans.hasNext()) {
      ((CMPBean) iterRelBeans.next()).__WL_makeCascadeDelList(mapCascadeDelBeans,
                                                              listCascadeDelBeans,
                                                              listCascadeDelBeansWithoutDBUpdate,
                                                              false);
    }
    iterRelBeans = listRelBeans_WithoutDBUpdate_RootBeanNotFKOwner.iterator();
    while (iterRelBeans.hasNext()) {
      ((CMPBean) iterRelBeans.next()).__WL_makeCascadeDelList(mapCascadeDelBeans,
                                                              listCascadeDelBeans,
                                                              listCascadeDelBeansWithoutDBUpdate,
                                                              true);
    }

    // This is the place every bean bean added to the list.
    // true  if don't need a database update, db-cascade-delete is specified
    // false if do need a database update, db-cascade-detelet isn't specified
    if (withoutDBUpdate) {
      listCascadeDelBeansWithoutDBUpdate.add(this);
    } else {
      listCascadeDelBeans.add(this);
    }

    iterRelBeans = listRelBeans_RootBeanFKOwner.iterator();
    while (iterRelBeans.hasNext()) {
      ((CMPBean) iterRelBeans.next()).__WL_makeCascadeDelList(mapCascadeDelBeans,
                                                              listCascadeDelBeans,
                                                              listCascadeDelBeansWithoutDBUpdate,
                                                              false);
    }
    iterRelBeans = listRelBeans_WithoutDBUpdate_RootBeanFKOwner.iterator();
    while (iterRelBeans.hasNext()) {
      ((CMPBean) iterRelBeans.next()).__WL_makeCascadeDelList(mapCascadeDelBeans,
                                                              listCascadeDelBeans,
                                                              listCascadeDelBeansWithoutDBUpdate,
                                                              true);
    }

    iterRelBeans = listRelBeans_EachOtherFKOwner.iterator();
    while (iterRelBeans.hasNext()) {
      ((CMPBean) iterRelBeans.next()).__WL_makeCascadeDelList(mapCascadeDelBeans,
                                                              listCascadeDelBeans,
                                                              listCascadeDelBeansWithoutDBUpdate,
                                                              false);
    }
    iterRelBeans = listRelBeans_WithoutDBUpdate_EachOtherFKOwner.iterator();
    while (iterRelBeans.hasNext()) {
      ((CMPBean) iterRelBeans.next()).__WL_makeCascadeDelList(mapCascadeDelBeans,
                                                              listCascadeDelBeans,
                                                              listCascadeDelBeansWithoutDBUpdate,
                                                              true);
    }

    __WL_method_state = @oldStateVar;
@end rule: implementMakeCascadeDelListMethodBody


@start rule: implementEjbStoreMethodBody
    __WL_store(true);
@end rule: implementEjbStoreMethodBody


@start rule: standardCatch
    } catch (RuntimeException re) {
      if (@debugEnabled) {
        @debugSay("rethrowing RuntimeException.");
        re.printStackTrace();
      }
      throw re;
    } catch (Exception ex) {
      if (@debugEnabled) {
        @debugSay("wrapping Exception in PersistenceRuntimeException.");
        ex.printStackTrace();
      }
      throw new PersistenceRuntimeException(ex);
    }
@end rule: standardCatch

@start rule: selectCatch
    } catch (RuntimeException re) {
      if (@debugEnabled) {
        @debugSay("rethrowing RuntimeException.");
        re.printStackTrace();
      }
      throw re;
    } catch (Exception ex) {
      if (@debugEnabled) {
        @debugSay("wrapping Exception in PersistenceRuntimeException.");
        ex.printStackTrace();
      }
      if(ex instanceof weblogic.ejb.container.InternalException)
      {
         weblogic.ejb.container.InternalException ie = (weblogic.ejb.container.InternalException)ex;
         if((ie.detail != null) && (javax.ejb.FinderException.class.isAssignableFrom(ie.detail.getClass())))
         {
            throw (javax.ejb.FinderException)(ie.detail);
          }
         else
        throw new PersistenceRuntimeException(ex);
     }
      else
        throw new PersistenceRuntimeException(ex);
    }
@end rule: selectCatch

@start rule: ejbPostCreateMethod
    int @oldStateVar = __WL_method_state;
    try {
      weblogic.ejb.container.internal.AllowedMethodsHelper.pushBean(this);
      __WL_method_state = @method_state;

      @addBeanToRelationships

      super.@method_name(@method_parameters_without_types);

      @perhapsInsertBean

      @perhapsCatchCreateException

      @standardCatch
    finally {
      __WL_method_state = @oldStateVar;
      weblogic.ejb.container.internal.AllowedMethodsHelper.popBean();
    }
@end rule: ejbPostCreateMethod


@start rule: ejbCreateMethodBody
    int @oldStateVar = __WL_method_state;
    try {
      weblogic.ejb.container.internal.AllowedMethodsHelper.pushBean(this);
      __WL_method_state = @method_state;

      for (int @iVar = 0; @iVar < @allFieldsCount; @iVar++) {
        @isLoadedVar[@iVar] = true;
        @isModifiedVar[@iVar] = false;
      }

      @beanIsLoadedVar = true;
      @perhapsSetTableLoadedVarsForBean

      // set true, this prevents us from registering the bean until after the
      // database insert
      @modifiedBeanIsRegisteredVar = true;
      @assignCmrIsLoadedFalse

      // initialize persistent and cached relationship variables, this
      // is done here purely to ensure robustness as the CMP variables
      // are also initialized whenever the bean enters the pooled state
      @initializePersistentVars
      @initializeRelationVars

      super.@method_name(@method_parameters_without_types);

      @perhapsCallPkCheck

      @perhapsGenKeyBeforeInsert

      @returnPkPerhapsInsertBean
    finally {
      __WL_method_state = @oldStateVar;
      weblogic.ejb.container.internal.AllowedMethodsHelper.popBean();
    }
@end rule: ejbCreateMethodBody


@start rule: emptyEjbCreateMethod
@method_signature_no_throws
    @beanmethod_throws_clause
  {
     
  }
@end rule: emptyEjbCreateMethod


@start rule: registerModifiedBean
    if (! @modifiedBeanIsRegisteredVar) {
      @pmVar.registerModifiedBean(((EntityEJBContextImpl)@ctxVar).__WL_getPrimaryKey());
      @modifiedBeanIsRegisteredVar = true;
    }
@end rule: registerModifiedBean

@start rule: registerInvalidatedBean
    if (! @invalidatedBeanIsRegisteredVar) {
      @pmVar.registerInvalidatedBean(@ctxVar.getPrimaryKey());
      @invalidatedBeanIsRegisteredVar = true;
    }
@end rule: registerInvalidatedBean


@start rule: unregisterModifiedBean
    @pmVar.unregisterModifiedBean(@ctxVar.getPrimaryKey());
@end rule: unregisterModifiedBean

@start rule: home_method
@method_signature_no_throws
    @beanmethod_throws_clause
  {
    int @oldStateVar = __WL_method_state;

    try {
      weblogic.ejb.container.internal.AllowedMethodsHelper.pushBean(this);
      __WL_method_state = @method_state;

      @declare_result

      @result super.@method_name(@method_parameters_without_types);

      @return_result
    } finally {
      __WL_method_state = @oldStateVar;
      weblogic.ejb.container.internal.AllowedMethodsHelper.popBean();
    }
  }
@end rule: home_method

@start rule: declarePkCheckMethod
  void pkCheck()
    throws javax.ejb.CreateException
  {
    @implementPkCheckMethodBody
  }
@end rule: declarePkCheckMethod

@start rule: checkExistsOnMethodBody
  if (__WL_getIsRemoved()) {
    Loggable l = EJBLogger.lognoSuchEntityExceptionLoggable(
      ((EntityEJBContextImpl)@ctxVar).__WL_getPrimaryKey().toString());
    NoSuchEntityException nsee = new NoSuchEntityException(l.getMessage());
    if (@debugEnabled) {
      @debugSay("throwing RuntimeException.");
      nsee.printStackTrace();
    }
    throw nsee;
  }

  if (__WL_beanIsLoaded()) return;

  int @oldStateVar = __WL_method_state;
  
  try {
    __WL_method_state = WLEnterpriseBean.STATE_BUSINESS_METHOD;
    @pmVar.disableTransactionStatusCheck();

    @loadMethodNameForGroup();
  @standardCatch
  finally {
    __WL_method_state = @oldStateVar;
    @pmVar.enableTransactionStatusCheck();
  }
@end rule: checkExistsOnMethodBody

@start rule: appendVerifySqlForTable
    // for each table
    if (@tableLoadedVar && !@tableModifiedVar) {
      
      if (verifyCount[@indexForTable]>0) {
        verifySql[@indexForTable].append(" OR ");
      }

      @verifySqlForTable

      // update count for this table
      verifyCount[@indexForTable]++;
      if (verifyCount[@indexForTable]>verifyMax)
        verifyMax = verifyCount[@indexForTable];

      // add to pk set
      verifyPk.add(@ctxVar.getPrimaryKey());
    }
@end rule: appendVerifySqlForTable

@start rule: appendVerifySqlForTableForBatch
    // for each table
    if ((@tableLoadedVar && @tableModifiedVar) @perhapsOptFieldCheckForBatch) {

      if (verifyCount[@indexForTable]>0) {
        verifySql[@indexForTable].append(" OR ");
      }

      @verifySqlForTable

      // update count for this table
      verifyCount[@indexForTable]++;
      if (verifyCount[@indexForTable]>verifyMax)
        verifyMax = verifyCount[@indexForTable];

      // add to pk set
      verifyPk.add(@ctxVar.getPrimaryKey());
    }
@end rule: appendVerifySqlForTableForBatch

@start rule: checkNullForAggregateQueries
        if (@rsVar.wasNull()) {
          if(@debugEnabled) {
            @debugSay("Throwing FinderException because of null result.");
          }
          Loggable l = EJBLogger.logNoResultsForAggregateQueryLoggable("@finderMethodName");
          throw new javax.ejb.ObjectNotFoundException(l.getMessage());
        }
@end rule: checkNullForAggregateQueries

@start rule: bean_state_timeout_check
   long lastLoadTime = __WL_lastLoadTime.get();
   if(lastLoadTime != 0) {
     if(__WL_readTimeoutMS == 0 ||
        System.currentTimeMillis() - lastLoadTime < __WL_readTimeoutMS)
      return true;
   }
   return false;
@end rule: bean_state_timeout_check

@start rule: java_class_relation_disconnect_check
  if (__WL_isConnected() == false) {
    throw new RuntimeException("You may not access related beans "+
      "when in a disconnected state.");
  }
@end rule: java_class_relation_disconnect_check
