package weblogic.ejb.container.cmp.rdbms;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import weblogic.ejb.container.cmp.rdbms.finders.RDBMSFinder;
import weblogic.ejb.container.compliance.EJBComplianceTextFormatter;
import weblogic.ejb.container.persistence.spi.CMPBeanDescriptor;
import weblogic.ejb.container.utils.MethodUtils;
import weblogic.ejb20.cmp.rdbms.RDBMSException;
import weblogic.j2ee.descriptor.wl.AutomaticKeyGenerationBean;
import weblogic.j2ee.descriptor.wl.CachingElementBean;
import weblogic.j2ee.descriptor.wl.ColumnMapBean;
import weblogic.j2ee.descriptor.wl.DatabaseSpecificSqlBean;
import weblogic.j2ee.descriptor.wl.FieldGroupBean;
import weblogic.j2ee.descriptor.wl.FieldMapBean;
import weblogic.j2ee.descriptor.wl.QueryMethodBean;
import weblogic.j2ee.descriptor.wl.RelationshipCachingBean;
import weblogic.j2ee.descriptor.wl.RelationshipRoleMapBean;
import weblogic.j2ee.descriptor.wl.SqlQueryBean;
import weblogic.j2ee.descriptor.wl.SqlShapeBean;
import weblogic.j2ee.descriptor.wl.TableBean;
import weblogic.j2ee.descriptor.wl.TableMapBean;
import weblogic.j2ee.descriptor.wl.UnknownPrimaryKeyFieldBean;
import weblogic.j2ee.descriptor.wl.WeblogicQueryBean;
import weblogic.j2ee.descriptor.wl.WeblogicRdbmsBeanBean;
import weblogic.j2ee.descriptor.wl.WeblogicRdbmsJarBean;
import weblogic.j2ee.descriptor.wl.WeblogicRdbmsRelationBean;
import weblogic.j2ee.descriptor.wl.WeblogicRelationshipRoleBean;

public final class RDBMSDeploymentInfo {
   private final Map rdbmsBeanMap = new HashMap();
   private final Map rdbmsRelationMap = new HashMap();
   private final String fileName;

   public RDBMSDeploymentInfo(WeblogicRdbmsJarBean wlJar, Map cmpBeanDescMap, String fileName) throws RDBMSException {
      this.fileName = fileName;
      WeblogicRdbmsBeanBean[] beans = wlJar.getWeblogicRdbmsBeans();

      for(int i = 0; i < beans.length; ++i) {
         CMPBeanDescriptor cmpbd = (CMPBeanDescriptor)cmpBeanDescMap.get(beans[i].getEjbName());
         if (cmpbd != null) {
            boolean newPKField = false;
            if (cmpbd.getPrimaryKeyClassName().equals("java.lang.Object")) {
               UnknownPrimaryKeyFieldBean upk = beans[i].getUnknownPrimaryKeyField();
               if (upk == null) {
                  throw new RDBMSException(EJBComplianceTextFormatter.getInstance().UNKNOWN_PK_NEVER_ASSIGNED(beans[i].getEjbName()));
               }

               newPKField = !cmpbd.getCMFieldNames().contains(upk.getCmpField());
               cmpbd.setPrimaryKeyField(upk.getCmpField());
            }

            RDBMSBean bean = new RDBMSBean();
            bean.setEjbName(beans[i].getEjbName());
            bean.setDataSourceName(beans[i].getDataSourceJndiName());
            this.processTableMaps(beans[i], bean);
            this.processFieldGroups(beans[i], bean);
            this.processRelationshipCaching(beans[i], bean);
            this.processSqlShapes(beans[i], bean);
            this.processQueries(beans[i], bean, cmpbd);
            bean.setDelayInsertUntil(beans[i].getDelayDatabaseInsertUntil());
            bean.setUseSelectForUpdate(beans[i].isUseSelectForUpdate());
            bean.setLockOrder(beans[i].getLockOrder());
            bean.setInstanceLockOrder(beans[i].getInstanceLockOrder());
            bean.setCheckExistsOnMethod(beans[i].isCheckExistsOnMethod());
            bean.setClusterInvalidationDisabled(beans[i].isClusterInvalidationDisabled());
            bean.setUseInnerJoin(beans[i].isUseInnerJoin());
            bean.setCategoryCmpField(beans[i].getCategoryCmpField());
            this.processAutoKeyGeneration(beans[i], bean);
            if (newPKField && !bean.hasAutoKeyGeneration()) {
               throw new RDBMSException(EJBComplianceTextFormatter.getInstance().UNKNOWN_PK_WITHOUT_AUTO_KEY(beans[i].getEjbName()));
            }

            this.rdbmsBeanMap.put(bean.getEjbName(), bean);
         }
      }

      this.processRelations(wlJar);
   }

