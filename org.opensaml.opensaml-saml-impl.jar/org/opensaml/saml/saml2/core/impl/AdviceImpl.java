package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.Advice;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.AssertionIDRef;
import org.opensaml.saml.saml2.core.AssertionURIRef;
import org.opensaml.saml.saml2.core.EncryptedAssertion;

public class AdviceImpl extends AbstractSAMLObject implements Advice {
   private final IndexedXMLObjectChildrenList indexedChildren = new IndexedXMLObjectChildrenList(this);

   protected AdviceImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getChildren() {
      return this.indexedChildren;
   }

   public List getChildren(QName typeOrName) {
      return this.indexedChildren.subList(typeOrName);
   }

   public List getAssertionIDReferences() {
      return this.indexedChildren.subList(AssertionIDRef.DEFAULT_ELEMENT_NAME);
   }

   public List getAssertionURIReferences() {
      return this.indexedChildren.subList(AssertionURIRef.DEFAULT_ELEMENT_NAME);
   }

   public List getAssertions() {
      return this.indexedChildren.subList(Assertion.DEFAULT_ELEMENT_NAME);
   }

   public List getEncryptedAssertions() {
      return this.indexedChildren.subList(EncryptedAssertion.DEFAULT_ELEMENT_NAME);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(this.indexedChildren);
      return Collections.unmodifiableList(children);
   }
}
