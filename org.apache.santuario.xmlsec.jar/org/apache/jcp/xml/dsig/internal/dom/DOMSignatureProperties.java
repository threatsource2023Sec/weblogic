package org.apache.jcp.xml.dsig.internal.dom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dom.DOMCryptoContext;
import javax.xml.crypto.dsig.SignatureProperties;
import javax.xml.crypto.dsig.SignatureProperty;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public final class DOMSignatureProperties extends DOMStructure implements SignatureProperties {
   private final String id;
   private final List properties;

   public DOMSignatureProperties(List properties, String id) {
      if (properties == null) {
         throw new NullPointerException("properties cannot be null");
      } else if (properties.isEmpty()) {
         throw new IllegalArgumentException("properties cannot be empty");
      } else {
         this.properties = Collections.unmodifiableList(new ArrayList(properties));
         int i = 0;

         for(int size = this.properties.size(); i < size; ++i) {
            if (!(this.properties.get(i) instanceof SignatureProperty)) {
               throw new ClassCastException("properties[" + i + "] is not a valid type");
            }
         }

         this.id = id;
      }
   }

   public DOMSignatureProperties(Element propsElem) throws MarshalException {
      Attr attr = propsElem.getAttributeNodeNS((String)null, "Id");
      if (attr != null) {
         this.id = attr.getValue();
         propsElem.setIdAttributeNode(attr, true);
      } else {
         this.id = null;
      }

      List newProperties = new ArrayList();

      for(Node firstChild = propsElem.getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
         if (firstChild.getNodeType() == 1) {
            String name = firstChild.getLocalName();
            String namespace = firstChild.getNamespaceURI();
            if (!"SignatureProperty".equals(name) || !"http://www.w3.org/2000/09/xmldsig#".equals(namespace)) {
               throw new MarshalException("Invalid element name: " + namespace + ":" + name + ", expected SignatureProperty");
            }

            newProperties.add(new DOMSignatureProperty((Element)firstChild));
         }
      }

      if (newProperties.isEmpty()) {
         throw new MarshalException("properties cannot be empty");
      } else {
         this.properties = Collections.unmodifiableList(newProperties);
      }
   }

   public List getProperties() {
      return this.properties;
   }

   public String getId() {
      return this.id;
   }

   public void marshal(Node parent, String dsPrefix, DOMCryptoContext context) throws MarshalException {
      Document ownerDoc = DOMUtils.getOwnerDocument(parent);
      Element propsElem = DOMUtils.createElement(ownerDoc, "SignatureProperties", "http://www.w3.org/2000/09/xmldsig#", dsPrefix);
      DOMUtils.setAttributeID(propsElem, "Id", this.id);
      Iterator var6 = this.properties.iterator();

      while(var6.hasNext()) {
         SignatureProperty property = (SignatureProperty)var6.next();
         ((DOMSignatureProperty)property).marshal(propsElem, dsPrefix, context);
      }

      parent.appendChild(propsElem);
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof SignatureProperties)) {
         return false;
      } else {
         SignatureProperties osp = (SignatureProperties)o;
         boolean idsEqual = this.id == null ? osp.getId() == null : this.id.equals(osp.getId());
         return this.properties.equals(osp.getProperties()) && idsEqual;
      }
   }

   public int hashCode() {
      int result = 17;
      if (this.id != null) {
         result = 31 * result + this.id.hashCode();
      }

      result = 31 * result + this.properties.hashCode();
      return result;
   }
}
