package org.apache.xml.security.keys.keyresolver.implementations;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Set;
import javax.crypto.SecretKey;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.xml.security.c14n.CanonicalizationException;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.keys.content.RetrievalMethod;
import org.apache.xml.security.keys.keyresolver.KeyResolver;
import org.apache.xml.security.keys.keyresolver.KeyResolverException;
import org.apache.xml.security.keys.keyresolver.KeyResolverSpi;
import org.apache.xml.security.keys.storage.StorageResolver;
import org.apache.xml.security.signature.XMLSignatureInput;
import org.apache.xml.security.transforms.Transforms;
import org.apache.xml.security.utils.XMLUtils;
import org.apache.xml.security.utils.resolver.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class RetrievalMethodResolver extends KeyResolverSpi {
   private static final Logger LOG = LoggerFactory.getLogger(RetrievalMethodResolver.class);

   public PublicKey engineLookupAndResolvePublicKey(Element element, String baseURI, StorageResolver storage) {
      if (!XMLUtils.elementIsInSignatureSpace(element, "RetrievalMethod")) {
         return null;
      } else {
         try {
            RetrievalMethod rm = new RetrievalMethod(element, baseURI);
            String type = rm.getType();
            XMLSignatureInput resource = resolveInput(rm, baseURI, this.secureValidation);
            if ("http://www.w3.org/2000/09/xmldsig#rawX509Certificate".equals(type)) {
               X509Certificate cert = getRawCertificate(resource);
               if (cert != null) {
                  return cert.getPublicKey();
               }

               return null;
            }

            Element e = obtainReferenceElement(resource, this.secureValidation);
            if (XMLUtils.elementIsInSignatureSpace(e, "RetrievalMethod")) {
               if (this.secureValidation) {
                  if (LOG.isDebugEnabled()) {
                     String error = "Error: It is forbidden to have one RetrievalMethod point to another with secure validation";
                     LOG.debug(error);
                  }

                  return null;
               }

               RetrievalMethod rm2 = new RetrievalMethod(e, baseURI);
               XMLSignatureInput resource2 = resolveInput(rm2, baseURI, this.secureValidation);
               Element e2 = obtainReferenceElement(resource2, this.secureValidation);
               if (e2 == element) {
                  LOG.debug("Error: Can't have RetrievalMethods pointing to each other");
                  return null;
               }
            }

            return resolveKey(e, baseURI, storage);
         } catch (XMLSecurityException var11) {
            LOG.debug("XMLSecurityException", var11);
         } catch (CertificateException var12) {
            LOG.debug("CertificateException", var12);
         } catch (IOException var13) {
            LOG.debug("IOException", var13);
         } catch (ParserConfigurationException var14) {
            LOG.debug("ParserConfigurationException", var14);
         } catch (SAXException var15) {
            LOG.debug("SAXException", var15);
         }

         return null;
      }
   }

   public X509Certificate engineLookupResolveX509Certificate(Element element, String baseURI, StorageResolver storage) {
      if (!XMLUtils.elementIsInSignatureSpace(element, "RetrievalMethod")) {
         return null;
      } else {
         try {
            RetrievalMethod rm = new RetrievalMethod(element, baseURI);
            String type = rm.getType();
            XMLSignatureInput resource = resolveInput(rm, baseURI, this.secureValidation);
            if ("http://www.w3.org/2000/09/xmldsig#rawX509Certificate".equals(type)) {
               return getRawCertificate(resource);
            }

            Element e = obtainReferenceElement(resource, this.secureValidation);
            if (XMLUtils.elementIsInSignatureSpace(e, "RetrievalMethod")) {
               if (this.secureValidation) {
                  if (LOG.isDebugEnabled()) {
                     String error = "Error: It is forbidden to have one RetrievalMethod point to another with secure validation";
                     LOG.debug(error);
                  }

                  return null;
               }

               RetrievalMethod rm2 = new RetrievalMethod(e, baseURI);
               XMLSignatureInput resource2 = resolveInput(rm2, baseURI, this.secureValidation);
               Element e2 = obtainReferenceElement(resource2, this.secureValidation);
               if (e2 == element) {
                  LOG.debug("Error: Can't have RetrievalMethods pointing to each other");
                  return null;
               }
            }

            return resolveCertificate(e, baseURI, storage);
         } catch (XMLSecurityException var11) {
            LOG.debug("XMLSecurityException", var11);
         } catch (CertificateException var12) {
            LOG.debug("CertificateException", var12);
         } catch (IOException var13) {
            LOG.debug("IOException", var13);
         } catch (ParserConfigurationException var14) {
            LOG.debug("ParserConfigurationException", var14);
         } catch (SAXException var15) {
            LOG.debug("SAXException", var15);
         }

         return null;
      }
   }

   private static X509Certificate resolveCertificate(Element e, String baseURI, StorageResolver storage) throws KeyResolverException {
      if (LOG.isDebugEnabled()) {
         LOG.debug("Now we have a {" + e.getNamespaceURI() + "}" + e.getLocalName() + " Element");
      }

      return e != null ? KeyResolver.getX509Certificate(e, baseURI, storage) : null;
   }

   private static PublicKey resolveKey(Element e, String baseURI, StorageResolver storage) throws KeyResolverException {
      if (LOG.isDebugEnabled()) {
         LOG.debug("Now we have a {" + e.getNamespaceURI() + "}" + e.getLocalName() + " Element");
      }

      return e != null ? KeyResolver.getPublicKey(e, baseURI, storage) : null;
   }

   private static Element obtainReferenceElement(XMLSignatureInput resource, boolean secureValidation) throws CanonicalizationException, ParserConfigurationException, IOException, SAXException, KeyResolverException {
      Element e;
      if (resource.isElement()) {
         e = (Element)resource.getSubNode();
      } else if (resource.isNodeSet()) {
         e = getDocumentElement(resource.getNodeSet());
      } else {
         byte[] inputBytes = resource.getBytes();
         e = getDocFromBytes(inputBytes, secureValidation);
         LOG.debug("we have to parse {} bytes", inputBytes.length);
      }

      return e;
   }

   private static X509Certificate getRawCertificate(XMLSignatureInput resource) throws CanonicalizationException, IOException, CertificateException {
      byte[] inputBytes = resource.getBytes();
      CertificateFactory certFact = CertificateFactory.getInstance("X.509");
      InputStream is = new ByteArrayInputStream(inputBytes);
      Throwable var4 = null;

      X509Certificate var5;
      try {
         var5 = (X509Certificate)certFact.generateCertificate(is);
      } catch (Throwable var14) {
         var4 = var14;
         throw var14;
      } finally {
         if (var4 != null) {
            try {
               is.close();
            } catch (Throwable var13) {
               var4.addSuppressed(var13);
            }
         } else {
            is.close();
         }

      }

      return var5;
   }

   private static XMLSignatureInput resolveInput(RetrievalMethod rm, String baseURI, boolean secureValidation) throws XMLSecurityException {
      Attr uri = rm.getURIAttr();
      Transforms transforms = rm.getTransforms();
      ResourceResolver resRes = ResourceResolver.getInstance(uri, baseURI, secureValidation);
      XMLSignatureInput resource = resRes.resolve(uri, baseURI, secureValidation);
      if (transforms != null) {
         LOG.debug("We have Transforms");
         resource = transforms.performTransforms(resource);
      }

      return resource;
   }

   public SecretKey engineLookupAndResolveSecretKey(Element element, String baseURI, StorageResolver storage) {
      return null;
   }

   private static Element getDocumentElement(Set set) {
      Iterator it = set.iterator();
      Element e = null;

      while(it.hasNext()) {
         Node currentNode = (Node)it.next();
         if (currentNode != null && 1 == currentNode.getNodeType()) {
            e = (Element)currentNode;
            break;
         }
      }

      Node n;
      ArrayList parents;
      for(parents = new ArrayList(); e != null; e = (Element)n) {
         parents.add(e);
         n = e.getParentNode();
         if (n == null || 1 != n.getNodeType()) {
            break;
         }
      }

      ListIterator it2 = parents.listIterator(parents.size() - 1);
      Element ele = null;

      do {
         if (!it2.hasPrevious()) {
            return null;
         }

         ele = (Element)it2.previous();
      } while(!set.contains(ele));

      return ele;
   }
}
