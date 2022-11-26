package org.opensaml.soap.wspolicy.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.soap.wspolicy.AppliesTo;
import org.opensaml.soap.wspolicy.Policy;
import org.opensaml.soap.wspolicy.PolicyAttachment;
import org.opensaml.soap.wspolicy.PolicyReference;

public class PolicyAttachmentImpl extends AbstractWSPolicyObject implements PolicyAttachment {
   private AppliesTo appliesTo;
   private IndexedXMLObjectChildrenList policiesAndReferences = new IndexedXMLObjectChildrenList(this);
   private IndexedXMLObjectChildrenList unknownChildren = new IndexedXMLObjectChildrenList(this);
   private AttributeMap unknownAttributes = new AttributeMap(this);

   public PolicyAttachmentImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public AppliesTo getAppliesTo() {
      return this.appliesTo;
   }

   public void setAppliesTo(AppliesTo newAppliesTo) {
      this.appliesTo = (AppliesTo)this.prepareForAssignment(this.appliesTo, newAppliesTo);
   }

   public List getPolicies() {
      return this.policiesAndReferences.subList(Policy.ELEMENT_NAME);
   }

   public List getPolicyReferences() {
      return this.policiesAndReferences.subList(PolicyReference.ELEMENT_NAME);
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
      if (this.appliesTo != null) {
         children.add(this.appliesTo);
      }

      children.addAll(this.policiesAndReferences);
      children.addAll(this.unknownChildren);
      return Collections.unmodifiableList(children);
   }
}
