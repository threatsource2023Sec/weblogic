package org.opensaml.saml.ext.saml2mdquery.impl;

import java.util.List;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.ext.saml2mdquery.ActionNamespace;

public class ActionNamespaceImpl extends AbstractSAMLObject implements ActionNamespace {
   private String value;

   protected ActionNamespaceImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getValue() {
      return this.value;
   }

   public void setValue(String newValue) {
      this.value = this.prepareForAssignment(this.value, newValue);
   }

   public List getOrderedChildren() {
      return null;
   }
}
