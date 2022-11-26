package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.AudienceRestriction;

public class AudienceRestrictionImpl extends AbstractSAMLObject implements AudienceRestriction {
   private final XMLObjectChildrenList audience = new XMLObjectChildrenList(this);

   protected AudienceRestrictionImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getAudiences() {
      return this.audience;
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(this.audience);
      return Collections.unmodifiableList(children);
   }
}
