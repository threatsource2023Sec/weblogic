package org.opensaml.saml.ext.saml2mdattr.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.ext.saml2mdattr.EntityAttributes;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.Attribute;

public class EntityAttributesImpl extends AbstractSAMLObject implements EntityAttributes {
   private final IndexedXMLObjectChildrenList attributeInfo = new IndexedXMLObjectChildrenList(this);

   protected EntityAttributesImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getAttributes() {
      return this.attributeInfo.subList(Attribute.DEFAULT_ELEMENT_NAME);
   }

   public List getAssertions() {
      return this.attributeInfo.subList(Assertion.DEFAULT_ELEMENT_NAME);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (this.attributeInfo.size() == 0) {
         return null;
      } else {
         children.addAll(this.attributeInfo);
         return Collections.unmodifiableList(children);
      }
   }
}
