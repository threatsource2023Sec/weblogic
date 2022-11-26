package org.opensaml.saml.saml1.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml1.core.Evidence;

public class EvidenceImpl extends AbstractSAMLObject implements Evidence {
   private final IndexedXMLObjectChildrenList evidence = new IndexedXMLObjectChildrenList(this);

   protected EvidenceImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getAssertionIDReferences() {
      QName qname = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AssertionIDReference");
      return this.evidence.subList(qname);
   }

   public List getAssertions() {
      QName qname = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "Assertion");
      return this.evidence.subList(qname);
   }

   public List getEvidence() {
      return this.evidence;
   }

   public List getOrderedChildren() {
      if (this.evidence.size() == 0) {
         return null;
      } else {
         ArrayList list = new ArrayList();
         list.addAll(this.evidence);
         return Collections.unmodifiableList(list);
      }
   }
}
