package org.opensaml.saml.saml2.core.impl;

import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.AuthnContextDecl;

public class AuthnContextDeclImpl extends AbstractSAMLObject implements AuthnContextDecl {
   private IndexedXMLObjectChildrenList unknownXMLObjects = new IndexedXMLObjectChildrenList(this);
   private AttributeMap unknownAttributes = new AttributeMap(this);
   private String textContent;

   protected AuthnContextDeclImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getTextContent() {
      return this.textContent;
   }

   public void setTextContent(String newContent) {
      this.textContent = this.prepareForAssignment(this.textContent, newContent);
   }

   public List getUnknownXMLObjects() {
      return this.unknownXMLObjects;
   }

   public List getUnknownXMLObjects(QName typeOrName) {
      return this.unknownXMLObjects.subList(typeOrName);
   }

   public List getOrderedChildren() {
      return Collections.unmodifiableList(this.unknownXMLObjects);
   }

   public AttributeMap getUnknownAttributes() {
      return this.unknownAttributes;
   }
}
