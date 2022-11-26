package weblogic.ejb.container.cmp.rdbms.finders;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;
import javax.ejb.EJBObject;
import javax.ejb.EntityBean;
import javax.ejb.FinderException;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.InternalException;
import weblogic.ejb.container.cmp.rdbms.RDBMSBean;
import weblogic.ejb.container.cmp.rdbms.RDBMSUtils;
import weblogic.ejb.container.cmp.rdbms.SqlShape;
import weblogic.ejb.container.cmp.rdbms.codegen.CodeGenUtils;
import weblogic.ejb.container.compliance.Log;
import weblogic.ejb.container.dd.DDConstants;
import weblogic.ejb.container.interfaces.ClientDrivenBeanInfo;
import weblogic.ejb.container.internal.QueryCachingHandler;
import weblogic.ejb.container.manager.BaseEntityManager;
import weblogic.ejb.container.manager.TTLManager;
import weblogic.ejb.container.persistence.spi.CMPBean;
import weblogic.ejb.container.persistence.spi.CMPBeanDescriptor;
import weblogic.ejb.container.persistence.spi.EjbRelation;
import weblogic.ejb.container.persistence.spi.EjbRelationshipRole;
import weblogic.ejb.container.persistence.spi.RoleSource;
import weblogic.ejb.container.utils.MethodUtils;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.ejb20.cmp.rdbms.finders.InvalidFinderException;
import weblogic.i18n.logging.Loggable;
import weblogic.jdbc.rowset.WLCachedRowSet;
import weblogic.utils.AssertionError;

public class SqlFinder extends Finder {
   private Method method;
   private Method secondMethod;
   private Map sqlQueries;
   private String sqlShapeName;
   private String query;
   private RDBMSBean rdbmsBean;
   private int[] query2method;
   private boolean initialized = false;
   private int columnCount = 0;
   private Field[] columnFields;
   private Method[] columnMethods;
   private int[] columnIndices;
   private boolean[] columnSetsPrimaryKey;
   private Field[] columnPrimaryKeyFields;
   private Field[] columnOptimisticFields;
   private String[] columnTypes;
   private Class[] columnClasses;
   private int[] columnIsLoadedIndices;
   private BaseEntityManager[] resultManagers;
   private RDBMSBean[] resultBeans;
   private int[] relationIndex1;
   private int[] relationIndex2;
   private Method[] relationMethod1;
   private Method[] relationMethod2;
   private List externalMethodParmList = null;
   private boolean usesStoredProcedure = false;
   private boolean usesStoredFunction = false;
   private boolean usesRelationshipCaching = false;
   private boolean isDynamicFinder = false;
   private String[] cmrFieldFinderMethodNames1;
   private String[] cmrFieldFinderMethodNames2;
   private int[] cmrFieldFinderReturnTypes1;
   private int[] cmrFieldFinderReturnTypes2;
   private static final Pattern FROM_PATTERN = Pattern.compile("(?i)\\bfrom\\s+([a-zA-Z_0-9.]+)");
   private static final int NO_MAPPING = -1;

   public SqlFinder(String name, Map sqlQueries, String sqlShapeName, RDBMSBean rdbmsBean) throws InvalidFinderException {
      super(name, true);
      this.sqlShapeName = sqlShapeName;
      this.sqlQueries = sqlQueries;
      this.rdbmsBean = rdbmsBean;
   }

   public void setMethods(Method[] methods) throws Exception {
      assert methods[0] != null;

      super.setMethod(methods[0]);
      this.setParameterClassTypes(methods[0].getParameterTypes());
      this.method = methods[0];
      this.secondMethod = methods[1];
   }

   public void setup(int databaseType) throws WLDeploymentException {
      this.query = (String)this.sqlQueries.get(new Integer(databaseType));
      if (this.query == null) {
         this.query = (String)this.sqlQueries.get(new Integer(0));
         if (this.query == null) {
            throw new WLDeploymentException("Error: no SQL query specified for " + DDConstants.getDBNameForType(databaseType) + "database type in finder method " + this.getName() + "of EJB " + this.rdbmsBean.getEjbName());
         }
      }

      if (Pattern.matches("\\{\\s*call.*\\}", this.query)) {
         this.usesStoredProcedure = true;
      } else if (Pattern.matches("\\{\\s*\\?\\s*=\\s*call.*\\}", this.query)) {
         this.usesStoredFunction = true;
      }

      if ((this.usesStoredProcedure || this.usesStoredFunction) && !this.hasSqlShape()) {
         Loggable log = EJBLogger.logNoSqlShapeSpecifiedLoggable(this.getName());
         throw new EJBException(log.getMessage());
      } else {
         this.sqlQuery = this.query;
         this.sqlQueryForUpdate = this.query;
         this.sqlQueryForUpdateNoWait = this.query;
         Pattern p = Pattern.compile("\\?\\d+");
         int matchIndex = 0;
         List temporary = new ArrayList();
         Matcher m = p.matcher(this.query);
         if (debugLogger.isDebugEnabled()) {
            debug("starting query- " + this.query);
         }

         while(m.find(matchIndex)) {
            String paramString = m.group();
            if (debugLogger.isDebugEnabled()) {
               debug("parameter- " + paramString);
            }

            matchIndex = m.end();
            String methodIndex = paramString.substring(1);
            temporary.add(Integer.parseInt(methodIndex) - 1);
         }

         this.query2method = new int[temporary.size()];

         for(int i = 0; i < temporary.size(); ++i) {
            this.query2method[i] = (Integer)temporary.get(i);
            if (debugLogger.isDebugEnabled()) {
               debug("query indghostlyex- " + i + "method index- " + this.query2method[i]);
            }
         }

         this.query = m.replaceAll(" ? ");
         if (debugLogger.isDebugEnabled()) {
            debug("ending query- " + this.query);
         }

         if (this.sqlShapeName != null) {
            SqlShape sqlShape = this.rdbmsBean.getSqlShape(this.sqlShapeName);
            String[] relationNames = sqlShape.getEjbRelationNames();
            this.usesRelationshipCaching = relationNames != null;
         }

      }
   }

