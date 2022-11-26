package org.apache.xml.security.stax.impl.processor.input;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.MGF1ParameterSpec;
import java.util.Base64;
import java.util.Deque;
import java.util.Iterator;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.PSource.PSpecified;
import javax.xml.bind.JAXBElement;
import org.apache.xml.security.binding.xmldsig.DigestMethodType;
import org.apache.xml.security.binding.xmldsig.KeyInfoType;
import org.apache.xml.security.binding.xmlenc.CipherValueType;
import org.apache.xml.security.binding.xmlenc.EncryptedKeyType;
import org.apache.xml.security.binding.xmlenc11.MGFType;
import org.apache.xml.security.binding.xop.Include;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.config.JCEAlgorithmMapper;
import org.apache.xml.security.stax.ext.AbstractInputSecurityHeaderHandler;
import org.apache.xml.security.stax.ext.InboundSecurityContext;
import org.apache.xml.security.stax.ext.InputProcessorChain;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.ext.XMLSecurityProperties;
import org.apache.xml.security.stax.ext.XMLSecurityUtils;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.impl.securityToken.AbstractInboundSecurityToken;
import org.apache.xml.security.stax.impl.util.IDGenerator;
import org.apache.xml.security.stax.securityEvent.AlgorithmSuiteSecurityEvent;
import org.apache.xml.security.stax.securityEvent.EncryptedKeyTokenSecurityEvent;
import org.apache.xml.security.stax.securityToken.InboundSecurityToken;
import org.apache.xml.security.stax.securityToken.SecurityToken;
import org.apache.xml.security.stax.securityToken.SecurityTokenConstants;
import org.apache.xml.security.stax.securityToken.SecurityTokenFactory;
import org.apache.xml.security.stax.securityToken.SecurityTokenProvider;
import org.apache.xml.security.utils.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XMLEncryptedKeyInputHandler extends AbstractInputSecurityHeaderHandler {
   private static final transient Logger LOG = LoggerFactory.getLogger(XMLEncryptedKeyInputHandler.class);

   public void handle(InputProcessorChain inputProcessorChain, XMLSecurityProperties securityProperties, Deque eventQueue, Integer index) throws XMLSecurityException {
      EncryptedKeyType encryptedKeyType = (EncryptedKeyType)((JAXBElement)this.parseStructure(eventQueue, index, securityProperties)).getValue();
      XMLSecEvent responsibleXMLSecStartXMLEvent = this.getResponsibleStartXMLEvent(eventQueue, index);
      this.handle(inputProcessorChain, encryptedKeyType, responsibleXMLSecStartXMLEvent, securityProperties);
   }

   public void handle(InputProcessorChain inputProcessorChain, final EncryptedKeyType encryptedKeyType, final XMLSecEvent responsibleXMLSecStartXMLEvent, final XMLSecurityProperties securityProperties) throws XMLSecurityException {
      if (encryptedKeyType.getEncryptionMethod() == null) {
         throw new XMLSecurityException("stax.encryption.noEncAlgo");
      } else {
         if (encryptedKeyType.getId() == null) {
            encryptedKeyType.setId(IDGenerator.generateID((String)null));
         }

         final InboundSecurityContext inboundSecurityContext = inputProcessorChain.getSecurityContext();
         SecurityTokenProvider securityTokenProvider = new SecurityTokenProvider() {
            private AbstractInboundSecurityToken securityToken;

            public InboundSecurityToken getSecurityToken() throws XMLSecurityException {
               if (this.securityToken != null) {
                  return this.securityToken;
               } else {
                  this.securityToken = new AbstractInboundSecurityToken(inboundSecurityContext, encryptedKeyType.getId(), SecurityTokenConstants.KeyIdentifier_EncryptedKey, true) {
                     private byte[] decryptedKey;
                     private InboundSecurityToken wrappingSecurityToken;

                     public Key getKey(String algorithmURI, XMLSecurityConstants.AlgorithmUsage algorithmUsage, String correlationID) throws XMLSecurityException {
                        Key keyx = (Key)this.getSecretKey().get(algorithmURI);
                        if (keyx != null) {
                           return keyx;
                        } else {
                           String algoFamily = JCEAlgorithmMapper.getJCEKeyAlgorithmFromURI(algorithmURI);
                           Key key = new SecretKeySpec(this.getSecret(this, correlationID, algorithmURI), algoFamily);
                           this.setSecretKey(algorithmURI, key);
                           return key;
                        }
                     }

                     public InboundSecurityToken getKeyWrappingToken() throws XMLSecurityException {
                        return this.getWrappingSecurityToken(this);
                     }

                     public SecurityTokenConstants.TokenType getTokenType() {
                        return SecurityTokenConstants.EncryptedKeyToken;
                     }

                     private InboundSecurityToken getWrappingSecurityToken(InboundSecurityToken wrappedSecurityToken) throws XMLSecurityException {
                        if (this.wrappingSecurityToken != null) {
                           return this.wrappingSecurityToken;
                        } else {
                           KeyInfoType keyInfoType = encryptedKeyType.getKeyInfo();
                           this.wrappingSecurityToken = SecurityTokenFactory.getInstance().getSecurityToken(keyInfoType, SecurityTokenConstants.KeyUsage_Decryption, securityProperties, inboundSecurityContext);
                           this.wrappingSecurityToken.addWrappedToken(wrappedSecurityToken);
                           return this.wrappingSecurityToken;
                        }
                     }

                     private byte[] getSecret(InboundSecurityToken wrappedSecurityToken, String correlationID, String symmetricAlgorithmURI) throws XMLSecurityException {
                        if (this.decryptedKey != null) {
                           return this.decryptedKey;
                        } else {
                           String algorithmURI = encryptedKeyType.getEncryptionMethod().getAlgorithm();
                           if (algorithmURI == null) {
                              throw new XMLSecurityException("stax.encryption.noEncAlgo");
                           } else {
                              String jceName = JCEAlgorithmMapper.translateURItoJCEID(algorithmURI);
                              String jceProvider = JCEAlgorithmMapper.getJCEProviderFromURI(algorithmURI);
                              if (jceName == null) {
                                 throw new XMLSecurityException("algorithms.NoSuchMap", new Object[]{algorithmURI});
                              } else {
                                 InboundSecurityToken wrappingSecurityToken = this.getWrappingSecurityToken(wrappedSecurityToken);

                                 Cipher cipher;
                                 String jceDigestAlgorithm;
                                 try {
                                    XMLSecurityConstants.AlgorithmUsage algorithmUsage;
                                    if (wrappingSecurityToken.isAsymmetric()) {
                                       algorithmUsage = XMLSecurityConstants.Asym_Key_Wrap;
                                    } else {
                                       algorithmUsage = XMLSecurityConstants.Sym_Key_Wrap;
                                    }

                                    if (jceProvider == null) {
                                       cipher = Cipher.getInstance(jceName);
                                    } else {
                                       cipher = Cipher.getInstance(jceName, jceProvider);
                                    }

                                    if (!"http://www.w3.org/2009/xmlenc11#rsa-oaep".equals(algorithmURI) && !"http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p".equals(algorithmURI)) {
                                       cipher.init(4, wrappingSecurityToken.getSecretKey(algorithmURI, algorithmUsage, correlationID));
                                    } else {
                                       DigestMethodType digestMethodType = (DigestMethodType)XMLSecurityUtils.getQNameType(encryptedKeyType.getEncryptionMethod().getContent(), XMLSecurityConstants.TAG_dsig_DigestMethod);
                                       jceDigestAlgorithm = "SHA-1";
                                       if (digestMethodType != null) {
                                          AlgorithmSuiteSecurityEvent algorithmSuiteSecurityEvent = new AlgorithmSuiteSecurityEvent();
                                          algorithmSuiteSecurityEvent.setAlgorithmURI(digestMethodType.getAlgorithm());
                                          algorithmSuiteSecurityEvent.setAlgorithmUsage(XMLSecurityConstants.EncDig);
                                          algorithmSuiteSecurityEvent.setCorrelationID(correlationID);
                                          inboundSecurityContext.registerSecurityEvent(algorithmSuiteSecurityEvent);
                                          jceDigestAlgorithm = JCEAlgorithmMapper.translateURItoJCEID(digestMethodType.getAlgorithm());
                                       }

                                       PSource.PSpecified pSource = PSpecified.DEFAULT;
                                       byte[] oaepParams = (byte[])XMLSecurityUtils.getQNameType(encryptedKeyType.getEncryptionMethod().getContent(), XMLSecurityConstants.TAG_xenc_OAEPparams);
                                       if (oaepParams != null) {
                                          pSource = new PSource.PSpecified(oaepParams);
                                       }

                                       MGF1ParameterSpec mgfParameterSpec = new MGF1ParameterSpec("SHA-1");
                                       MGFType mgfType = (MGFType)XMLSecurityUtils.getQNameType(encryptedKeyType.getEncryptionMethod().getContent(), XMLSecurityConstants.TAG_xenc11_MGF);
                                       if (mgfType != null) {
                                          String jceMGFAlgorithm = JCEAlgorithmMapper.translateURItoJCEID(mgfType.getAlgorithm());
                                          mgfParameterSpec = new MGF1ParameterSpec(jceMGFAlgorithm);
                                       }

                                       OAEPParameterSpec oaepParameterSpec = new OAEPParameterSpec(jceDigestAlgorithm, "MGF1", mgfParameterSpec, pSource);
                                       cipher.init(4, wrappingSecurityToken.getSecretKey(algorithmURI, algorithmUsage, correlationID), oaepParameterSpec);
                                    }

                                    if (encryptedKeyType.getCipherData() == null || encryptedKeyType.getCipherData().getCipherValue() == null || encryptedKeyType.getCipherData().getCipherValue().getContent() == null || encryptedKeyType.getCipherData().getCipherValue().getContent().isEmpty()) {
                                       throw new XMLSecurityException("stax.encryption.noCipherValue");
                                    }
                                 } catch (NoSuchPaddingException var19) {
                                    throw new XMLSecurityException(var19);
                                 } catch (NoSuchAlgorithmException var20) {
                                    throw new XMLSecurityException(var20);
                                 } catch (InvalidAlgorithmParameterException var21) {
                                    throw new XMLSecurityException(var21);
                                 } catch (InvalidKeyException var22) {
                                    throw new XMLSecurityException(var22);
                                 } catch (NoSuchProviderException var23) {
                                    throw new XMLSecurityException(var23);
                                 }

                                 byte[] encryptedBytes = getEncryptedBytes(encryptedKeyType.getCipherData().getCipherValue());
                                 byte[] sha1Bytes = XMLEncryptedKeyInputHandler.this.generateDigest(encryptedBytes);
                                 jceDigestAlgorithm = XMLUtils.encodeToString(sha1Bytes);
                                 super.setSha1Identifier(jceDigestAlgorithm);

                                 try {
                                    Key key = cipher.unwrap(encryptedBytes, jceName, 3);
                                    return this.decryptedKey = key.getEncoded();
                                 } catch (IllegalStateException var17) {
                                    throw new XMLSecurityException(var17);
                                 } catch (Exception var18) {
                                    XMLEncryptedKeyInputHandler.LOG.warn("Unwrapping of the encrypted key failed with error: " + var18.getMessage() + ". Generating a faked one to mitigate timing attacks.");
                                    int keyLength = JCEAlgorithmMapper.getKeyLengthFromURI(symmetricAlgorithmURI);
                                    this.decryptedKey = XMLSecurityConstants.generateBytes(keyLength / 8);
                                    return this.decryptedKey;
                                 }
                              }
                           }
                        }
                     }
                  };
                  this.securityToken.setElementPath(responsibleXMLSecStartXMLEvent.getElementPath());
                  this.securityToken.setXMLSecEvent(responsibleXMLSecStartXMLEvent);
                  return this.securityToken;
               }
            }

            private byte[] getEncryptedBytes(CipherValueType cipherValue) throws XMLSecurityException {
               StringBuilder sb = new StringBuilder();
               Iterator var3 = cipherValue.getContent().iterator();

               while(var3.hasNext()) {
                  Object obj = var3.next();
                  if (obj instanceof String) {
                     sb.append((String)obj);
                  } else if (obj instanceof JAXBElement) {
                     JAXBElement element = (JAXBElement)obj;
                     if (XMLSecurityConstants.TAG_XOP_INCLUDE.equals(element.getName())) {
                        Include include = (Include)element.getValue();
                        if (include != null && include.getHref() != null && include.getHref().startsWith("cid:")) {
                           return XMLEncryptedKeyInputHandler.this.getBytesFromAttachment(include.getHref(), securityProperties);
                        }
                     }
                  }
               }

               return Base64.getMimeDecoder().decode(sb.toString());
            }

            public String getId() {
               return encryptedKeyType.getId();
            }
         };
         inboundSecurityContext.registerSecurityTokenProvider(encryptedKeyType.getId(), securityTokenProvider);
         EncryptedKeyTokenSecurityEvent tokenSecurityEvent = new EncryptedKeyTokenSecurityEvent();
         tokenSecurityEvent.setSecurityToken((SecurityToken)securityTokenProvider.getSecurityToken());
         tokenSecurityEvent.setCorrelationID(encryptedKeyType.getId());
         inboundSecurityContext.registerSecurityEvent(tokenSecurityEvent);
         if (encryptedKeyType.getReferenceList() != null) {
            this.handleReferenceList(inputProcessorChain, encryptedKeyType, securityProperties);
         }

      }
   }

   private byte[] generateDigest(byte[] inputBytes) throws XMLSecurityException {
      try {
         return MessageDigest.getInstance("SHA-1").digest(inputBytes);
      } catch (NoSuchAlgorithmException var3) {
         throw new XMLSecurityException(var3);
      }
   }

   protected void handleReferenceList(InputProcessorChain inputProcessorChain, EncryptedKeyType encryptedKeyType, XMLSecurityProperties securityProperties) throws XMLSecurityException {
   }

   protected byte[] getBytesFromAttachment(String xopUri, XMLSecurityProperties securityProperties) throws XMLSecurityException {
      throw new XMLSecurityException("errorMessages.NotYetImplementedException");
   }
}
