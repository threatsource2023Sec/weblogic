package org.opensaml.saml.saml1.core.impl;

import net.shibboleth.utilities.java.support.xml.AttributeSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml1.core.AuthorityBinding;
import org.w3c.dom.Attr;

public class AuthorityBindingUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      AuthorityBinding authorityBinding = (AuthorityBinding)samlObject;
      if (attribute.getNamespaceURI() == null) {
         if ("AuthorityKind".equals(attribute.getLocalName())) {
            authorityBinding.setAuthorityKind(AttributeSupport.getAttributeValueAsQName(attribute));
         } else if ("Location".equals(attribute.getLocalName())) {
            authorityBinding.setLocation(attribute.getValue());
         } else if ("Binding".equals(attribute.getLocalName())) {
            authorityBinding.setBinding(attribute.getValue());
         } else {
            super.processAttribute(samlObject, attribute);
         }
      } else {
         super.processAttribute(samlObject, attribute);
      }

   }
}
