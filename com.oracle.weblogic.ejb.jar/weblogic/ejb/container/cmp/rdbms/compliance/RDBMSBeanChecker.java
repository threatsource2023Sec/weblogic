package weblogic.ejb.container.cmp.rdbms.compliance;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.cmp.rdbms.FieldGroup;
import weblogic.ejb.container.cmp.rdbms.RDBMSBean;
import weblogic.ejb.container.cmp.rdbms.RDBMSRelation;
import weblogic.ejb.container.cmp.rdbms.RelationshipCaching;
import weblogic.ejb.container.cmp.rdbms.SqlShape;
import weblogic.ejb.container.cmp.rdbms.finders.RDBMSFinder;
import weblogic.ejb.container.compliance.BaseComplianceChecker;
import weblogic.ejb.container.compliance.Log;
import weblogic.ejb.container.dd.DDConstants;
import weblogic.ejb.container.dd.xml.DDUtils;
import weblogic.ejb.container.interfaces.EntityBeanQuery;
import weblogic.ejb.container.persistence.PersistenceUtils;
import weblogic.ejb.container.persistence.spi.CMPBeanDescriptor;
import weblogic.ejb.container.persistence.spi.CmrField;
import weblogic.ejb.container.persistence.spi.EjbRelation;
import weblogic.ejb.container.persistence.spi.EjbRelationshipRole;
import weblogic.ejb.container.persistence.spi.RoleSource;
import weblogic.ejb.container.utils.ClassUtils;
import weblogic.ejb.container.utils.MethodUtils;
import weblogic.ejb20.dd.DescriptorErrorInfo;
import weblogic.j2ee.descriptor.wl.WeblogicRdbmsJarBean;
import weblogic.j2ee.validation.ComplianceException;
import weblogic.utils.ErrorCollectionException;

public final class RDBMSBeanChecker extends BaseComplianceChecker {
   private static final Set RESERVED_WORDS = new HashSet();
   private final Map beanMap;
   private final Map relationMap;
   private final Map rdbmsBeanMap;
   private final Map rdbmsRelationMap;
   private final WeblogicRdbmsJarBean cmpDesc;
   private Set fieldNames = null;
   private Set cmrFieldNames = null;
   private CMPBeanDescriptor testCMPB = null;
   private final ErrorCollectionException errors = new ErrorCollectionException();

   RDBMSBeanChecker(Map beanM, Map relationMap, Map rdbmsBeanM, Map rdbmsRelationM, WeblogicRdbmsJarBean cmpDesc) {
      this.beanMap = beanM;
      this.relationMap = relationMap;
      this.rdbmsBeanMap = rdbmsBeanM;
      this.rdbmsRelationMap = rdbmsRelationM;
      this.cmpDesc = cmpDesc;
   }

   public void runComplianceCheck() throws ErrorCollectionException {
      Iterator beanIt = this.beanMap.values().iterator();

      while(beanIt.hasNext()) {
         this.testCMPB = (CMPBeanDescriptor)beanIt.next();
         this.computeFieldNames();

         try {
            this.checkEjBeanHasWLBean();
            this.checkNoExtraAbstractMethods();
            this.checkCMPFieldsForFieldMaps();
            this.checkMultiTableNoDupCMPFields();
            this.checkMultiTableAllHavePKFields();
            this.checkFieldGroupsHaveValidFields();
            this.checkRelationshipCachingRequireDatabaseType();
            this.checkDelayDatabaseInsertUntilConflictDelayUpdatesUntilEndOfTx();
            this.checkWeblogicQueriesHaveEjbQuery();
            this.checkQueriesHaveValidGroupNamesAndCachingNames();
            this.checkNoSqlSelectDistinctWithBlobClob();
            this.checkSupportedDatabaseForKeyGenerator();
            this.checkGenKeyPKIsIntegerOrLong();
            this.checkBlobClobSupportedDatabase();
            this.checkBatchOperations();
            this.checkOptimisticConcurrency();
            this.checkUseSelectForUpdate();
            this.checkValuesForTableAutoCreation();
            this.checkWLFindByPrimaryKey();
            this.checkTableAndColumnNames();
            this.checkFieldGroupsUnused();
            this.checkRelationshipCachesUnused();
            this.checkSqlShapeExists();
         } catch (Exception var3) {
            this.errors.add(var3);
         }
      }

      if (!this.errors.isEmpty()) {
         throw this.errors;
      }
   }

   private void computeFieldNames() {
      this.fieldNames = new HashSet();
      this.cmrFieldNames = new HashSet();
      Iterator var1 = this.relationMap.values().iterator();

      while(var1.hasNext()) {
         EjbRelation ejbRel = (EjbRelation)var1.next();
         Iterator roles = ejbRel.getAllEjbRelationshipRoles().iterator();
         EjbRelationshipRole role1 = (EjbRelationshipRole)roles.next();
         EjbRelationshipRole role2 = (EjbRelationshipRole)roles.next();
         this.perhapsAddCmrField(role1.getRoleSource(), role1.getCmrField());
         this.perhapsAddCmrField(role2.getRoleSource(), role2.getCmrField());
      }

      this.fieldNames.addAll(this.testCMPB.getCMFieldNames());
      this.fieldNames.addAll(this.cmrFieldNames);
   }

