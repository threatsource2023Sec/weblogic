package org.opensaml.saml.saml2.core.impl;

import java.util.List;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.Action;

public class ActionImpl extends AbstractSAMLObject implements Action {
   private String namespace;
   private String action;

   protected ActionImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getNamespace() {
      return this.namespace;
   }

   public void setNamespace(String newNamespace) {
      this.namespace = this.prepareForAssignment(this.namespace, newNamespace);
   }

   public String getAction() {
      return this.action;
   }

   public void setAction(String newAction) {
      this.action = this.prepareForAssignment(this.action, newAction);
   }

   public List getOrderedChildren() {
      return null;
   }
}
