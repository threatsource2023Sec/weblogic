package org.opensaml.soap.soap11.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.soap.soap11.Detail;
import org.opensaml.soap.soap11.Fault;
import org.opensaml.soap.soap11.FaultActor;
import org.opensaml.soap.soap11.FaultCode;
import org.opensaml.soap.soap11.FaultString;

public class FaultImpl extends AbstractXMLObject implements Fault {
   private FaultCode faultCode;
   private FaultString message;
   private FaultActor actor;
   private Detail detail;

   protected FaultImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public FaultCode getCode() {
      return this.faultCode;
   }

   public void setCode(FaultCode newFaultCode) {
      this.faultCode = (FaultCode)this.prepareForAssignment(this.faultCode, newFaultCode);
   }

   public FaultString getMessage() {
      return this.message;
   }

   public void setMessage(FaultString newMessage) {
      this.message = (FaultString)this.prepareForAssignment(this.message, newMessage);
   }

   public FaultActor getActor() {
      return this.actor;
   }

   public void setActor(FaultActor newActor) {
      this.actor = (FaultActor)this.prepareForAssignment(this.actor, newActor);
   }

   public Detail getDetail() {
      return this.detail;
   }

   public void setDetail(Detail newDetail) {
      this.detail = (Detail)this.prepareForAssignment(this.detail, newDetail);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.add(this.faultCode);
      children.add(this.message);
      children.add(this.actor);
      children.add(this.detail);
      return Collections.unmodifiableList(children);
   }
}
