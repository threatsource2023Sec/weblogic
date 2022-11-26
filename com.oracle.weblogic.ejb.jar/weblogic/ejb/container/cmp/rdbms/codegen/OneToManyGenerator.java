package weblogic.ejb.container.cmp.rdbms.codegen;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Vector;
import weblogic.ejb.container.cmp.rdbms.RDBMSBean;
import weblogic.ejb.container.cmp.rdbms.RDBMSUtils;
import weblogic.ejb.container.ejbc.EJBCException;
import weblogic.ejb.container.ejbc.EjbCodeGenerator;
import weblogic.ejb.container.persistence.spi.CMPBeanDescriptor;
import weblogic.ejb.container.utils.ClassUtils;
import weblogic.ejb.container.utils.MethodUtils;
import weblogic.ejb20.cmp.rdbms.RDBMSException;
import weblogic.utils.Getopt2;
import weblogic.utils.compiler.CodeGenerationException;
import weblogic.utils.compiler.CodeGenerator;

public final class OneToManyGenerator extends BaseCodeGenerator {
   private RDBMSBean bean = null;
   private CMPBeanDescriptor bd = null;
   private String cmrField;
   private String packageName = null;

   public OneToManyGenerator(Getopt2 opts) {
      super(opts);
   }

   protected List typeSpecificTemplates() {
      throw new AssertionError("This method should never be called.");
   }

   public void setRDBMSBean(RDBMSBean bean) {
      assert bean != null;

      this.bean = bean;
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

      assert this.bean != null;

      if (debugLogger.isDebugEnabled()) {
         debug("cmp.rdbms.codegen.OneToManyGenerator.prepare() called");
      }

   }

   protected Enumeration outputs(List l) throws Exception {
      if (debugLogger.isDebugEnabled()) {
         debug("OneToManyGenerator.outputs() called");
      }

      assert l.size() == 0;

      Vector outputs = new Vector();
      this.addOutputs(outputs);
      return outputs.elements();
   }

