package weblogic.ejb.container.cmp.rdbms;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.cmp.rdbms.finders.EjbqlFinder;
import weblogic.ejb.container.cmp.rdbms.finders.Finder;
import weblogic.ejb.container.cmp.rdbms.finders.RDBMSFinder;
import weblogic.ejb.container.cmp.rdbms.finders.SqlFinder;
import weblogic.ejb.container.compliance.EJBComplianceTextFormatter;
import weblogic.ejb.container.compliance.Log;
import weblogic.ejb.container.persistence.spi.CMPBeanDescriptor;
import weblogic.ejb.container.persistence.spi.CmrField;
import weblogic.ejb.container.persistence.spi.EjbEntityRef;
import weblogic.ejb.container.persistence.spi.EjbRelation;
import weblogic.ejb.container.persistence.spi.EjbRelationshipRole;
import weblogic.ejb.container.persistence.spi.Relationships;
import weblogic.ejb.container.persistence.spi.RoleSource;
import weblogic.ejb.container.utils.ClassUtils;
import weblogic.ejb.container.utils.MethodUtils;
import weblogic.ejb20.cmp.rdbms.RDBMSException;
import weblogic.ejb20.cmp.rdbms.finders.EJBQLCompilerException;
import weblogic.ejb20.cmp.rdbms.finders.InvalidFinderException;
import weblogic.logging.Loggable;
import weblogic.utils.Debug;
import weblogic.utils.ErrorCollectionException;
import weblogic.utils.StackTraceUtilsClient;

public final class RDBMSBean implements Cloneable {
   private static final DebugLogger debugLogger;
   private String dataSourceName;
   private List cmpFieldNames = new ArrayList();
   private Map cmpFieldClasses = new HashMap();
   private Map tableNames = new HashMap();
   private List tableNamesList = new ArrayList();
   private Map tableName2cmpFieldName2columnName = new HashMap();
   private Map tableName2verifyRows = new HashMap();
   private Map tableName2verifyColumns = new HashMap();
   private Map tableName2optimisticColumn = new HashMap();
   private Map tableName2optimisticColumnTrigger = new HashMap();
   private Map tableName2versionColumnInitialValue = new HashMap();
   private boolean verifyReads = false;
   private List cmpColumnNames = new ArrayList();
   private Map fieldName2columnName = new HashMap();
   private Map columnName2fieldName = new HashMap();
   private Map fieldName2columnTypeName = new HashMap();
   private Map cmpFieldName2groupName = new HashMap();
   private Set dbmsDefaultValueFields;
   private List fieldGroups = new ArrayList();
   private List relationshipCachings = new ArrayList();
   private Map rdbmsFinders = new HashMap();
   private String delayInsertUntil = "ejbPostCreate";
   private boolean useSelectForUpdate = false;
   private int lockOrder = 0;
   private String instanceLockOrder = "AccessOrder";
   private short genKeyType = -1;
   private String genKeyGeneratorName;
   private int genKeyCacheSize = 0;
   private boolean selectFirstSeqKeyBeforeUpdate = false;
   protected boolean orderDatabaseOperations = true;
   protected boolean enableBatchOperations = true;
   private String createDefaultDBMSTables = "Disabled";
   private String ddlFileName;
   private int databaseType = 0;
   private boolean checkExistsOnMethod = true;
   private boolean hasBlobColumn = false;
   private boolean hasClobColumn = false;
   private boolean hasNClobColumn = false;
   private boolean hasBlobClobColumn = false;
   private boolean byteArrayIsSerializedToOracleBlob = false;
   private boolean loadRelatedBeansFromDbInPostCreate = false;
   private boolean allowReadonlyCreateAndRemove = false;
   private boolean charArrayIsSerializedToBytes = false;
   private boolean disableStringTrimming = false;
   private boolean findersReturnNulls = true;
   private String validateDbSchemaWith = "";
   private Map sqlShapes;
   private CMPBeanDescriptor bd;
   private String ejbName;
   private List primaryKeyFieldList;
   private List finderList = new LinkedList();
   private boolean hasResultSetFinder = false;
   private Map beanMap;
   private Relationships relationships;
   private Map rdbmsBeanMap;
   private Map rdbmsRelationMap;
   private boolean normalizeMultiTables_done = false;
   private Map table2cmpf2columnPKsOnly = new HashMap();
   private Map table2cmpf2columnNoPKs = new HashMap();
   private Map table2column2cmpf = new HashMap();
   private Map table2column2variable = new HashMap();
   private Map cmpf2Table = new HashMap();
   private Map pkCmpF2Table2Column = new HashMap();
   private Map column2tables = new HashMap();
   private Map variableName2table = new HashMap();
   private Map variableName2cmrField = new HashMap();
   private int numFields = 0;
   private Map fieldNameToIsModifiedIndex = new HashMap();
   private Map isModifiedIndexToFieldName = new HashMap();
   private List isModifiedPKIndexList = new ArrayList();
   private List isModifiedIndexToTableNumber = new ArrayList();
   private List[] tableIndexToFieldList;
   private List[] tableIndexToNonPKFieldList;
   private List[] tableIndexToCMPFieldList;
   private List[] tableIndexToCMRFieldList;
   private boolean synthesized = false;
   private Map fieldName2class = new HashMap();
   private List cmrFieldNames = new ArrayList();
   private List fkFieldNames = new ArrayList();
   private List fkPkFieldNames = new ArrayList();
   private List fkCmpFieldNames = new ArrayList();
   private List declaredFieldNames = new ArrayList();
   private List remoteFieldNames = new ArrayList();
   private Set one2OneSet = new HashSet();
   private Set one2ManySet = new HashSet();
   private Set many2ManySet = new HashSet();
   private Set biDirectional = new HashSet();
   private Map fieldName2cascadeDelete = new HashMap();
   private Map fieldName2DBCascadeDelete = new HashMap();
   private boolean selfRelationship = false;
   private Map fieldName2selfRel = new HashMap();
   private Map fkField2fkColumns = new HashMap();
   private Map fkField2fkColumn2Class = new HashMap();
   private Map fkField2fkColumn2FieldName = new HashMap();
   private Map fieldName2relatedDescriptor = new HashMap();
   private Map fieldName2relatedMultiplicity = new HashMap();
   private Map fieldName2relatedFieldOwnsFk = new HashMap();
   private Map fieldName2relatedClassName = new HashMap();
   private Map fieldName2relatedRDBMSBean = new HashMap();
   private Map fieldName2RelatedFieldName = new HashMap();
   private Map fieldName2groupName = new HashMap();
   private Map variableName2columnName = new HashMap();
   private Map fieldName2tableName = new HashMap();
   private Map groupName2tableNames = new HashMap();
   private Map fieldName2entityRef = new HashMap();
   private Map fieldName2remoteColumn = new HashMap();
   private Map fkField2symColumn2FieldName = new HashMap();
   private Map fkField2symColumns = new HashMap();
   private Set cmrMappedcmpFields = new HashSet();
   private Map cmrMapeedRelationFinder = new HashMap();
   private Map table2cmrf = new HashMap();
   private Map cmrf2table = new HashMap();
   private List cmrfHasMultiPkTable = new ArrayList();
   private Map cmrf2pkTable2fkColumn2pkColumn = new HashMap();
   private Map fkField2pkTable2symFkColumn2pkColumn = new HashMap();
   private Map table2fkCol2fkClass = new HashMap();
   private Map table2fkCol2RelatedBean = new HashMap();
   private List ejbSelectInternalList;
   private String synthAbstractSchemaName;
   private boolean genKeyBeforeInsert = true;
   private boolean genKeyExcludePKColumn = false;
   private String genKeyDefaultColumnVal;
   private String genKeyWLGeneratorQuery = "";
   private String genKeyWLGeneratorUpdatePrefix = "";
   private String genKeyPKField;
   private short genKeyPKFieldClassType;
   private Class generatedBeanInterface;
   private RDBMSPersistenceManager pm;
   private Set qcEnabledCmrFields;
   private final EJBComplianceTextFormatter fmt = EJBComplianceTextFormatter.getInstance();
   private boolean hasSqlFinder = false;
   private boolean clusterInvalidationDisabled = false;
   private boolean useInnerJoin = false;
   private String categoryCmpField;
   private List relFinders = null;

   public void setRDBMSPersistenceManager(RDBMSPersistenceManager pm) {
      this.pm = pm;
   }

   public RDBMSPersistenceManager getRDBMSPersistenceManager() {
      return this.pm;
   }

   public String getAbstractSchemaName() {
      if (this.bd.getAbstractSchemaName() != null) {
         return this.bd.getAbstractSchemaName();
      } else {
         if (this.synthAbstractSchemaName == null) {
            this.synthAbstractSchemaName = this.genSynthAbstractSchemaName();
         }

         return this.synthAbstractSchemaName;
      }
   }

   private String genSynthAbstractSchemaName() {
      return "_WL_abstractSchemaName_" + this.getEjbName().replace('.', '_');
   }

   public Map getBeanMap() {
      return this.beanMap;
   }

   public CMPBeanDescriptor getCMPBeanDescriptor() {
      assert this.bd != null;

      return this.bd;
   }

   public void setEjbName(String name) {
      this.ejbName = name;
   }

   public String getEjbName() {
      return this.ejbName;
   }

   public void setDataSourceName(String name) {
      this.dataSourceName = name;
   }

   public String getDataSourceName() {
      return this.dataSourceName;
   }

   public void addToEjbSelectInternalList(Finder f) {
      if (this.ejbSelectInternalList == null) {
         this.ejbSelectInternalList = new ArrayList();
      }

      this.ejbSelectInternalList.add(f);
   }

   public List getEjbSelectInternalList() {
      if (this.ejbSelectInternalList == null) {
         this.ejbSelectInternalList = new ArrayList();
      }

      return this.ejbSelectInternalList;
   }

   public boolean hasAutoKeyGeneration() {
      return this.genKeyType != -1;
   }

   public boolean getGenKeyBeforeInsert() {
      return this.genKeyBeforeInsert;
   }

   public String getGenKeyDefaultColumnVal() {
      return this.genKeyDefaultColumnVal;
   }

   public boolean genKeyExcludePKColumn() {
      return this.genKeyExcludePKColumn;
   }

   public void setGenKeyCacheSize(int i) {
      this.genKeyCacheSize = i;
   }

   public int getGenKeyCacheSize() {
      return this.genKeyCacheSize;
   }

   public void setGenKeyGeneratorName(String s) {
      this.genKeyGeneratorName = s;
   }

   public String getGenKeyGeneratorName() {
      return this.genKeyGeneratorName;
   }

   public String getGenKeyPKField() {
      return this.genKeyPKField;
   }

   public String getGenKeyPKClassName() {
      return this.getCmpFieldClass(this.genKeyPKField).getName();
   }

   public short getGenKeyPKFieldClassType() {
      return this.genKeyPKFieldClassType;
   }

   public void setGenKeyType(String s) {
      this.genKeyType = RDBMSUtils.getGenKeyTypeAsConstant(s);
   }

   public short getGenKeyType() {
      return this.genKeyType;
   }

   public String getGenKeyGeneratorQuery() {
      return this.genKeyWLGeneratorQuery;
   }

   public String getGenKeyGeneratorUpdatePrefix() {
      return this.genKeyWLGeneratorUpdatePrefix;
   }

   public boolean getSelectFirstSeqKeyBeforeUpdate() {
      return this.selectFirstSeqKeyBeforeUpdate;
   }

   public void setSelectFirstSeqKeyBeforeUpdate(boolean b) {
      this.selectFirstSeqKeyBeforeUpdate = b;
   }

   public boolean hasMultipleTables() {
      return this.tableNamesList.size() > 1;
   }

   public int tableCount() {
      return this.tableNamesList.size();
   }

   public void addTable(String tableName) {
      if (debugLogger.isDebugEnabled()) {
         debug("adding table name: '" + tableName + "'");
      }

      if (this.hasTable(tableName)) {
         throw new AssertionError("Duplicate tablename was detected: tableName-" + tableName + " ejbName-" + this.getEjbName());
      } else {
         this.tableNames.put(tableName, tableName);
         this.tableNamesList.add(tableName);
      }
   }

   public boolean hasTable(String tableName) {
      return this.tableNames.containsKey(tableName);
   }

   public List getTables() {
      return this.tableNamesList;
   }

   public String tableAt(int pos) {
      return pos >= this.tableNamesList.size() ? null : (String)this.tableNamesList.get(pos);
   }

   public int tableIndex(String tableName) {
      return this.tableNamesList.indexOf(tableName);
   }

   public String chooseTableAsJoinTarget() {
      String theTable = this.getTableName();
      List tList = (List)this.table2cmrf.get(theTable);
      int cmrfCount = 0;
      if (tList != null) {
         cmrfCount = tList.size();
      }

      Iterator var4 = this.getTables().iterator();

      while(var4.hasNext()) {
         String table = (String)var4.next();
         tList = (List)this.table2cmrf.get(table);
         int cmrfs = 0;
         if (tList != null) {
            cmrfs = tList.size();
         }

         if (cmrfs > cmrfCount) {
            theTable = table;
         }
      }

      return theTable;
   }

   public List getTableNamesForColumn(String column) {
      return (List)this.column2tables.get(column);
   }

   public Map getPKCmpf2ColumnForTable(String tableName) {
      return (Map)this.table2cmpf2columnPKsOnly.get(tableName);
   }

   public String getPKColumnName(String tableName, String pkField) {
      return (String)((Map)this.table2cmpf2columnPKsOnly.get(tableName)).get(pkField);
   }

   public boolean cmrfIsMultiPKTable(String cmrf) {
      return this.cmrfHasMultiPkTable.contains(cmrf);
   }

   public String getTableForCmrField(String cmrf) {
      return (String)this.cmrf2table.get(cmrf);
   }

   public int getTableIndexForCmrf(String cmrf) {
      String tableName = this.getTableForCmrField(cmrf);
      return tableName == null ? -1 : this.tableIndex(tableName);
   }

   public List getCmrFields(String tableName) {
      return (List)this.table2cmrf.get(tableName);
   }

   public String getField(String variable) {
      return this.fieldName2columnName.get(variable) != null ? variable : (String)this.variableName2cmrField.get(variable);
   }

   public Map getColumnMapForCmrfAndPkTable(String cmrf, String PKTableName) {
      Map pkTable2ColumnMap = (Map)this.cmrf2pkTable2fkColumn2pkColumn.get(cmrf);
      return pkTable2ColumnMap == null ? null : (Map)pkTable2ColumnMap.get(PKTableName);
   }

   public Map getSymColumnMapForCmrfAndPkTable(String cmrf, String pkTableName) {
      Map table2symFkColumn2pkColumn = (Map)this.fkField2pkTable2symFkColumn2pkColumn.get(cmrf);
      return table2symFkColumn2pkColumn == null ? null : (Map)table2symFkColumn2pkColumn.get(pkTableName);
   }

   public void setPrimaryKeyFields(List primaryKeyFields) {
      this.primaryKeyFieldList = primaryKeyFields;
   }

   public List getPrimaryKeyFields() {
      return this.primaryKeyFieldList;
   }

   public boolean isPrimaryKeyField(String field) {
      return this.primaryKeyFieldList.contains(field);
   }

