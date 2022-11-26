package org.opensaml.saml.common;

import javax.annotation.Nonnull;
import org.opensaml.core.xml.XMLObjectBuilder;

public interface SAMLObjectBuilder extends XMLObjectBuilder {
   @Nonnull
   SAMLObject buildObject();
}
