package weblogic.management.provider.internal;

import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Descriptor;
import org.glassfish.hk2.api.Filter;
import org.glassfish.hk2.api.IterableProvider;
import org.glassfish.hk2.api.Operation;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.api.ValidationInformation;
import org.glassfish.hk2.api.ValidationService;
import org.glassfish.hk2.api.Validator;
import weblogic.management.provider.RuntimeAccess;
import weblogic.utils.annotation.AdminServer;
import weblogic.utils.annotation.ManagedServer;

@Singleton
public class AdminManagedValidatorService implements ValidationService, Validator {
   private static final Filter FILTER = new Filter() {
      public boolean matches(Descriptor d) {
         return d.getQualifiers().contains(ManagedServer.class.getName()) || d.getQualifiers().contains(AdminServer.class.getName());
      }
   };
   @Inject
   private IterableProvider runtimeAccess;

   public Filter getLookupFilter() {
      return FILTER;
   }

   public Validator getValidator() {
      return this;
   }

   private boolean isAccessReady() {
      ServiceHandle handle = this.runtimeAccess.getHandle();
      return handle.isActive();
   }

   public boolean validate(ValidationInformation info) {
      Operation op = info.getOperation();
      if (!op.equals(Operation.LOOKUP)) {
         return true;
      } else if (!this.isAccessReady()) {
         return false;
      } else {
         boolean isAdmin = ((RuntimeAccess)this.runtimeAccess.get()).isAdminServer();
         ActiveDescriptor desc = info.getCandidate();
         Set qualifiers = desc.getQualifiers();
         if (isAdmin && qualifiers.contains(ManagedServer.class.getName())) {
            return false;
         } else {
            return isAdmin || !qualifiers.contains(AdminServer.class.getName());
         }
      }
   }
}
