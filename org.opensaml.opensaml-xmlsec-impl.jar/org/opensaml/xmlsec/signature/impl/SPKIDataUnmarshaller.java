package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xmlsec.signature.SPKIData;

public class SPKIDataUnmarshaller extends AbstractXMLSignatureUnmarshaller {
   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      SPKIData spkiData = (SPKIData)parentXMLObject;
      spkiData.getXMLObjects().add(childXMLObject);
   }
}
