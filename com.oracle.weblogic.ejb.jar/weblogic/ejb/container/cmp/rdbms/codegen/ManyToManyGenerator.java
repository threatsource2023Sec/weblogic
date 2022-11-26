package weblogic.ejb.container.cmp.rdbms.codegen;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.cmp.rdbms.RDBMSBean;
import weblogic.ejb.container.cmp.rdbms.RDBMSUtils;
import weblogic.ejb.container.ejbc.EJBCException;
import weblogic.ejb.container.ejbc.EjbCodeGenerator;
import weblogic.ejb.container.persistence.spi.CMPBeanDescriptor;
import weblogic.ejb.container.utils.ClassUtils;
import weblogic.ejb.container.utils.MethodUtils;
import weblogic.ejb20.cmp.rdbms.RDBMSException;
import weblogic.logging.Loggable;
import weblogic.utils.Getopt2;
import weblogic.utils.compiler.CodeGenerationException;
import weblogic.utils.compiler.CodeGenerator;

public final class ManyToManyGenerator extends BaseCodeGenerator {
   private RDBMSBean rbean = null;
   private CMPBeanDescriptor bd = null;
   private String cmrField;
   private String packageName = null;

   public ManyToManyGenerator(Getopt2 opts) {
      super(opts);
   }

   protected List typeSpecificTemplates() {
      throw new AssertionError("This method should never be called.");
   }

   public void setRDBMSBean(RDBMSBean rbean) {
      assert rbean != null;

      this.rbean = rbean;
   }

   public void setCMPBeanDescriptor(CMPBeanDescriptor bd) {
      this.bd = bd;
      this.packageName = RDBMSUtils.head(bd.getBeanClass().getName());
   }

   public void setCmrFieldName(String name) {
      assert name != null;

      this.cmrField = name;
   }

   protected void prepare(CodeGenerator.Output output) throws EJBCException, ClassNotFoundException {
      super.prepare(output);

      assert this.rbean != null;

   }

   protected Enumeration outputs(List l) throws Exception {
      assert l.size() == 0;

      Vector outputs = new Vector();
      this.addOutputs(outputs);
      return outputs.elements();
   }

   protected void addOutputs(List outputs) throws RDBMSException {
      EjbCodeGenerator.Output[] output = new EjbCodeGenerator.Output[]{new EjbCodeGenerator.Output(), null};
      output[0].setOutputFile(this.setClassName() + ".java");
      output[0].setTemplate("/weblogic/ejb/container/cmp/rdbms/codegen/ManyToManySet.j");
      output[0].setPackage(this.packageName());
      outputs.add(output[0]);
      output[1] = new EjbCodeGenerator.Output();
      output[1].setOutputFile(this.iteratorClassName() + ".java");
      output[1].setTemplate("/weblogic/ejb/container/cmp/rdbms/codegen/OneToManyIterator.j");
      output[1].setPackage(this.packageName());
      outputs.add(output[1]);
   }

   public String varPrefix() {
      return "__WL_";
   }

   public String debugVar() {
      return this.varPrefix() + "debugLogger";
   }

   public String debugEnabled() {
      return this.debugVar() + ".isDebugEnabled()";
   }

   public String debugSay() {
      return this.varPrefix() + "debug";
   }

   public String beanVar() {
      return this.varPrefix() + "bean";
   }

   public String stmtVar() {
      return this.varPrefix() + "stmt";
   }

   public String conVar() {
      return this.varPrefix() + "con";
   }

   public String rsVar() {
      return this.varPrefix() + "rs";
   }

   public String pmVar() {
      return this.varPrefix() + "pm";
   }

   public String pkVar() {
      return this.varPrefix() + "pk";
   }

   public String pk1Var() {
      return this.varPrefix() + "pk1";
   }

   public String pk2Var() {
      return this.varPrefix() + "pk2";
   }

   public String fkVar() {
      return this.varPrefix() + "fk";
   }

   public String keyVar() {
      return this.varPrefix() + "key";
   }

   public String numVar() {
      return this.varPrefix() + "num";
   }

   public String iVar() {
      return this.varPrefix() + "i";
   }

   public String countVar() {
      return this.varPrefix() + "count";
   }

   public String ctxVar() {
      return this.varPrefix() + "ctx";
   }

   public String ceoVar() {
      return this.varPrefix() + "createEo";
   }

   public String cpkVar() {
      return this.varPrefix() + "createPk";
   }

   public String colVar() {
      return this.varPrefix() + "collection";
   }

   public String bmVar() {
      return this.varPrefix() + "bm";
   }

