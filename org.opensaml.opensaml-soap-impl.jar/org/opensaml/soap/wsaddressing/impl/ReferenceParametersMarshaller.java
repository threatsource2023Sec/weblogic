package org.opensaml.soap.wsaddressing.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wsaddressing.ReferenceParameters;
import org.w3c.dom.Element;

public class ReferenceParametersMarshaller extends AbstractWSAddressingObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      ReferenceParameters rp = (ReferenceParameters)xmlObject;
      XMLObjectSupport.marshallAttributeMap(rp.getUnknownAttributes(), domElement);
   }
}
