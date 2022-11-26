package org.apache.xmlbeans.impl.jam.internal.elements;

import org.apache.xmlbeans.impl.jam.JClass;
import org.apache.xmlbeans.impl.jam.JParameter;
import org.apache.xmlbeans.impl.jam.internal.classrefs.DirectJClassRef;
import org.apache.xmlbeans.impl.jam.internal.classrefs.JClassRef;
import org.apache.xmlbeans.impl.jam.internal.classrefs.JClassRefContext;
import org.apache.xmlbeans.impl.jam.internal.classrefs.QualifiedJClassRef;
import org.apache.xmlbeans.impl.jam.internal.classrefs.UnqualifiedJClassRef;
import org.apache.xmlbeans.impl.jam.mutable.MParameter;
import org.apache.xmlbeans.impl.jam.visitor.JVisitor;
import org.apache.xmlbeans.impl.jam.visitor.MVisitor;

public class ParameterImpl extends MemberImpl implements MParameter {
   private JClassRef mTypeClassRef;

   ParameterImpl(String simpleName, InvokableImpl containingMember, String typeName) {
      super((ElementImpl)containingMember);
      this.setSimpleName(simpleName);
      this.setType(typeName);
   }

   public String getQualifiedName() {
      return this.getSimpleName();
   }

   public void setType(String qcname) {
      if (qcname == null) {
         throw new IllegalArgumentException("null typename");
      } else {
         this.mTypeClassRef = QualifiedJClassRef.create(qcname, (JClassRefContext)((ClassImpl)this.getContainingClass()));
      }
   }

   public void setType(JClass qcname) {
      if (qcname == null) {
         throw new IllegalArgumentException("null qcname");
      } else {
         this.mTypeClassRef = DirectJClassRef.create(qcname);
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
      return this.mTypeClassRef.getRefClass();
   }

   public void accept(MVisitor visitor) {
      visitor.visit((MParameter)this);
   }

   public void accept(JVisitor visitor) {
      visitor.visit((JParameter)this);
   }
}
