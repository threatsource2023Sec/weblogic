package weblogic.management.security.audit;

import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.modelmbean.ModelMBean;
import javax.management.modelmbean.RequiredModelMBean;
import weblogic.management.security.ProviderImpl;

public class ContextHandlerImpl extends ProviderImpl {
   public ContextHandlerImpl(ModelMBean base) throws MBeanException {
      super(base);
   }

   protected ContextHandlerImpl(RequiredModelMBean base) throws MBeanException {
      super(base);
   }

   private ContextHandlerMBean getMyMBean() {
      try {
         return (ContextHandlerMBean)this.getProxy();
      } catch (MBeanException var2) {
         throw new AssertionError(var2);
      }
   }

   public boolean validateActiveContextHandlerEntries(String[] activeContextHandlerEntries) throws InvalidAttributeValueException {
      String[] supportedContextHandlerEntries = this.getMyMBean().getSupportedContextHandlerEntries();
      int i = 0;

      while(true) {
         if (activeContextHandlerEntries != null && i < activeContextHandlerEntries.length) {
            String activeContextHandlerEntry = activeContextHandlerEntries[i];
            if (activeContextHandlerEntry != null && activeContextHandlerEntry.length() >= 1) {
               boolean found = false;

               for(int j = 0; !found && supportedContextHandlerEntries != null && j < supportedContextHandlerEntries.length; ++j) {
                  if (activeContextHandlerEntry.equals(supportedContextHandlerEntries[j])) {
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
