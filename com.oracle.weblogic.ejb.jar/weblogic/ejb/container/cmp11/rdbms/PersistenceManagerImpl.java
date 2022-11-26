package weblogic.ejb.container.cmp11.rdbms;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.ejb.EntityBean;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.InternalException;
import weblogic.ejb.container.interfaces.BeanManager;
import weblogic.ejb.container.interfaces.WLCMPPersistenceManager;
import weblogic.ejb.container.internal.EJBRuntimeUtils;
import weblogic.ejb.container.internal.TransactionService;
import weblogic.ejb.container.manager.BaseEntityManager;
import weblogic.ejb.container.persistence.spi.CMPBean;
import weblogic.ejb.container.persistence.spi.CMPBeanDescriptor;
import weblogic.ejb.container.persistence.spi.PersistenceManager;
import weblogic.ejb.container.persistence.spi.RSInfo;
import weblogic.ejb.container.utils.MethodUtils;
import weblogic.ejb.container.utils.TableVerifier;
import weblogic.ejb.container.utils.TableVerifierMetaData;
import weblogic.ejb.container.utils.TableVerifierSqlQuery;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.ejb20.persistence.spi.PersistenceRuntimeException;
import weblogic.logging.Loggable;
import weblogic.utils.AssertionError;

public final class PersistenceManagerImpl implements PersistenceManager, WLCMPPersistenceManager {
   private static final DebugLogger deploymentLogger;
   private static final DebugLogger runtimeLogger;
   private static final int GET_FIELD_INFO = 0;
   private static final int GET_COLUMN_INFO = 1;
   private BaseEntityManager beanManager = null;
   CMPBeanDescriptor bd = null;
   private RDBMSBean bean = null;
   private Driver jtsDriver = null;
   private Context ctx = null;
   private DataSource ds = null;
   private ClassLoader classLoader = null;
   private boolean usingJtsDriver = false;
   private TableVerifier verifier;
   private int databaseType = 0;
   private Map variable2SQLType = new HashMap();
   private Map variable2nullable = new HashMap();
   private TransactionManager tm = null;
   private String[] indexColumnMap = null;

   public void setup(BeanManager beanManager) throws WLDeploymentException {
      if (deploymentLogger.isDebugEnabled()) {
         debugDeployment("PersistenceManagerImpl.setup");
      }

      assert this.bd != null : "bd != null failed ";

      assert this.bean != null : "bean != null failed";

      this.beanManager = (BaseEntityManager)beanManager;
      this.classLoader = this.beanManager.getBeanInfo().getClassLoader();
      if ("MetaData".equalsIgnoreCase(this.getValidateDbSchemaWith())) {
         this.verifier = new TableVerifierMetaData();
      } else {
         this.verifier = new TableVerifierSqlQuery();
      }

      Loggable l;
      if (this.bean.getPoolName() != null) {
         try {
            this.jtsDriver = (Driver)Class.forName("weblogic.jdbc.jts.Driver").newInstance();
         } catch (Exception var6) {
            l = EJBLogger.logUnableToLoadJTSDriverLoggable();
            throw new WLDeploymentException(l.getMessageText(), var6);
         }

         assert this.jtsDriver != null;

         this.usingJtsDriver = true;
      } else {
         assert this.bean.getDataSourceName() != null;

         try {
            this.ctx = new InitialContext();
         } catch (NamingException var5) {
            throw new AssertionError(var5);
         }

         try {
            this.ds = (DataSource)this.ctx.lookup(this.bean.getDataSourceName());
         } catch (NamingException var4) {
            l = EJBLogger.logDataSourceNotFoundLoggable(this.bean.getDataSourceName());
            throw new WLDeploymentException(l.getMessageText(), var4);
         }
      }

      this.databaseType = this.bean.getDatabaseType();
      this.tm = TransactionService.getTransactionManager();
      this.verifyDatabaseType();
      this.verifyTablesExist();
      this.populateIndexColumnMap();
      this.populateFieldSQLTypeMap();
   }

   public String getCreateDefaultDBMSTables() {
      return this.bean.getCreateDefaultDBMSTables();
   }

   public String getValidateDbSchemaWith() {
      return this.bean.getValidateDbSchemaWith();
   }

   public String getEjbName() {
      return this.bean.getEjbName();
   }