   private void perhapsAddCmrField(RoleSource src, CmrField field) {
      if (src.getEjbName().equals(this.testCMPB.getEJBName()) && field != null) {
         this.cmrFieldNames.add(field.getName());
      }

   }

   public static boolean validateCategoryFieldAvailible(RDBMSBean bean) {
      if (bean == null) {
         return false;
      } else {
         String cField = bean.getCategoryCmpField();
         if (cField != null) {
            boolean canHasCategoryCmpField = true;
            if (!bean.getCmpFieldNames().contains(cField)) {
               Log.getInstance().logInfo("The field " + cField + " specified in <category-cmp-field> of EJB " + bean.getEjbName() + " isn't a cmp field.");
               canHasCategoryCmpField = false;
            }

            if (!bean.isReadOnly()) {
               Log.getInstance().logInfo("The <category-cmp-field> " + cField + " should be specified on Readonly Entity Bean only. " + bean.getEjbName() + " isn't a Readonly Entity Bean");
               canHasCategoryCmpField = false;
            }

            return canHasCategoryCmpField;
         } else {
            return false;
         }
      }
   }

   private void checkSqlShapeExists() {
      String ejbName = this.testCMPB.getEJBName();
      RDBMSBean rdbmsBean = (RDBMSBean)this.rdbmsBeanMap.get(ejbName);
      Iterator var3 = rdbmsBean.getRdbmsFinders().values().iterator();

      while(var3.hasNext()) {
         RDBMSFinder finder = (RDBMSFinder)var3.next();
         String test = finder.getSqlShapeName();
         if (test != null && rdbmsBean.getSqlShape(test) == null) {
            StringBuilder candidates = new StringBuilder();
            if (rdbmsBean.getSqlShapes() != null) {
               Iterator shapes = rdbmsBean.getSqlShapes().values().iterator();

               while(shapes.hasNext()) {
                  SqlShape shape = (SqlShape)shapes.next();
                  candidates.append(shape.getSqlShapeName());
                  if (shapes.hasNext()) {
                     candidates.append(", ");
                  }
               }
            }

            this.errors.add(new ComplianceException(this.getFmt().SQL_SHAPE_DOES_NOT_EXIST(ejbName, finder.toString(), test, candidates.toString())));
         }

         if (finder.usesSql() && finder.getSqlQueries().isEmpty()) {
            this.errors.add(new ComplianceException(this.getFmt().SQL_QUERY_NOT_SPECIFIED(ejbName, finder.toString())));
         }
      }

   }

   private void checkTableAndColumnNames() {
      String ejbName = this.testCMPB.getEJBName();
      RDBMSBean rbean = (RDBMSBean)this.rdbmsBeanMap.get(ejbName);
      Iterator var3 = rbean.getTableName2CmpField2ColumnMap().entrySet().iterator();

      while(var3.hasNext()) {
         Map.Entry entry = (Map.Entry)var3.next();
         String tableName = (String)entry.getKey();
         if (RESERVED_WORDS.contains(tableName.toUpperCase(Locale.ENGLISH))) {
            this.errors.add(new ComplianceException(this.getFmt().RESERVED_WORD_USED_FOR_COLUMN_OR_TABLE(ejbName, tableName)));
         }

         Iterator var6 = ((Map)entry.getValue()).values().iterator();

         while(var6.hasNext()) {
            String columnName = (String)var6.next();
            if (RESERVED_WORDS.contains(columnName.toUpperCase(Locale.ENGLISH))) {
               this.errors.add(new ComplianceException(this.getFmt().RESERVED_WORD_USED_FOR_COLUMN_OR_TABLE(ejbName, columnName)));
            }
         }
      }

   }

   private void checkValuesForTableAutoCreation() throws ComplianceException {
      String autoCreateTables = this.cmpDesc.getCreateDefaultDbmsTables();
      if (autoCreateTables != null && !autoCreateTables.equalsIgnoreCase("DropAndCreate") && !autoCreateTables.equalsIgnoreCase("DropAndCreateAlways") && !autoCreateTables.equalsIgnoreCase("AlterOrCreate") && !autoCreateTables.equalsIgnoreCase("CreateOnly") && !autoCreateTables.equalsIgnoreCase("Disabled")) {
         throw new ComplianceException(this.getFmt().WRONG_VALUE_FOR_DBMS_TABLE());
      }
   }

   private void checkEjBeanHasWLBean() throws ComplianceException {
      String ejbName = this.testCMPB.getEJBName();
      if (!this.rdbmsBeanMap.containsKey(ejbName)) {
         throw new ComplianceException(this.getFmt().NO_MATCHING_BEAN(ejbName), new DescriptorErrorInfo("<entity>", ejbName, ejbName));
      }
   }

