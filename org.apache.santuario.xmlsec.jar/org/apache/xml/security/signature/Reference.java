package org.apache.xml.security.signature;

import java.io.IOException;
import java.io.OutputStream;
import java.security.AccessController;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.apache.xml.security.algorithms.Algorithm;
import org.apache.xml.security.algorithms.MessageDigestAlgorithm;
import org.apache.xml.security.c14n.CanonicalizationException;
import org.apache.xml.security.c14n.InvalidCanonicalizerException;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.signature.reference.ReferenceData;
import org.apache.xml.security.signature.reference.ReferenceNodeSetData;
import org.apache.xml.security.signature.reference.ReferenceOctetStreamData;
import org.apache.xml.security.signature.reference.ReferenceSubTreeData;
import org.apache.xml.security.transforms.InvalidTransformException;
import org.apache.xml.security.transforms.Transform;
import org.apache.xml.security.transforms.TransformationException;
import org.apache.xml.security.transforms.Transforms;
import org.apache.xml.security.transforms.params.InclusiveNamespaces;
import org.apache.xml.security.utils.DigesterOutputStream;
import org.apache.xml.security.utils.SignatureElementProxy;
import org.apache.xml.security.utils.UnsyncBufferedOutputStream;
import org.apache.xml.security.utils.XMLUtils;
import org.apache.xml.security.utils.resolver.ResourceResolver;
import org.apache.xml.security.utils.resolver.ResourceResolverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

public class Reference extends SignatureElementProxy {
   public static final String OBJECT_URI = "http://www.w3.org/2000/09/xmldsig#Object";
   public static final String MANIFEST_URI = "http://www.w3.org/2000/09/xmldsig#Manifest";
   public static final int MAXIMUM_TRANSFORM_COUNT = 5;
   private boolean secureValidation;
   private static boolean useC14N11 = (Boolean)AccessController.doPrivileged(() -> {
      return Boolean.getBoolean("org.apache.xml.security.useC14N11");
   });
   private static final Logger LOG = LoggerFactory.getLogger(Reference.class);
   private Manifest manifest;
   private XMLSignatureInput transformsOutput;
   private Transforms transforms;
   private Element digestMethodElem;
   private Element digestValueElement;
   private ReferenceData referenceData;

   protected Reference(Document doc, String baseURI, String referenceURI, Manifest manifest, Transforms transforms, String messageDigestAlgorithm) throws XMLSignatureException {
      super(doc);
      this.addReturnToSelf();
      this.baseURI = baseURI;
      this.manifest = manifest;
      this.setURI(referenceURI);
      if (transforms != null) {
         this.transforms = transforms;
         this.appendSelf(transforms);
         this.addReturnToSelf();
      }

      Algorithm digestAlgorithm = new Algorithm(this.getDocument(), messageDigestAlgorithm) {
         public String getBaseNamespace() {
            return "http://www.w3.org/2000/09/xmldsig#";
         }

         public String getBaseLocalName() {
            return "DigestMethod";
         }
      };
      this.digestMethodElem = digestAlgorithm.getElement();
      this.appendSelf(this.digestMethodElem);
      this.addReturnToSelf();
      this.digestValueElement = XMLUtils.createElementInSignatureSpace(this.getDocument(), "DigestValue");
      this.appendSelf(this.digestValueElement);
      this.addReturnToSelf();
   }

   protected Reference(Element element, String baseURI, Manifest manifest) throws XMLSecurityException {
      this(element, baseURI, manifest, true);
   }

   protected Reference(Element element, String baseURI, Manifest manifest, boolean secureValidation) throws XMLSecurityException {
      super(element, baseURI);
      this.secureValidation = secureValidation;
      this.baseURI = baseURI;
      Element el = XMLUtils.getNextElement(element.getFirstChild());
      if (el != null && "Transforms".equals(el.getLocalName()) && "http://www.w3.org/2000/09/xmldsig#".equals(el.getNamespaceURI())) {
         this.transforms = new Transforms(el, this.baseURI);
         this.transforms.setSecureValidation(secureValidation);
         if (secureValidation && this.transforms.getLength() > 5) {
            Object[] exArgs = new Object[]{this.transforms.getLength(), 5};
            throw new XMLSecurityException("signature.tooManyTransforms", exArgs);
         }

         el = XMLUtils.getNextElement(el.getNextSibling());
      }

      this.digestMethodElem = el;
      if (this.digestMethodElem == null) {
         throw new XMLSecurityException("signature.Reference.NoDigestMethod");
      } else {
         this.digestValueElement = XMLUtils.getNextElement(this.digestMethodElem.getNextSibling());
         if (this.digestValueElement == null) {
            throw new XMLSecurityException("signature.Reference.NoDigestValue");
         } else {
            this.manifest = manifest;
         }
      }
   }

