package weblogic.entitlement.rules;

import weblogic.security.providers.authorization.IllegalPredicateArgumentException;
import weblogic.security.providers.authorization.PredicateArgument;

public abstract class IDDGroupPredicate extends BasePredicate {
   private static final String VERSION = "1.0";
   private static PredicateArgument GROUP_ARG = new GroupnamePredicateArgument();
   private static PredicateArgument IDD_ARG = new IDDPredicateArgument();
   private static PredicateArgument[] arguments;
   private String group = null;
   private String idd = null;

   public IDDGroupPredicate(String displayNameId, String descriptionId) {
      super(displayNameId, descriptionId);
   }

   public void init(String[] args) throws IllegalPredicateArgumentException {
      if (args != null && args.length == 2) {
         this.group = args[0];
         this.idd = args[1];
      } else {
         throw new IllegalPredicateArgumentException("Two arguments are expected");
      }
   }

   protected String getGroup() {
      return this.group;
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
      arguments = new PredicateArgument[]{GROUP_ARG, IDD_ARG};
   }
}
