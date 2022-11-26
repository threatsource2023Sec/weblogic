package org.apache.jcp.xml.dsig.internal.dom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dom.DOMCryptoContext;
import javax.xml.crypto.dsig.SignatureProperty;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public final class DOMSignatureProperty extends DOMStructure implements SignatureProperty {
   private final String id;
   private final String target;
   private final List content;

   public DOMSignatureProperty(List content, String target, String id) {
      if (target == null) {
         throw new NullPointerException("target cannot be null");
      } else if (content == null) {
         throw new NullPointerException("content cannot be null");
      } else if (content.isEmpty()) {
         throw new IllegalArgumentException("content cannot be empty");
      } else {
         this.content = Collections.unmodifiableList(new ArrayList(content));
         int i = 0;

         for(int size = this.content.size(); i < size; ++i) {
            if (!(this.content.get(i) instanceof XMLStructure)) {
               throw new ClassCastException("content[" + i + "] is not a valid type");
            }
         }

         this.target = target;
         this.id = id;
      }
   }

   public DOMSignatureProperty(Element propElem) throws MarshalException {
      this.target = DOMUtils.getAttributeValue(propElem, "Target");
      if (this.target == null) {
         throw new MarshalException("target cannot be null");
      } else {
         Attr attr = propElem.getAttributeNodeNS((String)null, "Id");
         if (attr != null) {
            this.id = attr.getValue();
            propElem.setIdAttributeNode(attr, true);
         } else {
            this.id = null;
         }

         List newContent = new ArrayList();

         for(Node firstChild = propElem.getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
            newContent.add(new javax.xml.crypto.dom.DOMStructure(firstChild));
         }

         if (newContent.isEmpty()) {
            throw new MarshalException("content cannot be empty");
         } else {
            this.content = Collections.unmodifiableList(newContent);
         }
      }
   }

   public List getContent() {
      return this.content;
   }

   public String getId() {
      return this.id;
   }

   public String getTarget() {
      return this.target;
   }

   public void marshal(Node parent, String dsPrefix, DOMCryptoContext context) throws MarshalException {
      Document ownerDoc = DOMUtils.getOwnerDocument(parent);
      Element propElem = DOMUtils.createElement(ownerDoc, "SignatureProperty", "http://www.w3.org/2000/09/xmldsig#", dsPrefix);
      DOMUtils.setAttributeID(propElem, "Id", this.id);
      DOMUtils.setAttribute(propElem, "Target", this.target);
      Iterator var6 = this.content.iterator();

      while(var6.hasNext()) {
         XMLStructure property = (XMLStructure)var6.next();
         DOMUtils.appendChild(propElem, ((javax.xml.crypto.dom.DOMStructure)property).getNode());
      }

      parent.appendChild(propElem);
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof SignatureProperty)) {
         return false;
      } else {
         SignatureProperty osp = (SignatureProperty)o;
         boolean idsEqual = this.id == null ? osp.getId() == null : this.id.equals(osp.getId());
         List ospContent = osp.getContent();
         return this.equalsContent(ospContent) && this.target.equals(osp.getTarget()) && idsEqual;
      }
   }

   public int hashCode() {
      int result = 17;
      if (this.id != null) {
         result = 31 * result + this.id.hashCode();
      }

      result = 31 * result + this.target.hashCode();
      result = 31 * result + this.content.hashCode();
      return result;
   }

   private boolean equalsContent(List otherContent) {
      int osize = otherContent.size();
      if (this.content.size() != osize) {
         return false;
      } else {
         for(int i = 0; i < osize; ++i) {
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
