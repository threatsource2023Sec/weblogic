package org.opensaml.saml.saml1.core.impl;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml1.core.AuthorityBinding;

public class AuthorityBindingImpl extends AbstractSAMLObject implements AuthorityBinding {
   private QName authorityKind;
   private String location;
   private String binding;

   protected AuthorityBindingImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public QName getAuthorityKind() {
      return this.authorityKind;
   }

   public void setAuthorityKind(QName kind) {
      this.authorityKind = this.prepareAttributeValueForAssignment("AuthorityKind", this.authorityKind, kind);
   }

   public String getLocation() {
      return this.location;
   }

   public void setLocation(String loc) {
      this.location = this.prepareForAssignment(this.location, loc);
   }

   public String getBinding() {
      return this.binding;
   }

   public void setBinding(String newBinding) {
      this.binding = this.prepareForAssignment(this.binding, newBinding);
   }

   public List getOrderedChildren() {
      return null;
   }
}
