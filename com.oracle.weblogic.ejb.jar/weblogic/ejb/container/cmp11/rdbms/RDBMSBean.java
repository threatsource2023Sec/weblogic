package weblogic.ejb.container.cmp11.rdbms;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.cmp11.rdbms.finders.Finder;
import weblogic.ejb.container.cmp11.rdbms.finders.IllegalExpressionException;
import weblogic.ejb.container.persistence.spi.CMPBeanDescriptor;
import weblogic.utils.ErrorCollectionException;

public final class RDBMSBean implements Cloneable {
   private static final DebugLogger debugLogger;
   private String ejbName;
   private String fileName;
   private List cmpFieldNames;
   private List primaryKeyFieldList;
   private List cmpColumnNames;
   private String dataSourceName;
   private String poolName;
   private Integer isolationLevel;
   private String schemaName;
   private String tableName;
   private boolean dbIsShared;
   private List attributeMap;
   private List finderList;
   private boolean useQuotedNames;
   private String createDefaultDBMSTable;
   private String validateDbSchemaWith;
   private boolean useTunedUpdates;
   private int databaseType;
   private CMPBeanDescriptor bd;

   public RDBMSBean() {
      this((String)null, "");
   }

   public RDBMSBean(String schema, String table) {
      this.ejbName = null;
      this.fileName = null;
      this.cmpFieldNames = null;
      this.primaryKeyFieldList = null;
      this.cmpColumnNames = null;
      this.dataSourceName = null;
      this.poolName = null;
      this.isolationLevel = null;
      this.schemaName = null;
      this.tableName = null;
      this.dbIsShared = true;
      this.attributeMap = null;
      this.finderList = null;
      this.useQuotedNames = false;
      this.createDefaultDBMSTable = "false";
      this.validateDbSchemaWith = "";
      this.useTunedUpdates = true;
      this.databaseType = 0;
      this.bd = null;
      this.setSchemaName(schema);
      this.setTableName(table);
      this.attributeMap = new ArrayList();
      this.finderList = new LinkedList();
      this.cmpFieldNames = new ArrayList();
      this.cmpColumnNames = new ArrayList();
   }

   public void setEjbName(String name) {
      this.ejbName = name;
   }

   public String getEjbName() {
      return this.ejbName;
   }

   public void setFileName(String name) {
      this.fileName = name;
   }

   public String getFileName() {
      return this.fileName;
   }

   public void setPoolName(String pool) {
      this.poolName = pool;
   }

   public String getPoolName() {
      return this.poolName;
   }

   public void setDataSourceName(String name) {
      this.dataSourceName = name;
   }

   public String getDataSourceName() {
      return this.dataSourceName;
   }

   public boolean useTunedUpdates() {
      return this.useTunedUpdates;
   }

   public void setEnableTunedUpdates(boolean b) {
      this.useTunedUpdates = b;
   }

   public int getDatabaseType() {
      return this.databaseType;
   }

   public void setDatabaseType(int databaseType) {
      this.databaseType = databaseType;
   }

   public void setTransactionIsolation(Integer isolationLevel) {
      this.isolationLevel = isolationLevel;
   }

   public Integer getTransactionIsolation() {
      return this.isolationLevel;
   }

   public void setSchemaName(String schema) {
      this.schemaName = schema;
   }

   public String getSchemaName() {
      return this.schemaName;
   }

   public void setTableName(String table) {
      this.tableName = table;
   }

   public String getTableName() {
      return this.tableName;
   }

   public String getQualifiedTableName() {
      return this.getSchemaName() != null && !this.getSchemaName().equals("") ? this.getSchemaName() + "." + this.getTableName() : this.getTableName();
   }

   public void setPrimaryKeyFields(List primaryKeyFields) {
      this.primaryKeyFieldList = primaryKeyFields;
   }

   public List getPrimaryKeyFields() {
      return this.primaryKeyFieldList;
   }

   public void setUseQuotedNames(boolean useQuotedNames) {
      this.useQuotedNames = useQuotedNames;
   }