   public RDBMSBean getRDBMSBean(String name) {
      return (RDBMSBean)this.rdbmsBeanMap.get(name);
   }

   public Map getRDBMSBeanMap() {
      return this.rdbmsBeanMap;
   }

   public Map getRDBMSRelationMap() {
      return this.rdbmsRelationMap;
   }

   private void processRelations(WeblogicRdbmsJarBean wlJar) throws RDBMSException {
      WeblogicRdbmsRelationBean[] var2 = wlJar.getWeblogicRdbmsRelations();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         WeblogicRdbmsRelationBean wrrb = var2[var4];
         RDBMSRelation rdbmsRelation = new RDBMSRelation();
         rdbmsRelation.setName(wrrb.getRelationName());
         rdbmsRelation.setTableName(wrrb.getTableName());
         Set roleNames = new HashSet();
         WeblogicRelationshipRoleBean[] var8 = wrrb.getWeblogicRelationshipRoles();
         int var9 = var8.length;

         for(int var10 = 0; var10 < var9; ++var10) {
            WeblogicRelationshipRoleBean rb = var8[var10];
            RDBMSRelation.RDBMSRole role = new RDBMSRelation.RDBMSRole();
            String roleName = rb.getRelationshipRoleName();
            if (!roleNames.add(roleName)) {
               throw new RDBMSException(EJBComplianceTextFormatter.getInstance().DuplicateRelationshipRoleNamesDetected(wrrb.getRelationName(), roleName));
            }

            role.setName(roleName);
            role.setGroupName(rb.getGroupName());
            role.setDBCascadeDelete(rb.getDbCascadeDelete() != null);
            role.setQueryCachingEnabled(rb.getEnableQueryCaching());
            RelationshipRoleMapBean roleMap = rb.getRelationshipRoleMap();
            if (roleMap != null) {
               role.setForeignKeyTableName(roleMap.getForeignKeyTable());
               role.setPrimaryKeyTableName(roleMap.getPrimaryKeyTable());
               ColumnMapBean[] var15 = roleMap.getColumnMaps();
               int var16 = var15.length;

               for(int var17 = 0; var17 < var16; ++var17) {
                  ColumnMapBean cmb = var15[var17];
                  Map columnMap = role.getColumnMap();
                  columnMap.put(cmb.getForeignKeyColumn(), cmb.getKeyColumn());
               }
            }

            if (null == rdbmsRelation.getRole1()) {
               rdbmsRelation.setRole1(role);
            } else {
               rdbmsRelation.setRole2(role);
            }
         }

         this.rdbmsRelationMap.put(rdbmsRelation.getName(), rdbmsRelation);
      }

   }

   private void processAutoKeyGeneration(WeblogicRdbmsBeanBean bean, RDBMSBean rdbmsBean) {
      AutomaticKeyGenerationBean keyGen = bean.getAutomaticKeyGeneration();
      if (keyGen != null) {
         rdbmsBean.setGenKeyType(keyGen.getGeneratorType());
         rdbmsBean.setGenKeyGeneratorName(keyGen.getGeneratorName());
         rdbmsBean.setGenKeyCacheSize(keyGen.getKeyCacheSize());
         rdbmsBean.setSelectFirstSeqKeyBeforeUpdate(keyGen.getSelectFirstSequenceKeyBeforeUpdate());
      }

   }

   private void processQueries(WeblogicRdbmsBeanBean bean, RDBMSBean rdbmsBean, CMPBeanDescriptor bd) throws RDBMSException {
      WeblogicQueryBean[] queries = bean.getWeblogicQueries();

      for(int i = 0; i < queries.length; ++i) {
         RDBMSFinder finder = new RDBMSFinder();
         QueryMethodBean method = queries[i].getQueryMethod();
         finder.setFinderName(method.getMethodName());
         finder.setFinderParams(method.getMethodParams().getMethodParams());
         if (queries[i].getEjbQlQuery() != null) {
            finder.setEjbQlQuery(queries[i].getEjbQlQuery().getWeblogicQl());
            finder.setGroupName(queries[i].getEjbQlQuery().getGroupName());
            finder.setCachingName(queries[i].getEjbQlQuery().getCachingName());
         } else if (queries[i].getSqlQuery() != null) {
            SqlQueryBean sqlQuery = queries[i].getSqlQuery();
            finder.setSqlShapeName(sqlQuery.getSqlShapeName());
            Map sqlQueries = new HashMap();
            if (sqlQuery.getSql() != null) {
               sqlQueries.put(new Integer(0), sqlQuery.getSql());
            }

            DatabaseSpecificSqlBean[] databaseSpecifics = sqlQuery.getDatabaseSpecificSqls();

            for(int ii = 0; ii < databaseSpecifics.length; ++ii) {
               int dbType = MethodUtils.dbmsType2int(databaseSpecifics[ii].getDatabaseType());
               sqlQueries.put(new Integer(dbType), databaseSpecifics[ii].getSql());
            }

            finder.setSqlQueries(sqlQueries);
         }

         finder.setMaxElements(queries[i].getMaxElements());
         if (bd.getConcurrencyStrategy() == 6) {
            if (queries[i].isIncludeUpdatesSet()) {
               finder.setIncludeUpdates(queries[i].isIncludeUpdates());
            } else {
               finder.setIncludeUpdates(false);
            }
         } else {
            finder.setIncludeUpdates(queries[i].isIncludeUpdates());
         }

         finder.setSqlSelectDistinct(queries[i].isSqlSelectDistinct());
         finder.setQueryCachingEnabled(queries[i].getEnableQueryCaching());
         finder.setEnableEagerRefresh(queries[i].getEnableEagerRefresh());
         finder.setIncludeResultCacheHint(queries[i].isIncludeResultCacheHint());
         if (rdbmsBean.containsRdbmsFinder(finder)) {
            throw new RDBMSException(EJBComplianceTextFormatter.getInstance().DuplicateWeblogicQueryElementsDetected(finder.toString(), rdbmsBean.getEjbName(), this.fileName));
         }

         rdbmsBean.addRdbmsFinder(finder);
      }

   }

   private void processRelationshipCaching(WeblogicRdbmsBeanBean bean, RDBMSBean rdbmsBean) throws RDBMSException {
      Set cachingNames = new HashSet();
      RelationshipCachingBean[] cachings = bean.getRelationshipCachings();

      for(int i = 0; i < cachings.length; ++i) {
         RelationshipCaching relationshipCaching = new RelationshipCaching();
         if (!cachingNames.add(cachings[i].getCachingName())) {
            throw new RDBMSException("Duplicate caching name '" + cachings[i].getCachingName() + "' found in RDBMS CMP deployment descriptor '" + this.fileName + "' while reading information for '" + bean.getEjbName() + "'.");
         }

         relationshipCaching.setCachingName(cachings[i].getCachingName());
         CachingElementBean[] cachingElements = cachings[i].getCachingElements();

         for(int j = 0; j < cachingElements.length; ++j) {
            relationshipCaching.addCachingElement(this.processCachingElement(cachingElements[j]));
         }

         rdbmsBean.addRelationshipCaching(relationshipCaching);
      }

   }

   private RelationshipCaching.CachingElement processCachingElement(CachingElementBean element) {
      RelationshipCaching.CachingElement ce = new RelationshipCaching.CachingElement();
      ce.setCmrField(element.getCmrField());
      ce.setGroupName(element.getGroupName());
      if (element.getGroupName() == null || element.getGroupName().length() == 0) {
         ce.setGroupName("defaultGroup");
      }

      CachingElementBean[] var3 = element.getCachingElements();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         CachingElementBean ceb = var3[var5];
         ce.addCachingElement(this.processCachingElement(ceb));
      }

      return ce;
   }

   private void processSqlShapes(WeblogicRdbmsBeanBean bean, RDBMSBean rdbmsBean) throws RDBMSException {
      Set sqlShapeNames = new HashSet();
      SqlShapeBean[] results = bean.getSqlShapes();

      for(int i = 0; i < results.length; ++i) {
         SqlShape sqlShape = new SqlShape();
         if (!sqlShapeNames.add(results[i].getSqlShapeName())) {
            throw new RDBMSException(EJBComplianceTextFormatter.getInstance().DUPLICATE_SQL_CACHING_NAME(bean.getEjbName(), this.fileName, results[i].getSqlShapeName()));
         }

         sqlShape.setSqlShapeName(results[i].getSqlShapeName());
         TableBean[] tables = results[i].getTables();

         for(int j = 0; j < tables.length; ++j) {
            SqlShape.Table table = new SqlShape.Table();
            table.setName(tables[j].getTableName());
            table.setColumns(Arrays.asList(tables[j].getDbmsColumns()));
            if (tables[j].getEjbRelationshipRoleName() != null) {
               table.addEjbRelationshipRoleName(tables[j].getEjbRelationshipRoleName());
            }

            sqlShape.addTable(table);
         }

         sqlShape.setEjbRelationNames(results[i].getEjbRelationNames());
         sqlShape.setPassThroughColumns(results[i].getPassThroughColumns());
         rdbmsBean.addSqlShape(sqlShape);
      }

   }

   private void processFieldGroups(WeblogicRdbmsBeanBean bean, RDBMSBean rdbmsBean) throws RDBMSException {
      Set groupNames = new HashSet();
      FieldGroupBean[] fieldGroups = bean.getFieldGroups();

      for(int i = 0; i < fieldGroups.length; ++i) {
         if (!groupNames.add(fieldGroups[i].getGroupName())) {
            throw new RDBMSException("Duplicate group name '" + fieldGroups[i].getGroupName() + "' found in RDBMS CMP deployment descriptor '" + this.fileName + "' while reading information for '" + bean.getEjbName() + "'.");
         }

         FieldGroup group = new FieldGroup();
         group.setName(fieldGroups[i].getGroupName());
         String[] cmpFields = fieldGroups[i].getCmpFields();

         for(int j = 0; j < cmpFields.length; ++j) {
            group.addCmpField(cmpFields[j]);
         }

         String[] cmrFields = fieldGroups[i].getCmrFields();

         for(int j = 0; j < cmrFields.length; ++j) {
            group.addCmrField(cmrFields[j]);
         }

         rdbmsBean.addFieldGroup(group);
      }

   }

   private void processTableMaps(WeblogicRdbmsBeanBean bean, RDBMSBean rdbmsBean) throws RDBMSException {
      TableMapBean[] tableMaps = bean.getTableMaps();

      for(int i = 0; i < tableMaps.length; ++i) {
         String tableName = tableMaps[i].getTableName();
         if (rdbmsBean.hasTable(tableName)) {
            throw new RDBMSException("In EJB, " + bean.getEjbName() + ", an attempt was made to specify a <table-name> of, " + tableName + ", in multiple <table-map> elements. Each <table-map> element must specify a unique <table-name> and should contain all <field-map> elements for that table.");
         }

         rdbmsBean.addTable(tableName);
         FieldMapBean[] fieldMaps = tableMaps[i].getFieldMaps();

         for(int j = 0; j < fieldMaps.length; ++j) {
            rdbmsBean.addTableFieldColumnMapping(tableName, fieldMaps[j].getCmpField(), fieldMaps[j].getDbmsColumn());
            if (fieldMaps[j].getDbmsColumnType() != null) {
               rdbmsBean.addFieldColumnTypeMapping(fieldMaps[j].getCmpField(), fieldMaps[j].getDbmsColumnType());
            }

            if (fieldMaps[j].isDbmsDefaultValue()) {
               rdbmsBean.addDbmsDefaultValueField(fieldMaps[j].getCmpField());
            }
         }

         if (tableMaps[i].getVerifyRows() != null) {
            rdbmsBean.setVerifyRows(tableName, tableMaps[i].getVerifyRows());
         }

         if (tableMaps[i].getVerifyColumns() != null) {
            rdbmsBean.setVerifyColumns(tableName, tableMaps[i].getVerifyColumns());
         }

         if (tableMaps[i].getOptimisticColumn() != null) {
            rdbmsBean.setOptimisticColumn(tableName, tableMaps[i].getOptimisticColumn());
         }

         rdbmsBean.setTriggerUpdatesOptimisticColumn(tableName, tableMaps[i].isTriggerUpdatesOptimisticColumn());
         rdbmsBean.setVersionColumnInitialValue(tableName, tableMaps[i].getVersionColumnInitialValue());
      }

   }
}