   private void checkSupportedDatabaseForKeyGenerator() throws ComplianceException {
      String ejbName = this.testCMPB.getEJBName();
      RDBMSBean rdbmsBean = (RDBMSBean)this.rdbmsBeanMap.get(ejbName);
      if (rdbmsBean.hasAutoKeyGeneration()) {
         if (rdbmsBean.getGenKeyType() == 2) {
            switch (rdbmsBean.getDatabaseType()) {
               case 1:
               case 3:
               case 4:
                  break;
               case 2:
               default:
                  throw new ComplianceException(this.getFmt().GENKEY_PK_SEQUENCE_WITH_UNSUPPORTED_DB(this.testCMPB.getEJBName(), DDConstants.getDBNameForType(rdbmsBean.getDatabaseType())), new DescriptorErrorInfo("<automatic-key-generation>", ejbName, ejbName));
            }
         }

         if (rdbmsBean.getGenKeyType() == 1 && rdbmsBean.getDatabaseType() == 1) {
            throw new ComplianceException(this.getFmt().GENKEY_PK_IDENTITY_WITH_UNSUPPORTED_DB(this.testCMPB.getEJBName(), DDConstants.getDBNameForType(rdbmsBean.getDatabaseType())), new DescriptorErrorInfo("<automatic-key-generation>", ejbName, ejbName));
         }
      }
   }

   private void checkGenKeyPKIsIntegerOrLong() throws ComplianceException {
      String ejbName = this.testCMPB.getEJBName();
      RDBMSBean rdbmsBean = (RDBMSBean)this.rdbmsBeanMap.get(ejbName);
      if (rdbmsBean.hasAutoKeyGeneration()) {
         CMPBeanDescriptor bd = (CMPBeanDescriptor)this.beanMap.get(rdbmsBean.getEjbName());
         if (bd.hasComplexPrimaryKey()) {
            if (bd.getPrimaryKeyFieldNames().size() == 1) {
               String pkField = (String)bd.getPrimaryKeyFieldNames().iterator().next();
               Class pkFieldClass = bd.getFieldClass(pkField);
               if (pkFieldClass.equals(Integer.class) || pkFieldClass.equals(Long.class) || pkFieldClass.equals(Integer.TYPE) || pkFieldClass.equals(Long.TYPE)) {
                  return;
               }
            }

            throw new ComplianceException(this.getFmt().GENKEY_PK_IS_INTEGER_OR_LONG(this.testCMPB.getEJBName()), new DescriptorErrorInfo("<automatic-key-generation>", ejbName, ejbName));
         } else {
            Iterator pkFields = bd.getPrimaryKeyFieldNames().iterator();
            String pkFieldName = (String)pkFields.next();
            String pkClassName = ClassUtils.classToJavaSourceType(bd.getFieldClass(pkFieldName));
            if (!pkClassName.equals("java.lang.Integer") && !pkClassName.equals("java.lang.Long")) {
               throw new ComplianceException(this.getFmt().GENKEY_PK_IS_INTEGER_OR_LONG(this.testCMPB.getEJBName()), new DescriptorErrorInfo("<automatic-key-generation>", ejbName, ejbName));
            } else if ((rdbmsBean.getGenKeyType() == 2 || rdbmsBean.getGenKeyType() == 3) && rdbmsBean.getGenKeyCacheSize() == 0) {
               throw new ComplianceException(this.getFmt().AUTO_PK_KEY_CACHE_SIZE_NOT_SPECIFIED(this.testCMPB.getEJBName()), new DescriptorErrorInfo("<automatic-key-generation>", ejbName, ejbName));
            }
         }
      }
   }

   private void checkBlobClobSupportedDatabase() throws ComplianceException {
      String ejbName = this.testCMPB.getEJBName();
      RDBMSBean rdbmsBean = (RDBMSBean)this.rdbmsBeanMap.get(ejbName);
      if (rdbmsBean.hasBlobClobColumn()) {
         switch (rdbmsBean.getDatabaseType()) {
            case 0:
               throw new ComplianceException(this.getFmt().BLOB_CLOB_WITH_UNKNOWN_DB(ejbName), new DescriptorErrorInfo("<dbms-column-type>", ejbName, ejbName));
            case 1:
            case 4:
            case 9:
               return;
            case 2:
            case 3:
            case 5:
            case 6:
            case 7:
            case 8:
            default:
               throw new ComplianceException(this.getFmt().BLOB_CLOB_WITH_UNSUPPORTED_DB(ejbName, DDConstants.getDBNameForType(rdbmsBean.getDatabaseType())), new DescriptorErrorInfo("<dbms-column-type>", ejbName, ejbName));
         }
      }
   }

   private void checkBatchOperations() {
      String ejbName = this.testCMPB.getEJBName();
      RDBMSBean rdbmsBean = (RDBMSBean)this.rdbmsBeanMap.get(ejbName);
      if (rdbmsBean.getEnableBatchOperations() || rdbmsBean.getOrderDatabaseOperations()) {
         if (rdbmsBean.hasAutoKeyGeneration() && rdbmsBean.getGenKeyType() == 1) {
            EJBLogger.logWarningBatchOperationOffForAutoKeyGen(ejbName, "Identity");
         }

      }
   }

