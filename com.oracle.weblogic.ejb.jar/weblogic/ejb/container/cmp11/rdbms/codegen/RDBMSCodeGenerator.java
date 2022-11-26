package weblogic.ejb.container.cmp11.rdbms.codegen;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.CreateException;
import javax.ejb.EntityContext;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.cmp11.rdbms.RDBMSBean;
import weblogic.ejb.container.cmp11.rdbms.finders.Finder;
import weblogic.ejb.container.ejbc.EJBCException;
import weblogic.ejb.container.ejbc.codegen.MethodSignature;
import weblogic.ejb.container.persistence.spi.CMPBeanDescriptor;
import weblogic.ejb.container.persistence.spi.CMPCodeGenerator;
import weblogic.ejb.container.utils.ClassUtils;
import weblogic.ejb.container.utils.MethodUtils;
import weblogic.utils.Debug;
import weblogic.utils.Getopt2;
import weblogic.utils.StackTraceUtilsClient;
import weblogic.utils.StringUtils;
import weblogic.utils.compiler.CodeGenerationException;
import weblogic.utils.compiler.CodeGenerator;
import weblogic.utils.string.Sprintf;

public final class RDBMSCodeGenerator extends CMPCodeGenerator {
   private static final int MAX_LINE_CHARS = 80;
   private static final int THRESHOLD = 7;
   private static final DebugLogger debugLogger;
   private RDBMSBean beanData = null;
   private Map variableToClass = null;
   private List finderList = null;
   private List primaryKeyFieldList = null;
   private FinderMethodInfo finderMethodInfo = null;
   private int preparedStatementParamIndex = -1;
   private Map parameterMap = null;
   private List fieldList = null;
   private List nonPrimaryKeyFieldList = null;
   private SnapshotFieldInfo[] snapshotFieldInfo;
   private boolean useTunedUpdates;
   private int level = 0;
   private Class[] immutableClasses = new Class[]{Boolean.class, Byte.class, Character.class, Double.class, Float.class, Integer.class, Long.class, Short.class, String.class, BigDecimal.class, BigInteger.class};
   private int current_index;
   private SnapshotFieldInfo current;

   public RDBMSCodeGenerator(Getopt2 opts) {
      super(opts);
   }

   public void setRDBMSBean(RDBMSBean bean) {
      if (debugLogger.isDebugEnabled()) {
         debug("RDBMSCodeGenerator.setRDBMSBean(" + bean + ")");
      }

      assert bean != null;

      this.beanData = bean;
      this.variableToClass = new HashMap();
      Iterator iter = bean.getCmpFieldNames().iterator();

      while(iter.hasNext()) {
         String cmpField = (String)iter.next();
         this.variableToClass.put(cmpField, this.bd.getFieldClass(cmpField));
      }

   }

   public void setFinderList(List finders) {
      if (debugLogger.isDebugEnabled()) {
         debug("RDBMSCodeGenerator.setFinderList(" + finders + ")");
      }

      this.finderList = finders;
   }

   public void setCMFields(List cmFields) {
      this.nonPrimaryKeyFieldList = this.deriveNonPrimaryKeyFields(cmFields);
      this.fieldList = new ArrayList();
      this.fieldList.addAll(this.primaryKeyFieldList);
      this.fieldList.addAll(this.nonPrimaryKeyFieldList);
   }

   public void setPrimaryKeyFields(List primaryKeyFields) {
      this.primaryKeyFieldList = primaryKeyFields;
   }

   protected List typeSpecificTemplates() {
      List templateNames = new ArrayList();
      templateNames.add("weblogic/ejb/container/cmp11/rdbms/codegen/template.j");
      return templateNames;
   }

   public void setParameterMap(Map map) {
      this.parameterMap = map;
   }

   private List deriveNonPrimaryKeyFields(List cmFields) {
      Debug.assertion(this.primaryKeyFieldList != null);
      Debug.assertion(cmFields != null);
      List newList = new ArrayList();
      Iterator it = cmFields.iterator();

      while(it.hasNext()) {
         String fName = (String)it.next();
         if (!this.primaryKeyFieldList.contains(fName)) {
            newList.add(fName);
         }
      }

      return newList;
   }

   protected void prepare(CodeGenerator.Output output) throws EJBCException, ClassNotFoundException {
      super.prepare(output);

      assert this.beanData != null;

      assert this.finderList != null;

      assert this.primaryKeyFieldList != null;

      assert this.fieldList != null;

      assert this.parameterMap != null;

      assert this.nonPrimaryKeyFieldList != null;

   }

   public static String varPrefix() {
      return "__WL_";
   }

   public String debugVar() {
      return varPrefix() + "debugLogger";
   }

   public String debugEnabled() {
      return this.debugVar() + ".isDebugEnabled()";
   }

   public String debugSay() {
      return varPrefix() + "debug";
   }

   public String beanVar() {
      return varPrefix() + "bean";
   }

   public String pkVar() {
      return varPrefix() + "pk";
   }

   public String eoVar() {
      return varPrefix() + "eo";
   }

   public String ctxVar() {
      return varPrefix() + "ctx";
   }

   public String conVar() {
      return varPrefix() + "con";
   }

   public String rsVar() {
      return varPrefix() + "rs";
   }

   public String rsInfoVar() {
      return varPrefix() + "rsInfo";
   }

   public String stmtVar() {
      return varPrefix() + "stmt";
   }

   public String pmVar() {
      return varPrefix() + "pm";
   }

   public String keyVar() {
      return varPrefix() + "key";
   }

   public String numVar() {
      return varPrefix() + "num";
   }

   public String queryVar() {
      return varPrefix() + "query";
   }

   public String iVar() {
      return varPrefix() + "i";
   }

   public String countVar() {
      return varPrefix() + "count";
   }

   public String stringVar() {
      return varPrefix() + "stringVar";
   }

   public String stringVar(String fld) {
      return varPrefix() + "stringVar_" + fld;
   }

   public String sqlTimestampVar() {
      return varPrefix() + "sqlTimestampVar";
   }

   public String sqlTimestampVar(String fld) {
      return varPrefix() + "sqlTimestampVar_" + fld;
   }

   public String byteArrayVar() {
      return varPrefix() + "byteArrayVar";
   }

   public String byteArrayVar(String fld) {
      return varPrefix() + "byteArrayVar_" + fld;
   }

   public String isModifiedVar() {
      return "isModified";
   }

   public String createMethodName() {
      return varPrefix() + "create";
   }

   public String existsMethodName() {
      return varPrefix() + "exists";
   }

   public String standardCatch() {
      StringBuffer sb = new StringBuffer();
      sb.append(this.lvl(2) + "} catch (RuntimeException e) {" + EOL);
      sb.append(this.upLvl() + "if (" + this.debugEnabled() + ") " + this.debugSay() + "(\"throwing runtime exception\");" + EOL);
      sb.append(this.lvl() + "throw e;" + EOL);
      sb.append(this.dnLvl() + "}" + EOL);
      sb.append(this.lvl() + "catch (Exception ex) {" + EOL);
      sb.append(this.upLvl() + "if (" + this.debugEnabled() + ") " + this.debugSay() + "(\"throwing ejbeception\");" + EOL);
      sb.append(this.lvl() + "throw new PersistenceRuntimeException(ex);" + EOL);
      sb.append(this.dnLvl() + "}" + EOL);
      return sb.toString();
   }

   public String declareEntityContextVar() {
      return this.lvl(0) + "private EntityContext " + this.ctxVar() + ";";
   }

