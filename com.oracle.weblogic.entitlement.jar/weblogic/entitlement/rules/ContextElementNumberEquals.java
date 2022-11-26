package weblogic.entitlement.rules;

import javax.security.auth.Subject;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.Resource;

public class ContextElementNumberEquals extends ContextElementNumberPredicate {
   public ContextElementNumberEquals() {
      super("ContextElementNumberEqualsName", "ContextElementNumberEqualsDescription");
   }

   public boolean evaluate(Subject subject, Resource resource, ContextHandler context) {
      Object elementValue = this.getElement(context);
      return elementValue != null && getElementNumber(elementValue) == this.getArgumentNumber();
   }
}
