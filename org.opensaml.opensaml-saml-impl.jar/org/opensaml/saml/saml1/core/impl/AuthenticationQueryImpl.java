package org.opensaml.saml.saml1.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.saml.saml1.core.AuthenticationQuery;

public class AuthenticationQueryImpl extends SubjectQueryImpl implements AuthenticationQuery {
   private String authenticationMethod;

   protected AuthenticationQueryImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getAuthenticationMethod() {
      return this.authenticationMethod;
   }

   public void setAuthenticationMethod(String method) {
      this.authenticationMethod = this.prepareForAssignment(this.authenticationMethod, method);
   }

   public List getOrderedChildren() {
      List list = new ArrayList();
      if (super.getOrderedChildren() != null) {
         list.addAll(super.getOrderedChildren());
      }

      return list.size() == 0 ? null : Collections.unmodifiableList(list);
   }
}
