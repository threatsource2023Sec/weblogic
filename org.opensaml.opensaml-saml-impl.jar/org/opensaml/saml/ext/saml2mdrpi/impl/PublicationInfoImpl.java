package org.opensaml.saml.ext.saml2mdrpi.impl;

import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.ext.saml2mdrpi.PublicationInfo;

public class PublicationInfoImpl extends AbstractSAMLObject implements PublicationInfo {
   private XMLObjectChildrenList usagePolicies = new IndexedXMLObjectChildrenList(this);
   private String publisher;
   private DateTime creationInstant;
   private String publicationId;

   protected PublicationInfoImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getPublisher() {
      return this.publisher;
   }

   public void setPublisher(String thePublisher) {
      this.publisher = this.prepareForAssignment(this.publisher, thePublisher);
   }

   public DateTime getCreationInstant() {
      return this.creationInstant;
   }

   public void setCreationInstant(DateTime dateTime) {
      this.creationInstant = this.prepareForAssignment(this.creationInstant, dateTime);
   }

   public String getPublicationId() {
      return this.publicationId;
   }

   public void setPublicationId(String id) {
      this.publicationId = this.prepareForAssignment(this.publicationId, id);
   }

   public List getUsagePolicies() {
      return this.usagePolicies;
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(this.usagePolicies);
      return children;
   }
}
