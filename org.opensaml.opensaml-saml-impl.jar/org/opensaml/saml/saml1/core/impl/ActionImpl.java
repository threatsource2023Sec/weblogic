package org.opensaml.saml.saml1.core.impl;

import java.util.List;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml1.core.Action;

public class ActionImpl extends AbstractSAMLObject implements Action {
   private String namespace;
   private String contents;

   protected ActionImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getNamespace() {
      return this.namespace;
   }

   public void setNamespace(String ns) {
      this.namespace = this.prepareForAssignment(this.namespace, ns);
   }

   public String getContents() {
      return this.contents;
   }

   public void setContents(String c) {
      this.contents = this.prepareForAssignment(this.contents, c);
   }

   public List getOrderedChildren() {
      return null;
   }
}
