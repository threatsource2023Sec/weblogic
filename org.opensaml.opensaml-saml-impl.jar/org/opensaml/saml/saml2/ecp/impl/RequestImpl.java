package org.opensaml.saml.saml2.ecp.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.IDPList;
import org.opensaml.saml.saml2.core.Issuer;
import org.opensaml.saml.saml2.ecp.Request;
import org.opensaml.soap.soap11.ActorBearing;
import org.opensaml.soap.soap11.MustUnderstandBearing;

public class RequestImpl extends AbstractSAMLObject implements Request {
   private IDPList idpList;
   private Issuer issuer;
   private String providerName;
   private XSBooleanValue isPassive;
   private String soap11Actor;
   private XSBooleanValue soap11MustUnderstand;

   protected RequestImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public IDPList getIDPList() {
      return this.idpList;
   }

   public void setIDPList(IDPList newIDPList) {
      this.idpList = (IDPList)this.prepareForAssignment(this.idpList, newIDPList);
   }

   public Issuer getIssuer() {
      return this.issuer;
   }

   public void setIssuer(Issuer newIssuer) {
      this.issuer = (Issuer)this.prepareForAssignment(this.issuer, newIssuer);
   }

   public String getProviderName() {
      return this.providerName;
   }

   public void setProviderName(String newProviderName) {
      this.providerName = this.prepareForAssignment(this.providerName, newProviderName);
   }

   public Boolean isPassive() {
      return this.isPassive != null ? this.isPassive.getValue() : Boolean.FALSE;
   }

   public XSBooleanValue isPassiveXSBoolean() {
      return this.isPassive;
   }

   public void setPassive(Boolean newIsPassive) {
      if (newIsPassive != null) {
         this.isPassive = (XSBooleanValue)this.prepareForAssignment(this.isPassive, new XSBooleanValue(newIsPassive, false));
      } else {
         this.isPassive = (XSBooleanValue)this.prepareForAssignment(this.isPassive, (Object)null);
      }

   }

   public void setPassive(XSBooleanValue newIsPassive) {
      this.isPassive = (XSBooleanValue)this.prepareForAssignment(this.isPassive, newIsPassive);
   }

   public Boolean isSOAP11MustUnderstand() {
      return this.soap11MustUnderstand != null ? this.soap11MustUnderstand.getValue() : Boolean.FALSE;
   }

   public XSBooleanValue isSOAP11MustUnderstandXSBoolean() {
      return this.soap11MustUnderstand;
   }

   public void setSOAP11MustUnderstand(Boolean newMustUnderstand) {
      if (newMustUnderstand != null) {
         this.soap11MustUnderstand = (XSBooleanValue)this.prepareForAssignment(this.soap11MustUnderstand, new XSBooleanValue(newMustUnderstand, true));
      } else {
         this.soap11MustUnderstand = (XSBooleanValue)this.prepareForAssignment(this.soap11MustUnderstand, (Object)null);
      }

      this.manageQualifiedAttributeNamespace(MustUnderstandBearing.SOAP11_MUST_UNDERSTAND_ATTR_NAME, this.soap11MustUnderstand != null);
   }

   public void setSOAP11MustUnderstand(XSBooleanValue newMustUnderstand) {
      this.soap11MustUnderstand = (XSBooleanValue)this.prepareForAssignment(this.soap11MustUnderstand, newMustUnderstand);
      this.manageQualifiedAttributeNamespace(MustUnderstandBearing.SOAP11_MUST_UNDERSTAND_ATTR_NAME, this.soap11MustUnderstand != null);
   }

   public String getSOAP11Actor() {
      return this.soap11Actor;
   }

   public void setSOAP11Actor(String newActor) {
      this.soap11Actor = this.prepareForAssignment(this.soap11Actor, newActor);
      this.manageQualifiedAttributeNamespace(ActorBearing.SOAP11_ACTOR_ATTR_NAME, this.soap11Actor != null);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (this.issuer != null) {
         children.add(this.issuer);
      }

      if (this.idpList != null) {
         children.add(this.idpList);
      }

      return Collections.unmodifiableList(children);
   }
}
