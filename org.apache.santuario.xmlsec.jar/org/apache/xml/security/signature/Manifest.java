package org.apache.xml.security.signature;

import java.io.IOException;
import java.io.OutputStream;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.xml.security.c14n.CanonicalizationException;
import org.apache.xml.security.c14n.InvalidCanonicalizerException;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.transforms.Transforms;
import org.apache.xml.security.utils.I18n;
import org.apache.xml.security.utils.SignatureElementProxy;
import org.apache.xml.security.utils.XMLUtils;
import org.apache.xml.security.utils.resolver.ResourceResolver;
import org.apache.xml.security.utils.resolver.ResourceResolverSpi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class Manifest extends SignatureElementProxy {
   public static final int MAXIMUM_REFERENCE_COUNT = 30;
   private static final Logger LOG = LoggerFactory.getLogger(Manifest.class);
   private static Integer referenceCount = (Integer)AccessController.doPrivileged(() -> {
      return Integer.parseInt(System.getProperty("org.apache.xml.security.maxReferences", Integer.toString(30)));
   });
   private List references;
   private Element[] referencesEl;
   private List verificationResults;
   private Map resolverProperties;
   private List perManifestResolvers;
   private boolean secureValidation;

   public Manifest(Document doc) {
      super(doc);
      this.addReturnToSelf();
      this.references = new ArrayList();
   }

   public Manifest(Element element, String baseURI) throws XMLSecurityException {
      this(element, baseURI, true);
   }

   public Manifest(Element element, String baseURI, boolean secureValidation) throws XMLSecurityException {
      super(element, baseURI);
      Attr attr = element.getAttributeNodeNS((String)null, "Id");
      if (attr != null) {
         element.setIdAttributeNode(attr, true);
      }

      this.secureValidation = secureValidation;
      this.referencesEl = XMLUtils.selectDsNodes(this.getFirstChild(), "Reference");
      int le = this.referencesEl.length;
      Object[] exArgs;
      if (le == 0) {
         exArgs = new Object[]{"Reference", "Manifest"};
         throw new DOMException((short)4, I18n.translate("xml.WrongContent", exArgs));
      } else if (secureValidation && le > referenceCount) {
         exArgs = new Object[]{le, referenceCount};
         throw new XMLSecurityException("signature.tooManyReferences", exArgs);
      } else {
         this.references = new ArrayList(le);

         for(int i = 0; i < le; ++i) {
            Element refElem = this.referencesEl[i];
            Attr refAttr = refElem.getAttributeNodeNS((String)null, "Id");
            if (refAttr != null) {
               refElem.setIdAttributeNode(refAttr, true);
            }

            this.references.add((Object)null);
         }

      }
   }

   public void addDocument(String baseURI, String referenceURI, Transforms transforms, String digestURI, String referenceId, String referenceType) throws XMLSignatureException {
      Reference ref = new Reference(this.getDocument(), baseURI, referenceURI, this, transforms, digestURI);
      if (referenceId != null) {
         ref.setId(referenceId);
      }

      if (referenceType != null) {
         ref.setType(referenceType);
      }

      this.references.add(ref);
      this.appendSelf(ref);
      this.addReturnToSelf();
   }

   public void generateDigestValues() throws XMLSignatureException, ReferenceNotInitializedException {
      for(int i = 0; i < this.getLength(); ++i) {
         Reference currentRef = (Reference)this.references.get(i);
         currentRef.generateDigestValue();
      }

   }

   public int getLength() {
      return this.references.size();
   }

   public Reference item(int i) throws XMLSecurityException {
      if (this.references.get(i) == null) {
         Reference ref = new Reference(this.referencesEl[i], this.baseURI, this, this.secureValidation);
         this.references.set(i, ref);
      }

      return (Reference)this.references.get(i);
   }

   public void setId(String Id) {
      if (Id != null) {
         this.setLocalIdAttribute("Id", Id);
      }

   }

   public String getId() {
      return this.getLocalAttribute("Id");
   }

   public boolean verifyReferences() throws MissingResourceFailureException, XMLSecurityException {
      return this.verifyReferences(false);
   }

   public boolean verifyReferences(boolean followManifests) throws MissingResourceFailureException, XMLSecurityException {
      if (this.referencesEl == null) {
         this.referencesEl = XMLUtils.selectDsNodes(this.getFirstChild(), "Reference");
      }

      LOG.debug("verify {} References", this.referencesEl.length);
      LOG.debug("I am {} requested to follow nested Manifests", followManifests ? "" : "not");
      if (this.referencesEl.length == 0) {
         throw new XMLSecurityException("empty", new Object[]{"References are empty"});
      } else if (this.secureValidation && this.referencesEl.length > referenceCount) {
         Object[] exArgs = new Object[]{this.referencesEl.length, referenceCount};
         throw new XMLSecurityException("signature.tooManyReferences", exArgs);
      } else {
         this.verificationResults = new ArrayList(this.referencesEl.length);
         boolean verify = true;

         for(int i = 0; i < this.referencesEl.length; ++i) {
            Reference currentRef = new Reference(this.referencesEl[i], this.baseURI, this, this.secureValidation);
            this.references.set(i, currentRef);

            try {
               boolean currentRefVerified = currentRef.verify();
               if (!currentRefVerified) {
                  verify = false;
               }

               LOG.debug("The Reference has Type {}", currentRef.getType());
               List manifestReferences = Collections.emptyList();
               if (verify && followManifests && currentRef.typeIsReferenceToManifest()) {
                  LOG.debug("We have to follow a nested Manifest");

                  try {
                     XMLSignatureInput signedManifestNodes = currentRef.dereferenceURIandPerformTransforms((OutputStream)null);
                     Set nl = signedManifestNodes.getNodeSet();
                     Manifest referencedManifest = null;
                     Iterator nlIterator = nl.iterator();

                     label88:
                     while(true) {
                        Node n;
                        do {
                           do {
                              do {
                                 if (!nlIterator.hasNext()) {
                                    break label88;
                                 }

                                 n = (Node)nlIterator.next();
                              } while(n.getNodeType() != 1);
                           } while(!((Element)n).getNamespaceURI().equals("http://www.w3.org/2000/09/xmldsig#"));
                        } while(!((Element)n).getLocalName().equals("Manifest"));

                        try {
                           referencedManifest = new Manifest((Element)n, signedManifestNodes.getSourceURI(), this.secureValidation);
                           break;
                        } catch (XMLSecurityException var13) {
                           LOG.debug(var13.getMessage(), var13);
                        }
                     }

                     if (referencedManifest == null) {
                        throw new MissingResourceFailureException(currentRef, "empty", new Object[]{"No Manifest found"});
                     }

                     referencedManifest.perManifestResolvers = this.perManifestResolvers;
                     referencedManifest.resolverProperties = this.resolverProperties;
                     boolean referencedManifestValid = referencedManifest.verifyReferences(followManifests);
                     if (!referencedManifestValid) {
                        verify = false;
                        LOG.warn("The nested Manifest was invalid (bad)");
                     } else {
                        LOG.debug("The nested Manifest was valid (good)");
                     }

                     manifestReferences = referencedManifest.getVerificationResults();
                  } catch (IOException var14) {
                     throw new ReferenceNotInitializedException(var14);
                  } catch (ParserConfigurationException var15) {
                     throw new ReferenceNotInitializedException(var15);
                  } catch (SAXException var16) {
                     throw new ReferenceNotInitializedException(var16);
                  }
               }

               this.verificationResults.add(new VerifiedReference(currentRefVerified, currentRef.getURI(), manifestReferences));
            } catch (ReferenceNotInitializedException var17) {
               Object[] exArgs = new Object[]{currentRef.getURI()};
               throw new MissingResourceFailureException(var17, currentRef, "signature.Verification.Reference.NoInput", exArgs);
            }
         }

         return verify;
      }
   }

   public boolean getVerificationResult(int index) throws XMLSecurityException {
      if (index >= 0 && index <= this.getLength() - 1) {
         if (this.verificationResults == null) {
            try {
               this.verifyReferences();
            } catch (Exception var4) {
               throw new XMLSecurityException(var4);
            }
         }

         return ((VerifiedReference)((ArrayList)this.verificationResults).get(index)).isValid();
      } else {
         Object[] exArgs = new Object[]{Integer.toString(index), Integer.toString(this.getLength())};
         Exception e = new IndexOutOfBoundsException(I18n.translate("signature.Verification.IndexOutOfBounds", exArgs));
         throw new XMLSecurityException(e);
      }
   }

   public List getVerificationResults() {
      return this.verificationResults == null ? Collections.emptyList() : Collections.unmodifiableList(this.verificationResults);
   }

   public void addResourceResolver(ResourceResolver resolver) {
      if (resolver != null) {
         if (this.perManifestResolvers == null) {
            this.perManifestResolvers = new ArrayList();
         }

         this.perManifestResolvers.add(resolver);
      }
   }

   public void addResourceResolver(ResourceResolverSpi resolverSpi) {
      if (resolverSpi != null) {
         if (this.perManifestResolvers == null) {
            this.perManifestResolvers = new ArrayList();
         }

         this.perManifestResolvers.add(new ResourceResolver(resolverSpi));
      }
   }

   public List getPerManifestResolvers() {
      return this.perManifestResolvers;
   }

   public Map getResolverProperties() {
      return this.resolverProperties;
   }

   public void setResolverProperty(String key, String value) {
      if (this.resolverProperties == null) {
         this.resolverProperties = new HashMap(10);
      }

      this.resolverProperties.put(key, value);
   }

   public String getResolverProperty(String key) {
      return (String)this.resolverProperties.get(key);
   }

   public byte[] getSignedContentItem(int i) throws XMLSignatureException {
      try {
         return this.getReferencedContentAfterTransformsItem(i).getBytes();
      } catch (IOException var3) {
         throw new XMLSignatureException(var3);
      } catch (CanonicalizationException var4) {
         throw new XMLSignatureException(var4);
      } catch (InvalidCanonicalizerException var5) {
         throw new XMLSignatureException(var5);
      } catch (XMLSecurityException var6) {
         throw new XMLSignatureException(var6);
      }
   }

   public XMLSignatureInput getReferencedContentBeforeTransformsItem(int i) throws XMLSecurityException {
      return this.item(i).getContentsBeforeTransformation();
   }

   public XMLSignatureInput getReferencedContentAfterTransformsItem(int i) throws XMLSecurityException {
      return this.item(i).getContentsAfterTransformation();
   }

   public int getSignedContentLength() {
      return this.getLength();
   }

   public String getBaseLocalName() {
      return "Manifest";
   }

   public boolean isSecureValidation() {
      return this.secureValidation;
   }
}
