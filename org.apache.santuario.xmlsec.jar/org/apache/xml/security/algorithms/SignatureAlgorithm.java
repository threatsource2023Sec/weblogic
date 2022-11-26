package org.apache.xml.security.algorithms;

import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.xml.security.algorithms.implementations.IntegrityHmac;
import org.apache.xml.security.algorithms.implementations.SignatureBaseRSA;
import org.apache.xml.security.algorithms.implementations.SignatureDSA;
import org.apache.xml.security.algorithms.implementations.SignatureECDSA;
import org.apache.xml.security.exceptions.AlgorithmAlreadyRegisteredException;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.signature.XMLSignatureException;
import org.apache.xml.security.utils.ClassLoaderUtils;
import org.apache.xml.security.utils.JavaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SignatureAlgorithm extends Algorithm {
   private static final Logger LOG = LoggerFactory.getLogger(SignatureAlgorithm.class);
   private static Map algorithmHash = new ConcurrentHashMap();
   private final SignatureAlgorithmSpi signatureAlgorithm;
   private final String algorithmURI;

   public SignatureAlgorithm(Document doc, String algorithmURI) throws XMLSecurityException {
      super(doc, algorithmURI);
      this.algorithmURI = algorithmURI;
      this.signatureAlgorithm = getSignatureAlgorithmSpi(algorithmURI);
      this.signatureAlgorithm.engineGetContextFromElement(this.getElement());
   }

   public SignatureAlgorithm(Document doc, String algorithmURI, int hmacOutputLength) throws XMLSecurityException {
      super(doc, algorithmURI);
      this.algorithmURI = algorithmURI;
      this.signatureAlgorithm = getSignatureAlgorithmSpi(algorithmURI);
      this.signatureAlgorithm.engineGetContextFromElement(this.getElement());
      this.signatureAlgorithm.engineSetHMACOutputLength(hmacOutputLength);
      ((IntegrityHmac)this.signatureAlgorithm).engineAddContextToElement(this.getElement());
   }

   public SignatureAlgorithm(Element element, String baseURI) throws XMLSecurityException {
      this(element, baseURI, true);
   }

   public SignatureAlgorithm(Element element, String baseURI, boolean secureValidation) throws XMLSecurityException {
      super(element, baseURI);
      this.algorithmURI = this.getURI();
      Attr attr = element.getAttributeNodeNS((String)null, "Id");
      if (attr != null) {
         element.setIdAttributeNode(attr, true);
      }

      if (!secureValidation || !"http://www.w3.org/2001/04/xmldsig-more#hmac-md5".equals(this.algorithmURI) && !"http://www.w3.org/2001/04/xmldsig-more#rsa-md5".equals(this.algorithmURI)) {
         this.signatureAlgorithm = getSignatureAlgorithmSpi(this.algorithmURI);
         this.signatureAlgorithm.engineGetContextFromElement(this.getElement());
      } else {
         Object[] exArgs = new Object[]{this.algorithmURI};
         throw new XMLSecurityException("signature.signatureAlgorithm", exArgs);
      }
   }

   private static SignatureAlgorithmSpi getSignatureAlgorithmSpi(String algorithmURI) throws XMLSignatureException {
      Object[] exArgs;
      try {
         Class implementingClass = (Class)algorithmHash.get(algorithmURI);
         LOG.debug("Create URI \"{}\" class \"{}\"", algorithmURI, implementingClass);
         if (implementingClass == null) {
            exArgs = new Object[]{algorithmURI};
            throw new XMLSignatureException("algorithms.NoSuchAlgorithmNoEx", exArgs);
         } else {
            return (SignatureAlgorithmSpi)implementingClass.newInstance();
         }
      } catch (InstantiationException | NullPointerException | IllegalAccessException var3) {
         exArgs = new Object[]{algorithmURI, var3.getMessage()};
         throw new XMLSignatureException(var3, "algorithms.NoSuchAlgorithm", exArgs);
      }
   }

   public byte[] sign() throws XMLSignatureException {
      return this.signatureAlgorithm.engineSign();
   }

   public String getJCEAlgorithmString() {
      return this.signatureAlgorithm.engineGetJCEAlgorithmString();
   }

   public String getJCEProviderName() {
      return this.signatureAlgorithm.engineGetJCEProviderName();
   }

   public void update(byte[] input) throws XMLSignatureException {
      this.signatureAlgorithm.engineUpdate(input);
   }

   public void update(byte input) throws XMLSignatureException {
      this.signatureAlgorithm.engineUpdate(input);
   }

   public void update(byte[] buf, int offset, int len) throws XMLSignatureException {
      this.signatureAlgorithm.engineUpdate(buf, offset, len);
   }

   public void initSign(Key signingKey) throws XMLSignatureException {
      this.signatureAlgorithm.engineInitSign(signingKey);
   }

   public void initSign(Key signingKey, SecureRandom secureRandom) throws XMLSignatureException {
      this.signatureAlgorithm.engineInitSign(signingKey, secureRandom);
   }

   public void initSign(Key signingKey, AlgorithmParameterSpec algorithmParameterSpec) throws XMLSignatureException {
      this.signatureAlgorithm.engineInitSign(signingKey, algorithmParameterSpec);
   }

   public void setParameter(AlgorithmParameterSpec params) throws XMLSignatureException {
      this.signatureAlgorithm.engineSetParameter(params);
   }

   public void initVerify(Key verificationKey) throws XMLSignatureException {
      this.signatureAlgorithm.engineInitVerify(verificationKey);
   }

   public boolean verify(byte[] signature) throws XMLSignatureException {
      return this.signatureAlgorithm.engineVerify(signature);
   }

   public final String getURI() {
      return this.getLocalAttribute("Algorithm");
   }

   public static void register(String algorithmURI, String implementingClass) throws AlgorithmAlreadyRegisteredException, ClassNotFoundException, XMLSignatureException {
      JavaUtils.checkRegisterPermission();
      LOG.debug("Try to register {} {}", algorithmURI, implementingClass);
      Class registeredClass = (Class)algorithmHash.get(algorithmURI);
      if (registeredClass != null) {
         Object[] exArgs = new Object[]{algorithmURI, registeredClass};
         throw new AlgorithmAlreadyRegisteredException("algorithm.alreadyRegistered", exArgs);
      } else {
         try {
            Class clazz = ClassLoaderUtils.loadClass(implementingClass, SignatureAlgorithm.class);
            algorithmHash.put(algorithmURI, clazz);
         } catch (NullPointerException var5) {
            Object[] exArgs = new Object[]{algorithmURI, var5.getMessage()};
            throw new XMLSignatureException(var5, "algorithms.NoSuchAlgorithm", exArgs);
         }
      }
   }

   public static void register(String algorithmURI, Class implementingClass) throws AlgorithmAlreadyRegisteredException, ClassNotFoundException, XMLSignatureException {
      JavaUtils.checkRegisterPermission();
      LOG.debug("Try to register {} {}", algorithmURI, implementingClass);
      Class registeredClass = (Class)algorithmHash.get(algorithmURI);
      if (registeredClass != null) {
         Object[] exArgs = new Object[]{algorithmURI, registeredClass};
         throw new AlgorithmAlreadyRegisteredException("algorithm.alreadyRegistered", exArgs);
      } else {
         algorithmHash.put(algorithmURI, implementingClass);
      }
   }

   public static void registerDefaultAlgorithms() {
      algorithmHash.put("http://www.w3.org/2000/09/xmldsig#dsa-sha1", SignatureDSA.class);
      algorithmHash.put("http://www.w3.org/2009/xmldsig11#dsa-sha256", SignatureDSA.SHA256.class);
      algorithmHash.put("http://www.w3.org/2000/09/xmldsig#rsa-sha1", SignatureBaseRSA.SignatureRSASHA1.class);
      algorithmHash.put("http://www.w3.org/2000/09/xmldsig#hmac-sha1", IntegrityHmac.IntegrityHmacSHA1.class);
      algorithmHash.put("http://www.w3.org/2001/04/xmldsig-more#rsa-md5", SignatureBaseRSA.SignatureRSAMD5.class);
      algorithmHash.put("http://www.w3.org/2001/04/xmldsig-more#rsa-ripemd160", SignatureBaseRSA.SignatureRSARIPEMD160.class);
      algorithmHash.put("http://www.w3.org/2001/04/xmldsig-more#rsa-sha224", SignatureBaseRSA.SignatureRSASHA224.class);
      algorithmHash.put("http://www.w3.org/2001/04/xmldsig-more#rsa-sha256", SignatureBaseRSA.SignatureRSASHA256.class);
      algorithmHash.put("http://www.w3.org/2001/04/xmldsig-more#rsa-sha384", SignatureBaseRSA.SignatureRSASHA384.class);
      algorithmHash.put("http://www.w3.org/2001/04/xmldsig-more#rsa-sha512", SignatureBaseRSA.SignatureRSASHA512.class);
      algorithmHash.put("http://www.w3.org/2007/05/xmldsig-more#sha1-rsa-MGF1", SignatureBaseRSA.SignatureRSASHA1MGF1.class);
      algorithmHash.put("http://www.w3.org/2007/05/xmldsig-more#sha224-rsa-MGF1", SignatureBaseRSA.SignatureRSASHA224MGF1.class);
      algorithmHash.put("http://www.w3.org/2007/05/xmldsig-more#sha256-rsa-MGF1", SignatureBaseRSA.SignatureRSASHA256MGF1.class);
      algorithmHash.put("http://www.w3.org/2007/05/xmldsig-more#sha384-rsa-MGF1", SignatureBaseRSA.SignatureRSASHA384MGF1.class);
      algorithmHash.put("http://www.w3.org/2007/05/xmldsig-more#sha512-rsa-MGF1", SignatureBaseRSA.SignatureRSASHA512MGF1.class);
      algorithmHash.put("http://www.w3.org/2007/05/xmldsig-more#sha3-224-rsa-MGF1", SignatureBaseRSA.SignatureRSASHA3_224MGF1.class);
      algorithmHash.put("http://www.w3.org/2007/05/xmldsig-more#sha3-256-rsa-MGF1", SignatureBaseRSA.SignatureRSASHA3_256MGF1.class);
      algorithmHash.put("http://www.w3.org/2007/05/xmldsig-more#sha3-384-rsa-MGF1", SignatureBaseRSA.SignatureRSASHA3_384MGF1.class);
      algorithmHash.put("http://www.w3.org/2007/05/xmldsig-more#sha3-512-rsa-MGF1", SignatureBaseRSA.SignatureRSASHA3_512MGF1.class);
      algorithmHash.put("http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha1", SignatureECDSA.SignatureECDSASHA1.class);
      algorithmHash.put("http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha224", SignatureECDSA.SignatureECDSASHA224.class);
      algorithmHash.put("http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha256", SignatureECDSA.SignatureECDSASHA256.class);
      algorithmHash.put("http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha384", SignatureECDSA.SignatureECDSASHA384.class);
      algorithmHash.put("http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha512", SignatureECDSA.SignatureECDSASHA512.class);
      algorithmHash.put("http://www.w3.org/2007/05/xmldsig-more#ecdsa-ripemd160", SignatureECDSA.SignatureECDSARIPEMD160.class);
      algorithmHash.put("http://www.w3.org/2001/04/xmldsig-more#hmac-md5", IntegrityHmac.IntegrityHmacMD5.class);
      algorithmHash.put("http://www.w3.org/2001/04/xmldsig-more#hmac-ripemd160", IntegrityHmac.IntegrityHmacRIPEMD160.class);
      algorithmHash.put("http://www.w3.org/2001/04/xmldsig-more#hmac-sha224", IntegrityHmac.IntegrityHmacSHA224.class);
      algorithmHash.put("http://www.w3.org/2001/04/xmldsig-more#hmac-sha256", IntegrityHmac.IntegrityHmacSHA256.class);
      algorithmHash.put("http://www.w3.org/2001/04/xmldsig-more#hmac-sha384", IntegrityHmac.IntegrityHmacSHA384.class);
      algorithmHash.put("http://www.w3.org/2001/04/xmldsig-more#hmac-sha512", IntegrityHmac.IntegrityHmacSHA512.class);
   }

   public String getBaseNamespace() {
      return "http://www.w3.org/2000/09/xmldsig#";
   }

   public String getBaseLocalName() {
      return "SignatureMethod";
   }
}
