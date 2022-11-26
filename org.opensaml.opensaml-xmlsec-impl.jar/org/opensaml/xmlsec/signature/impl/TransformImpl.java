package org.opensaml.xmlsec.signature.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.xmlsec.signature.Transform;
import org.opensaml.xmlsec.signature.XPath;

public class TransformImpl extends AbstractXMLObject implements Transform {
   private String algorithm;
   private final IndexedXMLObjectChildrenList indexedChildren = new IndexedXMLObjectChildrenList(this);

   protected TransformImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getAlgorithm() {
      return this.algorithm;
   }

   public void setAlgorithm(String newAlgorithm) {
      this.algorithm = this.prepareForAssignment(this.algorithm, newAlgorithm);
   }

   public List getAllChildren() {
      return this.indexedChildren;
   }

   public List getXMLObjects(QName typeOrName) {
      return this.indexedChildren.subList(typeOrName);
   }

   public List getXPaths() {
      return this.indexedChildren.subList(XPath.DEFAULT_ELEMENT_NAME);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(this.indexedChildren);
      return children.size() == 0 ? null : Collections.unmodifiableList(children);
   }
}
