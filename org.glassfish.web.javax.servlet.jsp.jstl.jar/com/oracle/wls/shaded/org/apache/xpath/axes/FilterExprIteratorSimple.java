package com.oracle.wls.shaded.org.apache.xpath.axes;

import com.oracle.wls.shaded.org.apache.xml.utils.PrefixResolver;
import com.oracle.wls.shaded.org.apache.xml.utils.WrappedRuntimeException;
import com.oracle.wls.shaded.org.apache.xpath.Expression;
import com.oracle.wls.shaded.org.apache.xpath.ExpressionOwner;
import com.oracle.wls.shaded.org.apache.xpath.VariableStack;
import com.oracle.wls.shaded.org.apache.xpath.XPathContext;
import com.oracle.wls.shaded.org.apache.xpath.XPathVisitor;
import com.oracle.wls.shaded.org.apache.xpath.objects.XNodeSet;
import java.util.Vector;
import javax.xml.transform.TransformerException;

public class FilterExprIteratorSimple extends LocPathIterator {
   static final long serialVersionUID = -6978977187025375579L;
   private Expression m_expr;
   private transient XNodeSet m_exprObj;
   private boolean m_mustHardReset = false;
   private boolean m_canDetachNodeset = true;

   public FilterExprIteratorSimple() {
      super((PrefixResolver)null);
   }

   public FilterExprIteratorSimple(Expression expr) {
      super((PrefixResolver)null);
      this.m_expr = expr;
   }

   public void setRoot(int context, Object environment) {
      super.setRoot(context, environment);
      this.m_exprObj = executeFilterExpr(context, this.m_execContext, this.getPrefixResolver(), this.getIsTopLevel(), this.m_stackFrame, this.m_expr);
   }

   public static XNodeSet executeFilterExpr(int context, XPathContext xctxt, PrefixResolver prefixResolver, boolean isTopLevel, int stackFrame, Expression expr) throws WrappedRuntimeException {
      PrefixResolver savedResolver = xctxt.getNamespaceContext();
      XNodeSet result = null;

      try {
         xctxt.pushCurrentNode(context);
         xctxt.setNamespaceContext(prefixResolver);
         if (isTopLevel) {
            VariableStack vars = xctxt.getVarStack();
            int savedStart = vars.getStackFrame();
            vars.setStackFrame(stackFrame);
            result = (XNodeSet)expr.execute(xctxt);
            result.setShouldCacheNodes(true);
            vars.setStackFrame(savedStart);
         } else {
            result = (XNodeSet)expr.execute(xctxt);
         }
      } catch (TransformerException var14) {
         throw new WrappedRuntimeException(var14);
      } finally {
         xctxt.popCurrentNode();
         xctxt.setNamespaceContext(savedResolver);
      }

      return result;
   }

   public int nextNode() {
      if (this.m_foundLast) {
         return -1;
      } else {
         int next;
         if (null != this.m_exprObj) {
            this.m_lastFetched = next = this.m_exprObj.nextNode();
         } else {
            next = -1;
            this.m_lastFetched = -1;
         }

         if (-1 != next) {
            ++this.m_pos;
            return next;
         } else {
            this.m_foundLast = true;
            return -1;
         }
      }
   }

   public void detach() {
      if (this.m_allowDetach) {
         super.detach();
         this.m_exprObj.detach();
         this.m_exprObj = null;
      }

   }

   public void fixupVariables(Vector vars, int globalsSize) {
      super.fixupVariables(vars, globalsSize);
      this.m_expr.fixupVariables(vars, globalsSize);
   }

   public Expression getInnerExpression() {
      return this.m_expr;
   }

   public void setInnerExpression(Expression expr) {
      expr.exprSetParent(this);
      this.m_expr = expr;
   }

   public int getAnalysisBits() {
      return null != this.m_expr && this.m_expr instanceof PathComponent ? ((PathComponent)this.m_expr).getAnalysisBits() : 67108864;
   }

   public boolean isDocOrdered() {
      return this.m_exprObj.isDocOrdered();
   }

   public void callPredicateVisitors(XPathVisitor visitor) {
      this.m_expr.callVisitors(new filterExprOwner(), visitor);
      super.callPredicateVisitors(visitor);
   }

   public boolean deepEquals(Expression expr) {
      if (!super.deepEquals(expr)) {
         return false;
      } else {
         FilterExprIteratorSimple fet = (FilterExprIteratorSimple)expr;
         return this.m_expr.deepEquals(fet.m_expr);
      }
   }

   public int getAxis() {
      return null != this.m_exprObj ? this.m_exprObj.getAxis() : 20;
   }

   class filterExprOwner implements ExpressionOwner {
      public Expression getExpression() {
         return FilterExprIteratorSimple.this.m_expr;
      }

      public void setExpression(Expression exp) {
         exp.exprSetParent(FilterExprIteratorSimple.this);
         FilterExprIteratorSimple.this.m_expr = exp;
      }
   }
}
