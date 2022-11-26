package weblogic.entitlement.rules;

import javax.security.auth.Subject;
import weblogic.security.providers.authorization.IllegalPredicateArgumentException;
import weblogic.security.providers.authorization.PredicateArgument;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.Resource;

public class ContextElementStringEquals extends ContextElementPredicate {
   private static final PredicateArgument[] arguments;
   private String stringValue = null;

   public ContextElementStringEquals() {
      super("ContextElementStringEqualsName", "ContextElementStringEqualsDescription");
   }

   public void init(String[] args) throws IllegalPredicateArgumentException {
      if (args != null && args.length == 2) {
         this.setElementName(args[0]);
         this.stringValue = args[1];
      } else {
         throw new IllegalPredicateArgumentException("Two arguments are expected");
      }
   }

   public boolean evaluate(Subject subject, Resource resource, ContextHandler context) {
      Object elementValue = this.getElement(context);
      return elementValue != null && elementValue.toString().equals(this.stringValue);
   }

   public int getArgumentCount() {
      return arguments.length;
   }

   public PredicateArgument getArgument(int index) {
      return arguments[index];
   }

   static {
      arguments = new PredicateArgument[]{ELEMENT_NAME_ARGUMENT, new StringPredicateArgument()};
   }
}