   protected void addOutputs(List outputs) throws RDBMSException {
      if (debugLogger.isDebugEnabled()) {
         debug("OneToManyGenerator.addOutputs called");
      }

      EjbCodeGenerator.Output[] output = new EjbCodeGenerator.Output[]{new EjbCodeGenerator.Output(), null};
      output[0].setOutputFile(this.setClassName() + ".java");
      output[0].setTemplate("/weblogic/ejb/container/cmp/rdbms/codegen/OneToManySet.j");
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

   public String pkVar() {
      return this.varPrefix() + "pk";
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

   public String pmVar() {
      return this.varPrefix() + "pm";
   }

   public String createTxIdVar() {
      return this.varPrefix() + "createTxId";
   }

   public String oldStateVar() {
      return this.varPrefix() + "oldState";
   }

   public String ejbName() {
      return this.bean.getEjbName();
   }

   public String relatedEjbName() {
      CMPBeanDescriptor rbd = this.bean.getRelatedDescriptor(this.cmrField);
      return rbd.getEJBName();
   }

   public String cmrName() {
      return this.cmrField;
   }

   public String wrapperSetFinder() {
      CMPBeanDescriptor rbd = this.bean.getRelatedDescriptor(this.cmrField);
      return rbd.hasLocalClientView() ? "localWrapperSetFinder" : "remoteWrapperSetFinder";
   }

   public String EJBObject() {
      return this.bd.hasLocalClientView() ? "EJBLocalObject" : "EJBObject";
   }

   public String EJBObjectForField() {
      CMPBeanDescriptor rbd = this.bean.getRelatedDescriptor(this.cmrField);
      return rbd.hasLocalClientView() ? "EJBLocalObject" : "EJBObject";
   }

   public String EoWrapper() {
      CMPBeanDescriptor rbd = this.bean.getRelatedDescriptor(this.cmrField);
      return rbd.hasLocalClientView() ? "EloWrapper" : "EoWrapper";
   }

   public String getEJBObject() {
      return this.bd.hasLocalClientView() ? "getEJBLocalObject" : "getEJBObject";
   }

   public String getEJBObjectForField() {
      CMPBeanDescriptor rbd = this.bean.getRelatedDescriptor(this.cmrField);
      return rbd.hasLocalClientView() ? "getEJBLocalObject" : "getEJBObject";
   }

   public String getWLGetEJBObject() {
      return this.bd.hasLocalClientView() ? "__WL_getEJBLocalObject" : "__WL_getEJBObject";
   }

   public String elementInterfaceName() {
      CMPBeanDescriptor rbd = this.bean.getRelatedDescriptor(this.cmrField);
      return rbd.hasLocalClientView() ? rbd.getLocalInterfaceName() : rbd.getRemoteInterfaceName();
   }

   public String remoteInterfaceName() {
      return this.bd.hasLocalClientView() ? this.bd.getLocalInterfaceName() : this.bd.getRemoteInterfaceName();
   }

   public String owningBeanClassName() {
      return this.bd.getGeneratedBeanClassName();
   }

   public String owningBeanInterfaceName() {
      return this.bd.getGeneratedBeanInterfaceName();
   }

   public String relatedBeanClassName() {
      return this.bean.getRelatedBeanClassName(this.cmrField);
   }

   public String relatedBeanInterfaceName() {
      CMPBeanDescriptor rbd = this.bean.getRelatedDescriptor(this.cmrField);
      return rbd.getGeneratedBeanInterfaceName();
   }

   private String declarePkVar() {
      StringBuffer sb = new StringBuffer();
      sb.append(this.bd.getPrimaryKeyClass().getName() + " " + this.pkVar() + " = null;");
      return sb.toString();
   }

   public String perhapsDeclarePkVar() {
      return this.bd.hasComplexPrimaryKey() ? this.declarePkVar() : "";
   }

   public String perhapsAssignPkVar() {
      StringBuffer sb = new StringBuffer();
      if (this.bd.hasComplexPrimaryKey()) {
         RDBMSBean rbean = this.bean.getRelatedRDBMSBean(this.cmrField);
         String rField = this.bean.getRelatedFieldName(this.cmrField);
         String fkTable = rbean.getTableForCmrField(rField);
         boolean hasObjectFields = false;
         Iterator fkColumns = rbean.getForeignKeyColNames(rField).iterator();

         String fkColumn;
         while(fkColumns.hasNext()) {
            fkColumn = (String)fkColumns.next();
            Class fkClass = rbean.getForeignKeyColClass(rField, fkColumn);
            if (Object.class.isAssignableFrom(fkClass)) {
               hasObjectFields = true;
            }
         }

         String fkVariable;
         if (hasObjectFields) {
            sb.append("if (");
            boolean first = true;
            fkColumns = rbean.getForeignKeyColNames(rField).iterator();

            while(fkColumns.hasNext()) {
               fkVariable = (String)fkColumns.next();
               String fkVariable = rbean.variableForField(rField, fkTable, fkVariable);
               Class fkClass = rbean.getForeignKeyColClass(rField, fkVariable);
               if (Object.class.isAssignableFrom(fkClass)) {
                  String expr = this.beanVar() + "." + MethodUtils.getMethodName(fkVariable) + "()";
                  if (!first) {
                     sb.append(" && ");
                  } else {
                     first = false;
                  }

                  sb.append(expr + "!=null");
               }
            }

            sb.append(") {" + EOL);
         }

         sb.append(this.pkVar() + " = new " + this.bd.getPrimaryKeyClass().getName() + "();" + EOL);

         String lhs;
         String rhs;
         for(fkColumns = rbean.getForeignKeyColNames(rField).iterator(); fkColumns.hasNext(); sb.append(lhs + " = " + rhs + ";" + EOL)) {
            fkColumn = (String)fkColumns.next();
            fkVariable = rbean.variableForField(rField, fkTable, fkColumn);
            rbean.getForeignKeyColClass(rField, fkColumn);
            String pkField = rbean.getRelatedPkFieldName(rField, fkColumn);
            Class pkFieldClass = this.bd.getFieldClass(pkField);
            lhs = this.pkVar() + "." + pkField;
            rhs = this.beanVar() + "." + MethodUtils.getMethodName(fkVariable) + "()";
            if (pkFieldClass.isPrimitive()) {
               rhs = MethodUtils.convertToPrimitive(pkFieldClass, rhs);
            }
         }

         if (hasObjectFields) {
            sb.append("}" + EOL);
         }
      }

      return sb.toString();
   }

   public String pkVarForBean() {
      StringBuffer sb = new StringBuffer();
      if (this.bd.hasComplexPrimaryKey()) {
         sb.append(this.pkVar());
      } else {
         if (debugLogger.isDebugEnabled()) {
            debug("cmrField is: " + this.cmrField);
         }

         RDBMSBean relBean = this.bean.getRelatedRDBMSBean(this.cmrField);
         String relField = this.bean.getRelatedFieldName(this.cmrField);
         String fkTable = relBean.getTableForCmrField(relField);
         String fkColumn = (String)relBean.getForeignKeyColNames(relField).iterator().next();
         String fkVariable = relBean.variableForField(relField, fkTable, fkColumn);
         Class fkClass = relBean.getForeignKeyColClass(relField, fkColumn);
         sb.append(this.perhapsConvertPrimitive(fkClass, this.beanVar() + "." + MethodUtils.getMethodName(fkVariable) + "()"));
      }

      return sb.toString();
   }

   private String setterMethodName(String fieldName) {
      return "set" + fieldName.substring(0, 1).toUpperCase(Locale.ENGLISH) + fieldName.substring(1);
   }

   public String relatedBeanSetMethod() {
      StringBuffer sb = new StringBuffer();
      sb.append(this.setterMethodName(this.bean.getRelatedFieldName(this.cmrField)));
      return sb.toString();
   }

   public String packageName() {
      assert this.packageName != null;

      return this.packageName;
   }

   public String packageStatement() {
      return this.packageName != null && !this.packageName.equals("") ? "package " + this.packageName + ";" : "";
   }

   public String setClassName() {
      return ClassUtils.setClassName(this.bd, this.cmrField);
   }

   public String iteratorClassName() {
      return ClassUtils.iteratorClassName(this.bd, this.cmrField);
   }

   public String perhapsInvokeFinder() throws CodeGenerationException {
      if (this.bean.getLoadRelatedBeansFromDbInPostCreate()) {
         return this.readOnlyFinderRunsInItsOwnTransaction();
      } else {
         StringBuffer sb = new StringBuffer();
         sb.append(this.parse(this.getProductionRule("loadIfNotPostCreate")));
         sb.append("\n else { \n");
         sb.append(this.readOnlyFinderRunsInItsOwnTransaction());
         sb.append("\n } \n");
         return sb.toString();
      }
   }

   public String isLocal() {
      CMPBeanDescriptor rbd = this.bean.getRelatedDescriptor(this.cmrField);
      return rbd.hasLocalClientView() ? "true" : "false";
   }

   public String perhapsImplementQueryCachingMethods() throws CodeGenerationException {
      return this.parse(this.getProductionRule("queryCachingMethods"));
   }

   public String perhapsPopulateFromQueryCache() {
      if (!this.bean.isQueryCachingEnabledForCMRField(this.cmrField)) {
         return "";
      } else {
         StringBuffer sb = new StringBuffer();
         sb.append(this.cacheVar()).append(" = getFromQueryCache();").append(EOL);
         sb.append("if (").append(this.cacheVar()).append(" == null) {").append(EOL);
         return sb.toString();
      }
   }

   public String perhapsPutInQueryCache() {
      if (!this.bean.isQueryCachingEnabledForCMRField(this.cmrField)) {
         return "";
      } else {
         StringBuffer sb = new StringBuffer("}");
         sb.append(EOL);
         sb.append("putInQueryCache(null);").append(EOL);
         return sb.toString();
      }
   }

   public String readOnlyFinderRunsInItsOwnTransaction() {
      CMPBeanDescriptor rbd = this.bean.getRelatedDescriptor(this.cmrField);
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
      CMPBeanDescriptor rbd = this.bean.getRelatedDescriptor(this.cmrField);
      return !rbd.isReadOnly() && rbd.getConcurrencyStrategy() != 4 ? "false" : "true";
   }

   public String perhapsDeclareReadWriteVars() {
      StringBuffer sb = new StringBuffer();
      CMPBeanDescriptor rbd = this.bean.getRelatedDescriptor(this.cmrField);
      if (!rbd.isReadOnly() && rbd.getConcurrencyStrategy() != 4) {
         sb.append("private Set " + this.addVar() + ";" + EOL);
         sb.append("private Set " + this.removeVar() + ";" + EOL);
         sb.append("private RDBMSPersistenceManager " + this.pmVar() + ";" + EOL);
      }

      return sb.toString();
   }

   public String perhapsInitReadWriteVars() {
      StringBuffer sb = new StringBuffer();
      CMPBeanDescriptor rbd = this.bean.getRelatedDescriptor(this.cmrField);
      if (!rbd.isReadOnly() && rbd.getConcurrencyStrategy() != 4) {
         sb.append(this.addVar() + " = new ArraySet(10);" + EOL);
         sb.append(this.removeVar() + " = new ArraySet(10);" + EOL);
         sb.append(this.pmVar() + " = (RDBMSPersistenceManager)" + this.creatorVar() + ".__WL_getPersistenceManager();" + EOL);
      }

      return sb.toString();
   }

   public String perhapsReconcileReadWriteChanges() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      CMPBeanDescriptor rbd = this.bean.getRelatedDescriptor(this.cmrField);
      if (!rbd.isReadOnly() && rbd.getConcurrencyStrategy() != 4) {
         sb.append(this.parse(this.getProductionRule("reconcileReadWriteChanges")));
      }

      return sb.toString();
   }

   public String perhapsDoAddForReadWrite() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      CMPBeanDescriptor rbd = this.bean.getRelatedDescriptor(this.cmrField);
      if (!rbd.isReadOnly() && rbd.getConcurrencyStrategy() != 4) {
         sb.append(this.parse(this.getProductionRule("doAddForReadWrite")));
      }

      return sb.toString();
   }

   public String perhapsDoRemoveForReadWrite() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      CMPBeanDescriptor rbd = this.bean.getRelatedDescriptor(this.cmrField);
      if (!rbd.isReadOnly() && rbd.getConcurrencyStrategy() != 4) {
         sb.append(this.parse(this.getProductionRule("doRemoveForReadWrite")));
      }

      return sb.toString();
   }

   private static void debug(String s) {
      debugLogger.debug("[OneToManyGenerator] " + s);
   }
}
