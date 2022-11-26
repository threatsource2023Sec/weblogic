package org.apache.xml.security.stax.ext;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.PublicKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.security.interfaces.DSAPublicKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.apache.xml.security.algorithms.JCEMapper;
import org.apache.xml.security.algorithms.implementations.ECDSAUtils;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.keys.content.x509.XMLX509SKI;
import org.apache.xml.security.stax.config.TransformerAlgorithmMapper;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.ext.stax.XMLSecNamespace;
import org.apache.xml.security.stax.ext.stax.XMLSecStartElement;
import org.apache.xml.security.stax.impl.util.ConcreteLSInput;
import org.apache.xml.security.stax.securityEvent.DefaultTokenSecurityEvent;
import org.apache.xml.security.stax.securityEvent.EncryptedKeyTokenSecurityEvent;
import org.apache.xml.security.stax.securityEvent.KeyNameTokenSecurityEvent;
import org.apache.xml.security.stax.securityEvent.KeyValueTokenSecurityEvent;
import org.apache.xml.security.stax.securityEvent.TokenSecurityEvent;
import org.apache.xml.security.stax.securityEvent.X509TokenSecurityEvent;
import org.apache.xml.security.stax.securityToken.InboundSecurityToken;
import org.apache.xml.security.stax.securityToken.SecurityTokenConstants;
import org.apache.xml.security.utils.ClassLoaderUtils;
import org.apache.xml.security.utils.XMLUtils;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.SAXException;

public class XMLSecurityUtils {
   private static final int MAX_SYMMETRIC_KEY_SIZE = 1024;

   protected XMLSecurityUtils() {
   }

   public static String dropReferenceMarker(String reference) {
      return reference != null && reference.length() > 0 && reference.charAt(0) == '#' ? reference.substring(1) : reference;
   }

   public static String getXMLEventAsString(XMLSecEvent xmlSecEvent) {
      int eventType = xmlSecEvent.getEventType();
      switch (eventType) {
         case 1:
            return "START_ELEMENT";
         case 2:
            return "END_ELEMENT";
         case 3:
            return "PROCESSING_INSTRUCTION";
         case 4:
            return "CHARACTERS";
         case 5:
            return "COMMENT";
         case 6:
         case 9:
         case 12:
         default:
            throw new IllegalArgumentException("Illegal XMLSecEvent received: " + eventType);
         case 7:
            return "START_DOCUMENT";
         case 8:
            return "END_DOCUMENT";
         case 10:
            return "ATTRIBUTE";
         case 11:
            return "DTD";
         case 13:
            return "NAMESPACE";
      }
   }

   public static Transformer getTransformer(Transformer transformer, OutputStream outputStream, Map properties, String algorithm, XMLSecurityConstants.DIRECTION direction) throws XMLSecurityException {
      Class transformerClass = TransformerAlgorithmMapper.getTransformerClass(algorithm, direction);
      Transformer childTransformer = null;

      try {
         childTransformer = (Transformer)transformerClass.newInstance();
         if (properties != null) {
            childTransformer.setProperties(properties);
         }

         if (outputStream != null) {
            childTransformer.setOutputStream(outputStream);
         } else {
            childTransformer.setTransformer(transformer);
         }

         return childTransformer;
      } catch (InstantiationException var8) {
         throw new XMLSecurityException(var8);
      } catch (IllegalAccessException var9) {
         throw new XMLSecurityException(var9);
      }
   }

   public static Object getType(List objects, Class clazz) {
      for(int i = 0; i < objects.size(); ++i) {
         Object o = objects.get(i);
         if (o instanceof JAXBElement) {
            o = ((JAXBElement)o).getValue();
         }

         if (clazz.isAssignableFrom(o.getClass())) {
            return o;
         }
      }

      return null;
   }

   public static Object getQNameType(List objects, QName qName) {
      for(int i = 0; i < objects.size(); ++i) {
         Object o = objects.get(i);
         if (o instanceof JAXBElement) {
            JAXBElement jaxbElement = (JAXBElement)o;
            if (jaxbElement.getName().equals(qName)) {
               return jaxbElement.getValue();
            }
         }
      }

      return null;
   }

