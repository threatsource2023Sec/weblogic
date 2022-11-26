package com.bea.xml_.impl.jam.visitor;

import com.bea.xml_.impl.jam.JAnnotation;
import com.bea.xml_.impl.jam.JClass;
import com.bea.xml_.impl.jam.JComment;
import com.bea.xml_.impl.jam.JConstructor;
import com.bea.xml_.impl.jam.JField;
import com.bea.xml_.impl.jam.JMethod;
import com.bea.xml_.impl.jam.JPackage;
import com.bea.xml_.impl.jam.JParameter;

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
