package org.apache.jcp.xml.dsig.internal.dom;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Provider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.xml.crypto.Data;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.NodeSetData;
import javax.xml.crypto.URIDereferencer;
import javax.xml.crypto.URIReferenceException;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dom.DOMCryptoContext;
import javax.xml.crypto.dom.DOMURIReference;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;
import javax.xml.parsers.DocumentBuilder;
import org.apache.xml.security.utils.XMLUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public final class DOMRetrievalMethod extends DOMStructure implements RetrievalMethod, DOMURIReference {
   private final List transforms;
   private String uri;
   private String type;
   private Attr here;

   public DOMRetrievalMethod(String uri, String type, List transforms) {
      if (uri == null) {
         throw new NullPointerException("uri cannot be null");
      } else {
         if (transforms != null && !transforms.isEmpty()) {
            this.transforms = Collections.unmodifiableList(new ArrayList(transforms));
            int i = 0;

            for(int size = this.transforms.size(); i < size; ++i) {
               if (!(this.transforms.get(i) instanceof Transform)) {
                  throw new ClassCastException("transforms[" + i + "] is not a valid type");
               }
            }
         } else {
            this.transforms = Collections.emptyList();
         }

         this.uri = uri;
         if (!uri.equals("")) {
            try {
               new URI(uri);
            } catch (URISyntaxException var6) {
               throw new IllegalArgumentException(var6.getMessage());
            }
         }

         this.type = type;
      }
   }

   public DOMRetrievalMethod(Element rmElem, XMLCryptoContext context, Provider provider) throws MarshalException {
      this.uri = DOMUtils.getAttributeValue(rmElem, "URI");
      this.type = DOMUtils.getAttributeValue(rmElem, "Type");
      this.here = rmElem.getAttributeNodeNS((String)null, "URI");
      boolean secVal = Utils.secureValidation(context);
      List newTransforms = new ArrayList();
      Element transformsElem = DOMUtils.getFirstChildElement(rmElem);
      if (transformsElem != null) {
         String localName = transformsElem.getLocalName();
         String namespace = transformsElem.getNamespaceURI();
         if (!"Transforms".equals(localName) || !"http://www.w3.org/2000/09/xmldsig#".equals(namespace)) {
            throw new MarshalException("Invalid element name: " + namespace + ":" + localName + ", expected Transforms");
         }

         for(Element transformElem = DOMUtils.getFirstChildElement(transformsElem, "Transform", "http://www.w3.org/2000/09/xmldsig#"); transformElem != null; transformElem = DOMUtils.getNextSiblingElement(transformElem)) {
            String name = transformElem.getLocalName();
            namespace = transformElem.getNamespaceURI();
            if (!"Transform".equals(name) || !"http://www.w3.org/2000/09/xmldsig#".equals(namespace)) {
               throw new MarshalException("Invalid element name: " + name + ", expected Transform");
            }

            newTransforms.add(new DOMTransform(transformElem, context, provider));
            if (secVal && newTransforms.size() > 5) {
               String error = "A maxiumum of 5 transforms per Reference are allowed with secure validation";
               throw new MarshalException(error);
            }
         }
      }

      if (newTransforms.isEmpty()) {
         this.transforms = Collections.emptyList();
      } else {
         this.transforms = Collections.unmodifiableList(newTransforms);
      }

   }

   public String getURI() {
      return this.uri;
   }

   public String getType() {
      return this.type;
   }

   public List getTransforms() {
      return this.transforms;
   }

   public void marshal(Node parent, String dsPrefix, DOMCryptoContext context) throws MarshalException {
      Document ownerDoc = DOMUtils.getOwnerDocument(parent);
      Element rmElem = DOMUtils.createElement(ownerDoc, "RetrievalMethod", "http://www.w3.org/2000/09/xmldsig#", dsPrefix);
      DOMUtils.setAttribute(rmElem, "URI", this.uri);
      DOMUtils.setAttribute(rmElem, "Type", this.type);
      if (!this.transforms.isEmpty()) {
         Element transformsElem = DOMUtils.createElement(ownerDoc, "Transforms", "http://www.w3.org/2000/09/xmldsig#", dsPrefix);
         rmElem.appendChild(transformsElem);
         Iterator var7 = this.transforms.iterator();

         while(var7.hasNext()) {
            Transform transform = (Transform)var7.next();
            ((DOMTransform)transform).marshal(transformsElem, dsPrefix, context);
         }
      }

      parent.appendChild(rmElem);
      this.here = rmElem.getAttributeNodeNS((String)null, "URI");
   }

   public Node getHere() {
      return this.here;
   }

   public Data dereference(XMLCryptoContext context) throws URIReferenceException {
      if (context == null) {
         throw new NullPointerException("context cannot be null");
      } else {
         URIDereferencer deref = context.getURIDereferencer();
         if (deref == null) {
            deref = DOMURIDereferencer.INSTANCE;
         }

         Data data = deref.dereference(this, context);

         Transform transform;
         try {
            for(Iterator var4 = this.transforms.iterator(); var4.hasNext(); data = ((DOMTransform)transform).transform(data, context)) {
               transform = (Transform)var4.next();
            }
         } catch (Exception var7) {
            throw new URIReferenceException(var7);
         }

         if (data instanceof NodeSetData && Utils.secureValidation(context)) {
            NodeSetData nsd = (NodeSetData)data;
            Iterator i = nsd.iterator();
            if (i.hasNext()) {
               Node root = (Node)i.next();
               if ("RetrievalMethod".equals(root.getLocalName())) {
                  throw new URIReferenceException("It is forbidden to have one RetrievalMethod point to another when secure validation is enabled");
               }
            }
         }

         return data;
      }
   }

   public XMLStructure dereferenceAsXMLStructure(XMLCryptoContext context) throws URIReferenceException {
      DocumentBuilder db = null;
      boolean secVal = Utils.secureValidation(context);
      ApacheData data = (ApacheData)this.dereference(context);

      DOMX509Data var9;
      try {
         InputStream is = new ByteArrayInputStream(data.getXMLSignatureInput().getBytes());
         Throwable var6 = null;

         try {
            db = XMLUtils.createDocumentBuilder(false, secVal);
            Document doc = db.parse(is);
            Element kiElem = doc.getDocumentElement();
            if (kiElem.getLocalName().equals("X509Data") && "http://www.w3.org/2000/09/xmldsig#".equals(kiElem.getNamespaceURI())) {
               var9 = new DOMX509Data(kiElem);
               return var9;
            }

            var9 = null;
         } catch (Throwable var29) {
            var6 = var29;
            throw var29;
         } finally {
            if (var6 != null) {
               try {
                  is.close();
               } catch (Throwable var28) {
                  var6.addSuppressed(var28);
               }
            } else {
               is.close();
            }

         }
      } catch (Exception var31) {
         throw new URIReferenceException(var31);
      } finally {
         if (db != null) {
            XMLUtils.repoolDocumentBuilder(db);
         }

      }

      return var9;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!(obj instanceof RetrievalMethod)) {
         return false;
      } else {
         RetrievalMethod orm = (RetrievalMethod)obj;
         boolean typesEqual = this.type == null ? orm.getType() == null : this.type.equals(orm.getType());
         return this.uri.equals(orm.getURI()) && this.transforms.equals(orm.getTransforms()) && typesEqual;
      }
   }

   public int hashCode() {
      int result = 17;
      if (this.type != null) {
         result = 31 * result + this.type.hashCode();
      }

      result = 31 * result + this.uri.hashCode();
      result = 31 * result + this.transforms.hashCode();
      return result;
   }
}