   public void setupDynamic(String sql) {
      this.query = sql;
      Pattern p = Pattern.compile("\\?");
      int matchIndex = 0;
      int count = 0;
      List temporary = new ArrayList();
      Matcher m = p.matcher(this.query);
      if (debugLogger.isDebugEnabled()) {
         debug("starting query- " + this.query);
      }

      while(m.find(matchIndex)) {
         String paramString = m.group();
         if (debugLogger.isDebugEnabled()) {
            debug("parameter- " + paramString);
         }

         matchIndex = m.end();
         temporary.add(new Integer(count++));
      }

      this.query2method = new int[temporary.size()];

      for(int i = 0; i < temporary.size(); ++i) {
         this.query2method[i] = (Integer)temporary.get(i);
         if (debugLogger.isDebugEnabled()) {
            debug("query indghostlyex- " + i + "method index- " + this.query2method[i]);
         }
      }

      this.isDynamicFinder = true;
      if (debugLogger.isDebugEnabled()) {
         debug("ending query- " + this.query);
      }

   }

   private boolean alreadyMapped(List whatsMapped, int beanIndex, String table, String column) {
      if (beanIndex == -1) {
         return false;
      } else {
         Map table2columns = (Map)whatsMapped.get(beanIndex);
         List columns = (List)table2columns.get(table);
         return columns == null ? false : columns.contains(column);
      }
   }

   private void updateMapped(List whatsMapped, int beanIndex, String table, String column) {
      Map table2columns = (Map)whatsMapped.get(beanIndex);

      assert table2columns != null;

      List columns = (List)table2columns.get(table);
      if (columns == null) {
         columns = new ArrayList();
         table2columns.put(table, columns);
      }

      if (!((List)columns).contains(column)) {
         ((List)columns).add(column);
      }

   }

   private boolean hasSqlShape() {
      return this.sqlShapeName != null;
   }

   private void getSqlMetadata(ResultSet rs, List sqlColumns, List sqlTables, List relationshipRoles) throws SQLException {
      ResultSetMetaData metadata = rs.getMetaData();
      int columnCount = metadata.getColumnCount();
      if (this.hasSqlShape()) {
         SqlShape sqlShape = this.rdbmsBean.getSqlShape(this.sqlShapeName);
         Iterator tables = sqlShape.getTables().iterator();

         while(true) {
            while(tables.hasNext()) {
               SqlShape.Table table = (SqlShape.Table)tables.next();
               if (table == null) {
                  sqlColumns.add((Object)null);
                  sqlTables.add((Object)null);
                  relationshipRoles.add((Object)null);
               } else {
                  Iterator columns = table.getColumns().iterator();

                  while(columns.hasNext()) {
                     String column = (String)columns.next();
                     sqlColumns.add(column);
                     sqlTables.add(table.getName());
                     relationshipRoles.add(table.getEjbRelationshipRoleNames());
                  }
               }
            }

            for(int i = 0; i < sqlShape.getPassThroughColumns(); ++i) {
               sqlColumns.add((Object)null);
               sqlTables.add((Object)null);
               relationshipRoles.add((Object)null);
            }

            if (sqlColumns.size() != columnCount) {
               Loggable log = EJBLogger.logErrorExecuteFinderLoggable(this.getName(), sqlShape.getSqlShapeName(), sqlColumns.size() + "", columnCount + "");
               throw new EJBException(log.getMessage());
            }
            break;
         }
      } else {
         String tableGuess = null;
         if (this.isSingletonSelect()) {
            if (columnCount != 1) {
               throw new EJBException("Too many columns (" + columnCount + ") selected by query '" + this.query + "'.  " + this.getName() + " returns a value of type " + this.getReturnClassType().getName() + " and requires and SQL query that selects a single value.");
            }

            sqlColumns.add((Object)null);
            sqlTables.add((Object)null);
         } else {
            for(int columnIndex = 1; columnIndex <= columnCount; ++columnIndex) {
               String column = null;
               String table = null;

               try {
                  column = metadata.getColumnName(columnIndex);
                  table = metadata.getTableName(columnIndex);
               } catch (Exception var12) {
               }

               if (column == null || column.length() == 0) {
                  throw new EJBException("Unknown column name for column- " + column + "of query- " + this.query + ".  Use a SqlShape element to specify the column name.");
               }

               if (table == null || table.length() == 0) {
                  if (tableGuess == null) {
                     tableGuess = this.guessTableName();
                     if (tableGuess == null || tableGuess.length() == 0) {
                        throw new EJBException("Unknown table name for column- " + column + "of query- " + this.query + ".  Use a SqlShape element to specifying the table name.");
                     }

                     tableGuess = tableGuess.trim();
                  }

                  table = tableGuess;
               }

               sqlColumns.add(column);
               sqlTables.add(table);
               relationshipRoles.add((Object)null);
            }
         }
      }

   }

