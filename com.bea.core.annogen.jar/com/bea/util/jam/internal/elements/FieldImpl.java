package com.bea.util.jam.internal.elements;

import com.bea.util.jam.JClass;
import com.bea.util.jam.JField;
import com.bea.util.jam.internal.classrefs.DirectJClassRef;
import com.bea.util.jam.internal.classrefs.JClassRef;
import com.bea.util.jam.internal.classrefs.JClassRefContext;
import com.bea.util.jam.internal.classrefs.QualifiedJClassRef;
import com.bea.util.jam.internal.classrefs.UnqualifiedJClassRef;
import com.bea.util.jam.mutable.MField;
import com.bea.util.jam.visitor.JVisitor;
import com.bea.util.jam.visitor.MVisitor;
import java.io.StringWriter;
import java.lang.reflect.Modifier;

public final class FieldImpl extends MemberImpl implements MField {
   private JClassRef mTypeClassRef;
   private Object constantValue;
   private String constantValueExpression;

   FieldImpl(String simpleName, ClassImpl containingClass, String qualifiedTypeClassName) {
      super((ElementImpl)containingClass);
      super.setSimpleName(simpleName);
      this.mTypeClassRef = QualifiedJClassRef.create(qualifiedTypeClassName, (JClassRefContext)containingClass);
   }

   public void setType(JClass type) {
      if (type == null) {
         throw new IllegalArgumentException("null type");
      } else {
         this.mTypeClassRef = DirectJClassRef.create(type);
      }
   }

   public void setType(String qcname) {
      if (qcname == null) {
         throw new IllegalArgumentException("null qcname");
      } else {
         this.mTypeClassRef = QualifiedJClassRef.create(qcname, (JClassRefContext)((ClassImpl)this.getContainingClass()));
      }
   }

   public void setUnqualifiedType(String ucname) {
      if (ucname == null) {
         throw new IllegalArgumentException("null ucname");
      } else {
         this.mTypeClassRef = UnqualifiedJClassRef.create(ucname, (ClassImpl)this.getContainingClass());
      }
   }

   public void setConstantValue(Object constantValue) {
      this.constantValue = constantValue;
   }

   public void setConstantValueExpression(String constantValueExpression) {
      this.constantValueExpression = constantValueExpression;
   }

   public JClass getType() {
      if (this.mTypeClassRef == null) {
         throw new IllegalStateException();
      } else {
         return this.mTypeClassRef.getRefClass();
      }
   }

   public boolean isFinal() {
      return Modifier.isFinal(this.getModifiers());
   }

   public boolean isStatic() {
      return Modifier.isStatic(this.getModifiers());
   }

   public boolean isVolatile() {
      return Modifier.isVolatile(this.getModifiers());
   }

   public boolean isTransient() {
      return Modifier.isTransient(this.getModifiers());
   }

   public Object getConstantValue() {
      return this.constantValue;
   }

   public String getConstantValueExpression() {
      return this.constantValueExpression;
   }

   public void accept(MVisitor visitor) {
      visitor.visit((MField)this);
   }

   public void accept(JVisitor visitor) {
      visitor.visit((JField)this);
   }

   public String getQualifiedName() {
      StringWriter sbuf = new StringWriter();
      sbuf.write(Modifier.toString(this.getModifiers()));
      sbuf.write(32);
      sbuf.write(this.getType().getQualifiedName());
      sbuf.write(32);
      sbuf.write(this.getContainingClass().getQualifiedName());
      sbuf.write(46);
      sbuf.write(this.getSimpleName());
      return sbuf.toString();
   }
}
