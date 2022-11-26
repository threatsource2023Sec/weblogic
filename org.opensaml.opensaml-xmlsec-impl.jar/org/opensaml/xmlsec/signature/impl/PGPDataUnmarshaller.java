package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xmlsec.signature.PGPData;
import org.opensaml.xmlsec.signature.PGPKeyID;
import org.opensaml.xmlsec.signature.PGPKeyPacket;

public class PGPDataUnmarshaller extends AbstractXMLSignatureUnmarshaller {
   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      PGPData pgpData = (PGPData)parentXMLObject;
      if (childXMLObject instanceof PGPKeyID) {
         pgpData.setPGPKeyID((PGPKeyID)childXMLObject);
      } else if (childXMLObject instanceof PGPKeyPacket) {
         pgpData.setPGPKeyPacket((PGPKeyPacket)childXMLObject);
      } else {
         pgpData.getUnknownXMLObjects().add(childXMLObject);
      }

   }
}