   private void checkOptimisticConcurrency() throws ComplianceException {
      String ejbName = this.testCMPB.getEJBName();
      RDBMSBean rdbmsBean = (RDBMSBean)this.rdbmsBeanMap.get(ejbName);
      CMPBeanDescriptor bd = (CMPBeanDescriptor)this.beanMap.get(rdbmsBean.getEjbName());
      Iterator queries = rdbmsBean.getTables().iterator();

      while(true) {
         while(queries.hasNext()) {
            String table = (String)queries.next();
            String verifyColumns = rdbmsBean.getVerifyColumns(table);
            String verifyRows = rdbmsBean.getVerifyRows(table);
            String column = rdbmsBean.getOptimisticColumn(table);
            if (bd.isOptimistic()) {
               if (verifyColumns == null) {
                  throw new ComplianceException(this.getFmt().MISSING_VERIFY_COLUMNS(ejbName, table), new DescriptorErrorInfo("<verify-fields>", ejbName, table));
               }

               if (!verifyColumns.equalsIgnoreCase("read") && !verifyColumns.equalsIgnoreCase("modified") && !verifyColumns.equalsIgnoreCase("version") && !verifyColumns.equalsIgnoreCase("timestamp")) {
                  throw new ComplianceException(this.getFmt().ILLEGAL_VERIFY_COLUMNS(ejbName, table, verifyColumns), new DescriptorErrorInfo("<verify-fields>", ejbName, table));
               }

               if (verifyRows != null) {
                  if (!verifyRows.equalsIgnoreCase("read") && !verifyRows.equalsIgnoreCase("modified")) {
                     throw new ComplianceException(this.getFmt().ILLEGAL_VERIFY_ROWS(ejbName, table, verifyRows), new DescriptorErrorInfo("<verify-fields>", ejbName, table));
                  }

                  if (verifyRows.equalsIgnoreCase("read") && verifyColumns.equalsIgnoreCase("modified")) {
                     throw new ComplianceException(this.getFmt().ILLEGAL_VERIFY_READ_MODIFIED(ejbName, table), new DescriptorErrorInfo("<verify-fields>", ejbName, table));
                  }
               }

               if (verifyColumns.equalsIgnoreCase("version") || verifyColumns.equalsIgnoreCase("timestamp")) {
                  if (!rdbmsBean.hasOptimisticColumn(table)) {
                     throw new ComplianceException(this.getFmt().MISSING_OPTIMISTIC_COLUMN(ejbName, table, verifyColumns), new DescriptorErrorInfo("<optimistic-column>", ejbName, table));
                  }

                  String field = rdbmsBean.getCmpField(table, column);
                  if (field != null) {
                     Class clss = this.testCMPB.getFieldClass(field);
                     if (verifyColumns.equalsIgnoreCase("version")) {
                        if (!clss.equals(Long.class)) {
                           throw new ComplianceException(this.getFmt().VERSION_FIELD_WRONG_TYPE(ejbName, field, table, column, clss.getName()));
                        }
                     } else if (!clss.equals(Timestamp.class)) {
                        throw new ComplianceException(this.getFmt().TIMESTAMP_FIELD_WRONG_TYPE(ejbName, field, table, column, clss.getName()));
                     }
                  }
               }

               if (rdbmsBean.hasBlobClobColumn() && !verifyColumns.equalsIgnoreCase("version") && !verifyColumns.equalsIgnoreCase("timestamp")) {
                  EJBLogger.logWarningOptimisticBlobBeanHasNoVersionTimestamp(ejbName);
               }
            } else {
               if (verifyColumns != null) {
                  EJBLogger.logWarningNonOptimisticBeanUsesVerifyColumns(ejbName);
                  rdbmsBean.setVerifyColumns(table, (String)null);
               }

               if (verifyRows != null) {
                  EJBLogger.logWarningNonOptimisticBeanUsesVerifyRows(ejbName);
                  rdbmsBean.setVerifyRows(table, (String)null);
               }

               if (column != null) {
                  EJBLogger.logWarningNonOptimisticBeanUsesOptimisticColumn(ejbName);
                  rdbmsBean.setOptimisticColumn(table, (String)null);
               }
            }
         }

         if (bd.isOptimistic()) {
            if (rdbmsBean.getDatabaseType() != 1) {
               queries = this.testCMPB.getAllQueries().iterator();

               label100:
               while(true) {
                  EntityBeanQuery query;
                  RDBMSFinder rdbmsFinder;
                  do {
                     if (!queries.hasNext()) {
                        break label100;
                     }

                     query = (EntityBeanQuery)queries.next();
                     RDBMSFinder.FinderKey key = new RDBMSFinder.FinderKey(query.getMethodName(), query.getMethodParams());
                     rdbmsFinder = (RDBMSFinder)rdbmsBean.getRdbmsFinders().get(key);
                  } while(rdbmsFinder != null && !rdbmsFinder.getIncludeUpdates());

                  EJBLogger.logWarningOptimisticBeanUsesIncludeUpdate(ejbName, DDUtils.getMethodSignature(query.getMethodName(), query.getMethodParams()), DDConstants.getDBNameForType(rdbmsBean.getDatabaseType()));
               }
            }

            if (rdbmsBean.getUseSelectForUpdate()) {
               EJBLogger.logWarningOptimisticBeanUsesUseSelectForUpdate(ejbName);
               rdbmsBean.setUseSelectForUpdate(false);
            }

            if (rdbmsBean.isClusterInvalidationDisabled() && !rdbmsBean.getVerifyReads()) {
               EJBLogger.logWarningOCBeanIsVerifyModAndNoClustInvalidate(ejbName);
            }
         } else if (rdbmsBean.isClusterInvalidationDisabled() && !bd.isReadOnly()) {
            EJBLogger.logWarningNonOCOrROBeanDisablesClustInvalidate(ejbName);
            rdbmsBean.setClusterInvalidationDisabled(false);
         }

         return;
      }
   }

