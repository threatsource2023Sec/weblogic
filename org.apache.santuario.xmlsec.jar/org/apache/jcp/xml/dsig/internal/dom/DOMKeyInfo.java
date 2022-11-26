package org.apache.jcp.xml.dsig.internal.dom;

import java.security.Provider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dom.DOMCryptoContext;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public final class DOMKeyInfo extends DOMStructure implements KeyInfo {
   private final String id;
   private final List keyInfoTypes;

   public static List getContent(KeyInfo ki) {
      return ki.getContent();
   }

   public DOMKeyInfo(List content, String id) {
      if (content == null) {
         throw new NullPointerException("content cannot be null");
      } else {
         this.keyInfoTypes = Collections.unmodifiableList(new ArrayList(content));
         if (this.keyInfoTypes.isEmpty()) {
            throw new IllegalArgumentException("content cannot be empty");
         } else {
            int i = 0;

            for(int size = this.keyInfoTypes.size(); i < size; ++i) {
               if (!(this.keyInfoTypes.get(i) instanceof XMLStructure)) {
                  throw new ClassCastException("content[" + i + "] is not a valid KeyInfo type");
               }
            }

            this.id = id;
         }
      }
   }

   public DOMKeyInfo(Element kiElem, XMLCryptoContext context, Provider provider) throws MarshalException {
      Attr attr = kiElem.getAttributeNodeNS((String)null, "Id");
      if (attr != null) {
         this.id = attr.getValue();
         kiElem.setIdAttributeNode(attr, true);
      } else {
         this.id = null;
      }

      List content = new ArrayList();
      Node firstChild = kiElem.getFirstChild();
      if (firstChild == null) {
         throw new MarshalException("KeyInfo must contain at least one type");
      } else {
         for(; firstChild != null; firstChild = firstChild.getNextSibling()) {
            if (firstChild.getNodeType() == 1) {
               Element childElem = (Element)firstChild;
               String localName = childElem.getLocalName();
               String namespace = childElem.getNamespaceURI();
               if ("X509Data".equals(localName) && "http://www.w3.org/2000/09/xmldsig#".equals(namespace)) {
                  content.add(new DOMX509Data(childElem));
               } else if ("KeyName".equals(localName) && "http://www.w3.org/2000/09/xmldsig#".equals(namespace)) {
                  content.add(new DOMKeyName(childElem));
               } else if ("KeyValue".equals(localName) && "http://www.w3.org/2000/09/xmldsig#".equals(namespace)) {
                  content.add(DOMKeyValue.unmarshal(childElem));
               } else if ("RetrievalMethod".equals(localName) && "http://www.w3.org/2000/09/xmldsig#".equals(namespace)) {
                  content.add(new DOMRetrievalMethod(childElem, context, provider));
               } else if ("PGPData".equals(localName) && "http://www.w3.org/2000/09/xmldsig#".equals(namespace)) {
                  content.add(new DOMPGPData(childElem));
               } else {
                  content.add(new javax.xml.crypto.dom.DOMStructure(childElem));
               }
            }
         }

         this.keyInfoTypes = Collections.unmodifiableList(content);
      }
   }

   public String getId() {
      return this.id;
   }

   public List getContent() {
      return this.keyInfoTypes;
   }

   public void marshal(XMLStructure parent, XMLCryptoContext context) throws MarshalException {
      if (parent == null) {
         throw new NullPointerException("parent is null");
      } else if (!(parent instanceof javax.xml.crypto.dom.DOMStructure)) {
         throw new ClassCastException("parent must be of type DOMStructure");
      } else {
         Node pNode = ((javax.xml.crypto.dom.DOMStructure)parent).getNode();
         String dsPrefix = DOMUtils.getSignaturePrefix(context);
         Element kiElem = DOMUtils.createElement(DOMUtils.getOwnerDocument(pNode), "KeyInfo", "http://www.w3.org/2000/09/xmldsig#", dsPrefix);
         if (dsPrefix != null && dsPrefix.length() != 0) {
            kiElem.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:" + dsPrefix, "http://www.w3.org/2000/09/xmldsig#");
         } else {
            kiElem.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", "http://www.w3.org/2000/09/xmldsig#");
         }

         Node nextSibling = null;
         if (context instanceof DOMSignContext) {
            nextSibling = ((DOMSignContext)context).getNextSibling();
         }

         this.marshal(pNode, kiElem, nextSibling, dsPrefix, (DOMCryptoContext)context);
      }
   }

   public void marshal(Node parent, String dsPrefix, DOMCryptoContext context) throws MarshalException {
      this.marshal(parent, (Node)null, dsPrefix, context);
   }

   public void marshal(Node parent, Node nextSibling, String dsPrefix, DOMCryptoContext context) throws MarshalException {
      Document ownerDoc = DOMUtils.getOwnerDocument(parent);
      Element kiElem = DOMUtils.createElement(ownerDoc, "KeyInfo", "http://www.w3.org/2000/09/xmldsig#", dsPrefix);
      this.marshal(parent, kiElem, nextSibling, dsPrefix, context);
   }

   private void marshal(Node parent, Element kiElem, Node nextSibling, String dsPrefix, DOMCryptoContext context) throws MarshalException {
      Iterator var6 = this.keyInfoTypes.iterator();

      while(var6.hasNext()) {
         XMLStructure kiType = (XMLStructure)var6.next();
         if (kiType instanceof DOMStructure) {
            ((DOMStructure)kiType).marshal(kiElem, dsPrefix, context);
         } else {
            DOMUtils.appendChild(kiElem, ((javax.xml.crypto.dom.DOMStructure)kiType).getNode());
         }
      }

      DOMUtils.setAttributeID(kiElem, "Id", this.id);
      parent.insertBefore(kiElem, nextSibling);
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof KeyInfo)) {
         return false;
      } else {
         KeyInfo oki = (KeyInfo)o;
         boolean idsEqual = this.id == null ? oki.getId() == null : this.id.equals(oki.getId());
         return this.keyInfoTypes.equals(oki.getContent()) && idsEqual;
      }
   }

   public int hashCode() {
      int result = 17;
      if (this.id != null) {
         result = 31 * result + this.id.hashCode();
      }

      result = 31 * result + this.keyInfoTypes.hashCode();
      return result;
   }
}
