package com.bea.util.jam.internal.elements;

import com.bea.util.jam.JAnnotation;
import com.bea.util.jam.JClass;
import com.bea.util.jam.JComment;
import com.bea.util.jam.JMethod;
import com.bea.util.jam.JProperty;
import com.bea.util.jam.JSourcePosition;
import com.bea.util.jam.internal.classrefs.JClassRef;
import com.bea.util.jam.internal.classrefs.JClassRefContext;
import com.bea.util.jam.internal.classrefs.QualifiedJClassRef;
import com.bea.util.jam.mutable.MMethod;
import com.bea.util.jam.visitor.JVisitor;
import com.bea.util.jam.visitor.MVisitor;

public class PropertyImpl extends AnnotatedElementImpl implements JProperty {
   private String mName;
   private JMethod mGetter;
   private JMethod mSetter;
   private JClassRef mTypeRef;

   public PropertyImpl(String name, JMethod getter, JMethod setter, String qualifiedTypeName) {
      super((ElementImpl)((ElementImpl)(getter != null ? getter.getParent() : setter.getParent())));
      this.mName = name;
      this.mGetter = getter;
      this.mSetter = setter;
      ClassImpl cont = (ClassImpl)((ClassImpl)(getter != null ? getter.getContainingClass() : setter.getContainingClass()));
      this.mTypeRef = QualifiedJClassRef.create(qualifiedTypeName, (JClassRefContext)cont);
      this.initAnnotations();
   }

   public JClass getType() {
      return this.mTypeRef.getRefClass();
   }

   public String getSimpleName() {
      return this.mName;
   }

   public String getQualifiedName() {
      return this.getParent().getQualifiedName() + "." + this.getSimpleName();
   }

   public JMethod getSetter() {
      return this.mSetter;
   }

   public JMethod getGetter() {
      return this.mGetter;
   }

   public JAnnotation[] getAnnotations() {
      return this.combine((JAnnotation[])(this.mGetter == null ? ElementImpl.NO_ANNOTATION : this.mGetter.getAnnotations()), (JAnnotation[])(this.mSetter == null ? ElementImpl.NO_ANNOTATION : this.mSetter.getAnnotations()));
   }

   public void setSetter(JMethod method) {
      this.mSetter = method;
   }

   public void setGetter(JMethod method) {
      this.mGetter = method;
   }

   public JAnnotation getAnnotation(String named) {
      JAnnotation out = this.mGetter != null ? this.mGetter.getAnnotation(named) : null;
      if (out != null) {
         return out;
      } else {
         return this.mSetter != null ? this.mSetter.getAnnotation(named) : null;
      }
   }

   public JComment getComment() {
      if (this.mGetter != null) {
         return this.mGetter.getComment();
      } else {
         return this.mSetter != null ? this.mSetter.getComment() : null;
      }
   }

   public JSourcePosition getSourcePosition() {
      return this.mGetter != null ? this.mGetter.getSourcePosition() : this.mSetter.getSourcePosition();
   }

   public void accept(JVisitor visitor) {
      if (this.mGetter != null) {
         visitor.visit(this.mGetter);
      }

      if (this.mSetter != null) {
         visitor.visit(this.mSetter);
      }

   }

   public String toString() {
      return this.getQualifiedName();
   }

   private void initAnnotations() {
      JAnnotation[] anns;
      int i;
      if (this.mSetter != null) {
         anns = this.mSetter.getAnnotations();

         for(i = 0; i < anns.length; ++i) {
            super.addAnnotation(anns[i]);
         }

         anns = this.mSetter.getAllJavadocTags();

         for(i = 0; i < anns.length; ++i) {
            super.addAnnotation(anns[i]);
         }
      }

      if (this.mGetter != null) {
         anns = this.mGetter.getAnnotations();

         for(i = 0; i < anns.length; ++i) {
            super.addAnnotation(anns[i]);
         }

         anns = this.mGetter.getAllJavadocTags();

         for(i = 0; i < anns.length; ++i) {
            super.addAnnotation(anns[i]);
         }
      }

   }

   private JAnnotation[] combine(JAnnotation[] a, JAnnotation[] b) {
      if (a.length == 0) {
         return b;
      } else if (b.length == 0) {
         return a;
      } else {
         JAnnotation[] out = new JAnnotation[a.length + b.length];
         System.arraycopy(a, 0, out, 0, a.length);
         System.arraycopy(b, 0, out, a.length, b.length);
         return out;
      }
   }

   private JComment[] combine(JComment[] a, JComment[] b) {
      if (a.length == 0) {
         return b;
      } else if (b.length == 0) {
         return a;
      } else {
         JComment[] out = new JComment[a.length + b.length];
         System.arraycopy(a, 0, out, 0, a.length);
         System.arraycopy(b, 0, out, a.length, b.length);
         return out;
      }
   }

   public void accept(MVisitor visitor) {
      if (this.mGetter != null) {
         visitor.visit((MMethod)this.mGetter);
      }

      if (this.mSetter != null) {
         visitor.visit((MMethod)this.mSetter);
      }

   }
}
