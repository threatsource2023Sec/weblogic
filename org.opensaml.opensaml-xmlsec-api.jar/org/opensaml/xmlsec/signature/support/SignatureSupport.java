package org.opensaml.xmlsec.signature.support;

import java.util.Iterator;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.core.xml.XMLObjectBuilder;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.Marshaller;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.security.SecurityException;
import org.opensaml.xmlsec.SignatureSigningParameters;
import org.opensaml.xmlsec.algorithm.AlgorithmSupport;
import org.opensaml.xmlsec.keyinfo.KeyInfoGenerator;
import org.opensaml.xmlsec.signature.KeyInfo;
import org.opensaml.xmlsec.signature.SignableXMLObject;
import org.opensaml.xmlsec.signature.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SignatureSupport {
   private SignatureSupport() {
   }

   @Nonnull
   private static Logger getLogger() {
      return LoggerFactory.getLogger(SignatureSupport.class);
   }

   public static void prepareSignatureParams(@Nonnull Signature signature, @Nonnull SignatureSigningParameters parameters) throws SecurityException {
      Constraint.isNotNull(signature, "Signature cannot be null");
      Constraint.isNotNull(parameters, "Signature signing parameters cannot be null");
      Logger log = getLogger();
      if (signature.getSigningCredential() == null) {
         signature.setSigningCredential(parameters.getSigningCredential());
      }

      if (signature.getSigningCredential() == null) {
         throw new SecurityException("No signing credential was available on the signing parameters or Signature");
      } else {
         if (signature.getSignatureAlgorithm() == null) {
            signature.setSignatureAlgorithm(parameters.getSignatureAlgorithm());
         }

         if (signature.getSignatureAlgorithm() == null) {
            throw new SecurityException("No signature algorithm was available on the signing parameters or Signature");
         } else {
            if (signature.getHMACOutputLength() == null && AlgorithmSupport.isHMAC(signature.getSignatureAlgorithm())) {
               signature.setHMACOutputLength(parameters.getSignatureHMACOutputLength());
            }

            if (signature.getCanonicalizationAlgorithm() == null) {
               signature.setCanonicalizationAlgorithm(parameters.getSignatureCanonicalizationAlgorithm());
            }

            if (signature.getCanonicalizationAlgorithm() == null) {
               throw new SecurityException("No C14N algorithm was available on the signing parameters or Signature");
            } else {
               String paramsDigestAlgo = parameters.getSignatureReferenceDigestMethod();
               Iterator var4 = signature.getContentReferences().iterator();

               while(var4.hasNext()) {
                  ContentReference cr = (ContentReference)var4.next();
                  if (cr instanceof ConfigurableContentReference) {
                     ConfigurableContentReference configurableReference = (ConfigurableContentReference)cr;
                     if (paramsDigestAlgo != null) {
                        configurableReference.setDigestAlgorithm(paramsDigestAlgo);
                     }

                     if (configurableReference.getDigestAlgorithm() == null) {
                        throw new SecurityException("No reference digest algorithm was available on the signing parameters or Signature ContentReference");
                     }
                  }
               }

               if (signature.getKeyInfo() == null) {
                  KeyInfoGenerator kiGenerator = parameters.getKeyInfoGenerator();
                  if (kiGenerator != null) {
                     try {
                        KeyInfo keyInfo = kiGenerator.generate(signature.getSigningCredential());
                        signature.setKeyInfo(keyInfo);
                     } catch (SecurityException var7) {
                        log.error("Error generating KeyInfo from credential", var7);
                        throw var7;
                     }
                  } else {
                     log.info("No KeyInfoGenerator was supplied in parameters or resolveable for credential type {}, No KeyInfo will be generated for Signature", signature.getSigningCredential().getCredentialType().getName());
                  }
               }

            }
         }
      }
   }

   public static void signObject(@Nonnull SignableXMLObject signable, @Nonnull SignatureSigningParameters parameters) throws SecurityException, MarshallingException, SignatureException {
      Constraint.isNotNull(signable, "Signable XMLObject cannot be null");
      Constraint.isNotNull(parameters, "Signature signing parameters cannot be null");
      XMLObjectBuilder signatureBuilder = XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilder(Signature.DEFAULT_ELEMENT_NAME);
      Signature signature = (Signature)signatureBuilder.buildObject(Signature.DEFAULT_ELEMENT_NAME);
      signable.setSignature(signature);
      prepareSignatureParams(signature, parameters);
      Marshaller marshaller = XMLObjectProviderRegistrySupport.getMarshallerFactory().getMarshaller(signable);
      marshaller.marshall(signable);
      Signer.signObject(signature);
   }
}
