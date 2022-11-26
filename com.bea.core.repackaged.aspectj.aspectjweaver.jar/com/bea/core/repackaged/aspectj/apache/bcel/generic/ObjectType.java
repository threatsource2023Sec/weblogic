package com.bea.core.repackaged.aspectj.apache.bcel.generic;

import com.bea.core.repackaged.aspectj.apache.bcel.Repository;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.JavaClass;

public class ObjectType extends ReferenceType {
   private String classname;

   public ObjectType(String class_name) {
      super((byte)14, toSignature(class_name));
      this.classname = class_name;
   }

   public ObjectType(String classname, String signature) {
      super((byte)14, signature);
      this.classname = classname;
   }

   private static String toSignature(String classname) {
      StringBuffer sig = new StringBuffer();
      sig.append("L").append(classname.replace('.', '/'));
      sig.append(";");
      return sig.toString();
   }

   public String getClassName() {
      return this.classname;
   }

   public int hashCode() {
      return this.classname.hashCode();
   }

   public boolean equals(Object type) {
      return type instanceof ObjectType ? ((ObjectType)type).classname.equals(this.classname) : false;
   }

   public boolean referencesClass() {
      JavaClass jc = Repository.lookupClass(this.classname);
      return jc == null ? false : jc.isClass();
   }

   public boolean referencesInterface() {
      JavaClass jc = Repository.lookupClass(this.classname);
      if (jc == null) {
         return false;
      } else {
         return !jc.isClass();
      }
   }

   public boolean subclassOf(ObjectType superclass) {
      return !this.referencesInterface() && !superclass.referencesInterface() ? Repository.instanceOf(this.classname, superclass.classname) : false;
   }

   public boolean accessibleTo(ObjectType accessor) {
      JavaClass jc = Repository.lookupClass(this.classname);
      if (jc.isPublic()) {
         return true;
      } else {
         JavaClass acc = Repository.lookupClass(accessor.classname);
         return acc.getPackageName().equals(jc.getPackageName());
      }
   }
}
