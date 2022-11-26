package org.opensaml.soap.wssecurity.impl;

import com.google.common.base.Strings;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.xml.QNameSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wssecurity.AttributedDateTime;
import org.w3c.dom.Attr;

public class AttributedDateTimeUnmarshaller extends AbstractWSSecurityObjectUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      AttributedDateTime dateTime = (AttributedDateTime)xmlObject;
      QName attrName = QNameSupport.constructQName(attribute.getNamespaceURI(), attribute.getLocalName(), attribute.getPrefix());
      if (AttributedDateTime.WSU_ID_ATTR_NAME.equals(attrName)) {
         dateTime.setWSUId(attribute.getValue());
         attribute.getOwnerElement().setIdAttributeNode(attribute, true);
      } else {
         XMLObjectSupport.unmarshallToAttributeMap(dateTime.getUnknownAttributes(), attribute);
      }

   }

   protected void processElementContent(XMLObject xmlObject, String elementContent) {
      AttributedDateTime dateTime = (AttributedDateTime)xmlObject;
      if (!Strings.isNullOrEmpty(elementContent)) {
         dateTime.setValue(elementContent);
      }

   }
}
