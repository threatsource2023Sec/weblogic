package weblogic.management.security.authentication;

import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.modelmbean.ModelMBean;
import javax.management.modelmbean.RequiredModelMBean;

public class IdentityAsserterImpl extends AuthenticationProviderImpl {
   public IdentityAsserterImpl(ModelMBean base) throws MBeanException {
      super(base);
   }

   protected IdentityAsserterImpl(RequiredModelMBean base) throws MBeanException {
      super(base);
   }

   private IdentityAsserterMBean getMyMBean() {
      try {
         return (IdentityAsserterMBean)this.getProxy();
      } catch (MBeanException var2) {
         throw new AssertionError(var2);
      }
   }

   public boolean validateActiveTypes(String[] activeTypes) throws InvalidAttributeValueException {
      String[] supportedTypes = this.getMyMBean().getSupportedTypes();
      int i = 0;

      while(true) {
         if (activeTypes != null && i < activeTypes.length) {
            String activeType = activeTypes[i];
            if (activeType != null && activeType.length() >= 1) {
               boolean found = false;

               for(int j = 0; !found && supportedTypes != null && j < supportedTypes.length; ++j) {
                  if (activeType.equals(supportedTypes[j])) {
                     found = true;
                  }
               }

               if (!found) {
                  return false;
               }

               ++i;
               continue;
            }

            return false;
         }

         return true;
      }
   }
}
