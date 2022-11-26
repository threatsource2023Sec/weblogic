package com.bea.core.repackaged.springframework.beans.factory.xml;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.lang.Nullable;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;

public class DefaultDocumentLoader implements DocumentLoader {
   private static final String SCHEMA_LANGUAGE_ATTRIBUTE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
   private static final String XSD_SCHEMA_LANGUAGE = "http://www.w3.org/2001/XMLSchema";
   private static final Log logger = LogFactory.getLog(DefaultDocumentLoader.class);

   public Document loadDocument(InputSource inputSource, EntityResolver entityResolver, ErrorHandler errorHandler, int validationMode, boolean namespaceAware) throws Exception {
      DocumentBuilderFactory factory = this.createDocumentBuilderFactory(validationMode, namespaceAware);
      if (logger.isTraceEnabled()) {
         logger.trace("Using JAXP provider [" + factory.getClass().getName() + "]");
      }

      DocumentBuilder builder = this.createDocumentBuilder(factory, entityResolver, errorHandler);
      return builder.parse(inputSource);
   }

   protected DocumentBuilderFactory createDocumentBuilderFactory(int validationMode, boolean namespaceAware) throws ParserConfigurationException {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setNamespaceAware(namespaceAware);
      if (validationMode != 0) {
         factory.setValidating(true);
         if (validationMode == 3) {
            factory.setNamespaceAware(true);

            try {
               factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema");
            } catch (IllegalArgumentException var6) {
               ParserConfigurationException pcex = new ParserConfigurationException("Unable to validate using XSD: Your JAXP provider [" + factory + "] does not support XML Schema. Are you running on Java 1.4 with Apache Crimson? Upgrade to Apache Xerces (or Java 1.5) for full XSD support.");
               pcex.initCause(var6);
               throw pcex;
            }
         }
      }

      return factory;
   }

   protected DocumentBuilder createDocumentBuilder(DocumentBuilderFactory factory, @Nullable EntityResolver entityResolver, @Nullable ErrorHandler errorHandler) throws ParserConfigurationException {
      DocumentBuilder docBuilder = factory.newDocumentBuilder();
      if (entityResolver != null) {
         docBuilder.setEntityResolver(entityResolver);
      }

      if (errorHandler != null) {
         docBuilder.setErrorHandler(errorHandler);
      }

      return docBuilder;
   }
}
