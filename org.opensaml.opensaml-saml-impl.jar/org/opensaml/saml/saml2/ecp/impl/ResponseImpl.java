package org.opensaml.saml.saml2.ecp.impl;

import java.util.List;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.ecp.Response;
import org.opensaml.soap.soap11.ActorBearing;
import org.opensaml.soap.soap11.MustUnderstandBearing;

public class ResponseImpl extends AbstractSAMLObject implements Response {
   private String soap11Actor;
   private XSBooleanValue soap11MustUnderstand;
   private String acsURL;

   protected ResponseImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getAssertionConsumerServiceURL() {
      return this.acsURL;
   }

   public void setAssertionConsumerServiceURL(String newAssertionConsumerServiceURL) {
      this.acsURL = this.prepareForAssignment(this.acsURL, newAssertionConsumerServiceURL);
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
      return null;
   }
}
