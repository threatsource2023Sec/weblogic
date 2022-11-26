package weblogic.descriptor.codegen;

import com.bea.util.jam.JAnnotation;
import com.bea.util.jam.JClass;
import com.bea.util.jam.JMethod;
import com.bea.util.jam.JParameter;
import java.util.ArrayList;
import java.util.Arrays;

public class AnnotatableMethod extends AnnotatableToken {
   protected JMethod jMethod;
   String qualifiedName;
   protected AnnotatableClass[] exceptions;
   protected String exceptionDecl;
   protected AnnotatableAttribute[] parameters;
   protected String parameterDecl;
   protected AnnotatableAttribute[] arguments;
   protected String argumentList;

   public AnnotatableMethod(JMethod jMethod) {
      super(jMethod, jMethod.getContainingClass().getClassLoader());
      this.jMethod = jMethod;
   }

   public JMethod getJMethod() {
      return this.jMethod;
   }

   public String getQualifiedName() {
      if (this.qualifiedName == null) {
         StringBuffer sb = new StringBuffer();
         sb.append(this.newAnnotatableClass(this.jMethod.getContainingClass()).getQualifiedName()).append(".").append(this.getName()).append("(");
         if (this.hasParameters()) {
            AnnotatableAttribute[] parms = this.getParameters();

            for(int i = 0; i < parms.length; ++i) {
               if (i > 0) {
                  sb.append(", ");
               }

               sb.append(parms[i].getType().getQualifiedName());
            }
         }

         sb.append(")");
         if (this.hasExceptions()) {
            sb.append(" throws ").append(this.getExceptionList());
         }

         this.qualifiedName = sb.toString();
      }

      return this.qualifiedName;
   }

   public boolean hasExceptions() {
      return this.getExceptions().length > 0;
   }

   public AnnotatableClass[] getExceptions() {
      if (this.exceptions == null) {
         JClass[] es = this.getJMethod().getExceptionTypes();
         this.exceptions = new AnnotatableClass[es.length];

         for(int i = 0; i < es.length; ++i) {
            this.exceptions[i] = this.newAnnotatableClass(es[i]);
         }
      }

      return this.exceptions;
   }

   public String getExceptionList() {
      if (this.exceptionDecl == null) {
         if (this.hasExceptions()) {
            StringBuffer sb = new StringBuffer("throws ");
            AnnotatableClass[] exs = this.getExceptions();
            sb.append(exs[0].getQualifiedName());
            if (exs.length > 1) {
               for(int i = 1; i < exs.length; ++i) {
                  sb.append(", ").append(exs[i].getQualifiedName());
               }
            }

            this.exceptionDecl = sb.toString();
         } else {
            this.exceptionDecl = "";
         }
      }

      return this.exceptionDecl;
   }

   public boolean hasParameters() {
      return this.getParameters().length > 0;
   }

   public AnnotatableAttribute[] getParameters() {
      if (this.parameters == null) {
         JParameter[] ps = this.getJMethod().getParameters();
         this.parameters = new AnnotatableAttribute[ps.length];

         for(int i = 0; i < ps.length; ++i) {
            this.parameters[i] = this.newAnnotatableAttribute(ps[i]);
         }
      }

      return this.parameters;
   }

   public String getParameterList() {
      if (this.parameterDecl == null) {
         if (this.hasParameters()) {
            StringBuffer sb = new StringBuffer();
            AnnotatableAttribute[] parms = this.getParameters();
            sb.append(parms[0].getType().getQualifiedName()).append(" ");
            if (parms[0].getName().equals("param0")) {
               sb.append(this.getAnnotatableAttribute().getName());
            } else {
               sb.append(parms[0].getName());
            }

            if (parms.length > 1) {
               for(int i = 1; i < parms.length; ++i) {
                  sb.append(", ").append(parms[i].getType().getQualifiedName()).append(" ");
                  if (parms[i].getName().equals("parms" + i)) {
                     sb.append(this.toLower(parms[i].getType().getName()));
                  } else {
                     sb.append(parms[i].getName());
                  }
               }
            }

            this.parameterDecl = sb.toString();
         } else {
            this.parameterDecl = "";
         }
      }

      return this.parameterDecl;
   }

