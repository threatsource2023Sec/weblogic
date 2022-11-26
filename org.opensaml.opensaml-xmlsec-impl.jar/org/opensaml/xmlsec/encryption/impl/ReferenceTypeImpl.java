package org.opensaml.xmlsec.encryption.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.xmlsec.encryption.ReferenceType;

public class ReferenceTypeImpl extends AbstractXMLObject implements ReferenceType {
   private String uri;
   private final IndexedXMLObjectChildrenList xmlChildren = new IndexedXMLObjectChildrenList(this);

   protected ReferenceTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getURI() {
      return this.uri;
   }

   public void setURI(String newURI) {
      this.uri = this.prepareForAssignment(this.uri, newURI);
   }

   public List getUnknownXMLObjects() {
      return this.xmlChildren;
   }

   public List getUnknownXMLObjects(QName typeOrName) {
      return this.xmlChildren.subList(typeOrName);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(this.xmlChildren);
      return children.size() == 0 ? null : Collections.unmodifiableList(children);
   }
}
