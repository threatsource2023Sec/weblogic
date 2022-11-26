package weblogic.entitlement.rules;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import weblogic.security.providers.authorization.IllegalPredicateArgumentException;
import weblogic.security.providers.authorization.PredicateArgument;
import weblogic.security.service.ContextHandler;

public abstract class HttpRequestAttrPredicate extends BasePredicate {
   private static final String VERSION = "1.0";
   protected static final PredicateArgument ATTR_NAME_ARGUMENT = new StringPredicateArgument("HttpRequestAttrPredicateAttributeArgumentName", "HttpRequestAttrPredicateAttributeArgumentDescription", (String)null) {
      public void validateValue(Object value, Locale locale) throws IllegalPredicateArgumentException {
         super.validateValue(value, locale);
         String attrName = (String)value;
         if (!attrName.startsWith("Attribute.") && !attrName.startsWith("Header.") && !attrName.startsWith("Parameter.") && !attrName.equals("Method") && !attrName.equals("RequestURI") && !attrName.equals("QueryString") && !attrName.equals("Protocol") && !attrName.equals("LocalPort") && !attrName.equals("RemotePort") && !attrName.equals("RemoteHost") && !attrName.startsWith("Session.Attribute.")) {
            String errorMsg = (new PredicateTextFormatter(locale)).getInvalidAttributeNameMessage(attrName);
            throw new IllegalPredicateArgumentException(errorMsg);
         }
      }
   };
   private String attrName;

   public HttpRequestAttrPredicate(String displayNameId, String descriptionId) {
      super(displayNameId, descriptionId);
   }

   protected String getAttributeName() {
      return this.attrName;
   }

   protected void setAttributeName(String attrName) throws IllegalPredicateArgumentException {
      ATTR_NAME_ARGUMENT.validateValue(attrName, (Locale)null);
      this.attrName = attrName;
   }

   protected Object getAttribute(ContextHandler context) {
      if (context == null) {
         return null;
      } else {
         Object value = null;
         HttpServletRequest request = (HttpServletRequest)context.getValue("com.bea.contextelement.servlet.HttpServletRequest");
         if (request != null) {
            if (this.attrName.startsWith("Attribute.")) {
               value = request.getAttribute(this.attrName.substring("Attribute.".length()));
            } else if (this.attrName.startsWith("Header.")) {
               value = request.getHeader(this.attrName.substring("Header.".length()));
            } else if (this.attrName.startsWith("Parameter.")) {
               value = request.getParameter(this.attrName.substring("Parameter.".length()));
            } else if (this.attrName.equals("Method")) {
               value = request.getMethod();
            } else if (this.attrName.equals("RequestURI")) {
               value = request.getRequestURI();
            } else if (this.attrName.equals("QueryString")) {
               value = request.getQueryString();
            } else if (this.attrName.equals("Protocol")) {
               value = request.getProtocol();
            } else if (this.attrName.equals("LocalPort")) {
               value = new Integer(request.getLocalPort());
            } else if (this.attrName.equals("RemotePort")) {
               value = new Integer(request.getRemotePort());
            } else if (this.attrName.equals("RemoteHost")) {
               value = request.getRemoteHost();
            } else {
               if (!this.attrName.startsWith("Session.Attribute.")) {
                  throw new IllegalArgumentException("Not a supported attribute: " + this.attrName);
               }

               HttpSession session = request.getSession();
               if (session != null) {
                  value = session.getAttribute(this.attrName.substring("Session.Attribute.".length()));
               }
            }
         }

         return value;
      }
   }

   public boolean isSupportedResource(String resourceId) {
      return resourceId.startsWith("type=<url>");
   }

   public String getVersion() {
      return "1.0";
   }
}
