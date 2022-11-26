package org.opensaml.saml.saml2.metadata.impl;

import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.metadata.Endpoint;

public abstract class EndpointImpl extends AbstractSAMLObject implements Endpoint {
   private String bindingId;
   private String location;
   private String responseLocation;
   private final AttributeMap unknownAttributes = new AttributeMap(this);
   private final IndexedXMLObjectChildrenList unknownChildren = new IndexedXMLObjectChildrenList(this);

   protected EndpointImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getBinding() {
      return this.bindingId;
   }

   public void setBinding(String binding) {
      this.bindingId = this.prepareForAssignment(this.bindingId, binding);
   }

   public String getLocation() {
      return this.location;
   }

   public void setLocation(String theLocation) {
      this.location = this.prepareForAssignment(this.location, theLocation);
   }

   public String getResponseLocation() {
      return this.responseLocation;
   }

   public void setResponseLocation(String theLocation) {
      this.responseLocation = this.prepareForAssignment(this.responseLocation, theLocation);
   }

   public AttributeMap getUnknownAttributes() {
      return this.unknownAttributes;
   }

   public List getUnknownXMLObjects() {
      return this.unknownChildren;
   }

   public List getUnknownXMLObjects(QName typeOrName) {
      return this.unknownChildren.subList(typeOrName);
   }

   public List getOrderedChildren() {
      return Collections.unmodifiableList(this.unknownChildren);
   }
}
