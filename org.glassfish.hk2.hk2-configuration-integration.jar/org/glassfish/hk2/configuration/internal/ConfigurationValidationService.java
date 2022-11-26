package org.glassfish.hk2.configuration.internal;

import javax.inject.Inject;
import javax.inject.Singleton;
import org.glassfish.hk2.api.Descriptor;
import org.glassfish.hk2.api.DescriptorVisibility;
import org.glassfish.hk2.api.Filter;
import org.glassfish.hk2.api.ValidationService;
import org.glassfish.hk2.api.Validator;
import org.glassfish.hk2.api.Visibility;
import org.glassfish.hk2.configuration.api.ConfiguredBy;

@Singleton
@Visibility(DescriptorVisibility.LOCAL)
public class ConfigurationValidationService implements ValidationService {
   private static final Filter LOOKUP_FILTER = new Filter() {
      public boolean matches(Descriptor d) {
         return ConfiguredBy.class.getName().equals(d.getScope());
      }
   };
   @Inject
   private ConfiguredValidator validator;

   public Filter getLookupFilter() {
      return LOOKUP_FILTER;
   }

   public Validator getValidator() {
      return this.validator;
   }
}
