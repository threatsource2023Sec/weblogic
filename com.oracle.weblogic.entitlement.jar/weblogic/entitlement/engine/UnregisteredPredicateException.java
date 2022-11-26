package weblogic.entitlement.engine;

public class UnregisteredPredicateException extends RuntimeException {
   private String predicateName;

   public UnregisteredPredicateException(String predicateName) {
      super("Unregistered predicate: " + predicateName);
      this.predicateName = predicateName;
   }

   public String getPredicateName() {
      return this.predicateName;
   }
}