   public EntityBean getBeanFromPool() throws InternalException {
      return this.beanManager.getBeanFromPool();
   }

   public EntityBean getBeanFromRS(Object pk, RSInfo rsInfo) throws InternalException {
      return this.beanManager.getBeanFromRS(pk, rsInfo);
   }

   public Object finderGetEoFromBeanOrPk(EntityBean bean, Object pk, boolean isLocal) {
      return this.beanManager.finderGetEoFromBeanOrPk(bean, pk, isLocal);
   }

   public ClassLoader getClassLoader() {
      return this.classLoader;
   }

   public Object findByPrimaryKey(EntityBean bean, Method finderMethod, Object pk) throws Throwable {
      if (runtimeLogger.isDebugEnabled()) {
         debugRuntime("PersistenceManagerImpl.findByPrimaryKey");
      }

      assert bean != null;

      assert finderMethod != null;

      assert pk != null;

      Object[] param = new Object[]{pk};

      try {
         return finderMethod.invoke(bean, param);
      } catch (InvocationTargetException var6) {
         throw var6.getTargetException();
      } catch (Exception var7) {
         if (runtimeLogger.isDebugEnabled()) {
            debugRuntime("Error invoking finder: ", var7);
         }

         throw var7;
      }
   }

   public int getSelectForUpdateValue() {
      try {
         Transaction tx = this.tm.getTransaction();
         weblogic.transaction.Transaction wtx = (weblogic.transaction.Transaction)tx;
         Integer i = null;
         if (wtx != null) {
            i = (Integer)wtx.getProperty("SELECT_FOR_UPDATE");
         }

         return i == null ? 0 : i;
      } catch (Exception var4) {
         throw new PersistenceRuntimeException(var4);
      }
   }

   public String selectForUpdate() {
      return RDBMSUtils.selectForUpdateToString(this.getSelectForUpdateValue());
   }

   private void verifyDatabaseType() throws WLDeploymentException {
      Connection conn = null;

      try {
         conn = this.getConnection();
         this.databaseType = this.verifier.verifyDatabaseType(conn, this.databaseType);
      } catch (Exception var6) {
         throw new WLDeploymentException(var6.getMessage(), var6);
      } finally {
         this.releaseResources(conn, (PreparedStatement)null, (ResultSet)null);
      }

   }

   private void verifyTablesExist() throws WLDeploymentException {
      Connection conn = null;

      try {
         conn = this.getConnection();
         this.verifier.verifyOrCreateOrAlterTable(this, conn, this.bean.getQualifiedTableName(), this.getPersistentFieldsOrColumns(1), true, this.getPersistentFieldsOrColumns(0), this.variable2SQLType, this.variable2nullable, this.bean.getCreateDefaultDBMSTables(), false);
      } catch (Exception var6) {
         throw new WLDeploymentException(var6.getMessage(), var6);
      } finally {
         this.releaseResources(conn, (PreparedStatement)null, (ResultSet)null);
      }

   }

   public EntityBean findByPrimaryKeyLoadBean(EntityBean bean, Method finderMethod, Object pk) throws Throwable {
      if (runtimeLogger.isDebugEnabled()) {
         debugRuntime("PersistenceManagerImpl.findByPrimaryKeyLoadBean");
      }

      try {
         Object[] param = new Object[]{pk};
         return (EntityBean)finderMethod.invoke(bean, param);
      } catch (InvocationTargetException var6) {
         Throwable t = var6.getTargetException();
         if (runtimeLogger.isDebugEnabled()) {
            debugRuntime("Error invoking finder: ", t);
         }

         throw t;
      } catch (Exception var7) {
         if (runtimeLogger.isDebugEnabled()) {
            debugRuntime("Error invoking finder: ", var7);
         }

         throw var7;
      }
   }

   public Object scalarFinder(EntityBean bean, Method finderMethod, Object[] args) throws Throwable {
      if (runtimeLogger.isDebugEnabled()) {
         debugRuntime("PersistenceManagerImpl.scalarFinder");
      }

      try {
         return finderMethod.invoke(bean, args);
      } catch (InvocationTargetException var6) {
         Throwable t = var6.getTargetException();
         if (runtimeLogger.isDebugEnabled()) {
            debugRuntime("Error invoking finder: ", t);
         }

         throw t;
      } catch (Exception var7) {
         if (runtimeLogger.isDebugEnabled()) {
            debugRuntime("Error invoking finder: ", var7);
         }

         throw var7;
      }
   }

