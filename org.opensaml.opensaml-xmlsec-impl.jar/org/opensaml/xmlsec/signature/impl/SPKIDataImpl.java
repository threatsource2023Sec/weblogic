package org.opensaml.xmlsec.signature.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.xmlsec.signature.SPKIData;
import org.opensaml.xmlsec.signature.SPKISexp;

public class SPKIDataImpl extends AbstractXMLObject implements SPKIData {
   private final IndexedXMLObjectChildrenList indexedChildren = new IndexedXMLObjectChildrenList(this);

   protected SPKIDataImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getXMLObjects() {
      return this.indexedChildren;
   }

   public List getXMLObjects(QName typeOrName) {
      return this.indexedChildren.subList(typeOrName);
   }

   public List getSPKISexps() {
      return this.indexedChildren.subList(SPKISexp.DEFAULT_ELEMENT_NAME);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(this.indexedChildren);
      return children.size() == 0 ? null : Collections.unmodifiableList(children);
   }
}
