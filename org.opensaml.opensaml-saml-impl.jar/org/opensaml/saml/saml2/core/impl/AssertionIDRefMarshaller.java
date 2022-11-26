package org.opensaml.saml.saml2.core.impl;

import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.saml2.core.AssertionIDRef;
import org.w3c.dom.Element;

public class AssertionIDRefMarshaller extends AbstractSAMLObjectMarshaller {
   protected void marshallElementContent(XMLObject samlObject, Element domElement) throws MarshallingException {
      AssertionIDRef assertionIDRef = (AssertionIDRef)samlObject;
      ElementSupport.appendTextContent(domElement, assertionIDRef.getAssertionID());
   }
}
