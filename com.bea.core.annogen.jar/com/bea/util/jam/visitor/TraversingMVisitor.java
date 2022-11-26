package com.bea.util.jam.visitor;

import com.bea.util.jam.mutable.MAnnotatedElement;
import com.bea.util.jam.mutable.MAnnotation;
import com.bea.util.jam.mutable.MClass;
import com.bea.util.jam.mutable.MComment;
import com.bea.util.jam.mutable.MConstructor;
import com.bea.util.jam.mutable.MField;
import com.bea.util.jam.mutable.MInvokable;
import com.bea.util.jam.mutable.MMethod;
import com.bea.util.jam.mutable.MPackage;
import com.bea.util.jam.mutable.MParameter;

public class TraversingMVisitor extends MVisitor {
   private MVisitor mDelegate;

   public TraversingMVisitor(MVisitor jv) {
      if (jv == null) {
         throw new IllegalArgumentException("null jv");
      } else {
         this.mDelegate = jv;
      }
   }

   public void visit(MPackage pkg) {
      pkg.accept(this.mDelegate);
      MClass[] c = pkg.getMutableClasses();

      for(int i = 0; i < c.length; ++i) {
         this.visit(c[i]);
      }

      this.visitAnnotations(pkg);
      this.visitComment(pkg);
   }

   public void visit(MClass clazz) {
      clazz.accept(this.mDelegate);
      MField[] f = clazz.getMutableFields();

      int i;
      for(i = 0; i < f.length; ++i) {
         this.visit(f[i]);
      }

      MConstructor[] c = clazz.getMutableConstructors();

      for(i = 0; i < c.length; ++i) {
         this.visit(c[i]);
      }

      MMethod[] m = clazz.getMutableMethods();

      for(i = 0; i < m.length; ++i) {
         if (m[i] != null) {
            this.visit(m[i]);
         }
      }

      this.visitAnnotations(clazz);
      this.visitComment(clazz);
   }

   public void visit(MField field) {
      field.accept(this.mDelegate);
      this.visitAnnotations(field);
      this.visitComment(field);
   }

   public void visit(MConstructor ctor) {
      ctor.accept(this.mDelegate);
      this.visitParameters(ctor);
      this.visitAnnotations(ctor);
      this.visitComment(ctor);
   }

   public void visit(MMethod method) {
      method.accept(this.mDelegate);
      this.visitParameters(method);
      this.visitAnnotations(method);
      this.visitComment(method);
   }

   public void visit(MParameter param) {
      param.accept(this.mDelegate);
      this.visitAnnotations(param);
      this.visitComment(param);
   }

   public void visit(MAnnotation ann) {
      ann.accept(this.mDelegate);
   }

   public void visit(MComment comment) {
      comment.accept(this.mDelegate);
   }

   private void visitParameters(MInvokable iv) {
      MParameter[] p = iv.getMutableParameters();

      for(int i = 0; i < p.length; ++i) {
         this.visit(p[i]);
      }

   }

   private void visitAnnotations(MAnnotatedElement ae) {
      MAnnotation[] anns = ae.getMutableAnnotations();

      for(int i = 0; i < anns.length; ++i) {
         this.visit(anns[i]);
      }

   }

   private void visitComment(MAnnotatedElement e) {
      MComment c = e.getMutableComment();
      if (c != null) {
         this.visit(c);
      }

   }
}
