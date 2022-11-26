package org.opensaml.soap.wstrust.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.soap.wssecurity.SecurityTokenReference;
import org.opensaml.soap.wstrust.RequestedReferenceType;

public class RequestedReferenceTypeImpl extends AbstractWSTrustObject implements RequestedReferenceType {
   private SecurityTokenReference securityTokenReference;

   public RequestedReferenceTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public SecurityTokenReference getSecurityTokenReference() {
      return this.securityTokenReference;
   }

   public void setSecurityTokenReference(SecurityTokenReference newSecurityTokenReference) {
      this.securityTokenReference = (SecurityTokenReference)this.prepareForAssignment(this.securityTokenReference, newSecurityTokenReference);
   }

   public List getOrderedChildren() {
      List children = new ArrayList();
      if (this.securityTokenReference != null) {
         children.add(this.securityTokenReference);
      }

      return Collections.unmodifiableList(children);
   }
}