   public Map scalarFinderLoadBean(EntityBean bean, Method finderMethod, Object[] args) throws Throwable {
      if (runtimeLogger.isDebugEnabled()) {
         debugRuntime("PersistenceManagerImpl.scalarFinderLoadBean");
      }

      try {
         return (Map)finderMethod.invoke(bean, args);
      } catch (InvocationTargetException var6) {
         Throwable t = var6.getTargetException();
         if (runtimeLogger.isDebugEnabled()) {
            debugRuntime("Error invoking finder: ", t);
         }

         throw t;
      } catch (Exception var7) {
         if (runtimeLogger.isDebugEnabled()) {
            debugRuntime("Error invoking finder: ", var7);
         }

         throw var7;
      }
   }

   public Enumeration enumFinder(EntityBean bean, Method finderMethod, Object[] args) throws Throwable {
      if (runtimeLogger.isDebugEnabled()) {
         debugRuntime("PersistenceManagerImpl.enumFinder");
      }

      try {
         return (Enumeration)finderMethod.invoke(bean, args);
      } catch (InvocationTargetException var6) {
         Throwable t = var6.getTargetException();
         if (runtimeLogger.isDebugEnabled()) {
            debugRuntime("Error invoking finder: ", t);
         }

         throw t;
      } catch (Exception var7) {
         if (runtimeLogger.isDebugEnabled()) {
            debugRuntime("Error invoking finder: ", var7);
         }

         throw var7;
      }
   }

   public Collection collectionFinder(EntityBean bean, Method finderMethod, Object[] args) throws Throwable {
      if (runtimeLogger.isDebugEnabled()) {
         debugRuntime("PersistenceManagerImpl.collectionFinder");
      }

      try {
         return (Collection)finderMethod.invoke(bean, args);
      } catch (InvocationTargetException var6) {
         Throwable t = var6.getTargetException();
         if (runtimeLogger.isDebugEnabled()) {
            debugRuntime("Error invoking finder: ", t);
         }

         throw t;
      } catch (Exception var7) {
         if (runtimeLogger.isDebugEnabled()) {
            debugRuntime("Error invoking finder: ", var7);
         }

         throw var7;
      }
   }

   public Map collectionFinderLoadBean(EntityBean bean, Method finderMethod, Object[] args) throws Throwable {
      if (runtimeLogger.isDebugEnabled()) {
         debugRuntime("PersistenceManagerImpl.collectionFinderLoadBean");
      }

      try {
         return (Map)finderMethod.invoke(bean, args);
      } catch (InvocationTargetException var6) {
         Throwable t = var6.getTargetException();
         if (runtimeLogger.isDebugEnabled()) {
            debugRuntime("Error invoking finder: ", t);
         }

         throw t;
      } catch (Exception var7) {
         if (runtimeLogger.isDebugEnabled()) {
            debugRuntime("Error invoking finder: ", var7);
         }

         throw var7;
      }
   }

   public void loadBeanFromRS(EntityBean bean, RSInfo rsInfo) throws InternalException {
      try {
         ((CMPBean)bean).__WL_loadGroupByIndex(rsInfo.getGroupIndex(), rsInfo.getRS(), rsInfo.getOffset(), rsInfo.getPK(), bean);
      } catch (Exception var4) {
         EJBRuntimeUtils.throwInternalException("Error load bean states from ResultSet", var4);
      }

   }

   public Connection getConnection() throws SQLException {
      if (runtimeLogger.isDebugEnabled()) {
         debugRuntime("PersistenceManagerImpl.getConnection");
      }

      Connection con = null;
      if (this.usingJtsDriver) {
         assert this.getDriver() != null;

         assert this.bean.getPoolName() != null;

         assert this.getDriver().acceptsURL("jdbc:weblogic:jts:" + this.bean.getPoolName());

         con = this.getDriver().connect("jdbc:weblogic:jts:" + this.bean.getPoolName(), (Properties)null);
      } else {
         con = this.ds.getConnection();
      }

      if (con == null) {
         Loggable l = EJBLogger.logCouldNotGetConnectionFromLoggable(this.connectionProducerType() + " '" + this.connectionProducerName() + "'");
         throw new SQLException(l.getMessageText());
      } else {
         return con;
      }
   }

