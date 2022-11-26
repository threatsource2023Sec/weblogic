package org.opensaml.saml.saml2.metadata.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.metadata.AttributeConsumingService;

public class AttributeConsumingServiceImpl extends AbstractSAMLObject implements AttributeConsumingService {
   private int index;
   private XSBooleanValue isDefault;
   private final XMLObjectChildrenList serviceNames = new XMLObjectChildrenList(this);
   private final XMLObjectChildrenList serviceDescriptions = new XMLObjectChildrenList(this);
   private final XMLObjectChildrenList requestAttributes = new XMLObjectChildrenList(this);

   protected AttributeConsumingServiceImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public int getIndex() {
      return this.index;
   }

   public void setIndex(int theIndex) {
      if (this.index != theIndex) {
         this.releaseThisandParentDOM();
         this.index = theIndex;
      }

   }

   public Boolean isDefault() {
      return this.isDefault != null ? this.isDefault.getValue() : Boolean.FALSE;
   }

   public XSBooleanValue isDefaultXSBoolean() {
      return this.isDefault;
   }

   public void setIsDefault(Boolean newIsDefault) {
      if (newIsDefault != null) {
         this.isDefault = (XSBooleanValue)this.prepareForAssignment(this.isDefault, new XSBooleanValue(newIsDefault, false));
      } else {
         this.isDefault = (XSBooleanValue)this.prepareForAssignment(this.isDefault, (Object)null);
      }

   }

   public void setIsDefault(XSBooleanValue newIsDefault) {
      this.isDefault = (XSBooleanValue)this.prepareForAssignment(this.isDefault, newIsDefault);
   }

   public List getNames() {
      return this.serviceNames;
   }

   public List getDescriptions() {
      return this.serviceDescriptions;
   }

   public List getRequestAttributes() {
      return this.requestAttributes;
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(this.serviceNames);
      children.addAll(this.serviceDescriptions);
      children.addAll(this.requestAttributes);
      return Collections.unmodifiableList(children);
   }
}
