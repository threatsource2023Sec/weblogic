package org.opensaml.xacml.ctx.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.xacml.ctx.ResourceContentType;
import org.opensaml.xacml.impl.AbstractXACMLObject;

public class ResourceContentTypeImpl extends AbstractXACMLObject implements ResourceContentType {
   private IndexedXMLObjectChildrenList unknownElements = new IndexedXMLObjectChildrenList(this);
   private AttributeMap unknownAttributes = new AttributeMap(this);
   private String value;

   protected ResourceContentTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(this.unknownElements);
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
      return this.value;
   }

   public void setValue(String newValue) {
      this.value = this.prepareForAssignment(this.value, newValue);
   }
}
