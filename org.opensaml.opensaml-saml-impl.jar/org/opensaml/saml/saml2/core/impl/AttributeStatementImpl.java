package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.Attribute;
import org.opensaml.saml.saml2.core.AttributeStatement;
import org.opensaml.saml.saml2.core.EncryptedAttribute;

public class AttributeStatementImpl extends AbstractSAMLObject implements AttributeStatement {
   private final IndexedXMLObjectChildrenList indexedChildren = new IndexedXMLObjectChildrenList(this);

   protected AttributeStatementImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getAttributes() {
      return this.indexedChildren.subList(Attribute.DEFAULT_ELEMENT_NAME);
   }

   public List getEncryptedAttributes() {
      return this.indexedChildren.subList(EncryptedAttribute.DEFAULT_ELEMENT_NAME);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(this.indexedChildren);
      return Collections.unmodifiableList(children);
   }
}
