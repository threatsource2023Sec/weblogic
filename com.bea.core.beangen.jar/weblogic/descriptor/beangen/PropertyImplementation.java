package weblogic.descriptor.beangen;

import com.bea.util.jam.JAnnotationValue;
import com.bea.util.jam.JClass;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import weblogic.descriptor.annotation.BeanAnnotations;
import weblogic.descriptor.annotation.PropertyAnnotations;

public class PropertyImplementation {
   private PropertyDeclaration decl;
   private PropertyImplementation delegate;
   private boolean implementedBySuperClass;
   private BeanField field;
   private PropertyMethodDeclaration[] syntheticMethods;
   private String initializer;
   public static final int DELEGATETYPE_NONE = 0;
   public static final int DELEGATETYPE_ENCRYPTION = 1;
   private int delegateType;
   public static final String encryptedDelegateSuffix = "Encrypted";

   public PropertyImplementation(PropertyDeclaration decl, BeanCustomizer customizer) {
      this(decl, customizer, 0);
   }

   public PropertyImplementation(PropertyDeclaration decl, BeanCustomizer customizer, int delegateType) {
      this.delegate = null;
      this.initializer = null;
      this.delegateType = 0;
      this.decl = decl;
      this.delegateType = delegateType;
      this.implementedBySuperClass = true;
      MethodDeclaration[] methods = decl.getMethods();

      int i;
      for(i = 0; i < methods.length; ++i) {
         MethodDeclaration methodDecl = methods[i];
         MethodImplementation mi = methodDecl.implement(customizer);
         if (!mi.isImplementedBySuperClass()) {
            this.implementedBySuperClass = false;
         }
      }

      if (!this.implementedBySuperClass) {
         for(i = 0; i < methods.length; ++i) {
            ((PropertyMethodImplementation)methods[i].getImplementation()).promote();
         }

         JClass fClass = decl.getJClass();
         String fName = "_" + decl.getName();
         this.initializer = this.getInitializer(decl);
         this.field = new BeanField(fClass, fName, (String)null);
         this.field.setIndex(decl.getIndex());
      }

      if (!this.implementedBySuperClass && !Context.get().getNoSynthetics()) {
         this.syntheticMethods = this.implementSyntheticMethods(customizer);
      }

      this.checkIfCustom();
   }

   public String getInitializer() {
      return this.initializer;
   }

   public String getIntfInitializer() {
      return this.getInitializer(this.decl, true);
   }

   public boolean isImplementedBySuperClass() {
      return this.implementedBySuperClass;
   }

   public boolean isMarshalledAsString() {
      if (this.decl.isTransient()) {
         return false;
      } else {
         return this.decl.isReference() || !this.decl.isBean() && (JClasses.COLLECTION.isAssignableFrom(this.decl.getJClass()) || JClasses.MAP.isAssignableFrom(this.decl.getJClass())) || this.getDelegateType() == 1;
      }
   }

   public BeanField getField() {
      return this.field;
   }

   public PropertyMethodDeclaration[] getSyntheticMethods() {
      return this.syntheticMethods;
   }

   protected PropertyMethodDeclaration[] implementSyntheticMethods(BeanCustomizer customizer) {
      List methods = new ArrayList();
      methods.add(PropertyMethodType.SETTER.createDeclaration(this.decl.getBean(), this.decl.getJClass(), this.decl.getName()));
      if (this.decl.isBean() && this.decl.isArray()) {
         JClass type = this.decl.getJClass().getArrayComponentType();
         if (type == null) {
            throw new AssertionError("Component type for: " + this.decl + " must be non-null");
         }

         methods.add(PropertyMethodType.ADDER.createDeclaration(this.decl.getBean(), type, this.decl.getComponentName()));
         methods.add(PropertyMethodType.REMOVER.createDeclaration(this.decl.getBean(), type, this.decl.getComponentName()));
      }

      if (this.isMarshalledAsString()) {
         methods.add(PropertyMethodType.STRING_GETTER.createDeclaration(this.decl.getBean(), JClasses.STRING, this.decl.getName()));
         methods.add(PropertyMethodType.STRING_SETTER.createDeclaration(this.decl.getBean(), JClasses.STRING, this.decl.getName()));
         if (this.decl.isReference() && !BeanAnnotations.REFERENCEABLE.isDefined(this.decl.getComponentJClass())) {
            this.decl.error("property " + this.decl + " is a reference that refers to " + this.decl.getComponentType() + " which is not declared as @referenceable");
         }
      }

      methods.add(PropertyMethodType.ISSET.createDeclaration(this.decl.getBean(), JClasses.BOOLEAN, this.decl.getName()));
      methods.add(PropertyMethodType.IS_INHERITED.createDeclaration(this.decl.getBean(), JClasses.BOOLEAN, this.decl.getName()));
      int srcOrder = this.decl.getGetter().getOrder();
      Iterator it = methods.iterator();

      while(it.hasNext()) {
         PropertyMethodDeclaration pm = (PropertyMethodDeclaration)it.next();
         pm.setOrder(srcOrder);
         this.decl.addMethod(pm, true);
         pm.implement(customizer);
      }

      return (PropertyMethodDeclaration[])((PropertyMethodDeclaration[])methods.toArray(new PropertyMethodDeclaration[0]));
   }

