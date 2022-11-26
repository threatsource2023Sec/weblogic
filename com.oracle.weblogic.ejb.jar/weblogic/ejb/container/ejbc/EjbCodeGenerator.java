package weblogic.ejb.container.ejbc;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;
import javax.ejb.EntityContext;
import weblogic.ejb.container.deployer.NamingConvention;
import weblogic.ejb.container.ejbc.codegen.MethodSignature;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.EntityBeanInfo;
import weblogic.ejb.container.persistence.spi.CMPBeanDescriptor;
import weblogic.ejb.container.utils.ClassUtils;
import weblogic.ejb.container.utils.EJBMethodsUtil;
import weblogic.utils.Getopt2;
import weblogic.utils.PlatformConstants;
import weblogic.utils.annotation.BeaSynthetic.Helper;
import weblogic.utils.compiler.CodeGenerationException;
import weblogic.utils.compiler.CodeGenerator;
import weblogic.utils.reflect.MethodText;

public abstract class EjbCodeGenerator extends CodeGenerator {
   private static final String EJB_HOME = "ejbHome";
   private boolean keepgenerated = false;
   private Getopt2 opts = null;
   protected final List generatedOutputs = new LinkedList();
   protected Output currentOutput;
   protected Method[] beanHomeMethods;
   protected Method[] remoteMethods;
   protected Method[] createMethods;
   protected Method[] findMethods;
   protected Method[] homeMethods;
   protected Method[] localMethods;
   protected Method[] localCreateMethods;
   protected Method[] localFindMethods;
   protected Method[] localHomeMethods;
   protected Method method;
   protected MethodSignature methodSignature;
   private MethodText mt = new MethodText();
   protected EntityBeanInfo bi;
   protected NamingConvention nc;
   protected Class ejbClass;
   protected Class homeInterfaceClass;
   protected Class remoteInterfaceClass;
   protected Class localHomeInterfaceClass;
   protected Class localInterfaceClass;
   protected Class primaryKeyClass;
   protected boolean isContainerManagedBean;
   protected boolean hasLocalClientView;
   protected boolean hasRemoteClientView;
   protected boolean hasDeclaredRemoteHome;
   protected boolean hasDeclaredLocalHome;
   protected short methodType = 0;
   private Set cmpGetterSetterMethods;

   public EjbCodeGenerator() {
   }

   public EjbCodeGenerator(Getopt2 opts) {
      super(opts);
      this.opts = opts;
      this.keepgenerated = opts.hasOption("keepgenerated");
   }

   protected Enumeration outputs(List l) throws Exception {
      this.generatedOutputs.clear();
      Vector outputs = new Vector();
      BeanInfo bi = (BeanInfo)l.get(0);
      this.addOutputs(outputs, bi, new NamingConvention(bi.getBeanClassName(), bi.getEJBName()));
      return outputs.elements();
   }

   protected final void interpretBeanInfo(BeanInfo bi) {
      EntityBeanInfo ebi = (EntityBeanInfo)bi;
      this.setBooleans(ebi);
      this.setClasses(ebi);
      this.setBeanHomeMethods(ebi);
      if (this.hasDeclaredRemoteHome) {
         this.remoteMethods = EJBMethodsUtil.getRemoteMethods(this.remoteInterfaceClass, true);
         this.createMethods = EJBMethodsUtil.getCreateMethods(this.homeInterfaceClass);
         this.findMethods = EJBMethodsUtil.getFindMethods(this.homeInterfaceClass);
         this.setHomeMethods(ebi);
      }

      if (this.hasDeclaredLocalHome) {
         this.localMethods = EJBMethodsUtil.getLocalMethods(this.localInterfaceClass, true);
         this.localCreateMethods = EJBMethodsUtil.getLocalCreateMethods(this.localHomeInterfaceClass);
         this.localFindMethods = EJBMethodsUtil.getLocalFindMethods(this.localHomeInterfaceClass);
         this.setLocalHomeMethods(ebi);
         if (!ebi.getIsBeanManagedPersistence() && ebi.getCMPInfo().uses20CMP()) {
            this.setCmpGettersAndSetters(ebi, this.localInterfaceClass);
         }
      }

   }

   protected void prepare(CodeGenerator.Output output) throws EJBCException, ClassNotFoundException {
      this.currentOutput = (Output)output;
      this.generatedOutputs.add(this.currentOutput);
      if (this.currentOutput.getBeanInfo() == null) {
         this.nc = this.currentOutput.getNamingConvention();
      } else if (this.bi != this.currentOutput.getBeanInfo()) {
         this.bi = (EntityBeanInfo)this.currentOutput.getBeanInfo();
         this.nc = this.currentOutput.getNamingConvention();
         this.interpretBeanInfo(this.bi);
      }
   }

   private void setBooleans(EntityBeanInfo ebi) {
      this.isContainerManagedBean = ebi.getCMPInfo() != null;
      this.hasRemoteClientView = ebi.hasRemoteClientView();
      this.hasLocalClientView = ebi.hasLocalClientView();
      this.hasDeclaredRemoteHome = ebi.hasDeclaredRemoteHome();
      this.hasDeclaredLocalHome = ebi.hasDeclaredLocalHome();
   }

   private final void setClasses(EntityBeanInfo ebi) {
      this.ejbClass = ebi.getBeanClass();
      if (this.hasLocalClientView) {
         this.localInterfaceClass = ebi.getLocalInterfaceClass();
         this.localHomeInterfaceClass = ebi.getLocalHomeInterfaceClass();
      }

      if (this.hasRemoteClientView) {
         this.remoteInterfaceClass = ebi.getRemoteInterfaceClass();
         this.homeInterfaceClass = ebi.getHomeInterfaceClass();
      }

      this.primaryKeyClass = ebi.getPrimaryKeyClass();
      if (ebi.isUnknownPrimaryKey() && !ebi.getIsBeanManagedPersistence()) {
         CMPBeanDescriptor bd = ebi.getCMPInfo().getCMPBeanDescriptor(ebi.getEJBName());
         this.primaryKeyClass = bd.getPrimaryKeyClass();
      }

   }

