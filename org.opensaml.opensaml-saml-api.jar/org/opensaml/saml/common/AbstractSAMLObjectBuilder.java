package org.opensaml.saml.common;

import javax.annotation.Nonnull;
import org.opensaml.core.xml.AbstractXMLObjectBuilder;

public abstract class AbstractSAMLObjectBuilder extends AbstractXMLObjectBuilder implements SAMLObjectBuilder {
   @Nonnull
   public abstract SAMLObject buildObject();
}
