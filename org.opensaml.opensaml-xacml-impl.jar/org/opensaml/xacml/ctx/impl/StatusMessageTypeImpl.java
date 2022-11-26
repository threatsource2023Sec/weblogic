package org.opensaml.xacml.ctx.impl;

import java.util.List;
import org.opensaml.xacml.ctx.StatusMessageType;
import org.opensaml.xacml.impl.AbstractXACMLObject;

public class StatusMessageTypeImpl extends AbstractXACMLObject implements StatusMessageType {
   private String message;

   protected StatusMessageTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getValue() {
      return this.message;
   }

   public void setValue(String newMessage) {
      this.message = this.prepareForAssignment(this.message, newMessage);
   }

   public List getOrderedChildren() {
      return null;
   }
}
