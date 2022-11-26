package com.oracle.wls.shaded.org.apache.xml.utils;

import java.util.Hashtable;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class XMLReaderManager {
   private static final String NAMESPACES_FEATURE = "http://xml.org/sax/features/namespaces";
   private static final String NAMESPACE_PREFIXES_FEATURE = "http://xml.org/sax/features/namespace-prefixes";
   private static final XMLReaderManager m_singletonManager = new XMLReaderManager();
   private static SAXParserFactory m_parserFactory;
   private ThreadLocal m_readers;
   private Hashtable m_inUse;

   private XMLReaderManager() {
   }

   public static XMLReaderManager getInstance() {
      return m_singletonManager;
   }

   public synchronized XMLReader getXMLReader() throws SAXException {
      if (this.m_readers == null) {
         this.m_readers = new ThreadLocal();
      }

      if (this.m_inUse == null) {
         this.m_inUse = new Hashtable();
      }

      XMLReader reader = (XMLReader)this.m_readers.get();
      boolean threadHasReader = reader != null;
      if (threadHasReader && this.m_inUse.get(reader) != Boolean.TRUE) {
         this.m_inUse.put(reader, Boolean.TRUE);
      } else {
         try {
            try {
               reader = XMLReaderFactory.createXMLReader();
            } catch (Exception var7) {
               try {
                  if (m_parserFactory == null) {
                     m_parserFactory = SAXParserFactory.newInstance();
                     m_parserFactory.setNamespaceAware(true);
                  }

                  reader = m_parserFactory.newSAXParser().getXMLReader();
               } catch (ParserConfigurationException var6) {
                  throw var6;
               }
            }

            try {
               reader.setFeature("http://xml.org/sax/features/namespaces", true);
               reader.setFeature("http://xml.org/sax/features/namespace-prefixes", false);
            } catch (SAXException var5) {
            }
         } catch (ParserConfigurationException var8) {
            throw new SAXException(var8);
         } catch (FactoryConfigurationError var9) {
            throw new SAXException(var9.toString());
         } catch (NoSuchMethodError var10) {
         } catch (AbstractMethodError var11) {
         }

         if (!threadHasReader) {
            this.m_readers.set(reader);
            this.m_inUse.put(reader, Boolean.TRUE);
         }
      }

      return reader;
   }

   public synchronized void releaseXMLReader(XMLReader reader) {
      if (this.m_readers.get() == reader && reader != null) {
         this.m_inUse.remove(reader);
      }

   }
}
