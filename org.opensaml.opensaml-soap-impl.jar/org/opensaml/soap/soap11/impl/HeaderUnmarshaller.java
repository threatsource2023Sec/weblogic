package org.opensaml.soap.soap11.impl;

import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.xml.QNameSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.AbstractXMLObjectUnmarshaller;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.soap.soap11.Header;
import org.w3c.dom.Attr;

public class HeaderUnmarshaller extends AbstractXMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      Header header = (Header)parentXMLObject;
      header.getUnknownXMLObjects().add(childXMLObject);
   }

   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      Header header = (Header)xmlObject;
      QName attribQName = QNameSupport.constructQName(attribute.getNamespaceURI(), attribute.getLocalName(), attribute.getPrefix());
      if (attribute.isId()) {
         header.getUnknownAttributes().registerID(attribQName);
      }

      header.getUnknownAttributes().put(attribQName, attribute.getValue());
   }

   protected void processElementContent(XMLObject xmlObject, String elementContent) {
   }
}
