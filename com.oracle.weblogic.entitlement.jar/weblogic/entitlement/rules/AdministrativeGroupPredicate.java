package weblogic.entitlement.rules;

import weblogic.security.providers.authorization.IllegalPredicateArgumentException;
import weblogic.security.providers.authorization.PredicateArgument;

public abstract class AdministrativeGroupPredicate extends BasePredicate {
   private static final String VERSION = "1.0";
   private static PredicateArgument GROUP_ARG = new GroupnamePredicateArgument();
   private static PredicateArgument[] arguments;
   private String group = null;

   public AdministrativeGroupPredicate(String displayNameId, String descriptionId) {
      super(displayNameId, descriptionId);
   }

   public void init(String[] args) throws IllegalPredicateArgumentException {
      if (args != null && args.length == 1) {
         this.group = args[0];
      } else {
         throw new IllegalPredicateArgumentException("One argument is expected");
      }
   }

   protected String getGroup() {
      return this.group;
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
      arguments = new PredicateArgument[]{GROUP_ARG};
   }
}
