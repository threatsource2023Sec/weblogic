package org.opensaml.saml.ext.saml2mdui.impl;

import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.ext.saml2mdui.GeolocationHint;
import org.w3c.dom.Element;

public class GeolocationHintMarshaller extends AbstractSAMLObjectMarshaller {
   protected void marshallElementContent(XMLObject samlObject, Element domElement) throws MarshallingException {
      GeolocationHint name = (GeolocationHint)samlObject;
      if (name.getHint() != null) {
         ElementSupport.appendTextContent(domElement, name.getHint());
      }

   }
}
