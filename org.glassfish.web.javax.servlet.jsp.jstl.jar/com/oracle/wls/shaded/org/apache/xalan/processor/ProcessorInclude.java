package com.oracle.wls.shaded.org.apache.xalan.processor;

import com.oracle.wls.shaded.org.apache.xalan.res.XSLMessages;
import com.oracle.wls.shaded.org.apache.xml.utils.DOM2Helper;
import com.oracle.wls.shaded.org.apache.xml.utils.SystemIDResolver;
import com.oracle.wls.shaded.org.apache.xml.utils.TreeWalker;
import java.io.IOException;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class ProcessorInclude extends XSLTElementProcessor {
   static final long serialVersionUID = -4570078731972673481L;
   private String m_href = null;

   public String getHref() {
      return this.m_href;
   }

   public void setHref(String baseIdent) {
      this.m_href = baseIdent;
   }

   protected int getStylesheetType() {
      return 2;
   }

   protected String getStylesheetInclErr() {
      return "ER_STYLESHEET_INCLUDES_ITSELF";
   }

   public void startElement(StylesheetHandler handler, String uri, String localName, String rawName, Attributes attributes) throws SAXException {
      this.setPropertiesFromAttributes(handler, rawName, attributes, this);

      try {
         Source sourceFromURIResolver = this.getSourceFromUriResolver(handler);
         String hrefUrl = this.getBaseURIOfIncludedStylesheet(handler, sourceFromURIResolver);
         if (handler.importStackContains(hrefUrl)) {
            throw new SAXException(XSLMessages.createMessage(this.getStylesheetInclErr(), new Object[]{hrefUrl}));
         }

         handler.pushImportURL(hrefUrl);
         handler.pushImportSource(sourceFromURIResolver);
         int savedStylesheetType = handler.getStylesheetType();
         handler.setStylesheetType(this.getStylesheetType());
         handler.pushNewNamespaceSupport();

         try {
            this.parse(handler, uri, localName, rawName, attributes);
         } finally {
            handler.setStylesheetType(savedStylesheetType);
            handler.popImportURL();
            handler.popImportSource();
            handler.popNamespaceSupport();
         }
      } catch (TransformerException var14) {
         handler.error(var14.getMessage(), var14);
      }

   }

   protected void parse(StylesheetHandler handler, String uri, String localName, String rawName, Attributes attributes) throws SAXException {
      TransformerFactoryImpl processor = handler.getStylesheetProcessor();
      URIResolver uriresolver = processor.getURIResolver();

      try {
         Source source = null;
         if (null != uriresolver) {
            source = handler.peekSourceFromURIResolver();
            if (null != source && source instanceof DOMSource) {
               Node node = ((DOMSource)source).getNode();
               String systemId = handler.peekImportURL();
               if (systemId != null) {
                  handler.pushBaseIndentifier(systemId);
               }

               TreeWalker walker = new TreeWalker(handler, new DOM2Helper(), systemId);

               try {
                  walker.traverse(node);
               } catch (SAXException var24) {
                  throw new TransformerException(var24);
               }

               if (systemId != null) {
                  handler.popBaseIndentifier();
               }

               return;
            }
         }

         if (null == source) {
            String absURL = SystemIDResolver.getAbsoluteURI(this.getHref(), handler.getBaseIdentifier());
            source = new StreamSource(absURL);
         }

         Source source = this.processSource(handler, (Source)source);
         XMLReader reader = null;
         if (source instanceof SAXSource) {
            SAXSource saxSource = (SAXSource)source;
            reader = saxSource.getXMLReader();
         }

         InputSource inputSource = SAXSource.sourceToInputSource(source);
         if (null == reader) {
            try {
               SAXParserFactory factory = SAXParserFactory.newInstance();
               factory.setNamespaceAware(true);
               if (handler.getStylesheetProcessor().isSecureProcessing()) {
                  try {
                     factory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
                  } catch (SAXException var26) {
                  }
               }

               SAXParser jaxpParser = factory.newSAXParser();
               reader = jaxpParser.getXMLReader();
            } catch (ParserConfigurationException var27) {
               throw new SAXException(var27);
            } catch (FactoryConfigurationError var28) {
               throw new SAXException(var28.toString());
            } catch (NoSuchMethodError var29) {
            } catch (AbstractMethodError var30) {
            }
         }

         if (null == reader) {
            reader = XMLReaderFactory.createXMLReader();
         }

         if (null != reader) {
            reader.setContentHandler(handler);
            handler.pushBaseIndentifier(inputSource.getSystemId());

            try {
               reader.parse(inputSource);
            } finally {
               handler.popBaseIndentifier();
            }
         }
      } catch (IOException var31) {
         handler.error("ER_IOEXCEPTION", new Object[]{this.getHref()}, var31);
      } catch (TransformerException var32) {
         handler.error(var32.getMessage(), var32);
      }

   }

   protected Source processSource(StylesheetHandler handler, Source source) {
      return source;
   }

   private Source getSourceFromUriResolver(StylesheetHandler handler) throws TransformerException {
      Source s = null;
      TransformerFactoryImpl processor = handler.getStylesheetProcessor();
      URIResolver uriresolver = processor.getURIResolver();
      if (uriresolver != null) {
         String href = this.getHref();
         String base = handler.getBaseIdentifier();
         s = uriresolver.resolve(href, base);
      }

      return s;
   }

   private String getBaseURIOfIncludedStylesheet(StylesheetHandler handler, Source s) throws TransformerException {
      String idFromUriResolverSource;
      String baseURI;
      if (s != null && (idFromUriResolverSource = s.getSystemId()) != null) {
         baseURI = idFromUriResolverSource;
      } else {
         baseURI = SystemIDResolver.getAbsoluteURI(this.getHref(), handler.getBaseIdentifier());
      }

      return baseURI;
   }
}
