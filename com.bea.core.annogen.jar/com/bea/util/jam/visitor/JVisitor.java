package com.bea.util.jam.visitor;

import com.bea.util.jam.JAnnotation;
import com.bea.util.jam.JClass;
import com.bea.util.jam.JComment;
import com.bea.util.jam.JConstructor;
import com.bea.util.jam.JField;
import com.bea.util.jam.JMethod;
import com.bea.util.jam.JPackage;
import com.bea.util.jam.JParameter;
import com.bea.util.jam.JProperty;
import com.bea.util.jam.JTag;

public abstract class JVisitor {
   public void visit(JPackage pkg) {
   }

   public void visit(JClass clazz) {
   }

   public void visit(JConstructor ctor) {
   }

   public void visit(JField field) {
   }

   public void visit(JMethod method) {
   }

   public void visit(JParameter param) {
   }

   public void visit(JAnnotation ann) {
   }

   public void visit(JComment comment) {
   }

   public void visit(JProperty property) {
   }

   public void visit(JTag tag) {
   }
}