   public String declareIsModified() {
      StringBuffer sb = new StringBuffer();
      sb.append("\tprivate boolean[] ");
      sb.append(this.isModifiedVar());
      sb.append(" = new boolean[");
      sb.append(this.fieldList.size());
      sb.append("];");
      sb.append(EOL);
      return sb.toString();
   }

   public String tableName() {
      String tableName = "";
      if (this.beanData.getUseQuotedNames()) {
         if (this.beanData.getSchemaName() != null && !this.beanData.getSchemaName().equals("")) {
            tableName = tableName + '"' + this.beanData.getSchemaName() + '"' + ".";
         }

         tableName = tableName + '"' + this.beanData.getTableName() + '"';
      } else {
         if (this.beanData.getSchemaName() != null && !this.beanData.getSchemaName().equals("")) {
            tableName = tableName + this.beanData.getSchemaName() + ".";
         }

         tableName = tableName + this.beanData.getTableName();
      }

      return tableName;
   }

   public String getSimpleBeanClassName() {
      return MethodUtils.tail(this.bd.getGeneratedBeanClassName());
   }

   public String declareNoArgsConstructor() {
      StringBuffer sb = new StringBuffer();
      CMPCodeGenerator.Output output = (CMPCodeGenerator.Output)this.currentOutput;
      CMPBeanDescriptor bd = output.getCMPBeanDescriptor();
      Class beanClass = bd.getBeanClass();

      try {
         Constructor constr = beanClass.getConstructor();
         sb.append(this.lvl(1) + "public " + MethodUtils.tail(bd.getGeneratedBeanClassName()));
         sb.append("()");
         Class[] excepts = constr.getExceptionTypes();
         if (excepts.length > 0) {
            sb.append(" throws ");

            for(int i = 0; i < excepts.length; ++i) {
               sb.append(this.javaCodeForType(excepts[i]));
               if (i < excepts.length - 1) {
                  sb.append(", ");
               }
            }
         }
      } catch (NoSuchMethodException var8) {
         throw new AssertionError("Unable to find constructor on class '" + beanClass.getName() + "'.");
      }

      return sb.toString();
   }

   public String declareBeanMethod(String methodName, Class[] params) {
      StringBuffer sb = new StringBuffer();
      CMPCodeGenerator.Output output = (CMPCodeGenerator.Output)this.currentOutput;
      Class beanClass = output.getCMPBeanDescriptor().getBeanClass();

      try {
         MethodSignature sig = new MethodSignature(beanClass.getMethod(methodName, params));
         sb.append(this.lvl(1) + sig.toString());
      } catch (NoSuchMethodException var7) {
         throw new AssertionError("Unable to find '" + methodName + "' method on class '" + beanClass.getName() + "'.");
      }

      return sb.toString();
   }

   public String initializePersistentVars() {
      StringBuffer sb = new StringBuffer();
      Iterator vars = this.variableToClass.keySet().iterator();

      while(vars.hasNext()) {
         String varName = (String)vars.next();
         Class varClass = (Class)this.variableToClass.get(varName);
         sb.append(this.lvl(2) + varName + " = " + ClassUtils.getDefaultValue(varClass) + ";" + EOL);
      }

      return sb.toString();
   }

   public String copyFromMethodBody() {
      StringBuffer sb = new StringBuffer();
      CMPCodeGenerator.Output output = (CMPCodeGenerator.Output)this.currentOutput;
      CMPBeanDescriptor bd = output.getCMPBeanDescriptor();
      sb.append(this.lvl(2) + bd.getGeneratedBeanClassName() + " " + this.beanVar() + " = null;" + EOL);
      sb.append(this.lvl() + "try {" + EOL);
      sb.append(this.upLvl() + this.beanVar() + " = (" + bd.getGeneratedBeanClassName() + ")otherBean;" + EOL);
      sb.append(this.standardCatch());
      Iterator cmpFields = bd.getCMFieldNames().iterator();

      while(cmpFields.hasNext()) {
         String fieldName = (String)cmpFields.next();
         sb.append(fieldName + " = " + this.beanVar() + "." + fieldName + ";" + EOL);
      }

      return sb.toString();
   }

   public String initializePersistentVarsForBeanVar() {
      StringBuffer sb = new StringBuffer();
      if (this.finderMethodInfo.loadBean) {
         Iterator vars = this.variableToClass.keySet().iterator();

         while(vars.hasNext()) {
            String varName = (String)vars.next();
            Class varClass = (Class)this.variableToClass.get(varName);
            sb.append(this.lvl(2) + this.beanVar() + "." + varName + " = " + ClassUtils.getDefaultValue(varClass) + ";" + EOL);
         }
      }

      return sb.toString();
   }

   public String declareSetEntityContextMethod() {
      return this.declareBeanMethod("setEntityContext", new Class[]{EntityContext.class});
   }

   public String declareEjbLoadMethod() {
      return this.declareBeanMethod("ejbLoad", new Class[0]);
   }

   private boolean throwsCreateException(Method method) {
      Class[] excClasses = method.getExceptionTypes();

      for(int i = 0; i < excClasses.length; ++i) {
         if (excClasses[i].isAssignableFrom(CreateException.class)) {
            return true;
         }
      }

      return false;
   }

   public String ejbLoadExceptionList() {
      return this.ejbCallbackMethodExceptionList("ejbLoad");
   }

   public String ejbStoreExceptionList() {
      return this.ejbCallbackMethodExceptionList("ejbStore");
   }

   public String ejbCallbackMethodExceptionList(String methodName) {
      StringBuffer sb = new StringBuffer();
      CMPCodeGenerator.Output output = (CMPCodeGenerator.Output)this.currentOutput;
      CMPBeanDescriptor bd = output.getCMPBeanDescriptor();
      Class beanClass = bd.getBeanClass();

      try {
         Method method = beanClass.getMethod(methodName);

         assert method != null;

         Class[] excepts = method.getExceptionTypes();
         if (excepts.length > 0) {
            sb.append(" throws ");

            for(int i = 0; i < excepts.length; ++i) {
               sb.append(this.javaCodeForType(excepts[i]));
               if (i < excepts.length - 1) {
                  sb.append(", ");
               }
            }
         }
      } catch (NoSuchMethodException var9) {
         throw new AssertionError("Unable to find ejbLoad on class '" + beanClass.getName() + "'.");
      }

      return sb.toString();
   }

   public String implementEjbCreateMethods() {
      StringBuffer sb = new StringBuffer();
      CMPCodeGenerator.Output output = (CMPCodeGenerator.Output)this.currentOutput;
      CMPBeanDescriptor bd = output.getCMPBeanDescriptor();
      Class beanClass = bd.getBeanClass();
      Method[] allMethods = beanClass.getMethods();

      for(int i = 0; i < allMethods.length; ++i) {
         Method method = allMethods[i];
         if (method.getName().equals("ejbCreate")) {
            MethodSignature sig = new MethodSignature(method);
            sb.append(this.lvl(1) + sig.toString() + " {" + EOL);
            sb.append("int oldState = __WL_method_state;" + EOL + "try {" + EOL);
            sb.append("__WL_method_state = STATE_EJB_CREATE;" + EOL);
            sb.append("__WL_initialize();" + EOL);
            sb.append(this.upLvl() + "super.ejbCreate(");
            sb.append(sig.getParametersAsArgs());
            sb.append(");" + EOL + EOL);
            Class returnType = sig.getReturnType();
            sb.append(this.lvl() + "try {" + EOL);
            sb.append(this.upLvl() + "return (" + ClassUtils.classToJavaSourceType(returnType) + ")" + this.createMethodName() + "();" + EOL);
            if (this.throwsCreateException(method)) {
               sb.append(this.dnLvl() + "} catch (javax.ejb.CreateException ce) {" + EOL);
               sb.append("System.out.println(\"throwing create exception.\");" + EOL);
               sb.append(this.upLvl() + "throw ce;" + EOL);
            }

            sb.append(this.standardCatch());
            sb.append("} finally { __WL_method_state = oldState; }" + EOL);
            sb.append(this.dnLvl() + "}" + EOL + EOL);
         }
      }

      return sb.toString();
   }