   private String guessTableName() {
      Matcher m = FROM_PATTERN.matcher(this.query);
      if (!m.find()) {
         return null;
      } else {
         String table = m.group(1);
         return table;
      }
   }

   public synchronized void initializeMapping(ResultSet rs) throws FinderException, SQLException {
      if (!this.initialized) {
         List sqlColumns = new ArrayList();
         List sqlTables = new ArrayList();
         List columnRoles = new ArrayList();
         Map bean2tableNames = new HashMap();
         Map bean2tableName2columnNames = new HashMap();
         this.getSqlMetadata(rs, sqlColumns, sqlTables, columnRoles);
         this.columnCount = sqlColumns.size();
         if (debugLogger.isDebugEnabled()) {
            debug("column count-" + this.columnCount);
         }

         List managers = new ArrayList();
         List beans = new ArrayList();
         List whatsMapped = new ArrayList();
         List primaryKeyFields = new ArrayList();
         List beanRoles = new ArrayList();
         this.columnFields = new Field[this.columnCount];
         this.columnMethods = new Method[this.columnCount];
         this.columnIndices = new int[this.columnCount];
         this.columnSetsPrimaryKey = new boolean[this.columnCount];
         this.columnPrimaryKeyFields = new Field[this.columnCount];
         this.columnIsLoadedIndices = new int[this.columnCount];
         this.columnOptimisticFields = new Field[this.columnCount];
         this.columnTypes = new String[this.columnCount];
         this.columnClasses = new Class[this.columnCount];

         int columnIndex;
         for(columnIndex = 0; columnIndex < this.columnCount; ++columnIndex) {
            if (debugLogger.isDebugEnabled()) {
               debug("current column- " + columnIndex);
            }

            String column = (String)sqlColumns.get(columnIndex);
            String table = (String)sqlTables.get(columnIndex);
            if (debugLogger.isDebugEnabled()) {
               debug("column name-" + column);
               debug("table name-" + table);
            }

            List roles = null;
            boolean mapped = false;
            if (table == null && column == null) {
               this.columnIndices[columnIndex] = whatsMapped.size();
               managers.add((Object)null);
               beans.add((Object)null);
               whatsMapped.add((Object)null);
               primaryKeyFields.add((Object)null);
               beanRoles.add((Object)null);
               if (this.isSingletonSelect()) {
                  this.columnClasses[columnIndex] = this.getReturnClassType();
               } else {
                  this.columnClasses[columnIndex] = Object.class;
               }

               mapped = true;
            }

            if (!mapped) {
               roles = (List)columnRoles.get(columnIndex);
               int beanIndex = 0;

               for(Iterator beansIterator = beans.iterator(); beansIterator.hasNext() && !mapped; mapped = this.mapTableAndColumn((RDBMSBean)beansIterator.next(), table, column, roles, columnIndex, managers, beans, whatsMapped, primaryKeyFields, beanRoles, beanIndex++, bean2tableNames, bean2tableName2columnNames)) {
               }
            }

            if (!mapped) {
               mapped = this.mapTableAndColumn(this.rdbmsBean, table, column, roles, columnIndex, managers, beans, whatsMapped, primaryKeyFields, beanRoles, -1, bean2tableNames, bean2tableName2columnNames);
            }

            RDBMSBean bean;
            if (!mapped) {
               for(Iterator beansIterator = this.rdbmsBean.getRdbmsBeanMap().values().iterator(); beansIterator.hasNext() && !mapped; mapped = this.mapTableAndColumn(bean, table, column, roles, columnIndex, managers, beans, whatsMapped, primaryKeyFields, beanRoles, -1, bean2tableNames, bean2tableName2columnNames)) {
                  bean = (RDBMSBean)beansIterator.next();
               }
            }

            if (!mapped) {
               Loggable log = EJBLogger.logErrorMapColumnLoggable(table, column, this.query);
               throw new FinderException(log.getMessage());
            }
         }

         this.resultManagers = (BaseEntityManager[])((BaseEntityManager[])managers.toArray(new BaseEntityManager[0]));
         this.resultBeans = (RDBMSBean[])((RDBMSBean[])beans.toArray(new RDBMSBean[0]));
         this.validateMapping(this.resultBeans, whatsMapped);
         this.initializeRelationshipCaching(beans, beanRoles);
         if (this.isQueryCachingEnabled()) {
            for(columnIndex = 0; columnIndex < this.resultManagers.length; ++columnIndex) {
               if (this.resultManagers[columnIndex] != null && !this.resultManagers[columnIndex].isReadOnly()) {
                  Log.getInstance().logWarning(this.fmt.QUERY_CACHING_SQLFINDER_RETURNS_RW_BEAN(this.rdbmsBean.getEjbName(), this.getName(), this.resultManagers[columnIndex].getBeanInfo().getEJBName()));
                  this.setQueryCachingEnabled(false);
               }
            }
         }

         this.initialized = true;
      }

   }

