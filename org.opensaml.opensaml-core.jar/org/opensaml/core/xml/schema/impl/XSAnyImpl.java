package org.opensaml.core.xml.schema.impl;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.schema.XSAny;
import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;

public class XSAnyImpl extends AbstractXMLObject implements XSAny {
   @Nonnull
   private IndexedXMLObjectChildrenList unknownXMLObjects = new IndexedXMLObjectChildrenList(this);
   @Nonnull
   private AttributeMap unknownAttributes = new AttributeMap(this);
   @Nullable
   private String textContent;

   protected XSAnyImpl(@Nullable String namespaceURI, @Nonnull String elementLocalName, @Nullable String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getTextContent() {
      return this.textContent;
   }

   public void setTextContent(@Nullable String newContent) {
      this.textContent = this.prepareForAssignment(this.textContent, newContent);
   }

   @Nonnull
   public List getUnknownXMLObjects() {
      return this.unknownXMLObjects;
   }

   @Nonnull
   public List getUnknownXMLObjects(@Nonnull QName typeOrName) {
      return this.unknownXMLObjects.subList(typeOrName);
   }

   @Nullable
   public List getOrderedChildren() {
      return Collections.unmodifiableList(this.unknownXMLObjects);
   }

   @Nonnull
   public AttributeMap getUnknownAttributes() {
      return this.unknownAttributes;
   }
}
