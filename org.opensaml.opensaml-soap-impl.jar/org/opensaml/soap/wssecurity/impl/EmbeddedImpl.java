package org.opensaml.soap.wssecurity.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.soap.wssecurity.Embedded;

public class EmbeddedImpl extends AbstractWSSecurityObject implements Embedded {
   private String valueType;
   private AttributeMap unknownAttributes = new AttributeMap(this);
   private IndexedXMLObjectChildrenList unknownChildren = new IndexedXMLObjectChildrenList(this);

   protected EmbeddedImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getValueType() {
      return this.valueType;
   }

   public void setValueType(String newValueType) {
      this.valueType = this.prepareForAssignment(this.valueType, newValueType);
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
      if (!this.getUnknownXMLObjects().isEmpty()) {
         children.addAll(this.getUnknownXMLObjects());
      }

      return Collections.unmodifiableList(children);
   }
}