   public MessageDigestAlgorithm getMessageDigestAlgorithm() throws XMLSignatureException {
      if (this.digestMethodElem == null) {
         return null;
      } else {
         String uri = this.digestMethodElem.getAttributeNS((String)null, "Algorithm");
         if ("".equals(uri)) {
            return null;
         } else if (this.secureValidation && "http://www.w3.org/2001/04/xmldsig-more#md5".equals(uri)) {
            Object[] exArgs = new Object[]{uri};
            throw new XMLSignatureException("signature.signatureAlgorithm", exArgs);
         } else {
            return MessageDigestAlgorithm.getInstance(this.getDocument(), uri);
         }
      }
   }

   public void setURI(String uri) {
      if (uri != null) {
         this.setLocalAttribute("URI", uri);
      }

   }

   public String getURI() {
      return this.getLocalAttribute("URI");
   }

   public void setId(String id) {
      if (id != null) {
         this.setLocalIdAttribute("Id", id);
      }

   }

   public String getId() {
      return this.getLocalAttribute("Id");
   }

   public void setType(String type) {
      if (type != null) {
         this.setLocalAttribute("Type", type);
      }

   }

   public String getType() {
      return this.getLocalAttribute("Type");
   }

   public boolean typeIsReferenceToObject() {
      return "http://www.w3.org/2000/09/xmldsig#Object".equals(this.getType());
   }

   public boolean typeIsReferenceToManifest() {
      return "http://www.w3.org/2000/09/xmldsig#Manifest".equals(this.getType());
   }

   private void setDigestValueElement(byte[] digestValue) {
      for(Node n = this.digestValueElement.getFirstChild(); n != null; n = n.getNextSibling()) {
         this.digestValueElement.removeChild(n);
      }

      String base64codedValue = XMLUtils.encodeToString(digestValue);
      Text t = this.createText(base64codedValue);
      this.digestValueElement.appendChild(t);
   }

   public void generateDigestValue() throws XMLSignatureException, ReferenceNotInitializedException {
      this.setDigestValueElement(this.calculateDigest(false));
   }

   public XMLSignatureInput getContentsBeforeTransformation() throws ReferenceNotInitializedException {
      try {
         Attr uriAttr = this.getElement().getAttributeNodeNS((String)null, "URI");
         ResourceResolver resolver = ResourceResolver.getInstance(uriAttr, this.baseURI, this.manifest.getPerManifestResolvers(), this.secureValidation);
         resolver.addProperties(this.manifest.getResolverProperties());
         return resolver.resolve(uriAttr, this.baseURI, this.secureValidation);
      } catch (ResourceResolverException var3) {
         throw new ReferenceNotInitializedException(var3);
      }
   }

   private XMLSignatureInput getContentsAfterTransformation(XMLSignatureInput input, OutputStream os) throws XMLSignatureException {
      try {
         Transforms transforms = this.getTransforms();
         XMLSignatureInput output = null;
         if (transforms != null) {
            output = transforms.performTransforms(input, os);
            this.transformsOutput = output;
         } else {
            output = input;
         }

         return output;
      } catch (ResourceResolverException var5) {
         throw new XMLSignatureException(var5);
      } catch (CanonicalizationException var6) {
         throw new XMLSignatureException(var6);
      } catch (InvalidCanonicalizerException var7) {
         throw new XMLSignatureException(var7);
      } catch (TransformationException var8) {
         throw new XMLSignatureException(var8);
      } catch (XMLSecurityException var9) {
         throw new XMLSignatureException(var9);
      }
   }

   public XMLSignatureInput getContentsAfterTransformation() throws XMLSignatureException {
      XMLSignatureInput input = this.getContentsBeforeTransformation();
      this.cacheDereferencedElement(input);
      return this.getContentsAfterTransformation(input, (OutputStream)null);
   }

