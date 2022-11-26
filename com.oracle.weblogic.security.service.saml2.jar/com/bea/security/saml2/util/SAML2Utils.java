package com.bea.security.saml2.util;

import com.bea.common.security.saml2.ConfigValidationException;
import com.bea.common.security.saml2.SingleSignOnServicesConfigSpi;
import com.bea.common.security.saml2.utils.SAML2SchemaValidator;
import com.bea.security.saml2.Saml2Logger;
import com.bea.security.saml2.config.SAML2ConfigSpi;
import com.bea.security.saml2.providers.registry.Partner;
import com.bea.security.saml2.providers.registry.WSSIdPPartner;
import com.bea.security.saml2.providers.registry.WSSPartner;
import com.bea.security.saml2.providers.registry.WebSSOIdPPartner;
import com.bea.security.saml2.providers.registry.WebSSOPartner;
import com.bea.security.saml2.providers.registry.WebSSOSPPartner;
import com.bea.security.saml2.service.SAML2Exception;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.crypto.Cipher;
import javax.security.auth.Subject;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import net.shibboleth.utilities.java.support.xml.ParserPool;
import net.shibboleth.utilities.java.support.xml.XMLParserException;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.Marshaller;
import org.opensaml.core.xml.io.MarshallerFactory;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.io.Unmarshaller;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.common.SignableSAMLObject;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.EncryptedAssertion;
import org.opensaml.saml.saml2.core.Response;
import org.opensaml.saml.saml2.encryption.Decrypter;
import org.opensaml.saml.saml2.encryption.EncryptedElementTypeEncryptedKeyResolver;
import org.opensaml.saml.saml2.encryption.Encrypter;
import org.opensaml.saml.saml2.metadata.ContactPersonTypeEnumeration;
import org.opensaml.saml.saml2.metadata.EncryptionMethod;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.security.credential.BasicCredential;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.CredentialSupport;
import org.opensaml.security.credential.UsageType;
import org.opensaml.xmlsec.EncryptionParameters;
import org.opensaml.xmlsec.encryption.EncryptedKey;
import org.opensaml.xmlsec.encryption.support.ChainingEncryptedKeyResolver;
import org.opensaml.xmlsec.encryption.support.DataEncryptionParameters;
import org.opensaml.xmlsec.encryption.support.DecryptionException;
import org.opensaml.xmlsec.encryption.support.EncryptionException;
import org.opensaml.xmlsec.encryption.support.InlineEncryptedKeyResolver;
import org.opensaml.xmlsec.encryption.support.KeyEncryptionParameters;
import org.opensaml.xmlsec.encryption.support.SimpleKeyInfoReferenceEncryptedKeyResolver;
import org.opensaml.xmlsec.encryption.support.SimpleRetrievalMethodEncryptedKeyResolver;
import org.opensaml.xmlsec.keyinfo.KeyInfoCredentialResolver;
import org.opensaml.xmlsec.keyinfo.impl.StaticKeyInfoCredentialResolver;
import org.opensaml.xmlsec.signature.KeyInfo;
import org.opensaml.xmlsec.signature.Signature;
import org.opensaml.xmlsec.signature.X509Certificate;
import org.opensaml.xmlsec.signature.X509Data;
import org.opensaml.xmlsec.signature.support.SignatureException;
import org.opensaml.xmlsec.signature.support.SignatureValidator;
import org.opensaml.xmlsec.signature.support.Signer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import weblogic.management.utils.InvalidParameterException;
import weblogic.security.service.ContextHandler;
import weblogic.utils.encoders.BASE64Decoder;
import weblogic.utils.encoders.BASE64Encoder;

