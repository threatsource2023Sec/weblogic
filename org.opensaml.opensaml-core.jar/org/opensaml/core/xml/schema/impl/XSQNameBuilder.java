package org.opensaml.core.xml.schema.impl;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.core.xml.schema.XSQName;

public class XSQNameBuilder extends AbstractXMLObjectBuilder {
   @Nonnull
   public XSQName buildObject(@Nullable String namespaceURI, @Nonnull @NotEmpty String localName, @Nullable String namespacePrefix) {
      return new XSQNameImpl(namespaceURI, localName, namespacePrefix);
   }
}
