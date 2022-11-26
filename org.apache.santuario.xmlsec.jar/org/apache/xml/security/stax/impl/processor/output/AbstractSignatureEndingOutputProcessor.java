package org.apache.xml.security.stax.impl.processor.output;

import java.io.IOException;
import java.io.OutputStream;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.stream.XMLStreamException;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.AbstractBufferingOutputProcessor;
import org.apache.xml.security.stax.ext.AbstractOutputProcessor;
import org.apache.xml.security.stax.ext.OutputProcessorChain;
import org.apache.xml.security.stax.ext.Transformer;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.ext.XMLSecurityUtils;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.ext.stax.XMLSecStartElement;
import org.apache.xml.security.stax.impl.SignaturePartDef;
import org.apache.xml.security.stax.impl.algorithms.SignatureAlgorithm;
import org.apache.xml.security.stax.impl.algorithms.SignatureAlgorithmFactory;
import org.apache.xml.security.stax.impl.util.IDGenerator;
import org.apache.xml.security.stax.impl.util.SignerOutputStream;
import org.apache.xml.security.stax.securityToken.OutboundSecurityToken;
import org.apache.xml.security.stax.securityToken.SecurityTokenConstants;
import org.apache.xml.security.stax.securityToken.SecurityTokenProvider;
import org.apache.xml.security.utils.UnsyncBufferedOutputStream;
import org.apache.xml.security.utils.XMLUtils;

public abstract class AbstractSignatureEndingOutputProcessor extends AbstractBufferingOutputProcessor {
   private List signaturePartDefList;

   public AbstractSignatureEndingOutputProcessor(AbstractSignatureOutputProcessor signatureOutputProcessor) throws XMLSecurityException {
      this.signaturePartDefList = signatureOutputProcessor.getSignaturePartDefList();
   }