   private void setCmpGettersAndSetters(EntityBeanInfo ebi, Class localIfcClass) {
      CMPBeanDescriptor cmpDesc = (CMPBeanDescriptor)ebi.getCMPInfo().getBeanMap().get(ebi.getEJBName());
      this.cmpGetterSetterMethods = new HashSet();
      Iterator var4 = ebi.getCMPInfo().getAllContainerManagedFieldNames().iterator();

      while(var4.hasNext()) {
         String cmpField = (String)var4.next();
         Method getter = cmpDesc.getGetterMethod(localIfcClass, cmpField);
         Method setter = cmpDesc.getSetterMethod(localIfcClass, cmpField);
         if (getter != null) {
            this.cmpGetterSetterMethods.add(getter);
         }

         if (setter != null) {
            this.cmpGetterSetterMethods.add(setter);
         }
      }

   }

   private void setBeanHomeMethods(BeanInfo bi) {
      List mdsBeanHomeV = new ArrayList();
      Method[] var3 = bi.getBeanClass().getMethods();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Method m = var3[var5];
         if (m.getName().startsWith("ejbHome") && !Modifier.isVolatile(m.getModifiers())) {
            mdsBeanHomeV.add(m);
         }
      }

      if (mdsBeanHomeV.size() > 0) {
         this.beanHomeMethods = (Method[])mdsBeanHomeV.toArray(new Method[mdsBeanHomeV.size()]);
      } else {
         this.beanHomeMethods = null;
      }

   }

   private void setHomeMethods(BeanInfo bi) {
      if (this.beanHomeMethods != null) {
         List mdsHomeV = new ArrayList();
         Method[] var3 = this.beanHomeMethods;
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Method m = var3[var5];
            String mName = m.getName();
            char ch = mName.charAt("ejbHome".length());
            String homeMethodName = Character.toLowerCase(ch) + mName.substring("ejbHome".length() + 1);

            try {
               Method homeMethod = this.homeInterfaceClass.getMethod(homeMethodName, m.getParameterTypes());
               mdsHomeV.add(homeMethod);
            } catch (NoSuchMethodException var17) {
               MethodSignature bmSig = new MethodSignature(m, bi.getBeanClass());
               Method[] var12 = this.homeInterfaceClass.getMethods();
               int var13 = var12.length;

               for(int var14 = 0; var14 < var13; ++var14) {
                  Method intfMethod = var12[var14];
                  MethodSignature biSig = new MethodSignature(intfMethod, this.homeInterfaceClass);
                  if (MethodSignature.equalsMethodsBySig(bmSig, biSig)) {
                     mdsHomeV.add(intfMethod);
                  }
               }
            }
         }

         if (mdsHomeV.size() > 0) {
            this.homeMethods = (Method[])mdsHomeV.toArray(new Method[mdsHomeV.size()]);
         } else {
            this.homeMethods = null;
         }
      } else {
         this.homeMethods = null;
      }

   }

   private void setLocalHomeMethods(BeanInfo bi) {
      if (this.beanHomeMethods != null) {
         List mdsHomeV = new ArrayList();
         Method[] var3 = this.beanHomeMethods;
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Method m = var3[var5];
            String mName = m.getName();
            char ch = mName.charAt("ejbHome".length());
            String homeMethodName = Character.toLowerCase(ch) + mName.substring("ejbHome".length() + 1);

            try {
               Method homeMethod = this.localHomeInterfaceClass.getMethod(homeMethodName, m.getParameterTypes());
               mdsHomeV.add(homeMethod);
            } catch (NoSuchMethodException var17) {
               MethodSignature bmSig = new MethodSignature(m, bi.getBeanClass());
               Method[] var12 = this.localHomeInterfaceClass.getMethods();
               int var13 = var12.length;

               for(int var14 = 0; var14 < var13; ++var14) {
                  Method intfMethod = var12[var14];
                  MethodSignature biSig = new MethodSignature(intfMethod, this.localHomeInterfaceClass);
                  if (MethodSignature.equalsMethodsBySig(bmSig, biSig)) {
                     mdsHomeV.add(intfMethod);
                  }
               }
            }
         }

         if (mdsHomeV.size() > 0) {
            this.localHomeMethods = (Method[])mdsHomeV.toArray(new Method[mdsHomeV.size()]);
         } else {
            this.localHomeMethods = null;
         }
      } else {
         this.localHomeMethods = null;
      }

   }

   protected abstract void addOutputs(Vector var1, BeanInfo var2, NamingConvention var3) throws EJBCException;

   public String ejb_callbacks() throws CodeGenerationException {
      StringBuilder sb = new StringBuilder();
      sb.append(this.parse(this.getProductionRule("common_ejb_callbacks")));
      sb.append(this.parse(this.getProductionRule("entity_callbacks")));
      return sb.toString();
   }

   public String method_state() {
      String methodName = this.method.getName();
      if (methodName.startsWith("ejbCreate")) {
         return "STATE_EJB_CREATE";
      } else if (methodName.startsWith("ejbPostCreate")) {
         return "STATE_EJB_POSTCREATE";
      } else if (methodName.startsWith("ejbFind")) {
         return "STATE_EJBFIND";
      } else if (methodName.startsWith("ejbHome")) {
         return "STATE_EJBHOME";
      } else {
         return methodName.startsWith("ejbTimeout") ? "STATE_EJBTIMEOUT" : "STATE_BUSINESS_METHOD";
      }
   }

   public String bean_postcreate_methods() throws CodeGenerationException {
      StringBuilder sb = new StringBuilder();
      Method[] var2 = this.ejbClass.getMethods();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Method m = var2[var4];
         String methodName = m.getName();
         if (methodName.startsWith("ejbPostCreate")) {
            this.setMethod(m, (short)0);
            sb.append(this.parse(this.getProductionRule("home_method")));
         }
      }

      return sb.toString();
   }

   public String bean_home_methods() throws CodeGenerationException {
      StringBuilder sb = new StringBuilder();
      Method[] var2 = EJBMethodsUtil.getBeanHomeClassMethods(this.ejbClass);
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Method m = var2[var4];
         this.setMethod(m, (short)0, (Class)this.ejbClass);
         sb.append(this.parse(this.getProductionRule("home_method")));
      }

      return sb.toString();
   }

   public String local_preInvoke() throws CodeGenerationException {
      return this.parse(this.getProductionRule("localPreInvoke"));
   }

   public String local_postInvokeTxRetry() throws CodeGenerationException {
      return this.parse(this.getProductionRule("localPostInvokeTxRetry"));
   }

   public String local_postInvokeCleanup() throws CodeGenerationException {
      return this.parse(this.getProductionRule("localPostInvokeCleanup"));
   }

   public String beanmethod_throws_clause() {
      try {
         Method bm = this.ejbClass.getMethod(this.method.getName(), this.method.getParameterTypes());
         return this.method_throws_clause(bm);
      } catch (NoSuchMethodException var2) {
         throw new AssertionError(this.method + " method missing in bean class: " + this.ejbClass.getName() + " exception: " + var2);
      }
   }

   public String setentitycontext_throws_clause() {
      try {
         return this.method_throws_clause(this.ejbClass.getMethod("setEntityContext", EntityContext.class));
      } catch (NoSuchMethodException var2) {
         throw new AssertionError("setEntityContext(EntityContext) method missing in bean class: " + this.ejbClass.getName() + " exception: " + var2);
      }
   }

   public String unsetentitycontext_throws_clause() {
      try {
         return this.method_throws_clause(this.ejbClass.getMethod("unsetEntityContext", (Class[])null));
      } catch (NoSuchMethodException var2) {
         throw new AssertionError("unsetEntityContext() method missing in bean class: " + this.ejbClass.getName() + " exception: " + var2);
      }
   }

   public String ejbload_throws_clause() {
      try {
         return this.method_throws_clause(this.ejbClass.getMethod("ejbLoad", (Class[])null));
      } catch (NoSuchMethodException var2) {
         throw new AssertionError("ejbLoad() method missing in bean class: " + this.ejbClass.getName() + " exception: " + var2);
      }
   }

   public String ejbstore_throws_clause() {
      try {
         return this.method_throws_clause(this.ejbClass.getMethod("ejbStore", (Class[])null));
      } catch (NoSuchMethodException var2) {
         throw new AssertionError("ejbStore() method missing in bean class: " + this.ejbClass.getName() + " exception: " + var2);
      }
   }

   public String activate_throws_clause() {
      try {
         return this.method_throws_clause(this.ejbClass.getMethod("ejbActivate", (Class[])null));
      } catch (NoSuchMethodException var2) {
         throw new AssertionError("ejbActivate() method missing in bean class: " + this.ejbClass.getName() + " exception: " + var2);
      }
   }

   public String passivate_throws_clause() {
      try {
         return this.method_throws_clause(this.ejbClass.getMethod("ejbPassivate", (Class[])null));
      } catch (NoSuchMethodException var2) {
         throw new AssertionError("ejbPassivate() method missing in bean class: " + this.ejbClass.getName() + " exception: " + var2);
      }
   }

   public String remove_throws_clause() {
      try {
         return this.method_throws_clause(this.ejbClass.getMethod("ejbRemove", (Class[])null));
      } catch (NoSuchMethodException var2) {
         throw new AssertionError("ejbRemove() method missing in bean class: " + this.ejbClass.getName() + " exception: " + var2);
      }
   }

   public String method_throws_clause(Method m) {
      Class[] ex = m.getExceptionTypes();
      if (ex != null && ex.length != 0) {
         StringBuilder sb = new StringBuilder(200);
         sb.append("throws ");

         for(int i = 0; i < ex.length; ++i) {
            if (i != 0) {
               sb.append(",");
            }

            sb.append(this.arg_type(ex[i]));
         }

         return sb.toString();
      } else {
         return "";
      }
   }

   public String create_methods() throws CodeGenerationException {
      if (this.createMethods == null) {
         return "";
      } else {
         StringBuilder sb = new StringBuilder(200);
         Method[] var2 = this.createMethods;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Method createMethod = var2[var4];
            this.setMethod(createMethod, (short)1);
            sb.append(this.parse(this.getProductionRule("create_method_en")));
         }

         return sb.toString();
      }
   }

   public String local_create_methods() throws CodeGenerationException {
      if (this.localCreateMethods == null) {
         return "";
      } else {
         StringBuilder sb = new StringBuilder(200);
         Method[] var2 = this.localCreateMethods;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Method localCreateMethod = var2[var4];
            this.setMethod(localCreateMethod, (short)1, (Class)this.localHomeInterfaceClass);
            sb.append(this.parse(this.getProductionRule("create_method_en")));
         }

         return sb.toString();
      }
   }

   public String optional_finder_type() {
      if (this.method.getName().equals("findByPrimaryKey")) {
         return "";
      } else if (this.method.getReturnType().equals(this.remoteInterfaceClass)) {
         return ", weblogic.ejb.container.internal.EntityEJBHome.SCALAR_FINDER";
      } else if (this.method.getReturnType().equals(this.localInterfaceClass)) {
         return ", weblogic.ejb.container.internal.EntityEJBHome.SCALAR_FINDER";
      } else if (this.method.getReturnType().equals(Collection.class)) {
         return ", weblogic.ejb.container.internal.EntityEJBHome.COLL_FINDER";
      } else if (this.method.getReturnType().equals(Enumeration.class)) {
         return ", weblogic.ejb.container.internal.EntityEJBHome.ENUM_FINDER";
      } else {
         throw new AssertionError("Unrecognized finder, return type is :" + this.method.getReturnType());
      }
   }

   public String finder_name() {
      if (this.method.getName().equals("findByPrimaryKey")) {
         return "findByPrimaryKey";
      } else if (this.method.getReturnType().equals(this.remoteInterfaceClass)) {
         return "finder";
      } else if (this.method.getReturnType().equals(this.localInterfaceClass)) {
         return "finder";
      } else if (this.method.getReturnType().equals(Collection.class)) {
         return "finder";
      } else if (this.method.getReturnType().equals(Enumeration.class)) {
         return "finder";
      } else {
         throw new AssertionError("Unrecognized finder, return type is :" + this.method.getReturnType());
      }
   }

   public String finder_parameters() {
      return this.method.getName().equals("findByPrimaryKey") ? this.wrapped_method_parameters_without_types() : this.method_parameters_in_array();
   }

   public String find_methods() throws CodeGenerationException {
      if (this.findMethods == null) {
         return "";
      } else {
         StringBuilder sb = new StringBuilder(200);
         Method[] var2 = this.findMethods;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Method m = var2[var4];
            this.setMethod(m, (short)1);
            sb.append(this.parse(this.getProductionRule("finder")));
         }

         return sb.toString();
      }
   }

   public String local_find_methods() throws CodeGenerationException {
      if (this.localFindMethods == null) {
         return "";
      } else {
         StringBuilder sb = new StringBuilder(200);
         Method[] var2 = this.localFindMethods;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Method fm = var2[var4];
            this.setMethod(fm, (short)1, (Class)this.localHomeInterfaceClass);
            sb.append(this.parse(this.getProductionRule("finder")));
         }

         return sb.toString();
      }
   }

   public String home_methods() throws CodeGenerationException {
      if (this.homeMethods == null) {
         return "";
      } else {
         StringBuilder sb = new StringBuilder(200);
         Method[] var2 = this.homeMethods;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Method m = var2[var4];
            this.setMethod(m, (short)1);
            MethodSignature sig = new MethodSignature(m);
            sig.setModifiers(1);
            sb.append(sig + "{" + EOL);
            sb.append(this.parse(this.getProductionRule("home_method_body")));
            sb.append(EOL + "}" + EOL);
         }

         return sb.toString();
      }
   }

   public String local_home_methods() throws CodeGenerationException {
      if (this.localHomeMethods == null) {
         return "";
      } else {
         StringBuilder sb = new StringBuilder(200);
         Method[] var2 = this.localHomeMethods;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Method m = var2[var4];
            this.setMethod(m, (short)1);
            MethodSignature sig = new MethodSignature(m);
            sig.setModifiers(1);
            sb.append(sig + "{" + EOL);
            sb.append(this.parse(this.getProductionRule("home_method_body")));
            sb.append(EOL + "}" + EOL);
         }

         return sb.toString();
      }
   }

   public String declare_result() {
      Class returnTypeClass = this.method.getReturnType();
      if (returnTypeClass.getName().equals("void")) {
         return "// No return value";
      } else {
         StringBuilder sb = new StringBuilder(50);
         String returnTypeName = this.methodSignature.getReturnTypeName();
         Class returnType = ClassUtils.getPrimitiveClass(returnTypeName);
         if (returnType == null && returnTypeName != null) {
            try {
               returnType = Class.forName(returnTypeName, false, this.getClass().getClassLoader());
            } catch (ClassNotFoundException var6) {
            }
         }

         if (returnType != null) {
            sb.append(this.return_type(returnType) + " result = ");
            if (returnType.isPrimitive()) {
               if (returnType.getName().equals("boolean")) {
                  sb.append("false;");
               } else {
                  sb.append("0;");
               }
            } else {
               sb.append("null;");
            }
         } else {
            sb.append(returnTypeName + " result = null;");
         }

         return sb.toString();
      }
   }

   public String ejb_class_name() {
      return this.ejbClass.getName();
   }

   public String enum_exceptions() {
      StringBuilder sb = new StringBuilder(80);
      Class[] exceptions = this.method.getExceptionTypes();
      if (exceptions.length == 0) {
         return sb.toString();
      } else {
         Class[] var3 = exceptions;
         int var4 = exceptions.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Class c = var3[var5];
            if (!RemoteException.class.isAssignableFrom(c)) {
               sb.append("else if (e instanceof " + this.arg_type(c) + ") {" + EOL);
               sb.append("   throw (" + this.arg_type(c) + ") e;" + EOL + "}" + EOL);
            }
         }

         sb.append("else if ((e instanceof java.lang.RuntimeException) && (deploymentInfo.getExceptionInfo(");
         sb.append(EJBMethodsUtil.methodDescriptorPrefix(this.methodType));
         sb.append(this.method_sig());
         sb.append(".getMethod(), e).isAppException())) {");
         sb.append(EOL);
         sb.append("   throw (java.lang.RuntimeException) e;");
         sb.append(EOL);
         sb.append("}");
         return sb.toString();
      }
   }

   public String enum_exceptions_home_method() {
      Class[] exceptions = this.method.getExceptionTypes();
      if (exceptions.length == 0) {
         return "";
      } else {
         StringBuilder sb = new StringBuilder(80);
         boolean hasOneIf = false;
         Class[] var4 = exceptions;
         int var5 = exceptions.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Class c = var4[var6];
            if (!c.isAssignableFrom(RemoteException.class)) {
               if (hasOneIf) {
                  sb.append("else ");
               }

               sb.append("if (ee instanceof " + this.arg_type(c) + ") {" + EOL);
               sb.append("   throw (" + this.arg_type(c) + ") ee;" + EOL + "}");
               hasOneIf = true;
            }
         }

         return sb.toString();
      }
   }

   public String find_method_throws_clause() {
      return this.exceptions(this.method);
   }

   public String home_class_name() {
      return this.nc.getHomeClassName();
   }

   public String home_interface_name() {
      return this.convertToLegalInnerClassName(this.homeInterfaceClass);
   }

   public String local_home_interface_name() {
      return this.convertToLegalInnerClassName(this.localHomeInterfaceClass);
   }

   public String create_method_name() {
      return "ejbC" + this.method.getName().substring(1);
   }

   public String postCreate_method_name() {
      return this.postCreate_method_name(this.method);
   }

   public String postCreate_method_name(Method m) {
      String methodName = m.getName();
      return "ejbPostC" + methodName.substring(1, methodName.length());
   }

   public String ejbCreate_method_name(Method m) {
      String methodName = m.getName();
      return "ejbC" + methodName.substring(1, methodName.length());
   }

   public String method_return() {
      return this.arg_type(this.method.getReturnType());
   }

   public String capitalized_method_name() {
      String n = this.method.getName();
      return Character.toUpperCase(n.charAt(0)) + n.substring(1);
   }

   public String method_name() {
      return this.method.getName();
   }

   public String method_parameters_in_array() {
      StringBuilder sb = new StringBuilder();
      sb.append("new Object [] { ");
      sb.append(this.wrapped_method_parameters_without_types());
      sb.append("}");
      return sb.toString();
   }

   public String method_parameters() {
      this.mt.setOptions(513);
      return this.mt.toString();
   }

   private String classToStringForm(Class c) {
      return c.isArray() ? this.classToStringForm(c.getComponentType()) + "[]" : this.arg_type(c);
   }

   public String method_types_as_array() {
      Class[] p = this.method.getParameterTypes();
      if (p != null && p.length != 0) {
         StringBuilder sb = new StringBuilder();
         sb.append("new Class [] {");

         for(int i = 0; i < p.length; ++i) {
            String cName = this.classToStringForm(p[i]) + ".class";
            if (i == 0) {
               sb.append(cName);
            } else {
               sb.append(", " + cName);
            }
         }

         sb.append("}");
         return sb.toString();
      } else {
         return "new Class [] {}";
      }
   }

   public String method_parameters_without_types() {
      this.mt.setOptions(1);
      return this.mt.toString();
   }

   public String wrapped_method_parameters_without_types() {
      this.mt.setOptions(1024);
      return this.mt.toString();
   }

   public String method_sig() {
      this.mt.setOptions(128);
      String txt = this.mt.toString();
      if (this.methodType == 1) {
         txt = EJBMethodsUtil.homeClassMethodNameMapper(txt);
      }

      return txt;
   }

   public String method_signature_no_throws() {
      this.methodSignature.setAbstract(false);
      this.methodSignature.setPrintThrowsClause(false);
      return this.methodSignature.toString();
   }

   public String method_signature() {
      this.methodSignature.setAbstract(false);
      return this.methodSignature.toString();
   }

   public String method_signature_throws_remote_exception() {
      this.methodSignature.setAbstract(false);
      Class[] declared = this.methodSignature.getExceptionTypes();
      Class[] exceptions = new Class[declared.length + 1];
      boolean containRemoteException = false;

      for(int i = 0; i < declared.length; ++i) {
         exceptions[i] = declared[i];
         if (declared[i].equals(RemoteException.class)) {
            containRemoteException = true;
         }
      }

      if (containRemoteException) {
         this.methodSignature.setExceptionTypes(declared);
      } else {
         exceptions[declared.length] = RemoteException.class;
         this.methodSignature.setExceptionTypes(exceptions);
      }

      return this.methodSignature.toString();
   }

   public String ctor_throws_clause() {
      Constructor[] var1 = this.ejbClass.getConstructors();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Constructor c = var1[var3];
         Class[] p = c.getParameterTypes();
         if (p == null || p.length == 0) {
            return this.exceptions(c.getExceptionTypes());
         }
      }

      throw new AssertionError("Bean class: " + this.ejbClass + " does not have a no-arg constructor");
   }

   public String method_throws_clause() {
      return this.exceptions(this.method);
   }

   public String pk_class() {
      return this.primaryKeyClass.getName();
   }

   public String declare_static_eo_method_descriptors() {
      return this.declare_method_descriptors(this.remoteMethods, (short)0, true);
   }

   public String declare_static_elo_method_descriptors() {
      return this.declare_method_descriptors(this.localMethods, (short)0, true);
   }

   public String declare_create_method_descriptors() {
      return this.declare_method_descriptors(this.createMethods, (short)1);
   }

   public String declare_local_create_method_descriptors() {
      return this.declare_method_descriptors(this.localCreateMethods, (short)1);
   }

   public String declare_find_method_descriptors() {
      return this.declare_method_descriptors(this.findMethods, (short)1);
   }

   public String declare_local_find_method_descriptors() {
      return this.declare_method_descriptors(this.localFindMethods, (short)1);
   }

   private String declare_method_descriptors(Method[] m, short type) {
      return this.declare_method_descriptors(m, type, false);
   }

   private String declare_method_descriptors(Method[] methods, short type, boolean isStatic) {
      if (methods == null) {
         return "";
      } else {
         StringBuilder sb = new StringBuilder(200);
         Method[] var5 = methods;
         int var6 = methods.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            Method m = var5[var7];
            this.setMethod(m, type);
            this.declare_method_descriptor(isStatic, sb);
         }

         return sb.toString();
      }
   }

   public String declare_create_methods() throws CodeGenerationException {
      if (this.createMethods == null) {
         return "";
      } else {
         StringBuilder sb = new StringBuilder(200);
         Method[] var2 = this.createMethods;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Method m = var2[var4];
            this.setMethod(m, (short)1);
            sb.append(this.parse(this.getProductionRule("declare_create_method")));
            sb.append(this.parse(this.getProductionRule("declare_postCreate_method")));
         }

         return sb.toString();
      }
   }

   public String declare_local_create_methods() throws CodeGenerationException {
      if (this.localCreateMethods == null) {
         return "";
      } else {
         StringBuilder sb = new StringBuilder(200);
         Method[] var2 = this.localCreateMethods;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Method m = var2[var4];
            this.setMethod(m, (short)1, (Class)this.localHomeInterfaceClass);
            sb.append(this.parse(this.getProductionRule("declare_create_method")));
            sb.append(this.parse(this.getProductionRule("declare_postCreate_method")));
         }

         return sb.toString();
      }
   }

   public String declare_home_method_descriptors() throws CodeGenerationException {
      if (this.homeMethods == null) {
         return "";
      } else {
         StringBuilder sb = new StringBuilder(200);
         Method[] var2 = this.homeMethods;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Method m = var2[var4];
            this.setMethod(m, (short)1);
            sb.append(this.parse(this.getProductionRule("declare_home_method_descriptor")));
         }

         return sb.toString();
      }
   }

   public String declare_local_home_method_descriptors() throws CodeGenerationException {
      if (this.localHomeMethods == null) {
         return "";
      } else {
         StringBuilder sb = new StringBuilder(200);
         Method[] var2 = this.localHomeMethods;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Method m = var2[var4];
            this.setMethod(m, (short)1);
            sb.append(this.parse(this.getProductionRule("declare_home_method_descriptor")));
         }

         return sb.toString();
      }
   }

   private void declare_method_descriptor(boolean isStatic, StringBuilder sb) {
      sb.append(" public");
      if (isStatic) {
         sb.append(" static");
      }

      sb.append(" weblogic.ejb.container.internal.MethodDescriptor ");
      sb.append(EJBMethodsUtil.methodDescriptorPrefix(this.methodType));
      sb.append(this.method_sig());
      sb.append(";" + PlatformConstants.EOL);
   }

   public String initialize_create_methods() throws CodeGenerationException {
      if (this.createMethods == null) {
         return "";
      } else {
         StringBuilder sb = new StringBuilder(200);
         Method[] var2 = this.createMethods;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Method m = var2[var4];
            this.setMethod(m, (short)1);
            sb.append(this.parse(this.getProductionRule("initialize_create_method")) + EOL);
            sb.append(this.parse(this.getProductionRule("initialize_postCreate_method")) + EOL);
         }

         return sb.toString();
      }
   }

   public String initialize_local_create_methods() throws CodeGenerationException {
      if (this.localCreateMethods == null) {
         return "";
      } else {
         StringBuilder sb = new StringBuilder(200);
         Method[] var2 = this.localCreateMethods;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Method m = var2[var4];
            this.setMethod(m, (short)1, (Class)this.localHomeInterfaceClass);
            sb.append(this.parse(this.getProductionRule("initialize_create_method")) + EOL);
            sb.append(this.parse(this.getProductionRule("initialize_postCreate_method")) + EOL);
         }

         return sb.toString();
      }
   }

   public String perhaps_initialize_create_methods() throws CodeGenerationException {
      return this.parse(this.getProductionRule("static_block_to_init_create_methods"));
   }

   public String perhaps_initialize_local_create_methods() throws CodeGenerationException {
      return this.parse(this.getProductionRule("static_block_to_init_create_methods"));
   }

   private List getAllMethods(Class cls) {
      List mdl = new ArrayList(Arrays.asList(cls.getMethods()));
      List volatileMethods = new ArrayList();
      List nonVolatileMethods = new ArrayList();
      Iterator var5 = mdl.iterator();

      while(true) {
         Method m;
         do {
            do {
               if (!var5.hasNext()) {
                  mdl.removeAll(volatileMethods);
                  nonVolatileMethods.addAll(mdl);
                  return nonVolatileMethods;
               }

               m = (Method)var5.next();
            } while(!Modifier.isVolatile(m.getModifiers()));
         } while(!m.toGenericString().equals(m.toString()));

         for(Class superC = cls.getSuperclass(); !superC.equals(Object.class); superC = superC.getSuperclass()) {
            if (!Modifier.isPublic(superC.getModifiers())) {
               try {
                  Method nm = superC.getMethod(m.getName(), m.getParameterTypes());
                  if (!Modifier.isVolatile(nm.getModifiers())) {
                     volatileMethods.add(m);
                     nonVolatileMethods.add(nm);
                     break;
                  }
               } catch (NoSuchMethodException var9) {
               }
            }
         }
      }
   }

   public String declare_bean_interface_methods() throws CodeGenerationException {
      StringBuilder sb = new StringBuilder(200);
      Class beanClass = this.bi.getBeanClass();
      List allMethods = new ArrayList();
      Iterator var4 = this.getAllMethods(this.bi.getBeanClass()).iterator();

      while(var4.hasNext()) {
         Method m = (Method)var4.next();
         if (!m.isBridge()) {
            allMethods.add(m);
         }
      }

      Comparator comp = new Comparator() {
         public int compare(Method m1, Method m2) {
            if (!m1.getName().equals(m2.getName())) {
               return m1.getName().compareTo(m2.getName());
            } else {
               Class[] params1 = m1.getParameterTypes();
               Class[] params2 = m2.getParameterTypes();
               if (params1.length != params2.length) {
                  return params1.length < params2.length ? -1 : 1;
               } else {
                  for(int i = 0; i < params1.length; ++i) {
                     if (params1[i] != params2[i]) {
                        return params1[i].getName().compareTo(params2[i].getName());
                     }
                  }

                  if (m1.isBridge() || m2.isBridge()) {
                     Class ret1 = m1.getReturnType();
                     Class ret2 = m2.getReturnType();
                     if (!ret1.equals(ret2)) {
                        return ret1.getName().compareTo(ret2.getName());
                     }
                  }

                  return 0;
               }
            }
         }
      };
      TreeSet unique = new TreeSet(comp);
      unique.addAll(allMethods);
      Iterator var6 = unique.iterator();

      while(var6.hasNext()) {
         Method m = (Method)var6.next();
         if (!m.isSynthetic() && !Helper.isBeaSyntheticMethod(m) && m.getDeclaringClass() != Object.class) {
            int modifiers = m.getModifiers();
            if (!Modifier.isStatic(modifiers) && !Modifier.isVolatile(modifiers)) {
               this.appendInterfaceMethodDeclaration(sb, m, beanClass);
            }
         }
      }

      return sb.toString();
   }

   public String extendsCMPBean() {
      return this.isContainerManagedBean ? ", weblogic.ejb.container.persistence.spi.CMPBean" : "";
   }

   private void appendInterfaceMethodDeclaration(StringBuilder sb, Method m, Class c) {
      MethodSignature sig = null;
      if (!m.toGenericString().equals(m.toString())) {
         sig = new MethodSignature(m, c);
      } else {
         sig = new MethodSignature(m);
      }

      sig.setFinal(false);
      sig.setNative(false);
      sb.append(sig + ";" + EOL);
   }

   public String remote_interface_methods() throws CodeGenerationException {
      return this.remote_interface_methods(this.remoteMethods, this.remoteInterfaceClass);
   }

   public String remote_interface_methods(Method[] methods, Class c) throws CodeGenerationException {
      StringBuilder sb = new StringBuilder(200);
      Method[] var4 = methods;
      int var5 = methods.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Method m = var4[var6];
         this.setMethod(m, (short)0, (Class)c);
         sb.append(this.parse(this.getProductionRule("remote_interface_method")));
      }

      return sb.toString();
   }

   public String local_interface_methods() throws CodeGenerationException {
      return this.local_interface_methods(this.localMethods);
   }

   private String local_interface_methods(Method[] methods) throws CodeGenerationException {
      StringBuilder sb = new StringBuilder(200);
      Method[] var3 = methods;
      int var4 = methods.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Method m = var3[var5];
         this.setMethod(m, (short)0, (Class)this.localInterfaceClass);
         sb.append(this.parse(this.getProductionRule("local_interface_method")));
      }

      return sb.toString();
   }

   public String remote_interface_name() {
      return this.convertToLegalInnerClassName(this.remoteInterfaceClass);
   }

   public String local_interface_name() {
      return this.convertToLegalInnerClassName(this.localInterfaceClass);
   }

   public String result() {
      return this.method.getReturnType().getName().equals("void") ? "" : "result = ";
   }

   public String return_result() {
      return this.method.getReturnType().getName().equals("void") ? "// No return result" : "return result;";
   }

   public String perhaps_return() {
      return this.method.getReturnType().getName().equals("void") ? "" : "return ";
   }

   public String declareBeanStateVar() {
      if (!this.bi.isReadOnly() && (!this.bi.isOptimistic() || !this.bi.getCacheBetweenTransactions())) {
         return this.bi.getCacheBetweenTransactions() ? "  private boolean __WL_beanStateValid = true;" + EOL : "";
      } else {
         StringBuilder sb = new StringBuilder(200);
         sb.append("  private weblogic.utils.concurrent.atomic.AtomicLong __WL_lastLoadTime = weblogic.utils.concurrent.atomic.AtomicFactory.createAtomicLong();");
         sb.append(EOL);
         sb.append("  public static int __WL_readTimeoutMS = 0;");
         sb.append(EOL);
         return sb.toString();
      }
   }

   public String perhapsSetLastLoadTime() {
      return !this.bi.isReadOnly() && (!this.bi.isOptimistic() || !this.bi.getCacheBetweenTransactions()) ? "" : "__WL_setLastLoadTime(System.currentTimeMillis());" + EOL;
   }

   public String perhapsSetLastLoadTimeMethodBody() {
      if (!this.bi.isReadOnly() && (!this.bi.isOptimistic() || !this.bi.getCacheBetweenTransactions())) {
         return "";
      } else {
         StringBuilder sb = new StringBuilder();
         sb.append(" __WL_lastLoadTime.set(time);");
         sb.append(EOL);
         return sb.toString();
      }
   }

   public String perhapsGetLastLoadTimeMethodBody() {
      if (!this.bi.isReadOnly() && (!this.bi.isOptimistic() || !this.bi.getCacheBetweenTransactions())) {
         return "return 0;" + EOL;
      } else {
         StringBuilder sb = new StringBuilder();
         sb.append(" return __WL_lastLoadTime.get();");
         sb.append(EOL);
         return sb.toString();
      }
   }

   public String perhapsDeclareBeanStateValidAccessors() throws CodeGenerationException {
      return this.parse(this.getProductionRule("bean_state_valid_accessors"));
   }

   public String setBeanStateValidMethodBody() {
      if (!this.bi.isReadOnly() && (!this.bi.isOptimistic() || !this.bi.getCacheBetweenTransactions())) {
         return this.bi.getCacheBetweenTransactions() ? "    __WL_beanStateValid = valid;" + EOL : "";
      } else {
         return "    if(!valid) __WL_setLastLoadTime(0);" + EOL;
      }
   }

   public String isBeanStateValidMethodBody() throws CodeGenerationException {
      if (!this.bi.isReadOnly() && (!this.bi.isOptimistic() || !this.bi.getCacheBetweenTransactions())) {
         return this.bi.getCacheBetweenTransactions() ? "    return __WL_beanStateValid;" + EOL : "    return true;" + EOL;
      } else {
         return this.parse(this.getProductionRule("bean_state_timeout_check"));
      }
   }

   public String simple_bean_class_name() {
      return this.nc.getSimpleBeanClassName();
   }

   public String simple_beanimpl_interface_name() {
      return this.nc.getSimpleGeneratedBeanInterfaceName();
   }

   public String simple_beanimpl_class_name() {
      return this.nc.getSimpleGeneratedBeanClassName();
   }

   public String simple_eoimpl_class_name() {
      return this.nc.getSimpleEJBObjectClassName();
   }

   public String simple_eloimpl_class_name() {
      return this.nc.getSimpleEJBLocalObjectClassName();
   }

   public String simple_home_class_name() {
      return this.nc.getSimpleHomeClassName();
   }

   public String simple_local_home_class_name() {
      return this.nc.getSimpleLocalHomeClassName();
   }

   public String invalidation_interface_name() {
      switch (this.bi.getConcurrencyStrategy()) {
         case 5:
         case 6:
            return ", weblogic.ejb.CachingHome";
         default:
            return "";
      }
   }

   public String local_invalidation_interface_name() {
      switch (this.bi.getConcurrencyStrategy()) {
         case 5:
         case 6:
            return ", weblogic.ejb.CachingLocalHome";
         default:
            return "";
      }
   }

   public String dynamic_query_interface_name() {
      return this.is20CMP() ? ", weblogic.ejb.QueryHome" : "";
   }

   public String dynamic_query_local_interface_name() {
      return this.is20CMP() ? ", weblogic.ejb.QueryLocalHome" : "";
   }

   private boolean is20CMP() {
      return this.isContainerManagedBean && this.bi.getCMPInfo().uses20CMP();
   }

   public String packageStatement() {
      String pkg = this.currentOutput.getPackage();
      return pkg != null && !pkg.equals("") ? "package " + pkg + ";" : "";
   }

   public String checkExistsOnMethod() {
      StringBuilder sb = new StringBuilder();
      if (!this.bi.getIsBeanManagedPersistence() && this.bi.getPersistenceUseIdentifier().equals("WebLogic_CMP_RDBMS") && this.bi.getPersistenceUseVersion().equals("6.0")) {
         sb.append("((weblogic.ejb.container.persistence.spi.CMPBean) __bean).__WL_checkExistsOnMethod();" + EOL);
      }

      return sb.toString();
   }

   public String wl_entitybean_fields() throws CodeGenerationException {
      return this.parse(this.getProductionRule("wl_entitybean_fields_code"));
   }

   public String wl_entitybean_methods() throws CodeGenerationException {
      return this.parse(this.getProductionRule("wl_entitybean_methods_code"));
   }

   public String return_type(Class returnType) {
      String result = null;
      if (returnType.isPrimitive()) {
         result = this.arg_type(returnType);
      } else if (returnType.isArray() && this.is_primitive_array(returnType)) {
         result = this.array_as_primitive_type(returnType);
      } else if (returnType.isArray()) {
         result = this.array_as_type(returnType);
      } else {
         result = this.arg_type(returnType);
      }

      return result;
   }

   public String arg_as_primitive_type(Class c) {
      if (c.equals(Boolean.TYPE)) {
         return "boolean";
      } else if (c.equals(Character.TYPE)) {
         return "char";
      } else if (c.equals(Byte.TYPE)) {
         return "byte";
      } else if (c.equals(Short.TYPE)) {
         return "short";
      } else if (c.equals(Integer.TYPE)) {
         return "int";
      } else if (c.equals(Long.TYPE)) {
         return "long";
      } else if (c.equals(Float.TYPE)) {
         return "float";
      } else if (c.equals(Double.TYPE)) {
         return "double";
      } else {
         throw new AssertionError("Primitive type not found");
      }
   }

   public String array_as_type(Class c) {
      int dim;
      for(dim = 0; c.isArray(); c = c.getComponentType()) {
         ++dim;
      }

      StringBuilder o = new StringBuilder(this.arg_type(c));

      for(int i = 0; i < dim; ++i) {
         o = o.append("[]");
      }

      return o.toString();
   }

   public String arg_type(Class c) {
      return ClassUtils.getCanonicalName(c);
   }

   public String array_as_primitive_type(Class c) {
      StringBuilder brs = new StringBuilder(" ");
      int multiDim = c.getName().length() - 1;
      if (multiDim > 0) {
         for(int i = 0; i < multiDim; ++i) {
            brs = brs.append("[]");
         }
      }

      if (c.getName().endsWith("[Z")) {
         return "boolean" + brs;
      } else if (c.getName().endsWith("[C")) {
         return "char" + brs;
      } else if (c.getName().endsWith("[B")) {
         return "byte" + brs;
      } else if (c.getName().endsWith("[S")) {
         return "short" + brs;
      } else if (c.getName().endsWith("[I")) {
         return "int" + brs;
      } else if (c.getName().endsWith("[J")) {
         return "long" + brs;
      } else if (c.getName().endsWith("[F")) {
         return "float" + brs;
      } else if (c.getName().endsWith("[D")) {
         return "double" + brs;
      } else {
         throw new AssertionError("Primitive type not found");
      }
   }

   public void setMethod(Method m, short type) {
      this.setMethod(m, type, (String[])null, (Class)null);
   }

   public void setMethod(Method m, short type, Class c) {
      if (m.toGenericString().equals(m.toString())) {
         this.setMethod(m, type, (String[])null, (Class)null);
      } else {
         this.setMethod(m, type, (String[])null, c);
      }

   }

   public void setMethod(Method m, short type, String[] parameterNames) {
      this.setMethod(m, type, parameterNames, (Class)null);
   }

   public void setMethod(Method m, short type, String[] parameterNames, Class c) {
      this.method = m;
      this.mt.setMethod(this.method);
      this.methodSignature = new MethodSignature(m, c);
      if (parameterNames != null) {
         this.methodSignature.setParameterNames(parameterNames);
      }

      this.methodType = type;
   }

   public String perhapsLite() {
      return this.cmpGetterSetterMethods != null && this.cmpGetterSetterMethods.contains(this.method) ? "Lite" : "";
   }

   protected void writeToFile(String result, File outputFile) throws IOException {
      this.currentOutput.setAbsoluteFilePath(outputFile.getAbsolutePath());
      this.currentOutput.setOutputContent(result);
      if (this.keepgenerated || !this.isJDTBased()) {
         super.writeToFile(result, outputFile);
      }

   }

   protected PrintWriter makeOutputStream(File f) throws IOException {
      return this.keepgenerated ? this.makeIndentingOutputStream(f) : super.makeOutputStream(f);
   }

   private String convertToLegalInnerClassName(Class classObj) {
      StringBuilder returnStr = new StringBuilder(classObj.getName());

      for(Class decClass = classObj.getDeclaringClass(); decClass != null; decClass = decClass.getDeclaringClass()) {
         returnStr.setCharAt(decClass.getName().length(), '.');
      }

      return returnStr.toString();
   }

   public boolean isJDTBased() {
      boolean defaultJDT = true;
      if (this.opts == null) {
         return defaultJDT;
      } else {
         String compilerType = this.opts.getOption("compiler");
         if (compilerType == null) {
            return defaultJDT;
         } else {
            return "jdt".equalsIgnoreCase(compilerType);
         }
      }
   }

   public List getGeneratedOutputs() {
      return this.generatedOutputs;
   }

   public static class Output extends CodeGenerator.Output implements Cloneable {
      private BeanInfo bi;
      private NamingConvention namingConvention;
      private String outputContent;
      private String absoluteFilePath;

      public String getOutputContent() {
         return this.outputContent;
      }

      private void setOutputContent(String content) {
         this.outputContent = content;
      }

      public String getAbsoluteFilePath() {
         return this.absoluteFilePath;
      }

      private void setAbsoluteFilePath(String absoluteFilePath) {
         this.absoluteFilePath = absoluteFilePath;
      }

      public BeanInfo getBeanInfo() {
         return this.bi;
      }

      public void setBeanInfo(BeanInfo bi) {
         this.bi = bi;
      }

      public NamingConvention getNamingConvention() {
         return this.namingConvention;
      }

      public void setNamingConvention(NamingConvention nc) {
         this.namingConvention = nc;
      }

      public Object clone() {
         try {
            return super.clone();
         } catch (CloneNotSupportedException var2) {
            throw new AssertionError(var2 + "");
         }
      }
   }
}