   private void initializeRelationshipCaching(List beans, List beanRoles) {
      if (this.sqlShapeName != null) {
         if (debugLogger.isDebugEnabled()) {
            debug("------------------initializeRelationshipCaching");
         }

         SqlShape sqlShape = this.rdbmsBean.getSqlShape(this.sqlShapeName);
         String[] relationNames = sqlShape.getEjbRelationNames();
         if (relationNames != null) {
            this.relationIndex1 = new int[relationNames.length];
            this.relationIndex2 = new int[relationNames.length];
            this.relationMethod1 = new Method[relationNames.length];
            this.relationMethod2 = new Method[relationNames.length];
            this.cmrFieldFinderMethodNames1 = new String[relationNames.length];
            this.cmrFieldFinderMethodNames2 = new String[relationNames.length];
            this.cmrFieldFinderReturnTypes1 = new int[relationNames.length];
            this.cmrFieldFinderReturnTypes2 = new int[relationNames.length];

            for(int i = 0; i < relationNames.length; ++i) {
               EjbRelation ejbRelation = this.rdbmsBean.getEjbRelation(relationNames[i]);
               if (debugLogger.isDebugEnabled()) {
                  debug("------------------relation name" + ejbRelation.getEjbRelationName());
               }

               Iterator roles = ejbRelation.getAllEjbRelationshipRoles().iterator();
               EjbRelationshipRole role1 = (EjbRelationshipRole)roles.next();
               EjbRelationshipRole role2 = (EjbRelationshipRole)roles.next();
               RoleSource source1 = role1.getRoleSource();
               RoleSource source2 = role2.getRoleSource();
               String ejbName1 = source1.getEjbName();
               String ejbName2 = source2.getEjbName();
               int index1 = this.getIndex(ejbName1, role1.getName(), beans, beanRoles, -1, ejbName1.equals(ejbName2), ejbRelation.getEjbRelationName());
               int index2 = this.getIndex(ejbName2, role2.getName(), beans, beanRoles, index1, ejbName1.equals(ejbName2), ejbRelation.getEjbRelationName());

               assert index1 != index2;

               this.relationIndex1[i] = index1;
               this.relationIndex2[i] = index2;
               String field1 = RDBMSUtils.getCmrFieldName(role1, role2);
               String field2 = RDBMSUtils.getCmrFieldName(role2, role1);
               String methodName1 = CodeGenUtils.cacheRelationshipMethodName(field1);
               String methodName2 = CodeGenUtils.cacheRelationshipMethodName(field2);
               this.setRelationMethod(this.relationMethod1, i, index1, methodName1);
               this.setRelationMethod(this.relationMethod2, i, index2, methodName2);
               Class cmrFieldType;
               if (this.isQueryCachingEnabled() || this.getRDBMSBean(index1).isQueryCachingEnabledForCMRField(field1)) {
                  this.cmrFieldFinderMethodNames1[i] = CodeGenUtils.getCMRFieldFinderMethodName(this.getRDBMSBean(index1), field1);
                  cmrFieldType = this.getRDBMSBean(index1).getCmrFieldClass(field1);
                  if (Set.class.isAssignableFrom(cmrFieldType)) {
                     this.cmrFieldFinderReturnTypes1[i] = 1;
                  } else {
                     this.cmrFieldFinderReturnTypes1[i] = 3;
                  }

                  if (debugLogger.isDebugEnabled()) {
                     debug("CMRField1: " + field1);
                     debug("CMRFieldFinderMethod: " + this.cmrFieldFinderMethodNames1[i]);
                     debug("CMRFieldFinderReturnType: " + this.cmrFieldFinderReturnTypes1[i]);
                     debug("MGR: " + this.resultManagers[this.relationIndex2[i]].getBeanInfo().getEJBName());
                  }
               }

               if (this.isQueryCachingEnabled() || this.getRDBMSBean(index2).isQueryCachingEnabledForCMRField(field2)) {
                  this.cmrFieldFinderMethodNames2[i] = CodeGenUtils.getCMRFieldFinderMethodName(this.getRDBMSBean(index2), field2);
                  cmrFieldType = this.getRDBMSBean(index2).getCmrFieldClass(field2);
                  if (Set.class.isAssignableFrom(cmrFieldType)) {
                     this.cmrFieldFinderReturnTypes2[i] = 1;
                  } else {
                     this.cmrFieldFinderReturnTypes2[i] = 3;
                  }

                  if (debugLogger.isDebugEnabled()) {
                     debug("CMRField: " + field2);
                     debug("CMRFieldFinderMethod: " + this.cmrFieldFinderMethodNames2[i]);
                     debug("CMRFieldFinderReturnType: " + this.cmrFieldFinderReturnTypes2[i]);
                     debug("MGR: " + this.resultManagers[this.relationIndex1[i]].getBeanInfo().getEJBName());
                  }
               }
            }
         }

      }
   }

