package org.apache.xml.security.stax.impl.processor.output;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.stream.XMLStreamException;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.config.JCEAlgorithmMapper;
import org.apache.xml.security.stax.config.ResourceResolverMapper;
import org.apache.xml.security.stax.ext.AbstractOutputProcessor;
import org.apache.xml.security.stax.ext.OutputProcessorChain;
import org.apache.xml.security.stax.ext.ResourceResolver;
import org.apache.xml.security.stax.ext.SecurePart;
import org.apache.xml.security.stax.ext.Transformer;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.ext.XMLSecurityUtils;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.ext.stax.XMLSecStartElement;
import org.apache.xml.security.stax.impl.SignaturePartDef;
import org.apache.xml.security.stax.impl.transformer.TransformIdentity;
import org.apache.xml.security.stax.impl.util.DigestOutputStream;
import org.apache.xml.security.utils.UnsyncBufferedOutputStream;
import org.apache.xml.security.utils.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractSignatureOutputProcessor extends AbstractOutputProcessor {
   private static final transient Logger LOG = LoggerFactory.getLogger(AbstractSignatureOutputProcessor.class);
   private final List signaturePartDefList = new ArrayList();
   private InternalSignatureOutputProcessor activeInternalSignatureOutputProcessor;

   public AbstractSignatureOutputProcessor() throws XMLSecurityException {
   }

   public List getSignaturePartDefList() {
      return this.signaturePartDefList;
   }

   public abstract void processEvent(XMLSecEvent var1, OutputProcessorChain var2) throws XMLStreamException, XMLSecurityException;

   public void doFinal(OutputProcessorChain outputProcessorChain) throws XMLStreamException, XMLSecurityException {
      this.doFinalInternal(outputProcessorChain);
      super.doFinal(outputProcessorChain);
   }

   protected void doFinalInternal(OutputProcessorChain outputProcessorChain) throws XMLSecurityException, XMLStreamException {
      Map dynamicSecureParts = outputProcessorChain.getSecurityContext().getAsMap("signatureParts");
      if (dynamicSecureParts != null) {
         Iterator securePartsMapIterator = dynamicSecureParts.entrySet().iterator();

         while(securePartsMapIterator.hasNext()) {
            Map.Entry securePartEntry = (Map.Entry)securePartsMapIterator.next();
            SecurePart securePart = (SecurePart)securePartEntry.getValue();
            if (securePart.getExternalReference() != null) {
               this.digestExternalReference(outputProcessorChain, securePart);
            }
         }
      }

      this.verifySignatureParts(outputProcessorChain);
   }

   protected void digestExternalReference(OutputProcessorChain outputProcessorChain, SecurePart securePart) throws XMLSecurityException, XMLStreamException {
      String externalReference = securePart.getExternalReference();
      ResourceResolver resourceResolver = ResourceResolverMapper.getResourceResolver(externalReference, outputProcessorChain.getDocumentContext().getBaseURI());
      String digestAlgo = securePart.getDigestMethod();
      if (digestAlgo == null) {
         digestAlgo = this.getSecurityProperties().getSignatureDigestAlgorithm();
      }

      DigestOutputStream digestOutputStream = this.createMessageDigestOutputStream(digestAlgo);
      InputStream inputStream = resourceResolver.getInputStreamFromExternalReference();
      SignaturePartDef signaturePartDef = new SignaturePartDef();
      signaturePartDef.setSecurePart(securePart);
      signaturePartDef.setSigRefId(externalReference);
      signaturePartDef.setExternalResource(true);
      signaturePartDef.setTransforms(securePart.getTransforms());
      signaturePartDef.setDigestAlgo(digestAlgo);

      try {
         if (securePart.getTransforms() != null) {
            signaturePartDef.setExcludeVisibleC14Nprefixes(true);
            Transformer transformer = this.buildTransformerChain(digestOutputStream, signaturePartDef, (XMLSecStartElement)null);
            transformer.transform(inputStream);
            transformer.doFinal();
         } else {
            XMLSecurityUtils.copy(inputStream, digestOutputStream);
         }

         digestOutputStream.close();
      } catch (IOException var10) {
         throw new XMLSecurityException(var10);
      }

      String calculatedDigest = XMLUtils.encodeToString(digestOutputStream.getDigestValue());
      LOG.debug("Calculated Digest: {}", calculatedDigest);
      signaturePartDef.setDigestValue(calculatedDigest);
      this.getSignaturePartDefList().add(signaturePartDef);
   }

   protected void verifySignatureParts(OutputProcessorChain outputProcessorChain) throws XMLSecurityException {
      List signaturePartDefs = this.getSignaturePartDefList();
      Map dynamicSecureParts = outputProcessorChain.getSecurityContext().getAsMap("signatureParts");
      if (dynamicSecureParts != null) {
         Iterator securePartsMapIterator = dynamicSecureParts.entrySet().iterator();

         label30:
         while(true) {
            SecurePart securePart;
            do {
               if (!securePartsMapIterator.hasNext()) {
                  return;
               }

               Map.Entry securePartEntry = (Map.Entry)securePartsMapIterator.next();
               securePart = (SecurePart)securePartEntry.getValue();
            } while(!securePart.isRequired());

            for(int i = 0; i < signaturePartDefs.size(); ++i) {
               SignaturePartDef signaturePartDef = (SignaturePartDef)signaturePartDefs.get(i);
               if (signaturePartDef.getSecurePart() == securePart) {
                  continue label30;
               }
            }

            throw new XMLSecurityException("stax.signature.securePartNotFound", new Object[]{securePart.getName()});
         }
      }
   }

   protected InternalSignatureOutputProcessor getActiveInternalSignatureOutputProcessor() {
      return this.activeInternalSignatureOutputProcessor;
   }

   protected void setActiveInternalSignatureOutputProcessor(InternalSignatureOutputProcessor activeInternalSignatureOutputProcessor) {
      this.activeInternalSignatureOutputProcessor = activeInternalSignatureOutputProcessor;
   }

   protected DigestOutputStream createMessageDigestOutputStream(String digestAlgorithm) throws XMLSecurityException {
      String jceName = JCEAlgorithmMapper.translateURItoJCEID(digestAlgorithm);
      String jceProvider = JCEAlgorithmMapper.getJCEProviderFromURI(digestAlgorithm);
      if (jceName == null) {
         throw new XMLSecurityException("algorithms.NoSuchMap", new Object[]{digestAlgorithm});
      } else {
         MessageDigest messageDigest;
         try {
            if (jceProvider != null) {
               messageDigest = MessageDigest.getInstance(jceName, jceProvider);
            } else {
               messageDigest = MessageDigest.getInstance(jceName);
            }
         } catch (NoSuchAlgorithmException var6) {
            throw new XMLSecurityException(var6);
         } catch (NoSuchProviderException var7) {
            throw new XMLSecurityException(var7);
         }

         return new DigestOutputStream(messageDigest);
      }
   }

   protected Transformer buildTransformerChain(OutputStream outputStream, SignaturePartDef signaturePartDef, XMLSecStartElement xmlSecStartElement) throws XMLSecurityException {
      String[] transforms = signaturePartDef.getTransforms();
      if (transforms != null && transforms.length != 0) {
         Transformer parentTransformer = null;

         for(int i = transforms.length - 1; i >= 0; --i) {
            String transform = transforms[i];
            Map transformerProperties = null;
            if (this.getSecurityProperties().isAddExcC14NInclusivePrefixes() && "http://www.w3.org/2001/10/xml-exc-c14n#".equals(transform)) {
               Set prefixSet = XMLSecurityUtils.getExcC14NInclusiveNamespacePrefixes(xmlSecStartElement, signaturePartDef.isExcludeVisibleC14Nprefixes());
               StringBuilder prefixes = new StringBuilder();

               String prefix;
               for(Iterator iterator = prefixSet.iterator(); iterator.hasNext(); prefixes.append(prefix)) {
                  prefix = (String)iterator.next();
                  if (prefixes.length() != 0) {
                     prefixes.append(" ");
                  }
               }

               signaturePartDef.setInclusiveNamespacesPrefixes(prefixes.toString());
               List inclusiveNamespacePrefixes = new ArrayList(prefixSet);
               transformerProperties = new HashMap();
               transformerProperties.put("inclusiveNamespacePrefixList", inclusiveNamespacePrefixes);
            }

            if (parentTransformer != null) {
               parentTransformer = XMLSecurityUtils.getTransformer(parentTransformer, (OutputStream)null, transformerProperties, transform, XMLSecurityConstants.DIRECTION.OUT);
            } else {
               parentTransformer = XMLSecurityUtils.getTransformer((Transformer)null, outputStream, transformerProperties, transform, XMLSecurityConstants.DIRECTION.OUT);
            }
         }

         return parentTransformer;
      } else {
         Transformer transformer = new TransformIdentity();
         transformer.setOutputStream(outputStream);
         return transformer;
      }
   }

   public class InternalSignatureOutputProcessor extends AbstractOutputProcessor {
      private SignaturePartDef signaturePartDef;
      private XMLSecStartElement xmlSecStartElement;
      private int elementCounter;
      private OutputStream bufferedDigestOutputStream;
      private DigestOutputStream digestOutputStream;
      private Transformer transformer;

      public InternalSignatureOutputProcessor(SignaturePartDef signaturePartDef, XMLSecStartElement xmlSecStartElement) throws XMLSecurityException {
         this.addBeforeProcessor(InternalSignatureOutputProcessor.class.getName());
         this.signaturePartDef = signaturePartDef;
         this.xmlSecStartElement = xmlSecStartElement;
      }

      public void init(OutputProcessorChain outputProcessorChain) throws XMLSecurityException {
         this.digestOutputStream = AbstractSignatureOutputProcessor.this.createMessageDigestOutputStream(this.signaturePartDef.getDigestAlgo());
         this.bufferedDigestOutputStream = new UnsyncBufferedOutputStream(this.digestOutputStream);
         this.transformer = AbstractSignatureOutputProcessor.this.buildTransformerChain(this.bufferedDigestOutputStream, this.signaturePartDef, this.xmlSecStartElement);
         super.init(outputProcessorChain);
      }

      public void processEvent(XMLSecEvent xmlSecEvent, OutputProcessorChain outputProcessorChain) throws XMLStreamException, XMLSecurityException {
         this.transformer.transform(xmlSecEvent);
         switch (xmlSecEvent.getEventType()) {
            case 1:
               ++this.elementCounter;
               break;
            case 2:
               --this.elementCounter;
               if (this.elementCounter == 0 && xmlSecEvent.asEndElement().getName().equals(this.xmlSecStartElement.getName())) {
                  this.transformer.doFinal();

                  try {
                     this.bufferedDigestOutputStream.close();
                  } catch (IOException var4) {
                     throw new XMLSecurityException(var4);
                  }

                  String calculatedDigest = XMLUtils.encodeToString(this.digestOutputStream.getDigestValue());
                  AbstractSignatureOutputProcessor.LOG.debug("Calculated Digest: {}", calculatedDigest);
                  this.signaturePartDef.setDigestValue(calculatedDigest);
                  outputProcessorChain.removeProcessor(this);
                  AbstractSignatureOutputProcessor.this.setActiveInternalSignatureOutputProcessor((InternalSignatureOutputProcessor)null);
               }
         }

         outputProcessorChain.processEvent(xmlSecEvent);
      }
   }
}
