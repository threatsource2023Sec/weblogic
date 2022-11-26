package org.opensaml.saml.saml1.core.impl;

import java.util.List;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml1.core.NameIdentifier;

public class NameIdentifierImpl extends AbstractSAMLObject implements NameIdentifier {
   private String nameQualifier;
   private String format;
   private String nameIdentifier;

   protected NameIdentifierImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getNameQualifier() {
      return this.nameQualifier;
   }

   public String getFormat() {
      return this.format;
   }

   /** @deprecated */
   @Deprecated
   public String getNameIdentifier() {
      return this.getValue();
   }

   public String getValue() {
      return this.nameIdentifier;
   }

   public void setNameQualifier(String qualifier) {
      this.nameQualifier = this.prepareForAssignment(this.nameQualifier, qualifier);
   }

   public void setFormat(String fmt) {
      this.format = this.prepareForAssignment(this.format, fmt);
   }

   /** @deprecated */
   @Deprecated
   public void setNameIdentifier(String id) {
      this.setValue(id);
   }

   public void setValue(String id) {
      this.nameIdentifier = this.prepareForAssignment(this.nameIdentifier, id);
   }

   public List getOrderedChildren() {
      return null;
   }
}
