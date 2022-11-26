package com.bea.util.jam.visitor;

import com.bea.util.jam.JAnnotation;
import com.bea.util.jam.JClass;
import com.bea.util.jam.JComment;
import com.bea.util.jam.JConstructor;
import com.bea.util.jam.JField;
import com.bea.util.jam.JMethod;
import com.bea.util.jam.JPackage;
import com.bea.util.jam.JParameter;

public class CompositeJVisitor extends JVisitor {
   private JVisitor[] mVisitors;

   public CompositeJVisitor(JVisitor[] visitors) {
      if (visitors == null) {
         throw new IllegalArgumentException("null visitors");
      } else {
         this.mVisitors = visitors;
      }
   }

   public void visit(JPackage pkg) {
      for(int i = 0; i < this.mVisitors.length; ++i) {
         this.mVisitors[i].visit(pkg);
      }

   }

   public void visit(JClass clazz) {
      for(int i = 0; i < this.mVisitors.length; ++i) {
         this.mVisitors[i].visit(clazz);
      }

   }

   public void visit(JConstructor ctor) {
      for(int i = 0; i < this.mVisitors.length; ++i) {
         this.mVisitors[i].visit(ctor);
      }

   }

   public void visit(JField field) {
      for(int i = 0; i < this.mVisitors.length; ++i) {
         this.mVisitors[i].visit(field);
      }

   }

   public void visit(JMethod method) {
      for(int i = 0; i < this.mVisitors.length; ++i) {
         this.mVisitors[i].visit(method);
      }

   }

   public void visit(JParameter param) {
      for(int i = 0; i < this.mVisitors.length; ++i) {
         this.mVisitors[i].visit(param);
      }

   }

   public void visit(JAnnotation ann) {
      for(int i = 0; i < this.mVisitors.length; ++i) {
         this.mVisitors[i].visit(ann);
      }

   }

   public void visit(JComment comment) {
      for(int i = 0; i < this.mVisitors.length; ++i) {
         this.mVisitors[i].visit(comment);
      }

   }
}
