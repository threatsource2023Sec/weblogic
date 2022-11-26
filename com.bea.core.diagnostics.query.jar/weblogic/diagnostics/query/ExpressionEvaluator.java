package weblogic.diagnostics.query;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.i18n.DiagnosticsTextTextFormatter;
import weblogic.utils.ArrayUtils;

class ExpressionEvaluator implements Operators {
   private static final BinaryOperator notEqualsOperator = new BinaryOperator() {
      public boolean evaluate(Object lhs, Object rhs) throws QueryExecutionException {
         return ExpressionEvaluator.evaluateNotEquals(lhs, rhs);
      }
   };
   private static final BinaryOperator equalsOperator = new BinaryOperator() {
      public boolean evaluate(Object lhs, Object rhs) throws QueryExecutionException {
         return ExpressionEvaluator.evaluateEquals(lhs, rhs);
      }
   };
   private static final BinaryOperator greaterThanEqualsOperator = new BinaryOperator() {
      public boolean evaluate(Object lhs, Object rhs) throws QueryExecutionException {
         return ExpressionEvaluator.evaluateGreaterThanEquals(lhs, rhs);
      }
   };
   private static final BinaryOperator lessThanEqualsOperator = new BinaryOperator() {
      public boolean evaluate(Object lhs, Object rhs) throws QueryExecutionException {
         return ExpressionEvaluator.evaluateLessThanEquals(lhs, rhs);
      }
   };
   private static final BinaryOperator greaterThanOperator = new BinaryOperator() {
      public boolean evaluate(Object lhs, Object rhs) throws QueryExecutionException {
         return ExpressionEvaluator.evaluateGreaterThan(lhs, rhs);
      }
   };
   private static final BinaryOperator lessThanOperator = new BinaryOperator() {
      public boolean evaluate(Object lhs, Object rhs) throws QueryExecutionException {
         return ExpressionEvaluator.evaluateLessThan(lhs, rhs);
      }
   };
   private static final DiagnosticsTextTextFormatter DTF = DiagnosticsTextTextFormatter.getInstance();
   private static DebugLogger queryDebugLogger = DebugLogger.getDebugLogger("DebugDiagnosticQuery");

   private static boolean performEvaluation(AtomNode left, AtomNode right, BinaryOperator operation) throws QueryExecutionException {
      Object lval;
      Object rval;
      try {
         lval = left.getValue();
         rval = right.getValue();
      } catch (UnknownVariableException var11) {
         return false;
      }

      if (lval != null && rval != null) {
         if (!lval.getClass().isArray() && !rval.getClass().isArray()) {
            if (lval instanceof VariableInstance) {
               lval = ((VariableInstance)lval).getInstanceValue();
            }

            if (rval instanceof VariableInstance) {
               rval = ((VariableInstance)rval).getInstanceValue();
            }

            return lval != null && rval != null ? operation.evaluate(lval, rval) : false;
         } else {
            Object[] lvalArray = lval.getClass().isArray() ? (Object[])((Object[])lval) : new Object[]{lval};
            Object[] rvalArray = rval.getClass().isArray() ? (Object[])((Object[])rval) : new Object[]{rval};

            for(int i = 0; i < lvalArray.length; ++i) {
               Object lhs = lvalArray[i];
               if (lhs instanceof VariableInstance) {
                  lhs = ((VariableInstance)lhs).getInstanceValue();
               }

               for(int j = 0; j < rvalArray.length; ++j) {
                  Object rhs = rvalArray[j];
                  if (rhs instanceof VariableInstance) {
                     rhs = ((VariableInstance)rhs).getInstanceValue();
                  }

                  if (lhs != null && rhs != null && operation.evaluate(lhs, rhs)) {
                     return true;
                  }
               }
            }

            return false;
         }
      } else {
         return false;
      }
   }

   static boolean evaluateLessThan(AtomNode left, AtomNode right) throws QueryExecutionException {
      if (queryDebugLogger.isDebugEnabled()) {
         queryDebugLogger.debug("Executing < operator");
         queryDebugLogger.debug("Left hand = " + left + " Right hand = " + right);
      }

      return performEvaluation(left, right, lessThanOperator);
   }

