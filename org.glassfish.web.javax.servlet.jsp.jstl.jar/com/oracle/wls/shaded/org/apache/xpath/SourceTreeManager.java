package com.oracle.wls.shaded.org.apache.xpath;

import com.oracle.wls.shaded.org.apache.xml.dtm.DTM;
import com.oracle.wls.shaded.org.apache.xml.dtm.DTMWSFilter;
import com.oracle.wls.shaded.org.apache.xml.utils.SystemIDResolver;
import java.io.IOException;
import java.util.Vector;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class SourceTreeManager {
   private Vector m_sourceTree = new Vector();
   URIResolver m_uriResolver;

   public void reset() {
      this.m_sourceTree = new Vector();
   }

   public void setURIResolver(URIResolver resolver) {
      this.m_uriResolver = resolver;
   }

   public URIResolver getURIResolver() {
      return this.m_uriResolver;
   }

   public String findURIFromDoc(int owner) {
      int n = this.m_sourceTree.size();

      for(int i = 0; i < n; ++i) {
         SourceTree sTree = (SourceTree)this.m_sourceTree.elementAt(i);
         if (owner == sTree.m_root) {
            return sTree.m_url;
         }
      }

      return null;
   }

   public Source resolveURI(String base, String urlString, SourceLocator locator) throws TransformerException, IOException {
      Source source = null;
      if (null != this.m_uriResolver) {
         source = this.m_uriResolver.resolve(urlString, base);
      }

      if (null == source) {
         String uri = SystemIDResolver.getAbsoluteURI(urlString, base);
         source = new StreamSource(uri);
      }

      return (Source)source;
   }

   public void removeDocumentFromCache(int n) {
      if (-1 != n) {
         for(int i = this.m_sourceTree.size() - 1; i >= 0; --i) {
            SourceTree st = (SourceTree)this.m_sourceTree.elementAt(i);
            if (st != null && st.m_root == n) {
               this.m_sourceTree.removeElementAt(i);
               return;
            }
         }

      }
   }

   public void putDocumentInCache(int n, Source source) {
      int cachedNode = this.getNode(source);
      if (-1 != cachedNode) {
         if (cachedNode != n) {
            throw new RuntimeException("Programmer's Error!  putDocumentInCache found reparse of doc: " + source.getSystemId());
         }
      } else {
         if (null != source.getSystemId()) {
            this.m_sourceTree.addElement(new SourceTree(n, source.getSystemId()));
         }

      }
   }

   public int getNode(Source source) {
      String url = source.getSystemId();
      if (null == url) {
         return -1;
      } else {
         int n = this.m_sourceTree.size();

         for(int i = 0; i < n; ++i) {
            SourceTree sTree = (SourceTree)this.m_sourceTree.elementAt(i);
            if (url.equals(sTree.m_url)) {
               return sTree.m_root;
            }
         }

         return -1;
      }
   }

   public int getSourceTree(String base, String urlString, SourceLocator locator, XPathContext xctxt) throws TransformerException {
      try {
         Source source = this.resolveURI(base, urlString, locator);
         return this.getSourceTree(source, locator, xctxt);
      } catch (IOException var6) {
         throw new TransformerException(var6.getMessage(), locator, var6);
      }
   }

   public int getSourceTree(Source source, SourceLocator locator, XPathContext xctxt) throws TransformerException {
      int n = this.getNode(source);
      if (-1 != n) {
         return n;
      } else {
         n = this.parseToNode(source, locator, xctxt);
         if (-1 != n) {
            this.putDocumentInCache(n, source);
         }

         return n;
      }
   }

   public int parseToNode(Source source, SourceLocator locator, XPathContext xctxt) throws TransformerException {
      try {
         Object xowner = xctxt.getOwnerObject();
         DTM dtm;
         if (null != xowner && xowner instanceof DTMWSFilter) {
            dtm = xctxt.getDTM(source, false, (DTMWSFilter)xowner, false, true);
         } else {
            dtm = xctxt.getDTM(source, false, (DTMWSFilter)null, false, true);
         }

         return dtm.getDocument();
      } catch (Exception var6) {
         throw new TransformerException(var6.getMessage(), locator, var6);
      }
   }

   public static XMLReader getXMLReader(Source inputSource, SourceLocator locator) throws TransformerException {
      try {
         XMLReader reader = inputSource instanceof SAXSource ? ((SAXSource)inputSource).getXMLReader() : null;
         if (null == reader) {
            try {
               SAXParserFactory factory = SAXParserFactory.newInstance();
               factory.setNamespaceAware(true);
               SAXParser jaxpParser = factory.newSAXParser();
               reader = jaxpParser.getXMLReader();
            } catch (ParserConfigurationException var6) {
               throw new SAXException(var6);
            } catch (FactoryConfigurationError var7) {
               throw new SAXException(var7.toString());
            } catch (NoSuchMethodError var8) {
            } catch (AbstractMethodError var9) {
            }

            if (null == reader) {
               reader = XMLReaderFactory.createXMLReader();
            }
         }

         try {
            reader.setFeature("http://xml.org/sax/features/namespace-prefixes", true);
         } catch (SAXException var5) {
         }

         return reader;
      } catch (SAXException var10) {
         throw new TransformerException(var10.getMessage(), locator, var10);
      }
   }
}
