package org.opensaml.saml.saml1.core.impl;

import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.saml1.core.AssertionIDReference;
import org.w3c.dom.Element;

public class AssertionIDReferenceMarshaller extends AbstractSAMLObjectMarshaller {
   protected void marshallElementContent(XMLObject samlObject, Element domElement) throws MarshallingException {
      AssertionIDReference assertionIDReference = (AssertionIDReference)samlObject;
      if (assertionIDReference.getReference() != null) {
         ElementSupport.appendTextContent(domElement, assertionIDReference.getReference());
      }

   }
}
