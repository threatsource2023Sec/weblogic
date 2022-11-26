package org.opensaml.soap.wstrust.impl;

import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.soap.wstrust.KeyExchangeToken;

public class KeyExchangeTokenImpl extends AbstractWSTrustObject implements KeyExchangeToken {
   private IndexedXMLObjectChildrenList unknownChildren = new IndexedXMLObjectChildrenList(this);

   public KeyExchangeTokenImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getUnknownXMLObjects() {
      return this.unknownChildren;
   }

   public List getUnknownXMLObjects(QName typeOrName) {
      return this.unknownChildren.subList(typeOrName);
   }

   public List getOrderedChildren() {
      return Collections.unmodifiableList(this.unknownChildren);
   }
}