public class SAML2Utils {
   public static final String QUERY_PARAMS_DELIMITER = "&";
   public static final String NO_QUERY_PARAMS_DELIMITER = "?";
   private static final String UNDER_SCORE = "_";
   private static final String DEFAULT_ENCODING = "UTF-8";
   public static final String ASSERTION_TYPE_BEARER = "urn:oasis:names:tc:SAML:2.0:cm:bearer";
   public static final String ASSERTION_TYPE_HOLDER_OF_KEY = "urn:oasis:names:tc:SAML:2.0:cm:holder-of-key";
   public static final String ASSERTION_TYPE_SENDER_VOUCHES = "urn:oasis:names:tc:SAML:2.0:cm:sender-vouches";
   public static final boolean ALLOW_EXPIRE_CERTS = Boolean.getBoolean("com.bea.common.security.saml2.allowExpiredCerts");
   public static final boolean ENABLE_URL_REWRITING = Boolean.getBoolean("com.bea.common.security.saml2.enableURLRewriting");
   public static final boolean USE_SHA1_SIGALGO = Boolean.getBoolean("com.bea.common.security.saml2.useSHA1SigAlgorithm");
   private static final String[] SUPPORTED_CEK_ALGORITHMS = new String[]{"http://www.w3.org/2009/xmlenc11#aes256-gcm", "http://www.w3.org/2009/xmlenc11#aes192-gcm", "http://www.w3.org/2009/xmlenc11#aes128-gcm", "http://www.w3.org/2001/04/xmlenc#aes256-cbc", "http://www.w3.org/2001/04/xmlenc#aes192-cbc", "http://www.w3.org/2001/04/xmlenc#aes128-cbc", "http://www.w3.org/2001/04/xmlenc#tripledes-cbc"};
   private static final int[] CEK_ALGORITHMS_KEY_LENGTHS = new int[]{256, 192, 128, 256, 192, 128};
   private static final String[] SUPPORTED_KEK_ALGORITHMS = new String[]{"http://www.w3.org/2009/xmlenc11#rsa-oaep", "http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p", "http://www.w3.org/2001/04/xmlenc#rsa-1_5"};
   public static final Map ALL_ALGORITHMS;
   private static final ChainingEncryptedKeyResolver CHAINING_ENCRYPTED_KEY_RESOLVER;
   private static final String AES = "AES";
   private static int MAX_AES_KEY_LENGTH_ALLOWED = 128;

   private static Map getAllAlgorithms() {
      Map allAlgorithms = new HashMap(SUPPORTED_CEK_ALGORITHMS.length + SUPPORTED_KEK_ALGORITHMS.length);
      String[] var1 = SUPPORTED_CEK_ALGORITHMS;
      int var2 = var1.length;

      int var3;
      String s;
      for(var3 = 0; var3 < var2; ++var3) {
         s = var1[var3];
         allAlgorithms.put(extractShortAlgName(s), s);
      }

      var1 = SUPPORTED_KEK_ALGORITHMS;
      var2 = var1.length;

      for(var3 = 0; var3 < var2; ++var3) {
         s = var1[var3];
         allAlgorithms.put(extractShortAlgName(s), s);
      }

      return Collections.unmodifiableMap(allAlgorithms);
   }

   private SAML2Utils() {
   }

   public static EntityDescriptor createSSODescriptor(String fileName) throws IOException, XMLParserException, SAXException {
      if (fileName != null && !fileName.trim().equals("")) {
         FileInputStream fis = new FileInputStream(fileName);
         ParserPool ppMgr = XMLObjectProviderRegistrySupport.getParserPool();

         try {
            Document doc = ppMgr.parse(fis);
            Element descriptor = doc.getDocumentElement();
            SAML2SchemaValidator.validateElement(descriptor);
            Unmarshaller unmarshaller = XMLObjectProviderRegistrySupport.getUnmarshallerFactory().getUnmarshaller(descriptor);
            return (EntityDescriptor)unmarshaller.unmarshall(descriptor);
         } catch (UnmarshallingException var6) {
            throw new XMLParserException("Unmarshalling failed when parsing descriptor: " + var6);
         } catch (ClassCastException var7) {
            throw new XMLParserException(Saml2Logger.getMetadataNotEntityDescriptor());
         }
      } else {
         throw new IllegalArgumentException("Null or empty filename");
      }
   }

   public static Assertion createAssertion(String assertion) throws XMLParserException {
      ParserPool ppMgr = XMLObjectProviderRegistrySupport.getParserPool();
      Document doc = null;

      try {
         doc = ppMgr.parse(new StringReader(assertion));
      } catch (XMLParserException var4) {
         throw new XMLParserException(var4);
      }

      Element samlElement = doc.getDocumentElement();
      return createAssertion(samlElement);
   }

   public static Assertion createAssertion(Element assertionElement) throws XMLParserException {
      try {
         Unmarshaller unmarshaller = XMLObjectProviderRegistrySupport.getUnmarshallerFactory().getUnmarshaller(assertionElement);
         return (Assertion)unmarshaller.unmarshall(assertionElement);
      } catch (UnmarshallingException var2) {
         throw new XMLParserException("Unmarshalling failed when parsing content: " + assertionElement + ": " + var2);
      }
   }

   public static SAMLObject signSamlObject(PrivateKey key, SignableSAMLObject samlobj) throws MarshallingException, SignatureException {
      return signSamlObject(key, samlobj, (List)null);
   }

