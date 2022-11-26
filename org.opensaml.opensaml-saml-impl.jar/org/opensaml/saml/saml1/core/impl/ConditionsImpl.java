package org.opensaml.saml.saml1.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.joda.time.DateTime;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml1.core.Conditions;

public class ConditionsImpl extends AbstractSAMLObject implements Conditions {
   private DateTime notBefore;
   private DateTime notOnOrAfter;
   private final IndexedXMLObjectChildrenList conditions = new IndexedXMLObjectChildrenList(this);

   protected ConditionsImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public DateTime getNotBefore() {
      return this.notBefore;
   }

   public void setNotBefore(DateTime dt) {
      this.notBefore = this.prepareForAssignment(this.notBefore, dt);
   }

   public DateTime getNotOnOrAfter() {
      return this.notOnOrAfter;
   }

   public void setNotOnOrAfter(DateTime dt) {
      this.notOnOrAfter = this.prepareForAssignment(this.notOnOrAfter, dt);
   }

   public List getConditions() {
      return this.conditions;
   }

   public List getConditions(QName typeOrName) {
      return this.conditions;
   }

   public List getAudienceRestrictionConditions() {
      QName qname = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AudienceRestrictionCondition");
      return this.conditions.subList(qname);
   }

   public List getDoNotCacheConditions() {
      QName qname = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "DoNotCacheCondition");
      return this.conditions.subList(qname);
   }

   public List getOrderedChildren() {
      if (this.conditions.size() == 0) {
         return null;
      } else {
         ArrayList children = new ArrayList();
         children.addAll(this.conditions);
         return Collections.unmodifiableList(children);
      }
   }
}
