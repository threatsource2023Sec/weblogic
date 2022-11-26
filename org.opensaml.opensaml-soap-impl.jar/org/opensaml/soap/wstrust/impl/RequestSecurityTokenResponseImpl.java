package org.opensaml.soap.wstrust.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.soap.wstrust.RequestSecurityTokenResponse;

public class RequestSecurityTokenResponseImpl extends AbstractWSTrustObject implements RequestSecurityTokenResponse {
   private String context;
   private IndexedXMLObjectChildrenList unknownChildren = new IndexedXMLObjectChildrenList(this);
   private AttributeMap unknownAttributes = new AttributeMap(this);

   public RequestSecurityTokenResponseImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getContext() {
      return this.context;
   }

   public void setContext(String newContext) {
      this.context = this.prepareForAssignment(this.context, newContext);
   }

   public List getUnknownXMLObjects() {
      return this.unknownChildren;
   }

   public List getUnknownXMLObjects(QName typeOrName) {
      return this.unknownChildren.subList(typeOrName);
   }

   public AttributeMap getUnknownAttributes() {
      return this.unknownAttributes;
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(this.unknownChildren);
      return Collections.unmodifiableList(children);
   }
}