   public void processHeaderEvent(OutputProcessorChain outputProcessorChain) throws XMLStreamException, XMLSecurityException {
      OutputProcessorChain subOutputProcessorChain = outputProcessorChain.createSubChain(this);
      new ArrayList(1);
      String signatureId = null;
      Object attributes;
      if (this.securityProperties.isSignatureGenerateIds()) {
         attributes = new ArrayList(1);
         signatureId = IDGenerator.generateID((String)null);
         ((List)attributes).add(this.createAttribute(XMLSecurityConstants.ATT_NULL_Id, signatureId));
      } else {
         attributes = Collections.emptyList();
      }

      XMLSecStartElement signatureElement = this.createStartElementAndOutputAsEvent(subOutputProcessorChain, XMLSecurityConstants.TAG_dsig_Signature, true, (List)attributes);

      SignatureAlgorithm signatureAlgorithm;
      try {
         signatureAlgorithm = SignatureAlgorithmFactory.getInstance().getSignatureAlgorithm(this.getSecurityProperties().getSignatureAlgorithm());
      } catch (NoSuchAlgorithmException var17) {
         throw new XMLSecurityException(var17);
      } catch (NoSuchProviderException var18) {
         throw new XMLSecurityException(var18);
      }

      String tokenId = (String)outputProcessorChain.getSecurityContext().get("PROP_USE_THIS_TOKEN_ID_FOR_SIGNATURE");
      if (tokenId == null) {
         throw new XMLSecurityException("stax.keyNotFound");
      } else {
         SecurityTokenProvider wrappingSecurityTokenProvider = outputProcessorChain.getSecurityContext().getSecurityTokenProvider(tokenId);
         if (wrappingSecurityTokenProvider == null) {
            throw new XMLSecurityException("stax.keyNotFound");
         } else {
            OutboundSecurityToken wrappingSecurityToken = (OutboundSecurityToken)wrappingSecurityTokenProvider.getSecurityToken();
            if (wrappingSecurityToken == null) {
               throw new XMLSecurityException("stax.keyNotFound");
            } else {
               String sigAlgorithm = this.getSecurityProperties().getSignatureAlgorithm();
               Key key = wrappingSecurityToken.getSecretKey(sigAlgorithm);
               if ("http://www.w3.org/2000/09/xmldsig#hmac-sha1".equals(sigAlgorithm)) {
                  key = XMLSecurityUtils.prepareSecretKey(sigAlgorithm, ((Key)key).getEncoded());
               }

               signatureAlgorithm.engineInitSign((Key)key);
               SignedInfoProcessor signedInfoProcessor = this.newSignedInfoProcessor(signatureAlgorithm, signatureId, signatureElement, subOutputProcessorChain);
               this.createStartElementAndOutputAsEvent(subOutputProcessorChain, XMLSecurityConstants.TAG_dsig_SignedInfo, false, (List)null);
               List attributes = new ArrayList(1);
               String signatureCanonicalizationAlgorithm = this.getSecurityProperties().getSignatureCanonicalizationAlgorithm();
               attributes.add(this.createAttribute(XMLSecurityConstants.ATT_NULL_Algorithm, signatureCanonicalizationAlgorithm));
               this.createStartElementAndOutputAsEvent(subOutputProcessorChain, XMLSecurityConstants.TAG_dsig_CanonicalizationMethod, false, attributes);
               if (this.getSecurityProperties().isAddExcC14NInclusivePrefixes() && "http://www.w3.org/2001/10/xml-exc-c14n#".equals(signatureCanonicalizationAlgorithm)) {
                  attributes = new ArrayList(1);
                  attributes.add(this.createAttribute(XMLSecurityConstants.ATT_NULL_PrefixList, signedInfoProcessor.getInclusiveNamespacePrefixes()));
                  this.createStartElementAndOutputAsEvent(subOutputProcessorChain, XMLSecurityConstants.TAG_c14nExcl_InclusiveNamespaces, true, attributes);
                  this.createEndElementAndOutputAsEvent(subOutputProcessorChain, XMLSecurityConstants.TAG_c14nExcl_InclusiveNamespaces);
               }

               this.createEndElementAndOutputAsEvent(subOutputProcessorChain, XMLSecurityConstants.TAG_dsig_CanonicalizationMethod);
               attributes = new ArrayList(1);
               attributes.add(this.createAttribute(XMLSecurityConstants.ATT_NULL_Algorithm, this.getSecurityProperties().getSignatureAlgorithm()));
               this.createStartElementAndOutputAsEvent(subOutputProcessorChain, XMLSecurityConstants.TAG_dsig_SignatureMethod, false, attributes);
               this.createEndElementAndOutputAsEvent(subOutputProcessorChain, XMLSecurityConstants.TAG_dsig_SignatureMethod);
               Iterator signaturePartDefIterator = this.signaturePartDefList.iterator();

               while(signaturePartDefIterator.hasNext()) {
                  SignaturePartDef signaturePartDef = (SignaturePartDef)signaturePartDefIterator.next();
                  String uriString;
                  if (signaturePartDef.isExternalResource()) {
                     uriString = signaturePartDef.getSigRefId();
                  } else if (signaturePartDef.getSigRefId() != null) {
                     if (signaturePartDef.isGenerateXPointer()) {
                        uriString = "#xpointer(id('" + signaturePartDef.getSigRefId() + "'))";
                     } else {
                        uriString = "#" + signaturePartDef.getSigRefId();
                     }
                  } else {
                     uriString = "";
                  }

                  attributes = new ArrayList(1);
                  attributes.add(this.createAttribute(XMLSecurityConstants.ATT_NULL_URI, uriString));
                  this.createStartElementAndOutputAsEvent(subOutputProcessorChain, XMLSecurityConstants.TAG_dsig_Reference, false, attributes);
                  this.createTransformsStructureForSignature(subOutputProcessorChain, signaturePartDef);
                  attributes = new ArrayList(1);
                  attributes.add(this.createAttribute(XMLSecurityConstants.ATT_NULL_Algorithm, signaturePartDef.getDigestAlgo()));
                  this.createStartElementAndOutputAsEvent(subOutputProcessorChain, XMLSecurityConstants.TAG_dsig_DigestMethod, false, attributes);
                  this.createEndElementAndOutputAsEvent(subOutputProcessorChain, XMLSecurityConstants.TAG_dsig_DigestMethod);
                  this.createStartElementAndOutputAsEvent(subOutputProcessorChain, XMLSecurityConstants.TAG_dsig_DigestValue, false, (List)null);
                  this.createCharactersAndOutputAsEvent(subOutputProcessorChain, signaturePartDef.getDigestValue());
                  this.createEndElementAndOutputAsEvent(subOutputProcessorChain, XMLSecurityConstants.TAG_dsig_DigestValue);
                  this.createEndElementAndOutputAsEvent(subOutputProcessorChain, XMLSecurityConstants.TAG_dsig_Reference);
               }

               this.createEndElementAndOutputAsEvent(subOutputProcessorChain, XMLSecurityConstants.TAG_dsig_SignedInfo);
               subOutputProcessorChain.removeProcessor(signedInfoProcessor);
               this.createStartElementAndOutputAsEvent(subOutputProcessorChain, XMLSecurityConstants.TAG_dsig_SignatureValue, false, (List)null);
               byte[] signatureValue = signedInfoProcessor.getSignatureValue();
               this.createCharactersAndOutputAsEvent(subOutputProcessorChain, XMLUtils.encodeToString(signatureValue));
               this.createEndElementAndOutputAsEvent(subOutputProcessorChain, XMLSecurityConstants.TAG_dsig_SignatureValue);
               if (this.securityProperties.isSignatureGenerateIds()) {
                  attributes = new ArrayList(1);
                  ((List)attributes).add(this.createAttribute(XMLSecurityConstants.ATT_NULL_Id, IDGenerator.generateID((String)null)));
               } else {
                  attributes = Collections.emptyList();
               }

               if (!this.getSecurityProperties().getSignatureKeyIdentifiers().contains(SecurityTokenConstants.KeyIdentifier_NoKeyInfo)) {
                  this.createStartElementAndOutputAsEvent(subOutputProcessorChain, XMLSecurityConstants.TAG_dsig_KeyInfo, false, (List)attributes);
                  this.createKeyInfoStructureForSignature(subOutputProcessorChain, wrappingSecurityToken, this.getSecurityProperties().isUseSingleCert());
                  this.createEndElementAndOutputAsEvent(subOutputProcessorChain, XMLSecurityConstants.TAG_dsig_KeyInfo);
               }

               this.createEndElementAndOutputAsEvent(subOutputProcessorChain, XMLSecurityConstants.TAG_dsig_Signature);
            }
         }
      }
   }