   public XMLSignatureInput getNodesetBeforeFirstCanonicalization() throws XMLSignatureException {
      try {
         XMLSignatureInput input = this.getContentsBeforeTransformation();
         this.cacheDereferencedElement(input);
         XMLSignatureInput output = input;
         Transforms transforms = this.getTransforms();
         if (transforms != null) {
            for(int i = 0; i < transforms.getLength(); ++i) {
               Transform t = transforms.item(i);
               String uri = t.getURI();
               if (uri.equals("http://www.w3.org/2001/10/xml-exc-c14n#") || uri.equals("http://www.w3.org/2001/10/xml-exc-c14n#WithComments") || uri.equals("http://www.w3.org/TR/2001/REC-xml-c14n-20010315") || uri.equals("http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments") || uri.equals("http://www.w3.org/2006/12/xml-c14n11") || uri.equals("http://www.w3.org/2006/12/xml-c14n11#WithComments")) {
                  break;
               }

               output = t.performTransform(output, (OutputStream)null);
            }

            output.setSourceURI(input.getSourceURI());
         }

         return output;
      } catch (IOException var7) {
         throw new XMLSignatureException(var7);
      } catch (ResourceResolverException var8) {
         throw new XMLSignatureException(var8);
      } catch (CanonicalizationException var9) {
         throw new XMLSignatureException(var9);
      } catch (InvalidCanonicalizerException var10) {
         throw new XMLSignatureException(var10);
      } catch (TransformationException var11) {
         throw new XMLSignatureException(var11);
      } catch (XMLSecurityException var12) {
         throw new XMLSignatureException(var12);
      }
   }

   public String getHTMLRepresentation() throws XMLSignatureException {
      try {
         XMLSignatureInput nodes = this.getNodesetBeforeFirstCanonicalization();
         Transforms transforms = this.getTransforms();
         Transform c14nTransform = null;
         if (transforms != null) {
            for(int i = 0; i < transforms.getLength(); ++i) {
               Transform t = transforms.item(i);
               String uri = t.getURI();
               if (uri.equals("http://www.w3.org/2001/10/xml-exc-c14n#") || uri.equals("http://www.w3.org/2001/10/xml-exc-c14n#WithComments")) {
                  c14nTransform = t;
                  break;
               }
            }
         }

         Set inclusiveNamespaces = new HashSet();
         if (c14nTransform != null && c14nTransform.length("http://www.w3.org/2001/10/xml-exc-c14n#", "InclusiveNamespaces") == 1) {
            InclusiveNamespaces in = new InclusiveNamespaces(XMLUtils.selectNode(c14nTransform.getElement().getFirstChild(), "http://www.w3.org/2001/10/xml-exc-c14n#", "InclusiveNamespaces", 0), this.getBaseURI());
            inclusiveNamespaces = InclusiveNamespaces.prefixStr2Set(in.getInclusiveNamespaces());
         }

         return nodes.getHTMLRepresentation((Set)inclusiveNamespaces);
      } catch (TransformationException var7) {
         throw new XMLSignatureException(var7);
      } catch (InvalidTransformException var8) {
         throw new XMLSignatureException(var8);
      } catch (XMLSecurityException var9) {
         throw new XMLSignatureException(var9);
      }
   }

   public XMLSignatureInput getTransformsOutput() {
      return this.transformsOutput;
   }

   public ReferenceData getReferenceData() {
      return this.referenceData;
   }

   protected XMLSignatureInput dereferenceURIandPerformTransforms(OutputStream os) throws XMLSignatureException {
      try {
         XMLSignatureInput input = this.getContentsBeforeTransformation();
         this.cacheDereferencedElement(input);
         XMLSignatureInput output = this.getContentsAfterTransformation(input, os);
         this.transformsOutput = output;
         return output;
      } catch (XMLSecurityException var4) {
         throw new ReferenceNotInitializedException(var4);
      }
   }

   private void cacheDereferencedElement(XMLSignatureInput input) {
      if (input.isNodeSet()) {
         try {
            final Set s = input.getNodeSet();
            this.referenceData = new ReferenceNodeSetData() {
               public Iterator iterator() {
                  return new Iterator() {
                     Iterator sIterator = s.iterator();

                     public boolean hasNext() {
                        return this.sIterator.hasNext();
                     }

                     public Node next() {
                        return (Node)this.sIterator.next();
                     }

                     public void remove() {
                        throw new UnsupportedOperationException();
                     }
                  };
               }
            };
         } catch (Exception var4) {
            LOG.warn("cannot cache dereferenced data: " + var4);
         }
      } else if (input.isElement()) {
         this.referenceData = new ReferenceSubTreeData(input.getSubNode(), input.isExcludeComments());
      } else if (input.isOctetStream() || input.isByteArray()) {
         try {
            this.referenceData = new ReferenceOctetStreamData(input.getOctetStream(), input.getSourceURI(), input.getMIMEType());
         } catch (IOException var3) {
            LOG.warn("cannot cache dereferenced data: " + var3);
         }
      }

   }

