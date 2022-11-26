package weblogic.diagnostics.instrumentation.engine.base;

import weblogic.diagnostics.instrumentation.InvalidPointcutException;

public class NotPointcutExpression implements PointcutExpression {
   private static final long serialVersionUID = -1772270149417855053L;
   private PointcutExpression pointcutExpr;
   private transient boolean keepHint = false;

   public NotPointcutExpression(PointcutExpression pointcutExpr) {
      this.pointcutExpr = pointcutExpr;
   }

   public MatchInfo isEligibleCallsite(ClassInstrumentor classInstrumentor, String className, String methodName, String methodDesc) throws InvalidPointcutException {
      return this.isEligibleCallsite(classInstrumentor, className, methodName, methodDesc, (MethodInfo)null);
   }

   public MatchInfo isEligibleCallsite(ClassInstrumentor classInstrumentor, String className, String methodName, String methodDesc, MethodInfo methodInfo) throws InvalidPointcutException {
      MatchInfo match = this.pointcutExpr.isEligibleCallsite(classInstrumentor, className, methodName, methodDesc, methodInfo);
      if (match == MatchInfo.PROBABLE_MATCH) {
         return match;
      } else {
         return match.isMatch() ? MatchInfo.NO_MATCH : MatchInfo.SIMPLE_MATCH;
      }
   }

   public MatchInfo isEligibleMethod(ClassInstrumentor classInstrumentor, String className, MethodInfo methodInfo) throws InvalidPointcutException {
      MatchInfo match = this.pointcutExpr.isEligibleMethod(classInstrumentor, className, methodInfo);
      if (match == MatchInfo.PROBABLE_MATCH) {
         return match;
      } else {
         return match.isMatch() ? MatchInfo.NO_MATCH : MatchInfo.SIMPLE_MATCH;
      }
   }

   public MatchInfo isEligibleCatchBlock(ClassInstrumentor classInstrumentor, String exceptionClassName, MethodInfo methodInfo) throws InvalidPointcutException {
      MatchInfo match = this.pointcutExpr.isEligibleCatchBlock(classInstrumentor, exceptionClassName, methodInfo);
      if (match == MatchInfo.PROBABLE_MATCH) {
         return match;
      } else {
         return match.isMatch() ? MatchInfo.NO_MATCH : MatchInfo.SIMPLE_MATCH;
      }
   }

   public void accept(PointcutExpressionVisitor visitor) {
      visitor.visit(this);
      this.pointcutExpr.accept(visitor);
   }

   public void markAsKeep() {
      this.keepHint = true;
      this.pointcutExpr.markAsKeep();
   }

   public boolean getKeepHint() {
      return this.keepHint;
   }
}
