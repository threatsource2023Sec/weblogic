package org.opensaml.soap.wstrust.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.AbstractXMLObjectMarshaller;
import org.opensaml.core.xml.io.MarshallingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public abstract class AbstractWSTrustObjectMarshaller extends AbstractXMLObjectMarshaller {
   private final Logger log = LoggerFactory.getLogger(AbstractWSTrustObjectMarshaller.class);

   protected AbstractWSTrustObjectMarshaller() {
   }

   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      this.log.debug("{} has no more attribute to marshall.", xmlObject.getElementQName().getLocalPart());
   }

   protected void marshallElementContent(XMLObject xmlObject, Element domElement) throws MarshallingException {
      this.log.debug("{} has no content to marshall.", xmlObject.getElementQName().getLocalPart());
   }
}