   public boolean getUseQuotedNames() {
      return this.useQuotedNames;
   }

   public void addObjectLink(ObjectLink link) {
      this.attributeMap.add(link);
      String beanField = link.getBeanField();
      String dbmsColumn = link.getDBMSColumn();
      this.cmpFieldNames.add(beanField);
      this.cmpColumnNames.add(dbmsColumn);
   }

   public void addObjectLink(String beanField, String dbmsColumn) {
      ObjectLink link = new ObjectLink(beanField, dbmsColumn);
      this.addObjectLink(link);
   }

   public Iterator getObjectLinks() {
      return this.attributeMap.iterator();
   }

   public List getCmpFieldNames() {
      return this.cmpFieldNames;
   }

   public List getCmpColumnNames() {
      return this.cmpColumnNames;
   }

   public String getColumnForField(String fieldName) {
      Iterator objectLinks = this.getObjectLinks();

      ObjectLink link;
      do {
         if (!objectLinks.hasNext()) {
            return null;
         }

         link = (ObjectLink)objectLinks.next();
      } while(!link.getBeanField().equals(fieldName));

      return link.getDBMSColumn();
   }

   public String getFieldForColumn(String columnName) {
      Iterator objectLinks = this.getObjectLinks();

      ObjectLink link;
      do {
         if (!objectLinks.hasNext()) {
            return null;
         }

         link = (ObjectLink)objectLinks.next();
      } while(!link.getDBMSColumn().equals(columnName));

      return link.getBeanField();
   }

   public Map getFieldToColumnMap() {
      Map map = new HashMap();
      Iterator objectLinks = this.getObjectLinks();

      while(objectLinks.hasNext()) {
         ObjectLink link = (ObjectLink)objectLinks.next();
         map.put(link.getBeanField(), link.getDBMSColumn());
      }

      return map;
   }

   public Map getColumnToFieldMap() {
      Map map = new HashMap();
      Iterator objectLinks = this.getObjectLinks();

      while(objectLinks.hasNext()) {
         ObjectLink link = (ObjectLink)objectLinks.next();
         map.put(link.getDBMSColumn(), link.getBeanField());
      }

      return map;
   }

   public List getFieldNamesList() {
      List list = new LinkedList();
      Iterator objectLinks = this.getObjectLinks();

      while(objectLinks.hasNext()) {
         ObjectLink link = (ObjectLink)objectLinks.next();
         list.add(link.getBeanField());
      }

      return list;
   }

   public Iterator getFieldNames() {
      List list = this.getFieldNamesList();
      return list.iterator();
   }

   public String getCreateDefaultDBMSTables() {
      return this.createDefaultDBMSTable;
   }

   public void setCreateDefaultDBMSTables(String b) {
      this.createDefaultDBMSTable = b;
   }

   public String getValidateDbSchemaWith() {
      return this.validateDbSchemaWith;
   }

   public void setValidateDbSchemaWith(String string) {
      this.validateDbSchemaWith = string;
   }

   public void addFinder(Finder finder) {
      this.finderList.add(finder);
   }

   public void replaceFinder(Finder old, Finder gnu) throws IllegalArgumentException {
      int idx = this.finderList.indexOf(old);
      if (idx == -1) {
         throw new IllegalArgumentException();
      } else {
         this.finderList.set(idx, gnu);
      }
   }

   public Iterator getFinders() {
      return this.finderList.iterator();
   }

   public List getFinderList() {
      return this.finderList;
   }

   public void generateFinderSQLStatements() throws ErrorCollectionException {
      ErrorCollectionException methodECE = new ErrorCollectionException();
      Iterator finders = this.getFinders();

      while(finders.hasNext()) {
         Finder finder = (Finder)finders.next();

         try {
            finder.computeSQLQuery(this.getMapTable());
            if (debugLogger.isDebugEnabled()) {
               String s = finder.getSQLQuery();
               if (s == null) {
                  debug("finder.computSQLQuery: None generated.  NULL !");
               } else {
                  debug("finder.computSQLQuery: " + s);
               }
            }
         } catch (IllegalExpressionException var5) {
            methodECE.add(var5);
         }
      }

      if (methodECE.getExceptions().size() > 0) {
         throw methodECE;
      }
   }