   private void setRelationMethod(Method[] relationMethod, int relationMethodIndex, int beanIndex, String methodName) {
      ClientDrivenBeanInfo info = (ClientDrivenBeanInfo)this.resultManagers[beanIndex].getBeanInfo();
      Class beanClassInterface = info.getGeneratedBeanInterface();

      try {
         relationMethod[relationMethodIndex] = beanClassInterface.getMethod(methodName, Object.class);
      } catch (NoSuchMethodException var8) {
         throw new AssertionError("the legal relationship setting method: name-" + methodName);
      }
   }

   private int getIndex(String ejbName, String role, List beans, List beanRoles, int taken, boolean requiresRole, String relationship) {
      if (debugLogger.isDebugEnabled()) {
         debug("ejbName-" + ejbName);
         debug("role-" + role);
         debug("taken-" + taken);
         debug("requiresRole-" + requiresRole);
         debug("relationship-" + relationship);
      }

      int i;
      RDBMSBean bean;
      for(i = 0; i < beans.size(); ++i) {
         bean = (RDBMSBean)beans.get(i);
         Set roles = (Set)beanRoles.get(i);
         if (ejbName.equals(bean.getEjbName()) && roles.contains(role) && (taken == -1 || taken != i)) {
            return i;
         }
      }

      if (!requiresRole) {
         for(i = 0; i < beans.size(); ++i) {
            bean = (RDBMSBean)beans.get(i);
            if (ejbName.equals(bean.getEjbName()) && (taken == -1 || taken != i)) {
               return i;
            }
         }
      }

      String candidates = "";

      for(int i = 0; i < beans.size(); ++i) {
         RDBMSBean bean = (RDBMSBean)beans.get(i);
         Set roles = (Set)beanRoles.get(i);
         if (i > 0) {
            candidates = candidates + ", ";
         }

         candidates = candidates + bean.getEjbName();
      }

      Loggable log = EJBLogger.logErrorMapRelatioshipLoggable(this.getName(), relationship, role, this.query, candidates);
      throw new EJBException(log.getMessage());
   }

   private void validateMapping(RDBMSBean[] resultBeans, List whatsMapped) {
      for(int beanIndex = 0; beanIndex < resultBeans.length; ++beanIndex) {
         RDBMSBean rdbmsBean = resultBeans[beanIndex];
         if (rdbmsBean != null) {
            CMPBeanDescriptor cmpBean = rdbmsBean.getCMPBeanDescriptor();
            Set keyFields = new HashSet(cmpBean.getPrimaryKeyFieldNames());
            Map table2columns = (Map)whatsMapped.get(beanIndex);
            Iterator tables = table2columns.entrySet().iterator();

            while(tables.hasNext()) {
               Map.Entry entry = (Map.Entry)tables.next();
               String table = (String)entry.getKey();
               Iterator columns = ((List)entry.getValue()).iterator();

               while(columns.hasNext()) {
                  String column = (String)columns.next();
                  String keyField = rdbmsBean.getCmpField(table, column);
                  keyFields.remove(keyField);
               }
            }

            if (!keyFields.isEmpty()) {
               String missingFields = "";
               Iterator iterator = keyFields.iterator();

               while(iterator.hasNext()) {
                  missingFields = missingFields + iterator.next();
                  if (iterator.hasNext()) {
                     missingFields = missingFields + ", ";
                  }
               }

               Loggable log = EJBLogger.logNotSelectForAllPrimaryKeyLoggable(this.getName(), this.query, rdbmsBean.getEjbName(), missingFields);
               throw new EJBException(log.getMessage());
            }
         }
      }

   }

   private String getTable(RDBMSBean bean, String table, Map bean2tableName2tableName) {
      Map tableName2tableName = (Map)bean2tableName2tableName.get(bean.getEjbName());
      if (tableName2tableName == null) {
         tableName2tableName = new TreeMap(String.CASE_INSENSITIVE_ORDER);
         Iterator tables = bean.getTables().iterator();

         while(tables.hasNext()) {
            String tableName = (String)tables.next();
            ((Map)tableName2tableName).put(tableName, tableName);
         }

         bean2tableName2tableName.put(bean.getEjbName(), tableName2tableName);
      }

      return (String)((Map)tableName2tableName).get(table);
   }

