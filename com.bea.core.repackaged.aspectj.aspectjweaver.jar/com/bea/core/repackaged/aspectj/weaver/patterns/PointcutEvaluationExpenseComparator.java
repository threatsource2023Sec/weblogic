package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.weaver.Shadow;
import java.util.Comparator;

public class PointcutEvaluationExpenseComparator implements Comparator {
   private static final int MATCHES_NOTHING = -1;
   private static final int WITHIN = 1;
   private static final int ATWITHIN = 2;
   private static final int STATICINIT = 3;
   private static final int ADVICEEXECUTION = 4;
   private static final int HANDLER = 5;
   private static final int GET_OR_SET = 6;
   private static final int WITHINCODE = 7;
   private static final int ATWITHINCODE = 8;
   private static final int EXE_INIT_PREINIT = 9;
   private static final int CALL_WITH_DECLARING_TYPE = 10;
   private static final int THIS_OR_TARGET = 11;
   private static final int CALL_WITHOUT_DECLARING_TYPE = 12;
   private static final int ANNOTATION = 13;
   private static final int AT_THIS_OR_TARGET = 14;
   private static final int ARGS = 15;
   private static final int AT_ARGS = 16;
   private static final int CFLOW = 17;
   private static final int IF = 18;
   private static final int OTHER = 20;

   public int compare(Pointcut p1, Pointcut p2) {
      if (p1.equals(p2)) {
         return 0;
      } else {
         int result = this.getScore(p1) - this.getScore(p2);
         if (result == 0) {
            int p1code = p1.hashCode();
            int p2code = p2.hashCode();
            if (p1code == p2code) {
               return 0;
            } else {
               return p1code < p2code ? -1 : 1;
            }
         } else {
            return result;
         }
      }
   }

   private int getScore(Pointcut p) {
      if (p.couldMatchKinds() == Shadow.NO_SHADOW_KINDS_BITS) {
         return -1;
      } else if (p instanceof WithinPointcut) {
         return 1;
      } else if (p instanceof WithinAnnotationPointcut) {
         return 2;
      } else if (p instanceof KindedPointcut) {
         KindedPointcut kp = (KindedPointcut)p;
         Shadow.Kind kind = kp.getKind();
         if (kind == Shadow.AdviceExecution) {
            return 4;
         } else if (kind != Shadow.ConstructorCall && kind != Shadow.MethodCall) {
            if (kind != Shadow.ConstructorExecution && kind != Shadow.MethodExecution && kind != Shadow.Initialization && kind != Shadow.PreInitialization) {
               if (kind == Shadow.ExceptionHandler) {
                  return 5;
               } else if (kind != Shadow.FieldGet && kind != Shadow.FieldSet) {
                  return kind == Shadow.StaticInitialization ? 3 : 20;
               } else {
                  return 6;
               }
            } else {
               return 9;
            }
         } else {
            TypePattern declaringTypePattern = kp.getSignature().getDeclaringType();
            return declaringTypePattern instanceof AnyTypePattern ? 12 : 10;
         }
      } else if (p instanceof AnnotationPointcut) {
         return 13;
      } else if (p instanceof ArgsPointcut) {
         return 15;
      } else if (p instanceof ArgsAnnotationPointcut) {
         return 16;
      } else if (!(p instanceof CflowPointcut) && !(p instanceof ConcreteCflowPointcut)) {
         if (p instanceof HandlerPointcut) {
            return 5;
         } else if (p instanceof IfPointcut) {
            return 18;
         } else if (p instanceof ThisOrTargetPointcut) {
            return 11;
         } else if (p instanceof ThisOrTargetAnnotationPointcut) {
            return 14;
         } else if (p instanceof WithincodePointcut) {
            return 7;
         } else if (p instanceof WithinCodeAnnotationPointcut) {
            return 8;
         } else if (p instanceof NotPointcut) {
            return this.getScore(((NotPointcut)p).getNegatedPointcut());
         } else {
            int leftScore;
            int rightScore;
            if (p instanceof AndPointcut) {
               leftScore = this.getScore(((AndPointcut)p).getLeft());
               rightScore = this.getScore(((AndPointcut)p).getRight());
               return leftScore < rightScore ? leftScore : rightScore;
            } else if (p instanceof OrPointcut) {
               leftScore = this.getScore(((OrPointcut)p).getLeft());
               rightScore = this.getScore(((OrPointcut)p).getRight());
               return leftScore > rightScore ? leftScore : rightScore;
            } else {
               return 20;
            }
         }
      } else {
         return 17;
      }
   }
}
