package org.opensaml.saml.common;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;
import org.opensaml.core.xml.io.AbstractXMLObjectUnmarshaller;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.w3c.dom.Attr;

@ThreadSafe
public abstract class AbstractSAMLObjectUnmarshaller extends AbstractXMLObjectUnmarshaller {
   @Nonnull
   protected SAMLVersion parseSAMLVersion(@Nonnull Attr attribute) throws UnmarshallingException {
      try {
         return SAMLVersion.valueOf(attribute.getValue());
      } catch (RuntimeException var3) {
         throw new UnmarshallingException(String.format("Could not parse SAMLVersion from DOM attribute value '%s'", attribute.getValue()), var3);
      }
   }
}
