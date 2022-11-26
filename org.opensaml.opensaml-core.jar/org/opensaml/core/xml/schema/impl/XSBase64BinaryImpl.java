package org.opensaml.core.xml.schema.impl;

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.schema.XSBase64Binary;

public class XSBase64BinaryImpl extends AbstractXMLObject implements XSBase64Binary {
   @Nullable
   private String value;

   protected XSBase64BinaryImpl(@Nullable String namespaceURI, @Nonnull String elementLocalName, @Nullable String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   @Nullable
   public String getValue() {
      return this.value;
   }

   public void setValue(@Nullable String newValue) {
      this.value = this.prepareForAssignment(this.value, newValue);
   }

   @Nullable
   public List getOrderedChildren() {
      return null;
   }
}
