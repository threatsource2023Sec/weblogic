package org.opensaml.saml.metadata.resolver.filter;

import javax.annotation.Nullable;
import org.opensaml.core.xml.XMLObject;

public interface MetadataFilter {
   @Nullable
   XMLObject filter(@Nullable XMLObject var1) throws FilterException;
}
