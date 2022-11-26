package weblogic.ejb.container.persistence.spi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.NoSuchEntityException;
import weblogic.ejb.container.cache.QueryCacheKey;
import weblogic.ejb.container.interfaces.WLEntityBean;
import weblogic.ejb.container.internal.QueryCachingHandler;

public interface CMPBean extends WLEntityBean {
   void __WL_setup(Map var1, PersistenceManager var2);

   Object __WL_getPrimaryKey();

   EntityContext __WL_getEntityContext();

   void __WL_superEjbLoad() throws Throwable;

   void __WL_superEjbStore() throws Throwable;

   void __WL_superEjbRemove(boolean var1) throws Throwable;

   void __WL_copyFrom(CMPBean var1, boolean var2);

   void __WL_initialize();

   void __WL_initialize(boolean var1);

   void __WL_store(boolean var1) throws Throwable;

   Object __WL_getPKFromRSInstance(ResultSet var1, Integer var2, ClassLoader var3) throws Exception;

   void __WL_loadGroupByIndex(int var1, ResultSet var2, Integer var3, Object var4, EntityBean var5) throws Exception;

   void __WL_loadCMRFieldByCmrField(String var1, ResultSet var2, Integer var3, EntityBean var4) throws Exception;

   PersistenceManager __WL_getPersistenceManager();

   void __WL_makeCascadeDelList(Map var1, List var2, List var3, boolean var4) throws Exception;

   void __WL_checkExistsOnMethod() throws NoSuchEntityException;

   void __WL_doCheckExistsOnMethod() throws NoSuchEntityException;

   boolean __WL_getIsRemoved();

   void __WL_setIsRemoved(boolean var1);

   boolean __WL_isModified();

   boolean[] __WL_getIsModifiedUnion(boolean[] var1);

   void __WL_resetIsModifiedVars(Connection var1, boolean var2) throws Exception;

   void __WL_resetIsModifiedVars(int var1, Connection var2, boolean var3) throws Exception;

   void __WL_perhapsTakeSnapshot();

   Collection __WL_getNullSnapshotVariables();

   void __WL_perhapsReloadOptimisticColumn() throws Exception;

   boolean __WL_exists(Object var1);

   PreparedStatement[] __WL_getStmtArray(Connection var1, boolean[] var2, int var3, boolean var4) throws Exception;

   void __WL_setBeanParamsForStmtArray(PreparedStatement[] var1, boolean[] var2, int var3, boolean var4) throws Exception;

   void __WL_addSelfRelatedBeansToInsertStmt(PreparedStatement[] var1, List var2, boolean var3) throws Exception;

   void __WL_addSelfRelatedBeansToDeleteStmt(PreparedStatement[] var1, List var2, boolean[] var3, boolean var4) throws Exception;

   String __WL_getM2NSQL(String var1, int var2);

   Collection __WL_getCmrBeansForCmrField(String var1);

   int __WL_appendVerifySqlForBatch(List var1, StringBuffer[] var2, int[] var3, int var4);

   void __WL_setVerifyParamsForBatch(Connection var1, PreparedStatement[] var2, int[] var3) throws SQLException;

   int __WL_appendVerifySql(List var1, StringBuffer[] var2, int[] var3, int var4);

   void __WL_setVerifyParams(Connection var1, PreparedStatement[] var2, int[] var3) throws SQLException;

   void __WL_setCreateAfterRemove(boolean var1);

   void __WL_loadBeansRelatedToCachingName(String var1, ResultSet var2, CMPBean var3, int var4, QueryCachingHandler var5) throws Exception;

   void __WL_setNonFKHolderRelationChange(boolean var1);

   boolean __WL_getNonFKHolderRelationChange();

   void __WL_setM2NInsert(boolean var1);

   boolean __WL_getM2NInsert();

   void __WL_clearCMRFields();

   void __WL_setInvalidatedBeanIsRegistered(boolean var1);

   void __WL_setLoaded(int var1, boolean var2);

   void __WL_putCmrFieldInQueryCache(String var1, QueryCacheKey var2);

   boolean __WL_isConnected();

   void __WL_setConnected(boolean var1);

   Object _WL__getCategoryValue();
}
