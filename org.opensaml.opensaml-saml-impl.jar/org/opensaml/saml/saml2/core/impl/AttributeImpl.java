package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.Attribute;

public class AttributeImpl extends AbstractSAMLObject implements Attribute {
   private String name;
   private String nameFormat;
   private String friendlyName;
   private AttributeMap unknownAttributes = new AttributeMap(this);
   private final XMLObjectChildrenList attributeValues = new XMLObjectChildrenList(this);

   protected AttributeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getName() {
      return this.name;
   }

   public void setName(String n) {
      this.name = this.prepareForAssignment(this.name, n);
   }

   public String getNameFormat() {
      return this.nameFormat;
   }

   public void setNameFormat(String format) {
      this.nameFormat = this.prepareForAssignment(this.nameFormat, format);
   }

   public String getFriendlyName() {
      return this.friendlyName;
   }

   public void setFriendlyName(String fname) {
      this.friendlyName = this.prepareForAssignment(this.friendlyName, fname);
   }

   public AttributeMap getUnknownAttributes() {
      return this.unknownAttributes;
   }

   public List getAttributeValues() {
      return this.attributeValues;
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(this.attributeValues);
      return Collections.unmodifiableList(children);
   }
}
