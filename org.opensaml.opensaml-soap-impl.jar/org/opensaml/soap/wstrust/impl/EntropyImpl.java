package org.opensaml.soap.wstrust.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.soap.wstrust.Entropy;

public class EntropyImpl extends AbstractWSTrustObject implements Entropy {
   private IndexedXMLObjectChildrenList unknownChildren = new IndexedXMLObjectChildrenList(this);
   private AttributeMap unknownAttributes = new AttributeMap(this);

   public EntropyImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public AttributeMap getUnknownAttributes() {
      return this.unknownAttributes;
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
      return Collections.unmodifiableList(children);
   }
}
