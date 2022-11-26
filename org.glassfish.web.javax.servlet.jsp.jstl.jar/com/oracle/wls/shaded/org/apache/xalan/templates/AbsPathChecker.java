package com.oracle.wls.shaded.org.apache.xalan.templates;

import com.oracle.wls.shaded.org.apache.xpath.ExpressionOwner;
import com.oracle.wls.shaded.org.apache.xpath.XPathVisitor;
import com.oracle.wls.shaded.org.apache.xpath.axes.LocPathIterator;
import com.oracle.wls.shaded.org.apache.xpath.functions.FuncCurrent;
import com.oracle.wls.shaded.org.apache.xpath.functions.FuncExtFunction;
import com.oracle.wls.shaded.org.apache.xpath.functions.Function;
import com.oracle.wls.shaded.org.apache.xpath.operations.Variable;

public class AbsPathChecker extends XPathVisitor {
   private boolean m_isAbs = true;

   public boolean checkAbsolute(LocPathIterator path) {
      this.m_isAbs = true;
      path.callVisitors((ExpressionOwner)null, this);
      return this.m_isAbs;
   }

   public boolean visitFunction(ExpressionOwner owner, Function func) {
      if (func instanceof FuncCurrent || func instanceof FuncExtFunction) {
         this.m_isAbs = false;
      }

      return true;
   }

   public boolean visitVariableRef(ExpressionOwner owner, Variable var) {
      this.m_isAbs = false;
      return true;
   }
}
