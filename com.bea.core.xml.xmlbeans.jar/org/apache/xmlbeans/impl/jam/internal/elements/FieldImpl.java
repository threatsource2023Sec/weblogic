package org.apache.xmlbeans.impl.jam.internal.elements;

import java.io.StringWriter;
import java.lang.reflect.Modifier;
import org.apache.xmlbeans.impl.jam.JClass;
import org.apache.xmlbeans.impl.jam.JField;
import org.apache.xmlbeans.impl.jam.internal.classrefs.DirectJClassRef;
import org.apache.xmlbeans.impl.jam.internal.classrefs.JClassRef;
import org.apache.xmlbeans.impl.jam.internal.classrefs.JClassRefContext;
import org.apache.xmlbeans.impl.jam.internal.classrefs.QualifiedJClassRef;
import org.apache.xmlbeans.impl.jam.internal.classrefs.UnqualifiedJClassRef;
import org.apache.xmlbeans.impl.jam.mutable.MField;
import org.apache.xmlbeans.impl.jam.visitor.JVisitor;
import org.apache.xmlbeans.impl.jam.visitor.MVisitor;

public final class FieldImpl extends MemberImpl implements MField {
   private JClassRef mTypeClassRef;

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
