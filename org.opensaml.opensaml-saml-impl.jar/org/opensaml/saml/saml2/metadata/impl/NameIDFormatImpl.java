package org.opensaml.saml.saml2.metadata.impl;

import java.util.List;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.metadata.NameIDFormat;

public class NameIDFormatImpl extends AbstractSAMLObject implements NameIDFormat {
   private String format;

   protected NameIDFormatImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getFormat() {
      return this.format;
   }

   public void setFormat(String newFormat) {
      this.format = this.prepareForAssignment(this.format, newFormat);
   }

   public List getOrderedChildren() {
      return null;
   }
}