   protected abstract SignedInfoProcessor newSignedInfoProcessor(SignatureAlgorithm var1, String var2, XMLSecStartElement var3, OutputProcessorChain var4) throws XMLSecurityException;

   protected abstract void createTransformsStructureForSignature(OutputProcessorChain var1, SignaturePartDef var2) throws XMLStreamException, XMLSecurityException;

   protected abstract void createKeyInfoStructureForSignature(OutputProcessorChain var1, OutboundSecurityToken var2, boolean var3) throws XMLStreamException, XMLSecurityException;

   protected static class SignedInfoProcessor extends AbstractOutputProcessor {
      private SignerOutputStream signerOutputStream;
      private OutputStream bufferedSignerOutputStream;
      private Transformer transformer;
      private byte[] signatureValue;
      private String inclusiveNamespacePrefixes;
      private SignatureAlgorithm signatureAlgorithm;
      private XMLSecStartElement xmlSecStartElement;
      private String signatureId;

      public SignedInfoProcessor(SignatureAlgorithm signatureAlgorithm, String signatureId, XMLSecStartElement xmlSecStartElement) throws XMLSecurityException {
         this.signatureAlgorithm = signatureAlgorithm;
         this.xmlSecStartElement = xmlSecStartElement;
         this.signatureId = signatureId;
      }

      public void init(OutputProcessorChain outputProcessorChain) throws XMLSecurityException {
         this.signerOutputStream = new SignerOutputStream(this.signatureAlgorithm);
         this.bufferedSignerOutputStream = new UnsyncBufferedOutputStream(this.signerOutputStream);
         String canonicalizationAlgorithm = this.getSecurityProperties().getSignatureCanonicalizationAlgorithm();
         Map transformerProperties = null;
         if (this.getSecurityProperties().isAddExcC14NInclusivePrefixes() && "http://www.w3.org/2001/10/xml-exc-c14n#".equals(canonicalizationAlgorithm)) {
            Set prefixSet = XMLSecurityUtils.getExcC14NInclusiveNamespacePrefixes(this.xmlSecStartElement, false);
            StringBuilder prefixes = new StringBuilder();

            String prefix;
            for(Iterator iterator = prefixSet.iterator(); iterator.hasNext(); prefixes.append(prefix)) {
               prefix = (String)iterator.next();
               if (prefixes.length() != 0) {
                  prefixes.append(" ");
               }
            }

            this.inclusiveNamespacePrefixes = prefixes.toString();
            transformerProperties = new HashMap(2);
            transformerProperties.put("inclusiveNamespacePrefixList", new ArrayList(prefixSet));
         }

         this.transformer = XMLSecurityUtils.getTransformer((Transformer)null, this.bufferedSignerOutputStream, transformerProperties, canonicalizationAlgorithm, XMLSecurityConstants.DIRECTION.OUT);
         super.init(outputProcessorChain);
      }

      public byte[] getSignatureValue() throws XMLSecurityException {
         if (this.signatureValue != null) {
            return this.signatureValue;
         } else {
            try {
               this.transformer.doFinal();
               this.bufferedSignerOutputStream.close();
               this.signatureValue = this.signerOutputStream.sign();
               return this.signatureValue;
            } catch (IOException var2) {
               throw new XMLSecurityException(var2);
            } catch (XMLStreamException var3) {
               throw new XMLSecurityException(var3);
            }
         }
      }

      public String getSignatureId() {
         return this.signatureId;
      }

      public String getInclusiveNamespacePrefixes() {
         return this.inclusiveNamespacePrefixes;
      }

      public void processEvent(XMLSecEvent xmlSecEvent, OutputProcessorChain outputProcessorChain) throws XMLStreamException, XMLSecurityException {
         this.transformer.transform(xmlSecEvent);
         outputProcessorChain.processEvent(xmlSecEvent);
      }
   }
}
