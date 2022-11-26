package weblogic.entitlement.rules;

import weblogic.security.providers.authorization.IllegalPredicateArgumentException;
import weblogic.security.providers.authorization.PredicateArgument;

public abstract class ContextElementNumberPredicate extends ContextElementPredicate {
   private static final PredicateArgument[] arguments;
   private double numValue = 0.0;

   public ContextElementNumberPredicate(String displayNameId, String descriptionId) {
      super(displayNameId, descriptionId);
   }

   public void init(String[] args) throws IllegalPredicateArgumentException {
      if (args != null && args.length == 2) {
         this.setElementName(args[0]);
         this.numValue = parseNumber(args[1]);
      } else {
         throw new IllegalPredicateArgumentException("Two arguments are expected");
      }
   }

   private static double parseNumber(String value) throws IllegalPredicateArgumentException {
      return ((Number)arguments[1].parseExprValue(value)).doubleValue();
   }

   protected static double getElementNumber(Object ElementValue) {
      if (ElementValue instanceof Number) {
         return ((Number)ElementValue).doubleValue();
      } else {
         if (ElementValue instanceof String) {
            try {
               return parseNumber((String)ElementValue);
            } catch (IllegalPredicateArgumentException var2) {
            }
         }

         throw new RuntimeException("Element value is not a number");
      }
   }

   protected double getArgumentNumber() {
      return this.numValue;
   }

   public int getArgumentCount() {
      return arguments.length;
   }

   public PredicateArgument getArgument(int index) {
      return arguments[index];
   }

   static {
      arguments = new PredicateArgument[]{ELEMENT_NAME_ARGUMENT, new NumberPredicateArgument()};
   }
}