   public static SAMLObject signSamlObject(PrivateKey key, SignableSAMLObject samlobj, List certs) throws MarshallingException, SignatureException {
      if (key != null && samlobj != null) {
         Signature sig = (Signature)XMLObjectSupport.buildXMLObject(Signature.DEFAULT_ELEMENT_NAME);
         BasicCredential credential = new SAMLPrivateKeyCredential(key);
         credential.setUsageType(UsageType.SIGNING);
         sig.setSigningCredential(credential);
         sig.setCanonicalizationAlgorithm("http://www.w3.org/2001/10/xml-exc-c14n#");
         if ("DSA".equals(key.getAlgorithm())) {
            if (USE_SHA1_SIGALGO) {
               sig.setSignatureAlgorithm("http://www.w3.org/2000/09/xmldsig#dsa-sha1");
            } else {
               sig.setSignatureAlgorithm("http://www.w3.org/2009/xmldsig11#dsa-sha256");
            }
         } else {
            if (!"RSA".equals(key.getAlgorithm())) {
               throw new MarshallingException("Invalid key algorithm found: " + key.getAlgorithm());
            }

            if (USE_SHA1_SIGALGO) {
               sig.setSignatureAlgorithm("http://www.w3.org/2000/09/xmldsig#rsa-sha1");
            } else {
               sig.setSignatureAlgorithm("http://www.w3.org/2001/04/xmldsig-more#rsa-sha256");
            }
         }

         if (certs != null && certs.size() > 0) {
            try {
               KeyInfo keyInfo = buildCertKeyInfo(certs);
               sig.setKeyInfo(keyInfo);
            } catch (CertificateEncodingException var7) {
               throw new MarshallingException("Unable to get the certificate's encoding", var7);
            }
         }

         samlobj.setSignature(sig);
         MarshallerFactory marshFactory = XMLObjectProviderRegistrySupport.getMarshallerFactory();
         Marshaller marsh = marshFactory.getMarshaller(samlobj);
         marsh.marshall(samlobj);
         Signer.signObject(sig);
         return samlobj;
      } else {
         throw new IllegalArgumentException("null parameter");
      }
   }

   public static KeyInfo buildCertKeyInfo(List certsToAdd) throws CertificateEncodingException {
      KeyInfo keyInfo = (KeyInfo)XMLObjectSupport.buildXMLObject(KeyInfo.DEFAULT_ELEMENT_NAME);
      if (certsToAdd != null && certsToAdd.size() > 0) {
         X509Data x509Data = (X509Data)XMLObjectSupport.buildXMLObject(X509Data.DEFAULT_ELEMENT_NAME);
         boolean certAdded = false;

         for(int i = 0; i < certsToAdd.size(); ++i) {
            X509Certificate samlCert = (X509Certificate)XMLObjectSupport.buildXMLObject(X509Certificate.DEFAULT_ELEMENT_NAME);
            samlCert.setValue(base64Encode(((java.security.cert.X509Certificate)certsToAdd.get(i)).getEncoded()));
            x509Data.getX509Certificates().add(samlCert);
            certAdded = true;
         }

         if (certAdded) {
            keyInfo.getX509Datas().add(x509Data);
         }
      }

      return keyInfo;
   }

   public static KeyInfo buildCertKeyInfo(Certificate certToAdd) throws CertificateEncodingException {
      List certList = new ArrayList(1);
      certList.add(certToAdd);
      return buildCertKeyInfo((List)certList);
   }

   public static void verifySamlObjectSignature(PublicKey key, SignableSAMLObject samlobj) throws SignatureException {
      if (samlobj == null) {
         throw new SignatureException("Signable SAML Object cannot be null.");
      } else {
         samlobj.getDOM().setIdAttributeNS((String)null, "ID", true);
         Signature sig = samlobj.getSignature();
         verifySamlObjectSignature(key, sig);
      }
   }

   public static void verifySamlObjectSignature(PublicKey key, Signature sig) throws SignatureException {
      if (key == null) {
         throw new SignatureException("No public key to verify the signature.");
      } else if (sig == null) {
         throw new SignatureException("the SAML object is not signed.");
      } else {
         SignatureValidator.validate(sig, CredentialSupport.getSimpleCredential(key, (PrivateKey)null));
      }
   }

   public static byte[] signString(byte[] tobeSigned, String algorithm, PrivateKey key) throws Exception {
      if (tobeSigned != null && tobeSigned.length != 0 && algorithm != null && !algorithm.equals("") && key != null) {
         java.security.Signature sig = java.security.Signature.getInstance(algorithm);
         sig.initSign(key);
         sig.update(tobeSigned);
         return sig.sign();
      } else {
         throw new IllegalArgumentException("null parameter");
      }
   }

