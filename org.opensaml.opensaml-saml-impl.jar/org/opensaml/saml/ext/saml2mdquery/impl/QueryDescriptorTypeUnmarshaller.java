package org.opensaml.saml.ext.saml2mdquery.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.saml.ext.saml2mdquery.QueryDescriptorType;
import org.opensaml.saml.saml2.metadata.NameIDFormat;
import org.opensaml.saml.saml2.metadata.impl.RoleDescriptorUnmarshaller;
import org.w3c.dom.Attr;

public class QueryDescriptorTypeUnmarshaller extends RoleDescriptorUnmarshaller {
   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
      QueryDescriptorType descriptor = (QueryDescriptorType)parentSAMLObject;
      if (childSAMLObject instanceof NameIDFormat) {
         descriptor.getNameIDFormat().add((NameIDFormat)childSAMLObject);
      } else {
         super.processChildElement(parentSAMLObject, childSAMLObject);
      }

   }

   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      QueryDescriptorType descriptor = (QueryDescriptorType)samlObject;
      if (attribute.getLocalName().equals("WantAssertionsSigned") && attribute.getNamespaceURI() == null) {
         descriptor.setWantAssertionsSigned(XSBooleanValue.valueOf(attribute.getValue()));
      } else {
         super.processAttribute(samlObject, attribute);
      }

   }
}
