package org.opensaml.soap.wssecurity.impl;

import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.xml.QNameSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wssecurity.Username;
import org.opensaml.soap.wssecurity.UsernameToken;
import org.w3c.dom.Attr;

public class UsernameTokenUnmarshaller extends AbstractWSSecurityObjectUnmarshaller {
   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      UsernameToken token = (UsernameToken)parentXMLObject;
      if (childXMLObject instanceof Username) {
         token.setUsername((Username)childXMLObject);
      } else {
         token.getUnknownXMLObjects().add(childXMLObject);
      }

   }

   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      UsernameToken token = (UsernameToken)xmlObject;
      QName attribQName = QNameSupport.constructQName(attribute.getNamespaceURI(), attribute.getLocalName(), attribute.getPrefix());
      if (UsernameToken.WSU_ID_ATTR_NAME.equals(attribQName)) {
         token.setWSUId(attribute.getValue());
         attribute.getOwnerElement().setIdAttributeNode(attribute, true);
      } else {
         XMLObjectSupport.unmarshallToAttributeMap(token.getUnknownAttributes(), attribute);
      }

   }
}
