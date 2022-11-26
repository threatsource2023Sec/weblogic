package com.bea.xml_.impl.jam.visitor;

import com.bea.xml_.impl.jam.JAnnotation;
import com.bea.xml_.impl.jam.JClass;
import com.bea.xml_.impl.jam.JComment;
import com.bea.xml_.impl.jam.JConstructor;
import com.bea.xml_.impl.jam.JField;
import com.bea.xml_.impl.jam.JMethod;
import com.bea.xml_.impl.jam.JPackage;
import com.bea.xml_.impl.jam.JParameter;
import com.bea.xml_.impl.jam.JProperty;

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

   public void visit(JProperty proprty) {
   }
}
