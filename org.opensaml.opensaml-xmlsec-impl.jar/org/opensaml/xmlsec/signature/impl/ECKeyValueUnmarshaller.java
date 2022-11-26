package org.opensaml.xmlsec.signature.impl;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xmlsec.signature.ECKeyValue;
import org.opensaml.xmlsec.signature.NamedCurve;
import org.opensaml.xmlsec.signature.PublicKey;
import org.w3c.dom.Attr;

public class ECKeyValueUnmarshaller extends AbstractXMLSignatureUnmarshaller {
   public static final QName ECPARAMETERS_ELEMENT_NAME = new QName("http://www.w3.org/2009/xmldsig11#", "ECParameters");

   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      ECKeyValue ec = (ECKeyValue)xmlObject;
      if (attribute.getLocalName().equals("Id")) {
         ec.setID(attribute.getValue());
         attribute.getOwnerElement().setIdAttributeNode(attribute, true);
      } else {
         super.processAttribute(xmlObject, attribute);
      }

   }

   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      ECKeyValue keyValue = (ECKeyValue)parentXMLObject;
      if (childXMLObject instanceof NamedCurve) {
         keyValue.setNamedCurve((NamedCurve)childXMLObject);
      } else if (childXMLObject instanceof PublicKey) {
         keyValue.setPublicKey((PublicKey)childXMLObject);
      } else if (childXMLObject.getElementQName().equals(ECPARAMETERS_ELEMENT_NAME)) {
         keyValue.setECParameters(childXMLObject);
      } else {
         super.processChildElement(parentXMLObject, childXMLObject);
      }

   }
}
