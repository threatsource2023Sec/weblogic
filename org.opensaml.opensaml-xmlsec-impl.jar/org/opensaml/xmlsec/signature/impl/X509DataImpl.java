package org.opensaml.xmlsec.signature.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.xmlsec.signature.X509CRL;
import org.opensaml.xmlsec.signature.X509Certificate;
import org.opensaml.xmlsec.signature.X509Data;
import org.opensaml.xmlsec.signature.X509Digest;
import org.opensaml.xmlsec.signature.X509IssuerSerial;
import org.opensaml.xmlsec.signature.X509SKI;
import org.opensaml.xmlsec.signature.X509SubjectName;

public class X509DataImpl extends AbstractXMLObject implements X509Data {
   private final IndexedXMLObjectChildrenList indexedChildren = new IndexedXMLObjectChildrenList(this);

   protected X509DataImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getXMLObjects() {
      return this.indexedChildren;
   }

   public List getXMLObjects(QName typeOrName) {
      return this.indexedChildren.subList(typeOrName);
   }

   public List getX509IssuerSerials() {
      return this.indexedChildren.subList(X509IssuerSerial.DEFAULT_ELEMENT_NAME);
   }

   public List getX509SKIs() {
      return this.indexedChildren.subList(X509SKI.DEFAULT_ELEMENT_NAME);
   }

   public List getX509SubjectNames() {
      return this.indexedChildren.subList(X509SubjectName.DEFAULT_ELEMENT_NAME);
   }

   public List getX509Certificates() {
      return this.indexedChildren.subList(X509Certificate.DEFAULT_ELEMENT_NAME);
   }

   public List getX509CRLs() {
      return this.indexedChildren.subList(X509CRL.DEFAULT_ELEMENT_NAME);
   }

   public List getX509Digests() {
      return this.indexedChildren.subList(X509Digest.DEFAULT_ELEMENT_NAME);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(this.indexedChildren);
      return children.size() == 0 ? null : Collections.unmodifiableList(children);
   }
}
