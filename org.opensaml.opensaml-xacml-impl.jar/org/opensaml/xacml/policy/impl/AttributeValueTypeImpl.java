package org.opensaml.xacml.policy.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.xacml.impl.AbstractXACMLObject;
import org.opensaml.xacml.policy.AttributeValueType;

public class AttributeValueTypeImpl extends AbstractXACMLObject implements AttributeValueType {
   private String dataType;
   private String textContent;
   private IndexedXMLObjectChildrenList unknownElements = new IndexedXMLObjectChildrenList(this);
   private AttributeMap unknownAttributes = new AttributeMap(this);

   protected AttributeValueTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getDataType() {
      return this.dataType;
   }

   public void setDataType(String type) {
      this.dataType = this.prepareForAssignment(this.dataType, type);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(this.unknownElements);
      return Collections.unmodifiableList(children);
   }

   public AttributeMap getUnknownAttributes() {
      return this.unknownAttributes;
   }

   public List getUnknownXMLObjects() {
      return this.unknownElements;
   }

   public List getUnknownXMLObjects(QName typeOrName) {
      return this.unknownElements.subList(typeOrName);
   }

   public String getValue() {
      return this.textContent;
   }

   public void setValue(String value) {
      this.textContent = this.prepareForAssignment(this.textContent, value);
   }
}
