package org.opensaml.xmlsec.signature.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.xmlsec.signature.PGPData;
import org.opensaml.xmlsec.signature.PGPKeyID;
import org.opensaml.xmlsec.signature.PGPKeyPacket;

public class PGPDataImpl extends AbstractXMLObject implements PGPData {
   private PGPKeyID pgpKeyID;
   private PGPKeyPacket pgpKeyPacket;
   private final IndexedXMLObjectChildrenList xmlChildren = new IndexedXMLObjectChildrenList(this);

   protected PGPDataImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public PGPKeyID getPGPKeyID() {
      return this.pgpKeyID;
   }

   public void setPGPKeyID(PGPKeyID newPGPKeyID) {
      this.pgpKeyID = (PGPKeyID)this.prepareForAssignment(this.pgpKeyID, newPGPKeyID);
   }

   public PGPKeyPacket getPGPKeyPacket() {
      return this.pgpKeyPacket;
   }

   public void setPGPKeyPacket(PGPKeyPacket newPGPKeyPacket) {
      this.pgpKeyPacket = (PGPKeyPacket)this.prepareForAssignment(this.pgpKeyPacket, newPGPKeyPacket);
   }

   public List getUnknownXMLObjects() {
      return this.xmlChildren;
   }

   public List getUnknownXMLObjects(QName typeOrName) {
      return this.xmlChildren.subList(typeOrName);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (this.pgpKeyID != null) {
         children.add(this.pgpKeyID);
      }

      if (this.pgpKeyPacket != null) {
         children.add(this.pgpKeyPacket);
      }

      children.addAll(this.xmlChildren);
      return children.size() == 0 ? null : Collections.unmodifiableList(children);
   }
}
