package org.opensaml.core.xml;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;

public abstract class AbstractElementExtensibleXMLObject extends AbstractXMLObject implements ElementExtensibleXMLObject {
   @Nonnull
   private final IndexedXMLObjectChildrenList anyXMLObjects = new IndexedXMLObjectChildrenList(this);

   public AbstractElementExtensibleXMLObject(@Nullable String namespaceURI, @Nonnull String elementLocalName, @Nullable String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   @Nullable
   public List getOrderedChildren() {
      return Collections.unmodifiableList(this.anyXMLObjects);
   }

   @Nonnull
   public List getUnknownXMLObjects() {
      return this.anyXMLObjects;
   }

   @Nonnull
   public List getUnknownXMLObjects(@Nonnull QName typeOrName) {
      return this.anyXMLObjects.subList(typeOrName);
   }
}
