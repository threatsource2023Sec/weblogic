package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xmlsec.encryption.AlgorithmIdentifierType;
import org.w3c.dom.Element;

public class AlgorithmIdentifierTypeMarshaller extends AbstractXMLEncryptionMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      AlgorithmIdentifierType algoIdType = (AlgorithmIdentifierType)xmlObject;
      if (algoIdType.getAlgorithm() != null) {
         domElement.setAttributeNS((String)null, "Algorithm", algoIdType.getAlgorithm());
      }

   }
}
