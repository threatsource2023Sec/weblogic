package org.opensaml.soap.soap11.impl;

import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.xml.QNameSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.AbstractXMLObjectUnmarshaller;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.soap.soap11.Body;
import org.opensaml.soap.soap11.Envelope;
import org.opensaml.soap.soap11.Header;
import org.w3c.dom.Attr;

public class EnvelopeUnmarshaller extends AbstractXMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      Envelope envelope = (Envelope)parentXMLObject;
      if (childXMLObject instanceof Header) {
         envelope.setHeader((Header)childXMLObject);
      } else if (childXMLObject instanceof Body) {
         envelope.setBody((Body)childXMLObject);
      } else {
         envelope.getUnknownXMLObjects().add(childXMLObject);
      }

   }

   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      Envelope envelope = (Envelope)xmlObject;
      QName attribQName = QNameSupport.constructQName(attribute.getNamespaceURI(), attribute.getLocalName(), attribute.getPrefix());
      if (attribute.isId()) {
         envelope.getUnknownAttributes().registerID(attribQName);
      }

      envelope.getUnknownAttributes().put(attribQName, attribute.getValue());
   }

   protected void processElementContent(XMLObject xmlObject, String elementContent) {
   }
}
