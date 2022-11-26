package org.opensaml.saml.metadata.resolver;

import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.joda.time.DateTime;

public interface RefreshableMetadataResolver extends MetadataResolver {
   void refresh() throws ResolverException;

   @Nullable
   DateTime getLastRefresh();

   @Nullable
   DateTime getLastUpdate();
}
