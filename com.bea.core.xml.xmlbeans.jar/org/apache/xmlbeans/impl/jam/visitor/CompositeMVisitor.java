package org.apache.xmlbeans.impl.jam.visitor;

import org.apache.xmlbeans.impl.jam.mutable.MAnnotation;
import org.apache.xmlbeans.impl.jam.mutable.MClass;
import org.apache.xmlbeans.impl.jam.mutable.MComment;
import org.apache.xmlbeans.impl.jam.mutable.MConstructor;
import org.apache.xmlbeans.impl.jam.mutable.MField;
import org.apache.xmlbeans.impl.jam.mutable.MMethod;
import org.apache.xmlbeans.impl.jam.mutable.MPackage;
import org.apache.xmlbeans.impl.jam.mutable.MParameter;

public class CompositeMVisitor extends MVisitor {
   private MVisitor[] mVisitors;

   public CompositeMVisitor(MVisitor[] visitors) {
      if (visitors == null) {
         throw new IllegalArgumentException("null visitors");
      } else {
         this.mVisitors = visitors;
      }
   }

   public void visit(MPackage pkg) {
      for(int i = 0; i < this.mVisitors.length; ++i) {
         this.mVisitors[i].visit(pkg);
      }

   }

   public void visit(MClass clazz) {
      for(int i = 0; i < this.mVisitors.length; ++i) {
         this.mVisitors[i].visit(clazz);
      }

   }

   public void visit(MConstructor ctor) {
      for(int i = 0; i < this.mVisitors.length; ++i) {
         this.mVisitors[i].visit(ctor);
      }

   }

   public void visit(MField field) {
      for(int i = 0; i < this.mVisitors.length; ++i) {
         this.mVisitors[i].visit(field);
      }

   }

   public void visit(MMethod method) {
      for(int i = 0; i < this.mVisitors.length; ++i) {
         this.mVisitors[i].visit(method);
      }

   }

   public void visit(MParameter param) {
      for(int i = 0; i < this.mVisitors.length; ++i) {
         this.mVisitors[i].visit(param);
      }

   }

   public void visit(MAnnotation ann) {
      for(int i = 0; i < this.mVisitors.length; ++i) {
         this.mVisitors[i].visit(ann);
      }

   }

   public void visit(MComment comment) {
      for(int i = 0; i < this.mVisitors.length; ++i) {
         this.mVisitors[i].visit(comment);
      }

   }
}
