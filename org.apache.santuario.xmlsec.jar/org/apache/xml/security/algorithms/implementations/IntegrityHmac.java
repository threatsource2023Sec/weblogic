package org.apache.xml.security.algorithms.implementations;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import org.apache.xml.security.algorithms.JCEMapper;
import org.apache.xml.security.algorithms.MessageDigestAlgorithm;
import org.apache.xml.security.algorithms.SignatureAlgorithmSpi;
import org.apache.xml.security.signature.XMLSignatureException;
import org.apache.xml.security.utils.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

public abstract class IntegrityHmac extends SignatureAlgorithmSpi {
   private static final Logger LOG = LoggerFactory.getLogger(IntegrityHmac.class);
   private Mac macAlgorithm;
   private int HMACOutputLength;
   private boolean HMACOutputLengthSet = false;

   public abstract String engineGetURI();

   abstract int getDigestLength();

   public IntegrityHmac() throws XMLSignatureException {
      String algorithmID = JCEMapper.translateURItoJCEID(this.engineGetURI());
      LOG.debug("Created IntegrityHmacSHA1 using {}", algorithmID);

      try {
         this.macAlgorithm = Mac.getInstance(algorithmID);
      } catch (NoSuchAlgorithmException var4) {
         Object[] exArgs = new Object[]{algorithmID, var4.getLocalizedMessage()};
         throw new XMLSignatureException("algorithms.NoSuchAlgorithm", exArgs);
      }
   }

   protected void engineSetParameter(AlgorithmParameterSpec params) throws XMLSignatureException {
      throw new XMLSignatureException("empty", new Object[]{"Incorrect method call"});
   }

   public void reset() {
      this.HMACOutputLength = 0;
      this.HMACOutputLengthSet = false;
      this.macAlgorithm.reset();
   }

   protected boolean engineVerify(byte[] signature) throws XMLSignatureException {
      try {
         if (this.HMACOutputLengthSet && this.HMACOutputLength < this.getDigestLength()) {
            LOG.debug("HMACOutputLength must not be less than {}", this.getDigestLength());
            Object[] exArgs = new Object[]{String.valueOf(this.getDigestLength())};
            throw new XMLSignatureException("algorithms.HMACOutputLengthMin", exArgs);
         } else {
            byte[] completeResult = this.macAlgorithm.doFinal();
            return MessageDigestAlgorithm.isEqual(completeResult, signature);
         }
      } catch (IllegalStateException var3) {
         throw new XMLSignatureException(var3);
      }
   }

   protected void engineInitVerify(Key secretKey) throws XMLSignatureException {
      if (!(secretKey instanceof SecretKey)) {
         String supplied = null;
         if (secretKey != null) {
            supplied = secretKey.getClass().getName();
         }

         String needed = SecretKey.class.getName();
         Object[] exArgs = new Object[]{supplied, needed};
         throw new XMLSignatureException("algorithms.WrongKeyForThisOperation", exArgs);
      } else {
         try {
            this.macAlgorithm.init(secretKey);
         } catch (InvalidKeyException var6) {
            Mac mac = this.macAlgorithm;

            try {
               this.macAlgorithm = Mac.getInstance(this.macAlgorithm.getAlgorithm());
            } catch (Exception var5) {
               LOG.debug("Exception when reinstantiating Mac: {}", var5);
               this.macAlgorithm = mac;
            }

            throw new XMLSignatureException(var6);
         }
      }
   }

   protected byte[] engineSign() throws XMLSignatureException {
      try {
         if (this.HMACOutputLengthSet && this.HMACOutputLength < this.getDigestLength()) {
            LOG.debug("HMACOutputLength must not be less than {}", this.getDigestLength());
            Object[] exArgs = new Object[]{String.valueOf(this.getDigestLength())};
            throw new XMLSignatureException("algorithms.HMACOutputLengthMin", exArgs);
         } else {
            return this.macAlgorithm.doFinal();
         }
      } catch (IllegalStateException var2) {
         throw new XMLSignatureException(var2);
      }
   }

   protected void engineInitSign(Key secretKey) throws XMLSignatureException {
      this.engineInitSign(secretKey, (AlgorithmParameterSpec)null);
   }

   protected void engineInitSign(Key secretKey, AlgorithmParameterSpec algorithmParameterSpec) throws XMLSignatureException {
      if (!(secretKey instanceof SecretKey)) {
         String supplied = null;
         if (secretKey != null) {
            supplied = secretKey.getClass().getName();
         }

         String needed = SecretKey.class.getName();
         Object[] exArgs = new Object[]{supplied, needed};
         throw new XMLSignatureException("algorithms.WrongKeyForThisOperation", exArgs);
      } else {
         try {
            if (algorithmParameterSpec == null) {
               this.macAlgorithm.init(secretKey);
            } else {
               this.macAlgorithm.init(secretKey, algorithmParameterSpec);
            }

         } catch (InvalidKeyException var6) {
            throw new XMLSignatureException(var6);
         } catch (InvalidAlgorithmParameterException var7) {
            throw new XMLSignatureException(var7);
         }
      }
   }

