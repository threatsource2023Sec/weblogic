package org.opensaml.soap.wssecurity.impl;

import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.xml.AttributeSupport;
import net.shibboleth.utilities.java.support.xml.QNameSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wssecurity.SecurityTokenReference;
import org.w3c.dom.Attr;

public class SecurityTokenReferenceUnmarshaller extends AbstractWSSecurityObjectUnmarshaller {
   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      SecurityTokenReference str = (SecurityTokenReference)parentXMLObject;
      str.getUnknownXMLObjects().add(childXMLObject);
   }

   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      SecurityTokenReference str = (SecurityTokenReference)xmlObject;
      QName attribQName = QNameSupport.constructQName(attribute.getNamespaceURI(), attribute.getLocalName(), attribute.getPrefix());
      if (SecurityTokenReference.WSU_ID_ATTR_NAME.equals(attribQName)) {
         str.setWSUId(attribute.getValue());
         attribute.getOwnerElement().setIdAttributeNode(attribute, true);
      } else if (SecurityTokenReference.WSSE_USAGE_ATTR_NAME.equals(attribQName)) {
         str.setWSSEUsages(AttributeSupport.getAttributeValueAsList(attribute));
      } else {
         XMLObjectSupport.unmarshallToAttributeMap(str.getUnknownAttributes(), attribute);
      }

   }
}
