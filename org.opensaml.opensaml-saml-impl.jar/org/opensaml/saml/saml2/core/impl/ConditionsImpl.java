package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.joda.time.DateTime;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.Conditions;
import org.opensaml.saml.saml2.core.OneTimeUse;
import org.opensaml.saml.saml2.core.ProxyRestriction;

public class ConditionsImpl extends AbstractSAMLObject implements Conditions {
   private final IndexedXMLObjectChildrenList conditions = new IndexedXMLObjectChildrenList(this);
   private DateTime notBefore;
   private DateTime notOnOrAfter;

   protected ConditionsImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getConditions() {
      return this.conditions;
   }

   public List getAudienceRestrictions() {
      QName conditionQName = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AudienceRestriction", "saml2");
      return this.conditions.subList(conditionQName);
   }

   public OneTimeUse getOneTimeUse() {
      QName conditionQName = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "OneTimeUse", "saml2");
      List list = this.conditions.subList(conditionQName);
      return list != null && list.size() != 0 ? (OneTimeUse)list.get(0) : null;
   }

   public ProxyRestriction getProxyRestriction() {
      QName conditionQName = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "ProxyRestriction", "saml2");
      List list = this.conditions.subList(conditionQName);
      return list != null && list.size() != 0 ? (ProxyRestriction)list.get(0) : null;
   }

   public DateTime getNotBefore() {
      return this.notBefore;
   }

   public void setNotBefore(DateTime newNotBefore) {
      this.notBefore = this.prepareForAssignment(this.notBefore, newNotBefore);
   }

   public DateTime getNotOnOrAfter() {
      return this.notOnOrAfter;
   }

   public void setNotOnOrAfter(DateTime newNotOnOrAfter) {
      this.notOnOrAfter = this.prepareForAssignment(this.notOnOrAfter, newNotOnOrAfter);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(this.conditions);
      return Collections.unmodifiableList(children);
   }
}