   public static boolean verifyStringSignature(byte[] tobeVerified, byte[] signature, String algorithm, PublicKey key) throws Exception {
      if (tobeVerified != null && tobeVerified.length != 0 && signature != null && signature.length != 0 && algorithm != null && !algorithm.equals("") && key != null) {
         java.security.Signature sig = java.security.Signature.getInstance(algorithm);
         sig.initVerify(key);
         sig.update(tobeVerified);
         return sig.verify(signature);
      } else {
         throw new IllegalArgumentException("null parameter");
      }
   }

   public static String base64Encode(byte[] b) {
      BASE64Encoder encoder = new BASE64Encoder();
      return encoder.encodeBuffer(b);
   }

   public static String urlEncode(String s) throws UnsupportedEncodingException {
      return URLEncoder.encode(s, "UTF-8");
   }

   public static byte[] base64Decode(String inputString) throws IOException {
      BASE64Decoder decoder = new BASE64Decoder();
      return decoder.decodeBuffer(inputString);
   }

   public static String determineConfirmationMethodName(Partner partner) {
      return partner instanceof WSSPartner ? ((WSSPartner)partner).getConfirmationMethod() : "urn:oasis:names:tc:SAML:2.0:cm:bearer";
   }

   public static boolean isHoldOfKeyPartner(Partner partner) {
      String confirmationMethodName = determineConfirmationMethodName(partner);
      return confirmationMethodName.equals("urn:oasis:names:tc:SAML:2.0:cm:holder-of-key");
   }

   public static byte[] sha1Hash(String s) throws NoSuchAlgorithmException, UnsupportedEncodingException {
      MessageDigest md = MessageDigest.getInstance("SHA-1");
      md.update(s.getBytes("UTF-8"));
      return md.digest();
   }

   public static String getLocalSiteFromPublishedURL(String publishedURL) {
      if (publishedURL != null && !publishedURL.trim().equals("")) {
         int start = true;
         int start = publishedURL.indexOf("://");
         if (start < 0) {
            start = publishedURL.indexOf(":\\\\");
         }

         if (start >= 0) {
            start += "://".length();
         }

         int point = publishedURL.indexOf("/", start);
         int point1 = publishedURL.indexOf("\\", start);
         if (point > 0 && point1 > 0) {
            if (point1 < point) {
               point = point1;
            }
         } else if (point < 0) {
            point = point1;
         }

         if (point < 0) {
            point = publishedURL.length();
         }

         String siteURL = publishedURL.substring(0, point);
         if (siteURL.endsWith("/") || siteURL.endsWith("\\")) {
            siteURL = siteURL.substring(0, siteURL.length() - 1);
         }

         return siteURL;
      } else {
         return "";
      }
   }

   public static void validatePartner(Partner partner) throws InvalidParameterException {
      if (partner != null && partner.isEnabled()) {
         if (checkNull(partner.getName())) {
            throw new InvalidParameterException(Saml2Logger.getEmptyPartnerName());
         } else {
            if (partner instanceof WebSSOPartner) {
               validateWebSSOPartner((WebSSOPartner)partner);
            } else if (partner instanceof WSSPartner) {
               validateWSSPartner((WSSPartner)partner);
            }

         }
      }
   }

   private static void validateWSSPartner(WSSPartner partner) throws InvalidParameterException {
      if (checkNull(partner.getConfirmationMethod())) {
         throw new InvalidParameterException(Saml2Logger.getEmptyConfirmationMethod());
      } else if (partner instanceof WSSIdPPartner && checkNull(((WSSIdPPartner)partner).getIssuerURI())) {
         throw new InvalidParameterException(Saml2Logger.getEmptyIssuerURI());
      }
   }

