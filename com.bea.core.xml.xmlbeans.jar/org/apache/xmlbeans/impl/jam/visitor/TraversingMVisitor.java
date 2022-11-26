package org.apache.xmlbeans.impl.jam.visitor;

import org.apache.xmlbeans.impl.jam.mutable.MAnnotatedElement;
import org.apache.xmlbeans.impl.jam.mutable.MAnnotation;
import org.apache.xmlbeans.impl.jam.mutable.MClass;
import org.apache.xmlbeans.impl.jam.mutable.MComment;
import org.apache.xmlbeans.impl.jam.mutable.MConstructor;
import org.apache.xmlbeans.impl.jam.mutable.MField;
import org.apache.xmlbeans.impl.jam.mutable.MInvokable;
import org.apache.xmlbeans.impl.jam.mutable.MMethod;
import org.apache.xmlbeans.impl.jam.mutable.MPackage;
import org.apache.xmlbeans.impl.jam.mutable.MParameter;

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
         this.visit(m[i]);
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
