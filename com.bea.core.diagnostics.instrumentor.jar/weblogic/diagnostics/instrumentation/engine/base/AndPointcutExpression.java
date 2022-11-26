package weblogic.diagnostics.instrumentation.engine.base;

import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.diagnostics.instrumentation.InvalidPointcutException;

public class AndPointcutExpression implements PointcutExpression {
   private static final long serialVersionUID = 2878597053676489618L;
   private PointcutExpression pointcutExpr1;
   private PointcutExpression pointcutExpr2;
   private transient boolean keepHint = false;

   public AndPointcutExpression(PointcutExpression pointcutExpr1, PointcutExpression pointcutExpr2) {
      this.pointcutExpr1 = pointcutExpr1;
      this.pointcutExpr2 = pointcutExpr2;
   }

   public MatchInfo isEligibleCallsite(ClassInstrumentor classInstrumentor, String className, String methodName, String methodDesc) throws InvalidPointcutException {
      return this.isEligibleCallsite(classInstrumentor, className, methodName, methodDesc, (MethodInfo)null);
   }

   public MatchInfo isEligibleCallsite(ClassInstrumentor classInstrumentor, String className, String methodName, String methodDesc, MethodInfo methodInfo) throws InvalidPointcutException {
      MatchInfo match1 = this.pointcutExpr1.isEligibleCallsite(classInstrumentor, className, methodName, methodDesc, methodInfo);
      if (!match1.isMatch()) {
         return MatchInfo.NO_MATCH;
      } else {
         MatchInfo match2 = this.pointcutExpr2.isEligibleCallsite(classInstrumentor, className, methodName, methodDesc, methodInfo);
         if (!match2.isMatch()) {
            return MatchInfo.NO_MATCH;
         } else if (match1 == MatchInfo.PROBABLE_MATCH) {
            return match2;
         } else if (match2 == MatchInfo.PROBABLE_MATCH) {
            return match1;
         } else if (!MatchInfo.compareInfo(match1, match2)) {
            throw new InvalidPointcutException(DiagnosticsLogger.getInconsistentHandlingModifiersSpecified(className, methodName, methodDesc));
         } else {
            return match1;
         }
      }
   }

   public MatchInfo isEligibleMethod(ClassInstrumentor classInstrumentor, String className, MethodInfo methodInfo) throws InvalidPointcutException {
      MatchInfo match1 = this.pointcutExpr1.isEligibleMethod(classInstrumentor, className, methodInfo);
      if (!match1.isMatch()) {
         return MatchInfo.NO_MATCH;
      } else {
         MatchInfo match2 = this.pointcutExpr2.isEligibleMethod(classInstrumentor, className, methodInfo);
         if (!match2.isMatch()) {
            return MatchInfo.NO_MATCH;
         } else if (match1 == MatchInfo.PROBABLE_MATCH) {
            return match2;
         } else if (match2 == MatchInfo.PROBABLE_MATCH) {
            return match1;
         } else if (!MatchInfo.compareInfo(match1, match2)) {
            throw new InvalidPointcutException(DiagnosticsLogger.getInconsistentHandlingModifiersSpecified(className, methodInfo.getMethodName(), methodInfo.getMethodDesc()));
         } else {
            return match1;
         }
      }
   }

   public MatchInfo isEligibleCatchBlock(ClassInstrumentor classInstrumentor, String exceptionClassName, MethodInfo methodInfo) throws InvalidPointcutException {
      MatchInfo match1 = this.pointcutExpr1.isEligibleCatchBlock(classInstrumentor, exceptionClassName, methodInfo);
      if (!match1.isMatch()) {
         return MatchInfo.NO_MATCH;
      } else {
         MatchInfo match2 = this.pointcutExpr2.isEligibleCatchBlock(classInstrumentor, exceptionClassName, methodInfo);
         if (!match2.isMatch()) {
            return MatchInfo.NO_MATCH;
         } else if (match1 == MatchInfo.PROBABLE_MATCH) {
            return match2;
         } else {
            return match2 == MatchInfo.PROBABLE_MATCH ? match1 : match1;
         }
      }
   }

   public void accept(PointcutExpressionVisitor visitor) {
      visitor.visit(this);
      this.pointcutExpr1.accept(visitor);
      this.pointcutExpr2.accept(visitor);
   }

   public void markAsKeep() {
      this.keepHint = true;
      this.pointcutExpr1.markAsKeep();
      this.pointcutExpr2.markAsKeep();
   }

   public boolean getKeepHint() {
      return this.keepHint;
   }
}