   public String finderVar() {
      return this.varPrefix() + "finder";
   }

   public String creatorVar() {
      return this.varPrefix() + "creator";
   }

   public String cacheVar() {
      return this.varPrefix() + "cache";
   }

   public String addVar() {
      return this.varPrefix() + "add";
   }

   public String removeVar() {
      return this.varPrefix() + "rem";
   }

   public String addIter() {
      return this.varPrefix() + "additer";
   }

   public String remIter() {
      return this.varPrefix() + "remiter";
   }

   public String wrapperVar() {
      return this.varPrefix() + "wrapper";
   }

   public String iterVar() {
      return this.varPrefix() + "iter";
   }

   public String queryVar() {
      return this.varPrefix() + "query";
   }

   public String symmetricVar() {
      return this.varPrefix() + "symmetric";
   }

   public String createTxIdVar() {
      return this.varPrefix() + "createTxId";
   }

   public String addSetVar() {
      return this.varPrefix() + "addSet";
   }

   public String ejbName() {
      return this.rbean.getEjbName();
   }

   public String cmrName() {
      return this.cmrField;
   }

   public String oldStateVar() {
      return this.varPrefix() + "oldState";
   }

   public String relatedEjbName() {
      CMPBeanDescriptor rbd = this.rbean.getRelatedDescriptor(this.cmrField);
      return rbd.getEJBName();
   }

   public String wrapperSetFinder() {
      CMPBeanDescriptor rbd = this.rbean.getRelatedDescriptor(this.cmrField);
      return rbd.hasLocalClientView() ? "localWrapperSetFinder" : "remoteWrapperSetFinder";
   }

   public String EJBObject() {
      return this.bd.hasLocalClientView() ? "EJBLocalObject" : "EJBObject";
   }

   public String EJBObjectForField() {
      CMPBeanDescriptor rbd = this.rbean.getRelatedDescriptor(this.cmrField);
      return rbd.hasLocalClientView() ? "EJBLocalObject" : "EJBObject";
   }

   public String EoWrapper() {
      CMPBeanDescriptor rbd = this.rbean.getRelatedDescriptor(this.cmrField);
      return rbd.hasLocalClientView() ? "EloWrapper" : "EoWrapper";
   }

   public String getEJBObject() {
      return this.bd.hasLocalClientView() ? "getEJBLocalObject" : "getEJBObject";
   }

   public String getEJBObjectForField() {
      CMPBeanDescriptor rbd = this.rbean.getRelatedDescriptor(this.cmrField);
      return rbd.hasLocalClientView() ? "getEJBLocalObject" : "getEJBObject";
   }

   public String setClassName() {
      return ClassUtils.setClassName(this.bd, this.cmrField);
   }

   public String iteratorClassName() {
      return ClassUtils.iteratorClassName(this.bd, this.cmrField);
   }

   public String owningBeanClassName() {
      return this.bd.getGeneratedBeanClassName();
   }

   public String owningBeanInterfaceName() {
      return this.bd.getGeneratedBeanInterfaceName();
   }

   public String remoteInterfaceName() {
      return this.bd.hasLocalClientView() ? this.bd.getLocalInterfaceName() : this.bd.getRemoteInterfaceName();
   }

   public String relatedBeanClassName() {
      return this.rbean.getRelatedBeanClassName(this.cmrField);
   }

   public String relatedBeanInterfaceName() {
      CMPBeanDescriptor rbd = this.rbean.getRelatedDescriptor(this.cmrField);
      return rbd.getGeneratedBeanInterfaceName();
   }

   public String relatedRemoteInterfaceName() {
      CMPBeanDescriptor rbd = this.rbean.getRelatedDescriptor(this.cmrField);
      return rbd.hasLocalClientView() ? rbd.getLocalInterfaceName() : rbd.getRemoteInterfaceName();
   }

   private String declarePkVar() {
      StringBuffer sb = new StringBuffer();
      sb.append(this.bd.getPrimaryKeyClass().getName() + " " + this.pkVar() + " = ");
      if (this.bd.hasComplexPrimaryKey()) {
         sb.append("new " + this.bd.getPrimaryKeyClass().getName() + "();");
      } else {
         sb.append("null;");
      }

      return sb.toString();
   }

   public String perhapsDeclarePkVar() {
      return this.bd.hasComplexPrimaryKey() ? this.declarePkVar() : "";
   }

   public String perhapsAssignPkVar() {
      StringBuffer sb = new StringBuffer();
      return sb.toString();
   }

   public String pkVarForBean() {
      StringBuffer sb = new StringBuffer();
      return sb.toString();
   }

