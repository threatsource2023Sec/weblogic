package org.opensaml.soap.wsaddressing.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wsaddressing.ProblemAction;
import org.w3c.dom.Element;

public class ProblemActionMarshaller extends AbstractWSAddressingObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      ProblemAction pa = (ProblemAction)xmlObject;
      XMLObjectSupport.marshallAttributeMap(pa.getUnknownAttributes(), domElement);
   }
}
