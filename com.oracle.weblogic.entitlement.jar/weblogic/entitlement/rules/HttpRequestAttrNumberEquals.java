package weblogic.entitlement.rules;

import javax.security.auth.Subject;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.Resource;

public class HttpRequestAttrNumberEquals extends HttpRequestAttrNumberPredicate {
   public HttpRequestAttrNumberEquals() {
      super("HttpRequestAttrNumberEqualsName", "HttpRequestAttrNumberEqualsDescription");
   }

   public boolean evaluate(Subject subject, Resource resource, ContextHandler context) {
      Object attrValue = this.getAttribute(context);
      return attrValue != null && getAttributeNumber(attrValue) == this.getArgumentNumber();
   }
}