   public String declareAddSet() {
      return "private Set " + this.addSetVar() + " = null;";
   }

   private String setterMethodName(String fieldName) {
      return "set" + fieldName.substring(0, 1).toUpperCase(Locale.ENGLISH) + fieldName.substring(1);
   }

   public String relatedBeanSetMethod() {
      return this.setterMethodName(this.rbean.getRelatedFieldName(this.cmrField));
   }

   public String packageName() {
      assert this.packageName != null;

      return this.packageName;
   }

   public String packageStatement() {
      return this.packageName != null && !this.packageName.equals("") ? "package " + this.packageName + ";" : "";
   }

   public String symmetricRelationship() {
      RDBMSBean relBean = this.rbean.getRelatedRDBMSBean(this.cmrField);
      String relFieldName = this.rbean.getRelatedFieldName(this.cmrField);
      return relBean == this.rbean && relFieldName.equals(this.cmrField) ? "true" : "false";
   }

   public String joinParamsSql() {
      StringBuffer sb = new StringBuffer();
      List joinColNames = new ArrayList();
      Iterator fkColNames = this.rbean.getForeignKeyColNames(this.cmrField).iterator();

      while(fkColNames.hasNext()) {
         String fkColName = (String)fkColNames.next();
         joinColNames.add(fkColName);
         sb.append("(" + fkColName + " = ?)");
         if (fkColNames.hasNext()) {
            sb.append(" AND ");
         }
      }

      RDBMSBean relBean = this.rbean.getRelatedRDBMSBean(this.cmrField);
      String relFieldName = this.rbean.getRelatedFieldName(this.cmrField);
      if (relBean == this.rbean && relFieldName.equals(this.cmrField)) {
         fkColNames = relBean.getSymmetricKeyColNames(relFieldName).iterator();
      } else {
         fkColNames = relBean.getForeignKeyColNames(relFieldName).iterator();
      }

      while(fkColNames.hasNext()) {
         String fkColName = (String)fkColNames.next();
         if (!joinColNames.contains(fkColName)) {
            sb.append(" AND ");
            sb.append("(" + fkColName + " = ?)");
         }
      }

      return sb.toString();
   }

   private void addPrimaryKeyBinding(StringBuffer sb, String pstmtTypeName, int paramIdx, String pkExpr, Class fieldClass, String fieldName) {
      sb.append(this.stmtVar() + ".set" + pstmtTypeName + "(");
      sb.append(paramIdx).append(", ");
      sb.append(pkExpr + MethodUtils.convertToPrimitive(fieldClass, fieldName));
      sb.append(");" + EOL);
   }

   private int addPrimaryKeyBindings(StringBuffer sb, RDBMSBean bean, CMPBeanDescriptor bd, String cmrFieldName, int paramIdx, String pkVar, List joinColNames, boolean relatedBean) throws CodeGenerationException {
      boolean hasComplexKey = bd.hasComplexPrimaryKey();
      Class pkClass = bd.getPrimaryKeyClass();
      Iterator fkColNames = null;
      RDBMSBean relBean = bean.getRelatedRDBMSBean(cmrFieldName);
      String relFieldName = bean.getRelatedFieldName(cmrFieldName);
      boolean symmetric = relatedBean && relBean == bean && relFieldName.equals(cmrFieldName);
      Map symColumn2field = null;
      if (symmetric) {
         fkColNames = relBean.getSymmetricKeyColNames(relFieldName).iterator();
         symColumn2field = relBean.getSymmetricColumn2FieldName(relFieldName);
      } else {
         fkColNames = bean.getForeignKeyColNames(cmrFieldName).iterator();
      }

      while(fkColNames.hasNext()) {
         String fkColName = (String)fkColNames.next();
         if (!joinColNames.contains(fkColName)) {
            joinColNames.add(fkColName);
            String pkFieldName = null;
            if (symmetric) {
               pkFieldName = (String)symColumn2field.get(fkColName);
            } else {
               pkFieldName = bean.getRelatedPkFieldName(cmrFieldName, fkColName);
            }

            String pkExpr = null;
            Class fieldClass = null;
            String pstmtTypeName = null;
            if (hasComplexKey) {
               pkExpr = "((" + pkClass.getName() + ")" + pkVar + ").";
               Field pkField = null;

               try {
                  pkField = pkClass.getField(pkFieldName);
               } catch (NoSuchFieldException var24) {
                  Loggable l = EJBLogger.logFieldNotFoundInClassLoggable(pkFieldName, pkClass.getName());
                  throw new CodeGenerationException(l.getMessageText());
               }

               fieldClass = pkField.getType();
               pstmtTypeName = StatementBinder.getStatementTypeNameForClass(fieldClass);
            } else {
               pkExpr = "";
               fieldClass = pkClass;
               pstmtTypeName = StatementBinder.getStatementTypeNameForClass(pkClass);
               pkFieldName = "((" + pkClass.getName() + ")" + pkVar + ")";
            }

            if (RDBMSUtils.isOracleNLSDataType(relBean, pkFieldName)) {
               sb.append("if(").append(this.stmtVar()).append(" instanceof oracle.jdbc.OraclePreparedStatement) {" + EOL);
               sb.append("((oracle.jdbc.OraclePreparedStatement)").append(this.stmtVar()).append(").setFormOfUse(").append(paramIdx).append(", oracle.jdbc.OraclePreparedStatement.FORM_NCHAR);").append(EOL);
               sb.append("}" + EOL);
            }

            this.addPrimaryKeyBinding(sb, pstmtTypeName, paramIdx, pkExpr, fieldClass, pkFieldName);
            ++paramIdx;
         }
      }

      return paramIdx;
   }

