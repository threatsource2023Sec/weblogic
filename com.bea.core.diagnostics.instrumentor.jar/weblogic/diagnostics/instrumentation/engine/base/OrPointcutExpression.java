package weblogic.diagnostics.instrumentation.engine.base;

import weblogic.diagnostics.instrumentation.InvalidPointcutException;

public class OrPointcutExpression implements PointcutExpression {
   private static final long serialVersionUID = 5211854999346760983L;
   private PointcutExpression pointcutExpr1;
   private PointcutExpression pointcutExpr2;
   private transient boolean keepHint = false;

   public OrPointcutExpression(PointcutExpression pointcutExpr1, PointcutExpression pointcutExpr2) {
      this.pointcutExpr1 = pointcutExpr1;
      this.pointcutExpr2 = pointcutExpr2;
   }

   public MatchInfo isEligibleCallsite(ClassInstrumentor classInstrumentor, String className, String methodName, String methodDesc) throws InvalidPointcutException {
      return this.isEligibleCallsite(classInstrumentor, className, methodName, methodDesc, (MethodInfo)null);
   }

   public MatchInfo isEligibleCallsite(ClassInstrumentor classInstrumentor, String className, String methodName, String methodDesc, MethodInfo methodInfo) throws InvalidPointcutException {
      MatchInfo match1 = this.pointcutExpr1.isEligibleCallsite(classInstrumentor, className, methodName, methodDesc, methodInfo);
      if (match1 != MatchInfo.PROBABLE_MATCH && match1.isMatch()) {
         return match1;
      } else {
         MatchInfo match2 = this.pointcutExpr2.isEligibleCallsite(classInstrumentor, className, methodName, methodDesc, methodInfo);
         if (match2 != MatchInfo.PROBABLE_MATCH && match2.isMatch()) {
            return match2;
         } else {
            return match1 != MatchInfo.PROBABLE_MATCH && match2 != MatchInfo.PROBABLE_MATCH ? MatchInfo.NO_MATCH : MatchInfo.PROBABLE_MATCH;
         }
      }
   }

   public MatchInfo isEligibleMethod(ClassInstrumentor classInstrumentor, String className, MethodInfo methodInfo) throws InvalidPointcutException {
      MatchInfo match1 = this.pointcutExpr1.isEligibleMethod(classInstrumentor, className, methodInfo);
      if (match1 != MatchInfo.PROBABLE_MATCH && match1.isMatch()) {
         return match1;
      } else {
         MatchInfo match2 = this.pointcutExpr2.isEligibleMethod(classInstrumentor, className, methodInfo);
         if (match2 != MatchInfo.PROBABLE_MATCH && match2.isMatch()) {
            return match2;
         } else {
            return match1 != MatchInfo.PROBABLE_MATCH && match2 != MatchInfo.PROBABLE_MATCH ? MatchInfo.NO_MATCH : MatchInfo.PROBABLE_MATCH;
         }
      }
   }

   public MatchInfo isEligibleCatchBlock(ClassInstrumentor classInstrumentor, String exceptionClassName, MethodInfo methodInfo) throws InvalidPointcutException {
      MatchInfo match1 = this.pointcutExpr1.isEligibleCatchBlock(classInstrumentor, exceptionClassName, methodInfo);
      if (match1 != MatchInfo.PROBABLE_MATCH && match1.isMatch()) {
         return match1;
      } else {
         MatchInfo match2 = this.pointcutExpr2.isEligibleCatchBlock(classInstrumentor, exceptionClassName, methodInfo);
         if (match2 != MatchInfo.PROBABLE_MATCH && match2.isMatch()) {
            return match2;
         } else {
            return match1 != MatchInfo.PROBABLE_MATCH && match2 != MatchInfo.PROBABLE_MATCH ? MatchInfo.NO_MATCH : MatchInfo.PROBABLE_MATCH;
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
