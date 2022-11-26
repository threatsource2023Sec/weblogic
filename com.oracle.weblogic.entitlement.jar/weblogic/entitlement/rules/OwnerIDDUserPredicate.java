package weblogic.entitlement.rules;

import weblogic.security.providers.authorization.IllegalPredicateArgumentException;
import weblogic.security.providers.authorization.PredicateArgument;

public abstract class OwnerIDDUserPredicate extends BasePredicate {
   private static final String VERSION = "1.0";
   private static PredicateArgument USER_ARG = new UsernamePredicateArgument();
   private static PredicateArgument[] arguments;
   private String user = null;

   public OwnerIDDUserPredicate(String displayNameId, String descriptionId) {
      super(displayNameId, descriptionId);
   }

   public void init(String[] args) throws IllegalPredicateArgumentException {
      if (args != null && args.length == 1) {
         this.user = args[0];
      } else {
         throw new IllegalPredicateArgumentException("One argument is expected");
      }
   }

   protected String getUser() {
      return this.user;
   }

   public String getVersion() {
      return "1.0";
   }

   public int getArgumentCount() {
      return arguments.length;
   }

   public PredicateArgument getArgument(int index) {
      return arguments[index];
   }

   static {
      arguments = new PredicateArgument[]{USER_ARG};
   }
}
