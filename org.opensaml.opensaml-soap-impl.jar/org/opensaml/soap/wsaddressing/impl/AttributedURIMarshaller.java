package org.opensaml.soap.wsaddressing.impl;

import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wsaddressing.AttributedURI;
import org.w3c.dom.Element;

public class AttributedURIMarshaller extends AbstractWSAddressingObjectMarshaller {
   protected void marshallElementContent(XMLObject xmlObject, Element domElement) throws MarshallingException {
      AttributedURI attributedURI = (AttributedURI)xmlObject;
      ElementSupport.appendTextContent(domElement, attributedURI.getValue());
   }

   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      AttributedURI attributedURI = (AttributedURI)xmlObject;
      XMLObjectSupport.marshallAttributeMap(attributedURI.getUnknownAttributes(), domElement);
   }
}