   public String getArgumentList() {
      if (this.argumentList == null) {
         if (this.hasParameters()) {
            StringBuffer sb = new StringBuffer();
            AnnotatableAttribute[] parms = this.getParameters();
            sb.append(parms[0].getName());
            if (parms.length > 1) {
               for(int i = 1; i < parms.length; ++i) {
                  sb.append(", ").append(parms[0].getName());
               }
            }

            this.argumentList = sb.toString();
         } else {
            this.argumentList = "";
         }
      }

      return this.argumentList;
   }

   public AnnotatableClass getReturnType() {
      return this.newAnnotatableClass(this.getJMethod().getReturnType());
   }

   public Annotation getAnnotation(String tag) {
      JAnnotation a = this.jNode.getAnnotation(tag);
      if (a != null) {
         return this.newAnnotation(a, this.loader);
      } else {
         AnnotatableAttribute[] attrs = this.getParameters();

         for(int i = 0; i < attrs.length; ++i) {
            Annotation annotation = attrs[i].getAnnotation(tag);
            if (annotation != null) {
               return annotation;
            }
         }

         return null;
      }
   }

   public Annotation[] getAnnotations() {
      ArrayList list = new ArrayList();
      if (super.hasAnnotations()) {
         list.addAll(Arrays.asList((Object[])super.getAnnotations()));
      }

      if (this.hasParameters()) {
         AnnotatableAttribute[] attrs = this.getParameters();

         for(int i = 0; i < attrs.length; ++i) {
            if (attrs[i].hasAnnotations()) {
               list.addAll(Arrays.asList((Object[])attrs[i].getAnnotations()));
            }
         }
      }

      Annotation[] annotations = new Annotation[list.size()];
      list.toArray(annotations);
      return annotations;
   }

   public boolean hasAnnotation(String tag) {
      return this.getAnnotation(tag) != null;
   }

   public boolean hasAnnotations() {
      Annotation[] a = this.getAnnotations();
      return a != null && a.length > 0;
   }

   public boolean isOperation() {
      return this.isOperationMethod(this.jMethod);
   }

   public boolean isGetter() {
      return this.isGetterMethod(this.jMethod);
   }

   public boolean isSetter() {
      return this.isSetterMethod(this.jMethod);
   }

   protected boolean isGetterMethod(JMethod m) {
      String methodName = m.getSimpleName();
      JClass retType = m.getReturnType();
      JParameter[] jParams = m.getParameters();
      return !retType.getQualifiedName().equals("void") && jParams.length == 0 && (methodName.startsWith("get") || methodName.startsWith("is") && retType.getQualifiedName().equals("boolean"));
   }

   protected boolean isSetterMethod(JMethod m) {
      String methodName = m.getSimpleName();
      JClass retType = m.getReturnType();
      JParameter[] jParams = m.getParameters();
      return retType.getQualifiedName().equals("void") && jParams.length == 1 && methodName.startsWith("set");
   }

   protected boolean isMutator(JMethod m) {
      if (this.isGetterMethod(m)) {
         return false;
      } else {
         return !this.isSetterMethod(m);
      }
   }

   boolean isOperationMethod(JMethod m) {
      if (this.isMutator(m)) {
         return false;
      } else {
         return !this.isGetterMethod(m) && !this.isSetterMethod(m);
      }
   }

   public boolean isBeanFactory() {
      if (!this.getName().startsWith("create")) {
         return false;
      } else if (!this.getReturnType().hasAnnotation("wld:bean")) {
         return false;
      } else if (this.hasParameters()) {
         return false;
      } else {
         JMethod[] m = this.getJMethod().getContainingClass().getMethods();

         for(int i = 0; i < m.length; ++i) {
            if (this.isGetterMethod(m[i]) && m[i].getReturnType().getQualifiedName().equals(this.getReturnType().getQualifiedName())) {
               return true;
            }
         }

         return false;
      }
   }