   public String getTableName() {
      return this.tableAt(0);
   }

   public String getQuotedTableName() {
      return RDBMSUtils.escQuotedID(this.getTableName());
   }

   public void addFieldGroup(FieldGroup fg) {
      this.fieldGroups.add(fg);
   }

   public List getFieldGroups() {
      return this.fieldGroups;
   }

   public FieldGroup getFieldGroup(String name) {
      Iterator var2 = this.getFieldGroups().iterator();

      FieldGroup fg;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         fg = (FieldGroup)var2.next();
      } while(!fg.getName().equals(name));

      return fg;
   }

   public void setupFieldGroupIndexes() {
      int index = 0;
      Iterator var2 = this.getFieldGroups().iterator();

      while(var2.hasNext()) {
         FieldGroup curGroup = (FieldGroup)var2.next();
         curGroup.setIndex(index++);
      }

   }

   public void addCmpFieldGroupNameMapping(String cmpFieldName, String groupName) {
      this.cmpFieldName2groupName.put(cmpFieldName, groupName);
   }

   public String getGroupNameForCmpField(String cmpFieldName) {
      String groupName = (String)this.cmpFieldName2groupName.get(cmpFieldName);
      if (groupName != null) {
         return groupName;
      } else {
         Iterator fgroups = this.getFieldGroups().iterator();

         FieldGroup fgroup;
         boolean found;
         do {
            if (!fgroups.hasNext()) {
               throw new AssertionError("RDBMSBean.getGroupNameForCmpField did not find a group for field '" + cmpFieldName + "'.");
            }

            fgroup = (FieldGroup)fgroups.next();
            found = fgroup.getCmpFields().contains(cmpFieldName);
            found |= fgroup.getCmrFields().contains(cmpFieldName);
         } while(!found);

         groupName = fgroup.getName();
         return groupName;
      }
   }

   public void addRelationshipCaching(RelationshipCaching rc) {
      this.relationshipCachings.add(rc);
   }

   public List getRelationshipCachings() {
      return this.relationshipCachings;
   }

   public RelationshipCaching getRelationshipCaching(String name) {
      Iterator var2 = this.relationshipCachings.iterator();

      RelationshipCaching rc;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         rc = (RelationshipCaching)var2.next();
      } while(!rc.getCachingName().equals(name));

      return rc;
   }

   public void setVerifyRows(String tableName, String verifyRows) {
      this.tableName2verifyRows.put(tableName, verifyRows);
      if ("read".equalsIgnoreCase(verifyRows)) {
         this.verifyReads = true;
      } else {
         this.verifyReads = false;
      }

   }

   public String getVerifyRows(String tableName) {
      return (String)this.tableName2verifyRows.get(tableName);
   }

   public boolean getVerifyReads() {
      return this.verifyReads;
   }

   public void setVerifyColumns(String tableName, String verifyColumns) {
      this.tableName2verifyColumns.put(tableName, verifyColumns);
   }

   public String getVerifyColumns(String tableName) {
      return (String)this.tableName2verifyColumns.get(tableName);
   }

   public RDBMSBean getRDBMSBeanForAbstractSchema(String schema) {
      String schemaName = this.getAbstractSchemaName();
      if (schemaName.equals(schema)) {
         return this;
      } else {
         Iterator var3 = this.beanMap.keySet().iterator();

         RDBMSBean bean;
         do {
            if (!var3.hasNext()) {
               return null;
            }

            String name = (String)var3.next();
            bean = (RDBMSBean)this.rdbmsBeanMap.get(name);
            schemaName = bean.getAbstractSchemaName();
         } while(schemaName == null || !schemaName.equals(schema));

         return bean;
      }
   }

   public boolean containsRdbmsFinder(RDBMSFinder finder) {
      return this.rdbmsFinders.containsKey(new RDBMSFinder.FinderKey(finder));
   }

   public void addRdbmsFinder(RDBMSFinder finder) {
      this.rdbmsFinders.put(new RDBMSFinder.FinderKey(finder), finder);
   }

   public Map getRdbmsFinders() {
      return this.rdbmsFinders;
   }

   public boolean isQueryCachingEnabledForCMRField(String cmrField) {
      CMPBeanDescriptor beanDescriptor = this.getRelatedDescriptor(cmrField);
      if (!beanDescriptor.isReadOnly()) {
         return false;
      } else {
         if (this.qcEnabledCmrFields != null && this.qcEnabledCmrFields.contains(cmrField)) {
            RDBMSBean otherBean = this.getRelatedRDBMSBean(cmrField);
            String otherField = this.getRelatedFieldName(cmrField);
            if (!otherBean.relatedFieldIsFkOwner(otherField) || this.isManyToManyRelation(cmrField)) {
               return true;
            }

            this.qcEnabledCmrFields.remove(cmrField);
         }

         return false;
      }
   }

   private String methodPrefix() {
      return "findBy";
   }

   public String finderMethodName(CMPBeanDescriptor desc, String fieldName) {
      String className = MethodUtils.tail(desc.getGeneratedBeanClassName());
      return this.methodPrefix() + className + "_" + fieldName + "__WL_";
   }

   public String variableForField(String cmrField, String fkTable, String fkColumn) {
      Debug.assertion(cmrField != null);
      Debug.assertion(fkTable != null);
      Debug.assertion(fkColumn != null);
      String val = null;
      if (this.hasCmpField(fkTable, fkColumn)) {
         val = this.getCmpField(fkTable, fkColumn);
      } else {
         val = "__WL_" + cmrField;
         if (!this.isRemoteField(cmrField)) {
            val = val + "_" + this.getRelatedPkFieldName(cmrField, fkColumn);
         }
      }

      return val;
   }

   private void addReverseColumnMapping(String table, String column, String variable) {
      Map column2variable = (Map)this.table2column2variable.get(table);
      if (column2variable == null) {
         column2variable = new HashMap();
         this.table2column2variable.put(table, column2variable);
      }

      ((Map)column2variable).put(column, variable);
   }

   public boolean isReadOnly() {
      return this.bd.isReadOnly();
   }

   public boolean isOptimistic() {
      return this.bd.isOptimistic();
   }

   public boolean getCacheBetweenTransactions() {
      return this.bd.getCacheBetweenTransactions();
   }

   public boolean hasOptimisticColumn(String tableName) {
      if (!"version".equalsIgnoreCase(this.getVerifyColumns(tableName)) && !"timestamp".equalsIgnoreCase(this.getVerifyColumns(tableName))) {
         return false;
      } else {
         assert this.tableName2optimisticColumn.get(tableName) != null;

         return this.tableName2optimisticColumn.get(tableName) != null;
      }
   }

   public String getOptimisticColumn(String tableName) {
      return (String)this.tableName2optimisticColumn.get(tableName);
   }

   public boolean normalizeMultiTables_done() {
      return this.normalizeMultiTables_done;
   }

   private void addOptimisticFields() {
      Iterator cmpFields = this.bd.getCMFieldNames().iterator();

      while(cmpFields.hasNext()) {
         String cmpField = (String)cmpFields.next();
         this.cmpFieldClasses.put(cmpField, this.bd.getFieldClass(cmpField));
      }

      Iterator it = this.tableNamesList.iterator();

      while(it.hasNext()) {
         String table = (String)it.next();
         if (this.hasOptimisticColumn(table)) {
            String optimisticField = this.getCmpField(table, this.getOptimisticColumn(table));
            if (optimisticField == null) {
               optimisticField = "__WL_optimisticField" + this.tableIndex(table);
               this.addTableFieldColumnMapping(table, optimisticField, this.getOptimisticColumn(table));
               if (this.getVerifyColumns(table).equalsIgnoreCase("version")) {
                  this.cmpFieldClasses.put(optimisticField, Long.class);
               } else {
                  if (!this.getVerifyColumns(table).equalsIgnoreCase("timestamp")) {
                     throw new AssertionError("invalid value for verify-columns: " + this.getVerifyColumns(table));
                  }

                  this.cmpFieldClasses.put(optimisticField, Timestamp.class);
               }
            }
         }
      }

   }

   public void normalizeMultiTables(CMPBeanDescriptor cmpBD) {
      if (!this.normalizeMultiTables_done) {
         this.normalizeMultiTables_done = true;
         this.bd = cmpBD;
         this.addOptimisticFields();
         Iterator var2 = this.tableNamesList.iterator();

         while(var2.hasNext()) {
            String table = (String)var2.next();
            Map cmpf2columnPKsOnly = new HashMap();
            Map cmpf2columnNoPKs = new HashMap();
            Map f2c = (Map)this.tableName2cmpFieldName2columnName.get(table);
            Iterator fieldIt = f2c.entrySet().iterator();

            while(fieldIt.hasNext()) {
               Map.Entry entry = (Map.Entry)fieldIt.next();
               String cmpField = (String)entry.getKey();
               String column = (String)entry.getValue();
               if (this.primaryKeyFieldList.contains(cmpField)) {
                  cmpf2columnPKsOnly.put(cmpField, column);
                  Map m = (Map)this.pkCmpF2Table2Column.get(cmpField);
                  if (m == null) {
                     m = new HashMap();
                     this.pkCmpF2Table2Column.put(cmpField, m);
                  }

                  ((Map)m).put(table, column);
               } else {
                  cmpf2columnNoPKs.put(cmpField, column);
               }
            }

            this.table2cmpf2columnPKsOnly.put(table, cmpf2columnPKsOnly);
            this.table2cmpf2columnNoPKs.put(table, cmpf2columnNoPKs);
         }

      }
   }

   public boolean initialized() {
      return this.synthesized;
   }

   public void processDescriptors(Map beanMap, Relationships relationships, Map rdbmsBeanMap, Map rdbmsRelationMap) throws RDBMSException {
      if (debugLogger.isDebugEnabled()) {
         debug("called RDBMSBean.processDescriptors");
      }

      assert beanMap != null;

      assert rdbmsBeanMap != null;

      assert rdbmsRelationMap != null;

      if (!this.synthesized) {
         this.synthesized = true;
         this.beanMap = beanMap;
         this.relationships = relationships;
         this.rdbmsBeanMap = rdbmsBeanMap;
         this.rdbmsRelationMap = rdbmsRelationMap;

         assert this.bd != null;

         Iterator relations;
         String tableName;
         if (relationships != null) {
            if (debugLogger.isDebugEnabled()) {
               debug("processing relationships...");
            }

            relations = relationships.getAllEjbRelations().entrySet().iterator();

            label211:
            while(true) {
               while(true) {
                  if (!relations.hasNext()) {
                     break label211;
                  }

                  Map.Entry entry = (Map.Entry)relations.next();
                  tableName = (String)entry.getKey();
                  if (debugLogger.isDebugEnabled()) {
                     debug("processing relationship: " + tableName);
                  }

                  EjbRelation ejbRel = (EjbRelation)entry.getValue();
                  RDBMSRelation rdbmsRel = (RDBMSRelation)rdbmsRelationMap.get(tableName);

                  assert rdbmsRel != null;

                  Iterator roles = ejbRel.getAllEjbRelationshipRoles().iterator();
                  EjbRelationshipRole role1 = (EjbRelationshipRole)roles.next();
                  EjbRelationshipRole role2 = (EjbRelationshipRole)roles.next();

                  assert role1 != null;

                  assert role2 != null;

                  RDBMSRelation.RDBMSRole r1 = rdbmsRel.getRole1();
                  RDBMSRelation.RDBMSRole r2 = rdbmsRel.getRole2();
                  if (!r1.getName().equals(role1.getName())) {
                     RDBMSRelation.RDBMSRole temp = r1;
                     r1 = r2;
                     r2 = temp;
                  }

                  assert r1 == null || role1.getName().equals(r1.getName());

                  assert r2 == null || role2.getName().equals(r2.getName());

                  if (r1 != null && r1.getColumnMap().size() > 0) {
                     this.processRole(role1, role2, r1, r2, rdbmsRel);
                     this.processRole(role2, role1, r2, r1, rdbmsRel);
                  } else {
                     this.processRole(role2, role1, r2, r1, rdbmsRel);
                     this.processRole(role1, role2, r1, r2, rdbmsRel);
                  }
               }
            }
         }

         this.calculateLoadModifyIndex();
         relations = this.getFieldGroups().iterator();

         Iterator cmrFields;
         String tableName;
         String optimisticField;
         label184:
         while(relations.hasNext()) {
            FieldGroup group = (FieldGroup)relations.next();
            Set tables = new HashSet();
            this.groupName2tableNames.put(group.getName(), tables);
            cmrFields = group.getCmrFields().iterator();

            while(true) {
               do {
                  if (!cmrFields.hasNext()) {
                     Iterator cmpFields = (new TreeSet(group.getCmpFields())).iterator();

                     String table;
                     String optimisticField;
                     while(cmpFields.hasNext()) {
                        optimisticField = (String)cmpFields.next();
                        table = this.getTableForCmpField(optimisticField);
                        tables.add(table);
                        if (this.hasOptimisticColumn(table)) {
                           optimisticField = this.getCmpField(table, this.getOptimisticColumn(table));
                           if (!group.getCmpFields().contains(optimisticField)) {
                              group.addCmpField(optimisticField);
                           }
                        }
                     }

                     cmrFields = (new TreeSet(group.getCmrFields())).iterator();

                     while(cmrFields.hasNext()) {
                        optimisticField = (String)cmrFields.next();
                        table = this.getTableForCmrField(optimisticField);
                        tables.add(table);
                        if (this.hasOptimisticColumn(table)) {
                           optimisticField = this.getCmpField(table, this.getOptimisticColumn(table));
                           if (!group.getCmpFields().contains(optimisticField)) {
                              group.addCmpField(optimisticField);
                           }
                        }
                     }
                     continue label184;
                  }

                  tableName = (String)cmrFields.next();
               } while(this.isForeignKeyField(tableName) && this.containsFkField(tableName));

               cmrFields.remove();
            }
         }

         if (this.isOptimistic()) {
            Set triggerTimestampTables = new HashSet();
            Iterator it = this.tableName2optimisticColumnTrigger.keySet().iterator();

            while(it.hasNext()) {
               tableName = (String)it.next();
               if (this.getTriggerUpdatesOptimisticColumn(tableName) && "timestamp".equalsIgnoreCase(this.getVerifyColumns(tableName))) {
                  triggerTimestampTables.add(tableName);
               }
            }

            if (!triggerTimestampTables.isEmpty()) {
               FieldGroup triggerGroup = new FieldGroup();
               triggerGroup.setName("optimisticTimestampTriggerGroup");
               this.groupName2tableNames.put(triggerGroup.getName(), triggerTimestampTables);

               for(cmrFields = triggerTimestampTables.iterator(); cmrFields.hasNext(); triggerGroup.addCmpField(optimisticField)) {
                  tableName = (String)cmrFields.next();
                  optimisticField = this.getCmpField(tableName, this.getOptimisticColumn(tableName));
                  if (optimisticField == null) {
                     optimisticField = "__WL_optimisticField" + this.tableIndex(tableName);
                  }
               }

               this.addFieldGroup(triggerGroup);
            }
         }

         FieldGroup defaultGroup = new FieldGroup();
         defaultGroup.setName("defaultGroup");
         Set tables = new HashSet();
         this.groupName2tableNames.put(defaultGroup.getName(), tables);
         Iterator fieldNames = this.getCmpFieldNames().iterator();

         while(fieldNames.hasNext()) {
            String fieldName = (String)fieldNames.next();
            tableName = this.getTableForCmpField(fieldName);
            tables.add(tableName);
            defaultGroup.addCmpField(fieldName);
         }

         cmrFields = this.getForeignKeyFieldNames().iterator();

         while(cmrFields.hasNext()) {
            tableName = (String)cmrFields.next();
            if (this.containsFkField(tableName)) {
               optimisticField = this.getTableForCmrField(tableName);
               tables.add(optimisticField);
               defaultGroup.addCmrField(tableName);
            }
         }

         this.addFieldGroup(defaultGroup);
         if (debugLogger.isDebugEnabled()) {
            this.printDebugInfo();
         }

         if (debugLogger.isDebugEnabled()) {
            this.printCmpFieldDebugInfo();
         }

      }
   }

   private void processRole(EjbRelationshipRole role, EjbRelationshipRole other, RDBMSRelation.RDBMSRole wlRole, RDBMSRelation.RDBMSRole wlOther, RDBMSRelation rdbmsRelation) throws RDBMSException {
      if (debugLogger.isDebugEnabled()) {
         debug("processing role: " + role.getName());
         debug("other role is: " + other.getName());
         debug(wlRole == null ? "wlRole is null" : "wlRole=" + wlRole.getName());
         debug(wlOther == null ? "wlOther is null" : "wlOther=" + wlOther.getName());
      }

      RoleSource roleSrc = role.getRoleSource();
      String srcName = roleSrc.getEjbName();
      if (this.getEjbName().equals(srcName)) {
         CmrField roleField = role.getCmrField();
         RoleSource otherSrc = other.getRoleSource();
         String otherName = otherSrc.getEjbName();
         CmrField otherField = other.getCmrField();
         Class cls = null;
         String type = null;
         boolean many2many = role.getMultiplicity().equals("Many") && other.getMultiplicity().equals("Many");
         boolean one2many = role.getMultiplicity().equals("One") && other.getMultiplicity().equals("Many");
         boolean one2one = role.getMultiplicity().equals("One") && other.getMultiplicity().equals("One");
         String cmrFieldName = RDBMSUtils.getCmrFieldName(role, other);
         if (this.cmrFieldNames.contains(cmrFieldName)) {
            this.processSymmetricRole(one2one, many2many, cmrFieldName, role, other, wlRole, wlOther);
         } else {
            this.cmrFieldNames.add(cmrFieldName);
            RDBMSBean targetBean = null;
            if (many2many) {
               targetBean = this;
            } else {
               targetBean = (RDBMSBean)this.rdbmsBeanMap.get(otherName);
            }

            if (targetBean.hasMultipleTables()) {
               this.cmrfHasMultiPkTable.add(cmrFieldName);
            }

            if (!many2many && srcName.equalsIgnoreCase(otherName)) {
               this.fieldName2selfRel.put(cmrFieldName, Boolean.TRUE);
               this.selfRelationship = true;
            } else {
               this.fieldName2selfRel.put(cmrFieldName, Boolean.FALSE);
            }

            this.fieldName2cascadeDelete.put(cmrFieldName, other.getCascadeDelete());
            if (wlOther != null) {
               this.fieldName2DBCascadeDelete.put(cmrFieldName, wlOther.getDBCascadeDelete());
            } else {
               this.fieldName2DBCascadeDelete.put(cmrFieldName, false);
            }

            if (roleField != null) {
               if (debugLogger.isDebugEnabled()) {
                  debug("processing declared field: " + role.getName());
               }

               type = roleField.getType();
               this.declaredFieldNames.add(cmrFieldName);
            }

            if (type == null) {
               if (!one2many && !many2many) {
                  cls = this.getElementalClass(other);
               } else {
                  cls = Collection.class;
               }
            } else if ("java.util.Collection".equals(type)) {
               cls = Collection.class;
            } else {
               if (!"java.util.Set".equals(type)) {
                  throw new AssertionError("invalid className: " + type);
               }

               cls = Set.class;
            }

            this.fieldName2class.put(cmrFieldName, cls);
            this.calculateRelationshipType(cmrFieldName, role, other, wlOther);
            if (wlRole != null) {
               this.processWeblogicRole_PhaseOne(cmrFieldName, role, wlRole, many2many, targetBean);
               this.processWeblogicRole_PhaseTwo(cmrFieldName, role, wlRole, rdbmsRelation, many2many, otherSrc);
               if (wlRole.isQueryCachingEnabled()) {
                  String cmrField = roleField.getName();
                  if (!targetBean.isReadOnly()) {
                     Log.getInstance().logWarning(this.fmt.QUERY_CACHING_ENABLED_FOR_CMR_TO_RW_BEAN(this.getEjbName(), wlRole.getName(), this.getRelatedRDBMSBean(cmrField).getEjbName()));
                  } else {
                     if (this.qcEnabledCmrFields == null) {
                        this.qcEnabledCmrFields = new HashSet();
                     }

                     this.qcEnabledCmrFields.add(cmrField);
                  }
               }
            }

         }
      }
   }

   public boolean isSelfRelationship(String fieldName) {
      return (Boolean)this.fieldName2selfRel.get(fieldName);
   }

   public boolean isSelfRelationship() {
      return this.selfRelationship;
   }

   private void processWeblogicRole_PhaseOne(String cmrFieldName, EjbRelationshipRole role, RDBMSRelation.RDBMSRole wlRole, boolean many2many, RDBMSBean targetBean) {
      this.initializeRole(wlRole, many2many);
      if (wlRole.getGroupName() != null) {
         this.fieldName2groupName.put(cmrFieldName, wlRole.getGroupName());
      }

      assert wlRole.getColumnMap() != null;

      if (wlRole.getColumnMap().size() > 0) {
         if (debugLogger.isDebugEnabled()) {
            debug("processing foreign key owner: " + role.getName());
            debug("foreign key field name: " + cmrFieldName);
         }

         String fkTable = wlRole.getForeignKeyTableName();
         if (!many2many) {
            List cmrFields = (List)this.table2cmrf.get(fkTable);
            if (cmrFields == null) {
               cmrFields = new ArrayList();
               this.table2cmrf.put(fkTable, cmrFields);
            }

            ((List)cmrFields).add(cmrFieldName);
            this.cmrf2table.put(cmrFieldName, fkTable);
         }

         Map columnMap = wlRole.getColumnMap();
         this.normalizeColumnNames(many2many, fkTable, columnMap);
         List fkColumns = new ArrayList(columnMap.keySet());
         this.fkField2fkColumns.put(cmrFieldName, fkColumns);
         Debug.assertion(this.cmrf2pkTable2fkColumn2pkColumn.get(cmrFieldName) == null);
         Map pkTable2fkColumn2pkColumn = new HashMap();
         this.cmrf2pkTable2fkColumn2pkColumn.put(cmrFieldName, pkTable2fkColumn2pkColumn);
         String targetPkTable = wlRole.getPrimaryKeyTableName();
         if (targetPkTable == null) {
            targetPkTable = targetBean.getTableName();
         }

         Iterator it = targetBean.getTables().iterator();

         while(it.hasNext()) {
            String table = (String)it.next();
            Debug.assertion(pkTable2fkColumn2pkColumn.get(table) == null);
            Map fkColumn2pkColumn = new HashMap();
            pkTable2fkColumn2pkColumn.put(table, fkColumn2pkColumn);

            String fkCol;
            String pkCol;
            for(Iterator it2 = columnMap.entrySet().iterator(); it2.hasNext(); fkColumn2pkColumn.put(fkCol, pkCol)) {
               Map.Entry entry = (Map.Entry)it2.next();
               fkCol = (String)entry.getKey();
               pkCol = (String)entry.getValue();
               if (!targetPkTable.equalsIgnoreCase(table)) {
                  String pkField = targetBean.getCmpField(targetPkTable, pkCol);
                  pkCol = targetBean.getColumnForCmpFieldAndTable(pkField, table);
               }
            }
         }
      }

   }

   private void processWeblogicRole_PhaseTwo(String cmrFieldName, EjbRelationshipRole role, RDBMSRelation.RDBMSRole wlRole, RDBMSRelation rdbmsRelation, boolean many2many, RoleSource otherSrc) throws RDBMSException {
      assert wlRole.getColumnMap() != null;

      if (wlRole.getColumnMap().size() > 0) {
         if (debugLogger.isDebugEnabled()) {
            debug("processing foreign key owner: " + role.getName());
            debug("foreign key field name: " + cmrFieldName);
         }

         this.fkFieldNames.add(cmrFieldName);
         RDBMSBean targetBean = this.getTargetBean(many2many, cmrFieldName, rdbmsRelation, otherSrc);
         CMPBeanDescriptor targetBd = targetBean.getCMPBeanDescriptor();
         String fkTable = wlRole.getForeignKeyTableName();
         Map columnMap = wlRole.getColumnMap();
         String pkTable = null;
         if (targetBean.hasMultipleTables()) {
            pkTable = wlRole.getPrimaryKeyTableName();
         } else {
            pkTable = targetBean.getTableName();
         }

         assert pkTable != null;

         Map fkColumn2Class = new HashMap();
         Map fkColumn2FieldName = new HashMap();
         this.fkField2fkColumn2Class.put(cmrFieldName, fkColumn2Class);
         this.fkField2fkColumn2FieldName.put(cmrFieldName, fkColumn2FieldName);
         if (rdbmsRelation.getTableName() != null) {
            this.table2fkCol2fkClass.put(rdbmsRelation.getTableName(), fkColumn2Class);
         } else {
            this.table2fkCol2fkClass.put(fkTable, fkColumn2Class);
         }

         assert this.getForeignKeyColNames(cmrFieldName) != null;

         Iterator iter = this.getForeignKeyColNames(cmrFieldName).iterator();
         boolean fkPkField = false;

         boolean fkCmpField;
         String fkColumn;
         Class fkFieldClass;
         for(fkCmpField = false; iter.hasNext(); fkColumn2Class.put(fkColumn, fkFieldClass)) {
            fkColumn = (String)iter.next();
            String keyColumn = (String)columnMap.get(fkColumn);
            if (debugLogger.isDebugEnabled()) {
               debug("processing column pair ( Fk Column: '" + fkColumn + "', Pk Column '" + keyColumn + "') and primary key table-" + pkTable);
            }

            String keyField = targetBean.getCmpField(pkTable, keyColumn);

            assert keyField != null;

            assert targetBd.getPrimaryKeyFieldNames().contains(keyField);

            if (debugLogger.isDebugEnabled()) {
               debug("found key column field pair (" + keyColumn + " " + keyField + ")");
            }

            fkColumn2FieldName.put(fkColumn, keyField);
            Class keyFieldClass = targetBd.getFieldClass(keyField);
            fkFieldClass = ClassUtils.getObjectClass(keyFieldClass);
            if (!many2many) {
               String variableName = this.variableForField(cmrFieldName, fkTable, fkColumn);
               this.variableName2columnName.put(variableName, fkColumn);
               this.variableName2table.put(variableName, fkTable);
               this.variableName2cmrField.put(variableName, cmrFieldName);
               this.addReverseColumnMapping(fkTable, fkColumn, variableName);
               if (this.hasCmpField(fkTable, fkColumn)) {
                  String cmpField = this.getCmpField(fkTable, fkColumn);
                  fkCmpField = true;
                  this.cmrMappedcmpFields.add(cmpField);
                  fkFieldClass = this.bd.getFieldClass(cmpField);
                  if (this.bd.getPrimaryKeyFieldNames().contains(cmpField)) {
                     fkPkField = true;
                  }
               }
            }
         }

         if (fkPkField) {
            this.fkPkFieldNames.add(cmrFieldName);
         }

         if (fkCmpField) {
            this.fkCmpFieldNames.add(cmrFieldName);
         }
      }

   }

   private void calculateLoadModifyIndex() {
      int tableCount = this.tableCount();
      this.tableIndexToFieldList = new List[tableCount];
      this.tableIndexToCMPFieldList = new List[tableCount];
      this.tableIndexToCMRFieldList = new List[tableCount];
      this.tableIndexToNonPKFieldList = new List[tableCount];
      Iterator it = this.getTables().iterator();

      int tableIndex;
      for(tableIndex = 0; it.hasNext(); ++tableIndex) {
         it.next();
         this.tableIndexToFieldList[tableIndex] = new ArrayList();
         this.tableIndexToCMPFieldList[tableIndex] = new ArrayList();
         this.tableIndexToCMRFieldList[tableIndex] = new ArrayList();
         this.tableIndexToNonPKFieldList[tableIndex] = new ArrayList();
      }

      Iterator fieldNames;
      String name;
      String tableName;
      for(fieldNames = this.cmpFieldNames.iterator(); fieldNames.hasNext(); ++this.numFields) {
         name = (String)fieldNames.next();
         this.fieldNameToIsModifiedIndex.put(name, new Integer(this.numFields));
         this.isModifiedIndexToFieldName.put(new Integer(this.numFields), name);
         tableName = this.getTableForCmpField(name);
         tableIndex = this.tableIndex(tableName);
         this.isModifiedIndexToTableNumber.add(new Integer(tableIndex));
         if (this.isPrimaryKeyField(name)) {
            this.isModifiedPKIndexList.add(new Integer(this.numFields));

            for(int i = 0; i < this.tableCount(); ++i) {
               this.tableIndexToFieldList[i].add(name);
               this.tableIndexToCMPFieldList[i].add(name);
            }
         } else {
            this.tableIndexToFieldList[tableIndex].add(name);
            this.tableIndexToCMPFieldList[tableIndex].add(name);
            this.tableIndexToNonPKFieldList[tableIndex].add(name);
         }
      }

      fieldNames = this.getForeignKeyFieldNames().iterator();

      while(fieldNames.hasNext()) {
         name = (String)fieldNames.next();
         if (this.containsFkField(name) && !this.isForeignCmpField(name)) {
            this.fieldNameToIsModifiedIndex.put(name, new Integer(this.numFields));
            this.isModifiedIndexToFieldName.put(new Integer(this.numFields), name);
            tableName = this.getTableForCmrField(name);
            tableIndex = this.tableIndex(tableName);
            this.isModifiedIndexToTableNumber.add(new Integer(tableIndex));
            this.tableIndexToFieldList[tableIndex].add(name);
            this.tableIndexToCMRFieldList[tableIndex].add(name);
            this.tableIndexToNonPKFieldList[tableIndex].add(name);
            ++this.numFields;
         }
      }

   }

   public void setupRelatedBeanMap() {
      if (this.relationships != null) {
         if (debugLogger.isDebugEnabled()) {
            debug("processing relationships...");
         }

         Iterator relations = this.relationships.getAllEjbRelations().entrySet().iterator();

         while(true) {
            EjbRelation ejbRel;
            RDBMSRelation rdbmsRel;
            do {
               if (!relations.hasNext()) {
                  return;
               }

               Map.Entry entry = (Map.Entry)relations.next();
               String relName = (String)entry.getKey();
               if (debugLogger.isDebugEnabled()) {
                  debug("processing relationship: " + relName);
               }

               ejbRel = (EjbRelation)entry.getValue();
               rdbmsRel = (RDBMSRelation)this.rdbmsRelationMap.get(relName);
            } while(rdbmsRel.getTableName() == null);

            assert rdbmsRel != null;

            Iterator roles = ejbRel.getAllEjbRelationshipRoles().iterator();
            EjbRelationshipRole role1 = (EjbRelationshipRole)roles.next();
            EjbRelationshipRole role2 = (EjbRelationshipRole)roles.next();
            RDBMSRelation.RDBMSRole r1 = rdbmsRel.getRole1();
            RDBMSRelation.RDBMSRole r2 = rdbmsRel.getRole2();
            if (!r1.getName().equals(role1.getName())) {
               RDBMSRelation.RDBMSRole temp = r1;
               r1 = r2;
               r2 = temp;
            }

            boolean many2many = role1.getMultiplicity().equals("Many") && role2.getMultiplicity().equals("Many");
            if (many2many) {
               if (this.getEjbName().equals(role1.getRoleSource().getEjbName())) {
                  this.createRelatedBeanMap(role2, r2, rdbmsRel);
               } else {
                  this.createRelatedBeanMap(role1, r1, rdbmsRel);
               }
            }
         }
      }
   }

   private void createRelatedBeanMap(EjbRelationshipRole other, RDBMSRelation.RDBMSRole wlOther, RDBMSRelation rel) {
      RoleSource otherSrc = other.getRoleSource();
      String otherEjbName = otherSrc.getEjbName();
      RDBMSBean relatedBean = (RDBMSBean)this.rdbmsBeanMap.get(otherEjbName);
      String joinTableName = rel.getTableName();
      Map fkCol2RelatedBean = new HashMap();
      Iterator it = wlOther.getColumnMap().entrySet().iterator();

      while(it.hasNext()) {
         Map.Entry me = (Map.Entry)it.next();
         fkCol2RelatedBean.put(me.getKey(), relatedBean);
      }

      this.table2fkCol2RelatedBean.put(joinTableName, fkCol2RelatedBean);
   }

   private void initializeRole(RDBMSRelation.RDBMSRole wlRole, boolean many2many) {
      if (!many2many && wlRole.getColumnMap().size() > 0 && !this.hasMultipleTables()) {
         wlRole.setForeignKeyTableName(this.getTableName());
      }

   }

   private void processSymmetricRole(boolean one2one, boolean many2many, String name, EjbRelationshipRole role, EjbRelationshipRole other, RDBMSRelation.RDBMSRole wlRole, RDBMSRelation.RDBMSRole wlOther) {
      Debug.assertion(one2one || many2many);
      if (many2many) {
         this.computeSymmetricColumnInfo(name, wlRole);
      }

      if (one2one) {
         this.fieldName2cascadeDelete.put(name, role.getCascadeDelete() || other.getCascadeDelete());
         boolean casDel = wlRole == null ? false : wlRole.getDBCascadeDelete();
         boolean otherCasDel = wlOther == null ? false : wlOther.getDBCascadeDelete();
         this.fieldName2DBCascadeDelete.put(name, casDel || otherCasDel);
      }

   }

   private RDBMSBean getTargetBean(boolean many2many, String name, RDBMSRelation rdbmsRelation, RoleSource otherSrc) throws RDBMSException {
      String otherName = otherSrc.getEjbName();
      RDBMSBean result = null;
      if (many2many) {
         this.fieldName2tableName.put(name, rdbmsRelation.getTableName());
         result = this;
      } else {
         String theJoinTable = rdbmsRelation.getTableName();
         if (theJoinTable != null && theJoinTable.length() > 0) {
            Loggable l = EJBLogger.logshouldNotDefineJoinTableForOneToManyLoggable(rdbmsRelation.getName(), theJoinTable);
            throw new RDBMSException(l.getMessageText());
         }

         result = (RDBMSBean)this.rdbmsBeanMap.get(otherName);
      }

      return result;
   }

   private void normalizeColumnNames(boolean many2many, String fkTable, Map columnMap) {
      if (!many2many) {
         Map tempMap = new HashMap(columnMap);
         Map column2cmpField = (Map)this.table2column2cmpf.get(fkTable);
         Iterator fkColumns = tempMap.keySet().iterator();

         while(true) {
            while(fkColumns.hasNext()) {
               String fkColumn = (String)fkColumns.next();
               Iterator cmpColumns = column2cmpField.keySet().iterator();

               while(cmpColumns.hasNext()) {
                  String cmpColumn = (String)cmpColumns.next();
                  if (cmpColumn.equalsIgnoreCase(fkColumn) && !cmpColumn.equals(fkColumn)) {
                     String pkColumn = (String)columnMap.get(fkColumn);
                     columnMap.remove(fkColumn);
                     columnMap.put(cmpColumn, pkColumn);
                     break;
                  }
               }
            }

            return;
         }
      }
   }

   private void calculateRelationshipType(String name, EjbRelationshipRole role, EjbRelationshipRole other, RDBMSRelation.RDBMSRole wlOther) {
      if (role.getMultiplicity().equals("One")) {
         if (other.getMultiplicity().equals("One")) {
            this.one2OneSet.add(name);
         } else {
            this.one2ManySet.add(name);
         }
      } else if (other.getMultiplicity().equals("Many")) {
         this.many2ManySet.add(name);
      } else {
         this.one2ManySet.add(name);
      }

      if (role.getCmrField() != null && other.getCmrField() != null) {
         this.biDirectional.add(name);
      }

      this.fieldName2relatedMultiplicity.put(name, other.getMultiplicity());
      this.fieldName2relatedFieldOwnsFk.put(name, wlOther != null && wlOther.getColumnMap().size() > 0);
      String relatedFieldName = RDBMSUtils.getCmrFieldName(other, role);
      this.fieldName2RelatedFieldName.put(name, relatedFieldName);
      RoleSource otherSrc = other.getRoleSource();
      String otherName = otherSrc.getEjbName();
      CMPBeanDescriptor targetBd = (CMPBeanDescriptor)this.beanMap.get(otherName);
      this.fieldName2relatedDescriptor.put(name, targetBd);
      this.fieldName2relatedRDBMSBean.put(name, this.rdbmsBeanMap.get(otherName));
      this.fieldName2relatedClassName.put(name, targetBd.getGeneratedBeanClassName());
   }

   private Class getElementalClass(EjbRelationshipRole role) {
      RoleSource src = role.getRoleSource();
      CMPBeanDescriptor targetBd = (CMPBeanDescriptor)this.beanMap.get(src.getEjbName());
      return targetBd.hasLocalClientView() ? targetBd.getLocalInterfaceClass() : targetBd.getRemoteInterfaceClass();
   }

   private void computeSymmetricColumnInfo(String fieldName, RDBMSRelation.RDBMSRole wlRole) {
      Map columnMap = wlRole.getColumnMap();
      List symColumns = new ArrayList(columnMap.keySet());
      this.fkField2symColumns.put(fieldName, symColumns);
      Map symColumn2FieldName = new HashMap();
      this.fkField2symColumn2FieldName.put(fieldName, symColumn2FieldName);
      Iterator iter = symColumns.iterator();

      while(iter.hasNext()) {
         String symColumn = (String)iter.next();
         String keyColumn = (String)columnMap.get(symColumn);
         if (debugLogger.isDebugEnabled()) {
            debug("processing column pair (" + symColumn + " " + keyColumn + ")");
         }

         String keyField = this.getCmpFieldForColumn(keyColumn);
         if (debugLogger.isDebugEnabled()) {
            debug("found key column field pair (" + keyColumn + " " + keyField + ")");
         }

         assert keyField != null;

         symColumn2FieldName.put(symColumn, keyField);
      }

      Map pkTable2symFkColumn2pkColumn = (Map)this.fkField2pkTable2symFkColumn2pkColumn.get(fieldName);
      if (pkTable2symFkColumn2pkColumn == null) {
         pkTable2symFkColumn2pkColumn = new HashMap();
         this.fkField2pkTable2symFkColumn2pkColumn.put(fieldName, pkTable2symFkColumn2pkColumn);
      }

      RDBMSBean otherBean = this;
      Map roleColumnMap = wlRole.getColumnMap();
      String pkTable = null;
      if (wlRole.getPrimaryKeyTableName() != null) {
         pkTable = wlRole.getPrimaryKeyTableName();
      } else {
         pkTable = this.getTableName();
      }

      Iterator var11 = this.getTables().iterator();

      while(var11.hasNext()) {
         String table = (String)var11.next();
         Map symFkColumn2pkColumn = (Map)((Map)pkTable2symFkColumn2pkColumn).get(table);
         if (symFkColumn2pkColumn == null) {
            symFkColumn2pkColumn = new HashMap();
            ((Map)pkTable2symFkColumn2pkColumn).put(table, symFkColumn2pkColumn);
         }

         String fkColumn;
         String pkColumn;
         for(Iterator fks = roleColumnMap.entrySet().iterator(); fks.hasNext(); ((Map)symFkColumn2pkColumn).put(fkColumn, pkColumn)) {
            Map.Entry entry = (Map.Entry)fks.next();
            fkColumn = (String)entry.getKey();
            pkColumn = (String)entry.getValue();
            if (!table.equals(pkTable)) {
               String pkField = otherBean.getCmpField(pkTable, pkColumn);
               pkColumn = otherBean.getColumnForCmpFieldAndTable(pkField, table);
            }
         }
      }

   }

   public void printDebugInfo() {
      debug("Ejb Name: " + this.getEjbName());
      debug("All Field Names: " + this.cmrFieldNames);
      debug("Declared Field Names: " + this.declaredFieldNames);
      debug("Foreign Key Field Names: " + this.fkFieldNames);
      debug("Foreign Key Primary Key Field Names: " + this.fkPkFieldNames);
      debug("Foreign Key CMP Field Names: " + this.fkCmpFieldNames);
      debug("Field Name To Class Mapping: ");
      Iterator var1 = this.fieldName2class.entrySet().iterator();

      Map.Entry entry;
      while(var1.hasNext()) {
         entry = (Map.Entry)var1.next();
         debug((String)entry.getKey() + ", \t" + ((Class)entry.getValue()).getName());
      }

      debug("FK Field Name To Column Name Mapping:");
      var1 = this.fkField2fkColumns.entrySet().iterator();

      String val;
      Iterator var4;
      while(var1.hasNext()) {
         entry = (Map.Entry)var1.next();
         val = (String)entry.getKey();
         var4 = ((List)entry.getValue()).iterator();

         while(var4.hasNext()) {
            String s = (String)var4.next();
            debug(val + ", \t" + s);
         }
      }

      debug("Field Name To Related Class Name  Mapping:");
      var1 = this.fieldName2relatedClassName.entrySet().iterator();

      while(var1.hasNext()) {
         entry = (Map.Entry)var1.next();
         debug((String)entry.getKey() + ", \t" + (String)entry.getValue());
      }

      debug("Field Name To Related Field Owns FK Mapping:");
      var1 = this.fieldName2relatedFieldOwnsFk.entrySet().iterator();

      while(var1.hasNext()) {
         entry = (Map.Entry)var1.next();
         debug((String)entry.getKey() + ", \t" + entry.getValue());
      }

      debug("Field Name To Related Multiplicity Mapping:");
      var1 = this.fieldName2relatedMultiplicity.entrySet().iterator();

      while(var1.hasNext()) {
         entry = (Map.Entry)var1.next();
         debug((String)entry.getKey() + ", \t" + (String)entry.getValue());
      }

      debug("Field Name To Related Descriptor Mapping:");
      var1 = this.fieldName2relatedDescriptor.entrySet().iterator();

      while(var1.hasNext()) {
         entry = (Map.Entry)var1.next();
         debug((String)entry.getKey() + ", \t" + ((CMPBeanDescriptor)entry.getValue()).getEJBName());
      }

      debug("Field Name To Related RDBMS Bean  Mapping:");
      var1 = this.fieldName2relatedRDBMSBean.entrySet().iterator();

      while(var1.hasNext()) {
         entry = (Map.Entry)var1.next();
         debug((String)entry.getKey() + ", \t" + ((RDBMSBean)entry.getValue()).getEjbName());
      }

      debug("CMR Field Name To Table Mapping:");
      var1 = this.cmrf2table.entrySet().iterator();

      while(var1.hasNext()) {
         entry = (Map.Entry)var1.next();
         debug((String)entry.getKey() + ", \t" + (String)entry.getValue());
      }

      debug("FK Field Name To Column Name Mapping:");
      var1 = this.fkField2fkColumn2FieldName.entrySet().iterator();

      Map.Entry entry2;
      while(var1.hasNext()) {
         entry = (Map.Entry)var1.next();
         val = (String)entry.getKey();
         var4 = ((Map)entry.getValue()).entrySet().iterator();

         while(var4.hasNext()) {
            entry2 = (Map.Entry)var4.next();
            debug(val + ", \t" + (String)entry2.getKey() + ", \t" + (String)entry2.getValue());
         }
      }

      debug("Field Name To Related Field Name  Mapping:");
      var1 = this.fieldName2RelatedFieldName.entrySet().iterator();

      while(var1.hasNext()) {
         entry = (Map.Entry)var1.next();
         debug((String)entry.getKey() + ", \t" + (String)entry.getValue());
      }

      debug("Bidirectional fields: " + this.biDirectional);
      debug("1 to 1 fields: " + this.one2OneSet);
      debug("1 to N fields: " + this.one2ManySet);
      debug("N to M fields: " + this.many2ManySet);
      debug("FK Field Name To Column To Class:");
      var1 = this.fkField2fkColumn2Class.entrySet().iterator();

      while(var1.hasNext()) {
         entry = (Map.Entry)var1.next();
         val = (String)entry.getKey();
         var4 = ((Map)entry.getValue()).entrySet().iterator();

         while(var4.hasNext()) {
            entry2 = (Map.Entry)var4.next();
            debug(val + ", \t" + (String)entry2.getKey() + ", \t" + ((Class)entry2.getValue()).getName());
         }
      }

      debug("Symmetric M-N Field To FK Columns:");
      var1 = this.fkField2symColumns.entrySet().iterator();

      Iterator var7;
      while(var1.hasNext()) {
         entry = (Map.Entry)var1.next();
         debug((String)entry.getKey());
         var7 = ((List)entry.getValue()).iterator();

         while(var7.hasNext()) {
            String s = (String)var7.next();
            debug(" \t" + s);
         }
      }

      debug("Symmetric M-N Field To FK Column To Field:");
      var1 = this.fkField2symColumn2FieldName.entrySet().iterator();

      while(var1.hasNext()) {
         entry = (Map.Entry)var1.next();
         val = (String)entry.getKey();
         var4 = ((Map)entry.getValue()).entrySet().iterator();

         while(var4.hasNext()) {
            entry2 = (Map.Entry)var4.next();
            debug(val + ", \t" + (String)entry2.getKey() + ", \t" + (String)entry2.getValue());
         }
      }

      debug("Symmetric M-N Field To PK Table To FK Column To PK Column:");
      var1 = this.fkField2pkTable2symFkColumn2pkColumn.entrySet().iterator();

      while(var1.hasNext()) {
         entry = (Map.Entry)var1.next();
         debug(" Symmetric CMR Field: " + (String)entry.getKey());
         var7 = ((Map)entry.getValue()).entrySet().iterator();

         while(var7.hasNext()) {
            Map.Entry entry2 = (Map.Entry)var7.next();
            debug("    Dest PK Table: " + (String)entry2.getKey());
            Iterator var11 = ((Map)entry2.getValue()).entrySet().iterator();

            while(var11.hasNext()) {
               Map.Entry entry3 = (Map.Entry)var11.next();
               debug("      FK Column: " + (String)entry3.getKey() + "   PK Column: " + (String)entry3.getValue());
            }
         }
      }

      debug("CMP Field Name To Column Mapping:");
      var1 = this.fieldName2columnName.entrySet().iterator();

      while(var1.hasNext()) {
         entry = (Map.Entry)var1.next();
         debug((String)entry.getKey() + ", \t" + (String)entry.getValue());
      }

      debug("CMP Field Name To ColumnType Mapping:");
      var1 = this.fieldName2columnTypeName.entrySet().iterator();

      while(var1.hasNext()) {
         entry = (Map.Entry)var1.next();
         debug((String)entry.getKey() + ", \t" + (String)entry.getValue());
      }

      debug("CMP Field Name To GroupName Mapping:");
      var1 = this.cmpFieldName2groupName.entrySet().iterator();

      while(var1.hasNext()) {
         entry = (Map.Entry)var1.next();
         debug((String)entry.getKey() + ", \t" + (String)entry.getValue());
      }

      debug("CMR Field Name To Join Table Name Mapping:");
      var1 = this.fieldName2tableName.entrySet().iterator();

      while(var1.hasNext()) {
         entry = (Map.Entry)var1.next();
         debug((String)entry.getKey() + ", \t" + (String)entry.getValue());
      }

      debug("RemoteField Names: " + this.remoteFieldNames);
      debug("RemoteField Names To Remote Column:");
      var1 = this.remoteFieldNames.iterator();

      while(var1.hasNext()) {
         String key = (String)var1.next();
         val = (String)this.fieldName2remoteColumn.get(key);
         if (null != val) {
            debug(key + ", \t" + val);
         }
      }

      debug("Field Name To Remote Name:");
      var1 = this.fieldName2entityRef.entrySet().iterator();

      while(var1.hasNext()) {
         entry = (Map.Entry)var1.next();
         debug((String)entry.getKey() + ", " + ((EjbEntityRef)entry.getValue()).getRemoteEjbName());
      }

      debug("Field Name To Group Name:");
      var1 = this.fieldName2groupName.entrySet().iterator();

      while(var1.hasNext()) {
         entry = (Map.Entry)var1.next();
         debug((String)entry.getKey() + ", " + (String)entry.getValue());
      }

      debug("Field Name To Cascade Delete:");
      var1 = this.fieldName2cascadeDelete.entrySet().iterator();

      while(var1.hasNext()) {
         entry = (Map.Entry)var1.next();
         debug((String)entry.getKey() + ", " + ((Boolean)entry.getValue()).toString());
      }

      debug("*****************************************************");
   }

   public void printCmpFieldDebugInfo() {
      debug("*****************************************************");
      debug("      CMP FIELD information ");
      debug("*****************************************************");
      debug("Ejb Name: " + this.getEjbName());
      debug("Table Name To cmp-field To DBMS Column Name  ALL:");
      Iterator var1 = this.tableName2cmpFieldName2columnName.entrySet().iterator();

      Map.Entry entry;
      Iterator var3;
      Map.Entry entry2;
      while(var1.hasNext()) {
         entry = (Map.Entry)var1.next();
         debug(" Table Name: " + (String)entry.getKey());
         var3 = ((Map)entry.getValue()).entrySet().iterator();

         while(var3.hasNext()) {
            entry2 = (Map.Entry)var3.next();
            debug("      " + (String)entry2.getKey() + ", " + (String)entry2.getValue());
         }
      }

      debug("Table Name To cmp-field To DBMS Column Name  PKs only:");
      var1 = this.table2cmpf2columnPKsOnly.entrySet().iterator();

      while(var1.hasNext()) {
         entry = (Map.Entry)var1.next();
         String key = (String)entry.getKey();
         debug(" Table Name: " + key);
         Iterator var8 = ((Map)entry.getValue()).entrySet().iterator();

         while(var8.hasNext()) {
            Map.Entry entry2 = (Map.Entry)var8.next();
            debug("      " + (String)entry2.getKey() + ", " + (String)entry2.getValue());
         }
      }

      debug("Table Name To cmp-field To DBMS Column Name  No PKs:");
      var1 = this.table2cmpf2columnNoPKs.entrySet().iterator();

      while(var1.hasNext()) {
         entry = (Map.Entry)var1.next();
         debug(" Table Name: " + (String)entry.getKey());
         var3 = ((Map)entry.getValue()).entrySet().iterator();

         while(var3.hasNext()) {
            entry2 = (Map.Entry)var3.next();
            debug("      " + (String)entry2.getKey() + ", " + (String)entry2.getValue());
         }
      }

      debug("cmp-field To Table:");
      var1 = this.cmpf2Table.entrySet().iterator();

      while(var1.hasNext()) {
         entry = (Map.Entry)var1.next();
         debug("      " + (String)entry.getKey() + ", " + (String)entry.getValue());
      }

      debug("PK cmp-field To Table Name To DBMS Column Name:");
      var1 = this.pkCmpF2Table2Column.entrySet().iterator();

      while(var1.hasNext()) {
         entry = (Map.Entry)var1.next();
         debug(" PK cmp-field: " + (String)entry.getKey());
         var3 = ((Map)entry.getValue()).entrySet().iterator();

         while(var3.hasNext()) {
            entry2 = (Map.Entry)var3.next();
            debug("      " + (String)entry2.getKey() + ", " + (String)entry2.getValue());
         }
      }

      debug("DBMS Column To Tables Containing the Column:");
      var1 = this.column2tables.entrySet().iterator();

      while(var1.hasNext()) {
         entry = (Map.Entry)var1.next();
         debug(" DBMS Column: " + (String)entry.getKey());
         var3 = ((List)entry.getValue()).iterator();

         while(var3.hasNext()) {
            String s = (String)var3.next();
            debug("      " + s);
         }
      }

      debug("CMR Field To PKTable (or JoinTable) To FKColumn to PKColumn:");
      var1 = this.cmrf2pkTable2fkColumn2pkColumn.entrySet().iterator();

      while(var1.hasNext()) {
         entry = (Map.Entry)var1.next();
         debug(" CMR Field: " + (String)entry.getKey());
         var3 = ((Map)entry.getValue()).entrySet().iterator();

         while(var3.hasNext()) {
            entry2 = (Map.Entry)var3.next();
            debug("    Dest PK Table: " + (String)entry2.getKey());
            Iterator var10 = ((Map)entry2.getValue()).entrySet().iterator();

            while(var10.hasNext()) {
               Map.Entry entry3 = (Map.Entry)var10.next();
               debug("      FK Column: " + (String)entry3.getKey() + "   PK Column: " + (String)entry3.getValue());
            }
         }
      }

      debug("*****************************************************\n");
   }

   public void addTableFieldColumnMapping(String tableName, String beanField, String dbmsColumn) {
      if (debugLogger.isDebugEnabled()) {
         debug(" adding TableFieldColumn Mapping for table: '" + tableName + "', field: '" + beanField + "', column: '" + dbmsColumn + "'");
      }

      Map m = (Map)this.tableName2cmpFieldName2columnName.get(tableName);
      if (m == null) {
         m = new HashMap();
         this.tableName2cmpFieldName2columnName.put(tableName, m);
      }

      ((Map)m).put(beanField, dbmsColumn);
      if (debugLogger.isDebugEnabled()) {
         debug(" added TableFieldColumn Mapping for table: '" + tableName + "' done.");
      }

      m = (Map)this.table2column2cmpf.get(tableName);
      if (m == null) {
         m = new HashMap();
         this.table2column2cmpf.put(tableName, m);
      }

      ((Map)m).put(dbmsColumn, beanField);
      this.addReverseColumnMapping(tableName, dbmsColumn, beanField);
      List l = (List)this.column2tables.get(dbmsColumn);
      if (l == null) {
         l = new ArrayList();
         this.column2tables.put(dbmsColumn, l);
      }

      ((List)l).add(tableName);
      this.cmpf2Table.put(beanField, tableName);
      if (!this.cmpFieldNames.contains(beanField)) {
         this.cmpFieldNames.add(beanField);
      }

      this.columnName2fieldName.put(dbmsColumn, beanField);
      this.fieldName2columnName.put(beanField, dbmsColumn);
      this.cmpColumnNames.add(dbmsColumn);
   }

   public void setOptimisticColumn(String tableName, String columnName) {
      this.tableName2optimisticColumn.put(tableName, columnName);
   }

   public void setTriggerUpdatesOptimisticColumn(String tableName, boolean trigger) {
      this.tableName2optimisticColumnTrigger.put(tableName, trigger);
   }

   public boolean getTriggerUpdatesOptimisticColumn(String tableName) {
      return (Boolean)this.tableName2optimisticColumnTrigger.get(tableName);
   }

   public void setVersionColumnInitialValue(String tableName, int value) {
      this.tableName2versionColumnInitialValue.put(tableName, value);
   }

   public int getVersionColumnInitialValue(String tableName) {
      return (Integer)this.tableName2versionColumnInitialValue.get(tableName);
   }

   public void addFieldColumnTypeMapping(String beanField, String dbmsColumnType) {
      if (dbmsColumnType.equals("Blob")) {
         this.hasBlobColumn = true;
      } else if (dbmsColumnType.equals("Clob")) {
         this.hasClobColumn = true;
      } else if (dbmsColumnType.equals("NClob")) {
         this.hasClobColumn = true;
         this.hasNClobColumn = true;
      }

      if (this.hasBlobColumn || this.hasClobColumn) {
         this.hasBlobClobColumn = true;
      }

      this.fieldName2columnTypeName.put(beanField, dbmsColumnType);
   }

   public void addDbmsDefaultValueField(String beanField) {
      if (this.dbmsDefaultValueFields == null) {
         this.dbmsDefaultValueFields = new HashSet();
      }

      this.dbmsDefaultValueFields.add(beanField);
   }

   public boolean isDbmsDefaultValueField(String fieldName) {
      return this.dbmsDefaultValueFields != null && this.dbmsDefaultValueFields.contains(fieldName) && !this.isPrimaryKeyField(fieldName);
   }

   public boolean allowReadonlyCreateAndRemove() {
      return this.allowReadonlyCreateAndRemove;
   }

   public void setAllowReadonlyCreateAndRemove(boolean value) {
      this.allowReadonlyCreateAndRemove = value;
   }

   public void setByteArrayIsSerializedToOracleBlob(boolean flag) {
      this.byteArrayIsSerializedToOracleBlob = flag;
   }

   public boolean getByteArrayIsSerializedToOracleBlob() {
      return this.byteArrayIsSerializedToOracleBlob;
   }

   public void setLoadRelatedBeansFromDbInPostCreate(boolean flag) {
      this.loadRelatedBeansFromDbInPostCreate = flag;
   }

   public boolean getLoadRelatedBeansFromDbInPostCreate() {
      return this.loadRelatedBeansFromDbInPostCreate;
   }

   public boolean hasBlobClobColumn() {
      return this.hasBlobClobColumn;
   }

   public boolean hasBlobColumn() {
      return this.hasBlobColumn;
   }

   public boolean hasClobColumn() {
      return this.hasClobColumn;
   }

   public boolean hasNClobColumn() {
      return this.hasNClobColumn;
   }

   public String getTableForCmpField(String fieldName) {
      return (String)this.cmpf2Table.get(fieldName);
   }

   public int getTableIndexForCmpField(String fieldName) {
      String tableName = this.getTableForCmpField(fieldName);
      return tableName == null ? -1 : this.tableIndex(tableName);
   }

   public Map getCmpField2ColumnMap(String tableName) {
      return (Map)this.tableName2cmpFieldName2columnName.get(tableName);
   }

   public Map getTableName2CmpField2ColumnMap() {
      return this.tableName2cmpFieldName2columnName;
   }

   public String getColumnForCmpFieldAndTable(String fieldName, String tableName) {
      Map m = (Map)this.tableName2cmpFieldName2columnName.get(tableName);
      return m == null ? null : (String)m.get(fieldName);
   }

   public String getCmpColumnForField(String fieldName) {
      return (String)this.fieldName2columnName.get(fieldName);
   }

   public String getCmpFieldForColumn(String columnName) {
      return (String)this.columnName2fieldName.get(columnName);
   }

   public String getPkCmpFieldForColumn(String columnName) {
      Iterator it = this.table2cmpf2columnPKsOnly.keySet().iterator();

      while(it.hasNext()) {
         String table = (String)it.next();
         Map m = (Map)this.table2cmpf2columnPKsOnly.get(table);
         Iterator it2 = m.entrySet().iterator();

         while(it2.hasNext()) {
            Map.Entry entry = (Map.Entry)it2.next();
            String cmpField = (String)entry.getKey();
            String columnNameMaybe = (String)entry.getValue();
            if (columnNameMaybe.equals(columnName)) {
               return cmpField;
            }
         }
      }

      return null;
   }

   public boolean hasCmpField(String tableName, String columnName) {
      return this.getCmpField(tableName, columnName) != null;
   }

   public String getCmpField(String tableName, String columnName) {
      Map m = (Map)this.table2column2cmpf.get(tableName);
      return m == null ? null : (String)m.get(columnName);
   }

   public String getVariable(String table, String column) {
      Map m = (Map)this.table2column2variable.get(table);
      return m == null ? null : (String)m.get(column);
   }

   public String getCmpColumnTypeForField(String fieldName) {
      return (String)this.fieldName2columnTypeName.get(fieldName);
   }

   public boolean isBlobCmpColumnTypeForField(String fieldName) {
      return "Blob".equalsIgnoreCase(this.getCmpColumnTypeForField(fieldName));
   }

   public boolean isClobCmpColumnTypeForField(String fieldName) {
      return "Clob".equalsIgnoreCase(this.getCmpColumnTypeForField(fieldName)) || this.isNClobCmpColumnTypeForField(fieldName);
   }

   public boolean isNClobCmpColumnTypeForField(String fieldName) {
      return "NClob".equalsIgnoreCase(this.getCmpColumnTypeForField(fieldName));
   }

   public boolean isBlobCmpColumnTypeForColumn(String columnName) {
      String fieldName = (String)this.columnName2fieldName.get(columnName);
      return this.isBlobCmpColumnTypeForField(fieldName);
   }

   public boolean isClobCmpColumnTypeForColumn(String columnName) {
      String fieldName = (String)this.columnName2fieldName.get(columnName);
      return this.isClobCmpColumnTypeForField(fieldName);
   }

   public Map getCmpColumnToFieldMap() {
      return this.columnName2fieldName;
   }

   public Map getCmpFieldToColumnMap() {
      return this.fieldName2columnName;
   }

   public Map getCmpFieldToColumnTypeMap() {
      return this.fieldName2columnTypeName;
   }

   public Class getCmpFieldClass(String fieldName) {
      assert this.cmpFieldClasses.get(fieldName) != null;

      return (Class)this.cmpFieldClasses.get(fieldName);
   }

   public List getCmpFieldNames() {
      return this.cmpFieldNames;
   }

   public List getCmpColumnNames() {
      return this.cmpColumnNames;
   }

   public boolean isCmpFieldName(String s) {
      return this.cmpFieldNames.contains(s);
   }

   public boolean hasCmpField(String fieldName) {
      return this.fieldName2columnName.get(fieldName) != null;
   }

   public boolean hasCmpColumnType(String fieldName) {
      return this.fieldName2columnTypeName.get(fieldName) != null;
   }

   public boolean hasPkColumn(String colName) {
      return this.getPkCmpFieldForColumn(colName) != null;
   }

   public boolean isCmrMappedCmpField(String cmpField) {
      return this.cmrMappedcmpFields.contains(cmpField);
   }

   public String getTableForVariable(String variable) {
      return (String)this.variableName2table.get(variable);
   }

   public String getCmpColumnForVariable(String variable) {
      return (String)this.variableName2columnName.get(variable);
   }

   public boolean isJoinTable(String name) {
      return this.fieldName2tableName.containsValue(name);
   }

   public String getCmrFieldForJoinTable(String tableName) {
      Iterator var2 = this.fieldName2tableName.entrySet().iterator();

      Map.Entry me;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         me = (Map.Entry)var2.next();
      } while(!((String)me.getValue()).equals(tableName));

      return (String)me.getKey();
   }

   public Map getJoinTableMap() {
      return this.fieldName2tableName;
   }

   public Map getFkColumn2ClassMapForFkField(String fkField) {
      return (Map)this.fkField2fkColumn2Class.get(fkField);
   }

   public void setOrderDatabaseOperations(boolean orderDatabaseOperations) {
      this.orderDatabaseOperations = orderDatabaseOperations;
   }

   public boolean getOrderDatabaseOperations() {
      return this.orderDatabaseOperations;
   }

   public void setEnableBatchOperations(boolean enableBatchOperations) {
      this.enableBatchOperations = enableBatchOperations;
      if (this.getDelayInsertUntil().equals("commit")) {
         this.enableBatchOperations = true;
      }

      if (this.enableBatchOperations) {
         this.orderDatabaseOperations = true;
      }

      if (this.hasAutoKeyGeneration() && this.genKeyType == 1) {
         this.enableBatchOperations = false;
         this.orderDatabaseOperations = false;
      }

   }

   public boolean getEnableBatchOperations() {
      return this.enableBatchOperations;
   }

   public String getValidateDbSchemaWith() {
      return this.validateDbSchemaWith;
   }

   public void setValidateDbSchemaWith(String string) {
      this.validateDbSchemaWith = string;
   }

   public String getCreateDefaultDBMSTables() {
      return this.createDefaultDBMSTables;
   }

   public void setCreateDefaultDBMSTables(String b) {
      this.createDefaultDBMSTables = b;
   }

   public String getDefaultDbmsTablesDdl() {
      return this.ddlFileName;
   }

   public void setDefaultDbmsTablesDdl(String ddlFileName) {
      this.ddlFileName = ddlFileName;
   }

   public int getDatabaseType() {
      return this.databaseType;
   }

   public void setDatabaseType(int databaseType) {
      this.databaseType = databaseType;
   }

   public void setCheckExistsOnMethod(boolean val) {
      this.checkExistsOnMethod = val;
   }

   public boolean getCheckExistsOnMethod() {
      return this.checkExistsOnMethod;
   }

   public void setClusterInvalidationDisabled(boolean val) {
      this.clusterInvalidationDisabled = val;
   }

   public boolean isClusterInvalidationDisabled() {
      return this.clusterInvalidationDisabled;
   }

   public void setDelayInsertUntil(String delayInsertUntil) {
      this.delayInsertUntil = delayInsertUntil;
   }

   public String getDelayInsertUntil() {
      return this.delayInsertUntil;
   }

   public void setUseSelectForUpdate(boolean useSelectForUpdate) {
      this.useSelectForUpdate = useSelectForUpdate;
   }

   public boolean getUseSelectForUpdate() {
      return this.useSelectForUpdate;
   }

   public void setLockOrder(int lockOrder) {
      this.lockOrder = lockOrder;
   }

   public int getLockOrder() {
      return this.lockOrder;
   }

   public void setInstanceLockOrder(String instanceLockOrder) {
      this.instanceLockOrder = instanceLockOrder;
   }

   public String getInstanceLockOrder() {
      return this.instanceLockOrder;
   }

   public int getFieldCount() {
      return this.numFields;
   }

   public Integer getIsModifiedIndex(String field) {
      return (Integer)this.fieldNameToIsModifiedIndex.get(field);
   }

   public Map getFieldNameToIsModifiedIndex() {
      return this.fieldNameToIsModifiedIndex;
   }

   public String getFieldName(Integer modifiedIndex) {
      return (String)this.isModifiedIndexToFieldName.get(modifiedIndex);
   }

   public List getNonPKFields(int tableIndex) {
      return this.tableIndexToNonPKFieldList[tableIndex];
   }

   public List getFields(int tableIndex) {
      return this.tableIndexToFieldList[tableIndex];
   }

   public List getCMPFields(int tableIndex) {
      return this.tableIndexToCMPFieldList[tableIndex];
   }

   public List getCMRFields(int tableIndex) {
      return this.tableIndexToCMRFieldList[tableIndex];
   }

   public List getIsModifiedIndices_PK() {
      return this.isModifiedPKIndexList;
   }

   public Integer getTableNumber(int modifiedIndex) {
      return (Integer)this.isModifiedIndexToTableNumber.get(modifiedIndex);
   }

   public List getIsModifiedIndexToTableNumber() {
      return this.isModifiedIndexToTableNumber;
   }

   public void addFinder(Finder finder) {
      this.finderList.add(finder);
      if (finder.isResultSetFinder()) {
         this.hasResultSetFinder = true;
      }

      if (finder.isSqlFinder()) {
         this.hasSqlFinder = true;
      }

   }

   public Finder createFinder(String methodName, String[] methodParams, String queryText) throws InvalidFinderException {
      RDBMSFinder rf = (RDBMSFinder)this.getRdbmsFinders().get(new RDBMSFinder.FinderKey(methodName, methodParams));
      if (debugLogger.isDebugEnabled()) {
         debug("ejb- " + this.getEjbName() + ", finder - " + methodName);
      }

      Object finder;
      if (rf != null) {
         if (rf.getSqlQueries() != null) {
            if (debugLogger.isDebugEnabled()) {
               debug("sql ** ejb- " + this.getEjbName() + ", finder - " + methodName);
            }

            SqlFinder sqlFinder = new SqlFinder(methodName, rf.getSqlQueries(), rf.getSqlShapeName(), this);
            finder = sqlFinder;
         } else {
            EjbqlFinder ejbqlFinder = new EjbqlFinder(methodName, queryText);
            if (rf.getEjbQlQuery() != null) {
               if (debugLogger.isDebugEnabled()) {
                  debug("wl ql ** ejb- " + this.getEjbName() + ", finder - " + methodName);
               }

               ejbqlFinder.setEjbQuery(rf.getEjbQlQuery());
            }

            ejbqlFinder.setGroupName(rf.getGroupName());
            ejbqlFinder.setCachingName(rf.getCachingName());
            ejbqlFinder.setSqlSelectDistinct(rf.getSqlSelectDistinct());
            ejbqlFinder.setIncludeResultCacheHint(rf.isIncludeResultCacheHint());
            finder = ejbqlFinder;
         }

         ((Finder)finder).setMaxElements(rf.getMaxElements());
         ((Finder)finder).setIncludeUpdates(rf.getIncludeUpdates());
      } else {
         finder = new EjbqlFinder(methodName, queryText);
      }

      ((Finder)finder).setRDBMSBean(this);
      return (Finder)finder;
   }

   public void perhapsSetQueryCachingEnabled(Finder finder) {
      RDBMSFinder rf = (RDBMSFinder)this.getRdbmsFinders().get(new RDBMSFinder.FinderKey(finder));
      if (rf != null) {
         finder.setQueryCachingEnabled(rf, this);
      } else {
         finder.setQueryCachingEnabled(false);
      }

   }

   public Iterator getFinders() {
      return this.finderList.iterator();
   }

   public List getFinderList() {
      return this.finderList;
   }

   public boolean hasResultSetFinder() {
      return this.hasResultSetFinder;
   }

   public void generateFinderSQLStatements(Iterator finders) throws ErrorCollectionException {
      EJBQLParsingException methodEPE = new EJBQLParsingException();

      while(finders.hasNext()) {
         Finder finder = (Finder)finders.next();
         if (finder instanceof EjbqlFinder) {
            EjbqlFinder ejbqlFinder = (EjbqlFinder)finder;

            try {
               ejbqlFinder.computeSQLQuery(this);
               if (debugLogger.isDebugEnabled()) {
                  String s = finder.getSQLQuery();
                  if (s == null) {
                     debug("finder.computSQLQuery: None generated.  NULL !");
                  } else {
                     debug("finder.computSQLQuery: " + s);
                  }
               }
            } catch (EJBQLCompilerException var6) {
               methodEPE.add(var6);
            }
         }
      }

      if (methodEPE.getExceptions().size() > 0) {
         throw methodEPE;
      }
   }

   public List getCmrFieldNames() {
      return this.cmrFieldNames;
   }

   public List getDeclaredFieldNames() {
      return this.declaredFieldNames;
   }

   public List getForeignKeyFieldNames() {
      return this.fkFieldNames;
   }

   public List getForeignPrimaryKeyFieldNames() {
      return this.fkPkFieldNames;
   }

   public List getRemoteFieldNames() {
      return this.remoteFieldNames;
   }

   public boolean isRemoteField(String fieldName) {
      return this.remoteFieldNames.contains(fieldName);
   }

   public EjbEntityRef getEjbEntityRef(String fieldName) {
      return (EjbEntityRef)this.fieldName2entityRef.get(fieldName);
   }

   public Class getForeignKeyColClass(String fieldName, String colName) {
      assert this.fkField2fkColumn2Class.get(fieldName) != null;

      assert ((Map)this.fkField2fkColumn2Class.get(fieldName)).get(colName) != null;

      return (Class)((Map)this.fkField2fkColumn2Class.get(fieldName)).get(colName);
   }

   public List getForeignKeyColNames(String fieldName) {
      assert this.fkField2fkColumns.get(fieldName) != null;

      return (List)this.fkField2fkColumns.get(fieldName);
   }

   public Class getJavaClassTypeForFkCol(String tableName, String dbmsColumnName) {
      Map fkColumn2fkClass = (Map)this.table2fkCol2fkClass.get(tableName);
      return fkColumn2fkClass == null ? null : (Class)fkColumn2fkClass.get(dbmsColumnName);
   }

   public boolean hasCmrField(String tableName, String columnName) {
      return this.getJavaClassTypeForFkCol(tableName, columnName) != null;
   }

   public RDBMSBean getRelatedBean(String tableName, String fkColumnName) {
      Map fkCol2RelatedBean = (Map)this.table2fkCol2RelatedBean.get(tableName);
      return fkCol2RelatedBean == null ? null : (RDBMSBean)fkCol2RelatedBean.get(fkColumnName);
   }

   public List getSymmetricKeyColNames(String fieldName) {
      assert this.fkField2fkColumns.get(fieldName) != null;

      assert this.fkField2symColumns.get(fieldName) != null;

      return (List)this.fkField2symColumns.get(fieldName);
   }

   public Map getSymmetricColumn2FieldName(String fieldName) {
      return (Map)this.fkField2symColumn2FieldName.get(fieldName);
   }

   public boolean isSymmetricField(String fieldName) {
      return this.fkField2symColumns.get(fieldName) != null;
   }

   public Class getCmrFieldClass(String fieldName) {
      return (Class)this.fieldName2class.get(fieldName);
   }

   public boolean isDeclaredField(String fieldName) {
      return this.declaredFieldNames.contains(fieldName);
   }

   public boolean isForeignKeyField(String fieldName) {
      return this.fkFieldNames.contains(fieldName);
   }

   public boolean isForeignPrimaryKeyField(String fieldName) {
      return this.fkPkFieldNames.contains(fieldName);
   }

   public boolean isForeignCmpField(String fieldName) {
      return this.fkCmpFieldNames.contains(fieldName);
   }

   public boolean isOneToOneRelation(String fieldName) {
      return this.one2OneSet.contains(fieldName);
   }

   public boolean isOneToManyRelation(String fieldName) {
      return this.one2ManySet.contains(fieldName);
   }

   public boolean isManyToManyRelation(String fieldName) {
      return this.many2ManySet.contains(fieldName);
   }

   public boolean isBiDirectional(String fieldName) {
      return this.biDirectional.contains(fieldName);
   }

   public boolean isCascadeDelete(String fieldName) {
      return (Boolean)this.fieldName2cascadeDelete.get(fieldName);
   }

   public boolean isCascadeDelete() {
      Iterator allFields = this.getCmrFieldNames().iterator();

      do {
         if (!allFields.hasNext()) {
            return false;
         }
      } while(!this.isCascadeDelete((String)allFields.next()));

      return true;
   }

   public boolean isDBCascadeDelete(String fieldName) {
      return (Boolean)this.fieldName2DBCascadeDelete.get(fieldName);
   }

   public String getRemoteColumn(String fieldName) {
      assert this.fieldName2remoteColumn.get(fieldName) != null;

      return (String)this.fieldName2remoteColumn.get(fieldName);
   }

   public String getRelatedFieldName(String fieldName) {
      return (String)this.fieldName2RelatedFieldName.get(fieldName);
   }

   public String getRelatedPkFieldName(String fieldName, String colName) {
      assert this.isForeignKeyField(fieldName);

      assert this.fkField2fkColumn2FieldName.get(fieldName) != null;

      assert ((Map)this.fkField2fkColumn2FieldName.get(fieldName)).get(colName) != null;

      return (String)((Map)this.fkField2fkColumn2FieldName.get(fieldName)).get(colName);
   }

   public CMPBeanDescriptor getRelatedDescriptor(String fieldName) {
      assert this.fieldName2relatedDescriptor.get(fieldName) != null;

      return (CMPBeanDescriptor)this.fieldName2relatedDescriptor.get(fieldName);
   }

   public RDBMSBean getRelatedRDBMSBean(String fieldName) {
      assert this.fieldName2relatedRDBMSBean.get(fieldName) != null;

      return (RDBMSBean)this.fieldName2relatedRDBMSBean.get(fieldName);
   }

   public Set getAllCmrFields() {
      return this.fieldName2relatedRDBMSBean.keySet();
   }

   public String getRelatedMultiplicity(String fieldName) {
      return (String)this.fieldName2relatedMultiplicity.get(fieldName);
   }

   public boolean relatedFieldIsFkOwner(String fieldName) {
      assert this.fieldName2relatedFieldOwnsFk.get(fieldName) != null;

      return (Boolean)this.fieldName2relatedFieldOwnsFk.get(fieldName);
   }

   public String getRelatedBeanClassName(String fieldName) {
      assert this.fieldName2relatedClassName.get(fieldName) != null;

      return (String)this.fieldName2relatedClassName.get(fieldName);
   }

   public String getJoinTableName(String fieldName) {
      assert this.fieldName2tableName.get(fieldName) != null;

      return (String)this.fieldName2tableName.get(fieldName);
   }

   public String getGroupName(String fieldName) {
      return (String)this.fieldName2groupName.get(fieldName);
   }

   public Set getTableNamesForGroup(String groupName) {
      return (Set)this.groupName2tableNames.get(groupName);
   }

   public boolean containsFkField(String fkField) {
      assert this.isForeignKeyField(fkField);

      boolean case1 = this.isOneToOneRelation(fkField);
      boolean case2 = this.isOneToManyRelation(fkField) && this.getRelatedMultiplicity(fkField).equals("One");
      return case1 || case2;
   }

   public String findByPrimaryKeyQuery() {
      StringBuffer query = new StringBuffer();
      query.append("SELECT OBJECT(bean) ");
      query.append("FROM ").append(this.getAbstractSchemaName());
      query.append(" AS bean ");
      query.append("WHERE ");
      int paramNum = 1;
      Iterator pkFields = this.bd.getPrimaryKeyFieldNames().iterator();

      assert pkFields.hasNext();

      while(pkFields.hasNext()) {
         String field = (String)pkFields.next();

         assert field != null;

         String subQuery = " ( bean." + field + " = ?" + paramNum++ + " ) ";
         query.append(subQuery);
         if (pkFields.hasNext()) {
            query.append(" AND ");
         }
      }

      return query.toString();
   }

   private String findByForeignKeyQuery(String fkField) {
      StringBuffer query = new StringBuffer();
      Set relPkFields = new TreeSet();
      Map relPkFieldMap = new HashMap();
      Iterator fkColumns = this.getForeignKeyColNames(fkField).iterator();

      while(fkColumns.hasNext()) {
         String col = (String)fkColumns.next();
         String relPkField = this.getRelatedPkFieldName(fkField, col);
         relPkFields.add(relPkField);
         relPkFieldMap.put(relPkField, col);
      }

      query.append("SELECT OBJECT(bean) ");
      query.append("FROM ").append(this.getAbstractSchemaName());
      query.append(" AS bean ");
      query.append("WHERE (");
      int paramNum = 1;
      Iterator relPkFieldsIter = relPkFields.iterator();

      while(relPkFieldsIter.hasNext()) {
         String relPkField = (String)relPkFieldsIter.next();
         String col = (String)relPkFieldMap.get(relPkField);
         if (debugLogger.isDebugEnabled()) {
            debug("processing foreign key column: " + col);
         }

         query.append("bean.");
         query.append(this.variableForField(fkField, this.getTableForCmrField(fkField), col) + " = ?" + paramNum++);
         if (relPkFieldsIter.hasNext()) {
            query.append(" AND ");
         }
      }

      query.append(")");
      if (debugLogger.isDebugEnabled()) {
         debug("relation Finder query: " + query);
      }

      return query.toString();
   }

   public void createRelationFinders() {
      if (debugLogger.isDebugEnabled()) {
         debug("createRelationFinders() called...");
      }

      this.relFinders = new ArrayList();
      Iterator allFields = this.getCmrFieldNames().iterator();
      if (debugLogger.isDebugEnabled()) {
         debug(allFields.hasNext() ? "have a field" : "no fields");
      }

      while(true) {
         String field;
         do {
            do {
               if (!allFields.hasNext()) {
                  return;
               }

               field = (String)allFields.next();
               if (debugLogger.isDebugEnabled()) {
                  debug("processing field: " + field);
               }
            } while(this.isRemoteField(field));
         } while(!this.isOneToOneRelation(field) && !this.isOneToManyRelation(field));

         String query = null;
         if (this.relatedFieldIsFkOwner(field)) {
            query = this.findByPrimaryKeyQuery();
         } else {
            query = this.findByForeignKeyQuery(field);
         }

         EjbqlFinder finder = null;
         RDBMSBean rrb = this.getRelatedRDBMSBean(field);
         CMPBeanDescriptor relBd = this.getRelatedDescriptor(field);
         String relField = this.getRelatedFieldName(field);

         try {
            finder = new EjbqlFinder(this.finderMethodName(relBd, relField), query);
            finder.setIncludeUpdates(false);
            finder.parseExpression();
         } catch (Exception var9) {
            throw new AssertionError(StackTraceUtilsClient.throwable2StackTrace(var9));
         }

         finder.setModifierString("public ");
         Class relClass = rrb.getCmrFieldClass(relField);

         assert relClass != null;

         if (Collection.class.isAssignableFrom(relClass)) {
            finder.setReturnClassType(Collection.class);
         } else {
            finder.setReturnClassType(relClass);
         }

         finder.setExceptionClassTypes(new Class[]{Exception.class});
         finder.setKeyFinder(true);
         if (this.relatedFieldIsFkOwner(field)) {
            finder.setParameterClassTypes(new Class[]{this.bd.getPrimaryKeyClass()});
            finder.setKeyBean(this);
         } else {
            finder.setParameterClassTypes(new Class[]{relBd.getPrimaryKeyClass()});
            finder.setKeyBean(rrb);
         }

         finder.setFinderLoadsBean(this.bd.getFindersLoadBean());
         finder.setRDBMSBean(this);
         finder.setGroupName(this.getGroupName(field));
         finder.setIsGeneratedRelationFinder(true);
         this.relFinders.add(finder);
         if (this.isCascadeDelete(field)) {
            finder.setCachingName(this.getRelationshipCachingForField(field));
         }

         this.cmrMapeedRelationFinder.put(field, finder);
      }
   }

   public Finder getRelatedFinder(String cmrField) {
      return (Finder)this.cmrMapeedRelationFinder.get(cmrField);
   }

   public String getRelationshipCachingForField(String field) {
      Iterator var2 = this.relationshipCachings.iterator();

      while(var2.hasNext()) {
         RelationshipCaching rc = (RelationshipCaching)var2.next();
         Iterator var4 = rc.getCachingElements().iterator();

         while(var4.hasNext()) {
            RelationshipCaching.CachingElement ce = (RelationshipCaching.CachingElement)var4.next();
            if (ce.getCmrField() != null && ce.getCmrField().equals(field)) {
               return rc.getCachingName();
            }
         }
      }

      return null;
   }

   public void createRelationFinders2() {
      if (debugLogger.isDebugEnabled()) {
         debug("createRelationFinders2() called...");
      }

      Iterator fkFields = this.getForeignKeyFieldNames().iterator();
      if (debugLogger.isDebugEnabled()) {
         debug(fkFields.hasNext() ? "have a foreign key" : "no foreign keys");
      }

      while(fkFields.hasNext()) {
         String fkField = (String)fkFields.next();
         if (debugLogger.isDebugEnabled()) {
            debug("processing foreign key field: " + fkField);
         }

         if (this.isManyToManyRelation(fkField) && !this.isRemoteField(fkField)) {
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT OBJECT(bean." + fkField + ") ");
            sb.append("FROM ").append(this.getAbstractSchemaName());
            sb.append(" AS bean ");
            String query = sb.toString();
            if (debugLogger.isDebugEnabled()) {
               debug("relation Finder query: " + query);
            }

            EjbqlFinder finder = null;

            try {
               String methodName = this.finderMethodName(this.bd, fkField);
               finder = new EjbqlFinder(methodName, query);
               finder.setIncludeUpdates(false);
               finder.parseExpression();
            } catch (Exception var7) {
               throw new AssertionError(StackTraceUtilsClient.throwable2StackTrace(var7));
            }

            finder.setParameterClassTypes(new Class[0]);
            finder.setModifierString("public ");
            finder.setReturnClassType(Collection.class);
            finder.setExceptionClassTypes(new Class[]{Exception.class});
            finder.setIsSelectInEntity(true);
            finder.setQueryType(4);
            finder.setFinderLoadsBean(this.bd.getFindersLoadBean());
            finder.setRDBMSBean(this);
            finder.setIsGeneratedRelationFinder(true);
            this.relFinders.add(finder);
         }
      }

   }

   public List getRelationFinderList() {
      if (this.relFinders == null) {
         this.relFinders = new ArrayList();
      }

      return this.relFinders;
   }

   public Iterator getRelationFinders() {
      return this.getRelationFinderList().iterator();
   }

   public void cleanup() {
      this.dataSourceName = null;
      this.finderList = null;
      this.cmpFieldNames = null;
      this.cmpFieldClasses = null;
      this.cmpColumnNames = null;
      this.cmpFieldName2groupName = null;
      this.fieldGroups = null;
      this.relationshipCachings = null;
      this.rdbmsFinders = null;
      this.dbmsDefaultValueFields = null;
      this.tableName2verifyRows = null;
      this.tableName2optimisticColumnTrigger = null;
      this.tableName2versionColumnInitialValue = null;
      this.table2cmpf2columnNoPKs = null;
      this.cmpf2Table = null;
      this.pkCmpF2Table2Column = null;
      this.column2tables = null;
      this.cmrf2table = null;
      this.cmrfHasMultiPkTable = null;
      this.cmrf2pkTable2fkColumn2pkColumn = null;
      this.fkField2pkTable2symFkColumn2pkColumn = null;
      this.variableName2table = null;
      this.fkField2fkColumn2Class = null;
      this.one2OneSet = null;
      this.one2ManySet = null;
      this.many2ManySet = null;
      this.biDirectional = null;
      this.fieldName2cascadeDelete = null;
      this.fieldName2DBCascadeDelete = null;
      this.fieldName2selfRel = null;
      this.fieldName2RelatedFieldName = null;
      this.fkField2fkColumn2FieldName = null;
      this.fkField2symColumn2FieldName = null;
      this.fieldName2relatedDescriptor = null;
      this.fieldName2relatedMultiplicity = null;
      this.fieldName2relatedFieldOwnsFk = null;
      this.fieldName2relatedClassName = null;
      this.fieldName2groupName = null;
      this.variableName2columnName = null;
      this.cmrFieldNames = null;
      this.declaredFieldNames = null;
      this.fkFieldNames = null;
      this.fkPkFieldNames = null;
      this.fkCmpFieldNames = null;
      this.fieldName2class = null;
      this.fkField2symColumns = null;
      this.fieldName2relatedRDBMSBean = null;
      this.fieldName2tableName = null;
      this.groupName2tableNames = null;
      this.fieldName2entityRef = null;
      this.remoteFieldNames = null;
      this.fieldName2remoteColumn = null;
      this.cmrMappedcmpFields = null;
      if (!this.hasSqlFinder) {
         this.table2cmrf = null;
         this.fkField2fkColumns = null;
         this.columnName2fieldName = null;
         this.tableName2cmpFieldName2columnName = null;
      }

   }

   public void setupAutoKeyGen() throws RDBMSException {
      if (this.hasAutoKeyGeneration()) {
         this.setGenKeyPKField();
         if (debugLogger.isDebugEnabled()) {
            debug(" AutoKey Generation is ON");
         }

         Loggable l;
         switch (this.getGenKeyType()) {
            case 1:
               this.genKeyBeforeInsert = false;
               this.genKeyExcludePKColumn = true;
               this.genKeyDefaultColumnVal = null;
               if (this.databaseType == 2) {
                  this.genKeyWLGeneratorQuery = "SELECT @@IDENTITY";
               } else if (this.databaseType == 7) {
                  this.genKeyWLGeneratorQuery = "SELECT SCOPE_IDENTITY()";
               }

               if (debugLogger.isDebugEnabled()) {
                  debug(" Generated Key Query: " + this.genKeyWLGeneratorQuery);
               }

               return;
            case 2:
               if (this.genKeyGeneratorName == null) {
                  l = EJBLogger.logBadAutoKeyGeneratorNameLoggable("Sequence", "database Sequence");
                  throw new RDBMSException(l.getMessageText());
               }

               this.genKeyBeforeInsert = true;
               this.genKeyExcludePKColumn = false;
               this.genKeyDefaultColumnVal = null;
               if (debugLogger.isDebugEnabled()) {
                  debug(" Generated Key Query: for Oracle, this is deferred to deployment time");
               }

               return;
            case 3:
               this.genKeyBeforeInsert = true;
               this.genKeyExcludePKColumn = false;
               this.genKeyDefaultColumnVal = null;
               if (this.genKeyGeneratorName == null) {
                  l = EJBLogger.logBadAutoKeyGeneratorNameLoggable("SequenceTable", "database sequence table");
                  throw new RDBMSException(l.getMessageText());
               }

               this.perhapsSetGenKeyCacheDefault();
               this.genKeyWLGeneratorQuery = "SELECT SEQUENCE FROM " + this.genKeyGeneratorName;
               this.genKeyWLGeneratorUpdatePrefix = "UPDATE " + this.genKeyGeneratorName + " SET SEQUENCE = SEQUENCE + ";
               if (debugLogger.isDebugEnabled()) {
                  debug(" Generated Key Query:  " + this.genKeyWLGeneratorQuery);
               }

               if (debugLogger.isDebugEnabled()) {
                  debug(" Generated Key Update: " + this.genKeyWLGeneratorUpdatePrefix);
               }

               return;
            default:
               throw new AssertionError("Unknown auto-key generator for " + this.ejbName);
         }
      }
   }

   String getOracleSequenceGeneratorQuery(String sequenceName) {
      return "SELECT " + sequenceName + ".nextval FROM DUAL";
   }

   String getInformixSequenceGeneratorQuery(String sequenceName) {
      return "SELECT " + sequenceName + ".nextval FROM SYSTABLES WHERE tabid=1";
   }

   String getDB2SequenceGeneratorQuery(String sequenceName) {
      return "SELECT NEXTVAL for " + sequenceName + " FROM SYSIBM.SYSDUMMY1";
   }

   private void setGenKeyPKField() throws RDBMSException {
      Iterator pkFields = this.bd.getPrimaryKeyFieldNames().iterator();

      assert pkFields.hasNext();

      this.genKeyPKField = (String)pkFields.next();
      if (this.isForeignPrimaryKeyField(this.genKeyPKField)) {
         Loggable l = EJBLogger.logAutoKeyCannotBePartOfFKLoggable();
         throw new RDBMSException(l.getMessageText());
      } else {
         if (debugLogger.isDebugEnabled()) {
            debug(" Gen Key PK Field: " + this.genKeyPKField);
         }

         Class c = this.getCmpFieldClass(this.genKeyPKField);
         if (!c.equals(Integer.class) && !c.equals(Integer.TYPE)) {
            if (!c.equals(Long.class) && !c.equals(Long.TYPE)) {
               throw new AssertionError("invalid pk class: " + c.getName());
            }

            this.genKeyPKFieldClassType = 1;
         } else {
            this.genKeyPKFieldClassType = 0;
         }

      }
   }

   public boolean equals(Object other) {
      if (!(other instanceof RDBMSBean)) {
         return false;
      } else {
         RDBMSBean otherBean = (RDBMSBean)other;
         if (!this.dataSourceName.equals(otherBean.getDataSourceName())) {
            return false;
         } else {
            return this.finderList.equals(otherBean.getFinderList());
         }
      }
   }

   public String toString() {
      StringBuffer buf = new StringBuffer(150);
      buf.append("[weblogic.cmp.rdbms.RDBMSBean {");
      buf.append("\n\tname = " + this.ejbName);
      buf.append("\n\tdata source = " + this.dataSourceName);
      buf.append("\n\ttableNames = " + this.getTables());
      buf.append("\n\tfinderList = " + this.finderList);
      buf.append("\n\tField Name to Column Name = ");
      if (this.fieldName2columnName != null) {
         Iterator var2 = this.fieldName2columnName.entrySet().iterator();

         while(var2.hasNext()) {
            Map.Entry entry = (Map.Entry)var2.next();
            buf.append("\n\t\tfield- " + (String)entry.getKey() + " column- " + (String)entry.getValue());
         }
      }

      buf.append("\n} end RDBMSBean ]\n");
      return buf.toString();
   }

   public int hashCode() {
      return this.dataSourceName.hashCode() ^ this.getTableName().hashCode();
   }

   private void perhapsSetGenKeyCacheDefault() {
      if (this.genKeyCacheSize <= 0) {
         this.genKeyCacheSize = 10;
      }

   }

   public static void deleteDefaultDbmsTableDdlFile(String ddlFileName) {
      try {
         File file = new File(ddlFileName);
         file.delete();
      } catch (Exception var2) {
         EJBLogger.logUnableToDeleteDDLFile(ddlFileName);
      }

   }

   public void addTableDefToDDLFile() {
      try {
         StringBuffer sb = new StringBuffer();
         Iterator var2 = this.getTables().iterator();

         String joinTableName;
         while(var2.hasNext()) {
            joinTableName = (String)var2.next();
            this.createDefaultDBMSTable(RDBMSUtils.escQuotedID(joinTableName), sb);
         }

         var2 = this.getJoinTableMap().values().iterator();

         while(var2.hasNext()) {
            joinTableName = (String)var2.next();
            this.createDefaultDBMSTable(joinTableName, sb);
         }

         this.writeToDDLFile(sb.toString());
      } catch (Exception var4) {
         EJBLogger.logUnableToWriteToDDLFile(this.ddlFileName);
      }

   }

   private void writeToDDLFile(String tableCreationScript) {
      BufferedWriter bw = null;

      try {
         bw = new BufferedWriter(new FileWriter(this.ddlFileName, true));
         bw.write(tableCreationScript + "\n\n");
         bw.flush();
      } catch (Exception var12) {
         EJBLogger.logUnableToCreateDDLFile(var12);
      } finally {
         try {
            if (bw != null) {
               bw.close();
            }
         } catch (IOException var11) {
         }

      }

   }

   private void createDefaultDBMSTable(String tableName, StringBuffer sb) throws Exception {
      sb.append("\n DROP TABLE " + tableName + "\n");
      sb.append("\nCREATE TABLE " + tableName + " (");
      this.addBeanOrJoinTableColumns(tableName, sb);
      sb.append(" )\n");
   }

   private void addBeanOrJoinTableColumns(String tableName, StringBuffer sb) throws Exception {
      Set pkCols = new HashSet();
      if (this.isJoinTable(tableName)) {
         this.addJoinTableColumns(tableName, sb, pkCols);
      } else {
         this.addBeanTableColumns(tableName, sb, pkCols);
      }

      if (pkCols.size() > 0) {
         sb.append(",");
         if (this.databaseType == 2 || this.databaseType == 7) {
            sb.append(" CONSTRAINT pk_" + tableName);
         }

         sb.append(" PRIMARY KEY (");
         Iterator it = pkCols.iterator();

         while(it.hasNext()) {
            sb.append((String)it.next());
            if (it.hasNext()) {
               sb.append(", ");
            }
         }

         sb.append(")");
      }

   }

   private void addJoinTableColumns(String tableName, StringBuffer sb, Set pkCols) throws Exception {
      if (debugLogger.isDebugEnabled()) {
         debug(" createDefaultDBMSTable: processing Join Table: " + tableName);
      }

      String cmrField = this.getCmrFieldForJoinTable(tableName);
      if (null == cmrField) {
         throw new RDBMSException(" Bean: " + this.ejbName + ", could not get cmrField for Join Table " + tableName);
      } else {
         Map colMap = this.getFkColumn2ClassMapForFkField(cmrField);
         if (null == colMap) {
            throw new RDBMSException(" Bean: " + this.ejbName + ", could not get Column To Class Map for FK Field " + cmrField);
         } else {
            List joinColNames = new ArrayList();
            Iterator it = colMap.entrySet().iterator();

            Map.Entry remotePkClass;
            String relatedCMRfield;
            Class cl;
            while(it.hasNext()) {
               remotePkClass = (Map.Entry)it.next();
               relatedCMRfield = (String)remotePkClass.getKey();
               cl = (Class)remotePkClass.getValue();
               sb.append(relatedCMRfield + " ");
               sb.append(this.getDefaultDBMSColType(cl));
               if (this.databaseType == 2 || this.databaseType == 7) {
                  sb.append(" NOT NULL ");
               }

               sb.append(", ");
               pkCols.add(relatedCMRfield);
               joinColNames.add(relatedCMRfield);
            }

            if (this.isRemoteField(cmrField)) {
               if (debugLogger.isDebugEnabled()) {
                  debug(" Do REMOTE RHS of Join Table ");
               }

               remotePkClass = null;
               relatedCMRfield = this.getRemoteColumn(cmrField);
               sb.append(relatedCMRfield + " ");
               cl = remotePkClass;
               if (!this.isValidSQLType(remotePkClass) && Serializable.class.isAssignableFrom(remotePkClass)) {
                  byte[] b = new byte[0];
                  cl = b.getClass();
               }

               sb.append(this.getDefaultDBMSColType(cl));
               pkCols.add(relatedCMRfield);
            } else {
               String otherField;
               Class cl;
               if (this.isSymmetricField(cmrField)) {
                  if (debugLogger.isDebugEnabled()) {
                     debug(" Do Symmetric RHS of Join Table ");
                  }

                  Map m2 = this.getSymmetricColumn2FieldName(cmrField);
                  if (null == m2) {
                     throw new RDBMSException(" Bean: " + this.ejbName + ", could not get Symmetric Column To Class Map for FK Field " + cmrField);
                  }

                  Iterator var16 = m2.entrySet().iterator();

                  while(var16.hasNext()) {
                     Map.Entry entry = (Map.Entry)var16.next();
                     String colName = (String)entry.getKey();
                     if (!joinColNames.contains(colName)) {
                        sb.append(colName + " ");
                        otherField = (String)entry.getValue();
                        cl = this.getCmpFieldClass(otherField);
                        sb.append(this.getDefaultDBMSColType(cl));
                        pkCols.add(colName);
                        if (it.hasNext()) {
                           sb.append(", ");
                        }
                     }
                  }
               } else {
                  if (debugLogger.isDebugEnabled()) {
                     debug(" Do Normal non-Remote non-Symmetric RHS of Join Table ");
                  }

                  RDBMSBean relatedBean = this.getRelatedRDBMSBean(cmrField);
                  relatedCMRfield = this.getRelatedFieldName(cmrField);
                  colMap = relatedBean.getFkColumn2ClassMapForFkField(relatedCMRfield);
                  if (null == colMap) {
                     throw new RDBMSException(" Bean: " + relatedBean.getEjbName() + ", could not get Column To Class Map for FK Field " + relatedCMRfield);
                  }

                  Iterator var19 = colMap.entrySet().iterator();

                  while(var19.hasNext()) {
                     Map.Entry entry = (Map.Entry)var19.next();
                     otherField = (String)entry.getKey();
                     if (!joinColNames.contains(otherField)) {
                        cl = (Class)entry.getValue();
                        sb.append(otherField + " ");
                        sb.append(this.getDefaultDBMSColType(cl));
                        pkCols.add(otherField);
                        if (it.hasNext()) {
                           sb.append(", ");
                        }
                     }
                  }
               }
            }

         }
      }
   }

   private void addBeanTableColumns(String tableName, StringBuffer sb, Set pkCols) throws Exception {
      if (debugLogger.isDebugEnabled()) {
         debug(" createDefaultDBMSTable: processing Bean Table: " + tableName);
      }

      List pkFields = this.getPrimaryKeyFields();
      Set colsAdded = new HashSet();
      Map field2Column = this.getCmpField2ColumnMap(tableName);
      Iterator it = field2Column.entrySet().iterator();

      while(true) {
         String field;
         String fkField;
         do {
            if (!it.hasNext()) {
               List cmrFields = this.getCmrFields(tableName);
               if (cmrFields != null) {
                  Iterator var16 = cmrFields.iterator();

                  label63:
                  while(true) {
                     do {
                        if (!var16.hasNext()) {
                           return;
                        }

                        fkField = (String)var16.next();
                     } while(!this.containsFkField(fkField));

                     Iterator fkColumns = this.getForeignKeyColNames(fkField).iterator();
                     Map fkColumn2Class = this.getFkColumn2ClassMapForFkField(fkField);

                     while(true) {
                        do {
                           String fkColumn;
                           do {
                              do {
                                 if (!fkColumns.hasNext()) {
                                    continue label63;
                                 }

                                 fkColumn = (String)fkColumns.next();
                              } while(colsAdded.contains(fkColumn));

                              sb.append(", ");
                              colsAdded.add(fkColumn);
                              sb.append(fkColumn + " ");
                              Class cl = (Class)fkColumn2Class.get(fkColumn);
                              sb.append(this.getDefaultDBMSColType(cl));
                           } while(!pkFields.contains(fkField));

                           pkCols.add(fkColumn);
                        } while(this.databaseType != 2 && this.databaseType != 7);

                        sb.append(" NOT NULL ");
                     }
                  }
               }

               return;
            }

            Map.Entry entry = (Map.Entry)it.next();
            field = (String)entry.getKey();
            fkField = (String)entry.getValue();
         } while(colsAdded.contains(fkField));

         colsAdded.add(fkField);
         sb.append(fkField + " ");
         Class cl = this.getCmpFieldClass(field);
         sb.append(this.getDefaultDBMSColType(cl));
         if (pkFields.contains(field)) {
            pkCols.add(fkField);
            if (this.databaseType == 2 || this.databaseType == 7 || this.databaseType == 4 || this.databaseType == 9) {
               sb.append(" NOT NULL ");
            }
         }

         if (it.hasNext()) {
            sb.append(", ");
         }
      }
   }

   public void setCharArrayIsSerializedToBytes(boolean val) {
      this.charArrayIsSerializedToBytes = val;
   }

   public void setDisableStringTrimming(boolean val) {
      this.disableStringTrimming = val;
   }

   public void setFindersReturnNulls(boolean val) {
      this.findersReturnNulls = val;
   }

   public boolean isFindersReturnNulls() {
      return this.findersReturnNulls;
   }

   public boolean isStringTrimmingEnabled() {
      return !this.disableStringTrimming;
   }

   public boolean isCharArrayMappedToString(Class type) {
      return type.isArray() && type.getComponentType() == Character.TYPE && !this.charArrayIsSerializedToBytes;
   }

   public String getDefaultDBMSColType(Class type) throws Exception {
      if (this.isCharArrayMappedToString(type)) {
         type = String.class;
      }

      return MethodUtils.getDefaultDBMSColType(type, this.databaseType);
   }

   public boolean isValidSQLType(Class type) {
      return ClassUtils.isValidSQLType(type) || this.isCharArrayMappedToString(type);
   }

   public void addSqlShape(SqlShape sqlShape) {
      if (this.sqlShapes == null) {
         this.sqlShapes = new HashMap();
      }

      this.sqlShapes.put(sqlShape.getSqlShapeName(), sqlShape);
   }

   public SqlShape getSqlShape(String name) {
      return this.sqlShapes == null ? null : (SqlShape)this.sqlShapes.get(name);
   }

   public Map getSqlShapes() {
      return this.sqlShapes;
   }

   public Map getRdbmsBeanMap() {
      return this.rdbmsBeanMap;
   }

   public EjbRelation getEjbRelation(String ejbRelationshipName) {
      return this.relationships == null ? null : this.relationships.getEjbRelation(ejbRelationshipName);
   }

   private static void debug(String s) {
      debugLogger.debug("[RDBMSBean] " + s);
   }

   public Class getBeanInterface() {
      try {
         if (this.generatedBeanInterface == null) {
            this.generatedBeanInterface = Thread.currentThread().getContextClassLoader().loadClass(this.bd.getGeneratedBeanInterfaceName());
         }
      } catch (ClassNotFoundException var2) {
         throw new AssertionError("Could not find generated bean interface name:" + this.bd.getGeneratedBeanInterfaceName());
      }

      return this.generatedBeanInterface;
   }

   public Method getBeanInterfaceMethod(Method m) {
      try {
         return this.getBeanInterface().getMethod(m.getName(), m.getParameterTypes());
      } catch (NoSuchMethodException var3) {
         throw new AssertionError("Could not find bean interface method:" + m.getName());
      }
   }

   public void computeAllTableColumns(Map tableName2columnName2columnName) {
      Iterator var2 = this.getTables().iterator();

      while(var2.hasNext()) {
         String tableName = (String)var2.next();
         Map columnName2columnName = new TreeMap(String.CASE_INSENSITIVE_ORDER);
         this.computeVariablesAndColumns(tableName, (List)null, (List)null, columnName2columnName);
         tableName2columnName2columnName.put(tableName, columnName2columnName);
      }

   }

   public void computeVariablesAndColumns(String tableName, List variableNames, List columnNames, Map columnName2columnName) {
      Map field2Column = this.getCmpField2ColumnMap(tableName);
      String dbmsColumn;
      if (field2Column != null) {
         Iterator var6 = field2Column.entrySet().iterator();

         while(var6.hasNext()) {
            Map.Entry entry = (Map.Entry)var6.next();
            dbmsColumn = (String)entry.getValue();

            assert dbmsColumn != null;

            if (variableNames != null) {
               variableNames.add(entry.getKey());
            }

            if (columnNames != null) {
               columnNames.add(dbmsColumn);
            }

            if (columnName2columnName != null) {
               columnName2columnName.put(dbmsColumn, dbmsColumn);
            }
         }
      }

      List cmrFields = this.getCmrFields(tableName);
      if (cmrFields != null) {
         Iterator var13 = cmrFields.iterator();

         while(var13.hasNext()) {
            dbmsColumn = (String)var13.next();
            Iterator fkColumns = this.getForeignKeyColNames(dbmsColumn).iterator();

            while(fkColumns.hasNext()) {
               String fkColumn = (String)fkColumns.next();
               if (variableNames != null) {
                  String variableName = this.variableForField(dbmsColumn, tableName, fkColumn);
                  if (!variableNames.contains(variableName)) {
                     variableNames.add(variableName);
                  }
               }

               if (columnNames != null && !columnNames.contains(fkColumn)) {
                  columnNames.add(fkColumn);
               }

               if (columnName2columnName != null && !columnName2columnName.containsKey(fkColumn)) {
                  columnName2columnName.put(fkColumn, fkColumn);
               }
            }
         }
      }

   }

   public boolean isUseInnerJoin() {
      return this.useInnerJoin;
   }

   public void setUseInnerJoin(boolean useInnerJoin) {
      this.useInnerJoin = useInnerJoin;
   }

   public String getCategoryCmpField() {
      return this.categoryCmpField;
   }

   public void setCategoryCmpField(String cmpField) {
      this.categoryCmpField = cmpField;
   }

   static {
      debugLogger = EJBDebugService.cmpDeploymentLogger;
   }
}
