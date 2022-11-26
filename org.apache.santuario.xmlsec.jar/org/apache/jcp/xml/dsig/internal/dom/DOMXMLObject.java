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
import javax.xml.crypto.dsig.XMLObject;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public final class DOMXMLObject extends DOMStructure implements XMLObject {
   private final String id;
   private final String mimeType;
   private final String encoding;
   private final List content;
   private Element objectElem;

   public DOMXMLObject(List content, String id, String mimeType, String encoding) {
      if (content != null && !content.isEmpty()) {
         this.content = Collections.unmodifiableList(new ArrayList(content));
         int i = 0;

         for(int size = this.content.size(); i < size; ++i) {
            if (!(this.content.get(i) instanceof XMLStructure)) {
               throw new ClassCastException("content[" + i + "] is not a valid type");
            }
         }
      } else {
         this.content = Collections.emptyList();
      }

      this.id = id;
      this.mimeType = mimeType;
      this.encoding = encoding;
   }

   public DOMXMLObject(Element objElem, XMLCryptoContext context, Provider provider) throws MarshalException {
      this.encoding = DOMUtils.getAttributeValue(objElem, "Encoding");
      Attr attr = objElem.getAttributeNodeNS((String)null, "Id");
      if (attr != null) {
         this.id = attr.getValue();
         objElem.setIdAttributeNode(attr, true);
      } else {
         this.id = null;
      }

      this.mimeType = DOMUtils.getAttributeValue(objElem, "MimeType");
      List newContent = new ArrayList();

      for(Node firstChild = objElem.getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
         if (firstChild.getNodeType() == 1) {
            Element childElem = (Element)firstChild;
            String tag = childElem.getLocalName();
            String namespace = childElem.getNamespaceURI();
            if ("Manifest".equals(tag) && "http://www.w3.org/2000/09/xmldsig#".equals(namespace)) {
               newContent.add(new DOMManifest(childElem, context, provider));
            } else if ("SignatureProperties".equals(tag) && "http://www.w3.org/2000/09/xmldsig#".equals(namespace)) {
               newContent.add(new DOMSignatureProperties(childElem));
            } else if ("X509Data".equals(tag) && "http://www.w3.org/2000/09/xmldsig#".equals(namespace)) {
               newContent.add(new DOMX509Data(childElem));
            } else {
               newContent.add(new javax.xml.crypto.dom.DOMStructure(firstChild));
            }
         } else {
            newContent.add(new javax.xml.crypto.dom.DOMStructure(firstChild));
         }
      }

      NamedNodeMap nnm = objElem.getAttributes();

      for(int idx = 0; idx < nnm.getLength(); ++idx) {
         Node nsDecl = nnm.item(idx);
         if (DOMUtils.isNamespace(nsDecl)) {
            newContent.add(new javax.xml.crypto.dom.DOMStructure(nsDecl));
         }
      }

      if (newContent.isEmpty()) {
         this.content = Collections.emptyList();
      } else {
         this.content = Collections.unmodifiableList(newContent);
      }

      this.objectElem = objElem;
   }

   public List getContent() {
      return this.content;
   }

   public String getId() {
      return this.id;
   }

   public String getMimeType() {
      return this.mimeType;
   }

   public String getEncoding() {
      return this.encoding;
   }

   public void marshal(Node parent, String dsPrefix, DOMCryptoContext context) throws MarshalException {
      Document ownerDoc = DOMUtils.getOwnerDocument(parent);
      Element objElem = this.objectElem != null ? this.objectElem : null;
      if (objElem == null) {
         objElem = DOMUtils.createElement(ownerDoc, "Object", "http://www.w3.org/2000/09/xmldsig#", dsPrefix);
         DOMUtils.setAttributeID(objElem, "Id", this.id);
         DOMUtils.setAttribute(objElem, "MimeType", this.mimeType);
         DOMUtils.setAttribute(objElem, "Encoding", this.encoding);
         Iterator var6 = this.content.iterator();

         while(var6.hasNext()) {
            XMLStructure object = (XMLStructure)var6.next();
            if (object instanceof DOMStructure) {
               ((DOMStructure)object).marshal(objElem, dsPrefix, context);
            } else {
               javax.xml.crypto.dom.DOMStructure domObject = (javax.xml.crypto.dom.DOMStructure)object;
               DOMUtils.appendChild(objElem, domObject.getNode());
            }
         }
      }

      parent.appendChild(objElem);
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof XMLObject)) {
         return false;
      } else {
         XMLObject oxo = (XMLObject)o;
         boolean idsEqual = this.id == null ? oxo.getId() == null : this.id.equals(oxo.getId());
         boolean encodingsEqual = this.encoding == null ? oxo.getEncoding() == null : this.encoding.equals(oxo.getEncoding());
         boolean mimeTypesEqual = this.mimeType == null ? oxo.getMimeType() == null : this.mimeType.equals(oxo.getMimeType());
         return idsEqual && encodingsEqual && mimeTypesEqual && this.equalsContent(oxo.getContent());
      }
   }

   public int hashCode() {
      int result = 17;
      if (this.id != null) {
         result = 31 * result + this.id.hashCode();
      }

      if (this.encoding != null) {
         result = 31 * result + this.encoding.hashCode();
      }

      if (this.mimeType != null) {
         result = 31 * result + this.mimeType.hashCode();
      }

      result = 31 * result + this.content.hashCode();
      return result;
   }

   private boolean equalsContent(List otherContent) {
      if (this.content.size() != otherContent.size()) {
         return false;
      } else {
         int i = 0;

         for(int osize = otherContent.size(); i < osize; ++i) {
            XMLStructure oxs = (XMLStructure)otherContent.get(i);
            XMLStructure xs = (XMLStructure)this.content.get(i);
            if (oxs instanceof javax.xml.crypto.dom.DOMStructure) {
               if (!(xs instanceof javax.xml.crypto.dom.DOMStructure)) {
                  return false;
               }

               Node onode = ((javax.xml.crypto.dom.DOMStructure)oxs).getNode();
               Node node = ((javax.xml.crypto.dom.DOMStructure)xs).getNode();
               if (!DOMUtils.nodesEqual(node, onode)) {
                  return false;
               }
            } else if (!xs.equals(oxs)) {
               return false;
            }
         }

         return true;
      }
   }
}
