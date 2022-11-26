package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml2.core.NameIDPolicy;
import org.w3c.dom.Attr;

public class NameIDPolicyUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      NameIDPolicy policy = (NameIDPolicy)samlObject;
      if (attribute.getNamespaceURI() == null) {
         if (attribute.getLocalName().equals("Format")) {
            policy.setFormat(attribute.getValue());
         }

         if (attribute.getLocalName().equals("SPNameQualifier")) {
            policy.setSPNameQualifier(attribute.getValue());
         }

         if (attribute.getLocalName().equals("AllowCreate")) {
            policy.setAllowCreate(XSBooleanValue.valueOf(attribute.getValue()));
         } else {
            super.processAttribute(samlObject, attribute);
         }
      } else {
         super.processAttribute(samlObject, attribute);
      }

   }
}
