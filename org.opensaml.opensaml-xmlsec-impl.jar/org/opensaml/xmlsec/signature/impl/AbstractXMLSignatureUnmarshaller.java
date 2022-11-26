package org.opensaml.xmlsec.signature.impl;

import javax.annotation.Nonnull;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.AbstractXMLObjectUnmarshaller;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;

public abstract class AbstractXMLSignatureUnmarshaller extends AbstractXMLObjectUnmarshaller {
   private final Logger log = LoggerFactory.getLogger(AbstractXMLSignatureUnmarshaller.class);

   protected void processChildElement(@Nonnull XMLObject parentXMLObject, @Nonnull XMLObject childXMLObject) throws UnmarshallingException {
      this.log.debug("Ignoring unknown element {}", childXMLObject.getElementQName());
   }

   protected void processAttribute(@Nonnull XMLObject xmlObject, @Nonnull Attr attribute) throws UnmarshallingException {
      this.log.debug("Ignorning unknown attribute {}", attribute.getLocalName());
   }

   protected void processElementContent(@Nonnull XMLObject xmlObject, @Nonnull String elementContent) {
      this.log.debug("Ignoring element content {}", elementContent);
   }
}
