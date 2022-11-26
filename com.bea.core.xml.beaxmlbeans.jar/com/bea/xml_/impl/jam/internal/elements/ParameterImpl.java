package com.bea.xml_.impl.jam.internal.elements;

import com.bea.xml_.impl.jam.JClass;
import com.bea.xml_.impl.jam.JParameter;
import com.bea.xml_.impl.jam.internal.classrefs.DirectJClassRef;
import com.bea.xml_.impl.jam.internal.classrefs.JClassRef;
import com.bea.xml_.impl.jam.internal.classrefs.JClassRefContext;
import com.bea.xml_.impl.jam.internal.classrefs.QualifiedJClassRef;
import com.bea.xml_.impl.jam.internal.classrefs.UnqualifiedJClassRef;
import com.bea.xml_.impl.jam.mutable.MParameter;
import com.bea.xml_.impl.jam.visitor.JVisitor;
import com.bea.xml_.impl.jam.visitor.MVisitor;

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