   private String getColumn(RDBMSBean bean, String table, String column, Map bean2tableName2columnName2columnName) {
      Map tableName2columnName2columnName = (Map)bean2tableName2columnName2columnName.get(bean.getEjbName());
      if (tableName2columnName2columnName == null) {
         tableName2columnName2columnName = new TreeMap(String.CASE_INSENSITIVE_ORDER);
         bean.computeAllTableColumns((Map)tableName2columnName2columnName);
         bean2tableName2columnName2columnName.put(bean.getEjbName(), tableName2columnName2columnName);
      }

      Map columnName2columnName = (Map)((Map)tableName2columnName2columnName).get(table);
      return columnName2columnName == null ? null : (String)columnName2columnName.get(column);
   }

   private boolean mapTableAndColumn(RDBMSBean bean, String table, String column, List roles, int columnIndex, List managers, List beans, List whatsMapped, List primaryKeyFields, List beanRoles, int beanIndex, Map bean2tableNames, Map bean2tableName2columnNames) {
      if (debugLogger.isDebugEnabled()) {
         debug("checking bean- " + bean.getEjbName());
      }

      boolean found = false;
      String temp = this.getTable(bean, table, bean2tableNames);
      if (temp != null) {
         table = temp;
         temp = this.getColumn(bean, temp, column, bean2tableName2columnNames);
         if (temp != null) {
            column = temp;
            if (!this.alreadyMapped(whatsMapped, beanIndex, table, temp)) {
               found = true;
            }
         }
      }

      if (found) {
         if (debugLogger.isDebugEnabled()) {
            debug("bean as field for column-" + column);
         }

         CMPBeanDescriptor descriptor = bean.getCMPBeanDescriptor();
         BaseEntityManager manager = bean.getRDBMSPersistenceManager().getBeanManager();
         if (beanIndex == -1) {
            if (debugLogger.isDebugEnabled()) {
               debug("beginning bean: EJB-" + bean.getEjbName());
            }

            this.columnIndices[columnIndex] = whatsMapped.size();
            managers.add(manager);
            beans.add(bean);
            whatsMapped.add(new HashMap());
            primaryKeyFields.add(new HashSet());
            beanRoles.add(new HashSet());
         } else {
            if (debugLogger.isDebugEnabled()) {
               debug("found column for bean- " + bean.getEjbName());
            }

            this.columnIndices[columnIndex] = beanIndex;
         }

         this.updateMapped(whatsMapped, this.columnIndices[columnIndex], table, column);
         ClientDrivenBeanInfo info = (ClientDrivenBeanInfo)manager.getBeanInfo();
         Class beanClass = info.getGeneratedBeanClass();
         boolean hasOptimisticField = bean.isOptimistic() && (bean.hasOptimisticColumn(table) && column.equalsIgnoreCase(bean.getOptimisticColumn(table)) || !bean.hasOptimisticColumn(table) && (!bean.hasCmpField(table, column) || !bean.isPrimaryKeyField(bean.getCmpField(table, column))));
         String variable = null;
         String fieldName;
         Class pkClass;
         if (!descriptor.isBeanClassAbstract() && bean.hasCmpField(table, column)) {
            fieldName = bean.getCmpField(table, column);
            pkClass = descriptor.getFieldClass(fieldName);
            String setterName = "__WL_super_" + MethodUtils.setMethodName(fieldName);
            variable = fieldName;

            try {
               this.columnMethods[columnIndex] = beanClass.getMethod(setterName, pkClass);
            } catch (NoSuchMethodException var28) {
               throw new AssertionError("illegal setter method: name-" + setterName + " argument type-" + pkClass.getName());
            }

            this.columnClasses[columnIndex] = pkClass;
         } else {
            variable = bean.getVariable(table, column);
            if (debugLogger.isDebugEnabled()) {
               debug("variable-" + variable);
            }

            try {
               this.columnFields[columnIndex] = beanClass.getField(variable);
            } catch (NoSuchFieldException var29) {
               throw new AssertionError("illegal field value- " + variable);
            }

            this.columnClasses[columnIndex] = this.columnFields[columnIndex].getType();
         }

         if (hasOptimisticField) {
            fieldName = CodeGenUtils.snapshotNameForVar(variable);
            if (debugLogger.isDebugEnabled()) {
               debug("-------------------------optimistic Field" + fieldName);
            }

            try {
               this.columnOptimisticFields[columnIndex] = beanClass.getField(fieldName);
            } catch (NoSuchFieldException var27) {
               throw new AssertionError("illegal optimistic field value- " + fieldName);
            }
         }

         if (bean.hasCmpField(variable)) {
            if (bean.hasCmpColumnType(variable)) {
               this.columnTypes[columnIndex] = bean.getCmpColumnTypeForField(variable);
            }

            if (bean.isPrimaryKeyField(variable)) {
               Set pkFields = (Set)primaryKeyFields.get(this.columnIndices[columnIndex]);
               if (!pkFields.contains(variable)) {
                  this.columnSetsPrimaryKey[columnIndex] = true;
                  if (descriptor.hasComplexPrimaryKey()) {
                     pkClass = descriptor.getPrimaryKeyClass();

                     try {
                        this.columnPrimaryKeyFields[columnIndex] = pkClass.getField(variable);
                     } catch (NoSuchFieldException var26) {
                        throw new AssertionError("illegal primary key field value- " + variable);
                     }
                  }
               } else {
                  this.columnSetsPrimaryKey[columnIndex] = false;
               }
            }
         }

         if (roles != null) {
            ((List)beanRoles.get(this.columnIndices[columnIndex])).addAll(roles);
         }

         fieldName = bean.getField(variable);
         this.columnIsLoadedIndices[columnIndex] = bean.getIsModifiedIndex(fieldName);
      }

      return found;
   }

