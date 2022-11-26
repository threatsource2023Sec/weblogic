package com.oracle.weblogic.lifecycle.provisioning.core;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.dom.DOMSource;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import weblogic.utils.XXEUtils;

public abstract class AbstractConfigurationURIResolver implements URIResolver {
   private final URIResolver delegate;
   private final Map sources;
   private final String scheme;
   private final boolean cache;
   private DocumentBuilder db;

   protected AbstractConfigurationURIResolver() {
      this((URIResolver)null, "config", true);
   }

   protected AbstractConfigurationURIResolver(URIResolver delegate) {
      this(delegate, "config", true);
   }

   protected AbstractConfigurationURIResolver(URIResolver delegate, String scheme) {
      this(delegate, scheme, true);
   }

   protected AbstractConfigurationURIResolver(URIResolver delegate, String scheme, boolean cache) {
      this.sources = new HashMap();
      this.delegate = delegate;
      if (scheme == null) {
         this.scheme = "config";
      } else {
         this.scheme = scheme;
      }

      this.cache = cache;
   }

   public final void flushCache() {
      this.sources.clear();
   }

   public final String getScheme() {
      return this.scheme;
   }

   public final Source resolve(String href, String base) throws TransformerException {
      String className = AbstractConfigurationURIResolver.class.getName();
      String methodName = "resolve";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "resolve", new Object[]{href, base});
      }

      Source returnValue = null;
      if (href != null && (base == null || base.isEmpty())) {
         String scheme = this.getScheme();
         if (scheme == null) {
            scheme = "config:";
         } else {
            scheme = scheme.trim();

            assert scheme != null;

            if (scheme.isEmpty()) {
               scheme = "config:";
            } else if (!scheme.endsWith(":")) {
               scheme = scheme + ":";
            }
         }

         if (href.length() > scheme.length() && href.startsWith(scheme)) {
            String name = href.substring(scheme.length());

            assert name != null;

            DOMSource source = null;
            if (this.cache) {
               source = (DOMSource)this.sources.get(name);
            }

            if (source == null) {
               if (logger != null && logger.isLoggable(Level.FINE)) {
                  logger.logp(Level.FINE, className, "resolve", "Getting property value for {0}", name);
               }

               String value = null;

               try {
                  value = this.getProperty(name, this.getDefaultPropertyValue(name));
               } catch (IllegalArgumentException var13) {
                  throw new TransformerException(var13);
               }

               if (logger != null && logger.isLoggable(Level.FINE)) {
                  logger.logp(Level.FINE, className, "resolve", "Got property value \"{1}\" for {0}", new Object[]{name, value});
               }

               try {
                  source = this.createSource(name, value);
               } catch (ParserConfigurationException var12) {
                  throw new TransformerException(var12);
               }

               if (source != null && this.cache) {
                  this.sources.put(name, source);
               }
            }

            returnValue = source;
         }
      }

      if (returnValue == null && this.delegate != null) {
         returnValue = this.delegate.resolve(href, base);
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "resolve", returnValue);
      }

      return (Source)returnValue;
   }

   protected abstract String getProperty(String var1, String var2);

   protected String getDefaultPropertyValue(String name) {
      return null;
   }

   protected DOMSource createSource(String name, String value) throws ParserConfigurationException {
      String className = AbstractConfigurationURIResolver.class.getName();
      String methodName = "createSource";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "createSource", new Object[]{name, value});
      }

      DOMSource returnValue;
      if (name != null && value != null) {
         Document document = this.newDocument();
         if (document == null) {
            throw new IllegalStateException("newDocument() == null");
         }

         Element configurableAttribute = document.createElement("configurableAttribute");

         assert configurableAttribute != null;

         document.appendChild(configurableAttribute);
         Attr configurableAttributeName = document.createAttribute("name");

         assert configurableAttributeName != null;

         configurableAttributeName.setValue(name);
         configurableAttribute.setAttributeNode(configurableAttributeName);
         Text configurableAttributeValue = document.createTextNode(value);
         configurableAttribute.appendChild(configurableAttributeValue);
         returnValue = new DOMSource(document);
      } else {
         returnValue = null;
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "createSource", returnValue);
      }

      return returnValue;
   }

   protected Document newDocument() throws ParserConfigurationException {
      DocumentBuilder db = this.db;
      if (db == null) {
         db = XXEUtils.createDocumentBuilderFactoryInstance().newDocumentBuilder();
         this.db = db;
      }

      assert db != null;

      return db.newDocument();
   }
}
