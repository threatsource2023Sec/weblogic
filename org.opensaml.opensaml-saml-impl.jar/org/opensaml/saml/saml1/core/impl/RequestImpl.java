package org.opensaml.saml.saml1.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.saml1.core.AttributeQuery;
import org.opensaml.saml.saml1.core.AuthenticationQuery;
import org.opensaml.saml.saml1.core.AuthorizationDecisionQuery;
import org.opensaml.saml.saml1.core.Query;
import org.opensaml.saml.saml1.core.Request;
import org.opensaml.saml.saml1.core.SubjectQuery;

public class RequestImpl extends RequestAbstractTypeImpl implements Request {
   private Query query;
   private final XMLObjectChildrenList assertionIDReferences = new XMLObjectChildrenList(this);
   private final XMLObjectChildrenList assertionArtifacts = new XMLObjectChildrenList(this);

   protected RequestImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public Query getQuery() {
      return this.query;
   }

   public SubjectQuery getSubjectQuery() {
      return this.query instanceof SubjectQuery ? (SubjectQuery)this.query : null;
   }

   public AttributeQuery getAttributeQuery() {
      return this.query instanceof AttributeQuery ? (AttributeQuery)this.query : null;
   }

   public AuthenticationQuery getAuthenticationQuery() {
      return this.query instanceof AuthenticationQuery ? (AuthenticationQuery)this.query : null;
   }

   public AuthorizationDecisionQuery getAuthorizationDecisionQuery() {
      return this.query instanceof AuthorizationDecisionQuery ? (AuthorizationDecisionQuery)this.query : null;
   }

   public void setQuery(Query q) {
      this.query = (Query)this.prepareForAssignment(this.query, q);
   }

   public List getAssertionIDReferences() {
      return this.assertionIDReferences;
   }

   public List getAssertionArtifacts() {
      return this.assertionArtifacts;
   }

   public List getOrderedChildren() {
      List list = new ArrayList();
      if (super.getOrderedChildren() != null) {
         list.addAll(super.getOrderedChildren());
      }

      if (this.query != null) {
         list.add(this.query);
      }

      if (this.assertionIDReferences.size() != 0) {
         list.addAll(this.assertionIDReferences);
      }

      if (this.assertionArtifacts.size() != 0) {
         list.addAll(this.assertionArtifacts);
      }

      return list.size() == 0 ? null : Collections.unmodifiableList(list);
   }
}
