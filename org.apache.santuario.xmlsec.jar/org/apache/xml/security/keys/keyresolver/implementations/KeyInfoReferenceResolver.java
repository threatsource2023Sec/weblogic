package org.apache.xml.security.keys.keyresolver.implementations;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import javax.crypto.SecretKey;
import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.xml.security.c14n.CanonicalizationException;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.keys.KeyInfo;
import org.apache.xml.security.keys.content.KeyInfoReference;
import org.apache.xml.security.keys.keyresolver.KeyResolverException;
import org.apache.xml.security.keys.keyresolver.KeyResolverSpi;
import org.apache.xml.security.keys.storage.StorageResolver;
import org.apache.xml.security.signature.XMLSignatureInput;
import org.apache.xml.security.utils.XMLUtils;
import org.apache.xml.security.utils.resolver.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class KeyInfoReferenceResolver extends KeyResolverSpi {
   private static final Logger LOG = LoggerFactory.getLogger(KeyInfoReferenceResolver.class);

   public boolean engineCanResolve(Element element, String baseURI, StorageResolver storage) {
      return XMLUtils.elementIsInSignature11Space(element, "KeyInfoReference");
   }

   public PublicKey engineLookupAndResolvePublicKey(Element element, String baseURI, StorageResolver storage) throws KeyResolverException {
      LOG.debug("Can I resolve {}", element.getTagName());
      if (!this.engineCanResolve(element, baseURI, storage)) {
         return null;
      } else {
         try {
            KeyInfo referent = this.resolveReferentKeyInfo(element, baseURI, storage);
            if (referent != null) {
               return referent.getPublicKey();
            }
         } catch (XMLSecurityException var5) {
            LOG.debug("XMLSecurityException", var5);
         }

         return null;
      }
   }

   public X509Certificate engineLookupResolveX509Certificate(Element element, String baseURI, StorageResolver storage) throws KeyResolverException {
      LOG.debug("Can I resolve {}", element.getTagName());
      if (!this.engineCanResolve(element, baseURI, storage)) {
         return null;
      } else {
         try {
            KeyInfo referent = this.resolveReferentKeyInfo(element, baseURI, storage);
            if (referent != null) {
               return referent.getX509Certificate();
            }
         } catch (XMLSecurityException var5) {
            LOG.debug("XMLSecurityException", var5);
         }

         return null;
      }
   }

   public SecretKey engineLookupAndResolveSecretKey(Element element, String baseURI, StorageResolver storage) throws KeyResolverException {
      LOG.debug("Can I resolve {}", element.getTagName());
      if (!this.engineCanResolve(element, baseURI, storage)) {
         return null;
      } else {
         try {
            KeyInfo referent = this.resolveReferentKeyInfo(element, baseURI, storage);
            if (referent != null) {
               return referent.getSecretKey();
            }
         } catch (XMLSecurityException var5) {
            LOG.debug("XMLSecurityException", var5);
         }

         return null;
      }
   }

   public PrivateKey engineLookupAndResolvePrivateKey(Element element, String baseURI, StorageResolver storage) throws KeyResolverException {
      LOG.debug("Can I resolve " + element.getTagName());
      if (!this.engineCanResolve(element, baseURI, storage)) {
         return null;
      } else {
         try {
            KeyInfo referent = this.resolveReferentKeyInfo(element, baseURI, storage);
            if (referent != null) {
               return referent.getPrivateKey();
            }
         } catch (XMLSecurityException var5) {
            LOG.debug("XMLSecurityException", var5);
         }

         return null;
      }
   }

   private KeyInfo resolveReferentKeyInfo(Element element, String baseURI, StorageResolver storage) throws XMLSecurityException {
      KeyInfoReference reference = new KeyInfoReference(element, baseURI);
      Attr uriAttr = reference.getURIAttr();
      XMLSignatureInput resource = this.resolveInput(uriAttr, baseURI, this.secureValidation);
      Element referentElement = null;

      try {
         referentElement = this.obtainReferenceElement(resource);
      } catch (Exception var9) {
         LOG.debug("XMLSecurityException", var9);
         return null;
      }

      if (referentElement == null) {
         LOG.debug("De-reference of KeyInfoReference URI returned null: {}", uriAttr.getValue());
         return null;
      } else {
         this.validateReference(referentElement);
         KeyInfo referent = new KeyInfo(referentElement, baseURI);
         referent.addStorageResolver(storage);
         return referent;
      }
   }

   private void validateReference(Element referentElement) throws XMLSecurityException {
      if (!XMLUtils.elementIsInSignatureSpace(referentElement, "KeyInfo")) {
         Object[] exArgs = new Object[]{new QName(referentElement.getNamespaceURI(), referentElement.getLocalName())};
         throw new XMLSecurityException("KeyInfoReferenceResolver.InvalidReferentElement.WrongType", exArgs);
      } else {
         KeyInfo referent = new KeyInfo(referentElement, "");
         if (referent.containsKeyInfoReference()) {
            if (this.secureValidation) {
               throw new XMLSecurityException("KeyInfoReferenceResolver.InvalidReferentElement.ReferenceWithSecure");
            } else {
               throw new XMLSecurityException("KeyInfoReferenceResolver.InvalidReferentElement.ReferenceWithoutSecure");
            }
         }
      }
   }

   private XMLSignatureInput resolveInput(Attr uri, String baseURI, boolean secureValidation) throws XMLSecurityException {
      ResourceResolver resRes = ResourceResolver.getInstance(uri, baseURI, secureValidation);
      return resRes.resolve(uri, baseURI, secureValidation);
   }

   private Element obtainReferenceElement(XMLSignatureInput resource) throws CanonicalizationException, ParserConfigurationException, IOException, SAXException, KeyResolverException {
      Element e;
      if (resource.isElement()) {
         e = (Element)resource.getSubNode();
      } else {
         if (resource.isNodeSet()) {
            LOG.debug("De-reference of KeyInfoReference returned an unsupported NodeSet");
            return null;
         }

         byte[] inputBytes = resource.getBytes();
         e = getDocFromBytes(inputBytes, this.secureValidation);
      }

      return e;
   }
}
