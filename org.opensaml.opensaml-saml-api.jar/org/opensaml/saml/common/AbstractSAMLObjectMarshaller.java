package org.opensaml.saml.common;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.AbstractXMLObjectMarshaller;
import org.opensaml.core.xml.io.MarshallingException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class AbstractSAMLObjectMarshaller extends AbstractXMLObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
   }

   protected void marshallElementContent(XMLObject xmlObject, Element domElement) throws MarshallingException {
   }

   public Element marshall(XMLObject xmlObject, Document document) throws MarshallingException {
      if (xmlObject instanceof SignableSAMLObject) {
         SAMLObjectSupport.declareNonVisibleNamespaces((SignableSAMLObject)xmlObject);
      }

      return super.marshall(xmlObject, document);
   }

   public Element marshall(XMLObject xmlObject, Element parentElement) throws MarshallingException {
      if (xmlObject instanceof SignableSAMLObject) {
         SAMLObjectSupport.declareNonVisibleNamespaces((SignableSAMLObject)xmlObject);
      }

      return super.marshall(xmlObject, parentElement);
   }
}
