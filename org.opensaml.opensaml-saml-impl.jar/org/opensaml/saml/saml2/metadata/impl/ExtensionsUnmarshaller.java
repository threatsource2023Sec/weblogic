package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml2.metadata.Extensions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;

public class ExtensionsUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   private final Logger log = LoggerFactory.getLogger(AbstractSAMLObjectUnmarshaller.class);

   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      Extensions extensions = (Extensions)parentXMLObject;
      extensions.getUnknownXMLObjects().add(childXMLObject);
   }

   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      this.log.debug("Ignorning unknown attribute {}", attribute.getLocalName());
   }

   protected void processElementContent(XMLObject xmlObject, String elementContent) {
      this.log.debug("Ignoring element content {}", elementContent);
   }
}
