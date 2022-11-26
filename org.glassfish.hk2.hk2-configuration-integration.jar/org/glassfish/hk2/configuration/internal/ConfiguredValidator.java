package org.glassfish.hk2.configuration.internal;

import javax.inject.Singleton;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.DescriptorVisibility;
import org.glassfish.hk2.api.Filter;
import org.glassfish.hk2.api.Operation;
import org.glassfish.hk2.api.ValidationInformation;
import org.glassfish.hk2.api.Validator;
import org.glassfish.hk2.api.Visibility;

@Singleton
@Visibility(DescriptorVisibility.LOCAL)
public class ConfiguredValidator implements Validator {
   private boolean validateLookup(ValidationInformation info) {
      ActiveDescriptor candidate = info.getCandidate();
      if (candidate.getName() != null) {
         return true;
      } else if (info.getInjectee() != null) {
         return false;
      } else {
         Filter f = info.getFilter();
         return f != null && f instanceof NoNameTypeFilter;
      }
   }

   public boolean validate(ValidationInformation info) {
      if (Operation.LOOKUP.equals(info.getOperation())) {
         return this.validateLookup(info);
      } else if (Operation.BIND.equals(info.getOperation())) {
         return true;
      } else {
         return Operation.UNBIND.equals(info.getOperation()) ? true : true;
      }
   }
}
