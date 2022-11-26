package com.bea.util.jam.visitor;

import com.bea.util.jam.mutable.MAnnotation;
import com.bea.util.jam.mutable.MClass;
import com.bea.util.jam.mutable.MComment;
import com.bea.util.jam.mutable.MConstructor;
import com.bea.util.jam.mutable.MField;
import com.bea.util.jam.mutable.MMethod;
import com.bea.util.jam.mutable.MPackage;
import com.bea.util.jam.mutable.MParameter;
import com.bea.util.jam.mutable.MTag;

public abstract class MVisitor {
   public void visit(MPackage pkg) {
   }

   public void visit(MClass clazz) {
   }

   public void visit(MConstructor ctor) {
   }

   public void visit(MField field) {
   }

   public void visit(MMethod method) {
   }

   public void visit(MParameter param) {
   }

   public void visit(MAnnotation ann) {
   }

   public void visit(MComment comment) {
   }

   public void visit(MTag tag) {
   }
}
