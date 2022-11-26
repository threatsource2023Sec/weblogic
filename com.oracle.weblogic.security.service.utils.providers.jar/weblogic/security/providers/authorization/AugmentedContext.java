package weblogic.security.providers.authorization;

import weblogic.security.service.ContextElement;
import weblogic.security.service.ContextHandler;

public class AugmentedContext implements ContextHandler {
   private ContextHandler context;
   private ContextElement augmentedEltDeprecated = null;
   private ContextElement augmentedElt = null;
   private int elementCount = 0;
   private static final String DEPRECATED_EAUXNAME = "weblogic.entitlement.expression.EAuxiliary.ID";

   public AugmentedContext(ContextHandler context, String augName, Object augValue) {
      this.context = context;
      this.setAugmentedElement(augName, augValue);
   }

   public void setAugmentedElement(String augName, Object augValue) {
      if (augName == null) {
         throw new NullPointerException("null augmented context element name");
      } else {
         this.augmentedElt = new ContextElement(augName, augValue);
         this.elementCount = 1;
         if (augName.equalsIgnoreCase("com.bea.contextelement.entitlement.EAuxiliaryID")) {
            this.augmentedEltDeprecated = new ContextElement("weblogic.entitlement.expression.EAuxiliary.ID", augValue);
            ++this.elementCount;
         }

      }
   }

   public Object getValue(String name) {
      Object value = this.context != null ? this.context.getValue(name) : null;
      if (value == null) {
         if (name.equals(this.augmentedElt.getName())) {
            value = this.augmentedElt.getValue();
         } else if (name.equals(this.augmentedEltDeprecated.getName())) {
            value = this.augmentedEltDeprecated.getValue();
         }
      }

      return value;
   }

   public int size() {
      return this.context == null ? this.elementCount : this.elementCount + this.context.size();
   }

   public String[] getNames() {
      if (this.context == null) {
         return new String[]{this.augmentedElt.getName()};
      } else {
         String[] ctxNames = this.context.getNames();
         String[] names = new String[ctxNames.length + this.elementCount];
         System.arraycopy(ctxNames, 0, names, 0, ctxNames.length);
         names[names.length - this.elementCount] = this.augmentedElt.getName();
         if (this.elementCount == 2) {
            names[names.length - 1] = this.augmentedEltDeprecated.getName();
         }

         return names;
      }
   }

   public ContextElement[] getValues(String[] names) {
      ContextElement[] values = this.context != null ? this.context.getValues(names) : new ContextElement[names.length];
      String augName = this.augmentedElt == null ? null : this.augmentedElt.getName();
      String augNameDeprecated = this.augmentedEltDeprecated == null ? null : this.augmentedEltDeprecated.getName();

      for(int i = 0; i < names.length; ++i) {
         if (values[i] == null) {
            if (augName != null && augName.equals(names[i])) {
               values[i] = this.augmentedElt;
               break;
            }

            if (augNameDeprecated != null && augNameDeprecated.equals(names[i])) {
               values[i] = this.augmentedEltDeprecated;
               break;
            }
         }
      }

      return values;
   }
}
