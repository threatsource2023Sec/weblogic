package org.opensaml.xmlsec.encryption.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.xmlsec.encryption.EncryptionProperties;

public class EncryptionPropertiesImpl extends AbstractXMLObject implements EncryptionProperties {
   private String id;
   private final XMLObjectChildrenList encryptionProperties = new XMLObjectChildrenList(this);

   protected EncryptionPropertiesImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
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

   public List getEncryptionProperties() {
      return this.encryptionProperties;
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(this.encryptionProperties);
      return children.size() == 0 ? null : Collections.unmodifiableList(children);
   }
}
