package org.opensaml.soap.wssecurity.impl;

import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.xml.QNameSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wssecurity.AttributedString;
import org.w3c.dom.Attr;

public class AttributedStringUnmarshaller extends AbstractWSSecurityObjectUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      AttributedString attributedString = (AttributedString)xmlObject;
      QName attribQName = QNameSupport.constructQName(attribute.getNamespaceURI(), attribute.getLocalName(), attribute.getPrefix());
      if (AttributedString.WSU_ID_ATTR_NAME.equals(attribQName)) {
         attributedString.setWSUId(attribute.getValue());
         attribute.getOwnerElement().setIdAttributeNode(attribute, true);
      } else {
         XMLObjectSupport.unmarshallToAttributeMap(attributedString.getUnknownAttributes(), attribute);
      }

   }

   protected void processElementContent(XMLObject xmlObject, String elementContent) {
      AttributedString attributedString = (AttributedString)xmlObject;
      attributedString.setValue(elementContent);
   }
}