   public void setBeanInfo(RDBMSBean bean) {
      assert bean != null;

      assert bean.getPoolName() != null || bean.getDataSourceName() != null : "No pool or data source set for this bean.";

      assert bean.getTableName() != null : "No table name set for this bean.";

      this.bean = bean;
   }

   public RDBMSBean getBeanInfo() {
      return this.bean;
   }

   private Driver getDriver() {
      return this.jtsDriver;
   }

   private String connectionProducerType() {
      return this.usingJtsDriver ? "connection pool" : "data source";
   }

   private String connectionProducerName() {
      return this.usingJtsDriver ? this.bean.getPoolName() : this.bean.getDataSourceName();
   }

   private void populateIndexColumnMap() {
      if (deploymentLogger.isDebugEnabled()) {
         debugDeployment("PersistenceManagerImpl.populateIndexColumnMap(");
      }

      Map fieldsToColumns = this.getBeanInfo().getFieldToColumnMap();
      String[] fieldNames = (String[])((String[])this.bean.getFieldNamesList().toArray(new String[0]));
      this.indexColumnMap = new String[fieldNames.length];

      for(int i = 0; i < fieldNames.length; ++i) {
         this.indexColumnMap[i] = (String)fieldsToColumns.get(fieldNames[i]);
      }

   }

   public String getColumnName(int modifiedIndex) {
      if (deploymentLogger.isDebugEnabled()) {
         debugDeployment("PersistenceManagerImpl.getColumnName");
      }

      assert this.indexColumnMap != null;

      if (deploymentLogger.isDebugEnabled()) {
         debugDeployment("modifiedIndex: " + modifiedIndex + " indexColumnMap.length: " + this.indexColumnMap.length);
      }

      assert modifiedIndex < this.indexColumnMap.length;

      if (deploymentLogger.isDebugEnabled()) {
         debugDeployment("returning: " + this.indexColumnMap[modifiedIndex]);
      }

      return this.indexColumnMap[modifiedIndex];
   }

   private List getPersistentFieldsOrColumns(int returnType) {
      List persistentFields = this.getBeanInfo().getFieldNamesList();
      List persistentColumns = new ArrayList();
      Map fieldsToColumns = this.getBeanInfo().getFieldToColumnMap();
      Iterator fieldNames = persistentFields.iterator();

      while(fieldNames.hasNext()) {
         String fieldName = (String)fieldNames.next();
         String dbmsColumn = (String)fieldsToColumns.get(fieldName);

         assert dbmsColumn != null;

         persistentColumns.add(dbmsColumn);
      }

      switch (returnType) {
         case 0:
            return persistentFields;
         case 1:
            return persistentColumns;
         default:
            throw new AssertionError("Unknown returnType: " + returnType + " encountered in RDBMSersistenceManager.getPersistentFieldsOrColumns(int returnType)");
      }
   }

   private void populateFieldSQLTypeMap() throws WLDeploymentException {
      if (deploymentLogger.isDebugEnabled()) {
         debugDeployment("PersistenceManagerImpl.populateFieldSQLTypeMap");
      }

      if (this.variable2SQLType.size() <= 0) {
         Connection conn = null;

         try {
            conn = this.getConnection();
            this.verifier.verifyOrCreateOrAlterTable(this, conn, this.getBeanInfo().getQualifiedTableName(), this.getPersistentFieldsOrColumns(1), true, this.getPersistentFieldsOrColumns(0), this.variable2SQLType, this.variable2nullable, (String)null, false);
            if (this.variable2SQLType.size() <= 0) {
               Loggable l = EJBLogger.logCouldNotInitializeFieldSQLTypeMapWithoutExceptionLoggable();
               throw new WLDeploymentException(l.getMessageText());
            }
         } catch (Exception var7) {
            Loggable l = EJBLogger.logCouldNotInitializeFieldSQLTypeMapLoggable(var7);
            throw new WLDeploymentException(l.getMessageText(), var7);
         } finally {
            this.releaseResources(conn, (PreparedStatement)null, (ResultSet)null);
         }

      }
   }

