package org.opensaml.saml.ext.saml2mdui.impl;

import java.util.List;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.ext.saml2mdui.IPHint;

public class IPHintImpl extends AbstractSAMLObject implements IPHint {
   private String hint;

   protected IPHintImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
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