   public static String getQNameAttribute(Map attributes, QName qName) {
      return (String)attributes.get(qName);
   }

   public static void createKeyValueTokenStructure(AbstractOutputProcessor abstractOutputProcessor, OutputProcessorChain outputProcessorChain, X509Certificate[] x509Certificates) throws XMLStreamException, XMLSecurityException {
      if (x509Certificates != null && x509Certificates.length != 0) {
         X509Certificate x509Certificate = x509Certificates[0];
         PublicKey publicKey = x509Certificate.getPublicKey();
         createKeyValueTokenStructure(abstractOutputProcessor, outputProcessorChain, publicKey);
      } else {
         throw new XMLSecurityException("stax.signature.publicKeyOrCertificateMissing");
      }
   }

   public static void createKeyValueTokenStructure(AbstractOutputProcessor abstractOutputProcessor, OutputProcessorChain outputProcessorChain, PublicKey publicKey) throws XMLStreamException, XMLSecurityException {
      if (publicKey == null) {
         throw new XMLSecurityException("stax.signature.publicKeyOrCertificateMissing");
      } else {
         String algorithm = publicKey.getAlgorithm();
         abstractOutputProcessor.createStartElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig_KeyValue, true, (List)null);
         if ("RSA".equals(algorithm)) {
            RSAPublicKey rsaPublicKey = (RSAPublicKey)publicKey;
            abstractOutputProcessor.createStartElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig_RSAKeyValue, false, (List)null);
            abstractOutputProcessor.createStartElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig_Modulus, false, (List)null);
            abstractOutputProcessor.createCharactersAndOutputAsEvent(outputProcessorChain, XMLUtils.encodeToString(rsaPublicKey.getModulus().toByteArray()));
            abstractOutputProcessor.createEndElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig_Modulus);
            abstractOutputProcessor.createStartElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig_Exponent, false, (List)null);
            abstractOutputProcessor.createCharactersAndOutputAsEvent(outputProcessorChain, XMLUtils.encodeToString(rsaPublicKey.getPublicExponent().toByteArray()));
            abstractOutputProcessor.createEndElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig_Exponent);
            abstractOutputProcessor.createEndElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig_RSAKeyValue);
         } else if ("DSA".equals(algorithm)) {
            DSAPublicKey dsaPublicKey = (DSAPublicKey)publicKey;
            BigInteger j = dsaPublicKey.getParams().getP().subtract(BigInteger.ONE).divide(dsaPublicKey.getParams().getQ());
            abstractOutputProcessor.createStartElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig_DSAKeyValue, false, (List)null);
            abstractOutputProcessor.createStartElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig_P, false, (List)null);
            abstractOutputProcessor.createCharactersAndOutputAsEvent(outputProcessorChain, XMLUtils.encodeToString(dsaPublicKey.getParams().getP().toByteArray()));
            abstractOutputProcessor.createEndElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig_P);
            abstractOutputProcessor.createStartElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig_Q, false, (List)null);
            abstractOutputProcessor.createCharactersAndOutputAsEvent(outputProcessorChain, XMLUtils.encodeToString(dsaPublicKey.getParams().getQ().toByteArray()));
            abstractOutputProcessor.createEndElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig_Q);
            abstractOutputProcessor.createStartElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig_G, false, (List)null);
            abstractOutputProcessor.createCharactersAndOutputAsEvent(outputProcessorChain, XMLUtils.encodeToString(dsaPublicKey.getParams().getG().toByteArray()));
            abstractOutputProcessor.createEndElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig_G);
            abstractOutputProcessor.createStartElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig_Y, false, (List)null);
            abstractOutputProcessor.createCharactersAndOutputAsEvent(outputProcessorChain, XMLUtils.encodeToString(dsaPublicKey.getY().toByteArray()));
            abstractOutputProcessor.createEndElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig_Y);
            abstractOutputProcessor.createStartElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig_J, false, (List)null);
            abstractOutputProcessor.createCharactersAndOutputAsEvent(outputProcessorChain, XMLUtils.encodeToString(j.toByteArray()));
            abstractOutputProcessor.createEndElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig_J);
            abstractOutputProcessor.createEndElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig_DSAKeyValue);
         } else if ("EC".equals(algorithm)) {
            ECPublicKey ecPublicKey = (ECPublicKey)publicKey;
            List attributes = new ArrayList(1);
            attributes.add(abstractOutputProcessor.createAttribute(XMLSecurityConstants.ATT_NULL_URI, "urn:oid:" + ECDSAUtils.getOIDFromPublicKey(ecPublicKey)));
            abstractOutputProcessor.createStartElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig11_ECKeyValue, true, (List)null);
            abstractOutputProcessor.createStartElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig11_NamedCurve, false, attributes);
            abstractOutputProcessor.createEndElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig11_NamedCurve);
            abstractOutputProcessor.createStartElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig11_PublicKey, false, (List)null);
            abstractOutputProcessor.createCharactersAndOutputAsEvent(outputProcessorChain, XMLUtils.encodeToString(ECDSAUtils.encodePoint(ecPublicKey.getW(), ecPublicKey.getParams().getCurve())));
            abstractOutputProcessor.createEndElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig11_PublicKey);
            abstractOutputProcessor.createEndElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig11_ECKeyValue);
         }

         abstractOutputProcessor.createEndElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig_KeyValue);
      }
   }

   public static void createX509SubjectKeyIdentifierStructure(AbstractOutputProcessor abstractOutputProcessor, OutputProcessorChain outputProcessorChain, X509Certificate[] x509Certificates) throws XMLSecurityException, XMLStreamException {
      createX509SubjectKeyIdentifierStructure(abstractOutputProcessor, outputProcessorChain, x509Certificates, true);
   }

   public static void createX509SubjectKeyIdentifierStructure(AbstractOutputProcessor abstractOutputProcessor, OutputProcessorChain outputProcessorChain, X509Certificate[] x509Certificates, boolean outputX509Data) throws XMLSecurityException, XMLStreamException {
      if (x509Certificates != null && x509Certificates.length != 0) {
         int version = x509Certificates[0].getVersion();
         if (version != 3) {
            throw new XMLSecurityException("certificate.noSki.lowVersion", new Object[]{version});
         } else {
            if (outputX509Data) {
               abstractOutputProcessor.createStartElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig_X509Data, true, (List)null);
            }

            abstractOutputProcessor.createStartElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig_X509SKI, false, (List)null);
            byte[] data = XMLX509SKI.getSKIBytesFromCert(x509Certificates[0]);
            abstractOutputProcessor.createCharactersAndOutputAsEvent(outputProcessorChain, XMLUtils.encodeToString(data));
            abstractOutputProcessor.createEndElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig_X509SKI);
            if (outputX509Data) {
               abstractOutputProcessor.createEndElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig_X509Data);
            }

         }
      } else {
         throw new XMLSecurityException("stax.signature.publicKeyOrCertificateMissing");
      }
   }

   public static void createX509CertificateStructure(AbstractOutputProcessor abstractOutputProcessor, OutputProcessorChain outputProcessorChain, X509Certificate[] x509Certificates) throws XMLSecurityException, XMLStreamException {
      createX509CertificateStructure(abstractOutputProcessor, outputProcessorChain, x509Certificates, true);
   }

   public static void createX509CertificateStructure(AbstractOutputProcessor abstractOutputProcessor, OutputProcessorChain outputProcessorChain, X509Certificate[] x509Certificates, boolean outputX509Data) throws XMLSecurityException, XMLStreamException {
      if (x509Certificates != null && x509Certificates.length != 0) {
         if (outputX509Data) {
            abstractOutputProcessor.createStartElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig_X509Data, true, (List)null);
         }

         abstractOutputProcessor.createStartElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig_X509Certificate, false, (List)null);

         byte[] data;
         try {
            data = x509Certificates[0].getEncoded();
         } catch (CertificateEncodingException var6) {
            throw new XMLSecurityException(var6);
         }

         abstractOutputProcessor.createCharactersAndOutputAsEvent(outputProcessorChain, XMLUtils.encodeToString(data));
         abstractOutputProcessor.createEndElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig_X509Certificate);
         if (outputX509Data) {
            abstractOutputProcessor.createEndElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig_X509Data);
         }

      } else {
         throw new XMLSecurityException("stax.signature.publicKeyOrCertificateMissing");
      }
   }

   public static void createX509SubjectNameStructure(AbstractOutputProcessor abstractOutputProcessor, OutputProcessorChain outputProcessorChain, X509Certificate[] x509Certificates) throws XMLSecurityException, XMLStreamException {
      createX509SubjectNameStructure(abstractOutputProcessor, outputProcessorChain, x509Certificates, true);
   }

   public static void createX509SubjectNameStructure(AbstractOutputProcessor abstractOutputProcessor, OutputProcessorChain outputProcessorChain, X509Certificate[] x509Certificates, boolean outputX509Data) throws XMLSecurityException, XMLStreamException {
      if (x509Certificates != null && x509Certificates.length != 0) {
         if (outputX509Data) {
            abstractOutputProcessor.createStartElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig_X509Data, true, (List)null);
         }

         abstractOutputProcessor.createStartElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig_X509SubjectName, false, (List)null);
         String subjectName = x509Certificates[0].getSubjectX500Principal().getName();
         abstractOutputProcessor.createCharactersAndOutputAsEvent(outputProcessorChain, subjectName);
         abstractOutputProcessor.createEndElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig_X509SubjectName);
         if (outputX509Data) {
            abstractOutputProcessor.createEndElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig_X509Data);
         }

      } else {
         throw new XMLSecurityException("stax.signature.publicKeyOrCertificateMissing");
      }
   }

   public static void createX509IssuerSerialStructure(AbstractOutputProcessor abstractOutputProcessor, OutputProcessorChain outputProcessorChain, X509Certificate[] x509Certificates) throws XMLStreamException, XMLSecurityException {
      createX509IssuerSerialStructure(abstractOutputProcessor, outputProcessorChain, x509Certificates, true);
   }

   public static void createX509IssuerSerialStructure(AbstractOutputProcessor abstractOutputProcessor, OutputProcessorChain outputProcessorChain, X509Certificate[] x509Certificates, boolean outputX509Data) throws XMLStreamException, XMLSecurityException {
      if (x509Certificates != null && x509Certificates.length != 0) {
         if (outputX509Data) {
            abstractOutputProcessor.createStartElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig_X509Data, true, (List)null);
         }

         abstractOutputProcessor.createStartElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig_X509IssuerSerial, false, (List)null);
         abstractOutputProcessor.createStartElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig_X509IssuerName, false, (List)null);
         abstractOutputProcessor.createCharactersAndOutputAsEvent(outputProcessorChain, x509Certificates[0].getIssuerX500Principal().getName());
         abstractOutputProcessor.createEndElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig_X509IssuerName);
         abstractOutputProcessor.createStartElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig_X509SerialNumber, false, (List)null);
         abstractOutputProcessor.createCharactersAndOutputAsEvent(outputProcessorChain, x509Certificates[0].getSerialNumber().toString());
         abstractOutputProcessor.createEndElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig_X509SerialNumber);
         abstractOutputProcessor.createEndElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig_X509IssuerSerial);
         if (outputX509Data) {
            abstractOutputProcessor.createEndElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig_X509Data);
         }

      } else {
         throw new XMLSecurityException("stax.signature.publicKeyOrCertificateMissing");
      }
   }

   public static TokenSecurityEvent createTokenSecurityEvent(InboundSecurityToken inboundSecurityToken, String correlationID) throws XMLSecurityException {
      SecurityTokenConstants.TokenType tokenType = inboundSecurityToken.getTokenType();
      TokenSecurityEvent tokenSecurityEvent = null;
      if (!SecurityTokenConstants.X509V1Token.equals(tokenType) && !SecurityTokenConstants.X509V3Token.equals(tokenType) && !SecurityTokenConstants.X509Pkcs7Token.equals(tokenType) && !SecurityTokenConstants.X509PkiPathV1Token.equals(tokenType)) {
         if (SecurityTokenConstants.KeyValueToken.equals(tokenType)) {
            tokenSecurityEvent = new KeyValueTokenSecurityEvent();
         } else if (SecurityTokenConstants.KeyNameToken.equals(tokenType)) {
            tokenSecurityEvent = new KeyNameTokenSecurityEvent();
         } else if (SecurityTokenConstants.DefaultToken.equals(tokenType)) {
            tokenSecurityEvent = new DefaultTokenSecurityEvent();
         } else {
            if (!SecurityTokenConstants.EncryptedKeyToken.equals(tokenType)) {
               throw new XMLSecurityException("stax.unsupportedToken", new Object[]{tokenType});
            }

            tokenSecurityEvent = new EncryptedKeyTokenSecurityEvent();
         }
      } else {
         tokenSecurityEvent = new X509TokenSecurityEvent();
      }

      ((TokenSecurityEvent)tokenSecurityEvent).setSecurityToken(inboundSecurityToken);
      ((TokenSecurityEvent)tokenSecurityEvent).setCorrelationID(correlationID);
      return (TokenSecurityEvent)tokenSecurityEvent;
   }

   public static Set getExcC14NInclusiveNamespacePrefixes(XMLSecStartElement xmlSecStartElement, boolean excludeVisible) {
      if (xmlSecStartElement == null) {
         return Collections.emptySet();
      } else {
         Set prefixes = new TreeSet();
         XMLSecStartElement parentXMXmlSecStartElement = xmlSecStartElement.getParentXMLSecStartElement();
         if (parentXMXmlSecStartElement != null) {
            List onElementDeclaredNamespaces = parentXMXmlSecStartElement.getOnElementDeclaredNamespaces();
            List xmlSecNamespaces = new ArrayList();
            parentXMXmlSecStartElement.getNamespacesFromCurrentScope(xmlSecNamespaces);
            List xmlSecNamespaces = xmlSecNamespaces.subList(0, xmlSecNamespaces.size() - onElementDeclaredNamespaces.size());

            int i;
            XMLSecNamespace xmlSecNamespace;
            String prefix;
            for(i = xmlSecNamespaces.size() - 1; i >= 0; --i) {
               xmlSecNamespace = (XMLSecNamespace)xmlSecNamespaces.get(i);
               prefix = xmlSecNamespace.getPrefix();
               if (prefix != null && !prefix.isEmpty()) {
                  prefixes.add(xmlSecNamespace.getPrefix());
               } else {
                  prefixes.add("#default");
               }
            }

            if (excludeVisible) {
               for(i = 0; i < onElementDeclaredNamespaces.size(); ++i) {
                  xmlSecNamespace = (XMLSecNamespace)onElementDeclaredNamespaces.get(i);
                  prefix = xmlSecNamespace.getPrefix();
                  if (prefix != null && !prefix.isEmpty()) {
                     prefixes.remove(prefix);
                  } else {
                     prefixes.remove("#default");
                  }
               }

               if (xmlSecStartElement.getName().getPrefix() != null && !xmlSecStartElement.getName().getPrefix().isEmpty()) {
                  prefixes.remove(xmlSecStartElement.getName().getPrefix());
               } else {
                  prefixes.remove("#default");
               }
            }
         }

         return prefixes;
      }
   }

   public static void copy(InputStream inputStream, OutputStream outputStream) throws IOException {
      int read = false;
      byte[] buf = new byte[4096];

      int read;
      while((read = inputStream.read(buf)) != -1) {
         outputStream.write(buf, 0, read);
      }

   }

   public static SecretKey prepareSecretKey(String symEncAlgo, byte[] rawKey) {
      int size = false;

      int size;
      try {
         size = JCEMapper.getKeyLengthFromURI(symEncAlgo) / 8;
      } catch (Exception var5) {
         size = 0;
      }

      String keyAlgorithm = JCEMapper.getJCEKeyAlgorithmFromURI(symEncAlgo);
      SecretKeySpec keySpec;
      if (size > 0 && !symEncAlgo.endsWith("gcm") && !symEncAlgo.contains("hmac-")) {
         keySpec = new SecretKeySpec(rawKey, 0, rawKey.length > size ? size : rawKey.length, keyAlgorithm);
      } else if (rawKey.length > 1024) {
         keySpec = new SecretKeySpec(rawKey, 0, 1024, keyAlgorithm);
      } else {
         keySpec = new SecretKeySpec(rawKey, keyAlgorithm);
      }

      return keySpec;
   }

   public static Schema loadXMLSecuritySchemas() throws SAXException {
      SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
      schemaFactory.setResourceResolver(new LSResourceResolver() {
         public LSInput resolveResource(String type, String namespaceURI, String publicId, String systemId, String baseURI) {
            ConcreteLSInput concreteLSInput;
            if ("http://www.w3.org/2001/XMLSchema.dtd".equals(systemId)) {
               concreteLSInput = new ConcreteLSInput();
               concreteLSInput.setByteStream(ClassLoaderUtils.getResourceAsStream("bindings/schemas/XMLSchema.dtd", XMLSecurityConstants.class));
               return concreteLSInput;
            } else if ("XMLSchema.dtd".equals(systemId)) {
               concreteLSInput = new ConcreteLSInput();
               concreteLSInput.setByteStream(ClassLoaderUtils.getResourceAsStream("bindings/schemas/XMLSchema.dtd", XMLSecurityConstants.class));
               return concreteLSInput;
            } else if ("datatypes.dtd".equals(systemId)) {
               concreteLSInput = new ConcreteLSInput();
               concreteLSInput.setByteStream(ClassLoaderUtils.getResourceAsStream("bindings/schemas/datatypes.dtd", XMLSecurityConstants.class));
               return concreteLSInput;
            } else if ("http://www.w3.org/TR/2002/REC-xmldsig-core-20020212/xmldsig-core-schema.xsd".equals(systemId)) {
               concreteLSInput = new ConcreteLSInput();
               concreteLSInput.setByteStream(ClassLoaderUtils.getResourceAsStream("bindings/schemas/xmldsig-core-schema.xsd", XMLSecurityConstants.class));
               return concreteLSInput;
            } else if ("http://www.w3.org/2001/xml.xsd".equals(systemId)) {
               concreteLSInput = new ConcreteLSInput();
               concreteLSInput.setByteStream(ClassLoaderUtils.getResourceAsStream("bindings/schemas/xml.xsd", XMLSecurityConstants.class));
               return concreteLSInput;
            } else {
               return null;
            }
         }
      });
      Schema schema = schemaFactory.newSchema(new Source[]{new StreamSource(ClassLoaderUtils.getResourceAsStream("bindings/schemas/exc-c14n.xsd", XMLSecurityConstants.class)), new StreamSource(ClassLoaderUtils.getResourceAsStream("bindings/schemas/xmldsig-core-schema.xsd", XMLSecurityConstants.class)), new StreamSource(ClassLoaderUtils.getResourceAsStream("bindings/schemas/xop-include.xsd", XMLSecurityConstants.class)), new StreamSource(ClassLoaderUtils.getResourceAsStream("bindings/schemas/xenc-schema.xsd", XMLSecurityConstants.class)), new StreamSource(ClassLoaderUtils.getResourceAsStream("bindings/schemas/xenc-schema-11.xsd", XMLSecurityConstants.class)), new StreamSource(ClassLoaderUtils.getResourceAsStream("bindings/schemas/xmldsig11-schema.xsd", XMLSecurityConstants.class))});
      return schema;
   }

   public static void createKeyNameTokenStructure(AbstractOutputProcessor abstractOutputProcessor, OutputProcessorChain outputProcessorChain, String keyName) throws XMLStreamException, XMLSecurityException {
      if (keyName != null && !keyName.isEmpty()) {
         abstractOutputProcessor.createStartElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig_KeyName, true, (List)null);
         abstractOutputProcessor.createCharactersAndOutputAsEvent(outputProcessorChain, keyName);
         abstractOutputProcessor.createEndElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig_KeyName);
      } else {
         throw new XMLSecurityException("stax.signature.keyNameMissing");
      }
   }
}