   protected String getInitializer(PropertyDeclaration decl) {
      return this.getInitializer(decl, false);
   }

   protected String getInitializer(PropertyDeclaration decl, boolean isIntfInitializer) {
      String dflt = decl.getAnnotationString(PropertyAnnotations.DEFAULT);
      if (this.getDelegateType() == 1 && dflt == null) {
         dflt = "null";
      }

      String prodDflt = decl.getAnnotationString(PropertyAnnotations.PRODUCTION_DEFAULT);
      return dflt == null ? this.computeInitializer(decl, prodDflt, isIntfInitializer) : this.computeInitializer(decl, dflt, isIntfInitializer);
   }

   public String getDeferredInitializer(PropertyDeclaration decl) {
      String dflt = decl.getAnnotationString(PropertyAnnotations.DEFAULT);
      String prodDflt = decl.getAnnotationString(PropertyAnnotations.PRODUCTION_DEFAULT);
      String secureValue = decl.getAnnotationString(PropertyAnnotations.SECURE_VALUE);
      if (decl.isAnnotationDefined(PropertyAnnotations.SECURE_VALUE_DOC_ONLY)) {
         secureValue = null;
      }

      if (secureValue != null && dflt != null && prodDflt != null && !secureValue.equals(prodDflt)) {
         return "_isSecureModeEnabled() ? " + this.computeInitializer(decl, secureValue) + " : _isProductionModeEnabled() ? " + this.computeInitializer(decl, prodDflt) + " : " + this.computeInitializer(decl, dflt) + ";";
      } else if (secureValue != null && prodDflt == null && !secureValue.equals(dflt) && (dflt != null || secureValue.equals(this.computeInitializer(decl, dflt)))) {
         return "_isSecureModeEnabled() ? " + this.computeInitializer(decl, secureValue) + " : " + this.computeInitializer(decl, dflt) + ";";
      } else {
         return dflt != null && prodDflt != null ? "_isProductionModeEnabled() ? " + this.computeInitializer(decl, prodDflt) + " : " + this.computeInitializer(decl, dflt) + ";" : null;
      }
   }

   private String computeInitializer(PropertyDeclaration decl, String dflt) {
      return this.computeInitializer(decl, dflt, false);
   }

   private String computeInitializer(PropertyDeclaration decl, String dflt, boolean isIntfInitializer) {
      JClass type = decl.getJClass();
      String typeName = type.getQualifiedName();
      Context ctx = Context.get();
      if (dflt != null) {
         if (type.equals(JClasses.STRING_ARRAY)) {
            if (dflt.indexOf(44) != -1) {
               dflt = "new String[] { " + dflt + " }";
            } else {
               dflt = "StringHelper.split(" + dflt + ")";
            }
         }

         return dflt;
      } else if (decl.isAnnotationDefined(PropertyAnnotations.REFERENCE)) {
         return "null";
      } else if (type.equals(JClasses.LIST)) {
         return "new " + ArrayList.class.getName() + "()";
      } else if (isIntfInitializer && decl.isBean() && decl.isArray()) {
         return "new " + decl.getComponentType() + "[]{}";
      } else if (type.isArrayType()) {
         return "new " + ctx.abbreviateClass(ctx.interfaceToClassName(type)) + "{}";
      } else if (decl.isChild() && decl.isReadOnly()) {
         String cName = ctx.abbreviateClass(ctx.interfaceToClassName(type));
         return "new " + cName + "(this, " + decl.getIndex() + ")";
      } else if (type.isPrimitiveType()) {
         if (typeName.equals(Boolean.TYPE.getName())) {
            return "false";
         } else {
            return typeName.equals(Character.TYPE.getName()) ? "'\u0000'" : "0";
         }
      } else {
         return "null";
      }
   }

   private void checkIfCustom() {
      if (!this.decl.isTransient()) {
         MethodDeclaration getter = this.decl.getGetter();
         MethodDeclaration setter = this.decl.findMethod(PropertyMethodType.SETTER);
         MethodImplementation getterImpl = getter.getImplementation();
         MethodImplementation setterImpl = setter == null ? null : setter.getImplementation();
         String error = null;
         BeanCustomizer customizer = null;
         if (getterImpl.isImplementedByCustomizer()) {
            if (setter == null || !setterImpl.isImplementedByCustomizer()) {
               customizer = getterImpl.getCustomizer();
               error = "customizer implements " + getter + " but not " + setter + "; must implement both to customize property implementation";
            }
         } else if (setter != null && setterImpl.isImplementedByCustomizer()) {
            customizer = setterImpl.getCustomizer();
            error = "customizer implements " + setter + " but not " + getter + "; must implement both to customize property implementation";
         }

         if (error != null) {
            customizer.error(error);
         }
      }

   }

   public PropertyImplementation getDelegate() {
      return this.delegate;
   }

