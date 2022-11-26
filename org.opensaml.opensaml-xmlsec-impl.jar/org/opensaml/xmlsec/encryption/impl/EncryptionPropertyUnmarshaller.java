package org.opensaml.xmlsec.encryption.impl;

import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.xml.QNameSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xmlsec.encryption.EncryptionProperty;
import org.w3c.dom.Attr;

public class EncryptionPropertyUnmarshaller extends AbstractXMLEncryptionUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      EncryptionProperty ep = (EncryptionProperty)xmlObject;
      if (attribute.getLocalName().equals("Id")) {
         ep.setID(attribute.getValue());
         attribute.getOwnerElement().setIdAttributeNode(attribute, true);
      } else if (attribute.getLocalName().equals("Target")) {
         ep.setTarget(attribute.getValue());
      } else {
         QName attributeName = QNameSupport.getNodeQName(attribute);
         if (attribute.isId()) {
            ep.getUnknownAttributes().registerID(attributeName);
         }

         ep.getUnknownAttributes().put(attributeName, attribute.getValue());
      }

   }

   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      EncryptionProperty ep = (EncryptionProperty)parentXMLObject;
      ep.getUnknownXMLObjects().add(childXMLObject);
   }
}