   private static void validateWebSSOPartner(WebSSOPartner partner) throws InvalidParameterException {
      if (checkNull(partner.getEntityID())) {
         throw new InvalidParameterException(Saml2Logger.getEmptyPartnerEntityId());
      } else {
         String contactPersonType = partner.getContactPersonType();
         if (!checkNull(contactPersonType) && !ContactPersonTypeEnumeration.TECHNICAL.toString().equals(contactPersonType) && !ContactPersonTypeEnumeration.SUPPORT.toString().equals(contactPersonType) && !ContactPersonTypeEnumeration.ADMINISTRATIVE.toString().equals(contactPersonType) && !ContactPersonTypeEnumeration.BILLING.toString().equals(contactPersonType) && !ContactPersonTypeEnumeration.OTHER.toString().equals(contactPersonType)) {
            throw new IllegalArgumentException(Saml2Logger.getIllegalContactPersonType());
         } else {
            java.security.cert.X509Certificate cert = partner.getSSOSigningCert();
            validateCertificateDate(cert);
            String nameMapper;
            if (partner instanceof WebSSOIdPPartner) {
               WebSSOIdPPartner idp = (WebSSOIdPPartner)partner;
               if (idp.getSingleSignOnService() == null || idp.getSingleSignOnService().length == 0) {
                  throw new InvalidParameterException(Saml2Logger.getEmptySingleSignService());
               }

               if (idp.getSSOSigningCert() == null && idp.isWantArtifactRequestSigned()) {
                  throw new InvalidParameterException(Saml2Logger.getEmptySSOSigningCert());
               }

               nameMapper = idp.getIdentityProviderNameMapperClassname();
               if (nameMapper != null && !nameMapper.equals("")) {
                  try {
                     Class.forName(nameMapper);
                  } catch (ClassNotFoundException var7) {
                     throw new InvalidParameterException(Saml2Logger.getSAML2InvalidNameMapperClassName(nameMapper));
                  }
               }
            } else if (partner instanceof WebSSOSPPartner) {
               WebSSOSPPartner sp = (WebSSOSPPartner)partner;
               if (sp.getAssertionConsumerService() == null || sp.getAssertionConsumerService().length == 0) {
                  throw new InvalidParameterException(Saml2Logger.getEmptyAssertionConsumerServices());
               }

               if (sp.getSSOSigningCert() == null && (sp.isWantArtifactRequestSigned() || sp.isWantAuthnRequestsSigned())) {
                  throw new InvalidParameterException(Saml2Logger.getEmptySSOSigningCert());
               }

               validateCertificateDate(sp.getAssertionEncryptionCert());
               nameMapper = sp.getServiceProviderNameMapperClassname();
               if (nameMapper != null && !nameMapper.equals("")) {
                  try {
                     Class.forName(nameMapper);
                  } catch (ClassNotFoundException var6) {
                     throw new InvalidParameterException(Saml2Logger.getSAML2InvalidNameMapperClassName(nameMapper));
                  }
               }
            }

         }
      }
   }

   public static void validateCertificateDate(java.security.cert.X509Certificate x509Certificate) throws InvalidParameterException {
      if (x509Certificate != null && !ALLOW_EXPIRE_CERTS) {
         try {
            x509Certificate.checkValidity();
         } catch (CertificateExpiredException var2) {
            throw new InvalidParameterException("Certificate expired: " + x509Certificate.getSubjectDN(), var2);
         } catch (CertificateNotYetValidException var3) {
            throw new InvalidParameterException("Certificate date is not yet valid: " + x509Certificate.getSubjectDN(), var3);
         }
      }

   }

   public static boolean validateEndpointBinding(String bindingType) {
      return "HTTP/Redirect".equals(bindingType) || "HTTP/POST".equals(bindingType) || "HTTP/Artifact".equals(bindingType) || "SOAP".equals(bindingType);
   }

   public static void validateLocalConfig(SingleSignOnServicesConfigSpi config) throws ConfigValidationException {
      if (config != null && (config.isServiceProviderEnabled() || config.isIdentityProviderEnabled())) {
         if (checkNull(config.getEntityID())) {
            throw new ConfigValidationException(Saml2Logger.getNoSAML2EntityConfig());
         } else {
            String publishedUrl = config.getPublishedSiteURL();
            if (checkNull(publishedUrl)) {
               throw new ConfigValidationException(Saml2Logger.getNoSAML2PublishedSiteURLConfig());
            } else {
               URL url;
               try {
                  url = new URL(publishedUrl);
               } catch (MalformedURLException var4) {
                  throw new ConfigValidationException(Saml2Logger.getIllegalPublishedSiteURL(publishedUrl));
               }

               String appContext = url.getPath();
               if (appContext == null || appContext.trim().length() == 0) {
                  throw new ConfigValidationException(Saml2Logger.getIllegalPublishedSiteURL(publishedUrl));
               }
            }
         }
      }
   }

   private static boolean checkNull(String string) {
      return string == null || string.trim().length() == 0;
   }

   public static final boolean getBooleanContextElement(String name, ContextHandler handler) {
      if (name != null && handler != null) {
         Object obj = handler.getValue(name);
         if (obj != null && obj instanceof Boolean) {
            return (Boolean)obj;
         }
      }

      return false;
   }

   public static final String displaySubject(Subject subject) {
      if (subject == null) {
         return "Subject == null";
      } else {
         StringBuffer buf = new StringBuffer("Subject: ");
         Set principals = subject.getPrincipals();
         buf.append(principals.size());
         buf.append("\n");
         Object[] pArray = principals.toArray();

         for(int i = 0; i < pArray.length; ++i) {
            Principal p = (Principal)pArray[i];
            buf.append("\tPrincipal = ");
            buf.append(p.getClass());
            buf.append("(\"");
            String userName = p.getName();
            if (userName != null) {
               buf.append(userName);
            }

            buf.append("\")\n");
         }

         return buf.toString();
      }
   }

