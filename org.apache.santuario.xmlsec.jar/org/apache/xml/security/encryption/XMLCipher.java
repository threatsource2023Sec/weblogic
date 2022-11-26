package org.apache.xml.security.encryption;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.MGF1ParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import javax.crypto.spec.PSource.PSpecified;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.xml.security.algorithms.JCEMapper;
import org.apache.xml.security.c14n.Canonicalizer;
import org.apache.xml.security.c14n.InvalidCanonicalizerException;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.keys.KeyInfo;
import org.apache.xml.security.keys.keyresolver.KeyResolverException;
import org.apache.xml.security.keys.keyresolver.KeyResolverSpi;
import org.apache.xml.security.keys.keyresolver.implementations.EncryptedKeyResolver;
import org.apache.xml.security.signature.XMLSignatureException;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.transforms.InvalidTransformException;
import org.apache.xml.security.transforms.TransformationException;
import org.apache.xml.security.utils.ElementProxy;
import org.apache.xml.security.utils.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLCipher {
   private static final Logger LOG = LoggerFactory.getLogger(XMLCipher.class);
   public static final String TRIPLEDES = "http://www.w3.org/2001/04/xmlenc#tripledes-cbc";
   public static final String AES_128 = "http://www.w3.org/2001/04/xmlenc#aes128-cbc";
   public static final String AES_256 = "http://www.w3.org/2001/04/xmlenc#aes256-cbc";
   public static final String AES_192 = "http://www.w3.org/2001/04/xmlenc#aes192-cbc";
   public static final String AES_128_GCM = "http://www.w3.org/2009/xmlenc11#aes128-gcm";
   public static final String AES_192_GCM = "http://www.w3.org/2009/xmlenc11#aes192-gcm";
   public static final String AES_256_GCM = "http://www.w3.org/2009/xmlenc11#aes256-gcm";
   public static final String SEED_128 = "http://www.w3.org/2007/05/xmldsig-more#seed128-cbc";
   public static final String CAMELLIA_128 = "http://www.w3.org/2001/04/xmldsig-more#camellia128-cbc";
   public static final String CAMELLIA_192 = "http://www.w3.org/2001/04/xmldsig-more#camellia192-cbc";
   public static final String CAMELLIA_256 = "http://www.w3.org/2001/04/xmldsig-more#camellia256-cbc";
   public static final String RSA_v1dot5 = "http://www.w3.org/2001/04/xmlenc#rsa-1_5";
   public static final String RSA_OAEP = "http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p";
   public static final String RSA_OAEP_11 = "http://www.w3.org/2009/xmlenc11#rsa-oaep";
   public static final String DIFFIE_HELLMAN = "http://www.w3.org/2001/04/xmlenc#dh";
   public static final String TRIPLEDES_KeyWrap = "http://www.w3.org/2001/04/xmlenc#kw-tripledes";
   public static final String AES_128_KeyWrap = "http://www.w3.org/2001/04/xmlenc#kw-aes128";
   public static final String AES_256_KeyWrap = "http://www.w3.org/2001/04/xmlenc#kw-aes256";
   public static final String AES_192_KeyWrap = "http://www.w3.org/2001/04/xmlenc#kw-aes192";
   public static final String CAMELLIA_128_KeyWrap = "http://www.w3.org/2001/04/xmldsig-more#kw-camellia128";
   public static final String CAMELLIA_192_KeyWrap = "http://www.w3.org/2001/04/xmldsig-more#kw-camellia192";
   public static final String CAMELLIA_256_KeyWrap = "http://www.w3.org/2001/04/xmldsig-more#kw-camellia256";
   public static final String SEED_128_KeyWrap = "http://www.w3.org/2007/05/xmldsig-more#kw-seed128";
   public static final String SHA1 = "http://www.w3.org/2000/09/xmldsig#sha1";
   public static final String SHA256 = "http://www.w3.org/2001/04/xmlenc#sha256";
   public static final String SHA512 = "http://www.w3.org/2001/04/xmlenc#sha512";
   public static final String RIPEMD_160 = "http://www.w3.org/2001/04/xmlenc#ripemd160";
   public static final String XML_DSIG = "http://www.w3.org/2000/09/xmldsig#";
   public static final String N14C_XML = "http://www.w3.org/TR/2001/REC-xml-c14n-20010315";
   public static final String N14C_XML_WITH_COMMENTS = "http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments";
   public static final String EXCL_XML_N14C = "http://www.w3.org/2001/10/xml-exc-c14n#";
   public static final String EXCL_XML_N14C_WITH_COMMENTS = "http://www.w3.org/2001/10/xml-exc-c14n#WithComments";
   public static final String PHYSICAL_XML_N14C = "http://santuario.apache.org/c14n/physical";
   public static final String BASE64_ENCODING = "http://www.w3.org/2000/09/xmldsig#base64";
   public static final int ENCRYPT_MODE = 1;
   public static final int DECRYPT_MODE = 2;
   public static final int UNWRAP_MODE = 4;
   public static final int WRAP_MODE = 3;
   private static final String ENC_ALGORITHMS = "http://www.w3.org/2001/04/xmlenc#tripledes-cbc\nhttp://www.w3.org/2001/04/xmlenc#aes128-cbc\nhttp://www.w3.org/2001/04/xmlenc#aes256-cbc\nhttp://www.w3.org/2001/04/xmlenc#aes192-cbc\nhttp://www.w3.org/2001/04/xmlenc#rsa-1_5\nhttp://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p\nhttp://www.w3.org/2009/xmlenc11#rsa-oaep\nhttp://www.w3.org/2001/04/xmlenc#kw-tripledes\nhttp://www.w3.org/2001/04/xmlenc#kw-aes128\nhttp://www.w3.org/2001/04/xmlenc#kw-aes256\nhttp://www.w3.org/2001/04/xmlenc#kw-aes192\nhttp://www.w3.org/2009/xmlenc11#aes128-gcm\nhttp://www.w3.org/2009/xmlenc11#aes192-gcm\nhttp://www.w3.org/2009/xmlenc11#aes256-gcm\nhttp://www.w3.org/2007/05/xmldsig-more#seed128-cbc\nhttp://www.w3.org/2001/04/xmldsig-more#camellia128-cbc\nhttp://www.w3.org/2001/04/xmldsig-more#camellia192-cbc\nhttp://www.w3.org/2001/04/xmldsig-more#camellia256-cbc\nhttp://www.w3.org/2001/04/xmldsig-more#kw-camellia128\nhttp://www.w3.org/2001/04/xmldsig-more#kw-camellia192\nhttp://www.w3.org/2001/04/xmldsig-more#kw-camellia256\nhttp://www.w3.org/2007/05/xmldsig-more#kw-seed128\n";
   private static final boolean HAVE_FUNCTIONAL_IDENTITY_TRANSFORMER = haveFunctionalIdentityTransformer();
   private Cipher contextCipher;
   private int cipherMode = Integer.MIN_VALUE;
   private String algorithm;
   private String requestedJCEProvider;
   private Canonicalizer canon;
   private Document contextDocument;
   private Factory factory;
   private Serializer serializer;
   private Key key;
   private Key kek;
   private EncryptedKey ek;
   private EncryptedData ed;
   private boolean secureValidation;
   private String digestAlg;
   private List internalKeyResolvers;

   public void setSerializer(Serializer serializer) {
      this.serializer = serializer;
      serializer.setCanonicalizer(this.canon);
   }

   public Serializer getSerializer() {
      return this.serializer;
   }

   private XMLCipher(String transformation, String provider, String canonAlg, String digestMethod) throws XMLEncryptionException {
      LOG.debug("Constructing XMLCipher...");
      this.factory = new Factory();
      this.algorithm = transformation;
      this.requestedJCEProvider = provider;
      this.digestAlg = digestMethod;

      try {
         if (canonAlg == null) {
            this.canon = Canonicalizer.getInstance("http://santuario.apache.org/c14n/physical");
         } else {
            this.canon = Canonicalizer.getInstance(canonAlg);
         }
      } catch (InvalidCanonicalizerException var6) {
         throw new XMLEncryptionException(var6);
      }

      if (this.serializer == null) {
         if (HAVE_FUNCTIONAL_IDENTITY_TRANSFORMER) {
            this.serializer = new TransformSerializer();
         } else {
            this.serializer = new DocumentSerializer();
         }
      }

      this.serializer.setCanonicalizer(this.canon);
      if (transformation != null) {
         this.contextCipher = this.constructCipher(transformation, digestMethod);
      }

   }

   private static boolean isValidEncryptionAlgorithm(String algorithm) {
      return algorithm.equals("http://www.w3.org/2001/04/xmlenc#tripledes-cbc") || algorithm.equals("http://www.w3.org/2001/04/xmlenc#aes128-cbc") || algorithm.equals("http://www.w3.org/2001/04/xmlenc#aes256-cbc") || algorithm.equals("http://www.w3.org/2001/04/xmlenc#aes192-cbc") || algorithm.equals("http://www.w3.org/2009/xmlenc11#aes128-gcm") || algorithm.equals("http://www.w3.org/2009/xmlenc11#aes192-gcm") || algorithm.equals("http://www.w3.org/2009/xmlenc11#aes256-gcm") || algorithm.equals("http://www.w3.org/2007/05/xmldsig-more#seed128-cbc") || algorithm.equals("http://www.w3.org/2001/04/xmldsig-more#camellia128-cbc") || algorithm.equals("http://www.w3.org/2001/04/xmldsig-more#camellia192-cbc") || algorithm.equals("http://www.w3.org/2001/04/xmldsig-more#camellia256-cbc") || algorithm.equals("http://www.w3.org/2001/04/xmlenc#rsa-1_5") || algorithm.equals("http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p") || algorithm.equals("http://www.w3.org/2009/xmlenc11#rsa-oaep") || algorithm.equals("http://www.w3.org/2001/04/xmlenc#kw-tripledes") || algorithm.equals("http://www.w3.org/2001/04/xmlenc#kw-aes128") || algorithm.equals("http://www.w3.org/2001/04/xmlenc#kw-aes256") || algorithm.equals("http://www.w3.org/2001/04/xmlenc#kw-aes192") || algorithm.equals("http://www.w3.org/2001/04/xmldsig-more#kw-camellia128") || algorithm.equals("http://www.w3.org/2001/04/xmldsig-more#kw-camellia192") || algorithm.equals("http://www.w3.org/2001/04/xmldsig-more#kw-camellia256") || algorithm.equals("http://www.w3.org/2007/05/xmldsig-more#kw-seed128");
   }

   private static void validateTransformation(String transformation) {
      if (null == transformation) {
         throw new NullPointerException("Transformation unexpectedly null...");
      } else {
         if (!isValidEncryptionAlgorithm(transformation)) {
            LOG.warn("Algorithm non-standard, expected one of http://www.w3.org/2001/04/xmlenc#tripledes-cbc\nhttp://www.w3.org/2001/04/xmlenc#aes128-cbc\nhttp://www.w3.org/2001/04/xmlenc#aes256-cbc\nhttp://www.w3.org/2001/04/xmlenc#aes192-cbc\nhttp://www.w3.org/2001/04/xmlenc#rsa-1_5\nhttp://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p\nhttp://www.w3.org/2009/xmlenc11#rsa-oaep\nhttp://www.w3.org/2001/04/xmlenc#kw-tripledes\nhttp://www.w3.org/2001/04/xmlenc#kw-aes128\nhttp://www.w3.org/2001/04/xmlenc#kw-aes256\nhttp://www.w3.org/2001/04/xmlenc#kw-aes192\nhttp://www.w3.org/2009/xmlenc11#aes128-gcm\nhttp://www.w3.org/2009/xmlenc11#aes192-gcm\nhttp://www.w3.org/2009/xmlenc11#aes256-gcm\nhttp://www.w3.org/2007/05/xmldsig-more#seed128-cbc\nhttp://www.w3.org/2001/04/xmldsig-more#camellia128-cbc\nhttp://www.w3.org/2001/04/xmldsig-more#camellia192-cbc\nhttp://www.w3.org/2001/04/xmldsig-more#camellia256-cbc\nhttp://www.w3.org/2001/04/xmldsig-more#kw-camellia128\nhttp://www.w3.org/2001/04/xmldsig-more#kw-camellia192\nhttp://www.w3.org/2001/04/xmldsig-more#kw-camellia256\nhttp://www.w3.org/2007/05/xmldsig-more#kw-seed128\n");
         }

      }
   }

   public static XMLCipher getInstance(String transformation) throws XMLEncryptionException {
      LOG.debug("Getting XMLCipher with transformation");
      validateTransformation(transformation);
      return new XMLCipher(transformation, (String)null, (String)null, (String)null);
   }

   public static XMLCipher getInstance(String transformation, String canon) throws XMLEncryptionException {
      LOG.debug("Getting XMLCipher with transformation and c14n algorithm");
      validateTransformation(transformation);
      return new XMLCipher(transformation, (String)null, canon, (String)null);
   }

   public static XMLCipher getInstance(String transformation, String canon, String digestMethod) throws XMLEncryptionException {
      LOG.debug("Getting XMLCipher with transformation and c14n algorithm");
      validateTransformation(transformation);
      return new XMLCipher(transformation, (String)null, canon, digestMethod);
   }

   public static XMLCipher getProviderInstance(String transformation, String provider) throws XMLEncryptionException {
      LOG.debug("Getting XMLCipher with transformation and provider");
      if (null == provider) {
         throw new NullPointerException("Provider unexpectedly null..");
      } else {
         validateTransformation(transformation);
         return new XMLCipher(transformation, provider, (String)null, (String)null);
      }
   }

   public static XMLCipher getProviderInstance(String transformation, String provider, String canon) throws XMLEncryptionException {
      LOG.debug("Getting XMLCipher with transformation, provider and c14n algorithm");
      if (null == provider) {
         throw new NullPointerException("Provider unexpectedly null..");
      } else {
         validateTransformation(transformation);
         return new XMLCipher(transformation, provider, canon, (String)null);
      }
   }

   public static XMLCipher getProviderInstance(String transformation, String provider, String canon, String digestMethod) throws XMLEncryptionException {
      LOG.debug("Getting XMLCipher with transformation, provider and c14n algorithm");
      if (null == provider) {
         throw new NullPointerException("Provider unexpectedly null..");
      } else {
         validateTransformation(transformation);
         return new XMLCipher(transformation, provider, canon, digestMethod);
      }
   }

   public static XMLCipher getInstance() throws XMLEncryptionException {
      LOG.debug("Getting XMLCipher with no arguments");
      return new XMLCipher((String)null, (String)null, (String)null, (String)null);
   }

   public static XMLCipher getProviderInstance(String provider) throws XMLEncryptionException {
      LOG.debug("Getting XMLCipher with provider");
      return new XMLCipher((String)null, provider, (String)null, (String)null);
   }

   public void init(int opmode, Key key) throws XMLEncryptionException {
      LOG.debug("Initializing XMLCipher...");
      this.ek = null;
      this.ed = null;
      switch (opmode) {
         case 1:
            LOG.debug("opmode = ENCRYPT_MODE");
            this.ed = this.createEncryptedData(1, "NO VALUE YET");
            break;
         case 2:
            LOG.debug("opmode = DECRYPT_MODE");
            break;
         case 3:
            LOG.debug("opmode = WRAP_MODE");
            this.ek = this.createEncryptedKey(1, "NO VALUE YET");
            break;
         case 4:
            LOG.debug("opmode = UNWRAP_MODE");
            break;
         default:
            LOG.error("Mode unexpectedly invalid");
            throw new XMLEncryptionException("Invalid mode in init");
      }

      this.cipherMode = opmode;
      this.key = key;
   }

   public void setSecureValidation(boolean secureValidation) {
      this.secureValidation = secureValidation;
   }

   public void registerInternalKeyResolver(KeyResolverSpi keyResolver) {
      if (this.internalKeyResolvers == null) {
         this.internalKeyResolvers = new ArrayList();
      }

      this.internalKeyResolvers.add(keyResolver);
   }

   public EncryptedData getEncryptedData() {
      LOG.debug("Returning EncryptedData");
      return this.ed;
   }

   public EncryptedKey getEncryptedKey() {
      LOG.debug("Returning EncryptedKey");
      return this.ek;
   }

   public void setKEK(Key kek) {
      this.kek = kek;
   }

   public Element martial(EncryptedData encryptedData) {
      return this.factory.toElement(encryptedData);
   }

   public Element martial(Document context, EncryptedData encryptedData) {
      this.contextDocument = context;
      return this.factory.toElement(encryptedData);
   }

   public Element martial(EncryptedKey encryptedKey) {
      return this.factory.toElement(encryptedKey);
   }

   public Element martial(Document context, EncryptedKey encryptedKey) {
      this.contextDocument = context;
      return this.factory.toElement(encryptedKey);
   }

   public Element martial(ReferenceList referenceList) {
      return this.factory.toElement(referenceList);
   }

   public Element martial(Document context, ReferenceList referenceList) {
      this.contextDocument = context;
      return this.factory.toElement(referenceList);
   }

   private Document encryptElement(Element element) throws Exception {
      LOG.debug("Encrypting element...");
      if (null == element) {
         throw new XMLEncryptionException("empty", new Object[]{"Element unexpectedly null..."});
      } else if (this.cipherMode != 1) {
         throw new XMLEncryptionException("empty", new Object[]{"XMLCipher unexpectedly not in ENCRYPT_MODE..."});
      } else if (this.algorithm == null) {
         throw new XMLEncryptionException("empty", new Object[]{"XMLCipher instance without transformation specified"});
      } else {
         this.encryptData(this.contextDocument, element, false);
         Element encryptedElement = this.factory.toElement(this.ed);
         Node sourceParent = element.getParentNode();
         sourceParent.replaceChild(encryptedElement, element);
         return this.contextDocument;
      }
   }

   private Document encryptElementContent(Element element) throws Exception {
      LOG.debug("Encrypting element content...");
      if (null == element) {
         throw new XMLEncryptionException("empty", new Object[]{"Element unexpectedly null..."});
      } else if (this.cipherMode != 1) {
         throw new XMLEncryptionException("empty", new Object[]{"XMLCipher unexpectedly not in ENCRYPT_MODE..."});
      } else if (this.algorithm == null) {
         throw new XMLEncryptionException("empty", new Object[]{"XMLCipher instance without transformation specified"});
      } else {
         this.encryptData(this.contextDocument, element, true);
         Element encryptedElement = this.factory.toElement(this.ed);
         removeContent(element);
         element.appendChild(encryptedElement);
         return this.contextDocument;
      }
   }

   public Document doFinal(Document context, Document source) throws Exception {
      LOG.debug("Processing source document...");
      if (null == context) {
         throw new XMLEncryptionException("empty", new Object[]{"Context document unexpectedly null..."});
      } else if (null == source) {
         throw new XMLEncryptionException("empty", new Object[]{"Source document unexpectedly null..."});
      } else {
         this.contextDocument = context;
         Document result = null;
         switch (this.cipherMode) {
            case 1:
               result = this.encryptElement(source.getDocumentElement());
               break;
            case 2:
               result = this.decryptElement(source.getDocumentElement());
            case 3:
            case 4:
               break;
            default:
               throw new XMLEncryptionException(new IllegalStateException());
         }

         return result;
      }
   }

   public Document doFinal(Document context, Element element) throws Exception {
      LOG.debug("Processing source element...");
      if (null == context) {
         throw new XMLEncryptionException("empty", new Object[]{"Context document unexpectedly null..."});
      } else if (null == element) {
         throw new XMLEncryptionException("empty", new Object[]{"Source element unexpectedly null..."});
      } else {
         this.contextDocument = context;
         Document result = null;
         switch (this.cipherMode) {
            case 1:
               result = this.encryptElement(element);
               break;
            case 2:
               result = this.decryptElement(element);
            case 3:
            case 4:
               break;
            default:
               throw new XMLEncryptionException(new IllegalStateException());
         }

         return result;
      }
   }

   public Document doFinal(Document context, Element element, boolean content) throws Exception {
      LOG.debug("Processing source element...");
      if (null == context) {
         throw new XMLEncryptionException("empty", new Object[]{"Context document unexpectedly null..."});
      } else if (null == element) {
         throw new XMLEncryptionException("empty", new Object[]{"Source element unexpectedly null..."});
      } else {
         this.contextDocument = context;
         Document result = null;
         switch (this.cipherMode) {
            case 1:
               if (content) {
                  result = this.encryptElementContent(element);
               } else {
                  result = this.encryptElement(element);
               }
               break;
            case 2:
               if (content) {
                  result = this.decryptElementContent(element);
               } else {
                  result = this.decryptElement(element);
               }
            case 3:
            case 4:
               break;
            default:
               throw new XMLEncryptionException(new IllegalStateException());
         }

         return result;
      }
   }

   public EncryptedData encryptData(Document context, Element element) throws Exception {
      return this.encryptData(context, element, false);
   }

   public EncryptedData encryptData(Document context, String type, InputStream serializedData) throws Exception {
      LOG.debug("Encrypting element...");
      if (null == context) {
         throw new XMLEncryptionException("empty", new Object[]{"Context document unexpectedly null..."});
      } else if (null == serializedData) {
         throw new XMLEncryptionException("empty", new Object[]{"Serialized data unexpectedly null..."});
      } else if (this.cipherMode != 1) {
         throw new XMLEncryptionException("empty", new Object[]{"XMLCipher unexpectedly not in ENCRYPT_MODE..."});
      } else {
         return this.encryptData(context, (Element)null, type, serializedData);
      }
   }

   public EncryptedData encryptData(Document context, Element element, boolean contentMode) throws Exception {
      LOG.debug("Encrypting element...");
      if (null == context) {
         throw new XMLEncryptionException("empty", new Object[]{"Context document unexpectedly null..."});
      } else if (null == element) {
         throw new XMLEncryptionException("empty", new Object[]{"Element unexpectedly null..."});
      } else if (this.cipherMode != 1) {
         throw new XMLEncryptionException("empty", new Object[]{"XMLCipher unexpectedly not in ENCRYPT_MODE..."});
      } else {
         return contentMode ? this.encryptData(context, element, "http://www.w3.org/2001/04/xmlenc#Content", (InputStream)null) : this.encryptData(context, element, "http://www.w3.org/2001/04/xmlenc#Element", (InputStream)null);
      }
   }

   private EncryptedData encryptData(Document context, Element element, String type, InputStream serializedData) throws Exception {
      this.contextDocument = context;
      if (this.algorithm == null) {
         throw new XMLEncryptionException("empty", new Object[]{"XMLCipher instance without transformation specified"});
      } else {
         if (this.serializer instanceof AbstractSerializer) {
            ((AbstractSerializer)this.serializer).setSecureValidation(this.secureValidation);
         }

         if (element != null && element.getParentNode() == null) {
            throw new XMLEncryptionException("empty", new Object[]{"The element can't be serialized as it has no parent"});
         } else {
            byte[] serializedOctets = null;
            NodeList children;
            if (serializedData == null) {
               if (type.equals("http://www.w3.org/2001/04/xmlenc#Content")) {
                  children = element.getChildNodes();
                  if (null == children) {
                     throw new XMLEncryptionException("empty", new Object[]{"Element has no content."});
                  }

                  serializedOctets = this.serializer.serializeToByteArray(children);
               } else {
                  serializedOctets = this.serializer.serializeToByteArray(element);
               }

               if (LOG.isDebugEnabled()) {
                  LOG.debug("Serialized octets:\n" + new String(serializedOctets, StandardCharsets.UTF_8));
               }
            }

            children = null;
            Cipher c;
            if (this.contextCipher == null) {
               c = this.constructCipher(this.algorithm, (String)null);
            } else {
               c = this.contextCipher;
            }

            int ivLen = JCEMapper.getIVLengthFromURI(this.algorithm) / 8;
            byte[] iv = XMLSecurityConstants.generateBytes(ivLen);

            try {
               AlgorithmParameterSpec paramSpec = this.constructBlockCipherParameters(this.algorithm, iv);
               c.init(this.cipherMode, this.key, paramSpec);
            } catch (InvalidKeyException var28) {
               throw new XMLEncryptionException(var28);
            }

            byte[] encryptedBytes;
            try {
               if (serializedData == null) {
                  encryptedBytes = c.doFinal(serializedOctets);
                  if (LOG.isDebugEnabled()) {
                     LOG.debug("Expected cipher.outputSize = " + Integer.toString(c.getOutputSize(serializedOctets.length)));
                  }
               } else {
                  byte[] buf = new byte[8192];
                  ByteArrayOutputStream baos = new ByteArrayOutputStream();
                  Throwable var13 = null;

                  try {
                     int numBytes;
                     while((numBytes = serializedData.read(buf)) != -1) {
                        byte[] data = c.update(buf, 0, numBytes);
                        baos.write(data);
                     }

                     baos.write(c.doFinal());
                     encryptedBytes = baos.toByteArray();
                  } catch (Throwable var30) {
                     var13 = var30;
                     throw var30;
                  } finally {
                     if (var13 != null) {
                        try {
                           baos.close();
                        } catch (Throwable var27) {
                           var13.addSuppressed(var27);
                        }
                     } else {
                        baos.close();
                     }

                  }
               }

               if (LOG.isDebugEnabled()) {
                  LOG.debug("Actual cipher.outputSize = " + Integer.toString(encryptedBytes.length));
               }
            } catch (IllegalStateException var32) {
               throw new XMLEncryptionException(var32);
            } catch (IllegalBlockSizeException var33) {
               throw new XMLEncryptionException(var33);
            } catch (BadPaddingException var34) {
               throw new XMLEncryptionException(var34);
            } catch (UnsupportedEncodingException var35) {
               throw new XMLEncryptionException(var35);
            }

            if (c.getIV() != null) {
               iv = c.getIV();
            }

            byte[] finalEncryptedBytes = new byte[iv.length + encryptedBytes.length];
            System.arraycopy(iv, 0, finalEncryptedBytes, 0, iv.length);
            System.arraycopy(encryptedBytes, 0, finalEncryptedBytes, iv.length, encryptedBytes.length);
            String base64EncodedEncryptedOctets = XMLUtils.encodeToString(finalEncryptedBytes);
            LOG.debug("Encrypted octets:\n{}", base64EncodedEncryptedOctets);
            LOG.debug("Encrypted octets length = {}", base64EncodedEncryptedOctets.length());

            try {
               CipherData cd = this.ed.getCipherData();
               CipherValue cv = cd.getCipherValue();
               cv.setValue(base64EncodedEncryptedOctets);
               if (type != null) {
                  this.ed.setType((new URI(type)).toString());
               }

               EncryptionMethod method = this.factory.newEncryptionMethod((new URI(this.algorithm)).toString());
               method.setDigestAlgorithm(this.digestAlg);
               this.ed.setEncryptionMethod(method);
            } catch (URISyntaxException var29) {
               throw new XMLEncryptionException(var29);
            }

            return this.ed;
         }
      }
   }

   private AlgorithmParameterSpec constructBlockCipherParameters(String algorithm, byte[] iv) {
      return XMLCipherUtil.constructBlockCipherParameters(algorithm, iv, this.getClass());
   }

   public EncryptedData loadEncryptedData(Document context, Element element) throws XMLEncryptionException {
      LOG.debug("Loading encrypted element...");
      if (null == context) {
         throw new XMLEncryptionException("empty", new Object[]{"Context document unexpectedly null..."});
      } else if (null == element) {
         throw new XMLEncryptionException("empty", new Object[]{"Element unexpectedly null..."});
      } else if (this.cipherMode != 2) {
         throw new XMLEncryptionException("empty", new Object[]{"XMLCipher unexpectedly not in DECRYPT_MODE..."});
      } else {
         this.contextDocument = context;
         this.ed = this.factory.newEncryptedData(element);
         return this.ed;
      }
   }

   public EncryptedKey loadEncryptedKey(Document context, Element element) throws XMLEncryptionException {
      LOG.debug("Loading encrypted key...");
      if (null == context) {
         throw new XMLEncryptionException("empty", new Object[]{"Context document unexpectedly null..."});
      } else if (null == element) {
         throw new XMLEncryptionException("empty", new Object[]{"Context document unexpectedly null..."});
      } else if (this.cipherMode != 4 && this.cipherMode != 2) {
         throw new XMLEncryptionException("empty", new Object[]{"XMLCipher unexpectedly not in UNWRAP_MODE or DECRYPT_MODE..."});
      } else {
         this.contextDocument = context;
         this.ek = this.factory.newEncryptedKey(element);
         return this.ek;
      }
   }

   public EncryptedKey loadEncryptedKey(Element element) throws XMLEncryptionException {
      return this.loadEncryptedKey(element.getOwnerDocument(), element);
   }

   public EncryptedKey encryptKey(Document doc, Key key) throws XMLEncryptionException {
      return this.encryptKey(doc, key, (String)null, (byte[])null);
   }

   public EncryptedKey encryptKey(Document doc, Key key, String mgfAlgorithm, byte[] oaepParams) throws XMLEncryptionException {
      return this.encryptKey(doc, key, mgfAlgorithm, oaepParams, (SecureRandom)null);
   }

   public EncryptedKey encryptKey(Document doc, Key key, String mgfAlgorithm, byte[] oaepParams, SecureRandom random) throws XMLEncryptionException {
      LOG.debug("Encrypting key ...");
      if (null == key) {
         throw new XMLEncryptionException("empty", new Object[]{"Key unexpectedly null..."});
      } else if (this.cipherMode != 3) {
         throw new XMLEncryptionException("empty", new Object[]{"XMLCipher unexpectedly not in WRAP_MODE..."});
      } else if (this.algorithm == null) {
         throw new XMLEncryptionException("empty", new Object[]{"XMLCipher instance without transformation specified"});
      } else {
         this.contextDocument = doc;
         byte[] encryptedBytes = null;
         Cipher c;
         if (this.contextCipher == null) {
            c = this.constructCipher(this.algorithm, (String)null);
         } else {
            c = this.contextCipher;
         }

         byte[] encryptedBytes;
         try {
            OAEPParameterSpec oaepParameters = this.constructOAEPParameters(this.algorithm, this.digestAlg, mgfAlgorithm, oaepParams);
            if (random != null) {
               if (oaepParameters == null) {
                  c.init(3, this.key, random);
               } else {
                  c.init(3, this.key, oaepParameters, random);
               }
            } else if (oaepParameters == null) {
               c.init(3, this.key);
            } else {
               c.init(3, this.key, oaepParameters);
            }

            encryptedBytes = c.wrap(key);
         } catch (InvalidKeyException var12) {
            throw new XMLEncryptionException(var12);
         } catch (IllegalBlockSizeException var13) {
            throw new XMLEncryptionException(var13);
         } catch (InvalidAlgorithmParameterException var14) {
            throw new XMLEncryptionException(var14);
         }

         String base64EncodedEncryptedOctets = XMLUtils.encodeToString(encryptedBytes);
         LOG.debug("Encrypted key octets:\n{}", base64EncodedEncryptedOctets);
         LOG.debug("Encrypted key octets length = {}", base64EncodedEncryptedOctets.length());
         CipherValue cv = this.ek.getCipherData().getCipherValue();
         cv.setValue(base64EncodedEncryptedOctets);

         try {
            EncryptionMethod method = this.factory.newEncryptionMethod((new URI(this.algorithm)).toString());
            method.setDigestAlgorithm(this.digestAlg);
            method.setMGFAlgorithm(mgfAlgorithm);
            method.setOAEPparams(oaepParams);
            this.ek.setEncryptionMethod(method);
         } catch (URISyntaxException var11) {
            throw new XMLEncryptionException(var11);
         }

         return this.ek;
      }
   }

   public Key decryptKey(EncryptedKey encryptedKey, String algorithm) throws XMLEncryptionException {
      LOG.debug("Decrypting key from previously loaded EncryptedKey...");
      if (this.cipherMode != 4) {
         throw new XMLEncryptionException("empty", new Object[]{"XMLCipher unexpectedly not in UNWRAP_MODE..."});
      } else if (algorithm == null) {
         throw new XMLEncryptionException("empty", new Object[]{"Cannot decrypt a key without knowing the algorithm"});
      } else {
         String jceKeyAlgorithm;
         if (this.key == null) {
            LOG.debug("Trying to find a KEK via key resolvers");
            KeyInfo ki = encryptedKey.getKeyInfo();
            if (ki != null) {
               ki.setSecureValidation(this.secureValidation);

               try {
                  String keyWrapAlg = encryptedKey.getEncryptionMethod().getAlgorithm();
                  jceKeyAlgorithm = JCEMapper.getJCEKeyAlgorithmFromURI(keyWrapAlg);
                  if (!"RSA".equals(jceKeyAlgorithm) && !"EC".equals(jceKeyAlgorithm)) {
                     this.key = ki.getSecretKey();
                  } else {
                     this.key = ki.getPrivateKey();
                  }
               } catch (Exception var13) {
                  LOG.debug(var13.getMessage(), var13);
               }
            }

            if (this.key == null) {
               LOG.error("XMLCipher::decryptKey called without a KEK and cannot resolve");
               throw new XMLEncryptionException("empty", new Object[]{"Unable to decrypt without a KEK"});
            }
         }

         XMLCipherInput cipherInput = new XMLCipherInput(encryptedKey);
         cipherInput.setSecureValidation(this.secureValidation);
         byte[] encryptedBytes = cipherInput.getBytes();
         jceKeyAlgorithm = JCEMapper.getJCEKeyAlgorithmFromURI(algorithm);
         LOG.debug("JCE Key Algorithm: {}", jceKeyAlgorithm);
         Cipher c;
         if (this.contextCipher == null) {
            c = this.constructCipher(encryptedKey.getEncryptionMethod().getAlgorithm(), encryptedKey.getEncryptionMethod().getDigestAlgorithm());
         } else {
            c = this.contextCipher;
         }

         Key ret;
         try {
            EncryptionMethod encMethod = encryptedKey.getEncryptionMethod();
            OAEPParameterSpec oaepParameters = this.constructOAEPParameters(encMethod.getAlgorithm(), encMethod.getDigestAlgorithm(), encMethod.getMGFAlgorithm(), encMethod.getOAEPparams());
            if (oaepParameters == null) {
               c.init(4, this.key);
            } else {
               c.init(4, this.key, oaepParameters);
            }

            ret = c.unwrap(encryptedBytes, jceKeyAlgorithm, 3);
         } catch (InvalidKeyException var10) {
            throw new XMLEncryptionException(var10);
         } catch (NoSuchAlgorithmException var11) {
            throw new XMLEncryptionException(var11);
         } catch (InvalidAlgorithmParameterException var12) {
            throw new XMLEncryptionException(var12);
         }

         LOG.debug("Decryption of key type {} OK", algorithm);
         return ret;
      }
   }

   private OAEPParameterSpec constructOAEPParameters(String encryptionAlgorithm, String digestAlgorithm, String mgfAlgorithm, byte[] oaepParams) {
      if (!"http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p".equals(encryptionAlgorithm) && !"http://www.w3.org/2009/xmlenc11#rsa-oaep".equals(encryptionAlgorithm)) {
         return null;
      } else {
         String jceDigestAlgorithm = "SHA-1";
         if (digestAlgorithm != null) {
            jceDigestAlgorithm = JCEMapper.translateURItoJCEID(digestAlgorithm);
         }

         PSource.PSpecified pSource = PSpecified.DEFAULT;
         if (oaepParams != null) {
            pSource = new PSource.PSpecified(oaepParams);
         }

         MGF1ParameterSpec mgfParameterSpec = new MGF1ParameterSpec("SHA-1");
         if ("http://www.w3.org/2009/xmlenc11#rsa-oaep".equals(encryptionAlgorithm)) {
            if ("http://www.w3.org/2009/xmlenc11#mgf1sha256".equals(mgfAlgorithm)) {
               mgfParameterSpec = new MGF1ParameterSpec("SHA-256");
            } else if ("http://www.w3.org/2009/xmlenc11#mgf1sha384".equals(mgfAlgorithm)) {
               mgfParameterSpec = new MGF1ParameterSpec("SHA-384");
            } else if ("http://www.w3.org/2009/xmlenc11#mgf1sha512".equals(mgfAlgorithm)) {
               mgfParameterSpec = new MGF1ParameterSpec("SHA-512");
            }
         }

         return new OAEPParameterSpec(jceDigestAlgorithm, "MGF1", mgfParameterSpec, pSource);
      }
   }

   private Cipher constructCipher(String algorithm, String digestAlgorithm) throws XMLEncryptionException {
      String jceAlgorithm = JCEMapper.translateURItoJCEID(algorithm);
      LOG.debug("JCE Algorithm = {}", jceAlgorithm);

      Cipher c;
      try {
         if (this.requestedJCEProvider == null) {
            c = Cipher.getInstance(jceAlgorithm);
         } else {
            c = Cipher.getInstance(jceAlgorithm, this.requestedJCEProvider);
         }
      } catch (NoSuchAlgorithmException var6) {
         c = this.constructCipher(algorithm, digestAlgorithm, var6);
      } catch (NoSuchProviderException var7) {
         throw new XMLEncryptionException(var7);
      } catch (NoSuchPaddingException var8) {
         throw new XMLEncryptionException(var8);
      }

      return c;
   }

   private Cipher constructCipher(String algorithm, String digestAlgorithm, Exception nsae) throws XMLEncryptionException {
      if (!"http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p".equals(algorithm)) {
         throw new XMLEncryptionException(nsae);
      } else if (digestAlgorithm != null && !"http://www.w3.org/2000/09/xmldsig#sha1".equals(digestAlgorithm)) {
         if ("http://www.w3.org/2001/04/xmlenc#sha256".equals(digestAlgorithm)) {
            try {
               return this.requestedJCEProvider == null ? Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding") : Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding", this.requestedJCEProvider);
            } catch (Exception var5) {
               throw new XMLEncryptionException(var5);
            }
         } else if ("http://www.w3.org/2001/04/xmldsig-more#sha384".equals(digestAlgorithm)) {
            try {
               return this.requestedJCEProvider == null ? Cipher.getInstance("RSA/ECB/OAEPWithSHA-384AndMGF1Padding") : Cipher.getInstance("RSA/ECB/OAEPWithSHA-384AndMGF1Padding", this.requestedJCEProvider);
            } catch (Exception var6) {
               throw new XMLEncryptionException(var6);
            }
         } else if ("http://www.w3.org/2001/04/xmlenc#sha512".equals(digestAlgorithm)) {
            try {
               return this.requestedJCEProvider == null ? Cipher.getInstance("RSA/ECB/OAEPWithSHA-512AndMGF1Padding") : Cipher.getInstance("RSA/ECB/OAEPWithSHA-512AndMGF1Padding", this.requestedJCEProvider);
            } catch (Exception var7) {
               throw new XMLEncryptionException(var7);
            }
         } else {
            throw new XMLEncryptionException(nsae);
         }
      } else {
         try {
            return this.requestedJCEProvider == null ? Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding") : Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding", this.requestedJCEProvider);
         } catch (Exception var8) {
            throw new XMLEncryptionException(var8);
         }
      }
   }

   public Key decryptKey(EncryptedKey encryptedKey) throws XMLEncryptionException {
      return this.decryptKey(encryptedKey, this.ed.getEncryptionMethod().getAlgorithm());
   }

   private static void removeContent(Node node) {
      while(node.hasChildNodes()) {
         node.removeChild(node.getFirstChild());
      }

   }

   private Document decryptElement(Element element) throws XMLEncryptionException {
      LOG.debug("Decrypting element...");
      if (this.serializer instanceof AbstractSerializer) {
         ((AbstractSerializer)this.serializer).setSecureValidation(this.secureValidation);
      }

      if (element != null && element.getParentNode() == null) {
         throw new XMLEncryptionException("empty", new Object[]{"The element can't be serialized as it has no parent"});
      } else if (this.cipherMode != 2) {
         throw new XMLEncryptionException("empty", new Object[]{"XMLCipher unexpectedly not in DECRYPT_MODE..."});
      } else {
         byte[] octets = this.decryptToByteArray(element);
         if (LOG.isDebugEnabled()) {
            LOG.debug("Decrypted octets:\n" + new String(octets));
         }

         Node sourceParent = element.getParentNode();

         try {
            Node decryptedNode = this.serializer.deserialize(octets, sourceParent);
            if (sourceParent != null && 9 == sourceParent.getNodeType()) {
               this.contextDocument.removeChild(this.contextDocument.getDocumentElement());
               this.contextDocument.appendChild(decryptedNode);
            } else if (sourceParent != null) {
               sourceParent.replaceChild(decryptedNode, element);
            }
         } catch (IOException var5) {
            throw new XMLEncryptionException(var5);
         }

         return this.contextDocument;
      }
   }

   private Document decryptElementContent(Element element) throws XMLEncryptionException {
      Element e = (Element)element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "EncryptedData").item(0);
      if (null == e) {
         throw new XMLEncryptionException("empty", new Object[]{"No EncryptedData child element."});
      } else {
         return this.decryptElement(e);
      }
   }

   public byte[] decryptToByteArray(Element element) throws XMLEncryptionException {
      LOG.debug("Decrypting to ByteArray...");
      if (this.cipherMode != 2) {
         throw new XMLEncryptionException("empty", new Object[]{"XMLCipher unexpectedly not in DECRYPT_MODE..."});
      } else {
         EncryptedData encryptedData = this.factory.newEncryptedData(element);
         String encMethodAlgorithm = encryptedData.getEncryptionMethod().getAlgorithm();
         if (this.key == null) {
            KeyInfo ki = encryptedData.getKeyInfo();
            if (ki != null) {
               try {
                  EncryptedKeyResolver resolver = new EncryptedKeyResolver(encMethodAlgorithm, this.kek);
                  if (this.internalKeyResolvers != null) {
                     int size = this.internalKeyResolvers.size();

                     for(int i = 0; i < size; ++i) {
                        resolver.registerInternalKeyResolver((KeyResolverSpi)this.internalKeyResolvers.get(i));
                     }
                  }

                  ki.registerInternalKeyResolver(resolver);
                  ki.setSecureValidation(this.secureValidation);
                  this.key = ki.getSecretKey();
               } catch (KeyResolverException var20) {
                  LOG.debug(var20.getMessage(), var20);
               }
            }

            if (this.key == null) {
               LOG.error("XMLCipher::decryptElement called without a key and unable to resolve");
               throw new XMLEncryptionException("empty", new Object[]{"encryption.nokey"});
            }
         }

         XMLCipherInput cipherInput = new XMLCipherInput(encryptedData);
         cipherInput.setSecureValidation(this.secureValidation);
         byte[] encryptedBytes = cipherInput.getBytes();
         String jceAlgorithm = JCEMapper.translateURItoJCEID(encMethodAlgorithm);
         LOG.debug("JCE Algorithm = {}", jceAlgorithm);

         Cipher c;
         try {
            if (this.requestedJCEProvider == null) {
               c = Cipher.getInstance(jceAlgorithm);
            } else {
               c = Cipher.getInstance(jceAlgorithm, this.requestedJCEProvider);
            }
         } catch (NoSuchAlgorithmException var17) {
            throw new XMLEncryptionException(var17);
         } catch (NoSuchProviderException var18) {
            throw new XMLEncryptionException(var18);
         } catch (NoSuchPaddingException var19) {
            throw new XMLEncryptionException(var19);
         }

         int ivLen = JCEMapper.getIVLengthFromURI(encMethodAlgorithm) / 8;
         byte[] ivBytes = new byte[ivLen];
         System.arraycopy(encryptedBytes, 0, ivBytes, 0, ivLen);
         String blockCipherAlg = this.algorithm;
         if (blockCipherAlg == null) {
            blockCipherAlg = encMethodAlgorithm;
         }

         AlgorithmParameterSpec paramSpec = this.constructBlockCipherParameters(blockCipherAlg, ivBytes);

         try {
            c.init(this.cipherMode, this.key, paramSpec);
         } catch (InvalidKeyException var15) {
            throw new XMLEncryptionException(var15);
         } catch (InvalidAlgorithmParameterException var16) {
            throw new XMLEncryptionException(var16);
         }

         try {
            return c.doFinal(encryptedBytes, ivLen, encryptedBytes.length - ivLen);
         } catch (IllegalBlockSizeException var13) {
            throw new XMLEncryptionException(var13);
         } catch (BadPaddingException var14) {
            throw new XMLEncryptionException(var14);
         }
      }
   }

   public EncryptedData createEncryptedData(int type, String value) throws XMLEncryptionException {
      EncryptedData result = null;
      CipherData data = null;
      switch (type) {
         case 1:
            CipherValue cipherValue = this.factory.newCipherValue(value);
            data = this.factory.newCipherData(type);
            data.setCipherValue(cipherValue);
            result = this.factory.newEncryptedData(data);
            break;
         case 2:
            CipherReference cipherReference = this.factory.newCipherReference(value);
            data = this.factory.newCipherData(type);
            data.setCipherReference(cipherReference);
            result = this.factory.newEncryptedData(data);
      }

      return result;
   }

   public EncryptedKey createEncryptedKey(int type, String value) throws XMLEncryptionException {
      EncryptedKey result = null;
      CipherData data = null;
      switch (type) {
         case 1:
            CipherValue cipherValue = this.factory.newCipherValue(value);
            data = this.factory.newCipherData(type);
            data.setCipherValue(cipherValue);
            result = this.factory.newEncryptedKey(data);
            break;
         case 2:
            CipherReference cipherReference = this.factory.newCipherReference(value);
            data = this.factory.newCipherData(type);
            data.setCipherReference(cipherReference);
            result = this.factory.newEncryptedKey(data);
      }

      return result;
   }

   public AgreementMethod createAgreementMethod(String algorithm) {
      return this.factory.newAgreementMethod(algorithm);
   }

   public CipherData createCipherData(int type) {
      return this.factory.newCipherData(type);
   }

   public CipherReference createCipherReference(String uri) {
      return this.factory.newCipherReference(uri);
   }

   public CipherValue createCipherValue(String value) {
      return this.factory.newCipherValue(value);
   }

   public EncryptionMethod createEncryptionMethod(String algorithm) {
      return this.factory.newEncryptionMethod(algorithm);
   }

   public EncryptionProperties createEncryptionProperties() {
      return this.factory.newEncryptionProperties();
   }

   public EncryptionProperty createEncryptionProperty() {
      return this.factory.newEncryptionProperty();
   }

   public ReferenceList createReferenceList(int type) {
      return this.factory.newReferenceList(type);
   }

   public Transforms createTransforms() {
      return this.factory.newTransforms();
   }

   public Transforms createTransforms(Document doc) {
      return this.factory.newTransforms(doc);
   }

   private static boolean haveFunctionalIdentityTransformer() {
      String xml = "<a:e1 xmlns:a=\"a\" xmlns:b=\"b\"><a xmlns=\"a\" xmlns:b=\"b\"/></a:e1>";

      try {
         DOMResult domResult = new DOMResult();
         TransformerFactory transformerFactory = TransformerFactory.newInstance();
         transformerFactory.newTransformer().transform(new StreamSource(new ByteArrayInputStream("<a:e1 xmlns:a=\"a\" xmlns:b=\"b\"><a xmlns=\"a\" xmlns:b=\"b\"/></a:e1>".getBytes(StandardCharsets.UTF_8))), domResult);
         boolean result = false;
         if (domResult.getNode().getFirstChild().getFirstChild().hasAttributes() && domResult.getNode().getFirstChild().getFirstChild().getAttributes().getLength() >= 1) {
            result = "http://www.w3.org/2000/xmlns/".equals(domResult.getNode().getFirstChild().getFirstChild().getAttributes().item(1).getNamespaceURI());
         }

         LOG.debug("Have functional IdentityTransformer: {}", result);
         return result;
      } catch (Exception var4) {
         LOG.debug(var4.getMessage(), var4);
         return false;
      }
   }

   private class Factory {
      private Factory() {
      }

      AgreementMethod newAgreementMethod(String algorithm) {
         return new AgreementMethodImpl(algorithm);
      }

      CipherData newCipherData(int type) {
         return new CipherDataImpl(type);
      }

      CipherReference newCipherReference(String uri) {
         return new CipherReferenceImpl(uri);
      }

      CipherValue newCipherValue(String value) {
         return new CipherValueImpl(value);
      }

      EncryptedData newEncryptedData(CipherData data) {
         return new EncryptedDataImpl(data);
      }

      EncryptedKey newEncryptedKey(CipherData data) {
         return new EncryptedKeyImpl(data);
      }

      EncryptionMethod newEncryptionMethod(String algorithm) {
         return new EncryptionMethodImpl(algorithm);
      }

      EncryptionProperties newEncryptionProperties() {
         return new EncryptionPropertiesImpl();
      }

      EncryptionProperty newEncryptionProperty() {
         return new EncryptionPropertyImpl();
      }

      ReferenceList newReferenceList(int type) {
         return new ReferenceListImpl(type);
      }

      Transforms newTransforms() {
         return new TransformsImpl();
      }

      Transforms newTransforms(Document doc) {
         return new TransformsImpl(doc);
      }

      CipherData newCipherData(Element element) throws XMLEncryptionException {
         if (null == element) {
            throw new NullPointerException("element is null");
         } else {
            int type = 0;
            Element e = null;
            if (element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "CipherValue").getLength() > 0) {
               type = 1;
               e = (Element)element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "CipherValue").item(0);
            } else if (element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "CipherReference").getLength() > 0) {
               type = 2;
               e = (Element)element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "CipherReference").item(0);
            }

            CipherData result = this.newCipherData(type);
            if (type == 1) {
               result.setCipherValue(this.newCipherValue(e));
            } else if (type == 2) {
               result.setCipherReference(this.newCipherReference(e));
            }

            return result;
         }
      }

      CipherReference newCipherReference(Element element) throws XMLEncryptionException {
         Attr uriAttr = element.getAttributeNodeNS((String)null, "URI");
         CipherReference result = new CipherReferenceImpl(uriAttr);
         NodeList transformsElements = element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "Transforms");
         Element transformsElement = (Element)transformsElements.item(0);
         if (transformsElement != null) {
            XMLCipher.LOG.debug("Creating a DSIG based Transforms element");

            try {
               result.setTransforms(new TransformsImpl(transformsElement));
            } catch (XMLSignatureException var7) {
               throw new XMLEncryptionException(var7);
            } catch (InvalidTransformException var8) {
               throw new XMLEncryptionException(var8);
            } catch (XMLSecurityException var9) {
               throw new XMLEncryptionException(var9);
            }
         }

         return result;
      }

      CipherValue newCipherValue(Element element) {
         String value = XMLUtils.getFullTextChildrenFromNode(element);
         return this.newCipherValue(value);
      }

      EncryptedData newEncryptedData(Element element) throws XMLEncryptionException {
         EncryptedData result = null;
         NodeList dataElements = element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "CipherData");
         Element dataElement = (Element)dataElements.item(dataElements.getLength() - 1);
         CipherData data = this.newCipherData(dataElement);
         result = this.newEncryptedData(data);
         result.setId(element.getAttributeNS((String)null, "Id"));
         result.setType(element.getAttributeNS((String)null, "Type"));
         result.setMimeType(element.getAttributeNS((String)null, "MimeType"));
         result.setEncoding(element.getAttributeNS((String)null, "Encoding"));
         Element encryptionMethodElement = (Element)element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "EncryptionMethod").item(0);
         if (null != encryptionMethodElement) {
            result.setEncryptionMethod(this.newEncryptionMethod(encryptionMethodElement));
         }

         Element keyInfoElement = (Element)element.getElementsByTagNameNS("http://www.w3.org/2000/09/xmldsig#", "KeyInfo").item(0);
         if (null != keyInfoElement) {
            KeyInfo ki = this.newKeyInfo(keyInfoElement);
            result.setKeyInfo(ki);
         }

         Element encryptionPropertiesElement = (Element)element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "EncryptionProperties").item(0);
         if (null != encryptionPropertiesElement) {
            result.setEncryptionProperties(this.newEncryptionProperties(encryptionPropertiesElement));
         }

         return result;
      }

      EncryptedKey newEncryptedKey(Element element) throws XMLEncryptionException {
         EncryptedKey result = null;
         NodeList dataElements = element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "CipherData");
         Element dataElement = (Element)dataElements.item(dataElements.getLength() - 1);
         CipherData data = this.newCipherData(dataElement);
         result = this.newEncryptedKey(data);
         result.setId(element.getAttributeNS((String)null, "Id"));
         result.setType(element.getAttributeNS((String)null, "Type"));
         result.setMimeType(element.getAttributeNS((String)null, "MimeType"));
         result.setEncoding(element.getAttributeNS((String)null, "Encoding"));
         result.setRecipient(element.getAttributeNS((String)null, "Recipient"));
         Element encryptionMethodElement = (Element)element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "EncryptionMethod").item(0);
         if (null != encryptionMethodElement) {
            result.setEncryptionMethod(this.newEncryptionMethod(encryptionMethodElement));
         }

         Element keyInfoElement = (Element)element.getElementsByTagNameNS("http://www.w3.org/2000/09/xmldsig#", "KeyInfo").item(0);
         if (null != keyInfoElement) {
            KeyInfo ki = this.newKeyInfo(keyInfoElement);
            result.setKeyInfo(ki);
         }

         Element encryptionPropertiesElement = (Element)element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "EncryptionProperties").item(0);
         if (null != encryptionPropertiesElement) {
            result.setEncryptionProperties(this.newEncryptionProperties(encryptionPropertiesElement));
         }

         Element referenceListElement = (Element)element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "ReferenceList").item(0);
         if (null != referenceListElement) {
            result.setReferenceList(this.newReferenceList(referenceListElement));
         }

         Element carriedNameElement = (Element)element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "CarriedKeyName").item(0);
         if (null != carriedNameElement) {
            result.setCarriedName(carriedNameElement.getFirstChild().getNodeValue());
         }

         return result;
      }

      KeyInfo newKeyInfo(Element element) throws XMLEncryptionException {
         try {
            KeyInfo ki = new KeyInfo(element, (String)null);
            ki.setSecureValidation(XMLCipher.this.secureValidation);
            if (XMLCipher.this.internalKeyResolvers != null) {
               int size = XMLCipher.this.internalKeyResolvers.size();

               for(int i = 0; i < size; ++i) {
                  ki.registerInternalKeyResolver((KeyResolverSpi)XMLCipher.this.internalKeyResolvers.get(i));
               }
            }

            return ki;
         } catch (XMLSecurityException var5) {
            throw new XMLEncryptionException(var5, "KeyInfo.error");
         }
      }

      EncryptionMethod newEncryptionMethod(Element element) {
         String encAlgorithm = element.getAttributeNS((String)null, "Algorithm");
         EncryptionMethod result = this.newEncryptionMethod(encAlgorithm);
         Element keySizeElement = (Element)element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "KeySize").item(0);
         if (null != keySizeElement) {
            result.setKeySize(Integer.parseInt(keySizeElement.getFirstChild().getNodeValue()));
         }

         Element oaepParamsElement = (Element)element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "OAEPparams").item(0);
         if (null != oaepParamsElement) {
            String oaepParams = oaepParamsElement.getFirstChild().getNodeValue();
            result.setOAEPparams(XMLUtils.decode(oaepParams.getBytes(StandardCharsets.UTF_8)));
         }

         Element digestElement = (Element)element.getElementsByTagNameNS("http://www.w3.org/2000/09/xmldsig#", "DigestMethod").item(0);
         if (digestElement != null) {
            String digestAlgorithm = digestElement.getAttributeNS((String)null, "Algorithm");
            result.setDigestAlgorithm(digestAlgorithm);
         }

         Element mgfElement = (Element)element.getElementsByTagNameNS("http://www.w3.org/2009/xmlenc11#", "MGF").item(0);
         if (mgfElement != null && !"http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p".equals(XMLCipher.this.algorithm)) {
            String mgfAlgorithm = mgfElement.getAttributeNS((String)null, "Algorithm");
            result.setMGFAlgorithm(mgfAlgorithm);
         }

         return result;
      }

      EncryptionProperties newEncryptionProperties(Element element) {
         EncryptionProperties result = this.newEncryptionProperties();
         result.setId(element.getAttributeNS((String)null, "Id"));
         NodeList encryptionPropertyList = element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "EncryptionProperty");
         int length = encryptionPropertyList.getLength();

         for(int i = 0; i < length; ++i) {
            Node n = encryptionPropertyList.item(i);
            if (null != n) {
               result.addEncryptionProperty(this.newEncryptionProperty((Element)n));
            }
         }

         return result;
      }

      EncryptionProperty newEncryptionProperty(Element element) {
         EncryptionProperty result = this.newEncryptionProperty();
         result.setTarget(element.getAttributeNS((String)null, "Target"));
         result.setId(element.getAttributeNS((String)null, "Id"));
         return result;
      }

      ReferenceList newReferenceList(Element element) {
         int type = 0;
         if (null != element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "DataReference").item(0)) {
            type = 1;
         } else if (null != element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "KeyReference").item(0)) {
            type = 2;
         }

         ReferenceList result = new ReferenceListImpl(type);
         NodeList list = null;
         int i;
         switch (type) {
            case 1:
               list = element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "DataReference");
               int drLength = list.getLength();

               for(i = 0; i < drLength; ++i) {
                  String urix = ((Element)list.item(i)).getAttributeNS((String)null, "URI");
                  result.add(result.newDataReference(urix));
               }

               return result;
            case 2:
               list = element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "KeyReference");
               i = list.getLength();

               for(int ix = 0; ix < i; ++ix) {
                  String uri = ((Element)list.item(ix)).getAttributeNS((String)null, "URI");
                  result.add(result.newKeyReference(uri));
               }
         }

         return result;
      }

      Element toElement(EncryptedData encryptedData) {
         return ((EncryptedDataImpl)encryptedData).toElement();
      }

      Element toElement(EncryptedKey encryptedKey) {
         return ((EncryptedKeyImpl)encryptedKey).toElement();
      }

      Element toElement(ReferenceList referenceList) {
         return ((ReferenceListImpl)referenceList).toElement();
      }

      // $FF: synthetic method
      Factory(Object x1) {
         this();
      }

      private class ReferenceListImpl implements ReferenceList {
         private Class sentry;
         private List references;

         public ReferenceListImpl(int type) {
            if (type == 1) {
               this.sentry = DataReference.class;
            } else {
               if (type != 2) {
                  throw new IllegalArgumentException();
               }

               this.sentry = KeyReference.class;
            }

            this.references = new LinkedList();
         }

         public void add(Reference reference) {
            if (!reference.getClass().equals(this.sentry)) {
               throw new IllegalArgumentException();
            } else {
               this.references.add(reference);
            }
         }

         public void remove(Reference reference) {
            if (!reference.getClass().equals(this.sentry)) {
               throw new IllegalArgumentException();
            } else {
               this.references.remove(reference);
            }
         }

         public int size() {
            return this.references.size();
         }

         public boolean isEmpty() {
            return this.references.isEmpty();
         }

         public Iterator getReferences() {
            return this.references.iterator();
         }

         Element toElement() {
            Element result = ElementProxy.createElementForFamily(XMLCipher.this.contextDocument, "http://www.w3.org/2001/04/xmlenc#", "ReferenceList");
            Iterator eachReference = this.references.iterator();

            while(eachReference.hasNext()) {
               Reference reference = (Reference)eachReference.next();
               result.appendChild(((ReferenceImpl)reference).toElement());
            }

            return result;
         }

         public Reference newDataReference(String uri) {
            return new DataReference(uri);
         }

         public Reference newKeyReference(String uri) {
            return new KeyReference(uri);
         }

         private class KeyReference extends ReferenceImpl {
            KeyReference(String uri) {
               super(uri);
            }

            public String getType() {
               return "KeyReference";
            }
         }

         private class DataReference extends ReferenceImpl {
            DataReference(String uri) {
               super(uri);
            }

            public String getType() {
               return "DataReference";
            }
         }

         private abstract class ReferenceImpl implements Reference {
            private String uri;
            private List referenceInformation;

            ReferenceImpl(String uri) {
               this.uri = uri;
               this.referenceInformation = new LinkedList();
            }

            public abstract String getType();

            public String getURI() {
               return this.uri;
            }

            public Iterator getElementRetrievalInformation() {
               return this.referenceInformation.iterator();
            }

            public void setURI(String uri) {
               this.uri = uri;
            }

            public void removeElementRetrievalInformation(Element node) {
               this.referenceInformation.remove(node);
            }

            public void addElementRetrievalInformation(Element node) {
               this.referenceInformation.add(node);
            }

            public Element toElement() {
               String tagName = this.getType();
               Element result = ElementProxy.createElementForFamily(XMLCipher.this.contextDocument, "http://www.w3.org/2001/04/xmlenc#", tagName);
               result.setAttributeNS((String)null, "URI", this.uri);
               return result;
            }
         }
      }

      private class TransformsImpl extends org.apache.xml.security.transforms.Transforms implements Transforms {
         public TransformsImpl() {
            super(XMLCipher.this.contextDocument);
         }

         public TransformsImpl(Document doc) {
            if (doc == null) {
               throw new RuntimeException("Document is null");
            } else {
               this.setDocument(doc);
               this.setElement(this.createElementForFamilyLocal(this.getBaseNamespace(), this.getBaseLocalName()));
            }
         }

         public TransformsImpl(Element element) throws XMLSignatureException, InvalidTransformException, XMLSecurityException, TransformationException {
            super(element, "");
         }

         public Element toElement() {
            if (this.getDocument() == null) {
               this.setDocument(XMLCipher.this.contextDocument);
            }

            return this.getElement();
         }

         public org.apache.xml.security.transforms.Transforms getDSTransforms() {
            return this;
         }

         public String getBaseNamespace() {
            return "http://www.w3.org/2001/04/xmlenc#";
         }
      }

      private class EncryptionPropertyImpl implements EncryptionProperty {
         private String target;
         private String id;
         private Map attributeMap = new HashMap();
         private List encryptionInformation = new LinkedList();

         public EncryptionPropertyImpl() {
         }

         public String getTarget() {
            return this.target;
         }

         public void setTarget(String target) {
            if (target != null && target.length() != 0) {
               if (target.startsWith("#")) {
                  this.target = target;
               } else {
                  URI tmpTarget = null;

                  try {
                     tmpTarget = new URI(target);
                  } catch (URISyntaxException var4) {
                     throw (IllegalArgumentException)(new IllegalArgumentException()).initCause(var4);
                  }

                  this.target = tmpTarget.toString();
               }
            } else {
               this.target = null;
            }

         }

         public String getId() {
            return this.id;
         }

         public void setId(String id) {
            this.id = id;
         }

         public String getAttribute(String attribute) {
            return (String)this.attributeMap.get(attribute);
         }

         public void setAttribute(String attribute, String value) {
            this.attributeMap.put(attribute, value);
         }

         public Iterator getEncryptionInformation() {
            return this.encryptionInformation.iterator();
         }

         public void addEncryptionInformation(Element info) {
            this.encryptionInformation.add(info);
         }

         public void removeEncryptionInformation(Element info) {
            this.encryptionInformation.remove(info);
         }

         Element toElement() {
            Element result = XMLUtils.createElementInEncryptionSpace(XMLCipher.this.contextDocument, "EncryptionProperty");
            if (null != this.target) {
               result.setAttributeNS((String)null, "Target", this.target);
            }

            if (null != this.id) {
               result.setAttributeNS((String)null, "Id", this.id);
            }

            Iterator var2;
            if (!this.attributeMap.isEmpty()) {
               var2 = this.attributeMap.entrySet().iterator();

               while(var2.hasNext()) {
                  Map.Entry entry = (Map.Entry)var2.next();
                  result.setAttributeNS("http://www.w3.org/XML/1998/namespace", (String)entry.getKey(), (String)entry.getValue());
               }
            }

            if (!this.encryptionInformation.isEmpty()) {
               var2 = this.encryptionInformation.iterator();

               while(var2.hasNext()) {
                  Element element = (Element)var2.next();
                  result.appendChild(element);
               }
            }

            return result;
         }
      }

      private class EncryptionPropertiesImpl implements EncryptionProperties {
         private String id;
         private List encryptionProperties = new LinkedList();

         public EncryptionPropertiesImpl() {
         }

         public String getId() {
            return this.id;
         }

         public void setId(String id) {
            this.id = id;
         }

         public Iterator getEncryptionProperties() {
            return this.encryptionProperties.iterator();
         }

         public void addEncryptionProperty(EncryptionProperty property) {
            this.encryptionProperties.add(property);
         }

         public void removeEncryptionProperty(EncryptionProperty property) {
            this.encryptionProperties.remove(property);
         }

         Element toElement() {
            Element result = XMLUtils.createElementInEncryptionSpace(XMLCipher.this.contextDocument, "EncryptionProperties");
            if (null != this.id) {
               result.setAttributeNS((String)null, "Id", this.id);
            }

            Iterator itr = this.getEncryptionProperties();

            while(itr.hasNext()) {
               result.appendChild(((EncryptionPropertyImpl)itr.next()).toElement());
            }

            return result;
         }
      }

      private class EncryptionMethodImpl implements EncryptionMethod {
         private String algorithm;
         private int keySize = Integer.MIN_VALUE;
         private byte[] oaepParams;
         private List encryptionMethodInformation;
         private String digestAlgorithm;
         private String mgfAlgorithm;

         public EncryptionMethodImpl(String algorithm) {
            URI tmpAlgorithm = null;

            try {
               tmpAlgorithm = new URI(algorithm);
            } catch (URISyntaxException var5) {
               throw (IllegalArgumentException)(new IllegalArgumentException()).initCause(var5);
            }

            this.algorithm = tmpAlgorithm.toString();
            this.encryptionMethodInformation = new LinkedList();
         }

         public String getAlgorithm() {
            return this.algorithm;
         }

         public int getKeySize() {
            return this.keySize;
         }

         public void setKeySize(int size) {
            this.keySize = size;
         }

         public byte[] getOAEPparams() {
            return this.oaepParams;
         }

         public void setOAEPparams(byte[] params) {
            this.oaepParams = params;
         }

         public void setDigestAlgorithm(String digestAlgorithm) {
            this.digestAlgorithm = digestAlgorithm;
         }

         public String getDigestAlgorithm() {
            return this.digestAlgorithm;
         }

         public void setMGFAlgorithm(String mgfAlgorithm) {
            this.mgfAlgorithm = mgfAlgorithm;
         }

         public String getMGFAlgorithm() {
            return this.mgfAlgorithm;
         }

         public Iterator getEncryptionMethodInformation() {
            return this.encryptionMethodInformation.iterator();
         }

         public void addEncryptionMethodInformation(Element info) {
            this.encryptionMethodInformation.add(info);
         }

         public void removeEncryptionMethodInformation(Element info) {
            this.encryptionMethodInformation.remove(info);
         }

         Element toElement() {
            Element result = XMLUtils.createElementInEncryptionSpace(XMLCipher.this.contextDocument, "EncryptionMethod");
            result.setAttributeNS((String)null, "Algorithm", this.algorithm);
            if (this.keySize > 0) {
               result.appendChild(XMLUtils.createElementInEncryptionSpace(XMLCipher.this.contextDocument, "KeySize").appendChild(XMLCipher.this.contextDocument.createTextNode(String.valueOf(this.keySize))));
            }

            Element mgfElement;
            if (null != this.oaepParams) {
               mgfElement = XMLUtils.createElementInEncryptionSpace(XMLCipher.this.contextDocument, "OAEPparams");
               mgfElement.appendChild(XMLCipher.this.contextDocument.createTextNode(XMLUtils.encodeToString(this.oaepParams)));
               result.appendChild(mgfElement);
            }

            if (this.digestAlgorithm != null) {
               mgfElement = XMLUtils.createElementInSignatureSpace(XMLCipher.this.contextDocument, "DigestMethod");
               mgfElement.setAttributeNS((String)null, "Algorithm", this.digestAlgorithm);
               mgfElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:" + ElementProxy.getDefaultPrefix("http://www.w3.org/2000/09/xmldsig#"), "http://www.w3.org/2000/09/xmldsig#");
               result.appendChild(mgfElement);
            }

            if (this.mgfAlgorithm != null) {
               mgfElement = XMLUtils.createElementInEncryption11Space(XMLCipher.this.contextDocument, "MGF");
               mgfElement.setAttributeNS((String)null, "Algorithm", this.mgfAlgorithm);
               mgfElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:" + ElementProxy.getDefaultPrefix("http://www.w3.org/2009/xmlenc11#"), "http://www.w3.org/2009/xmlenc11#");
               result.appendChild(mgfElement);
            }

            Iterator itr = this.encryptionMethodInformation.iterator();

            while(itr.hasNext()) {
               result.appendChild((Node)itr.next());
            }

            return result;
         }
      }

      private abstract class EncryptedTypeImpl {
         private String id;
         private String type;
         private String mimeType;
         private String encoding;
         private EncryptionMethod encryptionMethod;
         private KeyInfo keyInfo;
         private CipherData cipherData;
         private EncryptionProperties encryptionProperties;

         protected EncryptedTypeImpl(CipherData data) {
            this.cipherData = data;
         }

         public String getId() {
            return this.id;
         }

         public void setId(String id) {
            this.id = id;
         }

         public String getType() {
            return this.type;
         }

         public void setType(String type) {
            if (type != null && type.length() != 0) {
               URI tmpType = null;

               try {
                  tmpType = new URI(type);
               } catch (URISyntaxException var4) {
                  throw (IllegalArgumentException)(new IllegalArgumentException()).initCause(var4);
               }

               this.type = tmpType.toString();
            } else {
               this.type = null;
            }

         }

         public String getMimeType() {
            return this.mimeType;
         }

         public void setMimeType(String type) {
            this.mimeType = type;
         }

         public String getEncoding() {
            return this.encoding;
         }

         public void setEncoding(String encoding) {
            if (encoding != null && encoding.length() != 0) {
               URI tmpEncoding = null;

               try {
                  tmpEncoding = new URI(encoding);
               } catch (URISyntaxException var4) {
                  throw (IllegalArgumentException)(new IllegalArgumentException()).initCause(var4);
               }

               this.encoding = tmpEncoding.toString();
            } else {
               this.encoding = null;
            }

         }

         public EncryptionMethod getEncryptionMethod() {
            return this.encryptionMethod;
         }

         public void setEncryptionMethod(EncryptionMethod method) {
            this.encryptionMethod = method;
         }

         public KeyInfo getKeyInfo() {
            return this.keyInfo;
         }

         public void setKeyInfo(KeyInfo info) {
            this.keyInfo = info;
         }

         public CipherData getCipherData() {
            return this.cipherData;
         }

         public EncryptionProperties getEncryptionProperties() {
            return this.encryptionProperties;
         }

         public void setEncryptionProperties(EncryptionProperties properties) {
            this.encryptionProperties = properties;
         }
      }

      private class EncryptedKeyImpl extends EncryptedTypeImpl implements EncryptedKey {
         private String keyRecipient;
         private ReferenceList referenceList;
         private String carriedName;

         public EncryptedKeyImpl(CipherData data) {
            super(data);
         }

         public String getRecipient() {
            return this.keyRecipient;
         }

         public void setRecipient(String recipient) {
            this.keyRecipient = recipient;
         }

         public ReferenceList getReferenceList() {
            return this.referenceList;
         }

         public void setReferenceList(ReferenceList list) {
            this.referenceList = list;
         }

         public String getCarriedName() {
            return this.carriedName;
         }

         public void setCarriedName(String name) {
            this.carriedName = name;
         }

         Element toElement() {
            Element result = ElementProxy.createElementForFamily(XMLCipher.this.contextDocument, "http://www.w3.org/2001/04/xmlenc#", "EncryptedKey");
            if (null != super.getId()) {
               result.setAttributeNS((String)null, "Id", super.getId());
            }

            if (null != super.getType()) {
               result.setAttributeNS((String)null, "Type", super.getType());
            }

            if (null != super.getMimeType()) {
               result.setAttributeNS((String)null, "MimeType", super.getMimeType());
            }

            if (null != super.getEncoding()) {
               result.setAttributeNS((String)null, "Encoding", super.getEncoding());
            }

            if (null != this.getRecipient()) {
               result.setAttributeNS((String)null, "Recipient", this.getRecipient());
            }

            if (null != super.getEncryptionMethod()) {
               result.appendChild(((EncryptionMethodImpl)super.getEncryptionMethod()).toElement());
            }

            if (null != super.getKeyInfo()) {
               result.appendChild(super.getKeyInfo().getElement().cloneNode(true));
            }

            result.appendChild(((CipherDataImpl)super.getCipherData()).toElement());
            if (null != super.getEncryptionProperties()) {
               result.appendChild(((EncryptionPropertiesImpl)super.getEncryptionProperties()).toElement());
            }

            if (this.referenceList != null && !this.referenceList.isEmpty()) {
               result.appendChild(((ReferenceListImpl)this.getReferenceList()).toElement());
            }

            if (null != this.carriedName) {
               Element element = ElementProxy.createElementForFamily(XMLCipher.this.contextDocument, "http://www.w3.org/2001/04/xmlenc#", "CarriedKeyName");
               Node node = XMLCipher.this.contextDocument.createTextNode(this.carriedName);
               element.appendChild(node);
               result.appendChild(element);
            }

            return result;
         }
      }

      private class EncryptedDataImpl extends EncryptedTypeImpl implements EncryptedData {
         public EncryptedDataImpl(CipherData data) {
            super(data);
         }

         Element toElement() {
            Element result = ElementProxy.createElementForFamily(XMLCipher.this.contextDocument, "http://www.w3.org/2001/04/xmlenc#", "EncryptedData");
            if (null != super.getId()) {
               result.setAttributeNS((String)null, "Id", super.getId());
            }

            if (null != super.getType()) {
               result.setAttributeNS((String)null, "Type", super.getType());
            }

            if (null != super.getMimeType()) {
               result.setAttributeNS((String)null, "MimeType", super.getMimeType());
            }

            if (null != super.getEncoding()) {
               result.setAttributeNS((String)null, "Encoding", super.getEncoding());
            }

            if (null != super.getEncryptionMethod()) {
               result.appendChild(((EncryptionMethodImpl)super.getEncryptionMethod()).toElement());
            }

            if (null != super.getKeyInfo()) {
               result.appendChild(super.getKeyInfo().getElement().cloneNode(true));
            }

            result.appendChild(((CipherDataImpl)super.getCipherData()).toElement());
            if (null != super.getEncryptionProperties()) {
               result.appendChild(((EncryptionPropertiesImpl)super.getEncryptionProperties()).toElement());
            }

            return result;
         }
      }

      private class CipherValueImpl implements CipherValue {
         private String cipherValue;

         public CipherValueImpl(String value) {
            this.cipherValue = value;
         }

         public String getValue() {
            return this.cipherValue;
         }

         public void setValue(String value) {
            this.cipherValue = value;
         }

         Element toElement() {
            Element result = XMLUtils.createElementInEncryptionSpace(XMLCipher.this.contextDocument, "CipherValue");
            result.appendChild(XMLCipher.this.contextDocument.createTextNode(this.cipherValue));
            return result;
         }
      }

      private class CipherReferenceImpl implements CipherReference {
         private String referenceURI;
         private Transforms referenceTransforms;
         private Attr referenceNode;

         public CipherReferenceImpl(String uri) {
            this.referenceURI = uri;
            this.referenceNode = null;
         }

         public CipherReferenceImpl(Attr uri) {
            this.referenceURI = uri.getNodeValue();
            this.referenceNode = uri;
         }

         public String getURI() {
            return this.referenceURI;
         }

         public Attr getURIAsAttr() {
            return this.referenceNode;
         }

         public Transforms getTransforms() {
            return this.referenceTransforms;
         }

         public void setTransforms(Transforms transforms) {
            this.referenceTransforms = transforms;
         }

         Element toElement() {
            Element result = XMLUtils.createElementInEncryptionSpace(XMLCipher.this.contextDocument, "CipherReference");
            result.setAttributeNS((String)null, "URI", this.referenceURI);
            if (null != this.referenceTransforms) {
               result.appendChild(((TransformsImpl)this.referenceTransforms).toElement());
            }

            return result;
         }
      }

      private class CipherDataImpl implements CipherData {
         private static final String valueMessage = "Data type is reference type.";
         private static final String referenceMessage = "Data type is value type.";
         private CipherValue cipherValue;
         private CipherReference cipherReference;
         private int cipherType = Integer.MIN_VALUE;

         public CipherDataImpl(int type) {
            this.cipherType = type;
         }

         public CipherValue getCipherValue() {
            return this.cipherValue;
         }

         public void setCipherValue(CipherValue value) throws XMLEncryptionException {
            if (this.cipherType == 2) {
               throw new XMLEncryptionException(new UnsupportedOperationException("Data type is reference type."));
            } else {
               this.cipherValue = value;
            }
         }

         public CipherReference getCipherReference() {
            return this.cipherReference;
         }

         public void setCipherReference(CipherReference reference) throws XMLEncryptionException {
            if (this.cipherType == 1) {
               throw new XMLEncryptionException(new UnsupportedOperationException("Data type is value type."));
            } else {
               this.cipherReference = reference;
            }
         }

         public int getDataType() {
            return this.cipherType;
         }

         Element toElement() {
            Element result = XMLUtils.createElementInEncryptionSpace(XMLCipher.this.contextDocument, "CipherData");
            if (this.cipherType == 1) {
               result.appendChild(((CipherValueImpl)this.cipherValue).toElement());
            } else if (this.cipherType == 2) {
               result.appendChild(((CipherReferenceImpl)this.cipherReference).toElement());
            }

            return result;
         }
      }

      private class AgreementMethodImpl implements AgreementMethod {
         private byte[] kaNonce;
         private List agreementMethodInformation = new LinkedList();
         private KeyInfo originatorKeyInfo;
         private KeyInfo recipientKeyInfo;
         private String algorithmURI;

         public AgreementMethodImpl(String algorithm) {
            URI tmpAlgorithm = null;

            try {
               tmpAlgorithm = new URI(algorithm);
            } catch (URISyntaxException var5) {
               throw (IllegalArgumentException)(new IllegalArgumentException()).initCause(var5);
            }

            this.algorithmURI = tmpAlgorithm.toString();
         }

         public byte[] getKANonce() {
            return this.kaNonce;
         }

         public void setKANonce(byte[] kanonce) {
            this.kaNonce = kanonce;
         }

         public Iterator getAgreementMethodInformation() {
            return this.agreementMethodInformation.iterator();
         }

         public void addAgreementMethodInformation(Element info) {
            this.agreementMethodInformation.add(info);
         }

         public void revoveAgreementMethodInformation(Element info) {
            this.agreementMethodInformation.remove(info);
         }

         public KeyInfo getOriginatorKeyInfo() {
            return this.originatorKeyInfo;
         }

         public void setOriginatorKeyInfo(KeyInfo keyInfo) {
            this.originatorKeyInfo = keyInfo;
         }

         public KeyInfo getRecipientKeyInfo() {
            return this.recipientKeyInfo;
         }

         public void setRecipientKeyInfo(KeyInfo keyInfo) {
            this.recipientKeyInfo = keyInfo;
         }

         public String getAlgorithm() {
            return this.algorithmURI;
         }
      }
   }
}
