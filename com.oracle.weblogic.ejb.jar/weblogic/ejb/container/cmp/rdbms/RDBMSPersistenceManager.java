package weblogic.ejb.container.cmp.rdbms;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Reader;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;
import javax.ejb.EJBObject;
import javax.ejb.EntityBean;
import javax.ejb.FinderException;
import javax.ejb.ObjectNotFoundException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.OptimisticConcurrencyException;
import weblogic.ejb.PreparedQuery;
import weblogic.ejb.WLQueryProperties;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.InternalException;
import weblogic.ejb.container.cache.MultiValueQueryCacheElement;
import weblogic.ejb.container.cache.QueryCacheElement;
import weblogic.ejb.container.cache.QueryCacheKey;
import weblogic.ejb.container.cmp.rdbms.finders.EjbqlFinder;
import weblogic.ejb.container.cmp.rdbms.finders.Finder;
import weblogic.ejb.container.cmp.rdbms.finders.ParamNode;
import weblogic.ejb.container.cmp.rdbms.finders.SqlFinder;
import weblogic.ejb.container.dd.DDConstants;
import weblogic.ejb.container.dd.xml.DDUtils;
import weblogic.ejb.container.interfaces.BeanManager;
import weblogic.ejb.container.interfaces.EntityBeanInfo;
import weblogic.ejb.container.interfaces.WLCMPPersistenceManager;
import weblogic.ejb.container.interfaces.WLEntityBean;
import weblogic.ejb.container.internal.EJBRuntimeUtils;
import weblogic.ejb.container.internal.EntityEJBContextImpl;
import weblogic.ejb.container.internal.QueryCachingHandler;
import weblogic.ejb.container.internal.TransactionService;
import weblogic.ejb.container.manager.BaseEntityManager;
import weblogic.ejb.container.manager.TTLManager;
import weblogic.ejb.container.persistence.RSInfoImpl;
import weblogic.ejb.container.persistence.spi.CMPBean;
import weblogic.ejb.container.persistence.spi.CMPBeanManager;
import weblogic.ejb.container.persistence.spi.EjbEntityRef;
import weblogic.ejb.container.persistence.spi.PersistenceManager;
import weblogic.ejb.container.persistence.spi.RSInfo;
import weblogic.ejb.container.utils.TableVerifier;
import weblogic.ejb.container.utils.TableVerifierMetaData;
import weblogic.ejb.container.utils.TableVerifierSqlQuery;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.ejb20.cmp.rdbms.RDBMSException;
import weblogic.ejb20.cmp.rdbms.finders.InvalidFinderException;
import weblogic.ejb20.persistence.spi.PersistenceRuntimeException;
import weblogic.jdbc.common.internal.DataSourceMetaData;
import weblogic.jdbc.rowset.CachedRowSetMetaData;
import weblogic.jdbc.rowset.RowSetFactory;
import weblogic.jdbc.rowset.WLCachedRowSet;
import weblogic.logging.Loggable;
import weblogic.utils.AssertionError;
import weblogic.utils.Debug;
import weblogic.utils.collections.ArraySet;
import weblogic.utils.collections.ConcurrentHashMap;
import weblogic.utils.io.FilteringObjectInputStream;

public final class RDBMSPersistenceManager implements PersistenceManager, WLCMPPersistenceManager {
   private static final DebugLogger deploymentLogger;
   private static final DebugLogger runtimeLogger;
   private static final String EOL = "\n";
   private TableVerifier verifier;
   private RDBMSBean rbean = null;
   private int databaseType = 0;
   private Map variable2SQLType = new HashMap();
   private Map variable2nullable = new HashMap();
   private Map fkFieldNullableMap = new HashMap();
   private boolean fkColsNullable = true;
   private String[] snapshotStrings = null;
   private String[] nullSnapshotStrings = null;
   private DataSource ds = null;
   private ClassLoader classLoader = null;
   private BaseEntityManager beanManager = null;
   private short genKeyType;
   private String genKeyWLGeneratorQuery = null;
   private String genKeyWLGeneratorUpdatePrefix = null;
   private String genKeyWLGeneratorUpdate = null;
   private String genKeyGeneratorName = null;
   private Map finderMap;
   private int genKeyCacheSize = 1;
   private int genKeyCurrCacheSize = 0;
   private int genKeyCurrValueInt = 0;
   private long genKeyCurrValueLong = 0L;
   private short genKeyPKFieldClassType;
   private boolean enableBatchOperations = true;
   private boolean orderDatabaseOperations = true;
   private boolean isOptimistic = false;
   private boolean findersReturnNulls = true;
   private int transactionTimeoutSeconds = 0;
   private String dataSourceName = null;
   private String ejbName = null;
   private boolean selectForUpdateSupported = false;
   private boolean selectFirstSeqKeyBeforeUpdate = false;
   private TransactionManager tm = null;
   private String[] verifyText = null;
   private String[] verifyTextWithXLock = null;
   private int[] verifyCount = null;
   private int[] verifyCur = null;
   private static final String ORACLE_JDBC_DRIVER_NAME = "Oracle JDBC driver";
   private String databaseProductName = "";
   private String databaseProductVersion = "";
   private String driverName = "";
   private String driverVersion = "";
   private int driverMajorVersion = -1;
   private int driverMinorVersion = -1;
   private boolean initialized = false;
   private static final byte[] byteArray;
   private static final char[] charArray;

   public void setup(BeanManager beanManager) throws Exception {
      if (deploymentLogger.isDebugEnabled()) {
         debugDeployment("RDBMSPersistenceManager.setup");
      }

      if (!this.initialized) {
         assert this.rbean != null : "rbean != null failed";

         this.beanManager = (BaseEntityManager)beanManager;
         this.classLoader = this.beanManager.getBeanInfo().getClassLoader();
         if ("MetaData".equalsIgnoreCase(this.getValidateDbSchemaWith())) {
            this.verifier = new TableVerifierMetaData();
         } else {
            this.verifier = new TableVerifierSqlQuery();
         }

         this.enableBatchOperations = this.rbean.getEnableBatchOperations();
         this.orderDatabaseOperations = this.rbean.getOrderDatabaseOperations();
         this.isOptimistic = this.rbean().getCMPBeanDescriptor().isOptimistic();
         this.transactionTimeoutSeconds = this.rbean().getCMPBeanDescriptor().getTransactionTimeoutSeconds();
         this.dataSourceName = this.rbean().getDataSourceName();
         this.ejbName = this.rbean().getEjbName();
         this.databaseType = this.rbean.getDatabaseType();
         this.findersReturnNulls = this.rbean.isFindersReturnNulls();
         Context ctx = new InitialContext();

         try {
            this.ds = (DataSource)ctx.lookup("java:/app/jdbc/" + this.dataSourceName);
         } catch (NamingException var7) {
            try {
               this.ds = (DataSource)ctx.lookup(this.dataSourceName);
            } catch (NamingException var6) {
               Loggable l = EJBLogger.logDataSourceNotFoundLoggable(this.dataSourceName);
               throw new WLDeploymentException(l.getMessageText());
            }
         }

         this.verifyTXDataSource();
         this.tm = TransactionService.getTransactionManager();
         this.genKeyType = this.rbean().getGenKeyType();
         this.verifyDatabaseType();
         this.verifyTablesExist();
         this.verifyBatchUpdatesSupported();
         this.selectForUpdateSupported = this.verifySelectForUpdateSupported();
         if (this.rbean.getUseSelectForUpdate() && !this.selectForUpdateSupported) {
            Loggable l = EJBLogger.logselectForUpdateNotSupportedLoggable(this.ejbName);
            throw new WLDeploymentException(l.getMessageText());
         } else {
            this.populateSnapShotStrings();
            this.populateFieldSQLTypeMap();
            this.populateVerifyRows();
            this.genKeySetup();
            this.sqlFinderSetup();
            this.initialized = true;
            this.initializeDBProductAndDriverInfo();
         }
      }
   }

   private void sqlFinderSetup() throws WLDeploymentException {
      Iterator it = this.rbean.getFinders();

      while(it.hasNext()) {
         Finder finder = (Finder)it.next();
         if (this.finderMap == null) {
            this.finderMap = new ConcurrentHashMap();
         }

         if (finder instanceof SqlFinder) {
            SqlFinder sqlFinder = (SqlFinder)finder;
            sqlFinder.setup(this.databaseType);
            if (deploymentLogger.isDebugEnabled()) {
               debugDeployment("EJB-" + this.rbean.getEjbName() + ": add a method to map- " + sqlFinder.getName());
               debugDeployment("method- " + sqlFinder.getMethod());
            }

            if (sqlFinder.isSelect()) {
               this.finderMap.put(this.rbean.getBeanInterfaceMethod(sqlFinder.getMethod()), sqlFinder);
            } else {
               this.finderMap.put(sqlFinder.getMethod(), sqlFinder);
               if (sqlFinder.getSecondMethod() != null) {
                  this.finderMap.put(sqlFinder.getSecondMethod(), sqlFinder);
               }
            }
         }
      }

   }

   public boolean isFindersReturnNulls() {
      return this.findersReturnNulls;
   }

