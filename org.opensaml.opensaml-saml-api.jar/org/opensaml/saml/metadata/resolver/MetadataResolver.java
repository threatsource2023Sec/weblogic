package org.opensaml.saml.metadata.resolver;

import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.component.IdentifiedComponent;
import net.shibboleth.utilities.java.support.resolver.Resolver;
import org.opensaml.saml.metadata.resolver.filter.MetadataFilter;

public interface MetadataResolver extends Resolver, IdentifiedComponent {
   boolean isRequireValidMetadata();

   void setRequireValidMetadata(boolean var1);

   @Nullable
   MetadataFilter getMetadataFilter();

   void setMetadataFilter(@Nullable MetadataFilter var1);
}