   public Finder getFinderForMethod(Method method) {
      if (debugLogger.isDebugEnabled()) {
         debug("RDBMSBean.getFinderForMethod(" + method + ")");
      }

      Iterator finders = this.getFinders();

      Finder finder;
      do {
         if (!finders.hasNext()) {
            return null;
         }

         finder = (Finder)finders.next();
      } while(!finder.methodIsEquivalent(method));

      return finder;
   }

   public boolean equals(Object other) {
      if (!(other instanceof RDBMSBean)) {
         return false;
      } else {
         RDBMSBean otherBean = (RDBMSBean)other;
         return this.ejbName.equals(otherBean.getEjbName());
      }
   }

   public String toString() {
      StringBuilder buf = new StringBuilder(150);
      buf.append("[weblogic.cmp.rdbms.RDBMSBean {");
      buf.append("\n\tejbName = " + this.ejbName);
      buf.append("\n\tpoolName = " + this.poolName);
      buf.append("\n\tschemaName = " + this.schemaName);
      buf.append("\n\ttableName = " + this.tableName);
      buf.append("\n\tisolationLevel = " + RDBMSUtils.isolationLevelToString(this.isolationLevel));
      buf.append("\n\tattributeMap = " + this.attributeMap);
      buf.append("\n\tfinderList = " + this.finderList);
      buf.append("\n\tuseQuotedNames = " + this.useQuotedNames);
      buf.append("\n\tdbIsShared = " + this.dbIsShared);
      buf.append("\n} end RDBMSBean ]\n");
      return buf.toString();
   }

   public int hashCode() {
      return this.ejbName.hashCode();
   }

   private Hashtable getMapTable() {
      Hashtable mapTable = new Hashtable();
      Iterator links = this.getObjectLinks();

      while(links.hasNext()) {
         ObjectLink link = (ObjectLink)links.next();
         mapTable.put(link.getBeanField(), link.getDBMSColumn());
      }

      return mapTable;
   }

   public void setCMPBeanDescriptor(CMPBeanDescriptor bd) {
      this.bd = bd;
   }

   public Class getCmpFieldClass(String dbColumnName) {
      Iterator links = this.getObjectLinks();

      ObjectLink link;
      do {
         if (!links.hasNext()) {
            return null;
         }

         link = (ObjectLink)links.next();
      } while(!dbColumnName.equalsIgnoreCase(link.getDBMSColumn()));

      return this.bd.getFieldClass(link.getBeanField());
   }

   private static void debug(String s) {
      debugLogger.debug("[RDBMSBean] " + s);
   }

   static {
      debugLogger = EJBDebugService.cmpDeploymentLogger;
   }

   public static class ObjectLink {
      public static final boolean verbose = false;
      public static final boolean debug = false;
      private String beanField = null;
      private String dbmsColumn = null;

      public ObjectLink(String beanField, String dbmsColumn) {
         this.setBeanField(beanField);
         this.setDBMSColumn(dbmsColumn);
      }

      private void setBeanField(String fieldName) {
         this.beanField = fieldName;
      }

      public String getBeanField() {
         return this.beanField;
      }

      public void setDBMSColumn(String colName) {
         this.dbmsColumn = colName;
      }

      public String getDBMSColumn() {
         return this.dbmsColumn;
      }

      public boolean equals(Object other) {
         if (!(other instanceof ObjectLink)) {
            return false;
         } else {
            ObjectLink otherLink = (ObjectLink)other;
            if (!this.beanField.equals(otherLink.getBeanField())) {
               return false;
            } else {
               return this.dbmsColumn.equals(otherLink.getDBMSColumn());
            }
         }
      }

      public int hashCode() {
         return this.beanField.hashCode() | this.dbmsColumn.hashCode();
      }

      public String toString() {
         return "[ObjectLink: field<" + this.beanField + "> to column <" + this.dbmsColumn + ">]";
      }
   }
}
