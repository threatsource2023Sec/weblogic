package org.opensaml.xmlsec.encryption.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.xmlsec.encryption.DataReference;
import org.opensaml.xmlsec.encryption.KeyReference;
import org.opensaml.xmlsec.encryption.ReferenceList;

public class ReferenceListImpl extends AbstractXMLObject implements ReferenceList {
   private final IndexedXMLObjectChildrenList indexedChildren = new IndexedXMLObjectChildrenList(this);

   protected ReferenceListImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getReferences() {
      return this.indexedChildren;
   }

   public List getDataReferences() {
      return this.indexedChildren.subList(DataReference.DEFAULT_ELEMENT_NAME);
   }

   public List getKeyReferences() {
      return this.indexedChildren.subList(KeyReference.DEFAULT_ELEMENT_NAME);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(this.indexedChildren);
      return children.size() == 0 ? null : Collections.unmodifiableList(children);
   }
}
