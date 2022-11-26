package org.opensaml.saml.ext.saml2delrestrict.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.ext.saml2delrestrict.DelegationRestrictionType;

public class DelegationRestrictionTypeImpl extends AbstractSAMLObject implements DelegationRestrictionType {
   private XMLObjectChildrenList delegates = new XMLObjectChildrenList(this);

   protected DelegationRestrictionTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getDelegates() {
      return this.delegates;
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(this.delegates);
      return Collections.unmodifiableList(children);
   }
}
