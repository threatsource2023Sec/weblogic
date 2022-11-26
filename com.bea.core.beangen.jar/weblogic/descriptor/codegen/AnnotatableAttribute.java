package weblogic.descriptor.codegen;

import com.bea.util.jam.JClass;
import com.bea.util.jam.JMethod;
import com.bea.util.jam.JParameter;

public class AnnotatableAttribute extends AnnotatableToken {
   protected JClass jClass;

   public AnnotatableAttribute(JClass jClass) {
      super(jClass, jClass.getClassLoader());
      this.jClass = jClass;
      this.name = this.toLower(jClass.getSimpleName());
   }

   public AnnotatableAttribute(JParameter jParameter) {
      super(jParameter.getType(), jParameter.getType().getClassLoader());
      this.jClass = jParameter.getType();
      this.name = jParameter.getSimpleName();
      if (this.isKeyWord(this.name)) {
         this.name = "_" + this.name;
      }

   }

   public AnnotatableAttribute(JMethod jMethod) {
      super(jMethod, jMethod.getContainingClass().getClassLoader());
      this.jClass = jMethod.getReturnType();
      this.name = this.getAttributeName(jMethod);
      if (this.isKeyWord(this.name)) {
         this.name = "_" + this.name;
      }

   }

   protected JClass getJClass() {
      return this.jClass;
   }

   protected String getAttributeName(JMethod jMethod) {
      return this.toLower(this.getRootAttributeName(jMethod));
   }

   protected String getRootAttributeName(JMethod jMethod) {
      String n = jMethod.getSimpleName();
      AnnotatableMethod method = this.newAnnotatableMethod(jMethod);
      if (method.isGetter()) {
         if (n.startsWith("is")) {
            n = n.substring(2);
         } else {
            n = n.substring(3);
         }
      } else if (method.isSetter()) {
         n = n.substring(3);
      }

      return n;
   }

   public String getPropertyName() {
      return this.toUpper(this.getName());
   }

   public String getSingularPropertyName() {
      return this.singular(this.getPropertyName());
   }

   private String getSyntheticSetterName() {
      return "_set" + this.getPropertyName();
   }

   private String getSyntheticName() {
      return "_" + this.getName();
   }

   public AnnotatableClass getType() {
      return this.newAnnotatableClass(this.jClass);
   }

   public String getInitializer() {
      Annotation annotation = this.getAnnotation("default");
      return this.getDefaultValue(annotation);
   }

   String getDefaultValue(Annotation annotation) {
      String tName = this.getType().getQualifiedName();
      String val = annotation == null ? null : annotation.getStringValue();
      if (val == null && this.getType().isArray()) {
         return this.getZeroLengthArray();
      } else {
         return val == null ? this.getLegalNullValue() : val;
      }
   }

   String getZeroLengthArray() {
      String tName = this.getType().getComponentType().getQualifiedName();
      return "new " + tName + "[0]";
   }

   String getLegalNullValue() {
      if (this.getType().getComponentType() != null) {
         return "null";
      } else {
         String type = this.getType().getQualifiedName();
         switch (type.length()) {
            case 3:
               if (type.equals("int")) {
                  return "0";
               }
               break;
            case 4:
               if (type.equals("byte")) {
                  return "0";
               }

               if (type.equals("char")) {
                  return "0";
               }

               if (type.equals("long")) {
                  return "0";
               }
               break;
            case 5:
               if (type.equals("short")) {
                  return "0";
               }

               if (type.equals("float")) {
                  return "0";
               }
               break;
            case 6:
               if (type.equals("double")) {
                  return "0";
               }
               break;
            case 7:
               if (type.equals("boolean")) {
                  return "false";
               }
         }

         return "null";
      }
   }

   public String getInitializeFromString() {
      return "";
   }

   public String toString() {
      return this.getName();
   }
}
