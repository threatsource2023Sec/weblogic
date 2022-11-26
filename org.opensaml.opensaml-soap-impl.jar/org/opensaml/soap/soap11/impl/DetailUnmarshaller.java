package org.opensaml.soap.soap11.impl;

import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.xml.QNameSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.AbstractXMLObjectUnmarshaller;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.soap.soap11.Detail;
import org.w3c.dom.Attr;

public class DetailUnmarshaller extends AbstractXMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      Detail detail = (Detail)parentXMLObject;
      detail.getUnknownXMLObjects().add(childXMLObject);
   }

   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      Detail detail = (Detail)xmlObject;
      QName attribQName = QNameSupport.constructQName(attribute.getNamespaceURI(), attribute.getLocalName(), attribute.getPrefix());
      if (attribute.isId()) {
         detail.getUnknownAttributes().registerID(attribQName);
      }

      detail.getUnknownAttributes().put(attribQName, attribute.getValue());
   }

   protected void processElementContent(XMLObject xmlObject, String elementContent) {
   }
}