   public static PublicKey getVerifyKey(WebSSOPartner partner) throws CertificateException, KeyException {
      if (partner == null) {
         throw new IllegalArgumentException("Partner can not be null.");
      } else {
         java.security.cert.X509Certificate cert = partner.getSSOSigningCert();
         if (cert == null) {
            throw new CertificateException("Can not get the signing certificate from the partner registry.");
         } else {
            cert.checkValidity();
            PublicKey key = cert.getPublicKey();
            if (key == null) {
               throw new KeyException("Can not get the public key from the certificate");
            } else {
               return key;
            }
         }
      }
   }

   public static boolean isValidConfirmationMethod(String confirmationMethod) {
      boolean isValid = false;
      if (confirmationMethod.compareTo("urn:oasis:names:tc:SAML:2.0:cm:bearer") == 0 || confirmationMethod.compareTo("urn:oasis:names:tc:SAML:2.0:cm:sender-vouches") == 0 || confirmationMethod.compareTo("urn:oasis:names:tc:SAML:2.0:cm:holder-of-key") == 0) {
         isValid = true;
      }

      return isValid;
   }

   public static Element marshall(XMLObject object) {
      try {
         Marshaller marshaller = XMLObjectProviderRegistrySupport.getMarshallerFactory().getMarshaller(object);
         return marshaller.marshall(object);
      } catch (MarshallingException var2) {
         return null;
      }
   }

   public static String outputXml(Element element) throws Exception {
      String result = null;
      ByteArrayOutputStream fos = new ByteArrayOutputStream();

      try {
         TransformerFactory tf = TransformerFactory.newInstance();
         Transformer t = tf.newTransformer();
         t.transform(new DOMSource(element.getOwnerDocument()), new StreamResult(fos));
         result = new String(fos.toByteArray(), "UTF-8");
      } finally {
         fos.close();
      }

      return result;
   }

   public static String getDelimiterForQueryParams(String url) {
      String result = "?";
      if (url != null && url.indexOf(63) != -1) {
         result = "&";
      }

      return result;
   }

   public static List getCertificates(KeyInfo keyInfo) throws CertificateException, IOException {
      List certs = null;
      if (keyInfo != null) {
         List x509DataList = keyInfo.getX509Datas();
         if (x509DataList != null && !x509DataList.isEmpty()) {
            certs = new ArrayList();
            Iterator iterator = x509DataList.iterator();
            CertificateFactory certificateFactory = null;
            certificateFactory = CertificateFactory.getInstance("X509");
            if (certificateFactory != null) {
               while(true) {
                  List list;
                  do {
                     do {
                        if (!iterator.hasNext()) {
                           return certs;
                        }

                        X509Data x509Data = (X509Data)iterator.next();
                        list = x509Data.getX509Certificates();
                     } while(list == null);
                  } while(list.size() <= 0);

                  for(int i = 0; i < list.size(); ++i) {
                     certs.add((java.security.cert.X509Certificate)certificateFactory.generateCertificate(new ByteArrayInputStream(base64Decode(((X509Certificate)list.get(i)).getValue()))));
                  }
               }
            }
         }
      }

      return certs;
   }

   public static EncryptedAssertion encryptAssertion(Assertion assertion, String dataEncryptionAlgorithm, String keyEncryptionAlgorithm, Credential keyEncryptionCredential, String recipient) throws EncryptionException, NoSuchAlgorithmException {
      KeyEncryptionParameters keyEncryptionParameters = new KeyEncryptionParameters();
      keyEncryptionParameters.setAlgorithm(keyEncryptionAlgorithm);
      keyEncryptionParameters.setEncryptionCredential(keyEncryptionCredential);
      EncryptionParameters dataEncryptParams = new EncryptionParameters();
      dataEncryptParams.setDataEncryptionAlgorithm(dataEncryptionAlgorithm);
      DataEncryptionParameters dataEncryptionParameters = new DataEncryptionParameters(dataEncryptParams);
      Encrypter encrypter = new Encrypter(dataEncryptionParameters, keyEncryptionParameters);
      EncryptedAssertion encryptedAssertion = encrypter.encrypt(assertion);
      if (encryptedAssertion != null && recipient != null) {
         List keys = encryptedAssertion.getEncryptedKeys();
         if (keys != null && keys.size() > 0) {
            Iterator iterator = keys.iterator();

            while(iterator.hasNext()) {
               ((EncryptedKey)iterator.next()).setRecipient(recipient);
            }
         }
      }

      return encryptedAssertion;
   }

