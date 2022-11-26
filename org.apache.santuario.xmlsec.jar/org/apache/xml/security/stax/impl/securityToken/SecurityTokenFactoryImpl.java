package org.apache.xml.security.stax.impl.securityToken;

import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Map;
import org.apache.xml.security.binding.xmldsig.DSAKeyValueType;
import org.apache.xml.security.binding.xmldsig.KeyInfoType;
import org.apache.xml.security.binding.xmldsig.KeyValueType;
import org.apache.xml.security.binding.xmldsig.RSAKeyValueType;
import org.apache.xml.security.binding.xmldsig.X509DataType;
import org.apache.xml.security.binding.xmldsig.X509IssuerSerialType;
import org.apache.xml.security.binding.xmldsig11.ECKeyValueType;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.InboundSecurityContext;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.ext.XMLSecurityProperties;
import org.apache.xml.security.stax.ext.XMLSecurityUtils;
import org.apache.xml.security.stax.impl.util.IDGenerator;
import org.apache.xml.security.stax.securityToken.InboundSecurityToken;
import org.apache.xml.security.stax.securityToken.SecurityTokenConstants;
import org.apache.xml.security.stax.securityToken.SecurityTokenFactory;
import org.apache.xml.security.utils.RFC2253Parser;
import org.apache.xml.security.utils.UnsyncByteArrayInputStream;

public class SecurityTokenFactoryImpl extends SecurityTokenFactory {
   public InboundSecurityToken getSecurityToken(KeyInfoType keyInfoType, SecurityTokenConstants.KeyUsage keyUsage, XMLSecurityProperties securityProperties, InboundSecurityContext inboundSecurityContext) throws XMLSecurityException {
      if (keyInfoType != null) {
         X509DataType x509DataType = (X509DataType)XMLSecurityUtils.getQNameType(keyInfoType.getContent(), XMLSecurityConstants.TAG_dsig_X509Data);
         if (x509DataType != null) {
            return getSecurityToken(x509DataType, securityProperties, inboundSecurityContext, keyUsage);
         }

         KeyValueType keyValueType = (KeyValueType)XMLSecurityUtils.getQNameType(keyInfoType.getContent(), XMLSecurityConstants.TAG_dsig_KeyValue);
         if (keyValueType != null) {
            return getSecurityToken(keyValueType, securityProperties, inboundSecurityContext, keyUsage);
         }

         String keyName = (String)XMLSecurityUtils.getQNameType(keyInfoType.getContent(), XMLSecurityConstants.TAG_dsig_KeyName);
         if (keyName != null) {
            KeyNameSecurityToken token = this.getSecurityToken(keyName, securityProperties, inboundSecurityContext, keyUsage);
            return token;
         }
      }

      if (SecurityTokenConstants.KeyUsage_Signature_Verification.equals(keyUsage) && securityProperties.getSignatureVerificationKey() != null) {
         return this.getDefaultSecurityToken(securityProperties, inboundSecurityContext, keyUsage);
      } else if (SecurityTokenConstants.KeyUsage_Decryption.equals(keyUsage) && securityProperties.getDecryptionKey() != null) {
         return this.getDefaultSecurityToken(securityProperties, inboundSecurityContext, keyUsage);
      } else {
         throw new XMLSecurityException("stax.noKey", new Object[]{keyUsage});
      }
   }

   private InboundSecurityToken getDefaultSecurityToken(XMLSecurityProperties securityProperties, InboundSecurityContext inboundSecurityContext, SecurityTokenConstants.KeyUsage keyUsage) {
      AbstractInboundSecurityToken token = new AbstractInboundSecurityToken(inboundSecurityContext, IDGenerator.generateID((String)null), SecurityTokenConstants.KeyIdentifier_NoKeyInfo, false) {
         public SecurityTokenConstants.TokenType getTokenType() {
            return SecurityTokenConstants.DefaultToken;
         }
      };
      setTokenKey(securityProperties, keyUsage, token);
      return token;
   }