   public String implementEjbRemoveMethod() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      sb.append(this.lvl(1) + this.declareBeanMethod("ejbRemove", new Class[0]) + " {" + EOL);
      sb.append(this.parse(this.getProductionRule("implementEjbRemoveMethodBody")));
      sb.append(this.lvl(1) + "}");
      return sb.toString();
   }

   public String implementEjbStoreMethod() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      sb.append(this.lvl(1) + this.declareBeanMethod("ejbStore", new Class[0]) + " {" + EOL);
      sb.append(this.parse(this.getProductionRule("implementEjbStoreMethodBody")));
      sb.append(this.lvl(1) + "}");
      return sb.toString();
   }

   public String implementFinderMethods() throws EJBCException {
      StringBuffer sb = new StringBuffer(100);

      Method currMethod;
      for(Iterator finders = this.finderList.iterator(); finders.hasNext(); sb.append(this.implementFinderMethod(currMethod))) {
         currMethod = (Method)finders.next();
         if (debugLogger.isDebugEnabled()) {
            debug("generating finder: " + currMethod);
         }
      }

      return sb.toString();
   }

   private String implementFinderMethod(Method method) throws EJBCException {
      if (debugLogger.isDebugEnabled()) {
         debug("implementFinderMethod(" + method + ") called.");
      }

      CMPCodeGenerator.Output output = (CMPCodeGenerator.Output)this.currentOutput;
      CMPBeanDescriptor bd = output.getCMPBeanDescriptor();

      assert bd != null;

      this.finderMethodInfo = new FinderMethodInfo();
      this.finderMethodInfo.method = method;
      this.finderMethodInfo.finder = this.beanData.getFinderForMethod(method);
      this.finderMethodInfo.loadBean = bd.getFindersLoadBean();

      assert this.finderMethodInfo.method != null;

      if (this.finderMethodInfo.finder == null) {
         throw new EJBCException("Could not find finder descriptor for method with signature " + method);
      } else {
         StringBuffer sb = new StringBuffer();
         String methodDecl = this.getFinderMethodDeclaration(method, true);
         sb.append(methodDecl);
         sb.append("{" + EOL);

         try {
            if (this.isMultiFinder(method)) {
               sb.append(this.parse(this.getProductionRule("finderMethodBodyMulti")));
            } else {
               sb.append(this.parse(this.getProductionRule("finderMethodBodyScalar")));
            }
         } catch (CodeGenerationException var8) {
            if (debugLogger.isDebugEnabled()) {
               debug("finderMethod cought CodeGenerationException : " + StackTraceUtilsClient.throwable2StackTrace(var8));
            }

            String text = "Could not produce production rule for this finder.";
            EJBLogger.logStackTraceAndMessage(text, var8);
            throw new EJBCException(text);
         }

         sb.append("" + EOL + "}" + EOL);
         this.finderMethodInfo = null;
         return sb.toString();
      }
   }

   private boolean isMultiFinder(Method method) {
      return Collection.class.isAssignableFrom(method.getReturnType()) || Enumeration.class.isAssignableFrom(method.getReturnType());
   }

   public String finderMethodName() {
      assert this.finderMethodInfo != null;

      return this.finderMethodInfo.method.getName();
   }

   public String finderQuery() {
      if (debugLogger.isDebugEnabled()) {
         debug("finderQuery() called for method " + this.finderMethodInfo.method);
      }

      assert this.finderMethodInfo != null;

      assert this.finderMethodInfo.finder != null;

      assert this.finderMethodInfo.finder.getSQLQuery() != null;

      String query = this.finderMethodInfo.finder.getSQLQuery();
      if (debugLogger.isDebugEnabled()) {
         debug("finder query is: " + query);
      }

      return query;
   }

   public String declareResultVar() {
      StringBuffer sb = new StringBuffer();
      if (this.finderMethodInfo.loadBean) {
         sb.append(this.declareBeanVar());
         sb.append(this.declareEoVar());
      } else {
         sb.append(this.lvl(2) + "Object " + this.pkVar() + " = null;");
      }

      return sb.toString();
   }

   public String allocateResultVar() {
      StringBuffer sb = new StringBuffer();
      if (this.finderMethodInfo.loadBean) {
         sb.append(this.getBeanFromRS());
      }

      return sb.toString();
   }

   public String finderGetEo() {
      StringBuffer sb = new StringBuffer();
      if (this.finderMethodInfo.loadBean) {
         sb.append(this.eoVar() + " = " + this.pmVar() + ".finderGetEoFromBeanOrPk(" + this.beanVar() + ", " + this.pkVar() + ", __WL_getIsLocal());");
      }

      return sb.toString();
   }

   public String resultVar() {
      StringBuffer sb = new StringBuffer();
      if (this.finderMethodInfo.loadBean) {
         sb.append(this.eoVar());
      } else {
         sb.append(this.pkVar());
      }

      return sb.toString();
   }

   public String declareEoVar() {
      return "Object " + this.eoVar() + " = null;" + EOL;
   }

   public String getGeneratedBeanClassName() {
      return this.bd.getGeneratedBeanClassName();
   }

   public String declareBeanVar() {
      return this.lvl(2) + this.bd.getGeneratedBeanClassName() + " " + this.beanVar() + " = null;" + EOL;
   }

   public String declarePkVar() {
      return this.lvl(2) + this.pk_class() + " " + this.pkVar() + " = null;";
   }

   public String allocatePkVar() {
      StringBuffer sb = new StringBuffer();
      CMPCodeGenerator.Output output = (CMPCodeGenerator.Output)this.currentOutput;
      CMPBeanDescriptor bd = output.getCMPBeanDescriptor();
      if (bd.hasComplexPrimaryKey()) {
         sb.append(this.lvl(2) + this.pkVar() + " = ");
         sb.append("new " + this.pk_class() + "();");
      }

      return sb.toString();
   }

   public String allocateBeanVar() {
      CMPCodeGenerator.Output output = (CMPCodeGenerator.Output)this.currentOutput;
      CMPBeanDescriptor bd = output.getCMPBeanDescriptor();
      return this.lvl(2) + this.beanVar() + " = (" + bd.getGeneratedBeanClassName() + ")" + this.pmVar() + ".getBeanFromPool();" + EOL;
   }

   public String getBeanFromRS() {
      CMPCodeGenerator.Output output = (CMPCodeGenerator.Output)this.currentOutput;
      CMPBeanDescriptor bd = output.getCMPBeanDescriptor();
      StringBuffer sb = new StringBuffer();
      sb.append("RSInfo " + this.rsInfoVar() + " = new RSInfoImpl(" + this.rsVar() + ", 0, 0, " + this.pkVar() + ");" + EOL);
      sb.append(this.beanVar() + " = (" + bd.getGeneratedBeanClassName() + ")" + this.pmVar() + ".getBeanFromRS(" + this.pkVar() + ", " + this.rsInfoVar() + ");" + EOL);
      return sb.toString();
   }

   public String getPkVarFromRS() {
      StringBuffer sb = new StringBuffer();
      if (this.finderMethodInfo.loadBean) {
         sb.append("Object ");
      }

      sb.append(this.pkVar() + " = " + this.getPKFromRSMethodName() + this.getPKFromRSMethodParams() + EOL);
      return sb.toString();
   }

   public String implementGetPKFromRSStaticMethod() {
      StringBuffer sb = new StringBuffer();
      sb.append(EOL);
      sb.append("public static Object " + this.getPKFromRSMethodName() + "(java.sql.ResultSet " + this.rsVar() + ") " + EOL);
      sb.append("throws java.sql.SQLException, java.lang.Exception" + EOL);
      sb.append("{" + EOL);
      sb.append(this.declarePkVar() + EOL);
      sb.append(this.allocatePkVar() + EOL + EOL);
      this.assignToFields(this.primaryKeyFieldList, sb, 1, this.pkVar(), this.bd.hasComplexPrimaryKey());
      sb.append("return " + this.pkVar() + ";" + EOL);
      sb.append("}" + EOL);
      return sb.toString();
   }

   public String getPKFromRSMethodName() {
      return varPrefix() + "getPKFromRS";
   }

   private String getPKFromRSMethodParams() {
      return "(" + this.rsVar() + ");";
   }

   public String finderColumnsSql() {
      StringBuffer sb = new StringBuffer();
      List fNames = null;
      if (this.finderMethodInfo.loadBean) {
         fNames = this.fieldList;
      } else {
         fNames = this.primaryKeyFieldList;
      }

      Iterator iter = fNames.iterator();

      for(int i = 0; iter.hasNext(); ++i) {
         String fieldName = (String)iter.next();
         String columnName = this.beanData.getColumnForField(fieldName);

         assert columnName != null;

         sb.append(columnName);
         if (i < fNames.size() - 1) {
            sb.append(", ");
         }
      }

      return sb.toString();
   }

   public String implementGetPrimaryKey() {
      StringBuffer sb = new StringBuffer();
      sb.append(this.lvl(2) + this.declarePkVar() + EOL);
      sb.append(this.lvl() + this.allocatePkVar() + EOL);
      sb.append(this.lvl() + this.assignPkFieldsToPkVar() + EOL);
      sb.append(this.lvl() + "return " + this.pkVar() + ";" + EOL);
      return sb.toString();
   }

   public String implementSetPrimaryKey() {
      return this.assignPkVarToPkFields() + EOL;
   }

   public String assignPkFieldsToPkVar() {
      StringBuffer sb = new StringBuffer();
      Iterator pkFieldNames = this.primaryKeyFieldList.iterator();
      if (this.bd.hasComplexPrimaryKey()) {
         while(pkFieldNames.hasNext()) {
            String fname = (String)pkFieldNames.next();
            sb.append(this.lvl(3) + this.pkVar() + "." + fname + " = this." + fname + ";");
         }
      } else {
         sb.append(this.lvl(3) + this.pkVar() + " = this." + (String)pkFieldNames.next() + ";");
      }

      return sb.toString();
   }

   public String assignPkVarToPkFields() {
      StringBuffer sb = new StringBuffer();
      Iterator pkFieldNames = this.bd.getPrimaryKeyFieldNames().iterator();
      if (this.bd.hasComplexPrimaryKey()) {
         while(pkFieldNames.hasNext()) {
            String fname = (String)pkFieldNames.next();
            sb.append("this." + fname + " = " + this.pkVar() + "." + fname + ";" + EOL);
         }
      } else {
         sb.append("this." + (String)pkFieldNames.next() + " = " + this.pkVar() + ";");
      }

      return sb.toString();
   }

   public boolean isFindByPrimaryKey(Finder finder) {
      CMPCodeGenerator.Output output = (CMPCodeGenerator.Output)this.currentOutput;
      CMPBeanDescriptor bd = output.getCMPBeanDescriptor();
      if (!finder.getName().equals("findByPrimaryKey")) {
         return false;
      } else {
         Iterator iter = finder.getParameterTypes();
         if (!iter.hasNext()) {
            return false;
         } else {
            String pType = (String)iter.next();
            if (!pType.equals(bd.getPrimaryKeyClass().getName())) {
               return false;
            } else {
               return !iter.hasNext();
            }
         }
      }
   }

   public String setFinderQueryParams() throws EJBCException {
      StringBuffer sb = new StringBuffer();
      CMPCodeGenerator.Output output = (CMPCodeGenerator.Output)this.currentOutput;
      CMPBeanDescriptor bd = output.getCMPBeanDescriptor();
      Method method = this.finderMethodInfo.method;
      Finder finder = this.beanData.getFinderForMethod(method);

      assert finder != null;

      assert finder.getSQLQuery() != null;

      Class[] parameterTypes = null;
      String[] parameterNames = null;
      boolean isFindByPrimaryKey = this.isFindByPrimaryKey(finder);
      int queryVar;
      int paramIndex;
      if (isFindByPrimaryKey && bd.hasComplexPrimaryKey()) {
         String[] fieldNames = (String[])((String[])this.primaryKeyFieldList.toArray(new String[0]));
         parameterTypes = new Class[fieldNames.length];
         parameterNames = new String[fieldNames.length];

         for(paramIndex = 0; paramIndex < parameterTypes.length; ++paramIndex) {
            parameterTypes[paramIndex] = bd.getFieldClass(fieldNames[paramIndex]);
            parameterNames[paramIndex] = MethodUtils.getParameterName(0) + "." + fieldNames[paramIndex];
         }
      } else {
         parameterTypes = method.getParameterTypes();
         parameterNames = new String[parameterTypes.length];

         for(queryVar = 0; queryVar < parameterNames.length; ++queryVar) {
            parameterNames[queryVar] = MethodUtils.getParameterName(queryVar);
         }
      }

      for(queryVar = 1; queryVar <= finder.getVariableCount(); ++queryVar) {
         paramIndex = finder.getParameterIndex(queryVar);
         if (!parameterTypes[paramIndex].isPrimitive()) {
            this.addNullCheck("", sb, parameterNames[paramIndex], TypeUtils.getSQLTypeForClass(parameterTypes[paramIndex]), queryVar);
         }

         Object[] args = new Object[]{TypeUtils.getPreparedStatementMethodPostfix(parameterTypes[paramIndex]), new Integer(queryVar), this.getParameterSetterName(parameterTypes[paramIndex], parameterNames[paramIndex])};
         Sprintf.sprintf(this.stmtVar() + ".set%s(%d, %s);" + EOL, args, sb);
      }

      return sb.toString();
   }

   private void addNullCheck(String prefix, StringBuffer sb, String value, String fieldName, String paramIdx) {
      sb.append(prefix + "if(!" + this.pmVar() + ".setParamNull(" + this.stmtVar() + ", " + paramIdx + ", " + value + ", \"" + fieldName + "\")) {" + EOL);
   }

   private void addNullCheck(String prefix, StringBuffer sb, String value, int sqlType, int variableIndex) {
      sb.append("");
      sb.append("if (" + value + " == null) {" + EOL);
      sb.append("  " + this.stmtVar() + ".setNull(" + variableIndex + "," + sqlType + ");" + EOL);
      sb.append("} else " + EOL);
   }

   public String assignResultVar() {
      StringBuffer sb = new StringBuffer();
      this.assignToFields(this.fieldList, sb, 1, this.beanVar(), true, true);
      return sb.toString();
   }

   private void assignToFields(List fields, StringBuffer sb, int idx, String varName, boolean varIsCompound) {
      this.assignToFields(fields, sb, idx, varName, varIsCompound, false);
   }

   private void assignToFields(List fields, StringBuffer sb, int idx, String varName, boolean varIsCompound, boolean excludePKIfNull) {
      CMPCodeGenerator.Output output = (CMPCodeGenerator.Output)this.currentOutput;
      CMPBeanDescriptor bd = output.getCMPBeanDescriptor();
      Iterator iter = fields.iterator();

      while(iter.hasNext()) {
         String field = (String)iter.next();
         String value = this.convertToField(field, idx++);
         Class type = bd.getFieldClass(field);
         String fieldVar = null;
         if (excludePKIfNull && this.primaryKeyFieldList.contains(field)) {
            sb.append("if (" + this.pkVar() + " == null) {" + EOL);
         }

         if (varIsCompound) {
            fieldVar = varName + "." + field;
         } else {
            fieldVar = varName;
         }

         if (type != Character.class && type != Character.TYPE) {
            if (type == Date.class) {
               sb.append("java.sql.Timestamp " + this.sqlTimestampVar(field) + " = " + value + ";" + EOL);
               sb.append("  if (" + this.rsVar() + ".wasNull() || " + this.sqlTimestampVar(field) + "==null) { " + EOL);
               sb.append("    " + fieldVar + " = null");
               sb.append(";" + EOL);
               sb.append("  }" + EOL);
               sb.append("  else { " + EOL);
               sb.append("    " + fieldVar + " = ");
               sb.append("new java.util.Date(" + this.sqlTimestampVar(field) + ".getTime());" + EOL);
               sb.append("  }" + EOL);
            } else if (!ClassUtils.isValidSQLType(type)) {
               sb.append(this.lvl() + "byte[] " + this.byteArrayVar(field) + " = " + value + ";" + EOL);
               sb.append(this.lvl() + "if (" + this.debugEnabled() + ") {" + EOL);
               sb.append(this.upLvl() + this.debugSay() + "(\"returned bytes\" + " + this.byteArrayVar(field) + ");" + EOL);
               sb.append(this.lvl() + "if (" + this.byteArrayVar(field) + "!=null) {" + EOL);
               sb.append(this.upLvl() + this.debugSay() + "(\"length- \" + " + this.byteArrayVar(field) + ".length);" + EOL);
               sb.append(this.dnLvl() + "}" + EOL);
               sb.append(this.dnLvl() + "}" + EOL);
               sb.append(this.lvl() + "if (" + this.rsVar() + ".wasNull() || " + this.byteArrayVar(field) + " ==null || " + this.byteArrayVar(field) + ".length==0) { " + EOL);
               sb.append(this.upLvl() + fieldVar + " = null;" + EOL);
               sb.append(this.dnLvl() + "}" + EOL);
               String cast = "(" + this.javaCodeForType(type) + ")";
               sb.append(this.lvl() + "else { " + EOL);
               sb.append(this.upLvl() + "ByteArrayInputStream bstr = new java.io.ByteArrayInputStream(" + this.byteArrayVar(field) + ");" + EOL);
               sb.append(this.lvl() + "RDBMSObjectInputStream ostr = new RDBMSObjectInputStream(bstr, " + this.pmVar() + ".getClassLoader());" + EOL);
               sb.append(this.lvl() + fieldVar + " = " + cast + "ostr.readObject();" + EOL);
               sb.append(this.dnLvl() + "}" + EOL);
            } else {
               sb.append(fieldVar + " = " + value + ";" + EOL);
            }
         } else {
            sb.append("String " + this.stringVar(field) + " = " + value + ";" + EOL);
            sb.append("  if (" + this.rsVar() + ".wasNull() || " + this.stringVar(field) + "==null || " + this.stringVar(field) + ".length()==0) { " + EOL);
            sb.append("    " + fieldVar + " = ");
            if (type == Character.class) {
               sb.append("null");
            } else {
               sb.append("'\\u0000'");
            }

            sb.append(";" + EOL);
            sb.append("  }" + EOL);
            sb.append("  else { " + EOL);
            sb.append("    " + fieldVar + " = ");
            if (type == Character.class) {
               sb.append("new Character(" + this.stringVar(field) + ".charAt(0));" + EOL);
            } else {
               sb.append(this.stringVar(field) + ".charAt(0);" + EOL);
            }

            sb.append("  }" + EOL);
         }

         if (!type.isPrimitive() && type != Character.class && type != Date.class && ClassUtils.isValidSQLType(type)) {
            sb.append("  if (").append(this.rsVar()).append(".wasNull()) { ");
            sb.append(fieldVar + " = null; }").append(EOL);
         }

         if (excludePKIfNull && this.primaryKeyFieldList.contains(field)) {
            sb.append("} else {" + EOL);
            sb.append(this.beanVar() + ".__WL_setPrimaryKey((" + this.pk_class() + ") " + this.pkVar() + ");" + EOL);
            sb.append("}" + EOL);
         }
      }

   }

   private String convertToField(String f, int idx) {
      CMPCodeGenerator.Output output = (CMPCodeGenerator.Output)this.currentOutput;
      Class type = output.getCMPBeanDescriptor().getFieldClass(f);
      String cast = "(" + this.javaCodeForType(type) + ")";
      String value = this.getFromResultSet(idx, type);
      if (type == Boolean.class) {
         return "new Boolean(" + value + ")";
      } else if (type == Byte.class) {
         return "new Byte(" + value + ")";
      } else if (type != Character.class && type != Character.TYPE) {
         if (type == Double.class) {
            return "new Double(" + value + ")";
         } else if (type == Float.class) {
            return "new Float(" + value + ")";
         } else if (type == Integer.class) {
            return "new Integer(" + value + ")";
         } else if (type == Long.class) {
            return "new Long(" + value + ")";
         } else if (type == Short.class) {
            return "new Short(" + value + ")";
         } else if (type == Date.class) {
            cast = "(java.sql.Timestamp)";
            return cast + value;
         } else {
            return !ClassUtils.isValidSQLType(type) ? value : cast + value;
         }
      } else {
         return "(java.lang.String)" + value;
      }
   }

   private String getFromResultSet(int idx, Class type) {
      return this.rsVar() + ".get" + TypeUtils.getResultSetMethodPostfix(type) + "(" + idx + ")";
   }

   public String result_set_to_collection_class() {
      assert this.finderMethodInfo != null;

      assert this.finderMethodInfo.method != null;

      return "resultSetToCollection(" + this.rsVar() + ", \"" + this.finderMethodInfo.method.getName() + "\");";
   }

   public String beanIsUpdateable() {
      return this.fieldList.size() > this.primaryKeyFieldList.size() ? "true" : "false";
   }

   public String updateBeanColumnsSql() {
      String[] sArray = (String[])((String[])this.nonPrimaryKeyFieldList.toArray(new String[0]));
      return this.attrsAsColumnsAsParams(sArray, ", ");
   }

   public String idParamsSql() {
      String[] keyFieldNames = (String[])((String[])this.primaryKeyFieldList.toArray(new String[0]));

      assert keyFieldNames != null;

      return this.attrsAsColumnsAsParams(keyFieldNames, " AND ");
   }

   public String idColumnsSql() {
      String[] keyFieldNames = (String[])((String[])this.primaryKeyFieldList.toArray(new String[0]));

      assert keyFieldNames != null;

      return StringUtils.join(keyFieldNames, ", ");
   }

   public String allColumnsSql() {
      String[] fieldNames = (String[])((String[])this.fieldList.toArray(new String[0]));
      StringBuffer sb = new StringBuffer();
      int i = 0;

      for(int len = fieldNames.length; i < len; ++i) {
         String fieldName = fieldNames[i];
         String columnName = this.beanData.getColumnForField(fieldName);

         assert columnName != null;

         sb.append(columnName);
         if (i < len - 1) {
            sb.append(", ");
         }
      }

      return sb.toString();
   }

   public String allColumnsQMs() {
      String[] fieldNames = (String[])((String[])this.fieldList.toArray(new String[0]));
      int num = fieldNames.length;
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < num; ++i) {
         sb.append("?");
         if (i < num - 1) {
            sb.append(", ");
         }
      }

      return sb.toString();
   }

   public String copyKeyValuesToPkVar() {
      CMPCodeGenerator.Output output = (CMPCodeGenerator.Output)this.currentOutput;
      CMPBeanDescriptor bd = output.getCMPBeanDescriptor();
      String[] keyFieldNames = (String[])((String[])this.primaryKeyFieldList.toArray(new String[0]));
      StringBuffer sb = new StringBuffer();
      int i = 0;

      for(int len = keyFieldNames.length; i < len; ++i) {
         String fieldName = keyFieldNames[i];
         if (ClassUtils.isObjectPrimitive(bd.getPrimaryKeyClass())) {
            assert i == 0 : "Too many fields for an object primitive PK class";

            Class fieldClass = bd.getFieldClass(keyFieldNames[i]);
            sb.append(this.pkVar());
            if (fieldClass.getName().equals(bd.getPrimaryKeyClass().getName())) {
               sb.append(" = ");
               sb.append("this").append(".").append(keyFieldNames[i]).append(";");
            } else {
               sb.append(" = new ");
               sb.append(this.primaryKeyClass.getName());
               sb.append("(");
               sb.append(");");
            }
         } else {
            if (bd.hasComplexPrimaryKey()) {
               sb.append(this.pkVar()).append(".").append(fieldName);
            } else {
               sb.append(this.pkVar());
            }

            sb.append(" = ").append("this").append(".").append(fieldName).append(";");
         }

         sb.append(EOL);
      }

      return sb.toString();
   }

   public String perhaps_include_result_set_to_collection() throws CodeGenerationException {
      return this.parse(this.getProductionRule("resultSetToCollection"));
   }

   public String cm_bean_field_copy() {
      return this.cm_field_to_field_assign(this.ejbClass, "src.", "dest.");
   }

   public String cm_field_to_field_assign(Class c, String from, String to) {
      StringBuffer sb = new StringBuffer(100);
      Field[] fds = (Field[])((Field[])this.fieldList.toArray(new Field[0]));

      for(int i = 0; i < fds.length; ++i) {
         Field f = fds[i];
         sb.append(to + f.getName() + " = " + from + f.getName() + ";" + EOL);
      }

      return sb.toString();
   }

   public String implementStoreUtilities() {
      CMPCodeGenerator.Output output = (CMPCodeGenerator.Output)this.currentOutput;
      CMPBeanDescriptor bd = output.getCMPBeanDescriptor();
      String[] fieldNames = (String[])((String[])this.fieldList.toArray(new String[0]));
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < fieldNames.length; ++i) {
         sb.append("private void setParam" + fieldNames[i]);
         sb.append("(PreparedStatement " + this.stmtVar() + ", ");
         sb.append("int " + this.numVar() + ", ");
         sb.append(bd.getFieldClass(fieldNames[i]).getName() + " ");
         sb.append(fieldNames[i] + ") {" + EOL);
         this.addPreparedStatementBinding("", sb, fieldNames[i], fieldNames[i], this.numVar(), false, bd.getFieldClass(fieldNames[i]));
         sb.append(EOL);
         sb.append("}" + EOL + EOL);
      }

      return sb.toString();
   }

   private String lvl() {
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < this.level; ++i) {
         sb.append("  ");
      }

      return sb.toString();
   }

   private String lvl(int level) {
      this.level = level;
      return this.lvl();
   }

   private String upLvl() {
      ++this.level;
      return this.lvl();
   }

   private String dnLvl() {
      --this.level;
      return this.lvl();
   }

   private String attrsAsColumnsAsParams(String[] attrNames, String delim) {
      StringBuffer sb = new StringBuffer();
      int i = 0;

      for(int len = attrNames.length; i < len; ++i) {
         String colName = this.beanData.getColumnForField(attrNames[i]);
         sb.append(colName).append(" = ?");
         if (i < len - 1) {
            sb.append(delim);
         }
      }

      return sb.toString();
   }

   public String resetParams() {
      this.preparedStatementParamIndex = 1;
      return "// preparedStatementParamIndex reset.";
   }

   public String setBeanParams() {
      CMPCodeGenerator.Output output = (CMPCodeGenerator.Output)this.currentOutput;
      String[] fieldNames = (String[])((String[])this.fieldList.toArray(new String[0]));
      return this.preparedStatementBindings(fieldNames, "this", true, output.getCMPBeanDescriptor().getBeanClass(), false);
   }

   public String setPrimaryKeyParams() {
      CMPCodeGenerator.Output output = (CMPCodeGenerator.Output)this.currentOutput;
      CMPBeanDescriptor bd = output.getCMPBeanDescriptor();
      String[] keyFieldNames = (String[])((String[])this.primaryKeyFieldList.toArray(new String[0]));
      return this.preparedStatementBindings(keyFieldNames, this.pkVar(), bd.hasComplexPrimaryKey(), bd.getPrimaryKeyClass(), false);
   }

   public String setPrimaryKeyParamsUsingNum() {
      CMPCodeGenerator.Output output = (CMPCodeGenerator.Output)this.currentOutput;
      CMPBeanDescriptor bd = output.getCMPBeanDescriptor();
      String[] fieldNames = (String[])((String[])this.primaryKeyFieldList.toArray(new String[0]));
      return this.preparedStatementBindings(fieldNames, this.pkVar(), bd.hasComplexPrimaryKey(), bd.getPrimaryKeyClass(), true);
   }

   private String preparedStatementBindings(String[] fields, String obj, boolean objIsCompound, Class objectType, boolean useNumVar) {
      StringBuffer sb = new StringBuffer();
      int i = 0;

      for(int len = fields.length; i < len; ++i) {
         String f = fields[i];
         String index;
         if (useNumVar) {
            index = this.numVar();
         } else {
            index = String.valueOf(this.preparedStatementParamIndex);
         }

         this.addPreparedStatementBinding("", sb, f, obj, index, objIsCompound, objectType);
         if (useNumVar) {
            sb.append(this.numVar() + "++;" + EOL);
         }

         ++this.preparedStatementParamIndex;
         if (i < len - 1) {
            sb.append(EOL);
         }
      }

      return sb.toString();
   }

   private void addPreparedStatementBinding(String prefix, StringBuffer sb, String field, String obj, String paramIdx, boolean objIsCompound, Class objectType) {
      if (debugLogger.isDebugEnabled()) {
         debug("Adding a prepared statement binding: ");
         debug("\t\tfield = " + field);
         debug("\t\tobj = " + obj);
         debug("\t\tparamIdx = " + paramIdx);
         debug("\t\tobjIsCompound = " + objIsCompound);
         debug("\t\tobjectType = " + objectType);
      }

      CMPCodeGenerator.Output output = (CMPCodeGenerator.Output)this.currentOutput;
      Class fieldClass = output.getCMPBeanDescriptor().getFieldClass(field);
      String fieldTypeName = StatementBinder.getStatementTypeNameForClass(fieldClass);
      String value = obj;
      if (!fieldClass.isPrimitive()) {
         if (objIsCompound) {
            value = obj + "." + field;
         }

         this.addNullCheck(prefix, sb, value, field, paramIdx);
      }

      if (!ClassUtils.isValidSQLType(fieldClass)) {
         sb.append(this.upLvl() + "ByteArrayOutputStream bstr = new ByteArrayOutputStream();" + EOL);
         sb.append(this.lvl() + "ObjectOutputStream ostr = new ObjectOutputStream(bstr);" + EOL);
         sb.append(this.lvl() + "ostr.writeObject(" + value + ");" + EOL);
         sb.append(this.lvl() + "byte[] byteArray = bstr.toByteArray();" + EOL);
         sb.append(this.lvl() + "if (" + this.debugEnabled() + ") {" + EOL);
         sb.append(this.upLvl() + this.debugSay() + "(\"writing bytes: \" + byteArray);" + EOL);
         sb.append(this.lvl() + "if (byteArray!=null) {" + EOL);
         sb.append(this.upLvl() + this.debugSay() + "(\"bytes length: \" + byteArray.length);" + EOL);
         sb.append(this.dnLvl() + "}" + EOL);
         sb.append(this.dnLvl() + "}" + EOL);
         sb.append(this.lvl() + "InputStream inputStream  = new ByteArrayInputStream(byteArray);" + EOL);
         sb.append(this.lvl() + this.stmtVar() + ".setBinaryStream(" + paramIdx + ", inputStream, byteArray.length);" + EOL);
      } else if (ClassUtils.isByteArray(fieldClass)) {
         sb.append(this.lvl() + "InputStream inputStream  = new ByteArrayInputStream(" + value + ");" + EOL);
         sb.append(this.lvl() + this.stmtVar() + ".setBinaryStream(" + paramIdx + ", inputStream, " + value + ".length);" + EOL);
      } else {
         sb.append(prefix + "\t" + this.stmtVar());
         sb.append(".set" + fieldTypeName + "(");
         sb.append(paramIdx).append(", ");
         if (objIsCompound) {
            if (fieldClass == Character.TYPE) {
               sb.append("String.valueOf(" + obj + "." + field + ")");
            } else if (fieldClass == Character.class) {
               sb.append("String.valueOf(" + obj + "." + field + ".charValue())");
            } else if (fieldClass == Date.class) {
               sb.append("new java.sql.Timestamp(");
               sb.append(obj).append(".").append(this.getParameterSetterName(fieldClass, field));
               sb.append(".getTime())");
            } else {
               sb.append(obj).append(".").append(this.getParameterSetterName(fieldClass, field));
            }
         } else if (ClassUtils.isObjectPrimitive(objectType)) {
            if (objectType == Character.class) {
               sb.append("String.valueOf(" + obj + ".charValue())");
            } else {
               sb.append(this.getParameterSetterName(objectType, obj));
            }

            if (debugLogger.isDebugEnabled()) {
               debug("\tThis type IS an object primitive.");
            }
         } else {
            if (objectType == Character.TYPE) {
               sb.append("String.valueOf(" + obj + ")");
            } else if (fieldClass == Date.class) {
               sb.append("new java.sql.Timestamp(");
               sb.append(obj);
               sb.append(".getTime())");
            } else {
               sb.append(obj);
            }

            if (debugLogger.isDebugEnabled()) {
               debug("\tThis type is not an object primitive.");
            }
         }

         sb.append(");" + EOL);
      }

      sb.append(this.lvl() + "if (" + this.debugEnabled() + ") {" + EOL);
      sb.append(this.upLvl() + this.debugSay() + "(\"paramIdx :\"+" + paramIdx + "+\" binded with value :\"+" + value + ");" + EOL);
      sb.append(this.dnLvl() + "}" + EOL);
      if (!fieldClass.isPrimitive()) {
         sb.append(prefix + "}" + EOL);
      }

   }

   private String getParameterSetterName(Class type, String name) {
      if (type == Boolean.class) {
         return name + ".booleanValue()";
      } else if (type == Byte.class) {
         return name + ".byteValue()";
      } else if (type == Character.class) {
         return name + ".charValue()";
      } else if (type == Double.class) {
         return name + ".doubleValue()";
      } else if (type == Float.class) {
         return name + ".floatValue()";
      } else if (type == Integer.class) {
         return name + ".intValue()";
      } else if (type == Long.class) {
         return name + ".longValue()";
      } else if (type == Short.class) {
         return name + ".shortValue()";
      } else {
         return type == Character.TYPE ? "String.valueOf(" + name + ")" : name;
      }
   }

   public String setUpdateBeanParams() {
      CMPCodeGenerator.Output output = (CMPCodeGenerator.Output)this.currentOutput;
      CMPBeanDescriptor bd = output.getCMPBeanDescriptor();
      String[] fieldNames = (String[])((String[])this.nonPrimaryKeyFieldList.toArray(new String[0]));
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < fieldNames.length; ++i) {
         sb.append("\t\tif((! __WL_snapshots_enabled) || __WL_modified[" + i + "]) {" + EOL);
         this.addPreparedStatementBinding("", sb, fieldNames[i], fieldNames[i], this.numVar(), false, bd.getFieldClass(fieldNames[i]));
         sb.append("\t\t" + this.numVar() + "++;" + EOL);
         sb.append("__WL_modified[" + i + "] = false;" + EOL);
         sb.append("}" + EOL);
      }

      return sb.toString();
   }

   public String assignAllColumnsToBean() {
      StringBuffer sb = new StringBuffer();
      this.assignToFields(this.fieldList, sb, 1, "this", true);
      return sb.toString();
   }

   public String refresh_bean_from_key() {
      return this.isContainerManagedBean ? "loadByPrimaryKey(ctx);" : "((" + this.ejbClass.getName() + ")(ctx.getBean())).ejbFindByPrimaryKey(pk);";
   }

   public String snapshots_enabled() {
      this.useTunedUpdates = this.beanData.useTunedUpdates();
      Iterator it = this.fieldList.iterator();

      while(it.hasNext()) {
         String fName = (String)it.next();
         Class fClass = this.getBeanField(fName).getType();
         if (!ClassUtils.isValidSQLType(fClass)) {
            this.useTunedUpdates = false;
            break;
         }
      }

      this.initializeSnapshotArray();
      return "" + this.useTunedUpdates;
   }

   private Field getBeanField(String fieldName) {
      try {
         return this.ejbClass.getField(fieldName);
      } catch (NoSuchFieldException var3) {
         EJBLogger.logStackTrace(var3);
         throw new AssertionError(var3);
      }
   }

   private void initializeSnapshotArray() {
      int fieldCount = this.nonPrimaryKeyFieldList.size();
      String[] nonPkFields = new String[this.nonPrimaryKeyFieldList.size()];
      nonPkFields = (String[])((String[])this.nonPrimaryKeyFieldList.toArray(nonPkFields));
      this.snapshotFieldInfo = new SnapshotFieldInfo[fieldCount];

      for(int i = 0; i < fieldCount; ++i) {
         Class fieldClass = this.getBeanField(nonPkFields[i]).getType();
         this.snapshotFieldInfo[i] = new SnapshotFieldInfo(fieldClass, nonPkFields[i], this.getSnapFieldTypeForClass(fieldClass), "__WL_snap_" + nonPkFields[i]);
      }

   }

   public String modified_array_count() {
      return "" + this.nonPrimaryKeyFieldList.size();
   }

   private boolean isImmutableType(Class c) {
      for(int i = 0; i < this.immutableClasses.length; ++i) {
         if (c.equals(this.immutableClasses[i])) {
            return true;
         }
      }

      return false;
   }

   private Class getSnapFieldTypeForClass(Class c) {
      if (!c.isPrimitive() && !this.isImmutableType(c)) {
         if (Date.class.isAssignableFrom(c)) {
            return Long.class;
         } else {
            return byte[].class.equals(c) ? c : c;
         }
      } else {
         return c;
      }
   }

   private String getSnapAssignmentCode(String snapName, Class c) {
      return Date.class.isAssignableFrom(c) ? "((" + snapName + "==null)?null:new Long(" + snapName + ".getTime()))" : snapName;
   }

   public String declare_snapshot_variables() {
      StringBuffer sb = new StringBuffer(200);

      for(int i = 0; i < this.snapshotFieldInfo.length; ++i) {
         SnapshotFieldInfo s = this.snapshotFieldInfo[i];
         String varType;
         if (byte[].class.equals(s.snapFieldType)) {
            varType = "byte []";
         } else if (String[].class.equals(s.snapFieldType)) {
            varType = "String []";
         } else if (char[].class.equals(s.snapFieldType)) {
            varType = "char []";
         } else {
            varType = s.snapFieldType.getName();
         }

         sb.append("private " + varType + " " + s.snapFieldName + ";" + EOL);
      }

      return sb.toString();
   }

   public String clear_snapshot_variables() {
      StringBuffer sb = new StringBuffer(200);

      for(int i = 0; i < this.snapshotFieldInfo.length; ++i) {
         SnapshotFieldInfo s = this.snapshotFieldInfo[i];
         if (!s.snapFieldType.isPrimitive()) {
            sb.append("\t\t" + s.snapFieldName + " = null;" + EOL);
         }
      }

      return sb.toString();
   }

   public String take_snapshot_variables() {
      StringBuffer sb = new StringBuffer(200);

      for(int i = 0; i < this.snapshotFieldInfo.length; ++i) {
         SnapshotFieldInfo s = this.snapshotFieldInfo[i];
         if (byte[].class.equals(s.beanFieldType)) {
            sb.append(s.snapFieldName + " = __WL_snapshot_byte_array(" + s.beanFieldName + ");" + EOL);
         } else {
            sb.append(s.snapFieldName + " = " + this.getSnapAssignmentCode(s.beanFieldName, s.beanFieldType) + ";" + EOL);
         }
      }

      return sb.toString();
   }

   public String modified_field_index() {
      return "" + this.current_index;
   }

   public String snapshot_field() {
      return this.current.snapFieldName;
   }

   public String modified_field() {
      return this.current.beanFieldName;
   }

   public String modified_column_name() {
      return this.beanData.getColumnForField(this.current.beanFieldName);
   }

   public String determineSetString() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer(200);

      for(int i = 0; i < this.snapshotFieldInfo.length; ++i) {
         this.current_index = i;
         this.current = this.snapshotFieldInfo[i];
         if (Date.class.isAssignableFrom(this.current.beanFieldType)) {
            sb.append(this.parse(this.getProductionRule("check_for_date_modified_field")));
         } else if (byte[].class.isAssignableFrom(this.current.beanFieldType)) {
            sb.append(this.parse(this.getProductionRule("check_for_bytea_modified_field")));
         } else {
            sb.append(this.parse(this.getProductionRule("check_for_simple_modified_field")));
         }
      }

      return sb.toString();
   }

   public String home_methods() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer(200);
      if (this.homeMethods != null) {
         for(int j = 0; j < this.homeMethods.length; ++j) {
            try {
               String methodName = null;
               methodName = this.homeToBeanName("ejbHome", this.homeMethods[j].getName());
               Method beanMethod = this.ejbClass.getMethod(methodName, this.homeMethods[j].getParameterTypes());
               this.setMethod(beanMethod, (short)0);
               sb.append(this.parse(this.getProductionRule("business_method")));
            } catch (NoSuchMethodException var5) {
               throw new AssertionError(var5);
            }
         }
      }

      return sb.toString();
   }

   private String getFinderMethodDeclaration(Method method, boolean makeConcrete) {
      StringBuffer sb = new StringBuffer();
      int methodModifiers = method.getModifiers();
      if (makeConcrete && Modifier.isAbstract(methodModifiers)) {
         methodModifiers ^= 1024;
      }

      sb.append(Modifier.toString(methodModifiers)).append(" ");
      int count = sb.toString().indexOf("strict ");
      if (count != -1) {
         sb.insert(count + "strict".length(), "fp");
      }

      if (!method.getReturnType().getName().equals("java.util.Enumeration") && !method.getReturnType().getName().equals("java.util.Collection")) {
         sb.append("java.lang.Object");
      } else {
         sb.append("java.util.Collection");
      }

      if (sb.length() > 73) {
         sb.append("" + EOL + "    ");
      }

      sb.append(" ");
      sb.append(MethodUtils.convertToFinderName(method.getName()));
      if (sb.length() > 73) {
         sb.append("" + EOL + "    ");
      }

      sb.append("(").append(MethodUtils.getParameterList(method)).append(")");
      count = 0;
      Class[] var6 = method.getExceptionTypes();
      int var7 = var6.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         Class ex = var6[var8];
         if (!ex.getName().equals("java.rmi.RemoteException")) {
            if (count == 0) {
               sb.append(" throws ");
            } else {
               sb.append(", ");
            }

            ++count;
            sb.append(ClassUtils.classToJavaSourceType(ex));
            if (sb.length() > 73) {
               sb.append("" + EOL + "    ");
            }
         }
      }

      return sb.toString();
   }

   public String declare_bean_interface_methods() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer(200);
      sb.append(super.declare_bean_interface_methods());
      Iterator finders = this.finderList.iterator();

      while(finders.hasNext()) {
         Method currMethod = (Method)finders.next();
         String methodDecl = this.getFinderMethodDeclaration(currMethod, true);
         sb.append(methodDecl);
         sb.append(";");
         sb.append(EOL);
      }

      return sb.toString();
   }

   private String homeToBeanName(String prefix, String m) {
      StringBuffer sb = new StringBuffer(prefix + m);
      sb.setCharAt(prefix.length(), Character.toUpperCase(sb.charAt(prefix.length())));
      return sb.toString();
   }

   private static void debug(String s) {
      debugLogger.debug("[RDBMSCodeGenerator] " + s);
   }

   static {
      debugLogger = EJBDebugService.cmpDeploymentLogger;
   }

   private class FinderMethodInfo {
      public Method method;
      public Finder finder;
      public boolean loadBean;

      private FinderMethodInfo() {
      }

      // $FF: synthetic method
      FinderMethodInfo(Object x1) {
         this();
      }
   }

   private static class SnapshotFieldInfo {
      private Class beanFieldType;
      private String beanFieldName;
      private Class snapFieldType;
      private String snapFieldName;

      public SnapshotFieldInfo(Class fc, String fn, Class sc, String sn) {
         this.beanFieldType = fc;
         this.beanFieldName = fn;
         this.snapFieldType = sc;
         this.snapFieldName = sn;
      }
   }
}
