package org.opensaml.xmlsec.signature.support.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.annotation.ParameterName;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.xml.AttributeSupport;
import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.opensaml.xmlsec.SignatureValidationParameters;
import org.opensaml.xmlsec.algorithm.AlgorithmSupport;
import org.opensaml.xmlsec.signature.Signature;
import org.opensaml.xmlsec.signature.support.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class SignatureAlgorithmValidator {
   private static final QName ELEMENT_NAME_SIGNED_INFO = new QName("http://www.w3.org/2000/09/xmldsig#", "SignedInfo");
   private static final QName ELEMENT_NAME_SIGNATURE_METHOD = new QName("http://www.w3.org/2000/09/xmldsig#", "SignatureMethod");
   private static final QName ELEMENT_NAME_REFERENCE = new QName("http://www.w3.org/2000/09/xmldsig#", "Reference");
   private static final QName ELEMENT_NAME_DIGEST_METHOD = new QName("http://www.w3.org/2000/09/xmldsig#", "DigestMethod");
   private static final String ATTR_NAME_ALGORTHM = "Algorithm";
   private Logger log = LoggerFactory.getLogger(SignatureAlgorithmValidator.class);
   private Collection whitelistedAlgorithmURIs;
   private Collection blacklistedAlgorithmURIs;

   public SignatureAlgorithmValidator(@Nonnull @ParameterName(name = "params") SignatureValidationParameters params) {
      Constraint.isNotNull(params, "SignatureValidationParameters may not be null");
      this.whitelistedAlgorithmURIs = params.getWhitelistedAlgorithms();
      this.blacklistedAlgorithmURIs = params.getBlacklistedAlgorithms();
   }

   public SignatureAlgorithmValidator(@Nullable @ParameterName(name = "whitelistAlgos") Collection whitelistAlgos, @Nullable @ParameterName(name = "blacklistAlgos") Collection blacklistAlgos) {
      this.whitelistedAlgorithmURIs = whitelistAlgos;
      this.blacklistedAlgorithmURIs = blacklistAlgos;
   }

   public void validate(@Nonnull Signature signature) throws SignatureException {
      Constraint.isNotNull(signature, "Signature was null");
      this.checkDOM(signature);
      String signatureAlgorithm = this.getSignatureAlgorithm(signature);
      this.log.debug("Validating SignedInfo/SignatureMethod/@Algorithm against whitelist/blacklist: {}", signatureAlgorithm);
      this.validateAlgorithmURI(signatureAlgorithm);
      Iterator var3 = this.getDigestMethods(signature).iterator();

      while(var3.hasNext()) {
         String digestMethod = (String)var3.next();
         this.log.debug("Validating SignedInfo/Reference/DigestMethod/@Algorithm against whitelist/blacklist: {}", digestMethod);
         this.validateAlgorithmURI(digestMethod);
      }

   }

   protected void checkDOM(@Nonnull Signature signature) throws SignatureException {
      if (signature.getDOM() == null) {
         this.log.warn("Signgaure does not have a cached DOM Element.");
         throw new SignatureException("Signature does not have a cached DOM Element.");
      }
   }

   @Nonnull
   protected String getSignatureAlgorithm(@Nonnull Signature signatureXMLObject) throws SignatureException {
      Element signature = signatureXMLObject.getDOM();
      Element signedInfo = ElementSupport.getFirstChildElement(signature, ELEMENT_NAME_SIGNED_INFO);
      Element signatureMethod = ElementSupport.getFirstChildElement(signedInfo, ELEMENT_NAME_SIGNATURE_METHOD);
      String signatureMethodAlgorithm = StringSupport.trimOrNull(AttributeSupport.getAttributeValue(signatureMethod, (String)null, "Algorithm"));
      if (signatureMethodAlgorithm != null) {
         return signatureMethodAlgorithm;
      } else {
         throw new SignatureException("SignatureMethod Algorithm was null");
      }
   }

   @Nonnull
   protected List getDigestMethods(@Nonnull Signature signatureXMLObject) throws SignatureException {
      ArrayList digestMethodAlgorithms = new ArrayList();
      Element signature = signatureXMLObject.getDOM();
      Element signedInfo = ElementSupport.getFirstChildElement(signature, ELEMENT_NAME_SIGNED_INFO);
      Iterator var5 = ElementSupport.getChildElements(signedInfo, ELEMENT_NAME_REFERENCE).iterator();

      while(var5.hasNext()) {
         Element reference = (Element)var5.next();
         Element digestMethod = ElementSupport.getFirstChildElement(reference, ELEMENT_NAME_DIGEST_METHOD);
         String digestMethodAlgorithm = StringSupport.trimOrNull(AttributeSupport.getAttributeValue(digestMethod, (String)null, "Algorithm"));
         if (digestMethodAlgorithm == null) {
            throw new SignatureException("Saw null DigestMethod Algorithm");
         }

         digestMethodAlgorithms.add(digestMethodAlgorithm);
      }

      return digestMethodAlgorithms;
   }

   protected void validateAlgorithmURI(@Nonnull String algorithmURI) throws SignatureException {
      this.log.debug("Validating algorithm URI against whitelist and blacklist: algorithm: {}, whitelist: {}, blacklist: {}", new Object[]{algorithmURI, this.whitelistedAlgorithmURIs, this.blacklistedAlgorithmURIs});
      if (!AlgorithmSupport.validateAlgorithmURI(algorithmURI, this.whitelistedAlgorithmURIs, this.blacklistedAlgorithmURIs)) {
         throw new SignatureException("Algorithm failed whitelist/blacklist validation: " + algorithmURI);
      }
   }
}
