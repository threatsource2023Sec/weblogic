package org.opensaml.soap.wsaddressing.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.soap.wsaddressing.Address;
import org.opensaml.soap.wsaddressing.EndpointReferenceType;
import org.opensaml.soap.wsaddressing.Metadata;
import org.opensaml.soap.wsaddressing.ReferenceParameters;

public class EndpointReferenceTypeImpl extends AbstractWSAddressingObject implements EndpointReferenceType {
   private Address address;
   private Metadata metadata;
   private ReferenceParameters referenceParameters;
   private IndexedXMLObjectChildrenList unknownChildren = new IndexedXMLObjectChildrenList(this);
   private AttributeMap unknownAttributes = new AttributeMap(this);

   public EndpointReferenceTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public Address getAddress() {
      return this.address;
   }

   public void setAddress(Address newAddress) {
      this.address = (Address)this.prepareForAssignment(this.address, newAddress);
   }

   public Metadata getMetadata() {
      return this.metadata;
   }

   public void setMetadata(Metadata newMetadata) {
      this.metadata = (Metadata)this.prepareForAssignment(this.metadata, newMetadata);
   }

   public ReferenceParameters getReferenceParameters() {
      return this.referenceParameters;
   }

   public void setReferenceParameters(ReferenceParameters newReferenceParameters) {
      this.referenceParameters = (ReferenceParameters)this.prepareForAssignment(this.referenceParameters, newReferenceParameters);
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
      ArrayList children = new ArrayList();
      if (this.address != null) {
         children.add(this.address);
      }

      if (this.referenceParameters != null) {
         children.add(this.referenceParameters);
      }

      if (this.metadata != null) {
         children.add(this.metadata);
      }

      if (!this.getUnknownXMLObjects().isEmpty()) {
         children.addAll(this.getUnknownXMLObjects());
      }

      return Collections.unmodifiableList(children);
   }
}