   static boolean evaluateLessThan(Object lval, Object rval) throws QueryExecutionException {
      if (lval != null && rval != null) {
         double dl;
         double dr;
         if (lval instanceof Number && rval instanceof Number) {
            dl = ((Number)lval).doubleValue();
            dr = ((Number)rval).doubleValue();
            return dl < dr;
         } else if (lval instanceof String && rval instanceof String) {
            String sl = (String)lval;
            String sr = (String)rval;
            return sl.compareTo(sr) < 0;
         } else if (lval instanceof String && rval instanceof Number) {
            try {
               dl = Double.parseDouble((String)lval);
               dr = ((Number)rval).doubleValue();
               return dl < dr;
            } catch (NumberFormatException var6) {
               throw new TypeMismatchException(DTF.getMismatchOperandTypesText("<", lval.toString(), rval.toString()));
            }
         } else {
            throw new TypeMismatchException(DTF.getMismatchOperandTypesText("<", lval.toString(), rval.toString()));
         }
      } else {
         return false;
      }
   }

   static boolean evaluateGreaterThan(AtomNode left, AtomNode right) throws QueryExecutionException {
      if (queryDebugLogger.isDebugEnabled()) {
         queryDebugLogger.debug("Executing > operator");
         queryDebugLogger.debug("Left hand = " + left + " Right hand = " + right);
      }

      return performEvaluation(left, right, greaterThanOperator);
   }

   static boolean evaluateGreaterThan(Object lval, Object rval) throws QueryExecutionException {
      double dl;
      double dr;
      if (lval instanceof Number && rval instanceof Number) {
         dl = ((Number)lval).doubleValue();
         dr = ((Number)rval).doubleValue();
         return dl > dr;
      } else if (lval instanceof String && rval instanceof String) {
         String sl = (String)lval;
         String sr = (String)rval;
         return sl.compareTo(sr) > 0;
      } else if (lval instanceof String && rval instanceof Number) {
         try {
            dl = Double.parseDouble((String)lval);
            dr = ((Number)rval).doubleValue();
            return dl > dr;
         } catch (NumberFormatException var6) {
            throw new TypeMismatchException(DTF.getMismatchOperandTypesText(">", lval.toString(), rval.toString()));
         }
      } else {
         throw new TypeMismatchException(DTF.getMismatchOperandTypesText(">", lval.toString(), rval.toString()));
      }
   }

   static boolean evaluateLessThanEquals(AtomNode left, AtomNode right) throws QueryExecutionException {
      if (queryDebugLogger.isDebugEnabled()) {
         queryDebugLogger.debug("Executing <= operator");
         queryDebugLogger.debug("Left hand = " + left + " Right hand = " + right);
      }

      return performEvaluation(left, right, lessThanEqualsOperator);
   }

   static boolean evaluateLessThanEquals(Object lval, Object rval) throws QueryExecutionException {
      double dl;
      double dr;
      if (lval instanceof Number && rval instanceof Number) {
         dl = ((Number)lval).doubleValue();
         dr = ((Number)rval).doubleValue();
         return dl <= dr;
      } else if (lval instanceof String && rval instanceof String) {
         String sl = (String)lval;
         String sr = (String)rval;
         return sl.compareTo(sr) < 0 || sl.equals(sr);
      } else if (lval instanceof String && rval instanceof Number) {
         try {
            dl = Double.parseDouble((String)lval);
            dr = ((Number)rval).doubleValue();
            return dl <= dr;
         } catch (NumberFormatException var6) {
            throw new TypeMismatchException(DTF.getMismatchOperandTypesText("<=", lval.toString(), rval.toString()));
         }
      } else {
         throw new TypeMismatchException(DTF.getMismatchOperandTypesText("<=", lval.toString(), rval.toString()));
      }
   }

   static boolean evaluateGreaterThanEquals(AtomNode left, AtomNode right) throws QueryExecutionException {
      if (queryDebugLogger.isDebugEnabled()) {
         queryDebugLogger.debug("Executing >= operator");
         queryDebugLogger.debug("Left hand = " + left + " Right hand = " + right);
      }

      return performEvaluation(left, right, greaterThanEqualsOperator);
   }

