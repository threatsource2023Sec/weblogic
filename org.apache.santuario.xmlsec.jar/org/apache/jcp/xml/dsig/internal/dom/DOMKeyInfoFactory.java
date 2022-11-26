package org.apache.jcp.xml.dsig.internal.dom;

import java.math.BigInteger;
import java.security.KeyException;
import java.security.PublicKey;
import java.security.interfaces.DSAPublicKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.URIDereferencer;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dom.DOMCryptoContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.KeyName;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import javax.xml.crypto.dsig.keyinfo.PGPData;
import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.keyinfo.X509IssuerSerial;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public final class DOMKeyInfoFactory extends KeyInfoFactory {
   public KeyInfo newKeyInfo(List content) {
      return this.newKeyInfo(content, (String)null);
   }

   public KeyInfo newKeyInfo(List content, String id) {
      return new DOMKeyInfo(content, id);
   }

   public KeyName newKeyName(String name) {
      return new DOMKeyName(name);
   }

   public KeyValue newKeyValue(PublicKey key) throws KeyException {
      String algorithm = key.getAlgorithm();
      if ("DSA".equals(algorithm)) {
         return new DOMKeyValue.DSA((DSAPublicKey)key);
      } else if ("RSA".equals(algorithm)) {
         return new DOMKeyValue.RSA((RSAPublicKey)key);
      } else if ("EC".equals(algorithm)) {
         return new DOMKeyValue.EC((ECPublicKey)key);
      } else {
         throw new KeyException("unsupported key algorithm: " + algorithm);
      }
   }

   public PGPData newPGPData(byte[] keyId) {
      return this.newPGPData(keyId, (byte[])null, (List)null);
   }

   public PGPData newPGPData(byte[] keyId, byte[] keyPacket, List other) {
      return new DOMPGPData(keyId, keyPacket, other);
   }

   public PGPData newPGPData(byte[] keyPacket, List other) {
      return new DOMPGPData(keyPacket, other);
   }

   public RetrievalMethod newRetrievalMethod(String uri) {
      return this.newRetrievalMethod(uri, (String)null, (List)null);
   }

   public RetrievalMethod newRetrievalMethod(String uri, String type, List transforms) {
      if (uri == null) {
         throw new NullPointerException("uri must not be null");
      } else {
         return new DOMRetrievalMethod(uri, type, transforms);
      }
   }

   public X509Data newX509Data(List content) {
      return new DOMX509Data(content);
   }

   public X509IssuerSerial newX509IssuerSerial(String issuerName, BigInteger serialNumber) {
      return new DOMX509IssuerSerial(issuerName, serialNumber);
   }

   public boolean isFeatureSupported(String feature) {
      if (feature == null) {
         throw new NullPointerException();
      } else {
         return false;
      }
   }

   public URIDereferencer getURIDereferencer() {
      return DOMURIDereferencer.INSTANCE;
   }

   public KeyInfo unmarshalKeyInfo(XMLStructure xmlStructure) throws MarshalException {
      if (xmlStructure == null) {
         throw new NullPointerException("xmlStructure cannot be null");
      } else if (!(xmlStructure instanceof javax.xml.crypto.dom.DOMStructure)) {
         throw new ClassCastException("xmlStructure must be of type DOMStructure");
      } else {
         Node node = ((javax.xml.crypto.dom.DOMStructure)xmlStructure).getNode();
         node.normalize();
         Element element = null;
         if (node.getNodeType() == 9) {
            element = ((Document)node).getDocumentElement();
         } else {
            if (node.getNodeType() != 1) {
               throw new MarshalException("xmlStructure does not contain a proper Node");
            }

            element = (Element)node;
         }

         String tag = element.getLocalName();
         String namespace = element.getNamespaceURI();
         if (tag != null && namespace != null) {
            if ("KeyInfo".equals(tag) && "http://www.w3.org/2000/09/xmldsig#".equals(namespace)) {
               return new DOMKeyInfo(element, new UnmarshalContext(), this.getProvider());
            } else {
               throw new MarshalException("invalid KeyInfo tag: " + namespace + ":" + tag);
            }
         } else {
            throw new MarshalException("Document implementation must support DOM Level 2 and be namespace aware");
         }
      }
   }

   private static class UnmarshalContext extends DOMCryptoContext {
      UnmarshalContext() {
      }
   }
}
