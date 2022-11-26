package org.opensaml.soap.wstrust.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.soap.wstrust.RequestSecurityTokenResponseCollection;

public class RequestSecurityTokenResponseCollectionImpl extends AbstractWSTrustObject implements RequestSecurityTokenResponseCollection {
   private AttributeMap unknownAttributes = new AttributeMap(this);
   private List requestSecurityTokenResponses = new ArrayList();

   public RequestSecurityTokenResponseCollectionImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public AttributeMap getUnknownAttributes() {
      return this.unknownAttributes;
   }

   public List getRequestSecurityTokenResponses() {
      return this.requestSecurityTokenResponses;
   }

   public List getOrderedChildren() {
      List children = new ArrayList();
      children.addAll(this.requestSecurityTokenResponses);
      return Collections.unmodifiableList(children);
   }
}
