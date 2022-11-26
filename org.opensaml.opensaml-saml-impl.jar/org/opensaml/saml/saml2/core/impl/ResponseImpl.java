package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.EncryptedAssertion;
import org.opensaml.saml.saml2.core.Response;

public class ResponseImpl extends StatusResponseTypeImpl implements Response {
   private final IndexedXMLObjectChildrenList indexedChildren = new IndexedXMLObjectChildrenList(this);

   protected ResponseImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getAssertions() {
      return this.indexedChildren.subList(Assertion.DEFAULT_ELEMENT_NAME);
   }

   public List getEncryptedAssertions() {
      return this.indexedChildren.subList(EncryptedAssertion.DEFAULT_ELEMENT_NAME);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (super.getOrderedChildren() != null) {
         children.addAll(super.getOrderedChildren());
      }

      children.addAll(this.indexedChildren);
      return children.size() == 0 ? null : Collections.unmodifiableList(children);
   }
}
