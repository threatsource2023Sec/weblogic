package weblogic.ejb.container.cmp.rdbms.codegen;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.EJBObject;
import javax.ejb.EntityContext;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.cmp.rdbms.FieldGroup;
import weblogic.ejb.container.cmp.rdbms.RDBMSBean;
import weblogic.ejb.container.cmp.rdbms.RDBMSUtils;
import weblogic.ejb.container.cmp.rdbms.RelationshipCaching;
import weblogic.ejb.container.cmp.rdbms.finders.EjbqlFinder;
import weblogic.ejb.container.cmp.rdbms.finders.Finder;
import weblogic.ejb.container.cmp.rdbms.finders.ParamNode;
import weblogic.ejb.container.cmp.rdbms.finders.SqlFinder;
import weblogic.ejb.container.ejbc.EJBCException;
import weblogic.ejb.container.ejbc.codegen.MethodSignature;
import weblogic.ejb.container.persistence.spi.CMPBeanDescriptor;
import weblogic.ejb.container.persistence.spi.CMPCodeGenerator;
import weblogic.ejb.container.persistence.spi.EjbEntityRef;
import weblogic.ejb.container.utils.ClassUtils;
import weblogic.ejb.container.utils.MethodUtils;
import weblogic.logging.Loggable;
import weblogic.utils.Debug;
import weblogic.utils.Getopt2;
import weblogic.utils.StackTraceUtilsClient;
import weblogic.utils.StringUtils;
import weblogic.utils.compiler.CodeGenerationException;
import weblogic.utils.compiler.CodeGenerator;

public final class RDBMSCodeGenerator extends BaseCodeGenerator {
   private static final int MAX_LINE_CHARS = 80;
   private static final int THRESHOLD = 7;
   private static final char[] ILLEGAL_JAVA_CHARACTERS = new char[]{'@', '.', '-'};
   private RDBMSBean bean = null;
   private List cmpFieldNames = null;
   private List pkFieldNames = null;
   private Map variableToClass = null;
   private Map variableToField = null;
   private List finderList = null;
   private Map beanMap = null;
   private List ejbSelectInternalList = null;
   private Map ejbSelectBeanTargetMap = null;
   private Map declaredManagerVars = new HashMap();
   private Finder curFinder = null;
   private String curField = null;
   private String curTableName = null;
   private int curTableIndex = 0;
   private FieldGroup curGroup = null;
   private String curTable = null;
   private RelationshipCaching curRelationshipCaching = null;
   private int preparedStatementParamIndex = -1;
   private Map parameterMap = null;
   private boolean currFinderLoadsQueryCachingEnabledCMRFields = false;
   private Getopt2 options;
   private String currCachingElementCmrField = null;

   public RDBMSCodeGenerator(Getopt2 opts) {
      super(opts);
      this.options = opts;
   }

   public void setRDBMSBean(RDBMSBean bean) {
      assert bean != null;

      assert this.bd != null;

      this.bean = bean;
      this.variableToClass = new HashMap();
      this.variableToField = new HashMap();
      this.cmpFieldNames = bean.getCmpFieldNames();
      Iterator iter = this.cmpFieldNames.iterator();

      String fkField;
      while(iter.hasNext()) {
         fkField = (String)iter.next();
         this.variableToClass.put(fkField, bean.getCmpFieldClass(fkField));
         this.variableToField.put(fkField, fkField);
      }

      this.pkFieldNames = new ArrayList(this.bd.getPrimaryKeyFieldNames());
      iter = bean.getForeignKeyFieldNames().iterator();

      while(true) {
         do {
            if (!iter.hasNext()) {
               if (debugLogger.isDebugEnabled()) {
                  debug("Variable to Class Map--------------------");
                  Iterator var9 = this.variableToClass.entrySet().iterator();

                  while(var9.hasNext()) {
                     Map.Entry e = (Map.Entry)var9.next();
                     debug("(" + (String)e.getKey() + ", \t" + ((Class)e.getValue()).getName());
                  }
               }

               this.curTableName = bean.getTableName();
               this.beanMap = bean.getBeanMap();
               return;
            }

            fkField = (String)iter.next();
         } while(!bean.containsFkField(fkField));

         String fkTable = bean.getTableForCmrField(fkField);
         Iterator fkColumns = bean.getForeignKeyColNames(fkField).iterator();

         while(fkColumns.hasNext()) {
            String fkColumn = (String)fkColumns.next();
            if (!bean.hasCmpField(fkTable, fkColumn)) {
               String fkVar = bean.variableForField(fkField, fkTable, fkColumn);
               Class fkClass = bean.getForeignKeyColClass(fkField, fkColumn);
               if (debugLogger.isDebugEnabled()) {
                  debug("fkField: " + fkField + " fkColumn: " + fkColumn + " fkVar: " + fkVar + " fkClass: " + fkClass.getName());
               }

               this.variableToClass.put(fkVar, fkClass);
               this.variableToField.put(fkVar, fkField);
            }
         }
      }
   }

   public void setFinderList(List finders) {
      if (debugLogger.isDebugEnabled()) {
         debug("RDBMSCodeGenerator.setFinderList(" + finders + ")");
      }

      this.finderList = finders;
      this.ejbSelectBeanTargetMap = new HashMap();
      Iterator it = finders.iterator();

      while(it.hasNext()) {
         Finder f = (Finder)it.next();
         if (f.getQueryType() == 4) {
            RDBMSBean targBean = f.getSelectBeanTarget();
            if (targBean != null) {
               String targ = targBean.getEjbName();
               if (!this.ejbSelectBeanTargetMap.containsKey(targ)) {
                  this.ejbSelectBeanTargetMap.put(targ, targ);
               }
            }
         }
      }

   }

   public void setEjbSelectInternalList(List l) {
      if (debugLogger.isDebugEnabled()) {
         debug("RDBMSCodeGenerator.setEjbSelectInternalList(" + l + ")");
      }

      this.ejbSelectInternalList = l;
   }

   public void setCMPBeanDescriptor(CMPBeanDescriptor bd) {
      super.setCMPBeanDescriptor(bd);
   }

   protected List typeSpecificTemplates() {
      List templateNames = new ArrayList();
      templateNames.add("weblogic/ejb/container/cmp/rdbms/codegen/bean.j");
      templateNames.add("weblogic/ejb/container/cmp/rdbms/codegen/relationship.j");
      return templateNames;
   }

   public void setParameterMap(Map map) {
      this.parameterMap = map;
   }

   protected void prepare(CodeGenerator.Output output) throws EJBCException, ClassNotFoundException {
      super.prepare(output);

      assert this.bean != null;

      assert this.finderList != null;

      assert this.pkFieldNames != null;

      assert this.parameterMap != null;

      if (debugLogger.isDebugEnabled()) {
         debug("cmp.rdbms.codegen.RDBMSCodeGenerator.prepare() called");
      }

   }

   public String getCategoryValueMethodBody() {
      StringBuffer sb = new StringBuffer();
      String fieldName = this.bean.getCategoryCmpField();
      if (fieldName != null) {
         sb.append("return ");
         sb.append(fieldName);
         sb.append(";");
         return sb.toString();
      } else {
         sb.append("throw new AssertionError(\"Categories not supported!\");");
         return sb.toString();
      }
   }

   public String declareEjbSelectMethodVars() {
      StringBuffer sb = new StringBuffer();
      Iterator it = this.finderList.iterator();

      while(it.hasNext()) {
         Finder f = (Finder)it.next();
         boolean declareMethod = f.getQueryType() == 4 || f.getQueryType() == 2 || f instanceof SqlFinder && f.isSelect();
         if (declareMethod) {
            sb.append("\n  public static java.lang.reflect.Method ");
            sb.append(this.ejbSelectMDName(f));
            sb.append(";");
            sb.append(EOL);
         }
      }

      return sb.toString();
   }

   public String implementEjbSelectMethods() throws CodeGenerationException {
      Iterator it = this.finderList.iterator();
      StringBuffer sb = new StringBuffer();

      while(true) {
         while(it.hasNext()) {
            Finder f = (Finder)it.next();
            int queryType = f.getQueryType();
            Class retClass;
            String retVal;
            String m;
            if (f.isSelect() && f instanceof SqlFinder) {
               retClass = f.getReturnClassType();
               retVal = this.varPrefix() + "ret";
               List parameterList = f.getExternalMethodParmList();
               sb.append(this.getFinderMethodDeclaration(f, retClass.getName(), f.getName(), parameterList));
               sb.append("  {" + EOL);
               sb.append("Object " + retVal + " = ");
               sb.append(this.pmVar());
               sb.append(".processSqlFinder (");
               sb.append(this.ejbSelectMDName(f));
               sb.append(", new Object [] {");
               Iterator parameters = parameterList.iterator();

               while(parameters.hasNext()) {
                  ParamNode parameter = (ParamNode)parameters.next();
                  Class parameterClass = parameter.getParamClass();
                  m = parameter.getParamName();
                  sb.append(this.perhapsConvertPrimitive(parameterClass, m));
                  if (parameters.hasNext()) {
                     sb.append(", ");
                  }
               }

               sb.append("}, ");
               sb.append(f.hasLocalResultType());
               sb.append(");");
               sb.append(EOL);
               sb.append(EOL);
               sb.append("if (" + retVal + "== null) {" + EOL);
               if (retClass.isPrimitive()) {
                  sb.append("throw new javax.ejb.FinderException (\"" + f.getName() + " cannot return null.  Return type is " + ClassUtils.classToJavaSourceType(retClass) + ".\");" + EOL);
               } else {
                  sb.append("return null;" + EOL);
               }

               sb.append("}" + EOL);
               if (retClass.isPrimitive()) {
                  sb.append("return ((" + ClassUtils.classToJavaSourceType(ClassUtils.getObjectClass(retClass)) + ")" + retVal + ")" + this.lookupConvert(ClassUtils.getObjectClass(retClass)) + ";" + EOL);
               } else {
                  sb.append("return (" + ClassUtils.classToJavaSourceType(retClass) + ")" + retVal + ";" + EOL);
               }

               sb.append("}" + EOL);
            } else if (queryType != 4 && queryType != 2) {
               if (queryType == 3 || queryType == 5) {
                  sb.append(this.implementEJBSelectField(f));
               }
            } else {
               retClass = f.getReturnClassType();
               retVal = this.varPrefix() + "ret";
               String targEjbName = f.getSelectBeanTarget().getEjbName();
               String bmFinderMethod = this.scalarFinder(f);
               if (retClass.equals(Collection.class)) {
                  bmFinderMethod = this.collectionFinder(f);
               } else if (retClass.equals(Set.class)) {
                  bmFinderMethod = this.setFinder(f);
               }

               String targBmName = "";
               if (queryType == 4) {
                  targBmName = this.bmVar((String)this.declaredManagerVars.get(targEjbName));
               } else if (queryType == 2) {
                  targBmName = "((CMPBeanManager) " + this.pmVar() + ".getBeanManager())";
               }

               sb.append("  ");
               List parameterList = f.getExternalMethodParmList();
               m = this.getFinderMethodDeclaration(f, f.getReturnClassType().getName(), f.getName(), parameterList);
               sb.append(m);
               sb.append("  {" + EOL);
               sb.append("    ");
               sb.append(retClass.getName());
               sb.append(" " + retVal + " = null;");
               sb.append(EOL);
               if (f.isSelectInEntity()) {
                  sb.append("Object ").append(this.selectInEntityPKVar()).append(" = ");
                  sb.append(" __WL_ctx.getPrimaryKey();");
                  sb.append(EOL);
               }

               sb.append("try {");
               sb.append("      ");
               sb.append(retVal + " = (" + retClass.getName() + ")" + EOL);
               sb.append(targBmName);
               sb.append(".");
               sb.append(bmFinderMethod + "(");
               sb.append(this.ejbSelectMDName(f) + "," + EOL);
               sb.append("new Object[] { ");
               sb.append(this.wrapped_ejbSelect_params(f.getExternalMethodAndInEntityParmList()));
               sb.append(" } ");
               sb.append(" ); " + EOL);
               sb.append(this.parse(this.getProductionRule("selectCatch")));
               sb.append("return " + retVal + ";" + EOL);
               sb.append("}");
               sb.append(EOL);
            }
         }

         return sb.toString();
      }
   }

   public String implementEjbSelectInternalMethods() throws CodeGenerationException {
      if (this.ejbSelectInternalList == null) {
         return "";
      } else {
         StringBuffer sb = new StringBuffer();
         Iterator it = this.ejbSelectInternalList.iterator();

         while(it.hasNext()) {
            Finder f = (Finder)it.next();

            try {
               if (debugLogger.isDebugEnabled()) {
                  debug("generating ejbSelectInternal Finder: " + f);
               }

               sb.append(this.implementFinderMethod(f));
            } catch (EJBCException var6) {
               Loggable l = EJBLogger.logCouldNotGenerateFinderLoggable("ejbSelectInternal", f.toString(), var6.toString());
               throw new CodeGenerationException(l.getMessageText());
            }
         }

         return sb.toString();
      }
   }

   private String implementEJBSelectField(Finder f) throws CodeGenerationException {
      if (debugLogger.isDebugEnabled()) {
         debug("implementEJBSelectField(" + f + ") called.");
      }

      assert this.bd != null;

      this.curFinder = f;
      if (this.curFinder == null) {
         Loggable l = EJBLogger.logNullFinderLoggable("implementEJBSelectField");
         throw new CodeGenerationException(l.getMessageText());
      } else {
         StringBuffer sb = new StringBuffer();
         sb.append(this.getFinderMethodDeclaration(f, f.getReturnClassType()));
         sb.append("{" + EOL);

         try {
            if (f.isResultSetFinder()) {
               sb.append(this.parse(this.getProductionRule("ejbSelectFieldBodyResultSet")));
            } else if (f.isMultiFinder()) {
               sb.append(this.parse(this.getProductionRule("ejbSelectFieldBodyMulti")));
            } else {
               try {
                  sb.append(this.parse(this.getProductionRule("ejbSelectFieldBodyScalar")));
               } catch (Throwable var4) {
                  throw new CodeGenerationException(var4.getMessage());
               }
            }
         } catch (CodeGenerationException var5) {
            if (debugLogger.isDebugEnabled()) {
               debug("finderMethod cought CodeGenerationException : " + var5);
            }
         } catch (Exception var6) {
            throw new CodeGenerationException(var6.getMessage());
         }

         sb.append("" + EOL + "}" + EOL);
         this.curFinder = null;
         return sb.toString();
      }
   }

   public String implementClearCMRFields() {
      StringBuffer sb = new StringBuffer();
      Iterator all = this.bean.getCmrFieldNames().iterator();

      while(all.hasNext()) {
         this.curField = (String)all.next();
         if (this.addCMRClearForCurrentField()) {
            sb.append(this.fieldVarForField() + " = null;" + EOL);
         }
      }

      return sb.toString();
   }

   private boolean addCMRClearForCurrentField() {
      return this.bean.isOneToOneRelation(this.curField) && this.bean.isForeignKeyField(this.curField) || this.bean.isOneToManyRelation(this.curField) && !this.bean.getRelatedMultiplicity(this.curField).equals("One") || this.bean.isManyToManyRelation(this.curField);
   }

   public String ejbSelectFieldResultVar() {
      return this.varPrefix() + "retVal";
   }

   public String declareEjbSelectFieldResultVar() {
      return this.declareEjbSelectFieldResultVar(this.curFinder.getReturnClassType());
   }

   public String declareEjbSelectFieldCollResultVar() {
      Class varClass = this.curFinder.getSelectFieldClass();
      if (varClass.isPrimitive()) {
         varClass = ClassUtils.getObjectClass(varClass);
      }

      return this.declareEjbSelectFieldResultVar(varClass);
   }

   public String declareEjbSelectFieldResultVar(Class varClass) {
      StringBuffer sb = new StringBuffer();
      String varType = ClassUtils.classToJavaSourceType(varClass);
      sb.append("    ");
      sb.append(varType);
      sb.append(" ");
      sb.append(this.ejbSelectFieldResultVar());
      if (varClass.isPrimitive()) {
         sb.append(" = ");
         sb.append(ClassUtils.getDefaultValue(varClass));
         sb.append(";");
      } else {
         sb.append(" = null;");
      }

      sb.append(EOL);
      return sb.toString();
   }

   public String perhapsDeclareDistinctFilterVar() {
      return this.curFinder.isMultiFinder() && !this.curFinder.isSetFinder() && this.curFinder.isSelectDistinct() ? this.declareSetVar() + EOL : "";
   }

   public String assignEjbSelectFieldResultVar() {
      return this.assignEjbSelectFieldResultVar(this.curFinder.getReturnClassType());
   }

   public String assignEjbSelectFieldCollResultVar() {
      Class varClass = this.curFinder.getSelectFieldClass();
      if (varClass.isPrimitive()) {
         varClass = ClassUtils.getObjectClass(varClass);
      }

      return this.assignEjbSelectFieldResultVar(varClass);
   }

   public String assignEjbSelectFieldResultVar(Class varClass) {
      StringBuffer sb = new StringBuffer();
      sb.append("    ");
      sb.append(this.ejbSelectFieldResultVar());
      sb.append(" = ");
      sb.append(this.resultSetToVariable(1, varClass, (String)null));
      sb.append(";");
      if (!varClass.isPrimitive()) {
         sb.append("  if (").append(this.rsVar()).append(".wasNull()) { ");
         sb.append(this.ejbSelectFieldResultVar());
         sb.append(" = null; }").append(EOL);
      }

      return sb.toString();
   }

   public String checkNullForAggregateQuery() throws CodeGenerationException {
      Class varClass = this.curFinder.getReturnClassType();
      return varClass.isPrimitive() && this.curFinder.isAggregateQuery() ? this.parse(this.getProductionRule("checkNullForAggregateQueries")) : "";
   }

   public String addEjbSelectFieldToList() {
      StringBuffer sb = new StringBuffer();
      if (this.curFinder.isSelectDistinct()) {
         sb.append("if (" + this.setVar() + ".add(" + this.ejbSelectFieldResultVar() + ")) {" + EOL);
         sb.append("list.add(" + this.ejbSelectFieldResultVar() + ");" + EOL);
         sb.append("}" + EOL);
      } else {
         sb.append("list.add(" + this.ejbSelectFieldResultVar() + ");" + EOL);
      }

      return sb.toString();
   }

   public String ejbSelect_result_set_to_collection_class() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      Class c = this.curFinder.getReturnClassType();
      if (c.equals(Collection.class)) {
         sb.append(this.parse(this.getProductionRule("ejbSelectFieldToCollection")));
      } else {
         if (!c.equals(Set.class)) {
            throw new AssertionError("Invalid return type for ejbSelect.");
         }

         sb.append(this.parse(this.getProductionRule("ejbSelectFieldToSet")));
      }

      return sb.toString();
   }

   private String wrapped_ejbSelect_params(List pList) {
      StringBuffer sb = new StringBuffer();
      Iterator it = pList.iterator();

      while(it.hasNext()) {
         ParamNode n = (ParamNode)it.next();
         if (n.isBeanParam()) {
            sb.append(n.getParamName());
            sb.append(", ");
         } else if (n.isSelectInEntity()) {
            sb.append("__WL_ctx.getPrimaryKey(), ");
         } else {
            sb.append(this.wrapped_param(n.getParamClass().getName(), n.getParamName()));
            sb.append(", ");
         }
      }

      return sb.toString();
   }

   private String wrapped_param(String typeName, String paramName) {
      StringBuffer sb = new StringBuffer();
      boolean wrapped = false;
      wrapped = !this.primConversion(typeName).equals(typeName);
      if (wrapped) {
         sb.append("new " + this.primConversion(typeName) + "(");
      }

      sb.append(" " + paramName);
      if (wrapped) {
         sb.append(" )");
      }

      sb.append(" ");
      return sb.toString();
   }

   private String primConversion(String toconvert) {
      String converted = toconvert;
      if (toconvert.equals("boolean")) {
         converted = "Boolean";
      }

      if (toconvert.equals("int")) {
         converted = "Integer";
      }

      if (toconvert.equals("short")) {
         converted = "Short";
      }

      if (toconvert.equals("long")) {
         converted = "Long";
      }

      if (toconvert.equals("double")) {
         converted = "Double";
      }

      if (toconvert.equals("float")) {
         converted = "Float";
      }

      if (toconvert.equals("char")) {
         converted = "Character";
      }

      if (toconvert.equals("byte")) {
         converted = "Byte";
      }

      return converted;
   }

   public void checkCurFinder() throws CodeGenerationException {
      if (this.curFinder == null) {
         Loggable l = EJBLogger.logNullFinderLoggable("checkCurFinder");
         throw new CodeGenerationException(l.getMessageText());
      }
   }

   public String getRemoteHomeVarForFinder() throws CodeGenerationException {
      String cmrField = null;
      EjbqlFinder ejbqlFinder = (EjbqlFinder)this.curFinder;
      if (ejbqlFinder.hasRemoteBeanParam()) {
         ParamNode n = ejbqlFinder.getRemoteBeanParam();
         if (n != null) {
            cmrField = n.getId();
         }
      }

      if (cmrField == null) {
         Loggable l = EJBLogger.logNoCMRFieldForRemoteRelationshipLoggable(this.curFinder.getName());
         throw new CodeGenerationException(l.getMessageText());
      } else {
         return this.homeVar(cmrField);
      }
   }

   public String currentFinderName() throws CodeGenerationException {
      this.checkCurFinder();
      return this.curFinder.getName();
   }

   public String declarePkVarIfLoadsBean() {
      return this.curFinder.finderLoadsBean() ? this.declarePkVar() : "";
   }

   public Class getRemotePKClass() throws CodeGenerationException {
      String remoteHomeName = null;

      assert this.curFinder instanceof EjbqlFinder;

      EjbqlFinder ejbqlFinder = (EjbqlFinder)this.curFinder;
      if (!ejbqlFinder.hasRemoteBeanParam()) {
         Loggable l = EJBLogger.logNoRemoteHomeLoggable(ejbqlFinder.getName());
         throw new CodeGenerationException(l.getMessageText());
      } else {
         ParamNode n = ejbqlFinder.getRemoteBeanParam();
         remoteHomeName = n.getRemoteHomeName();
         Class var8 = this.loadClass(remoteHomeName);
         Method[] homeMArray = var8.getMethods();
         Method remoteFindByPKMethod = null;

         for(int i = 0; i < homeMArray.length; ++i) {
            if (homeMArray[i].getName().compareTo("findByPrimaryKey") == 0) {
               remoteFindByPKMethod = homeMArray[i];
               break;
            }
         }

         if (remoteFindByPKMethod == null) {
            Loggable l = EJBLogger.logMethodNotFoundInInterfaceLoggable("findByPrimaryKey", remoteHomeName);
            throw new CodeGenerationException(l.getMessageText());
         } else {
            Class[] remoteFinderPTypes = remoteFindByPKMethod.getParameterTypes();
            if (remoteFinderPTypes.length > 1) {
               Loggable l = EJBLogger.logMethodHasWrongParamCountLoggable("findByPrimaryKey", remoteHomeName, "1");
               throw new CodeGenerationException(l.getMessageText());
            } else {
               return remoteFinderPTypes[0];
            }
         }
      }
   }

   public String getRemotePKClassString() throws CodeGenerationException {
      return this.getRemotePKClass().getName();
   }

   public String getRemotePKObject() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      Class remotePKClass = this.getRemotePKClass();
      String remotePKTypeString = remotePKClass.getName();
      if (remotePKTypeString.startsWith("java.lang.")) {
         sb.append(this.parse(this.getProductionRule("remoteReadJavaLangEOColumn")));
      } else {
         sb.append(this.parse(this.getProductionRule("remoteReadJavaObjectEOColumn")));
      }

      return sb.toString();
   }

   public String PkOrGenBeanClassName() {
      return this.curFinder.finderLoadsBean() ? this.getGeneratedBeanClassName() : this.pk_class();
   }

   public String PkVarOrWLBean() {
      return this.curFinder.finderLoadsBean() ? this.beanVar() : this.pkVar();
   }

   public int getPKOrGroupColumnCount() {
      return ((EjbqlFinder)this.curFinder).getPKOrGroupColumnCount();
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
            columns.add(rdbmsBean.getCmpColumnForField((String)cmpFields.next()));
         }

         Iterator cmrFields = group.getCmrFields().iterator();

         while(cmrFields.hasNext()) {
            String cmrField = (String)cmrFields.next();
            Iterator cmrColumns = rdbmsBean.getForeignKeyColNames(cmrField).iterator();

            while(cmrColumns.hasNext()) {
               columns.add(cmrColumns.next());
            }
         }

         return columns.size();
      }
   }

   public String otherPkVar() {
      return this.pkVar() + "_2";
   }

   public String declareOtherPkVar() {
      CMPCodeGenerator.Output output = (CMPCodeGenerator.Output)this.currentOutput;
      StringBuffer sb = new StringBuffer();
      sb.append(this.pk_class() + " " + this.otherPkVar() + " = ");
      if (output.getCMPBeanDescriptor().hasComplexPrimaryKey()) {
         sb.append("new " + this.pk_class() + "();");
      } else {
         sb.append("null;");
      }

      return sb.toString();
   }

   private Class loadClass(String className) throws CodeGenerationException {
      Class cls = null;

      try {
         cls = this.bd.getClassLoader().loadClass(className);
         return cls;
      } catch (ClassNotFoundException var5) {
         Loggable l = EJBLogger.logUnableToLoadClassLoggable("RDBMSCodeGenerator", className);
         throw new CodeGenerationException(l.getMessageText());
      }
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

   public String eoVar() {
      return this.varPrefix() + "eo";
   }

   public String eoRCVar() {
      return this.varPrefix() + "eo_rc";
   }

   public String pkVar() {
      return this.varPrefix() + "pk";
   }

   public String fkVar() {
      return this.varPrefix() + "fk";
   }

   public String conVar() {
      return this.varPrefix() + "con";
   }

   public String rsVar() {
      return this.varPrefix() + "rs";
   }

   public String rsInfoVar() {
      return this.varPrefix() + "rsInfo";
   }

   public String stmtVar() {
      return this.varPrefix() + "stmt";
   }

   public String currStmtArrayVar() {
      return this.stmtArrayElement(this.curTableIndex);
   }

   public String stmtArrayVar() {
      return this.varPrefix() + "stmt_array";
   }

   public String stmtArrayElement(int index) {
      return this.varPrefix() + "stmt_array[" + index + "]";
   }

   public String stmtTableVar(String tableName) {
      return this.varPrefix() + "stmt_" + this.replaceIllegalJavaCharacters(tableName) + "_" + this.bean.tableIndex(tableName);
   }

   public String pmVar() {
      return this.varPrefix() + "pm";
   }

   public String keyVar() {
      return this.varPrefix() + "key";
   }

   public String numVar() {
      return this.varPrefix() + "num";
   }

   public String txVar() {
      return this.varPrefix() + "tx";
   }

   public String offsetVar() {
      return this.varPrefix() + "offset";
   }

   public String offsetIntObjVar() {
      return this.varPrefix() + "offsetIntObj";
   }

   public String pkMapVar() {
      return this.varPrefix() + "pkMap";
   }

   public String isMultiVar() {
      return this.varPrefix() + "isMulti";
   }

   public String queryVar() {
      return this.varPrefix() + "query";
   }

   public String queryArrayVar() {
      return this.varPrefix() + "query_array";
   }

   public String queryArrayElement(int index) {
      return this.varPrefix() + "query_array[" + index + "]";
   }

   public String iVar() {
      return this.varPrefix() + "i";
   }

   public String countVar() {
      return this.varPrefix() + "count";
   }

   public String totalVar() {
      return this.varPrefix() + "total";
   }

   public String blobClobCountVar() {
      return this.varPrefix() + "blobClob_count";
   }

   public String oldStateVar() {
      return this.varPrefix() + "oldState";
   }

   public String setBlobClobForOutputMethodName() {
      return this.varPrefix() + "set_" + this.curField + "ForOutput";
   }

   public String setBlobClobForInputMethodName() {
      return this.varPrefix() + "set_" + this.curField + "ForInput";
   }

   public String classLoaderVar() {
      return this.varPrefix() + "classLoader";
   }

   public String ctxVar() {
      return this.varPrefix() + "ctx";
   }

   public String jctxVar() {
      return this.varPrefix() + "initialCtx";
   }

   public String isModifiedVar() {
      return this.varPrefix() + "isModified";
   }

   public String beanIsModifiedVar() {
      return this.varPrefix() + "beanIsModified";
   }

   public String isModifiedUnionVar() {
      return this.varPrefix() + "isModifiedUnion";
   }

   public String modifiedBeanIsRegisteredVar() {
      return this.varPrefix() + "modifiedBeanIsRegistered";
   }

   public String beanIsLoadedVar() {
      return this.varPrefix() + "beanIsLoaded";
   }

   public String invalidatedBeanIsRegisteredVar() {
      return this.varPrefix() + "invalidatedBeanIsRegistered";
   }

   public String isLoadedVar() {
      return this.varPrefix() + "isLoaded";
   }

   public String colVar() {
      return this.varPrefix() + "collection";
   }

   public String setVar() {
      return this.varPrefix() + "set";
   }

   public String orderedSetVar() {
      return this.varPrefix() + "orderedSet";
   }

   public String selectInEntityPKVar() {
      return this.varPrefix() + "selectInEntityPK";
   }

   public String mapVar() {
      return this.varPrefix() + "map";
   }

   public String stringVar(int unique) {
      return this.varPrefix() + "stringVar" + unique;
   }

   public String tempVar(int unique) {
      return this.varPrefix() + "tempVar" + unique;
   }

   public String sqlTimestampVar(int unique) {
      return this.varPrefix() + "sqlTimestampVar" + unique;
   }

   private String bmVar(String fieldName) {
      return this.varPrefix() + ClassUtils.makeLegalName(fieldName) + "_bm";
   }

   private String genKeyVar() {
      return this.varPrefix() + "genKey";
   }

   private String byteArrayVar(String varName) {
      return this.varPrefix() + "byteArray_" + varName;
   }

   private String tableModifiedVar(String table) {
      return this.bean.hasMultipleTables() ? this.varPrefix() + "tableModified" + this.replaceIllegalJavaCharacters(table) : this.modifiedBeanIsRegisteredVar();
   }

   public String tableModifiedVar() {
      return this.tableModifiedVar(this.curTable);
   }

   private String tableLoadedVar(String table) {
      return this.bean.hasMultipleTables() ? this.varPrefix() + "tableLoaded" + this.replaceIllegalJavaCharacters(table) : this.beanIsLoadedVar();
   }

   public String tableLoadedVar() {
      return this.tableLoadedVar(this.curTable);
   }

   private String snapshotBufferVar() {
      return "sb_snap";
   }

   public String createMethodName() {
      return this.varPrefix() + "create";
   }

   public String existsMethodName() {
      return this.varPrefix() + "exists";
   }

   public String invalidVar() {
      return this.varPrefix() + "invalid";
   }

   public String rowSetFactoryVar() {
      return this.varPrefix() + "rowSetFactory";
   }

   public String rowSetVar() {
      return this.varPrefix() + "rowSet";
   }

   public String rowSetFactoryName() {
      return "weblogic.jdbc.rowset.RowSetFactory ";
   }

   private String replaceIllegalJavaCharacters(String s) {
      String replacedString = s;

      for(int i = 0; i < ILLEGAL_JAVA_CHARACTERS.length; ++i) {
         replacedString = replacedString.replace(ILLEGAL_JAVA_CHARACTERS[i], '_');
      }

      return replacedString;
   }

   public String perhapsDeclareRowSetFactoryVar() {
      return this.bean.hasResultSetFinder() ? "private static " + this.rowSetFactoryName() + " " + this.rowSetFactoryVar() + " = null;" : "";
   }

   public String declareStmtArrayVars() {
      StringBuffer sb = new StringBuffer();
      sb.append(EOL);
      sb.append("java.sql.PreparedStatement[] ").append(this.stmtArrayVar()).append(" = ");
      sb.append("new java.sql.PreparedStatement[").append(this.bean.tableCount()).append("];");
      sb.append(EOL).append(EOL);

      for(int i = 0; i < this.bean.tableCount(); ++i) {
         this.curTableIndex = i;
         this.curTableName = this.bean.tableAt(i);
         sb.append("java.sql.PreparedStatement ").append(this.stmtTableVar(this.curTableName)).append(" = null;").append(EOL);
         sb.append(this.stmtArrayElement(i)).append(" = ").append(this.stmtTableVar(this.curTableName)).append(";").append(EOL);
      }

      sb.append(EOL);
      return sb.toString();
   }

   public String declareQueryArrayVars() {
      return EOL + "String[] " + this.queryArrayVar() + " = new String[" + this.bean.tableCount() + "];" + EOL;
   }

   public String declareContainerManagedFieldVars() {
      StringBuffer sb = new StringBuffer();
      Iterator var2 = this.cmpFieldNames.iterator();

      while(var2.hasNext()) {
         String name = (String)var2.next();
         sb.append("public ");
         sb.append(ClassUtils.classToJavaSourceType(this.bean.getCmpFieldClass(name)) + " ");
         sb.append(name + ";");
         sb.append(EOL);
      }

      return sb.toString();
   }

   public String declareForeignKeyFieldVars() {
      StringBuffer sb = new StringBuffer();
      Iterator fieldNames = this.bean.getForeignKeyFieldNames().iterator();

      while(true) {
         String fieldName;
         do {
            do {
               if (!fieldNames.hasNext()) {
                  return sb.toString();
               }

               fieldName = (String)fieldNames.next();
            } while(!this.bean.containsFkField(fieldName));
         } while(this.bean.isForeignCmpField(fieldName));

         String fkTable = this.bean.getTableForCmrField(fieldName);
         Iterator colNames = this.bean.getForeignKeyColNames(fieldName).iterator();

         while(colNames.hasNext()) {
            String colName = (String)colNames.next();
            String className = ClassUtils.classToJavaSourceType(this.bean.getForeignKeyColClass(fieldName, colName));
            String varName = this.bean.variableForField(fieldName, fkTable, colName);
            sb.append("public ");
            sb.append(className + " ");
            sb.append(varName + ";");
            sb.append(EOL);
         }
      }
   }

   public String declareRelationFieldVars() {
      StringBuffer sb = new StringBuffer();
      Iterator declared = this.bean.getCmrFieldNames().iterator();

      while(declared.hasNext()) {
         String fieldName = (String)declared.next();
         String varName = CodeGenUtils.fieldVarName(fieldName);
         String className = ClassUtils.classToJavaSourceType(this.bean.getCmrFieldClass(fieldName));
         sb.append("public ");
         sb.append(className + " ");
         sb.append(varName + ";");
         sb.append(EOL);
         if (this.bean.isSelfRelationship(fieldName)) {
            String removedVarName = CodeGenUtils.fieldRemovedVarName(fieldName);
            sb.append("public ");
            sb.append(className + " ");
            sb.append(removedVarName + ";");
            sb.append(EOL);
         }
      }

      return sb.toString();
   }

   private String finderVarName(String fieldName) {
      return this.varPrefix() + fieldName + "_finder_";
   }

   public String declareStaticFinderVars() {
      StringBuffer sb = new StringBuffer();
      Iterator declared = this.bean.getCmrFieldNames().iterator();

      while(declared.hasNext()) {
         String fieldName = (String)declared.next();
         this.relInterfaceNameForField(fieldName);
         this.bean.finderMethodName(this.bd, fieldName);
         String varName = this.finderVarName(fieldName);
         sb.append("private static final java.lang.reflect.Method ");
         sb.append(varName);
         sb.append(";");
         sb.append(EOL);
      }

      sb.append(EOL);
      return sb.toString();
   }

   public String assignStaticFinderVars() {
      StringBuffer sb = new StringBuffer();
      Iterator declared = this.bean.getCmrFieldNames().iterator();
      if (declared.hasNext()) {
         sb.append("static {");
         sb.append(EOL);
         sb.append("Method m = null;");
         sb.append(EOL);
         declared = this.bean.getCmrFieldNames().iterator();

         while(declared.hasNext()) {
            String fieldName = (String)declared.next();
            String relatedBeanInterface = this.relInterfaceNameForField(fieldName);
            String varName = this.finderVarName(fieldName);
            String methodName = this.generateCMRFieldFinderMethodName(fieldName);
            sb.append("try {");
            sb.append(EOL);
            sb.append("m = ");
            sb.append(relatedBeanInterface);
            sb.append(".class.getMethod(\"");
            sb.append(methodName);
            sb.append("\", ");
            if (this.bean.isManyToManyRelation(fieldName)) {
               sb.append(" new Class[] { Object.class } ");
            } else {
               sb.append(this.primaryFieldClassesArray(fieldName));
            }

            sb.append(");" + EOL);
            sb.append("} catch (NoSuchMethodException ignore) {");
            sb.append(EOL);
            sb.append("m = null;");
            sb.append(EOL);
            sb.append("}");
            sb.append(EOL);
            sb.append(varName);
            sb.append(" = m;");
            sb.append(EOL);
         }

         sb.append("}");
         sb.append(EOL);
         sb.append(EOL);
      }

      return sb.toString();
   }

   public String perhapsInitializeIsModified() {
      StringBuffer sb = new StringBuffer();
      if (!this.bd.isReadOnly() || this.bean.allowReadonlyCreateAndRemove()) {
         sb.append(this.isModifiedVar());
         sb.append("[");
         sb.append(this.iVar());
         sb.append("] = false;");
         sb.append(EOL);
      }

      return sb.toString();
   }

   public String perhapsInitializeModifiedBeanIsRegisteredVar() {
      StringBuffer sb = new StringBuffer();
      if (!this.bd.isReadOnly() || this.bean.allowReadonlyCreateAndRemove()) {
         sb.append(this.modifiedBeanIsRegisteredVar());
         sb.append(" = false;");
         sb.append(EOL);
      }

      return sb.toString();
   }

   public String perhapsOrTermForIsModified() {
      StringBuffer sb = new StringBuffer();
      if (!this.bd.isReadOnly() || this.bean.allowReadonlyCreateAndRemove()) {
         sb.append("|| ");
         sb.append(this.isModifiedVarForField());
      }

      return sb.toString();
   }

   public String declareIsModifiedVar() {
      StringBuffer sb = new StringBuffer();
      if (!this.bd.isReadOnly() || this.bean.allowReadonlyCreateAndRemove()) {
         sb.append("private boolean[] " + this.isModifiedVar() + " = new boolean[" + this.bean.getFieldCount() + "];" + EOL);
         sb.append("private boolean " + this.modifiedBeanIsRegisteredVar() + "= false;" + EOL);
      }

      return sb.toString();
   }

   public String declareisModifiedUnionVar() {
      return this.isModifiedUnionVar() + " = new boolean[" + this.bean.getFieldCount() + "];" + EOL;
   }

   public String perhapsComputeModifiedUnion() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      if (!this.bd.isReadOnly() || this.bean.allowReadonlyCreateAndRemove()) {
         sb.append(this.parse(this.getProductionRule("computeModifiedUnion")));
      }

      return sb.toString();
   }

   public String perhapsImplementResetIsModifiedVarsMethodBody() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      if (!this.bd.isReadOnly() || this.bean.allowReadonlyCreateAndRemove()) {
         sb.append(this.parse(this.getProductionRule("implementResetIsModifiedVarsMethodBody")));
      }

      return sb.toString();
   }

   public String fieldsWoFkColumns() {
      StringBuffer sb = new StringBuffer();
      int index = 0;

      int i;
      for(i = 0; i < this.cmpFieldNames.size(); ++i) {
         sb.append("(" + this.iVar() + "==" + index + ") || ");
         ++index;
      }

      for(i = 0; i < this.bean.tableCount(); ++i) {
         for(Iterator it = this.bean.getCMRFields(i).iterator(); it.hasNext(); ++index) {
            String fieldName = (String)it.next();
            if (this.bean.isSelfRelationship(fieldName) && this.bean.containsFkField(fieldName) && !this.bean.isForeignCmpField(fieldName)) {
               sb.append("(" + this.iVar() + "==" + index + ") || ");
            }
         }
      }

      sb.setLength(sb.length() - 4);
      return sb.toString();
   }

   public String perhapsIsFkColsNullableCheck(String fieldName) {
      StringBuffer sb = new StringBuffer();
      if (this.bean.isOneToOneRelation(fieldName) && !this.bean.isSelfRelationship(fieldName) || this.bean.isOneToManyRelation(fieldName)) {
         String varName = null;
         if (this.bean.isSelfRelationship(fieldName)) {
            varName = CodeGenUtils.fieldRemovedVarName(fieldName);
         } else {
            varName = CodeGenUtils.fieldVarName(fieldName);
         }

         sb.append(" && (" + this.pmVar() + ".isFkColsNullable(\"" + fieldName + "\") || " + varName + " != null || !__WL_getIsRemoved())");
      }

      return sb.toString();
   }

   public String perhapsAddSelfRelatedBeansToInsertStmt() throws CodeGenerationException {
      if (this.bean.getOrderDatabaseOperations() && this.bean.isSelfRelationship()) {
         StringBuffer sb = new StringBuffer();
         Iterator it = this.bean.getDeclaredFieldNames().iterator();
         if (!it.hasNext()) {
            return "";
         } else {
            while(it.hasNext()) {
               this.curField = (String)it.next();
               if (this.bean.isOneToManyRelation(this.curField) && this.bean.isSelfRelationship(this.curField) && this.bean.getRelatedMultiplicity(this.curField).equals("One")) {
                  sb.append(this.parse(this.getProductionRule("implementAddSelfRelatedBeansToInsertStmtMethodBody")));
               }
            }

            return sb.toString();
         }
      } else {
         return "";
      }
   }

   public String perhapsAddSelfRelatedBeansToDeleteStmt() throws CodeGenerationException {
      if (this.bean.getOrderDatabaseOperations() && this.bean.isSelfRelationship()) {
         StringBuffer sb = new StringBuffer();
         Iterator it = this.bean.getDeclaredFieldNames().iterator();
         if (!it.hasNext()) {
            return "";
         } else {
            while(it.hasNext()) {
               this.curField = (String)it.next();
               if (this.bean.isOneToManyRelation(this.curField) && this.bean.isSelfRelationship(this.curField) && this.bean.getRelatedMultiplicity(this.curField).equals("Many")) {
                  sb.append(this.parse(this.getProductionRule("implementAddSelfRelatedBeansToDeleteStmtMethodBody")));
               }
            }

            return sb.toString();
         }
      } else {
         return "";
      }
   }

   public String perhapsImplementResetIsModifiedVarsMethodBodyOpnBased() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      if (!this.bd.isReadOnly() || this.bean.allowReadonlyCreateAndRemove()) {
         sb.append(this.parse(this.getProductionRule("implementResetIsModifiedVarsMethodBodyOpnBased")));
      }

      return sb.toString();
   }

   public String setBlobClobBasedOnOperation() {
      StringBuffer sb = new StringBuffer();
      if (!this.bean.hasBlobClobColumn()) {
         sb.append("// No blob/clob field is defined for this cmp bean ;" + EOL);
      } else {
         sb.append("if (operation == DDConstants.INSERT) { " + EOL);
         sb.append(this.setBlobClobForCreate());
         sb.append("} else{ " + EOL);
         sb.append(this.setBlobClobForStore());
         sb.append("} " + EOL);
      }

      return sb.toString();
   }

   public String determineBeanParamsForCreateArray() {
      StringBuffer sb = new StringBuffer();
      if (!this.bean.hasClobColumn()) {
         sb.append(this.setBeanParamsForCreateArray() + EOL);
      } else {
         sb.append("if(" + this.pmVar() + ".perhapsUseSetStringForClobForOracle()){" + EOL);
         sb.append(this.setBeanParamsForCreateArrayOptimizedForClobUpdate() + EOL);
         sb.append("} else{ " + EOL);
         sb.append(this.setBeanParamsForCreateArray() + EOL);
         sb.append("} " + EOL);
      }

      return sb.toString();
   }

   public String declareIsInvalidatedVar() {
      return "private boolean " + this.invalidatedBeanIsRegisteredVar() + "= false;" + EOL;
   }

   public String declareIsLoadedVar() {
      StringBuffer sb = new StringBuffer();
      sb.append("private boolean[] ");
      sb.append(this.isLoadedVar());
      sb.append(" = new boolean[");
      sb.append(this.bean.getFieldCount());
      sb.append("];");
      sb.append(EOL);
      sb.append("private boolean " + this.beanIsLoadedVar() + "= false;" + EOL);
      return sb.toString();
   }

   public String isCmrLoadedVarName(String fieldName) {
      return this.varPrefix() + fieldName + "_isLoaded_";
   }

   public String isCmrLoadedVarNameForField() {
      return this.isCmrLoadedVarName(this.curField);
   }

   public String declareCmrIsLoadedVars() {
      StringBuffer sb = new StringBuffer();
      Iterator all = this.bean.getCmrFieldNames().iterator();

      while(all.hasNext()) {
         String fieldName = (String)all.next();
         String varName = this.isCmrLoadedVarName(fieldName);
         sb.append("public ");
         sb.append("boolean ");
         sb.append(varName + " = false;");
         sb.append(EOL);
      }

      return sb.toString();
   }

   public String assignCmrIsLoadedFalse() {
      StringBuffer sb = new StringBuffer();
      Iterator all = this.bean.getCmrFieldNames().iterator();
      if (all.hasNext()) {
         sb.append(EOL);
      }

      while(all.hasNext()) {
         String fieldName = (String)all.next();
         String varName = this.isCmrLoadedVarName(fieldName);
         sb.append(varName + " = false;");
         sb.append(EOL);
      }

      return sb.toString();
   }

   public String declareEntityContextVar() {
      StringBuffer sb = new StringBuffer();
      sb.append("private EntityContext " + this.ctxVar() + ";");
      return sb.toString();
   }

   public String perhapsDeclareInitialContext() {
      return this.bean.getRemoteFieldNames().size() > 0 ? "Context " + this.jctxVar() + " = null;" : "";
   }

   public String perhapsDeclareTableLoadedModifiedVars() {
      if (!this.bean.isOptimistic() && !this.bean.getVerifyReads()) {
         return "";
      } else if (!this.bean.hasMultipleTables()) {
         return "";
      } else {
         StringBuffer sb = new StringBuffer();
         Iterator tables = this.bean.getTables().iterator();

         while(tables.hasNext()) {
            String table = (String)tables.next();
            sb.append("private boolean " + this.tableModifiedVar(table) + " = false;" + EOL);
            sb.append("private boolean " + this.tableLoadedVar(table) + " = false;" + EOL);
         }

         return sb.toString();
      }
   }

   public String perhapsInitializeTableLoadedModifiedVars() {
      if (!this.bean.isOptimistic() && !this.bean.getVerifyReads()) {
         return "";
      } else if (!this.bean.hasMultipleTables()) {
         return "";
      } else {
         StringBuffer sb = new StringBuffer();
         Iterator tables = this.bean.getTables().iterator();

         while(tables.hasNext()) {
            String table = (String)tables.next();
            sb.append(this.tableModifiedVar(table) + " = false;" + EOL);
            sb.append(this.tableLoadedVar(table) + " = false;" + EOL);
         }

         return sb.toString();
      }
   }

   public String perhapsSetTableLoadedVarsForBean() {
      if (!this.bean.isOptimistic() && !this.bean.getVerifyReads()) {
         return "";
      } else if (!this.bean.hasMultipleTables()) {
         return "";
      } else {
         StringBuffer sb = new StringBuffer();
         Iterator tables = this.bean.getTables().iterator();

         while(tables.hasNext()) {
            String table = (String)tables.next();
            sb.append(this.tableLoadedVar(table) + " = true;" + EOL);
         }

         return sb.toString();
      }
   }

   public String perhapsSetTableLoadedVarsForGroup() {
      if (!this.bean.isOptimistic() && !this.bean.getVerifyReads()) {
         return "";
      } else if (!this.bean.hasMultipleTables()) {
         return "";
      } else {
         StringBuffer sb = new StringBuffer();
         Iterator tables = this.bean.getTableNamesForGroup(this.curGroup.getName()).iterator();

         while(tables.hasNext()) {
            String table = (String)tables.next();
            sb.append(this.tableLoadedVar(table) + " = true;" + EOL);
         }

         return sb.toString();
      }
   }

   public String perhapsSetTableModifiedVarForCmpField() {
      if (!this.bean.isOptimistic() && !this.bean.getVerifyReads()) {
         return "";
      } else if (!this.bean.hasMultipleTables()) {
         return "";
      } else {
         StringBuffer sb = new StringBuffer();
         String table = this.bean.getTableForCmpField(this.curField);
         sb.append(this.tableModifiedVar(table) + " = true;" + EOL);
         return sb.toString();
      }
   }

   public String perhapsSetTableModifiedVarForCmrField() {
      if (!this.bean.isOptimistic() && !this.bean.getVerifyReads()) {
         return "";
      } else if (!this.bean.hasMultipleTables()) {
         return "";
      } else {
         StringBuffer sb = new StringBuffer();
         String table = this.bean.getTableForCmrField(this.curField);
         sb.append(this.tableModifiedVar(table) + " = true;" + EOL);
         return sb.toString();
      }
   }

   public String perhapsSetTableModifiedVarsForBean() {
      if (!this.bean.isOptimistic() && !this.bean.getVerifyReads()) {
         return "";
      } else if (!this.bean.hasMultipleTables()) {
         return "";
      } else {
         StringBuffer sb = new StringBuffer();
         Iterator tables = this.bean.getTables().iterator();

         while(tables.hasNext()) {
            String table = (String)tables.next();
            sb.append(this.tableModifiedVar(table) + " = true;" + EOL);
         }

         return sb.toString();
      }
   }

   public String perhapsResetTableModifiedVarsForBean() {
      if (!this.bean.isOptimistic() && !this.bean.getVerifyReads()) {
         return "";
      } else if (!this.bean.hasMultipleTables()) {
         return "";
      } else {
         StringBuffer sb = new StringBuffer();
         Iterator tables = this.bean.getTables().iterator();

         while(tables.hasNext()) {
            String table = (String)tables.next();
            sb.append(this.tableModifiedVar(table) + " = false;" + EOL);
         }

         return sb.toString();
      }
   }

   public String perhapsOptFieldCheckForBatch() {
      if (this.bd.isOptimistic()) {
         String table = this.curTableName;
         if (this.bean.getVerifyColumns(table).equalsIgnoreCase("version") || this.bean.getVerifyColumns(table).equalsIgnoreCase("timestamp")) {
            String optColumn = this.bean.getOptimisticColumn(table);
            String optField = this.bean.getCmpField(table, optColumn);
            return "|| (" + this.isLoadedVar(optField) + " || " + this.isModifiedVar(optField) + ")";
         }
      }

      return "";
   }

   public String perhapsAppendVerifySqlForBatch() throws CodeGenerationException {
      if (!this.bean.isOptimistic()) {
         return "";
      } else {
         StringBuffer sb = new StringBuffer();

         for(Iterator tables = this.bean.getTables().iterator(); tables.hasNext(); this.curTable = null) {
            this.curTable = (String)tables.next();
            sb.append(this.parse(this.getProductionRule("appendVerifySqlForTableForBatch")));
         }

         return sb.toString();
      }
   }

   public String perhapsSetVerifyParamsForBatch() throws CodeGenerationException {
      if (!this.bean.isOptimistic()) {
         return "";
      } else {
         StringBuffer sb = new StringBuffer();

         for(Iterator tables = this.bean.getTables().iterator(); tables.hasNext(); this.curTable = null) {
            this.curTable = (String)tables.next();
            int tableIndex = this.bean.tableIndex(this.curTable);
            sb.append("if ((" + this.tableLoadedVar() + " && " + this.tableModifiedVar() + ") " + this.perhapsOptFieldCheckForBatch() + ") {" + EOL);
            sb.append("int " + this.numVar() + " = verifyCount[" + tableIndex + "];" + EOL);
            String[] fieldNames = (String[])((String[])this.pkFieldNames.toArray(new String[0]));
            sb.append(this.preparedStatementBindings(fieldNames, "this", true, true, true, false, this.stmtArrayElement(tableIndex)));
            sb.append(this.setSnapshotParams());
            sb.append("verifyCount[" + tableIndex + "] = " + this.numVar() + ";" + EOL);
            sb.append("}" + EOL);
         }

         return sb.toString();
      }
   }

   public String perhapsAppendVerifySql() throws CodeGenerationException {
      if (!this.bean.getVerifyReads()) {
         return "";
      } else {
         StringBuffer sb = new StringBuffer();

         for(Iterator tables = this.bean.getTables().iterator(); tables.hasNext(); this.curTable = null) {
            this.curTable = (String)tables.next();
            this.curTableIndex = this.bean.tableIndex(this.curTable);
            sb.append(this.parse(this.getProductionRule("appendVerifySqlForTable")));
         }

         return sb.toString();
      }
   }

   public String verifySqlForTable() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      sb.append("verifySql[" + this.bean.tableIndex(this.curTable) + "].append(\"(");
      Iterator pkFields = this.pkFieldNames.iterator();

      while(pkFields.hasNext()) {
         String pkField = (String)pkFields.next();
         String pkColumn = this.bean.getPKColumnName(this.curTable, pkField);
         sb.append(pkColumn + " = ? ");
         if (pkFields.hasNext()) {
            sb.append("AND ");
         }
      }

      if (!this.bean.getVerifyColumns(this.curTable).equalsIgnoreCase("version") && !this.bean.getVerifyColumns(this.curTable).equalsIgnoreCase("timestamp")) {
         sb.append("\");" + EOL);
         sb.append("StringBuffer " + this.snapshotBufferVar() + " = new StringBuffer();" + EOL);
         sb.append(this.perhapsConstructSnapshotPredicate());
         sb.append("verifySql[" + this.bean.tableIndex(this.curTable) + "].append(" + this.snapshotBufferVar() + ".toString() + \")\");" + EOL);
      } else {
         sb.append(" AND " + this.bean.getOptimisticColumn(this.curTable) + " = ?)\");" + EOL);
      }

      return sb.toString();
   }

   public String perhapsSetVerifyParams() throws CodeGenerationException {
      if (!this.bean.getVerifyReads()) {
         return "";
      } else {
         StringBuffer sb = new StringBuffer();

         for(Iterator tables = this.bean.getTables().iterator(); tables.hasNext(); this.curTable = null) {
            this.curTable = (String)tables.next();
            this.curTableIndex = this.bean.tableIndex(this.curTable);
            sb.append("if (" + this.tableLoadedVar() + " && !" + this.tableModifiedVar() + ") {" + EOL);
            sb.append("int " + this.numVar() + " = verifyCount[" + this.curTableIndex + "];" + EOL);
            sb.append(this.bd.getPrimaryKeyClass().getName() + " " + this.pkVar() + " = (" + this.bd.getPrimaryKeyClass().getName() + ")((EntityEJBContextImpl)" + this.ctxVar() + ").__WL_getPrimaryKey();" + EOL);
            String[] fieldNames = (String[])((String[])this.pkFieldNames.toArray(new String[0]));
            sb.append(this.preparedStatementBindings(fieldNames, this.pkVar(), false, this.bd.hasComplexPrimaryKey(), true, false, this.currStmtArrayVar()));
            if (!this.bean.getVerifyColumns(this.curTable).equalsIgnoreCase("version") && !this.bean.getVerifyColumns(this.curTable).equalsIgnoreCase("timestamp")) {
               sb.append(this.setSnapshotParams());
            } else {
               String optColumn = this.bean.getOptimisticColumn(this.curTable);
               String optField = this.bean.getCmpField(this.curTable, optColumn);
               fieldNames = new String[]{optField};
               sb.append(this.preparedStatementBindings(fieldNames, "this", true, true, true, false, this.currStmtArrayVar()));
            }

            sb.append("verifyCount[" + this.curTableIndex + "] = " + this.numVar() + ";" + EOL);
            sb.append("}" + EOL);
         }

         return sb.toString();
      }
   }

   public String perhapsAssignInitialContext() throws CodeGenerationException {
      if (this.bean.getRemoteFieldNames().size() > 0) {
         StringBuffer sb = new StringBuffer();
         sb.append("try {");
         sb.append(this.jctxVar() + " = new InitialContext();" + EOL);
         sb.append(this.parse(this.getProductionRule("standardCatch")));
         return sb.toString();
      } else {
         return "";
      }
   }

   private String homeVar(String field) {
      return this.varPrefix() + field + "_home";
   }

   public String homeVarForField() {
      return this.homeVar(this.curField);
   }

   public String declareHomeVars() {
      StringBuffer sb = new StringBuffer();
      Iterator iter = this.bean.getRemoteFieldNames().iterator();

      while(iter.hasNext()) {
         String fname = (String)iter.next();
         EjbEntityRef eref = this.bean.getEjbEntityRef(fname);
         sb.append(eref.getHome() + " " + this.homeVar(fname) + ";" + EOL);
      }

      return sb.toString();
   }

   public String declareManagerVars() {
      StringBuffer sb = new StringBuffer();
      sb.append("private RDBMSPersistenceManager " + this.pmVar() + ";" + EOL);
      sb.append("private ClassLoader " + this.classLoaderVar() + ";" + EOL);
      Iterator fieldNames = this.bean.getCmrFieldNames().iterator();

      while(fieldNames.hasNext()) {
         String fieldName = (String)fieldNames.next();
         if (!this.bean.isRemoteField(fieldName)) {
            sb.append(this.declareManagerVarField(fieldName));
         }
      }

      Iterator relationshipCachings = this.ejbSelectBeanTargetMap.keySet().iterator();

      while(relationshipCachings.hasNext()) {
         String beanName = (String)relationshipCachings.next();
         sb.append(this.declareManagerVarBean(beanName));
      }

      relationshipCachings = this.bean.getRelationshipCachings().iterator();

      while(relationshipCachings.hasNext()) {
         RelationshipCaching caching = (RelationshipCaching)relationshipCachings.next();
         Iterator cachingElements = caching.getCachingElements().iterator();
         sb.append(this.declareManagerVarsForCachingElements(this.bean, cachingElements));
      }

      return sb.toString();
   }

   private String declareManagerVarsForCachingElements(RDBMSBean prevBean, Iterator cachingElements) {
      StringBuffer sb = new StringBuffer();

      while(cachingElements.hasNext()) {
         RelationshipCaching.CachingElement cachingElement = (RelationshipCaching.CachingElement)cachingElements.next();
         String cachingElementCmrField = cachingElement.getCmrField();
         RDBMSBean cachingElementBean = prevBean.getRelatedRDBMSBean(cachingElementCmrField);
         String beanName = cachingElementBean.getEjbName();
         sb.append(this.declareManagerVarBean(beanName));
         Iterator cachingElementNested = cachingElement.getCachingElements().iterator();
         if (cachingElementNested.hasNext()) {
            sb.append(this.declareManagerVarsForCachingElements(cachingElementBean, cachingElementNested));
         }
      }

      return sb.toString();
   }

   private String declareManagerVarField(String fieldName) {
      String ejbName = this.bean.getRelatedRDBMSBean(fieldName).getEjbName();
      if (!this.declaredManagerVars.containsKey(fieldName)) {
         this.declaredManagerVars.put(fieldName, ejbName);
         return this.managerVar(fieldName);
      } else {
         return "";
      }
   }

   private String declareManagerVarBean(String name) {
      if (!this.declaredManagerVars.containsKey(name)) {
         this.declaredManagerVars.put(name, name);
         return this.managerVar(name);
      } else {
         return "";
      }
   }

   private String managerVar(String name) {
      return "private CMPBeanManager " + this.bmVar(name) + ";" + EOL;
   }

   public String initializePersistentVars() {
      StringBuffer sb = new StringBuffer();
      Iterator vars = this.variableToClass.keySet().iterator();
      if (vars.hasNext()) {
         sb.append(EOL);
      }

      while(vars.hasNext()) {
         String varName = (String)vars.next();
         Class varClass = (Class)this.variableToClass.get(varName);
         if (this.bean.hasCmpField(varName)) {
            sb.append(this.setCmpField(varName, ClassUtils.getDefaultValue(varClass)) + ";" + EOL);
         } else {
            sb.append("this." + varName + " = " + ClassUtils.getDefaultValue(varClass) + ";" + EOL);
         }
      }

      return sb.toString();
   }

   public String initializeRelationVars() {
      StringBuffer sb = new StringBuffer();
      Iterator all = this.bean.getCmrFieldNames().iterator();
      if (all.hasNext()) {
         sb.append(EOL);
      }

      while(all.hasNext()) {
         String fieldName = (String)all.next();
         String varName = CodeGenUtils.fieldVarName(fieldName);
         Class varClass = this.bean.getCmrFieldClass(fieldName);
         sb.append("this." + varName + " = " + ClassUtils.getDefaultValue(varClass) + ";" + EOL);
         if (this.bean.isSelfRelationship(fieldName)) {
            String removedVarName = CodeGenUtils.fieldRemovedVarName(fieldName);
            sb.append("this." + removedVarName + " = " + ClassUtils.getDefaultValue(varClass) + ";" + EOL);
         }
      }

      sb.append(EOL);
      return sb.toString();
   }

   private boolean doSnapshot(String varName) {
      String fieldName = (String)this.variableToField.get(varName);
      String table = null;
      String colname = null;
      if (this.bean.hasCmpField(fieldName)) {
         table = this.bean.getTableForCmpField(fieldName);
         colname = this.bean.getColumnForCmpFieldAndTable(fieldName, table);
         return !this.bean.getVerifyColumns(table).equalsIgnoreCase("version") && !this.bean.getVerifyColumns(table).equalsIgnoreCase("timestamp") ? true : colname.equals(this.bean.getOptimisticColumn(table));
      } else {
         table = this.bean.getTableForVariable(varName);
         return !this.bean.getVerifyColumns(table).equalsIgnoreCase("version") && !this.bean.getVerifyColumns(table).equalsIgnoreCase("timestamp");
      }
   }

   public String perhapsDeclareSnapshotVars() {
      if (!this.bd.isOptimistic()) {
         return "";
      } else {
         StringBuffer sb = new StringBuffer();
         Iterator iter = this.bean.getTables().iterator();

         while(true) {
            label33:
            while(iter.hasNext()) {
               String table = (String)iter.next();
               String varName;
               Class varClass;
               if (!this.bean.getVerifyColumns(table).equalsIgnoreCase("version") && !this.bean.getVerifyColumns(table).equalsIgnoreCase("timestamp")) {
                  Iterator varNames = this.variableToField.keySet().iterator();

                  while(true) {
                     String fieldName;
                     do {
                        do {
                           do {
                              do {
                                 if (!varNames.hasNext()) {
                                    continue label33;
                                 }

                                 varName = (String)varNames.next();
                                 fieldName = (String)this.variableToField.get(varName);
                              } while(this.bean.isBlobCmpColumnTypeForField(varName));
                           } while(this.bean.isClobCmpColumnTypeForField(varName));
                        } while(this.bd.getPrimaryKeyFieldNames().contains(varName));
                     } while((!this.bean.hasCmpField(fieldName) || !this.bean.getTableForCmpField(fieldName).equals(table)) && (this.bean.hasCmpField(fieldName) || !this.bean.getTableForVariable(varName).equals(table)));

                     varClass = this.getVariableClass(varName);
                     Class snapClass = CodeGenUtils.getSnapshotClass(this.bean, varClass);
                     sb.append("public ");
                     sb.append(ClassUtils.classToJavaSourceType(snapClass) + " ");
                     sb.append(CodeGenUtils.snapshotNameForVar(varName) + ";");
                     sb.append(EOL);
                  }
               } else {
                  String optColumn = this.bean.getOptimisticColumn(table);
                  varName = this.bean.getCmpField(table, optColumn);
                  Class varClass = this.getVariableClass(varName);
                  varClass = CodeGenUtils.getSnapshotClass(this.bean, varClass);
                  sb.append("public ");
                  sb.append(ClassUtils.classToJavaSourceType(varClass) + " ");
                  sb.append(CodeGenUtils.snapshotNameForVar(varName) + ";");
                  sb.append(EOL);
               }
            }

            return sb.toString();
         }
      }
   }

   public String perhapsCreateSnapshotBuffer() {
      return !this.bd.isOptimistic() ? "" : "StringBuffer " + this.snapshotBufferVar() + " = new StringBuffer();";
   }

   public String perhapsAssignOptimisticField() {
      if (!this.bd.isOptimistic()) {
         return "";
      } else {
         StringBuffer sb = new StringBuffer();
         Iterator iter = this.bean.getTables().iterator();

         while(true) {
            String table;
            do {
               if (!iter.hasNext()) {
                  return sb.toString();
               }

               table = (String)iter.next();
            } while(!this.bean.getVerifyColumns(table).equalsIgnoreCase("version") && (!this.bean.getVerifyColumns(table).equalsIgnoreCase("timestamp") || this.bean.getTriggerUpdatesOptimisticColumn(table)));

            String optColumn = this.bean.getOptimisticColumn(table);
            String optField = this.bean.getCmpField(table, optColumn);
            this.curField = optField;
            sb.append("if (!" + this.isModifiedVar(optField) + ") {" + EOL);
            sb.append(this.setterMethodNameForField() + "(" + this.initialOptimisticValue(table) + ");" + EOL);
            sb.append("}" + EOL);
            this.curField = null;
         }
      }
   }

   public String perhapsResetOurOptimisticColumnVariable() {
      if (!this.bd.isOptimistic()) {
         return "";
      } else {
         StringBuffer sb = new StringBuffer();
         Iterator iter = this.bean.getTables().iterator();

         while(true) {
            String table;
            do {
               if (!iter.hasNext()) {
                  return sb.toString();
               }

               table = (String)iter.next();
            } while(!this.bean.getVerifyColumns(table).equalsIgnoreCase("version") && !this.bean.getVerifyColumns(table).equalsIgnoreCase("timestamp"));

            String optColumn = this.bean.getOptimisticColumn(table);
            String optField = this.bean.getCmpField(table, optColumn);
            this.curField = optField;
            sb.append(this.curField + " = ");
            sb.append("this." + CodeGenUtils.snapshotNameForVar(this.curField));
            sb.append(";" + EOL);
            this.curField = null;
         }
      }
   }

   private String initialOptimisticValue(String table) {
      String verifyColumns = this.bean.getVerifyColumns(table);
      if (verifyColumns.equalsIgnoreCase("version")) {
         return "new Long(" + this.bean.getVersionColumnInitialValue(table) + ")";
      } else if (verifyColumns.equalsIgnoreCase("timestamp")) {
         return "new java.sql.Timestamp(System.currentTimeMillis())";
      } else {
         throw new AssertionError("Invalid verify-columns: " + verifyColumns);
      }
   }

   public String updateOptimisticField(String table) {
      StringBuffer sb = new StringBuffer();
      if (this.bean.getVerifyColumns(table).equalsIgnoreCase("version")) {
         sb.append(this.setMethodNameForField() + "__WL_optimisticField(new Long(" + this.getMethodNameForField() + "().longValue()+1));" + EOL);
      } else {
         sb.append("long cur = System.currentTimeMillis();" + EOL);
         sb.append("if ((cur-1000)<=" + this.getMethodNameForField() + "().getTime()) {" + EOL);
         sb.append("cur = " + this.getMethodNameForField() + "().getTime()+1000;" + EOL);
         sb.append("}" + EOL);
         sb.append(this.setMethodNameForField() + "__WL_optimisticField(new java.sql.Timestamp(cur));" + EOL);
      }

      return sb.toString();
   }

   public String perhapsUpdateOptimisticField() {
      if (this.bd.isOptimistic()) {
         String table = this.curTableName;
         boolean timestamp = this.bean.getVerifyColumns(table).equalsIgnoreCase("timestamp");
         boolean version = this.bean.getVerifyColumns(table).equalsIgnoreCase("version");
         boolean trigger = this.bean.getTriggerUpdatesOptimisticColumn(table);
         if ((version || timestamp) && (!timestamp || !trigger)) {
            StringBuffer sb = new StringBuffer();
            String optColumn = this.bean.getOptimisticColumn(table);
            String optField = this.bean.getCmpField(table, optColumn);
            this.curField = optField;
            sb.append("if (" + this.isLoadedVar(optField) + " || " + this.isModifiedVar(optField) + ") {" + EOL);
            sb.append(this.updateOptimisticField(table));
            if (!this.bean.getTriggerUpdatesOptimisticColumn(table)) {
               if (this.useVersionOrTimestampCheckingForBlobClob(table)) {
                  sb.append("if (" + this.countVar() + " > 0) sb.append(\", \");" + EOL);
                  sb.append("sb.append(\" " + optColumn + " = ? \");" + EOL);
               } else {
                  sb.append("sb.append(\", " + optColumn + " = ? \");" + EOL);
               }
            }

            sb.append("}" + EOL);
            this.curField = null;
            return sb.toString();
         }
      }

      return "";
   }

   public String perhapsSetUpdateOptimisticFieldStringForBatch() {
      if (this.bd.isOptimistic()) {
         String table = this.curTableName;
         if (this.bean.getVerifyColumns(table).equalsIgnoreCase("version") || this.bean.getVerifyColumns(table).equalsIgnoreCase("timestamp")) {
            StringBuffer sb = new StringBuffer();
            String optColumn = this.bean.getOptimisticColumn(table);
            String optField = this.bean.getCmpField(table, optColumn);
            this.curField = optField;
            sb.append("if (" + this.isLoadedVar(optField) + " || " + this.isModifiedVar(optField) + ") {" + EOL);
            sb.append(this.isModifiedVar(optField) + " = true;" + EOL);
            if (!this.bean.getTriggerUpdatesOptimisticColumn(table)) {
               if (this.useVersionOrTimestampCheckingForBlobClob(table)) {
                  sb.append("if (" + this.countVar() + " > 0) sb.append(\", \");" + EOL);
                  sb.append("sb.append(\" " + optColumn + " = ? \");" + EOL);
               } else {
                  sb.append("sb.append(\", " + optColumn + " = ? \");" + EOL);
               }
            }

            sb.append("}" + EOL);
            this.curField = null;
            return sb.toString();
         }
      }

      return "";
   }

   public String perhapsUpdateOptimisticFieldForBatch() {
      if (this.bd.isOptimistic()) {
         String table = this.curTableName;
         boolean timestamp = this.bean.getVerifyColumns(table).equalsIgnoreCase("timestamp");
         boolean version = this.bean.getVerifyColumns(table).equalsIgnoreCase("version");
         boolean trigger = this.bean.getTriggerUpdatesOptimisticColumn(table);
         if ((version || timestamp) && (!timestamp || !trigger)) {
            StringBuffer sb = new StringBuffer();
            String optColumn = this.bean.getOptimisticColumn(table);
            String optField = this.bean.getCmpField(table, optColumn);
            this.curField = optField;
            sb.append("if (" + this.isLoadedVar(optField) + " || " + this.isModifiedVar(optField) + ") {" + EOL);
            sb.append(this.updateOptimisticField(table));
            sb.append("}" + EOL);
            this.curField = null;
            return sb.toString();
         }
      }

      return "";
   }

   public String perhapsUpdateOptimisticFieldInTables() {
      if (!this.bd.isOptimistic()) {
         return "";
      } else {
         StringBuffer sb = new StringBuffer();
         if (this.bean.hasMultipleTables()) {
            for(int i = 0; i < this.bean.tableCount(); ++i) {
               this.curTableIndex = i;
               this.curTableName = this.bean.tableAt(this.curTableIndex);
               sb.append("if (" + this.tableModifiedVar(this.curTableName) + ") {" + EOL);
               sb.append(this.perhapsUpdateOptimisticFieldForBatch());
               sb.append("}" + EOL);
            }
         } else {
            sb.append(this.perhapsUpdateOptimisticFieldForBatch());
         }

         return sb.toString();
      }
   }

   public String declareByteArrayVars() {
      StringBuffer sb = new StringBuffer();
      Iterator varNames = this.variableToField.keySet().iterator();

      while(varNames.hasNext()) {
         String varName = (String)varNames.next();
         if (!this.bean.isBlobCmpColumnTypeForField(varName) && !this.bean.isClobCmpColumnTypeForField(varName) && !this.bd.getPrimaryKeyFieldNames().contains(varName) && !this.bean.isValidSQLType(this.getVariableClass(varName))) {
            sb.append("byte[] " + this.byteArrayVar(varName) + " = null;" + EOL);
         }
      }

      return sb.toString();
   }

   public String perhapsTransactionParam() {
      return this.bd.isOptimistic() && this.bean.getDatabaseType() != 1 ? this.txVar() : "";
   }

   public String perhapsSuspendTransaction() {
      if (this.bd.isOptimistic() && this.bean.getDatabaseType() != 1) {
         StringBuffer sb = new StringBuffer();
         sb.append(EOL);
         sb.append("Integer isolation = ").append(this.pmVar()).append(".getTransactionIsolationLevel();").append(EOL);
         sb.append("boolean shouldSuspendTx = (isolation != null && (").append(EOL);
         sb.append("isolation.intValue() != ").append("Connection.TRANSACTION_READ_UNCOMMITTED &&").append(EOL);
         sb.append("isolation.intValue() != ").append("Connection.TRANSACTION_READ_COMMITTED));").append(EOL);
         sb.append("javax.transaction.Transaction " + this.txVar() + "= null;" + EOL);
         sb.append(EOL);
         sb.append("try {" + EOL);
         sb.append("if (shouldSuspendTx) {").append(EOL);
         sb.append(this.txVar() + " = " + this.pmVar() + ".suspendTransaction();" + EOL);
         sb.append("}").append(EOL);
         return sb.toString();
      } else {
         return "";
      }
   }

   public String perhapsResumeTransaction() {
      if (this.bd.isOptimistic() && this.bean.getDatabaseType() != 1) {
         StringBuffer sb = new StringBuffer();
         sb.append("} finally {" + EOL);
         sb.append("try {" + EOL);
         sb.append("if (shouldSuspendTx) {").append(EOL);
         sb.append(this.pmVar() + ".resumeTransaction(" + this.txVar() + ");" + EOL);
         sb.append("}").append(EOL);
         sb.append("} catch (weblogic.ejb20.persistence.spi.PersistenceRuntimeException e) {" + EOL);
         sb.append(this.pmVar() + ".releaseResources(__WL_con, " + this.stmtVar() + ", " + this.rsVar() + ");" + EOL);
         sb.append("throw e;" + EOL);
         sb.append("}" + EOL);
         sb.append("}" + EOL);
         return sb.toString();
      } else {
         return "";
      }
   }

   public String perhapsGetNullSnapshotVariables() {
      StringBuffer sb = new StringBuffer();
      if (!this.bd.isOptimistic()) {
         sb.append("return null;");
         return sb.toString();
      } else {
         sb.append("Collection nullSnapshotVariables = new HashSet();" + EOL);
         Iterator iter = this.bean.getTables().iterator();

         while(true) {
            label38:
            while(iter.hasNext()) {
               String table = (String)iter.next();
               String varName;
               if (!this.bean.getVerifyColumns(table).equalsIgnoreCase("version") && !this.bean.getVerifyColumns(table).equalsIgnoreCase("timestamp")) {
                  Iterator varNames = this.variableToField.keySet().iterator();

                  while(true) {
                     String fieldName;
                     do {
                        do {
                           do {
                              do {
                                 if (!varNames.hasNext()) {
                                    continue label38;
                                 }

                                 varName = (String)varNames.next();
                                 fieldName = (String)this.variableToField.get(varName);
                              } while(this.bean.isBlobCmpColumnTypeForField(varName));
                           } while(this.bean.isClobCmpColumnTypeForField(varName));
                        } while(this.bd.getPrimaryKeyFieldNames().contains(fieldName));
                     } while((!this.bean.hasCmpField(fieldName) || !this.bean.getTableForCmpField(fieldName).equals(table)) && (this.bean.hasCmpField(fieldName) || !this.bean.getTableForVariable(varName).equals(table)));

                     Class varClass = this.getVariableClass(varName);
                     String snapName = CodeGenUtils.snapshotNameForVar(varName);
                     if (!varClass.isPrimitive()) {
                        sb.append("if ( this." + snapName + " == null) {" + EOL);
                        sb.append("  nullSnapshotVariables.add(\"" + varName + "\");" + EOL);
                        sb.append("}" + EOL);
                     }
                  }
               } else {
                  String optColumn = this.bean.getOptimisticColumn(table);
                  varName = this.bean.getCmpField(table, optColumn);
                  Class varClass = this.getVariableClass(varName);
                  String snapName = CodeGenUtils.snapshotNameForVar(varName);
                  Class snapClass = CodeGenUtils.getSnapshotClass(this.bean, varClass);
                  String className = ClassUtils.classToJavaSourceType(snapClass);
                  if (!varClass.isPrimitive()) {
                     sb.append("if ( this." + snapName + " == null) {" + EOL);
                     sb.append("  nullSnapshotVariables.add(\"" + varName + "\");" + EOL);
                     sb.append("}" + EOL);
                  }
               }
            }

            sb.append("return nullSnapshotVariables;" + EOL);
            return sb.toString();
         }
      }
   }

   public String perhapsInitializeSnapshotVars() {
      if (!this.bd.isOptimistic()) {
         return "";
      } else {
         StringBuffer sb = new StringBuffer();
         Iterator iter = this.bean.getTables().iterator();

         while(true) {
            label34:
            while(iter.hasNext()) {
               String table = (String)iter.next();
               String varName;
               if (!this.bean.getVerifyColumns(table).equalsIgnoreCase("version") && !this.bean.getVerifyColumns(table).equalsIgnoreCase("timestamp")) {
                  Iterator varNames = this.variableToField.keySet().iterator();

                  while(true) {
                     String fieldName;
                     do {
                        do {
                           do {
                              do {
                                 if (!varNames.hasNext()) {
                                    sb.append(EOL);
                                    continue label34;
                                 }

                                 varName = (String)varNames.next();
                                 fieldName = (String)this.variableToField.get(varName);
                              } while(this.bean.isBlobCmpColumnTypeForField(varName));
                           } while(this.bean.isClobCmpColumnTypeForField(varName));
                        } while(this.bd.getPrimaryKeyFieldNames().contains(fieldName));
                     } while((!this.bean.hasCmpField(fieldName) || !this.bean.getTableForCmpField(fieldName).equals(table)) && (this.bean.hasCmpField(fieldName) || !this.bean.getTableForVariable(varName).equals(table)));

                     Class varClass = this.getVariableClass(varName);
                     String snapName = CodeGenUtils.snapshotNameForVar(varName);
                     Class snapClass = CodeGenUtils.getSnapshotClass(this.bean, varClass);
                     String className = ClassUtils.classToJavaSourceType(snapClass);
                     sb.append(snapName + " = " + ClassUtils.getDefaultValue(snapClass) + ";" + EOL);
                  }
               } else {
                  String optColumn = this.bean.getOptimisticColumn(table);
                  varName = this.bean.getCmpField(table, optColumn);
                  Class varClass = this.getVariableClass(varName);
                  String snapName = CodeGenUtils.snapshotNameForVar(varName);
                  Class snapClass = CodeGenUtils.getSnapshotClass(this.bean, varClass);
                  String className = ClassUtils.classToJavaSourceType(snapClass);
                  sb.append(snapName + " = " + ClassUtils.getDefaultValue(snapClass) + ";" + EOL);
               }
            }

            return sb.toString();
         }
      }
   }

   private String takeSnapshotForVar(String beanName, String varName, boolean assignUsingMethod) {
      if (!this.bean.isBlobCmpColumnTypeForField(varName) && !this.bean.isClobCmpColumnTypeForField(varName)) {
         StringBuffer sb = new StringBuffer();
         if (!this.bd.getPrimaryKeyFieldNames().contains(varName)) {
            Class varClass = this.getVariableClass(varName);
            String snapName = CodeGenUtils.snapshotNameForVar(varName);
            Class snapClass = CodeGenUtils.getSnapshotClass(this.bean, varClass);
            String className = ClassUtils.classToJavaSourceType(snapClass);
            String value = null;
            if (assignUsingMethod) {
               value = beanName + "." + MethodUtils.getMethodName(varName) + "()";
            } else {
               value = beanName + "." + varName;
            }

            if (Date.class.isAssignableFrom(varClass)) {
               sb.append("if (" + value + "==null) {" + EOL);
               sb.append(beanName + "." + snapName + " = null;" + EOL);
               sb.append("}" + EOL);
               sb.append("else {" + EOL);
               sb.append(beanName + "." + snapName + " = (" + varClass.getName() + ")" + value + ".clone();" + EOL);
               sb.append("}" + EOL);
            } else {
               sb.append(beanName + "." + snapName + " = " + value + ";" + EOL);
            }
         }

         return sb.toString();
      } else {
         return "";
      }
   }

   public String perhapsTakeSnapshot() {
      if (!this.bd.isOptimistic()) {
         return "";
      } else {
         StringBuffer sb = new StringBuffer();
         Iterator iter = this.bean.getTables().iterator();

         while(true) {
            label54:
            while(iter.hasNext()) {
               String table = (String)iter.next();
               boolean timestamp = this.bean.getVerifyColumns(table).equalsIgnoreCase("timestamp");
               boolean version = this.bean.getVerifyColumns(table).equalsIgnoreCase("version");
               String varName;
               if (!version && !timestamp) {
                  Iterator varNames = this.variableToField.keySet().iterator();
                  sb.append(EOL);

                  while(true) {
                     while(true) {
                        String fieldName;
                        do {
                           do {
                              if (!varNames.hasNext()) {
                                 continue label54;
                              }

                              varName = (String)varNames.next();
                              fieldName = (String)this.variableToField.get(varName);
                              if (this.bean.isBlobCmpColumnTypeForField(varName) || this.bean.isClobCmpColumnTypeForField(varName)) {
                                 return "";
                              }
                           } while(this.bd.getPrimaryKeyFieldNames().contains(fieldName));
                        } while((!this.bean.hasCmpField(fieldName) || !this.bean.getTableForCmpField(fieldName).equals(table)) && (this.bean.hasCmpField(fieldName) || !this.bean.getTableForVariable(varName).equals(table)));

                        Class varClass = this.getVariableClass(varName);
                        sb.append("if (" + this.isLoadedVar(fieldName) + ")" + EOL);
                        if (this.bean.isValidSQLType(varClass)) {
                           sb.append(this.takeSnapshotForVar("this", varName, !this.bd.isBeanClassAbstract() && this.bean.hasCmpField(varName)));
                        } else {
                           sb.append(CodeGenUtils.snapshotNameForVar(varName) + " = " + this.byteArrayVar(varName) + ";" + EOL);
                        }
                     }
                  }
               } else if (!timestamp || !this.bean.getTriggerUpdatesOptimisticColumn(table)) {
                  String optColumn = this.bean.getOptimisticColumn(table);
                  varName = this.bean.getCmpField(table, optColumn);
                  sb.append("if (" + this.isLoadedVar(varName) + " || " + this.isModifiedVar(varName) + ") {" + EOL);
                  sb.append(this.takeSnapshotForVar("this", varName, !this.bd.isBeanClassAbstract() && this.bean.hasCmpField(varName)));
                  sb.append("}" + EOL);
               }
            }

            return sb.toString();
         }
      }
   }

   public String perhapsInvalidateOptimisticColumnField() {
      if (!this.bd.isOptimistic()) {
         return "";
      } else {
         StringBuffer sb = new StringBuffer();
         Iterator iter = this.bean.getTables().iterator();

         while(iter.hasNext()) {
            String table = (String)iter.next();
            if (this.bean.getVerifyColumns(table).equalsIgnoreCase("timestamp") && this.bean.getTriggerUpdatesOptimisticColumn(table)) {
               String optColumn = this.bean.getOptimisticColumn(table);
               String optField = this.bean.getCmpField(table, optColumn);
               sb.append(this.isLoadedVar(optField) + " = false;" + EOL);
            }
         }

         if (sb.length() > 0 && this.bd.getCacheBetweenTransactions()) {
            FieldGroup group = this.bean.getFieldGroup("optimisticTimestampTriggerGroup");
            sb.append("int oldState = __WL_method_state;" + EOL);
            sb.append("try {" + EOL);
            sb.append("__WL_method_state = STATE_EJBSTORE;" + EOL);
            sb.append(this.loadMethodName(this.getFieldGroupSuffix(group)) + "();" + EOL);
            sb.append("} finally {" + EOL);
            sb.append("__WL_method_state = oldState;" + EOL);
            sb.append("}" + EOL);
         }

         return sb.toString();
      }
   }

   public String perhapsReloadOptimisticColumn() {
      if (!this.bd.isOptimistic()) {
         return "";
      } else {
         StringBuffer sb = new StringBuffer();
         Iterator iter = this.bean.getTables().iterator();

         while(iter.hasNext()) {
            String table = (String)iter.next();
            if (this.bean.getVerifyColumns(table).equalsIgnoreCase("timestamp") && this.bean.getTriggerUpdatesOptimisticColumn(table) && !this.bd.getCacheBetweenTransactions()) {
               String optColumn = this.bean.getOptimisticColumn(table);
               String optField = this.bean.getCmpField(table, optColumn);
               sb.append("if (!" + this.isLoadedVar(optField) + " && !" + this.isModifiedVar(optField) + ") {" + EOL);
               FieldGroup group = this.bean.getFieldGroup("optimisticTimestampTriggerGroup");
               sb.append(this.loadMethodName(this.getFieldGroupSuffix(group)) + "();" + EOL);
               sb.append("}" + EOL);
            }
         }

         return sb.toString();
      }
   }

   public String perhapsConstructSnapshotPredicate() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      if (this.bd.isOptimistic()) {
         String curTable = this.bean.tableAt(this.curTableIndex);
         sb.append(EOL);
         sb.append(this.snapshotBufferVar() + ".setLength(0);" + EOL);
         String fieldName;
         if (!this.bean.getVerifyColumns(curTable).equalsIgnoreCase("version") && !this.bean.getVerifyColumns(curTable).equalsIgnoreCase("timestamp")) {
            for(int i = 0; i < this.bean.getFieldCount(); ++i) {
               fieldName = this.isModifiedIndexToField(i);
               int fTableIndex;
               if (this.bean.isCmpFieldName(fieldName)) {
                  if (!this.bean.isBlobCmpColumnTypeForField(fieldName) && !this.bean.isClobCmpColumnTypeForField(fieldName) && !this.bd.getPrimaryKeyFieldNames().contains(fieldName)) {
                     fTableIndex = this.bean.getTableIndexForCmpField(fieldName);
                     if (fTableIndex == -1) {
                        throw new CodeGenerationException("perhapsConstructSnapshotPredicate: Could not find tableIndex for cmp-field: '" + fieldName + "'");
                     }

                     if (fTableIndex == this.curTableIndex) {
                        Class fieldClass = this.bean.getCmpFieldClass(fieldName);
                        Class snapClass = CodeGenUtils.getSnapshotClass(this.bean, fieldClass);
                        sb.append("if (" + this.isLoadedVar(fieldName));
                        if (this.bean.getVerifyColumns(curTable).equalsIgnoreCase("modified")) {
                           sb.append(" && " + this.isModifiedVar(fieldName));
                        }

                        sb.append(") {" + EOL);
                        if (snapClass.isPrimitive()) {
                           sb.append(this.snapshotBufferVar() + ".append(\" AND \" +" + this.pmVar() + ".getSnapshotPredicate(" + this.bean.getIsModifiedIndex(fieldName) + "));" + EOL);
                        } else {
                           sb.append(this.snapshotBufferVar() + ".append(\" AND \" +" + this.pmVar() + ".getSnapshotPredicate(" + this.bean.getIsModifiedIndex(fieldName) + ", this." + CodeGenUtils.snapshotNameForVar(fieldName) + "));" + EOL);
                        }

                        sb.append("}" + EOL);
                     }
                  }
               } else if (this.bean.containsFkField(fieldName) && !this.bean.isForeignCmpField(fieldName)) {
                  fTableIndex = this.bean.getTableIndexForCmrf(fieldName);
                  if (fTableIndex == -1) {
                     throw new CodeGenerationException("perhapsConstructSnapshotPredicate: Could not find tableIndex for cmr-field: '" + fieldName + "'");
                  }

                  if (fTableIndex == this.curTableIndex) {
                     Iterator colNames = this.bean.getForeignKeyColNames(fieldName).iterator();
                     String colName = (String)colNames.next();
                     String varName = this.bean.variableForField(fieldName, curTable, colName);
                     Class varClass = this.bean.getForeignKeyColClass(fieldName, colName);
                     String snapName = CodeGenUtils.snapshotNameForVar(varName);
                     Class snapClass = CodeGenUtils.getSnapshotClass(this.bean, varClass);
                     sb.append("if (" + this.isLoadedVar(fieldName));
                     if (this.bean.getVerifyColumns(curTable).equalsIgnoreCase("modified")) {
                        sb.append(" && " + this.isModifiedVar(fieldName));
                     }

                     sb.append(") {" + EOL);
                     if (snapClass.isPrimitive()) {
                        sb.append(this.snapshotBufferVar() + ".append(\" AND \" +" + this.pmVar() + ".getSnapshotPredicate(" + this.bean.getIsModifiedIndex(fieldName) + "));" + EOL);
                     } else {
                        sb.append(this.snapshotBufferVar() + ".append(\" AND \" +" + this.pmVar() + ".getSnapshotPredicate(" + this.bean.getIsModifiedIndex(fieldName) + ", this." + snapName + "));" + EOL);
                     }

                     sb.append("}" + EOL);
                  }
               }
            }
         } else {
            String optColumn = this.bean.getOptimisticColumn(curTable);
            fieldName = this.bean.getCmpField(curTable, optColumn);
            sb.append("if (" + this.isLoadedVar(fieldName) + " || " + this.isModifiedVar(fieldName) + ") {" + EOL);
            sb.append(this.snapshotBufferVar() + ".append(\" AND \" +" + this.pmVar() + ".getSnapshotPredicate(" + this.bean.getIsModifiedIndex(fieldName) + ", this." + CodeGenUtils.snapshotNameForVar(fieldName) + "));" + EOL);
            sb.append("}" + EOL);
         }
      }

      return sb.toString();
   }

   public String perhapsAddSnapshotPredicate() {
      StringBuffer sb = new StringBuffer();
      if (this.bd.isOptimistic()) {
         sb.append("+ " + this.snapshotBufferVar() + ".toString()");
      }

      return sb.toString();
   }

   public String perhapsSetSnapshotParameters() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      if (this.bd.isOptimistic()) {
         sb.append(this.setSnapshotParams());
      }

      return sb.toString();
   }

   public String setSnapshotParams() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      sb.append(EOL);
      String curTable = this.bean.tableAt(this.curTableIndex);
      String fieldName;
      if (!this.bean.getVerifyColumns(curTable).equalsIgnoreCase("version") && !this.bean.getVerifyColumns(curTable).equalsIgnoreCase("timestamp")) {
         for(int i = 0; i < this.bean.getFieldCount(); ++i) {
            fieldName = this.isModifiedIndexToField(i);

            assert fieldName != null;

            int fTableIndex;
            if (this.bean.isCmpFieldName(fieldName)) {
               if (!this.bd.getPrimaryKeyFieldNames().contains(fieldName) && !this.bean.isBlobCmpColumnTypeForField(fieldName) && !this.bean.isClobCmpColumnTypeForField(fieldName)) {
                  fTableIndex = this.bean.getTableIndexForCmpField(fieldName);
                  if (fTableIndex == -1) {
                     throw new CodeGenerationException("perhapsSetSnapshotParameters: Could not find tableIndex for field: '" + fieldName + "'");
                  }

                  if (fTableIndex == this.curTableIndex) {
                     String snapName = CodeGenUtils.snapshotNameForVar(fieldName);
                     sb.append("if (" + this.isLoadedVar(fieldName));
                     if (this.bean.getVerifyColumns(curTable).equalsIgnoreCase("modified")) {
                        sb.append(" && " + this.isModifiedVar(fieldName));
                     }

                     sb.append(") {" + EOL);
                     sb.append("if(" + this.debugEnabled() + ") " + this.debugSay() + "(\"setting(\"+this+\") '" + snapName + "' using column \" +" + this.numVar() + " + \". Value is \" + this." + snapName + ");" + EOL);
                     this.addSnapshotBinding(sb, fieldName, "this", this.numVar(), this.currStmtArrayVar());
                     sb.append("}" + EOL);
                  }
               }
            } else {
               fTableIndex = this.bean.getTableIndexForCmrf(fieldName);
               if (fTableIndex == -1) {
                  throw new CodeGenerationException("perhapsSetSnapshotParameters: Could not find tableIndex for field: '" + fieldName + "'");
               }

               if (fTableIndex == this.curTableIndex) {
                  Iterator fkColumns = this.bean.getForeignKeyColNames(fieldName).iterator();

                  while(fkColumns.hasNext()) {
                     String colName = (String)fkColumns.next();
                     String varName = this.bean.variableForField(fieldName, curTable, colName);
                     String snapName = CodeGenUtils.snapshotNameForVar(varName);
                     sb.append("if (" + this.isLoadedVar(fieldName));
                     if (this.bean.getVerifyColumns(curTable).equalsIgnoreCase("modified")) {
                        sb.append(" && " + this.isModifiedVar(fieldName));
                     }

                     sb.append(") {" + EOL);
                     sb.append("if(" + this.debugEnabled() + ") " + this.debugSay() + "(\"setting(\"+this+\") '" + snapName + "' using column \" +" + this.numVar() + " + \". Value is \" + this." + snapName + ");" + EOL);
                     this.addSnapshotBinding(sb, varName, "this", this.numVar(), this.currStmtArrayVar());
                     sb.append("}" + EOL);
                  }
               }
            }
         }
      } else {
         String optColumn = this.bean.getOptimisticColumn(curTable);
         fieldName = this.bean.getCmpField(curTable, optColumn);
         String snapName = CodeGenUtils.snapshotNameForVar(fieldName);
         sb.append("if (" + this.isLoadedVar(fieldName) + " || " + this.isModifiedVar(fieldName) + ") {" + EOL);
         sb.append("if(" + this.debugEnabled() + ") " + this.debugSay() + "(\"setting(\"+this+\") '" + snapName + "' using column \" +" + this.numVar() + " + \". Value is \" + this." + snapName + ");" + EOL);
         this.addSnapshotBinding(sb, fieldName, "this", this.numVar(), this.currStmtArrayVar());
         sb.append("}" + EOL);
      }

      return sb.toString();
   }

   private void addSnapshotBinding(StringBuffer sb, String var, String obj, String paramIdx, String stmtVar) {
      if (debugLogger.isDebugEnabled()) {
         debug("Adding a snapshot binding: ");
         debug("\t\tvar = " + var);
         debug("\t\tobj = " + obj);
         debug("\t\tparamIdx = " + paramIdx);
      }

      if (!this.bean.isBlobCmpColumnTypeForField(var) && !this.bean.isClobCmpColumnTypeForField(var)) {
         Class varClass = this.getVariableClass(var);
         String snapName = CodeGenUtils.snapshotNameForVar(var);
         Class snapClass = CodeGenUtils.getSnapshotClass(this.bean, varClass);
         if (!snapClass.isPrimitive()) {
            sb.append("if (" + snapName + "!= null) {" + EOL);
         }

         this.snapshotBindingBody(sb, obj, var, varClass, paramIdx, stmtVar);
         sb.append(this.numVar() + "++;" + EOL);
         if (!snapClass.isPrimitive()) {
            sb.append("}" + EOL);
         }

      } else {
         sb.append("  ");
      }
   }

   private void snapshotBindingBody(StringBuffer sb, String obj, String var, Class varClass, String paramIdx, String stmtVar) {
      String snapName = CodeGenUtils.snapshotNameForVar(var);
      CodeGenUtils.getSnapshotClass(this.bean, varClass);
      if (this.bean.isValidSQLType(varClass) && !ClassUtils.isByteArray(varClass)) {
         String varTypeName = StatementBinder.getStatementTypeNameForClass(varClass);
         if (RDBMSUtils.isOracleNLSDataType(this.bean, var)) {
            sb.append("if(").append(stmtVar).append(" instanceof oracle.jdbc.OraclePreparedStatement) {" + EOL);
            sb.append("((oracle.jdbc.OraclePreparedStatement)").append(stmtVar).append(").setFormOfUse(").append(paramIdx).append(", oracle.jdbc.OraclePreparedStatement.FORM_NCHAR);").append(EOL);
            sb.append("}" + EOL);
         }

         sb.append(stmtVar);
         sb.append(".set" + varTypeName + "(");
         sb.append(paramIdx).append(", ");
         if (varClass == Character.TYPE) {
            sb.append("String.valueOf(" + obj + "." + snapName + ")");
         } else if (varClass == Character.class) {
            sb.append("String.valueOf(" + obj + "." + snapName + ".charValue())");
         } else if (varClass == Date.class) {
            sb.append("new java.sql.Timestamp(");
            sb.append(obj + ".");
            sb.append(MethodUtils.convertToPrimitive(varClass, snapName));
            sb.append(".getTime())");
         } else {
            sb.append(obj + ".");
            sb.append(MethodUtils.convertToPrimitive(varClass, snapName));
         }

         sb.append(");" + EOL);
      } else if (!ClassUtils.isByteArray(varClass) || !"SybaseBinary".equalsIgnoreCase(this.bean.getCmpColumnTypeForField(var)) && !this.perhapsSybaseBinarySetForAnyCmpField()) {
         sb.append("InputStream inputStream  = new ByteArrayInputStream(" + snapName + ");" + EOL);
         sb.append(stmtVar + ".setBinaryStream(" + paramIdx + ", inputStream, " + snapName + ".length);" + EOL);
      } else {
         sb.append(stmtVar + ".setBytes(" + paramIdx + "," + snapName + ");" + EOL);
      }

   }

   public String throwOperationFailedException() {
      return this.bd.isOptimistic() ? " Loggable l = EJBLogger.logoptimisticUpdateFailedLoggable( \"" + this.bd.getEJBName() + "\"," + this.pkVar() + ".toString());" + EOL + " throw new OptimisticConcurrencyException(l.getMessageText());" : " Loggable l = EJBLogger.logbeanDoesNotExistLoggable(\"" + this.bd.getEJBName() + "\"," + this.pkVar() + ".toString()); " + EOL + "throw new NoSuchEntityException(l.getMessageText()); ";
   }

   public String primaryFieldClassesArray(String fieldName) {
      CMPBeanDescriptor d = null;
      if (this.bean.isForeignKeyField(fieldName) && this.bean.containsFkField(fieldName)) {
         d = this.bean.getRelatedDescriptor(fieldName);
      } else {
         d = this.bd;
      }

      return "new Class[] {" + d.getPrimaryKeyClass().getName() + ".class }";
   }

   public String assignHomeVars() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      boolean haveSomething = false;
      Iterator fnames = this.bean.getRemoteFieldNames().iterator();
      if (fnames.hasNext()) {
         sb.append("try {");
         haveSomething = true;
         sb.append("if (" + this.debugEnabled() + ") {" + EOL);
         sb.append("Context ctx = (Context)" + this.jctxVar() + ".lookup(\"java:comp/env\");" + EOL);
         sb.append("assert (ctx!=null);" + EOL);
         sb.append(this.debugSay() + "(\"Listing contents of java:comp/env\");" + EOL);
         sb.append("NamingEnumeration nenum = ctx.list(\"\");" + EOL);
         sb.append("while (nenum.hasMore()) {" + EOL);
         sb.append("NameClassPair ncp = (NameClassPair)nenum.next();" + EOL);
         sb.append(this.debugSay() + "(\"name bound- \" + ncp.getName());" + EOL);
         sb.append("}" + EOL);
         sb.append("}" + EOL);
      }

      while(fnames.hasNext()) {
         String fname = (String)fnames.next();
         EjbEntityRef eref = this.bean.getEjbEntityRef(fname);
         sb.append("if (" + this.debugEnabled() + ") {" + EOL);
         sb.append(this.debugSay() + "(\"Looking up name- " + eref.getEjbRefName() + "\");" + EOL);
         sb.append("}" + EOL);
         sb.append(this.homeVar(fname) + " = (" + eref.getHome() + ")");
         sb.append(this.jctxVar() + ".lookup(\"java:comp/env/");
         sb.append(eref.getEjbRefName() + "\");" + EOL);
      }

      if (haveSomething) {
         sb.append(this.parse(this.getProductionRule("standardCatch")));
      }

      return sb.toString();
   }

   public String assignManagerVars() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      boolean haveSomething = false;

      for(Iterator var3 = this.declaredManagerVars.entrySet().iterator(); var3.hasNext(); haveSomething = true) {
         Map.Entry me = (Map.Entry)var3.next();
         String varName = (String)me.getKey();
         String beanName = (String)me.getValue();
         Map hasSeen = new HashMap();
         if (hasSeen.containsKey(beanName)) {
            sb.append(this.bmVar(varName)).append(" = ");
            sb.append((String)hasSeen.get(beanName)).append(";").append(EOL);
         } else {
            sb.append(this.bmVar(varName) + " = ");
            sb.append("(CMPBeanManager)bmMap.get(\"");
            sb.append(beanName);
            sb.append("\");" + EOL);
            sb.append("assert (bmMap.get(\"");
            sb.append(beanName);
            sb.append("\")!=null);" + EOL + EOL);
            hasSeen.put(beanName, this.bmVar(varName));
         }
      }

      if (haveSomething) {
         return "try {" + sb.toString() + this.parse(this.getProductionRule("standardCatch"));
      } else {
         return "";
      }
   }

   public String assignEjbSelectMethodVars() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      boolean haveSomething = false;
      Iterator it = this.finderList.iterator();

      while(true) {
         while(true) {
            Finder f;
            do {
               if (!it.hasNext()) {
                  if (haveSomething) {
                     sb.append("}");
                     sb.append(EOL);
                     return sb.toString();
                  }

                  return "";
               }

               f = (Finder)it.next();
            } while(f.getQueryType() != 4 && f.getQueryType() != 2 && (!f.isSelect() || !(f instanceof SqlFinder)));

            if (!haveSomething) {
               sb.append("static {");
               sb.append(EOL);
               sb.append("Method m = null;");
               sb.append(EOL);
               haveSomething = true;
            }

            if (f.isSelect() && f instanceof SqlFinder) {
               String genBeanName = this.bean.getCMPBeanDescriptor().getGeneratedBeanInterfaceName();
               sb.append("try {");
               sb.append(EOL);
               sb.append("m = ");
               sb.append(genBeanName);
               sb.append(".class.getMethod(\"");
               sb.append(f.getName());
               sb.append("\", ");
               sb.append("new Class[] { ");
               Class[] parameterTypes = f.getParameterClassTypes();

               for(int i = 0; i < parameterTypes.length; ++i) {
                  sb.append(ClassUtils.classToJavaSourceType(parameterTypes[i]));
                  sb.append(".class");
                  if (i < parameterTypes.length - 1) {
                     sb.append(", ");
                  }
               }

               sb.append("});" + EOL);
               sb.append("} catch (NoSuchMethodException ignore) {");
               sb.append(EOL);
               sb.append("m = null;");
               sb.append(EOL);
               sb.append("}");
               sb.append(EOL);
               sb.append(this.ejbSelectMDName(f));
               sb.append(" = m;");
               sb.append(EOL);
            } else {
               RDBMSBean bean = f.getSelectBeanTarget();
               if (bean == null) {
                  Loggable l = EJBLogger.logGotNullXForFinderLoggable("getSelectBeanTarget", f.toString());
                  throw new CodeGenerationException(l.getMessageText());
               }

               CMPBeanDescriptor rbd = (CMPBeanDescriptor)this.beanMap.get(bean.getEjbName());
               if (rbd == null) {
                  Loggable l = EJBLogger.logGotNullBeanFromBeanMapLoggable(bean.getEjbName());
                  throw new CodeGenerationException(l.getMessageText());
               }

               String genBeanName = rbd.getGeneratedBeanInterfaceName();
               sb.append("try {");
               sb.append(EOL);
               sb.append("m = ");
               sb.append(genBeanName);
               sb.append(".class.getMethod(\"");
               sb.append(this.ejbSelectMDName(f));
               sb.append("\", ");
               sb.append(EOL);
               sb.append("         ");
               sb.append("new Class[] { ");
               StringBuffer sb2 = new StringBuffer();
               Iterator it2 = f.getExternalMethodAndInEntityParmList().iterator();

               while(it2.hasNext()) {
                  ParamNode n = (ParamNode)it2.next();
                  Class c = n.getParamClass();
                  sb2.append(c.getName());
                  sb2.append(".class, ");
                  sb2.append(EOL);
               }

               if (sb2.length() > 2) {
                  sb2.setLength(sb2.length() - 2);
               }

               sb.append(sb2.toString());
               sb.append("} );");
               sb.append("} catch (NoSuchMethodException ignore) {");
               sb.append(EOL);
               sb.append("m = null;");
               sb.append(EOL);
               sb.append("}");
               sb.append(EOL);
               sb.append(this.ejbSelectMDName(f));
               sb.append(" = m;");
               sb.append(EOL + EOL);
            }
         }
      }
   }

   public String fieldNameGetPrimaryKey() {
      return this.fieldNameForField() + ".getPrimaryKey()";
   }

   public String fieldVarGetPrimaryKey() {
      return this.fieldVarForField() + ".getPrimaryKey()";
   }

   public String fieldNameForField() {
      return this.curField;
   }

   public String assignFieldVarForFieldWithFieldNameForField() {
      return this.fieldVarForField() + " = " + this.fieldNameForField() + ";";
   }

   public String assignFieldVarForFieldWithAllocatedOneToManySet() {
      return this.fieldVarForField() + " = " + this.allocateOneToManySet() + ";";
   }

   public String assignFieldVarForFieldWithOneToManySetClone() {
      return this.fieldVarForField() + " = (Set)(((" + this.collectionClassForField() + ")" + EOL + "      " + this.fieldVarForField() + ").clone());";
   }

   public String assignFieldVarForFieldWithAllocatedManyToManySet() {
      return this.fieldVarForField() + " = " + this.allocateManyToManySet() + ";";
   }

   public String assignFieldVarForFieldWithNull() {
      return this.fieldVarForField() + " = null;";
   }

   public String setterMethodNameForField() {
      return "set" + this.curField.substring(0, 1).toUpperCase(Locale.ENGLISH) + this.curField.substring(1);
   }

   public String doSetMethodNameForField() {
      return this.doSetMethodName(this.curField);
   }

   public String setRestMethodNameForField() {
      assert this.curField != null;

      return this.setRestMethodName(this.curField);
   }

   public String getMethodNameForField() {
      return MethodUtils.getMethodName(this.curField);
   }

   public String setMethodNameForField() {
      return MethodUtils.setMethodName(this.curField);
   }

   public String getRelatedMethodNameForField() {
      String relFieldNm = this.bean.getRelatedFieldName(this.curField);
      return MethodUtils.getMethodName(relFieldNm);
   }

   public String declareCmpGettersAndSetters() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      Iterator fieldIter = this.cmpFieldNames.iterator();

      while(fieldIter.hasNext()) {
         this.curField = (String)fieldIter.next();
         sb.append(this.parse(this.getProductionRule("cmpGetMethod")));
         sb.append(this.parse(this.getProductionRule("cmpSetMethod")));
         sb.append(EOL);
      }

      return sb.toString();
   }

   public String cmpSetMethodGuard() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      if (this.bd.isReadOnly() && !this.bean.allowReadonlyCreateAndRemove()) {
         sb.append("Loggable l = EJBLogger.logCannotCallSetForReadOnlyBeanLoggable(\"" + this.bean.getEjbName() + "\");" + EOL);
         sb.append("throw new javax.ejb.EJBException(l.getMessageText());" + EOL);
      } else {
         String table;
         if (this.bean.isDbmsDefaultValueField(this.curField)) {
            sb.append("if (__WL_method_state==STATE_EJB_CREATE ||");
            sb.append(EOL);
            if (!this.bean.getDelayInsertUntil().equals("ejbCreate")) {
               sb.append("__WL_method_state==STATE_EJB_POSTCREATE ||");
               sb.append(EOL);
            }

            Iterator it = this.bd.getPrimaryKeyFieldNames().iterator();

            while(it.hasNext()) {
               table = (String)it.next();
               sb.append(this.isModifiedVar(table));
               if (it.hasNext()) {
                  sb.append("||").append(EOL);
               }
            }

            sb.append(") {" + EOL);
            sb.append("Loggable l = EJBLogger.logCannotCallSetOnDBMSDefaultFieldBeforeInsertLoggable();");
            sb.append(EOL);
            sb.append("throw new IllegalStateException(l.getMessageText());");
            sb.append(EOL);
            sb.append("}" + EOL);
         }

         if (this.bd.getPrimaryKeyFieldNames().contains(this.curField)) {
            sb.append("if (__WL_method_state!=STATE_EJB_CREATE) {" + EOL);
            sb.append("Loggable l = EJBLogger.logcannotCallSetOnPkLoggable();");
            sb.append("throw new IllegalStateException(l.getMessage());");
            sb.append("}" + EOL);
            sb.append(this.parse(this.getProductionRule("cmpSetMethodBody")));
         } else if (this.bean.isCmrMappedCmpField(this.curField)) {
            sb.append("Loggable l = EJBLogger.logcannotCallSetOnCmpCmrFieldLoggable();");
            sb.append("throw new EJBException(l.getMessage());" + EOL + EOL);
         } else if (this.bd.isOptimistic()) {
            boolean found = false;
            table = null;
            String optColumn = null;
            String optField = null;

            for(int i = 0; i < this.bean.tableCount(); ++i) {
               table = this.bean.tableAt(i);
               if (this.bean.getVerifyColumns(table).equalsIgnoreCase("version") || this.bean.getVerifyColumns(table).equalsIgnoreCase("timestamp")) {
                  optColumn = this.bean.getOptimisticColumn(table);
                  optField = this.bean.getCmpField(table, optColumn);
                  if (optField.equals(this.curField)) {
                     found = true;
                     break;
                  }
               }
            }

            String inputArg = this.fieldNameForField();
            if (found) {
               sb.append(this.setMethodNameForField() + "__WL_optimisticField(" + inputArg + ");" + EOL);
               sb.append("if (!__WL_createAfterRemove) {" + EOL);
               sb.append("this." + CodeGenUtils.snapshotNameForVar(this.curField) + " = ");
               sb.append(inputArg + ";" + EOL);
               sb.append("}" + EOL);
               sb.append("}" + EOL + EOL);
               sb.append("public void " + this.setMethodNameForField() + "__WL_optimisticField(" + this.fieldClassForCmpField() + " " + inputArg + ") {" + EOL);
               sb.append(this.parse(this.getProductionRule("cmpSetMethodBody")));
            } else {
               sb.append(this.parse(this.getProductionRule("cmpSetMethodBodyForOptimistic")));
            }
         } else {
            sb.append(this.parse(this.getProductionRule("cmpSetMethodBody")));
         }
      }

      return sb.toString();
   }

   public String cmpSetMethodCheck() {
      assert this.curField != null;

      String result = "";
      if (this.bd.getPrimaryKeyFieldNames().contains(this.curField)) {
         return result;
      } else {
         String inputArg = this.fieldNameForField();
         StringBuffer sb = new StringBuffer();
         Class fieldClass = this.bean.getCmpFieldClass(this.curField);
         if (ClassUtils.isPrimitiveOrImmutable(fieldClass)) {
            sb.append("this." + this.fieldNameForField());
            sb.append(" == " + inputArg + "  ");
            result = sb.toString();
            sb.setLength(0);
            if (!fieldClass.isPrimitive()) {
               sb.append("(" + result + " || (");
               sb.append("this." + this.fieldNameForField());
               sb.append("!=null && ");
               sb.append("this." + this.fieldNameForField());
               sb.append(".equals(" + inputArg + ")))  ");
               result = sb.toString();
            }

            result = "if (" + result + " && " + this.isLoadedVarForField() + ") return;" + EOL;
         }

         return result;
      }
   }

   public String trimStringTypes() {
      if (this.bean.isStringTrimmingEnabled()) {
         Class fieldClass = this.bean.getCmpFieldClass(this.curField);
         String inputArg = this.fieldNameForField();
         StringBuffer sb;
         if (fieldClass == String.class) {
            sb = new StringBuffer();
            sb.append("if(");
            sb.append(inputArg);
            sb.append("!= null) {");
            sb.append(EOL);
            sb.append(this.trimStringTypedValue(inputArg));
            sb.append("}");
            sb.append(EOL);
            return sb.toString();
         }

         if (this.bean.isCharArrayMappedToString(fieldClass)) {
            sb = new StringBuffer();
            sb.append("if(");
            sb.append(inputArg);
            sb.append("!= null) {");
            sb.append(EOL);
            sb.append("int i = ");
            sb.append(inputArg);
            sb.append(".length;\n");
            sb.append("while(i > 0 && Character.isWhitespace(");
            sb.append(inputArg);
            sb.append("[i-1])) {i--;}\n");
            sb.append("if(i<");
            sb.append(inputArg);
            sb.append(".length) {\n");
            sb.append("char[] temp = new char[i];\n");
            sb.append("for(int j=0;j<i;j++) {\n");
            sb.append("temp[j] = ");
            sb.append(inputArg);
            sb.append("[j];\n");
            sb.append("}\n");
            sb.append(inputArg);
            sb.append("= temp;\n");
            sb.append("}\n");
            sb.append("}\n");
            return sb.toString();
         }
      }

      return "";
   }

   public String trimStringTypedValue(String variableName) {
      if (!this.bean.isStringTrimmingEnabled()) {
         return "";
      } else {
         StringBuffer sb = new StringBuffer();
         sb.append("int i = ");
         sb.append(variableName);
         sb.append(".length();");
         sb.append(EOL);
         sb.append("while(i > 0 && Character.isWhitespace(");
         sb.append(variableName);
         sb.append(".charAt(i-1))) {i--;}");
         sb.append(EOL);
         sb.append("if(i<");
         sb.append(variableName);
         sb.append(".length()) {");
         sb.append(EOL);
         sb.append(variableName);
         sb.append(" = ");
         sb.append(variableName);
         sb.append(".substring(0,i);");
         sb.append(EOL);
         sb.append("}");
         sb.append(EOL);
         return sb.toString();
      }
   }

   public String getFieldGroupSuffix(FieldGroup fg) {
      return "group" + fg.getIndex();
   }

   public String getMethodSuffix(String fieldName) {
      String groupName = this.bean.getGroupNameForCmpField(fieldName);
      return this.getFieldGroupSuffix(this.bean.getFieldGroup(groupName));
   }

   public String implementGroupLoadMethods() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();

      for(Iterator fieldGroups = this.bean.getFieldGroups().iterator(); fieldGroups.hasNext(); sb.append("}" + EOL)) {
         this.curGroup = (FieldGroup)fieldGroups.next();
         sb.append(EOL);
         sb.append("// loadGroup method for the '" + this.curGroup.getName() + "' group." + EOL);
         sb.append("public void " + this.loadMethodName(this.getFieldGroupSuffix(this.curGroup)) + "() ");
         sb.append("throws Exception {" + EOL);
         if (this.groupColumnCount() > 0) {
            sb.append(this.parse(this.getProductionRule("implementGroupLoadMethodBody")));
         }
      }

      return sb.toString();
   }

   public String constructorExceptionList() {
      StringBuffer sb = new StringBuffer();
      CMPCodeGenerator.Output output = (CMPCodeGenerator.Output)this.currentOutput;
      Class beanClass = output.getCMPBeanDescriptor().getBeanClass();

      try {
         Constructor constr = beanClass.getConstructor();

         assert constr != null;

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
      } catch (NoSuchMethodException var7) {
         throw new AssertionError("Unable to find constructor on class '" + beanClass.getName() + "'.");
      }

      return sb.toString();
   }

   public String ejbLoadExceptionList() {
      return this.ejbCallbackMethodExceptionList("ejbLoad");
   }

   public String ejbRemoveExceptionList() {
      return this.ejbCallbackMethodExceptionList("ejbRemove");
   }

   public String ejbStoreExceptionList() {
      return this.ejbCallbackMethodExceptionList("ejbStore");
   }

   public String ejbCallbackMethodExceptionList(String methodName) {
      StringBuffer sb = new StringBuffer();
      CMPCodeGenerator.Output output = (CMPCodeGenerator.Output)this.currentOutput;
      Class beanClass = output.getCMPBeanDescriptor().getBeanClass();

      try {
         Class[] excepts = beanClass.getMethod(methodName).getExceptionTypes();
         if (excepts.length > 0) {
            sb.append(" throws ");

            for(int i = 0; i < excepts.length; ++i) {
               sb.append(this.javaCodeForType(excepts[i]));
               if (i < excepts.length - 1) {
                  sb.append(", ");
               }
            }
         }
      } catch (NoSuchMethodException var7) {
         throw new AssertionError("Unable to find " + methodName + " on class '" + beanClass.getName() + "'.");
      }

      return sb.toString();
   }

   private String declareBeanMethod(String methodName, Class[] params, boolean assertIfNotFound) {
      StringBuffer sb = new StringBuffer();
      Class beanClass = this.bd.getBeanClass();

      try {
         MethodSignature sig = new MethodSignature(beanClass.getMethod(methodName, params));
         sb.append(sig.toString());
      } catch (NoSuchMethodException var7) {
         if (assertIfNotFound) {
            throw new AssertionError("Unable to find '" + methodName + "' method on class '" + beanClass.getName() + "'.");
         }

         return null;
      }

      return sb.toString();
   }

   public String declareSetEntityContextMethod() {
      return this.declareBeanMethod("setEntityContext", new Class[]{EntityContext.class}, true);
   }

   public String declareEjbLoadMethod() {
      return this.declareBeanMethod("ejbLoad", new Class[0], true);
   }

   public String declareEjbStoreMethod() {
      return this.declareBeanMethod("ejbStore", new Class[0], true);
   }

   public String beanVarEjbStoreForField() {
      return this.beanVar() + ".ejbStore();";
   }

   public String declareEjbRemoveMethod() {
      return this.declareBeanMethod("ejbRemove", new Class[0], true);
   }

   public String declareEjbPassivateMethod() {
      return this.declareBeanMethod("ejbPassivate", new Class[0], true);
   }

   private boolean throwsCreateException(Method m) {
      Class[] excClasses = m.getExceptionTypes();

      for(int i = 0; i < excClasses.length; ++i) {
         if (excClasses[i].isAssignableFrom(CreateException.class)) {
            return true;
         }
      }

      return false;
   }

   public String returnPkPerhapsInsertBean() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      sb.append(EOL);
      Class pkClass = this.bd.getPrimaryKeyClass();
      sb.append(ClassUtils.classToJavaSourceType(pkClass) + " " + this.pkVar() + " = null;" + EOL);
      if (this.bean.getDelayInsertUntil().equals("ejbCreate")) {
         sb.append("if (!" + this.pmVar() + ".getOrderDatabaseOperations() || TransactionHelper.getTransactionHelper().getTransaction()==null) {" + EOL);
         sb.append(this.pkVar() + " = (" + ClassUtils.classToJavaSourceType(pkClass) + ")" + this.createMethodName() + "();" + EOL);
         sb.append("} else {");
         sb.append(EOL);
      }

      sb.append(this.pkVar() + " = (" + ClassUtils.classToJavaSourceType(pkClass) + ") __WL_getPrimaryKey();" + EOL);
      if (this.bean.getDelayInsertUntil().equals("ejbCreate")) {
         sb.append("}");
         sb.append(EOL);
      }

      sb.append("return " + this.pkVar() + ";" + EOL);
      sb.append(this.perhapsCatchCreateException());
      if (!this.bean.getDelayInsertUntil().equals("ejbCreate") && this.bean.hasAutoKeyGeneration()) {
         sb.append("}" + EOL + EOL);
      } else {
         sb.append(this.parse(this.getProductionRule("standardCatch")));
      }

      return sb.toString();
   }

   public String perhapsCatchCreateException() {
      if (this.throwsCreateException(this.method)) {
         StringBuffer sb = new StringBuffer();
         sb.append("} catch (javax.ejb.CreateException ce) {" + EOL);
         sb.append("throw ce;" + EOL);
         return sb.toString();
      } else {
         return "";
      }
   }

   public String perhapsInsertBean() {
      StringBuffer sb = new StringBuffer();
      if (this.bean.getDelayInsertUntil().equals("ejbPostCreate")) {
         sb.append("if (!" + this.pmVar() + ".getOrderDatabaseOperations() || TransactionHelper.getTransactionHelper().getTransaction()==null)" + EOL);
         sb.append(this.createMethodName() + "();" + EOL);
      }

      return sb.toString();
   }

   public String implementEjbCreateMethods() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      Method[] var2 = this.bd.getBeanClass().getMethods();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Method m = var2[var4];
         if (m.getName().startsWith("ejbCreate")) {
            this.setMethod(m, (short)0);
            sb.append(this.method_signature_no_throws());
            sb.append(this.beanmethod_throws_clause());
            sb.append("{");
            sb.append(EOL);
            if (this.bd.isReadOnly() && !this.bean.allowReadonlyCreateAndRemove()) {
               sb.append("Loggable l = EJBLogger.logCannotCreateReadOnlyBeanLoggable(\"" + this.bean.getEjbName() + "\");" + EOL);
               sb.append("throw new javax.ejb.EJBException(l.getMessageText());" + EOL);
            } else {
               sb.append(this.parse(this.getProductionRule("ejbCreateMethodBody")));
            }

            sb.append("}");
            sb.append(EOL);
         }
      }

      return sb.toString();
   }

   public String implementEjbPostCreateMethods() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      Method[] allMethods = this.bd.getBeanClass().getMethods();
      Method pcMethod = null;

      for(int i = 0; i < allMethods.length; ++i) {
         pcMethod = allMethods[i];
         if (pcMethod.getName().startsWith("ejbPostCreate")) {
            this.setMethod(pcMethod, (short)0);
            sb.append(this.method_signature_no_throws());
            sb.append(this.beanmethod_throws_clause());
            sb.append("{");
            sb.append(EOL);
            if (this.bd.isReadOnly() && !this.bean.allowReadonlyCreateAndRemove()) {
               sb.append("Loggable l = EJBLogger.logCannotCreateReadOnlyBeanLoggable(\"" + this.bean.getEjbName() + "\");" + EOL);
               sb.append("throw new javax.ejb.EJBException(l.getMessageText());" + EOL);
            } else {
               sb.append(this.parse(this.getProductionRule("ejbPostCreateMethod")));
            }

            sb.append("}");
            sb.append(EOL);
         }
      }

      return sb.toString();
   }

   public String implementEjbRemoveMethod() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      sb.append(this.declareEjbRemoveMethod());
      sb.append(" {" + EOL);
      if (this.bd.isReadOnly() && !this.bean.allowReadonlyCreateAndRemove()) {
         sb.append("Loggable l = EJBLogger.logCannotRemoveReadOnlyBeanLoggable(\"" + this.bean.getEjbName() + "\");" + EOL);
         sb.append("throw new javax.ejb.EJBException(l.getMessageText());" + EOL);
      } else {
         sb.append(this.parse(this.getProductionRule("implementEjbRemoveMethodBody")));
      }

      sb.append("}");
      return sb.toString();
   }

   public String implementSetNullMethods() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();

      for(Iterator allFields = this.bean.getCmrFieldNames().iterator(); allFields.hasNext(); this.curField = null) {
         this.curField = (String)allFields.next();
         if (this.bean.isOneToOneRelation(this.curField) && !this.bean.isRemoteField(this.curField)) {
            sb.append("public void " + this.setNullMethodName(this.curField) + "(boolean ejbStore) {" + EOL);
            if (this.bean.isForeignKeyField(this.curField)) {
               sb.append(this.parse(this.getProductionRule("oneToOneSetNullBody_fkOwner")));
            } else {
               sb.append(this.parse(this.getProductionRule("oneToOneSetNullBody")));
            }

            sb.append("}" + EOL + EOL);
         }
      }

      return sb.toString();
   }

   public String implementMakeCascadeDelListMethod() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      if (this.bean.isCascadeDelete()) {
         sb.append(this.parse(this.getProductionRule("implementMakeCascadeDelListMethodBody")));
      } else {
         sb.append(this.parse(this.getProductionRule("implementMakeCascadeDelListMethodBody_AddThisBean")));
      }

      return sb.toString();
   }

   public String get11_RelBeans_RootBeanFKOwner() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      Iterator allFields = this.bean.getCmrFieldNames().iterator();

      while(allFields.hasNext()) {
         String fieldName = (String)allFields.next();
         this.curField = fieldName;
         if (this.bean.isCascadeDelete(fieldName) && this.bean.isOneToOneRelation(fieldName) && !this.bean.isRemoteField(fieldName)) {
            RDBMSBean rbean = this.bean.getRelatedRDBMSBean(fieldName);
            String rfield = this.bean.getRelatedFieldName(fieldName);
            if (!this.bean.relatedFieldIsFkOwner(fieldName) && rbean.relatedFieldIsFkOwner(rfield)) {
               sb.append(EOL);
               sb.append(this.parse(this.getProductionRule("oneToOneCascadeDel")));
            }
         }
      }

      return sb.toString();
   }

   public String get1N11_RelBeans_RootBeanNotFKOwner() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      Iterator allFields = this.bean.getCmrFieldNames().iterator();

      while(allFields.hasNext()) {
         String fieldName = (String)allFields.next();
         this.curField = fieldName;
         if (this.bean.isCascadeDelete(fieldName)) {
            RDBMSBean rbean;
            String rfield;
            if (this.bean.isOneToOneRelation(fieldName)) {
               if (!this.bean.isRemoteField(fieldName)) {
                  rbean = this.bean.getRelatedRDBMSBean(fieldName);
                  rfield = this.bean.getRelatedFieldName(fieldName);
                  if (this.bean.relatedFieldIsFkOwner(fieldName) && !rbean.relatedFieldIsFkOwner(rfield)) {
                     sb.append(EOL);
                     sb.append(this.parse(this.getProductionRule("oneToOneCascadeDel")));
                  }
               }
            } else if (this.bean.isOneToManyRelation(fieldName) && !this.bean.isRemoteField(fieldName) && this.bean.getRelatedMultiplicity(fieldName).equals("Many")) {
               rbean = this.bean.getRelatedRDBMSBean(fieldName);
               rfield = this.bean.getRelatedFieldName(fieldName);
               if (this.bean.relatedFieldIsFkOwner(fieldName) && !rbean.relatedFieldIsFkOwner(rfield)) {
                  sb.append(EOL);
                  sb.append(this.parse(this.getProductionRule("oneToManyCascadeDel")));
               }
            }
         }
      }

      return sb.toString();
   }

   public String get11_RelBeans_EachOtherFKOwner() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      Iterator allFields = this.bean.getCmrFieldNames().iterator();

      while(allFields.hasNext()) {
         String fieldName = (String)allFields.next();
         this.curField = fieldName;
         if (this.bean.isCascadeDelete(fieldName) && this.bean.isOneToOneRelation(fieldName) && !this.bean.isRemoteField(fieldName)) {
            RDBMSBean rbean = this.bean.getRelatedRDBMSBean(fieldName);
            String rfield = this.bean.getRelatedFieldName(fieldName);
            if (this.bean.relatedFieldIsFkOwner(fieldName) && rbean.relatedFieldIsFkOwner(rfield)) {
               sb.append(EOL);
               sb.append(this.parse(this.getProductionRule("oneToOneCascadeDel")));
            }
         }
      }

      return sb.toString();
   }

   public boolean isPrimaryKeyCMRField(String fieldName) {
      if (this.bean.getForeignKeyColNames(fieldName) == null) {
         return true;
      } else {
         Iterator fkColumns = this.bean.getForeignKeyColNames(fieldName).iterator();

         int i;
         for(i = 0; fkColumns.hasNext(); ++i) {
            String column = (String)fkColumns.next();
            String field = this.bean.getCmpFieldForColumn(column);
            if (field == null) {
               return false;
            }

            if (!this.bean.isPrimaryKeyField(field)) {
               break;
            }
         }

         return i == this.bean.getForeignKeyColNames(fieldName).size();
      }
   }

   public String perhapsLoadDefaultGroup() {
      StringBuffer sb = new StringBuffer();
      boolean hasLockOrder = false;
      Iterator allFields = this.bean.getCmrFieldNames().iterator();
      boolean needLoad = false;

      while(allFields.hasNext()) {
         String fieldName = (String)allFields.next();
         if (this.bean.isCascadeDelete(fieldName)) {
            if (!this.bean.isManyToManyRelation(fieldName) && this.isPrimaryKeyCMRField(fieldName) && this.bean.getRelationshipCaching(fieldName) != null) {
               needLoad = true;
            }

            RDBMSBean rbean = this.bean.getRelatedRDBMSBean(fieldName);
            if (this.bean.getLockOrder() < rbean.getLockOrder()) {
               FieldGroup group = this.bean.getFieldGroup("defaultGroup");
               sb.append(this.loadMethodName(this.getFieldGroupSuffix(group)) + "();" + EOL);
               hasLockOrder = true;
               break;
            }
         }
      }

      if (!hasLockOrder && needLoad) {
         sb.append(this.loadByRelationFinder());
      }

      return sb.toString();
   }

   public String listRelBeansVar() {
      StringBuffer sb = new StringBuffer();
      if (this.bean.isDBCascadeDelete(this.curField)) {
         sb.append("listRelBeans_WithoutDBUpdate");
      } else {
         sb.append("listRelBeans");
      }

      return sb.toString();
   }

   public String addBeanToRelationships() {
      StringBuffer sb = new StringBuffer();
      Iterator allFields = this.bean.getCmrFieldNames().iterator();
      if (allFields.hasNext()) {
         sb.append(EOL);
      }

      while(allFields.hasNext()) {
         String fieldName = (String)allFields.next();
         if (!this.bean.isRemoteField(fieldName) && this.bean.isForeignPrimaryKeyField(fieldName)) {
            sb.append(this.postSetMethodName(fieldName) + "();" + EOL);
         }
      }

      return sb.toString();
   }

   public String loadByRelationFinder() {
      StringBuffer sb = new StringBuffer();
      Iterator cmrs = this.bean.getCmrFieldNames().iterator();
      String field = null;
      sb.append("//load related bean in join sql" + EOL);
      sb.append("boolean executed = false;" + EOL);
      sb.append("if((__WL_isModified() || !__WL_beanIsLoaded())){" + EOL);
      sb.append("Transaction currentTx = TransactionHelper.getTransactionHelper().getTransaction();" + EOL);

      while(true) {
         do {
            do {
               do {
                  do {
                     if (!cmrs.hasNext()) {
                        sb.append("}" + EOL);
                        return sb.toString();
                     }

                     field = (String)cmrs.next();
                  } while(!this.bean.isCascadeDelete(field));
               } while(this.bean.isManyToManyRelation(field));
            } while(!this.isPrimaryKeyCMRField(field));
         } while(this.bean.getRelationshipCaching(field) == null);

         EjbqlFinder qfinder = (EjbqlFinder)this.bean.getRelatedFinder(field);
         if (this.bean.isManyToManyRelation(field)) {
            sb.append("if(!executed||" + CodeGenUtils.fieldVarName(field) + " == null ||(" + CodeGenUtils.fieldVarName(field) + "!=null && currentTx!=null && !(((RDBMSSet)" + CodeGenUtils.fieldVarName(field) + ").checkIfCurrentTxEqualsCreateTx(currentTx)))){" + EOL);
         } else {
            sb.append("if (!executed");
            Iterator iterator = this.bean.getPrimaryKeyFields().iterator();

            while(iterator.hasNext()) {
               String fieldName = (String)iterator.next();
               sb.append("||!" + this.isLoadedVar(fieldName));
            }

            sb.append(") {" + EOL);
         }

         sb.append(MethodUtils.convertToFinderName(qfinder.getName()) + "((" + this.bean.getCMPBeanDescriptor().getPrimaryKeyClass().getName() + ")" + this.ctxVar() + ".getPrimaryKey());" + EOL);
         sb.append("executed=true;" + EOL);
         sb.append("}" + EOL);
      }
   }

   public String removeBeanFromRelationships() {
      StringBuffer sb = new StringBuffer();
      Iterator allFields = this.bean.getCmrFieldNames().iterator();
      if (allFields.hasNext()) {
         sb.append(EOL);
      }

      while(true) {
         String fieldName;
         RDBMSBean rbean;
         String rfield;
         String ejbStore;
         label62:
         do {
            while(allFields.hasNext()) {
               fieldName = (String)allFields.next();
               this.curField = fieldName;
               if (this.bean.isOneToOneRelation(fieldName)) {
                  continue label62;
               }

               if (!this.bean.isOneToManyRelation(fieldName)) {
                  sb.append(MethodUtils.getMethodName(fieldName) + "().clear();" + EOL);
               } else if (!this.bean.isRemoteField(fieldName)) {
                  if (!this.bean.getRelatedMultiplicity(fieldName).equals("Many")) {
                     sb.append(this.varPrefix() + MethodUtils.setMethodName(fieldName) + "(null, false);" + EOL);
                  } else {
                     rbean = this.bean.getRelatedRDBMSBean(fieldName);
                     rfield = this.bean.getRelatedFieldName(fieldName);
                     ejbStore = this.bean.relatedFieldIsFkOwner(fieldName) && !rbean.isForeignPrimaryKeyField(rfield) && !this.bean.isCascadeDelete(fieldName) ? "true" : "false";
                     sb.append(MethodUtils.getMethodName(fieldName) + "();" + EOL);
                     if (this.bean.isSelfRelationship(fieldName)) {
                        sb.append("try {" + EOL);
                        sb.append("if (" + this.fieldRemovedVarForField() + " == null)" + EOL);
                        sb.append(this.fieldRemovedVarForField() + " = (Set)((" + this.collectionClassForField() + ")" + this.fieldVarForField() + ").clone();" + EOL);
                        sb.append("else" + EOL);
                        sb.append(this.fieldRemovedVarForField() + ".addAll((Set)((" + this.collectionClassForField() + ")" + this.fieldVarForField() + ").clone());" + EOL);
                        sb.append("} catch (CloneNotSupportedException e) {" + EOL);
                        sb.append("// clone() failed, do nothing" + EOL);
                        sb.append("}" + EOL);
                     }

                     sb.append("((" + ClassUtils.setClassName(this.bd, fieldName) + ")");
                     sb.append(CodeGenUtils.fieldVarName(fieldName));
                     sb.append(").clear(" + ejbStore + ");" + EOL);
                  }
               } else {
                  sb.append(MethodUtils.setMethodName(fieldName) + "(null);" + EOL);
               }
            }

            return sb.toString();
         } while(this.bean.isRemoteField(fieldName));

         rbean = this.bean.getRelatedRDBMSBean(fieldName);
         rfield = this.bean.getRelatedFieldName(fieldName);
         ejbStore = this.bean.relatedFieldIsFkOwner(fieldName) && !rbean.isForeignPrimaryKeyField(rfield) && !this.bean.isCascadeDelete(fieldName) ? "true" : "false";
         if (this.bean.isSelfRelationship(fieldName)) {
            sb.append(this.fieldRemovedVarForField() + " = " + this.fieldVarForField() + ";" + EOL);
         }

         sb.append(this.setNullMethodName(fieldName) + "(" + ejbStore + ");" + EOL);
         if (!this.bean.isSelfRelationship(fieldName)) {
            sb.append(this.doSetMethodName(fieldName) + "(null);" + EOL);
         }
      }
   }

   public String implementEjbStoreMethod() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      sb.append(EOL);
      sb.append(this.declareEjbStoreMethod());
      sb.append(" { " + EOL);
      sb.append(this.parse(this.getProductionRule("implementEjbStoreMethodBody")));
      sb.append("}");
      return sb.toString();
   }

   public String declareCmrGettersAndSetters() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      Iterator all = this.bean.getCmrFieldNames().iterator();

      while(all.hasNext()) {
         this.curField = (String)all.next();
         sb.append(this.generateCmrGetterMethod());
         if (this.bean.isOneToOneRelation(this.curField)) {
            sb.append(this.oneToOneSetMethod());
            sb.append(this.oneToManyAddMethod());
         } else if (this.bean.isOneToManyRelation(this.curField)) {
            sb.append(this.oneToManySetMethod());
            sb.append(this.oneToManyAddMethod());
         } else {
            if (!this.bean.isManyToManyRelation(this.curField)) {
               throw new AssertionError("Invalid  multiplicity for relation.");
            }

            sb.append(this.manyToManySetMethod());
         }
      }

      return sb.toString();
   }

   public String implementCmrFieldPutInQueryCacheMethod() {
      StringBuffer sb = new StringBuffer();

      try {
         Iterator iterator = this.bean.getCmrFieldNames().iterator();
         boolean elseNeeded = false;

         while(true) {
            while(iterator.hasNext()) {
               String cmrField = (String)iterator.next();
               if (this.bean.getCmrFieldNames().size() > 1) {
                  if (!elseNeeded) {
                     sb.append("if (").append(this.cmrFieldNameVar()).append(".equals(\"");
                     elseNeeded = true;
                  } else {
                     sb.append("} else if (").append(this.cmrFieldNameVar()).append(".equals(\"");
                  }

                  sb.append(cmrField).append("\")) {").append(EOL);
               }

               RDBMSBean relatedBean = this.bean.getRelatedRDBMSBean(cmrField);
               String relatedField = this.bean.getRelatedFieldName(cmrField);
               CMPBeanDescriptor rbd = this.bean.getRelatedDescriptor(cmrField);
               if (this.bean.isOneToOneRelation(cmrField) && rbd.hasLocalClientView() && !relatedBean.relatedFieldIsFkOwner(relatedField)) {
                  String fieldVarName = CodeGenUtils.fieldVarName(cmrField);
                  String relFinderName = this.generateCMRFieldFinderMethodName(cmrField);
                  sb.append("TTLManager roMgr = (TTLManager)");
                  sb.append(this.bmVar(cmrField)).append(";").append(EOL);
                  sb.append("QueryCacheKey qckey = new QueryCacheKey(\"");
                  sb.append(relFinderName).append("\", new Object[]{");
                  sb.append("__WL_getPrimaryKey()").append("}, roMgr, ");
                  sb.append("QueryCacheKey.RET_TYPE_SINGLETON);").append(EOL);
                  sb.append("if (").append(this.sourceQueryCacheKeyVar());
                  sb.append(" != null) {").append(EOL);
                  sb.append(this.sourceQueryCacheKeyVar()).append(".addDestinationQuery(");
                  sb.append("qckey);").append(EOL);
                  sb.append("qckey.addSourceQuery(").append(this.sourceQueryCacheKeyVar());
                  sb.append(");").append(EOL);
                  sb.append("}").append(EOL);
                  sb.append("QueryCacheElement qce = new QueryCacheElement(");
                  sb.append(fieldVarName);
                  sb.append(".getPrimaryKey(), roMgr);").append(EOL);
                  sb.append("roMgr.putInQueryCache(qckey, qce);").append(EOL);
               } else if (this.bean.isOneToManyRelation(cmrField) && !relatedBean.relatedFieldIsFkOwner(relatedField) || this.bean.isManyToManyRelation(cmrField)) {
                  sb.append("((RDBMSSet)").append(CodeGenUtils.fieldVarName(cmrField));
                  sb.append(").putInQueryCache(").append(this.sourceQueryCacheKeyVar());
                  sb.append(");").append(EOL);
               }
            }

            if (this.bean.getCmrFieldNames().size() > 1) {
               sb.append("} else {").append(EOL);
               sb.append("throw new AssertionError(\"Unknown CMR field: \"");
               sb.append("+").append(this.cmrFieldNameVar()).append(");").append(EOL);
               sb.append("}").append(EOL);
            }
            break;
         }
      } catch (NullPointerException var10) {
         throw var10;
      }

      return sb.toString();
   }

   public String cmrFieldQueryCachingMethodName() {
      return this.varPrefix() + "putCmrFieldInQueryCache";
   }

   public String cmrFieldNameVar() {
      return this.varPrefix() + "cmrFieldName";
   }

   public String sourceQueryCacheKeyVar() {
      return this.varPrefix() + "sourceQCKey";
   }

   public String loadCheckForCmrField() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      if (this.bean.isForeignCmpField(this.curField)) {
         Set groupNames = new HashSet();
         Iterator fkColumns = this.bean.getForeignKeyColNames(this.curField).iterator();

         String fkColumn;
         while(fkColumns.hasNext()) {
            String fkColumn = (String)fkColumns.next();
            fkColumn = this.bean.getCmpFieldForColumn(fkColumn);
            groupNames.add(this.getMethodSuffix(fkColumn));
         }

         boolean needMultipleChecks = groupNames.size() > 1;
         fkColumns = this.bean.getForeignKeyColNames(this.curField).iterator();

         while(fkColumns.hasNext()) {
            fkColumn = (String)fkColumns.next();
            String cmpField = this.bean.getCmpFieldForColumn(fkColumn);
            sb.append("if (!" + this.isLoadedVar(cmpField) + ") ");
            sb.append(this.callLoadMethod(cmpField) + EOL);
            if (!needMultipleChecks) {
               break;
            }
         }
      } else {
         sb.append(this.parse(this.getProductionRule("simpleLoadCheckForField")));
      }

      return sb.toString();
   }

   public String declareCmrVariableGetters() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();

      try {
         Iterator fkFields = this.bean.getForeignKeyFieldNames().iterator();

         while(true) {
            String fkField;
            do {
               do {
                  if (!fkFields.hasNext()) {
                     return sb.toString();
                  }

                  fkField = (String)fkFields.next();
               } while(!this.bean.isOneToManyRelation(fkField));
            } while(this.bean.isRemoteField(fkField));

            this.curField = fkField;
            String fkTable = this.bean.getTableForCmrField(fkField);
            Iterator fkColumns = this.bean.getForeignKeyColNames(fkField).iterator();

            while(fkColumns.hasNext()) {
               String fkColumn = (String)fkColumns.next();
               if (!this.bean.hasCmpField(fkTable, fkColumn)) {
                  String fkVariable = this.bean.variableForField(fkField, fkTable, fkColumn);
                  Class fkClass = this.bean.getForeignKeyColClass(fkField, fkColumn);
                  String className = ClassUtils.classToJavaSourceType(fkClass);
                  sb.append("public ");
                  sb.append(className + " ");
                  sb.append(MethodUtils.getMethodName(fkVariable));
                  sb.append("() {" + EOL);
                  sb.append("try {" + EOL);
                  sb.append(this.loadCheckForCmrField());
                  sb.append(this.parse(this.getProductionRule("standardCatch")));
                  sb.append("return " + fkVariable + ";" + EOL);
                  sb.append("}" + EOL + EOL);
               }
            }

            this.curField = null;
         }
      } catch (CodeGenerationException var10) {
         throw var10;
      } catch (Exception var11) {
         throw new CodeGenerationException("Error in RDBMSCodeGenerator.generateCmrVariableGetterMethods: " + StackTraceUtilsClient.throwable2StackTrace(var11));
      }
   }

   private String generateCmrGetterMethod() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      String className = ClassUtils.classToJavaSourceType(this.bean.getCmrFieldClass(this.curField));
      boolean shouldImplementQueryCachingForCMRField = this.bean.isQueryCachingEnabledForCMRField(this.curField);
      sb.append("public ");
      sb.append(className + " ");
      sb.append(this.getMethodNameForField());
      sb.append("() {");
      sb.append(EOL);

      try {
         if (this.bean.isOneToOneRelation(this.curField)) {
            if (this.bean.isForeignKeyField(this.curField)) {
               sb.append(this.parse(this.getProductionRule("oneToOneGetterBody_fkOwner")));
            } else {
               if (shouldImplementQueryCachingForCMRField) {
                  sb.append(this.fieldVarForField()).append(" = ");
                  sb.append(this.bmVarForField()).append(".getFromQueryCache(");
                  sb.append(this.finderVarForField()).append(".getName(), new Object[]{");
                  sb.append(this.ctxVar()).append(".getPrimaryKey()});").append(EOL);
                  sb.append("if (").append(this.fieldVarForField()).append(" != null) ");
                  sb.append("return ").append(this.fieldVarForField());
                  sb.append(";").append(EOL);
               }

               sb.append(this.parse(this.getProductionRule("oneToOneGetterBody")));
               if (shouldImplementQueryCachingForCMRField) {
                  sb.append("QueryCacheKey qckey = new QueryCacheKey(");
                  sb.append(this.finderVarForField()).append(".getName(), ");
                  sb.append("new Object[] {").append(this.ctxVar());
                  sb.append(".getPrimaryKey()}, ").append("(TTLManager)");
                  sb.append(this.bmVarForField());
                  sb.append(", QueryCacheKey.RET_TYPE_SINGLETON);").append(EOL);
                  sb.append("QueryCacheElement qcelem = new QueryCacheElement(");
                  sb.append(this.fieldVarGetPrimaryKey()).append(", ");
                  sb.append(this.bmVarForField()).append(");").append(EOL);
                  sb.append(this.bmVarForField()).append(".putInQueryCache(qckey, ");
                  sb.append("qcelem);").append(EOL);
               }
            }
         } else if (this.bean.isOneToManyRelation(this.curField)) {
            if (this.bean.getRelatedMultiplicity(this.curField).equals("One")) {
               sb.append(this.parse(this.getProductionRule("oneToManyGetterBody_fkOwner")));
            } else {
               sb.append(this.parse(this.getProductionRule("oneToManyGetterBody")));
               if (!this.bean.isRemoteField(this.curField)) {
                  this.generateOneToManyCollection();
               }
            }
         } else {
            if (!this.bean.isManyToManyRelation(this.curField)) {
               throw new AssertionError("Invalid  multiplicity for relation.");
            }

            sb.append(this.parse(this.getProductionRule("ManyToManyGetterBody")));
            if (!this.bean.isRemoteField(this.curField)) {
               this.generateManyToManyCollection();
            }
         }
      } catch (Exception var5) {
         throw new CodeGenerationException("Error in RDBMSCodeGenerator.generateCmrGetterMethod: " + StackTraceUtilsClient.throwable2StackTrace(var5));
      }

      sb.append("}");
      sb.append(EOL);
      return sb.toString();
   }

   public String allocateOneToManySet() {
      StringBuffer sb = new StringBuffer();
      if (!this.bean.isRemoteField(this.curField)) {
         sb.append("new " + this.collectionClassForField() + "(this, " + this.bmVarForField() + ", " + this.finderVarForField() + ")" + EOL);
      } else {
         sb.append("new " + this.collectionClassForField() + "(this, " + this.homeVarForField() + ", " + this.pmVar() + ")" + EOL);
      }

      return sb.toString();
   }

   private void generateOneToManyCollection() throws CodeGenerationException {
      try {
         OneToManyGenerator generator = new OneToManyGenerator(this.options);
         generator.setRootDirectoryName(this.getRootDirectoryName());
         generator.setTargetDirectory(this.getRootDirectoryName());
         generator.setRDBMSBean(this.bean);
         generator.setCMPBeanDescriptor(this.bd);
         generator.setCmrFieldName(this.curField);
         List list = generator.generate(new ArrayList());
         this.getGeneratedOutputs().addAll(generator.getGeneratedOutputs());
         this.currentOutput.addExtraOutputFiles(list);
      } catch (Exception var3) {
         Loggable l = EJBLogger.logErrorWhileGeneratingLoggable("One to Many Collection");
         EJBLogger.logStackTraceAndMessage(l.getMessageText(), var3);
         throw new CodeGenerationException(l.getMessageText(), var3);
      }
   }

   public String allocateManyToManySet() {
      StringBuffer sb = new StringBuffer();
      if (!this.bean.isRemoteField(this.curField)) {
         sb.append("new " + this.collectionClassForField() + "(this, " + this.bmVarForField() + ", " + this.finderVarForField() + ", " + this.pmVar() + ")" + EOL);
      } else {
         sb.append("new " + this.collectionClassForField() + "(this, " + this.homeVarForField() + ", " + this.pmVar() + ")" + EOL);
      }

      return sb.toString();
   }

   private void generateManyToManyCollection() throws CodeGenerationException {
      if (debugLogger.isDebugEnabled()) {
         debug("called generateManyToManyCollection");
      }

      try {
         ManyToManyGenerator generator = new ManyToManyGenerator(this.options);
         generator.setRootDirectoryName(this.getRootDirectoryName());
         generator.setTargetDirectory(this.getRootDirectoryName());
         generator.setRDBMSBean(this.bean);
         generator.setCMPBeanDescriptor(this.bd);
         generator.setCmrFieldName(this.curField);
         List list = generator.generate(new ArrayList());
         this.getGeneratedOutputs().addAll(generator.getGeneratedOutputs());
         this.currentOutput.addExtraOutputFiles(list);
      } catch (Exception var3) {
         Loggable l = EJBLogger.logErrorWhileGeneratingLoggable("One to Many Collection");
         EJBLogger.logStackTraceAndMessage(l.getMessageText(), var3);
         throw new CodeGenerationException(l.getMessageText(), var3);
      }
   }

   public String perhapsGetM2NSQL() throws CodeGenerationException {
      if (!this.bean.getOrderDatabaseOperations()) {
         return "return \"\";";
      } else {
         Iterator it = this.bean.getDeclaredFieldNames().iterator();
         if (!it.hasNext()) {
            return "return \"\";";
         } else {
            StringBuffer sb = new StringBuffer();

            while(it.hasNext()) {
               String cmrf = (String)it.next();
               if (this.bean.isManyToManyRelation(cmrf)) {
                  this.curField = cmrf;
                  sb.append(this.parse(this.getProductionRule("manyToManyGetSQL")));
               }
            }

            sb.append("throw new AssertionError(\" in __WL_getM2NSQL: unknown Many To Many cmr-field \"+cmrf+\".\");");
            sb.append(EOL);
            return sb.toString();
         }
      }
   }

   public String perhapsGetCmrBeansForCmrField() throws CodeGenerationException {
      if (!this.bean.getOrderDatabaseOperations()) {
         return "return null;";
      } else {
         Iterator it = this.bean.getDeclaredFieldNames().iterator();
         StringBuffer sb = null;
         if (!it.hasNext()) {
            return "return null;";
         } else {
            sb = new StringBuffer();

            while(it.hasNext()) {
               String cmrf = (String)it.next();
               if (this.bean.isManyToManyRelation(cmrf)) {
                  this.curField = cmrf;
                  sb.append(this.parse(this.getProductionRule("getCmrBeansForCmrField")));
               }
            }

            sb.append("throw new AssertionError(\" in __WL_getCmrBeansForCmrField: unknown Many To Many cmr-field \"+cmrf+\".\");");
            sb.append(EOL);
            return sb.toString();
         }
      }
   }

   public String perhapsManyToManySetSymmetricBeanInsertParams() throws CodeGenerationException {
      if (!this.bean.getOrderDatabaseOperations()) {
         return "";
      } else {
         return !this.bean.isSymmetricField(this.curField) ? "" : this.parse(this.getProductionRule("manyToManySetSymmetricBeanInsertParams"));
      }
   }

   public String doCheckExistsOnMethod() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      this.curGroup = this.bean.getFieldGroup("defaultGroup");
      sb.append(this.parse(this.getProductionRule("checkExistsOnMethodBody")));
      this.curGroup = null;
      return sb.toString();
   }

   public String perhapsDoCheckExistsOnMethod() {
      return this.bean.getCheckExistsOnMethod() ? "__WL_doCheckExistsOnMethod();" : "";
   }

   public String perhapsCheckRelatedExistsOneMany() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      RDBMSBean refBean = this.bean.getRelatedRDBMSBean(this.curField);
      if (refBean.getCheckExistsOnMethod()) {
         sb.append(this.parse(this.getProductionRule("checkRelatedExistsOneMany")));
      }

      return sb.toString();
   }

   public String perhapsCheckExistsOneOne() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      if (this.bean.getCheckExistsOnMethod()) {
         sb.append(this.parse(this.getProductionRule("checkExistsOneOne")));
      }

      return sb.toString();
   }

   public String bmVarForField() {
      return this.bmVar(this.curField);
   }

   public String finderInvokerForField() {
      return this.bean.isRemoteField(this.curField) ? this.homeVarForField() : this.bmVarForField();
   }

   public String finderVarForField() {
      return this.finderVarName(this.curField);
   }

   public String finderParamForField() {
      return this.bean.isRemoteField(this.curField) ? "" : this.finderVarForField() + ", ";
   }

   public String fieldClassForCmpField() {
      return ClassUtils.classToJavaSourceType(this.bean.getCmpFieldClass(this.curField));
   }

   public String cmpColumnForCmpField() {
      return this.bean.getCmpColumnForField(this.curField);
   }

   public String collectionClassForField() {
      return ClassUtils.setClassName(this.bd, this.curField);
   }

   public String isLoadedVar(String field) {
      return this.isLoadedVar() + "[" + this.bean.getIsModifiedIndex(field) + "]";
   }

   public String isLoadedVarForField() {
      return this.isLoadedVar(this.curField);
   }

   public String isModifiedVar(String field) {
      return this.isModifiedVar() + "[" + this.bean.getIsModifiedIndex(field) + "]";
   }

   public String isModifiedVarForField() {
      return this.isModifiedVar(this.curField);
   }

   public String loadMethodNameForGroup() {
      return this.loadMethodName(this.getFieldGroupSuffix(this.curGroup));
   }

   public String loadMethodName(String groupSuffixName) {
      return this.varPrefix() + "load" + this.capitalize(groupSuffixName);
   }

   public String callLoadMethod(String field) {
      String groupSuffixName = this.getMethodSuffix(field);
      return this.loadMethodName(groupSuffixName) + "();";
   }

   public String callLoadMethodForField() {
      return this.callLoadMethod(this.curField);
   }

   public String fieldVarForField() {
      return CodeGenUtils.fieldVarName(this.curField);
   }

   public String fieldRemovedVarForField() {
      return CodeGenUtils.fieldRemovedVarName(this.curField);
   }

   public String classNameForField() {
      return this.bean.getCmrFieldClass(this.curField).getName();
   }

   public String fkVarForFieldIsNull() {
      StringBuffer sb = new StringBuffer();
      String fkTable = this.bean.getTableForCmrField(this.curField);
      Iterator colNames = this.bean.getForeignKeyColNames(this.curField).iterator();
      boolean needsOr = false;
      sb.append("(");

      while(colNames.hasNext()) {
         String colName = (String)colNames.next();
         String varName = this.bean.variableForField(this.curField, fkTable, colName);
         Class varClass = this.bean.getForeignKeyColClass(this.curField, colName);
         if (!varClass.isPrimitive()) {
            if (needsOr) {
               sb.append(" || ");
            } else {
               needsOr = true;
            }

            sb.append(varName + "==null");
         }
      }

      if (!needsOr) {
         sb.append("false");
      }

      sb.append(")");
      return sb.toString();
   }

   public String fkVarForField() {
      if (this.bean.getRelatedDescriptor(this.curField).hasComplexPrimaryKey()) {
         return this.fkVar();
      } else {
         String fkTable = this.bean.getTableForCmrField(this.curField);
         String colName = (String)this.bean.getForeignKeyColNames(this.curField).iterator().next();
         Class varClass = this.bean.getForeignKeyColClass(this.curField, colName);
         return this.perhapsConvertPrimitive(varClass, "this." + this.bean.variableForField(this.curField, fkTable, colName));
      }
   }

   private String capitalize(String str) {
      return str.substring(0, 1).toUpperCase(Locale.ENGLISH) + str.substring(1);
   }

   public String declareFkVarForField() {
      String className = this.bean.getRelatedDescriptor(this.curField).getPrimaryKeyClass().getName();
      return className + " " + this.fkVar() + ";" + EOL;
   }

   public String perhapsDeclareFkVar() {
      return !this.bean.isRemoteField(this.curField) && this.bean.getRelatedDescriptor(this.curField).hasComplexPrimaryKey() ? this.declareFkVarForField() : "";
   }

   public String allocateFkVarForField() {
      String className = this.bean.getRelatedDescriptor(this.curField).getPrimaryKeyClass().getName();
      return this.fkVar() + " = new " + className + "();" + EOL;
   }

   public String perhapsAllocateFkVar() {
      return !this.bean.isRemoteField(this.curField) && this.bean.getRelatedDescriptor(this.curField).hasComplexPrimaryKey() ? this.allocateFkVarForField() : "";
   }

   public String assignFkVarForField() {
      StringBuffer sb = new StringBuffer();
      Iterator colNames = this.bean.getForeignKeyColNames(this.curField).iterator();
      String fkTable = this.bean.getTableForCmrField(this.curField);

      while(colNames.hasNext()) {
         String colName = (String)colNames.next();
         String mappedField = this.bean.getRelatedPkFieldName(this.curField, colName);
         sb.append(this.fkVar()).append(".").append(mappedField).append(" = ");
         RDBMSBean refBean = this.bean.getRelatedRDBMSBean(this.curField);
         CMPBeanDescriptor refBd = refBean.getCMPBeanDescriptor();
         Class keyFieldClass = refBd.getFieldClass(mappedField);
         String varName = this.bean.variableForField(this.curField, fkTable, colName);
         Class varClass = this.bean.getForeignKeyColClass(this.curField, colName);
         String leftSide = this.perhapsConvert(keyFieldClass, varClass, varName);
         sb.append("this").append(".").append(leftSide).append(";");
         sb.append(EOL);
      }

      return sb.toString();
   }

   public String perhapsAssignFkVar() {
      return !this.bean.isRemoteField(this.curField) && this.bean.getRelatedDescriptor(this.curField).hasComplexPrimaryKey() ? this.assignFkVarForField() : "";
   }

   public String declarePkVar() {
      return this.pk_class() + " " + this.pkVar() + " = null;";
   }

   public String allocatePkVar() {
      return this.bd.hasComplexPrimaryKey() ? this.pkVar() + " = new " + this.pk_class() + "();" : "";
   }

   public String perhapsDeclarePkVar() {
      CMPCodeGenerator.Output output = (CMPCodeGenerator.Output)this.currentOutput;
      return output.getCMPBeanDescriptor().hasComplexPrimaryKey() ? this.declarePkVar() : "";
   }

   public String perhapsCopyKeyValuesToPkVar() {
      CMPCodeGenerator.Output output = (CMPCodeGenerator.Output)this.currentOutput;
      return output.getCMPBeanDescriptor().hasComplexPrimaryKey() ? this.copyKeyValuesToPkVar() : "";
   }

   public String pkVarForField() {
      return this.bd.hasComplexPrimaryKey() ? this.pkVar() : (String)this.pkFieldNames.iterator().next();
   }

   public String implementGetPrimaryKey() {
      StringBuffer sb = new StringBuffer();
      sb.append(this.declarePkVar() + EOL);
      sb.append(this.allocatePkVar() + EOL);
      sb.append(this.assignPkFieldsToPkVar() + EOL);
      sb.append("return " + this.pkVar() + ";" + EOL);
      return sb.toString();
   }

   public String implementSetPrimaryKey() {
      return this.assignPkVarToPkFields() + EOL;
   }

   private String oneToOneSetMethod() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      String fieldClassName = ClassUtils.classToJavaSourceType(this.bean.getCmrFieldClass(this.curField));
      sb.append("public void ");
      sb.append(MethodUtils.setMethodName(this.curField));
      sb.append("(");
      sb.append(fieldClassName);
      sb.append(" ");
      sb.append(this.curField);
      sb.append(") {");
      sb.append(EOL);
      if (this.bd.isReadOnly() && !this.bean.allowReadonlyCreateAndRemove()) {
         sb.append("Loggable l = EJBLogger.logCannotCallSetForReadOnlyBeanLoggable(\"" + this.bean.getEjbName() + "\");" + EOL);
         sb.append("throw new javax.ejb.EJBException(l.getMessageText());" + EOL);
      } else if (this.bean.isForeignKeyField(this.curField)) {
         sb.append(this.oneToOneSetBody_fkOwner());
      } else {
         sb.append(this.oneToOneSetBody());
      }

      sb.append("}");
      sb.append(EOL);
      sb.append(EOL);
      sb.append("public void ");
      sb.append(this.doSetMethodName(this.curField));
      sb.append("(");
      sb.append(fieldClassName);
      sb.append(" ");
      sb.append(this.curField);
      sb.append(") {");
      sb.append(EOL);
      if (!this.bd.isReadOnly() || this.bean.allowReadonlyCreateAndRemove()) {
         if (this.bean.isForeignKeyField(this.curField)) {
            sb.append(this.parse(this.getProductionRule("oneToOneDoSetBody_fkOwner")));
         } else {
            sb.append(this.oneToOneDoSetBody());
         }
      }

      sb.append("}");
      sb.append(EOL);
      sb.append(EOL);
      if (!this.bean.isForeignPrimaryKeyField(this.curField)) {
         sb.append("public boolean ");
         sb.append(this.checkIsRemovedMethodName(this.curField));
         sb.append("(");
         sb.append(fieldClassName);
         sb.append(" ");
         sb.append(this.curField);
         sb.append(") throws java.lang.Exception {");
         sb.append(EOL);
         sb.append(this.parse(this.getProductionRule("checkIsRemovedBody")));
         sb.append("}");
         sb.append(EOL);
         sb.append(EOL);
      }

      if (!this.bean.isRemoteField(this.curField)) {
         sb.append("private void ");
         sb.append(this.postSetMethodName(this.curField));
         sb.append("() throws java.lang.Exception {");
         sb.append(EOL);
         if (!this.bd.isReadOnly() || this.bean.allowReadonlyCreateAndRemove()) {
            if (this.bean.isForeignKeyField(this.curField)) {
               sb.append(this.parse(this.getProductionRule("oneToOnePostSetBody_fkOwner")));
            } else {
               sb.append(this.parse(this.getProductionRule("oneToOnePostSetBody")));
            }
         }

         sb.append("}");
         sb.append(EOL);
         sb.append(EOL);
         sb.append("public void ");
         sb.append(this.setRestMethodName(this.curField));
         sb.append("(");
         sb.append(fieldClassName);
         sb.append(" ");
         sb.append(this.curField);
         sb.append(", int methodState) {");
         sb.append(EOL);
         if (!this.bd.isReadOnly() || this.bean.allowReadonlyCreateAndRemove()) {
            if (this.bean.isForeignKeyField(this.curField)) {
               sb.append(this.parse(this.getProductionRule("oneToOneSetRestBody_fkOwner")));
            } else {
               sb.append(this.parse(this.getProductionRule("oneToOneSetRestBody")));
            }
         }

         sb.append("}");
         sb.append(EOL);
         sb.append(EOL);
         sb.append("public void ");
         sb.append(this.setCmrIsLoadedMethodName(this.curField));
         sb.append("(boolean b) {");
         sb.append(EOL);
         if (!this.bd.isReadOnly() || this.bean.allowReadonlyCreateAndRemove()) {
            sb.append(this.isCmrLoadedVarName(this.curField) + " = b;");
            sb.append(EOL);
         }

         sb.append("}");
         sb.append(EOL);
         sb.append(EOL);
      }

      return sb.toString();
   }

   public String perhapsOptimizeOneToOne() {
      StringBuffer sb = new StringBuffer();
      if (this.bean.isBiDirectional(this.curField)) {
         sb.append("if(methodState != WLEnterpriseBean.STATE_EJB_POSTCREATE)" + EOL);
         sb.append(this.getMethodNameForField() + "();" + EOL);
      } else {
         sb.append("if(this.__WL_getMethodState() != WLEnterpriseBean.STATE_EJB_POSTCREATE) " + EOL);
         sb.append(this.getMethodNameForField() + "();" + EOL);
      }

      return sb.toString();
   }

   public String relClassNameForField() {
      return this.bean.getRelatedBeanClassName(this.curField);
   }

   public String relInterfaceNameForField() {
      return this.relInterfaceNameForField(this.curField);
   }

   public String relInterfaceNameForField(String field) {
      CMPBeanDescriptor rbd = this.bean.getRelatedDescriptor(field);
      return rbd.getGeneratedBeanInterfaceName();
   }

   public String relatedDoSetForField() {
      String relFieldNm = this.bean.getRelatedFieldName(this.curField);
      return this.doSetMethodName(relFieldNm);
   }

   public String componentInterfaceForBean() {
      return this.bd.hasLocalClientView() ? this.bd.getLocalInterfaceClass().getName() : this.bd.getRemoteInterfaceClass().getName();
   }

   public String relatedSetRestForField() {
      String relFieldNm = this.bean.getRelatedFieldName(this.curField);
      return this.setRestMethodName(relFieldNm);
   }

   public String relatedIsCmrLoadedVarNameForField() {
      String relFieldNm = this.bean.getRelatedFieldName(this.curField);
      return this.setCmrIsLoadedMethodName(relFieldNm);
   }

   public String oneToOneSetBody_fkOwner() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      if (this.bean.isForeignPrimaryKeyField(this.curField)) {
         sb.append("Loggable l = EJBLogger.logsetCheckForCmrFieldAsPkLoggable();" + EOL);
         sb.append("throw new EJBException(l.getMessage());" + EOL + EOL);
      } else {
         sb.append("if (__WL_method_state==STATE_EJB_CREATE) {" + EOL);
         sb.append("Loggable l = EJBLogger.logsetCheckForCmrFieldDuringEjbCreateLoggable();" + EOL);
         sb.append("throw new IllegalStateException(l.getMessage());" + EOL);
         sb.append("}" + EOL);
         sb.append("try {" + EOL);
         sb.append("if (" + this.checkIsRemovedMethodName(this.curField) + "(" + this.curField + ")) {" + EOL);
         sb.append("Loggable l = EJBLogger.logillegalAttemptToAssignRemovedBeanToCMRFieldLoggable(");
         sb.append(this.curField).append(".getPrimaryKey().toString());").append(EOL);
         sb.append("throw new IllegalArgumentException(l.getMessageText());" + EOL);
         sb.append("}" + EOL);
         sb.append("if (" + this.debugEnabled() + ") {" + EOL);
         sb.append(this.debugSay() + "(\"[\" + " + this.ctxVar() + ".getPrimaryKey() + \"]called ");
         sb.append(MethodUtils.setMethodName(this.curField) + "...\");" + EOL);
         sb.append("}" + EOL);
         if (!this.bean.isRemoteField(this.curField)) {
            sb.append(this.setNullMethodName(this.curField) + "(false);" + EOL);
         }

         sb.append(this.doSetMethodName(this.curField) + "(" + this.curField + ");" + EOL);
         if (!this.bean.isRemoteField(this.curField)) {
            sb.append(this.postSetMethodName(this.curField) + "();" + EOL);
         }

         sb.append(this.parse(this.getProductionRule("standardCatch")));
      }

      return sb.toString();
   }

   public String oneToOneSetBody() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      RDBMSBean rbean = this.bean.getRelatedRDBMSBean(this.curField);
      String rfield = this.bean.getRelatedFieldName(this.curField);
      if (rbean.isForeignPrimaryKeyField(rfield)) {
         sb.append("Loggable l = EJBLogger.logsetCheckForCmrFieldAsPkLoggable();" + EOL);
         sb.append("throw new EJBException(l.getMessage());" + EOL + EOL);
      } else {
         sb.append("if (__WL_method_state==STATE_EJB_CREATE) {" + EOL);
         sb.append("Loggable l = EJBLogger.logsetCheckForCmrFieldDuringEjbCreateLoggable();" + EOL);
         sb.append("throw new IllegalStateException(l.getMessage());" + EOL);
         sb.append("}" + EOL);
         sb.append("try {" + EOL);
         sb.append("if (" + this.checkIsRemovedMethodName(this.curField) + "(" + this.curField + ")) {" + EOL);
         sb.append("Loggable l = EJBLogger.logillegalAttemptToAssignRemovedBeanToCMRFieldLoggable(");
         sb.append(this.curField).append(".getPrimaryKey().toString());").append(EOL);
         sb.append("throw new IllegalArgumentException(l.getMessageText());" + EOL);
         sb.append("}" + EOL);
         sb.append(this.setNullMethodName(this.curField) + "(false);" + EOL);
         sb.append(this.doSetMethodName(this.curField) + "(" + this.curField + ");" + EOL + EOL);
         sb.append(this.postSetMethodName(this.curField) + "();" + EOL);
         sb.append(this.parse(this.getProductionRule("standardCatch")));
      }

      return sb.toString();
   }

   public String assignFkVarsNull_forField() {
      StringBuffer sb = new StringBuffer();
      if (!this.bean.isForeignPrimaryKeyField(this.curField)) {
         String fkTable = this.bean.getTableForCmrField(this.curField);
         Iterator colNames = this.bean.getForeignKeyColNames(this.curField).iterator();

         String fkColumn;
         while(colNames.hasNext()) {
            String colName = (String)colNames.next();
            fkColumn = this.bean.variableForField(this.curField, fkTable, colName);
            sb.append(fkColumn + " = null;" + EOL);
         }

         if (this.bean.isForeignCmpField(this.curField)) {
            Iterator fkColumns = this.bean.getForeignKeyColNames(this.curField).iterator();

            while(fkColumns.hasNext()) {
               fkColumn = (String)fkColumns.next();
               String cmpField = this.bean.getCmpFieldForColumn(fkColumn);
               sb.append(this.isModifiedVar(cmpField) + " = true;" + EOL);
            }
         } else {
            sb.append(this.isModifiedVarForField() + " = true;" + EOL);
         }

         sb.append(this.perhapsSetTableModifiedVarForCmrField());
      }

      return sb.toString();
   }

   public String assignFkVarsFkField_forField() {
      StringBuffer sb = new StringBuffer();
      if (!this.bean.isForeignPrimaryKeyField(this.curField)) {
         boolean hasComplexPk = false;
         CMPBeanDescriptor rbd;
         String relPkClassName;
         String fkTable;
         if (!this.bean.isRemoteField(this.curField)) {
            rbd = this.bean.getRelatedDescriptor(this.curField);
            Class relPkClass = rbd.getPrimaryKeyClass();
            relPkClassName = relPkClass.getName();
            if (rbd.hasComplexPrimaryKey()) {
               hasComplexPk = true;
               sb.append(this.declareFkVarForField());
               sb.append(this.fkVar() + " = (" + relPkClassName + ")");
               sb.append(this.fieldVarGetPrimaryKey()).append(";").append(EOL);
               fkTable = this.bean.getTableForCmrField(this.curField);

               String fkFieldVar;
               Field fkField;
               for(Iterator colNames = this.bean.getForeignKeyColNames(this.curField).iterator(); colNames.hasNext(); sb.append(this.assignPkFieldToVariable(fkFieldVar, this.fkVar(), fkField))) {
                  String colName = (String)colNames.next();
                  fkFieldVar = this.bean.variableForField(this.curField, fkTable, colName);
                  String relatedPkField = this.bean.getRelatedPkFieldName(this.curField, colName);
                  fkField = null;

                  try {
                     fkField = relPkClass.getField(relatedPkField);
                  } catch (Exception var13) {
                     throw new AssertionError("Unable to access field '" + relatedPkField + "' in class '" + relPkClassName + "'. " + StackTraceUtilsClient.throwable2StackTrace(var13));
                  }
               }
            }
         }

         String fkColumn;
         if (!hasComplexPk) {
            rbd = this.bean.getRelatedDescriptor(this.curField);
            fkColumn = this.bean.getTableForCmrField(this.curField);
            relPkClassName = (String)this.bean.getForeignKeyColNames(this.curField).iterator().next();
            fkTable = this.bean.variableForField(this.curField, fkColumn, relPkClassName);
            String className = ClassUtils.classToJavaSourceType((Class)this.variableToClass.get(fkTable));
            sb.append(fkTable + " = ");
            sb.append("(" + className + ")");
            sb.append(this.fieldVarGetPrimaryKey()).append(";").append(EOL);
         }

         if (this.bean.isForeignCmpField(this.curField)) {
            Iterator fkColumns = this.bean.getForeignKeyColNames(this.curField).iterator();

            while(fkColumns.hasNext()) {
               fkColumn = (String)fkColumns.next();
               relPkClassName = this.bean.getCmpFieldForColumn(fkColumn);
               sb.append(this.isModifiedVar(relPkClassName) + " = true;" + EOL);
            }
         } else {
            sb.append(this.isModifiedVarForField() + " = true;" + EOL);
         }

         sb.append(this.perhapsSetTableModifiedVarForCmrField());
      }

      return sb.toString();
   }

   public String assignPkFieldToVariable(String var, String pkVar, Field pkField) {
      Class type = pkField.getType();
      String lhs = pkVar + "." + pkField.getName();
      if (type.isPrimitive()) {
         if (type == Boolean.TYPE) {
            lhs = "new Boolean(" + lhs + ")";
         } else if (type == Byte.TYPE) {
            lhs = "new Byte(" + lhs + ")";
         } else if (type == Character.TYPE) {
            lhs = "new Character(" + lhs + ")";
         } else if (type == Double.TYPE) {
            lhs = "new Double(" + lhs + ")";
         } else if (type == Float.TYPE) {
            lhs = "new Float(" + lhs + ")";
         } else if (type == Integer.TYPE) {
            lhs = "new Integer(" + lhs + ")";
         } else if (type == Long.TYPE) {
            lhs = "new Long(" + lhs + ")";
         } else {
            if (type != Short.TYPE) {
               throw new AssertionError("Missing primitive in CommonRules.assignPkFieldToVariable");
            }

            lhs = "new Short(" + lhs + ")";
         }
      }

      return var + " = " + lhs + ";" + EOL;
   }

   public String oneToOneDoSetBody() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      sb.append(this.assignFieldVarForFieldWithFieldNameForField()).append(EOL);
      sb.append(this.isCmrLoadedVarName(this.curField) + " = true;" + EOL);
      sb.append(EOL);
      sb.append("  // whenever this bean's relationship with " + this.curField + " changes" + EOL);
      sb.append("  // this __WL_doSet method is invoked." + EOL);
      sb.append("  // so we mark the nonFKHolderRelationChange bit in this __WL_doSet method." + EOL);
      sb.append("  __WL_setNonFKHolderRelationChange(true);" + EOL);
      sb.append(this.parse(this.getProductionRule("registerInvalidatedBean")));
      return sb.toString();
   }

   private String oneToManySetMethod() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      Class cmrFieldClass = this.bean.getCmrFieldClass(this.curField);
      String fieldClassName = ClassUtils.classToJavaSourceType(cmrFieldClass);
      sb.append("public void ");
      sb.append(MethodUtils.setMethodName(this.curField));
      sb.append("(");
      sb.append(fieldClassName);
      sb.append(" ");
      sb.append(this.curField);
      sb.append(") {");
      sb.append(EOL);
      if (this.bean.getRelatedMultiplicity(this.curField).equals("One")) {
         if (this.bean.isForeignPrimaryKeyField(this.curField)) {
            sb.append("Loggable l = EJBLogger.logsetCheckForCmrFieldAsPkLoggable();" + EOL);
            sb.append("throw new EJBException(l.getMessage());" + EOL + EOL);
         } else if (this.bd.isReadOnly() && !this.bean.allowReadonlyCreateAndRemove()) {
            sb.append("Loggable l = EJBLogger.logCannotCallSetForReadOnlyBeanLoggable(\"" + this.bean.getEjbName() + "\");" + EOL);
            sb.append("throw new javax.ejb.EJBException(l.getMessageText());" + EOL);
         } else {
            sb.append("if (__WL_method_state==STATE_EJB_CREATE) {" + EOL);
            sb.append("Loggable l = EJBLogger.logsetCheckForCmrFieldDuringEjbCreateLoggable();" + EOL);
            sb.append("throw new IllegalStateException(l.getMessage());" + EOL);
            sb.append("}" + EOL);
            sb.append("try {" + EOL);
            sb.append("if (" + this.checkIsRemovedMethodName(this.curField) + "(" + this.curField + ")) {" + EOL);
            sb.append("Loggable l = EJBLogger.logillegalAttemptToAssignRemovedBeanToCMRFieldLoggable(");
            sb.append(this.curField).append(".getPrimaryKey().toString());");
            sb.append(EOL);
            sb.append("throw new IllegalArgumentException(l.getMessageText());" + EOL);
            sb.append("}" + EOL);
            sb.append(this.parse(this.getProductionRule("standardCatch")));
            sb.append(this.varPrefix() + MethodUtils.setMethodName(this.curField) + "(" + this.curField + ", false);" + EOL);
         }

         sb.append("}" + EOL);
         sb.append("public void ");
         sb.append(this.varPrefix() + MethodUtils.setMethodName(this.curField));
         sb.append("(");
         sb.append(fieldClassName);
         sb.append(" ");
         sb.append(this.curField);
         sb.append(", boolean ejbStore) {");
         sb.append(EOL);
         sb.append(this.varPrefix() + MethodUtils.setMethodName(this.curField));
         sb.append("(").append(this.curField).append(", ejbStore, true);" + EOL);
         sb.append("}" + EOL + EOL);
         sb.append("// The flag 'remove' controls whether the Relationship's" + EOL);
         sb.append("// underlying __WL_cache does a remove() operation." + EOL);
         sb.append("// If an Iterator of the __WL_cache is used to effect a remove()" + EOL);
         sb.append("// then we must be sure to not to do a__WL_cache.remove()" + EOL);
         sb.append("//   that is the intended use of the 'remove' flag." + EOL + EOL);
         sb.append("public void ");
         sb.append(this.varPrefix() + MethodUtils.setMethodName(this.curField));
         sb.append("(");
         sb.append(fieldClassName);
         sb.append(" ");
         sb.append(this.curField);
         sb.append(", boolean ejbStore");
         sb.append(", boolean remove) {");
         sb.append(EOL);
         if (!this.bd.isReadOnly() || this.bean.allowReadonlyCreateAndRemove()) {
            if (!this.bean.isRemoteField(this.curField)) {
               sb.append(this.parse(this.getProductionRule("oneToManySetBody_local_fkOwner")));
            } else {
               sb.append(this.parse(this.getProductionRule("oneToManySetBody_remote_fkOwner")));
            }
         }

         sb.append("}");
         sb.append(EOL);
         sb.append(EOL);
         if (!this.bean.isForeignPrimaryKeyField(this.curField)) {
            sb.append("public boolean ");
            sb.append(this.checkIsRemovedMethodName(this.curField));
            sb.append("(");
            sb.append(fieldClassName);
            sb.append(" ");
            sb.append(this.curField);
            sb.append(") throws java.lang.Exception {");
            sb.append(EOL);
            sb.append(this.parse(this.getProductionRule("checkIsRemovedBody")));
            sb.append("}");
            sb.append(EOL);
            sb.append(EOL);
         }

         if ((!this.bd.isReadOnly() || this.bean.allowReadonlyCreateAndRemove()) && !this.bean.isRemoteField(this.curField)) {
            sb.append("private void " + this.postSetMethodName(this.curField) + "() throws java.lang.Exception {" + EOL);
            sb.append(this.parse(this.getProductionRule("oneToManyPostSetBody")));
            sb.append("}");
            sb.append(EOL);
            sb.append(EOL);
         }
      } else {
         boolean foreignPrimary = false;
         if (!this.bean.isRemoteField(this.curField)) {
            RDBMSBean rbean = this.bean.getRelatedRDBMSBean(this.curField);
            String rfield = this.bean.getRelatedFieldName(this.curField);
            if (rbean.isForeignPrimaryKeyField(rfield)) {
               foreignPrimary = true;
               sb.append("Loggable l = EJBLogger.logsetCheckForCmrFieldAsPkLoggable();");
               sb.append("throw new EJBException(l.getMessage());" + EOL + EOL);
            }
         }

         if (!foreignPrimary) {
            if (this.bd.isReadOnly() && !this.bean.allowReadonlyCreateAndRemove()) {
               sb.append("Loggable l = EJBLogger.logCannotCallSetForReadOnlyBeanLoggable(\"" + this.bean.getEjbName() + "\");" + EOL);
               sb.append("throw new javax.ejb.EJBException(l.getMessageText());" + EOL);
            } else {
               sb.append("if (__WL_method_state==STATE_EJB_CREATE) {" + EOL);
               sb.append("Loggable l = EJBLogger.logsetCheckForCmrFieldDuringEjbCreateLoggable();");
               sb.append("throw new IllegalStateException(l.getMessage());" + EOL);
               sb.append("}" + EOL);
               sb.append(this.parse(this.getProductionRule("oneToManySetBody")));
            }
         }

         sb.append("}");
         sb.append(EOL);
         sb.append(EOL);
      }

      if (this.bean.getRelatedMultiplicity(this.curField).equals("One") && (!this.bd.isReadOnly() || this.bean.allowReadonlyCreateAndRemove())) {
         sb.append("public void ");
         sb.append(this.doSetMethodName(this.curField));
         sb.append("(");
         sb.append(fieldClassName);
         sb.append(" ");
         sb.append(this.curField);
         sb.append(") {");
         sb.append(EOL);
         sb.append(this.parse(this.getProductionRule("oneToOneDoSetBody_fkOwner")));
         sb.append("}");
         sb.append(EOL);
         sb.append(EOL);
      }

      return sb.toString();
   }

   public String perhapsCallPostSetMethodForField() {
      StringBuffer sb = new StringBuffer();
      if (!this.bean.isForeignPrimaryKeyField(this.curField)) {
         sb.append(this.postSetMethodName(this.curField) + "();" + EOL);
      }

      return sb.toString();
   }

   private String manyToManySetMethod() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      String fieldClassName = ClassUtils.classToJavaSourceType(this.bean.getCmrFieldClass(this.curField));
      sb.append("public void ");
      sb.append(MethodUtils.setMethodName(this.curField));
      sb.append("(");
      sb.append(fieldClassName);
      sb.append(" ");
      sb.append(this.curField);
      sb.append(") {");
      sb.append(EOL);
      sb.append(this.parse(this.getProductionRule("oneToManySetBody")));
      sb.append("}");
      sb.append(EOL);
      sb.append(EOL);
      return sb.toString();
   }

   public String copyFromMethodBody() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      sb.append(this.cmpBeanClassName() + " " + this.beanVar() + " = null;" + EOL);
      sb.append("try {" + EOL);
      sb.append(this.beanVar() + " = (" + this.cmpBeanClassName() + ")otherBean;" + EOL);
      sb.append(this.parse(this.getProductionRule("standardCatch")));
      Iterator fieldIter = this.cmpFieldNames.iterator();

      String fieldName;
      while(fieldIter.hasNext()) {
         fieldName = (String)fieldIter.next();
         sb.append(this.checkFieldNotModifiedOrLoaded("this", fieldName));
         sb.append("if (" + this.beanVar() + "." + this.isLoadedVar() + "[" + this.bean.getIsModifiedIndex(fieldName) + "]) {" + EOL);
         sb.append("if (" + this.debugEnabled() + ") {" + EOL);
         sb.append(this.debugSay() + "(\"copying field '" + fieldName + "' to bean '\" +" + EOL + this.beanVar() + ".__WL_getPrimaryKey() + \"'.\");" + EOL);
         sb.append("}" + EOL);
         sb.append(this.setCmpField(fieldName, this.getCmpField(this.beanVar(), fieldName)) + ";" + EOL);
         sb.append("this." + this.isLoadedVar() + "[" + this.bean.getIsModifiedIndex(fieldName) + "]");
         sb.append(" = true;" + EOL);
         if (!this.bd.isReadOnly() || this.bean.allowReadonlyCreateAndRemove()) {
            sb.append("if (!__WL_initSnapshotVars) {").append(EOL);
            sb.append("this." + this.isModifiedVar() + "[" + this.bean.getIsModifiedIndex(fieldName) + "] = true;" + EOL);
            sb.append(this.beanIsModifiedVar()).append(" = true;").append(EOL);
            sb.append("}").append(EOL);
         }

         if (this.bd.isOptimistic() && !this.bd.getPrimaryKeyFieldNames().contains(fieldName) && !this.bean.isBlobCmpColumnTypeForField(fieldName) && !this.bean.isClobCmpColumnTypeForField(fieldName) && this.doSnapshot(fieldName)) {
            sb.append("if (__WL_initSnapshotVars) ");
            sb.append("this." + CodeGenUtils.snapshotNameForVar(fieldName) + " = " + this.beanVar() + "." + CodeGenUtils.snapshotNameForVar(fieldName) + ";" + EOL);
         }

         sb.append("}" + EOL);
         sb.append("}" + EOL + EOL);
      }

      fieldIter = this.bean.getForeignKeyFieldNames().iterator();

      while(true) {
         do {
            do {
               if (!fieldIter.hasNext()) {
                  if (!this.bd.isReadOnly() || this.bean.allowReadonlyCreateAndRemove()) {
                     sb.append("this." + this.modifiedBeanIsRegisteredVar() + " = " + this.beanVar() + "." + this.modifiedBeanIsRegisteredVar() + ";" + EOL);
                  }

                  sb.append("this." + this.beanIsLoadedVar() + " = " + this.beanVar() + "." + this.beanIsLoadedVar() + ";" + EOL + EOL);
                  fieldIter = this.bean.getAllCmrFields().iterator();

                  while(fieldIter.hasNext()) {
                     fieldName = (String)fieldIter.next();
                     this.curField = fieldName;
                     sb.append("if (!this." + this.isCmrLoadedVarName(fieldName) + ") {" + EOL);
                     sb.append("if (" + this.beanVar() + "." + this.isCmrLoadedVarName(fieldName) + "==true) {" + EOL);
                     sb.append("if (" + this.debugEnabled() + ") {" + EOL);
                     sb.append(this.debugSay() + "(\"copying cmr field '" + fieldName + "' to bean '\" +" + EOL + this.beanVar() + ".__WL_getPrimaryKey() + \"'.\");" + EOL);
                     sb.append("}" + EOL);
                     sb.append("this." + CodeGenUtils.fieldVarName(fieldName) + " = " + this.beanVar() + "." + CodeGenUtils.fieldVarName(fieldName) + ";" + EOL);
                     sb.append("this." + this.isCmrLoadedVarName(fieldName) + " = true;" + EOL);
                     sb.append("}" + EOL);
                     sb.append("}" + EOL + EOL);
                  }

                  return sb.toString();
               }

               fieldName = (String)fieldIter.next();
            } while(!this.bean.containsFkField(fieldName));
         } while(this.bean.isForeignCmpField(fieldName));

         sb.append(this.checkFieldNotModifiedOrLoaded("this", fieldName));
         sb.append("if (" + this.beanVar() + "." + this.isLoadedVar() + "[" + this.bean.getIsModifiedIndex(fieldName) + "]==true) {" + EOL);
         sb.append("if (" + this.debugEnabled() + ") {" + EOL);
         sb.append(this.debugSay() + "(\"copying field '" + fieldName + "' to bean '\" +" + EOL + this.beanVar() + ".__WL_getPrimaryKey() + \"'.\");" + EOL);
         sb.append("}" + EOL);
         String fkTable = this.bean.getTableForCmrField(fieldName);
         Iterator colNames = this.bean.getForeignKeyColNames(fieldName).iterator();

         while(colNames.hasNext()) {
            String colName = (String)colNames.next();
            String varName = this.bean.variableForField(fieldName, fkTable, colName);
            sb.append("this." + varName + " = " + this.beanVar() + "." + varName + ";" + EOL);
            if (this.bd.isOptimistic() && this.doSnapshot(varName)) {
               sb.append("if (__WL_initSnapshotVars) ");
               sb.append("this." + CodeGenUtils.snapshotNameForVar(varName) + " = " + this.beanVar() + "." + CodeGenUtils.snapshotNameForVar(varName) + ";" + EOL);
            }
         }

         sb.append("this." + this.isLoadedVar() + "[" + this.bean.getIsModifiedIndex(fieldName) + "]");
         sb.append(" = true;" + EOL);
         if (!this.bd.isReadOnly() || this.bean.allowReadonlyCreateAndRemove()) {
            sb.append("if (!__WL_initSnapshotVars) {").append(EOL);
            sb.append("this." + this.isModifiedVar() + "[" + this.bean.getIsModifiedIndex(fieldName) + "] = true;" + EOL);
            sb.append(this.beanIsModifiedVar()).append(" = true;").append(EOL);
            sb.append("}").append(EOL);
         }

         sb.append("}" + EOL);
         sb.append("}" + EOL + EOL);
      }
   }

   public String tableName() {
      return this.bean.getQuotedTableName();
   }

   public String curTableName() {
      return this.curTableName;
   }

   public int curTableIndex() {
      return this.curTableIndex;
   }

   public String implementFinderMethods() throws EJBCException {
      if (debugLogger.isDebugEnabled()) {
         debug("implementFinderMethods() called.");
      }

      StringBuffer sb = new StringBuffer(100);
      Iterator finders = this.finderList.iterator();

      while(finders.hasNext()) {
         Finder finder = (Finder)finders.next();
         if (finder.getQueryType() == 0 && !(finder instanceof SqlFinder)) {
            try {
               if (debugLogger.isDebugEnabled()) {
                  debug("generating finder: " + finder);
               }

               sb.append(this.implementFinderMethod(finder));
            } catch (CodeGenerationException var8) {
               Loggable l = EJBLogger.logCouldNotGenerateFinderLoggable("finder", finder.toString(), var8.getMessage());
               throw new EJBCException(l.getMessageText());
            }
         }
      }

      Iterator relFinders = this.bean.getRelationFinders();

      while(relFinders.hasNext()) {
         Finder finder = (Finder)relFinders.next();
         if (finder.getQueryType() == 0) {
            try {
               sb.append(this.implementFinderMethod(finder));
            } catch (CodeGenerationException var7) {
               Loggable l = EJBLogger.logCouldNotGenerateFinderLoggable(" relation finder", finder.toString(), var7.getMessage());
               throw new EJBCException(l.getMessageText());
            }
         }
      }

      return sb.toString();
   }

   public String perhapsImplementRelCachingForDynamicFinders() {
      StringBuffer sb = new StringBuffer();
      boolean isRelationshipCaching = false;
      List relationshipCachings = this.bean.getRelationshipCachings();
      if (relationshipCachings != null) {
         isRelationshipCaching = relationshipCachings.iterator().hasNext();
      }

      if (!isRelationshipCaching) {
         return "";
      } else {
         Iterator it = relationshipCachings.iterator();

         while(true) {
            RelationshipCaching rc;
            String cachingName;
            do {
               if (!it.hasNext()) {
                  return sb.toString();
               }

               rc = (RelationshipCaching)it.next();
               cachingName = rc.getCachingName();
            } while(cachingName == null && cachingName == "");

            sb.append(this.generateMethodsToLoadBeansForCachingNames(rc, this.bean));
         }
      }
   }

   private String generateMethodsToLoadBeansForCachingNames(RelationshipCaching rc, RDBMSBean bean) {
      StringBuffer sb = new StringBuffer();
      String cachingName = rc.getCachingName();
      List cachingElements = rc.getCachingElements();
      sb.append(EOL + "public void loadBeansFor" + this.replaceIllegalJavaCharacters(cachingName) + "(java.sql.ResultSet " + this.rsVar() + ", CMPBean bean, int groupColumnCount, QueryCachingHandler qcHandler) " + EOL);
      sb.append(" throws Exception {" + EOL + EOL);
      sb.append(EOL + "// load related beans " + EOL);
      sb.append(this.getGeneratedBeanClassName() + " " + this.beanVar() + " = (" + this.getGeneratedBeanClassName() + ") bean;" + EOL + EOL);
      this.perhapsRelationshipCachingPooledBeanVar(bean, cachingElements.iterator(), (String)null, 1, sb);
      sb.append(EOL);
      this.perhapsRelationshipCachingPooledBeanVar(bean, cachingElements.iterator(), (String)null, 2, sb);
      sb.append(EOL);
      sb.append("Integer " + this.offsetIntObjVar() + " = null;" + EOL);
      sb.append("Object " + this.eoRCVar() + " = null;" + EOL);
      sb.append("Object " + this.eoVar() + " = null;" + EOL);
      sb.append(EOL + "Map " + this.pkMapVar() + " = new HashMap();" + EOL);
      sb.append(this.declarePKMapVarForCachingElements(bean, cachingElements.iterator(), (String)null));
      sb.append(EOL + EOL);
      sb.append("int increment = groupColumnCount;" + EOL);
      this.invokeEagerCachingMethodsForCachingElements(bean, cachingElements.iterator(), (String)null, sb, true);
      sb.append("}" + EOL);
      return sb.toString();
   }

   public String implementLoaderMethodForDynamicFinders() {
      StringBuffer sb = new StringBuffer();
      List relationshipCachings = this.bean.getRelationshipCachings();
      Iterator it = relationshipCachings.iterator();
      sb.append("public void __WL_loadBeansRelatedToCachingName(String cachingName, java.sql.ResultSet rs, CMPBean bean, int groupColumnCount, QueryCachingHandler qcHandler)" + EOL);
      sb.append("throws Exception {" + EOL);

      while(true) {
         String cachingName;
         do {
            if (!it.hasNext()) {
               sb.append("}" + EOL);
               return sb.toString();
            }

            RelationshipCaching rc = (RelationshipCaching)it.next();
            cachingName = rc.getCachingName();
         } while(cachingName == null && cachingName != "");

         sb.append("if(cachingName.equals(\"" + cachingName + "\")) {" + EOL);
         sb.append("loadBeansFor" + this.replaceIllegalJavaCharacters(cachingName) + "(rs, bean, groupColumnCount, qcHandler);" + EOL);
         sb.append("}" + EOL);
      }
   }

   private String doSetMethodName(String fieldName) {
      return "__WL_doSet" + this.capitalize(fieldName);
   }

   private String setRestMethodName(String fieldName) {
      return "__WL_setRest" + this.capitalize(fieldName);
   }

   private String checkIsRemovedMethodName(String fieldName) {
      return "__WL_checkIsRemoved" + this.capitalize(fieldName);
   }

   private String setCmrIsLoadedMethodName(String fieldName) {
      return "__WL_set_" + fieldName + "_isLoaded_";
   }

   private String setNullMethodName(String fieldName) {
      return "__WL_setNull" + this.capitalize(fieldName);
   }

   private String postSetMethodName(String fieldName) {
      return "__WL_postSet" + this.capitalize(fieldName);
   }

   private String getEjbSelectInternalMethodDeclaration(Finder f, Class pkClazz) {
      String methodName = this.ejbSelectMDName(f);
      List parameterList = f.getExternalMethodAndInEntityParmList();
      String retClassName = this.getFinderReturnClass(f, pkClazz);
      return this.getFinderMethodDeclaration(f, retClassName, methodName, parameterList);
   }

   private String ejbSelectMDName(Finder f) {
      Class[] paramClassTypes;
      if (f instanceof EjbqlFinder && ((EjbqlFinder)f).isGeneratedRelationFinder()) {
         paramClassTypes = new Class[0];
      } else {
         paramClassTypes = f.getParameterClassTypes();
      }

      String beanName = null;
      if (f instanceof EjbqlFinder && !((EjbqlFinder)f).isGeneratedRelationFinder()) {
         beanName = MethodUtils.tail(f.getRDBMSBean().getCMPBeanDescriptor().getGeneratedBeanClassName());
      }

      return CodeGenUtils.convertToEjbSelectInternalName(f.getName(), paramClassTypes, beanName);
   }

   private String implementFinderMethod(Finder finder) throws EJBCException, CodeGenerationException {
      if (debugLogger.isDebugEnabled()) {
         debug("implementFinderMethod(" + finder + ") called.");
      }

      assert this.bd != null;

      this.curFinder = finder;
      if (this.curFinder == null) {
         Loggable l = EJBLogger.logNullFinderLoggable("implementFinderMethod");
         throw new EJBCException(l.getMessageText());
      } else {
         StringBuffer sb = new StringBuffer();
         String methodDecl = null;
         if (finder.getQueryType() != 4 && finder.getQueryType() != 2) {
            methodDecl = this.getFinderMethodDeclaration(finder, this.bd.getPrimaryKeyClass());
         } else {
            methodDecl = this.getEjbSelectInternalMethodDeclaration(finder, this.bd.getPrimaryKeyClass());
         }

         sb.append(methodDecl);
         sb.append("{" + EOL);

         try {
            if (this.curFinder.isMultiFinder()) {
               sb.append(this.parse(this.getProductionRule("finderMethodBodyMulti")));
            } else {
               sb.append(this.parse(this.getProductionRule("finderMethodBodyScalar")));
            }
         } catch (CodeGenerationException var6) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("finderMethod cought CodeGenerationException", var6);
            }

            Loggable l = EJBLogger.logCouldNotProduceProductionRuleLoggable("finder");
            EJBLogger.logStackTraceAndMessage(l.getMessageText(), var6);
            throw var6;
         }

         sb.append("" + EOL + "}" + EOL + EOL);
         this.curFinder = null;
         return sb.toString();
      }
   }

   private String getFinderReturnClass(Finder finder, Class pkClazz) {
      Class returnClass = finder.getReturnClassType();
      boolean multiFinder = Enumeration.class.isAssignableFrom(returnClass);
      multiFinder |= Collection.class.isAssignableFrom(returnClass);
      if (multiFinder) {
         return returnClass.getName();
      } else {
         return finder.finderLoadsBean() && finder.getQueryType() != 3 && finder.getQueryType() != 5 ? Object.class.getName() : ClassUtils.classToJavaSourceType(pkClazz);
      }
   }

   private String getFinderMethodDeclaration(Finder f, Class pkCLazz) {
      String methodName = MethodUtils.convertToFinderName(f.getName());
      List parameterList = f.getExternalMethodParmList();
      String retClassName = this.getFinderReturnClass(f, pkCLazz);
      return this.getFinderMethodDeclaration(f, retClassName, methodName, parameterList);
   }

   private String getFinderMethodDeclaration(Finder finder, String returnClassName, String methodName, List parameterList) {
      StringBuffer sb = new StringBuffer();
      sb.append(finder.getModifierString());
      sb.append(returnClassName);
      if (sb.length() > 73) {
         sb.append("" + EOL + "    ");
      }

      sb.append(" ");
      sb.append(methodName);
      if (sb.length() > 73) {
         sb.append("" + EOL + "    ");
      }

      sb.append("(");
      if (finder.isKeyFinder() && ((EjbqlFinder)finder).getKeyBean().getCMPBeanDescriptor().hasComplexPrimaryKey()) {
         RDBMSBean rb = ((EjbqlFinder)finder).getKeyBean();
         sb.append(rb.getCMPBeanDescriptor().getPrimaryKeyClass().getName() + " param0");
      } else {
         StringBuffer sb2 = new StringBuffer();
         Iterator it = parameterList.iterator();

         while(it.hasNext()) {
            ParamNode n = (ParamNode)it.next();
            sb2.append(ClassUtils.classToJavaSourceType(n.getParamClass()));
            sb2.append(" ").append(n.getParamName());
            sb2.append(", ");
         }

         if (sb2.length() > 2) {
            sb2.setLength(sb2.length() - 2);
         }

         sb.append(sb2.toString());
      }

      if (sb.length() > 73) {
         sb.append("" + EOL + "    ");
      }

      sb.append(")");
      int count = 0;
      Class[] var13 = finder.getExceptionClassTypes();
      int var14 = var13.length;

      for(int var9 = 0; var9 < var14; ++var9) {
         Class ex = var13[var9];
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

   public String finderMethodName() {
      assert this.curFinder != null;

      return this.curFinder.getName();
   }

   public String firstPrimaryKeyColumn() {
      assert this.curTableName != null;

      return (String)this.bean.getPKCmpf2ColumnForTable(this.curTableName).values().iterator().next();
   }

   public String finderQuery() {
      if (debugLogger.isDebugEnabled()) {
         debug("finderQuery() called for method " + this.curFinder.getName());
      }

      assert this.curFinder != null;

      assert this.curFinder.getSQLQuery() != null;

      return this.curFinder.getSQLQuery();
   }

   public String finderQueryForUpdate() {
      if (debugLogger.isDebugEnabled()) {
         debug("finderQueryForUpdate() called for method " + this.curFinder.getName());
      }

      assert this.curFinder != null;

      assert this.curFinder.getSQLQueryForUpdate() != null;

      return this.curFinder.getSQLQueryForUpdate();
   }

   public String finderQueryForUpdateNoWait() {
      if (debugLogger.isDebugEnabled()) {
         debug("finderQueryForUpdateNoWait() called for method " + this.curFinder.getName());
      }

      assert this.curFinder != null;

      assert this.curFinder.getSQLQueryForUpdateNoWait() != null;

      return this.curFinder.getSQLQueryForUpdateNoWait();
   }

   private String finderQueryForUpdateSelective() {
      if (debugLogger.isDebugEnabled()) {
         debug("finderQueryForUpdateSelective() called for method " + this.curFinder.getName());
      }

      if (debugLogger.isDebugEnabled()) {
         Debug.assertion(this.curFinder != null);
      }

      return this.curFinder.getSQLQueryForUpdateSelective();
   }

   public String generateSqlQueryOrSqlQueryForUpdateOrSqlQueryForUpdateOf() {
      if (debugLogger.isDebugEnabled()) {
         debug("generateSqlQueryOrSqlQueryForUpdateOrSqlQueryForUpdateOf called for method " + this.curFinder.getName());
      }

      if (this.finderQueryForUpdateSelective() != null) {
         return this.queryVar() + " = \"" + this.finderQueryForUpdateSelective() + "\"";
      } else {
         return this.bean.getUseSelectForUpdate() ? this.queryVar() + " = \"" + this.finderQueryForUpdate() + "\"" : this.queryVar() + " = \"" + this.finderQuery() + "\"";
      }
   }

   public String perhapsSetMaxRows() {
      StringBuffer sb = new StringBuffer();
      if (this.curFinder.getMaxElements() != 0) {
         sb.append(this.stmtVar() + ".setMaxRows(" + this.curFinder.getMaxElements() + ");" + EOL);
      }

      return sb.toString();
   }

   public String perhapsFlushCaches() {
      StringBuffer sb = new StringBuffer();
      if (debugLogger.isDebugEnabled()) {
         debug("perhaps flush caches for finder- " + this.curFinder.getName() + "include updates- " + this.curFinder.getIncludeUpdates());
      }

      if (this.curFinder.getIncludeUpdates() && !this.curFinder.isFindByPrimaryKey()) {
         sb.append(this.pmVar() + ".flushModifiedBeans();" + EOL);
      }

      return sb.toString();
   }

   public String findOrEjbSelectBeanNotFound() {
      StringBuffer sb = new StringBuffer();
      sb.append("throw new ");
      sb.append("javax.ejb.ObjectNotFoundException(");
      if (this.curFinder.isFindByPrimaryKey()) {
         sb.append("\"Bean with primary key '\" + param0.toString() + \"' was not found by 'findByPrimaryKey'.\");");
      } else {
         sb.append("\"Bean not found in '");
         sb.append(this.finderMethodName()).append("'.\");");
      }

      return sb.toString();
   }

   public String declareResultVar() {
      StringBuffer sb = new StringBuffer();
      if (this.curFinder.isMultiFinder()) {
         if (this.curFinder.isSetFinder()) {
            sb.append(this.declareOrderedSetVar() + EOL);
         } else {
            sb.append(this.declareColVar() + EOL);
         }
      }

      if (this.curFinder.finderLoadsBean()) {
         sb.append(this.declareBeanVar());
         sb.append(this.declareEoVar());
      } else {
         sb.append(this.declarePkVar());
      }

      return sb.toString();
   }

   public String declareColVar() {
      return "java.util.Collection " + this.colVar() + " = new java.util.ArrayList();";
   }

   public String declareSetVar() {
      return "java.util.Set " + this.setVar() + " = new java.util.HashSet();";
   }

   public String declareOrderedSetVar() {
      return "java.util.Set " + this.orderedSetVar() + " = new weblogic.ejb20.utils.OrderedSet();";
   }

   public String resultVar() {
      StringBuffer sb = new StringBuffer();
      if (this.curFinder.isMultiFinder()) {
         if (this.curFinder.isSetFinder()) {
            sb.append(this.orderedSetVar());
         } else {
            sb.append(this.colVar());
         }
      } else if (this.curFinder.finderLoadsBean()) {
         sb.append(this.eoVar());
      } else {
         sb.append(this.pkVar());
      }

      return sb.toString();
   }

   public String getSimpleGeneratedBeanClassName() {
      return MethodUtils.tail(this.bd.getGeneratedBeanClassName());
   }

   public String getGeneratedBeanClassName() {
      return this.bd.getGeneratedBeanClassName();
   }

   public String getGeneratedBeanInterfaceName() {
      return this.bd.getGeneratedBeanInterfaceName();
   }

   public String declareBeanVar() {
      return this.getGeneratedBeanClassName() + " " + this.beanVar() + " = null;" + EOL;
   }

   public String getBeanFromRS() {
      StringBuffer sb = new StringBuffer();
      sb.append("RSInfo " + this.rsInfoVar() + " = new RSInfoImpl(" + this.rsVar() + ", " + this.curGroup.getIndex() + ", 0, " + this.pkVar() + ");" + EOL);
      sb.append(this.beanVar() + " = (" + this.getGeneratedBeanClassName() + ")" + this.pmVar() + ".getBeanFromRS(" + this.pkVar() + ", " + this.rsInfoVar() + ");");
      sb.append(EOL + EOL);
      return sb.toString();
   }

   public String allocateBeanVar() {
      StringBuffer sb = new StringBuffer();
      sb.append(this.beanVar() + " = (" + this.getGeneratedBeanClassName() + ")" + this.pmVar() + ".getBeanFromPool();" + EOL);
      sb.append(this.beanVar() + ".__WL_initialize();" + EOL);
      return sb.toString();
   }

   public String declareEoVar() {
      return "Object " + this.eoVar() + " = null;" + EOL + "Object " + this.eoRCVar() + " = null;" + EOL;
   }

   public String finderColumnsSql() {
      StringBuffer sb = new StringBuffer();
      List fNames = null;
      if (this.curFinder.finderLoadsBean()) {
         fNames = this.cmpFieldNames;
      } else {
         fNames = this.pkFieldNames;
      }

      Iterator iter = fNames.iterator();

      for(int i = 0; iter.hasNext(); ++i) {
         String fieldName = (String)iter.next();
         String columnName = this.bean.getCmpColumnForField(fieldName);

         assert columnName != null;

         sb.append(RDBMSUtils.escQuotedID(columnName));
         if (i < fNames.size() - 1) {
            sb.append(", ");
         }
      }

      return sb.toString();
   }

   public String setFinderQueryParams() throws EJBCException {
      StringBuffer sb = new StringBuffer();
      sb.append("\n");
      Finder finder = this.curFinder;

      assert finder != null;

      assert finder.getSQLQuery() != null;

      int queryType = finder.getQueryType();
      Class[] parameterTypes = null;
      List parameterList = null;
      String cmpField;
      if (finder instanceof EjbqlFinder) {
         EjbqlFinder ejbqlFinder = (EjbqlFinder)finder;
         if (ejbqlFinder.isKeyFinder() && ejbqlFinder.getKeyBean().getCMPBeanDescriptor().hasComplexPrimaryKey()) {
            parameterList = new ArrayList();
            RDBMSBean keyBean = ejbqlFinder.getKeyBean();
            CMPBeanDescriptor keyDesc = keyBean.getCMPBeanDescriptor();
            String[] fieldNames = (String[])((String[])keyDesc.getPrimaryKeyFieldNames().toArray(new String[0]));
            parameterTypes = new Class[fieldNames.length];

            for(int i = 0; i < parameterTypes.length; ++i) {
               parameterTypes[i] = keyDesc.getFieldClass(fieldNames[i]);
               cmpField = "param0." + fieldNames[i];
               ParamNode pNode = new ParamNode(keyBean, cmpField, i + 1, parameterTypes[i], "", "", false, false, (Class)null, false, RDBMSUtils.isOracleNLSDataType(keyBean, fieldNames[i]));
               ((List)parameterList).add(pNode);
            }
         } else {
            parameterList = finder.getInternalQueryAndInEntityParmList();
         }
      }

      if (debugLogger.isDebugEnabled()) {
         debug("\n  prepStmt setXX processing, parameterList is: " + parameterList);
      }

      int index = 0;
      Iterator it = ((List)parameterList).iterator();

      while(true) {
         while(true) {
            while(it.hasNext()) {
               ParamNode n = (ParamNode)it.next();
               ++index;
               ParamNode subN;
               String pkFieldName;
               Class pkFieldClass;
               String paramName;
               String pkVarName;
               Class pkClass;
               String pkClassName;
               Iterator subIt;
               if (n.isBeanParam()) {
                  paramName = n.getParamName();
                  pkVarName = this.varPrefix() + paramName + "_PK";
                  pkClass = n.getPrimaryKeyClass();
                  pkClassName = pkClass.getName();
                  sb.append(pkClassName + " " + pkVarName + ";" + EOL);
                  sb.append(pkVarName + " = (" + pkClassName + ")" + paramName + ".getPrimaryKey();" + EOL + EOL);
                  if (n.hasCompoundKey()) {
                     subIt = n.getParamSubList().iterator();

                     while(subIt.hasNext()) {
                        subN = (ParamNode)subIt.next();
                        pkFieldName = this.varPrefix() + paramName + "_PK." + subN.getId();
                        pkFieldClass = subN.getParamClass();
                        this.writePrepStmtSet(sb, index, pkFieldName, pkFieldClass, subN.isOracleNLSDataType());
                        if (subIt.hasNext()) {
                           ++index;
                        }
                     }
                  } else {
                     this.writePrepStmtSet(sb, index, pkVarName, pkClass, n.isOracleNLSDataType());
                  }
               } else if (n.isSelectInEntity()) {
                  if (queryType != 5 && queryType != 3) {
                     paramName = n.getParamName();
                     pkVarName = this.varPrefix() + paramName + "_PK";
                     pkClass = n.getPrimaryKeyClass();
                     pkClassName = pkClass.getName();
                     sb.append(pkClassName).append(" ").append(pkVarName).append(";");
                     sb.append(EOL);
                     sb.append(pkVarName).append(" = ");
                     sb.append("(").append(pkClassName).append(")");
                     sb.append(paramName).append(";");
                     sb.append(EOL);
                     if (n.hasCompoundKey()) {
                        subIt = n.getParamSubList().iterator();

                        while(subIt.hasNext()) {
                           subN = (ParamNode)subIt.next();
                           pkFieldName = this.varPrefix() + paramName + "_PK." + subN.getId();
                           pkFieldClass = subN.getParamClass();
                           this.writePrepStmtSet(sb, index, pkFieldName, pkFieldClass, subN.isOracleNLSDataType());
                           if (subIt.hasNext()) {
                              ++index;
                           }
                        }
                     } else {
                        this.writePrepStmtSet(sb, index, pkVarName, pkClass, n.isOracleNLSDataType());
                     }
                  } else {
                     Iterator subIt = n.getParamSubList().iterator();

                     while(subIt.hasNext()) {
                        ParamNode subN = (ParamNode)subIt.next();
                        if (debugLogger.isDebugEnabled()) {
                           debug(" process Select In Entity returning Field  param node: " + subN.toString());
                        }

                        cmpField = subN.getId();
                        Class pClass = subN.getParamClass();
                        if (pClass == null) {
                           Loggable l = EJBLogger.logCouldNotGetClassForParamLoggable(this.curFinder.getName(), cmpField);
                           throw new EJBCException(l.getMessageText());
                        }

                        String localFieldName = this.varPrefix() + cmpField;
                        sb.append(pClass.getName());
                        sb.append(" ");
                        sb.append(localFieldName + " = ");
                        sb.append(MethodUtils.getMethodName(cmpField) + "();");
                        sb.append(EOL);
                        this.writePrepStmtSet(sb, index, localFieldName, pClass, subN.isOracleNLSDataType());
                     }
                  }
               } else {
                  if (debugLogger.isDebugEnabled()) {
                     debug(" process param node: " + n.toString());
                  }

                  this.writePrepStmtSet(sb, index, n.getParamName(), n.getParamClass(), n.isOracleNLSDataType());
               }
            }

            return sb.toString();
         }
      }
   }

   private void writePrepStmtSet(StringBuffer sb, int pos, String fName, Class fClass, boolean isOracleNLSDataType) throws EJBCException {
      if (!fClass.isPrimitive()) {
         this.addNullCheck(sb, fName, ClassUtils.getSQLTypeForClass(fClass), pos);
      }

      this.preparedStatementBindingBody(sb, fName, fName, fClass, String.valueOf(pos), false, false, false, false, isOracleNLSDataType);
      if (!fClass.isPrimitive()) {
         sb.append("}" + EOL);
      }

   }

   private void addNullCheck(StringBuffer sb, String value, String fieldName, String paramIdx, String stmtVar) {
      sb.append("if(!" + this.pmVar() + ".setParamNull(" + stmtVar + ", " + paramIdx + ", " + value + ", \"" + fieldName + "\")) {" + EOL);
   }

   private void addNullCheck(StringBuffer sb, String value, String sqlType, int variableIndex) {
      this.addNullCheck(sb, value, sqlType, variableIndex, this.stmtVar());
   }

   private void addNullCheck(StringBuffer sb, String value, String sqlType, int variableIndex, String stmtVar) {
      sb.append("if (" + value + " == null) {" + EOL);
      sb.append(stmtVar + ".setNull(" + variableIndex + "," + sqlType + ");" + EOL);
      sb.append("} else {" + EOL);
   }

   private String getCmpField(String fieldName) {
      return this.getCmpField("this", fieldName);
   }

   private String getCmpField(String target, String fieldName) {
      return this.bd.isBeanClassAbstract() ? target + "." + fieldName : target + "." + "__WL_super_" + MethodUtils.getMethodName(fieldName) + "()";
   }

   private String setCmpField(String fieldName, String value) {
      return this.bd.isBeanClassAbstract() ? "this." + fieldName + " = " + value : "__WL_super_" + MethodUtils.setMethodName(fieldName) + "(" + value + ")";
   }

   public String assignPkFieldsToPkVar() {
      StringBuffer sb = new StringBuffer();
      Iterator pkFieldNames = this.bd.getPrimaryKeyFieldNames().iterator();
      if (this.bd.hasComplexPrimaryKey()) {
         while(pkFieldNames.hasNext()) {
            String fname = (String)pkFieldNames.next();
            sb.append(this.pkVar() + "." + fname + " = " + this.getCmpField(fname) + ";" + EOL);
         }
      } else {
         sb.append(this.pkVar() + " = " + this.getCmpField((String)pkFieldNames.next()) + ";");
      }

      return sb.toString();
   }

   public String assignPkVarToPkFields() {
      StringBuffer sb = new StringBuffer();
      Iterator pkFieldNames = this.bd.getPrimaryKeyFieldNames().iterator();
      if (this.bd.hasComplexPrimaryKey()) {
         while(pkFieldNames.hasNext()) {
            String fname = (String)pkFieldNames.next();
            sb.append(this.setCmpField(fname, this.pkVar() + "." + fname) + ";" + EOL);
         }
      } else {
         sb.append(this.setCmpField((String)pkFieldNames.next(), this.pkVar()) + ";");
      }

      return sb.toString();
   }

   public String assignResultVar() throws CodeGenerationException {
      assert this.curFinder != null;

      StringBuffer sb = new StringBuffer();
      boolean maybeFilterDuplicates = this.curFinder.isMultiFinder() && (this.curFinder.isSetFinder() || this.curFinder.isSelectDistinct());
      if (this.curFinder.finderLoadsBean()) {
         boolean isRelationshipCaching = false;
         String cmrFieldName;
         if (this.curFinder instanceof EjbqlFinder) {
            EjbqlFinder ejbqlFinder = (EjbqlFinder)this.curFinder;
            cmrFieldName = ejbqlFinder.getGroupName();
            this.curGroup = this.bean.getFieldGroup(cmrFieldName);
            String cachingName = ejbqlFinder.getCachingName();
            if (cachingName != null) {
               this.curRelationshipCaching = this.bean.getRelationshipCaching(cachingName);
               if (this.curRelationshipCaching != null) {
                  isRelationshipCaching = this.curRelationshipCaching.getCachingElements().iterator().hasNext();
               }
            }
         }

         sb.append(EOL);
         sb.append("Integer " + this.offsetIntObjVar() + " = new Integer(0);" + EOL);
         sb.append("Object " + this.pkVar() + " = " + this.getPKFromRSMethodName() + this.getPKFromRSMethodParams() + EOL);
         sb.append(this.eoVar() + " = null;" + EOL);
         sb.append("if (" + this.pkVar() + " != null) { " + EOL);
         if (isRelationshipCaching) {
            sb.append("if (!" + this.pkMapVar() + ".containsKey(" + this.pkVar() + ")) {" + EOL);
         } else if (maybeFilterDuplicates) {
            if (!this.bean.getUseSelectForUpdate()) {
               sb.append("if (updateLockType == ").append("DDConstants.UPDATE_LOCK_NONE || ").append("updateLockType == ").append("DDConstants.UPDATE_LOCK_AS_GENERATED || ").append("(!" + this.pkMapVar() + ".containsKey(" + this.pkVar() + "))) {");
            } else {
               sb.append("if ").append("(!" + this.pkMapVar() + ".containsKey(" + this.pkVar() + ")) {");
            }

            sb.append(EOL);
         }

         sb.append(this.perhapsLoadPKsForQueryCaching());
         sb.append(this.getBeanFromRS());
         sb.append(this.eoVar() + " = " + this.pmVar() + ".finderGetEoFromBeanOrPk(" + this.beanVar() + ", " + this.pkVar() + ", __WL_getIsLocal());" + EOL);
         sb.append("if (" + this.debugEnabled() + ") " + this.debugSay() + "(\"bean after finder load: \" + ((__WL_bean == null) ? \"null\" : __WL_bean.hashCode()));" + EOL);
         if (this.curFinder.isMultiFinder()) {
            sb.append("if( " + this.beanVar() + " == null || ( " + this.beanVar() + " != null && !" + this.beanVar() + ".__WL_getIsRemoved()))" + EOL);
            sb.append("{" + EOL);
            if (this.curFinder.isSetFinder()) {
               sb.append(this.orderedSetVar() + ".add(" + this.eoVar() + ");" + EOL);
            } else {
               sb.append(this.colVar() + ".add(" + this.eoVar() + ");" + EOL);
            }

            sb.append("}" + EOL);
         }

         sb.append("Object __WL_retVal = " + this.pkMapVar() + ".put(" + this.pkVar() + ", " + this.beanVar() + ");" + EOL);
         if (isRelationshipCaching) {
            Iterator all = this.bean.getCmrFieldNames().iterator();

            while(all.hasNext()) {
               cmrFieldName = (String)all.next();
               sb.append(this.beanVar() + "." + CodeGenUtils.fieldVarName(cmrFieldName) + " = null;" + EOL);
            }
         }

         if (!isRelationshipCaching && !this.curFinder.isMultiFinder()) {
            sb.append("if (__WL_retVal!=null) " + this.isMultiVar() + "=true;" + EOL);
         }

         if (isRelationshipCaching) {
            sb.append("} else {" + EOL);
            sb.append(this.beanVar() + " = (" + this.getGeneratedBeanClassName() + ") " + this.pkMapVar() + ".get(" + this.pkVar() + ");" + EOL);
            sb.append(this.eoVar() + " = " + this.pmVar() + ".finderGetEoFromBeanOrPk(" + this.beanVar() + ", " + this.pkVar() + ", __WL_getIsLocal());" + EOL);
            sb.append("}" + EOL);
         } else if (maybeFilterDuplicates) {
            sb.append("}").append(EOL);
         }

         if (this.curFinder.isMultiFinder()) {
            sb.append("} else {").append(EOL);
            sb.append("if (").append(this.pmVar()).append(".isFindersReturnNulls()) {").append(EOL);
            if (this.curFinder.isSetFinder()) {
               sb.append(this.orderedSetVar()).append(".add(null);").append(EOL);
            } else {
               sb.append(this.colVar()).append(".add(null);").append(EOL);
            }

            sb.append("}");
         } else if (!this.curFinder.isFindByPrimaryKey()) {
            sb.append("} else {").append(EOL);
            sb.append("if (").append(this.pmVar()).append(".isFindersReturnNulls()) {").append(EOL);
            sb.append(this.pkMapVar()).append(".put(null, null); ").append(EOL);
            sb.append("}");
         }

         sb.append("}").append(EOL).append(EOL);
         if (isRelationshipCaching) {
            sb.append(this.invokeEagerCachingMethods() + EOL);
         }

         this.curGroup = null;
      } else {
         if (this.bd.hasComplexPrimaryKey()) {
            sb.append(this.pkVar()).append(" = new ");
            sb.append(this.pk_class()).append("();").append(EOL);
         }

         this.assignToVars(sb, this.pkVar(), this.bd.hasComplexPrimaryKey(), this.pkFieldNames, 1, false);
         if (maybeFilterDuplicates) {
            if (!this.bean.getUseSelectForUpdate()) {
               sb.append("if (updateLockType == ").append("DDConstants.UPDATE_LOCK_NONE || ").append("updateLockType == ").append("DDConstants.UPDATE_LOCK_AS_GENERATED || ").append("(!" + this.pkMapVar() + ".containsKey(" + this.pkVar() + "))) {");
            } else {
               sb.append("if ").append("(!" + this.pkMapVar() + ".containsKey(" + this.pkVar() + ")) {");
            }
         }

         sb.append(this.perhapsLoadPKsForQueryCaching());
         if (this.curFinder.isMultiFinder()) {
            sb.append("if (").append(this.rsVar()).append(".wasNull()) {").append(EOL);
            sb.append("if (").append(this.pmVar()).append(".isFindersReturnNulls()) {").append(EOL);
            sb.append(this.pkVar()).append(" = null;").append(EOL);
            if (this.curFinder.isSetFinder()) {
               sb.append(this.orderedSetVar() + ".add(" + this.pkVar() + ");" + EOL);
            } else {
               sb.append(this.colVar() + ".add(" + this.pkVar() + ");").append(EOL);
            }

            sb.append("}").append(EOL);
            sb.append("}").append(EOL);
            sb.append("else {").append(EOL);
            if (this.curFinder.isSetFinder()) {
               sb.append(this.orderedSetVar() + ".add(" + this.pkVar() + ");" + EOL);
            } else {
               sb.append(this.colVar() + ".add(" + this.pkVar() + ");").append(EOL);
            }

            sb.append("}").append(EOL);
         } else if (!this.curFinder.isFindByPrimaryKey()) {
            sb.append("if (").append(this.rsVar()).append(".wasNull()) {").append(EOL);
            sb.append(this.pkVar()).append(" = null;").append(EOL);
            sb.append("if (").append(this.pmVar()).append(".isFindersReturnNulls()) {").append(EOL);
            sb.append(this.pkMapVar()).append(".put(null, null);").append(EOL);
            sb.append("}").append(EOL);
            sb.append("}").append(EOL);
         }

         sb.append("if (" + this.pkVar() + " != null) {");
         sb.append("Object __WL_retVal = " + this.pkMapVar() + ".put(" + this.pkVar() + ", " + this.pkVar() + ");" + EOL);
         if (!this.curFinder.isMultiFinder()) {
            sb.append("if (__WL_retVal!=null) " + this.isMultiVar() + "=true;" + EOL);
         }

         sb.append("}" + EOL);
         if (maybeFilterDuplicates) {
            sb.append("}").append(EOL);
         }
      }

      return sb.toString();
   }

   public String invokeLoadGroupMethod() {
      StringBuffer sb = new StringBuffer();
      sb.append(this.loadGroupFromRSMethodName(this.curGroup) + "(" + this.rsVar() + ", " + this.offsetIntObjVar() + ", " + this.pkVar() + ", " + this.beanVar() + ");" + EOL);
      return sb.toString();
   }

   private String perhapsLoadPKsForQueryCaching() {
      StringBuffer sb = new StringBuffer("");
      if (this.shouldImplementQueryCaching(this.curFinder)) {
         sb.append("// Load PK for query-caching - start").append(EOL);
         sb.append("QueryCacheElement ").append(this.queryCacheElementVar());
         sb.append(" = null;").append(EOL);
         sb.append("if (").append(this.pkVar()).append(" == null && ");
         sb.append(this.pmVar()).append(".isFindersReturnNulls()) {").append(EOL);
         sb.append(this.queryCacheElementVar()).append(" = new QueryCacheElement(");
         sb.append("null);").append(EOL);
         sb.append("} else if (").append(this.pkVar()).append(" != null) {");
         sb.append(EOL);
         sb.append(this.queryCacheElementVar()).append(" = new QueryCacheElement(");
         sb.append(this.pkVar()).append(", (TTLManager)").append(this.pmVar());
         sb.append(".getBeanManager());").append(EOL);
         sb.append("}").append(EOL);
         sb.append("if (").append(this.queryCacheElementVar()).append(" != null) {");
         sb.append(EOL);
         if (this.curFinder.isMultiFinder()) {
            sb.append(this.queryCacheElementsVar()).append(".add(");
            sb.append(this.queryCacheElementVar()).append(");").append(EOL);
         } else {
            sb.append(this.queryCacheElementsVar()).append(" = ");
            sb.append(this.queryCacheElementVar()).append(";").append(EOL);
         }

         sb.append("}").append(EOL);
         sb.append("// Load PK for query-caching - end").append(EOL);
      }

      return sb.toString();
   }

   private Class getVariableClass(String variable) {
      return (Class)this.variableToClass.get(variable);
   }

   private String checkFieldNotModifiedOrLoaded(String targetName, String fieldName) {
      StringBuffer sb = new StringBuffer();
      sb.append("if (!(" + targetName + "." + this.isLoadedVar() + "[" + this.bean.getIsModifiedIndex(fieldName) + "]");
      if (!this.bd.isReadOnly() || this.bean.allowReadonlyCreateAndRemove()) {
         sb.append(" || " + targetName + "." + this.isModifiedVar() + "[" + this.bean.getIsModifiedIndex(fieldName) + "]");
      }

      sb.append(")) {" + EOL);
      return sb.toString();
   }

   private void assignToVars(StringBuffer sb, String targetName, boolean targetIsCompound, List vars, int index, boolean assigningToBean) throws CodeGenerationException {
      Iterator iter = vars.iterator();

      while(true) {
         while(iter.hasNext()) {
            String var = (String)iter.next();
            Class type = this.getVariableClass(var);
            boolean assignUsingMethod = assigningToBean && !this.bd.isBeanClassAbstract() && this.bean.hasCmpField(var);
            String value = null;
            if (targetIsCompound) {
               value = targetName + "." + var;
            } else {
               value = targetName;
            }

            boolean isCharArrayMappedToString = this.bean.isCharArrayMappedToString(type);
            if (isCharArrayMappedToString) {
               type = Character.TYPE;
            }

            String getResult = this.getFromResultSet(index, type, this.bean.getCmpColumnTypeForField(var));
            if (assigningToBean) {
               sb.append(this.checkFieldNotModifiedOrLoaded(targetName, (String)this.variableToField.get(var)));
            }

            if (this.bean.hasCmpColumnType(var) && !"SybaseBinary".equals(this.bean.getCmpColumnTypeForField(var)) && !"LongString".equals(this.bean.getCmpColumnTypeForField(var)) && !"NChar".equals(this.bean.getCmpColumnTypeForField(var)) && !"NVarchar2".equals(this.bean.getCmpColumnTypeForField(var)) && !"SQLXML".equals(this.bean.getCmpColumnTypeForField(var))) {
               if (!this.bean.isBlobCmpColumnTypeForField(var) && !this.bean.isClobCmpColumnTypeForField(var)) {
                  throw new AssertionError("Unrecognized Cmp Column Type " + this.bean.getCmpColumnTypeForField(var) + " for field " + var);
               }

               this.curField = var;
               sb.append("\n" + this.setBlobClobForInputMethodName() + "(" + this.rsVar() + "," + targetName + ");");
               if (assigningToBean) {
                  sb.append("}" + EOL);
               }

               ++index;
            } else {
               boolean isSQLXMLMappedIntoString = false;
               if (this.bean.hasCmpColumnType(var) && "SQLXML".equals(this.bean.getCmpColumnTypeForField(var))) {
                  if (type != String.class) {
                     throw new CodeGenerationException("only String can be mapped into SQLXML : " + var);
                  }

                  isSQLXMLMappedIntoString = true;
               }

               String rhs;
               if (!this.bean.isValidSQLType(type)) {
                  sb.append("byte[] byteArray = " + getResult + ";" + EOL);
                  sb.append("if (" + this.debugEnabled() + ") {" + EOL);
                  sb.append(this.debugSay() + "(\"returned bytes\" + byteArray );" + EOL);
                  sb.append("if (byteArray!=null) {" + EOL);
                  sb.append(this.debugSay() + "(\"length- \" + byteArray.length);" + EOL);
                  sb.append("}" + EOL);
                  sb.append("}" + EOL);
                  sb.append("if (byteArray==null || byteArray.length==0) { " + EOL);
                  this.doSimpleAssignment(sb, value, var, "null", assignUsingMethod);
                  if (assigningToBean && this.bd.isOptimistic() && this.doSnapshot(var)) {
                     sb.append(targetName + "." + CodeGenUtils.snapshotNameForVar(var) + " = null;" + EOL);
                  }

                  sb.append("}" + EOL);
                  rhs = "(" + this.javaCodeForType(type) + ")";
                  sb.append("else { " + EOL);
                  if (assigningToBean && this.bd.isOptimistic() && this.doSnapshot(var)) {
                     sb.append(targetName + "." + CodeGenUtils.snapshotNameForVar(var) + " = byteArray;" + EOL);
                  }

                  sb.append("ByteArrayInputStream bstr = new java.io.ByteArrayInputStream(byteArray);" + EOL);
                  sb.append("RDBMSObjectInputStream ostr = new RDBMSObjectInputStream(bstr, " + this.classLoaderVar() + ");" + EOL);
                  if (EJBHome.class.isAssignableFrom(type)) {
                     sb.append("HomeHandle handle = (HomeHandle)ostr.readObject();" + EOL);
                     this.doSimpleAssignment(sb, value, var, rhs + "handle.getEJBHome();", assignUsingMethod);
                  } else if (EJBObject.class.isAssignableFrom(type)) {
                     sb.append("Handle handle = (Handle)ostr.readObject();" + EOL);
                     this.doSimpleAssignment(sb, value, var, rhs + "handle.getEJBObject();", assignUsingMethod);
                  } else {
                     this.doSimpleAssignment(sb, value, var, rhs + "ostr.readObject();", assignUsingMethod);
                  }

                  sb.append("}" + EOL);
               } else {
                  rhs = this.resultSetToVariable(index, type, this.bean.getCmpColumnTypeForField(var));
                  if (isCharArrayMappedToString) {
                     sb.append("String " + this.stringVar(index) + " = " + rhs + ";" + EOL);
                     sb.append("if (" + this.stringVar(index) + "==null ) { " + EOL);
                     this.doSimpleAssignment(sb, value, var, "null", assignUsingMethod);
                     sb.append("}" + EOL);
                     sb.append("else { " + EOL);
                     sb.append(this.trimStringTypedValue(this.stringVar(index)));
                     this.doSimpleAssignment(sb, value, var, this.stringVar(index) + ".toCharArray();", assignUsingMethod);
                     sb.append("}" + EOL);
                  } else if (type != Character.class && type != Character.TYPE) {
                     if (type == Date.class) {
                        sb.append("java.sql.Timestamp " + this.sqlTimestampVar(index) + " = " + rhs + ";" + EOL);
                        sb.append("if (" + this.sqlTimestampVar(index) + "==null) { " + EOL);
                        this.doSimpleAssignment(sb, value, var, ClassUtils.getDefaultValue(type), assignUsingMethod);
                        sb.append("}" + EOL);
                        sb.append("else { " + EOL);
                        this.doSimpleAssignment(sb, value, var, "new java.util.Date(" + this.sqlTimestampVar(index) + ".getTime())", assignUsingMethod);
                        sb.append("}" + EOL);
                     } else {
                        String lhs;
                        if (type == String.class) {
                           lhs = null;
                           if (assignUsingMethod) {
                              lhs = this.stringVar(index);
                              sb.append("String ");
                           } else {
                              lhs = value;
                           }

                           if (isSQLXMLMappedIntoString) {
                              rhs = rhs + " != null ? " + rhs + ".getString() : null";
                           }

                           sb.append(lhs + " = " + rhs + ";" + EOL);
                           sb.append("if(");
                           sb.append(lhs);
                           sb.append(" != null) {");
                           sb.append(EOL);
                           sb.append(this.trimStringTypedValue(lhs));
                           sb.append("}");
                           sb.append(EOL);
                           if (assignUsingMethod) {
                              sb.append(MethodUtils.setMethodName(var) + "(" + lhs + ");" + EOL);
                           }
                        } else if (type != BigDecimal.class && type != java.sql.Date.class && type != Timestamp.class) {
                           this.doAssignment(sb, value, index, var, type, rhs, assignUsingMethod);
                        } else {
                           lhs = null;
                           if (assignUsingMethod) {
                              lhs = this.tempVar(index);
                              sb.append(type.getName()).append(" ");
                           } else {
                              lhs = value;
                           }

                           sb.append(lhs + " = " + rhs + ";" + EOL);
                           if (assignUsingMethod) {
                              sb.append(MethodUtils.setMethodName(var) + "(" + lhs + ");" + EOL);
                           }
                        }
                     }
                  } else {
                     sb.append("String " + this.stringVar(index) + " = " + rhs + ";" + EOL);
                     sb.append("if (" + this.stringVar(index) + "==null || " + this.stringVar(index) + ".length()==0) { " + EOL);
                     this.doSimpleAssignment(sb, value, var, ClassUtils.getDefaultValue(type), assignUsingMethod);
                     sb.append("}" + EOL);
                     sb.append("else { " + EOL);
                     if (type == Character.class) {
                        this.doSimpleAssignment(sb, value, var, "new Character(" + this.stringVar(index) + ".charAt(0))", assignUsingMethod);
                     } else {
                        this.doSimpleAssignment(sb, value, var, this.stringVar(index) + ".charAt(0);", assignUsingMethod);
                     }

                     sb.append("}" + EOL);
                  }

                  if (assigningToBean && this.bd.isOptimistic() && this.doSnapshot(var)) {
                     sb.append(this.takeSnapshotForVar(targetName, var, assignUsingMethod));
                  }
               }

               if (assigningToBean) {
                  sb.append("}" + EOL);
                  sb.append("else {" + EOL);
                  sb.append(getResult + ";" + EOL);
                  sb.append("}" + EOL);
               }

               ++index;
            }
         }

         return;
      }
   }

   private void doAssignment(StringBuffer sb, String value, int index, String var, Class type, String rhs, boolean assignUsingMethod) {
      if (assignUsingMethod) {
         if (type.isPrimitive()) {
            sb.append("__WL_super_");
            sb.append(MethodUtils.setMethodName(var) + "(" + rhs + ");" + EOL);
         } else {
            sb.append(this.javaCodeForType(type) + " " + this.tempVar(index) + " = " + rhs + ";" + EOL);
            sb.append("if (" + this.rsVar() + ".wasNull()) { " + EOL);
            sb.append("__WL_super_");
            sb.append(MethodUtils.setMethodName(var) + "(null);" + EOL);
            sb.append("} else {" + EOL);
            sb.append("__WL_super_");
            sb.append(MethodUtils.setMethodName(var) + "(" + this.tempVar(index) + ");" + EOL);
            sb.append("}" + EOL);
         }
      } else {
         sb.append(value + " = " + rhs + ";" + EOL);
         if (!type.isPrimitive()) {
            sb.append("if (" + this.rsVar() + ".wasNull()) { " + EOL);
            sb.append(value + " = null;" + EOL);
            sb.append("}" + EOL);
         }
      }

   }

   private void doSimpleAssignment(StringBuffer sb, String value, String var, String rhs, boolean assignUsingMethod) {
      if (assignUsingMethod) {
         sb.append("__WL_super_");
         sb.append(MethodUtils.setMethodName(var) + "(" + rhs + ");" + EOL);
      } else {
         sb.append(value + " = " + rhs + ";" + EOL);
      }

   }

   private String resultSetToVariable(int idx, Class type, String columnType) {
      String cast = "";
      String value = this.getFromResultSet(idx, type, columnType);
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
            return cast + value;
         }
      } else {
         return "(java.lang.String)" + value;
      }
   }

   private String getFromResultSet(int idx, Class type, String columnType) {
      String postfix;
      if (columnType != null && "SQLXML".equals(columnType)) {
         postfix = "SQLXML";
      } else {
         postfix = MethodUtils.getMethodPostfix(type);
      }

      return this.rsVar() + ".get" + postfix + "(" + idx + "+" + this.offsetVar() + ")";
   }

   public String indexForTable() {
      return String.valueOf(this.bean.tableIndex(this.curTable));
   }

   public String updateBeanColumnsSqlForTable(String tName) {
      List nonKeyList = this.bean.getNonPKFields(this.bean.tableIndex(tName));
      String[] nonKeyFieldNames = (String[])nonKeyList.toArray(new String[0]);
      return this.attrsAsColumnsAsParamsForTable(tName, nonKeyFieldNames, ", ");
   }

   public String idParamsSqlForCurTable() {
      return this.idParamsSqlForTable(this.curTableName);
   }

   public String idParamsSqlForTable(String tName) {
      String[] keyFieldNames = (String[])((String[])this.pkFieldNames.toArray(new String[0]));
      return this.attrsAsColumnsAsParamsForTable(tName, keyFieldNames, " AND ");
   }

   public String idParamsSql() {
      String[] keyFieldNames = (String[])((String[])this.pkFieldNames.toArray(new String[0]));

      assert keyFieldNames != null;

      return this.attrsAsColumnsAsParams(keyFieldNames, " AND ");
   }

   public String idColumnsSql() {
      String[] keyFieldNames = (String[])((String[])this.pkFieldNames.toArray(new String[0]));

      assert keyFieldNames != null;

      return StringUtils.join(keyFieldNames, ", ");
   }

   public String groupSqlNonFinder() throws CodeGenerationException {
      try {
         return EjbqlFinder.generateGroupSQLNonFinder(this.bean, this.curGroup.getName(), 0);
      } catch (Exception var2) {
         throw new CodeGenerationException("Internal Error while attempting to generate an Internal Finder for FieldGroup: '" + this.curGroup.getName());
      }
   }

   public String groupSqlNonFinderPerhapsForUpdate() throws CodeGenerationException {
      return this.bean.getUseSelectForUpdate() ? this.groupSqlNonFinderForUpdate() : this.groupSqlNonFinder();
   }

   public String groupSqlNonFinderForUpdate() throws CodeGenerationException {
      try {
         return EjbqlFinder.generateGroupSQLNonFinder(this.bean, this.curGroup.getName(), 1);
      } catch (Exception var2) {
         throw new CodeGenerationException("Internal Error while attempting to generate an Internal Finder for FieldGroup: '" + this.curGroup.getName());
      }
   }

   public String groupSqlNonFinderForUpdateNoWait() throws CodeGenerationException {
      try {
         return EjbqlFinder.generateGroupSQLNonFinder(this.bean, this.curGroup.getName(), 2);
      } catch (Exception var2) {
         throw new CodeGenerationException("Internal Error while attempting to generate an Internal Finder for FieldGroup: '" + this.curGroup.getName());
      }
   }

   public String groupColumnsSql() {
      StringBuffer sb = new StringBuffer();
      Iterator fieldNames = this.curGroup.getCmpFields().iterator();

      String fkFieldName;
      while(fieldNames.hasNext()) {
         String fieldName = (String)fieldNames.next();
         fkFieldName = RDBMSUtils.escQuotedID(this.bean.getCmpColumnForField(fieldName));

         assert fkFieldName != null;

         sb.append(fkFieldName);
         sb.append(", ");
      }

      Iterator fkFieldNames = this.curGroup.getCmrFields().iterator();

      while(true) {
         do {
            if (!fkFieldNames.hasNext()) {
               if (sb.length() > 1) {
                  sb.setCharAt(sb.length() - 2, ' ');
               }

               return sb.toString();
            }

            fkFieldName = (String)fkFieldNames.next();
         } while(this.bean.isForeignCmpField(fkFieldName));

         Iterator fkColumnNames = this.bean.getForeignKeyColNames(fkFieldName).iterator();

         while(fkColumnNames.hasNext()) {
            String columnName = (String)fkColumnNames.next();
            sb.append(RDBMSUtils.escQuotedID(columnName));
            sb.append(", ");
         }
      }
   }

   public int groupColumnCount() {
      int count = 0;

      for(Iterator fieldNames = this.curGroup.getCmpFields().iterator(); fieldNames.hasNext(); ++count) {
         fieldNames.next();
      }

      Iterator fkFieldNames = this.curGroup.getCmrFields().iterator();

      while(true) {
         String fkFieldName;
         do {
            if (!fkFieldNames.hasNext()) {
               return count;
            }

            fkFieldName = (String)fkFieldNames.next();
         } while(this.bean.isForeignCmpField(fkFieldName));

         for(Iterator fkColumnNames = this.bean.getForeignKeyColNames(fkFieldName).iterator(); fkColumnNames.hasNext(); ++count) {
            fkColumnNames.next();
         }
      }
   }

   public String createColumnsQMs() {
      String[] fieldNames = (String[])this.cmpFieldNames.toArray(new String[0]);
      int num = fieldNames.length;
      if (this.bean.genKeyExcludePKColumn()) {
         --num;
      }

      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < num; ++i) {
         sb.append(this.getInsertQuoteStringForField(fieldNames[i]));
         if (i < num - 1) {
            sb.append(", ");
         }
      }

      Iterator fkFieldNames = this.bean.getForeignKeyFieldNames().iterator();

      while(true) {
         String fkFieldName;
         do {
            do {
               if (!fkFieldNames.hasNext()) {
                  return sb.toString();
               }

               fkFieldName = (String)fkFieldNames.next();
            } while(!this.bean.containsFkField(fkFieldName));
         } while(this.bean.isForeignCmpField(fkFieldName));

         Iterator fkColumnNames = this.bean.getForeignKeyColNames(fkFieldName).iterator();
         sb.append(", ");

         while(fkColumnNames.hasNext()) {
            sb.append("?");
            fkColumnNames.next();
            if (fkColumnNames.hasNext()) {
               sb.append(", ");
            }
         }
      }
   }

   public String copyKeyValuesToPkVar() {
      StringBuffer sb = new StringBuffer();
      sb.append(EOL);
      String[] keyFieldNames = (String[])((String[])this.pkFieldNames.toArray(new String[0]));
      int i = 0;

      for(int len = keyFieldNames.length; i < len; ++i) {
         String fieldName = keyFieldNames[i];
         if (ClassUtils.isObjectPrimitive(this.bd.getPrimaryKeyClass())) {
            assert i == 0 : "Too many fields for an object primitive PK class";

            this.bean.getCmpFieldClass(keyFieldNames[i]);
            sb.append(this.pkVar());
            sb.append(" = ");
            sb.append(this.getCmpField(keyFieldNames[i])).append(";");
         } else {
            if (this.bd.hasComplexPrimaryKey()) {
               sb.append(this.pkVar()).append(".").append(fieldName);
            } else {
               sb.append(this.pkVar());
            }

            sb.append(" = ").append(this.getCmpField(fieldName)).append(";");
         }

         sb.append(EOL);
      }

      return sb.toString();
   }

   public String copyBeanToComplexFk_forField() {
      StringBuffer sb = new StringBuffer();
      String fkTable = this.bean.getTableForCmrField(this.curField);
      Iterator colNames = this.bean.getForeignKeyColNames(this.curField).iterator();

      while(colNames.hasNext()) {
         String colName = (String)colNames.next();
         String mappedField = this.bean.getRelatedPkFieldName(this.curField, colName);
         sb.append(this.fkVar()).append(".").append(mappedField);
         sb.append(" = ").append("this").append(".").append(this.bean.variableForField(this.curField, fkTable, colName)).append(";");
         sb.append(EOL);
      }

      return sb.toString();
   }

   public String perhaps_include_result_set_to_collection() throws CodeGenerationException {
      boolean bFoundCollection = true;

      try {
         Class.forName("java.util.Collection");
         bFoundCollection = true;
      } catch (ClassNotFoundException var3) {
         bFoundCollection = false;
      }

      StringBuffer sb = new StringBuffer();
      if (bFoundCollection) {
         sb.append(this.parse(this.getProductionRule("resultSetToCollection")));
      }

      return sb.toString();
   }

   public String perhapsImplementWLStoreMethodBody() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      if (this.bd.isReadOnly() && !this.bean.allowReadonlyCreateAndRemove()) {
         sb.append("throw new AssertionError(\"internal error: ejbStore called ");
         sb.append("for bean '");
         sb.append(this.bd.getEJBName());
         sb.append("' which uses ReadOnly concurrency.\");");
         sb.append(EOL);
      } else {
         sb.append(this.parse(this.getProductionRule("implementWLStoreMethodBody")));
      }

      return sb.toString();
   }

   public String wlStoreToTables() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < this.bean.tableCount(); ++i) {
         this.curTableIndex = i;
         this.curTableName = this.bean.tableAt(this.curTableIndex);
         sb.append(this.parse(this.getProductionRule("implementStoreTable")));
      }

      return sb.toString();
   }

   public String needsStoreOptimisticField() {
      return this.useVersionOrTimestampCheckingForBlobClob(this.curTableName) ? "true" : "false";
   }

   public String iVarIsNotPK() {
      StringBuffer sb = new StringBuffer();
      sb.append("  if (! (");
      Iterator it = this.bean.getIsModifiedIndices_PK().iterator();

      while(it.hasNext()) {
         Integer I = (Integer)it.next();
         sb.append(this.iVar()).append(" == ").append(I.toString());
         if (it.hasNext()) {
            sb.append(" || ");
         }
      }

      sb.append(" ) )  ").append("//  ").append(this.iVar()).append(" does not point to a PK field").append(EOL);
      return sb.toString();
   }

   public String perhapsIVarContinueOnTableCheck() {
      StringBuffer sb = new StringBuffer();
      sb.append(this.iVarIsNotPK());
      sb.append("            if (").append(this.isModifiedToTableMethodName()).append("(").append(this.iVar()).append(") != ");
      sb.append(this.curTableIndex).append(")").append(EOL);
      sb.append("              continue;  // this Field's Column is not in the current Table").append(EOL).append(EOL);
      return sb.toString();
   }

   public String isModifiedIndexToField(int index) {
      return this.bean.getFieldName(index);
   }

   public String isModifiedToTableMethodName() {
      return "isModifiedToTableIndex";
   }

   public String isModifiedToTableMethod() {
      StringBuffer sb = new StringBuffer();
      sb.append("int ").append(this.isModifiedToTableMethodName()).append("(int isModifiedIndex) {").append(EOL);
      sb.append("    switch (isModifiedIndex) {").append(EOL);
      Iterator it = this.bean.getIsModifiedIndexToTableNumber().iterator();

      for(int index = 0; it.hasNext(); ++index) {
         Integer val = (Integer)it.next();
         sb.append("     case ").append(Integer.toString(index)).append(EOL);
         sb.append("       return ").append(val.toString()).append(";").append(EOL).append(EOL);
      }

      sb.append("      default:").append(EOL);
      sb.append("        throw new RuntimeException(");
      sb.append("\" Internal Error attempt to call " + this.isModifiedToTableMethodName() + ", with isModifiedIndex = \"+").append(EOL);
      sb.append("           \" '\"+isModifiedIndex+\"', this exceeds the expected size limit: '" + this.bean.getFieldNameToIsModifiedIndex().size() + "'. \"); ").append(EOL);
      sb.append("    }").append(EOL);
      sb.append("  }").append(EOL);
      sb.append(EOL);
      return sb.toString();
   }

   public String constructModifiedFieldStoreColumnStrings() {
      StringBuffer sb2 = new StringBuffer();
      sb2.append(EOL);
      sb2.append("sb.setLength(0);" + EOL);
      List fkfieldNameList = this.bean.getForeignKeyFieldNames();

      for(int i = 0; i < this.bean.getFieldCount(); ++i) {
         if (!this.bean.getIsModifiedIndices_PK().contains(i)) {
            int tIndex = this.bean.getTableNumber(i);
            if (tIndex == this.curTableIndex) {
               String fieldName = this.isModifiedIndexToField(i);
               Debug.assertion(fieldName != null);
               StringBuffer sb = new StringBuffer();
               String isFkColsNullable = "";
               boolean skip = false;
               if (this.bean.isCmpFieldName(fieldName)) {
                  String colName = this.bean.getColumnForCmpFieldAndTable(fieldName, this.curTableName);
                  if (this.bean.hasOptimisticColumn(this.curTableName) && this.bean.getOptimisticColumn(this.curTableName).equals(colName)) {
                     skip = true;
                  } else {
                     if (fkfieldNameList != null) {
                        String fkFieldName = null;
                        Iterator iterator = fkfieldNameList.iterator();

                        while(iterator.hasNext()) {
                           fkFieldName = (String)iterator.next();
                           if (this.bean.getForeignKeyColNames(fkFieldName).contains(colName)) {
                              isFkColsNullable = this.perhapsIsFkColsNullableCheck(fkFieldName);
                              break;
                           }
                        }
                     }

                     sb.append(colName + " = ? ");
                  }
               } else {
                  Debug.assertion(this.bean.isForeignKeyField(fieldName));
                  if (this.bean.containsFkField(fieldName) && !this.bean.isForeignCmpField(fieldName)) {
                     isFkColsNullable = this.perhapsIsFkColsNullableCheck(fieldName);
                     Iterator fkColumns = this.bean.getForeignKeyColNames(fieldName).iterator();

                     while(fkColumns.hasNext()) {
                        String fkColumn = (String)fkColumns.next();
                        sb.append(fkColumn + " = ? ");
                        if (fkColumns.hasNext()) {
                           sb.append(", ");
                        }
                     }
                  }
               }

               if (!skip) {
                  sb2.append("if (" + this.isModifiedVar() + "[" + i + "]" + isFkColsNullable + ")  {" + EOL);
                  if (!this.bean.isBlobCmpColumnTypeForField(fieldName) && !this.bean.isClobCmpColumnTypeForField(fieldName)) {
                     sb2.append("if (" + this.countVar() + " > 0) sb.append(\", \");" + EOL);
                     sb2.append("sb.append(\"" + sb.toString() + "\");" + EOL);
                     sb2.append(this.countVar() + "++;" + EOL);
                  } else {
                     sb2.append(this.blobClobCountVar() + "++;" + EOL);
                  }

                  sb2.append("}" + EOL + EOL);
               }
            }
         }
      }

      return sb2.toString();
   }

   public String implementStoreUtilities() {
      String[] fieldNames = (String[])this.cmpFieldNames.toArray(new String[0]);
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < fieldNames.length; ++i) {
         sb.append("private void setParam" + fieldNames[i]);
         sb.append("(PreparedStatement " + this.stmtVar() + ", ");
         sb.append("int " + this.numVar() + ", ");
         sb.append(this.bean.getCmpFieldClass(fieldNames[i]).getName() + " ");
         sb.append(fieldNames[i] + ") {" + EOL);
         this.addPreparedStatementBinding(sb, fieldNames[i], fieldNames[i], this.numVar(), true, false, false, false);
         sb.append(EOL);
         sb.append("}" + EOL + EOL);
      }

      return sb.toString();
   }

   public String createUpdateFailureMsg() {
      StringBuffer sb = new StringBuffer("Failed to CREATE Bean.");
      if (this.bean.hasAutoKeyGeneration() && !this.bean.getGenKeyBeforeInsert()) {
         return sb.toString();
      } else {
         sb.append("  Primary Key Value: '\" + ").append(this.pkVar()).append(" + \"'");
         return sb.toString();
      }
   }

   public String createExceptionCheckForDuplicateKey() throws CodeGenerationException {
      return this.bean.hasAutoKeyGeneration() && !this.bean.getGenKeyBeforeInsert() ? "throw se;   // not possible for there to be duplicate key with DBMS Identity Column, skip dup key check." : this.parse(this.getProductionRule("createMethodDuplicateKeyCheck"));
   }

   public String implementLoadIndexedGroupFromRSMethod() {
      StringBuffer sb = new StringBuffer();
      Iterator fieldGroups = this.bean.getFieldGroups().iterator();

      while(fieldGroups.hasNext()) {
         this.curGroup = (FieldGroup)fieldGroups.next();
         sb.append("case " + this.curGroup.getIndex() + ": " + this.loadGroupFromRSMethodName(this.curGroup) + "(rs, offset, " + this.pkVar() + ", (" + this.getGeneratedBeanClassName() + ")eb); break;" + EOL);
      }

      sb.append("default: throw new AssertionError(\"Bad Group index: \"+index);" + EOL);
      return sb.toString();
   }

   public String implementLoadGroupFromRSMethods() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      Iterator fieldGroups = this.bean.getFieldGroups().iterator();

      while(fieldGroups.hasNext()) {
         this.curGroup = (FieldGroup)fieldGroups.next();
         sb.append(EOL);
         sb.append("// loadGroup from ResultSet to bean method for the '" + this.curGroup.getName() + "' group." + EOL);
         sb.append("public void " + this.loadGroupFromRSMethodName(this.curGroup) + EOL + "(java.sql.ResultSet " + this.rsVar() + ", " + EOL + "java.lang.Integer " + this.offsetIntObjVar() + ", " + EOL + "Object " + this.pkVar() + "," + EOL + this.getGeneratedBeanInterfaceName() + " beanIntf)" + EOL);
         sb.append("throws java.sql.SQLException, java.lang.Exception" + EOL);
         sb.append("{" + EOL);
         sb.append(this.getGeneratedBeanClassName());
         sb.append(" ");
         sb.append(this.beanVar());
         sb.append(" = (");
         sb.append(this.getGeneratedBeanClassName());
         sb.append(")beanIntf;");
         sb.append(EOL);
         sb.append("if (" + this.debugEnabled() + ") {" + EOL);
         sb.append(this.debugSay() + "(\"" + this.loadGroupFromRSMethodName(this.curGroup) + "\");" + EOL);
         sb.append("}" + EOL + EOL);
         sb.append(this.assignOffsetVar() + EOL);
         sb.append(this.assignGroupColumnsToBean() + EOL);
         sb.append(this.beanIsLoadedVar() + " = true;" + EOL);
         sb.append(this.perhapsSetTableLoadedVarsForGroup());
         sb.append("}" + EOL);
      }

      return sb.toString();
   }

   public String implementLoadCMRFieldFromRSMethod() {
      StringBuffer sb = new StringBuffer();
      Iterator cmrFields = this.bean.getAllCmrFields().iterator();

      while(cmrFields.hasNext()) {
         String cmrField = (String)cmrFields.next();
         sb.append("if (\"" + cmrField + "\".equalsIgnoreCase(cmrField)) " + this.loadCMRFieldFromRSMethodName(cmrField) + "(rs, offset,(" + this.getGeneratedBeanClassName() + ")eb);" + EOL);
      }

      return sb.toString();
   }

   public String implementLoadCMRFieldFromRSMethods() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();

      for(Iterator cmrFields = this.bean.getAllCmrFields().iterator(); cmrFields.hasNext(); sb.append("}" + EOL)) {
         String cmrField = (String)cmrFields.next();
         sb.append(EOL);
         sb.append("public void " + this.loadCMRFieldFromRSMethodName(cmrField) + EOL + "(java.sql.ResultSet " + this.rsVar() + ", " + EOL + "java.lang.Integer " + this.offsetIntObjVar() + ", " + EOL + this.getGeneratedBeanInterfaceName() + " beanIntf) " + EOL);
         sb.append("throws java.sql.SQLException, java.lang.Exception" + EOL);
         sb.append("{" + EOL);
         sb.append(this.getGeneratedBeanClassName());
         sb.append(" ");
         sb.append(this.beanVar());
         sb.append(" = (");
         sb.append(this.getGeneratedBeanClassName());
         sb.append(")beanIntf;");
         sb.append(EOL);
         sb.append("if (" + this.debugEnabled() + ") {" + EOL);
         sb.append(this.debugSay() + "(\"" + this.loadCMRFieldFromRSMethodName(cmrField) + "\");" + EOL);
         sb.append("}" + EOL + EOL);
         sb.append(this.assignOffsetVar() + EOL);
         RDBMSBean relatedBean = this.bean.getRelatedRDBMSBean(cmrField);
         String relatedCmrField = this.bean.getRelatedFieldName(cmrField);
         if (this.lhsBeanHasFKForLocal11or1NPath(relatedBean, relatedCmrField)) {
            sb.append(this.assignCMRFieldPKColumns(this.beanVar()) + EOL);
         } else {
            sb.append(this.assignCMRFieldFKColumns(this.beanVar(), cmrField) + EOL);
         }
      }

      return sb.toString();
   }

   public String assignRSToPkVar() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      List variables = new ArrayList(this.bd.getPrimaryKeyFieldNames());
      if (this.bd.hasComplexPrimaryKey()) {
         this.assignToVars(sb, this.pkVar(), true, variables, 1, false);
      } else {
         this.assignToVars(sb, this.pkVar(), false, variables, 1, false);
      }

      return sb.toString();
   }

   public String genIsPKNull() {
      StringBuffer sb = new StringBuffer();
      if (this.bd.hasComplexPrimaryKey()) {
         Iterator pkFieldNames = this.bd.getPrimaryKeyFieldNames().iterator();

         while(pkFieldNames.hasNext()) {
            String fname = (String)pkFieldNames.next();
            Class type = this.getVariableClass(fname);
            if (!type.isPrimitive()) {
               sb.append("if (" + this.pkVar() + "." + fname + " == null) return null;" + EOL);
            }
         }
      }

      return sb.toString();
   }

   public String invokeEagerCachingMethods() {
      StringBuffer sb = new StringBuffer();
      sb.append("int increment = " + this.getGroupColumnCount(this.bean, this.curGroup.getName()) + ";" + EOL);
      Iterator cachingElements = this.curRelationshipCaching.getCachingElements().iterator();
      this.invokeEagerCachingMethodsForCachingElements(this.bean, cachingElements, (String)null, sb, false);
      return sb.toString();
   }

   public void invokeEagerCachingMethodsForCachingElements(RDBMSBean prevBean, Iterator cachingElements, String cmrFieldPath, StringBuffer sb, boolean finderIsDynamic) {
      while(cachingElements.hasNext()) {
         RelationshipCaching.CachingElement cachingElement = (RelationshipCaching.CachingElement)cachingElements.next();
         String cachingElementCmrField = cachingElement.getCmrField();
         String cachingElementGroupName = cachingElement.getGroupName();
         this.setCurrCachingElementCmrField(cachingElementCmrField);
         RDBMSBean cachingElementBean = prevBean.getRelatedRDBMSBean(cachingElementCmrField);
         String cachingElementBmName = this.bmVar((String)this.declaredManagerVars.get(cachingElementBean.getEjbName()));
         CMPBeanDescriptor rbd = (CMPBeanDescriptor)this.beanMap.get(cachingElementBean.getEjbName());
         String genBeanName = rbd.getGeneratedBeanInterfaceName();
         sb.append("// load " + cachingElementCmrField + " bean from RS" + EOL);
         sb.append(this.offsetIntObjVar() + " = new Integer(increment);" + EOL);
         sb.append("Object " + this.cmrFieldPKVar(cmrFieldPath) + " = " + this.cmrFieldBeanVar() + "_pooledBean." + this.getPKFromRSMethodName() + "Instance" + this.getPKFromRSMethodParams() + EOL);
         sb.append(genBeanName + " " + this.cmrFieldBeanVar() + " = null;" + EOL);
         sb.append(this.eoRCVar() + " = null;" + EOL);
         sb.append("if (" + this.cmrFieldPKVar(cmrFieldPath) + " != null) {" + EOL);
         sb.append("if (!" + this.cmrFieldPKMapVar(cmrFieldPath) + ".containsKey(" + this.cmrFieldPKVar(cmrFieldPath) + ")) {" + EOL);
         String cmrFieldName = prevBean.getRelatedFieldName(cachingElementCmrField);
         int cmrFieldOffset = this.getGroupColumnCount(cachingElementBean, cachingElementGroupName);
         sb.append("RSInfo " + this.rsInfoVar() + " = new RSInfoImpl(" + this.rsVar() + ", " + this.loadGroupFromRSIndex(cachingElementBean, cachingElementGroupName) + ", increment, \"" + cmrFieldName + "\", increment + " + cmrFieldOffset + ", " + this.cmrFieldPKVar(cmrFieldPath) + ");" + EOL);
         sb.append(this.cmrFieldBeanVar() + " = (" + genBeanName + ") " + cachingElementBmName + ".getBeanFromRS(" + this.cmrFieldPKVar(cmrFieldPath) + ", " + this.rsInfoVar() + ");" + EOL);
         if (debugLogger.isDebugEnabled()) {
            debug(prevBean.getEjbName() + " is relationship caching " + cachingElementBean.getEjbName() + " through cmrField " + cachingElementCmrField + ", " + cachingElementBean.getEjbName() + " has cmrField " + cmrFieldName);
         }

         int offset;
         if (this.lhsBeanHasFKForLocal11or1NPath(prevBean, cachingElementCmrField)) {
            offset = cmrFieldOffset + prevBean.getForeignKeyColNames(cachingElementCmrField).size();
         } else {
            offset = cmrFieldOffset + cachingElementBean.getForeignKeyColNames(cmrFieldName).size();
         }

         sb.append(this.cmrFieldPKMapVar(cmrFieldPath) + ".put(" + this.cmrFieldPKVar(cmrFieldPath) + ", " + this.cmrFieldBeanVar() + ");" + EOL);
         sb.append(this.eoRCVar() + " = " + cachingElementBmName + ".finderGetEoFromBeanOrPk((javax.ejb.EntityBean)" + this.cmrFieldBeanVar() + ", " + this.cmrFieldPKVar(cmrFieldPath) + ", true);" + EOL);
         String prevCmrFieldName = "";
         if (cmrFieldPath != null) {
            int index = cmrFieldPath.lastIndexOf("_");
            if (index == -1) {
               prevCmrFieldName = cmrFieldPath;
            } else {
               prevCmrFieldName = cmrFieldPath.substring(index + 1);
            }
         }

         sb.append("// set *_isLoaded_ flag even when " + this.eoRCVar() + " is null" + EOL);
         sb.append("if (" + this.cmrFieldBeanVar(prevCmrFieldName) + " != null) {" + EOL);
         sb.append(this.cmrFieldBeanVar(prevCmrFieldName) + "." + CodeGenUtils.cacheRelationshipMethodName(cachingElementCmrField) + "(" + this.eoRCVar() + ");" + EOL);
         String prevBmName;
         if (finderIsDynamic) {
            if (!prevBean.isQueryCachingEnabledForCMRField(cachingElementCmrField)) {
               sb.append("if (qcHandler.isQueryCachingEnabledForFinder()) {");
               sb.append(EOL);
            }

            prevBmName = this.generateCMRFieldFinderMethodName(cachingElementCmrField);
            sb.append("TTLManager roMgr = (TTLManager)");
            sb.append(cachingElementBmName).append(";").append(EOL);
            Class cmrFieldClass = prevBean.getCmrFieldClass(cachingElementCmrField);
            sb.append("QueryCacheKey qckey = new QueryCacheKey(\"");
            sb.append(prevBmName).append("\", new Object[]{");
            sb.append("__WL_getPrimaryKey()").append("}, roMgr, ");
            if (Set.class.isAssignableFrom(cmrFieldClass)) {
               sb.append("QueryCacheKey.RET_TYPE_SET);").append(EOL);
            } else {
               sb.append("QueryCacheKey.RET_TYPE_SINGLETON);").append(EOL);
            }

            sb.append("QueryCacheElement qce = new QueryCacheElement(");
            sb.append("((CMPBean)").append(this.cmrFieldBeanVar());
            sb.append(").__WL_getPrimaryKey(), roMgr);").append(EOL);
            sb.append("qcHandler.addQueryCachingEntry(roMgr, qckey, qce);");
            sb.append(EOL);
            if (!prevBean.isQueryCachingEnabledForCMRField(cachingElementCmrField)) {
               sb.append("}").append(EOL);
            }
         } else if (prevBean.isQueryCachingEnabledForCMRField(cachingElementCmrField) || this.shouldImplementQueryCaching(this.curFinder)) {
            this.currFinderLoadsQueryCachingEnabledCMRFields = true;
            sb.append("if (").append(this.beanMapVar()).append(" == null) {");
            sb.append(EOL);
            sb.append(this.beanMapVar()).append(" = new MultiMap();");
            sb.append(EOL);
            sb.append("}").append(EOL);
            sb.append("if (").append(this.beanMapVar()).append(".get(");
            sb.append(this.cmrFieldBeanVar(prevCmrFieldName)).append(", \"");
            sb.append(cachingElementCmrField).append("\") == null) {");
            sb.append(EOL);
            sb.append(this.beanMapVar()).append(".put(");
            sb.append(this.cmrFieldBeanVar(prevCmrFieldName)).append(", \"");
            sb.append(cachingElementCmrField).append("\");").append(EOL);
            sb.append("}").append(EOL);
         }

         prevBmName = null;
         if (cmrFieldPath != null) {
            prevBmName = this.bmVar((String)this.declaredManagerVars.get(prevBean.getEjbName()));
            sb.append(this.eoRCVar() + " = " + prevBmName + ".finderGetEoFromBeanOrPk((javax.ejb.EntityBean)" + this.cmrFieldBeanVar(prevCmrFieldName) + ", null, true);" + EOL);
         } else {
            sb.append(this.eoRCVar() + " = " + this.eoVar() + ";" + EOL);
         }

         sb.append("} else {" + EOL);
         sb.append(this.eoRCVar() + " = null;" + EOL);
         sb.append("}" + EOL);
         sb.append("if (" + this.cmrFieldBeanVar() + " != null) {" + EOL);
         String relFinderName;
         if (finderIsDynamic) {
            if (!prevBean.relatedFieldIsFkOwner(cachingElementCmrField) || prevBean.isManyToManyRelation(cachingElementCmrField)) {
               if (!cachingElementBean.isQueryCachingEnabledForCMRField(cmrFieldName)) {
                  sb.append("if (qcHandler.isQueryCachingEnabledForFinder()) {");
                  sb.append(EOL);
               }

               String fieldVarName = CodeGenUtils.fieldVarName(cmrFieldName);
               relFinderName = this.generateCMRFieldFinderMethodName(cmrFieldName);
               sb.append("TTLManager roMgr = (TTLManager)");
               if (cmrFieldPath != null) {
                  sb.append(prevBmName).append(";").append(EOL);
               } else {
                  sb.append(this.pmVar()).append(".getBeanManager();").append(EOL);
               }

               Class cmrFieldClass = cachingElementBean.getCmrFieldClass(cmrFieldName);
               sb.append("QueryCacheKey qckey = new QueryCacheKey(\"");
               sb.append(relFinderName).append("\", new Object[]{");
               sb.append("((CMPBean)").append(this.cmrFieldBeanVar());
               sb.append(").__WL_getPrimaryKey()").append("}, roMgr, ");
               if (Set.class.isAssignableFrom(cmrFieldClass)) {
                  sb.append("QueryCacheKey.RET_TYPE_SET);").append(EOL);
               } else {
                  sb.append("QueryCacheKey.RET_TYPE_SINGLETON);").append(EOL);
               }

               sb.append("QueryCacheElement qce = new QueryCacheElement(");
               if (cmrFieldPath != null) {
                  sb.append(this.cmrFieldBeanVar(prevCmrFieldName));
                  sb.append(".__WL_getPrimaryKey(), roMgr);").append(EOL);
               } else {
                  sb.append("__WL_getPrimaryKey(), roMgr);").append(EOL);
               }

               sb.append("qcHandler.addQueryCachingEntry(roMgr, qckey, qce);");
               sb.append(EOL);
               if (!cachingElementBean.isQueryCachingEnabledForCMRField(cmrFieldName)) {
                  sb.append("}").append(EOL);
               }
            }
         } else if (this.shouldImplementQueryCaching(this.curFinder) && (!prevBean.relatedFieldIsFkOwner(cachingElementCmrField) || prevBean.isManyToManyRelation(cachingElementCmrField)) || cachingElementBean.isQueryCachingEnabledForCMRField(cmrFieldName)) {
            this.currFinderLoadsQueryCachingEnabledCMRFields = true;
            sb.append("if (").append(this.beanMapVar()).append(" == null) {");
            sb.append(EOL);
            sb.append(this.beanMapVar()).append(" = new MultiMap();");
            sb.append(EOL);
            sb.append("}").append(EOL);
            sb.append("if (").append(this.beanMapVar()).append(".get(");
            sb.append(this.cmrFieldBeanVar()).append(", \"");
            sb.append(cmrFieldName).append("\") == null) {");
            sb.append(EOL);
            sb.append(this.beanMapVar()).append(".put(");
            sb.append(this.cmrFieldBeanVar()).append(", \"");
            sb.append(cmrFieldName).append("\");").append(EOL);
            sb.append("}").append(EOL);
         }

         sb.append(this.cmrFieldBeanVar() + "." + CodeGenUtils.cacheRelationshipMethodName(cmrFieldName) + "(" + this.eoRCVar() + ");" + EOL);
         sb.append("}" + EOL);
         sb.append("} else {" + EOL);
         sb.append(this.cmrFieldBeanVar() + " = (" + genBeanName + ") " + this.cmrFieldPKMapVar(cmrFieldPath) + ".get(" + this.cmrFieldPKVar(cmrFieldPath) + ");" + EOL);
         sb.append("}" + EOL);
         sb.append("}" + EOL + EOL);
         sb.append("increment = increment + " + offset + ";" + EOL);
         Iterator cachingElementNested = cachingElement.getCachingElements().iterator();
         if (cachingElementNested.hasNext()) {
            relFinderName = null;
            if (cmrFieldPath == null) {
               relFinderName = cachingElementCmrField;
            } else {
               relFinderName = cmrFieldPath + "_" + cachingElementCmrField;
            }

            this.invokeEagerCachingMethodsForCachingElements(cachingElementBean, cachingElementNested, relFinderName, sb, finderIsDynamic);
         }
      }

   }

   public String perhapsPostFinderCleanupForEagerCaching() {
      if (!(this.curFinder instanceof EjbqlFinder)) {
         return "";
      } else {
         EjbqlFinder ejbqlFinder = (EjbqlFinder)this.curFinder;
         String cachingName = ejbqlFinder.getCachingName();
         if (cachingName == null) {
            return "";
         } else {
            List cachingElements = this.curRelationshipCaching.getCachingElements();
            if (cachingElements.size() <= 0) {
               return "";
            } else {
               Iterator it = cachingElements.iterator();
               StringBuffer sb = new StringBuffer();
               sb.append("// After all related beans have been loaded into cache").append(EOL);
               sb.append("// we run postFinderCleanup on the related beans here.").append(EOL);
               sb.append("// postFinderCleanup on the primary beans is done in the beanManager.");
               sb.append(EOL).append(EOL);
               this.postFinderCleanupForEagerCaching(this.bean, it, (String)null, sb);
               return sb.toString();
            }
         }
      }
   }

   private void postFinderCleanupForEagerCaching(RDBMSBean prevBean, Iterator cachingElements, String cmrFieldPath, StringBuffer sb) {
      if (cachingElements.hasNext()) {
         while(cachingElements.hasNext()) {
            RelationshipCaching.CachingElement cachingElement = (RelationshipCaching.CachingElement)cachingElements.next();
            String cachingElementCmrField = cachingElement.getCmrField();
            String cachingElementGroupName = cachingElement.getGroupName();
            this.setCurrCachingElementCmrField(cachingElementCmrField);
            RDBMSBean cachingElementBean = prevBean.getRelatedRDBMSBean(cachingElementCmrField);
            CMPBeanDescriptor rbd = prevBean.getRelatedDescriptor(cachingElementCmrField);
            String cachingElementBmName = this.bmVar((String)this.declaredManagerVars.get(cachingElementBean.getEjbName()));
            boolean isLocal = rbd.hasLocalClientView();
            sb.append(cachingElementBmName).append(".postFinderCleanup(");
            sb.append("null,  // Object argument").append(EOL);
            sb.append(this.cmrFieldPKMapVar(cmrFieldPath)).append(".keySet(),  // Collection of the pks").append(EOL);
            sb.append("true,  // related cached pks").append(EOL);
            sb.append(isLocal).append("  // isLocal ?").append(EOL);
            sb.append(");").append(EOL).append(EOL);
            Iterator cachingElementNestedIterator = cachingElement.getCachingElements().iterator();
            if (cachingElementNestedIterator.hasNext()) {
               String cmrFieldPathNested = null;
               if (cmrFieldPath == null) {
                  cmrFieldPathNested = cachingElementCmrField;
               } else {
                  cmrFieldPathNested = cmrFieldPath + "_" + cachingElementCmrField;
               }

               this.postFinderCleanupForEagerCaching(cachingElementBean, cachingElementNestedIterator, cmrFieldPathNested, sb);
            }
         }

         sb.append(EOL);
      }
   }

   public String perhapsDeclareRelationshipCachingPooledBeanVar() {
      return this.perhapsRelationshipCachingPooledBeanVar(1);
   }

   public String perhapsAllocateRelationshipCachingPooledBeanVar() {
      return this.perhapsRelationshipCachingPooledBeanVar(2);
   }

   public String perhapsRemoveRelationshipCachingPooledBeanVar() {
      return this.perhapsRelationshipCachingPooledBeanVar(3);
   }

   public String perhapsRelationshipCachingPooledBeanVar(int type) {
      boolean isRelationshipCaching = false;
      if (this.curFinder instanceof EjbqlFinder) {
         EjbqlFinder ejbqlFinder = (EjbqlFinder)this.curFinder;
         String cachingName = ejbqlFinder.getCachingName();
         if (cachingName != null) {
            this.curRelationshipCaching = this.bean.getRelationshipCaching(cachingName);
            if (this.curRelationshipCaching != null) {
               isRelationshipCaching = this.curRelationshipCaching.getCachingElements().iterator().hasNext();
            }
         }
      }

      if (!isRelationshipCaching) {
         return "";
      } else {
         StringBuffer sb = new StringBuffer();
         Iterator cachingElements = this.curRelationshipCaching.getCachingElements().iterator();
         this.perhapsRelationshipCachingPooledBeanVar(this.bean, cachingElements, (String)null, type, sb);
         return sb.toString();
      }
   }

   public void perhapsRelationshipCachingPooledBeanVar(RDBMSBean prevBean, Iterator cachingElements, String cmrFieldPath, int type, StringBuffer sb) {
      while(cachingElements.hasNext()) {
         RelationshipCaching.CachingElement cachingElement = (RelationshipCaching.CachingElement)cachingElements.next();
         String cachingElementCmrField = cachingElement.getCmrField();
         this.setCurrCachingElementCmrField(cachingElementCmrField);
         RDBMSBean cachingElementBean = prevBean.getRelatedRDBMSBean(cachingElementCmrField);
         String cachingElementBmName = this.bmVar((String)this.declaredManagerVars.get(cachingElementBean.getEjbName()));
         CMPBeanDescriptor rbd = (CMPBeanDescriptor)this.beanMap.get(cachingElementBean.getEjbName());
         String genBeanName = rbd.getGeneratedBeanInterfaceName();
         if (type == 1) {
            sb.append(genBeanName + " " + this.cmrFieldBeanVar() + "_pooledBean = null;" + EOL);
         } else if (type == 2) {
            sb.append(this.cmrFieldBeanVar() + "_pooledBean = (" + genBeanName + ") " + cachingElementBmName + ".getBeanFromPool();" + EOL);
         } else if (type == 3) {
            sb.append("if (" + this.cmrFieldBeanVar() + "_pooledBean!=null) ((weblogic.ejb.container.manager.BaseEntityManager)" + cachingElementBmName + ").releaseBeanToPool(((javax.ejb.EntityBean)" + this.cmrFieldBeanVar() + "_pooledBean));" + EOL);
         }

         Iterator cachingElementNested = cachingElement.getCachingElements().iterator();
         if (cachingElementNested.hasNext()) {
            String cmrFieldPathNested = null;
            if (cmrFieldPath == null) {
               cmrFieldPathNested = cachingElementCmrField;
            } else {
               cmrFieldPathNested = cmrFieldPath + "_" + cachingElementCmrField;
            }

            this.perhapsRelationshipCachingPooledBeanVar(cachingElementBean, cachingElementNested, cmrFieldPathNested, type, sb);
         }
      }

   }

   public String declarePKMapVar() {
      StringBuffer sb = new StringBuffer();
      sb.append(EOL + "Map " + this.pkMapVar() + " = new HashMap();" + EOL);
      String cachingName = null;
      if (this.curFinder instanceof EjbqlFinder) {
         EjbqlFinder ejbqlFinder = (EjbqlFinder)this.curFinder;
         cachingName = ejbqlFinder.getCachingName();
      }

      if (cachingName == null) {
         return sb.toString();
      } else {
         RelationshipCaching caching = this.bean.getRelationshipCaching(cachingName);
         if (caching == null) {
            return sb.toString();
         } else {
            Iterator cachingElements = caching.getCachingElements().iterator();
            sb.append(this.declarePKMapVarForCachingElements(this.bean, cachingElements, (String)null));
            return sb.toString();
         }
      }
   }

   public String declarePKMapVarForCachingElements(RDBMSBean prevBean, Iterator cachingElements, String cmrFieldPath) {
      StringBuffer sb = new StringBuffer();

      while(cachingElements.hasNext()) {
         RelationshipCaching.CachingElement cachingElement = (RelationshipCaching.CachingElement)cachingElements.next();
         String cachingElementCmrField = cachingElement.getCmrField();
         String cachingElementGroupName = cachingElement.getGroupName();
         this.setCurrCachingElementCmrField(cachingElementCmrField);
         RDBMSBean cachingElementBean = prevBean.getRelatedRDBMSBean(cachingElementCmrField);
         sb.append("Map " + this.cmrFieldPKMapVar(cmrFieldPath) + " = new HashMap();" + EOL);
         Iterator cachingElementNested = cachingElement.getCachingElements().iterator();
         if (cachingElementNested.hasNext()) {
            String cmrFieldPathNested = null;
            if (cmrFieldPath == null) {
               cmrFieldPathNested = cachingElementCmrField;
            } else {
               cmrFieldPathNested = cmrFieldPath + "_" + cachingElementCmrField;
            }

            sb.append(this.declarePKMapVarForCachingElements(cachingElementBean, cachingElementNested, cmrFieldPathNested));
         }
      }

      return sb.toString();
   }

   private void setCurrCachingElementCmrField(String cachingElementCmrField) {
      this.currCachingElementCmrField = cachingElementCmrField;
   }

   private String cmrFieldBeanVar() {
      return this.beanVar() + "_" + this.currCachingElementCmrField;
   }

   private String cmrFieldBeanVar(String prevCmrFieldName) {
      return "".equals(prevCmrFieldName) ? this.beanVar() : this.beanVar() + "_" + prevCmrFieldName;
   }

   private String cmrFieldPKVar(String cmrFieldPath) {
      return cmrFieldPath == null ? this.pkVar() + "_" + this.currCachingElementCmrField : this.pkVar() + "_" + cmrFieldPath + "_" + this.currCachingElementCmrField;
   }

   private String cmrFieldPKMapVar(String cmrFieldPath) {
      return cmrFieldPath == null ? this.pkMapVar() + "_" + this.currCachingElementCmrField : this.pkMapVar() + "_" + cmrFieldPath + "_" + this.currCachingElementCmrField;
   }

   public String assignOffsetIntObjVar(int offset) {
      return this.offsetIntObjVar() + " = new Integer(" + offset + ");" + EOL;
   }

   public String assignOffsetVar() {
      return "int " + this.offsetVar() + " = " + this.offsetIntObjVar() + ".intValue();" + EOL;
   }

   public String getPKFromRSMethodName() {
      return this.varPrefix() + "getPKFromRS";
   }

   private String getPKFromRSMethodParams() {
      return "(" + this.rsVar() + ", " + this.offsetIntObjVar() + ", " + this.classLoaderVar() + ");";
   }

   private int loadGroupFromRSIndex(RDBMSBean cachingElementBean, String groupName) {
      return cachingElementBean.getFieldGroup(groupName).getIndex();
   }

   private String loadGroupFromRSMethodName(FieldGroup group) {
      return this.loadMethodName(this.getFieldGroupSuffix(group) + "FromRS");
   }

   private String loadCMRFieldFromRSMethodName(String cmrFieldName) {
      return this.varPrefix() + "loadCMRFieldFromRS_" + cmrFieldName;
   }

   private boolean lhsBeanHasFKForLocal11or1NPath(RDBMSBean lhsBean, String lhsCmrFieldName) {
      return lhsBean.isForeignKeyField(lhsCmrFieldName);
   }

   private String oneToManyAddMethod() {
      StringBuffer sb = new StringBuffer();
      sb.append("public void " + CodeGenUtils.cacheRelationshipMethodName(this.curField) + "(Object " + this.curField + ") {" + EOL);
      if (this.bean.getRelatedMultiplicity(this.curField).equals("One")) {
         String fieldClassName = ClassUtils.classToJavaSourceType(this.bean.getCmrFieldClass(this.curField));
         sb.append("if (" + this.curField + " != null) {" + EOL);
         sb.append(CodeGenUtils.fieldVarName(this.curField) + " = (" + fieldClassName + ") " + this.curField + ";" + EOL);
         sb.append("}" + EOL);
      } else {
         sb.append("if (");
         sb.append(CodeGenUtils.fieldVarName(this.curField));
         sb.append(" == null) {" + EOL);
         sb.append(CodeGenUtils.fieldVarName(this.curField) + " = " + this.allocateOneToManySet() + ";" + EOL);
         sb.append("}" + EOL);
         if (this.bean.isReadOnly() || this.bean.getCacheBetweenTransactions()) {
            sb.append("else {").append(EOL);
            sb.append("Transaction currentTx = TransactionHelper");
            sb.append(".getTransactionHelper().getTransaction();").append(EOL);
            sb.append("if (currentTx == null || !((RDBMSSet)");
            sb.append(CodeGenUtils.fieldVarName(this.curField));
            sb.append(").checkIfCurrentTxEqualsCreateTx(currentTx)) {");
            sb.append(EOL);
            sb.append(CodeGenUtils.fieldVarName(this.curField)).append(" = ");
            sb.append(this.allocateOneToManySet()).append(";").append(EOL);
            sb.append("}").append(EOL);
            sb.append("}").append(EOL);
         }

         sb.append("((RDBMSSet)");
         sb.append(CodeGenUtils.fieldVarName(this.curField));
         sb.append(").doAddToCache(" + this.curField + ");" + EOL);
      }

      sb.append(this.isCmrLoadedVarName(this.curField) + " = true;" + EOL);
      sb.append("}" + EOL + EOL);
      return sb.toString();
   }

   public String perhapsGenKeyBeforeInsert() throws CodeGenerationException {
      if (this.bean.hasAutoKeyGeneration() && this.bean.getGenKeyBeforeInsert()) {
         StringBuffer sb = new StringBuffer();
         sb.append(EOL);
         sb.append(this.genKeyQuery()).append(EOL);
         sb.append(this.genKeyCallSetPK()).append(EOL);
         return sb.toString();
      } else {
         return "";
      }
   }

   public String perhapsDeclarePkCheckMethod() throws CodeGenerationException {
      return !this.bean.hasAutoKeyGeneration() ? this.parse(this.getProductionRule("declarePkCheckMethod")) : "";
   }

   public String implementPkCheckMethodBody() {
      StringBuffer sb = new StringBuffer();
      if (!this.bd.isReadOnly() || this.bean.allowReadonlyCreateAndRemove()) {
         Iterator pkFields = this.pkFieldNames.iterator();

         while(pkFields.hasNext()) {
            String pkField = (String)pkFields.next();
            sb.append("// check that '" + pkField + "' was set" + EOL);
            sb.append("if (!" + this.isModifiedVar() + "[" + this.bean.getIsModifiedIndex(pkField) + "]) {" + EOL);
            sb.append("Loggable l = EJBLogger.logpkNotSetLoggable(\"" + this.bean.getEjbName() + "\",\"" + pkField + "\");");
            sb.append("throw new javax.ejb.CreateException(l.getMessage());" + EOL);
            sb.append("}");
            if (pkFields.hasNext()) {
               sb.append(EOL);
            }
         }
      }

      return sb.toString();
   }

   public String perhapsImplementCreateMethodBody() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      if (this.bd.isReadOnly() && !this.bean.allowReadonlyCreateAndRemove()) {
         sb.append("Loggable l = EJBLogger.logCannotCreateReadOnlyBeanLoggable(\"" + this.bean.getEjbName() + "\");" + EOL);
         sb.append("throw new javax.ejb.CreateException(l.getMessageText());" + EOL);
      } else {
         sb.append(this.parse(this.getProductionRule("implementCreateMethodBody")));
      }

      return sb.toString();
   }

   public String perhapsCallPkCheck() {
      return !this.bean.hasAutoKeyGeneration() ? EOL + "pkCheck();" + EOL : "";
   }

   public String genKeyQuery() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      short genKeyType = this.bean.getGenKeyType();
      String genKeyPKClassName = this.genKeyGetPKClassName();
      sb.append(genKeyPKClassName).append(" ").append(this.genKeyVar());
      if (genKeyType == 2) {
         sb.append(" = (").append(genKeyPKClassName).append(") __WL_pm.getNextSequenceKey();");
      } else {
         if (genKeyType != 3) {
            throw new AssertionError("Unknown prefetch auto-key generator for " + this.bd.getEJBName());
         }

         sb.append(" = (").append(genKeyPKClassName).append(") __WL_pm.getNextSequenceTableKey();");
      }

      sb.append(EOL);
      return sb.toString();
   }

   private String genKeyCallSetPK() {
      StringBuffer sb = new StringBuffer();
      String pkFieldName = (String)this.bd.getPrimaryKeyFieldNames().iterator().next();
      Class pkFieldClass = this.bd.getFieldClass(pkFieldName);
      String value = this.genKeyVar();
      if (pkFieldClass.equals(Integer.TYPE)) {
         value = value + ".intValue()";
      } else if (pkFieldClass.equals(Long.TYPE)) {
         value = value + ".longValue()";
      }

      sb.append(RDBMSUtils.setterMethodName(this.bean.getGenKeyPKField()));
      sb.append("(").append(value).append(");").append(EOL);
      return sb.toString();
   }

   private String genKeyGetPKClassName() throws CodeGenerationException {
      switch (this.bean.getGenKeyPKFieldClassType()) {
         case 0:
            return "java.lang.Integer";
         case 1:
            return "java.lang.Long";
         default:
            throw new CodeGenerationException(" Internal Error, unknown genKeyPKFieldClassType: " + this.bean.getGenKeyPKFieldClassType());
      }
   }

   private String attrsAsColumnsAsParamsForTable(String tabName, String[] attrNames, String delim) {
      StringBuffer sb = new StringBuffer();
      int i = 0;

      for(int len = attrNames.length; i < len; ++i) {
         String colName = RDBMSUtils.escQuotedID(this.bean.getColumnForCmpFieldAndTable(attrNames[i], tabName));
         sb.append(colName).append(" = ?");
         if (i < len - 1) {
            sb.append(delim);
         }
      }

      return sb.toString();
   }

   private String attrsAsColumnsAsParams(String[] attrNames, String delim) {
      StringBuffer sb = new StringBuffer();
      int i = 0;

      for(int len = attrNames.length; i < len; ++i) {
         String colName = RDBMSUtils.escQuotedID(this.bean.getCmpColumnForField(attrNames[i]));
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

   public String getTableCount() {
      return Integer.toString(this.bean.tableCount());
   }

   public String setCreateQueryArray() {
      return this.setCreateQueryArray(false);
   }

   public String setCreateQueryArrayWoFkColumns() {
      return this.setCreateQueryArray(true);
   }

   public String setCreateQueryArray(boolean woFkCols) {
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < this.bean.tableCount(); ++i) {
         this.curTableName = this.bean.tableAt(i);
         StringBuffer quotes = new StringBuffer();
         String columnSql = this.getCreateQueryColumnSQLForTable(i, quotes, woFkCols);
         sb.append(this.queryArrayElement(i)).append(" = \"INSERT INTO ");
         sb.append(this.curTableName).append(" (");
         sb.append(columnSql);
         sb.append(") VALUES (");
         sb.append(quotes);
         if (this.bean.getGenKeyType() != 1 || this.bean.getDatabaseType() != 2 && this.bean.getDatabaseType() != 7) {
            sb.append(")\";").append(EOL);
         } else {
            sb.append(") " + this.bean.getGenKeyGeneratorQuery() + "\";").append(EOL);
         }
      }

      return sb.toString();
   }

   public String executeUpdateOrExecuteQuery() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      short genKeyType = this.bean.getGenKeyType();
      Class pkClass = this.bd.getPrimaryKeyClass();
      if (genKeyType == 1) {
         sb.append(this.genKeyGetPKClassName()).append(" " + this.genKeyVar() + " = null;");
         sb.append(EOL);
      }

      for(int i = 0; i < this.bean.tableCount(); ++i) {
         if (genKeyType != 1 || this.bean.getDatabaseType() != 2 && this.bean.getDatabaseType() != 7) {
            sb.append("if (" + this.stmtArrayElement(i)).append(".executeUpdate() != 1)").append(EOL);
            sb.append("throw new java.lang.Exception(\"" + this.createUpdateFailureMsg() + "\");").append(EOL);
            if (genKeyType == 1) {
               sb.append(this.rsVar() + " = " + this.stmtArrayElement(i));
               sb.append(".getGeneratedKeys();").append(EOL);
            }
         } else {
            sb.append(this.rsVar() + " = " + this.stmtArrayElement(i)).append(".executeQuery();").append(EOL);
         }

         if (genKeyType == 1) {
            sb.append("if (" + this.rsVar() + ".next()) {").append(EOL);
            sb.append(this.genKeyVar());
            sb.append(" = new ").append(this.genKeyGetPKClassName() + "(" + this.rsVar() + ".get");
            if (this.genKeyGetPKClassName().equals("java.lang.Integer")) {
               sb.append("Int(1)");
            } else if (this.genKeyGetPKClassName().equals("java.lang.Long")) {
               sb.append("Long(1)");
            }

            sb.append(");").append(EOL);
            sb.append("this.").append(this.bean.getGenKeyPKField()).append(" = ");
            sb.append(this.genKeyVar()).append(";").append(EOL);
            sb.append(this.pkVar());
            sb.append(" = (").append(ClassUtils.classToJavaSourceType(pkClass)).append(") __WL_getPrimaryKey();").append(EOL);
            sb.append("} else {").append(EOL);
            sb.append("Loggable l = EJBLogger.logNoGeneratedPKReturnedLoggable();").append(EOL);
            sb.append("throw new javax.ejb.CreateException(l.getMessage());" + EOL);
            sb.append("}").append(EOL);
            sb.append("if (" + this.rsVar() + ".next()) {").append(EOL);
            sb.append("Loggable l = EJBLogger.logMultiplGeneratedKeysReturnedLoggable();").append(EOL);
            sb.append("throw new javax.ejb.CreateException(l.getMessage());" + EOL);
            sb.append("}").append(EOL);
         }
      }

      return sb.toString();
   }

   public String setUpdateQueryArray() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < this.bean.tableCount(); ++i) {
         this.curTableIndex = i;
         this.curTableName = this.bean.tableAt(i);
         sb.append(this.perhapsResetBlobClobCountVar()).append(EOL);
         sb.append(this.countVar() + " = 0;");
         sb.append(this.constructModifiedFieldStoreColumnStrings());
         if (this.useVersionOrTimestampCheckingForBlobClob(this.curTableName)) {
            sb.append("if ( (").append(this.countVar()).append(" != 0) || ");
            sb.append("(").append(this.blobClobCountVarOrZero()).append(" > 0) ) {").append(EOL);
         } else {
            sb.append("if (").append(this.countVar()).append(" != 0) {").append(EOL);
         }

         sb.append(this.perhapsSetUpdateOptimisticFieldStringForBatch());
         sb.append(this.perhapsConstructSnapshotPredicate());
         sb.append("if (sb.length() != 0) {").append(EOL);
         sb.append(this.queryArrayElement(i)).append(" = \"UPDATE ");
         sb.append(this.curTableName);
         sb.append(" SET \" + ");
         sb.append("sb.toString() + ");
         sb.append("\" WHERE " + this.idParamsSqlForCurTable() + "\"");
         sb.append(this.perhapsAddSnapshotPredicate() + ";").append(EOL);
         sb.append("}").append(EOL);
         sb.append("}").append(EOL);
      }

      return sb.toString();
   }

   public String setBeanParamsForUpdateArray() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      sb.append(this.declareByteArrayVars());

      for(int i = 0; i < this.bean.tableCount(); ++i) {
         this.curTableIndex = i;
         this.curTableName = this.bean.tableAt(i);
         sb.append(this.parse(this.getProductionRule("implementSetBeanParamsForUpdateArrayMethodBody")));
      }

      return sb.toString();
   }

   private boolean appendComma(boolean comma, StringBuffer sb, StringBuffer quoteString) {
      if (comma) {
         sb.append(", ");
         quoteString.append(", ");
      } else {
         comma = true;
      }

      return comma;
   }

   private String getCreateQueryColumnSQLForTable(int tableIndex, StringBuffer quoteString, boolean woFkCols) {
      StringBuffer sb = new StringBuffer();
      this.curTableIndex = tableIndex;
      this.curTableName = this.bean.tableAt(tableIndex);
      List fieldNamesList = new ArrayList(this.bean.getFields(tableIndex));
      String genKeyPKField = null;
      String genKeyDefaultColumnVal = null;
      if (this.bean.hasAutoKeyGeneration()) {
         genKeyPKField = this.bean.getGenKeyPKField();
         genKeyDefaultColumnVal = this.bean.getGenKeyDefaultColumnVal();
      }

      if (this.bean.hasAutoKeyGeneration() && this.bean.genKeyExcludePKColumn()) {
         assert fieldNamesList.contains(genKeyPKField);

         fieldNamesList.remove(genKeyPKField);
      }

      if (this.bean.getTriggerUpdatesOptimisticColumn(this.curTableName)) {
         String optColumn = this.bean.getOptimisticColumn(this.curTableName);
         String optField = this.bean.getCmpField(this.curTableName, optColumn);

         assert fieldNamesList.contains(optField);

         fieldNamesList.remove(optField);
      }

      boolean comma = false;
      Iterator it = fieldNamesList.iterator();

      while(true) {
         while(true) {
            String fieldName;
            Iterator fkColumnNames;
            String columnName;
            while(it.hasNext()) {
               fieldName = (String)it.next();
               if (this.bean.isCmpFieldName(fieldName)) {
                  if (fieldName.equals(genKeyPKField) && genKeyDefaultColumnVal != null) {
                     comma = this.appendComma(comma, sb, quoteString);
                     sb.append(genKeyDefaultColumnVal);
                     quoteString.append(genKeyDefaultColumnVal);
                     if (debugLogger.isDebugEnabled()) {
                        debug("substitute default column value '" + genKeyDefaultColumnVal + "'");
                     }
                  } else if (!this.bean.isDbmsDefaultValueField(fieldName)) {
                     comma = this.appendComma(comma, sb, quoteString);
                     sb.append(this.getInsertColumnStringForField(fieldName, tableIndex));
                     quoteString.append(this.getInsertQuoteStringForField(fieldName));
                  }
               } else if (this.bean.isSelfRelationship(fieldName) && this.bean.containsFkField(fieldName) && !this.bean.isForeignCmpField(fieldName)) {
                  fkColumnNames = this.bean.getForeignKeyColNames(fieldName).iterator();

                  while(fkColumnNames.hasNext()) {
                     columnName = (String)fkColumnNames.next();
                     comma = this.appendComma(comma, sb, quoteString);
                     sb.append(RDBMSUtils.escQuotedID(columnName));
                     quoteString.append("?");
                  }
               }
            }

            it = fieldNamesList.iterator();

            while(true) {
               do {
                  do {
                     do {
                        do {
                           do {
                              if (!it.hasNext()) {
                                 return sb.toString();
                              }

                              fieldName = (String)it.next();
                           } while(this.bean.isCmpFieldName(fieldName));
                        } while(woFkCols);
                     } while(this.bean.isSelfRelationship(fieldName));
                  } while(!this.bean.containsFkField(fieldName));
               } while(this.bean.isForeignCmpField(fieldName));

               fkColumnNames = this.bean.getForeignKeyColNames(fieldName).iterator();

               while(fkColumnNames.hasNext()) {
                  columnName = (String)fkColumnNames.next();
                  comma = this.appendComma(comma, sb, quoteString);
                  sb.append(RDBMSUtils.escQuotedID(columnName));
                  quoteString.append("?");
               }
            }
         }
      }
   }

   private String getInsertColumnStringForField(String fieldName, int tableIndex) {
      this.curTableName = this.bean.tableAt(tableIndex);
      String columnName = this.bean.getColumnForCmpFieldAndTable(fieldName, this.curTableName);

      assert columnName != null;

      return RDBMSUtils.escQuotedID(columnName);
   }

   private String getInsertQuoteStringForField(String fieldName) {
      if (this.bean.isBlobCmpColumnTypeForField(fieldName) && this.bean.getDatabaseType() == 1) {
         return "EMPTY_BLOB()";
      } else {
         return this.bean.isClobCmpColumnTypeForField(fieldName) && this.bean.getDatabaseType() == 1 ? "\"+((" + this.pmVar() + ".perhapsUseSetStringForClobForOracle()) ? \"?\":\"EMPTY_CLOB()\")+\"" : "?";
      }
   }

   public String prepareCreateStmtArray() {
      return this.bean.getGenKeyType() == 1 && this.bean.getDatabaseType() != 2 && this.bean.getDatabaseType() != 7 ? this.prepareStmtArray(true) : this.prepareStmtArray(false);
   }

   public String prepareStmtArray() {
      return this.prepareStmtArray(false);
   }

   public String prepareStmtArray(boolean autoGeneratedKeys) {
      StringBuffer sb = new StringBuffer();
      sb.append(EOL);

      for(int i = 0; i < this.bean.tableCount(); ++i) {
         sb.append("if (" + this.queryArrayElement(i) + " != null) ");
         sb.append(this.stmtArrayElement(i)).append(" = ");
         sb.append(this.conVar()).append(".prepareStatement(").append(this.queryArrayElement(i));
         if (autoGeneratedKeys) {
            sb.append(", java.sql.Statement.RETURN_GENERATED_KEYS");
         }

         sb.append(");").append(EOL);
      }

      return sb.toString();
   }

   public String setBeanParamsForCreateArray() {
      StringBuffer sb = new StringBuffer();
      sb.append("int " + this.numVar() + " = 0;" + EOL);

      label117:
      for(int i = 0; i < this.bean.tableCount(); ++i) {
         sb.append(this.resetParams() + EOL);
         sb.append(this.numVar() + " = 1;" + EOL);
         List pfieldNames = new ArrayList(this.bean.getCMPFields(i));
         String table;
         if (this.bean.genKeyExcludePKColumn()) {
            table = this.bean.getGenKeyPKField();
            if (debugLogger.isDebugEnabled()) {
               debug("CreateBeanParams Exclude PK field: " + table);
            }

            pfieldNames.remove(table);
         }

         table = this.bean.tableAt(i);
         if (this.bean.getTriggerUpdatesOptimisticColumn(table)) {
            String optColumn = this.bean.getOptimisticColumn(table);
            String optField = this.bean.getCmpField(table, optColumn);

            assert pfieldNames.contains(optField);

            pfieldNames.remove(optField);
         }

         List l = new ArrayList(this.bean.getCMPFields(i));
         Iterator it = l.iterator();

         while(true) {
            while(it.hasNext()) {
               String field = (String)it.next();
               if ((this.bean.isBlobCmpColumnTypeForField(field) || this.bean.isClobCmpColumnTypeForField(field)) && this.bean.getDatabaseType() == 1) {
                  pfieldNames.remove(field);
               } else if (this.bean.isDbmsDefaultValueField(field)) {
                  pfieldNames.remove(field);
               }
            }

            List cmrf = this.bean.getCMRFields(i);
            it = cmrf.iterator();

            while(true) {
               String fieldName;
               String fkTable;
               do {
                  do {
                     do {
                        if (!it.hasNext()) {
                           String[] names = (String[])((String[])pfieldNames.toArray(new String[0]));
                           sb.append(this.preparedStatementBindings(names, "this", true, true, true, this.bd.isOptimistic(), this.stmtArrayElement(i)));
                           List cmrPfieldNames = new ArrayList();
                           cmrf = this.bean.getCMRFields(i);
                           it = cmrf.iterator();

                           while(true) {
                              String fieldName;
                              do {
                                 do {
                                    do {
                                       if (!it.hasNext()) {
                                          String[] cmrNames = (String[])((String[])cmrPfieldNames.toArray(new String[0]));
                                          sb.append("if (!woFkCols) {");
                                          sb.append(this.preparedStatementBindings(cmrNames, "this", true, true, true, this.bd.isOptimistic(), this.stmtArrayElement(i)));
                                          sb.append("}\n");
                                          continue label117;
                                       }

                                       fieldName = (String)it.next();
                                    } while(this.bean.isSelfRelationship(fieldName));
                                 } while(!this.bean.containsFkField(fieldName));
                              } while(this.bean.isForeignCmpField(fieldName));

                              fkTable = this.bean.getTableForCmrField(fieldName);
                              Iterator colNames = this.bean.getForeignKeyColNames(fieldName).iterator();

                              while(colNames.hasNext()) {
                                 String colName = RDBMSUtils.escQuotedID((String)colNames.next());
                                 String varName = this.bean.variableForField(fieldName, fkTable, colName);
                                 cmrPfieldNames.add(varName);
                              }
                           }
                        }

                        fieldName = (String)it.next();
                     } while(!this.bean.isSelfRelationship(fieldName));
                  } while(!this.bean.containsFkField(fieldName));
               } while(this.bean.isForeignCmpField(fieldName));

               String fkTable = this.bean.getTableForCmrField(fieldName);
               Iterator colNames = this.bean.getForeignKeyColNames(fieldName).iterator();

               while(colNames.hasNext()) {
                  fkTable = RDBMSUtils.escQuotedID((String)colNames.next());
                  String varName = this.bean.variableForField(fieldName, fkTable, fkTable);
                  pfieldNames.add(varName);
               }
            }
         }
      }

      return sb.toString();
   }

   public String setBeanParamsForCreateArrayOptimizedForClobUpdate() {
      StringBuffer sb = new StringBuffer();
      sb.append("int " + this.numVar() + " = 0;" + EOL);

      label119:
      for(int i = 0; i < this.bean.tableCount(); ++i) {
         sb.append(this.resetParams() + EOL);
         sb.append(this.numVar() + " = 1;" + EOL);
         List pfieldNames = new ArrayList(this.bean.getCMPFields(i));
         String table;
         if (this.bean.genKeyExcludePKColumn()) {
            table = this.bean.getGenKeyPKField();
            if (debugLogger.isDebugEnabled()) {
               debug("CreateBeanParams Exclude PK field: " + table);
            }

            pfieldNames.remove(table);
         }

         table = this.bean.tableAt(i);
         if (this.bean.getTriggerUpdatesOptimisticColumn(table)) {
            String optColumn = this.bean.getOptimisticColumn(table);
            String optField = this.bean.getCmpField(table, optColumn);

            assert pfieldNames.contains(optField);

            pfieldNames.remove(optField);
         }

         List l = new ArrayList(this.bean.getCMPFields(i));
         Iterator it = l.iterator();
         int paramIndexForClob = 0;

         while(true) {
            while(it.hasNext()) {
               String field = (String)it.next();
               ++paramIndexForClob;
               if (this.bean.isBlobCmpColumnTypeForField(field) && this.bean.getDatabaseType() == 1) {
                  --paramIndexForClob;
                  pfieldNames.remove(field);
               } else if (this.bean.isClobCmpColumnTypeForField(field) && this.bean.getDatabaseType() == 1) {
                  sb.append(this.doPreparedStmtBindingForClob(field, paramIndexForClob, "this", true, false, this.bd.isOptimistic(), this.stmtArrayElement(i)));
               } else if (this.bean.isDbmsDefaultValueField(field)) {
                  --paramIndexForClob;
                  pfieldNames.remove(field);
               }
            }

            List cmrf = this.bean.getCMRFields(i);
            it = cmrf.iterator();

            while(true) {
               String fieldName;
               String fkTable;
               do {
                  do {
                     do {
                        if (!it.hasNext()) {
                           String[] names = (String[])((String[])pfieldNames.toArray(new String[0]));
                           sb.append(this.preparedStatementBindings(names, "this", true, true, true, this.bd.isOptimistic(), this.stmtArrayElement(i)));
                           List cmrPfieldNames = new ArrayList();
                           cmrf = this.bean.getCMRFields(i);
                           it = cmrf.iterator();

                           while(true) {
                              String fieldName;
                              do {
                                 do {
                                    do {
                                       if (!it.hasNext()) {
                                          String[] cmrNames = (String[])((String[])cmrPfieldNames.toArray(new String[0]));
                                          sb.append("if (!woFkCols) {");
                                          sb.append(this.preparedStatementBindings(cmrNames, "this", true, true, true, this.bd.isOptimistic(), this.stmtArrayElement(i)));
                                          sb.append("}\n");
                                          continue label119;
                                       }

                                       fieldName = (String)it.next();
                                    } while(this.bean.isSelfRelationship(fieldName));
                                 } while(!this.bean.containsFkField(fieldName));
                              } while(this.bean.isForeignCmpField(fieldName));

                              fkTable = this.bean.getTableForCmrField(fieldName);
                              Iterator colNames = this.bean.getForeignKeyColNames(fieldName).iterator();

                              while(colNames.hasNext()) {
                                 String colName = RDBMSUtils.escQuotedID((String)colNames.next());
                                 String varName = this.bean.variableForField(fieldName, fkTable, colName);
                                 cmrPfieldNames.add(varName);
                              }
                           }
                        }

                        fieldName = (String)it.next();
                     } while(!this.bean.isSelfRelationship(fieldName));
                  } while(!this.bean.containsFkField(fieldName));
               } while(this.bean.isForeignCmpField(fieldName));

               String fkTable = this.bean.getTableForCmrField(fieldName);
               Iterator colNames = this.bean.getForeignKeyColNames(fieldName).iterator();

               while(colNames.hasNext()) {
                  fkTable = RDBMSUtils.escQuotedID((String)colNames.next());
                  String varName = this.bean.variableForField(fieldName, fkTable, fkTable);
                  pfieldNames.add(varName);
               }
            }
         }
      }

      return sb.toString();
   }

   public String setBeanParamsForCreate() {
      List pfieldNames = new ArrayList(this.cmpFieldNames);
      if (this.bean.genKeyExcludePKColumn()) {
         String excludePKfield = this.bean.getGenKeyPKField();
         if (debugLogger.isDebugEnabled()) {
            debug("CreateBeanParams Exclude PK field: " + excludePKfield);
         }

         pfieldNames.remove(excludePKfield);
      }

      Iterator it = this.cmpFieldNames.iterator();

      while(true) {
         String field;
         do {
            if (!it.hasNext()) {
               Iterator fieldNames = this.bean.getForeignKeyFieldNames().iterator();

               while(true) {
                  String fieldName;
                  do {
                     do {
                        if (!fieldNames.hasNext()) {
                           String[] names = (String[])((String[])pfieldNames.toArray(new String[0]));
                           return this.preparedStatementBindings(names, "this", true, true, false, this.bd.isOptimistic());
                        }

                        fieldName = (String)fieldNames.next();
                     } while(!this.bean.containsFkField(fieldName));
                  } while(this.bean.isForeignCmpField(fieldName));

                  String fkTable = this.bean.getTableForCmrField(fieldName);
                  Iterator colNames = this.bean.getForeignKeyColNames(fieldName).iterator();

                  while(colNames.hasNext()) {
                     String colName = RDBMSUtils.escQuotedID((String)colNames.next());
                     String varName = this.bean.variableForField(fieldName, fkTable, colName);
                     pfieldNames.add(varName);
                  }
               }
            }

            field = (String)it.next();
         } while(!this.bean.isBlobCmpColumnTypeForField(field) && !this.bean.isClobCmpColumnTypeForField(field));

         pfieldNames.remove(field);
      }
   }

   public String setRemoveQueryArray() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < this.bean.tableCount(); ++i) {
         this.curTableIndex = i;
         this.curTableName = this.bean.tableAt(this.curTableIndex);
         sb.append(this.perhapsConstructSnapshotPredicate());
         sb.append(this.queryArrayElement(i)).append(" = \"DELETE FROM ");
         sb.append(this.curTableName).append(" WHERE ");
         sb.append(this.idParamsSqlForTable(this.curTableName) + "\" ");
         sb.append(this.perhapsAddSnapshotPredicate());
         sb.append(";").append(EOL);
      }

      return sb.toString();
   }

   public String perhapsDeclareSetBlobClobForOutputMethod() throws CodeGenerationException {
      if (!this.bean.hasBlobClobColumn()) {
         return "";
      } else {
         StringBuffer sb = new StringBuffer();
         String[] names = (String[])this.cmpFieldNames.toArray(new String[0]);
         int i = 0;

         for(int len = names.length; i < len; ++i) {
            String field = names[i];
            if (this.bean.hasCmpColumnType(field)) {
               if (this.bean.isBlobCmpColumnTypeForField(field)) {
                  this.curField = names[i];
                  this.curTableName = this.bean.getTableForCmpField(this.curField);
                  this.curTableIndex = this.bean.tableIndex(this.curTableName);
                  if (this.bean.getDatabaseType() == 9) {
                     sb.append(this.parse(this.getProductionRule("setBlobForOutputBodyForDerby")));
                  } else {
                     sb.append(this.parse(this.getProductionRule("setBlobForOutputBody")));
                  }

                  sb.append(this.parse(this.getProductionRule("setBlobForInputBody")));
               }

               if (this.bean.isClobCmpColumnTypeForField(field)) {
                  this.curField = names[i];
                  this.curTableName = this.bean.getTableForCmpField(this.curField);
                  this.curTableIndex = this.bean.tableIndex(this.curTableName);
                  if (this.bean.getDatabaseType() == 9) {
                     sb.append(this.parse(this.getProductionRule("setClobForOutputBodyForDerby")));
                  } else {
                     sb.append(this.parse(this.getProductionRule("setClobForOutputBody")));
                  }

                  sb.append(this.parse(this.getProductionRule("setClobForInputBody")));
               }
            }
         }

         return sb.toString();
      }
   }

   public String perhapsTruncateClob() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      if (this.bean.getDatabaseType() == 1) {
         sb.append(this.parse(this.getProductionRule("truncateOracleClob")));
      } else {
         sb.append(this.parse(this.getProductionRule("truncateGenericClob")));
      }

      return sb.toString();
   }

   public String getClobWriter() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      if (this.bean.getDatabaseType() == 1) {
         sb.append(this.parse(this.getProductionRule("getOracleClobWriter")));
      } else {
         sb.append(this.parse(this.getProductionRule("getGenericClobWriter")));
      }

      return sb.toString();
   }

   public String perhapsUpdateClob() throws CodeGenerationException {
      return this.bean.getDatabaseType() == 4 ? this.parse(this.getProductionRule("updateClob")) : "";
   }

   public String setClobParam() {
      StringBuffer sb = new StringBuffer();
      sb.append(this.stmtVar());
      sb.append(".setClob(");
      sb.append(this.preparedStatementParamIndex++).append(", lob");
      sb.append(");" + EOL);
      return sb.toString();
   }

   public String setLobAsTypeParam() {
      StringBuffer sb = new StringBuffer();
      this.addPreparedStatementBinding(sb, this.curField, "this", String.valueOf(this.preparedStatementParamIndex), true, true, false, false, this.stmtVar());
      ++this.preparedStatementParamIndex;
      return sb.toString();
   }

   public String perhapsTruncateBlob() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      if (this.bean.getDatabaseType() == 1) {
         sb.append(this.parse(this.getProductionRule("truncateOracleBlob")));
      } else {
         sb.append(this.parse(this.getProductionRule("truncateGenericBlob")));
      }

      return sb.toString();
   }

   public String setBlobParam() {
      StringBuffer sb = new StringBuffer();
      sb.append(this.stmtVar());
      sb.append(".setBlob(");
      sb.append(this.preparedStatementParamIndex++).append(", lob");
      sb.append(");" + EOL);
      return sb.toString();
   }

   public String writeBlobToOutputStream() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      if (this.bean.getDatabaseType() == 1) {
         sb.append(this.parse(this.getProductionRule("writeOracleBlobToOutputStream")));
      } else {
         sb.append(this.parse(this.getProductionRule("writeGenericBlobToOutputStream")));
      }

      return sb.toString();
   }

   public String perhapsUpdateBlob() throws CodeGenerationException {
      return this.bean.getDatabaseType() == 4 ? this.parse(this.getProductionRule("updateBlob")) : "";
   }

   public String setBlobClobForCreate() {
      if (this.bean.hasBlobClobColumn() && this.bean.getDatabaseType() == 1) {
         StringBuffer sb = new StringBuffer();
         String[] names = (String[])this.cmpFieldNames.toArray(new String[0]);
         int i = 0;

         for(int len = names.length; i < len; ++i) {
            String field = names[i];
            if (this.bean.hasCmpColumnType(field)) {
               this.curField = names[i];
               this.curTableName = this.bean.getTableForCmpField(this.curField);
               this.curTableIndex = this.bean.tableIndex(this.curTableName);
               if (this.bean.isBlobCmpColumnTypeForField(field)) {
                  sb.append("\n" + this.setBlobClobForOutputMethodName() + "(" + this.conVar() + "," + this.pkVar() + ");");
               } else if (this.bean.isClobCmpColumnTypeForField(field)) {
                  sb.append(EOL + "if(!" + this.pmVar() + ".perhapsUseSetStringForClobForOracle()){" + EOL);
                  sb.append("// Using 3 step procedure to insert Clob column as the  setStringForClob API is not supported by current driver " + EOL);
                  sb.append(this.setBlobClobForOutputMethodName() + "(" + this.conVar() + "," + this.pkVar() + ");" + EOL);
                  sb.append("}" + EOL);
               }
            }
         }

         return sb.toString();
      } else {
         return "";
      }
   }

   public String setBlobClobForStore() {
      if (!this.bean.hasBlobClobColumn()) {
         return "";
      } else {
         StringBuffer sb = new StringBuffer();
         String[] names = (String[])this.cmpFieldNames.toArray(new String[0]);
         int i = 0;

         for(int len = names.length; i < len; ++i) {
            String field = names[i];
            if (this.bean.hasCmpColumnType(field) && (this.bean.isBlobCmpColumnTypeForField(field) || this.bean.isClobCmpColumnTypeForField(field))) {
               this.curField = names[i];
               this.curTableName = this.bean.getTableForCmpField(this.curField);
               this.curTableIndex = this.bean.tableIndex(this.curTableName);
               sb.append(EOL);
               sb.append("if (" + this.isModifiedVar() + "[" + this.bean.getIsModifiedIndex(field) + "]) {" + EOL);
               sb.append("if(" + this.debugEnabled() + ") " + this.debugSay() + "(\"setting(\"+this+\") '" + field + "' using column \" +" + this.numVar() + " + \". Value is \" + this." + field + ");" + EOL);
               sb.append(this.setBlobClobForOutputMethodName() + "(" + this.conVar() + "," + this.pkVar() + ");\n");
               sb.append("}").append(EOL);
            }
         }

         return sb.toString();
      }
   }

   public String perhapsDeclareBlobClobCountVar() {
      return this.bean.hasBlobClobColumn() ? "int " + this.blobClobCountVar() + " = 0;" : "";
   }

   public String perhapsResetBlobClobCountVar() {
      return this.bean.hasBlobClobColumn() ? this.blobClobCountVar() + " = 0;" : "";
   }

   public String blobClobCountVarOrZero() {
      return this.bean.hasBlobClobColumn() ? this.blobClobCountVar() : "0";
   }

   public String perhapsByteArrayIsSerializedToBlob() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      String curField = this.fieldNameForField();
      Class curFieldClass = this.bean.getCmpFieldClass(curField);
      if (ClassUtils.isByteArray(curFieldClass) && !this.bean.getByteArrayIsSerializedToOracleBlob()) {
         sb.append("byte[] outByteArray = ").append(curField).append(";");
         sb.append(EOL);
      } else {
         sb.append(this.parse(this.getProductionRule("convertFieldToByteArray")));
      }

      return sb.toString();
   }

   public String perhapsByteArrayIsDeserializedFromBlob() {
      StringBuffer sb = new StringBuffer();
      String curField = this.fieldNameForField();
      Class curFieldClass = this.bean.getCmpFieldClass(curField);
      if (ClassUtils.isByteArray(curFieldClass) && !this.bean.getByteArrayIsSerializedToOracleBlob()) {
         sb.append(curField + " = inByteArray;");
      } else {
         sb.append("ByteArrayInputStream bais = new ByteArrayInputStream(inByteArray, 0, length);").append(EOL);
         sb.append("ObjectInputStream ois = new ObjectInputStream(bais);").append(EOL);
         sb.append("try {").append(EOL);
         sb.append(curField + " = (" + this.fieldClassForCmpField() + ") ois.readObject();").append(EOL);
         sb.append("} catch (ClassNotFoundException cnfe) {").append(EOL).append(EOL);
         sb.append("if (" + this.debugEnabled() + ") {").append(EOL);
         sb.append(this.debugSay() + "(\"ClassNotFoundException for Blob-Clob\" + cnfe.getMessage());").append(EOL);
         sb.append("}").append(EOL);
         sb.append("throw cnfe;").append(EOL);
         sb.append("}").append(EOL);
         sb.append("bais.close();").append(EOL);
         sb.append("ois.close();").append(EOL);
      }

      return sb.toString();
   }

   private boolean useVersionOrTimestampCheckingForBlobClob(String tableName) {
      return this.bean.hasBlobClobColumn() && this.bd.isOptimistic() && (this.bean.getVerifyColumns(tableName).equalsIgnoreCase("version") || this.bean.getVerifyColumns(tableName).equalsIgnoreCase("timestamp"));
   }

   public String setPrimaryKeyParamsArray() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      sb.append("int " + this.numVar() + " = 0;" + EOL);

      for(int i = 0; i < this.bean.tableCount(); ++i) {
         this.curTableIndex = i;
         this.curTableName = this.bean.tableAt(i);
         this.resetParams();
         sb.append(this.numVar() + " = 1;" + EOL);
         sb.append(this.setPrimaryKeyParamsUsingNum());
         sb.append(this.perhapsSetSnapshotParameters());
      }

      return sb.toString();
   }

   public String setPrimaryKeyParamsForTableIndex(int tableIndex) {
      String[] fieldNames = (String[])((String[])this.pkFieldNames.toArray(new String[0]));
      return this.preparedStatementBindings(fieldNames, this.pkVar(), false, this.bd.hasComplexPrimaryKey(), false, false, this.stmtArrayElement(tableIndex));
   }

   public String setPrimaryKeyParams() {
      String[] fieldNames = (String[])((String[])this.pkFieldNames.toArray(new String[0]));
      return this.preparedStatementBindings(fieldNames, this.pkVar(), false, this.bd.hasComplexPrimaryKey(), false, false);
   }

   public String setPrimaryKeyParamsUsingNum() {
      String[] fieldNames = (String[])((String[])this.pkFieldNames.toArray(new String[0]));
      return this.preparedStatementBindings(fieldNames, this.pkVar(), false, this.bd.hasComplexPrimaryKey(), true, false, this.stmtArrayElement(this.curTableIndex));
   }

   private String preparedStatementBindings(String[] fields, String obj, boolean objectIsBean, boolean objIsCompound, boolean useNumVar, boolean takeSnapshot) {
      return this.preparedStatementBindings(fields, obj, objectIsBean, objIsCompound, useNumVar, takeSnapshot, this.stmtVar());
   }

   private String preparedStatementBindings(String[] fields, String obj, boolean objectIsBean, boolean objIsCompound, boolean useNumVar, boolean takeSnapshot, String stmtVar) {
      StringBuffer sb = new StringBuffer();
      sb.append("\n");
      int i = 0;

      for(int len = fields.length; i < len; ++i) {
         String field = fields[i];
         String index;
         if (useNumVar) {
            index = this.numVar();
         } else {
            index = String.valueOf(this.preparedStatementParamIndex);
         }

         this.addPreparedStatementBinding(sb, field, obj, index, objectIsBean, objIsCompound, takeSnapshot && !this.bd.getPrimaryKeyFieldNames().contains(field), false, stmtVar);
         if (useNumVar) {
            sb.append(this.numVar() + "++;" + EOL);
         } else {
            ++this.preparedStatementParamIndex;
         }

         if (i < len - 1) {
            sb.append(EOL);
         }
      }

      return sb.toString();
   }

   private void addPreparedStatementBinding(StringBuffer sb, String field, String obj, String paramIdx, boolean objectIsBean, boolean objIsCompound, boolean takeSnapshot, boolean useMethodLevelVariable) {
      this.addPreparedStatementBinding(sb, field, obj, paramIdx, objectIsBean, objIsCompound, takeSnapshot, useMethodLevelVariable, this.stmtVar());
   }

   private void addPreparedStatementBinding(StringBuffer sb, String field, String obj, String paramIdx, boolean objectIsBean, boolean objIsCompound, boolean takeSnapshot, boolean useMethodLevelVariable, String stmtVar) {
      if (this.bean.isClobCmpColumnTypeForField(field) && this.bean.getDatabaseType() == 1) {
         sb.append("// Clob column " + field + " is already binded in PreparedStatement as first variable, but we still need to increment local variable to keep correct indexing." + EOL);
      } else {
         if (debugLogger.isDebugEnabled()) {
            debug("Adding a prepared statement binding: ");
            debug("\t\tfield = " + field);
            debug("\t\tobj = " + obj);
            debug("\t\tparamIdx = " + paramIdx);
            debug("\t\tobjIsCompound = " + objIsCompound);
         }

         String value = null;
         if (objectIsBean && this.bean.hasCmpField(field)) {
            value = this.getCmpField(obj, field);
         } else if (objIsCompound) {
            value = obj + "." + field;
         } else {
            value = obj;
         }

         Class fieldClass = this.getVariableClass(field);

         assert fieldClass != null;

         if (!fieldClass.isPrimitive()) {
            this.addNullCheck(sb, value, field, paramIdx, stmtVar);
         }

         this.preparedStatementBindingBody(sb, obj, field, fieldClass, paramIdx, objectIsBean, objIsCompound, takeSnapshot, useMethodLevelVariable, RDBMSUtils.isOracleNLSDataType(this.bean, field), stmtVar);
         if (!fieldClass.isPrimitive()) {
            sb.append("}" + EOL);
            if (takeSnapshot && this.doSnapshot(field) && !this.bean.isClobCmpColumnTypeForField(field) && !this.bean.isBlobCmpColumnTypeForField(field)) {
               sb.append("else {" + EOL);
               sb.append(obj + "." + CodeGenUtils.snapshotNameForVar(field) + " = null;" + EOL);
               sb.append("}" + EOL);
            }
         }

      }
   }

   private void preparedStatementBindingBody(StringBuffer sb, String obj, String field, Class fieldClass, String paramIdx, boolean objectIsBean, boolean objIsCompound, boolean takeSnapshot, boolean useMethodLevelVariable, boolean isOracleNLSDataType) {
      this.preparedStatementBindingBody(sb, obj, field, fieldClass, paramIdx, objectIsBean, objIsCompound, takeSnapshot, useMethodLevelVariable, isOracleNLSDataType, this.stmtVar());
   }

   private void preparedStatementBindingBody(StringBuffer sb, String obj, String field, Class fieldClass, String paramIdx, boolean objectIsBean, boolean objIsCompound, boolean takeSnapshot, boolean useMethodLevelVariable, boolean isOracleNLSDataType, String stmtVar) {
      String colType = this.bean.getCmpColumnTypeForField(field);
      if (this.bean.hasCmpColumnType(field) && (colType.equalsIgnoreCase("Blob") || colType.equalsIgnoreCase("Clob")) && this.bean.getDatabaseType() == 1) {
         sb.append("  ");
      } else {
         boolean accessUsingMethod = objectIsBean && !this.bd.isBeanClassAbstract() && this.bean.hasCmpField(field);
         String value = null;
         if (accessUsingMethod) {
            value = obj + "." + "__WL_super_" + MethodUtils.getMethodName(field) + "()";
         } else if (objIsCompound) {
            value = obj + "." + field;
         } else {
            value = obj;
         }

         boolean isCharArrayMappedToString = this.bean.isCharArrayMappedToString(fieldClass);
         if (isCharArrayMappedToString) {
            fieldClass = Character.TYPE;
         }

         String arrayName;
         if (!this.bean.isValidSQLType(fieldClass)) {
            sb.append("ByteArrayOutputStream bstr = new ByteArrayOutputStream();" + EOL);
            sb.append("ObjectOutputStream ostr = new ObjectOutputStream(bstr);" + EOL);
            if (EJBHome.class.isAssignableFrom(fieldClass)) {
               sb.append("HomeHandle handle = " + value + ".getHomeHandle();" + EOL);
               sb.append("ostr.writeObject(handle);" + EOL);
            } else if (EJBObject.class.isAssignableFrom(fieldClass)) {
               sb.append("Handle handle = " + value + ".getHandle();" + EOL);
               sb.append("ostr.writeObject(handle);" + EOL);
            } else {
               sb.append("ostr.writeObject(" + value + ");" + EOL);
            }

            arrayName = null;
            if (useMethodLevelVariable) {
               arrayName = this.byteArrayVar(field);
            } else {
               arrayName = "byteArray";
               sb.append("byte[] ");
            }

            sb.append(arrayName + " = bstr.toByteArray();" + EOL);
            sb.append("if (" + this.debugEnabled() + ") {" + EOL);
            sb.append(this.debugSay() + "(\"writing bytes: \" + " + arrayName + ");" + EOL);
            sb.append("if (" + arrayName + "!=null) {" + EOL);
            sb.append(this.debugSay() + "(\"bytes length: \" + " + arrayName + ".length);" + EOL);
            sb.append("}" + EOL);
            sb.append("}" + EOL);
            if (takeSnapshot && this.doSnapshot(field) && !this.bean.isBlobCmpColumnTypeForField(field)) {
               sb.append(obj + "." + CodeGenUtils.snapshotNameForVar(field) + " = " + arrayName + ";" + EOL);
            }

            if (!"SybaseBinary".equalsIgnoreCase(this.bean.getCmpColumnTypeForField(field)) && !this.perhapsSybaseBinarySetForAnyCmpField()) {
               sb.append("InputStream inputStream  = new ByteArrayInputStream(" + arrayName + ");" + EOL);
               sb.append(stmtVar + ".setBinaryStream(" + paramIdx + ", inputStream, " + arrayName + ".length);" + EOL);
            } else {
               sb.append(stmtVar + ".setBytes(" + paramIdx + "," + arrayName + ");" + EOL);
            }
         } else {
            if (takeSnapshot && this.doSnapshot(field)) {
               sb.append(this.takeSnapshotForVar(obj, field, accessUsingMethod));
            }

            if (ClassUtils.isByteArray(fieldClass)) {
               if (!"SybaseBinary".equalsIgnoreCase(this.bean.getCmpColumnTypeForField(field)) && !this.perhapsSybaseBinarySetForAnyCmpField()) {
                  sb.append("InputStream inputStream  = new ByteArrayInputStream(" + value + ");" + EOL);
                  sb.append(stmtVar + ".setBinaryStream(" + paramIdx + ", inputStream, " + value + ".length);" + EOL);
               } else {
                  sb.append(stmtVar + ".setBytes(" + paramIdx + "," + value + ");" + EOL);
               }
            } else {
               arrayName = StatementBinder.getStatementTypeNameForClass(fieldClass);
               if (String.class.equals(fieldClass) && "LongString".equals(this.bean.getCmpColumnTypeForField(field))) {
                  sb.append("java.io.StringReader stringReader  = new java.io.StringReader(" + value + ");" + EOL);
                  sb.append(stmtVar + ".setCharacterStream(" + paramIdx + ", stringReader, " + value + ".length());" + EOL);
               } else if (String.class.equals(fieldClass) && "SQLXML".equals(this.bean.getCmpColumnTypeForField(field))) {
                  sb.append("java.sql.SQLXML sqlXml  = " + stmtVar + ".getConnection().createSQLXML();" + EOL);
                  sb.append("sqlXml.setString(" + value + ");" + EOL);
                  sb.append(stmtVar + ".setSQLXML(" + paramIdx + ", sqlXml);" + EOL);
               } else {
                  if (isOracleNLSDataType) {
                     sb.append("if(").append(stmtVar).append(" instanceof oracle.jdbc.OraclePreparedStatement) {" + EOL);
                     sb.append("((oracle.jdbc.OraclePreparedStatement)").append(stmtVar).append(").setFormOfUse(").append(paramIdx).append(", oracle.jdbc.OraclePreparedStatement.FORM_NCHAR);").append(EOL);
                     sb.append("}" + EOL);
                  }

                  sb.append(stmtVar);
                  sb.append(".set" + arrayName + "(");
                  sb.append(paramIdx).append(", ");
                  if (objIsCompound) {
                     if (fieldClass == Character.TYPE) {
                        if (isCharArrayMappedToString) {
                           sb.append("new String(" + value + ")");
                        } else {
                           sb.append("String.valueOf(" + value + ")");
                        }
                     } else if (fieldClass == Character.class) {
                        sb.append("String.valueOf(" + value + ".charValue())");
                     } else if (fieldClass == Date.class) {
                        sb.append("new java.sql.Timestamp(");
                        sb.append(value);
                        sb.append(".getTime())");
                     } else {
                        sb.append(MethodUtils.convertToPrimitive(fieldClass, value));
                     }
                  } else if (fieldClass == Character.TYPE) {
                     if (isCharArrayMappedToString) {
                        sb.append("new String(" + obj + ")");
                     } else {
                        sb.append("String.valueOf(" + obj + ")");
                     }
                  } else if (fieldClass == Character.class) {
                     sb.append("String.valueOf(" + obj + ".charValue())");
                  } else if (fieldClass == Date.class) {
                     sb.append("new java.sql.Timestamp(");
                     sb.append(obj + ".getTime())");
                  } else {
                     sb.append(MethodUtils.convertToPrimitive(fieldClass, obj));
                  }

                  sb.append(");" + EOL);
               }

               sb.append("if (" + this.debugEnabled() + ") {" + EOL);
               sb.append(this.debugSay() + "(\"paramIdx :\"+" + paramIdx + "+\" binded with value :\"+" + value + ");" + EOL);
               sb.append("}" + EOL);
            }
         }

      }
   }

   private boolean perhapsSybaseBinarySetForAnyCmpField() {
      boolean isSybaseBinarySet = false;
      Iterator iter = this.bean.getCmpFieldNames().iterator();

      while(iter.hasNext()) {
         String cmpField = (String)iter.next();
         if ("SybaseBinary".equalsIgnoreCase(this.bean.getCmpColumnTypeForField(cmpField))) {
            isSybaseBinarySet = true;
            break;
         }
      }

      return isSybaseBinarySet;
   }

   public String setUpdateBeanParams() {
      String[] names = (String[])this.cmpFieldNames.toArray(new String[0]);
      StringBuffer sb = new StringBuffer();
      List fkfieldNameList = this.bean.getForeignKeyFieldNames();

      String colName;
      String fkFieldName;
      for(int i = 0; i < names.length; ++i) {
         if (!this.bean.isBlobCmpColumnTypeForField(names[i]) && !this.bean.isClobCmpColumnTypeForField(names[i]) && !this.bd.getPrimaryKeyFieldNames().contains(names[i])) {
            int fTableIndex = this.bean.getTableIndexForCmpField(names[i]);

            assert fTableIndex >= 0;

            if (fTableIndex == this.curTableIndex) {
               String table = this.bean.getTableForCmpField(names[i]);
               colName = this.bean.getColumnForCmpFieldAndTable(names[i], table);
               if (!this.bean.hasOptimisticColumn(table) || !this.bean.getOptimisticColumn(table).equals(colName)) {
                  String isFkColsNullable = "";
                  if (fkfieldNameList != null) {
                     fkFieldName = null;
                     Iterator iterator = fkfieldNameList.iterator();

                     while(iterator.hasNext()) {
                        fkFieldName = (String)iterator.next();
                        if (this.bean.getForeignKeyColNames(fkFieldName).contains(colName)) {
                           isFkColsNullable = this.perhapsIsFkColsNullableCheck(fkFieldName);
                           break;
                        }
                     }
                  }

                  sb.append("if (" + this.isModifiedVar() + "[" + this.bean.getIsModifiedIndex(names[i]) + "] " + isFkColsNullable + ") {" + EOL);
                  sb.append("if(" + this.debugEnabled() + ") " + this.debugSay() + "(\"setting(\"+this+\") '" + names[i] + "' using column \" +" + this.numVar() + " + \". Value is \" +" + this.getCmpField(names[i]) + ");" + EOL);
                  this.addPreparedStatementBinding(sb, names[i], "this", this.numVar(), true, true, false, !this.bd.getPrimaryKeyFieldNames().contains(names[i]), this.stmtArrayElement(this.curTableIndex));
                  sb.append(this.numVar() + "++;" + EOL);
                  sb.append("};" + EOL + EOL);
               }
            }
         }
      }

      Iterator fieldNames = this.bean.getForeignKeyFieldNames().iterator();

      while(true) {
         String fieldName;
         int fTableIndex;
         do {
            do {
               do {
                  if (!fieldNames.hasNext()) {
                     return sb.toString();
                  }

                  fieldName = (String)fieldNames.next();
               } while(!this.bean.containsFkField(fieldName));
            } while(this.bean.isForeignCmpField(fieldName));

            fTableIndex = this.bean.getTableIndexForCmrf(fieldName);
            Debug.assertion(fTableIndex >= 0);
         } while(fTableIndex != this.curTableIndex);

         colName = this.bean.getTableForCmrField(fieldName);
         Iterator colNames = this.bean.getForeignKeyColNames(fieldName).iterator();

         while(colNames.hasNext()) {
            fkFieldName = (String)colNames.next();
            String varName = this.bean.variableForField(fieldName, colName, fkFieldName);
            sb.append("if (" + this.isModifiedVar() + "[" + this.bean.getIsModifiedIndex(fieldName) + "]" + this.perhapsIsFkColsNullableCheck(fieldName) + ") {" + EOL);
            sb.append("if(" + this.debugEnabled() + ") " + this.debugSay() + "(\"setting(\"+this+\") '" + varName + "' using column \" +" + this.numVar() + " + \". Value is \" + this." + varName + ");" + EOL);
            this.addPreparedStatementBinding(sb, varName, "this", this.numVar(), true, true, false, true, this.stmtArrayElement(this.curTableIndex));
            sb.append(this.numVar() + "++;" + EOL);
            sb.append("};" + EOL);
         }
      }
   }

   public String perhapsVerifyOptimistic() {
      assert this.curGroup != null;

      StringBuffer buffer = new StringBuffer();
      Set tableNames = this.bean.getTableNamesForGroup(this.curGroup.getName());
      Iterator iterator = tableNames.iterator();

      while(iterator.hasNext()) {
         String tableName = (String)iterator.next();
         if (this.bean.hasOptimisticColumn(tableName) && this.bean.getTriggerUpdatesOptimisticColumn(tableName)) {
            String columnName = this.bean.getOptimisticColumn(tableName);
            String fieldName = this.bean.getCmpField(tableName, columnName);
            buffer.append("if (this.");
            buffer.append(fieldName);
            buffer.append(" == null) {");
            buffer.append(EOL);
            buffer.append("Loggable l = EJBLogger.logoptimisticColumnIsNullLoggable(\"");
            buffer.append(this.bean.getEjbName());
            buffer.append("\", \"");
            buffer.append(tableName);
            buffer.append("\", \"");
            buffer.append(columnName);
            buffer.append("\");");
            buffer.append(EOL);
            buffer.append("throw new EJBException(l.getMessageText());");
            buffer.append(EOL);
            buffer.append("}");
            buffer.append(EOL);
         }
      }

      return buffer.toString();
   }

   public String perhapsSetOptimisticColumnParam() {
      StringBuffer sb = new StringBuffer();
      String table = this.bean.tableAt(this.curTableIndex);
      if (this.bean.hasOptimisticColumn(table) && !this.bean.getTriggerUpdatesOptimisticColumn(table)) {
         String colName = this.bean.getOptimisticColumn(table);
         String fieldName = this.bean.getCmpField(table, colName);
         sb.append("if (" + this.isModifiedVar() + "[" + this.bean.getIsModifiedIndex(fieldName) + "]) {" + EOL);
         sb.append("if(" + this.debugEnabled() + ") " + this.debugSay() + "(\"setting(\"+this+\") '" + fieldName + "' using column \" +" + this.numVar() + " + \". Value is \" + " + this.getCmpField(fieldName) + ");" + EOL);
         this.addPreparedStatementBinding(sb, fieldName, "this", this.numVar(), true, true, false, !this.bd.getPrimaryKeyFieldNames().contains(fieldName), this.stmtArrayElement(this.curTableIndex));
         sb.append(this.numVar() + "++;" + EOL);
         sb.append("};" + EOL + EOL);
      }

      return sb.toString();
   }

   public String assignGroupColumnsToThis() throws CodeGenerationException {
      return this.assignGroupColumns("this", false);
   }

   public String assignGroupColumnsToBean() throws CodeGenerationException {
      return this.assignGroupColumns(this.beanVar(), true);
   }

   public String assignGroupColumns(String target, boolean addPkFields) throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      List variables = new ArrayList();
      Set cmpColumns = new HashSet();
      Set cmpFields = new TreeSet();
      if (addPkFields) {
         variables.addAll(this.bd.getPrimaryKeyFieldNames());
         sb.append("if (" + this.pkVar() + " == null) {" + EOL);
         this.assignToVars(sb, target, true, variables, 1, true);
         sb.append("} else {" + EOL);
         sb.append(this.beanVar() + ".__WL_setPrimaryKey((" + this.pk_class() + ") " + this.pkVar() + ");" + EOL);
         sb.append("}" + EOL);
      }

      Set groupCmpFieldNamesSet = new TreeSet(this.curGroup.getCmpFields());
      Iterator groupCmpFieldNames = groupCmpFieldNamesSet.iterator();

      while(groupCmpFieldNames.hasNext()) {
         String cmpFieldName = (String)groupCmpFieldNames.next();
         if (!variables.contains(cmpFieldName)) {
            variables.add(cmpFieldName);
         }
      }

      cmpFields.addAll(variables);
      Iterator cmpFieldsIter = cmpFields.iterator();

      while(cmpFieldsIter.hasNext()) {
         String cmpField = (String)cmpFieldsIter.next();
         cmpColumns.add(this.bean.getCmpColumnForField(cmpField));
      }

      Iterator cmrFieldsIter = this.curGroup.getCmrFields().iterator();

      String cmrField;
      String fkTable;
      String cmrVar;
      while(cmrFieldsIter.hasNext()) {
         cmrField = (String)cmrFieldsIter.next();
         fkTable = this.bean.getTableForCmrField(cmrField);
         Iterator colNames = this.bean.getForeignKeyColNames(cmrField).iterator();

         while(colNames.hasNext()) {
            cmrVar = (String)colNames.next();
            if (!cmpColumns.contains(cmrVar)) {
               variables.add(this.bean.variableForField(cmrField, fkTable, cmrVar));
            }
         }
      }

      if (addPkFields) {
         variables.removeAll(this.bd.getPrimaryKeyFieldNames());
         this.assignToVars(sb, target, true, variables, 1 + this.bd.getPrimaryKeyFieldNames().size(), true);
      } else {
         this.assignToVars(sb, target, true, variables, 1, true);
      }

      cmpFieldsIter = cmpFields.iterator();

      while(cmpFieldsIter.hasNext()) {
         cmrField = (String)cmpFieldsIter.next();
         sb.append(target + "." + this.isLoadedVar() + "[" + this.bean.getIsModifiedIndex(cmrField) + "]");
         sb.append(" = true;" + EOL);
      }

      cmrFieldsIter = this.curGroup.getCmrFields().iterator();

      while(cmrFieldsIter.hasNext()) {
         cmrField = (String)cmrFieldsIter.next();
         fkTable = this.bean.getTableForCmrField(cmrField);
         String cmrColumn = (String)this.bean.getForeignKeyColNames(cmrField).iterator().next();
         if (!cmpColumns.contains(cmrColumn)) {
            cmrVar = this.bean.variableForField(cmrField, fkTable, cmrColumn);
            sb.append(target + "." + this.isLoadedVar() + "[" + this.bean.getIsModifiedIndex((String)this.variableToField.get(cmrVar)) + "]");
            sb.append(" = true;" + EOL);
         }
      }

      sb.append(target + "." + this.beanIsLoadedVar() + " = true;");
      return sb.toString();
   }

   public String assignCMRFieldFKColumns(String target, String cmrField) throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      List variables = new ArrayList();
      String fkTable = this.bean.getTableForCmrField(cmrField);
      Iterator colNames = this.bean.getForeignKeyColNames(cmrField).iterator();

      while(colNames.hasNext()) {
         String colName = (String)colNames.next();
         variables.add(this.bean.variableForField(cmrField, fkTable, colName));
      }

      this.assignToVars(sb, target, true, variables, 1, true);

      for(Iterator cmpFieldsIter = variables.iterator(); cmpFieldsIter.hasNext(); sb.append(" = true;" + EOL)) {
         String cmpField = (String)cmpFieldsIter.next();
         if (this.bean.getIsModifiedIndex(cmpField) != null) {
            sb.append(target + "." + this.isLoadedVar() + "[" + this.bean.getIsModifiedIndex(cmpField) + "]");
         } else {
            sb.append(target + "." + this.isLoadedVar() + "[" + this.bean.getIsModifiedIndex(cmrField) + "]");
         }
      }

      return sb.toString();
   }

   public String assignCMRFieldPKColumns(String target) throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      List variables = new ArrayList();
      variables.addAll(this.bd.getPrimaryKeyFieldNames());
      this.assignToVars(sb, target, true, variables, 1, true);
      Iterator cmpFieldsIter = variables.iterator();

      while(cmpFieldsIter.hasNext()) {
         String cmpField = (String)cmpFieldsIter.next();
         sb.append(target + "." + this.isLoadedVar() + "[" + this.bean.getIsModifiedIndex(cmpField) + "]");
         sb.append(" = true;" + EOL);
      }

      return sb.toString();
   }

   public String allFieldsCount() {
      return String.valueOf(this.bean.getFieldCount());
   }

   public String refresh_bean_from_key() {
      return this.isContainerManagedBean ? "loadByPrimaryKey(ctx);" : "((" + this.ejbClass.getName() + ")(ctx.getBean())).ejbFindByPrimaryKey(pk);";
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
               sb.append(this.parse(this.getProductionRule("home_method")));
            } catch (NoSuchMethodException var5) {
               throw new AssertionError(var5);
            }
         }
      }

      return sb.toString();
   }

   private String homeToBeanName(String prefix, String m) {
      StringBuffer sb = new StringBuffer(prefix + m);
      sb.setCharAt(prefix.length(), Character.toUpperCase(sb.charAt(prefix.length())));
      return sb.toString();
   }

   public String getEJBObject() {
      return this.bd.hasLocalClientView() ? "getEJBLocalObject" : "getEJBObject";
   }

   public String EJBObjectForField() {
      assert this.curField != null;

      CMPBeanDescriptor rbd = this.bean.getRelatedDescriptor(this.curField);
      return rbd.hasLocalClientView() ? "javax.ejb.EJBLocalObject" : "javax.ejb.EJBObject";
   }

   public String findByPrimaryKeyForField() {
      assert this.curField != null;

      CMPBeanDescriptor rbd = this.bean.getRelatedDescriptor(this.curField);
      return rbd.hasLocalClientView() ? "localFindByPrimaryKey" : "remoteFindByPrimaryKey";
   }

   public String scalarFinderForField() {
      assert this.curField != null;

      CMPBeanDescriptor rbd = this.bean.getRelatedDescriptor(this.curField);
      return rbd.hasLocalClientView() ? "localScalarFinder" : "remoteScalarFinder";
   }

   public String scalarFinder(Finder f) {
      RDBMSBean rrb = f.getSelectBeanTarget();
      CMPBeanDescriptor rbd = rrb.getCMPBeanDescriptor();
      if (f.hasLocalResultType()) {
         return rbd.hasLocalClientView() ? "localScalarFinder" : "remoteScalarFinder";
      } else {
         assert rbd.hasRemoteClientView();

         return "remoteScalarFinder";
      }
   }

   public String collectionFinder(Finder f) {
      RDBMSBean rrb = f.getSelectBeanTarget();
      CMPBeanDescriptor rbd = rrb.getCMPBeanDescriptor();
      if (f.hasLocalResultType()) {
         return rbd.hasLocalClientView() ? "localCollectionFinder" : "remoteCollectionFinder";
      } else {
         assert rbd.hasRemoteClientView();

         return "remoteCollectionFinder";
      }
   }

   public String setFinder(Finder f) {
      RDBMSBean rrb = f.getSelectBeanTarget();
      CMPBeanDescriptor rbd = rrb.getCMPBeanDescriptor();
      if (f.hasLocalResultType()) {
         return rbd.hasLocalClientView() ? "localSetFinder" : "remoteSetFinder";
      } else {
         assert rbd.hasRemoteClientView();

         return "remoteSetFinder";
      }
   }

   public String registerInvalidationBean() {
      StringBuffer sb = new StringBuffer();
      if (this.bi.getInvalidationTargetEJBName() != null) {
         sb.append(this.pmVar() + ".registerModifiedBean(" + this.ctxVar() + ".getPrimaryKey());" + EOL);
      }

      return sb.toString();
   }

   public String readOnlyFinderOneToOneGetterBody() {
      CMPBeanDescriptor rbd = this.bean.getRelatedDescriptor(this.curField);
      StringBuffer sb = new StringBuffer();
      if (rbd.getConcurrencyStrategy() == 4) {
         sb.append("TransactionManager tms = TxHelper.getTransactionManager();\n");
         sb.append("tms.suspend();\n");
         sb.append("tms.begin();\n");
         sb.append("Transaction tx = tms.getTransaction();\n");
         sb.append("try { " + EOL);
      }

      sb.append(this.fieldVarForField() + " = (" + this.classNameForField() + ")" + this.bmVarForField() + "." + this.scalarFinderForField() + "(\n");
      sb.append(this.finderVarForField() + ",new Object[]{" + this.ctxVar() + ".getPrimaryKey()});\n");
      if (rbd.getConcurrencyStrategy() == 4) {
         sb.append("} finally { " + EOL);
         sb.append("// Dont need to worry for rollback call etc, " + EOL);
         sb.append("// as this is readonly tx and this is used only for a finder call. " + EOL);
         sb.append("tx.commit();\n");
         sb.append("} " + EOL);
      }

      return sb.toString();
   }

   public String readOnlyOneToOneGetterBody_fkOwner() {
      CMPBeanDescriptor rbd = this.bean.getRelatedDescriptor(this.curField);
      StringBuffer sb = new StringBuffer();
      if (rbd.getConcurrencyStrategy() == 4) {
         sb.append("TransactionManager tms = TxHelper.getTransactionManager();\n");
         sb.append("tms.suspend();\n");
         sb.append("tms.begin();\n");
         sb.append("Transaction tx = tms.getTransaction();\n");
         sb.append("try { " + EOL);
      }

      sb.append(this.fieldVarForField() + " = (" + this.classNameForField() + ")" + this.finderInvokerForField() + "." + this.findByPrimaryKeyForField() + "(\n");
      sb.append(this.finderParamForField() + this.fkVarForField() + ");\n");
      if (rbd.getConcurrencyStrategy() == 4) {
         sb.append("} finally { " + EOL);
         sb.append("// Dont need to worry for rollback call etc, " + EOL);
         sb.append("// as this is readonly tx and this is used only for a finder call. " + EOL);
         sb.append("tx.commit();\n");
         sb.append("} " + EOL);
      }

      return sb.toString();
   }

   public String readOnlyOneToManyGetterBody_fkOwner() {
      CMPBeanDescriptor rbd = this.bean.getRelatedDescriptor(this.curField);
      StringBuffer sb = new StringBuffer();
      if (rbd.getConcurrencyStrategy() == 4) {
         sb.append("TransactionManager tms = TxHelper.getTransactionManager();" + EOL);
         sb.append("tms.suspend();" + EOL);
         sb.append("tms.begin();" + EOL);
         sb.append("Transaction tx = tms.getTransaction();" + EOL);
         sb.append("try { " + EOL);
      }

      sb.append(this.fieldVarForField() + " = (" + this.classNameForField() + ")" + this.finderInvokerForField() + "." + this.findByPrimaryKeyForField() + "(" + EOL);
      sb.append(this.finderParamForField() + this.fkVarForField() + ");" + EOL);
      if (rbd.getConcurrencyStrategy() == 4) {
         sb.append("} finally { " + EOL);
         sb.append("// Dont need to worry for rollback call etc, " + EOL);
         sb.append("// as this is readonly tx and this is used only for a finder call. " + EOL);
         sb.append("tx.commit(); " + EOL);
         sb.append("} " + EOL);
      }

      return sb.toString();
   }

   public String readOnlyResumeTx() {
      CMPBeanDescriptor rbd = this.bean.getRelatedDescriptor(this.curField);
      if (rbd.getConcurrencyStrategy() == 4) {
         StringBuffer sb = new StringBuffer();
         sb.append("TxHelper.getTransactionManager().resume(orgTx);\n");
         return sb.toString();
      } else {
         return "";
      }
   }

   public String preReadOnlyStateChange() {
      return this.bd.isReadOnly() ? "synchronized(this) {" + EOL : "";
   }

   public String postReadOnlyStateChange() {
      return this.bd.isReadOnly() ? "}" + EOL : "";
   }

   public String perhapsUpdateLastLoadTimeDueToEJBStore() {
      if (this.bi.isOptimistic() && this.bi.getCacheBetweenTransactions()) {
         StringBuffer sb = new StringBuffer();
         sb.append("else {");
         sb.append(EOL);
         sb.append("  __WL_setLastLoadTime(System.currentTimeMillis());");
         sb.append(EOL);
         sb.append("}");
         sb.append(EOL);
         return sb.toString();
      } else {
         return "";
      }
   }

   public String perhapsUpdateLastLoadTime() {
      if (!this.bi.isReadOnly() && (!this.bi.isOptimistic() || !this.bi.getCacheBetweenTransactions())) {
         return "";
      } else {
         StringBuffer sb = new StringBuffer();
         sb.append("if(!__WL_beanIsLoaded()) {");
         sb.append(EOL);
         sb.append("  __WL_setLastLoadTime(System.currentTimeMillis());");
         sb.append(EOL);
         sb.append("}");
         sb.append(EOL);
         return sb.toString();
      }
   }

   public String isReadOnly() {
      CMPBeanDescriptor rbd = this.bean.getRelatedDescriptor(this.curField);
      return !rbd.isReadOnly() && rbd.getConcurrencyStrategy() != 4 ? "false" : "true";
   }

   public String declare_bean_interface_methods() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer(200);
      sb.append(super.declare_bean_interface_methods());
      List undeclaredFields = new ArrayList();
      undeclaredFields.addAll(this.bean.getCmrFieldNames());
      undeclaredFields.removeAll(this.bean.getDeclaredFieldNames());
      sb.append(this.declareCmrVariableGetterMethods(undeclaredFields));
      sb.append(this.declareCmrVariableSetterMethods(undeclaredFields));
      sb.append(this.declareRelationshipFinderMethods());
      sb.append(this.declareRelationshipSelectMethods());
      sb.append("public Object ");
      sb.append(this.getPKFromRSMethodName());
      sb.append("Instance(java.sql.ResultSet rs, java.lang.Integer offset, ClassLoader cl)");
      sb.append(EOL);
      sb.append("throws java.sql.SQLException, java.lang.Exception;");
      sb.append(EOL);
      Iterator fieldGroups = this.bean.getFieldGroups().iterator();

      while(fieldGroups.hasNext()) {
         this.curGroup = (FieldGroup)fieldGroups.next();
         sb.append("public void ");
         sb.append(this.loadGroupFromRSMethodName(this.curGroup));
         sb.append(EOL);
         sb.append("(java.sql.ResultSet rs, java.lang.Integer offset, ");
         sb.append("Object " + this.pkVar() + ", ");
         sb.append(this.getGeneratedBeanInterfaceName());
         sb.append(" var) ");
         sb.append(EOL);
         sb.append("throws java.sql.SQLException, java.lang.Exception;");
         sb.append(EOL);
      }

      Iterator cmrFields = this.bean.getAllCmrFields().iterator();

      while(cmrFields.hasNext()) {
         String cmrField = (String)cmrFields.next();
         sb.append("public void " + this.loadCMRFieldFromRSMethodName(cmrField) + EOL + "(java.sql.ResultSet rs, java.lang.Integer offset, " + this.getGeneratedBeanInterfaceName() + " var) " + EOL);
         sb.append("throws java.sql.SQLException, java.lang.Exception;" + EOL);
      }

      sb.append("public boolean ");
      sb.append(this.existsMethodName());
      sb.append("(Object key);");
      sb.append(EOL);
      sb.append("public boolean __WL_beanIsLoaded();");
      sb.append(EOL);
      Iterator it = this.finderList.iterator();

      while(it.hasNext()) {
         Finder f = (Finder)it.next();
         if (f.getQueryType() == 0 && f instanceof EjbqlFinder) {
            sb.append(this.getFinderMethodDeclaration(f, this.bd.getPrimaryKeyClass()));
            sb.append(";");
            sb.append(EOL);
         }
      }

      return sb.toString();
   }

   public String declareRelationshipSelectMethods() {
      StringBuffer sb = new StringBuffer();
      Iterator it = this.ejbSelectInternalList.iterator();

      while(true) {
         Finder finder;
         do {
            if (!it.hasNext()) {
               return sb.toString();
            }

            finder = (Finder)it.next();
         } while(finder.getQueryType() != 4 && finder.getQueryType() != 2);

         sb.append(this.getEjbSelectInternalMethodDeclaration(finder, this.bd.getPrimaryKeyClass()));
         sb.append(";");
         sb.append(EOL);
      }
   }

   public String declareRelationshipFinderMethods() {
      StringBuffer sb = new StringBuffer();
      Iterator relFinders = this.bean.getRelationFinders();

      while(relFinders.hasNext()) {
         Finder finder = (Finder)relFinders.next();
         if (finder.getQueryType() == 0) {
            sb.append(this.getFinderMethodDeclaration(finder, this.bd.getPrimaryKeyClass()));
            sb.append(";");
            sb.append(EOL);
         }
      }

      return sb.toString();
   }

   public String declareCmrVariableGetterMethods(List undeclaredFields) {
      StringBuffer sb = new StringBuffer();
      Iterator all = undeclaredFields.iterator();

      String fkField;
      while(all.hasNext()) {
         this.curField = (String)all.next();
         Class cmrFieldClass = this.bean.getCmrFieldClass(this.curField);
         fkField = ClassUtils.classToJavaSourceType(cmrFieldClass);
         sb.append("public ");
         sb.append(fkField + " ");
         sb.append(this.getMethodNameForField());
         sb.append("();");
         sb.append(EOL);
      }

      Iterator fkFields = this.bean.getForeignKeyFieldNames().iterator();

      while(true) {
         do {
            do {
               if (!fkFields.hasNext()) {
                  return sb.toString();
               }

               fkField = (String)fkFields.next();
            } while(!this.bean.isOneToManyRelation(fkField));
         } while(this.bean.isRemoteField(fkField));

         this.curField = fkField;
         String fkTable = this.bean.getTableForCmrField(fkField);
         Iterator fkColumns = this.bean.getForeignKeyColNames(fkField).iterator();

         while(fkColumns.hasNext()) {
            String fkColumn = (String)fkColumns.next();
            if (!this.bean.hasCmpField(fkTable, fkColumn)) {
               String fkVariable = this.bean.variableForField(fkField, fkTable, fkColumn);
               Class fkClass = this.bean.getForeignKeyColClass(fkField, fkColumn);
               String className = ClassUtils.classToJavaSourceType(fkClass);
               sb.append("public ");
               sb.append(className + " ");
               sb.append(MethodUtils.getMethodName(fkVariable));
               sb.append("();" + EOL);
            }
         }

         this.curField = null;
      }
   }

   public String declareCmrVariableSetterMethods(List undeclaredFields) {
      StringBuffer sb = new StringBuffer();
      Iterator all = this.bean.getCmrFieldNames().iterator();

      while(all.hasNext()) {
         this.curField = (String)all.next();
         Class cmrFieldClass = this.bean.getCmrFieldClass(this.curField);
         String fieldClassName = ClassUtils.classToJavaSourceType(cmrFieldClass);
         if (this.bean.isOneToManyRelation(this.curField)) {
            sb.append("public void ");
            sb.append(CodeGenUtils.cacheRelationshipMethodName(this.curField));
            sb.append("(Object obj);");
            if (undeclaredFields.contains(this.curField)) {
               sb.append("public void ");
               sb.append(this.setMethodNameForField());
               sb.append("(");
               sb.append(fieldClassName);
               sb.append(" ");
               sb.append(this.curField);
               sb.append(");");
               sb.append(EOL);
            }

            if (this.bean.getRelatedMultiplicity(this.curField).equals("One")) {
               sb.append("public void ");
               sb.append(this.varPrefix() + this.setMethodNameForField());
               sb.append("(");
               sb.append(fieldClassName);
               sb.append(" ");
               sb.append(this.curField);
               sb.append(", boolean ejbStore");
               sb.append(", boolean remove);");
               sb.append(EOL);
            }
         } else if (this.bean.isOneToOneRelation(this.curField)) {
            sb.append("public void ");
            sb.append(this.doSetMethodNameForField());
            sb.append("(");
            sb.append(fieldClassName);
            sb.append(" ");
            sb.append(this.curField);
            sb.append(");");
            sb.append(EOL);
            sb.append("public void ");
            sb.append(this.setRestMethodNameForField());
            sb.append("(");
            sb.append(fieldClassName);
            sb.append(" ");
            sb.append(this.curField);
            sb.append(", int methodState);");
            sb.append(EOL);
            sb.append("public void ");
            sb.append(this.setCmrIsLoadedMethodName(this.curField));
            sb.append("(boolean b);");
            sb.append(EOL);
            sb.append("public void ");
            sb.append(CodeGenUtils.cacheRelationshipMethodName(this.curField));
            sb.append("(Object obj);");
            sb.append(EOL);
         }
      }

      return sb.toString();
   }

   public String doPreparedStmtBindingForClob(String field, int paramIndexForClob, String obj, boolean objIsCompound, boolean useNumVar, boolean takeSnapshot, String stmtVar) {
      StringBuffer sb = new StringBuffer();
      sb.append(EOL);
      String value = null;
      if (objIsCompound) {
         value = obj + "." + field;
      } else {
         value = obj;
      }

      boolean var10000;
      if (takeSnapshot && !this.bd.getPrimaryKeyFieldNames().contains(field)) {
         var10000 = true;
      } else {
         var10000 = false;
      }

      Class fieldClass = this.getVariableClass(field);
      if (!fieldClass.isPrimitive()) {
         this.addNullCheck(sb, value, field, "" + paramIndexForClob, stmtVar);
      }

      sb.append("    if (" + this.debugEnabled() + ") {" + EOL);
      sb.append("      Debug.say(\"Adding a prepared statement binding: \");" + EOL);
      sb.append("      Debug.say(\"\\t\\tfield = \" +" + field + ");" + EOL);
      sb.append("      Debug.say(\"\\t\\tobj = \" +" + obj + ");" + EOL);
      sb.append("      Debug.say(\"\\t\\tparamIdx = \" +" + paramIndexForClob + ");" + EOL);
      sb.append("      Debug.say(\"\\t\\tobjIsCompound = \" +" + objIsCompound + ");" + EOL);
      sb.append("    }" + EOL);
      if (this.bean.isNClobCmpColumnTypeForField(field)) {
         sb.append("    if(" + stmtVar + " instanceof oracle.jdbc.OraclePreparedStatement) {" + EOL);
         sb.append("      ((oracle.jdbc.OraclePreparedStatement)" + stmtVar + ").setFormOfUse(" + paramIndexForClob + ", oracle.jdbc.OraclePreparedStatement.FORM_NCHAR);" + EOL);
         sb.append("    }" + EOL);
      }

      sb.append("      java.lang.reflect.Method[] meths = " + stmtVar + ".getClass().getMethods();\n");
      sb.append("      java.lang.reflect.Method meth = null;\n");
      sb.append("      for (int i = 0; i < meths.length; i++) {\n");
      sb.append("        if (meths[i].getName().equalsIgnoreCase(\"setStringForClob\"))\n");
      sb.append("        {\n" + EOL);
      sb.append("          meth = meths[i];\n");
      sb.append("          break;\n");
      sb.append("        }\n");
      sb.append("      }\n");
      sb.append("      if (meth != null) {\n");
      sb.append("        meth.invoke(" + stmtVar + ", new Object[]{ Integer.valueOf(\"" + paramIndexForClob + "\") , " + value + " });\n");
      sb.append("      }\n");
      if (!fieldClass.isPrimitive()) {
         sb.append("}" + EOL);
      }

      return sb.toString();
   }

   public String perhapsDeclareQueryCachingVars() {
      StringBuffer sb = new StringBuffer();
      if (this.shouldImplementQueryCaching(this.curFinder)) {
         sb.append("QueryCacheKey ").append(this.queryCacheKeyVar());
         sb.append(" = new QueryCacheKey(\"");
         sb.append(this.curFinder.getFinderIndex()).append("\", new Object[] {");
         sb.append(this.getParametersAsArray(this.curFinder)).append("}, ");
         sb.append("(TTLManager)").append(this.pmVar()).append(".getBeanManager(), ");
         if (Collection.class.isAssignableFrom(this.curFinder.getReturnClassType())) {
            sb.append("QueryCacheKey.RET_TYPE_COLLECTION);").append(EOL);
         } else {
            sb.append("QueryCacheKey.RET_TYPE_SINGLETON);").append(EOL);
         }

         if (this.curFinder.isMultiFinder()) {
            sb.append("    Collection ").append(this.queryCacheElementsVar());
            sb.append(" = new ArrayList();").append(EOL);
         } else {
            sb.append("    QueryCacheElement ").append(this.queryCacheElementsVar());
            sb.append(" = null;").append(EOL);
         }
      }

      boolean isRelationshipCaching = this.isRelationshipCaching(this.curFinder);
      if (isRelationshipCaching) {
         sb.append("MultiMap ").append(this.beanMapVar()).append(" = ");
         sb.append("null;").append(EOL);
      }

      return sb.toString();
   }

   public String perhapsPutInQueryCache() {
      StringBuffer sb = new StringBuffer();
      boolean isRelationshipCaching = this.isRelationshipCaching(this.curFinder);
      if (this.currFinderLoadsQueryCachingEnabledCMRFields) {
         sb.append("Iterator iterator = ").append(this.beanMapVar());
         sb.append(".keySet().iterator();").append(EOL);
         sb.append("while (iterator.hasNext()) {").append(EOL);
         sb.append("CMPBean __WL_relBean = (CMPBean)iterator.next();").append(EOL);
         sb.append("List list = ").append(this.beanMapVar()).append(".get(__WL_relBean);");
         sb.append(EOL);
         sb.append("for (int i=0; i<list.size(); i++) {").append(EOL);
         sb.append("__WL_relBean.").append(this.cmrFieldQueryCachingMethodName());
         if (this.shouldImplementQueryCaching(this.curFinder)) {
            sb.append("((String)list.get(i), ").append(this.queryCacheKeyVar());
         } else {
            sb.append("((String)list.get(i), null");
         }

         sb.append(");").append(EOL);
         sb.append("}").append(EOL);
         sb.append("}").append(EOL);
      }

      if (this.shouldImplementQueryCaching(this.curFinder)) {
         sb.append("((TTLManager)").append(this.pmVar()).append(".getBeanManager())");
         sb.append(".putInQueryCache(").append(this.queryCacheKeyVar());
         sb.append(", ").append(this.queryCacheElementsVar()).append(");").append(EOL);
      }

      this.currFinderLoadsQueryCachingEnabledCMRFields = false;
      return sb.toString();
   }

   private String queryCacheKeyVar() {
      return this.varPrefix() + "qckey";
   }

   private String queryCacheElementVar() {
      return this.varPrefix() + "querycacheelement";
   }

   private String queryCacheElementsVar() {
      return this.varPrefix() + "qcelements";
   }

   private String beanMapVar() {
      return this.varPrefix() + "beanMap";
   }

   private boolean shouldImplementQueryCaching(Finder finder) {
      return !finder.isFindByPrimaryKey() ? finder.isQueryCachingEnabled() : false;
   }

   private String getParametersAsArray(Finder finder) {
      Class[] paramTypes = finder.getParameterClassTypes();
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < paramTypes.length; ++i) {
         if (i > 0) {
            sb.append(", ");
         }

         if (paramTypes[i].equals(Boolean.TYPE)) {
            sb.append("new Boolean(param").append(i).append(")");
         } else if (paramTypes[i].equals(Character.TYPE)) {
            sb.append("new Character(param").append(i).append(")");
         } else if (paramTypes[i].equals(Byte.TYPE)) {
            sb.append("new Byte(param").append(i).append(")");
         } else if (paramTypes[i].equals(Short.TYPE)) {
            sb.append("new Short(param").append(i).append(")");
         } else if (paramTypes[i].equals(Integer.TYPE)) {
            sb.append("new Integer(param").append(i).append(")");
         } else if (paramTypes[i].equals(Long.TYPE)) {
            sb.append("new Long(param").append(i).append(")");
         } else if (paramTypes[i].equals(Float.TYPE)) {
            sb.append("new Float(param").append(i).append(")");
         } else if (paramTypes[i].equals(Double.TYPE)) {
            sb.append("new Double(param").append(i).append(")");
         } else {
            sb.append("param").append(i);
         }
      }

      return sb.toString();
   }

   private String generateCMRFieldFinderMethodName(String fieldName) {
      return CodeGenUtils.getCMRFieldFinderMethodName(this.bean, fieldName);
   }

   private boolean isRelationshipCaching(Finder finder) {
      if (finder instanceof EjbqlFinder) {
         EjbqlFinder ejbqlFinder = (EjbqlFinder)finder;
         String cachingName = ejbqlFinder.getCachingName();
         if (cachingName != null) {
            RelationshipCaching rc = this.bean.getRelationshipCaching(cachingName);
            if (rc != null) {
               return rc.getCachingElements().iterator().hasNext();
            }
         }
      }

      return false;
   }

   private static void debug(String s) {
      debugLogger.debug("[RDBMSCodeGenerator] " + s);
   }
}