   void setDelegate(PropertyImplementation delegate) {
      this.delegate = delegate;
   }

   public void implementDelegate(PropertyDeclaration delegateDeclaration) {
      this.delegate = delegateDeclaration.implement(delegateDeclaration.getBean().getCustomizer(), 1);
   }

   public PropertyDeclaration getDeclaration() {
      return this.decl;
   }

   public int getDelegateType() {
      return this.delegateType;
   }

   public boolean isDelegate() {
      return this.getDelegateType() != 0;
   }

   public boolean isEncryptionDelegate() {
      return this.getDelegateType() == 1;
   }

   public boolean hasDelegate() {
      return this.getDelegate() != null;
   }

   private String getClassName(String typeName) {
      return typeName.substring(0, typeName.length() - 6);
   }

   public List getAnnotationListenerCode(String classVar, String currentAnnotationVar) {
      List code = new ArrayList();
      if (this.getDeclaration().hasAnnotationListener()) {
         JAnnotationValue[] values = PropertyAnnotations.META_DATA.getAnnotationValues(this.getDeclaration().getGetter().getMethod());
         String type = null;
         String condition = null;
         String action = null;
         String key = null;
         String map = null;
         boolean recurse = true;
         String value = null;
         boolean noAttrs = true;
         String annotationVar = this.getDeclaration().getName() + "Anno";

         for(int count = 0; count < values.length; ++count) {
            if (values[count].getName().equals("type")) {
               type = values[count].asString();
               noAttrs = false;
            } else if (values[count].getName().equals("condition")) {
               condition = this.replaceExpressions(values[count].asString(), classVar, annotationVar);
               noAttrs = false;
            } else if (values[count].getName().equals("action")) {
               action = this.replaceExpressions(values[count].asString(), classVar, annotationVar);
               noAttrs = false;
            } else if (values[count].getName().equals("key")) {
               key = this.replaceExpressions(values[count].asString(), classVar, annotationVar);
               noAttrs = false;
            } else if (values[count].getName().equals("map")) {
               map = this.replaceExpressions(values[count].asString(), classVar, annotationVar);
               noAttrs = false;
            } else if (values[count].getName().equals("recurse")) {
               String sRecurse = values[count].asString();
               if (sRecurse != null && sRecurse.equals("false")) {
                  recurse = false;
               }

               noAttrs = false;
            } else if (values[count].getName().equals("value")) {
               value = values[count].asString();
            }
         }

         if (noAttrs) {
            map = this.replaceExpressions(value, classVar, annotationVar);
         }

         String className = null;
         if (type != null) {
            className = this.getClassName(type);
            code.add(className + " " + annotationVar + ";");
         } else {
            className = "java.lang.annotation.Annotation";
            code.add(className + " " + annotationVar + " = (" + className + ")" + currentAnnotationVar + ";");
         }

         if (type != null) {
            code.add("if (" + classVar + ".isAnnotationPresent(" + type + ")) {");
            code.add(annotationVar + " = (" + className + ")" + classVar + ".getAnnotation(" + type + ");");
         }

         if (condition != null) {
            code.add("  if (" + condition + ") {");
         } else if (this.getDeclaration().isChild() && this.getDeclaration().isArray() && key != null) {
            code.add("  if (bean.lookup" + this.getDeclaration().getComponentName() + "(" + key + ") == null) {");
         } else {
            code.add("  if (!bean.is" + this.getDeclaration().getName() + "Set()) {");
         }

         if (action != null) {
            code.add("    " + action + ";");
         } else if (this.getDeclaration().isChild()) {
            if (this.getDeclaration().isArray()) {
               if (key != null) {
                  code.add("    bean.create" + this.getDeclaration().getComponentName() + "(" + key + ");");
               }
            } else {
               code.add("    bean.create" + this.getDeclaration().getName() + "();");
            }
         } else if (map != null) {
            code.add("    bean.set" + this.getDeclaration().getName() + "(" + map + ");");
         }

         code.add("  }");
         if (recurse && this.getDeclaration().isChild()) {
            if (this.getDeclaration().isArray()) {
               if (key != null) {
                  code.add("  Object property = bean.lookup" + this.getDeclaration().getComponentName() + "(" + key + ");");
               } else {
                  code.add("  Object[] property = bean.get" + this.getDeclaration().getName() + "();");
               }
            } else {
               code.add("  Object property = bean.get" + this.getDeclaration().getName() + "();");
            }

            code.add("  inferSubTree(property, " + classVar + ", " + annotationVar + ");");
         }

         if (type != null) {
            code.add("}");
         }
      }

      return code;
   }

   private String replaceExpressions(String exp, String classVar, String annotationVar) {
      if (exp != null) {
         String newExpr = StringHelper.replaceExpression(exp, "annotation", annotationVar);
         newExpr = StringHelper.replaceExpression(newExpr, "class", classVar);
         newExpr = StringHelper.replaceExpression(newExpr, "annoType", "((java.lang.annotation.Annotation)" + annotationVar + ").annotationType()");
         return newExpr;
      } else {
         return exp;
      }
   }
}
