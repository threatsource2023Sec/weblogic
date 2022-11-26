package org.opensaml.saml.ext.saml2mdquery.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.ext.saml2mdquery.AttributeQueryDescriptorType;

public class AttributeQueryDescriptorTypeImpl extends QueryDescriptorTypeImpl implements AttributeQueryDescriptorType {
   private XMLObjectChildrenList attributeConsumingServices = new XMLObjectChildrenList(this);

   protected AttributeQueryDescriptorTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getAttributeConsumingServices() {
      return this.attributeConsumingServices;
   }

   public List getEndpoints() {
      return new ArrayList();
   }

   public List getEndpoints(QName type) {
      return null;
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(super.getOrderedChildren());
      children.addAll(this.attributeConsumingServices);
      return Collections.unmodifiableList(children);
   }
}
