package org.opensaml.saml.saml1.core.impl;

import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.saml1.core.Audience;
import org.w3c.dom.Element;

public class AudienceMarshaller extends AbstractSAMLObjectMarshaller {
   protected void marshallElementContent(XMLObject samlObject, Element domElement) throws MarshallingException {
      Audience audience = (Audience)samlObject;
      ElementSupport.appendTextContent(domElement, audience.getUri());
   }
}
