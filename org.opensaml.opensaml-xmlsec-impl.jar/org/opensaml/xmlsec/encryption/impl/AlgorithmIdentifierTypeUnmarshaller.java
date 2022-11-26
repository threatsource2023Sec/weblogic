package org.opensaml.xmlsec.encryption.impl;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xmlsec.encryption.AlgorithmIdentifierType;
import org.w3c.dom.Attr;

public class AlgorithmIdentifierTypeUnmarshaller extends AbstractXMLEncryptionUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      AlgorithmIdentifierType algoIdType = (AlgorithmIdentifierType)xmlObject;
      if (attribute.getLocalName().equals("Algorithm")) {
         algoIdType.setAlgorithm(attribute.getValue());
      } else {
         super.processAttribute(xmlObject, attribute);
      }

   }

   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      AlgorithmIdentifierType algoIdType = (AlgorithmIdentifierType)parentXMLObject;
      QName childQName = childXMLObject.getElementQName();
      if (childQName.getLocalPart().equals("Parameters") && childQName.getNamespaceURI().equals("http://www.w3.org/2009/xmlenc11#")) {
         algoIdType.setParameters(childXMLObject);
      } else {
         super.processChildElement(parentXMLObject, childXMLObject);
      }

   }
}
