package com.oracle.wls.shaded.org.apache.xpath.axes;

import com.oracle.wls.shaded.org.apache.xpath.Expression;
import com.oracle.wls.shaded.org.apache.xpath.ExpressionOwner;
import com.oracle.wls.shaded.org.apache.xpath.XPathVisitor;
import com.oracle.wls.shaded.org.apache.xpath.functions.FuncLast;
import com.oracle.wls.shaded.org.apache.xpath.functions.FuncPosition;
import com.oracle.wls.shaded.org.apache.xpath.functions.Function;
import com.oracle.wls.shaded.org.apache.xpath.objects.XNumber;
import com.oracle.wls.shaded.org.apache.xpath.operations.Div;
import com.oracle.wls.shaded.org.apache.xpath.operations.Minus;
import com.oracle.wls.shaded.org.apache.xpath.operations.Mod;
import com.oracle.wls.shaded.org.apache.xpath.operations.Mult;
import com.oracle.wls.shaded.org.apache.xpath.operations.Number;
import com.oracle.wls.shaded.org.apache.xpath.operations.Plus;
import com.oracle.wls.shaded.org.apache.xpath.operations.Quo;
import com.oracle.wls.shaded.org.apache.xpath.operations.Variable;

public class HasPositionalPredChecker extends XPathVisitor {
   private boolean m_hasPositionalPred = false;
   private int m_predDepth = 0;

   public static boolean check(LocPathIterator path) {
      HasPositionalPredChecker hppc = new HasPositionalPredChecker();
      path.callVisitors((ExpressionOwner)null, hppc);
      return hppc.m_hasPositionalPred;
   }

   public boolean visitFunction(ExpressionOwner owner, Function func) {
      if (func instanceof FuncPosition || func instanceof FuncLast) {
         this.m_hasPositionalPred = true;
      }

      return true;
   }

   public boolean visitPredicate(ExpressionOwner owner, Expression pred) {
      ++this.m_predDepth;
      if (this.m_predDepth == 1) {
         if (!(pred instanceof Variable) && !(pred instanceof XNumber) && !(pred instanceof Div) && !(pred instanceof Plus) && !(pred instanceof Minus) && !(pred instanceof Mod) && !(pred instanceof Quo) && !(pred instanceof Mult) && !(pred instanceof Number) && !(pred instanceof Function)) {
            pred.callVisitors(owner, this);
         } else {
            this.m_hasPositionalPred = true;
         }
      }

      --this.m_predDepth;
      return false;
   }
}
