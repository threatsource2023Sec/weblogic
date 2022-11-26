package org.opensaml.saml.metadata.resolver;

import net.shibboleth.utilities.java.support.component.IdentifiedComponent;
import net.shibboleth.utilities.java.support.resolver.Resolver;

public interface RoleDescriptorResolver extends Resolver, IdentifiedComponent {
   boolean isRequireValidMetadata();

   void setRequireValidMetadata(boolean var1);
}