   public static Assertion decryptAssertion(Response assertRes, Credential decryptionCredential, String entityID) throws DecryptionException, MarshallingException, UnmarshallingException {
      return decryptAssertion((EncryptedAssertion)assertRes.getEncryptedAssertions().get(0), decryptionCredential, entityID);
   }

   static Assertion decryptAssertion(EncryptedAssertion encryptedAssertion, Credential decryptionCredential, String entityID) throws DecryptionException, MarshallingException, UnmarshallingException {
      validateEncryptedKeyRecipient(encryptedAssertion, entityID);
      Decrypter decrypter = new Decrypter((KeyInfoCredentialResolver)null, new StaticKeyInfoCredentialResolver(decryptionCredential), CHAINING_ENCRYPTED_KEY_RESOLVER);
      decrypter.setRootInNewDocument(true);
      return decrypter.decrypt(encryptedAssertion);
   }

   private static void validateEncryptedKeyRecipient(EncryptedAssertion encryptedAssertion, String entityID) throws DecryptionException {
      if (encryptedAssertion != null) {
         List encryptedAssertionList = encryptedAssertion.getEncryptedKeys();
         Iterator encryptedKeyIterable = encryptedAssertionList.iterator();
         if (encryptedKeyIterable != null) {
            while(encryptedKeyIterable.hasNext()) {
               EncryptedKey encryptedKey = (EncryptedKey)encryptedKeyIterable.next();
               if (encryptedKey != null && encryptedKey.getRecipient() != null && !entityID.equalsIgnoreCase(encryptedKey.getRecipient())) {
                  throw new DecryptionException("The EncryptedKey's Recipient(" + encryptedKey.getRecipient() + ") does not match the Entity ID(" + entityID + ")");
               }
            }
         }
      }

   }

   public static List getSupportedEncryptionMethods(String[] algorithms) {
      List encMethods = null;
      if (algorithms != null && algorithms.length > 0) {
         encMethods = new ArrayList(algorithms.length);
         String[] var2 = algorithms;
         int var3 = algorithms.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String s = var2[var4];
            EncryptionMethod em = (EncryptionMethod)XMLObjectSupport.buildXMLObject(EncryptionMethod.DEFAULT_ELEMENT_NAME);
            em.setAlgorithm((String)ALL_ALGORITHMS.get(s));
            encMethods.add(em);
         }
      }