   private void checkUseSelectForUpdate() {
      String ejbName = this.testCMPB.getEJBName();
      RDBMSBean rdbmsBean = (RDBMSBean)this.rdbmsBeanMap.get(ejbName);
      if (rdbmsBean.getUseSelectForUpdate()) {
         CMPBeanDescriptor bd = (CMPBeanDescriptor)this.beanMap.get(rdbmsBean.getEjbName());
         if (bd.isReadOnly()) {
            EJBLogger.logWarningReadOnlyBeanUsesUseSelectForUpdate(ejbName);
         }

         if (bd.getConcurrencyStrategy() == 1) {
            EJBLogger.logWarningExclusiveBeanUsesUseSelectForUpdate(ejbName);
         }
      }

   }

   private void checkNoExtraAbstractMethods() {
      Set methodNames = new HashSet();
      Iterator iter = PersistenceUtils.getAbstractMethodCollection(this.testCMPB.getBeanClass()).iterator();

      while(iter.hasNext()) {
         Method method = (Method)iter.next();
         if (methodNames.contains(method.getName())) {
            this.errors.add(new ComplianceException(this.getFmt().OVERLOADED_ABSTRACT_METHOD(this.testCMPB.getEJBName(), method.getName())));
         }

         boolean ok = false;
         if (method.getName().startsWith("ejbSelect")) {
            ok = true;
         } else if (!method.getName().startsWith("get") && !method.getName().startsWith("set")) {
            ok = false;
         } else {
            methodNames.add(method.getName());
            String fieldName = MethodUtils.decapitalize(method.getName().substring(3));
            ok = this.fieldNames.contains(fieldName);
         }

         if (!ok) {
            this.errors.add(new ComplianceException(this.getFmt().EXTRA_ABSTRACT_METHOD(this.testCMPB.getEJBName(), DDUtils.getMethodSignature(method))));
         }
      }

   }

   private void checkCMPFieldsForFieldMaps() {
      String ejbName = this.testCMPB.getEJBName();
      RDBMSBean rdbmsBean = (RDBMSBean)this.rdbmsBeanMap.get(ejbName);
      CMPBeanDescriptor bd = (CMPBeanDescriptor)this.beanMap.get(rdbmsBean.getEjbName());
      Map rFieldName2ColName = rdbmsBean.getCmpFieldToColumnMap();
      Map rFieldName2ColTypeName = rdbmsBean.getCmpFieldToColumnTypeMap();
      Iterator cmfi = this.testCMPB.getCMFieldNames().iterator();

      String fName;
      while(cmfi.hasNext()) {
         fName = (String)cmfi.next();
         if (!rFieldName2ColName.containsKey(fName)) {
            this.errors.add(new ComplianceException(this.getFmt().NO_MATCHING_FIELD_MAP(ejbName, fName), new DescriptorErrorInfo("<field-map>", ejbName, fName)));
         }

         if (rFieldName2ColTypeName.containsKey(fName) && rdbmsBean.isClobCmpColumnTypeForField(fName)) {
            String fieldClassName = ClassUtils.classToJavaSourceType(bd.getFieldClass(fName));
            if (!fieldClassName.equals("java.lang.String")) {
               this.errors.add(new ComplianceException(this.getFmt().FIELDCLASSTYPE_MUST_BE_STRING_FOR_ORACLECLOB_COLUMNTYPE(ejbName, fName, fieldClassName), new DescriptorErrorInfo("<dbms-column-type>", ejbName, fName)));
            }
         }
      }

      cmfi = rFieldName2ColName.keySet().iterator();

      while(cmfi.hasNext()) {
         fName = (String)cmfi.next();
         if (!this.testCMPB.getCMFieldNames().contains(fName)) {
            this.errors.add(new ComplianceException(this.getFmt().NO_MATCHING_CMP_FIELD(ejbName, fName), new DescriptorErrorInfo("<cmp-field>", ejbName, fName)));
         }
      }

   }

   private void checkMultiTableNoDupCMPFields() {
      String ejbName = this.testCMPB.getEJBName();
      RDBMSBean rdbmsBean = (RDBMSBean)this.rdbmsBeanMap.get(ejbName);
      if (rdbmsBean.hasMultipleTables()) {
         CMPBeanDescriptor bd = (CMPBeanDescriptor)this.beanMap.get(rdbmsBean.getEjbName());
         Set pkFields = bd.getPrimaryKeyFieldNames();
         Iterator cmfi = this.testCMPB.getCMFieldNames().iterator();

         while(true) {
            String cmpField;
            do {
               if (!cmfi.hasNext()) {
                  return;
               }

               cmpField = (String)cmfi.next();
            } while(pkFields.contains(cmpField));

            int count = 0;
            StringBuilder dupTableNames = new StringBuilder();
            Iterator var9 = rdbmsBean.getTables().iterator();

            while(var9.hasNext()) {
               String tableName = (String)var9.next();
               if (rdbmsBean.getCmpField2ColumnMap(tableName).containsKey(cmpField)) {
                  dupTableNames.append((count > 0 ? ", " : "") + tableName);
                  ++count;
               }
            }

            if (count > 1) {
               this.errors.add(new ComplianceException(this.getFmt().NON_PK_CMP_FIELD_MAPPED_TO_MORE_THAN_ONE_TABLE(ejbName, cmpField, count, dupTableNames.toString()), new DescriptorErrorInfo("<cmp-field>", ejbName, cmpField)));
            }
         }
      }
   }

