package org.opensaml.soap.wssecurity.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.soap.wssecurity.IdBearing;
import org.opensaml.soap.wssecurity.SecurityTokenReference;
import org.opensaml.soap.wssecurity.UsageBearing;

public class SecurityTokenReferenceImpl extends AbstractWSSecurityObject implements SecurityTokenReference {
   private String id;
   private List usages = new ArrayList();
   private AttributeMap unknownAttributes = new AttributeMap(this);
   private IndexedXMLObjectChildrenList unknownChildren = new IndexedXMLObjectChildrenList(this);

   public SecurityTokenReferenceImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getWSSEUsages() {
      return this.usages;
   }

   public void setWSSEUsages(List newUsages) {
      this.usages = (List)this.prepareForAssignment(this.usages, newUsages);
      this.manageQualifiedAttributeNamespace(UsageBearing.WSSE_USAGE_ATTR_NAME, !this.usages.isEmpty());
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
      List children = new ArrayList();
      if (!this.getUnknownXMLObjects().isEmpty()) {
         children.addAll(this.getUnknownXMLObjects());
      }

      return Collections.unmodifiableList(children);
   }
}
