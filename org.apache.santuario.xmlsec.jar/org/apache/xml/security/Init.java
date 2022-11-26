package org.apache.xml.security;

import java.io.InputStream;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import org.apache.xml.security.algorithms.JCEMapper;
import org.apache.xml.security.algorithms.SignatureAlgorithm;
import org.apache.xml.security.c14n.Canonicalizer;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.keys.keyresolver.KeyResolver;
import org.apache.xml.security.transforms.Transform;
import org.apache.xml.security.utils.ElementProxy;
import org.apache.xml.security.utils.I18n;
import org.apache.xml.security.utils.XMLUtils;
import org.apache.xml.security.utils.resolver.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Init {
   public static final String CONF_NS = "http://www.xmlsecurity.org/NS/#configuration";
   private static final Logger LOG = LoggerFactory.getLogger(Init.class);
   private static boolean alreadyInitialized = false;

   public static final synchronized boolean isInitialized() {
      return alreadyInitialized;
   }

   public static synchronized void init() {
      if (!alreadyInitialized) {
         InputStream is = (InputStream)AccessController.doPrivileged(() -> {
            String cfile = System.getProperty("org.apache.xml.security.resource.config");
            return cfile == null ? null : Init.class.getResourceAsStream(cfile);
         });
         if (is == null) {
            dynamicInit();
         } else {
            fileInit(is);
         }

         alreadyInitialized = true;
      }
   }

   private static void dynamicInit() {
      I18n.init("en", "US");
      LOG.debug("Registering default algorithms");

      try {
         ElementProxy.registerDefaultPrefixes();
      } catch (XMLSecurityException var1) {
         LOG.error(var1.getMessage(), var1);
      }

      Transform.registerDefaultAlgorithms();
      SignatureAlgorithm.registerDefaultAlgorithms();
      JCEMapper.registerDefaultAlgorithms();
      Canonicalizer.registerDefaultAlgorithms();
      ResourceResolver.registerDefaultResolvers();
      KeyResolver.registerDefaultResolvers();
   }

   private static void fileInit(InputStream is) {
      try {
         DocumentBuilder db = XMLUtils.createDocumentBuilder(false);

         Document doc;
         try {
            doc = db.parse(is);
         } finally {
            XMLUtils.repoolDocumentBuilder(db);
            db = null;
         }

         Node config;
         for(config = doc.getFirstChild(); config != null && !"Configuration".equals(config.getLocalName()); config = config.getNextSibling()) {
         }

         if (config == null) {
            LOG.error("Error in reading configuration file - Configuration element not found");
            return;
         }

         for(Node el = config.getFirstChild(); el != null; el = el.getNextSibling()) {
            if (1 == el.getNodeType()) {
               String tag = el.getLocalName();
               if ("ResourceBundles".equals(tag)) {
                  Element resource = (Element)el;
                  Attr langAttr = resource.getAttributeNodeNS((String)null, "defaultLanguageCode");
                  Attr countryAttr = resource.getAttributeNodeNS((String)null, "defaultCountryCode");
                  String languageCode = langAttr == null ? null : langAttr.getNodeValue();
                  String countryCode = countryAttr == null ? null : countryAttr.getNodeValue();
                  I18n.init(languageCode, countryCode);
               }

               String namespace;
               String javaClass;
               Object[] exArgs;
               Element[] nl;
               Element[] algorithms;
               int var32;
               int var35;
               Element element;
               if ("CanonicalizationMethods".equals(tag)) {
                  nl = XMLUtils.selectNodes(el.getFirstChild(), "http://www.xmlsecurity.org/NS/#configuration", "CanonicalizationMethod");
                  algorithms = nl;
                  var32 = nl.length;

                  for(var35 = 0; var35 < var32; ++var35) {
                     element = algorithms[var35];
                     namespace = element.getAttributeNS((String)null, "URI");
                     javaClass = element.getAttributeNS((String)null, "JAVACLASS");

                     try {
                        Canonicalizer.register(namespace, javaClass);
                        LOG.debug("Canonicalizer.register({}, {})", namespace, javaClass);
                     } catch (ClassNotFoundException var26) {
                        exArgs = new Object[]{namespace, javaClass};
                        LOG.error(I18n.translate("algorithm.classDoesNotExist", exArgs));
                     }
                  }
               }

               if ("TransformAlgorithms".equals(tag)) {
                  nl = XMLUtils.selectNodes(el.getFirstChild(), "http://www.xmlsecurity.org/NS/#configuration", "TransformAlgorithm");
                  algorithms = nl;
                  var32 = nl.length;

                  for(var35 = 0; var35 < var32; ++var35) {
                     element = algorithms[var35];
                     namespace = element.getAttributeNS((String)null, "URI");
                     javaClass = element.getAttributeNS((String)null, "JAVACLASS");

                     try {
                        Transform.register(namespace, javaClass);
                        LOG.debug("Transform.register({}, {})", namespace, javaClass);
                     } catch (ClassNotFoundException var24) {
                        exArgs = new Object[]{namespace, javaClass};
                        LOG.error(I18n.translate("algorithm.classDoesNotExist", exArgs));
                     } catch (NoClassDefFoundError var25) {
                        LOG.warn("Not able to found dependencies for algorithm, I'll keep working.");
                     }
                  }
               }

               Element[] var33;
               int var37;
               Element element;
               if ("JCEAlgorithmMappings".equals(tag)) {
                  Node algorithmsNode = ((Element)el).getElementsByTagName("Algorithms").item(0);
                  if (algorithmsNode != null) {
                     algorithms = XMLUtils.selectNodes(algorithmsNode.getFirstChild(), "http://www.xmlsecurity.org/NS/#configuration", "Algorithm");
                     var33 = algorithms;
                     var35 = algorithms.length;

                     for(var37 = 0; var37 < var35; ++var37) {
                        element = var33[var37];
                        javaClass = element.getAttributeNS((String)null, "URI");
                        JCEMapper.register(javaClass, new JCEMapper.Algorithm(element));
                     }
                  }
               }

               if ("SignatureAlgorithms".equals(tag)) {
                  nl = XMLUtils.selectNodes(el.getFirstChild(), "http://www.xmlsecurity.org/NS/#configuration", "SignatureAlgorithm");
                  algorithms = nl;
                  var32 = nl.length;

                  for(var35 = 0; var35 < var32; ++var35) {
                     element = algorithms[var35];
                     namespace = element.getAttributeNS((String)null, "URI");
                     javaClass = element.getAttributeNS((String)null, "JAVACLASS");

                     try {
                        SignatureAlgorithm.register(namespace, javaClass);
                        LOG.debug("SignatureAlgorithm.register({}, {})", namespace, javaClass);
                     } catch (ClassNotFoundException var23) {
                        exArgs = new Object[]{namespace, javaClass};
                        LOG.error(I18n.translate("algorithm.classDoesNotExist", exArgs));
                     }
                  }
               }

               if ("ResourceResolvers".equals(tag)) {
                  nl = XMLUtils.selectNodes(el.getFirstChild(), "http://www.xmlsecurity.org/NS/#configuration", "Resolver");
                  algorithms = nl;
                  var32 = nl.length;

                  for(var35 = 0; var35 < var32; ++var35) {
                     element = algorithms[var35];
                     namespace = element.getAttributeNS((String)null, "JAVACLASS");
                     javaClass = element.getAttributeNS((String)null, "DESCRIPTION");
                     if (javaClass != null && javaClass.length() > 0) {
                        LOG.debug("Register Resolver: {}: {}", namespace, javaClass);
                     } else {
                        LOG.debug("Register Resolver: {}: For unknown purposes", namespace);
                     }

                     try {
                        ResourceResolver.register(namespace);
                     } catch (Throwable var22) {
                        LOG.warn("Cannot register:" + namespace + " perhaps some needed jars are not installed", var22);
                     }
                  }
               }

               if ("KeyResolver".equals(tag)) {
                  nl = XMLUtils.selectNodes(el.getFirstChild(), "http://www.xmlsecurity.org/NS/#configuration", "Resolver");
                  List classNames = new ArrayList(nl.length);
                  var33 = nl;
                  var35 = nl.length;

                  for(var37 = 0; var37 < var35; ++var37) {
                     element = var33[var37];
                     javaClass = element.getAttributeNS((String)null, "JAVACLASS");
                     String description = element.getAttributeNS((String)null, "DESCRIPTION");
                     if (description != null && description.length() > 0) {
                        LOG.debug("Register Resolver: {}: {}", javaClass, description);
                     } else {
                        LOG.debug("Register Resolver: {}: For unknown purposes", javaClass);
                     }

                     classNames.add(javaClass);
                  }

                  KeyResolver.registerClassNames(classNames);
               }

               if ("PrefixMappings".equals(tag)) {
                  LOG.debug("Now I try to bind prefixes:");
                  nl = XMLUtils.selectNodes(el.getFirstChild(), "http://www.xmlsecurity.org/NS/#configuration", "PrefixMapping");
                  algorithms = nl;
                  var32 = nl.length;

                  for(var35 = 0; var35 < var32; ++var35) {
                     element = algorithms[var35];
                     namespace = element.getAttributeNS((String)null, "namespace");
                     javaClass = element.getAttributeNS((String)null, "prefix");
                     LOG.debug("Now I try to bind {} to {}", javaClass, namespace);
                     ElementProxy.setDefaultPrefix(namespace, javaClass);
                  }
               }
            }
         }
      } catch (Exception var28) {
         LOG.error("Bad: ", var28);
      }

   }
}
