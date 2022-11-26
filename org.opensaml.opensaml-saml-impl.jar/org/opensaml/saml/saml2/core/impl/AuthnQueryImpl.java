package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.saml.saml2.core.AuthnQuery;
import org.opensaml.saml.saml2.core.RequestedAuthnContext;

public class AuthnQueryImpl extends SubjectQueryImpl implements AuthnQuery {
   private String sessionIndex;
   private RequestedAuthnContext requestedAuthnContext;

   protected AuthnQueryImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getSessionIndex() {
      return this.sessionIndex;
   }

   public void setSessionIndex(String newSessionIndex) {
      this.sessionIndex = this.prepareForAssignment(this.sessionIndex, newSessionIndex);
   }

   public RequestedAuthnContext getRequestedAuthnContext() {
      return this.requestedAuthnContext;
   }

   public void setRequestedAuthnContext(RequestedAuthnContext newRequestedAuthnContext) {
      this.requestedAuthnContext = (RequestedAuthnContext)this.prepareForAssignment(this.requestedAuthnContext, newRequestedAuthnContext);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (super.getOrderedChildren() != null) {
         children.addAll(super.getOrderedChildren());
      }

      if (this.requestedAuthnContext != null) {
         children.add(this.requestedAuthnContext);
      }

      return children.size() == 0 ? null : Collections.unmodifiableList(children);
   }
}
