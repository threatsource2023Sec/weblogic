package org.opensaml.xacml.ctx.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.xacml.ctx.AttributeValueType;
import org.opensaml.xacml.impl.AbstractXACMLObject;

public class AttributeValueTypeImpl extends AbstractXACMLObject implements AttributeValueType {
   private IndexedXMLObjectChildrenList unknownElements = new IndexedXMLObjectChildrenList(this);
   private AttributeMap unknownAttributes = new AttributeMap(this);
   private String textContent;

   protected AttributeValueTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (this.textContent == null) {
         children.addAll(this.unknownElements);
      }

      return Collections.unmodifiableList(children);
   }

   public List getUnknownXMLObjects() {
      return this.unknownElements;
   }

   public List getUnknownXMLObjects(QName typeOrName) {
      return this.unknownElements.subList(typeOrName);
   }

   public AttributeMap getUnknownAttributes() {
      return this.unknownAttributes;
   }

   public String getValue() {
      return this.textContent;
   }

   public void setValue(String value) {
      this.textContent = this.prepareForAssignment(this.textContent, value);
   }
}
