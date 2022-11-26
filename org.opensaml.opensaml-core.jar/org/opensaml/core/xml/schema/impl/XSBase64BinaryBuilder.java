package org.opensaml.core.xml.schema.impl;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.core.xml.schema.XSBase64Binary;

public class XSBase64BinaryBuilder extends AbstractXMLObjectBuilder {
   @Nonnull
   public XSBase64Binary buildObject(@Nullable String namespaceURI, @Nonnull @NotEmpty String localName, @Nullable String namespacePrefix) {
      return new XSBase64BinaryImpl(namespaceURI, localName, namespacePrefix);
   }
}
