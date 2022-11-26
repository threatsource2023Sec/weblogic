package org.opensaml.xmlsec.signature.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.xmlsec.signature.DigestMethod;

public class DigestMethodImpl extends AbstractXMLObject implements DigestMethod {
   private String algorithm;
   private final IndexedXMLObjectChildrenList unknownChildren = new IndexedXMLObjectChildrenList(this);

   protected DigestMethodImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getAlgorithm() {
      return this.algorithm;
   }

   public void setAlgorithm(String newAlgorithm) {
      this.algorithm = this.prepareForAssignment(this.algorithm, newAlgorithm);
   }

   public List getUnknownXMLObjects() {
      return this.unknownChildren;
   }

   public List getUnknownXMLObjects(QName typeOrName) {
      return this.unknownChildren.subList(typeOrName);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(this.unknownChildren);
      return children.size() == 0 ? null : Collections.unmodifiableList(children);
   }
}