   public String getQuery(int selectForUpdate) {
      String runtimeQuery = null;
      switch (selectForUpdate) {
         case 0:
            runtimeQuery = this.query;
            break;
         case 1:
            runtimeQuery = this.query;
            break;
         case 2:
            runtimeQuery = this.query;
            break;
         default:
            throw new AssertionError("Unknown selectForUpdate type: '" + selectForUpdate + "'");
      }

      return runtimeQuery;
   }

   public Object[] getBeans() throws InternalException {
      Object[] result = new Object[this.resultManagers.length];

      for(int i = 0; i < this.resultManagers.length; ++i) {
         if (this.resultManagers[i] != null) {
            result[i] = this.resultManagers[i].getBeanFromPool();
            ((CMPBean)result[i]).__WL_initialize();
         }
      }

      return result;
   }

   public void releaseBeans(Object[] beans, int startIndex) {
      for(int i = startIndex; i < this.resultManagers.length; ++i) {
         if (this.resultManagers[i] != null) {
            this.resultManagers[i].releaseBeanToPool((EntityBean)beans[i]);
         }
      }

   }

   public Object[] getPrimaryKey() throws InstantiationException, IllegalAccessException {
      Object[] result = new Object[this.resultManagers.length];

      for(int i = 0; i < this.resultManagers.length; ++i) {
         if (this.resultBeans[i] != null && this.resultBeans[i].getCMPBeanDescriptor().hasComplexPrimaryKey()) {
            result[i] = this.resultBeans[i].getCMPBeanDescriptor().getPrimaryKeyClass().newInstance();
         }
      }

      return result;
   }

   public Method getMethod() {
      return this.method;
   }

   public Method getSecondMethod() {
      return this.secondMethod;
   }

   public Field getField(int column) {
      return this.columnFields[column];
   }

   public boolean hasField(int column) {
      return this.columnFields[column] != null;
   }

   public Method getMethod(int column) {
      return this.columnMethods[column];
   }

   public int getNumQueryParams() {
      return this.query2method.length;
   }

   public int getMethodIndex(int queryIndex) {
      assert queryIndex < this.query2method.length;

      return this.query2method[queryIndex];
   }

   public int getColumnCount() {
      return this.columnCount;
   }

   public Class getColumnClass(int column) {
      return this.columnClasses[column];
   }

   public int getResultIndex(int column) {
      return this.columnIndices[column];
   }

   public boolean setsPrimaryKey(int column) {
      return this.columnSetsPrimaryKey[column];
   }

   public Field getPrimaryKeyField(int column) {
      return this.columnPrimaryKeyFields[column];
   }

   public int getIsLoadedIndex(int column) {
      return this.columnIsLoadedIndices[column];
   }

   public BaseEntityManager getManager(int resultIndex) {
      return this.resultManagers[resultIndex];
   }

   public RDBMSBean getRDBMSBean(int resultIndex) {
      return this.resultBeans[resultIndex];
   }

   public boolean isOptimistic(int column) {
      return this.columnOptimisticFields[column] != null;
   }

   public Field getOptimisticField(int column) {
      return this.columnOptimisticFields[column];
   }

   public boolean isCharArrayMappedToString(Class targetClass) {
      return this.rdbmsBean.isCharArrayMappedToString(targetClass);
   }

   private static void debug(String s) {
      debugLogger.debug("[SqlFinder] " + s);
   }

   public RDBMSBean getSelectBeanTarget() {
      return this.rdbmsBean;
   }

   public List getExternalMethodParmList() {
      if (this.externalMethodParmList == null) {
         Class[] parameterTypes = this.method.getParameterTypes();
         this.externalMethodParmList = new ArrayList();

         for(int i = 0; i < parameterTypes.length; ++i) {
            this.externalMethodParmList.add(new ParamNode((RDBMSBean)null, "param" + i, 0, parameterTypes[i], (String)null, (String)null, false, false, (Class)null, false, false));
         }
      }

      return this.externalMethodParmList;
   }

