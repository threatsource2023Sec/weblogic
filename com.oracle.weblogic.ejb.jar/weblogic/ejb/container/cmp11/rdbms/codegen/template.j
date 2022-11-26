//depot/dev/src/weblogic/ejb/ejbc/jdbc.j#33 - edit change 42059 (text)

@start rule: main
/**
 * This code was automatically generated at @time on @date
 * by @generator -- do not edit.
 *
 * @@version @buildString
 * @@author Copyright (c) @year by BEA Systems, Inc. All Rights Reserved.
 */

@packageStatement

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectOutputStream;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.EntityContext;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.NoSuchEntityException;
import javax.ejb.EJBException;

import weblogic.ejb.container.internal.EntityEJBContextImpl;

import weblogic.ejb.container.persistence.RSInfoImpl;
import weblogic.ejb.container.persistence.spi.PersistenceManager;
import weblogic.ejb.container.persistence.spi.CMPBean;
import weblogic.ejb.container.persistence.spi.CMPBeanManager;
import weblogic.ejb.container.persistence.spi.RSInfo;

import weblogic.ejb.container.internal.QueryCachingHandler;

import weblogic.ejb.container.cache.QueryCacheKey;

import weblogic.ejb.container.cmp.rdbms.RDBMSObjectInputStream;

import weblogic.ejb.container.cmp11.rdbms.PersistenceManagerImpl;
import weblogic.ejb.container.cmp11.rdbms.RDBMSUtils;
import weblogic.ejb.container.dd.DDConstants;

import weblogic.ejb20.persistence.spi.PersistenceRuntimeException;

import weblogic.utils.Debug;
import weblogic.ejb.container.EJBLogger;
import weblogic.logging.Loggable;

