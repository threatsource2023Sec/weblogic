package com.bea.util.jam.internal.classrefs;

import com.bea.util.jam.JClass;
import com.bea.util.jam.JamClassLoader;

public class QualifiedJClassRef implements JClassRef {
   private String mQualifiedClassname;
   private JamClassLoader mClassLoader;

   public static JClassRef create(JClass clazz) {
      if (clazz == null) {
         throw new IllegalArgumentException("null clazz");
      } else {
         return new QualifiedJClassRef(clazz.getFieldDescriptor(), clazz.getClassLoader());
      }
   }

   public static JClassRef create(String qcname, JClassRefContext ctx) {
      if (qcname == null) {
         throw new IllegalArgumentException("null qcname");
      } else if (ctx == null) {
         throw new IllegalArgumentException("null ctx");
      } else {
         return create(qcname, ctx.getClassLoader());
      }
   }

   public static JClassRef create(String qcname, JamClassLoader cl) {
      if (qcname == null) {
         throw new IllegalArgumentException("null qcname");
      } else if (cl == null) {
         throw new IllegalArgumentException("null classloader");
      } else {
         return new QualifiedJClassRef(qcname, cl);
      }
   }

   private QualifiedJClassRef(String qcname, JamClassLoader cl) {
      this.mClassLoader = cl;
      this.mQualifiedClassname = qcname;
   }

   public JClass getRefClass() {
      return this.mClassLoader.loadClass(this.mQualifiedClassname);
   }

   public String getQualifiedName() {
      return this.mQualifiedClassname;
   }

   public String toString() {
      return "(QualifiedJClassRef '" + this.mQualifiedClassname + "')";
   }
}
