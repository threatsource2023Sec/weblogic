package com.bea.util.jam.internal.elements;

import com.bea.util.jam.JClass;
import com.bea.util.jam.JMethod;
import com.bea.util.jam.JParameter;
import com.bea.util.jam.internal.classrefs.DirectJClassRef;
import com.bea.util.jam.internal.classrefs.JClassRef;
import com.bea.util.jam.internal.classrefs.JClassRefContext;
import com.bea.util.jam.internal.classrefs.QualifiedJClassRef;
import com.bea.util.jam.internal.classrefs.UnqualifiedJClassRef;
import com.bea.util.jam.mutable.MMethod;
import com.bea.util.jam.visitor.JVisitor;
import com.bea.util.jam.visitor.MVisitor;
import java.io.StringWriter;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public final class MethodImpl extends InvokableImpl implements MMethod {
   private JClassRef mReturnTypeRef = null;

   MethodImpl(String simpleName, ClassImpl containingClass) {
      super(containingClass);
      this.setSimpleName(simpleName);
   }

   public void setReturnType(String className) {
      this.mReturnTypeRef = QualifiedJClassRef.create(className, (JClassRefContext)((ClassImpl)this.getContainingClass()));
   }

   public void setUnqualifiedReturnType(String unqualifiedTypeName) {
      this.mReturnTypeRef = UnqualifiedJClassRef.create(unqualifiedTypeName, (ClassImpl)this.getContainingClass());
   }

   public void setReturnType(JClass c) {
      this.mReturnTypeRef = DirectJClassRef.create(c);
   }

   public JClass getReturnType() {
      return this.mReturnTypeRef == null ? this.getClassLoader().loadClass("void") : this.mReturnTypeRef.getRefClass();
   }

   public boolean isFinal() {
      return Modifier.isFinal(this.getModifiers());
   }

   public boolean isStatic() {
      return Modifier.isStatic(this.getModifiers());
   }

   public boolean isAbstract() {
      return Modifier.isAbstract(this.getModifiers());
   }

   public boolean isNative() {
      return Modifier.isNative(this.getModifiers());
   }

   public boolean isSynchronized() {
      return Modifier.isSynchronized(this.getModifiers());
   }

   public void accept(MVisitor visitor) {
      visitor.visit((MMethod)this);
   }

   public void accept(JVisitor visitor) {
      visitor.visit((JMethod)this);
   }

   public String getQualifiedName() {
      StringWriter sbuf = this.getQualifiedNameWithoutExceptionTypes();
      JClass[] thrown = this.getExceptionTypes();
      if (thrown != null && thrown.length > 0) {
         sbuf.write(" throws ");

         for(int i = 0; i < thrown.length; ++i) {
            sbuf.write(thrown[i].getQualifiedName());
            if (i < thrown.length - 1) {
               sbuf.write(44);
            }
         }
      }

      return sbuf.toString();
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof MethodImpl)) {
         return false;
      } else {
         MethodImpl eMethod = (MethodImpl)o;
         String qn = this.getQualifiedNameWithSortedExceptionTypes();
         if (qn == null) {
            return false;
         } else {
            String oqn = eMethod.getQualifiedNameWithSortedExceptionTypes();
            return oqn == null ? false : qn.equals(oqn);
         }
      }
   }

   public int hashCode() {
      String qn = this.getQualifiedName();
      return qn == null ? 0 : qn.hashCode();
   }

   public String toString() {
      return this.getQualifiedNameWithSortedExceptionTypes();
   }

   private StringWriter getQualifiedNameWithoutExceptionTypes() {
      StringWriter sbuf = new StringWriter();
      sbuf.write(Modifier.toString(this.getModifiers()));
      sbuf.write(32);
      JClass returnJClass = this.getReturnType();
      if (returnJClass == null) {
         sbuf.write("void ");
      } else {
         sbuf.write(returnJClass.getQualifiedName());
         sbuf.write(32);
      }

      sbuf.write(this.getSimpleName());
      sbuf.write(40);
      JParameter[] params = this.getParameters();
      if (params != null && params.length > 0) {
         for(int i = 0; i < params.length; ++i) {
            sbuf.write(params[i].getType().getQualifiedName());
            if (i < params.length - 1) {
               sbuf.write(44);
            }
         }
      }

      sbuf.write(41);
      return sbuf;
   }

   private String getQualifiedNameWithSortedExceptionTypes() {
      JClass[] thrown = this.getExceptionTypes();
      if (thrown != null && thrown.length > 1) {
         StringWriter sbuf = this.getQualifiedNameWithoutExceptionTypes();
         Arrays.sort(thrown);
         sbuf.write(" throws ");

         for(int i = 0; i < thrown.length; ++i) {
            sbuf.write(thrown[i].getQualifiedName());
            if (i < thrown.length - 1) {
               sbuf.write(44);
            }
         }

         return sbuf.toString();
      } else {
         return this.getQualifiedName();
      }
   }
}