   static boolean evaluateGreaterThanEquals(Object lval, Object rval) throws QueryExecutionException {
      double dl;
      double dr;
      if (lval instanceof Number && rval instanceof Number) {
         dl = ((Number)lval).doubleValue();
         dr = ((Number)rval).doubleValue();
         return dl >= dr;
      } else if (lval instanceof String && rval instanceof String) {
         String sl = (String)lval;
         String sr = (String)rval;
         return sl.compareTo(sr) > 0 || sl.equals(sr);
      } else if (lval instanceof String && rval instanceof Number) {
         try {
            dl = Double.parseDouble((String)lval);
            dr = ((Number)rval).doubleValue();
            return dl >= dr;
         } catch (NumberFormatException var6) {
            throw new TypeMismatchException(DTF.getMismatchOperandTypesText(">=", lval.toString(), rval.toString()));
         }
      } else {
         throw new TypeMismatchException(DTF.getMismatchOperandTypesText(">=", lval.toString(), rval.toString()));
      }
   }

   static boolean evaluateEquals(AtomNode left, AtomNode right) throws QueryExecutionException {
      if (queryDebugLogger.isDebugEnabled()) {
         queryDebugLogger.debug("Executing == operator");
         queryDebugLogger.debug("Left hand = " + left + " Right hand = " + right);
      }

      return performEvaluation(left, right, equalsOperator);
   }

   static boolean evaluateEquals(Object lval, Object rval) throws QueryExecutionException {
      double dl;
      double dr;
      if (lval instanceof Number && rval instanceof Number) {
         dl = ((Number)lval).doubleValue();
         dr = ((Number)rval).doubleValue();
         return dl == dr;
      } else if (lval instanceof String && rval instanceof Number) {
         try {
            dl = Double.parseDouble((String)lval);
            dr = ((Number)rval).doubleValue();
            return dl == dr;
         } catch (NumberFormatException var6) {
            throw new TypeMismatchException(DTF.getMismatchOperandTypesText("=", lval.toString(), rval.toString()));
         }
      } else {
         return lval.equals(rval);
      }
   }

   static boolean evaluateNotEquals(AtomNode left, AtomNode right) throws QueryExecutionException {
      if (queryDebugLogger.isDebugEnabled()) {
         queryDebugLogger.debug("Executing != operator");
         queryDebugLogger.debug("Left hand = " + left + " Right hand = " + right);
      }

      return performEvaluation(left, right, notEqualsOperator);
   }

   static boolean evaluateNotEquals(Object lval, Object rval) throws QueryExecutionException {
      double dl;
      double dr;
      if (lval instanceof Number && rval instanceof Number) {
         dl = ((Number)lval).doubleValue();
         dr = ((Number)rval).doubleValue();
         return dl != dr;
      } else if (lval instanceof String && rval instanceof Number) {
         try {
            dl = Double.parseDouble((String)lval);
            dr = ((Number)rval).doubleValue();
            return dl != dr;
         } catch (NumberFormatException var6) {
            throw new TypeMismatchException(DTF.getMismatchOperandTypesText("!=", lval.toString(), rval.toString()));
         }
      } else {
         return !lval.equals(rval);
      }
   }

   static boolean evaluateLike(AtomNode left, AtomNode right) throws QueryExecutionException {
      if (queryDebugLogger.isDebugEnabled()) {
         queryDebugLogger.debug("Executing LIKE operator");
         queryDebugLogger.debug("Left hand = " + left + " Right hand = " + right);
      }

      Object lval;
      Object rval;
      try {
         lval = left.getValue();
         rval = right.getValue();
      } catch (UnknownVariableException var8) {
         return false;
      }

      if (lval != null && rval != null) {
         Pattern p = right.getPattern(true);
         if (lval.getClass().isArray()) {
            Object[] lvalArray = (Object[])((Object[])lval);

            for(int i = 0; i < lvalArray.length; ++i) {
               Object lhs = lvalArray[i];
               if (lhs instanceof VariableInstance) {
                  lhs = ((VariableInstance)lhs).getInstanceValue();
               }

               if (lhs != null && evaluatePatternMatch(lhs.toString(), p)) {
                  return true;
               }
            }

            return false;
         } else {
            if (lval instanceof VariableInstance) {
               lval = ((VariableInstance)lval).getInstanceValue();
            }

            return evaluatePatternMatch(lval.toString(), p);
         }
      } else {
         return false;
      }
   }

