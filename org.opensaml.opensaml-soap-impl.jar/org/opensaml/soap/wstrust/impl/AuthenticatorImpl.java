package org.opensaml.soap.wstrust.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.soap.wstrust.Authenticator;
import org.opensaml.soap.wstrust.CombinedHash;

public class AuthenticatorImpl extends AbstractWSTrustObject implements Authenticator {
   private CombinedHash combinedHash;
   private IndexedXMLObjectChildrenList unknownChildren = new IndexedXMLObjectChildrenList(this);

   public AuthenticatorImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public CombinedHash getCombinedHash() {
      return this.combinedHash;
   }

   public void setCombinedHash(CombinedHash newCombinedHash) {
      this.combinedHash = (CombinedHash)this.prepareForAssignment(this.combinedHash, newCombinedHash);
   }

   public List getUnknownXMLObjects() {
      return this.unknownChildren;
   }

   public List getUnknownXMLObjects(QName typeOrName) {
      return this.unknownChildren.subList(typeOrName);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (this.combinedHash != null) {
         children.add(this.combinedHash);
      }

      children.addAll(this.unknownChildren);
      return Collections.unmodifiableList(children);
   }
}