   private void checkMultiTableAllHavePKFields() {
      String ejbName = this.testCMPB.getEJBName();
      RDBMSBean rdbmsBean = (RDBMSBean)this.rdbmsBeanMap.get(ejbName);
      if (rdbmsBean.hasMultipleTables()) {
         CMPBeanDescriptor bd = (CMPBeanDescriptor)this.beanMap.get(rdbmsBean.getEjbName());
         Set pkFields = bd.getPrimaryKeyFieldNames();
         Iterator var5 = rdbmsBean.getTables().iterator();

         while(var5.hasNext()) {
            String tableName = (String)var5.next();
            Map m = rdbmsBean.getCmpField2ColumnMap(tableName);
            Iterator var8 = pkFields.iterator();

            while(var8.hasNext()) {
               String pkField = (String)var8.next();
               if (!m.containsKey(pkField)) {
                  this.errors.add(new ComplianceException(this.getFmt().MISSING_MULTITABLE_PK_FIELD_MAP(ejbName, tableName, pkField), new DescriptorErrorInfo("TableNameForOneCMP", ejbName, tableName)));
               }
            }
         }

      }
   }

   private void checkFieldGroupsHaveValidFields() {
      String ejbName = this.testCMPB.getEJBName();
      RDBMSBean rb = (RDBMSBean)this.rdbmsBeanMap.get(ejbName);
      Iterator var3 = rb.getFieldGroups().iterator();

      while(var3.hasNext()) {
         FieldGroup group = (FieldGroup)var3.next();
         Iterator var5 = group.getCmpFields().iterator();

         String fieldName;
         while(var5.hasNext()) {
            fieldName = (String)var5.next();
            if (!rb.hasCmpField(fieldName)) {
               this.errors.add(new ComplianceException(this.getFmt().GROUP_CONTAINS_UNDEFINED_CMP_FIELD(ejbName, group.getName(), fieldName), new DescriptorErrorInfo("<field-group>", ejbName, group.getName())));
            }
         }

         var5 = group.getCmrFields().iterator();

         while(var5.hasNext()) {
            fieldName = (String)var5.next();
            if (!this.cmrFieldNames.contains(fieldName)) {
               this.errors.add(new ComplianceException(this.getFmt().GROUP_CONTAINS_UNDEFINED_CMR_FIELD(ejbName, group.getName(), fieldName), new DescriptorErrorInfo("<field-group>", ejbName, group.getName())));
            }
         }
      }

   }

   private void checkRelationshipCachingRequireDatabaseType() {
      String ejbName = this.testCMPB.getEJBName();
      RDBMSBean rb = (RDBMSBean)this.rdbmsBeanMap.get(ejbName);
      if (rb.getRelationshipCachings().iterator().hasNext() && this.cmpDesc.getDatabaseType() == null) {
         this.errors.add(new ComplianceException(this.getFmt().RELATIONSHIP_CACHING_REQUIRE_DATABASETYPE(ejbName), new DescriptorErrorInfo("<relationship-caching>", ejbName, ejbName)));
      }

   }

   private void checkDelayDatabaseInsertUntilConflictDelayUpdatesUntilEndOfTx() {
      String ejbName = this.testCMPB.getEJBName();
      RDBMSBean rb = (RDBMSBean)this.rdbmsBeanMap.get(ejbName);
      CMPBeanDescriptor bd = (CMPBeanDescriptor)this.beanMap.get(rb.getEjbName());
      if (!bd.getBoxCarUpdates() && this.cmpDesc.isEnableBatchOperations() || !bd.getBoxCarUpdates() && this.cmpDesc.isOrderDatabaseOperations()) {
         this.errors.add(new ComplianceException(this.getFmt().DelayDatabaseInsertUntilConflictEnableBatchOperations(ejbName), new DescriptorErrorInfo("<delay-updates-until-end-of-tx>", ejbName, ejbName)));
      }

   }

   private void checkWeblogicQueriesHaveEjbQuery() {
      String ejbName = this.testCMPB.getEJBName();
      RDBMSBean rb = (RDBMSBean)this.rdbmsBeanMap.get(ejbName);
      Set queryKeys = new HashSet();
      Iterator queryIter = this.testCMPB.getAllQueries().iterator();

      while(queryIter.hasNext()) {
         EntityBeanQuery query = (EntityBeanQuery)queryIter.next();
         RDBMSFinder.FinderKey finderKey = new RDBMSFinder.FinderKey(query.getMethodName(), query.getMethodParams());
         queryKeys.add(finderKey);
      }

      queryKeys.add(new RDBMSFinder.FinderKey("findByPrimaryKey", new String[]{this.testCMPB.getPrimaryKeyClassName()}));
      queryIter = rb.getRdbmsFinders().keySet().iterator();

      while(queryIter.hasNext()) {
         RDBMSFinder.FinderKey wlQueryKey = (RDBMSFinder.FinderKey)queryIter.next();
         if (!queryKeys.contains(wlQueryKey)) {
            EJBLogger.logWarningWeblogicQueryHasNoMatchingEjbQuery(ejbName, DDUtils.getMethodSignature(wlQueryKey.getFinderName(), wlQueryKey.getFinderParams()));
         }
      }

   }