   public Object findByPrimaryKey(EntityBean bean, Method finderMethod, Object pk) throws Throwable {
      if (runtimeLogger.isDebugEnabled()) {
         debugRuntime("RDBMSPersistenceManager.findByPrimaryKey");
      }

      Debug.assertion(bean != null);
      Debug.assertion(pk != null);
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

   public EntityBean findByPrimaryKeyLoadBean(EntityBean bean, Method finderMethod, Object pk) throws Throwable {
      if (runtimeLogger.isDebugEnabled()) {
         debugRuntime("RDBMSPersistenceManager.findByPrimaryKeyLoadBean");
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
         debugRuntime("RDBMSPersistenceManager.scalarFinder");
      }

      try {
         if (runtimeLogger.isDebugEnabled()) {
            debugRuntime("---------------------------searching for method- " + finderMethod);
         }

         SqlFinder finder = (SqlFinder)this.finderMap.get(finderMethod);
         return finder != null ? this.processSqlFinder(finder, args, ((WLEntityBean)bean).__WL_getIsLocal()) : finderMethod.invoke(bean, args);
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
         debugRuntime("RDBMSPersistenceManager.scalarFinderLoadBean");
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
         debugRuntime("RDBMSPersistenceManager.enumFinder");
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
         debugRuntime("RDBMSPersistenceManager.collectionFinder");
      }

      try {
         if (runtimeLogger.isDebugEnabled()) {
            debugRuntime("---------------------------searching for method- " + finderMethod);
         }

         SqlFinder finder = (SqlFinder)this.finderMap.get(finderMethod);
         return finder != null ? (Collection)this.processSqlFinder(finder, args, ((WLEntityBean)bean).__WL_getIsLocal()) : (Collection)finderMethod.invoke(bean, args);
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

   public Object dynamicSqlQuery(String sql, Object[] arguments, WLQueryProperties props, boolean isLocal, Class returnType) {
      SqlFinder sqlFinder = null;
      String sqlShapeName = null;
      int maxElements = false;
      boolean includeUpdates = true;
      boolean queryCachingEnabled = false;

      try {
         sqlShapeName = props.getSqlShapeName();
         if (sqlShapeName != null && this.rbean.getSqlShape(sqlShapeName) == null) {
            String candidates = "";
            if (this.rbean.getSqlShapes() != null) {
               Iterator results = this.rbean.getSqlShapes().values().iterator();

               while(results.hasNext()) {
                  SqlShape result = (SqlShape)results.next();
                  candidates = candidates + result.getSqlShapeName();
                  if (results.hasNext()) {
                     candidates = candidates + ", ";
                  }
               }
            }

            throw new EJBException(EJBLogger.logSqlShapeDoesNotExist(this.rbean.getEjbName(), "Dynamic SQL Query", sqlShapeName, candidates));
         }
      } catch (FinderException var20) {
         throw new AssertionError("should never get here");
      }

      int maxElements;
      try {
         maxElements = props.getMaxElements();
      } catch (FinderException var17) {
         throw new AssertionError("should never get here");
      }

      try {
         includeUpdates = props.getIncludeUpdates();
      } catch (FinderException var16) {
         throw new AssertionError("should never get here");
      }

      try {
         if (isLocal && props.isResultTypeRemote()) {
            isLocal = false;
         }
      } catch (FinderException var19) {
         throw new AssertionError("should never get here");
      }

      try {
         queryCachingEnabled = props.getEnableQueryCaching();
      } catch (FinderException var15) {
         throw new AssertionError("should never get here");
      }

      try {
         assert this.rbean != null;

         sqlFinder = new SqlFinder("execute", (Map)null, sqlShapeName, this.rbean);
         sqlFinder.setReturnClassType(returnType);
         sqlFinder.setMaxElements(maxElements);
         sqlFinder.setIncludeUpdates(includeUpdates);
      } catch (InvalidFinderException var18) {
         throw new EJBException(var18);
      }

      sqlFinder.setupDynamic(sql);
      sqlFinder.setQueryCachingEnabled(queryCachingEnabled);

      try {
         return this.processSqlFinder(sqlFinder, arguments, isLocal);
      } catch (FinderException var14) {
         throw new EJBException(var14);
      }
   }

   public Object processSqlFinder(Method method, Object[] args, boolean isLocal) throws FinderException {
      SqlFinder finder = (SqlFinder)this.finderMap.get(method);
      if (finder != null) {
         return this.processSqlFinder(finder, args, isLocal);
      } else {
         throw new AssertionError("no SqlFinder found for method:" + method);
      }
   }

   private Object processSqlFinder(SqlFinder finder, Object[] args, boolean isLocal) throws FinderException {
      Connection connection = null;
      PreparedStatement statement = null;
      ResultSet rs = null;
      if (finder.getIncludeUpdates()) {
         this.flushModifiedBeans();
      }

      try {
         connection = this.getConnection();
         if (runtimeLogger.isDebugEnabled()) {
            debugRuntime(finder.getName() + " got connection.");
         }
      } catch (Exception var50) {
         this.releaseResources(connection, statement, rs);
         Loggable log = EJBLogger.logDuringFindCannotGetConnectionLoggable(finder.getName(), var50.toString(), RDBMSUtils.throwable2StackTrace(var50));
         throw new FinderException(log.getMessageText());
      }

      String[] query = new String[1];

      Loggable log;
      try {
         statement = this.getStatement(connection, finder, query);
      } catch (Exception var43) {
         this.releaseResources(connection, statement, rs);
         log = EJBLogger.logExceptionWhilePrepareingQueryLoggable(finder.getName(), query[0], var43.toString(), RDBMSUtils.throwable2StackTrace(var43));
         throw new FinderException(log.getMessageText());
      }

      try {
         this.setParameters(statement, finder, args);
      } catch (Exception var42) {
         this.releaseResources(connection, statement, rs);
         log = EJBLogger.logErrorSetQueryParametorLoggable(finder.getName(), query[0], var42.toString(), RDBMSUtils.throwable2StackTrace(var42));
         throw new FinderException(log.getMessageText());
      }

      try {
         rs = this.getResultSet(statement, finder);
      } catch (Exception var41) {
         this.releaseResources(connection, statement, rs);
         log = EJBLogger.logErrorExecuteQueryLoggable(finder.getName(), var41.toString(), RDBMSUtils.throwable2StackTrace(var41));
         throw new FinderException(log.getMessageText());
      }

      Object singletonResult = null;
      Collection collectionResult = null;
      WLCachedRowSet rowsetResult = null;
      Collection pkResult = new ArrayList();

      WLCachedRowSet var54;
      try {
         finder.initializeMapping(rs);
         QueryCachingHandler qcHandler = null;
         if (finder.isQueryCachingEnabled()) {
            qcHandler = finder.getQueryCachingHandler(args, (TTLManager)this.getBeanManager());
         } else {
            qcHandler = finder.getQueryCachingHandler((Object[])null, (TTLManager)null);
         }

         if (runtimeLogger.isDebugEnabled()) {
            debugRuntime(finder.getName() + " mappings have been initialized.");
         }

         if (Collection.class.isAssignableFrom(finder.getReturnClassType())) {
            if (runtimeLogger.isDebugEnabled()) {
               debugRuntime(finder.getName() + " is returning a collection.");
            }

            collectionResult = new ArrayList();
         } else if (ResultSet.class.isAssignableFrom(finder.getReturnClassType())) {
            if (runtimeLogger.isDebugEnabled()) {
               debugRuntime(finder.getName() + " is returning a ResultSet.");
            }

            rowsetResult = RowSetFactory.newInstance().newCachedRowSet();
            CachedRowSetMetaData metadata = new CachedRowSetMetaData();
            metadata.setColumnCount(finder.getResultColumnCount());
            rowsetResult.populate(metadata);
            rowsetResult.moveToInsertRow();
         }

         Object[] beans;
         for(; rs.next() && !finder.maxElementsReached(collectionResult, rowsetResult); this.processRelationshipCaching(finder, beans, qcHandler)) {
            if (runtimeLogger.isDebugEnabled()) {
               debugRuntime(finder.getName() + "result set contains data");
            }

            beans = finder.getBeans();
            Object[] primaryKeys = finder.getPrimaryKey();

            try {
               this.processSQLRow(rs, finder, beans, primaryKeys);
               if (runtimeLogger.isDebugEnabled()) {
                  debugRuntime(finder.getName() + " processed row successfully.");
               }
            } catch (Exception var44) {
               finder.releaseBeans(beans, 0);
               throw var44;
            }

            Object[] resultArray = null;
            QueryCacheElement[] qcEntryArray = null;
            QueryCacheElement qcEntry = null;
            QueryCacheElement qcElement = null;

            for(int i = 0; i < beans.length; ++i) {
               Object result = null;
               BaseEntityManager beanManager = finder.getManager(i);
               if (beanManager != null) {
                  CMPBean bean = (CMPBean)beans[i];
                  Object pk = primaryKeys[i];
                  RSInfo rsInfo = new RSInfoImpl(bean, pk);
                  CMPBean beanFromCache = null;

                  try {
                     if (runtimeLogger.isDebugEnabled()) {
                        debugRuntime(finder.getName() + " caching bean " + pk + ".");
                     }

                     beanFromCache = (CMPBean)beanManager.getBeanFromRS(pk, rsInfo);
                     if (beanFromCache != bean) {
                        if (runtimeLogger.isDebugEnabled()) {
                           debugRuntime(finder.getName() + " returning bean " + pk + " to the pool.");
                        }

                        beanManager.releaseBeanToPool((EntityBean)bean);
                        beans[i] = beanFromCache;
                     }
                  } catch (Exception var45) {
                     finder.releaseBeans(beans, i);
                     throw var45;
                  }

                  try {
                     result = beanManager.finderGetEoFromBeanOrPk((EntityBean)beanFromCache, pk, isLocal);
                     if (finder.isQueryCachingEnabled()) {
                        TTLManager romgr = (TTLManager)beanManager;
                        QueryCacheElement qce = new QueryCacheElement(pk, romgr);
                        qce.setIncludable(false);
                        qcHandler.addQueryCachingEntry(romgr, qce);
                        qcEntry = new QueryCacheElement(pk, romgr);
                        qcEntry.setInvalidatable(false);
                     }
                  } catch (Exception var40) {
                     finder.releaseBeans(beans, i + 1);
                     throw var40;
                  }
               } else {
                  result = beans[i];
                  if (finder.isQueryCachingEnabled()) {
                     qcEntry = new QueryCacheElement(beans[i]);
                  }
               }

               if (collectionResult != null) {
                  if (runtimeLogger.isDebugEnabled()) {
                     debugRuntime(finder.getName() + " adding to collection result...." + result);
                  }

                  if (beans.length > 1 && !finder.usesRelationshipCaching()) {
                     if (i == 0) {
                        resultArray = new Object[beans.length];
                        collectionResult.add(resultArray);
                        if (finder.isQueryCachingEnabled()) {
                           qcEntryArray = new QueryCacheElement[beans.length];
                           qcElement = new MultiValueQueryCacheElement(qcEntryArray);
                        }
                     }

                     resultArray[i] = result;
                     if (finder.isQueryCachingEnabled()) {
                        qcEntryArray[i] = qcEntry;
                     }
                  } else if (i == 0) {
                     collectionResult.add(result);
                     if (finder.isQueryCachingEnabled()) {
                        qcElement = qcEntry;
                     }
                  }
               } else if (rowsetResult != null) {
                  if (runtimeLogger.isDebugEnabled()) {
                     debugRuntime(finder.getName() + " adding to ResultSet result. " + result);
                  }

                  if (i == 0 || !finder.usesRelationshipCaching()) {
                     rowsetResult.updateObject(i + 1, result);
                  }
               } else {
                  if (runtimeLogger.isDebugEnabled()) {
                     debugRuntime(finder.getName() + " adding to single result.");
                  }

                  if (i == 0) {
                     if (singletonResult != null) {
                        if (beanManager == null) {
                           if (!singletonResult.equals(result)) {
                              throw new FinderException("Error in '" + finder.getName() + "'.  The finder returns a single value, but multiple rows were returned by the query from the database (" + query[0] + ").");
                           }
                        } else if (singletonResult != result) {
                           throw new FinderException("Error in '" + finder.getName() + "'.  The finder returns a single value, but multiple rows were returned by the query from the database (" + query[0] + ").");
                        }
                     }

                     singletonResult = result;
                     if (finder.isQueryCachingEnabled()) {
                        qcElement = qcEntry;
                     }
                  } else if (!finder.usesRelationshipCaching()) {
                     throw new FinderException("Error in '" + finder.getName() + "'.  The finder returns a single value, but multiple values are selected for each row in the database.  The selected values must be mapped to a single bean or relationship caching must be used.");
                  }
               }
            }

            pkResult.add(primaryKeys);
            if (finder.isQueryCachingEnabled()) {
               qcHandler.addQueryCachingEntry((TTLManager)this.getBeanManager(), (QueryCacheElement)qcElement);
            }

            if (rowsetResult != null) {
               if (runtimeLogger.isDebugEnabled()) {
                  debugRuntime(finder.getName() + " adding row to ResultSet result.");
               }

               rowsetResult.insertRow();
            }
         }

         this.unpin(pkResult, finder);
         qcHandler.putInQueryCache();
         if (collectionResult != null) {
            ArrayList var56 = collectionResult;
            return var56;
         }

         if (rowsetResult == null) {
            if (pkResult.size() == 0) {
               throw new ObjectNotFoundException("Bean not found in " + finder.getName() + ".");
            }

            Object var55 = singletonResult;
            return var55;
         }

         rowsetResult.moveToCurrentRow();
         var54 = rowsetResult;
      } catch (SQLException var46) {
         throw new FinderException("Exception in finder " + finder.getName() + " while using result set: '" + rs + "'" + "\n" + var46.toString() + "\n" + RDBMSUtils.throwable2StackTrace(var46));
      } catch (ObjectNotFoundException var47) {
         throw var47;
      } catch (Exception var48) {
         throw new FinderException("Exception executing finder " + finder.getName() + " : " + "\n" + var48.toString() + "\n" + RDBMSUtils.throwable2StackTrace(var48));
      } finally {
         this.releaseResources(connection, statement, rs);
      }

      return var54;
   }

   private void unpin(Collection pkResult, SqlFinder finder) {
      Object pinner = this.beanManager.getInvokeTxOrThread();
      Iterator pkIterator = pkResult.iterator();

      while(pkIterator.hasNext()) {
         Object[] pk = (Object[])((Object[])pkIterator.next());

         for(int i = 0; i < pk.length; ++i) {
            BaseEntityManager manager = finder.getManager(i);
            if (manager != null) {
               manager.unpin(pinner, pk[i]);
            }
         }
      }

   }

   private ResultSet getResultSet(PreparedStatement statement, SqlFinder finder) throws SQLException {
      ResultSet result = null;
      if (finder.usesStoredFunction()) {
         CallableStatement callable = (CallableStatement)statement;
         callable.execute();
         result = (ResultSet)callable.getObject(1);
      } else {
         result = statement.executeQuery();
      }

      return result;
   }

   private PreparedStatement getStatement(Connection connection, SqlFinder finder, String[] query) throws SQLException {
      PreparedStatement result = null;
      int selectForUpdate = this.getSelectForUpdateValue();
      query[0] = finder.getQuery(selectForUpdate);
      if (runtimeLogger.isDebugEnabled()) {
         debugRuntime(finder.getName() + " got query: " + query[0]);
      }

      if (!finder.usesStoredProcedure() && !finder.usesStoredFunction()) {
         result = connection.prepareStatement(query[0]);
      } else {
         result = connection.prepareCall(query[0]);
      }

      if (runtimeLogger.isDebugEnabled()) {
         debugRuntime(finder.getName() + " got PreparedStatement.");
      }

      return (PreparedStatement)result;
   }

   private void setParameters(PreparedStatement statement, SqlFinder finder, Object[] args) throws SQLException {
      int queryParamIndex = 1;
      if (finder.usesStoredFunction()) {
         if (runtimeLogger.isDebugEnabled()) {
            debugRuntime(finder.getName() + " setting out parameter for stored function.");
         }

         CallableStatement callable = (CallableStatement)statement;
         switch (this.databaseType) {
            case 1:
               if (runtimeLogger.isDebugEnabled()) {
                  debugRuntime(finder.getName() + " setting output parameter for Oracle.");
               }

               callable.registerOutParameter(queryParamIndex, -10);
               ++queryParamIndex;
               break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            default:
               throw new EJBException("Attempt to use stored function in '" + finder.getName() + "'.  Stored functions are only supported for Oracle.");
         }
      }

      for(int currentParam = 0; currentParam < finder.getNumQueryParams(); ++queryParamIndex) {
         int methodParamIndex = finder.getMethodIndex(currentParam);
         if (runtimeLogger.isDebugEnabled()) {
            debugRuntime("------------------------------------" + finder.getName() + ": query parameter" + queryParamIndex + ": method parameter" + methodParamIndex + " bound with value :" + args[methodParamIndex]);
         }

         if (args[methodParamIndex] != null && args[methodParamIndex].getClass().equals(Character.class)) {
            statement.setString(queryParamIndex, args[methodParamIndex].toString());
         } else {
            statement.setObject(queryParamIndex, args[methodParamIndex]);
         }

         ++currentParam;
      }

      if (runtimeLogger.isDebugEnabled()) {
         debugRuntime(finder.getName() + " done setting input parameters");
      }

   }

   private void processSQLRow(ResultSet rs, SqlFinder finder, Object[] beans, Object[] primaryKeys) throws SQLException, IOException, IllegalArgumentException, IllegalAccessException, ClassNotFoundException {
      if (runtimeLogger.isDebugEnabled()) {
         debugRuntime(finder.getName() + " processing row: column count-" + finder.getColumnCount());
      }

      for(int column = 0; column < finder.getColumnCount(); ++column) {
         int resultIndex = finder.getResultIndex(column);
         if (runtimeLogger.isDebugEnabled()) {
            debugRuntime(finder.getName() + " results index- " + resultIndex);
         }

         Object columnValue = this.processSqlColumn(finder, rs, column, (CMPBean)beans[resultIndex]);
         if (beans[resultIndex] == null) {
            beans[resultIndex] = columnValue;
         } else if (finder.setsPrimaryKey(column)) {
            if (primaryKeys[resultIndex] == null) {
               primaryKeys[resultIndex] = columnValue;
            } else {
               Field pkField = finder.getPrimaryKeyField(column);
               pkField.set(primaryKeys[resultIndex], columnValue);
               Debug.assertion(columnValue != null);
            }
         }
      }

   }

   private void processRelationshipCaching(SqlFinder finder, Object[] beans, QueryCachingHandler qcHandler) {
      if (finder.usesRelationshipCaching()) {
         int relationCount = finder.getRelationCount();
         int[] relationIndex1 = finder.getRelationIndex1();
         int[] relationIndex2 = finder.getRelationIndex2();
         Method[] relationMethod1 = finder.getRelationMethod1();
         Method[] relationMethod2 = finder.getRelationMethod2();

         for(int i = 0; i < relationCount; ++i) {
            Object bean1 = beans[relationIndex1[i]];
            Object bean2 = beans[relationIndex2[i]];
            Object proxy1 = bean1;
            Object proxy2 = bean2;
            RDBMSBean rdbmsBean1 = finder.getRDBMSBean(relationIndex1[i]);
            RDBMSBean rdbmsBean2 = finder.getRDBMSBean(relationIndex2[i]);
            if (rdbmsBean2.getCMPBeanDescriptor().isBeanClassAbstract()) {
               proxy2 = ((EntityEJBContextImpl)((CMPBean)bean2).__WL_getEntityContext()).__WL_getEJBLocalObject();
            }

            try {
               if (runtimeLogger.isDebugEnabled()) {
                  debugRuntime("------------------setting relationship between " + rdbmsBean1.getEjbName() + " and " + rdbmsBean2.getEjbName());
                  debugRuntime("------------------ primary key " + ((CMPBean)bean1).__WL_getPrimaryKey());
                  debugRuntime("------------------ primary key " + ((CMPBean)bean2).__WL_getPrimaryKey());
               }

               relationMethod1[i].invoke(bean1, proxy2);
            } catch (Exception var23) {
               throw new AssertionError("exception while invoking method-" + DDUtils.getMethodSignature(relationMethod1[i]), var23);
            }

            if (rdbmsBean1.getCMPBeanDescriptor().isBeanClassAbstract()) {
               proxy1 = ((EntityEJBContextImpl)((CMPBean)bean1).__WL_getEntityContext()).__WL_getEJBLocalObject();
            }

            try {
               relationMethod2[i].invoke(bean2, proxy1);
            } catch (Exception var22) {
               throw new AssertionError("exception while invoking method-" + DDUtils.getMethodSignature(relationMethod2[i]), var22);
            }

            String finderName;
            Object[] args;
            TTLManager manager;
            Object pk;
            QueryCacheKey qck;
            QueryCacheElement qce;
            if (finder.getCmrFieldFinderMethodName1(i) != null) {
               finderName = finder.getCmrFieldFinderMethodName1(i);
               args = new Object[]{((CMPBean)bean1).__WL_getPrimaryKey()};
               manager = (TTLManager)finder.getManager(relationIndex2[i]);
               pk = ((CMPBean)bean2).__WL_getPrimaryKey();
               qck = new QueryCacheKey(finderName, args, manager, finder.getCmrFieldFinderReturnType1(i));
               qce = new QueryCacheElement(pk, manager);
               qcHandler.addQueryCachingEntry(manager, qck, qce);
            }

            if (finder.getCmrFieldFinderMethodName2(i) != null) {
               finderName = finder.getCmrFieldFinderMethodName2(i);
               args = new Object[]{((CMPBean)bean2).__WL_getPrimaryKey()};
               manager = (TTLManager)finder.getManager(relationIndex1[i]);
               pk = ((CMPBean)bean1).__WL_getPrimaryKey();
               qck = new QueryCacheKey(finderName, args, manager, finder.getCmrFieldFinderReturnType2(i));
               qce = new QueryCacheElement(pk, manager);
               qcHandler.addQueryCachingEntry(manager, qck, qce);
            }
         }

      }
   }

   private Object processSqlColumn(SqlFinder finder, ResultSet rs, int column, CMPBean targetBean) throws SQLException, IOException, IllegalAccessException, ClassNotFoundException {
      Class targetClass = finder.getColumnClass(column);
      if (runtimeLogger.isDebugEnabled()) {
         debugRuntime(finder.getName() + " targetClass-" + targetClass);
      }

      int jdbcColumn = column + 1;
      Object columnValue = null;
      byte[] rawBytes = null;
      String value;
      if (targetClass.isPrimitive()) {
         if (targetClass == Boolean.TYPE) {
            columnValue = new Boolean(rs.getBoolean(jdbcColumn));
         } else if (targetClass == Byte.TYPE) {
            columnValue = new Byte(rs.getByte(jdbcColumn));
         } else if (targetClass == Character.TYPE) {
            value = rs.getString(jdbcColumn);
            if (!rs.wasNull() && value != null && value.length() != 0) {
               columnValue = new Character(value.charAt(0));
            } else {
               columnValue = new Character('\u0000');
            }
         } else if (targetClass == Short.TYPE) {
            columnValue = new Short(rs.getShort(jdbcColumn));
         } else if (targetClass == Integer.TYPE) {
            columnValue = new Integer(rs.getInt(jdbcColumn));
         } else if (targetClass == Long.TYPE) {
            columnValue = new Long(rs.getLong(jdbcColumn));
         } else if (targetClass == Float.TYPE) {
            columnValue = new Float(rs.getFloat(jdbcColumn));
         } else if (targetClass == Double.TYPE) {
            columnValue = new Double(rs.getDouble(jdbcColumn));
         }
      } else if (targetClass == String.class) {
         if (finder.isClobColumn(column)) {
            columnValue = this.processClobColumn(targetClass, jdbcColumn, rs);
         } else {
            columnValue = rs.getString(jdbcColumn);
            if (rs.wasNull()) {
               columnValue = null;
            }
         }
      } else if (targetClass == BigDecimal.class) {
         columnValue = rs.getBigDecimal(jdbcColumn);
         if (rs.wasNull()) {
            columnValue = null;
         }
      } else if (targetClass == Boolean.class) {
         boolean value = rs.getBoolean(jdbcColumn);
         if (rs.wasNull()) {
            columnValue = null;
         } else {
            columnValue = new Boolean(value);
         }
      } else {
         int value;
         if (targetClass == Byte.class) {
            value = rs.getByte(jdbcColumn);
            if (rs.wasNull()) {
               columnValue = null;
            } else {
               columnValue = new Byte((byte)value);
            }
         } else if (targetClass == byteArray.getClass()) {
            if (finder.isBlobColumn(column)) {
               columnValue = this.processBlobColumn(targetClass, jdbcColumn, rs);
            } else {
               columnValue = rs.getBytes(jdbcColumn);
               if (rs.wasNull()) {
                  columnValue = null;
               }
            }
         } else if (targetClass == Character.class) {
            value = rs.getString(jdbcColumn);
            if (!rs.wasNull() && value != null && value.length() != 0) {
               columnValue = new Character(value.charAt(0));
            } else {
               columnValue = null;
            }
         } else if (targetClass == Date.class) {
            columnValue = rs.getDate(jdbcColumn);
            if (rs.wasNull()) {
               columnValue = null;
            }
         } else if (targetClass == Double.class) {
            double value = rs.getDouble(jdbcColumn);
            if (rs.wasNull()) {
               columnValue = null;
            } else {
               columnValue = new Double(value);
            }
         } else if (targetClass == Float.class) {
            float value = rs.getFloat(jdbcColumn);
            if (rs.wasNull()) {
               columnValue = null;
            } else {
               columnValue = new Float(value);
            }
         } else if (targetClass == Integer.class) {
            value = rs.getInt(jdbcColumn);
            if (rs.wasNull()) {
               columnValue = null;
            } else {
               columnValue = new Integer(value);
            }
         } else if (targetClass == Long.class) {
            long value = rs.getLong(jdbcColumn);
            if (rs.wasNull()) {
               columnValue = null;
            } else {
               columnValue = new Long(value);
            }
         } else if (targetClass == Short.class) {
            short value = rs.getShort(jdbcColumn);
            if (rs.wasNull()) {
               columnValue = null;
            } else {
               columnValue = new Short(value);
            }
         } else if (targetClass == Time.class) {
            columnValue = rs.getTime(jdbcColumn);
            if (rs.wasNull()) {
               columnValue = null;
            }
         } else if (targetClass == java.util.Date.class) {
            Timestamp timestamp = rs.getTimestamp(jdbcColumn);
            if (!rs.wasNull() && timestamp != null) {
               columnValue = new java.util.Date(timestamp.getTime());
            } else {
               columnValue = null;
            }
         } else if (finder.isCharArrayMappedToString(targetClass)) {
            if (finder.isClobColumn(column)) {
               columnValue = this.processClobColumn(targetClass, jdbcColumn, rs);
            } else {
               value = rs.getString(jdbcColumn);
               if (!rs.wasNull() && value != null) {
                  columnValue = value.toCharArray();
               } else {
                  columnValue = null;
               }
            }
         } else if (targetBean == null) {
            columnValue = rs.getObject(jdbcColumn);
            if (rs.wasNull()) {
               columnValue = null;
            }
         } else if (finder.isBlobColumn(column)) {
            columnValue = this.processBlobColumn(targetClass, jdbcColumn, rs);
         } else {
            rawBytes = rs.getBytes(jdbcColumn);
            if (!rs.wasNull() && rawBytes != null && rawBytes.length != 0) {
               ByteArrayInputStream bstr = new ByteArrayInputStream(rawBytes);
               RDBMSObjectInputStream ostr = new RDBMSObjectInputStream(bstr, this.classLoader);
               columnValue = ostr.readObject();
            } else {
               columnValue = null;
               rawBytes = null;
            }
         }
      }

      if (runtimeLogger.isDebugEnabled()) {
         if (columnValue != null) {
            debugRuntime(finder.getName() + " processing column-" + column + " value- " + columnValue + "type- " + columnValue.getClass().getName());
         } else {
            debugRuntime(finder.getName() + " processing column-" + column + " value- null ");
         }
      }

      if (targetBean != null) {
         Field optimisticField;
         if (finder.hasField(column)) {
            optimisticField = finder.getField(column);
            if (runtimeLogger.isDebugEnabled()) {
               debugRuntime(finder.getName() + " setting field: field- " + optimisticField.getName() + " bean- " + targetBean.getClass().getName());
            }

            optimisticField.set(targetBean, columnValue);
         } else {
            Method targetMethod = finder.getMethod(column);

            try {
               targetMethod.invoke(targetBean, columnValue);
            } catch (InvocationTargetException var11) {
               throw new AssertionError("exception while invoking method-" + targetMethod);
            }
         }

         if (finder.isOptimistic(column)) {
            optimisticField = finder.getOptimisticField(column);
            if (rawBytes == null) {
               if (java.util.Date.class.isAssignableFrom(targetClass) && columnValue != null) {
                  optimisticField.set(targetBean, ((java.util.Date)columnValue).clone());
               } else if (targetClass == byteArray.getClass() && columnValue != null) {
                  optimisticField.set(targetBean, ((byte[])((byte[])columnValue)).clone());
               } else if (targetClass == charArray.getClass() && columnValue != null) {
                  optimisticField.set(targetBean, ((char[])((char[])columnValue)).clone());
               } else {
                  optimisticField.set(targetBean, columnValue);
               }
            } else {
               optimisticField.set(targetBean, rawBytes);
            }
         }

         targetBean.__WL_setLoaded(finder.getIsLoadedIndex(column), true);
      }

      return columnValue;
   }

   private Object processBlobColumn(Class targetClass, int jdbcColumn, ResultSet rs) throws SQLException, IOException, ClassNotFoundException {
      Object columnValue = null;

      try {
         Blob lob = rs.getBlob(jdbcColumn);
         if (lob == null) {
            if (runtimeLogger.isDebugEnabled()) {
               debugRuntime("got NULL Blob, set result to null.");
            }
         } else {
            int length = (int)lob.length();
            if (length == 0) {
               if (runtimeLogger.isDebugEnabled()) {
                  debugRuntime("got zero length Blob");
               }

               if (byteArray.getClass().equals(targetClass)) {
                  columnValue = new byte[0];
               }
            } else {
               byte[] inByteArray = new byte[length];
               if (runtimeLogger.isDebugEnabled()) {
                  debugRuntime("got: " + length + " length Blob, now read data.");
               }

               InputStream is = lob.getBinaryStream();
               is.read(inByteArray);
               is.close();
               if (byteArray.getClass().equals(targetClass)) {
                  columnValue = inByteArray;
               } else {
                  ByteArrayInputStream bais = new ByteArrayInputStream(inByteArray, 0, length);
                  ObjectInputStream ois = new FilteringObjectInputStream(bais);

                  try {
                     columnValue = ois.readObject();
                  } catch (ClassNotFoundException var17) {
                     if (runtimeLogger.isDebugEnabled()) {
                        debugRuntime("ClassNotFoundException for Blob" + var17.getMessage());
                     }

                     throw var17;
                  } finally {
                     bais.close();
                     ois.close();
                  }
               }
            }
         }

         return columnValue;
      } catch (IOException var19) {
         if (runtimeLogger.isDebugEnabled()) {
            debugRuntime("IOException for Blob" + var19.getMessage());
         }

         throw var19;
      } catch (SQLException var20) {
         if (runtimeLogger.isDebugEnabled()) {
            debugRuntime("SQLException for Blob" + var20.getMessage());
         }

         throw var20;
      }
   }

   private Object processClobColumn(Class targetClass, int jdbcColumn, ResultSet rs) throws SQLException, IOException {
      Object columnValue = null;

      try {
         Clob lob = rs.getClob(jdbcColumn);
         if (lob == null) {
            if (runtimeLogger.isDebugEnabled()) {
               debugRuntime("got NULL Clob, set result to null.");
            }
         } else {
            int length = (int)lob.length();
            if (length == 0) {
               if (runtimeLogger.isDebugEnabled()) {
                  debugRuntime("got zero length Clob.");
               }

               if (targetClass == String.class) {
                  columnValue = new String("");
               } else {
                  columnValue = new char[0];
               }
            } else {
               char[] inCharArray = new char[length];
               if (runtimeLogger.isDebugEnabled()) {
                  debugRuntime("got: " + length + " length Clob, now read data.");
               }

               Reader reader = lob.getCharacterStream();
               reader.read(inCharArray);
               reader.close();
               if (targetClass == String.class) {
                  columnValue = new String(inCharArray);
               } else {
                  columnValue = inCharArray;
               }
            }
         }

         return columnValue;
      } catch (IOException var9) {
         if (runtimeLogger.isDebugEnabled()) {
            debugRuntime("IOException for Blob/Clob" + var9.getMessage());
         }

         throw var9;
      } catch (SQLException var10) {
         if (runtimeLogger.isDebugEnabled()) {
            debugRuntime("SQLException for Blob/Clob" + var10.getMessage());
         }

         throw var10;
      }
   }

   public Map collectionFinderLoadBean(EntityBean bean, Method finderMethod, Object[] args) throws Throwable {
      if (runtimeLogger.isDebugEnabled()) {
         debugRuntime("RDBMSPersistenceManager.collectionFinderLoadBean");
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
         if (rsInfo.usesCmpBean()) {
            CMPBean cmpbean = (CMPBean)bean;
            CMPBean beanFromFinder = ((RSInfoImpl)rsInfo).getCmpBean();
            cmpbean.__WL_copyFrom(beanFromFinder, true);
         } else {
            ((CMPBean)bean).__WL_loadGroupByIndex(rsInfo.getGroupIndex(), rsInfo.getRS(), rsInfo.getOffset(), rsInfo.getPK(), bean);
            if (rsInfo.getCmrField() != null) {
               ((CMPBean)bean).__WL_loadCMRFieldByCmrField(rsInfo.getCmrField(), rsInfo.getRS(), rsInfo.getCmrFieldOffset(), bean);
            }
         }
      } catch (Exception var5) {
         EJBRuntimeUtils.throwInternalException("Error load bean states from ResultSet", var5);
      }

   }

   public void updateClassLoader(ClassLoader cl) {
      this.classLoader = cl;
   }

   public void setRdbmsBean(RDBMSBean rbean) {
      assert rbean != null;

      assert rbean.getDataSourceName() != null : "No data source set for this RDBMS bean.";

      assert rbean.getTableName() != null : "No table name set for this RDBMS bean.";

      this.rbean = rbean;
   }

   public void cleanup() {
      if (deploymentLogger.isDebugEnabled()) {
         debugDeployment("**************************************cleanup()- " + this.ejbName);
      }

      assert !((EntityBeanInfo)this.beanManager.getBeanInfo()).isDynamicQueriesEnabled();

      this.rbean.cleanup();
   }

   private RDBMSBean rbean() {
      if (this.rbean == null) {
         throw new AssertionError("Internal error: RDBMSBean is null in RDBMSPersistenceManager()");
      } else {
         return this.rbean;
      }
   }

   public boolean getVerifyReads() {
      return this.rbean().getVerifyReads();
   }

   public void setupParentBeanManagers() {
      Iterator fkFieldNames = this.rbean().getForeignKeyFieldNames().iterator();

      while(true) {
         String fkFieldName;
         do {
            if (!fkFieldNames.hasNext()) {
               return;
            }

            fkFieldName = (String)fkFieldNames.next();
         } while(this.rbean.isManyToManyRelation(fkFieldName));

         RDBMSBean parentBean = this.rbean().getRelatedRDBMSBean(fkFieldName);
         boolean curFkColsNullable = true;
         BaseEntityManager parentBeanManager = parentBean.getRDBMSPersistenceManager().getBeanManager();
         if (!this.isSelfRelationship(fkFieldName)) {
            if (deploymentLogger.isDebugEnabled()) {
               debugDeployment(this.rbean.getEjbName() + ": adding " + parentBean.getEjbName() + " to its parentBeanManager");
            }

            this.beanManager.addParentBeanManager(parentBeanManager);
            parentBeanManager.addChildBeanManager(this.beanManager);
         }

         List fkColNames = this.rbean().getForeignKeyColNames(fkFieldName);
         String tableName = this.rbean().getTableForCmrField(fkFieldName);

         String variable;
         for(Iterator i = fkColNames.iterator(); i.hasNext(); curFkColsNullable &= (Boolean)this.variable2nullable.get(variable)) {
            String fkColName = (String)i.next();
            variable = this.rbean().getVariable(tableName, fkColName);
         }

         this.fkFieldNullableMap.put(fkFieldName, new Boolean(curFkColsNullable));
         this.fkColsNullable &= curFkColsNullable;
         if (!this.isSelfRelationship(fkFieldName) && !curFkColsNullable) {
            if (deploymentLogger.isDebugEnabled()) {
               debugDeployment(this.rbean.getEjbName() + ": adding " + parentBean.getEjbName() + " to its notNullableParentBeanManager");
            }

            this.beanManager.addNotNullableParentBeanManager(parentBeanManager);
            parentBeanManager.addNotNullableChildBeanManager(this.beanManager);
         }
      }
   }

   public boolean isSelfRelationship(String fieldName) {
      return this.rbean().isSelfRelationship(fieldName);
   }

   public boolean isSelfRelationship() {
      return this.rbean().isSelfRelationship();
   }

   public boolean isFkColsNullable(String fkFieldName) {
      return (Boolean)this.fkFieldNullableMap.get(fkFieldName);
   }

   public boolean isFkColsNullable() {
      return this.fkColsNullable;
   }

   public void setCycleExists() {
      this.getBeanManager().setCycleExists(new HashSet());
   }

   public void setupM2NBeanManagers() {
      Iterator it = this.rbean().getAllCmrFields().iterator();

      while(true) {
         while(true) {
            String cmrf;
            do {
               do {
                  if (!it.hasNext()) {
                     return;
                  }

                  cmrf = (String)it.next();
               } while(!this.rbean().isDeclaredField(cmrf));
            } while(!this.rbean().isManyToManyRelation(cmrf));

            RDBMSBean otherBean = this.rbean.getRelatedRDBMSBean(cmrf);
            String otherEjbName = otherBean.getEjbName();
            BaseEntityManager otherBeanManager = otherBean.getRDBMSPersistenceManager().getBeanManager();
            String thisEjbName = this.rbean.getEjbName();
            if (this.rbean().isSymmetricField(cmrf)) {
               if (deploymentLogger.isDebugEnabled()) {
                  debugDeployment("  adding M2N Bean Manager " + this.rbean.getEjbName() + " to exec delayed INSERTS for symmetric cmrf: " + cmrf);
               }

               this.beanManager.addM2NInsertSet(cmrf);
            } else if (this.rbean().equals(otherBean) && this.rbean().isBiDirectional(cmrf)) {
               String otherCmrf = this.rbean().getRelatedFieldName(cmrf);
               if (!this.beanManager.isM2NInsertSet(cmrf) && !this.beanManager.isM2NInsertSet(otherCmrf)) {
                  if (deploymentLogger.isDebugEnabled()) {
                     debugDeployment("  adding M2N Bean Manager " + this.rbean.getEjbName() + " to exec delayed INSERTS for self-reflected none-symmetric cmrf: " + cmrf);
                  }

                  this.beanManager.addM2NInsertSet(cmrf);
               }
            } else if (!this.rbean().isBiDirectional(cmrf)) {
               if (deploymentLogger.isDebugEnabled()) {
                  debugDeployment("  adding M2N Bean Manager '" + this.rbean().getEjbName() + "' to exec delayed INSERTS forunidirectional cmrf: '" + cmrf + "', parent bean manager is: '" + otherEjbName + "'");
               }

               this.beanManager.addM2NInsertSet(cmrf);
               this.beanManager.addParentBeanManager(otherBeanManager);
               otherBeanManager.addChildBeanManager(this.beanManager);
            } else if (thisEjbName.compareTo(otherEjbName) <= 0) {
               if (deploymentLogger.isDebugEnabled()) {
                  debugDeployment("  adding M2N Bean Manager " + thisEjbName + " to exec delayed INSERTS for bidirectional cmrf: " + cmrf + ", parent bean manager is: '" + otherEjbName + "'");
               }

               this.beanManager.addM2NInsertSet(cmrf);
               this.beanManager.addParentBeanManager(otherBeanManager);
               otherBeanManager.addChildBeanManager(this.beanManager);
            } else if (deploymentLogger.isDebugEnabled()) {
               debugDeployment("  " + thisEjbName + "  will defer to other M2N Bean Manager " + otherEjbName + " to exec delayed INSERTS for cmrf: " + cmrf);
            }
         }
      }
   }

   private String getCreateDefaultDBMSTables() {
      return this.rbean().getCreateDefaultDBMSTables();
   }

   private String getValidateDbSchemaWith() {
      return this.rbean().getValidateDbSchemaWith();
   }

   private void populateVerifyRows() {
      if (this.isOptimistic && this.rbean().getVerifyReads() || this.needsBatchOperationsWorkaround()) {
         if (!this.selectForUpdateSupported) {
            EJBLogger.logAnomalousRRBehaviorPossible(this.ejbName);
         }

         this.verifyText = new String[this.rbean().tableCount()];
         this.verifyTextWithXLock = new String[this.rbean().tableCount()];
         this.verifyCount = new int[this.rbean().tableCount()];
         this.verifyCur = new int[this.rbean().tableCount()];

         int i;
         for(i = 0; i < this.rbean().tableCount(); ++i) {
            String tableName = this.rbean().tableAt(i);
            this.verifyText[i] = "SELECT 7 FROM " + tableName;
            this.verifyTextWithXLock[i] = this.verifyText[i];
            StringBuilder var10000;
            String[] var10002;
            if (this.selectForUpdateSupported) {
               switch (this.databaseType) {
                  case 2:
                  case 7:
                     var10000 = new StringBuilder();
                     var10002 = this.verifyTextWithXLock;
                     var10002[i] = var10000.append(var10002[i]).append(" WITH(UPDLOCK) ").toString();
                     break;
                  case 5:
                     var10000 = new StringBuilder();
                     var10002 = this.verifyTextWithXLock;
                     var10002[i] = var10000.append(var10002[i]).append(" HOLDLOCK ").toString();
               }
            }

            var10000 = new StringBuilder();
            var10002 = this.verifyText;
            var10002[i] = var10000.append(var10002[i]).append(" WHERE ").toString();
            var10000 = new StringBuilder();
            var10002 = this.verifyTextWithXLock;
            var10002[i] = var10000.append(var10002[i]).append(" WHERE ").toString();
            this.verifyCount[i] = 0;
            this.verifyCur[i] = 1;
         }

         if (deploymentLogger.isDebugEnabled()) {
            for(i = 0; i < this.verifyText.length; ++i) {
               debugDeployment("verifyText[" + i + "]: " + this.verifyText[i]);
               debugDeployment("verifyTextWithXLock[" + i + "]: " + this.verifyTextWithXLock[i]);
            }
         }
      }

   }

   private void populateSnapShotStrings() {
      List cmpFields = this.rbean().getCmpFieldNames();
      List fkFields = this.rbean().getForeignKeyFieldNames();
      if (this.rbean().getCMPBeanDescriptor().isOptimistic()) {
         this.snapshotStrings = new String[cmpFields.size() + fkFields.size()];
         this.nullSnapshotStrings = new String[cmpFields.size() + fkFields.size()];
      }

      Iterator fields = cmpFields.iterator();

      int i;
      String fkField;
      for(i = 0; fields.hasNext(); ++i) {
         fkField = (String)fields.next();
         String cmpColumn = this.rbean().getCmpColumnForField(fkField);
         if (this.rbean().getCMPBeanDescriptor().isOptimistic()) {
            this.snapshotStrings[i] = cmpColumn + " = ?";
            this.nullSnapshotStrings[i] = cmpColumn + " is null";
         }
      }

      fields = fkFields.iterator();

      while(true) {
         do {
            do {
               if (!fields.hasNext()) {
                  return;
               }

               fkField = (String)fields.next();
            } while(!this.rbean().containsFkField(fkField));
         } while(this.rbean().isForeignCmpField(fkField));

         Iterator fkColumns = this.rbean().getForeignKeyColNames(fkField).iterator();
         if (this.rbean().getCMPBeanDescriptor().isOptimistic()) {
            this.snapshotStrings[i] = "";
            this.nullSnapshotStrings[i] = "";
         }

         while(fkColumns.hasNext()) {
            String fkColumn = (String)fkColumns.next();
            if (this.rbean().getCMPBeanDescriptor().isOptimistic()) {
               StringBuilder var10000 = new StringBuilder();
               String[] var10002 = this.snapshotStrings;
               var10002[i] = var10000.append(var10002[i]).append(fkColumn).append(" = ?").toString();
               var10000 = new StringBuilder();
               var10002 = this.nullSnapshotStrings;
               var10002[i] = var10000.append(var10002[i]).append(fkColumn).append(" is null").toString();
               if (fkColumns.hasNext()) {
                  var10000 = new StringBuilder();
                  var10002 = this.snapshotStrings;
                  var10002[i] = var10000.append(var10002[i]).append(" AND ").toString();
                  var10000 = new StringBuilder();
                  var10002 = this.nullSnapshotStrings;
                  var10002[i] = var10000.append(var10002[i]).append(" AND ").toString();
               }
            }
         }

         ++i;
      }
   }

   private List getJoinTableColumns(String joinTable) {
      if (!this.rbean().isJoinTable(joinTable)) {
         throw new AssertionError(" Bean: '" + this.ejbName + "', passed in table name: '" + joinTable + "'.  We were expecting a JoinTable but apparently this isn't a JoinTable.");
      } else {
         List joinTableColList = new ArrayList();
         String cmrField = this.rbean().getCmrFieldForJoinTable(joinTable);
         Iterator it = this.rbean().getFkColumn2ClassMapForFkField(cmrField).keySet().iterator();

         while(it.hasNext()) {
            joinTableColList.add((String)it.next());
         }

         String colName;
         if (this.rbean().isRemoteField(cmrField)) {
            colName = this.rbean().getRemoteColumn(cmrField);
            joinTableColList.add(colName);
         } else if (this.rbean().isSymmetricField(cmrField)) {
            it = this.rbean().getSymmetricColumn2FieldName(cmrField).keySet().iterator();

            while(it.hasNext()) {
               colName = (String)it.next();
               joinTableColList.add(colName);
            }
         } else {
            RDBMSBean relatedBean = this.rbean().getRelatedRDBMSBean(cmrField);
            String relatedCMRfield = this.rbean().getRelatedFieldName(cmrField);
            it = relatedBean.getFkColumn2ClassMapForFkField(relatedCMRfield).keySet().iterator();

            while(it.hasNext()) {
               String dbmsCol = (String)it.next();
               joinTableColList.add(dbmsCol);
            }
         }

         return joinTableColList;
      }
   }

   private void populateFieldSQLTypeMap() throws WLDeploymentException {
      if (deploymentLogger.isDebugEnabled()) {
         debugDeployment("RDBMSPersistenceManager.populateFieldSQLTypeMap");
      }

      if (this.variable2SQLType.size() <= 0) {
         Connection conn = null;

         try {
            Loggable l;
            try {
               conn = this.getConnection();
               Iterator it = this.rbean().getTables().iterator();

               while(it.hasNext()) {
                  String tableName = (String)it.next();
                  tableName = RDBMSUtils.escQuotedID(tableName);
                  if (deploymentLogger.isDebugEnabled()) {
                     debugDeployment(" populateFieldSQLTypeMap call verify on Table: '" + tableName + "'");
                  }

                  List variables = new ArrayList();
                  List columns = new ArrayList();
                  this.rbean.computeVariablesAndColumns(tableName, variables, columns, (Map)null);
                  this.verifier.verifyOrCreateOrAlterTable(this, conn, tableName, columns, true, variables, this.variable2SQLType, this.variable2nullable, (String)null, false);
               }

               if (this.variable2SQLType.size() <= 0) {
                  l = EJBLogger.logCouldNotInitializeFieldSQLTypeMapWithoutExceptionLoggable();
                  throw new WLDeploymentException(l.getMessageText());
               }
            } catch (Exception var9) {
               l = EJBLogger.logCouldNotInitializeFieldSQLTypeMapLoggable(var9);
               throw new WLDeploymentException(l.getMessageText(), var9);
            }
         } finally {
            this.releaseResources(conn, (Statement)null, (ResultSet)null);
         }

      }
   }

   private void verifyTablesExist() throws WLDeploymentException {
      Connection conn = null;

      try {
         conn = this.getConnection();
         Iterator it = this.rbean().getTables().iterator();

         while(it.hasNext()) {
            String tableName = (String)it.next();
            tableName = RDBMSUtils.escQuotedID(tableName);
            if (deploymentLogger.isDebugEnabled()) {
               debugDeployment(" verifyTablesExist call verify on Main Table: '" + tableName + "'");
            }

            List variables = new ArrayList();
            List columns = new ArrayList();
            this.rbean.computeVariablesAndColumns(tableName, variables, columns, (Map)null);
            boolean tableHasTrigger = this.rbean().getTriggerUpdatesOptimisticColumn(tableName);
            this.verifier.verifyOrCreateOrAlterTable(this, conn, tableName, columns, true, variables, this.variable2SQLType, this.variable2nullable, this.rbean().getCreateDefaultDBMSTables(), tableHasTrigger);
         }

         String joinTableName;
         for(Iterator jit = this.rbean().getJoinTableMap().values().iterator(); jit.hasNext(); this.verifier.verifyOrCreateOrAlterTable(this, conn, joinTableName, this.getJoinTableColumns(joinTableName), false, new ArrayList(), (Map)null, (Map)null, this.rbean().getCreateDefaultDBMSTables(), false)) {
            joinTableName = (String)jit.next();
            if (deploymentLogger.isDebugEnabled()) {
               debugDeployment(" verifyTablesExist call verify on Join Table: '" + joinTableName + "'");
            }
         }

      } catch (WLDeploymentException var11) {
         throw var11;
      } catch (Exception var12) {
         EJBLogger.logStackTraceAndMessage(var12.getMessage(), var12);
         throw new WLDeploymentException(var12.getMessage(), var12);
      } finally {
         this.releaseResources(conn, (Statement)null, (ResultSet)null);
      }
   }

   private boolean verifySelectForUpdateSupported() {
      Connection conn = null;
      Statement stmt = null;
      boolean result = false;

      try {
         try {
            conn = this.getConnection();
            stmt = conn.createStatement();
            switch (this.databaseType) {
               case 1:
                  stmt.executeQuery("SELECT 7 FROM " + this.rbean().getTableName() + " WHERE ROWNUM < 1 FOR UPDATE");
                  break;
               case 2:
               case 7:
                  stmt.executeQuery("SELECT 7 FROM " + this.rbean().getTableName() + " WITH(UPDLOCK) WHERE (1=0)");
                  break;
               case 3:
               case 6:
               case 8:
               case 10:
               default:
                  stmt.executeQuery("SELECT 7 FROM " + this.rbean().getTableName() + " WHERE (1=0) FOR UPDATE");
                  break;
               case 4:
               case 9:
                  if (!this.rbean.getUseSelectForUpdate() && !this.isOptimistic) {
                     boolean var11 = false;
                     return var11;
                  }

                  Iterator pkIterator = this.rbean().getCMPBeanDescriptor().getPrimaryKeyFieldNames().iterator();
                  String whereClause = this.rbean().getColumnForCmpFieldAndTable((String)pkIterator.next(), this.rbean().getTableName()) + " is null";
                  stmt.executeQuery("SELECT 7 FROM " + this.rbean().getTableName() + " WHERE " + whereClause + " FOR UPDATE");
                  break;
               case 5:
                  stmt.executeQuery("SELECT 7 FROM " + this.rbean().getTableName() + " HOLDLOCK WHERE (1=0)");
            }

            result = true;
         } catch (SQLException var9) {
            if (deploymentLogger.isDebugEnabled()) {
               debugDeployment("Exception while verifying select for update support: " + var9.getMessage());
               return result;
            }
         }

         return result;
      } finally {
         this.releaseResources(conn, stmt, (ResultSet)null);
      }
   }

   private void genKeySetup() throws WLDeploymentException {
      if (this.rbean().hasAutoKeyGeneration()) {
         this.genKeyPKFieldClassType = this.rbean().getGenKeyPKFieldClassType();
         this.genKeyWLGeneratorQuery = this.rbean().getGenKeyGeneratorQuery();
         Connection conn;
         Loggable l;
         if (this.genKeyType == 2) {
            this.genKeyGeneratorName = this.rbean().getGenKeyGeneratorName();
            this.genKeyCacheSize = this.rbean().getGenKeyCacheSize();
            this.genKeyCurrCacheSize = 0;
            conn = null;

            try {
               conn = this.getConnection();
               this.genKeyGeneratorName = this.verifier.verifyOrCreateOrAlterSequence(conn, this.genKeyGeneratorName, this.genKeyCacheSize, this.rbean().getCreateDefaultDBMSTables(), this.databaseType);
               if (deploymentLogger.isDebugEnabled()) {
                  debugDeployment("RDBMSPersistenceManager will use sequence: '" + this.genKeyGeneratorName + "'");
               }

               switch (this.databaseType) {
                  case 1:
                     this.genKeyWLGeneratorQuery = this.rbean.getOracleSequenceGeneratorQuery(this.genKeyGeneratorName);
                     break;
                  case 2:
                  default:
                     throw new AssertionError("Database Type: " + DDConstants.getDBNameForType(this.databaseType) + " does not support the SEQUENCE key generator");
                  case 3:
                     this.genKeyWLGeneratorQuery = this.rbean.getInformixSequenceGeneratorQuery(this.genKeyGeneratorName);
                     break;
                  case 4:
                     this.genKeyWLGeneratorQuery = this.rbean.getDB2SequenceGeneratorQuery(this.genKeyGeneratorName);
               }
            } catch (Exception var21) {
               l = EJBLogger.logSequenceSetupFailureLoggable(this.genKeyGeneratorName, Integer.toString(this.genKeyCacheSize), var21.getMessage());
               throw new WLDeploymentException(l.getMessageText(), var21);
            } finally {
               this.releaseResources(conn, (Statement)null, (ResultSet)null);
            }
         } else if (this.genKeyType != 1 && this.genKeyType == 3) {
            this.genKeyCacheSize = this.rbean().getGenKeyCacheSize();
            this.genKeyWLGeneratorUpdatePrefix = this.rbean().getGenKeyGeneratorUpdatePrefix();
            this.genKeyWLGeneratorUpdate = this.genKeyWLGeneratorUpdatePrefix + this.genKeyCacheSize;
            this.genKeyGeneratorName = this.rbean().getGenKeyGeneratorName();
            this.selectFirstSeqKeyBeforeUpdate = this.rbean().getSelectFirstSeqKeyBeforeUpdate();
            conn = null;
            Statement statement = null;
            l = null;
            TransactionManager tm = null;

            try {
               Loggable l;
               try {
                  if (TransactionService.getTransaction() == null) {
                     tm = TransactionService.getTransactionManager();
                     tm.setTransactionTimeout(60);
                     tm.begin();
                  }

                  conn = this.getConnection();
                  int ret = this.verifier.checkTableAndColumns(this, conn, this.genKeyGeneratorName, new String[]{"SEQUENCE"}, false, (List)null, (Map)null, (Map)null);
                  if (ret == 0 && !this.getCreateDefaultDBMSTables().equalsIgnoreCase("Disabled")) {
                     List colList = new ArrayList();
                     colList.add("SEQUENCE");
                     this.verifier.verifyOrCreateOrAlterTable(this, conn, this.genKeyGeneratorName, colList, false, (List)null, (Map)null, (Map)null, this.getCreateDefaultDBMSTables(), false);
                     statement = conn.createStatement();
                     statement.executeUpdate("INSERT INTO " + this.genKeyGeneratorName + " (SEQUENCE) VALUES (0)");
                     statement.close();
                     if (tm != null) {
                        tm.commit();
                     }
                  }

                  statement = conn.createStatement();
                  ResultSet results = statement.executeQuery(this.genKeyWLGeneratorQuery);
                  if (!results.next()) {
                     results.close();
                     statement.close();
                     l = EJBLogger.logGenKeySequenceTableEmptyLoggable(this.genKeyGeneratorName);
                     throw new WLDeploymentException(l.getMessageText());
                  }
               } catch (Exception var19) {
                  try {
                     if (tm != null) {
                        tm.rollback();
                     }
                  } catch (Exception var18) {
                  }

                  l = EJBLogger.logGenKeySequenceTableSetupFailureLoggable(this.genKeyGeneratorName + "  " + var19.getMessage());
                  throw new WLDeploymentException(l.getMessageText(), var19);
               }
            } finally {
               this.releaseResources(conn, (Statement)null, (ResultSet)null);
            }
         }

      }
   }

   private Set createPrimaryKeyCols(String tableName) {
      Map field2Column = this.rbean().getCmpField2ColumnMap(tableName);
      if (field2Column == null) {
         return new ArraySet();
      } else {
         List pkFields = this.rbean().getPrimaryKeyFields();
         Iterator it = field2Column.entrySet().iterator();
         ArraySet pkCols = new ArraySet();

         while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String field = (String)entry.getKey();
            String column = (String)entry.getValue();
            if (pkFields.contains(field)) {
               pkCols.add(column);
            }
         }

         return pkCols;
      }
   }

   private String getSqltypeForCol(String tableName, String columnName) throws WLDeploymentException {
      String cmpField = this.rbean().getCmpField(tableName, columnName);
      Class fieldClass = null;
      if (cmpField != null) {
         fieldClass = this.rbean().getCmpFieldClass(cmpField);
      } else {
         fieldClass = this.rbean().getJavaClassTypeForFkCol(tableName, columnName);
         if (fieldClass == null) {
            RDBMSBean relatedBean = this.rbean().getRelatedBean(tableName, columnName);
            if (relatedBean == null) {
               throw new WLDeploymentException(" Bean: " + this.rbean().getEjbName() + ", could not get Column To Field Map for column " + columnName);
            }

            fieldClass = relatedBean.getJavaClassTypeForFkCol(tableName, columnName);
         }
      }

      if (null == fieldClass) {
         throw new WLDeploymentException(" Bean: " + this.ejbName + ", could not get Column To Field Map for column " + columnName);
      } else {
         try {
            return this.rbean.getDefaultDBMSColType(fieldClass);
         } catch (Exception var6) {
            throw new WLDeploymentException("No Field class found for " + columnName);
         }
      }
   }

   private boolean getSequenceTableColumns(String tableName, StringBuffer sb) throws Exception {
      if (this.rbean().hasAutoKeyGeneration() && this.genKeyType == 3) {
         String genKeyGeneratorName = this.rbean().getGenKeyGeneratorName();
         if (genKeyGeneratorName != null && tableName != null) {
            if (genKeyGeneratorName.equals(tableName)) {
               sb.append("SEQUENCE ").append(this.getGenKeySequenceDBColType());
               return true;
            } else {
               return false;
            }
         } else {
            throw new RDBMSException(" in getSequenceTableColumns: either the SEQUENCE_TABLE name in the RDBMSBean or the passed in table Name  is NULL for bean: " + this.ejbName);
         }
      } else {
         return false;
      }
   }

   private boolean getBeanOrJoinTableColumns(String tableName, StringBuffer sb) throws Exception {
      Set pkCols = new HashSet();
      Iterator it;
      Map.Entry entry;
      String fkField;
      Class cl;
      String colName;
      Class cl;
      if (this.rbean().isJoinTable(tableName)) {
         if (deploymentLogger.isDebugEnabled()) {
            debugDeployment(" createDefaultDBMSTable: processing Join Table: " + tableName);
         }

         String cmrField = this.rbean().getCmrFieldForJoinTable(tableName);
         if (null == cmrField) {
            throw new RDBMSException(" Bean: " + this.ejbName + ", could not get cmrField for Join Table " + tableName);
         }

         Map colMap = this.rbean().getFkColumn2ClassMapForFkField(cmrField);
         if (null == colMap) {
            throw new RDBMSException(" Bean: " + this.ejbName + ", could not get Column To Class Map for FK Field " + cmrField);
         }

         List joinColNames = new ArrayList();
         it = colMap.entrySet().iterator();

         while(it.hasNext()) {
            entry = (Map.Entry)it.next();
            fkField = (String)entry.getKey();
            Class fieldClass = (Class)entry.getValue();
            sb.append(fkField + " ");
            sb.append(this.rbean.getDefaultDBMSColType(fieldClass));
            if (this.databaseType == 2 || this.databaseType == 7 || this.databaseType == 5 || this.databaseType == 4 || this.databaseType == 9) {
               sb.append(" NOT NULL ");
            }

            sb.append(", ");
            pkCols.add(fkField);
            joinColNames.add(fkField);
         }

         if (this.rbean().isRemoteField(cmrField)) {
            if (deploymentLogger.isDebugEnabled()) {
               debugDeployment(" Do REMOTE RHS of Join Table ");
            }

            EjbEntityRef eref = this.rbean().getEjbEntityRef(cmrField);
            fkField = null;
            colName = this.rbean().getRemoteColumn(cmrField);
            sb.append(colName + " ");
            cl = fkField;
            if (!this.rbean.isValidSQLType(fkField) && Serializable.class.isAssignableFrom(fkField)) {
               byte[] b = new byte[0];
               cl = b.getClass();
            }

            sb.append(this.rbean.getDefaultDBMSColType(cl));
            if (this.databaseType == 2 || this.databaseType == 7 || this.databaseType == 5) {
               sb.append(" NOT NULL ");
            }

            pkCols.add(colName);
         } else {
            String otherField;
            if (this.rbean().isSymmetricField(cmrField)) {
               if (deploymentLogger.isDebugEnabled()) {
                  debugDeployment(" Do Symmetric RHS of Join Table ");
               }

               Map m2 = this.rbean().getSymmetricColumn2FieldName(cmrField);
               if (null == m2) {
                  throw new RDBMSException(" Bean: " + this.ejbName + ", could not get Symmetric Column To Class Map for FK Field " + cmrField);
               }

               it = m2.entrySet().iterator();

               label270:
               while(true) {
                  Map.Entry entry;
                  do {
                     if (!it.hasNext()) {
                        break label270;
                     }

                     entry = (Map.Entry)it.next();
                     colName = (String)entry.getKey();
                  } while(joinColNames.contains(colName));

                  sb.append(colName + " ");
                  otherField = (String)entry.getValue();
                  cl = this.rbean().getCmpFieldClass(otherField);
                  sb.append(this.rbean.getDefaultDBMSColType(cl));
                  if (this.databaseType == 2 || this.databaseType == 7 || this.databaseType == 5) {
                     sb.append(" NOT NULL ");
                  }

                  pkCols.add(colName);
                  if (it.hasNext()) {
                     sb.append(", ");
                  }
               }
            } else {
               if (deploymentLogger.isDebugEnabled()) {
                  debugDeployment(" Do Normal non-Remote non-Symmetric RHS of Join Table ");
               }

               RDBMSBean relatedBean = this.rbean().getRelatedRDBMSBean(cmrField);
               fkField = this.rbean().getRelatedFieldName(cmrField);
               colMap = relatedBean.getFkColumn2ClassMapForFkField(fkField);
               if (null == colMap) {
                  throw new RDBMSException(" Bean: " + relatedBean.getEjbName() + ", could not get Column To Class Map for FK Field " + fkField);
               }

               it = colMap.entrySet().iterator();

               label255:
               while(true) {
                  Map.Entry entry;
                  do {
                     if (!it.hasNext()) {
                        break label255;
                     }

                     entry = (Map.Entry)it.next();
                     otherField = (String)entry.getKey();
                  } while(joinColNames.contains(otherField));

                  cl = (Class)entry.getValue();
                  sb.append(otherField + " ");
                  sb.append(this.rbean.getDefaultDBMSColType(cl));
                  pkCols.add(otherField);
                  if (this.databaseType == 2 || this.databaseType == 7 || this.databaseType == 5 || this.databaseType == 4 || this.databaseType == 9) {
                     sb.append(" NOT NULL ");
                  }

                  if (it.hasNext()) {
                     sb.append(", ");
                  }
               }
            }
         }
      } else {
         if (deploymentLogger.isDebugEnabled()) {
            debugDeployment(" createDefaultDBMSTable: processing Bean Table: " + tableName);
         }

         List pkFields = this.rbean().getPrimaryKeyFields();
         Set colsAdded = new HashSet();
         Map field2Column = this.rbean().getCmpField2ColumnMap(tableName);
         it = field2Column.entrySet().iterator();

         label237:
         while(true) {
            String fkColumn;
            do {
               if (!it.hasNext()) {
                  List cmrFields = this.rbean().getCmrFields(tableName);
                  if (cmrFields != null) {
                     it = cmrFields.iterator();

                     label219:
                     while(true) {
                        do {
                           if (!it.hasNext()) {
                              break label237;
                           }

                           fkField = (String)it.next();
                        } while(!this.rbean().containsFkField(fkField));

                        Iterator fkColumns = this.rbean().getForeignKeyColNames(fkField).iterator();
                        Map fkColumn2Class = this.rbean().getFkColumn2ClassMapForFkField(fkField);

                        while(true) {
                           do {
                              while(true) {
                                 do {
                                    if (!fkColumns.hasNext()) {
                                       continue label219;
                                    }

                                    fkColumn = (String)fkColumns.next();
                                 } while(colsAdded.contains(fkColumn));

                                 sb.append(", ");
                                 colsAdded.add(fkColumn);
                                 sb.append(fkColumn + " ");
                                 Class cl = (Class)fkColumn2Class.get(fkColumn);
                                 sb.append(this.rbean.getDefaultDBMSColType(cl));
                                 if (pkFields.contains(fkField)) {
                                    pkCols.add(fkColumn);
                                    break;
                                 }

                                 if (this.databaseType == 5) {
                                    sb.append(" NULL ");
                                 }
                              }
                           } while(this.databaseType != 2 && this.databaseType != 7 && this.databaseType != 5);

                           sb.append(" NOT NULL ");
                        }
                     }
                  }
                  break label237;
               }

               entry = (Map.Entry)it.next();
               fkField = (String)entry.getKey();
               colName = (String)entry.getValue();
            } while(colsAdded.contains(colName));

            colsAdded.add(colName);
            cl = this.rbean().getCmpFieldClass(fkField);
            cl = null;
            if (this.rbean.isClobCmpColumnTypeForField(fkField)) {
               fkColumn = "Clob";
            } else if (this.rbean.isBlobCmpColumnTypeForField(fkField)) {
               fkColumn = "Blob";
            } else {
               fkColumn = this.rbean.getDefaultDBMSColType(cl);
            }

            sb.append(colName + " ");
            if (!pkFields.contains(fkField)) {
               sb.append(fkColumn);
               if (this.databaseType == 5 && !"BIT".equals(fkColumn)) {
                  sb.append(" NULL ");
               } else if (this.databaseType == 5 && "BIT".equals(fkColumn)) {
                  sb.append(" NOT NULL ");
               }
            } else {
               pkCols.add(colName);
               switch (this.databaseType) {
                  case 0:
                     sb.append(fkColumn);
                     break;
                  case 1:
                  case 10:
                     sb.append(fkColumn);
                     sb.append(" NOT NULL ");
                     break;
                  case 2:
                  case 7:
                     sb.append(fkColumn);
                     sb.append(" NOT NULL ");
                     if (this.genKeyType == 1) {
                        sb.append("IDENTITY ");
                     }
                     break;
                  case 3:
                     if (this.genKeyType == 1) {
                        sb.append("SERIAL");
                     } else {
                        sb.append(fkColumn);
                     }

                     sb.append(" NOT NULL ");
                     break;
                  case 4:
                  case 9:
                     sb.append(fkColumn);
                     sb.append(" NOT NULL ");
                     if (this.genKeyType == 1) {
                        sb.append("GENERATED ALWAYS AS IDENTITY ");
                     }
                     break;
                  case 5:
                     if (this.genKeyType == 1) {
                        sb.append("NUMERIC IDENTITY");
                     } else {
                        sb.append(fkColumn);
                     }

                     sb.append(" NOT NULL ");
                     break;
                  case 6:
                     sb.append(fkColumn);
                     if (this.genKeyType == 1) {
                        sb.append(" IDENTITY");
                     }

                     sb.append(" NOT NULL ");
                     break;
                  case 8:
                     sb.append(fkColumn);
                     sb.append(" NOT NULL ");
                     break;
                  default:
                     throw new AssertionError("Unknown DB Type: " + this.databaseType);
               }
            }

            if (it.hasNext()) {
               sb.append(", ");
            }
         }
      }

      if (pkCols.size() > 0) {
         sb.append(",");
         if (this.databaseType == 6 || this.databaseType == 8 || this.databaseType == 2 || this.databaseType == 7 || this.databaseType == 5) {
            sb.append(" CONSTRAINT pk_" + tableName);
         }

         sb.append(" PRIMARY KEY (");
         Iterator it = pkCols.iterator();

         while(it.hasNext()) {
            String s = (String)it.next();
            sb.append(s);
            if (it.hasNext()) {
               sb.append(", ");
            }
         }

         sb.append(")");
      }

      return true;
   }

   private Finder createDynamicFinder(String ejbql, WLQueryProperties props, boolean isLocal, boolean isSelect, Class returnType) throws Exception {
      EjbqlFinder finder = null;
      if (isSelect) {
         finder = new EjbqlFinder("execute", ejbql);
         finder.setIsSelect(true);
      } else {
         finder = new EjbqlFinder("find", ejbql);
      }

      finder.parseExpression();
      finder.setFinderLoadsBean(this.rbean().getCMPBeanDescriptor().getFindersLoadBean());
      if (isLocal) {
         finder.setResultTypeMapping("Local");
      } else {
         finder.setResultTypeMapping("Remote");
      }

      finder.setRDBMSBean(this.rbean());
      if (props != null) {
         finder.setMaxElements(props.getMaxElements());
         finder.setIncludeUpdates(props.getIncludeUpdates());
         finder.setSqlSelectDistinct(props.getSQLSelectDistinct());
         if (!isSelect) {
            String cachingName = props.getRelationshipCachingName();
            if (cachingName != null) {
               if (this.rbean().getRelationshipCaching(cachingName) == null) {
                  Loggable l = EJBLogger.logInvalidRelationshipCachingNameLoggable(cachingName);
                  throw new FinderException(l.getMessageText());
               }

               finder.setCachingName(cachingName);
            }

            String groupName = props.getFieldGroupName();
            if (groupName != null) {
               if (this.rbean().getFieldGroup(groupName) == null) {
                  Loggable l = EJBLogger.loginvalidFieldGroupNameLoggable(groupName);
                  throw new FinderException(l.getMessageText());
               }

               finder.setGroupName(groupName);
            }

            if (isLocal && props.isResultTypeRemote()) {
               finder.setResultTypeMapping("Remote");
            }
         }
      } else {
         finder.setNativeQuery(true);
      }

      finder.setReturnClassType(returnType);
      finder.setParameterClassTypes(new Class[0]);
      if (props != null) {
         finder.setQueryCachingEnabled(props.getEnableQueryCaching());
      }

      finder.computeSQLQuery(this.rbean());
      return finder;
   }

   private Object getDynamicQueryResult(ResultSet rs, Finder finder, boolean isLocal, boolean isSelect) throws Exception {
      if (isSelect) {
         RowSetFactory factory = RowSetFactory.newInstance();
         WLCachedRowSet rowSet = factory.newCachedRowSet();
         rowSet.populate(rs);
         return rowSet;
      } else {
         boolean selectLocal = finder.hasLocalResultType();
         this.checkResultTypeMapping(this.rbean(), selectLocal);
         QueryCachingHandler qcHandler = null;
         if (finder.isQueryCachingEnabled()) {
            qcHandler = finder.getQueryCachingHandler((Object[])null, (TTLManager)this.getBeanManager());
         } else {
            qcHandler = finder.getQueryCachingHandler((Object[])null, (TTLManager)null);
         }

         Collection coll = this.loadBeansFromRS(rs, finder, this.rbean(), this.beanManager, selectLocal, qcHandler);
         qcHandler.putInQueryCache();
         return coll;
      }
   }

   private void checkResultTypeMapping(RDBMSBean rdbmsBean, boolean selectLocal) throws FinderException {
      Loggable l;
      if (selectLocal) {
         if (!rdbmsBean.getCMPBeanDescriptor().hasLocalClientView()) {
            l = EJBLogger.loginvalidResultTypeMappingLoggable(rdbmsBean.getEjbName(), "Local");
            throw new FinderException(l.getMessageText());
         }
      } else if (!rdbmsBean.getCMPBeanDescriptor().hasRemoteClientView()) {
         l = EJBLogger.loginvalidResultTypeMappingLoggable(rdbmsBean.getEjbName(), "Remote");
         throw new FinderException(l.getMessageText());
      }

   }

   private Collection loadBeansFromRS(ResultSet rs, Finder finder, RDBMSBean rdbms, BeanManager bm, boolean isLocal, QueryCachingHandler qcHandler) throws Exception {
      Collection col = new ArrayList();
      CMPBeanManager cbm = (CMPBeanManager)bm;
      boolean isDistinct = finder.isSelectDistinct();
      Set distinctSet = null;
      boolean isRelationshipCaching = false;
      if (isDistinct) {
         distinctSet = new HashSet();
      }

      CMPBean bean = (CMPBean)cbm.getBeanFromPool();

      try {
         if (finder.finderLoadsBean()) {
            if (runtimeLogger.isDebugEnabled()) {
               debugRuntime("FinderLoadsBean == true");
            }

            Debug.assertion(finder instanceof EjbqlFinder);
            if (((EjbqlFinder)finder).getCachingName() != null) {
               isRelationshipCaching = true;
            }

            String groupName = ((EjbqlFinder)finder).getGroupName();
            int index = rdbms.getFieldGroup(groupName).getIndex();
            int groupColumnCount = this.getGroupColumnCount(rdbms, groupName);
            EntityBean eb = null;
            Transaction tx = TransactionService.getTransactionManager().getTransaction();
            int rowCounter = 1;

            while(rs.next()) {
               if (runtimeLogger.isDebugEnabled()) {
                  debugRuntime("Loading Bean: " + rowCounter++);
               }

               Object pk = bean.__WL_getPKFromRSInstance(rs, new Integer(0), this.classLoader);
               RSInfoImpl rsInfo;
               TTLManager romgr;
               if (isDistinct) {
                  if (distinctSet.add(pk)) {
                     rsInfo = new RSInfoImpl(rs, index, 0, pk);
                     eb = ((BaseEntityManager)cbm).getBeanFromRS(tx, pk, rsInfo);
                     ((Collection)col).add(cbm.finderGetEoFromBeanOrPk(eb, pk, finder.hasLocalResultType()));
                     if (finder.isQueryCachingEnabled()) {
                        romgr = (TTLManager)cbm;
                        qcHandler.addQueryCachingEntry(romgr, new QueryCacheElement(pk, romgr));
                     }
                  } else if (runtimeLogger.isDebugEnabled()) {
                     debugRuntime("Bean was already loaded ");
                  }
               } else {
                  rsInfo = new RSInfoImpl(rs, index, 0, pk);
                  eb = ((BaseEntityManager)cbm).getBeanFromRS(tx, pk, rsInfo);
                  ((Collection)col).add(cbm.finderGetEoFromBeanOrPk(eb, pk, finder.hasLocalResultType()));
                  if (finder.isQueryCachingEnabled()) {
                     romgr = (TTLManager)cbm;
                     qcHandler.addQueryCachingEntry(romgr, new QueryCacheElement(pk, romgr));
                  }
               }

               if (isRelationshipCaching) {
                  if (runtimeLogger.isDebugEnabled()) {
                     debugRuntime("Dynamic finder has RelationshipCaching turned on, load the related beans");
                  }

                  ((CMPBean)eb).__WL_loadBeansRelatedToCachingName(((EjbqlFinder)finder).getCachingName(), rs, (CMPBean)eb, groupColumnCount, qcHandler);
               }
            }
         } else {
            if (runtimeLogger.isDebugEnabled()) {
               debugRuntime("FinderLoadsBean == false");
            }

            while(rs.next()) {
               if (runtimeLogger.isDebugEnabled()) {
                  debugRuntime("Loading PK");
               }

               Object pk = bean.__WL_getPKFromRSInstance(rs, new Integer(0), this.classLoader);
               TTLManager romgr;
               if (isDistinct) {
                  if (distinctSet.add(pk)) {
                     ((Collection)col).add(pk);
                     if (finder.isQueryCachingEnabled()) {
                        romgr = (TTLManager)cbm;
                        qcHandler.addQueryCachingEntry(romgr, new QueryCacheElement(pk, romgr));
                     }
                  }
               } else {
                  ((Collection)col).add(pk);
                  if (finder.isQueryCachingEnabled()) {
                     romgr = (TTLManager)cbm;
                     qcHandler.addQueryCachingEntry(romgr, new QueryCacheElement(pk, romgr));
                  }
               }
            }

            col = ((BaseEntityManager)cbm).pkCollToColl((Collection)col, finder.hasLocalResultType());
         }
      } finally {
         ((BaseEntityManager)cbm).releaseBeanToPool((EntityBean)bean);
      }

      return (Collection)col;
   }

   public int getGroupColumnCount(RDBMSBean rdbmsBean, String groupName) {
      FieldGroup group = rdbmsBean.getFieldGroup(groupName);
      if (group == null) {
         return rdbmsBean.getPrimaryKeyFields().size();
      } else {
         Set columns = new HashSet();
         Set finderFieldSet = new TreeSet(group.getCmpFields());
         finderFieldSet.addAll(rdbmsBean.getPrimaryKeyFields());
         Iterator cmpFields = finderFieldSet.iterator();

         while(cmpFields.hasNext()) {
            String cmpField = (String)cmpFields.next();
            columns.add(rdbmsBean.getCmpColumnForField(cmpField));
         }

         Iterator cmrFields = group.getCmrFields().iterator();

         while(cmrFields.hasNext()) {
            String cmrField = (String)cmrFields.next();
            Iterator cmrColumns = rdbmsBean.getForeignKeyColNames(cmrField).iterator();

            while(cmrColumns.hasNext()) {
               String cmrColumn = (String)cmrColumns.next();
               columns.add(cmrColumn);
            }
         }

         return columns.size();
      }
   }

   public String getEjbName() {
      return this.ejbName;
   }

   public BaseEntityManager getBeanManager() {
      return this.beanManager;
   }

   public EntityBean getBeanFromPool() throws InternalException {
      return this.beanManager.getBeanFromPool();
   }

   public EntityBean getBeanFromRS(Object pk, RSInfo rsInfo) throws InternalException {
      return this.beanManager.getBeanFromRS(pk, rsInfo);
   }

   public Object finderGetEoFromBeanOrPk(EntityBean bean, Object pk, boolean isLocal) throws InternalException {
      return this.beanManager.finderGetEoFromBeanOrPk(bean, pk, isLocal);
   }

   public ClassLoader getClassLoader() {
      return this.classLoader;
   }

   public String nativeQuery(String ejbql) throws FinderException {
      String sql = null;
      Finder finder = null;

      try {
         finder = this.createDynamicFinder(ejbql, (WLQueryProperties)null, true, true, Collection.class);
         sql = finder.getSQLQuery();
      } catch (Exception var19) {
         throw new FinderException("Error constructing query: \n" + var19.toString() + "\n" + RDBMSUtils.throwable2StackTrace(var19));
      }

      if (runtimeLogger.isDebugEnabled()) {
         debugRuntime("EJB-QL produced SQL: " + sql);
      }

      Connection conn = null;

      String var5;
      try {
         try {
            conn = this.getConnection();
         } catch (Exception var17) {
            throw new FinderException("Couldn't get connection: \n" + var17.toString() + "\n" + RDBMSUtils.throwable2StackTrace(var17));
         }

         try {
            var5 = conn.nativeSQL(sql);
         } catch (SQLException var16) {
            throw new FinderException("Error getting native SQL: \n" + var16.toString() + "\n" + RDBMSUtils.throwable2StackTrace(var16));
         }
      } finally {
         try {
            this.releaseConnection(conn);
         } catch (SQLException var15) {
         }

      }

      return var5;
   }

   public String getDatabaseProductName() throws FinderException {
      Connection conn = null;

      String var2;
      try {
         try {
            conn = this.getConnection();
         } catch (Exception var13) {
            throw new FinderException("Couldn't get connection: \n" + var13.toString() + "\n" + RDBMSUtils.throwable2StackTrace(var13));
         }

         try {
            var2 = conn.getMetaData().getDatabaseProductName();
         } catch (SQLException var12) {
            throw new FinderException("Error calling getDatabaseProductName: \n" + var12.toString() + "\n" + RDBMSUtils.throwable2StackTrace(var12));
         }
      } finally {
         try {
            this.releaseConnection(conn);
         } catch (SQLException var11) {
         }

      }

      return var2;
   }

   public String getDatabaseProductVersion() throws FinderException {
      Connection conn = null;

      String var2;
      try {
         try {
            conn = this.getConnection();
         } catch (Exception var13) {
            throw new FinderException("Couldn't get connection: \n" + var13.toString() + "\n" + RDBMSUtils.throwable2StackTrace(var13));
         }

         try {
            var2 = conn.getMetaData().getDatabaseProductVersion();
         } catch (SQLException var12) {
            throw new FinderException("Error calling getDatabaseProductVersion: \n" + var12.toString() + "\n" + RDBMSUtils.throwable2StackTrace(var12));
         }
      } finally {
         try {
            this.releaseConnection(conn);
         } catch (SQLException var11) {
         }

      }

      return var2;
   }

   public Object executePreparedQuery(String sql, boolean isLocal, boolean isSelect, Map arguments, Map flattenedArguments, PreparedQuery pquery) throws FinderException {
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      EjbqlFinder finder = null;
      Object[] returnArray = new Object[3];
      Map adjustedArguments = new TreeMap();
      Object value;
      String innerValue;
      int adjustedArgumentIndex;
      Iterator iterator;
      int key;
      if (sql == null) {
         if (runtimeLogger.isDebugEnabled()) {
            debugRuntime("\nParsing EJBQL for the first time");
         }

         String sqlFromFinder;
         try {
            if (isSelect) {
               finder = new EjbqlFinder("execute", pquery.getEjbql());
               finder.setIsSelect(true);
            } else {
               finder = new EjbqlFinder("find", pquery.getEjbql());
            }

            finder.setIsPreparedQueryFinder(true);
            finder.parseExpression();
            finder.setFinderLoadsBean(this.rbean().getCMPBeanDescriptor().getFindersLoadBean());
            finder.setRDBMSBean(this.rbean());
            if (pquery != null) {
               WLQueryProperties props = (WLQueryProperties)pquery;
               finder.setSqlSelectDistinct(props.getSQLSelectDistinct());
               if (!isSelect) {
                  sqlFromFinder = props.getRelationshipCachingName();
                  if (sqlFromFinder != null) {
                     if (this.rbean().getRelationshipCaching(sqlFromFinder) == null) {
                        Loggable l = EJBLogger.logInvalidRelationshipCachingNameLoggable(sqlFromFinder);
                        throw new FinderException(l.getMessageText());
                     }

                     finder.setCachingName(sqlFromFinder);
                  }

                  String groupName = props.getFieldGroupName();
                  if (groupName != null) {
                     if (this.rbean().getFieldGroup(groupName) == null) {
                        Loggable l = EJBLogger.loginvalidFieldGroupNameLoggable(groupName);
                        throw new FinderException(l.getMessageText());
                     }

                     finder.setGroupName(groupName);
                  }
               }
            }

            Class[] parameterTypes = new Class[arguments.size()];
            iterator = arguments.values().iterator();
            key = 0;

            while(true) {
               if (!iterator.hasNext()) {
                  finder.setParameterClassTypes(parameterTypes);
                  finder.computeSQLQuery(this.rbean());
                  break;
               }

               value = iterator.next();
               if (!(value instanceof EJBObject) && !(value instanceof EJBLocalObject)) {
                  parameterTypes[key] = value.getClass();
               } else {
                  parameterTypes[key] = value.getClass().getInterfaces()[0];
               }

               if (runtimeLogger.isDebugEnabled()) {
                  debugRuntime("parameter class type " + key + " : set to: " + parameterTypes[key]);
               }

               ++key;
            }
         } catch (Exception var44) {
            throw new FinderException("Error constructing prepared query: \n" + var44.toString() + "\n" + RDBMSUtils.throwable2StackTrace(var44));
         }

         adjustedArgumentIndex = this.getUpdateLockType();
         sqlFromFinder = null;
         switch (adjustedArgumentIndex) {
            case 4:
               sqlFromFinder = finder.getSQLQuery();
               break;
            case 5:
               sqlFromFinder = finder.getSQLQueryForUpdateSelective();
               if (sqlFromFinder == null) {
                  if (this.rbean.getUseSelectForUpdate()) {
                     sqlFromFinder = finder.getSQLQueryForUpdate();
                  } else {
                     sqlFromFinder = finder.getSQLQuery();
                  }
               }
               break;
            case 6:
               sqlFromFinder = finder.getSQLQueryForUpdate();
               break;
            case 7:
               sqlFromFinder = finder.getSQLQueryForUpdateNoWait();
               break;
            default:
               throw new AssertionError("Undefined update lock value");
         }

         if (runtimeLogger.isDebugEnabled()) {
            debugRuntime("EJBQL converted to SQL: " + sqlFromFinder);
         }

         sql = sqlFromFinder;
         returnArray[0] = sqlFromFinder;
         boolean argumentsHaveCompoundPK = false;
         List listOfParamNodes = finder.getExternalMethodParmList();
         Iterator paramNodeIterator = listOfParamNodes.iterator();

         for(int index = 1; paramNodeIterator.hasNext(); ++index) {
            ParamNode node = (ParamNode)paramNodeIterator.next();
            if (!node.hasCompoundKey()) {
               ((Map)adjustedArguments).put(new Integer(index), new DynamicEJBQLArgumentWrapper(node.getParamName(), node.isOracleNLSDataType()));
            } else {
               Map compoundPKMap = new TreeMap();
               List subParamNodes = node.getParamSubList();
               int count = 1;

               for(Iterator subParamNodeIterator = subParamNodes.iterator(); subParamNodeIterator.hasNext(); ++count) {
                  ParamNode subNode = (ParamNode)subParamNodeIterator.next();
                  innerValue = subNode.getId();
                  compoundPKMap.put(new Integer(count), new DynamicEJBQLArgumentWrapper(innerValue, subNode.isOracleNLSDataType()));
               }

               argumentsHaveCompoundPK = true;
               ((Map)adjustedArguments).put(new Integer(index), compoundPKMap);
            }
         }

         if (argumentsHaveCompoundPK && runtimeLogger.isDebugEnabled()) {
            debugRuntime("\n-------------------------");
            debugRuntime("The arguments have a compoundPK, the Flattened argument Map is: \n" + adjustedArguments);
            debugRuntime("-------------------------\n");
         }

         returnArray[1] = adjustedArguments;
      } else {
         if (runtimeLogger.isDebugEnabled()) {
            debugRuntime("\nEJBQL was already parsed and converted to SQL");
         }

         try {
            if (isSelect) {
               finder = new EjbqlFinder("execute", "dummy");
            } else {
               finder = new EjbqlFinder("find", "dummy");
            }
         } catch (Exception var42) {
            throw new FinderException("Error constructing dummy query while  executing PreparedStatement: \n" + var42.toString() + "\n" + RDBMSUtils.throwable2StackTrace(var42));
         }

         adjustedArguments = flattenedArguments;
      }

      if (runtimeLogger.isDebugEnabled()) {
         debugRuntime("findersLoadBean: " + this.rbean().getCMPBeanDescriptor().getFindersLoadBean());
         debugRuntime("isLocal: " + isLocal);
         debugRuntime("isResultTypeRemote: " + pquery.isResultTypeRemote());
         debugRuntime("isSelect: " + isSelect);
      }

      finder.setFinderLoadsBean(this.rbean().getCMPBeanDescriptor().getFindersLoadBean());
      if (isLocal) {
         finder.setResultTypeMapping("Local");
      } else {
         finder.setResultTypeMapping("Remote");
      }

      if (isLocal && pquery != null && pquery.isResultTypeRemote()) {
         finder.setResultTypeMapping("Remote");
      }

      if (isSelect) {
         finder.setReturnClassType(ResultSet.class);
      } else {
         finder.setReturnClassType(Collection.class);
      }

      if (pquery != null && pquery.getIncludeUpdates()) {
         if (runtimeLogger.isDebugEnabled()) {
            debugRuntime("Flushing modified beans before running the Dyn Query with PrepStmt");
         }

         this.flushModifiedBeans();
      }

      try {
         conn = this.getConnection();
      } catch (Exception var41) {
         this.releaseResources(conn, (Statement)null, (ResultSet)null);
         throw new FinderException("Couldn't get connection: \n" + var41.toString() + "\n" + RDBMSUtils.throwable2StackTrace(var41));
      }

      if (runtimeLogger.isDebugEnabled()) {
         debugRuntime("Creating PreparedStatement with sql: " + sql);
      }

      try {
         pstmt = conn.prepareStatement(sql);
      } catch (Exception var40) {
         this.releaseResources(conn, pstmt, (ResultSet)null);
         throw new FinderException("Exception in executePreparedQuery while preparing statement: " + pstmt + "'\n" + var40.toString() + "\n" + RDBMSUtils.throwable2StackTrace(var40));
      }

      try {
         if (pquery != null && pquery.getMaxElements() != 0) {
            pstmt.setMaxRows(pquery.getMaxElements());
         }

         if (adjustedArguments == null) {
            if (runtimeLogger.isDebugEnabled()) {
               debugRuntime("arguments do not have a compound PK");
            }

            Iterator iterator = arguments.entrySet().iterator();

            while(iterator.hasNext()) {
               Map.Entry entry = (Map.Entry)iterator.next();
               key = (Integer)entry.getKey();
               value = entry.getValue();
               if (value instanceof EJBObject) {
                  value = ((EJBObject)value).getPrimaryKey();
               }

               if (value instanceof EJBLocalObject) {
                  value = ((EJBLocalObject)value).getPrimaryKey();
               }

               if (runtimeLogger.isDebugEnabled()) {
                  debugRuntime("paramIndex: " + key + " binded with value: " + value);
               }

               if (value.getClass().equals(Character.class)) {
                  pstmt.setString(key, value.toString());
               } else {
                  pstmt.setObject(key, value);
               }
            }
         } else {
            if (runtimeLogger.isDebugEnabled()) {
               debugRuntime("arguments have a compound PK");
            }

            adjustedArgumentIndex = 1;
            iterator = ((Map)adjustedArguments).entrySet().iterator();

            label522:
            while(true) {
               while(true) {
                  if (!iterator.hasNext()) {
                     break label522;
                  }

                  Map.Entry entry = (Map.Entry)iterator.next();
                  int key = (Integer)entry.getKey();
                  Object value = entry.getValue();
                  if (value instanceof Map) {
                     Object eo = arguments.get(new Integer(key));
                     Object pk = ((EJBLocalObject)eo).getPrimaryKey();
                     Class pkClass = pk.getClass();

                     for(Iterator it = ((Map)value).entrySet().iterator(); it.hasNext(); ++adjustedArgumentIndex) {
                        Map.Entry compoundEntry = (Map.Entry)it.next();
                        int innerKey = (Integer)compoundEntry.getKey();
                        DynamicEJBQLArgumentWrapper wrapper = (DynamicEJBQLArgumentWrapper)compoundEntry.getValue();
                        innerValue = wrapper.getArgumentName();
                        Field f = pkClass.getField(innerValue);
                        Object actualValue = f.get(pk);
                        if (runtimeLogger.isDebugEnabled()) {
                           debugRuntime("paramIndex: " + adjustedArgumentIndex + " binded with value: " + actualValue);
                        }

                        if (wrapper.isOracleNLSDataType()) {
                           this.invokeOracleSetFormOfUse(adjustedArgumentIndex, pstmt);
                        }

                        if (actualValue.getClass().equals(Character.class)) {
                           pstmt.setString(adjustedArgumentIndex, actualValue.toString());
                        } else {
                           pstmt.setObject(adjustedArgumentIndex, actualValue);
                        }
                     }
                  } else {
                     DynamicEJBQLArgumentWrapper wrapper = (DynamicEJBQLArgumentWrapper)value;
                     value = arguments.get(new Integer(key));
                     if (runtimeLogger.isDebugEnabled()) {
                        debugRuntime("paramIndex: " + adjustedArgumentIndex + " binded with value: " + value);
                     }

                     if (wrapper.isOracleNLSDataType()) {
                        this.invokeOracleSetFormOfUse(adjustedArgumentIndex, pstmt);
                     }

                     if (value.getClass().equals(Character.class)) {
                        pstmt.setString(adjustedArgumentIndex, value.toString());
                     } else {
                        pstmt.setObject(adjustedArgumentIndex, value);
                     }

                     ++adjustedArgumentIndex;
                  }
               }
            }
         }

         rs = pstmt.executeQuery();
      } catch (Exception var43) {
         this.releaseResources(conn, pstmt, rs);
         throw new FinderException("Error in executing PreparedQuery: PreparedStatement: '" + pstmt + "'\n" + var43.toString() + "\n" + RDBMSUtils.throwable2StackTrace(var43));
      }

      if (runtimeLogger.isDebugEnabled()) {
         debugRuntime("Dynamic Query with PreparedStatement executed ");
      }

      Object[] var59;
      try {
         Object results = this.getDynamicQueryResult(rs, finder, isLocal, isSelect);
         returnArray[2] = results;
         var59 = returnArray;
      } catch (SQLException var37) {
         throw new FinderException("Exception in executePreparedQuery while using result set: '" + rs + "'\n" + var37.toString() + "\n" + RDBMSUtils.throwable2StackTrace(var37));
      } catch (Exception var38) {
         throw new FinderException("Exception executing executePreparedQuery: \n" + var38.toString() + "\n" + RDBMSUtils.throwable2StackTrace(var38));
      } finally {
         this.releaseResources(conn, pstmt, rs);
      }

      return var59;
   }

   private void invokeOracleSetFormOfUse(int index, PreparedStatement pstmt) throws Exception {
      if ("oracle.jdbc.OraclePreparedStatement".equals(pstmt.getClass().getName())) {
         Method meth = pstmt.getClass().getMethod("setFormOfUse", Integer.TYPE, Short.TYPE);
         if (meth != null) {
            meth.invoke(pstmt, index, new Short((short)2));
         }
      }

   }

   public Object dynamicQuery(String ejbql, WLQueryProperties props, boolean isLocal, boolean isSelect) throws FinderException {
      String sql = null;
      Finder finder = null;

      try {
         if (isSelect) {
            finder = this.createDynamicFinder(ejbql, props, isLocal, isSelect, ResultSet.class);
            sql = finder.getSQLQuery();
         } else {
            finder = this.createDynamicFinder(ejbql, props, isLocal, isSelect, Collection.class);
            int updateLockType = this.getUpdateLockType();
            switch (updateLockType) {
               case 4:
                  sql = finder.getSQLQuery();
                  break;
               case 5:
                  sql = finder.getSQLQueryForUpdateSelective();
                  if (sql == null) {
                     if (this.rbean.getUseSelectForUpdate()) {
                        sql = finder.getSQLQueryForUpdate();
                     } else {
                        sql = finder.getSQLQuery();
                     }
                  }
                  break;
               case 6:
                  sql = finder.getSQLQueryForUpdate();
                  break;
               case 7:
                  sql = finder.getSQLQueryForUpdateNoWait();
                  break;
               default:
                  throw new AssertionError("Undefined update lock value");
            }
         }
      } catch (Exception var22) {
         throw new FinderException("Error constructing query: \n" + var22.toString() + "\n" + RDBMSUtils.throwable2StackTrace(var22));
      }

      if (runtimeLogger.isDebugEnabled()) {
         debugRuntime("Dynamic Query produced statement string " + sql);
      }

      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      if (finder.getIncludeUpdates()) {
         this.flushModifiedBeans();
      }

      try {
         conn = this.getConnection();
      } catch (Exception var21) {
         this.releaseResources(conn, pstmt, rs);
         throw new FinderException("Couldn't get connection: \n" + var21.toString() + "\n" + RDBMSUtils.throwable2StackTrace(var21));
      }

      try {
         pstmt = conn.prepareStatement(sql);
         if (finder.getMaxElements() != 0) {
            pstmt.setMaxRows(finder.getMaxElements());
         }

         rs = pstmt.executeQuery();
      } catch (Exception var23) {
         this.releaseResources(conn, pstmt, rs);
         throw new FinderException("Exception in dynamicQuery while preparing or executing statement: '" + pstmt + "'\n" + var23.toString() + "\n" + RDBMSUtils.throwable2StackTrace(var23));
      }

      Object var10;
      try {
         var10 = this.getDynamicQueryResult(rs, finder, isLocal, isSelect);
      } catch (SQLException var18) {
         throw new FinderException("Exception in dynamicQuery while using result set: '" + rs + "'\n" + var18.toString() + "\n" + RDBMSUtils.throwable2StackTrace(var18));
      } catch (Exception var19) {
         throw new FinderException("Exception executing dynamicQuery: \n" + var19.toString() + "\n" + RDBMSUtils.throwable2StackTrace(var19));
      } finally {
         this.releaseResources(conn, pstmt, rs);
      }

      return var10;
   }

   public Connection getConnection() throws SQLException {
      if (runtimeLogger.isDebugEnabled()) {
         debugRuntime("RDBMSPersistenceManager.getConnection");
      }

      assert this.ds != null;

      Connection con = this.ds.getConnection();
      if (con == null) {
         Loggable l = EJBLogger.logCouldNotGetConnectionFromDataSourceLoggable(this.dataSourceName);
         throw new SQLException(l.getMessageText());
      } else {
         return con;
      }
   }

   public Connection getConnection(Transaction tx) throws SQLException {
      if (runtimeLogger.isDebugEnabled()) {
         debugRuntime("RDBMSPersistenceManager.getConnection");
      }

      assert this.ds != null;

      Connection con = this.ds.getConnection();
      if (con == null) {
         Loggable l = EJBLogger.logCouldNotGetConnectionFromDataSourceLoggable(this.dataSourceName);
         throw new SQLException(l.getMessageText());
      } else {
         return con;
      }
   }

   public Integer getTransactionIsolationLevel() {
      Transaction tx = TransactionService.getTransaction();
      return tx == null ? null : (Integer)((weblogic.transaction.Transaction)tx).getProperty("ISOLATION LEVEL");
   }

   public Transaction suspendTransaction() throws PersistenceRuntimeException {
      if (runtimeLogger.isDebugEnabled()) {
         debugRuntime("suspendTransaction");
      }

      if (TransactionService.getTransaction() == null) {
         return null;
      } else {
         try {
            return this.tm.suspend();
         } catch (Exception var2) {
            throw new PersistenceRuntimeException(var2);
         }
      }
   }

   public void resumeTransaction(Transaction tx) throws PersistenceRuntimeException {
      if (runtimeLogger.isDebugEnabled()) {
         debugRuntime("resumeTransaction");
      }

      if (tx != null) {
         assert this.tm != null;

         assert tx != null;

         try {
            this.tm.resume(tx);
         } catch (Exception var3) {
            throw new PersistenceRuntimeException(var3);
         }
      }
   }

   public boolean needsBatchOperationsWorkaround() {
      return this.isOptimistic && this.databaseType == 1 && this.enableBatchOperations;
   }

   public boolean perhapsUseSendBatchForOracle() {
      boolean isOracleDriver = false;
      Connection conn = null;

      try {
         conn = this.getConnection();
         if (this.driverName.equalsIgnoreCase("Oracle JDBC driver") && "oracle.jdbc.OracleConnection".equals(conn.getClass().getName())) {
            isOracleDriver = true;
         }
      } catch (SQLException var8) {
         if (deploymentLogger.isDebugEnabled()) {
            debugDeployment("SQLException while checking for sendBatch API usage:" + var8.getMessage());
         }
      } catch (Throwable var9) {
         if (deploymentLogger.isDebugEnabled()) {
            debugDeployment("Exception while checking for sendBatch API usage:" + var9.getMessage());
         }
      } finally {
         this.releaseResources(conn, (Statement)null, (ResultSet)null);
      }

      return this.isOptimistic && this.databaseType == 1 && this.enableBatchOperations && isOracleDriver;
   }

   public int[] getVerifyCount() {
      return (int[])this.verifyCount.clone();
   }

   public int[] getVerifyCur() {
      return (int[])this.verifyCur.clone();
   }

   public StringBuffer[] getVerifySql(boolean withXLock) {
      StringBuffer[] verifySql = new StringBuffer[this.verifyText.length];
      int i;
      if (withXLock) {
         for(i = 0; i < verifySql.length; ++i) {
            verifySql[i] = new StringBuffer(this.verifyTextWithXLock[i]);
         }
      } else {
         for(i = 0; i < verifySql.length; ++i) {
            verifySql[i] = new StringBuffer(this.verifyText[i]);
         }
      }

      return verifySql;
   }

   public PreparedStatement[] prepareStatement(Connection con, StringBuffer[] verifySql, int[] verifyCount, boolean setXLock) throws SQLException {
      PreparedStatement[] pstmt = new PreparedStatement[verifySql.length];

      for(int i = 0; i < verifySql.length; ++i) {
         if (verifyCount[i] > 0) {
            if (runtimeLogger.isDebugEnabled()) {
               debugRuntime("prepareStatement: sql=" + verifySql[i].toString());
            }

            if (setXLock && this.selectForUpdateSupported && this.databaseType != 2 && this.databaseType != 7) {
               verifySql[i].append(" FOR UPDATE");
            }

            pstmt[i] = con.prepareStatement(verifySql[i].toString());
         }
      }

      return pstmt;
   }

   public ResultSet[] executeQuery(PreparedStatement[] verifyStmt) throws SQLException {
      ResultSet[] verifyResult = new ResultSet[verifyStmt.length];

      for(int i = 0; i < verifyResult.length; ++i) {
         if (verifyStmt[i] != null) {
            verifyResult[i] = verifyStmt[i].executeQuery();
         }
      }

      return verifyResult;
   }

   public void checkResults(ResultSet[] verifyResult, int[] verifyCount) throws SQLException, OptimisticConcurrencyException {
      for(int i = 0; i < verifyResult.length; ++i) {
         if (verifyCount[i] > 0) {
            int count;
            for(count = 0; verifyResult[i].next(); ++count) {
            }

            if (count != verifyCount[i]) {
               Loggable l = EJBLogger.logoptimisticUpdateFailedLoggable(this.ejbName, "<unknown>");
               throw new OptimisticConcurrencyException(l.getMessageText());
            }
         }
      }

   }

   public String getSnapshotPredicate(int modifiedIndex) {
      return this.snapshotStrings[modifiedIndex];
   }

   public String getSnapshotPredicate(int modifiedIndex, Object value) {
      assert this.nullSnapshotStrings[modifiedIndex] != null;

      assert this.snapshotStrings[modifiedIndex] != null;

      return value == null ? this.nullSnapshotStrings[modifiedIndex] : this.snapshotStrings[modifiedIndex];
   }

   private void verifyDatabaseType() throws WLDeploymentException {
      Connection conn = null;

      try {
         conn = this.getConnection();
         this.databaseType = this.verifier.verifyDatabaseType(conn, this.databaseType);
         this.rbean.setDatabaseType(this.databaseType);
      } catch (Exception var6) {
         throw new WLDeploymentException(var6.getMessage());
      } finally {
         this.releaseResources(conn, (Statement)null, (ResultSet)null);
      }

   }

   private void verifyTXDataSource() throws WLDeploymentException {
      if (this.ds instanceof DataSourceMetaData) {
         if (!((DataSourceMetaData)this.ds).isTxDataSource()) {
            Loggable l = EJBLogger.logcmpBeanMustHaveTXDataSourceSpecifiedLoggable(this.dataSourceName, this.ejbName);
            throw new WLDeploymentException(l.getMessage());
         }
      } else {
         Connection conn = null;

         try {
            conn = this.getConnection();
            if (conn.getAutoCommit()) {
               Loggable l = EJBLogger.logcmpBeanMustHaveTXDataSourceSpecifiedLoggable(this.dataSourceName, this.ejbName);
               throw new WLDeploymentException(l.getMessageText());
            }
         } catch (SQLException var6) {
            throw new WLDeploymentException(var6.getMessage());
         } finally {
            this.releaseResources(conn, (Statement)null, (ResultSet)null);
         }

      }
   }

   private void verifyBatchUpdatesSupported() {
      if (this.enableBatchOperations) {
         boolean supportBatchUpdates = false;
         Connection conn = null;

         try {
            conn = this.getConnection();
            DatabaseMetaData dbmd = conn.getMetaData();
            supportBatchUpdates = dbmd.supportsBatchUpdates();
         } catch (SQLException var8) {
            supportBatchUpdates = false;
         } catch (AbstractMethodError var9) {
            supportBatchUpdates = false;
         } finally {
            this.releaseResources(conn, (Statement)null, (ResultSet)null);
         }

         if (deploymentLogger.isDebugEnabled()) {
            if (!supportBatchUpdates) {
               debugDeployment("The database or JDBC driver doesn't support batch update.");
            } else {
               debugDeployment("The database or JDBC driver supports batch update.");
            }
         }

         if (this.orderDatabaseOperations && this.enableBatchOperations) {
            this.orderDatabaseOperations = supportBatchUpdates;
            this.enableBatchOperations = supportBatchUpdates;
            if (deploymentLogger.isDebugEnabled()) {
               debugDeployment("The orderDatabaseOperations and enableBatchOperations are set to " + supportBatchUpdates);
            }
         }

      }
   }

   public boolean getEnableBatchOperations() {
      return this.enableBatchOperations;
   }

   public boolean getOrderDatabaseOperations() {
      return this.orderDatabaseOperations;
   }

   public boolean setParamNull(PreparedStatement statement, int paramIndex, Object value, String fieldName) throws SQLException {
      if (runtimeLogger.isDebugEnabled()) {
         debugRuntime("RDBMSPersistenceManager.setParamNull");
      }

      if (value == null) {
         assert this.variable2SQLType != null;

         assert this.variable2SQLType.get(fieldName) != null : "No field->SQLType mapping for field " + fieldName;

         Integer sqlTypeObj = (Integer)this.variable2SQLType.get(fieldName);
         int sqlType = sqlTypeObj;
         if (runtimeLogger.isDebugEnabled()) {
            debugRuntime("setting field- " + fieldName + " null (" + paramIndex + ", " + RDBMSUtils.sqlTypeToString(sqlType) + ")");
         }

         if (sqlType == 2007) {
            statement.setNull(paramIndex, 2007, "XMLTYPE");
         } else if (sqlType == 2009) {
            statement.setNull(paramIndex, 2009, "XMLTYPE");
         } else {
            statement.setNull(paramIndex, sqlType);
         }

         return true;
      } else {
         if (runtimeLogger.isDebugEnabled()) {
            debugRuntime("field- " + fieldName + " is not null.");
         }

         return false;
      }
   }

   public Object getNextSequenceKey() throws EJBException {
      return this.getNextGenKeyPreFetch(2);
   }

   public Object getNextSequenceTableKey() throws EJBException {
      return this.getNextGenKeyPreFetch(3);
   }

   public synchronized Object getNextGenKeyPreFetch(int genKeyType) throws EJBException {
      if (this.genKeyCurrCacheSize <= 0) {
         switch (this.genKeyPKFieldClassType) {
            case 0:
               if (genKeyType == 2) {
                  this.genKeyCurrValueInt = (Integer)this.execGenKeyQuery();
               } else {
                  if (genKeyType != 3) {
                     throw new EJBException(" Internal Error, unknown genKeyType: " + genKeyType);
                  }

                  this.genKeyCurrValueInt = (Integer)this.execGenKeySequenceTableUpdateAndQuery();
               }

               if (runtimeLogger.isDebugEnabled()) {
                  debugRuntime("  refresh Key Cache.  New Value: " + this.genKeyCurrValueInt);
               }
               break;
            case 1:
               if (genKeyType == 2) {
                  this.genKeyCurrValueLong = (Long)this.execGenKeyQuery();
               } else {
                  if (genKeyType != 3) {
                     throw new EJBException(" Internal Error, unknown genKeyType: " + genKeyType);
                  }

                  this.genKeyCurrValueLong = (Long)this.execGenKeySequenceTableUpdateAndQuery();
               }

               if (runtimeLogger.isDebugEnabled()) {
                  debugRuntime("  refresh Key Cache.  New Value: " + this.genKeyCurrValueLong);
               }
               break;
            default:
               throw new EJBException(" Internal Error, unknown genKeyPKFieldClassType: " + this.genKeyPKFieldClassType);
         }

         this.genKeyCurrCacheSize = this.genKeyCacheSize;
      }

      if (--this.genKeyCurrCacheSize < 0) {
         if (this.genKeyPKFieldClassType == 0) {
            this.genKeyCurrValueLong = (long)this.genKeyCurrValueInt;
         }

         throw new EJBException("Error in auto PK generator key caching  genKeyCurrCacheSize = " + this.genKeyCurrCacheSize + "  is less than zero !   genKeyCurrVal Integer/Long = " + this.genKeyCurrValueLong + ", genKeyCacheSize = " + this.genKeyCacheSize);
      } else {
         switch (this.genKeyPKFieldClassType) {
            case 0:
               return new Integer(this.genKeyCurrValueInt++);
            case 1:
               return new Long((long)(this.genKeyCurrValueLong++));
            default:
               throw new EJBException(" Internal Error, unknown genKeyPKFieldClassType: " + this.genKeyPKFieldClassType);
         }
      }
   }

   private Object execGenKeyQuery() throws EJBException {
      Connection connection = null;
      Statement statement = null;
      ResultSet results = null;

      try {
         connection = this.getConnection();
         statement = connection.createStatement();
         results = statement.executeQuery(this.genKeyWLGeneratorQuery);
         if (!results.next()) {
            Loggable l = EJBLogger.logExecGenKeyErrorLoggable(this.genKeyWLGeneratorQuery);
            throw new EJBException(l.getMessageText());
         } else {
            switch (this.genKeyPKFieldClassType) {
               case 0:
                  Integer var11 = new Integer(results.getInt(1));
                  return var11;
               case 1:
                  Long var4 = new Long(results.getLong(1));
                  return var4;
               default:
                  throw new EJBException(" Internal Error, unknown genKeyPKFieldClassType: " + this.genKeyPKFieldClassType);
            }
         }
      } catch (SQLException var9) {
         EJBException e = new EJBException(var9.getMessage());
         throw e;
      } finally {
         this.releaseResources(connection, statement, results);
      }
   }

   private synchronized Object execGenKeySequenceTableUpdateAndQuery() throws EJBException {
      Connection connection = null;
      Statement statement = null;
      ResultSet results = null;
      Transaction callerTx = null;
      Transaction invokeTx = null;
      boolean var52 = false;

      Object var66;
      try {
         var52 = true;
         TransactionManager tms = TransactionService.getTransactionManager();
         callerTx = TransactionService.getTransaction();

         try {
            tms.suspend();
            if (runtimeLogger.isDebugEnabled()) {
               debugRuntime("execGenKeySequenceTableUpdateAndQuery: suspended Caller TX");
            }

            int txTimeoutSecondsDefault = 120;
            if (this.transactionTimeoutSeconds <= 0) {
               if (runtimeLogger.isDebugEnabled()) {
                  debugRuntime("execGenKeySequenceTableUpdateAndQuery:  WARNING !  transactionTimeoutSeconds = " + this.transactionTimeoutSeconds + " forcing to " + txTimeoutSecondsDefault);
               }

               this.transactionTimeoutSeconds = txTimeoutSecondsDefault;
            }

            tms.setTransactionTimeout(this.transactionTimeoutSeconds);
            tms.begin();
            invokeTx = tms.getTransaction();
            if (runtimeLogger.isDebugEnabled()) {
               debugRuntime("execGenKeySequenceTableUpdateAndQuery: began and obtained new Local TX for UPDATE and QUERY");
            }

            ((weblogic.transaction.Transaction)invokeTx).setProperty("LOCAL_ENTITY_TX", "true");
         } catch (Exception var60) {
            try {
               tms.resume(callerTx);
            } catch (Exception var59) {
               if (runtimeLogger.isDebugEnabled()) {
                  debugRuntime("execGenKeySequenceTableUpdateAndQuery:  Error encountered while attempting to Resume caller's TX.  Do forceResume of Caller's TX after encountering Exception: " + var59.getMessage());
               }

               ((weblogic.transaction.TransactionManager)tms).forceResume(callerTx);
            }

            Loggable l = EJBLogger.logGenKeySequenceTableNewTxFailureLoggable(var60.getMessage());
            throw new EJBException(l.getMessageText());
         }

         connection = this.getConnection();
         statement = connection.createStatement();
         String updateString = this.genKeyWLGeneratorUpdate;

         Object retval;
         Loggable l;
         try {
            int updatedRowCount = statement.executeUpdate(updateString);
            if (updatedRowCount < 1) {
               l = EJBLogger.logExecGenKeyErrorLoggable(updateString);
               throw new EJBException(l.getMessageText());
            }

            results = statement.executeQuery(this.genKeyWLGeneratorQuery);
            if (!results.next()) {
               l = EJBLogger.logExecGenKeyErrorLoggable(this.genKeyWLGeneratorQuery);
               throw new EJBException(l.getMessageText());
            }

            switch (this.genKeyPKFieldClassType) {
               case 0:
                  if (this.selectFirstSeqKeyBeforeUpdate) {
                     retval = new Integer(results.getInt(1) - this.genKeyCacheSize + 1);
                  } else {
                     retval = new Integer(results.getInt(1));
                  }
                  break;
               case 1:
                  if (this.selectFirstSeqKeyBeforeUpdate) {
                     retval = new Long(results.getLong(1) - (long)this.genKeyCacheSize + 1L);
                  } else {
                     retval = new Long(results.getLong(1));
                  }
                  break;
               default:
                  throw new EJBException(" Internal Error, unknown genKeyPKFieldClassType: " + this.genKeyPKFieldClassType);
            }
         } catch (Exception var61) {
            l = EJBLogger.logGenKeySequenceTableUpdateFailureLoggable(this.genKeyGeneratorName, var61.toString());
            throw new SQLException(l.getMessageText());
         }

         try {
            invokeTx.commit();
         } catch (Exception var58) {
            l = EJBLogger.logGenKeySequenceTableLocalCommitFailureLoggable(this.genKeyGeneratorName, var58.getMessage());
            throw new SQLException(l.getMessageText());
         }

         var66 = retval;
         var52 = false;
      } catch (SQLException var62) {
         if (invokeTx != null) {
            try {
               invokeTx.rollback();
            } catch (Exception var55) {
            }
         }

         throw new EJBException(var62);
      } finally {
         if (var52) {
            boolean releasedResources = false;
            if (callerTx != null && invokeTx != null) {
               try {
                  if (runtimeLogger.isDebugEnabled()) {
                     debugRuntime("execGenKeySequenceTableUpdateAndQuery: in finally: Now resume Caller TX");
                  }

                  TransactionService.resumeCallersTransaction(callerTx, invokeTx);
               } catch (InternalException var53) {
                  Loggable l = EJBLogger.logGenKeySequenceTableTxResumeFailureLoggable(this.genKeyGeneratorName, var53.getMessage());
                  throw new EJBException(l.getMessageText());
               } finally {
                  this.releaseResources(connection, statement, results);
                  releasedResources = true;
               }
            }

            if (!releasedResources) {
               this.releaseResources(connection, statement, results);
               releasedResources = true;
            }

         }
      }

      boolean releasedResources = false;
      if (callerTx != null && invokeTx != null) {
         try {
            if (runtimeLogger.isDebugEnabled()) {
               debugRuntime("execGenKeySequenceTableUpdateAndQuery: in finally: Now resume Caller TX");
            }

            TransactionService.resumeCallersTransaction(callerTx, invokeTx);
         } catch (InternalException var56) {
            Loggable l = EJBLogger.logGenKeySequenceTableTxResumeFailureLoggable(this.genKeyGeneratorName, var56.getMessage());
            throw new EJBException(l.getMessageText());
         } finally {
            this.releaseResources(connection, statement, results);
            releasedResources = true;
         }
      }

      if (!releasedResources) {
         this.releaseResources(connection, statement, results);
         releasedResources = true;
      }

      return var66;
   }

   public void updateKeyCacheSize(int size) {
      if (this.genKeyType == 3) {
         if (size <= 0) {
            size = 10;
         }

         synchronized(this) {
            this.genKeyCacheSize = size;
            this.genKeyWLGeneratorUpdate = this.genKeyWLGeneratorUpdatePrefix + this.genKeyCacheSize;
         }
      }

   }

   public String getGenKeySequenceDBColType() {
      return "DECIMAL";
   }

   public int getUpdateLockType() {
      if (this.isOptimistic) {
         return 4;
      } else {
         try {
            Transaction tx = this.tm.getTransaction();
            weblogic.transaction.Transaction wtx = (weblogic.transaction.Transaction)tx;
            if (wtx == null) {
               return 5;
            } else {
               Integer value = (Integer)wtx.getProperty("ISOLATION LEVEL");
               if (value != null && value == 8) {
                  return 4;
               } else {
                  value = (Integer)wtx.getProperty("SELECT_FOR_UPDATE");
                  if (value == null) {
                     return 5;
                  } else if (value == 1) {
                     return 6;
                  } else {
                     return value == 2 ? 7 : 5;
                  }
               }
            }
         } catch (Exception var4) {
            throw new PersistenceRuntimeException(var4);
         }
      }
   }

   public int getSelectForUpdateValue() {
      if (this.isOptimistic) {
         return 0;
      } else {
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
   }

   public String selectForUpdate() {
      return RDBMSUtils.selectForUpdateToString(this.getSelectForUpdateValue());
   }

   public String selectForUpdateOrForUpdateNowait() {
      try {
         Transaction tx = this.tm.getTransaction();
         weblogic.transaction.Transaction wtx = (weblogic.transaction.Transaction)tx;
         Integer i = null;
         if (wtx != null) {
            i = (Integer)wtx.getProperty("SELECT_FOR_UPDATE");
         }

         if (i == null) {
            return " FOR UPDATE ";
         } else {
            switch (i) {
               case 2:
                  return " FOR UPDATE NOWAIT ";
               default:
                  return " FOR UPDATE ";
            }
         }
      } catch (Exception var4) {
         throw new PersistenceRuntimeException(var4);
      }
   }

   public void disableTransactionStatusCheck() {
      try {
         this.beanManager.disableTransactionStatusCheck();
      } catch (Exception var2) {
         throw new PersistenceRuntimeException(var2);
      }
   }

   public void enableTransactionStatusCheck() {
      try {
         this.beanManager.enableTransactionStatusCheck();
      } catch (Exception var2) {
         throw new PersistenceRuntimeException(var2);
      }
   }

   public void registerModifiedBean(Object pk) {
      try {
         Transaction tx = TransactionService.getTransactionManager().getTransaction();
         this.beanManager.registerModifiedBean(pk, tx);
      } catch (RuntimeException var3) {
         throw var3;
      } catch (Exception var4) {
         throw new EJBException(var4);
      }
   }

   public void registerInvalidatedBean(Object pk) {
      try {
         Object txOrThread = null;
         txOrThread = TransactionService.getTransactionManager().getTransaction();
         if (txOrThread == null) {
            txOrThread = Thread.currentThread();
         }

         this.beanManager.registerInvalidatedBean(pk, txOrThread);
      } catch (RuntimeException var3) {
         throw var3;
      } catch (Exception var4) {
         throw new EJBException(var4);
      }
   }

   public void unregisterModifiedBean(Object pk) {
      try {
         Transaction tx = TransactionService.getTransactionManager().getTransaction();
         this.beanManager.unregisterModifiedBean(pk, tx);
      } catch (RuntimeException var3) {
         throw var3;
      } catch (Exception var4) {
         throw new EJBException(var4);
      }
   }

   public void flushModifiedBeans() {
      try {
         Transaction tx = TransactionService.getTransactionManager().getTransaction();
         this.beanManager.flushModifiedBeans(tx);
      } catch (RuntimeException var2) {
         throw var2;
      } catch (Exception var3) {
         throw new EJBException(var3);
      }
   }

   public void registerM2NJoinTableInsert(String cmrField, Object pk) {
      try {
         Transaction tx = TransactionService.getTransactionManager().getTransaction();
         this.beanManager.registerM2NJoinTableInsert(cmrField, pk, tx);
      } catch (RuntimeException var4) {
         throw var4;
      } catch (Exception var5) {
         throw new EJBException(var5);
      }
   }

   public void releaseResources(Connection connection, Statement statement, ResultSet results) {
      if (runtimeLogger.isDebugEnabled()) {
         debugRuntime("RDBMSPersistenceManager.releaseResources");
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

   public void releaseArrayResources(Connection connection, Statement[] statements, ResultSet[] results) {
      if (runtimeLogger.isDebugEnabled()) {
         debugRuntime("RDBMSPersistenceManager.releaseResources");
      }

      int var5;
      int var6;
      if (results != null) {
         ResultSet[] var4 = results;
         var5 = results.length;

         for(var6 = 0; var6 < var5; ++var6) {
            ResultSet result = var4[var6];

            try {
               this.releaseResultSet(result);
            } catch (SQLException var11) {
            }
         }
      }

      if (statements != null) {
         Statement[] var12 = statements;
         var5 = statements.length;

         for(var6 = 0; var6 < var5; ++var6) {
            Statement statement = var12[var6];

            try {
               this.releaseStatement(statement);
            } catch (SQLException var10) {
            }
         }
      }

      try {
         this.releaseConnection(connection);
      } catch (SQLException var9) {
      }

   }

   public void releaseConnection(Connection connection) throws SQLException {
      if (runtimeLogger.isDebugEnabled()) {
         debugRuntime("RDBMSPersistenceManager.releaseConnection");
      }

      if (connection != null) {
         connection.close();
      }

   }

   public void releaseStatement(PreparedStatement statement) throws SQLException {
      if (runtimeLogger.isDebugEnabled()) {
         debugRuntime("RDBMSPersistenceManager.releaseStatement");
      }

      if (statement != null) {
         statement.close();
      }

   }

   public void releaseStatement(Statement statement) throws SQLException {
      if (runtimeLogger.isDebugEnabled()) {
         debugRuntime("RDBMSPersistenceManager.releaseStatement");
      }

      if (statement != null) {
         statement.close();
      }

   }

   public void releaseResultSet(ResultSet results) throws SQLException {
      if (runtimeLogger.isDebugEnabled()) {
         debugRuntime("RDBMSPersistenceManager.releaseResultSet");
      }

      if (results != null) {
         results.close();
      }

   }

   public boolean createDefaultDBMSTable(String tableName) throws WLDeploymentException {
      StringBuffer sb = new StringBuffer("CREATE TABLE " + tableName + " (");
      Connection connection = null;
      Statement stmt = null;

      try {
         if (!this.getSequenceTableColumns(tableName, sb) && !this.getBeanOrJoinTableColumns(tableName, sb)) {
            throw new RDBMSException(" Unknown Error while attempting to get DB Columns for table '" + tableName + "'");
         }

         sb.append(",");
         sb.append("WLS_TEMP");
         sb.append(" int");
         if (this.databaseType == 5) {
            sb.append(" NULL ");
         }

         sb.append(" )");
         String query = sb.toString();
         if (deploymentLogger.isDebugEnabled()) {
            debugDeployment(" full DEFAULT TABLE CREATE QUERY: '" + query + "'");
         }

         connection = this.getConnection();
         stmt = connection.createStatement();
         stmt.executeUpdate(query);
      } catch (Exception var10) {
         Loggable l = EJBLogger.logerrorCreatingDefaultDBMSTableLoggable(tableName, var10.getMessage());
         l.log();
         throw new WLDeploymentException(l.getMessageText(), var10);
      } finally {
         this.releaseResources(connection, stmt, (ResultSet)null);
      }

      return true;
   }

   public void dropAndCreateDefaultDBMSTable(String tableName) throws WLDeploymentException {
      StringBuffer sb = new StringBuffer("DROP TABLE " + tableName);
      Connection connection = null;
      Statement stmt = null;

      try {
         connection = this.getConnection();
         stmt = connection.createStatement();
         stmt.executeUpdate(sb.toString());
      } catch (Exception var9) {
      } finally {
         this.releaseResources(connection, stmt, (ResultSet)null);
      }

      this.createDefaultDBMSTable(tableName);
   }

   public void alterDefaultDBMSTable(String tableName, Set newColumns, Set removedColumns) throws WLDeploymentException {
      if (newColumns.isEmpty() && removedColumns.isEmpty()) {
         if (deploymentLogger.isDebugEnabled()) {
            debugDeployment("Table not changed so no alter table");
         }

      } else {
         switch (this.databaseType) {
            case 1:
            case 10:
               this.alterOracleDefaultDBMSTable(tableName, newColumns, removedColumns);
               break;
            case 2:
            case 7:
               this.alterMSSqlServerDefaultDBMSTable(tableName, newColumns, removedColumns);
               break;
            case 3:
               this.alterInformixDefaultDBMSTable(tableName, newColumns, removedColumns);
               break;
            case 4:
               Loggable l1 = EJBLogger.logalterTableNotSupportedLoggable("DB2");
               l1.log();
               throw new WLDeploymentException(l1.getMessageText());
            case 5:
               Loggable l = EJBLogger.logalterTableNotSupportedLoggable("Sybase");
               l.log();
               throw new WLDeploymentException(l.getMessageText());
            case 6:
               this.alterPointbaseDefaultDBMSTable(tableName, newColumns, removedColumns);
               break;
            case 8:
               this.alterMySQLDefaultDBMSTable(tableName, newColumns, removedColumns);
               break;
            case 9:
            default:
               this.alterOracleDefaultDBMSTable(tableName, newColumns, removedColumns);
         }

      }
   }

   private void alterPointbaseDefaultDBMSTable(String tableName, Set newColumns, Set removedColumns) throws WLDeploymentException {
      Set pkCols = this.createPrimaryKeyCols(tableName);
      boolean newPkColAdded = this.isAnyNewColAPKCol(pkCols, newColumns);
      if (pkCols.size() > 1 && newPkColAdded) {
         EJBLogger.logalterTableNotSupportedForPointbaseLoggable();
      } else {
         this.addPointbaseColumns(tableName, newColumns, pkCols, newPkColAdded);
         this.removePointbaseColumns(tableName, removedColumns);
         this.dropMSSqlServerPrimaryKeyConstraint(tableName);
         this.createMSSqlServerPrimaryKeyConstraint(tableName);
      }
   }

   private boolean isAnyNewColAPKCol(Set pkCols, Set newColumns) {
      Iterator newIt = newColumns.iterator();

      String colName;
      do {
         if (!newIt.hasNext()) {
            return false;
         }

         colName = (String)newIt.next();
      } while(!pkCols.contains(colName));

      return true;
   }

   private List getOldPrimaryKeys(Connection conn, String tableName) {
      try {
         DatabaseMetaData dbmd = conn.getMetaData();
         ResultSet rs = dbmd.getPrimaryKeys((String)null, (String)null, tableName.toUpperCase(Locale.ENGLISH));
         List oldPks = new ArrayList();

         while(rs.next()) {
            oldPks.add(rs.getString("COLUMN_NAME"));
         }

         return oldPks;
      } catch (Exception var6) {
         return null;
      }
   }

   private void addPointbaseColumns(String tableName, Set newColumns, Set pkCols, boolean newPkColAdded) throws WLDeploymentException {
      Connection connection = null;
      Statement stmt = null;

      try {
         connection = this.getConnection();
         List oldPkCols = null;
         boolean isSimplePrimaryKey = false;
         if (newPkColAdded) {
            oldPkCols = this.getOldPrimaryKeys(connection, tableName);
            if (oldPkCols != null) {
               if (oldPkCols.size() > 1) {
                  EJBLogger.logalterTableNotSupportedForPointbaseLoggable();
                  return;
               }

               if (oldPkCols.size() == 1) {
                  isSimplePrimaryKey = true;
               }
            }
         }

         StringBuffer alterTable = new StringBuffer("alter table " + tableName + " ");
         if (!newColumns.isEmpty()) {
            Iterator newColIter = newColumns.iterator();

            while(newColIter.hasNext()) {
               alterTable.append("add  ");
               String columnName = (String)newColIter.next();
               alterTable.append(columnName + " " + this.getSqltypeForCol(tableName, columnName));
               if (newPkColAdded && pkCols.contains(columnName) && isSimplePrimaryKey) {
                  alterTable.append(" DEFAULT '" + oldPkCols.get(0) + "' NOT NULL ");
               }

               if (newColIter.hasNext()) {
                  alterTable.append(",");
               }
            }

            if (deploymentLogger.isDebugEnabled()) {
               debugDeployment("The alter table command is ..." + alterTable);
            }

            stmt = connection.createStatement();
            stmt.executeUpdate(alterTable.toString());
         }
      } catch (Exception var15) {
         Loggable l = EJBLogger.logErrorAlteringDefaultDBMSTableLoggable(tableName, var15);
         l.log();
         throw new WLDeploymentException(l.getMessageText(), var15);
      } finally {
         this.releaseResources(connection, stmt, (ResultSet)null);
      }
   }

   private void removePointbaseColumns(String tableName, Set removedColumns) throws WLDeploymentException {
      StringBuffer alterTable = new StringBuffer("alter table " + tableName + " ");
      if (!removedColumns.isEmpty()) {
         Iterator removedColIter = removedColumns.iterator();

         while(removedColIter.hasNext()) {
            alterTable.append("drop  ");
            String columnName = (String)removedColIter.next();
            alterTable.append(columnName);
            if (removedColIter.hasNext()) {
               alterTable.append(",");
            }
         }

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
            throw new WLDeploymentException(l.getMessageText(), var11);
         } finally {
            this.releaseResources(connection, stmt, (ResultSet)null);
         }

      }
   }

   private void alterMySQLDefaultDBMSTable(String tableName, Set newColumns, Set removedColumns) throws WLDeploymentException {
      Set pkCols = this.createPrimaryKeyCols(tableName);
      boolean newPkColAdded = this.isAnyNewColAPKCol(pkCols, newColumns);
      if (pkCols.size() > 1 && newPkColAdded) {
         EJBLogger.logalterTableNotSupportedForPointbaseLoggable();
      } else {
         this.addMySQLColumns(tableName, newColumns, pkCols, newPkColAdded);
         this.removeMySQLColumns(tableName, removedColumns);
         this.dropMSSqlServerPrimaryKeyConstraint(tableName);
         this.createMSSqlServerPrimaryKeyConstraint(tableName);
      }
   }

   private void addMySQLColumns(String tableName, Set newColumns, Set pkCols, boolean newPkColAdded) throws WLDeploymentException {
      Connection connection = null;
      Statement stmt = null;

      try {
         connection = this.getConnection();
         List oldPkCols = null;
         boolean isSimplePrimaryKey = false;
         if (newPkColAdded) {
            oldPkCols = this.getOldPrimaryKeys(connection, tableName);
            if (oldPkCols != null) {
               if (oldPkCols.size() > 1) {
                  EJBLogger.logalterTableNotSupportedForPointbaseLoggable();
                  return;
               }

               if (oldPkCols.size() == 1) {
                  isSimplePrimaryKey = true;
               }
            }
         }

         StringBuffer alterTable = new StringBuffer("alter table " + tableName + " ");
         if (!newColumns.isEmpty()) {
            Iterator newColIter = newColumns.iterator();

            while(newColIter.hasNext()) {
               String columnName = (String)newColIter.next();
               alterTable.append("add column ");
               alterTable.append(columnName + " " + this.getSqltypeForCol(tableName, columnName));
               if (newPkColAdded && pkCols.contains(columnName) && isSimplePrimaryKey) {
                  alterTable.append(" DEFAULT '" + oldPkCols.get(0) + "' NOT NULL ");
               }

               if (newColIter.hasNext()) {
                  alterTable.append(",");
               }
            }

            if (deploymentLogger.isDebugEnabled()) {
               debugDeployment("The alter table command is ..." + alterTable);
            }

            stmt = connection.createStatement();
            stmt.executeUpdate(alterTable.toString());
         }
      } catch (Exception var15) {
         Loggable l = EJBLogger.logErrorAlteringDefaultDBMSTableLoggable(tableName, var15);
         l.log();
         throw new WLDeploymentException(l.getMessageText(), var15);
      } finally {
         this.releaseResources(connection, stmt, (ResultSet)null);
      }
   }

   private void removeMySQLColumns(String tableName, Set removedColumns) throws WLDeploymentException {
      StringBuffer alterTable = new StringBuffer("alter table " + tableName + " ");
      if (!removedColumns.isEmpty()) {
         Iterator removedColIter = removedColumns.iterator();

         while(removedColIter.hasNext()) {
            String columnName = (String)removedColIter.next();
            alterTable.append("drop column ");
            alterTable.append(columnName);
            if (removedColIter.hasNext()) {
               alterTable.append(",");
            }
         }

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
            throw new WLDeploymentException(l.getMessageText(), var11);
         } finally {
            this.releaseResources(connection, stmt, (ResultSet)null);
         }

      }
   }

   private void alterMSSqlServerDefaultDBMSTable(String tableName, Set newColumns, Set removedColumns) throws WLDeploymentException {
      this.dropMSSqlServerPrimaryKeyConstraint(tableName);
      this.addMSSqlServerColumns(tableName, newColumns);
      this.removeMSSqlServerColumns(tableName, removedColumns);
      this.createMSSqlServerPrimaryKeyConstraint(tableName);
   }

   private void dropMSSqlServerPrimaryKeyConstraint(String tableName) throws WLDeploymentException {
      StringBuffer dropPkCons = new StringBuffer("alter table ");
      dropPkCons.append(tableName);
      dropPkCons.append(" drop CONSTRAINT pk_" + tableName);
      Connection connection = null;
      Statement stmt = null;
      if (deploymentLogger.isDebugEnabled()) {
         debugDeployment("the alter table command is .." + dropPkCons);
      }

      try {
         connection = this.getConnection();
         stmt = connection.createStatement();
         stmt.executeUpdate(dropPkCons.toString());
      } catch (Exception var9) {
      } finally {
         this.releaseResources(connection, stmt, (ResultSet)null);
      }

   }

   private void createMSSqlServerPrimaryKeyConstraint(String tableName) throws WLDeploymentException {
      Set pkCols = this.createPrimaryKeyCols(tableName);
      if (pkCols.size() > 0) {
         StringBuffer createPkCons = new StringBuffer("alter table ");
         createPkCons.append(tableName);
         createPkCons.append(" add constraint pk_" + tableName + " PRIMARY KEY(");
         Iterator it = pkCols.iterator();

         while(it.hasNext()) {
            String s = (String)it.next();
            createPkCons.append(s);
            if (it.hasNext()) {
               createPkCons.append(", ");
            }
         }

         createPkCons.append(")");
         if (deploymentLogger.isDebugEnabled()) {
            debugDeployment("the alter table command is .." + createPkCons);
         }

         Connection connection = null;
         Statement stmt = null;

         try {
            connection = this.getConnection();
            stmt = connection.createStatement();
            stmt.executeUpdate(createPkCons.toString());
         } catch (Exception var12) {
            Loggable l = EJBLogger.logErrorAlteringDefaultDBMSTableLoggable(tableName, var12);
            l.log();
            throw new WLDeploymentException(l.getMessageText(), var12);
         } finally {
            this.releaseResources(connection, stmt, (ResultSet)null);
         }
      }

   }

   private void removeMSSqlServerColumns(String tableName, Set removedColumns) throws WLDeploymentException {
      StringBuffer alterTable = new StringBuffer("alter table " + tableName + " ");
      if (!removedColumns.isEmpty()) {
         alterTable.append("drop column ");
         Iterator removedColIter = removedColumns.iterator();

         while(removedColIter.hasNext()) {
            String columnName = (String)removedColIter.next();
            alterTable.append(columnName);
            if (removedColIter.hasNext()) {
               alterTable.append(",");
            }
         }

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
            throw new WLDeploymentException(l.getMessageText(), var11);
         } finally {
            this.releaseResources(connection, stmt, (ResultSet)null);
         }

      }
   }

   private void addMSSqlServerColumns(String tableName, Set newColumns) throws WLDeploymentException {
      StringBuffer alterTable = new StringBuffer("alter table " + tableName + " ");
      if (!newColumns.isEmpty()) {
         alterTable.append("add  ");
         Iterator newColIter = newColumns.iterator();

         while(newColIter.hasNext()) {
            String columnName = (String)newColIter.next();
            alterTable.append(columnName + " " + this.getSqltypeForCol(tableName, columnName));
            if (newColIter.hasNext()) {
               alterTable.append(",");
            }
         }

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
            throw new WLDeploymentException(l.getMessageText(), var11);
         } finally {
            this.releaseResources(connection, stmt, (ResultSet)null);
         }

      }
   }

   private void alterInformixDefaultDBMSTable(String tableName, Set newColumns, Set removedColumns) throws WLDeploymentException {
      this.addOracleColumns(tableName, newColumns);
      this.removeOracleColumns(tableName, removedColumns);
      this.alterInformixPrimaryKeyConstraints(tableName);
   }

   private void alterOracleDefaultDBMSTable(String tableName, Set newColumns, Set removedColumns) throws WLDeploymentException {
      this.addOracleColumns(tableName, newColumns);
      this.removeOracleColumns(tableName, removedColumns);
      this.alterPrimaryKeyConstraints(tableName, newColumns, removedColumns);
   }

   private void alterInformixPrimaryKeyConstraints(String tableName) throws WLDeploymentException {
      Set pkCols = this.createPrimaryKeyCols(tableName);
      if (pkCols.size() > 0) {
         this.createInformixPrimaryKeyConstraint(tableName, pkCols);
      }

   }

   private void createInformixPrimaryKeyConstraint(String tableName, Set pkCols) throws WLDeploymentException {
      if (pkCols.size() > 0) {
         StringBuffer createPkCons = new StringBuffer("alter table ");
         createPkCons.append(tableName);
         createPkCons.append(" add constraint PRIMARY KEY (");
         Iterator it = pkCols.iterator();

         while(it.hasNext()) {
            String s = (String)it.next();
            createPkCons.append(s);
            if (it.hasNext()) {
               createPkCons.append(", ");
            }
         }

         createPkCons.append(") CONSTRAINT pk_" + tableName);
         Connection connection = null;
         Statement stmt = null;

         try {
            connection = this.getConnection();
            stmt = connection.createStatement();
            stmt.executeUpdate(createPkCons.toString());
         } catch (Exception var12) {
            Loggable l = EJBLogger.logErrorAlteringDefaultDBMSTableLoggable(tableName, var12);
            throw new WLDeploymentException(l.getMessageText(), var12);
         } finally {
            this.releaseResources(connection, stmt, (ResultSet)null);
         }
      }

   }

   private void alterPrimaryKeyConstraints(String tableName, Set newColumns, Set removedColumns) throws WLDeploymentException {
      Set pkCols = this.createPrimaryKeyCols(tableName);
      if (pkCols.size() > 0) {
         this.dropOraclePrimaryKeyConstraint(tableName);
         this.createOraclePrimaryKeyConstraint(tableName, pkCols);
      }

   }

   private void createOraclePrimaryKeyConstraint(String tableName, Set pkCols) throws WLDeploymentException {
      if (pkCols.size() > 0) {
         StringBuffer createPkCons = new StringBuffer("alter table ");
         createPkCons.append(tableName);
         createPkCons.append(" add constraint pk_" + tableName + " PRIMARY KEY(");
         Iterator it = pkCols.iterator();

         while(it.hasNext()) {
            String s = (String)it.next();
            createPkCons.append(s);
            if (it.hasNext()) {
               createPkCons.append(", ");
            }
         }

         createPkCons.append(")");
         if (deploymentLogger.isDebugEnabled()) {
            debugDeployment("The alter command is ..." + createPkCons.toString());
         }

         Connection connection = null;
         Statement stmt = null;

         try {
            connection = this.getConnection();
            stmt = connection.createStatement();
            stmt.executeUpdate(createPkCons.toString());
         } catch (Exception var12) {
            Loggable l = EJBLogger.logErrorAlteringDefaultDBMSTableLoggable(tableName, var12);
            l.log();
            throw new WLDeploymentException(l.getMessageText(), var12);
         } finally {
            this.releaseResources(connection, stmt, (ResultSet)null);
         }
      }

   }

   private void dropOraclePrimaryKeyConstraint(String tableName) throws WLDeploymentException {
      StringBuffer dropPkCons = new StringBuffer("alter table ");
      dropPkCons.append(tableName);
      dropPkCons.append(" drop PRIMARY KEY CASCADE");
      Connection connection = null;
      Statement stmt = null;
      if (deploymentLogger.isDebugEnabled()) {
         debugDeployment("the alter command is ..." + dropPkCons);
      }

      try {
         connection = this.getConnection();
         stmt = connection.createStatement();
         stmt.executeUpdate(dropPkCons.toString());
      } catch (Exception var9) {
      } finally {
         this.releaseResources(connection, stmt, (ResultSet)null);
      }

   }

   private void removeOracleColumns(String tableName, Set removedColumns) throws WLDeploymentException {
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
            throw new WLDeploymentException(l.getMessageText(), var11);
         } finally {
            this.releaseResources(connection, stmt, (ResultSet)null);
         }

      }
   }

   private void addOracleColumns(String tableName, Set newColumns) throws WLDeploymentException {
      StringBuffer alterTable = new StringBuffer("alter table " + tableName + " ");
      if (!newColumns.isEmpty()) {
         alterTable.append("add ( ");
         Iterator newColIter = newColumns.iterator();

         while(newColIter.hasNext()) {
            String columnName = (String)newColIter.next();
            alterTable.append(columnName + " " + this.getSqltypeForCol(tableName, columnName));
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
            this.releaseResources(connection, stmt, (ResultSet)null);
         }

      }
   }

   private static void debugDeployment(String s) {
      deploymentLogger.debug("[RDBMSPersistenceManager] " + s);
   }

   private static void debugRuntime(String s) {
      runtimeLogger.debug("[RDBMSPersistenceManager] " + s);
   }

   private static void debugRuntime(String s, Throwable th) {
      runtimeLogger.debug("[RDBMSPersistenceManager] " + s, th);
   }

   public RDBMSBean getRDBMSBean() {
      return this.rbean;
   }

   public int getDatabaseType() {
      return this.databaseType;
   }

   private void initializeDBProductAndDriverInfo() {
      Connection conn = null;

      try {
         conn = this.getConnection();
         DatabaseMetaData dbmd = conn.getMetaData();
         this.driverName = dbmd.getDriverName();
         this.driverVersion = dbmd.getDriverVersion();
         this.databaseProductName = dbmd.getDatabaseProductName();
         this.databaseProductVersion = dbmd.getDatabaseProductVersion();
         this.driverMajorVersion = dbmd.getDriverMajorVersion();
         this.driverMinorVersion = dbmd.getDriverMinorVersion();
      } catch (SQLException var7) {
         if (deploymentLogger.isDebugEnabled()) {
            debugDeployment("SQLException while initializing DatabaseMetaData related product/driver info: " + var7.getMessage());
         }
      } catch (Throwable var8) {
         if (deploymentLogger.isDebugEnabled()) {
            debugDeployment("Exception while initializing DatabaseMetaData related product/driver info: " + var8.getMessage());
         }
      } finally {
         if (deploymentLogger.isDebugEnabled()) {
            debugDeployment("Printing the DatabaseMetadata related product/driver info");
            if (conn != null) {
               debugDeployment("Connection class is " + conn.getClass().getName());
            }

            debugDeployment("DatabaseProductName is  :  " + this.databaseProductName);
            debugDeployment("DatabaseProductVersion is  :  " + this.databaseProductVersion);
            debugDeployment("DriverName is  :  " + this.driverName);
            debugDeployment("DriverVersion is  :  " + this.driverVersion);
            debugDeployment("DriverMajorVersion is  :  " + this.driverMajorVersion);
            debugDeployment("DriverMinorVersion is  :  " + this.driverMinorVersion);
         }

         this.releaseResources(conn, (Statement)null, (ResultSet)null);
      }

   }

   public boolean perhapsUseSetStringForClobForOracle() {
      return this.driverName.equalsIgnoreCase("Oracle JDBC driver") && this.driverMajorVersion >= 10;
   }

   static {
      deploymentLogger = EJBDebugService.cmpDeploymentLogger;
      runtimeLogger = EJBDebugService.cmpRuntimeLogger;
      byteArray = new byte[0];
      charArray = new char[0];
   }
}