      return encMethods;
   }

   public static String resolveDataEncryptionAlgorithm(String preferredDataEncryptionAlgorithm, List encryptionAlgorithmsFromSP) {
      String resolvedAlgorithm = null;
      if (preferredDataEncryptionAlgorithm != null) {
         if (encryptionAlgorithmsFromSP != null && encryptionAlgorithmsFromSP.size() > 0 && containsAlgorithm(encryptionAlgorithmsFromSP, SUPPORTED_CEK_ALGORITHMS)) {
            if (encryptionAlgorithmsFromSP.contains(preferredDataEncryptionAlgorithm)) {
               resolvedAlgorithm = (String)ALL_ALGORITHMS.get(preferredDataEncryptionAlgorithm);
            }
         } else if (isAlgorithmFound(SUPPORTED_CEK_ALGORITHMS, preferredDataEncryptionAlgorithm)) {
            resolvedAlgorithm = (String)ALL_ALGORITHMS.get(preferredDataEncryptionAlgorithm);
         }
      }

      if (resolvedAlgorithm == null && encryptionAlgorithmsFromSP != null && encryptionAlgorithmsFromSP.size() > 0) {
         for(int i = 0; i < SUPPORTED_CEK_ALGORITHMS.length; ++i) {
            if (encryptionAlgorithmsFromSP.contains(SUPPORTED_CEK_ALGORITHMS[i]) && (!SUPPORTED_CEK_ALGORITHMS[i].contains("#aes") || CEK_ALGORITHMS_KEY_LENGTHS[i] <= MAX_AES_KEY_LENGTH_ALLOWED)) {
               resolvedAlgorithm = SUPPORTED_CEK_ALGORITHMS[i];
               break;
            }
         }
      }

      return resolvedAlgorithm;
   }

   public static String resolveKeyEncryptionAlgorithm(String preferredKeyEncryptionAlgorithm, List encryptionAlgorithmsFromSP) {
      String resolvedAlgorithm = null;
      if (preferredKeyEncryptionAlgorithm != null) {
         if (encryptionAlgorithmsFromSP != null && encryptionAlgorithmsFromSP.size() > 0 && containsAlgorithm(encryptionAlgorithmsFromSP, SUPPORTED_KEK_ALGORITHMS)) {
            if (isAlgorithmFound((String[])encryptionAlgorithmsFromSP.toArray(new String[encryptionAlgorithmsFromSP.size()]), preferredKeyEncryptionAlgorithm) && isAlgorithmFound(SUPPORTED_KEK_ALGORITHMS, preferredKeyEncryptionAlgorithm)) {
               resolvedAlgorithm = (String)ALL_ALGORITHMS.get(preferredKeyEncryptionAlgorithm);
            }
         } else if (isAlgorithmFound(SUPPORTED_KEK_ALGORITHMS, preferredKeyEncryptionAlgorithm)) {
            resolvedAlgorithm = (String)ALL_ALGORITHMS.get(preferredKeyEncryptionAlgorithm);
         }
      }

      if (encryptionAlgorithmsFromSP != null && encryptionAlgorithmsFromSP.size() > 0) {
         for(int i = 0; i < SUPPORTED_KEK_ALGORITHMS.length; ++i) {
            if (encryptionAlgorithmsFromSP.contains(SUPPORTED_KEK_ALGORITHMS[i])) {
               resolvedAlgorithm = SUPPORTED_KEK_ALGORITHMS[i];
               break;
            }
         }
      }

      return resolvedAlgorithm;
   }

   private static boolean isAlgorithmFound(String[] algorithmURIs, String shortAlgorithmName) {
      boolean found = false;
      if (shortAlgorithmName != null && algorithmURIs != null && algorithmURIs.length > 0) {
         String[] var3 = algorithmURIs;
         int var4 = algorithmURIs.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String s = var3[var5];
            if (s.endsWith("#" + shortAlgorithmName)) {
               found = true;
               break;
            }
         }
      }

      return found;
   }

   private static boolean containsAlgorithm(List algorithmsFromSP, String[] algorithms) {
      boolean found = false;
      if (algorithms != null && algorithms.length > 0 && algorithmsFromSP != null && algorithmsFromSP.size() > 0) {
         for(int i = 0; i < algorithmsFromSP.size(); ++i) {
            for(int j = 0; j < algorithms.length; ++j) {
               if (algorithmsFromSP.get(i).equals(algorithms[j])) {
                  found = true;
                  break;
               }
            }
         }
      }

      return found;
   }

   public static String extractShortAlgName(String algorithmURI) {
      String result = null;
      int delimiter = true;
      if (algorithmURI != null) {
         int index = algorithmURI.indexOf(35);
         if (index > -1) {
            result = algorithmURI.substring(index + 1);
         }
      }

      return result;
   }

   public static String getXMLSafeSecureUUID() {
      return "_" + UUID.randomUUID().toString();
   }

   public static void checkBindingType(String bindingType, SAML2ConfigSpi config, boolean isIdP) throws SAML2Exception {
      if (!"HTTP/Artifact".equals(bindingType) && !"HTTP/POST".equals(bindingType)) {
         throw new SAML2Exception(isIdP ? Saml2Logger.getSPACSBindingNotSupportedForAuthnResponse(bindingType) : Saml2Logger.getBindingNotSupportedForAuthnResponse(bindingType));
      } else {
         if (!isIdP) {
            if (!config.getLocalConfiguration().isServiceProviderArtifactBindingEnabled() && "HTTP/Artifact".equals(bindingType)) {
               throw new SAML2Exception(Saml2Logger.getBindingUnenabled(bindingType));
            }

            if (!config.getLocalConfiguration().isServiceProviderPOSTBindingEnabled() && "HTTP/POST".equals(bindingType)) {
               throw new SAML2Exception(Saml2Logger.getBindingUnenabled(bindingType));
            }
         }

      }
   }

   static {
      List encryptedKeyResolvers = new ArrayList();
      encryptedKeyResolvers.add(new InlineEncryptedKeyResolver());
      encryptedKeyResolvers.add(new EncryptedElementTypeEncryptedKeyResolver());
      encryptedKeyResolvers.add(new SimpleKeyInfoReferenceEncryptedKeyResolver());
      encryptedKeyResolvers.add(new SimpleRetrievalMethodEncryptedKeyResolver());
      CHAINING_ENCRYPTED_KEY_RESOLVER = new ChainingEncryptedKeyResolver(encryptedKeyResolvers);

      try {
         MAX_AES_KEY_LENGTH_ALLOWED = Cipher.getMaxAllowedKeyLength("AES");
      } catch (NoSuchAlgorithmException var2) {
      }

      ALL_ALGORITHMS = getAllAlgorithms();
   }

   public static class SAMLPrivateKeyCredential extends BasicCredential {
      public SAMLPrivateKeyCredential(PrivateKey privateKey) {
         this.setPrivateKey(privateKey);
      }
   }
}