   private KeyNameSecurityToken getSecurityToken(String keyName, XMLSecurityProperties securityProperties, InboundSecurityContext inboundSecurityContext, SecurityTokenConstants.KeyUsage keyUsage) throws XMLSecurityException {
      KeyNameSecurityToken token = new KeyNameSecurityToken(keyName, inboundSecurityContext);
      if (SecurityTokenConstants.KeyUsage_Signature_Verification.equals(keyUsage) && securityProperties.getSignatureVerificationKey() == null) {
         Map keyNameMap = securityProperties.getKeyNameMap();
         Key key = (Key)keyNameMap.get(keyName);
         if (key == null) {
            throw new XMLSecurityException("stax.keyNotFoundForName", new Object[]{keyName});
         }

         if (!(key instanceof PublicKey)) {
            throw new XMLSecurityException("stax.keyTypeNotSupported", new Object[]{key.getClass().getSimpleName()});
         }

         token.setPublicKey((PublicKey)key);
      }

      setTokenKey(securityProperties, keyUsage, token);
      return token;
   }

   private static InboundSecurityToken getSecurityToken(KeyValueType keyValueType, XMLSecurityProperties securityProperties, InboundSecurityContext inboundSecurityContext, SecurityTokenConstants.KeyUsage keyUsage) throws XMLSecurityException {
      RSAKeyValueType rsaKeyValueType = (RSAKeyValueType)XMLSecurityUtils.getQNameType(keyValueType.getContent(), XMLSecurityConstants.TAG_dsig_RSAKeyValue);
      if (rsaKeyValueType != null) {
         RsaKeyValueSecurityToken token = new RsaKeyValueSecurityToken(rsaKeyValueType, inboundSecurityContext);
         setTokenKey(securityProperties, keyUsage, token);
         return token;
      } else {
         DSAKeyValueType dsaKeyValueType = (DSAKeyValueType)XMLSecurityUtils.getQNameType(keyValueType.getContent(), XMLSecurityConstants.TAG_dsig_DSAKeyValue);
         if (dsaKeyValueType != null) {
            DsaKeyValueSecurityToken token = new DsaKeyValueSecurityToken(dsaKeyValueType, inboundSecurityContext);
            setTokenKey(securityProperties, keyUsage, token);
            return token;
         } else {
            ECKeyValueType ecKeyValueType = (ECKeyValueType)XMLSecurityUtils.getQNameType(keyValueType.getContent(), XMLSecurityConstants.TAG_dsig11_ECKeyValue);
            if (ecKeyValueType != null) {
               ECKeyValueSecurityToken token = new ECKeyValueSecurityToken(ecKeyValueType, inboundSecurityContext);
               setTokenKey(securityProperties, keyUsage, token);
               return token;
            } else {
               throw new XMLSecurityException("stax.unsupportedKeyValue");
            }
         }
      }
   }

