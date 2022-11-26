package org.apache.xml.security.signature;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.xml.security.algorithms.SignatureAlgorithm;
import org.apache.xml.security.c14n.CanonicalizationException;
import org.apache.xml.security.c14n.Canonicalizer;
import org.apache.xml.security.c14n.InvalidCanonicalizerException;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.transforms.params.InclusiveNamespaces;
import org.apache.xml.security.utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class SignedInfo extends Manifest {
   private SignatureAlgorithm signatureAlgorithm;
   private byte[] c14nizedBytes;
   private Element c14nMethod;
   private Element signatureMethod;

   public SignedInfo(Document doc) throws XMLSecurityException {
      this(doc, "http://www.w3.org/2000/09/xmldsig#dsa-sha1", "http://www.w3.org/TR/2001/REC-xml-c14n-20010315");
   }

   public SignedInfo(Document doc, String signatureMethodURI, String canonicalizationMethodURI) throws XMLSecurityException {
      this(doc, signatureMethodURI, 0, canonicalizationMethodURI);
   }

   public SignedInfo(Document doc, String signatureMethodURI, int hMACOutputLength, String canonicalizationMethodURI) throws XMLSecurityException {
      super(doc);
      this.c14nMethod = XMLUtils.createElementInSignatureSpace(this.getDocument(), "CanonicalizationMethod");
      this.c14nMethod.setAttributeNS((String)null, "Algorithm", canonicalizationMethodURI);
      this.appendSelf(this.c14nMethod);
      this.addReturnToSelf();
      if (hMACOutputLength > 0) {
         this.signatureAlgorithm = new SignatureAlgorithm(this.getDocument(), signatureMethodURI, hMACOutputLength);
      } else {
         this.signatureAlgorithm = new SignatureAlgorithm(this.getDocument(), signatureMethodURI);
      }

      this.signatureMethod = this.signatureAlgorithm.getElement();
      this.appendSelf(this.signatureMethod);
      this.addReturnToSelf();
   }

   public SignedInfo(Document doc, Element signatureMethodElem, Element canonicalizationMethodElem) throws XMLSecurityException {
      super(doc);
      this.c14nMethod = canonicalizationMethodElem;
      this.appendSelf(this.c14nMethod);
      this.addReturnToSelf();
      this.signatureAlgorithm = new SignatureAlgorithm(signatureMethodElem, (String)null);
      this.signatureMethod = this.signatureAlgorithm.getElement();
      this.appendSelf(this.signatureMethod);
      this.addReturnToSelf();
   }

   public SignedInfo(Element element, String baseURI) throws XMLSecurityException {
      this(element, baseURI, true);
   }

   public SignedInfo(Element element, String baseURI, boolean secureValidation) throws XMLSecurityException {
      super(reparseSignedInfoElem(element, secureValidation), baseURI, secureValidation);
      this.c14nMethod = XMLUtils.getNextElement(element.getFirstChild());
      this.signatureMethod = XMLUtils.getNextElement(this.c14nMethod.getNextSibling());
      this.signatureAlgorithm = new SignatureAlgorithm(this.signatureMethod, this.getBaseURI(), secureValidation);
   }

   private static Element reparseSignedInfoElem(Element element, boolean secureValidation) throws XMLSecurityException {
      Element c14nMethod = XMLUtils.getNextElement(element.getFirstChild());
      String c14nMethodURI = c14nMethod.getAttributeNS((String)null, "Algorithm");
      if (!c14nMethodURI.equals("http://www.w3.org/TR/2001/REC-xml-c14n-20010315") && !c14nMethodURI.equals("http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments") && !c14nMethodURI.equals("http://www.w3.org/2001/10/xml-exc-c14n#") && !c14nMethodURI.equals("http://www.w3.org/2001/10/xml-exc-c14n#WithComments") && !c14nMethodURI.equals("http://www.w3.org/2006/12/xml-c14n11") && !c14nMethodURI.equals("http://www.w3.org/2006/12/xml-c14n11#WithComments")) {
         try {
            Canonicalizer c14nizer = Canonicalizer.getInstance(c14nMethodURI);
            c14nizer.setSecureValidation(secureValidation);
            byte[] c14nizedBytes = c14nizer.canonicalizeSubtree(element);
            DocumentBuilder db = XMLUtils.createDocumentBuilder(false, secureValidation);

            Element var11;
            try {
               InputStream is = new ByteArrayInputStream(c14nizedBytes);
               Throwable var8 = null;

               try {
                  Document newdoc = db.parse(is);
                  Node imported = element.getOwnerDocument().importNode(newdoc.getDocumentElement(), true);
                  element.getParentNode().replaceChild(imported, element);
                  var11 = (Element)imported;
               } catch (Throwable var33) {
                  var8 = var33;
                  throw var33;
               } finally {
                  if (var8 != null) {
                     try {
                        is.close();
                     } catch (Throwable var32) {
                        var8.addSuppressed(var32);
                     }
                  } else {
                     is.close();
                  }

               }
            } finally {
               XMLUtils.repoolDocumentBuilder(db);
            }

            return var11;
         } catch (ParserConfigurationException var36) {
            throw new XMLSecurityException(var36);
         } catch (IOException var37) {
            throw new XMLSecurityException(var37);
         } catch (SAXException var38) {
            throw new XMLSecurityException(var38);
         }
      } else {
         return element;
      }
   }

   public boolean verify() throws MissingResourceFailureException, XMLSecurityException {
      return super.verifyReferences(false);
   }

   public boolean verify(boolean followManifests) throws MissingResourceFailureException, XMLSecurityException {
      return super.verifyReferences(followManifests);
   }

   public byte[] getCanonicalizedOctetStream() throws CanonicalizationException, InvalidCanonicalizerException, XMLSecurityException {
      if (this.c14nizedBytes == null) {
         Canonicalizer c14nizer = Canonicalizer.getInstance(this.getCanonicalizationMethodURI());
         c14nizer.setSecureValidation(this.isSecureValidation());
         String inclusiveNamespaces = this.getInclusiveNamespaces();
         if (inclusiveNamespaces == null) {
            this.c14nizedBytes = c14nizer.canonicalizeSubtree(this.getElement());
         } else {
            this.c14nizedBytes = c14nizer.canonicalizeSubtree(this.getElement(), inclusiveNamespaces);
         }
      }

      return (byte[])this.c14nizedBytes.clone();
   }

   public void signInOctetStream(OutputStream os) throws CanonicalizationException, InvalidCanonicalizerException, XMLSecurityException {
      if (this.c14nizedBytes == null) {
         Canonicalizer c14nizer = Canonicalizer.getInstance(this.getCanonicalizationMethodURI());
         c14nizer.setSecureValidation(this.isSecureValidation());
         c14nizer.setWriter(os);
         String inclusiveNamespaces = this.getInclusiveNamespaces();
         if (inclusiveNamespaces == null) {
            c14nizer.canonicalizeSubtree(this.getElement());
         } else {
            c14nizer.canonicalizeSubtree(this.getElement(), inclusiveNamespaces);
         }
      } else {
         try {
            os.write(this.c14nizedBytes);
         } catch (IOException var4) {
            throw new RuntimeException(var4);
         }
      }

   }

   public String getCanonicalizationMethodURI() {
      return this.c14nMethod.getAttributeNS((String)null, "Algorithm");
   }

   public String getSignatureMethodURI() {
      Element signatureElement = this.getSignatureMethodElement();
      return signatureElement != null ? signatureElement.getAttributeNS((String)null, "Algorithm") : null;
   }

   public Element getSignatureMethodElement() {
      return this.signatureMethod;
   }

   public SecretKey createSecretKey(byte[] secretKeyBytes) {
      return new SecretKeySpec(secretKeyBytes, this.signatureAlgorithm.getJCEAlgorithmString());
   }

   public SignatureAlgorithm getSignatureAlgorithm() {
      return this.signatureAlgorithm;
   }

   public String getBaseLocalName() {
      return "SignedInfo";
   }

   public String getInclusiveNamespaces() {
      String c14nMethodURI = this.getCanonicalizationMethodURI();
      if (!c14nMethodURI.equals("http://www.w3.org/2001/10/xml-exc-c14n#") && !c14nMethodURI.equals("http://www.w3.org/2001/10/xml-exc-c14n#WithComments")) {
         return null;
      } else {
         Element inclusiveElement = XMLUtils.getNextElement(this.c14nMethod.getFirstChild());
         if (inclusiveElement != null) {
            try {
               String inclusiveNamespaces = (new InclusiveNamespaces(inclusiveElement, "http://www.w3.org/2001/10/xml-exc-c14n#")).getInclusiveNamespaces();
               return inclusiveNamespaces;
            } catch (XMLSecurityException var4) {
               return null;
            }
         } else {
            return null;
         }
      }
   }
}
