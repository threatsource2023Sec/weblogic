package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.AssertionIDRef;
import org.opensaml.saml.saml2.core.AssertionURIRef;
import org.opensaml.saml.saml2.core.EncryptedAssertion;
import org.opensaml.saml.saml2.core.Evidence;

public class EvidenceImpl extends AbstractSAMLObject implements Evidence {
   private final IndexedXMLObjectChildrenList evidence = new IndexedXMLObjectChildrenList(this);

   protected EvidenceImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getEvidence() {
      return this.evidence;
   }

   public List getAssertionIDReferences() {
      return this.evidence.subList(AssertionIDRef.DEFAULT_ELEMENT_NAME);
   }

   public List getAssertionURIReferences() {
      return this.evidence.subList(AssertionURIRef.DEFAULT_ELEMENT_NAME);
   }

   public List getAssertions() {
      return this.evidence.subList(Assertion.DEFAULT_ELEMENT_NAME);
   }

   public List getEncryptedAssertions() {
      return this.evidence.subList(EncryptedAssertion.DEFAULT_ELEMENT_NAME);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (this.evidence.size() == 0) {
         return null;
      } else {
         children.addAll(this.evidence);
         return Collections.unmodifiableList(children);
      }
   }
}
