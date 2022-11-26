package org.opensaml.saml.ext.saml2aslo.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2aslo.Asynchronous;

public class AsynchronousBuilder extends AbstractSAMLObjectBuilder {
   public Asynchronous buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol:ext:async-slo", "Asynchronous", "aslo");
   }

   public Asynchronous buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AsynchronousImpl(namespaceURI, localName, namespacePrefix);
   }
}