   private void checkQueriesHaveValidGroupNamesAndCachingNames() {
      String ejbName = this.testCMPB.getEJBName();
      RDBMSBean rb = (RDBMSBean)this.rdbmsBeanMap.get(ejbName);
      Iterator var3 = rb.getRdbmsFinders().values().iterator();

      while(var3.hasNext()) {
         RDBMSFinder rdbmsFinder = (RDBMSFinder)var3.next();
         if (rdbmsFinder.getGroupName() != null && rb.getFieldGroup(rdbmsFinder.getGroupName()) == null) {
            this.errors.add(new ComplianceException(this.getFmt().QUERY_CONTAINS_UNDEFINED_GROUP(ejbName, rdbmsFinder.getGroupName()), new DescriptorErrorInfo("<group-name>", ejbName, rdbmsFinder.getFinderName())));
         }

         if (rdbmsFinder.getCachingName() != null && rb.getRelationshipCaching(rdbmsFinder.getCachingName()) == null) {
            this.errors.add(new ComplianceException(this.getFmt().QUERY_CONTAINS_UNDEFINED_CACHING_NAME(ejbName, rdbmsFinder.getCachingName()), new DescriptorErrorInfo("<caching-name>", ejbName, rdbmsFinder.getFinderName())));
         }
      }

   }

   private void checkNoSqlSelectDistinctWithBlobClob() {
      String ejbName = this.testCMPB.getEJBName();
      RDBMSBean rb = (RDBMSBean)this.rdbmsBeanMap.get(ejbName);
      Iterator var3 = rb.getRdbmsFinders().values().iterator();

      while(true) {
         label29:
         while(true) {
            RDBMSFinder rdbmsFinder;
            do {
               if (!var3.hasNext()) {
                  return;
               }

               rdbmsFinder = (RDBMSFinder)var3.next();
            } while(!rdbmsFinder.getSqlSelectDistinct());

            String groupName = rdbmsFinder.getGroupName();
            if (groupName != null) {
               Iterator var6 = rb.getFieldGroup(groupName).getCmpFields().iterator();

               while(true) {
                  String fieldName;
                  do {
                     if (!var6.hasNext()) {
                        continue label29;
                     }

                     fieldName = (String)var6.next();
                  } while(!rb.isBlobCmpColumnTypeForField(fieldName) && !rb.isClobCmpColumnTypeForField(fieldName));

                  this.errors.add(new ComplianceException(this.getFmt().NoSqlSelectDistinctWithBlobClobField(ejbName, rdbmsFinder.getFinderName(), groupName)));
               }
            } else if (rb.hasBlobClobColumn()) {
               this.errors.add(new ComplianceException(this.getFmt().NoSqlSelectDistinctWithBlobClobField(ejbName, rdbmsFinder.getFinderName(), "defaultGroup")));
            }
         }
      }
   }

   private void checkWLFindByPrimaryKey() {
      String ejbName = this.testCMPB.getEJBName();
      RDBMSBean rdbmsBean = (RDBMSBean)this.rdbmsBeanMap.get(ejbName);
      String findByPKName = "findByPrimaryKey";
      String[] findByPKParams = new String[]{this.testCMPB.getPrimaryKeyClassName()};
      RDBMSFinder rf = (RDBMSFinder)rdbmsBean.getRdbmsFinders().get(new RDBMSFinder.FinderKey(findByPKName, findByPKParams));
      if (rf != null && rf.getEjbQlQuery() != null) {
         this.errors.add(new ComplianceException(this.getFmt().WLQL_CANNOT_OVERRIDE_FINDBYPK_QL(ejbName, rf.getEjbQlQuery())));
      }
   }

