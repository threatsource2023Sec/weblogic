package org.apache.xml.security.stax.config;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.xml.namespace.NamespaceContext;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathFactoryConfigurationException;
import org.apache.xml.security.utils.ClassLoaderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

public class XIncludeHandler extends DefaultHandler {
   private static final transient Logger LOG = LoggerFactory.getLogger(XIncludeHandler.class);
   private static final String xIncludeNS = "http://www.w3.org/2001/XInclude";
   private static final String xIncludeLN = "include";
   private final ContentHandler contentHandler;
   private URL systemId;
   private boolean skipEvents = false;
   Map uriDocMap = new HashMap();

   public XIncludeHandler(ContentHandler contentHandler) {
      this.contentHandler = contentHandler;
   }

   private XIncludeHandler(ContentHandler contentHandler, Map uriDocMap) {
      this.contentHandler = contentHandler;
      this.uriDocMap = uriDocMap;
   }

   public void setDocumentLocator(Locator locator) {
      if (locator.getSystemId() == null && this.systemId == null) {
         throw new UnsupportedOperationException("Please specify a correct systemId to the sax.parse() method!");
      } else {
         try {
            if (locator.getSystemId() != null) {
               this.systemId = new URL(locator.getSystemId());
            }
         } catch (MalformedURLException var3) {
            throw new IllegalArgumentException(var3);
         }

         this.contentHandler.setDocumentLocator(locator);
      }
   }

   public void startDocument() throws SAXException {
      if (!this.skipEvents) {
         this.contentHandler.startDocument();
      }

   }

   public void endDocument() throws SAXException {
      if (!this.skipEvents) {
         this.contentHandler.endDocument();
      }

   }

   public void startPrefixMapping(String prefix, String uri) throws SAXException {
      if (!this.skipEvents) {
         this.contentHandler.startPrefixMapping(prefix, uri);
      }

   }

   public void endPrefixMapping(String prefix) throws SAXException {
      if (!this.skipEvents) {
         this.contentHandler.endPrefixMapping(prefix);
      }

   }

