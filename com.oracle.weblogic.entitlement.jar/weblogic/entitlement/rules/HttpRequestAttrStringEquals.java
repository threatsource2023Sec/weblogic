package weblogic.entitlement.rules;

import javax.security.auth.Subject;
import weblogic.security.providers.authorization.IllegalPredicateArgumentException;
import weblogic.security.providers.authorization.PredicateArgument;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.Resource;

public class HttpRequestAttrStringEquals extends HttpRequestAttrPredicate {
   private static final PredicateArgument[] arguments;
   private String stringValue = null;

   public HttpRequestAttrStringEquals() {
      super("HttpRequestAttrStringEqualsName", "HttpRequestAttrStringEqualsDescription");
   }

   public void init(String[] args) throws IllegalPredicateArgumentException {
      if (args != null && args.length == 2) {
         this.setAttributeName(args[0]);
         this.stringValue = args[1];
      } else {
         throw new IllegalPredicateArgumentException("Two arguments are expected");
      }
   }

   public boolean evaluate(Subject subject, Resource resource, ContextHandler context) {
      Object attrValue = this.getAttribute(context);
      return attrValue != null && attrValue.toString().equals(this.stringValue);
   }

   public int getArgumentCount() {
      return arguments.length;
   }

   public PredicateArgument getArgument(int index) {
      return arguments[index];
   }

   static {
      arguments = new PredicateArgument[]{ATTR_NAME_ARGUMENT, new StringPredicateArgument()};
   }
}