   private void checkFieldGroupsUnused() {
      String ejbName = this.testCMPB.getEJBName();
      RDBMSBean rb = (RDBMSBean)this.rdbmsBeanMap.get(ejbName);
      CMPBeanDescriptor bd = (CMPBeanDescriptor)this.beanMap.get(ejbName);
      boolean isDynamicQueriesEnabled = bd.getIsDynamicQueriesEnabled();
      if (!isDynamicQueriesEnabled) {
         List l = rb.getFieldGroups();
         if (!l.isEmpty()) {
            List fieldGroups = new LinkedList();
            Iterator var9 = l.iterator();

            while(var9.hasNext()) {
               FieldGroup fg = (FieldGroup)var9.next();
               fieldGroups.add(fg);
            }

            var9 = rb.getRdbmsFinders().values().iterator();

            FieldGroup group;
            String groupName;
            while(var9.hasNext()) {
               RDBMSFinder rfinder = (RDBMSFinder)var9.next();
               groupName = rfinder.getGroupName();
               if (groupName != null && groupName.length() > 0) {
                  group = this.getFieldGroupByName(groupName, fieldGroups);
                  if (group != null) {
                     fieldGroups.remove(group);
                  }
               }
            }

            var9 = this.rdbmsBeanMap.values().iterator();

            while(var9.hasNext()) {
               RDBMSBean bean = (RDBMSBean)var9.next();
               Iterator var11 = bean.getRelationshipCachings().iterator();

               while(var11.hasNext()) {
                  RelationshipCaching rc = (RelationshipCaching)var11.next();
                  List list = rc.getCachingElements();
                  if (!list.isEmpty()) {
                     this.filterFieldGroupInCachingElements(fieldGroups, list);
                  }
               }
            }

            var9 = this.rdbmsRelationMap.values().iterator();

            while(var9.hasNext()) {
               RDBMSRelation rRel = (RDBMSRelation)var9.next();
               RDBMSRelation.RDBMSRole role1 = rRel.getRole1();
               RDBMSRelation.RDBMSRole role2 = rRel.getRole2();
               if (role1 != null) {
                  groupName = role1.getGroupName();
                  if (groupName != null && groupName.length() > 0) {
                     group = this.getFieldGroupByName(groupName, fieldGroups);
                     if (group != null) {
                        fieldGroups.remove(group);
                     }
                  }
               }

               if (role2 != null) {
                  groupName = role2.getGroupName();
                  if (groupName != null && groupName.length() > 0) {
                     group = this.getFieldGroupByName(groupName, fieldGroups);
                     if (group != null) {
                        fieldGroups.remove(group);
                     }
                  }
               }
            }

            if (fieldGroups.size() > 0) {
               StringBuilder sb = new StringBuilder();
               Iterator it = fieldGroups.iterator();

               while(it.hasNext()) {
                  group = (FieldGroup)it.next();
                  sb.append(group.getName());
                  if (it.hasNext()) {
                     sb.append(", ");
                  }
               }

               EJBLogger.logWarningUnusedFieldGroups(ejbName, sb.toString());
            }

         }
      }
   }

   private void filterFieldGroupInCachingElements(List fieldGroups, List cachingElements) {
      RelationshipCaching.CachingElement ce;
      for(Iterator var3 = cachingElements.iterator(); var3.hasNext(); this.filterFieldGroupInCachingElements(fieldGroups, ce.getCachingElements())) {
         ce = (RelationshipCaching.CachingElement)var3.next();
         String groupName = ce.getGroupName();
         if (groupName != null && groupName.length() > 0) {
            FieldGroup group = this.getFieldGroupByName(groupName, fieldGroups);
            if (group != null) {
               fieldGroups.remove(group);
            }
         }
      }

   }

   private FieldGroup getFieldGroupByName(String name, List groups) {
      if (name != null && groups != null) {
         Iterator var3 = groups.iterator();

         FieldGroup group;
         do {
            if (!var3.hasNext()) {
               return null;
            }

            group = (FieldGroup)var3.next();
         } while(!group.getName().equals(name));

         return group;
      } else {
         return null;
      }
   }

   private void checkRelationshipCachesUnused() {
      String ejbName = this.testCMPB.getEJBName();
      RDBMSBean rb = (RDBMSBean)this.rdbmsBeanMap.get(ejbName);
      if (!((CMPBeanDescriptor)this.beanMap.get(ejbName)).getIsDynamicQueriesEnabled()) {
         List l = rb.getRelationshipCachings();
         if (!l.isEmpty()) {
            List relCachings = new LinkedList();
            Iterator var6 = l.iterator();

            while(var6.hasNext()) {
               RelationshipCaching relationshipCaching = (RelationshipCaching)var6.next();
               relCachings.add(relationshipCaching);
            }

            var6 = rb.getRdbmsFinders().values().iterator();

            RelationshipCaching rCaching;
            while(var6.hasNext()) {
               RDBMSFinder rdbmsFinder = (RDBMSFinder)var6.next();
               String cachingName = rdbmsFinder.getCachingName();
               if (cachingName != null && cachingName.length() > 0) {
                  rCaching = this.getRelationshipCachingByName(cachingName, relCachings);
                  if (rCaching != null) {
                     relCachings.remove(rCaching);
                  }
               }
            }

            if (!relCachings.isEmpty()) {
               StringBuilder sb = new StringBuilder();
               Iterator it = relCachings.iterator();

               while(it.hasNext()) {
                  rCaching = (RelationshipCaching)it.next();
                  sb.append(rCaching.getCachingName());
                  if (it.hasNext()) {
                     sb.append(", ");
                  }
               }

               EJBLogger.logWarningUnusedRelationshipCachings(ejbName, sb.toString());
            }

         }
      }
   }

   private RelationshipCaching getRelationshipCachingByName(String name, List rCachings) {
      if (name != null && rCachings != null) {
         Iterator var3 = rCachings.iterator();

         RelationshipCaching rCaching;
         do {
            if (!var3.hasNext()) {
               return null;
            }

            rCaching = (RelationshipCaching)var3.next();
         } while(!rCaching.getCachingName().equals(name));

         return rCaching;
      } else {
         return null;
      }
   }

   static {
      RESERVED_WORDS.add("GROUPBY");
      RESERVED_WORDS.add("ORDERBY");
      RESERVED_WORDS.add("SELECT");
      RESERVED_WORDS.add("FROM");
      RESERVED_WORDS.add("WHERE");
      RESERVED_WORDS.add("WLS_TEMP");
   }
}
