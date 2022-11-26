package org.opensaml.xmlsec.signature.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xmlsec.signature.DSAKeyValue;
import org.opensaml.xmlsec.signature.ECKeyValue;
import org.opensaml.xmlsec.signature.KeyValue;
import org.opensaml.xmlsec.signature.RSAKeyValue;

public class KeyValueImpl extends AbstractXMLObject implements KeyValue {
   private DSAKeyValue dsaKeyValue;
   private RSAKeyValue rsaKeyValue;
   private ECKeyValue ecKeyValue;
   private XMLObject unknownXMLObject;

   protected KeyValueImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public DSAKeyValue getDSAKeyValue() {
      return this.dsaKeyValue;
   }

   public void setDSAKeyValue(DSAKeyValue newDSAKeyValue) {
      this.dsaKeyValue = (DSAKeyValue)this.prepareForAssignment(this.dsaKeyValue, newDSAKeyValue);
   }

   public RSAKeyValue getRSAKeyValue() {
      return this.rsaKeyValue;
   }

   public void setRSAKeyValue(RSAKeyValue newRSAKeyValue) {
      this.rsaKeyValue = (RSAKeyValue)this.prepareForAssignment(this.rsaKeyValue, newRSAKeyValue);
   }

   public ECKeyValue getECKeyValue() {
      return this.ecKeyValue;
   }

   public void setECKeyValue(ECKeyValue newECKeyValue) {
      this.ecKeyValue = (ECKeyValue)this.prepareForAssignment(this.ecKeyValue, newECKeyValue);
   }

   public XMLObject getUnknownXMLObject() {
      return this.unknownXMLObject;
   }

   public void setUnknownXMLObject(XMLObject newXMLObject) {
      this.unknownXMLObject = this.prepareForAssignment(this.unknownXMLObject, newXMLObject);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (this.dsaKeyValue != null) {
         children.add(this.dsaKeyValue);
      }

      if (this.rsaKeyValue != null) {
         children.add(this.rsaKeyValue);
      }

      if (this.ecKeyValue != null) {
         children.add(this.ecKeyValue);
      }

      if (this.unknownXMLObject != null) {
         children.add(this.unknownXMLObject);
      }

      return children.size() == 0 ? null : Collections.unmodifiableList(children);
   }
}