   public boolean setParamNull(PreparedStatement statement, int paramIndex, Object value, String fieldName) throws SQLException {
      if (runtimeLogger.isDebugEnabled()) {
         debugRuntime("PersistenceManagerImpl.setParamNull");
      }

      if (value == null) {
         assert this.variable2SQLType != null;

         assert this.variable2SQLType.get(fieldName) != null : "No field->SQLType mapping for field " + fieldName;

         Integer sqlTypeObj = (Integer)this.variable2SQLType.get(fieldName);
         int sqlType = sqlTypeObj;
         statement.setNull(paramIndex, sqlType);
         return true;
      } else {
         return false;
      }
   }

   public void releaseResources(Connection connection, PreparedStatement statement, ResultSet results) {
      this.releaseResources(connection, (Statement)statement, results);
   }

   public void releaseResources(Connection connection, Statement statement, ResultSet results) {
      if (runtimeLogger.isDebugEnabled()) {
         debugRuntime("PersistenceManagerImpl.releaseResources");
      }

      try {
         this.releaseResultSet(results);
      } catch (SQLException var7) {
      }

      try {
         this.releaseStatement(statement);
      } catch (SQLException var6) {
      }

      try {
         this.releaseConnection(connection);
      } catch (SQLException var5) {
      }

   }

   public void releaseConnection(Connection connection) throws SQLException {
      if (runtimeLogger.isDebugEnabled()) {
         debugRuntime("PersistenceManagerImpl.releaseConnection");
      }

      if (connection != null && !connection.isClosed()) {
         connection.close();
      }

   }

   public void releaseStatement(Statement statement) throws SQLException {
      if (runtimeLogger.isDebugEnabled()) {
         debugRuntime("PersistenceManagerImpl.releaseStatement");
      }

      if (statement != null) {
         statement.close();
      }

   }

   public void releaseResultSet(ResultSet results) throws SQLException {
      if (runtimeLogger.isDebugEnabled()) {
         debugRuntime("PersistenceManagerImpl.releaseResultSet");
      }

      if (results != null) {
         results.close();
      }

   }

   public void dropAndCreateDefaultDBMSTable(String tableName) throws WLDeploymentException {
      StringBuffer sb = new StringBuffer("DROP TABLE " + tableName);
      Connection connection = null;
      Statement stmt = null;

      try {
         connection = this.getConnection();
         stmt = connection.createStatement();
         stmt.executeUpdate(sb.toString());
      } catch (Exception var10) {
         Loggable l = EJBLogger.logErrorDroppingDefaultDBMSTableLoggable(tableName, var10);
         l.log();
      } finally {
         this.releaseResources(connection, (Statement)stmt, (ResultSet)null);
      }

      this.createDefaultDBMSTable(tableName);
   }

   public void alterDefaultDBMSTable(String tableName, Set newColumns, Set removedColumns) throws WLDeploymentException {
      if (newColumns.isEmpty() && removedColumns.isEmpty()) {
         if (deploymentLogger.isDebugEnabled()) {
            debugDeployment("Table not changed so no alter table");
         }

      } else {
         this.alterOracleDefaultDBMSTable(tableName, newColumns, removedColumns);
      }
   }

   private void alterOracleDefaultDBMSTable(String tableName, Set newColumns, Set removedColumns) throws WLDeploymentException {
      this.addColumns(tableName, newColumns);
      this.removeColumns(tableName, removedColumns);
   }

   private void removeColumns(String tableName, Set removedColumns) throws WLDeploymentException {
      StringBuffer alterTable = new StringBuffer("alter table " + tableName + " ");
      if (!removedColumns.isEmpty()) {
         alterTable.append("drop ( ");
         Iterator removedColIter = removedColumns.iterator();

         while(removedColIter.hasNext()) {
            String columnName = (String)removedColIter.next();
            alterTable.append(columnName);
            if (removedColIter.hasNext()) {
               alterTable.append(",");
            }
         }

         alterTable.append(" ) ");
         if (deploymentLogger.isDebugEnabled()) {
            debugDeployment("The alter table command is ..." + alterTable);
         }

         Connection connection = null;
         Statement stmt = null;

         try {
            connection = this.getConnection();
            stmt = connection.createStatement();
            stmt.executeUpdate(alterTable.toString());
         } catch (Exception var11) {
            Loggable l = EJBLogger.logErrorAlteringDefaultDBMSTableLoggable(tableName, var11);
            l.log();
            throw new WLDeploymentException(l.getMessageText(), var11);
         } finally {
            this.releaseResources(connection, (Statement)stmt, (ResultSet)null);
         }

      }
   }

