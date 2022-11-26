package org.opensaml.soap.wssecurity.impl;

import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.xml.QNameSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.soap.wssecurity.SignatureConfirmation;
import org.w3c.dom.Attr;

public class SignatureConfirmationUnmarshaller extends AbstractWSSecurityObjectUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      SignatureConfirmation sc = (SignatureConfirmation)xmlObject;
      QName attrName = QNameSupport.constructQName(attribute.getNamespaceURI(), attribute.getLocalName(), attribute.getPrefix());
      if (SignatureConfirmation.WSU_ID_ATTR_NAME.equals(attrName)) {
         sc.setWSUId(attribute.getValue());
         attribute.getOwnerElement().setIdAttributeNode(attribute, true);
      } else if ("Value".equals(attribute.getLocalName())) {
         sc.setValue(attribute.getValue());
      } else {
         super.processAttribute(xmlObject, attribute);
      }

   }
}
