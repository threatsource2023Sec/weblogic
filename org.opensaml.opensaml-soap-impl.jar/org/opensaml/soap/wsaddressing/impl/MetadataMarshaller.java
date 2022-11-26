package org.opensaml.soap.wsaddressing.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wsaddressing.Metadata;
import org.w3c.dom.Element;

public class MetadataMarshaller extends AbstractWSAddressingObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      Metadata metadata = (Metadata)xmlObject;
      XMLObjectSupport.marshallAttributeMap(metadata.getUnknownAttributes(), domElement);
   }
}
