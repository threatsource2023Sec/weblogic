package org.opensaml.soap.soap11.impl;

import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.xml.QNameSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.AbstractXMLObjectUnmarshaller;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.soap.soap11.Body;
import org.w3c.dom.Attr;

public class BodyUnmarshaller extends AbstractXMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      Body body = (Body)parentXMLObject;
      body.getUnknownXMLObjects().add(childXMLObject);
   }

   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      Body body = (Body)xmlObject;
      QName attribQName = QNameSupport.constructQName(attribute.getNamespaceURI(), attribute.getLocalName(), attribute.getPrefix());
      if (attribute.isId()) {
         body.getUnknownAttributes().registerID(attribQName);
      }

      body.getUnknownAttributes().put(attribQName, attribute.getValue());
   }

   protected void processElementContent(XMLObject xmlObject, String elementContent) {
   }
}
