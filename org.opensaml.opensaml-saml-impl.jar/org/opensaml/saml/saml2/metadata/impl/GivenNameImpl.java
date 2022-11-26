package org.opensaml.saml.saml2.metadata.impl;

import java.util.List;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.metadata.GivenName;

public class GivenNameImpl extends AbstractSAMLObject implements GivenName {
   private String name;

   protected GivenNameImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getName() {
      return this.name;
   }

   public void setName(String newName) {
      this.name = this.prepareForAssignment(this.name, newName);
   }

   public List getOrderedChildren() {
      return null;
   }
}
