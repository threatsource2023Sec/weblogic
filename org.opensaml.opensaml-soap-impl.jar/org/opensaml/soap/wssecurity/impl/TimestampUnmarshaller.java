package org.opensaml.soap.wssecurity.impl;

import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.xml.QNameSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wssecurity.Created;
import org.opensaml.soap.wssecurity.Expires;
import org.opensaml.soap.wssecurity.Timestamp;
import org.w3c.dom.Attr;

public class TimestampUnmarshaller extends AbstractWSSecurityObjectUnmarshaller {
   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      Timestamp timestamp = (Timestamp)parentXMLObject;
      if (childXMLObject instanceof Created) {
         timestamp.setCreated((Created)childXMLObject);
      } else if (childXMLObject instanceof Expires) {
         timestamp.setExpires((Expires)childXMLObject);
      } else {
         timestamp.getUnknownXMLObjects().add(childXMLObject);
      }

   }

   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      Timestamp timestamp = (Timestamp)xmlObject;
      QName attrName = QNameSupport.constructQName(attribute.getNamespaceURI(), attribute.getLocalName(), attribute.getPrefix());
      if (Timestamp.WSU_ID_ATTR_NAME.equals(attrName)) {
         timestamp.setWSUId(attribute.getValue());
         attribute.getOwnerElement().setIdAttributeNode(attribute, true);
      } else {
         XMLObjectSupport.unmarshallToAttributeMap(timestamp.getUnknownAttributes(), attribute);
      }

   }
}
