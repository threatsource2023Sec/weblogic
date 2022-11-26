package com.bea.core.repackaged.aspectj.internal.lang.reflect;

import com.bea.core.repackaged.aspectj.lang.reflect.PerClauseKind;
import com.bea.core.repackaged.aspectj.lang.reflect.PointcutBasedPerClause;
import com.bea.core.repackaged.aspectj.lang.reflect.PointcutExpression;

public class PointcutBasedPerClauseImpl extends PerClauseImpl implements PointcutBasedPerClause {
   private final PointcutExpression pointcutExpression;

   public PointcutBasedPerClauseImpl(PerClauseKind kind, String pointcutExpression) {
      super(kind);
      this.pointcutExpression = new PointcutExpressionImpl(pointcutExpression);
   }

   public PointcutExpression getPointcutExpression() {
      return this.pointcutExpression;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      switch (this.getKind()) {
         case PERCFLOW:
            sb.append("percflow(");
            break;
         case PERCFLOWBELOW:
            sb.append("percflowbelow(");
            break;
         case PERTARGET:
            sb.append("pertarget(");
            break;
         case PERTHIS:
            sb.append("perthis(");
      }

      sb.append(this.pointcutExpression.asString());
      sb.append(")");
      return sb.toString();
   }
}
