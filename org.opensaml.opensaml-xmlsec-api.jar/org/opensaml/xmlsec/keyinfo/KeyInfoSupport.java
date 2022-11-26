package org.opensaml.xmlsec.keyinfo;

import com.google.common.base.Strings;
import java.math.BigInteger;
import java.security.KeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CRLException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.DSAParameterSpec;
import java.security.spec.DSAPublicKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.codec.Base64Support;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.apache.xml.security.utils.Base64;
import org.opensaml.core.xml.XMLObjectBuilder;
import org.opensaml.core.xml.XMLObjectBuilderFactory;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.x509.X509Support;
import org.opensaml.xmlsec.algorithm.AlgorithmSupport;
import org.opensaml.xmlsec.signature.DEREncodedKeyValue;
import org.opensaml.xmlsec.signature.DSAKeyValue;
import org.opensaml.xmlsec.signature.Exponent;
import org.opensaml.xmlsec.signature.G;
import org.opensaml.xmlsec.signature.KeyInfo;
import org.opensaml.xmlsec.signature.KeyName;
import org.opensaml.xmlsec.signature.KeyValue;
import org.opensaml.xmlsec.signature.Modulus;
import org.opensaml.xmlsec.signature.P;
import org.opensaml.xmlsec.signature.Q;
import org.opensaml.xmlsec.signature.RSAKeyValue;
import org.opensaml.xmlsec.signature.X509CRL;
import org.opensaml.xmlsec.signature.X509Certificate;
import org.opensaml.xmlsec.signature.X509Data;
import org.opensaml.xmlsec.signature.X509Digest;
import org.opensaml.xmlsec.signature.X509IssuerName;
import org.opensaml.xmlsec.signature.X509IssuerSerial;
import org.opensaml.xmlsec.signature.X509SKI;
import org.opensaml.xmlsec.signature.X509SerialNumber;
import org.opensaml.xmlsec.signature.X509SubjectName;
import org.opensaml.xmlsec.signature.Y;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KeyInfoSupport {
   private static CertificateFactory x509CertFactory;

   protected KeyInfoSupport() {
   }

   @Nonnull
   public static List getKeyNames(@Nullable KeyInfo keyInfo) {
      List keynameList = new LinkedList();
      if (keyInfo == null) {
         return keynameList;
      } else {
         List keyNames = keyInfo.getKeyNames();
         Iterator var3 = keyNames.iterator();

         while(var3.hasNext()) {
            KeyName keyName = (KeyName)var3.next();
            if (keyName.getValue() != null) {
               keynameList.add(keyName.getValue());
            }
         }

         return keynameList;
      }
   }

   public static void addKeyName(@Nonnull KeyInfo keyInfo, @Nullable String keyNameValue) {
      Constraint.isNotNull(keyInfo, "KeyInfo cannot be null");
      XMLObjectBuilder keyNameBuilder = XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilder(KeyName.DEFAULT_ELEMENT_NAME);
      KeyName keyName = (KeyName)((XMLObjectBuilder)Constraint.isNotNull(keyNameBuilder, "KeyName builder not available")).buildObject(KeyName.DEFAULT_ELEMENT_NAME);
      keyName.setValue(keyNameValue);
      keyInfo.getKeyNames().add(keyName);
   }

   @Nonnull
   public static List getCertificates(@Nullable KeyInfo keyInfo) throws CertificateException {
      List certList = new LinkedList();
      if (keyInfo == null) {
         return certList;
      } else {
         List x509Datas = keyInfo.getX509Datas();
         Iterator var3 = x509Datas.iterator();

         while(var3.hasNext()) {
            X509Data x509Data = (X509Data)var3.next();
            certList.addAll(getCertificates(x509Data));
         }

         return certList;
      }
   }

   @Nonnull
   public static List getCertificates(@Nullable X509Data x509Data) throws CertificateException {
      List certList = new LinkedList();
      if (x509Data == null) {
         return certList;
      } else {
         Iterator var2 = x509Data.getX509Certificates().iterator();

         while(var2.hasNext()) {
            X509Certificate xmlCert = (X509Certificate)var2.next();
            java.security.cert.X509Certificate newCert = getCertificate(xmlCert);
            if (newCert != null) {
               certList.add(newCert);
            }
         }

         return certList;
      }
   }

   @Nullable
   public static java.security.cert.X509Certificate getCertificate(@Nullable X509Certificate xmlCert) throws CertificateException {
      return xmlCert != null && xmlCert.getValue() != null ? X509Support.decodeCertificate(xmlCert.getValue()) : null;
   }

   @Nonnull
   public static List getCRLs(@Nullable KeyInfo keyInfo) throws CRLException {
      List crlList = new LinkedList();
      if (keyInfo == null) {
         return crlList;
      } else {
         List x509Datas = keyInfo.getX509Datas();
         Iterator var3 = x509Datas.iterator();

         while(var3.hasNext()) {
            X509Data x509Data = (X509Data)var3.next();
            crlList.addAll(getCRLs(x509Data));
         }

         return crlList;
      }
   }

   @Nonnull
   public static List getCRLs(@Nullable X509Data x509Data) throws CRLException {
      List crlList = new LinkedList();
      if (x509Data == null) {
         return crlList;
      } else {
         Iterator var2 = x509Data.getX509CRLs().iterator();

         while(var2.hasNext()) {
            X509CRL xmlCRL = (X509CRL)var2.next();
            java.security.cert.X509CRL newCRL = getCRL(xmlCRL);
            if (newCRL != null) {
               crlList.add(newCRL);
            }
         }

         return crlList;
      }
   }

   @Nullable
   public static java.security.cert.X509CRL getCRL(@Nullable X509CRL xmlCRL) throws CRLException {
      if (xmlCRL != null && xmlCRL.getValue() != null) {
         try {
            return X509Support.decodeCRL(xmlCRL.getValue());
         } catch (CertificateException var2) {
            throw new CRLException("Certificate error attempting to decode CRL", var2);
         }
      } else {
         return null;
      }
   }

   public static void addCertificate(@Nonnull KeyInfo keyInfo, @Nonnull java.security.cert.X509Certificate cert) throws CertificateEncodingException {
      Constraint.isNotNull(keyInfo, "KeyInfo cannot be null");
      X509Data x509Data;
      if (keyInfo.getX509Datas().size() == 0) {
         XMLObjectBuilder x509DataBuilder = XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilder(X509Data.DEFAULT_ELEMENT_NAME);
         x509Data = (X509Data)((XMLObjectBuilder)Constraint.isNotNull(x509DataBuilder, "X509Data builder not available")).buildObject(X509Data.DEFAULT_ELEMENT_NAME);
         keyInfo.getX509Datas().add(x509Data);
      } else {
         x509Data = (X509Data)keyInfo.getX509Datas().get(0);
      }

      x509Data.getX509Certificates().add(buildX509Certificate(cert));
   }

   public static void addCRL(@Nonnull KeyInfo keyInfo, @Nonnull java.security.cert.X509CRL crl) throws CRLException {
      Constraint.isNotNull(keyInfo, "KeyInfo cannot be null");
      X509Data x509Data;
      if (keyInfo.getX509Datas().size() == 0) {
         XMLObjectBuilder x509DataBuilder = XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilder(X509Data.DEFAULT_ELEMENT_NAME);
         x509Data = (X509Data)((XMLObjectBuilder)Constraint.isNotNull(x509DataBuilder, "X509Data builder not available")).buildObject(X509Data.DEFAULT_ELEMENT_NAME);
         keyInfo.getX509Datas().add(x509Data);
      } else {
         x509Data = (X509Data)keyInfo.getX509Datas().get(0);
      }

      x509Data.getX509CRLs().add(buildX509CRL(crl));
   }

   @Nonnull
   public static X509Certificate buildX509Certificate(java.security.cert.X509Certificate cert) throws CertificateEncodingException {
      Constraint.isNotNull(cert, "X.509 certificate cannot be null");
      XMLObjectBuilder xmlCertBuilder = XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilder(X509Certificate.DEFAULT_ELEMENT_NAME);
      X509Certificate xmlCert = (X509Certificate)((XMLObjectBuilder)Constraint.isNotNull(xmlCertBuilder, "X509Certificate builder not available")).buildObject(X509Certificate.DEFAULT_ELEMENT_NAME);
      xmlCert.setValue(Base64Support.encode(cert.getEncoded(), true));
      return xmlCert;
   }

   @Nonnull
   public static X509CRL buildX509CRL(java.security.cert.X509CRL crl) throws CRLException {
      Constraint.isNotNull(crl, "X.509 CRL cannot be null");
      XMLObjectBuilder xmlCRLBuilder = XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilder(X509CRL.DEFAULT_ELEMENT_NAME);
      X509CRL xmlCRL = (X509CRL)((XMLObjectBuilder)Constraint.isNotNull(xmlCRLBuilder, "X509Certificate builder not available")).buildObject(X509CRL.DEFAULT_ELEMENT_NAME);
      xmlCRL.setValue(Base64Support.encode(crl.getEncoded(), true));
      return xmlCRL;
   }

   @Nonnull
   public static X509SubjectName buildX509SubjectName(@Nullable String subjectName) {
      XMLObjectBuilder xmlSubjectNameBuilder = XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilder(X509SubjectName.DEFAULT_ELEMENT_NAME);
      X509SubjectName xmlSubjectName = (X509SubjectName)((XMLObjectBuilder)Constraint.isNotNull(xmlSubjectNameBuilder, "X509SubjectName builder not available")).buildObject(X509SubjectName.DEFAULT_ELEMENT_NAME);
      xmlSubjectName.setValue(subjectName);
      return xmlSubjectName;
   }

   @Nonnull
   public static X509IssuerSerial buildX509IssuerSerial(@Nullable String issuerName, @Nullable BigInteger serialNumber) {
      XMLObjectBuilderFactory builderFactory = XMLObjectProviderRegistrySupport.getBuilderFactory();
      XMLObjectBuilder xmlIssuerNameBuilder = builderFactory.getBuilder(X509IssuerName.DEFAULT_ELEMENT_NAME);
      X509IssuerName xmlIssuerName = (X509IssuerName)((XMLObjectBuilder)Constraint.isNotNull(xmlIssuerNameBuilder, "X509IssuerName builder not available")).buildObject(X509IssuerName.DEFAULT_ELEMENT_NAME);
      xmlIssuerName.setValue(issuerName);
      XMLObjectBuilder xmlSerialNumberBuilder = builderFactory.getBuilder(X509SerialNumber.DEFAULT_ELEMENT_NAME);
      X509SerialNumber xmlSerialNumber = (X509SerialNumber)((XMLObjectBuilder)Constraint.isNotNull(xmlSerialNumberBuilder, "X509SerialNumber builder not available")).buildObject(X509SerialNumber.DEFAULT_ELEMENT_NAME);
      xmlSerialNumber.setValue(serialNumber);
      XMLObjectBuilder xmlIssuerSerialBuilder = builderFactory.getBuilder(X509IssuerSerial.DEFAULT_ELEMENT_NAME);
      X509IssuerSerial xmlIssuerSerial = (X509IssuerSerial)((XMLObjectBuilder)Constraint.isNotNull(xmlIssuerSerialBuilder, "X509IssuerSerial builder not available")).buildObject(X509IssuerSerial.DEFAULT_ELEMENT_NAME);
      xmlIssuerSerial.setX509IssuerName(xmlIssuerName);
      xmlIssuerSerial.setX509SerialNumber(xmlSerialNumber);
      return xmlIssuerSerial;
   }

   @Nullable
   public static X509SKI buildX509SKI(@Nonnull java.security.cert.X509Certificate javaCert) {
      byte[] skiPlainValue = X509Support.getSubjectKeyIdentifier(javaCert);
      if (skiPlainValue != null && skiPlainValue.length != 0) {
         XMLObjectBuilder xmlSKIBuilder = XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilder(X509SKI.DEFAULT_ELEMENT_NAME);
         X509SKI xmlSKI = (X509SKI)((XMLObjectBuilder)Constraint.isNotNull(xmlSKIBuilder, "X509SKI builder not available")).buildObject(X509SKI.DEFAULT_ELEMENT_NAME);
         xmlSKI.setValue(Base64Support.encode(skiPlainValue, true));
         return xmlSKI;
      } else {
         return null;
      }
   }

   @Nonnull
   public static X509Digest buildX509Digest(@Nonnull java.security.cert.X509Certificate javaCert, @Nonnull String algorithmURI) throws NoSuchAlgorithmException, CertificateEncodingException {
      Constraint.isNotNull(javaCert, "Certificate cannot be null");
      String jceAlg = AlgorithmSupport.getAlgorithmID(algorithmURI);
      if (jceAlg == null) {
         throw new NoSuchAlgorithmException("No JCE algorithm found for " + algorithmURI);
      } else {
         MessageDigest md = MessageDigest.getInstance(jceAlg);
         byte[] hash = md.digest(javaCert.getEncoded());
         XMLObjectBuilder builder = XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilder(X509Digest.DEFAULT_ELEMENT_NAME);
         X509Digest xmlDigest = (X509Digest)((XMLObjectBuilder)Constraint.isNotNull(builder, "X509Digest builder not available")).buildObject(X509Digest.DEFAULT_ELEMENT_NAME);
         xmlDigest.setAlgorithm(algorithmURI);
         xmlDigest.setValue(Base64Support.encode(hash, true));
         return xmlDigest;
      }
   }

   public static void addPublicKey(@Nonnull KeyInfo keyInfo, @Nullable PublicKey pk) {
      Constraint.isNotNull(keyInfo, "KeyInfo cannot be null");
      XMLObjectBuilder keyValueBuilder = XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilder(KeyValue.DEFAULT_ELEMENT_NAME);
      KeyValue keyValue = (KeyValue)((XMLObjectBuilder)Constraint.isNotNull(keyValueBuilder, "KeyValue builder not available")).buildObject(KeyValue.DEFAULT_ELEMENT_NAME);
      if (pk instanceof RSAPublicKey) {
         keyValue.setRSAKeyValue(buildRSAKeyValue((RSAPublicKey)pk));
      } else {
         if (!(pk instanceof DSAPublicKey)) {
            throw new IllegalArgumentException("Only RSAPublicKey and DSAPublicKey are supported");
         }

         keyValue.setDSAKeyValue(buildDSAKeyValue((DSAPublicKey)pk));
      }

      keyInfo.getKeyValues().add(keyValue);
   }

   @Nonnull
   public static RSAKeyValue buildRSAKeyValue(@Nonnull RSAPublicKey rsaPubKey) {
      Constraint.isNotNull(rsaPubKey, "RSA public key cannot be null");
      XMLObjectBuilderFactory builderFactory = XMLObjectProviderRegistrySupport.getBuilderFactory();
      XMLObjectBuilder rsaKeyValueBuilder = builderFactory.getBuilder(RSAKeyValue.DEFAULT_ELEMENT_NAME);
      RSAKeyValue rsaKeyValue = (RSAKeyValue)((XMLObjectBuilder)Constraint.isNotNull(rsaKeyValueBuilder, "RSAKeyValue builder not available")).buildObject(RSAKeyValue.DEFAULT_ELEMENT_NAME);
      XMLObjectBuilder modulusBuilder = builderFactory.getBuilder(Modulus.DEFAULT_ELEMENT_NAME);
      Modulus modulus = (Modulus)((XMLObjectBuilder)Constraint.isNotNull(modulusBuilder, "Modulus builder not available")).buildObject(Modulus.DEFAULT_ELEMENT_NAME);
      XMLObjectBuilder exponentBuilder = builderFactory.getBuilder(Exponent.DEFAULT_ELEMENT_NAME);
      Exponent exponent = (Exponent)((XMLObjectBuilder)Constraint.isNotNull(exponentBuilder, "Exponent builder not available")).buildObject(Exponent.DEFAULT_ELEMENT_NAME);
      modulus.setValueBigInt(rsaPubKey.getModulus());
      rsaKeyValue.setModulus(modulus);
      exponent.setValueBigInt(rsaPubKey.getPublicExponent());
      rsaKeyValue.setExponent(exponent);
      return rsaKeyValue;
   }

   @Nonnull
   public static DSAKeyValue buildDSAKeyValue(@Nonnull DSAPublicKey dsaPubKey) {
      Constraint.isNotNull(dsaPubKey, "DSA public key cannot be null");
      XMLObjectBuilderFactory builderFactory = XMLObjectProviderRegistrySupport.getBuilderFactory();
      XMLObjectBuilder dsaKeyValueBuilder = builderFactory.getBuilder(DSAKeyValue.DEFAULT_ELEMENT_NAME);
      DSAKeyValue dsaKeyValue = (DSAKeyValue)((XMLObjectBuilder)Constraint.isNotNull(dsaKeyValueBuilder, "DSAKeyValue builder not available")).buildObject(DSAKeyValue.DEFAULT_ELEMENT_NAME);
      XMLObjectBuilder yBuilder = builderFactory.getBuilder(Y.DEFAULT_ELEMENT_NAME);
      XMLObjectBuilder gBuilder = builderFactory.getBuilder(G.DEFAULT_ELEMENT_NAME);
      XMLObjectBuilder pBuilder = builderFactory.getBuilder(P.DEFAULT_ELEMENT_NAME);
      XMLObjectBuilder qBuilder = builderFactory.getBuilder(Q.DEFAULT_ELEMENT_NAME);
      Y y = (Y)((XMLObjectBuilder)Constraint.isNotNull(yBuilder, "Y builder not available")).buildObject(Y.DEFAULT_ELEMENT_NAME);
      G g = (G)((XMLObjectBuilder)Constraint.isNotNull(gBuilder, "G builder not available")).buildObject(G.DEFAULT_ELEMENT_NAME);
      P p = (P)((XMLObjectBuilder)Constraint.isNotNull(pBuilder, "P builder not available")).buildObject(P.DEFAULT_ELEMENT_NAME);
      Q q = (Q)((XMLObjectBuilder)Constraint.isNotNull(qBuilder, "Q builder not available")).buildObject(Q.DEFAULT_ELEMENT_NAME);
      y.setValueBigInt(dsaPubKey.getY());
      dsaKeyValue.setY(y);
      g.setValueBigInt(dsaPubKey.getParams().getG());
      dsaKeyValue.setG(g);
      p.setValueBigInt(dsaPubKey.getParams().getP());
      dsaKeyValue.setP(p);
      q.setValueBigInt(dsaPubKey.getParams().getQ());
      dsaKeyValue.setQ(q);
      return dsaKeyValue;
   }

   public static void addDEREncodedPublicKey(@Nonnull KeyInfo keyInfo, @Nonnull PublicKey pk) throws NoSuchAlgorithmException, InvalidKeySpecException {
      Constraint.isNotNull(keyInfo, "KeyInfo cannot be null");
      Constraint.isNotNull(pk, "Public key cannot be null");
      XMLObjectBuilder builder = XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilder(DEREncodedKeyValue.DEFAULT_ELEMENT_NAME);
      DEREncodedKeyValue keyValue = (DEREncodedKeyValue)((XMLObjectBuilder)Constraint.isNotNull(builder, "DEREncodedKeyValue builder not available")).buildObject(DEREncodedKeyValue.DEFAULT_ELEMENT_NAME);
      KeyFactory keyFactory = KeyFactory.getInstance(pk.getAlgorithm());
      X509EncodedKeySpec keySpec = (X509EncodedKeySpec)keyFactory.getKeySpec(pk, X509EncodedKeySpec.class);
      keyValue.setValue(Base64Support.encode(keySpec.getEncoded(), true));
      keyInfo.getDEREncodedKeyValues().add(keyValue);
   }

   @Nonnull
   public static List getPublicKeys(@Nullable KeyInfo keyInfo) throws KeyException {
      List keys = new LinkedList();
      if (keyInfo == null) {
         return keys;
      } else {
         Iterator var2 = keyInfo.getKeyValues().iterator();

         PublicKey newKey;
         while(var2.hasNext()) {
            KeyValue keyDescriptor = (KeyValue)var2.next();
            newKey = getKey(keyDescriptor);
            if (newKey != null) {
               keys.add(newKey);
            }
         }

         var2 = keyInfo.getDEREncodedKeyValues().iterator();

         while(var2.hasNext()) {
            DEREncodedKeyValue keyDescriptor = (DEREncodedKeyValue)var2.next();
            newKey = getKey(keyDescriptor);
            if (newKey != null) {
               keys.add(newKey);
            }
         }

         return keys;
      }
   }

   @Nullable
   public static PublicKey getKey(@Nonnull KeyValue keyValue) throws KeyException {
      Constraint.isNotNull(keyValue, "KeyValue cannot be null");
      if (keyValue.getDSAKeyValue() != null) {
         return getDSAKey(keyValue.getDSAKeyValue());
      } else {
         return keyValue.getRSAKeyValue() != null ? getRSAKey(keyValue.getRSAKeyValue()) : null;
      }
   }

   @Nonnull
   public static PublicKey getDSAKey(@Nonnull DSAKeyValue keyDescriptor) throws KeyException {
      if (!hasCompleteDSAParams(keyDescriptor)) {
         throw new KeyException("DSAKeyValue element did not contain at least one of DSA parameters P, Q or G");
      } else {
         BigInteger gComponent = keyDescriptor.getG().getValueBigInt();
         BigInteger pComponent = keyDescriptor.getP().getValueBigInt();
         BigInteger qComponent = keyDescriptor.getQ().getValueBigInt();
         DSAParams dsaParams = new DSAParameterSpec(pComponent, qComponent, gComponent);
         return getDSAKey(keyDescriptor, dsaParams);
      }
   }

   @Nonnull
   public static PublicKey getDSAKey(@Nonnull DSAKeyValue keyDescriptor, @Nonnull DSAParams dsaParams) throws KeyException {
      Constraint.isNotNull(keyDescriptor, "DSAKeyValue cannot be null");
      Constraint.isNotNull(dsaParams, "DSAParams cannot be null");
      BigInteger yComponent = keyDescriptor.getY().getValueBigInt();
      DSAPublicKeySpec keySpec = new DSAPublicKeySpec(yComponent, dsaParams.getP(), dsaParams.getQ(), dsaParams.getG());
      return buildKey(keySpec, "DSA");
   }

   public static boolean hasCompleteDSAParams(@Nullable DSAKeyValue keyDescriptor) {
      return keyDescriptor != null && keyDescriptor.getG() != null && !Strings.isNullOrEmpty(keyDescriptor.getG().getValue()) && keyDescriptor.getP() != null && !Strings.isNullOrEmpty(keyDescriptor.getP().getValue()) && keyDescriptor.getQ() != null && !Strings.isNullOrEmpty(keyDescriptor.getQ().getValue());
   }

   @Nonnull
   public static PublicKey getRSAKey(@Nonnull RSAKeyValue keyDescriptor) throws KeyException {
      Constraint.isNotNull(keyDescriptor, "RSAKeyValue cannot be null");
      BigInteger modulus = keyDescriptor.getModulus().getValueBigInt();
      BigInteger exponent = keyDescriptor.getExponent().getValueBigInt();
      RSAPublicKeySpec keySpec = new RSAPublicKeySpec(modulus, exponent);
      return buildKey(keySpec, "RSA");
   }

   @Nonnull
   public static final BigInteger decodeBigIntegerFromCryptoBinary(@Nonnull String base64Value) {
      return new BigInteger(1, Base64Support.decode(base64Value));
   }

   @Nonnull
   public static final String encodeCryptoBinaryFromBigInteger(@Nonnull BigInteger bigInt) {
      Constraint.isNotNull(bigInt, "BigInteger cannot be null");
      byte[] bigIntBytes = Base64.encode(bigInt, bigInt.bitLength());
      return Base64Support.encode(bigIntBytes, false);
   }

   @Nonnull
   protected static PublicKey buildKey(@Nonnull KeySpec keySpec, @Nonnull String keyAlgorithm) throws KeyException {
      Logger log = getLogger();

      try {
         KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm);
         return keyFactory.generatePublic(keySpec);
      } catch (NoSuchAlgorithmException var5) {
         String msg = keyAlgorithm + " algorithm is not supported by this JCE";
         log.error(msg, var5);
         throw new KeyException(msg, var5);
      } catch (InvalidKeySpecException var6) {
         log.error("Invalid key information", var6);
         throw new KeyException("Invalid key information", var6);
      }
   }

   @Nonnull
   public static PublicKey getKey(@Nonnull DEREncodedKeyValue keyValue) throws KeyException {
      String[] supportedKeyTypes = new String[]{"RSA", "DSA", "EC"};
      Constraint.isNotNull(keyValue, "DEREncodedKeyValue cannot be null");
      if (keyValue.getValue() == null) {
         throw new KeyException("No data found in key value element");
      } else {
         byte[] encodedKey = Base64Support.decode(keyValue.getValue());
         String[] var3 = supportedKeyTypes;
         int var4 = supportedKeyTypes.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String keyType = var3[var5];

            try {
               KeyFactory keyFactory = KeyFactory.getInstance(keyType);
               X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encodedKey);
               PublicKey publicKey = keyFactory.generatePublic(keySpec);
               if (publicKey != null) {
                  return publicKey;
               }
            } catch (InvalidKeySpecException | NoSuchAlgorithmException var10) {
            }
         }

         throw new KeyException("DEREncodedKeyValue did not contain a supported key type");
      }
   }

   @Nonnull
   protected static CertificateFactory getX509CertFactory() throws CertificateException {
      if (x509CertFactory == null) {
         x509CertFactory = CertificateFactory.getInstance("X.509");
      }

      return x509CertFactory;
   }

   @Nullable
   public static KeyInfoGenerator getKeyInfoGenerator(@Nonnull Credential credential, @Nonnull NamedKeyInfoGeneratorManager manager, @Nullable String keyInfoProfileName) {
      Constraint.isNotNull(credential, "Credential may not be null");
      Constraint.isNotNull(manager, "NamedKeyInfoGeneratorManager may not be null");
      Logger log = getLogger();
      KeyInfoGeneratorFactory factory = null;
      if (keyInfoProfileName != null) {
         log.trace("Resolving KeyInfoGeneratorFactory using profile name: {}", keyInfoProfileName);
         factory = manager.getFactory(keyInfoProfileName, credential);
      } else {
         log.trace("Resolving KeyInfoGeneratorFactory using default manager: {}", keyInfoProfileName);
         factory = manager.getDefaultManager().getFactory(credential);
      }

      if (factory != null) {
         log.trace("Found KeyInfoGeneratorFactory: {}", factory.getClass().getName());
         return factory.newInstance();
      } else {
         log.trace("Unable to resolve KeyInfoGeneratorFactory for credential");
         return null;
      }
   }

   @Nonnull
   private static Logger getLogger() {
      return LoggerFactory.getLogger(KeyInfoSupport.class);
   }
}
