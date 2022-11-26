package org.opensaml.saml.ext.saml2mdui.impl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.ext.saml2mdui.DiscoHints;
import org.opensaml.saml.ext.saml2mdui.DomainHint;
import org.opensaml.saml.ext.saml2mdui.GeolocationHint;
import org.opensaml.saml.ext.saml2mdui.IPHint;

public class DiscoHintsImpl extends AbstractSAMLObject implements DiscoHints {
   private final IndexedXMLObjectChildrenList discoHintsChildren = new IndexedXMLObjectChildrenList(this);

   protected DiscoHintsImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getXMLObjects() {
      return this.discoHintsChildren;
   }

   public List getXMLObjects(QName typeOrName) {
      return this.discoHintsChildren.subList(typeOrName);
   }

   public List getDomainHints() {
      return this.discoHintsChildren.subList(DomainHint.DEFAULT_ELEMENT_NAME);
   }

   public List getGeolocationHints() {
      return this.discoHintsChildren.subList(GeolocationHint.DEFAULT_ELEMENT_NAME);
   }

   public List getIPHints() {
      return this.discoHintsChildren.subList(IPHint.DEFAULT_ELEMENT_NAME);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(this.discoHintsChildren);
      return children;
   }
}
