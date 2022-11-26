package weblogic.descriptor.codegen;

import com.bea.util.jam.JClass;
import com.bea.util.jam.JMethod;
import java.util.ArrayList;

public class AnnotatableClass extends AnnotatableToken {
   protected JClass jClass;
   protected String qualifiedName;
   protected String elementName;
   protected String packageName;
   protected JClass jSuperClass;
   protected String superClassName;
   protected AnnotatableMethod[] methods;
   protected AnnotatableClass[] interfaces;
   protected AnnotatableAttribute[] allAttributes;

   public AnnotatableClass(JClass jClass) {
      super(jClass, jClass.getClassLoader());
      this.jClass = jClass;
   }

   public JClass getJClass() {
      return this.jClass;
   }

   public String getQualifiedName() {
      if (this.qualifiedName == null) {
         if (this.getJClass().isArrayType()) {
            JClass componentType = this.getJClass().getArrayComponentType();
            String componentTypeName = componentType.getQualifiedName();
            this.qualifiedName = componentTypeName + "[]";
         } else {
            this.qualifiedName = this.getJClass().getQualifiedName();
         }
      }

      return this.qualifiedName;
   }

   public String getElementName() {
      if (this.elementName == null) {
         Annotation annotation = this.getAnnotation("elementName");
         if (annotation == null) {
            this.elementName = this.toUpper(this.getName());
         } else {
            this.elementName = annotation.getStringValue();
         }
      }

      return this.elementName;
   }

   public AnnotatableClass getComponentType() {
      return this.getJClass().isArrayType() ? this.newAnnotatableClass(this.getJClass().getArrayComponentType()) : null;
   }

   public String getPackage() {
      if (this.packageName == null) {
         this.packageName = this.getJClass().getContainingPackage().getQualifiedName();
      }

      return this.packageName;
   }

   public boolean isAbstract() {
      return this.hasAnnotation("abstract");
   }

   public boolean isArray() {
      return this.getJClass().isArrayType();
   }

   public boolean isInterface() {
      return this.getJClass().isInterface();
   }

   public boolean hasMethods() {
      return this.getMethods().length > 0;
   }

   public AnnotatableMethod[] getMethods() {
      if (this.methods == null) {
         JMethod[] ma = this.getJClass().getDeclaredMethods();
         ArrayList l = this.getMethodsList();
         this.methods = new AnnotatableMethod[l.size()];

         for(int i = 0; i < this.methods.length; ++i) {
            this.methods[i] = (AnnotatableMethod)l.get(i);
         }
      }

      return this.methods;
   }

   public ArrayList getMethodsList() {
      JMethod[] ma = this.getJClass().getDeclaredMethods();
      ArrayList l = new ArrayList();

      for(int i = 0; i < ma.length; ++i) {
         l.add(this.newAnnotatableMethod(ma[i]));
      }

      return l;
   }

   public boolean hasSuperClass() {
      return !this.getSuperClass().getQualifiedName().equals("java.lang.Object");
   }

   public AnnotatableClass getSuperClass() {
      if (this.jSuperClass == null) {
         this.jSuperClass = this.loader.loadClass(this.getSuperClassName());
      }

      return this.newAnnotatableClass(this.jSuperClass);
   }

   public String getSuperClassName() {
      if (this.superClassName == null) {
         if (this.getJClass().isInterface()) {
            JClass[] jClasses = this.getJClass().getInterfaces();
            if (jClasses.length > 0) {
               this.superClassName = this.getJClass().getInterfaces()[0].getQualifiedName();
            } else {
               this.superClassName = "java.lang.Object";
            }
         } else {
            JClass sc = this.getJClass().getSuperclass();
            if (sc == null) {
               this.superClassName = "java.lang.Object";
            } else {
               this.superClassName = sc.getQualifiedName();
            }
         }
      }

      return this.superClassName;
   }

   public AnnotatableClass[] getInterfaces() {
      if (this.interfaces == null) {
         JClass[] jClasses = this.getJClass().getInterfaces();
         if (jClasses.length > 0) {
            this.interfaces = new AnnotatableClass[jClasses.length];

            for(int i = 0; i < jClasses.length; ++i) {
               this.interfaces[i] = this.newAnnotatableClass(jClasses[i]);
            }
         } else {
            this.interfaces = new AnnotatableClass[0];
         }
      }

      return this.interfaces;
   }

   public boolean hasExtensions() {
      return this.getInterfaces().length > 0;
   }

   public String getExtensionsList() {
      AnnotatableClass[] c = this.getInterfaces();
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < c.length; ++i) {
         if (i > 0) {
            sb.append(", ");
         }

         sb.append(c[i].getQualifiedName());
      }

      return sb.toString();
   }

   public boolean hasAttributes() {
      return this.getAttributes().length > 0;
   }

   public AnnotatableAttribute[] getAttributes() {
      if (this.allAttributes == null) {
         JMethod[] methods = this.getJClass().getDeclaredMethods();
         ArrayList list = new ArrayList();

         int i;
         for(i = 0; i < methods.length; ++i) {
            if (this.newAnnotatableMethod(methods[i]).isGetter()) {
               list.add(this.newAnnotatableAttribute(methods[i]));
            }
         }

         this.allAttributes = new AnnotatableAttribute[list.size()];

         for(i = 0; i < list.size(); ++i) {
            this.allAttributes[i] = (AnnotatableAttribute)list.get(i);
         }
      }

      return this.allAttributes;
   }

   public String toString() {
      return this.getQualifiedName();
   }

   public boolean equals(Object other) {
      if (other instanceof AnnotatableClass) {
         AnnotatableClass oc = (AnnotatableClass)other;
         return this.getQualifiedName().equals(oc.getQualifiedName());
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.getQualifiedName().hashCode();
   }

   public boolean isReadOnlySingleton(AnnotatableAttribute attribute) {
      String queryName = "create" + attribute.getPropertyName();
      AnnotatableMethod[] var3 = this.getMethods();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         AnnotatableMethod m = var3[var5];
         if (m.getName().equals(queryName)) {
            return false;
         }
      }

      return true;
   }
}
