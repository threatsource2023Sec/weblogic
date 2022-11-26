package org.opensaml.soap.wspolicy.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.soap.wspolicy.All;
import org.opensaml.soap.wspolicy.ExactlyOne;
import org.opensaml.soap.wspolicy.OperatorContentType;
import org.opensaml.soap.wspolicy.Policy;
import org.opensaml.soap.wspolicy.PolicyReference;

public class OperatorContentTypeImpl extends AbstractWSPolicyObject implements OperatorContentType {
   private IndexedXMLObjectChildrenList xmlObjects = new IndexedXMLObjectChildrenList(this);

   public OperatorContentTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getAlls() {
      return this.xmlObjects.subList(All.ELEMENT_NAME);
   }

   public List getExactlyOnes() {
      return this.xmlObjects.subList(ExactlyOne.ELEMENT_NAME);
   }

   public List getPolicies() {
      return this.xmlObjects.subList(Policy.ELEMENT_NAME);
   }

   public List getPolicyReferences() {
      return this.xmlObjects.subList(PolicyReference.ELEMENT_NAME);
   }

   public List getXMLObjects() {
      return this.xmlObjects;
   }

   public List getXMLObjects(QName typeOrName) {
      return this.xmlObjects.subList(typeOrName);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(this.xmlObjects);
      return Collections.unmodifiableList(children);
   }
}
