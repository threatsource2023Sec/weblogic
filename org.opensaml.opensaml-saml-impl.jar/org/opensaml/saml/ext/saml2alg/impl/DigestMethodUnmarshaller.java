package org.opensaml.saml.ext.saml2alg.impl;

import javax.annotation.Nonnull;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.ext.saml2alg.DigestMethod;
import org.w3c.dom.Attr;

public class DigestMethodUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processChildElement(@Nonnull XMLObject parentXMLObject, @Nonnull XMLObject childXMLObject) throws UnmarshallingException {
      DigestMethod digestMethod = (DigestMethod)parentXMLObject;
      digestMethod.getUnknownXMLObjects().add(childXMLObject);
   }

   protected void processAttribute(@Nonnull XMLObject xmlObject, @Nonnull Attr attribute) throws UnmarshallingException {
      DigestMethod digestMethod = (DigestMethod)xmlObject;
      if (attribute.getLocalName().equals("Algorithm") && attribute.getNamespaceURI() == null) {
         digestMethod.setAlgorithm(attribute.getValue());
      } else {
         super.processAttribute(xmlObject, attribute);
      }

   }
}
