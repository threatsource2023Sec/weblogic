package com.bea.xml_.impl.jam.internal.elements;

import com.bea.xml_.impl.jam.JClass;
import com.bea.xml_.impl.jam.JMethod;
import com.bea.xml_.impl.jam.JParameter;
import com.bea.xml_.impl.jam.internal.classrefs.DirectJClassRef;
import com.bea.xml_.impl.jam.internal.classrefs.JClassRef;
import com.bea.xml_.impl.jam.internal.classrefs.JClassRefContext;
import com.bea.xml_.impl.jam.internal.classrefs.QualifiedJClassRef;
import com.bea.xml_.impl.jam.internal.classrefs.UnqualifiedJClassRef;
import com.bea.xml_.impl.jam.mutable.MMethod;
import com.bea.xml_.impl.jam.visitor.JVisitor;
import com.bea.xml_.impl.jam.visitor.MVisitor;
import java.io.StringWriter;
import java.lang.reflect.Modifier;

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
      int i;
      if (params != null && params.length > 0) {
         for(i = 0; i < params.length; ++i) {
            sbuf.write(params[i].getType().getQualifiedName());
            if (i < params.length - 1) {
               sbuf.write(44);
            }
         }
      }

      sbuf.write(41);
      JClass[] thrown = this.getExceptionTypes();
      if (thrown != null && thrown.length > 0) {
         sbuf.write(" throws ");

         for(i = 0; i < thrown.length; ++i) {
            sbuf.write(thrown[i].getQualifiedName());
            if (i < thrown.length - 1) {
               sbuf.write(44);
            }
         }
      }

      return sbuf.toString();
   }
}
