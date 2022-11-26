package org.apache.openjpa.lib.xml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.apache.commons.lang.exception.NestableRuntimeException;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

public class XMLFactory {
   private static SAXParserFactory[] _saxFactories = null;
   private static DocumentBuilderFactory[] _domFactories = null;
   private static ErrorHandler _validating;

   public static SAXParser getSAXParser(boolean validating, boolean namespaceAware) {
      SAXParser sp;
      try {
         sp = _saxFactories[factoryIndex(validating, namespaceAware)].newSAXParser();
      } catch (ParserConfigurationException var5) {
         throw new NestableRuntimeException(var5);
      } catch (SAXException var6) {
         throw new NestableRuntimeException(var6);
      }

      if (validating) {
         try {
            sp.getXMLReader().setErrorHandler(_validating);
         } catch (SAXException var4) {
            throw new NestableRuntimeException(var4);
         }
      }

      return sp;
   }

   public static DocumentBuilder getDOMParser(boolean validating, boolean namespaceAware) {
      DocumentBuilder db;
      try {
         db = _domFactories[factoryIndex(validating, namespaceAware)].newDocumentBuilder();
      } catch (ParserConfigurationException var4) {
         throw new NestableRuntimeException(var4);
      }

      if (validating) {
         db.setErrorHandler(_validating);
      }

      return db;
   }

   public static Document getDocument() {
      return getDOMParser(false, false).newDocument();
   }

   private static int factoryIndex(boolean validating, boolean namespaceAware) {
      int arrayIndex = 0;
      if (validating) {
         arrayIndex += 2;
      }

      if (namespaceAware) {
         ++arrayIndex;
      }

      return arrayIndex;
   }

   static {
      _saxFactories = new SAXParserFactory[4];
      _domFactories = new DocumentBuilderFactory[4];

      for(int validating = 0; validating < 2; ++validating) {
         for(int namespace = 0; namespace < 2; ++namespace) {
            int arrIdx = factoryIndex(validating == 1, namespace == 1);
            SAXParserFactory saxFactory = SAXParserFactory.newInstance();
            saxFactory.setValidating(validating == 1);
            saxFactory.setNamespaceAware(namespace == 1);
            _saxFactories[arrIdx] = saxFactory;
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setValidating(validating == 1);
            domFactory.setNamespaceAware(namespace == 1);
            _domFactories[arrIdx] = domFactory;
         }
      }

      _validating = new ValidatingErrorHandler();
   }
}
