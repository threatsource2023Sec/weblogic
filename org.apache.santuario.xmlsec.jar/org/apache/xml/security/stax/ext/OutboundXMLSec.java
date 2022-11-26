package org.apache.xml.security.stax.ext;

import java.io.OutputStream;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.List;
import javax.crypto.KeyGenerator;
import javax.xml.stream.XMLStreamWriter;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.config.JCEAlgorithmMapper;
import org.apache.xml.security.stax.impl.DocumentContextImpl;
import org.apache.xml.security.stax.impl.OutboundSecurityContextImpl;
import org.apache.xml.security.stax.impl.OutputProcessorChainImpl;
import org.apache.xml.security.stax.impl.XMLSecurityStreamWriter;
import org.apache.xml.security.stax.impl.processor.output.FinalOutputProcessor;
import org.apache.xml.security.stax.impl.processor.output.XMLEncryptOutputProcessor;
import org.apache.xml.security.stax.impl.processor.output.XMLSignatureOutputProcessor;
import org.apache.xml.security.stax.impl.securityToken.GenericOutboundSecurityToken;
import org.apache.xml.security.stax.impl.util.IDGenerator;
import org.apache.xml.security.stax.securityEvent.SecurityEventListener;
import org.apache.xml.security.stax.securityToken.OutboundSecurityToken;
import org.apache.xml.security.stax.securityToken.SecurityTokenConstants;
import org.apache.xml.security.stax.securityToken.SecurityTokenProvider;

public class OutboundXMLSec {
   private final XMLSecurityProperties securityProperties;

   public OutboundXMLSec(XMLSecurityProperties securityProperties) {
      this.securityProperties = securityProperties;
   }

   public XMLStreamWriter processOutMessage(OutputStream outputStream, String encoding) throws XMLSecurityException {
      return this.processOutMessage((Object)outputStream, encoding, (SecurityEventListener)null);
   }

   public XMLStreamWriter processOutMessage(XMLStreamWriter xmlStreamWriter, String encoding) throws XMLSecurityException {
      return this.processOutMessage((Object)xmlStreamWriter, encoding, (SecurityEventListener)null);
   }

   public XMLStreamWriter processOutMessage(OutputStream outputStream, String encoding, SecurityEventListener eventListener) throws XMLSecurityException {
      return this.processOutMessage((Object)outputStream, encoding, eventListener);
   }

   public XMLStreamWriter processOutMessage(XMLStreamWriter xmlStreamWriter, String encoding, SecurityEventListener eventListener) throws XMLSecurityException {
      return this.processOutMessage((Object)xmlStreamWriter, encoding, eventListener);
   }

   private XMLStreamWriter processOutMessage(Object output, String encoding, SecurityEventListener eventListener) throws XMLSecurityException {
      OutboundSecurityContextImpl outboundSecurityContext = new OutboundSecurityContextImpl();
      if (eventListener != null) {
         outboundSecurityContext.addSecurityEventListener(eventListener);
      }

      DocumentContextImpl documentContext = new DocumentContextImpl();
      documentContext.setEncoding(encoding);
      OutputProcessorChainImpl outputProcessorChain = new OutputProcessorChainImpl(outboundSecurityContext, documentContext);
      SecurePart signEntireRequestPart = null;
      SecurePart encryptEntireRequestPart = null;
      Iterator var9 = this.securityProperties.getActions().iterator();

      while(true) {
         while(var9.hasNext()) {
            XMLSecurityConstants.Action action = (XMLSecurityConstants.Action)var9.next();
            List encryptionParts;
            int j;
            SecurePart securePart;
            if (XMLSecurityConstants.SIGNATURE.equals(action)) {
               XMLSignatureOutputProcessor signatureOutputProcessor = new XMLSignatureOutputProcessor();
               this.initializeOutputProcessor(outputProcessorChain, signatureOutputProcessor, action);
               this.configureSignatureKeys(outboundSecurityContext);
               encryptionParts = this.securityProperties.getSignatureSecureParts();

               for(j = 0; j < encryptionParts.size(); ++j) {
                  securePart = (SecurePart)encryptionParts.get(j);
                  if (securePart.getIdToSign() == null && securePart.getName() != null) {
                     outputProcessorChain.getSecurityContext().putAsMap("signatureParts", securePart.getName(), securePart);
                  } else if (securePart.getIdToSign() != null) {
                     outputProcessorChain.getSecurityContext().putAsMap("signatureParts", securePart.getIdToSign(), securePart);
                  } else if (securePart.isSecureEntireRequest()) {
                     signEntireRequestPart = securePart;
                  }
               }
            } else if (XMLSecurityConstants.ENCRYPT.equals(action)) {
               XMLEncryptOutputProcessor encryptOutputProcessor = new XMLEncryptOutputProcessor();
               this.initializeOutputProcessor(outputProcessorChain, encryptOutputProcessor, action);
               this.configureEncryptionKeys(outboundSecurityContext);
               encryptionParts = this.securityProperties.getEncryptionSecureParts();

               for(j = 0; j < encryptionParts.size(); ++j) {
                  securePart = (SecurePart)encryptionParts.get(j);
                  if (securePart.getIdToSign() == null && securePart.getName() != null) {
                     outputProcessorChain.getSecurityContext().putAsMap("encryptionParts", securePart.getName(), securePart);
                  } else if (securePart.getIdToSign() != null) {
                     outputProcessorChain.getSecurityContext().putAsMap("encryptionParts", securePart.getIdToSign(), securePart);
                  } else if (securePart.isSecureEntireRequest()) {
                     encryptEntireRequestPart = securePart;
                  }
               }
            }
         }

         FinalOutputProcessor finalOutputProcessor;
         if (output instanceof OutputStream) {
            finalOutputProcessor = new FinalOutputProcessor((OutputStream)output, encoding);
            this.initializeOutputProcessor(outputProcessorChain, finalOutputProcessor, (XMLSecurityConstants.Action)null);
         } else {
            if (!(output instanceof XMLStreamWriter)) {
               throw new IllegalArgumentException(output + " is not supported as output");
            }

            finalOutputProcessor = new FinalOutputProcessor((XMLStreamWriter)output);
            this.initializeOutputProcessor(outputProcessorChain, finalOutputProcessor, (XMLSecurityConstants.Action)null);
         }

         XMLSecurityStreamWriter streamWriter = new XMLSecurityStreamWriter(outputProcessorChain);
         streamWriter.setSignEntireRequestPart(signEntireRequestPart);
         streamWriter.setEncryptEntireRequestPart(encryptEntireRequestPart);
         return streamWriter;
      }
   }

