package org.opensaml.saml.saml2.core.impl;

import java.util.List;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.AuthnContextDeclRef;

public class AuthnContextDeclRefImpl extends AbstractSAMLObject implements AuthnContextDeclRef {
   private String authnContextDeclRef;

   protected AuthnContextDeclRefImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getAuthnContextDeclRef() {
      return this.authnContextDeclRef;
   }

   public void setAuthnContextDeclRef(String newAuthnContextDeclRef) {
      this.authnContextDeclRef = this.prepareForAssignment(this.authnContextDeclRef, newAuthnContextDeclRef);
   }

   public List getOrderedChildren() {
      return null;
   }
}