   public Transforms getTransforms() throws XMLSignatureException, InvalidTransformException, TransformationException, XMLSecurityException {
      return this.transforms;
   }

   public byte[] getReferencedBytes() throws ReferenceNotInitializedException, XMLSignatureException {
      try {
         XMLSignatureInput output = this.dereferenceURIandPerformTransforms((OutputStream)null);
         return output.getBytes();
      } catch (IOException var2) {
         throw new ReferenceNotInitializedException(var2);
      } catch (CanonicalizationException var3) {
         throw new ReferenceNotInitializedException(var3);
      }
   }

   private byte[] calculateDigest(boolean validating) throws ReferenceNotInitializedException, XMLSignatureException {
      XMLSignatureInput input = this.getContentsBeforeTransformation();
      if (input.isPreCalculatedDigest()) {
         return this.getPreCalculatedDigest(input);
      } else {
         this.cacheDereferencedElement(input);
         MessageDigestAlgorithm mda = this.getMessageDigestAlgorithm();
         mda.reset();

         try {
            DigesterOutputStream diOs = new DigesterOutputStream(mda);
            Throwable var5 = null;

            byte[] var9;
            try {
               OutputStream os = new UnsyncBufferedOutputStream(diOs);
               Throwable var7 = null;

               try {
                  XMLSignatureInput output = this.getContentsAfterTransformation(input, os);
                  this.transformsOutput = output;
                  if (useC14N11 && !validating && !output.isOutputStreamSet() && !output.isOctetStream()) {
                     if (this.transforms == null) {
                        this.transforms = new Transforms(this.getDocument());
                        this.transforms.setSecureValidation(this.secureValidation);
                        this.getElement().insertBefore(this.transforms.getElement(), this.digestMethodElem);
                     }

                     this.transforms.addTransform("http://www.w3.org/2006/12/xml-c14n11");
                     output.updateOutputStream(os, true);
                  } else {
                     output.updateOutputStream(os);
                  }

                  os.flush();
                  if (output.getOctetStreamReal() != null) {
                     output.getOctetStreamReal().close();
                  }

                  var9 = diOs.getDigestValue();
               } catch (Throwable var24) {
                  var7 = var24;
                  throw var24;
               } finally {
                  $closeResource(var7, os);
               }
            } catch (Throwable var26) {
               var5 = var26;
               throw var26;
            } finally {
               $closeResource(var5, diOs);
            }

            return var9;
         } catch (XMLSecurityException var28) {
            throw new ReferenceNotInitializedException(var28);
         } catch (IOException var29) {
            throw new ReferenceNotInitializedException(var29);
         }
      }
   }

   private byte[] getPreCalculatedDigest(XMLSignatureInput input) throws ReferenceNotInitializedException {
      LOG.debug("Verifying element with pre-calculated digest");
      String preCalculatedDigest = input.getPreCalculatedDigest();
      return XMLUtils.decode(preCalculatedDigest);
   }

   public byte[] getDigestValue() throws XMLSecurityException {
      if (this.digestValueElement == null) {
         Object[] exArgs = new Object[]{"DigestValue", "http://www.w3.org/2000/09/xmldsig#"};
         throw new XMLSecurityException("signature.Verification.NoSignatureElement", exArgs);
      } else {
         String content = XMLUtils.getFullTextChildrenFromNode(this.digestValueElement);
         return XMLUtils.decode(content);
      }
   }

   public boolean verify() throws ReferenceNotInitializedException, XMLSecurityException {
      byte[] elemDig = this.getDigestValue();
      byte[] calcDig = this.calculateDigest(true);
      boolean equal = MessageDigestAlgorithm.isEqual(elemDig, calcDig);
      if (!equal) {
         LOG.warn("Verification failed for URI \"" + this.getURI() + "\"");
         LOG.warn("Expected Digest: " + XMLUtils.encodeToString(elemDig));
         LOG.warn("Actual Digest: " + XMLUtils.encodeToString(calcDig));
      } else {
         LOG.debug("Verification successful for URI \"{}\"", this.getURI());
      }

      return equal;
   }

   public String getBaseLocalName() {
      return "Reference";
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