   private static InboundSecurityToken getSecurityToken(X509DataType x509DataType, XMLSecurityProperties securityProperties, InboundSecurityContext inboundSecurityContext, SecurityTokenConstants.KeyUsage keyUsage) throws XMLSecurityException {
      byte[] certBytes = (byte[])XMLSecurityUtils.getQNameType(x509DataType.getX509IssuerSerialOrX509SKIOrX509SubjectName(), XMLSecurityConstants.TAG_dsig_X509Certificate);
      if (certBytes != null) {
         X509Certificate cert = getCertificateFromBytes(certBytes);
         SecurityTokenConstants.TokenType tokenType = SecurityTokenConstants.X509V3Token;
         if (cert.getVersion() == 1) {
            tokenType = SecurityTokenConstants.X509V1Token;
         }

         X509SecurityToken token = new X509SecurityToken(tokenType, inboundSecurityContext, IDGenerator.generateID((String)null), SecurityTokenConstants.KeyIdentifier_X509KeyIdentifier, true);
         token.setX509Certificates(new X509Certificate[]{cert});
         setTokenKey(securityProperties, keyUsage, token);
         return token;
      } else {
         X509IssuerSerialType issuerSerialType = (X509IssuerSerialType)XMLSecurityUtils.getQNameType(x509DataType.getX509IssuerSerialOrX509SKIOrX509SubjectName(), XMLSecurityConstants.TAG_dsig_X509IssuerSerial);
         if (issuerSerialType != null) {
            if (issuerSerialType.getX509IssuerName() != null && issuerSerialType.getX509SerialNumber() != null && (!SecurityTokenConstants.KeyUsage_Signature_Verification.equals(keyUsage) || securityProperties.getSignatureVerificationKey() != null) && (!SecurityTokenConstants.KeyUsage_Decryption.equals(keyUsage) || securityProperties.getDecryptionKey() != null)) {
               X509IssuerSerialSecurityToken token = new X509IssuerSerialSecurityToken(SecurityTokenConstants.X509V3Token, inboundSecurityContext, IDGenerator.generateID((String)null));
               token.setIssuerName(issuerSerialType.getX509IssuerName());
               token.setSerialNumber(issuerSerialType.getX509SerialNumber());
               setTokenKey(securityProperties, keyUsage, token);
               return token;
            } else {
               throw new XMLSecurityException("stax.noKey", new Object[]{keyUsage});
            }
         } else {
            byte[] skiBytes = (byte[])XMLSecurityUtils.getQNameType(x509DataType.getX509IssuerSerialOrX509SKIOrX509SubjectName(), XMLSecurityConstants.TAG_dsig_X509SKI);
            if (skiBytes != null) {
               if ((!SecurityTokenConstants.KeyUsage_Signature_Verification.equals(keyUsage) || securityProperties.getSignatureVerificationKey() != null) && (!SecurityTokenConstants.KeyUsage_Decryption.equals(keyUsage) || securityProperties.getDecryptionKey() != null)) {
                  X509SKISecurityToken token = new X509SKISecurityToken(SecurityTokenConstants.X509V3Token, inboundSecurityContext, IDGenerator.generateID((String)null));
                  token.setSkiBytes(skiBytes);
                  setTokenKey(securityProperties, keyUsage, token);
                  return token;
               } else {
                  throw new XMLSecurityException("stax.noKey", new Object[]{keyUsage});
               }
            } else {
               String subjectName = (String)XMLSecurityUtils.getQNameType(x509DataType.getX509IssuerSerialOrX509SKIOrX509SubjectName(), XMLSecurityConstants.TAG_dsig_X509SubjectName);
               if (subjectName == null) {
                  throw new XMLSecurityException("stax.noKey", new Object[]{keyUsage});
               } else if ((!SecurityTokenConstants.KeyUsage_Signature_Verification.equals(keyUsage) || securityProperties.getSignatureVerificationKey() != null) && (!SecurityTokenConstants.KeyUsage_Decryption.equals(keyUsage) || securityProperties.getDecryptionKey() != null)) {
                  String normalizedSubjectName = RFC2253Parser.normalize(subjectName);
                  X509SubjectNameSecurityToken token = new X509SubjectNameSecurityToken(SecurityTokenConstants.X509V3Token, inboundSecurityContext, IDGenerator.generateID((String)null));
                  token.setSubjectName(normalizedSubjectName);
                  setTokenKey(securityProperties, keyUsage, token);
                  return token;
               } else {
                  throw new XMLSecurityException("stax.noKey", new Object[]{keyUsage});
               }
            }
         }
      }
   }

   private static void setTokenKey(XMLSecurityProperties securityProperties, SecurityTokenConstants.KeyUsage keyUsage, AbstractInboundSecurityToken token) {
      Key key = null;
      if (SecurityTokenConstants.KeyUsage_Signature_Verification.equals(keyUsage)) {
         key = securityProperties.getSignatureVerificationKey();
      } else if (SecurityTokenConstants.KeyUsage_Decryption.equals(keyUsage)) {
         key = securityProperties.getDecryptionKey();
      }

      if (key instanceof PublicKey && !SecurityTokenConstants.KeyValueToken.equals(token.getTokenType())) {
         token.setPublicKey((PublicKey)key);
      } else {
         token.setSecretKey("", key);
      }

   }

   private static X509Certificate getCertificateFromBytes(byte[] data) throws XMLSecurityException {
      try {
         InputStream in = new UnsyncByteArrayInputStream(data);
         Throwable var2 = null;

         X509Certificate var4;
         try {
            CertificateFactory factory = CertificateFactory.getInstance("X.509");
            var4 = (X509Certificate)factory.generateCertificate(in);
         } catch (Throwable var14) {
            var2 = var14;
            throw var14;
         } finally {
            if (var2 != null) {
               try {
                  in.close();
               } catch (Throwable var13) {
                  var2.addSuppressed(var13);
               }
            } else {
               in.close();
            }

         }

         return var4;
      } catch (IOException | CertificateException var16) {
         throw new XMLSecurityException(var16);
      }
   }
}
