package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.GetComplete;
import org.opensaml.saml.saml2.core.IDPList;

public class IDPListImpl extends AbstractSAMLObject implements IDPList {
   private final XMLObjectChildrenList idpEntries = new XMLObjectChildrenList(this);
   private GetComplete getComplete;

   protected IDPListImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getIDPEntrys() {
      return this.idpEntries;
   }

   public GetComplete getGetComplete() {
      return this.getComplete;
   }

   public void setGetComplete(GetComplete newGetComplete) {
      this.getComplete = (GetComplete)this.prepareForAssignment(this.getComplete, newGetComplete);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(this.idpEntries);
      children.add(this.getComplete);
      return children.size() > 0 ? Collections.unmodifiableList(children) : null;
   }
}
