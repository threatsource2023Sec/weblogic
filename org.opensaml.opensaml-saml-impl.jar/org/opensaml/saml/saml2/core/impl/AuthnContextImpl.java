package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.AuthnContext;
import org.opensaml.saml.saml2.core.AuthnContextClassRef;
import org.opensaml.saml.saml2.core.AuthnContextDecl;
import org.opensaml.saml.saml2.core.AuthnContextDeclRef;

public class AuthnContextImpl extends AbstractSAMLObject implements AuthnContext {
   private AuthnContextClassRef authnContextClassRef;
   private AuthnContextDecl authnContextDecl;
   private AuthnContextDeclRef authnContextDeclRef;
   private final XMLObjectChildrenList authenticatingAuthority = new XMLObjectChildrenList(this);

   protected AuthnContextImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public AuthnContextClassRef getAuthnContextClassRef() {
      return this.authnContextClassRef;
   }

   public void setAuthnContextClassRef(AuthnContextClassRef newAuthnContextClassRef) {
      this.authnContextClassRef = (AuthnContextClassRef)this.prepareForAssignment(this.authnContextClassRef, newAuthnContextClassRef);
   }

   public AuthnContextDecl getAuthContextDecl() {
      return this.authnContextDecl;
   }

   public void setAuthnContextDecl(AuthnContextDecl newAuthnContextDecl) {
      this.authnContextDecl = (AuthnContextDecl)this.prepareForAssignment(this.authnContextDecl, newAuthnContextDecl);
   }

   public AuthnContextDeclRef getAuthnContextDeclRef() {
      return this.authnContextDeclRef;
   }

   public void setAuthnContextDeclRef(AuthnContextDeclRef newAuthnContextDeclRef) {
      this.authnContextDeclRef = (AuthnContextDeclRef)this.prepareForAssignment(this.authnContextDeclRef, newAuthnContextDeclRef);
   }

   public List getAuthenticatingAuthorities() {
      return this.authenticatingAuthority;
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.add(this.authnContextClassRef);
      children.add(this.authnContextDecl);
      children.add(this.authnContextDeclRef);
      children.addAll(this.authenticatingAuthority);
      return Collections.unmodifiableList(children);
   }
}
