package weblogic.entitlement.expression;

import weblogic.entitlement.parser.ParseException;

public class InvalidPredicateClassException extends ParseException {
   public InvalidPredicateClassException() {
   }

   public InvalidPredicateClassException(String message) {
      super(message);
   }
}
