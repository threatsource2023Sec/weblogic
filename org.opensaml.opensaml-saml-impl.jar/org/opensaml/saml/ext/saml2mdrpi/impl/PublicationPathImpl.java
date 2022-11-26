package org.opensaml.saml.ext.saml2mdrpi.impl;

import java.util.ArrayList;
import java.util.List;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.ext.saml2mdrpi.PublicationPath;

public class PublicationPathImpl extends AbstractSAMLObject implements PublicationPath {
   private XMLObjectChildrenList publications = new IndexedXMLObjectChildrenList(this);

   protected PublicationPathImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getPublications() {
      return this.publications;
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(this.publications);
      return children;
   }
}