   private void addColumns(String tableName, Set newColumns) throws WLDeploymentException {
      StringBuffer alterTable = new StringBuffer("alter table " + tableName + " ");
      if (!newColumns.isEmpty()) {
         alterTable.append("add ( ");
         Iterator newColIter = newColumns.iterator();

         while(newColIter.hasNext()) {
            String columnName = (String)newColIter.next();
            alterTable.append(columnName + " " + this.getSqltypeForCol(columnName));
            if (newColIter.hasNext()) {
               alterTable.append(",");
            }
         }

         alterTable.append(" ) ");
         if (deploymentLogger.isDebugEnabled()) {
            debugDeployment("The alter table command is ..." + alterTable);
         }

         Connection connection = null;
         Statement stmt = null;

         try {
            connection = this.getConnection();
            stmt = connection.createStatement();
            stmt.executeUpdate(alterTable.toString());
         } catch (Exception var11) {
            Loggable l = EJBLogger.logErrorAlteringDefaultDBMSTableLoggable(tableName, var11);
            l.log();
            throw new WLDeploymentException(l.getMessageText(), var11);
         } finally {
            this.releaseResources(connection, (Statement)stmt, (ResultSet)null);
         }

      }
   }

   private String getSqltypeForCol(String columnName) throws WLDeploymentException {
      Class fieldClass = this.bean.getCmpFieldClass(columnName);
      if (null == fieldClass) {
         throw new WLDeploymentException(" Bean: " + this.bean.getEjbName() + ", could not get Column To Field Map for column ");
      } else {
         try {
            return MethodUtils.getDefaultDBMSColType(fieldClass, this.databaseType);
         } catch (Exception var4) {
            throw new WLDeploymentException("No Field class found for " + columnName);
         }
      }
   }

   public boolean createDefaultDBMSTable(String tableName) throws WLDeploymentException {
      StringBuffer sb = new StringBuffer("CREATE TABLE " + tableName + " (");
      Connection connection = null;
      Statement stmt = null;
      List pkFields = this.bean.getPrimaryKeyFields();
      Set pkCols = new HashSet();

      try {
         if (deploymentLogger.isDebugEnabled()) {
            debugDeployment(" createDefaultDBMSTable: processing Bean Table: " + tableName);
         }

         Iterator it = this.bean.getFieldNames();

         String query;
         while(it.hasNext()) {
            query = (String)it.next();
            String column = this.bean.getColumnForField(query);
            sb.append(column + " ");
            if (pkFields.contains(query)) {
               pkCols.add(column);
            }

            Class cl = this.bd.getFieldClass(query);
            sb.append(MethodUtils.getDefaultDBMSColType(cl, this.databaseType));
            if (it.hasNext()) {
               sb.append(", ");
            }
         }

         if (pkCols.size() > 0) {
            sb.append(", PRIMARY KEY (");
            it = pkCols.iterator();

            while(it.hasNext()) {
               query = (String)it.next();
               sb.append(query);
               if (it.hasNext()) {
                  sb.append(", ");
               }
            }

            sb.append(")");
         }

         sb.append(")");
         query = sb.toString();
         if (deploymentLogger.isDebugEnabled()) {
            debugDeployment(" full CREATE TABLE QUERY: '" + query + "'");
         }

         connection = this.getConnection();
         stmt = connection.createStatement();
         stmt.executeUpdate(query);
      } catch (Exception var14) {
         Loggable l = EJBLogger.logerrorCreatingDefaultDBMSTableLoggable(tableName, var14.getMessage());
         l.log();
         throw new WLDeploymentException(l.getMessage(), var14);
      } finally {
         this.releaseResources(connection, (Statement)stmt, (ResultSet)null);
      }

      return true;
   }

   public void updateClassLoader(ClassLoader cl) {
      this.classLoader = cl;
   }

   private static void debugDeployment(String s) {
      deploymentLogger.debug("[PersistenceManagerImpl] " + s);
   }

   private static void debugRuntime(String s) {
      runtimeLogger.debug("[PersistenceManagerImpl] " + s);
   }

   private static void debugRuntime(String s, Throwable th) {
      runtimeLogger.debug("[PersistenceManagerImpl] " + s, th);
   }

   static {
      deploymentLogger = EJBDebugService.cmpDeploymentLogger;
      runtimeLogger = EJBDebugService.cmpRuntimeLogger;
   }
}
