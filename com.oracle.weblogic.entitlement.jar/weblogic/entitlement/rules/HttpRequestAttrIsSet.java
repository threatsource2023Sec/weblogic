package weblogic.entitlement.rules;

import javax.security.auth.Subject;
import weblogic.security.providers.authorization.IllegalPredicateArgumentException;
import weblogic.security.providers.authorization.PredicateArgument;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.Resource;

public class HttpRequestAttrIsSet extends HttpRequestAttrPredicate {
   private static final PredicateArgument[] arguments;

   public HttpRequestAttrIsSet() {
      super("HttpRequestAttrIsSetName", "HttpRequestAttrIsSetDescription");
   }

   public void init(String[] args) throws IllegalPredicateArgumentException {
      if (args != null && args.length == 1) {
         this.setAttributeName(args[0]);
      } else {
         throw new IllegalPredicateArgumentException("One attribute name arguments is expected");
      }
   }

   public boolean evaluate(Subject subject, Resource resource, ContextHandler context) {
      return this.getAttribute(context) != null;
   }

   public int getArgumentCount() {
      return arguments.length;
   }

   public PredicateArgument getArgument(int index) {
      return arguments[index];
   }

   static {
      arguments = new PredicateArgument[]{ATTR_NAME_ARGUMENT};
   }
}
