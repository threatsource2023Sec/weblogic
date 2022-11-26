package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.saml2.core.AssertionIDRequest;

public class AssertionIDRequestImpl extends RequestAbstractTypeImpl implements AssertionIDRequest {
   private final XMLObjectChildrenList assertionIDRefs = new XMLObjectChildrenList(this);

   protected AssertionIDRequestImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getAssertionIDRefs() {
      return this.assertionIDRefs;
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (super.getOrderedChildren() != null) {
         children.addAll(super.getOrderedChildren());
      }

      children.addAll(this.assertionIDRefs);
      return children.size() == 0 ? null : Collections.unmodifiableList(children);
   }
}
