package org.opensaml.xacml.ctx.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.xacml.ctx.ResourceContentType;
import org.opensaml.xacml.ctx.ResourceType;
import org.opensaml.xacml.impl.AbstractXACMLObject;

public class ResourceTypeImpl extends AbstractXACMLObject implements ResourceType {
   private XMLObjectChildrenList attributes = new XMLObjectChildrenList(this);
   private ResourceContentType resourceContent;

   protected ResourceTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getAttributes() {
      return this.attributes;
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (this.resourceContent != null) {
         children.add(this.resourceContent);
      }

      children.addAll(this.attributes);
      return Collections.unmodifiableList(children);
   }

   public ResourceContentType getResourceContent() {
      return this.resourceContent;
   }

   public void setResourceContent(ResourceContentType content) {
      this.resourceContent = (ResourceContentType)this.prepareForAssignment(this.resourceContent, content);
   }
}
