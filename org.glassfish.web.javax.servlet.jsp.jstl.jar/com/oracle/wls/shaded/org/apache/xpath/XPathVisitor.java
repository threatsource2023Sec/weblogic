package com.oracle.wls.shaded.org.apache.xpath;

import com.oracle.wls.shaded.org.apache.xpath.axes.LocPathIterator;
import com.oracle.wls.shaded.org.apache.xpath.axes.UnionPathIterator;
import com.oracle.wls.shaded.org.apache.xpath.functions.Function;
import com.oracle.wls.shaded.org.apache.xpath.objects.XNumber;
import com.oracle.wls.shaded.org.apache.xpath.objects.XString;
import com.oracle.wls.shaded.org.apache.xpath.operations.Operation;
import com.oracle.wls.shaded.org.apache.xpath.operations.UnaryOperation;
import com.oracle.wls.shaded.org.apache.xpath.operations.Variable;
import com.oracle.wls.shaded.org.apache.xpath.patterns.NodeTest;
import com.oracle.wls.shaded.org.apache.xpath.patterns.StepPattern;
import com.oracle.wls.shaded.org.apache.xpath.patterns.UnionPattern;

public class XPathVisitor {
   public boolean visitLocationPath(ExpressionOwner owner, LocPathIterator path) {
      return true;
   }

   public boolean visitUnionPath(ExpressionOwner owner, UnionPathIterator path) {
      return true;
   }

   public boolean visitStep(ExpressionOwner owner, NodeTest step) {
      return true;
   }

   public boolean visitPredicate(ExpressionOwner owner, Expression pred) {
      return true;
   }

   public boolean visitBinaryOperation(ExpressionOwner owner, Operation op) {
      return true;
   }

   public boolean visitUnaryOperation(ExpressionOwner owner, UnaryOperation op) {
      return true;
   }

   public boolean visitVariableRef(ExpressionOwner owner, Variable var) {
      return true;
   }

   public boolean visitFunction(ExpressionOwner owner, Function func) {
      return true;
   }

   public boolean visitMatchPattern(ExpressionOwner owner, StepPattern pattern) {
      return true;
   }

   public boolean visitUnionPattern(ExpressionOwner owner, UnionPattern pattern) {
      return true;
   }

   public boolean visitStringLiteral(ExpressionOwner owner, XString str) {
      return true;
   }

   public boolean visitNumberLiteral(ExpressionOwner owner, XNumber num) {
      return true;
   }
}