   public List getExternalMethodAndInEntityParmList() {
      return this.getExternalMethodParmList();
   }

   public boolean isSingletonSelect() {
      return this.isSelect() && !Collection.class.isAssignableFrom(this.getReturnClassType()) && !ResultSet.class.isAssignableFrom(this.getReturnClassType()) && !EJBObject.class.isAssignableFrom(this.getReturnClassType()) && !EJBLocalObject.class.isAssignableFrom(this.getReturnClassType());
   }

   public boolean maxElementsReached(Collection collectionResult, WLCachedRowSet rowsetResult) {
      if (this.maxElements == 0) {
         return false;
      } else if (collectionResult == null && rowsetResult == null) {
         return false;
      } else if (collectionResult != null && collectionResult.size() < this.maxElements) {
         return false;
      } else {
         return rowsetResult == null || rowsetResult.size() >= this.maxElements;
      }
   }

   public int getResultColumnCount() {
      return this.resultManagers.length;
   }

   public boolean usesStoredProcedure() {
      return this.usesStoredProcedure;
   }

   public boolean usesStoredFunction() {
      return this.usesStoredFunction;
   }

   public boolean usesRelationshipCaching() {
      return this.usesRelationshipCaching;
   }

   public int getRelationCount() {
      return this.relationIndex1.length;
   }

   public String getCmrFieldFinderMethodName1(int index) {
      return this.cmrFieldFinderMethodNames1[index];
   }

   public String getCmrFieldFinderMethodName2(int index) {
      return this.cmrFieldFinderMethodNames2[index];
   }

   public int getCmrFieldFinderReturnType1(int index) {
      return this.cmrFieldFinderReturnTypes1[index];
   }

   public int getCmrFieldFinderReturnType2(int index) {
      return this.cmrFieldFinderReturnTypes2[index];
   }

   public int[] getRelationIndex1() {
      return this.relationIndex1;
   }

   public int[] getRelationIndex2() {
      return this.relationIndex2;
   }

   public Method[] getRelationMethod1() {
      return this.relationMethod1;
   }

   public Method[] getRelationMethod2() {
      return this.relationMethod2;
   }

   public boolean isBlobColumn(int column) {
      return "blob".equalsIgnoreCase(this.columnTypes[column]);
   }

   public boolean isClobColumn(int column) {
      return "clob".equalsIgnoreCase(this.columnTypes[column]);
   }

   public QueryCachingHandler getQueryCachingHandler(Object[] args, TTLManager mgr) {
      if (!this.isQueryCachingEnabled()) {
         return new QueryCachingHandler(this);
      } else {
         return this.isDynamicFinder ? new QueryCachingHandler(this.query, this.getMaxElements(), this, mgr) : new QueryCachingHandler(this.getFinderIndex(), args, this, mgr);
      }
   }

   protected boolean checkIfQueryCachingLegal(RDBMSBean rbean) {
      if (!super.checkIfQueryCachingLegal(rbean)) {
         return false;
      } else {
         SqlShape sqlShape = this.rdbmsBean.getSqlShape(this.sqlShapeName);
         if (sqlShape == null) {
            return true;
         } else {
            String[] relationNames = sqlShape.getEjbRelationNames();
            if (relationNames == null) {
               return true;
            } else {
               String[] var4 = relationNames;
               int var5 = relationNames.length;

               for(int var6 = 0; var6 < var5; ++var6) {
                  String relationName = var4[var6];
                  EjbRelation ejbRelation = this.rdbmsBean.getEjbRelation(relationName);
                  Iterator iterator = ejbRelation.getAllEjbRelationshipRoles().iterator();
                  String cmrField = null;

                  while(iterator.hasNext()) {
                     EjbRelationshipRole role = (EjbRelationshipRole)iterator.next();
                     RoleSource source = role.getRoleSource();
                     String ejbName = source.getEjbName();
                     if (ejbName.equals(this.rdbmsBean.getEjbName())) {
                        cmrField = role.getCmrField().getName();
                        break;
                     }
                  }

                  RDBMSBean relatedRDBMSBean = this.rdbmsBean.getRelatedRDBMSBean(cmrField);
                  if (relatedRDBMSBean == null) {
                     throw new AssertionError("Related RDBMS bean not found for cmr-field " + cmrField + " from " + this.rdbmsBean.getEjbName());
                  }

                  if (!relatedRDBMSBean.isReadOnly()) {
                     Log.getInstance().logWarning(this.fmt.QUERY_CACHING_SQLFINDER_HAS_RW_RELATED_BEAN(rbean.getEjbName(), this.getName(), sqlShape.getSqlShapeName(), ejbRelation.getEjbRelationName()));
                     return false;
                  }
               }

               return true;
            }
         }
      }
   }
}
