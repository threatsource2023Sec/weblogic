package org.opensaml.saml.saml2.metadata.impl;

import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.saml2.metadata.GivenName;
import org.w3c.dom.Element;

public class GivenNameMarshaller extends AbstractSAMLObjectMarshaller {
   protected void marshallElementContent(XMLObject samlObject, Element domElement) throws MarshallingException {
      GivenName name = (GivenName)samlObject;
      if (name.getName() != null) {
         ElementSupport.appendTextContent(domElement, name.getName());
      }

   }
}
