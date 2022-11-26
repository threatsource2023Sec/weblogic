package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.ProxyRestriction;

public class ProxyRestrictionImpl extends AbstractSAMLObject implements ProxyRestriction {
   private final XMLObjectChildrenList audiences = new XMLObjectChildrenList(this);
   private Integer proxyCount;

   protected ProxyRestrictionImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getAudiences() {
      return this.audiences;
   }

   public Integer getProxyCount() {
      return this.proxyCount;
   }

   public void setProxyCount(Integer newProxyCount) {
      if (newProxyCount >= 0) {
         this.proxyCount = (Integer)this.prepareForAssignment(this.proxyCount, newProxyCount);
      } else {
         throw new IllegalArgumentException("Count must be a non-negative integer.");
      }
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(this.audiences);
      return Collections.unmodifiableList(children);
   }
}
