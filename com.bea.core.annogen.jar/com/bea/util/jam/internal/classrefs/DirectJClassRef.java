package com.bea.util.jam.internal.classrefs;

import com.bea.util.jam.JClass;

public class DirectJClassRef implements JClassRef {
   private JClass mClass;

   public static JClassRef create(JClass clazz) {
      return (JClassRef)(clazz instanceof JClassRef ? (JClassRef)clazz : new DirectJClassRef(clazz));
   }

   private DirectJClassRef(JClass clazz) {
      if (clazz == null) {
         throw new IllegalArgumentException("null clazz");
      } else {
         this.mClass = clazz;
      }
   }

   public JClass getRefClass() {
      return this.mClass;
   }

   public String getQualifiedName() {
      return this.mClass.getQualifiedName();
   }
}
