package org.opensaml.saml.saml1.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml1.core.AudienceRestrictionCondition;

public class AudienceRestrictionConditionImpl extends AbstractSAMLObject implements AudienceRestrictionCondition {
   private final XMLObjectChildrenList audiences = new XMLObjectChildrenList(this);

   protected AudienceRestrictionConditionImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getAudiences() {
      return this.audiences;
   }

   public List getOrderedChildren() {
      if (this.audiences.size() == 0) {
         return null;
      } else {
         ArrayList children = new ArrayList();
         children.addAll(this.audiences);
         return Collections.unmodifiableList(children);
      }
   }
}
