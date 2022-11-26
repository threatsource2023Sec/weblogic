package org.opensaml.saml.ext.saml2alg.impl;

import javax.annotation.Nonnull;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.ext.saml2alg.SigningMethod;
import org.w3c.dom.Attr;

public class SigningMethodUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processChildElement(@Nonnull XMLObject parentXMLObject, @Nonnull XMLObject childXMLObject) throws UnmarshallingException {
      SigningMethod signingMethod = (SigningMethod)parentXMLObject;
      signingMethod.getUnknownXMLObjects().add(childXMLObject);
   }

   protected void processAttribute(@Nonnull XMLObject xmlObject, @Nonnull Attr attribute) throws UnmarshallingException {
      SigningMethod signingMethod = (SigningMethod)xmlObject;
      if (attribute.getNamespaceURI() == null) {
         if (attribute.getLocalName().equals("Algorithm")) {
            signingMethod.setAlgorithm(attribute.getValue());
         } else if (attribute.getLocalName().equals("MinKeySize")) {
            signingMethod.setMinKeySize(Integer.valueOf(attribute.getValue()));
         } else if (attribute.getLocalName().equals("MaxKeySize")) {
            signingMethod.setMaxKeySize(Integer.valueOf(attribute.getValue()));
         } else {
            super.processAttribute(xmlObject, attribute);
         }
      } else {
         super.processAttribute(xmlObject, attribute);
      }

   }
}