   public boolean isBeanAntiFactory() {
      if (!this.getName().startsWith("destroy")) {
         return false;
      } else if (!this.getReturnType().getName().equals("void")) {
         return false;
      } else if (!this.hasParameters()) {
         return false;
      } else {
         AnnotatableAttribute[] ap = this.getParameters();
         if (ap.length != 1) {
            return false;
         } else if (!ap[0].getType().hasAnnotation("wld:bean")) {
            return false;
         } else {
            JMethod[] m = this.getJMethod().getContainingClass().getMethods();

            for(int i = 0; i < m.length; ++i) {
               if (this.isGetterMethod(m[i]) && m[i].getReturnType().getQualifiedName().equals(ap[0].getType().getQualifiedName())) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   public boolean isEnroller() {
      if (!this.getName().startsWith("create")) {
         return false;
      } else if (this.hasParameters()) {
         return false;
      } else if (!this.getReturnType().hasAnnotation("wld:bean")) {
         return false;
      } else {
         JMethod[] m = this.getJMethod().getContainingClass().getMethods();

         for(int i = 0; i < m.length; ++i) {
            if (this.isGetterMethod(m[i]) && m[i].getReturnType().isArrayType() && m[i].getReturnType().getArrayComponentType().getQualifiedName().equals(this.getReturnType().getQualifiedName())) {
               return true;
            }
         }

         return false;
      }
   }

   public boolean isUnenroller() {
      if (!this.getName().startsWith("destroy")) {
         return false;
      } else if (!this.getReturnType().getName().equals("void")) {
         return false;
      } else if (!this.hasParameters()) {
         return false;
      } else {
         AnnotatableAttribute[] ap = this.getParameters();
         if (ap.length != 1) {
            return false;
         } else if (!ap[0].getType().hasAnnotation("wld:bean")) {
            return false;
         } else {
            JMethod[] m = this.getJMethod().getContainingClass().getMethods();

            for(int i = 0; i < m.length; ++i) {
               if (this.isGetterMethod(m[i]) && m[i].getReturnType().isArrayType() && m[i].getReturnType().getArrayComponentType().getQualifiedName().equals(ap[0].getType().getQualifiedName())) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   public boolean isAdder() {
      if (!this.getName().startsWith("add")) {
         return false;
      } else if (!this.getReturnType().getName().equals("void")) {
         return false;
      } else if (!this.hasParameters()) {
         return false;
      } else {
         AnnotatableAttribute[] ap = this.getParameters();
         if (ap.length != 1) {
            return false;
         } else if (ap[0].getType().hasAnnotation("wld:bean")) {
            return false;
         } else {
            JMethod[] m = this.getJMethod().getContainingClass().getMethods();

            for(int i = 0; i < m.length; ++i) {
               if (this.isGetterMethod(m[i]) && m[i].getReturnType().isArrayType() && m[i].getReturnType().getArrayComponentType().getQualifiedName().equals(ap[0].getType().getQualifiedName())) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   public boolean isRemover() {
      if (!this.getName().startsWith("remove")) {
         return false;
      } else if (!this.getReturnType().getName().equals("void")) {
         return false;
      } else if (!this.hasParameters()) {
         return false;
      } else {
         AnnotatableAttribute[] ap = this.getParameters();
         if (ap.length != 1) {
            return false;
         } else if (ap[0].getType().hasAnnotation("wld:bean")) {
            return false;
         } else {
            JMethod[] m = this.getJMethod().getContainingClass().getMethods();

            for(int i = 0; i < m.length; ++i) {
               if (this.isGetterMethod(m[i]) && m[i].getReturnType().isArrayType() && m[i].getReturnType().getArrayComponentType().getQualifiedName().equals(ap[0].getType().getQualifiedName())) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   public boolean hasDelegate() {
      return this.getAnnotation("delegate") != null;
   }

   public AnnotatableClass getDelegate() throws Exception {
      Annotation annotation = this.getAnnotation("delegate");
      return annotation == null ? null : annotation.getAnnotatableClassValue();
   }

   public AnnotatableAttribute getAnnotatableAttribute() {
      if (this.isGetter()) {
         return this.newAnnotatableAttribute(this.jMethod);
      } else if (this.isSetter()) {
         return this.newAnnotatableAttribute(this.fromSetter(this.jMethod));
      } else if (this.isBeanFactory()) {
         return this.newAnnotatableAttribute(this.fromBeanFactory(this.jMethod));
      } else if (this.isBeanAntiFactory()) {
         return this.newAnnotatableAttribute(this.fromBeanAntiFactory(this.jMethod));
      } else if (this.isEnroller()) {
         return this.newAnnotatableAttribute(this.fromEnroller(this.jMethod));
      } else if (this.isUnenroller()) {
         return this.newAnnotatableAttribute(this.fromUnenroller(this.jMethod));
      } else if (this.isAdder()) {
         return this.newAnnotatableAttribute(this.fromAdder(this.jMethod));
      } else if (this.isRemover()) {
         return this.newAnnotatableAttribute(this.fromRemover(this.jMethod));
      } else {
         throw new AssertionError("Can't find attribute for method: " + this.toString());
      }
   }

   JMethod findGetter(String attrName, JMethod xmeth) {
      JMethod[] m = xmeth.getContainingClass().getMethods();

      for(int i = 0; i < m.length; ++i) {
         if (this.isGetterMethod(m[i])) {
            if (m[i].getSimpleName().substring(3).equals(attrName)) {
               return m[i];
            }

            if (m[i].getSimpleName().substring(2).equals(attrName)) {
               return m[i];
            }
         }
      }

      throw new AssertionError("no getter for: " + xmeth.getQualifiedName());
   }

   JMethod fromSetter(JMethod setter) {
      String attrName = setter.getSimpleName().substring(3);
      return this.findGetter(attrName, setter);
   }

   JMethod fromBeanFactory(JMethod f) {
      String attrName = f.getSimpleName().substring(6);
      return this.findGetter(attrName, f);
   }

   JMethod fromBeanAntiFactory(JMethod af) {
      String attrName = af.getSimpleName().substring(7);
      return this.findGetter(attrName, af);
   }

   JMethod fromEnroller(JMethod e) {
      String attrName = e.getSimpleName().substring(6);
      attrName = this.plural(attrName);
      return this.findGetter(attrName, e);
   }

   JMethod fromUnenroller(JMethod u) {
      String attrName = u.getSimpleName().substring(7);
      attrName = this.plural(attrName);
      return this.findGetter(attrName, u);
   }

   JMethod fromAdder(JMethod a) {
      String attrName = a.getSimpleName().substring(3);
      attrName = this.plural(attrName);
      return this.findGetter(attrName, a);
   }

   JMethod fromRemover(JMethod r) {
      String attrName = r.getSimpleName().substring(6);
      attrName = this.plural(attrName);
      return this.findGetter(attrName, r);
   }

   boolean methodsCompare(JMethod m1, JMethod m2) {
      if (m1.getSimpleName().equals(m2.getSimpleName())) {
         JParameter[] p1 = m1.getParameters();
         JParameter[] p2 = m2.getParameters();
         if (p1.length != p2.length) {
            return false;
         } else if (!this.compareClasses(m1.getReturnType(), m2.getReturnType())) {
            return false;
         } else {
            for(int i = 0; i < p1.length; ++i) {
               if (!p1[i].equals(p2[i])) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   boolean compareClasses(JClass c1, JClass c2) {
      if (!c1.equals(c2)) {
         return false;
      } else {
         return c1.isArrayType() ? this.compareClasses(c1.getArrayComponentType(), c2.getArrayComponentType()) : true;
      }
   }

   public String toString() {
      return this.getQualifiedName();
   }

   public boolean equals(Object other) {
      if (other instanceof AnnotatableMethod) {
         AnnotatableMethod oc = (AnnotatableMethod)other;
         return this.getQualifiedName().equals(oc.getQualifiedName());
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.getQualifiedName().hashCode();
   }
}
