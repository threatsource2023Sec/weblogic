package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.IDPList;
import org.opensaml.saml.saml2.core.Scoping;

public class ScopingImpl extends AbstractSAMLObject implements Scoping {
   private IDPList idpList;
   private final XMLObjectChildrenList requesterIDs = new XMLObjectChildrenList(this);
   private Integer proxyCount;

   protected ScopingImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public Integer getProxyCount() {
      return this.proxyCount;
   }

   public void setProxyCount(Integer newProxyCount) {
      this.proxyCount = (Integer)this.prepareForAssignment(this.proxyCount, newProxyCount);
   }

   public IDPList getIDPList() {
      return this.idpList;
   }

   public void setIDPList(IDPList newIDPList) {
      this.idpList = (IDPList)this.prepareForAssignment(this.idpList, newIDPList);
   }

   public List getRequesterIDs() {
      return this.requesterIDs;
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (this.idpList != null) {
         children.add(this.idpList);
      }

      children.addAll(this.requesterIDs);
      return children.size() > 0 ? Collections.unmodifiableList(children) : null;
   }
}