   static boolean evaluateMatches(AtomNode left, AtomNode right) throws QueryExecutionException {
      if (queryDebugLogger.isDebugEnabled()) {
         queryDebugLogger.debug("Executing MATCHES operator");
         queryDebugLogger.debug("Left hand = " + left + " Right hand = " + right);
      }

      Object lval;
      Object rval;
      try {
         lval = left.getValue();
         rval = right.getValue();
      } catch (UnknownVariableException var8) {
         return false;
      }

      if (lval != null && rval != null) {
         Pattern p = right.getPattern(false);
         if (lval.getClass().isArray()) {
            Object[] lvalArray = (Object[])((Object[])lval);

            for(int i = 0; i < lvalArray.length; ++i) {
               Object lhs = lvalArray[i];
               if (lhs instanceof VariableInstance) {
                  lhs = ((VariableInstance)lhs).getInstanceValue();
               }

               if (lhs != null && evaluatePatternMatch(lhs.toString(), p)) {
                  return true;
               }
            }

            return false;
         } else {
            if (lval instanceof VariableInstance) {
               lval = ((VariableInstance)lval).getInstanceValue();
            }

            return evaluatePatternMatch(lval.toString(), p);
         }
      } else {
         return false;
      }
   }

   static boolean evaluatePatternMatch(String lval, Pattern pattern) throws QueryExecutionException {
      if (lval == null) {
         return false;
      } else {
         try {
            if (queryDebugLogger.isDebugEnabled()) {
               queryDebugLogger.debug("Matching " + lval + " against the regex " + pattern.pattern());
            }

            Matcher matcher = pattern.matcher(lval);
            return matcher.matches();
         } catch (PatternSyntaxException var3) {
            throw new QueryExecutionException(DTF.getPatternSyntaxErrorText(lval));
         }
      }
   }

   static boolean evaluateIn(AtomNode left, AtomNode right) throws QueryExecutionException {
      if (queryDebugLogger.isDebugEnabled()) {
         queryDebugLogger.debug("Executing IN operator");
         queryDebugLogger.debug("Left hand = " + left + " Right hand = " + right);
      }

      try {
         Object lval = left.getValue();
         if (lval == null) {
            return false;
         } else {
            Set rval = (Set)right.getValue();
            if (lval.getClass().isArray()) {
               Object[] lvalArray = (Object[])((Object[])lval);

               for(int i = 0; i < lvalArray.length; ++i) {
                  Object lhs = lvalArray[i];
                  if (lhs instanceof VariableInstance) {
                     lhs = ((VariableInstance)lhs).getInstanceValue();
                  }

                  if (lhs != null && evaluateIn(lhs, rval)) {
                     return true;
                  }
               }

               return false;
            } else {
               if (lval instanceof VariableInstance) {
                  lval = ((VariableInstance)lval).getInstanceValue();
               }

               return evaluateIn(lval, rval);
            }
         }
      } catch (UnknownVariableException var7) {
         return false;
      }
   }

   static boolean evaluateIn(Object lval, Set set) throws QueryExecutionException {
      if (lval == null) {
         return false;
      } else {
         if (queryDebugLogger.isDebugEnabled()) {
            queryDebugLogger.debug("LValue= " + lval.toString());
            queryDebugLogger.debug("RValue = " + ArrayUtils.toString(set.toArray()));
         }

         return set.contains(lval);
      }
   }

   static long evaluateBitwiseAnd(AtomNode left, AtomNode right) throws QueryExecutionException {
      long l = left.getLongValue();
      long r = right.getLongValue();
      return l & r;
   }

   static long evaluateBitwiseOr(AtomNode left, AtomNode right) throws QueryExecutionException {
      long l = left.getLongValue();
      long r = right.getLongValue();
      return l | r;
   }

   private interface BinaryOperator {
      boolean evaluate(Object var1, Object var2) throws QueryExecutionException;
   }
}
