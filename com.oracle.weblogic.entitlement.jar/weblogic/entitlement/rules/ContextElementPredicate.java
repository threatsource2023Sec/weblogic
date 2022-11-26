package weblogic.entitlement.rules;

import weblogic.security.providers.authorization.IllegalPredicateArgumentException;
import weblogic.security.providers.authorization.PredicateArgument;
import weblogic.security.service.ContextHandler;

public abstract class ContextElementPredicate extends BasePredicate {
   private static final String VERSION = "1.0";
   protected static final PredicateArgument ELEMENT_NAME_ARGUMENT = new StringPredicateArgument("ContextElementPredicateElementArgumentName", "ContextElementPredicateElementArgumentDescription", (String)null);
   private String elementName;

   public ContextElementPredicate(String displayNameId, String descriptionId) {
      super(displayNameId, descriptionId);
   }

   protected String getElementName() {
      return this.elementName;
   }

   protected void setElementName(String elementName) throws IllegalPredicateArgumentException {
      if (elementName == null) {
         throw new IllegalPredicateArgumentException("Null context element name");
      } else {
         this.elementName = elementName;
      }
   }

   protected Object getElement(ContextHandler context) {
      return context != null ? context.getValue(this.elementName) : null;
   }

   public String getVersion() {
      return "1.0";
   }
}
