package com.bea.xml_.impl.jam.visitor;

import com.bea.xml_.impl.jam.mutable.MAnnotation;
import com.bea.xml_.impl.jam.mutable.MClass;
import com.bea.xml_.impl.jam.mutable.MComment;
import com.bea.xml_.impl.jam.mutable.MConstructor;
import com.bea.xml_.impl.jam.mutable.MField;
import com.bea.xml_.impl.jam.mutable.MMethod;
import com.bea.xml_.impl.jam.mutable.MPackage;
import com.bea.xml_.impl.jam.mutable.MParameter;

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
}
