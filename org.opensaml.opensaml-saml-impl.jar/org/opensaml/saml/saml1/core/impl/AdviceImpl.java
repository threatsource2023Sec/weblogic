package org.opensaml.saml.saml1.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml1.core.Advice;

public class AdviceImpl extends AbstractSAMLObject implements Advice {
   private final IndexedXMLObjectChildrenList assertionChildren = new IndexedXMLObjectChildrenList(this);
   private final IndexedXMLObjectChildrenList unknownChildren = new IndexedXMLObjectChildrenList(this);

   protected AdviceImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getAssertionIDReferences() {
      QName assertionIDRefQName = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AssertionIDReference");
      return this.assertionChildren.subList(assertionIDRefQName);
   }

   public List getAssertions() {
      QName assertionQname = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "Assertion");
      return this.assertionChildren.subList(assertionQname);
   }

   public List getUnknownXMLObjects() {
      return this.unknownChildren;
   }

   public List getUnknownXMLObjects(QName typeOrName) {
      return this.unknownChildren.subList(typeOrName);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(this.assertionChildren);
      children.addAll(this.unknownChildren);
      return Collections.unmodifiableList(children);
   }
}
