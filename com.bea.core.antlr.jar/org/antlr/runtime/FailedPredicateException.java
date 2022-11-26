package org.antlr.runtime;

public class FailedPredicateException extends RecognitionException {
   public String ruleName;
   public String predicateText;

   public FailedPredicateException() {
   }

   public FailedPredicateException(IntStream input, String ruleName, String predicateText) {
      super(input);
      this.ruleName = ruleName;
      this.predicateText = predicateText;
   }

   public String toString() {
      return "FailedPredicateException(" + this.ruleName + ",{" + this.predicateText + "}?)";
   }
}
