package org.opensaml.saml.ext.saml2mdui.impl;

import java.util.List;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.ext.saml2mdui.GeolocationHint;

public class GeolocationHintImpl extends AbstractSAMLObject implements GeolocationHint {
   private String hint;

   protected GeolocationHintImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
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
