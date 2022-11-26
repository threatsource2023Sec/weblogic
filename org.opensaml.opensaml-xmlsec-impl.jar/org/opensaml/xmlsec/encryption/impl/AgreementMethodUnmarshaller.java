package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xmlsec.encryption.AgreementMethod;
import org.opensaml.xmlsec.encryption.KANonce;
import org.opensaml.xmlsec.encryption.OriginatorKeyInfo;
import org.opensaml.xmlsec.encryption.RecipientKeyInfo;
import org.w3c.dom.Attr;

public class AgreementMethodUnmarshaller extends AbstractXMLEncryptionUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      AgreementMethod am = (AgreementMethod)xmlObject;
      if (attribute.getLocalName().equals("Algorithm")) {
         am.setAlgorithm(attribute.getValue());
      } else {
         super.processAttribute(xmlObject, attribute);
      }

   }

   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      AgreementMethod am = (AgreementMethod)parentXMLObject;
      if (childXMLObject instanceof KANonce) {
         am.setKANonce((KANonce)childXMLObject);
      } else if (childXMLObject instanceof OriginatorKeyInfo) {
         am.setOriginatorKeyInfo((OriginatorKeyInfo)childXMLObject);
      } else if (childXMLObject instanceof RecipientKeyInfo) {
         am.setRecipientKeyInfo((RecipientKeyInfo)childXMLObject);
      } else {
         am.getUnknownXMLObjects().add(childXMLObject);
      }

   }
}
