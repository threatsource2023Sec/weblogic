package org.opensaml.saml.ext.saml2mdui.impl;

import java.util.List;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.ext.saml2mdui.DomainHint;

public class DomainHintImpl extends AbstractSAMLObject implements DomainHint {
   private String hint;

   protected DomainHintImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getHint() {
      return this.hint;
   }

   public void setHint(String newHint) {
      this.hint = this.prepareForAssignment(this.hint, newHint);
   }

   public List getOrderedChildren() {
      return null;
   }
}
