package org.apache.jcp.xml.dsig.internal.dom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dom.DOMCryptoContext;
import javax.xml.crypto.dsig.keyinfo.PGPData;
import org.apache.xml.security.utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public final class DOMPGPData extends DOMStructure implements PGPData {
   private final byte[] keyId;
   private final byte[] keyPacket;
   private final List externalElements;

   public DOMPGPData(byte[] keyPacket, List other) {
      if (keyPacket == null) {
         throw new NullPointerException("keyPacket cannot be null");
      } else {
         if (other != null && !other.isEmpty()) {
            this.externalElements = Collections.unmodifiableList(new ArrayList(other));
            int i = 0;

            for(int size = this.externalElements.size(); i < size; ++i) {
               if (!(this.externalElements.get(i) instanceof XMLStructure)) {
                  throw new ClassCastException("other[" + i + "] is not a valid PGPData type");
               }
            }
         } else {
            this.externalElements = Collections.emptyList();
         }

         this.keyPacket = (byte[])keyPacket.clone();
         this.checkKeyPacket(keyPacket);
         this.keyId = null;
      }
   }

   public DOMPGPData(byte[] keyId, byte[] keyPacket, List other) {
      if (keyId == null) {
         throw new NullPointerException("keyId cannot be null");
      } else if (keyId.length != 8) {
         throw new IllegalArgumentException("keyId must be 8 bytes long");
      } else {
         if (other != null && !other.isEmpty()) {
            this.externalElements = Collections.unmodifiableList(new ArrayList(other));
            int i = 0;

            for(int size = this.externalElements.size(); i < size; ++i) {
               if (!(this.externalElements.get(i) instanceof XMLStructure)) {
                  throw new ClassCastException("other[" + i + "] is not a valid PGPData type");
               }
            }
         } else {
            this.externalElements = Collections.emptyList();
         }

         this.keyId = (byte[])keyId.clone();
         this.keyPacket = keyPacket == null ? null : (byte[])keyPacket.clone();
         if (keyPacket != null) {
            this.checkKeyPacket(keyPacket);
         }

      }
   }

   public DOMPGPData(Element pdElem) throws MarshalException {
      byte[] pgpKeyId = null;
      byte[] pgpKeyPacket = null;
      List other = new ArrayList();

      for(Node firstChild = pdElem.getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
         if (firstChild.getNodeType() == 1) {
            Element childElem = (Element)firstChild;
            String localName = childElem.getLocalName();
            String namespace = childElem.getNamespaceURI();
            String content;
            if ("PGPKeyID".equals(localName) && "http://www.w3.org/2000/09/xmldsig#".equals(namespace)) {
               content = XMLUtils.getFullTextChildrenFromNode(childElem);
               pgpKeyId = XMLUtils.decode(content);
            } else if ("PGPKeyPacket".equals(localName) && "http://www.w3.org/2000/09/xmldsig#".equals(namespace)) {
               content = XMLUtils.getFullTextChildrenFromNode(childElem);
               pgpKeyPacket = XMLUtils.decode(content);
            } else {
               other.add(new javax.xml.crypto.dom.DOMStructure(childElem));
            }
         }
      }

      this.keyId = pgpKeyId;
      this.keyPacket = pgpKeyPacket;
      this.externalElements = Collections.unmodifiableList(other);
   }

   public byte[] getKeyId() {
      return this.keyId == null ? null : (byte[])this.keyId.clone();
   }

   public byte[] getKeyPacket() {
      return this.keyPacket == null ? null : (byte[])this.keyPacket.clone();
   }

   public List getExternalElements() {
      return this.externalElements;
   }

   public void marshal(Node parent, String dsPrefix, DOMCryptoContext context) throws MarshalException {
      Document ownerDoc = DOMUtils.getOwnerDocument(parent);
      Element pdElem = DOMUtils.createElement(ownerDoc, "PGPData", "http://www.w3.org/2000/09/xmldsig#", dsPrefix);
      Element keyPktElem;
      if (this.keyId != null) {
         keyPktElem = DOMUtils.createElement(ownerDoc, "PGPKeyID", "http://www.w3.org/2000/09/xmldsig#", dsPrefix);
         keyPktElem.appendChild(ownerDoc.createTextNode(XMLUtils.encodeToString(this.keyId)));
         pdElem.appendChild(keyPktElem);
      }

      if (this.keyPacket != null) {
         keyPktElem = DOMUtils.createElement(ownerDoc, "PGPKeyPacket", "http://www.w3.org/2000/09/xmldsig#", dsPrefix);
         keyPktElem.appendChild(ownerDoc.createTextNode(XMLUtils.encodeToString(this.keyPacket)));
         pdElem.appendChild(keyPktElem);
      }

      Iterator var8 = this.externalElements.iterator();

      while(var8.hasNext()) {
         XMLStructure extElem = (XMLStructure)var8.next();
         DOMUtils.appendChild(pdElem, ((javax.xml.crypto.dom.DOMStructure)extElem).getNode());
      }

      parent.appendChild(pdElem);
   }

   private void checkKeyPacket(byte[] keyPacket) {
      if (keyPacket.length < 3) {
         throw new IllegalArgumentException("keypacket must be at least 3 bytes long");
      } else {
         int tag = keyPacket[0];
         if ((tag & 128) != 128) {
            throw new IllegalArgumentException("keypacket tag is invalid: bit 7 is not set");
         } else if ((tag & 64) != 64) {
            throw new IllegalArgumentException("old keypacket tag format is unsupported");
         } else if ((tag & 6) != 6 && (tag & 14) != 14 && (tag & 5) != 5 && (tag & 7) != 7) {
            throw new IllegalArgumentException("keypacket tag is invalid: must be 6, 14, 5, or 7");
         }
      }
   }
}
