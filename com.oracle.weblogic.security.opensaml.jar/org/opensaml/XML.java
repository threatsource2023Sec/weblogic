package org.opensaml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;
import java.util.Vector;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XML {
   public static final String XML_NS = "http://www.w3.org/XML/1998/namespace";
   public static final String XMLNS_NS = "http://www.w3.org/2000/xmlns/";
   public static final String XSI_NS = "http://www.w3.org/2001/XMLSchema-instance";
   public static final String XSD_NS = "http://www.w3.org/2001/XMLSchema";
   public static final String OPENSAML_NS = "http://www.opensaml.org";
   public static final String SAML_NS = "urn:oasis:names:tc:SAML:1.0:assertion";
   public static final String SAMLP_NS = "urn:oasis:names:tc:SAML:1.0:protocol";
   public static final String XMLSIG_NS = "http://www.w3.org/2000/09/xmldsig#";
   public static final String XPATH2_NS = "http://www.w3.org/2002/06/xmldsig-filter2";
   public static final String SOAP11ENV_NS = "http://schemas.xmlsoap.org/soap/envelope/";
   public static final String EXCC14N_NS = "http://www.w3.org/2001/10/xml-exc-c14n#";
   public static final String XML_SCHEMA_ID = "xml.xsd";
   public static final String SAML_SCHEMA_ID = "cs-sstc-schema-assertion-01.xsd";
   public static final String SAMLP_SCHEMA_ID = "cs-sstc-schema-protocol-01.xsd";
   public static final String SAML11_SCHEMA_ID = "cs-sstc-schema-assertion-1.1.xsd";
   public static final String SAMLP11_SCHEMA_ID = "cs-sstc-schema-protocol-1.1.xsd";
   public static final String XMLSIG_SCHEMA_ID = "xmldsig-core-schema.xsd";
   public static final String XPATH2_SCHEMA_ID = "xmldsig-filter2.xsd";
   public static final String SOAP11ENV_SCHEMA_ID = "soap-envelope.xsd";
   public static final String EXCC14N_SCHEMA_ID = "exc-c14n.xsd";
   protected static byte[] XML_schema;
   protected static byte[] SAML_schema;
   protected static byte[] SAMLP_schema;
   protected static byte[] SAML11_schema;
   protected static byte[] SAMLP11_schema;
   protected static byte[] XMLSig_schema;
   protected static byte[] XPath2_schema;
   protected static byte[] SOAP11Env_schema;
   protected static byte[] EXCc14n_schema;
   public static ParserPool parserPool = new ParserPool();
   private static final DebugLogger LOGGER = SAMLServicesHelper.getDebugLogger();

   public static void initialize(int var0) {
      logDebug("Initializing parser pool to contain a minimum of " + String.valueOf(var0) + " DOM parsers");
      parserPool.initializePool(var0);
      logDebug("Parser pool initialized.");
   }

   private static final void logDebug(String var0) {
      if (LOGGER != null && LOGGER.isDebugEnabled()) {
         LOGGER.debug(var0);
      }

   }

   public static boolean isEmpty(String var0) {
      return var0 == null || var0.length() == 0;
   }

   public static boolean safeCompare(String var0, String var1) {
      if (var0 != null && var1 != null) {
         return var0.equals(var1);
      } else {
         return var0 == var1;
      }
   }

   public static boolean isElementNamed(Element var0, String var1, String var2) {
      return var0 != null && safeCompare(var1, var0.getNamespaceURI()) && safeCompare(var2, var0.getLocalName());
   }

   public static Element getFirstChildElement(Node var0) {
      Node var1;
      for(var1 = var0.getFirstChild(); var1 != null && var1.getNodeType() != 1; var1 = var1.getNextSibling()) {
      }

      return var1 != null ? (Element)var1 : null;
   }

   public static Element getLastChildElement(Node var0) {
      Node var1;
      for(var1 = var0.getLastChild(); var1 != null && var1.getNodeType() != 1; var1 = var1.getPreviousSibling()) {
      }

      return var1 != null ? (Element)var1 : null;
   }

   public static Element getFirstChildElement(Node var0, String var1, String var2) {
      Element var3;
      for(var3 = getFirstChildElement(var0); var3 != null && !isElementNamed(var3, var1, var2); var3 = getNextSiblingElement(var3)) {
      }

      return var3;
   }

   public static Element getLastChildElement(Node var0, String var1, String var2) {
      Element var3;
      for(var3 = getLastChildElement(var0); var3 != null && !isElementNamed(var3, var1, var2); var3 = getPreviousSiblingElement(var3)) {
      }

      return var3;
   }

   public static Element getNextSiblingElement(Node var0) {
      Node var1;
      for(var1 = var0.getNextSibling(); var1 != null && var1.getNodeType() != 1; var1 = var1.getNextSibling()) {
      }

      return var1 != null ? (Element)var1 : null;
   }

   public static Element getPreviousSiblingElement(Node var0) {
      Node var1;
      for(var1 = var0.getPreviousSibling(); var1 != null && var1.getNodeType() != 1; var1 = var1.getPreviousSibling()) {
      }

      return var1 != null ? (Element)var1 : null;
   }

   public static Element getNextSiblingElement(Node var0, String var1, String var2) {
      Element var3;
      for(var3 = getNextSiblingElement(var0); var3 != null && !isElementNamed(var3, var1, var2); var3 = getNextSiblingElement(var3)) {
      }

      return var3;
   }

   public static Element getPreviousSiblingElement(Node var0, String var1, String var2) {
      Element var3;
      for(var3 = getPreviousSiblingElement(var0); var3 != null && !isElementNamed(var3, var1, var2); var3 = getPreviousSiblingElement(var3)) {
      }

      return var3;
   }

   public static void outputNode(Node var0, OutputStream var1) throws IOException {
      try {
         TransformerFactory var2 = SAMLServicesHelper.getTransformerFactory();
         if (var2 == null) {
            var2 = TransformerFactory.newInstance();
            SAMLServicesHelper.setTransformerFactory(var2);
         }

         Transformer var3 = var2.newTransformer();
         DOMSource var4 = new DOMSource(var0);
         StreamResult var5 = new StreamResult(var1);
         var3.transform(var4, var5);
      } catch (TransformerException var6) {
         throw new IOException(var6.getMessage());
      }
   }

   static {
      try {
         StringBuffer var1 = new StringBuffer(1024);
         InputStream var2 = XML.class.getResourceAsStream("/opensaml/schemas/cs-sstc-schema-assertion-01.xsd");
         if (var2 == null) {
            throw new RuntimeException("XML static initializer unable to locate SAML assertion schema");
         } else {
            int var0;
            while((var0 = var2.read()) != -1) {
               var1.append((char)var0);
            }

            SAML_schema = var1.toString().getBytes();
            var2.close();
            var2 = XML.class.getResourceAsStream("/opensaml/schemas/cs-sstc-schema-protocol-01.xsd");
            if (var2 == null) {
               throw new RuntimeException("XML static initializer unable to locate SAML protocol schema");
            } else {
               var1.setLength(0);

               while((var0 = var2.read()) != -1) {
                  var1.append((char)var0);
               }

               SAMLP_schema = var1.toString().getBytes();
               var2.close();
               var2 = XML.class.getResourceAsStream("/opensaml/schemas/cs-sstc-schema-assertion-1.1.xsd");
               if (var2 == null) {
                  throw new RuntimeException("XML static initializer unable to locate SAML 1.1 assertion schema");
               } else {
                  var1.setLength(0);

                  while((var0 = var2.read()) != -1) {
                     var1.append((char)var0);
                  }

                  SAML11_schema = var1.toString().getBytes();
                  var2.close();
                  var2 = XML.class.getResourceAsStream("/opensaml/schemas/cs-sstc-schema-protocol-1.1.xsd");
                  if (var2 == null) {
                     throw new RuntimeException("XML static initializer unable to locate SAML 1.1 protocol schema");
                  } else {
                     var1.setLength(0);

                     while((var0 = var2.read()) != -1) {
                        var1.append((char)var0);
                     }

                     SAMLP11_schema = var1.toString().getBytes();
                     var2.close();
                     var2 = XML.class.getResourceAsStream("/opensaml/schemas/xmldsig-core-schema.xsd");
                     if (var2 == null) {
                        throw new RuntimeException("XML static initializer unable to locate XML Signature schema");
                     } else {
                        var1.setLength(0);

                        while((var0 = var2.read()) != -1) {
                           var1.append((char)var0);
                        }

                        XMLSig_schema = var1.toString().getBytes();
                        var2.close();
                        var2 = XML.class.getResourceAsStream("/opensaml/schemas/xmldsig-filter2.xsd");
                        if (var2 == null) {
                           throw new RuntimeException("XML static initializer unable to locate XPath Filter2 schema");
                        } else {
                           var1.setLength(0);

                           while((var0 = var2.read()) != -1) {
                              var1.append((char)var0);
                           }

                           XPath2_schema = var1.toString().getBytes();
                           var2.close();
                           var2 = XML.class.getResourceAsStream("/opensaml/schemas/soap-envelope.xsd");
                           if (var2 == null) {
                              throw new RuntimeException("XML static initializer unable to locate SOAP 1.1 Envelope schema");
                           } else {
                              var1.setLength(0);

                              while((var0 = var2.read()) != -1) {
                                 var1.append((char)var0);
                              }

                              SOAP11Env_schema = var1.toString().getBytes();
                              var2.close();
                              var2 = XML.class.getResourceAsStream("/opensaml/schemas/xml.xsd");
                              if (var2 == null) {
                                 throw new RuntimeException("XML static initializer unable to locate XML core schema");
                              } else {
                                 var1.setLength(0);

                                 while((var0 = var2.read()) != -1) {
                                    var1.append((char)var0);
                                 }

                                 XML_schema = var1.toString().getBytes();
                                 var2.close();
                                 var2 = XML.class.getResourceAsStream("/opensaml/schemas/exc-c14n.xsd");
                                 if (var2 == null) {
                                    throw new RuntimeException("XML static initializer unable to locate Exclusive Canonicalization schema");
                                 } else {
                                    var1.setLength(0);

                                    while((var0 = var2.read()) != -1) {
                                       var1.append((char)var0);
                                    }

                                    EXCc14n_schema = var1.toString().getBytes();
                                    var2.close();
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      } catch (IOException var3) {
         throw new RuntimeException("XML static initializer caught an I/O error");
      }
   }

   public static class ParserPool implements EntityResolver, ErrorHandler {
      private Stack pool = null;
      private Vector resolvers = null;
      private String schemaLocations = null;
      private Hashtable schemaLocMap = null;
      private DocumentBuilderFactory dbFac = null;

      public ParserPool() {
         this.pool = new Stack();
         this.resolvers = new Vector();
         this.schemaLocMap = new Hashtable();
         this.registerSchema("urn:oasis:names:tc:SAML:1.0:assertion", SAMLConfig.instance().getBooleanProperty("org.opensaml.compatibility-mode") ? "cs-sstc-schema-assertion-01.xsd" : "cs-sstc-schema-assertion-1.1.xsd", (EntityResolver)null);
         this.registerSchema("urn:oasis:names:tc:SAML:1.0:protocol", SAMLConfig.instance().getBooleanProperty("org.opensaml.compatibility-mode") ? "cs-sstc-schema-protocol-01.xsd" : "cs-sstc-schema-protocol-1.1.xsd", (EntityResolver)null);
         this.registerSchema("http://schemas.xmlsoap.org/soap/envelope/", "soap-envelope.xsd", (EntityResolver)null);
         this.registerSchema("http://www.w3.org/2000/09/xmldsig#", "xmldsig-core-schema.xsd", (EntityResolver)null);
         this.registerSchema("http://www.w3.org/XML/1998/namespace", "xml.xsd", (EntityResolver)null);
         this.registerSchema("http://www.w3.org/2002/06/xmldsig-filter2", "xmldsig-filter2.xsd", (EntityResolver)null);
         this.registerSchema("http://www.w3.org/2001/10/xml-exc-c14n#", "exc-c14n.xsd", (EntityResolver)null);
         System.setProperty("org.apache.xerces.xni.parser.Configuration", "com.sun.org.apache.xerces.internal.parsers.XMLGrammarCachingConfiguration");
         this.dbFac = SAMLServicesHelper.getDocumentBuilderFactory();
         if (this.dbFac == null) {
            this.dbFac = DocumentBuilderFactory.newInstance();
         }

         this.dbFac.setNamespaceAware(true);
         this.dbFac.setValidating(true);
         this.dbFac.setAttribute("http://xml.org/sax/features/validation", new Boolean(true));
         this.dbFac.setAttribute("http://apache.org/xml/features/validation/schema", new Boolean(true));
         this.dbFac.setAttribute("http://apache.org/xml/features/validation/schema/normalized-value", new Boolean(false));
         this.dbFac.setAttribute("http://apache.org/xml/features/dom/defer-node-expansion", new Boolean(false));
         this.dbFac.setAttribute("http://apache.org/xml/properties/schema/external-schemaLocation", this.schemaLocations);
      }

      public synchronized DocumentBuilder get() throws SAMLException {
         try {
            DocumentBuilder var1 = null;
            if (this.pool.empty()) {
               var1 = this.createNewParser();
            } else {
               var1 = (DocumentBuilder)this.pool.pop();
            }

            return var1;
         } catch (ParserConfigurationException var2) {
            throw new SAMLException("XML.ParserPool.get() unable to configure parser properly", var2);
         }
      }

      private DocumentBuilder createNewParser() throws ParserConfigurationException {
         DocumentBuilder var1 = this.dbFac.newDocumentBuilder();
         var1.setEntityResolver(this);
         var1.setErrorHandler(this);
         return var1;
      }

      public Document parse(InputStream var1) throws SAMLException, SAXException, IOException {
         DocumentBuilder var2 = this.get();

         Document var3;
         try {
            var3 = var2.parse(new InputSource(var1));
         } finally {
            this.put(var2);
         }

         return var3;
      }

      public Document parse(String var1) throws SAMLException, SAXException, IOException {
         DocumentBuilder var2 = this.get();

         Document var3;
         try {
            var3 = var2.parse(new InputSource(var1));
         } finally {
            this.put(var2);
         }

         return var3;
      }

      public void validate(Node var1) throws SAMLException {
         try {
            TransformerFactory var2 = SAMLServicesHelper.getTransformerFactory();
            if (var2 == null) {
               var2 = TransformerFactory.newInstance();
               SAMLServicesHelper.setTransformerFactory(var2);
            }

            Transformer var3 = var2.newTransformer();
            ByteArrayOutputStream var4 = new ByteArrayOutputStream();
            var3.transform(new DOMSource(var1), new StreamResult(var4));
            this.parse((InputStream)(new ByteArrayInputStream(var4.toByteArray())));
         } catch (Exception var5) {
            throw new SAMLException("XML.ParserPool.validate() document validation fails", var5);
         }
      }

      public Document newDocument() {
         DocumentBuilder var1 = null;

         Object var3;
         try {
            var1 = this.get();
            Document var2 = var1.newDocument();
            return var2;
         } catch (SAMLException var7) {
            XML.logDebug("newDocument() exception: " + var7.toString());
            var3 = null;
         } finally {
            if (var1 != null) {
               this.put(var1);
            }

         }

         return (Document)var3;
      }

      public synchronized void registerSchema(String var1, String var2, EntityResolver var3) {
         if (var1 != null && var2 != null) {
            if (this.schemaLocMap.containsKey(var1)) {
               this.schemaLocMap.remove(var1);
            }

            this.schemaLocMap.put(var1, var2);
            if (var3 != null) {
               this.resolvers.add(var3);
            }

            this.schemaLocations = null;
            Iterator var4 = this.schemaLocMap.entrySet().iterator();

            while(var4.hasNext()) {
               Map.Entry var5 = (Map.Entry)var4.next();
               if (this.schemaLocations == null) {
                  this.schemaLocations = var5.getKey() + " " + var5.getValue();
               } else {
                  this.schemaLocations = this.schemaLocations + " " + var5.getKey() + " " + var5.getValue();
               }
            }
         }

      }

      public synchronized void put(DocumentBuilder var1) {
         this.pool.push(var1);
      }

      public InputSource resolveEntity(String var1, String var2) throws SAXException, IOException {
         XML.logDebug("ParserPool resolving entity: publicId = " + var1 + " : systemId = " + var2);
         InputSource var3 = null;
         if (var2.endsWith("cs-sstc-schema-assertion-01.xsd") && XML.SAML_schema != null) {
            var3 = new InputSource(new ByteArrayInputStream(XML.SAML_schema));
         } else if (var2.endsWith("cs-sstc-schema-protocol-01.xsd") && XML.SAMLP_schema != null) {
            var3 = new InputSource(new ByteArrayInputStream(XML.SAMLP_schema));
         } else if (var2.endsWith("cs-sstc-schema-assertion-1.1.xsd") && XML.SAML11_schema != null) {
            var3 = new InputSource(new ByteArrayInputStream(XML.SAML11_schema));
         } else if (var2.endsWith("cs-sstc-schema-protocol-1.1.xsd") && XML.SAMLP11_schema != null) {
            var3 = new InputSource(new ByteArrayInputStream(XML.SAMLP11_schema));
         } else if (var2.endsWith("xmldsig-core-schema.xsd") && XML.XMLSig_schema != null) {
            var3 = new InputSource(new ByteArrayInputStream(XML.XMLSig_schema));
         } else if (var2.endsWith("soap-envelope.xsd") && XML.SOAP11Env_schema != null) {
            var3 = new InputSource(new ByteArrayInputStream(XML.SOAP11Env_schema));
         } else if (var2.endsWith("xml.xsd") && XML.XML_schema != null) {
            var3 = new InputSource(new ByteArrayInputStream(XML.XML_schema));
         } else if (var2.endsWith("xmldsig-filter2.xsd") && XML.XPath2_schema != null) {
            var3 = new InputSource(new ByteArrayInputStream(XML.XPath2_schema));
         } else if (var2.endsWith("exc-c14n.xsd") && XML.EXCc14n_schema != null) {
            var3 = new InputSource(new ByteArrayInputStream(XML.EXCc14n_schema));
         } else {
            EntityResolver var5;
            if (this.resolvers != null) {
               for(Iterator var4 = this.resolvers.iterator(); var3 == null && var4.hasNext(); var3 = var5.resolveEntity(var1, var2)) {
                  var5 = (EntityResolver)((EntityResolver)var4.next());
               }
            }
         }

         if (var3 != null) {
            XML.logDebug("entity resolved by ParserPool");
         }

         return var3;
      }

      public void fatalError(SAXParseException var1) throws SAXException {
      }

      public void error(SAXParseException var1) throws SAXParseException {
         throw var1;
      }

      public void warning(SAXParseException var1) throws SAXParseException {
         XML.logDebug("Parser warning: line = " + var1.getLineNumber() + " : uri = " + var1.getSystemId());
         XML.logDebug("Parser warning (root cause): " + var1.getMessage());
      }

      public synchronized void initializePool(int var1) {
         if (this.pool.size() < var1) {
            int var2 = var1 - this.pool.size();

            for(int var3 = 0; var3 < var2; ++var3) {
               DocumentBuilder var4 = null;

               try {
                  var4 = this.createNewParser();
                  this.pool.push(var4);
                  XML.logDebug("Added new parser to pool. Total of " + String.valueOf(this.pool.size()));
               } catch (ParserConfigurationException var6) {
                  throw new RuntimeException("XML pool initializer unable to add DOM parser to XML parser pool.", var6);
               }
            }
         }

      }
   }
}
