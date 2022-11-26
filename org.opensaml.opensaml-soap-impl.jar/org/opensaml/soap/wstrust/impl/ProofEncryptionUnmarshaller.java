package org.opensaml.soap.wstrust.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.soap.wstrust.ProofEncryption;

public class ProofEncryptionUnmarshaller extends AbstractWSTrustObjectUnmarshaller {
   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      ProofEncryption pe = (ProofEncryption)parentXMLObject;
      pe.setUnknownXMLObject(childXMLObject);
   }
}
