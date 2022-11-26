package org.opensaml.soap.wstrust.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.soap.wstrust.RequestSecurityTokenCollection;

public class RequestSecurityTokenCollectionImpl extends AbstractWSTrustObject implements RequestSecurityTokenCollection {
   private List requestSecurityTokens = new ArrayList();

   public RequestSecurityTokenCollectionImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getRequestSecurityTokens() {
      return this.requestSecurityTokens;
   }

   public List getOrderedChildren() {
      List children = new ArrayList();
      children.addAll(this.requestSecurityTokens);
      return Collections.unmodifiableList(children);
   }
}
