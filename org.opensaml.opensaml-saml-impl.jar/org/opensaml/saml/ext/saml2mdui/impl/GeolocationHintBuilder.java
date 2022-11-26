package org.opensaml.saml.ext.saml2mdui.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2mdui.GeolocationHint;

public class GeolocationHintBuilder extends AbstractSAMLObjectBuilder {
   public GeolocationHint buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:metadata:ui", "GeolocationHint", "mdui");
   }

   public GeolocationHint buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new GeolocationHintImpl(namespaceURI, localName, namespacePrefix);
   }
}
