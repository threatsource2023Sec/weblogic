package weblogic.entitlement.rules;

import javax.security.auth.Subject;
import weblogic.security.providers.authorization.IllegalPredicateArgumentException;
import weblogic.security.providers.authorization.PredicateArgument;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.Resource;

public class ContextElementIsSet extends ContextElementPredicate {
   private static final PredicateArgument[] arguments;

   public ContextElementIsSet() {
      super("ContextElementIsSetName", "ContextElementIsSetDescription");
   }

   public void init(String[] args) throws IllegalPredicateArgumentException {
      if (args != null && args.length == 1) {
         this.setElementName(args[0]);
      } else {
         throw new IllegalPredicateArgumentException("One Element name arguments is expected");
      }
   }

   public boolean evaluate(Subject subject, Resource resource, ContextHandler context) {
      return this.getElement(context) != null;
   }

   public int getArgumentCount() {
      return arguments.length;
   }

   public PredicateArgument getArgument(int index) {
      return arguments[index];
   }

   static {
      arguments = new PredicateArgument[]{ELEMENT_NAME_ARGUMENT};
   }
}