   public String setJoinTableParams() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      int paramIdx = 1;
      List joinColNames = new ArrayList();
      paramIdx = this.addPrimaryKeyBindings(sb, this.rbean, this.bd, this.cmrField, paramIdx, this.pk1Var(), joinColNames, false);
      RDBMSBean relBean = this.rbean.getRelatedRDBMSBean(this.cmrField);
      CMPBeanDescriptor relDesc = this.rbean.getRelatedDescriptor(this.cmrField);
      String relField = this.rbean.getRelatedFieldName(this.cmrField);
      this.addPrimaryKeyBindings(sb, relBean, relDesc, relField, paramIdx, this.pk2Var(), joinColNames, true);
      return sb.toString();
   }

   public String joinColumnsSql() {
      StringBuffer sb = new StringBuffer();
      List joinColNames = new ArrayList();
      Iterator fkColNames = this.rbean.getForeignKeyColNames(this.cmrField).iterator();

      while(fkColNames.hasNext()) {
         String fkColName = (String)fkColNames.next();
         sb.append(fkColName);
         joinColNames.add(fkColName);
         if (fkColNames.hasNext()) {
            sb.append(", ");
         }
      }

      RDBMSBean relBean = this.rbean.getRelatedRDBMSBean(this.cmrField);
      String relFieldName = this.rbean.getRelatedFieldName(this.cmrField);
      if (relBean == this.rbean && relFieldName.equals(this.cmrField)) {
         fkColNames = relBean.getSymmetricKeyColNames(relFieldName).iterator();
      } else {
         fkColNames = relBean.getForeignKeyColNames(relFieldName).iterator();
      }

      while(fkColNames.hasNext()) {
         String fkColName = (String)fkColNames.next();
         if (!joinColNames.contains(fkColName)) {
            sb.append(", ");
            sb.append(fkColName);
         }
      }

      return sb.toString();
   }

   public String joinColsQMs() {
      StringBuffer sb = new StringBuffer();
      List joinColNames = new ArrayList();
      Iterator fkColNames = this.rbean.getForeignKeyColNames(this.cmrField).iterator();

      while(fkColNames.hasNext()) {
         String fkColName = (String)fkColNames.next();
         sb.append("?");
         joinColNames.add(fkColName);
         if (fkColNames.hasNext()) {
            sb.append(", ");
         }
      }

      RDBMSBean relBean = this.rbean.getRelatedRDBMSBean(this.cmrField);
      String relFieldName = this.rbean.getRelatedFieldName(this.cmrField);
      if (relBean == this.rbean && relFieldName.equals(this.cmrField)) {
         fkColNames = relBean.getSymmetricKeyColNames(relFieldName).iterator();
      } else {
         fkColNames = relBean.getForeignKeyColNames(relFieldName).iterator();
      }

      while(fkColNames.hasNext()) {
         String fkColName = (String)fkColNames.next();
         if (!joinColNames.contains(fkColName)) {
            sb.append(", ");
            sb.append("?");
         }
      }

      return sb.toString();
   }

   public String joinTableName() {
      assert this.rbean.getJoinTableName(this.cmrField) != null;

      return this.rbean.getJoinTableName(this.cmrField);
   }

   public String relatedGetMethodName() {
      String relFieldNm = this.rbean.getRelatedFieldName(this.cmrField);
      return MethodUtils.getMethodName(relFieldNm);
   }

   public String existsJoinTableQuery() {
      return this.existsJoinTableQuery(0);
   }

   public String existsJoinTableQueryForUpdate() {
      return this.existsJoinTableQuery(1);
   }

   public String existsJoinTableQueryForUpdateNoWait() {
      return this.existsJoinTableQuery(2);
   }

   private String existsJoinTableQuery(int selectForUpdateVal) {
      StringBuffer sb = new StringBuffer("SELECT 7 FROM ");
      sb.append(this.joinTableName());
      int dbType = this.rbean.getDatabaseType();
      switch (dbType) {
         case 0:
         case 1:
         case 3:
         case 4:
         case 6:
         case 8:
         case 9:
         case 10:
            sb.append(" WHERE ");
            sb.append(this.joinParamsSql());
            sb.append(RDBMSUtils.selectForUpdateToString(selectForUpdateVal));
            break;
         case 2:
         case 7:
            if (selectForUpdateVal == 1) {
               sb.append(" WITH(UPDLOCK)");
            }

            sb.append(" WHERE ");
            sb.append(this.joinParamsSql());
            if (selectForUpdateVal == 2) {
               sb.append(RDBMSUtils.selectForUpdateToString(selectForUpdateVal));
            } else if (selectForUpdateVal == 0) {
            }
            break;
         case 5:
            if (selectForUpdateVal == 1) {
               sb.append(" HOLDLOCK");
            }

            sb.append(" WHERE ");
            sb.append(this.joinParamsSql());
            if (selectForUpdateVal == 2) {
               sb.append(RDBMSUtils.selectForUpdateToString(selectForUpdateVal));
            } else if (selectForUpdateVal == 0) {
            }
            break;
         default:
            throw new AssertionError("Undefined database type " + dbType);
      }

      return sb.toString();
   }

   public String perhapsImplementQueryCachingMethods() throws CodeGenerationException {
      return this.parse(this.getProductionRule("queryCachingMethods"));
   }

   public String isLocal() {
      CMPBeanDescriptor rbd = this.rbean.getRelatedDescriptor(this.cmrField);
      return rbd.hasLocalClientView() ? "true" : "false";
   }

   public String perhapsPopulateFromQueryCache() {
      if (!this.rbean.isQueryCachingEnabledForCMRField(this.cmrField)) {
         return "";
      } else {
         StringBuffer sb = new StringBuffer();
         sb.append(this.cacheVar()).append(" = getFromQueryCache();").append(EOL);
         sb.append("if (").append(this.cacheVar()).append(" == null) {").append(EOL);
         return sb.toString();
      }
   }

   public String perhapsPutInQueryCache() {
      if (!this.rbean.isQueryCachingEnabledForCMRField(this.cmrField)) {
         return "";
      } else {
         StringBuffer sb = new StringBuffer("}");
         sb.append(EOL);
         sb.append("putInQueryCache(null);").append(EOL);
         return sb.toString();
      }
   }

   public String readOnlyFinderRunsInItsOwnTransaction() {
      CMPBeanDescriptor rbd = this.rbean.getRelatedDescriptor(this.cmrField);
      if (rbd.getConcurrencyStrategy() == 4) {
         StringBuffer sb = new StringBuffer();
         sb.append("Transaction orgTx = TransactionHelper.getTransactionHelper().getTransaction();\n");
         sb.append("try {\n");
         sb.append("TransactionManager tms = TxHelper.getTransactionManager();\n");
         sb.append("tms.suspend();\n");
         sb.append("tms.begin();\n");
         sb.append("Transaction tx = tms.getTransaction();\n");
         sb.append(this.cacheVar() + " = " + this.bmVar() + "." + this.wrapperSetFinder() + "(" + this.finderVar() + ", new Object[] {" + this.cpkVar() + "}, true);\n");
         sb.append("tx.commit();\n");
         sb.append("} catch(Exception e) {\n");
         sb.append(" throw e; }\n");
         sb.append("finally {\n");
         sb.append("TxHelper.getTransactionManager().resume(orgTx);\n");
         sb.append("}\n");
         return sb.toString();
      } else {
         return this.cacheVar() + " = " + this.bmVar() + "." + this.wrapperSetFinder() + "(" + this.finderVar() + ", new Object[] {" + this.cpkVar() + "}, true);";
      }
   }

   public String isReadOnly() {
      CMPBeanDescriptor rbd = this.rbean.getRelatedDescriptor(this.cmrField);
      return !rbd.isReadOnly() && rbd.getConcurrencyStrategy() != 4 ? "false" : "true";
   }
}
