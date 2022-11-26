package org.opensaml.saml.saml2.core.impl;

import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.Extensions;

public class ExtensionsImpl extends AbstractSAMLObject implements Extensions {
   private final IndexedXMLObjectChildrenList unknownChildren = new IndexedXMLObjectChildrenList(this);

   protected ExtensionsImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getUnknownXMLObjects() {
      return this.unknownChildren;
   }

   public List getUnknownXMLObjects(QName typeOrName) {
      return this.unknownChildren.subList(typeOrName);
   }

   public List getOrderedChildren() {
      return Collections.unmodifiableList(this.unknownChildren);
   }
}
