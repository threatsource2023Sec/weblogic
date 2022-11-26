package weblogic.entitlement.rules;

import weblogic.security.providers.authorization.IllegalPredicateArgumentException;
import weblogic.security.providers.authorization.PredicateArgument;
import weblogic.security.shared.LoggerWrapper;

public abstract class ScopePredicate extends BasePredicate {
   private static final String VERSION = "1.0";
   private static PredicateArgument SCOPE_ARG = new StringPredicateArgument();
   private static PredicateArgument IDD_ARG = new IDDPredicateArgument();
   private static PredicateArgument[] arguments;
   private String scope = null;
   private String idd = null;
   protected static final LoggerWrapper log;

   public ScopePredicate(String displayNameId, String descriptionId) {
      super(displayNameId, descriptionId);
   }

   public void init(String[] args) throws IllegalPredicateArgumentException {
      if (args != null && args.length >= 1) {
         if (args.length > 2) {
            throw new IllegalPredicateArgumentException("Maximum two arguments are expected");
         } else {
            this.scope = args[0];
            this.idd = args.length > 1 ? args[1] : null;
         }
      } else {
         throw new IllegalPredicateArgumentException("At least one argument is expected");
      }
   }

   protected String getScope() {
      return this.scope;
   }

   protected String getIdd() {
      return this.idd;
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
      arguments = new PredicateArgument[]{SCOPE_ARG, IDD_ARG};
      log = LoggerWrapper.getInstance("SecurityPredicate");
   }
}
