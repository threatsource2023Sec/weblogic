package org.opensaml.soap.wssecurity.impl;

import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.xml.QNameSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wssecurity.AttributedURI;
import org.w3c.dom.Attr;

public class AttributedURIUnmarshaller extends AbstractWSSecurityObjectUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      AttributedURI attributedURI = (AttributedURI)xmlObject;
      QName attribQName = QNameSupport.constructQName(attribute.getNamespaceURI(), attribute.getLocalName(), attribute.getPrefix());
      if (AttributedURI.WSU_ID_ATTR_NAME.equals(attribQName)) {
         attributedURI.setWSUId(attribute.getValue());
         attribute.getOwnerElement().setIdAttributeNode(attribute, true);
      } else {
         XMLObjectSupport.unmarshallToAttributeMap(attributedURI.getUnknownAttributes(), attribute);
      }

   }

   protected void processElementContent(XMLObject xmlObject, String elementContent) {
      AttributedURI attributedURI = (AttributedURI)xmlObject;
      attributedURI.setValue(elementContent);
   }
}
