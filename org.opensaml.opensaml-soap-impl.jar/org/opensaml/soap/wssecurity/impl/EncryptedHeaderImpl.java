package org.opensaml.soap.wssecurity.impl;

import java.util.Collections;
import java.util.List;
import net.shibboleth.utilities.java.support.collection.LazyList;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.soap.soap11.ActorBearing;
import org.opensaml.soap.soap11.MustUnderstandBearing;
import org.opensaml.soap.soap12.RelayBearing;
import org.opensaml.soap.soap12.RoleBearing;
import org.opensaml.soap.wssecurity.EncryptedHeader;
import org.opensaml.soap.wssecurity.IdBearing;
import org.opensaml.xmlsec.encryption.EncryptedData;

public class EncryptedHeaderImpl extends AbstractWSSecurityObject implements EncryptedHeader {
   private EncryptedData encryptedData;
   private String wsuId;
   private XSBooleanValue soap11MustUnderstand;
   private String soap11Actor;
   private XSBooleanValue soap12MustUnderstand;
   private String soap12Role;
   private XSBooleanValue soap12Relay;

   public EncryptedHeaderImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public EncryptedData getEncryptedData() {
      return this.encryptedData;
   }

   public void setEncryptedData(EncryptedData newEncryptedData) {
      this.encryptedData = (EncryptedData)this.prepareForAssignment(this.encryptedData, newEncryptedData);
   }

   public String getWSUId() {
      return this.wsuId;
   }

   public void setWSUId(String newId) {
      String oldId = this.wsuId;
      this.wsuId = this.prepareForAssignment(this.wsuId, newId);
      this.registerOwnID(oldId, this.wsuId);
      this.manageQualifiedAttributeNamespace(IdBearing.WSU_ID_ATTR_NAME, this.wsuId != null);
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

   public Boolean isSOAP12MustUnderstand() {
      return this.soap12MustUnderstand != null ? this.soap12MustUnderstand.getValue() : Boolean.FALSE;
   }

   public XSBooleanValue isSOAP12MustUnderstandXSBoolean() {
      return this.soap12MustUnderstand;
   }

   public void setSOAP12MustUnderstand(Boolean newMustUnderstand) {
      if (newMustUnderstand != null) {
         this.soap12MustUnderstand = (XSBooleanValue)this.prepareForAssignment(this.soap12MustUnderstand, new XSBooleanValue(newMustUnderstand, false));
      } else {
         this.soap12MustUnderstand = (XSBooleanValue)this.prepareForAssignment(this.soap12MustUnderstand, (Object)null);
      }

      this.manageQualifiedAttributeNamespace(SOAP12_MUST_UNDERSTAND_ATTR_NAME, this.soap12MustUnderstand != null);
   }

   public void setSOAP12MustUnderstand(XSBooleanValue newMustUnderstand) {
      this.soap12MustUnderstand = (XSBooleanValue)this.prepareForAssignment(this.soap12MustUnderstand, newMustUnderstand);
      this.manageQualifiedAttributeNamespace(SOAP12_MUST_UNDERSTAND_ATTR_NAME, this.soap12MustUnderstand != null);
   }

   public String getSOAP12Role() {
      return this.soap12Role;
   }

   public void setSOAP12Role(String newRole) {
      this.soap12Role = this.prepareForAssignment(this.soap12Role, newRole);
      this.manageQualifiedAttributeNamespace(RoleBearing.SOAP12_ROLE_ATTR_NAME, this.soap12Role != null);
   }

   public Boolean isSOAP12Relay() {
      return this.soap12Relay != null ? this.soap12Relay.getValue() : Boolean.FALSE;
   }

   public XSBooleanValue isSOAP12RelayXSBoolean() {
      return this.soap12Relay;
   }

   public void setSOAP12Relay(Boolean newRelay) {
      if (newRelay != null) {
         this.soap12Relay = (XSBooleanValue)this.prepareForAssignment(this.soap12Relay, new XSBooleanValue(newRelay, false));
      } else {
         this.soap12Relay = (XSBooleanValue)this.prepareForAssignment(this.soap12Relay, (Object)null);
      }

      this.manageQualifiedAttributeNamespace(RelayBearing.SOAP12_RELAY_ATTR_NAME, this.soap12Relay != null);
   }

   public void setSOAP12Relay(XSBooleanValue newRelay) {
      this.soap12Relay = (XSBooleanValue)this.prepareForAssignment(this.soap12Relay, newRelay);
      this.manageQualifiedAttributeNamespace(RelayBearing.SOAP12_RELAY_ATTR_NAME, this.soap12Relay != null);
   }

   public List getOrderedChildren() {
      LazyList children = new LazyList();
      if (this.encryptedData != null) {
         children.add(this.encryptedData);
      }

      return Collections.unmodifiableList(children);
   }
}
