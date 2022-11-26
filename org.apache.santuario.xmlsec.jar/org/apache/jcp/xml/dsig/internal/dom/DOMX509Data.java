package org.apache.jcp.xml.dsig.internal.dom;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.cert.CRLException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.security.auth.x500.X500Principal;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dom.DOMCryptoContext;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.keyinfo.X509IssuerSerial;
import org.apache.xml.security.utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public final class DOMX509Data extends DOMStructure implements X509Data {
   private final List content;
   private CertificateFactory cf;

   public DOMX509Data(List content) {
      if (content == null) {
         throw new NullPointerException("content cannot be null");
      } else {
         List contentCopy = new ArrayList(content);
         if (contentCopy.isEmpty()) {
            throw new IllegalArgumentException("content cannot be empty");
         } else {
            int i = 0;

            for(int size = contentCopy.size(); i < size; ++i) {
               Object x509Type = contentCopy.get(i);
               if (x509Type instanceof String) {
                  new X500Principal((String)x509Type);
               } else if (!(x509Type instanceof byte[]) && !(x509Type instanceof X509Certificate) && !(x509Type instanceof X509CRL) && !(x509Type instanceof XMLStructure)) {
                  throw new ClassCastException("content[" + i + "] is not a valid X509Data type");
               }
            }

            this.content = Collections.unmodifiableList(contentCopy);
         }
      }
   }

   public DOMX509Data(Element xdElem) throws MarshalException {
      List newContent = new ArrayList();

      for(Node firstChild = xdElem.getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
         if (firstChild.getNodeType() == 1) {
            Element childElem = (Element)firstChild;
            String localName = childElem.getLocalName();
            String namespace = childElem.getNamespaceURI();
            if ("X509Certificate".equals(localName) && "http://www.w3.org/2000/09/xmldsig#".equals(namespace)) {
               newContent.add(this.unmarshalX509Certificate(childElem));
            } else if ("X509IssuerSerial".equals(localName) && "http://www.w3.org/2000/09/xmldsig#".equals(namespace)) {
               newContent.add(new DOMX509IssuerSerial(childElem));
            } else if ("X509SubjectName".equals(localName) && "http://www.w3.org/2000/09/xmldsig#".equals(namespace)) {
               newContent.add(childElem.getFirstChild().getNodeValue());
            } else if ("X509SKI".equals(localName) && "http://www.w3.org/2000/09/xmldsig#".equals(namespace)) {
               String content = XMLUtils.getFullTextChildrenFromNode(childElem);
               newContent.add(XMLUtils.decode(content));
            } else if ("X509CRL".equals(localName) && "http://www.w3.org/2000/09/xmldsig#".equals(namespace)) {
               newContent.add(this.unmarshalX509CRL(childElem));
            } else {
               newContent.add(new javax.xml.crypto.dom.DOMStructure(childElem));
            }
         }
      }

      this.content = Collections.unmodifiableList(newContent);
   }

   public List getContent() {
      return this.content;
   }

   public void marshal(Node parent, String dsPrefix, DOMCryptoContext context) throws MarshalException {
      Document ownerDoc = DOMUtils.getOwnerDocument(parent);
      Element xdElem = DOMUtils.createElement(ownerDoc, "X509Data", "http://www.w3.org/2000/09/xmldsig#", dsPrefix);
      int i = 0;

      for(int size = this.content.size(); i < size; ++i) {
         Object object = this.content.get(i);
         if (object instanceof X509Certificate) {
            this.marshalCert((X509Certificate)object, xdElem, ownerDoc, dsPrefix);
         } else if (object instanceof XMLStructure) {
            if (object instanceof X509IssuerSerial) {
               ((DOMX509IssuerSerial)object).marshal(xdElem, dsPrefix, context);
            } else {
               javax.xml.crypto.dom.DOMStructure domContent = (javax.xml.crypto.dom.DOMStructure)object;
               DOMUtils.appendChild(xdElem, domContent.getNode());
            }
         } else if (object instanceof byte[]) {
            this.marshalSKI((byte[])object, xdElem, ownerDoc, dsPrefix);
         } else if (object instanceof String) {
            this.marshalSubjectName((String)object, xdElem, ownerDoc, dsPrefix);
         } else if (object instanceof X509CRL) {
            this.marshalCRL((X509CRL)object, xdElem, ownerDoc, dsPrefix);
         }
      }

      parent.appendChild(xdElem);
   }

   private void marshalSKI(byte[] skid, Node parent, Document doc, String dsPrefix) {
      Element skidElem = DOMUtils.createElement(doc, "X509SKI", "http://www.w3.org/2000/09/xmldsig#", dsPrefix);
      skidElem.appendChild(doc.createTextNode(XMLUtils.encodeToString(skid)));
      parent.appendChild(skidElem);
   }

   private void marshalSubjectName(String name, Node parent, Document doc, String dsPrefix) {
      Element snElem = DOMUtils.createElement(doc, "X509SubjectName", "http://www.w3.org/2000/09/xmldsig#", dsPrefix);
      snElem.appendChild(doc.createTextNode(name));
      parent.appendChild(snElem);
   }

   private void marshalCert(X509Certificate cert, Node parent, Document doc, String dsPrefix) throws MarshalException {
      Element certElem = DOMUtils.createElement(doc, "X509Certificate", "http://www.w3.org/2000/09/xmldsig#", dsPrefix);

      try {
         certElem.appendChild(doc.createTextNode(XMLUtils.encodeToString(cert.getEncoded())));
      } catch (CertificateEncodingException var7) {
         throw new MarshalException("Error encoding X509Certificate", var7);
      }

      parent.appendChild(certElem);
   }

   private void marshalCRL(X509CRL crl, Node parent, Document doc, String dsPrefix) throws MarshalException {
      Element crlElem = DOMUtils.createElement(doc, "X509CRL", "http://www.w3.org/2000/09/xmldsig#", dsPrefix);

      try {
         crlElem.appendChild(doc.createTextNode(XMLUtils.encodeToString(crl.getEncoded())));
      } catch (CRLException var7) {
         throw new MarshalException("Error encoding X509CRL", var7);
      }

      parent.appendChild(crlElem);
   }

   private X509Certificate unmarshalX509Certificate(Element elem) throws MarshalException {
      try {
         ByteArrayInputStream bs = this.unmarshalBase64Binary(elem);
         Throwable var3 = null;

         X509Certificate var4;
         try {
            var4 = (X509Certificate)this.cf.generateCertificate(bs);
         } catch (Throwable var10) {
            var3 = var10;
            throw var10;
         } finally {
            if (bs != null) {
               $closeResource(var3, bs);
            }

         }

         return var4;
      } catch (CertificateException var12) {
         throw new MarshalException("Cannot create X509Certificate", var12);
      } catch (IOException var13) {
         throw new MarshalException("Error closing stream", var13);
      }
   }

   private X509CRL unmarshalX509CRL(Element elem) throws MarshalException {
      try {
         ByteArrayInputStream bs = this.unmarshalBase64Binary(elem);
         Throwable var3 = null;

         X509CRL var4;
         try {
            var4 = (X509CRL)this.cf.generateCRL(bs);
         } catch (Throwable var10) {
            var3 = var10;
            throw var10;
         } finally {
            if (bs != null) {
               $closeResource(var3, bs);
            }

         }

         return var4;
      } catch (CRLException var12) {
         throw new MarshalException("Cannot create X509CRL", var12);
      } catch (IOException var13) {
         throw new MarshalException("Error closing stream", var13);
      }
   }

   private ByteArrayInputStream unmarshalBase64Binary(Element elem) throws MarshalException {
      try {
         if (this.cf == null) {
            this.cf = CertificateFactory.getInstance("X.509");
         }

         String content = XMLUtils.getFullTextChildrenFromNode(elem);
         return new ByteArrayInputStream(XMLUtils.decode(content));
      } catch (CertificateException var3) {
         throw new MarshalException("Cannot create CertificateFactory", var3);
      }
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof X509Data)) {
         return false;
      } else {
         X509Data oxd = (X509Data)o;
         List ocontent = oxd.getContent();
         int size = this.content.size();
         if (size != ocontent.size()) {
            return false;
         } else {
            for(int i = 0; i < size; ++i) {
               Object x = this.content.get(i);
               Object ox = ocontent.get(i);
               if (x instanceof byte[]) {
                  if (!(ox instanceof byte[]) || !Arrays.equals((byte[])x, (byte[])ox)) {
                     return false;
                  }
               } else if (!x.equals(ox)) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public int hashCode() {
      int result = 17;
      result = 31 * result + this.content.hashCode();
      return result;
   }

   // $FF: synthetic method
   private static void $closeResource(Throwable x0, AutoCloseable x1) {
      if (x0 != null) {
         try {
            x1.close();
         } catch (Throwable var3) {
            x0.addSuppressed(var3);
         }
      } else {
         x1.close();
      }

   }
}
