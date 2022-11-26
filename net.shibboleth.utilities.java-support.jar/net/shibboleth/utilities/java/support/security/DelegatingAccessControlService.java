package net.shibboleth.utilities.java.support.security;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.ParameterName;
import net.shibboleth.utilities.java.support.component.AbstractIdentifiableInitializableComponent;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.service.ReloadableService;
import net.shibboleth.utilities.java.support.service.ServiceableComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DelegatingAccessControlService extends AbstractIdentifiableInitializableComponent implements AccessControlService {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(DelegatingAccessControlService.class);
   private final ReloadableService service;

   public DelegatingAccessControlService(@Nonnull @ParameterName(name = "acService") ReloadableService acService) {
      this.service = (ReloadableService)Constraint.isNotNull(acService, "AccessControlService cannot be null");
   }

   public AccessControl getInstance(@Nonnull String name) {
      ComponentSupport.ifNotInitializedThrowUninitializedComponentException(this);
      ServiceableComponent component = null;

      AccessControl var4;
      try {
         component = this.service.getServiceableComponent();
         if (null == component) {
            this.log.error("AccessControlService '{}': Error accessing underlying component: Invalid configuration.", this.getId());
            return null;
         }

         AccessControlService svc = (AccessControlService)component.getComponent();
         var4 = svc.getInstance(name);
      } finally {
         if (null != component) {
            component.unpinComponent();
         }

      }

      return var4;
   }
}
