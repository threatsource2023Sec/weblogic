package org.opensaml.soap.wsaddressing.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.AbstractXMLObjectUnmarshaller;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;

public abstract class AbstractWSAddressingObjectUnmarshaller extends AbstractXMLObjectUnmarshaller {
   private final Logger log = LoggerFactory.getLogger(AbstractWSAddressingObjectUnmarshaller.class);

   protected AbstractWSAddressingObjectUnmarshaller() {
   }

   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      this.log.warn("{} ignoring unknown child element {}", parentXMLObject.getElementQName().getLocalPart(), childXMLObject.getElementQName().getLocalPart());
   }

   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      this.log.warn("{} ignoring unknown attribute {}", xmlObject.getElementQName().getLocalPart(), attribute.getLocalName());
   }

   protected void processElementContent(XMLObject xmlObject, String elementContent) {
      this.log.warn("{} ignoring unknown element content: {}", xmlObject.getElementQName().getLocalPart(), elementContent);
   }
}
