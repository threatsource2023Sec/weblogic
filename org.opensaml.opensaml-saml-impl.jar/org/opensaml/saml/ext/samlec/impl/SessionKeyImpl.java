package org.opensaml.saml.ext.samlec.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.ext.samlec.SessionKey;
import org.opensaml.soap.soap11.ActorBearing;
import org.opensaml.soap.soap11.MustUnderstandBearing;
import org.opensaml.xmlsec.signature.KeyInfo;

public class SessionKeyImpl extends AbstractSAMLObject implements SessionKey {
   private String soap11Actor;
   private XSBooleanValue soap11MustUnderstand;
   private String algorithm;
   private XMLObjectChildrenList encTypes = new XMLObjectChildrenList(this);
   private KeyInfo keyInfo;

   protected SessionKeyImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
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

   public String getAlgorithm() {
      return this.algorithm;
   }

   public void setAlgorithm(String newAlgorithm) {
      this.algorithm = this.prepareForAssignment(this.algorithm, newAlgorithm);
   }

   public List getEncTypes() {
      return this.encTypes;
   }

   public KeyInfo getKeyInfo() {
      return this.keyInfo;
   }

   public void setKeyInfo(KeyInfo newKeyInfo) {
      this.keyInfo = (KeyInfo)this.prepareForAssignment(this.keyInfo, newKeyInfo);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(this.encTypes);
      if (this.keyInfo != null) {
         children.add(this.keyInfo);
      }

      return Collections.unmodifiableList(children);
   }
}
