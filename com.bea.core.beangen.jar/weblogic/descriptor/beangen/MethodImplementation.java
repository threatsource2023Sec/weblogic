package weblogic.descriptor.beangen;

import com.bea.util.jam.JAnnotation;
import com.bea.util.jam.JClass;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import weblogic.utils.Debug;

public abstract class MethodImplementation {
   private MethodDeclaration declaration;
   private BeanCustomizer customizer;
   private int implLocation;
   private Set declaredExceptionSet;
   protected static final int SUPER_CLASS = 0;
   protected static final int LOCAL = 1;
   protected static final int CUSTOMIZER = 2;

   protected MethodImplementation(MethodDeclaration declaration, BeanCustomizer customizer) {
      this.declaration = declaration;
      this.customizer = customizer;
      JClass beanif = declaration.getBean().getJClass();
      JAnnotation anno = beanif.getAnnotation("inheritSuperCustomizer");
      boolean inheritSuperCustomizer = anno != null;
      if ((!inheritSuperCustomizer || customizer == null || !customizer.definesMethod(declaration) || customizer.definesMethodInSuperClass(declaration)) && (inheritSuperCustomizer || customizer == null || !customizer.definesMethod(declaration))) {
         if (declaration.isDeclared()) {
            if (declaration.isType(MethodType.OPERATION) && declaration.getBean().isImplementedByBaseClass(declaration)) {
               this.implLocation = 0;
            } else {
               this.implLocation = 1;
            }
         } else {
            this.implLocation = 0;
         }
      } else {
         this.implLocation = 2;
      }

   }

   public String[] getComments() {
      List sl = new ArrayList();
      this.addComments(sl);
      return (String[])((String[])sl.toArray(new String[0]));
   }

   public String[] getBody() {
      List sl = new ArrayList();
      this.addBody(sl);
      return (String[])((String[])sl.toArray(new String[0]));
   }

   public String toString() {
      return this.declaration.toString();
   }

   protected void promote() {
      if (this.implLocation < 1) {
         this.implLocation = 1;
      }

   }

   public boolean isImplementedBySuperClass() {
      return this.implLocation == 0;
   }

   public boolean isImplementedByCustomizer() {
      return this.implLocation == 2;
   }

   public boolean isImplementedLocally() {
      return this.implLocation == 1;
   }

   public BeanCustomizer getCustomizer() {
      Debug.assertion(!this.isImplementedByCustomizer() || this.customizer != null);
      return this.customizer;
   }

   protected MethodDeclaration getDeclaration() {
      return this.declaration;
   }

   protected void addComments(List sl) {
   }

   protected void addBody(List sl) {
      Context.get().getLog().error("Method not implemented", this.declaration.getMethod());
   }

   protected void addDelegateToCustomizer(List sl, String prefix) {
      JClass[] possible = this.getCustomizer().getMethod(this.getDeclaration()).getExceptionTypes();
      JClass[] undeclared = this.getUndeclaredExceptions(possible);
      if (undeclared.length > 0) {
         this.beginImpossibleExceptionWrapper(sl);
      }

      sl.add(prefix + this.cfName() + "." + this.mName() + "(" + this.params() + ");");
      if (undeclared.length > 0) {
         this.endImpossibleExceptionWrapper(sl, undeclared);
      }

   }

   protected JClass[] getUndeclaredExceptions(JClass[] possible) {
      if (this.declaredExceptionSet == null) {
         JClass[] exceptions = this.getDeclaration().getExceptionTypes();
         this.declaredExceptionSet = new HashSet(Arrays.asList((Object[])exceptions));
      }

      List undeclared = new ArrayList();

      for(int i = 0; i < possible.length; ++i) {
         if (!this.declaredExceptionSet.contains(possible[i])) {
            undeclared.add(possible[i]);
         }
      }

      return (JClass[])((JClass[])undeclared.toArray(new JClass[0]));
   }

   protected void beginImpossibleExceptionWrapper(List sl) {
      this.beginImpossibleExceptionWrapper(sl, "");
   }

   protected void beginImpossibleExceptionWrapper(List sl, String intendation) {
      sl.add(intendation + "try {");
   }

   protected void endImpossibleExceptionWrapper(List sl, JClass[] undeclared) {
      Context ctx = Context.get();

      for(int i = 0; i < undeclared.length; ++i) {
         String exception = ctx.abbreviateClass(undeclared[i].getQualifiedName());
         sl.add("} catch (" + exception + " e) {");
         sl.add("  throw new java.lang.reflect.UndeclaredThrowableException(e);");
      }

      sl.add("}");
   }

   protected void endImpossibleExceptionWrapper(List sl) {
      this.endImpossibleExceptionWrapper(sl, "");
   }

   protected void endImpossibleExceptionWrapper(List sl, String intendation) {
      Context ctx = Context.get();
      JClass[] declared = this.getDeclaration().getExceptionTypes();
      sl.add(intendation + "} catch (Exception e) {");
      sl.add(intendation + "  if (e instanceof RuntimeException)");
      sl.add(intendation + "    throw (RuntimeException)e;");

      for(int i = 0; i < declared.length; ++i) {
         String exception = ctx.abbreviateClass(declared[i].getQualifiedName());
         sl.add(intendation + "  else if (e instanceof " + exception + ")");
         sl.add(intendation + "    throw (" + exception + ")e;");
      }

      sl.add(intendation + "  throw new java.lang.reflect.UndeclaredThrowableException(e);");
      sl.add(intendation + "}");
   }

   protected String mName() {
      return this.getDeclaration().getName();
   }

   protected String rType() {
      return this.getDeclaration().getReturnType();
   }

   protected JClass rJClass() {
      return this.getDeclaration().getReturnJClass();
   }

   protected String params() {
      return this.getDeclaration().getParamList();
   }

   protected String cfName() {
      return this.getCustomizer().getField().getName();
   }

   protected JClass cfJClass() {
      return this.getCustomizer().getField().getJClass();
   }

   protected String replaceExpressions(String exp, String paramName) {
      if (exp != null) {
         String newExpr = StringHelper.replaceExpression(exp, "void", "null");
         newExpr = StringHelper.replaceExpression(newExpr, "self", "this");
         newExpr = StringHelper.replaceExpression(newExpr, ";", " ");
         newExpr = StringHelper.replaceExpression(newExpr, "value", paramName);
         if (this.customizer != null) {
            newExpr = StringHelper.replaceExpression(newExpr, "customizerField", this.customizer.getField().getName());
         }

         return newExpr;
      } else {
         return null;
      }
   }
}
