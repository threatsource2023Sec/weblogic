package org.opensaml.soap.wssecurity.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.soap.wssecurity.Security;

public class SecurityImpl extends AbstractWSSecurityObject implements Security {
   private AttributeMap unknownAttributes = new AttributeMap(this);
   private IndexedXMLObjectChildrenList unknownChildren = new IndexedXMLObjectChildrenList(this);

   public SecurityImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getUnknownXMLObjects() {
      return this.unknownChildren;
   }

   public List getUnknownXMLObjects(QName typeOrName) {
      return this.unknownChildren.subList(typeOrName);
   }

   public AttributeMap getUnknownAttributes() {
      return this.unknownAttributes;
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (!this.getUnknownXMLObjects().isEmpty()) {
         children.addAll(this.getUnknownXMLObjects());
      }

      return Collections.unmodifiableList(children);
   }
}