   private void initializeOutputProcessor(OutputProcessorChainImpl outputProcessorChain, OutputProcessor outputProcessor, XMLSecurityConstants.Action action) throws XMLSecurityException {
      outputProcessor.setXMLSecurityProperties(this.securityProperties);
      outputProcessor.setAction(action);
      outputProcessor.init(outputProcessorChain);
   }

   private void configureSignatureKeys(OutboundSecurityContextImpl outboundSecurityContext) throws XMLSecurityException {
      Key key = this.securityProperties.getSignatureKey();
      X509Certificate[] x509Certificates = this.securityProperties.getSignatureCerts();
      if (key instanceof PrivateKey && (x509Certificates == null || x509Certificates.length == 0) && this.securityProperties.getSignatureVerificationKey() == null) {
         throw new XMLSecurityException("stax.signature.publicKeyOrCertificateMissing");
      } else {
         final String securityTokenid = IDGenerator.generateID("SIG");
         final OutboundSecurityToken securityToken = new GenericOutboundSecurityToken(securityTokenid, SecurityTokenConstants.DefaultToken, key, x509Certificates);
         if (this.securityProperties.getSignatureVerificationKey() instanceof PublicKey) {
            ((GenericOutboundSecurityToken)securityToken).setPublicKey((PublicKey)this.securityProperties.getSignatureVerificationKey());
         }

         SecurityTokenProvider securityTokenProvider = new SecurityTokenProvider() {
            public OutboundSecurityToken getSecurityToken() throws XMLSecurityException {
               return securityToken;
            }

            public String getId() {
               return securityTokenid;
            }
         };
         outboundSecurityContext.registerSecurityTokenProvider(securityTokenid, securityTokenProvider);
         outboundSecurityContext.put("PROP_USE_THIS_TOKEN_ID_FOR_SIGNATURE", securityTokenid);
      }
   }

   private void configureEncryptionKeys(OutboundSecurityContextImpl outboundSecurityContext) throws XMLSecurityException {
      Key transportKey = this.securityProperties.getEncryptionTransportKey();
      X509Certificate transportCert = this.securityProperties.getEncryptionUseThisCertificate();
      X509Certificate[] transportCerts = null;
      if (transportCert != null) {
         transportCerts = new X509Certificate[]{transportCert};
      }

      OutboundSecurityToken transportSecurityToken = new GenericOutboundSecurityToken(IDGenerator.generateID((String)null), SecurityTokenConstants.DefaultToken, transportKey, transportCerts);
      Key key = this.securityProperties.getEncryptionKey();
      final String keyAlgorithm;
      if (key == null) {
         if (transportCert == null && transportKey == null) {
            throw new XMLSecurityException("stax.encryption.encryptionKeyMissing");
         }

         keyAlgorithm = JCEAlgorithmMapper.getJCEKeyAlgorithmFromURI(this.securityProperties.getEncryptionSymAlgorithm());

         KeyGenerator keyGen;
         try {
            keyGen = KeyGenerator.getInstance(keyAlgorithm);
         } catch (NoSuchAlgorithmException var10) {
            throw new XMLSecurityException(var10);
         }

         if (keyAlgorithm.contains("AES")) {
            int keyLength = JCEAlgorithmMapper.getKeyLengthFromURI(this.securityProperties.getEncryptionSymAlgorithm());
            keyGen.init(keyLength);
         }

         key = keyGen.generateKey();
      }

      keyAlgorithm = IDGenerator.generateID((String)null);
      final GenericOutboundSecurityToken securityToken = new GenericOutboundSecurityToken(keyAlgorithm, SecurityTokenConstants.DefaultToken, (Key)key);
      securityToken.setKeyWrappingToken(transportSecurityToken);
      SecurityTokenProvider securityTokenProvider = new SecurityTokenProvider() {
         public OutboundSecurityToken getSecurityToken() throws XMLSecurityException {
            return securityToken;
         }

         public String getId() {
            return keyAlgorithm;
         }
      };
      outboundSecurityContext.registerSecurityTokenProvider(keyAlgorithm, securityTokenProvider);
      outboundSecurityContext.put("PROP_USE_THIS_TOKEN_ID_FOR_ENCRYPTION", keyAlgorithm);
   }
}
