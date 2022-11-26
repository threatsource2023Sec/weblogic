package org.opensaml.xmlsec.signature.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xmlsec.signature.ECKeyValue;
import org.opensaml.xmlsec.signature.NamedCurve;
import org.opensaml.xmlsec.signature.PublicKey;

public class ECKeyValueImpl extends AbstractXMLObject implements ECKeyValue {
   private String id;
   private XMLObject ecParams;
   private NamedCurve namedCurve;
   private PublicKey publicKey;

   protected ECKeyValueImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getID() {
      return this.id;
   }

   public void setID(String newID) {
      String oldID = this.id;
      this.id = this.prepareForAssignment(this.id, newID);
      this.registerOwnID(oldID, this.id);
   }

   public XMLObject getECParameters() {
      return this.ecParams;
   }

   public void setECParameters(XMLObject newParams) {
      this.ecParams = this.prepareForAssignment(this.ecParams, newParams);
   }

   public NamedCurve getNamedCurve() {
      return this.namedCurve;
   }

   public void setNamedCurve(NamedCurve newCurve) {
      this.namedCurve = (NamedCurve)this.prepareForAssignment(this.namedCurve, newCurve);
   }

   public PublicKey getPublicKey() {
      return this.publicKey;
   }

   public void setPublicKey(PublicKey newKey) {
      this.publicKey = (PublicKey)this.prepareForAssignment(this.publicKey, newKey);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (this.ecParams != null) {
         children.add(this.ecParams);
      }

      if (this.namedCurve != null) {
         children.add(this.namedCurve);
      }

      if (this.publicKey != null) {
         children.add(this.publicKey);
      }

      return children.size() == 0 ? null : Collections.unmodifiableList(children);
   }
}