   protected void engineInitSign(Key secretKey, SecureRandom secureRandom) throws XMLSignatureException {
      throw new XMLSignatureException("algorithms.CannotUseSecureRandomOnMAC");
   }

   protected void engineUpdate(byte[] input) throws XMLSignatureException {
      try {
         this.macAlgorithm.update(input);
      } catch (IllegalStateException var3) {
         throw new XMLSignatureException(var3);
      }
   }

   protected void engineUpdate(byte input) throws XMLSignatureException {
      try {
         this.macAlgorithm.update(input);
      } catch (IllegalStateException var3) {
         throw new XMLSignatureException(var3);
      }
   }

   protected void engineUpdate(byte[] buf, int offset, int len) throws XMLSignatureException {
      try {
         this.macAlgorithm.update(buf, offset, len);
      } catch (IllegalStateException var5) {
         throw new XMLSignatureException(var5);
      }
   }

   protected String engineGetJCEAlgorithmString() {
      return this.macAlgorithm.getAlgorithm();
   }

   protected String engineGetJCEProviderName() {
      return this.macAlgorithm.getProvider().getName();
   }

   protected void engineSetHMACOutputLength(int HMACOutputLength) {
      this.HMACOutputLength = HMACOutputLength;
      this.HMACOutputLengthSet = true;
   }

   protected void engineGetContextFromElement(Element element) {
      super.engineGetContextFromElement(element);
      if (element == null) {
         throw new IllegalArgumentException("element null");
      } else {
         Node n = XMLUtils.selectDsNode(element.getFirstChild(), "HMACOutputLength", 0);
         if (n != null) {
            String hmacLength = XMLUtils.getFullTextChildrenFromNode(n);
            if (hmacLength != null && !"".equals(hmacLength)) {
               this.HMACOutputLength = Integer.parseInt(hmacLength);
               this.HMACOutputLengthSet = true;
            }
         }

      }
   }

   public void engineAddContextToElement(Element element) {
      if (element == null) {
         throw new IllegalArgumentException("null element");
      } else {
         if (this.HMACOutputLengthSet) {
            Document doc = element.getOwnerDocument();
            Element HMElem = XMLUtils.createElementInSignatureSpace(doc, "HMACOutputLength");
            Text HMText = doc.createTextNode("" + this.HMACOutputLength);
            HMElem.appendChild(HMText);
            XMLUtils.addReturnToElement(element);
            element.appendChild(HMElem);
            XMLUtils.addReturnToElement(element);
         }

      }
   }

   public static class IntegrityHmacMD5 extends IntegrityHmac {
      public IntegrityHmacMD5() throws XMLSignatureException {
      }

      public String engineGetURI() {
         return "http://www.w3.org/2001/04/xmldsig-more#hmac-md5";
      }

      int getDigestLength() {
         return 128;
      }
   }

   public static class IntegrityHmacRIPEMD160 extends IntegrityHmac {
      public IntegrityHmacRIPEMD160() throws XMLSignatureException {
      }

      public String engineGetURI() {
         return "http://www.w3.org/2001/04/xmldsig-more#hmac-ripemd160";
      }

      int getDigestLength() {
         return 160;
      }
   }

   public static class IntegrityHmacSHA512 extends IntegrityHmac {
      public IntegrityHmacSHA512() throws XMLSignatureException {
      }

      public String engineGetURI() {
         return "http://www.w3.org/2001/04/xmldsig-more#hmac-sha512";
      }

      int getDigestLength() {
         return 512;
      }
   }

   public static class IntegrityHmacSHA384 extends IntegrityHmac {
      public IntegrityHmacSHA384() throws XMLSignatureException {
      }

      public String engineGetURI() {
         return "http://www.w3.org/2001/04/xmldsig-more#hmac-sha384";
      }

      int getDigestLength() {
         return 384;
      }
   }

   public static class IntegrityHmacSHA256 extends IntegrityHmac {
      public IntegrityHmacSHA256() throws XMLSignatureException {
      }

      public String engineGetURI() {
         return "http://www.w3.org/2001/04/xmldsig-more#hmac-sha256";
      }

      int getDigestLength() {
         return 256;
      }
   }

   public static class IntegrityHmacSHA224 extends IntegrityHmac {
      public IntegrityHmacSHA224() throws XMLSignatureException {
      }

      public String engineGetURI() {
         return "http://www.w3.org/2001/04/xmldsig-more#hmac-sha224";
      }

      int getDigestLength() {
         return 224;
      }
   }

   public static class IntegrityHmacSHA1 extends IntegrityHmac {
      public IntegrityHmacSHA1() throws XMLSignatureException {
      }

      public String engineGetURI() {
         return "http://www.w3.org/2000/09/xmldsig#hmac-sha1";
      }

      int getDigestLength() {
         return 160;
      }
   }
}