   public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
      if ("http://www.w3.org/2001/XInclude".equals(uri) && "include".equals(localName)) {
         String href = atts.getValue("href");
         if (href == null) {
            throw new SAXException("XInclude href attribute is missing");
         }

         String parse = atts.getValue("parse");
         if (parse != null && !"xml".equals(parse)) {
            throw new UnsupportedOperationException("Only parse=\"xml\" is currently supported");
         }

         String xpointer = atts.getValue("xpointer");
         URL url = ClassLoaderUtils.getResource(href, XIncludeHandler.class);
         if (url == null) {
            throw new SAXException("XML file not found: " + href);
         }

         Document document = null;

         try {
            document = (Document)this.uriDocMap.get(url.toURI());
         } catch (URISyntaxException var28) {
            throw new SAXException(var28);
         }

         if (document == null) {
            DOMResult domResult = new DOMResult();

            try {
               XMLReader xmlReader = XMLReaderFactory.createXMLReader();
               SAXTransformerFactory saxTransformerFactory = (SAXTransformerFactory)SAXTransformerFactory.newInstance();
               saxTransformerFactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", Boolean.TRUE);
               TransformerHandler transformerHandler = saxTransformerFactory.newTransformerHandler();
               transformerHandler.setResult(domResult);
               xmlReader.setContentHandler(new XIncludeHandler(transformerHandler, this.uriDocMap));
               xmlReader.parse(url.toExternalForm());
            } catch (TransformerConfigurationException var26) {
               throw new SAXException(var26);
            } catch (IOException var27) {
               throw new SAXException(var27);
            }

            document = (Document)domResult.getNode();
            document.setDocumentURI(url.toExternalForm());

            try {
               this.uriDocMap.put(url.toURI(), document);
            } catch (URISyntaxException var25) {
               throw new SAXException(var25);
            }
         }

         SAXResult saxResult = new SAXResult(this);
         this.skipEvents = true;

         try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", Boolean.TRUE);
            Transformer transformer = transformerFactory.newTransformer();
            if (xpointer == null) {
               transformer.transform(new DOMSource(document, document.getDocumentURI()), saxResult);
            } else {
               NodeList nodeList = this.evaluateXPointer(xpointer, document);
               int length = nodeList.getLength();

               for(int i = 0; i < length; ++i) {
                  Node node = nodeList.item(i);
                  transformer.transform(new DOMSource(node, document.getDocumentURI()), saxResult);
               }
            }
         } catch (TransformerConfigurationException var29) {
            throw new SAXException(var29);
         } catch (TransformerException var30) {
            throw new SAXException(var30);
         } finally {
            this.skipEvents = false;
         }
      } else {
         this.contentHandler.startElement(uri, localName, qName, atts);
      }

   }

   public void endElement(String uri, String localName, String qName) throws SAXException {
      if (!"http://www.w3.org/2001/XInclude".equals(uri) || !"include".equals(localName)) {
         this.contentHandler.endElement(uri, localName, qName);
      }

   }

   public void characters(char[] ch, int start, int length) throws SAXException {
      this.contentHandler.characters(ch, start, length);
   }

   public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
      this.contentHandler.ignorableWhitespace(ch, start, length);
   }

   public void processingInstruction(String target, String data) throws SAXException {
      this.contentHandler.processingInstruction(target, data);
   }

   public void skippedEntity(String name) throws SAXException {
      this.contentHandler.skippedEntity(name);
   }

   public void warning(SAXParseException e) throws SAXException {
      LOG.warn(e.getMessage(), e);
   }

   public void error(SAXParseException e) throws SAXException {
      LOG.error(e.getMessage(), e);
   }

   public void fatalError(SAXParseException e) throws SAXException {
      LOG.error(e.getMessage(), e);
   }

   private NodeList evaluateXPointer(String xpointer, Node node) throws SAXException {
      String xPointerSchemeString = "xpointer(";
      String xmlnsSchemeString = "xmlns(";
      int xPointerSchemeIndex = xpointer.indexOf("xpointer(");
      if (xPointerSchemeIndex < 0) {
         throw new SAXException("Only xpointer scheme is supported ATM");
      } else {
         xPointerSchemeIndex += "xpointer(".length();
         int xPointerSchemeEndIndex = this.findBalancedEndIndex(xpointer, xPointerSchemeIndex, '(', ')');
         XPathFactory xPathFactory = XPathFactory.newInstance();

         try {
            xPathFactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", Boolean.TRUE);
         } catch (XPathFactoryConfigurationException var14) {
            throw new SAXException(var14);
         }

         XPath xPath = xPathFactory.newXPath();
         int xmlnsSchemeIndex = xpointer.indexOf("xmlns(");
         if (xmlnsSchemeIndex >= 0) {
            xmlnsSchemeIndex += "xmlns(".length();
            int xmlnsSchemeEndIndex = this.findBalancedEndIndex(xpointer, xmlnsSchemeIndex, '(', ')');
            String namespaceScheme = xpointer.substring(xmlnsSchemeIndex, xmlnsSchemeEndIndex);
            final String[] namespaceSplit = namespaceScheme.split("=");
            xPath.setNamespaceContext(new NamespaceContext() {
               public String getNamespaceURI(String prefix) {
                  return prefix.equals(namespaceSplit[0]) ? namespaceSplit[1] : null;
               }

               public String getPrefix(String namespaceURI) {
                  return namespaceURI.equals(namespaceSplit[1]) ? namespaceSplit[0] : null;
               }

               public Iterator getPrefixes(String namespaceURI) {
                  return null;
               }
            });
         }

         try {
            return (NodeList)xPath.evaluate(xpointer.substring(xPointerSchemeIndex, xPointerSchemeEndIndex), node, XPathConstants.NODESET);
         } catch (XPathExpressionException var13) {
            throw new SAXException(var13);
         }
      }
   }

   private int findBalancedEndIndex(String string, int startIndex, char opening, char ending) {
      int endIndex = -1;
      int openPar = 1;
      int length = string.length();

      for(int i = startIndex; i < length; ++i) {
         char curChar = string.charAt(i);
         if (curChar == opening) {
            ++openPar;
         } else if (curChar == ending) {
            --openPar;
         }

         if (openPar == 0) {
            endIndex = i;
            break;
         }
      }

      return endIndex;
   }
}
