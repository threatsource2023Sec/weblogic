package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xmlsec.signature.DSAKeyValue;
import org.opensaml.xmlsec.signature.ECKeyValue;
import org.opensaml.xmlsec.signature.KeyValue;
import org.opensaml.xmlsec.signature.RSAKeyValue;

public class KeyValueUnmarshaller extends AbstractXMLSignatureUnmarshaller {
   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      KeyValue keyValue = (KeyValue)parentXMLObject;
      if (childXMLObject instanceof DSAKeyValue) {
         keyValue.setDSAKeyValue((DSAKeyValue)childXMLObject);
      } else if (childXMLObject instanceof RSAKeyValue) {
         keyValue.setRSAKeyValue((RSAKeyValue)childXMLObject);
      } else if (childXMLObject instanceof ECKeyValue) {
         keyValue.setECKeyValue((ECKeyValue)childXMLObject);
      } else if (keyValue.getUnknownXMLObject() == null) {
         keyValue.setUnknownXMLObject(childXMLObject);
      } else {
         super.processChildElement(parentXMLObject, childXMLObject);
      }

   }
}
