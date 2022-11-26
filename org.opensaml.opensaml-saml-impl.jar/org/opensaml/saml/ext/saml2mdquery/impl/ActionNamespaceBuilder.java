package org.opensaml.saml.ext.saml2mdquery.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2mdquery.ActionNamespace;

public class ActionNamespaceBuilder extends AbstractSAMLObjectBuilder {
   public ActionNamespace buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:metadata:ext:query", "ActionNamespace", "query");
   }

   public ActionNamespace buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ActionNamespaceImpl(namespaceURI, localName, namespacePrefix);
   }
}