public final class @cmpBeanClassName extends @ejb_class_name 
  implements CMPBean, @simple_beanimpl_interface_name
{

  private static final weblogic.diagnostics.debug.DebugLogger @debugVar =
    weblogic.ejb.container.EJBDebugService.cmpRuntimeLogger;

  private static String EOL = System.getProperty("line.separator");
  private static boolean __WL_updateable = @beanIsUpdateable;

  // Snapshot Code to implement Tuned Writes
  private boolean __WL_snapshots_enabled = @snapshots_enabled;

  private boolean __WL_modified[] = new boolean [@modified_array_count];

  /**
   *  EJB3+ beans may be connected or disconnected
   *  from their datasources.
   */
  private boolean __WL_isConnected = true;

  @declareBeanStateVar

  // Snapshot variables
  @declare_snapshot_variables

  private boolean __WL_operationsComplete = false;

  // This method is called by ejbLoad, ejbPassivate, and ejbRemove
  // to initialize the persistent state of the bean and its associated
  // variables.
  public void __WL_initialize() 
  {
    __WL_initialize(true);
  }

  public void __WL_initialize(boolean __WL_initSnapshotVars)
  {
    // initialize persistent state.
    @initializePersistentVars
  }

  private void __WL_print_byte_array(byte [] b) {

    if (b == null) {
      @debugSay("** NULL **");
    } else {
      @debugSay("Length: "+b.length);
      for (int i=0; i<b.length; i++) {
        @debugSay("** b["+i+"] = "+b[i]);
      }
    }

  }

  // Clear snapshot variables on passivation to free any associated
  // memory

  private void __WL_clear_snapshot_variables() {

    @clear_snapshot_variables

  }

  private void __WL_take_snapshot() {

    for (int i=0; i<__WL_modified.length; i++) {
      __WL_modified[i] = false;
    }

    @take_snapshot_variables

  }

  private String __WL_determineSetString() {

    if (! __WL_snapshots_enabled) return "@updateBeanColumnsSql";

    boolean nothingModified = true;

    boolean needsComma = false;

    StringBuffer sb = null;


    @determineSetString

        
    if (nothingModified) {
      return null;
    } else {
      return sb.toString();
    }

  }


  private byte [] __WL_snapshot_byte_array(byte [] a) {
 
    if (a == null) return null;

    byte [] b = new byte[a.length];

    System.arraycopy(a,0,b,0,a.length);

    return b;
  }


  private javax.ejb.EJBContext __WL_EJBContext;

  private int __WL_method_state;

  private boolean __WL_isRemoved = false;

  private boolean __WL_busy = false;

  private boolean __WL_isLocal = true;

  private boolean  __WL_needsRemove;

  private Object __WL_loadUser;

  private boolean __WL_creatorOfTx;

  public boolean __WL_isBusy() { return __WL_busy; }
  public void __WL_setBusy(boolean b) { __WL_busy = b; }

  public boolean __WL_getIsLocal() { return __WL_isLocal; }
  public void __WL_setIsLocal(boolean b) { __WL_isLocal = b; }

  public int __WL_getMethodState() { return __WL_method_state; }
  public void __WL_setMethodState(int state) { __WL_method_state = state; }


  public boolean __WL_needsRemove() { return __WL_needsRemove; }
  public void __WL_setNeedsRemove(boolean b) { __WL_needsRemove = b; }

  public void __WL_setCreatorOfTx (boolean b) { __WL_creatorOfTx = b; }
  public boolean __WL_isCreatorOfTx() { return __WL_creatorOfTx; }

  public javax.ejb.EJBContext __WL_getEJBContext() { return __WL_EJBContext; }
  public void __WL_setEJBContext(javax.ejb.EJBContext ctx) { 
    __WL_EJBContext = ctx; 
  }

  public void __WL_setLoadUser(Object o) { __WL_loadUser = o;}
  public Object __WL_getLoadUser() { return __WL_loadUser; } 

  public boolean __WL_isConnected() {
    return __WL_isConnected;
  }

  public void __WL_setConnected(boolean b) {
    __WL_isConnected = b;
  }

  // =================================================================
  // Instance variable(s)
  @declareEntityContextVar


  // =================================================================
  // Constructor.
  @declareNoArgsConstructor {
    super();
    __WL_initialize();
  }


  // =================================================================
  // the CMP fields.

  @declareIsModified

  private PersistenceManagerImpl @pmVar = null;


  // =================================================================
  // Implementation of CMPBean
  public void __WL_setup(java.util.Map bmMap, PersistenceManager pm) {
	  this.@pmVar = (PersistenceManagerImpl)pm;
  }

  public Object __WL_getPrimaryKey() {
    @implementGetPrimaryKey
  }

  public void __WL_setPrimaryKey(@pk_class @pkVar) {
    @implementSetPrimaryKey
  }

  public EntityContext __WL_getEntityContext() {
    return @ctxVar;
  }

  public void __WL_store(boolean unregister) {}

  public void __WL_superEjbLoad() @ejbLoadExceptionList {
    int oldState = __WL_method_state;

    try {
      weblogic.ejb.container.internal.AllowedMethodsHelper.pushBean(this);
      __WL_method_state = STATE_EJBLOAD;
      super.ejbLoad();
          
      if (__WL_snapshots_enabled) __WL_take_snapshot();

    } finally {
      __WL_method_state = oldState;
      weblogic.ejb.container.internal.AllowedMethodsHelper.popBean();
    }
  }

  public void __WL_superEjbStore() @ejbStoreExceptionList {
    int oldState = __WL_method_state;

    try {
      weblogic.ejb.container.internal.AllowedMethodsHelper.pushBean(this);
      __WL_method_state = STATE_EJBSTORE;
      if (@debugEnabled) {
        @debugSay("__WL_superEjbStore "+ @ctxVar.getPrimaryKey());
      }
      super.ejbStore();
    } finally {
      __WL_method_state = oldState;
      weblogic.ejb.container.internal.AllowedMethodsHelper.popBean();
    }
  }

  public void __WL_superEjbRemove(boolean initialize)
    throws javax.ejb.RemoveException {
  }

  public void __WL_copyFrom(CMPBean otherBean, boolean __WL_initSnapshotVars) {
    @copyFromMethodBody
  }
  

  public void __WL_loadGroupByIndex(int index, java.sql.ResultSet @rsVar,
    Integer offset, Object @pkVar, javax.ejb.EntityBean @beanVar) throws Exception
  {
    __WL_loadFromRS(@rsVar, @pkVar, (@getGeneratedBeanClassName) @beanVar);
  }

  public void __WL_loadFromRS(java.sql.ResultSet @rsVar, Object @pkVar,
    @getGeneratedBeanClassName @beanVar) throws Exception
  {
    @assignResultVar
  }

  public void __WL_loadCMRFieldByCmrField(String cmrField, java.sql.ResultSet rs,
     Integer offset, javax.ejb.EntityBean eb) throws Exception
  {
    // not used for CMP11 bean
  }

  public PersistenceManager __WL_getPersistenceManager() {
    return null;
  }

  public void __WL_makeCascadeDelList(Map mapCascadeDelBeans,
                                      List listCascadeDelBeans,
                                      List listCascadeDelBeansWithoutDBUpdate,
                                      boolean withoutDBUpdate)
    throws java.lang.Exception
  {
    listCascadeDelBeans.add(this);
  }

  public boolean[] __WL_getIsModifiedUnion(boolean[] isModified)
  {
    return null;
  }

  public java.sql.PreparedStatement[] __WL_getStmtArray(
    java.sql.Connection conn,
    boolean[] isModifiedVar,
    int operation,
    boolean woFkCols)
    throws SQLException
  {
    return null;
  }

  public void __WL_setBeanParamsForStmtArray(
    java.sql.PreparedStatement[] __WL_stmt_array,
    boolean[] isModifiedVar,
    int operation,
    boolean woFkCols)
    throws Exception
  {
  }

  public void __WL_addSelfRelatedBeansToInsertStmt(PreparedStatement[] stmtArray,
                                                   List finishedKeys,
                                                   boolean internalFlush)
    throws Exception
  {
  }

  public void __WL_addSelfRelatedBeansToDeleteStmt(PreparedStatement[] stmtArray,
                                                   List finishedKeys,
                                                   boolean[] isModifiedUnion,
                                                   boolean internalFlush)
    throws Exception
  {
  }

  public void __WL_resetIsModifiedVars(java.sql.Connection conn,
                                       boolean woFkCols)
    throws Exception
  {
  }

  public void __WL_resetIsModifiedVars(int operation,
                                     java.sql.Connection conn,
                                     boolean woFkCols)
    throws Exception
  {
  }

  public void __WL_perhapsTakeSnapshot()
  {
  }

  public Collection __WL_getNullSnapshotVariables() {
    throw new AssertionError(
      "Optimistic concurrency is not supported for 1.1 CMP");
  }
	
  public void __WL_perhapsReloadOptimisticColumn() throws Exception
  {
  }

  public void __WL_checkExistsOnMethod() throws NoSuchEntityException
  {
  }

  public void __WL_doCheckExistsOnMethod() throws NoSuchEntityException
  {
    @doCheckExistsOnMethodBody
  }

  public boolean __WL_isModifiedOrLoaded() 
  {
    throw new AssertionError(
      "__WL_isModifiedOrLoaded should not be called for a 1.1 CMP bean."); 
  }

  public boolean @existsMethodName(Object @keyVar) {
    @implementExistsMethodBody
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

  public void __WL_setCreateAfterRemove(boolean createAfterRemove) 
  {
  }
  public void __WL_setInvalidatedBeanIsRegistered(boolean invalidationFlag) {
  }
  public int __WL_appendVerifySqlForBatch(java.util.List verifyPk, 
                                          StringBuffer[] verifySql, 
                                          int[] verifyCount,
                                          int verifyMax)
  {
    throw new AssertionError(
      "__WL_appendVerifySqlForBatch should not be called on a 1.1 CMP bean.");
  }

  public void __WL_setVerifyParamsForBatch(java.sql.Connection con,
                                           java.sql.PreparedStatement[] verifyStmt, 
                                           int[] verifyCount)
  {
    throw new AssertionError(
      "__WL_setVerifyParamsForBatch should not be called on a 1.1 CMP bean.");
  }

  public void __WL_loadBeansRelatedToCachingName(String cachingName, 
                                                 java.sql.ResultSet rs,
                                                 CMPBean bean, int groupColumnCount, QueryCachingHandler qch)
    throws Exception
  {
    throw new AssertionError(
      "__WL_loadBeansRelatedToCachingName should not be called on a 1.1 CMP bean.");
  }

  public int __WL_appendVerifySql(java.util.List verifyPk, 
                             StringBuffer[] verifySql, 
                             int[] verifyCount,
                             int verifyMax)
  {
    throw new AssertionError(
      "appendVerifySql should not be called on a 1.1 CMP bean.");
  }

  public void __WL_setVerifyParams(java.sql.Connection con,
                              java.sql.PreparedStatement[] verifyStmt, 
                              int[] verifyCount)
  {
    throw new AssertionError(
      "setVerifyParams should not be called on a 1.1 CMP bean.");
  }

  /**
   *   Used in EJB2.0 Many To Many relationships only
   */
  public String __WL_getM2NSQL(String cmrf, int operation)
  {
    return "";    
  }

  /**
   *   Used in EJB2.0 Many To Many relationships only
   */
  public Collection __WL_getCmrBeansForCmrField(String cmrf)
  {
    return null;
  }

  public void __WL_setOperationsComplete(boolean b) {
    __WL_operationsComplete = b;
  }

  public boolean __WL_getOperationsComplete() {
    return __WL_operationsComplete;
  }


  /**
   *  nonFKHolderRelationChange is meaningful for CMP20+ beans only.
   */
  public void __WL_setNonFKHolderRelationChange(boolean b) { }

  public boolean __WL_getNonFKHolderRelationChange() { return false; }


  /**
   *  deferred m2N Join Table inserts is meaningful for CMP20+ beans only.
   */
  public void __WL_setM2NInsert(boolean b) { }

  public boolean __WL_getM2NInsert() { return false; }

  public void __WL_clearCMRFields() {
    throw new AssertionError("Should never be called for EJB 1.1 beans");
  }

  public void __WL_setLoaded(int fieldIndex, boolean value) {
  }

  // This method is used by finders to cache related beans; unimplemented
  // for EJB 1.1 beans 
  public void  __WL_putCmrFieldInQueryCache(String cmrField, 
                                            QueryCacheKey source) {
  }

  public boolean __WL_isModified() {
    throw new AssertionError("Should never be called for EJB 1.1 beans");
  }
  
  // end of CMPBean
  // ================================================================




  //=================================================================
  //Finder methods.

  @implementFinderMethods

  @implementGetPKFromRSStaticMethod

  public Object __WL_getPKFromRSInstance(java.sql.ResultSet @rsVar, java.lang.Integer offset, ClassLoader cl)
    throws java.sql.SQLException, java.lang.Exception
  {
    return @getPKFromRSMethodName(@rsVar);
  }

  //End finder methods.
  //=================================================================

  //=================================================================
  //Home methods.
  @home_methods

  //End home methods.
  //=================================================================

  //=================================================================
  // implementation of javax.ejb.EntityBean

  @bean_postcreate_methods

  public void ejbActivate() 
    @activate_throws_clause
  {
    int oldState = __WL_method_state;
    try {
      weblogic.ejb.container.internal.AllowedMethodsHelper.pushBean(this);
      __WL_method_state = STATE_EJB_ACTIVATE;
      super.ejbActivate();
    } finally {
      __WL_method_state = oldState;
      weblogic.ejb.container.internal.AllowedMethodsHelper.popBean();
    }
  }

  public void ejbPassivate() 
    @passivate_throws_clause
  {
    int oldState = __WL_method_state;
    try {
      weblogic.ejb.container.internal.AllowedMethodsHelper.pushBean(this);
      __WL_method_state = STATE_EJB_PASSIVATE;
      super.ejbPassivate();
      __WL_initialize();
      __WL_clear_snapshot_variables();
    } finally {
      __WL_method_state = oldState;
      weblogic.ejb.container.internal.AllowedMethodsHelper.popBean();
    }
  }


  @declareSetEntityContextMethod {

    int oldState = __WL_method_state;
    
    try {
      weblogic.ejb.container.internal.AllowedMethodsHelper.pushBean(this);
      __WL_method_state = STATE_SET_CONTEXT;
      super.setEntityContext(arg0);
      this.@ctxVar = arg0;
    } finally {
      __WL_method_state = oldState;
      weblogic.ejb.container.internal.AllowedMethodsHelper.popBean();
    }
  }

  public void unsetEntityContext()
    @unsetentitycontext_throws_clause
  {
    int oldState = __WL_method_state;
    try {
      weblogic.ejb.container.internal.AllowedMethodsHelper.pushBean(this);
      __WL_method_state = STATE_UNSET_CONTEXT;
      super.unsetEntityContext();
    } finally {
      __WL_method_state = oldState;
      weblogic.ejb.container.internal.AllowedMethodsHelper.popBean();
    }
  }


  @implementEjbCreateMethods

  private Object @createMethodName() throws Exception {
    @implementCreateMethodBody
  }

  @implementEjbRemoveMethod

  @declareEjbLoadMethod {
    int oldState = __WL_method_state;

    try {
      weblogic.ejb.container.internal.AllowedMethodsHelper.pushBean(this);
      __WL_method_state = STATE_EJBLOAD;
      __WL_initialize();

      try {
      @implementLoadMethodBody
      @perhapsSetLastLoadTime
    @standardCatch

      super.ejbLoad();

      if (__WL_snapshots_enabled) __WL_take_snapshot();


    } finally {
      __WL_method_state = oldState;
      weblogic.ejb.container.internal.AllowedMethodsHelper.popBean();
    }
  }

  @implementEjbStoreMethod

  // end javax.ejb.EntityBean
  //=================================================================

  public Object _WL__getCategoryValue() {
    throw new AssertionError("Categories not supported for CMP 1.1 beans!");
  }

  private static void @debugSay(String s) {
    @debugVar.debug("[@getSimpleBeanClassName] " + s);
  }

  private static void @debugSay(String s, Throwable th) {
    @debugVar.debug("[@getSimpleBeanClassName] " + s, th);
  }

}

@end rule: main


@start rule: finderMethodBodyScalar
		if(@debugEnabled) {
			@debugSay("@cmpBeanClassName.@finderMethodName");   
		}

    java.sql.Connection __WL_con = null;
    java.sql.PreparedStatement @stmtVar = null;
    java.sql.ResultSet @rsVar = null;

    try {
      __WL_con = @pmVar.getConnection();
    } catch (java.lang.Exception e) {
      @pmVar.releaseResources(__WL_con, @stmtVar, @rsVar);
      throw new javax.ejb.FinderException("Couldn't get connection: " + 
        RDBMSUtils.throwable2StackTrace(e));
    }

    String selectForUpdate = @pmVar.selectForUpdate();

    java.lang.String @queryVar =
      "SELECT @finderColumnsSql FROM @tableName @finderQuery" + selectForUpdate;
    
    if(@debugEnabled) {
      @debugSay("Scalar Finder produced statement string "
        + @queryVar);
    }
    
    @declareResultVar
    try {
      @stmtVar = @conVar.prepareStatement(@queryVar);
      @resetParams
      @setFinderQueryParams
      @rsVar = @stmtVar.executeQuery();

      if (@rsVar.next()) {
        @getPkVarFromRS
        @allocateResultVar
        @finderGetEo
      } else {
        if(@debugEnabled) {
          @debugSay("Throwing FinderException because " +
            "bean wasn't found.");
        }
        Loggable l = EJBLogger.logbeanNotFoundLoggable("@finderMethodName");
        throw new javax.ejb.ObjectNotFoundException(l.getMessage());
      }   
      if (@rsVar.next()) {
        Loggable l = EJBLogger.logfinderReturnedMultipleValuesLoggable("@finderMethodName");
        throw new javax.ejb.FinderException(l.getMessageText());
      }

      return @resultVar;
  } catch (javax.ejb.FinderException fe) {
    throw fe;
  } catch (java.sql.SQLException sqle) {
    throw new javax.ejb.FinderException(
      "Problem in @finderMethodName while preparing or executing " +
      "statement: '" + 
		  @stmtVar + "': " + EOL +
      RDBMSUtils.throwable2StackTrace(sqle));
  } catch (Exception e) {
    throw new javax.ejb.FinderException(
      "Exception raised in @finderMethodName " + EOL + 
      RDBMSUtils.throwable2StackTrace(e));
  } finally {
		@pmVar.releaseResources(@conVar, @stmtVar, @rsVar);
  }
@end rule: finderMethodBodyScalar

@start rule: finderMethodBodyMulti
		if(@debugEnabled) {
			@debugSay("@cmpBeanClassName.@finderMethodName");
		}

    java.sql.Connection __WL_con = null;
    java.sql.PreparedStatement @stmtVar = null;
    java.sql.ResultSet @rsVar = null;

    try {
      __WL_con = @pmVar.getConnection();
    } catch (java.lang.Exception e) {
		  @pmVar.releaseResources(__WL_con, @stmtVar, @rsVar);
      throw new javax.ejb.FinderException("Couldn't get connection: " + 
        RDBMSUtils.throwable2StackTrace(e));
    }

    String selectForUpdate = @pmVar.selectForUpdate();

    java.lang.String @queryVar =
      "SELECT @finderColumnsSql FROM @tableName @finderQuery" + selectForUpdate;

    if(@debugEnabled) {
      @debugSay("Multi Finder produced statement string "
        + @queryVar);
    }

    try { 
      @stmtVar = __WL_con.prepareStatement(@queryVar);
      @resetParams
      @setFinderQueryParams
      @rsVar = @stmtVar.executeQuery();
    } catch (java.sql.SQLException sqle) {
		  @pmVar.releaseResources(__WL_con, @stmtVar, @rsVar);
      throw new javax.ejb.FinderException(
        "Exception in @finderMethodName while preparing or executing " +
        "statement: '" + @stmtVar + "'" + EOL +
        RDBMSUtils.throwable2StackTrace(sqle));
    }

    try {
  		java.util.ArrayList list = new java.util.ArrayList();
      @declareResultVar 
      while (@rsVar.next()) {
        @getPkVarFromRS
        @allocateResultVar
        @finderGetEo
        list.add(@resultVar);
      }
      return list;
    }
	  catch (java.sql.SQLException sqle) {
      throw new javax.ejb.FinderException(
        "Exception in '@finderMethodName' while using " +
        "result set: '" + @rsVar + "'" + EOL+
        RDBMSUtils.throwable2StackTrace(sqle));
    } catch (java.lang.Exception e) {
      throw new javax.ejb.FinderException(
        "Exception executing finder '@finderMethodName': " +
        RDBMSUtils.throwable2StackTrace(e));
    } finally {
		  @pmVar.releaseResources(@conVar, @stmtVar, @rsVar);
    }
@end rule: finderMethodBodyMulti


@start rule: implementLoadMethodBody
  if (@debugEnabled) {
      @debugSay("@cmpBeanClassName.ejbLoad" 
        + @ctxVar.getPrimaryKey());
  }

  java.sql.Connection @conVar = null;
  java.sql.PreparedStatement @stmtVar = null;
  java.sql.ResultSet @rsVar = null;
  try {
    @pk_class @pkVar = (@pk_class) @ctxVar.getPrimaryKey();
    __WL_con = @pmVar.getConnection();

    int selectForUpdateVal = @pmVar.getSelectForUpdateValue();


    // http://bugs.beasys.com/CRView?CR=CR070260
    //
    //  optimization.

    switch(selectForUpdateVal) {
      case DDConstants.SELECT_FOR_UPDATE_DISABLED:
        @stmtVar = @conVar.prepareStatement("SELECT @allColumnsSql FROM  @tableName WHERE @idParamsSql");
        break;

      case DDConstants.SELECT_FOR_UPDATE:
        @stmtVar = @conVar.prepareStatement("SELECT @allColumnsSql FROM  @tableName WHERE @idParamsSql FOR UPDATE");
        break;

      case DDConstants.SELECT_FOR_UPDATE_NO_WAIT:
        @stmtVar = @conVar.prepareStatement("SELECT @allColumnsSql FROM  @tableName WHERE @idParamsSql FOR UPDATE NOWAIT");
        break;

      default:
        throw new AssertionError(
        "Unknown selectForUpdate type: '"+selectForUpdateVal+"'");
    }

    @resetParams
    @setPrimaryKeyParams

    @rsVar = @stmtVar.executeQuery();
    if (@rsVar.next()) {
      @assignAllColumnsToBean
    } else {
      if(@debugEnabled) {
        @debugSay("@cmpBeanClassName.ejbLoad could not "
          + " find primary key " + @pkVar);
      }
      Loggable l = EJBLogger.lognoSuchEntityExceptionLoggable(@pkVar.toString());
      throw new NoSuchEntityException(l.getMessage());
    }
  } finally {
		@pmVar.releaseResources(@conVar, @stmtVar, @rsVar);
  }
@end rule: implementLoadMethodBody


@start rule: implementCreateMethodBody
  if (@debugEnabled) {
    @debugSay("@cmpBeanClassName.@createMethodName");
  }
  java.sql.Connection @conVar = null;
  java.sql.PreparedStatement @stmtVar = null;
  @declarePkVar
  try {
    @allocatePkVar
		@copyKeyValuesToPkVar

    @conVar = @pmVar.getConnection();
    if(@debugEnabled) {
        @debugSay(
          "@cmpBeanClassName.@createMethodName() got connection " + 
          @conVar);
    }
    String @queryVar = 
      "insert into @tableName (@allColumnsSql) values (@allColumnsQMs)";
    if(@debugEnabled) {
        @debugSay(
          "@cmpBeanClassName.createMethodName() produced sqlString "
          + @queryVar);
    }
    @stmtVar = @conVar.prepareStatement(@queryVar);

    @resetParams

    @setBeanParams

    int count = @stmtVar.executeUpdate();
    if (count != 1) {
      Loggable l = EJBLogger.logbeanNotCreatedLoggable(@pkVar.toString());
      throw new java.lang.Exception(l.getMessage());
    }

    if (__WL_snapshots_enabled) __WL_take_snapshot();

	  return @pkVar;
  } catch (java.sql.SQLException se) {
	    //ejb wants a duplicate key exception if that was what happened
      if(@debugEnabled) {
        @debugSay("@cmpBeanClassName.@createMethodName() "+
	         "checking for duplicate key " + __WL_con);
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
	     throw new javax.ejb.DuplicateKeyException(l.getMessage());
      }
	    else {
        throw se;
      }
  } finally {
    @pmVar.releaseResources(@conVar, @stmtVar, null);
  }
@end rule: implementCreateMethodBody

@start rule: doCheckExistsOnMethodBody
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
@end rule: doCheckExistsOnMethodBody


@start rule: implementExistsMethodBody
    if (@debugEnabled) {
      @debugSay("@cmpBeanClassName.exists: " + @keyVar);
    }

    java.sql.Connection @conVar = null;
    java.sql.PreparedStatement @stmtVar = null;
    java.sql.ResultSet @rsVar = null;
    try {
      @pk_class @pkVar = (@pk_class) @keyVar;
      @conVar = @pmVar.getConnection();
      @stmtVar = @conVar.prepareStatement(
          "select * from @tableName where @idParamsSql");

      @resetParams
      @setPrimaryKeyParams

      @rsVar = @stmtVar.executeQuery();
      if (@rsVar.next()) {
        if (@debugEnabled) @debugSay("exists returning true");
        return true;
      }
	    else {
        if (@debugEnabled) @debugSay("exists returning false");
        return false;
      }
    @standardCatch
    finally {
      @pmVar.releaseResources(@conVar, @stmtVar, @rsVar);
    }
@end rule: implementExistsMethodBody


@start rule: implementEjbStoreMethodBody
  int oldState = __WL_method_state;

  try {
      weblogic.ejb.container.internal.AllowedMethodsHelper.pushBean(this);
    __WL_method_state = STATE_EJBSTORE;
   if (@debugEnabled) {  
     @debugSay("@cmpBeanClassName.ejbStore "+ 
                         @ctxVar.getPrimaryKey());
    }
    super.ejbStore();

    String setString = __WL_determineSetString();

    if (setString == null) {
      // nothing to write back to the database
      if (@debugEnabled) @debugSay("** Skipping database write for" +
                                 "read-only transaction");
      return;
    }

  if (__WL_updateable) {
    if(@debugEnabled) {
      @debugSay(
        "@cmpBeanClassName.ejbStore: bean is updatable.");
    }
    java.sql.Connection @conVar = null;
    java.sql.PreparedStatement @stmtVar = null;
    try {
      @pk_class @pkVar = (@pk_class) @ctxVar.getPrimaryKey();

      String @queryVar = "UPDATE @tableName SET " + 
		                     setString +
                         " WHERE @idParamsSql";
      if(@debugEnabled) {
        @debugSay("sql: " + @queryVar);
      }

      @conVar = @pmVar.getConnection();
      @stmtVar = @conVar.prepareStatement(@queryVar);
    
      int @numVar = 1;
      @setUpdateBeanParams
      @setPrimaryKeyParamsUsingNum

      int @iVar = @stmtVar.executeUpdate();
      if (@iVar == 0) {
              Loggable l = EJBLogger.lognoSuchEntityExceptionLoggable(@pkVar.toString());
	      throw new NoSuchEntityException(l.getMessage());
      }
    @standardCatch
    finally {

      try {
        if (@stmtVar != null) @stmtVar.close();
	      @pmVar.releaseConnection (@conVar);
      } catch (java.lang.Exception e) {
	 if (@debugEnabled) @debugSay("Error during store: ", e);
         throw new EJBException(e);
      }
    }
  }
  } finally {
      __WL_method_state = oldState; 
      weblogic.ejb.container.internal.AllowedMethodsHelper.popBean();
  }

@end rule: implementEjbStoreMethodBody


@start rule: implementEjbRemoveMethodBody
    int oldState = __WL_method_state;

    try {
      weblogic.ejb.container.internal.AllowedMethodsHelper.pushBean(this);
      __WL_method_state = STATE_EJB_REMOVE;
      if (@debugEnabled) {
        @debugSay("@cmpBeanClassName.ejbRemove " + 
          @ctxVar.getPrimaryKey());
      }

      super.ejbRemove();

      java.sql.Connection @conVar = null;
      java.sql.PreparedStatement @stmtVar = null;
      try {
        __WL_initialize();
        @pk_class @pkVar = (@pk_class) @ctxVar.getPrimaryKey();
        @conVar = @pmVar.getConnection();
        @stmtVar = @conVar.prepareStatement(
            "delete from @tableName where @idParamsSql");

        @resetParams
        @setPrimaryKeyParams

        int i = @stmtVar.executeUpdate();
        if (i == 0) {
          Loggable l = EJBLogger.lognoSuchEntityExceptionLoggable(@pkVar.toString());
          throw new NoSuchEntityException(l.getMessage());
        }
      @standardCatch
      finally {
        @pmVar.releaseResources(@conVar, @stmtVar, null);
      }

    } finally {
      __WL_method_state = oldState;
      weblogic.ejb.container.internal.AllowedMethodsHelper.popBean();
    }

@end rule: implementEjbRemoveMethodBody


@start rule: home_method
  @method_signature_no_throws
    @beanmethod_throws_clause
  {
    int oldState = __WL_method_state;

    try {
      weblogic.ejb.container.internal.AllowedMethodsHelper.pushBean(this);
      __WL_method_state = @method_state;

      @declare_result

      @result super.@method_name(@method_parameters_without_types);

      @return_result
    } finally {
      __WL_method_state = oldState;
      weblogic.ejb.container.internal.AllowedMethodsHelper.popBean();
    }
  }
@end rule: home_method

@start rule: check_for_simple_modified_field

  __WL_modified[@modified_field_index] = @modified_field != @snapshot_field;

  if (__WL_modified[@modified_field_index]) {

    nothingModified = false;

    @snapshot_field = @modified_field;

    if (needsComma) {
      sb.append(", ");
    } else {
      sb = new StringBuffer(200);
      needsComma = true;
    }

    sb.append("@modified_column_name = ?");
  }

@end rule: check_for_simple_modified_field

@start rule: check_for_date_modified_field

  Long __WL_date_@modified_field_index = 
    (@modified_field == null) ? null: new Long(@modified_field.getTime());


  if (__WL_date_@modified_field_index == @snapshot_field) {
    // must both be null
    __WL_modified[@modified_field_index] = false;
  } else if (@snapshot_field == null) {
    // @snapshot_field is null, but bean field is non-null
    __WL_modified[@modified_field_index] = true;
  } else {
    __WL_modified[@modified_field_index] = 
      ! @snapshot_field.equals(__WL_date_@modified_field_index);
  }

  if (__WL_modified[@modified_field_index]) {

    nothingModified = false;

    @snapshot_field = __WL_date_@modified_field_index;

    if (needsComma) {
      sb.append(", ");
    } else {
      sb = new StringBuffer(200);
      needsComma = true;
    }

    sb.append("@modified_column_name = ?");
  }

@end rule: check_for_date_modified_field

@start rule: check_for_bytea_modified_field

  if (@debugEnabled) {
    @debugSay("** Before is: ");
    __WL_print_byte_array(@snapshot_field);
    @debugSay("** After is: ");
    __WL_print_byte_array(@modified_field);
  }

  if ((@modified_field == null) && (@snapshot_field == null)) {
    __WL_modified[@modified_field_index] = false;
  } else if ((@modified_field == null) || (@snapshot_field == null)) {
    __WL_modified[@modified_field_index] = true;
  } else if (@modified_field.length != @snapshot_field.length) {
    __WL_modified[@modified_field_index] = true;
  } else {
    __WL_modified[@modified_field_index] = false;
    for (int i=0; i<@modified_field.length; i++) {
      if (@modified_field[i] != @snapshot_field[i]) {
        __WL_modified[@modified_field_index] = true;
        break;
      }
    }
  }

  if (@debugEnabled) {  
    @debugSay("** Modified: "+__WL_modified[@modified_field_index]);
  }


  if (__WL_modified[@modified_field_index]) {

    nothingModified = false;

    @snapshot_field = __WL_snapshot_byte_array(@modified_field);

    if (needsComma) {
      sb.append(", ");
    } else {
      sb = new StringBuffer(200);
      needsComma = true;
    }

    sb.append("@modified_column_name = ?");
  }

@end rule: check_for_bytea_modified_field

@start rule: bean_state_timeout_check
   long lastLoadTime = __WL_lastLoadTime.get();
   if(lastLoadTime != 0) {
     if(__WL_readTimeoutMS == 0 ||
        System.currentTimeMillis() - lastLoadTime < __WL_readTimeoutMS)
      return true;
   }
   return false;
@end rule: bean_state_timeout_check
