package org.opensaml.soap.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.ElementExtensibleXMLObject;
import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;

public abstract class AbstractExtensibleSOAPObject extends AbstractXMLObject implements SOAPObject, AttributeExtensibleXMLObject, ElementExtensibleXMLObject {
   private IndexedXMLObjectChildrenList unknownXMLObjects = new IndexedXMLObjectChildrenList(this);
   private AttributeMap attributes = new AttributeMap(this);

   protected AbstractExtensibleSOAPObject(@Nullable String namespaceURI, @Nonnull String elementLocalName, @Nullable String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   @Nullable
   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(this.unknownXMLObjects);
      return Collections.unmodifiableList(children);
   }

   @Nonnull
   public List getUnknownXMLObjects() {
      return this.unknownXMLObjects;
   }

   @Nonnull
   public List getUnknownXMLObjects(QName typeOrName) {
      return this.unknownXMLObjects.subList(typeOrName);
   }

   @Nonnull
   public AttributeMap getUnknownAttributes() {
      return this.attributes;
   }
}
