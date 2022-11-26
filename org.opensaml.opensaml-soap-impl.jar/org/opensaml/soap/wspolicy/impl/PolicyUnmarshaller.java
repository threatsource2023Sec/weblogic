package org.opensaml.soap.wspolicy.impl;

import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.xml.QNameSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wspolicy.Policy;
import org.w3c.dom.Attr;

public class PolicyUnmarshaller extends OperatorContentTypeUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      Policy policy = (Policy)xmlObject;
      QName nameQName = new QName("Name");
      QName attribQName = QNameSupport.constructQName(attribute.getNamespaceURI(), attribute.getLocalName(), attribute.getPrefix());
      if (nameQName.equals(attribQName)) {
         policy.setName(attribute.getValue());
      } else if (Policy.WSU_ID_ATTR_NAME.equals(attribQName)) {
         policy.setWSUId(attribute.getValue());
         attribute.getOwnerElement().setIdAttributeNode(attribute, true);
      } else {
         XMLObjectSupport.unmarshallToAttributeMap(policy.getUnknownAttributes(), attribute);
      }

   }
}
