package org.apache.xmlbeans.impl.jam.visitor;

import org.apache.xmlbeans.impl.jam.JAnnotatedElement;
import org.apache.xmlbeans.impl.jam.JAnnotation;
import org.apache.xmlbeans.impl.jam.JClass;
import org.apache.xmlbeans.impl.jam.JComment;
import org.apache.xmlbeans.impl.jam.JConstructor;
import org.apache.xmlbeans.impl.jam.JField;
import org.apache.xmlbeans.impl.jam.JInvokable;
import org.apache.xmlbeans.impl.jam.JMethod;
import org.apache.xmlbeans.impl.jam.JPackage;
import org.apache.xmlbeans.impl.jam.JParameter;

public class TraversingJVisitor extends JVisitor {
   private JVisitor mDelegate;

   public TraversingJVisitor(JVisitor jv) {
      if (jv == null) {
         throw new IllegalArgumentException("null jv");
      } else {
         this.mDelegate = jv;
      }
   }

   public void visit(JPackage pkg) {
      pkg.accept(this.mDelegate);
      JClass[] c = pkg.getClasses();

      for(int i = 0; i < c.length; ++i) {
         this.visit(c[i]);
      }

      this.visitAnnotations(pkg);
      this.visitComment(pkg);
   }

   public void visit(JClass clazz) {
      clazz.accept(this.mDelegate);
      JField[] f = clazz.getDeclaredFields();

      int i;
      for(i = 0; i < f.length; ++i) {
         this.visit(f[i]);
      }

      JConstructor[] c = clazz.getConstructors();

      for(i = 0; i < c.length; ++i) {
         this.visit(c[i]);
      }

      JMethod[] m = clazz.getMethods();

      for(i = 0; i < m.length; ++i) {
         this.visit(m[i]);
      }

      this.visitAnnotations(clazz);
      this.visitComment(clazz);
   }

   public void visit(JField field) {
      field.accept(this.mDelegate);
      this.visitAnnotations(field);
      this.visitComment(field);
   }

   public void visit(JConstructor ctor) {
      ctor.accept(this.mDelegate);
      this.visitParameters(ctor);
      this.visitAnnotations(ctor);
      this.visitComment(ctor);
   }

   public void visit(JMethod method) {
      method.accept(this.mDelegate);
      this.visitParameters(method);
      this.visitAnnotations(method);
      this.visitComment(method);
   }

   public void visit(JParameter param) {
      param.accept(this.mDelegate);
      this.visitAnnotations(param);
      this.visitComment(param);
   }

   public void visit(JAnnotation ann) {
      ann.accept(this.mDelegate);
   }

   public void visit(JComment comment) {
      comment.accept(this.mDelegate);
   }

   private void visitParameters(JInvokable iv) {
      JParameter[] p = iv.getParameters();

      for(int i = 0; i < p.length; ++i) {
         this.visit(p[i]);
      }

   }

   private void visitAnnotations(JAnnotatedElement ae) {
      JAnnotation[] anns = ae.getAnnotations();

      for(int i = 0; i < anns.length; ++i) {
         this.visit(anns[i]);
      }

   }

   private void visitComment(JAnnotatedElement e) {
      JComment c = e.getComment();
      if (c != null) {
         this.visit(c);
      }

   }
}
