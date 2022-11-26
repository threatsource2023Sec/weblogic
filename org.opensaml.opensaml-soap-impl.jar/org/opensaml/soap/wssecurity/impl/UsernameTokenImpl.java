package org.opensaml.soap.wssecurity.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.soap.wssecurity.IdBearing;
import org.opensaml.soap.wssecurity.Username;
import org.opensaml.soap.wssecurity.UsernameToken;

public class UsernameTokenImpl extends AbstractWSSecurityObject implements UsernameToken {
   private String id;
   private Username username;
   private AttributeMap unknownAttributes = new AttributeMap(this);
   private IndexedXMLObjectChildrenList unknownChildren = new IndexedXMLObjectChildrenList(this);

   public UsernameTokenImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public Username getUsername() {
      return this.username;
   }

   public void setUsername(Username newUsername) {
      this.username = (Username)this.prepareForAssignment(this.username, newUsername);
   }

   public String getWSUId() {
      return this.id;
   }

   public void setWSUId(String newId) {
      String oldId = this.id;
      this.id = this.prepareForAssignment(this.id, newId);
      this.registerOwnID(oldId, this.id);
      this.manageQualifiedAttributeNamespace(IdBearing.WSU_ID_ATTR_NAME, this.id != null);
   }

   public AttributeMap getUnknownAttributes() {
      return this.unknownAttributes;
   }

   public List getUnknownXMLObjects() {
      return this.unknownChildren;
   }

   public List getUnknownXMLObjects(QName typeOrName) {
      return this.unknownChildren.subList(typeOrName);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (this.username != null) {
         children.add(this.username);
      }

      if (!this.getUnknownXMLObjects().isEmpty()) {
         children.addAll(this.getUnknownXMLObjects());
      }

      return Collections.unmodifiableList(children);
   }
}
