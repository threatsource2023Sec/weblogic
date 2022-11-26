package weblogic.xml.registry;

import java.io.IOException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.validation.SchemaFactory;
import javax.xml.xpath.XPathFactory;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;
import weblogic.xml.XMLLogger;
import weblogic.xml.jaxp.ReParsingStatus;
import weblogic.xml.util.InputSourceUtil;
import weblogic.xml.util.Tools;
import weblogic.xml.util.cache.entitycache.CX;

public class RegistryEntityResolver implements EntityResolver {
   XMLRegistry[] registryPath = null;
   private ReParsingStatus status;

   RegistryEntityResolver(XMLRegistry[] regs) {
      this.registryPath = regs;
   }

   public XMLRegistry[] getRegistryPath() {
      try {
         return XMLRegistry.getXMLRegistryPath();
      } catch (XMLRegistryException var2) {
         XMLLogger.logStackTrace("XMLRegistryException on the path.", var2);
         return this.registryPath;
      }
   }

   public RegistryEntityResolver() throws XMLRegistryException {
      this.registryPath = XMLRegistry.getXMLRegistryPath();
   }

   public boolean hasDocumentSpecificParserEntries() {
      for(int i = 0; i < this.registryPath.length; ++i) {
         if (this.registryPath[i].hasDocumentSpecificParserEntries()) {
            return true;
         }
      }

      return false;
   }

   public boolean hasDocumentSpecificEntityEntries() {
      for(int i = 0; i < this.registryPath.length; ++i) {
         if (this.registryPath[i].hasDocumentSpecificEntityEntries()) {
            return true;
         }
      }

      return false;
   }

   public TransformerFactory getTransformerFactory() throws XMLRegistryException {
      TransformerFactory factory = null;

      for(int i = 0; i < this.registryPath.length; ++i) {
         factory = this.registryPath[i].getTransformerFactory();
         if (factory != null) {
            break;
         }
      }

      return factory;
   }

   public TransformerFactory getTransformerFactory(String publicId, String systemId, String rootElementTag) throws XMLRegistryException {
      if (publicId == null && systemId == null && rootElementTag == null) {
         return null;
      } else {
         TransformerFactory factory = null;

         for(int i = 0; i < this.registryPath.length; ++i) {
            factory = this.registryPath[i].getTransformerFactory(publicId, systemId, rootElementTag);
            if (factory != null) {
               break;
            }
         }

         return factory;
      }
   }

   public DocumentBuilderFactory getDocumentBuilderFactory() throws XMLRegistryException {
      DocumentBuilderFactory factory = null;

      for(int i = 0; i < this.registryPath.length; ++i) {
         factory = this.registryPath[i].getDocumentBuilderFactory();
         if (factory != null) {
            break;
         }
      }

      return factory;
   }

   public DocumentBuilderFactory getDocumentBuilderFactory(String publicId, String systemId, String rootElementTag) throws XMLRegistryException {
      if (publicId == null && systemId == null && rootElementTag == null) {
         return null;
      } else {
         DocumentBuilderFactory factory = null;

         for(int i = 0; i < this.getRegistryPath().length; ++i) {
            factory = this.registryPath[i].getDocumentBuilderFactory(publicId, systemId, rootElementTag);
            if (factory != null) {
               break;
            }
         }

         return factory;
      }
   }

   public Parser getParser(String publicId, String systemId, String rootElementTag) throws XMLRegistryException {
      Parser parser = null;

      for(int i = 0; i < this.registryPath.length; ++i) {
         parser = this.registryPath[i].getParser(publicId, systemId, rootElementTag);
         if (parser != null) {
            break;
         }
      }

      return parser;
   }

   public SAXParserFactory getSAXParserFactory(String publicId, String systemId, String rootElementTag) throws XMLRegistryException {
      if (publicId == null && systemId == null && rootElementTag == null) {
         return null;
      } else {
         SAXParserFactory factory = null;

         for(int i = 0; i < this.registryPath.length; ++i) {
            factory = this.registryPath[i].getSAXParserFactory(publicId, systemId, rootElementTag);
            if (factory != null) {
               break;
            }
         }

         return factory;
      }
   }

   public SAXParserFactory getSAXParserFactory() throws XMLRegistryException {
      SAXParserFactory factory = null;

      for(int i = 0; i < this.registryPath.length; ++i) {
         factory = this.registryPath[i].getSAXParserFactory();
         if (factory != null) {
            break;
         }
      }

      return factory;
   }

   public XPathFactory getXPathFactory() throws XMLRegistryException {
      XPathFactory factory = null;

      for(int i = 0; i < this.registryPath.length; ++i) {
         factory = this.registryPath[i].getXPathFactory();
         if (factory != null) {
            break;
         }
      }

      return factory;
   }

   public SchemaFactory getSchemaFactory() throws XMLRegistryException {
      SchemaFactory factory = null;

      for(int i = 0; i < this.registryPath.length; ++i) {
         factory = this.registryPath[i].getSchemaFactory();
         if (factory != null) {
            break;
         }
      }

      return factory;
   }

   public XMLInputFactory getXMLInputFactory() throws XMLRegistryException {
      XMLInputFactory factory = null;

      for(int i = 0; i < this.registryPath.length; ++i) {
         factory = this.registryPath[i].getXMLInputFactory();
         if (factory != null) {
            break;
         }
      }

      return factory;
   }

   public XMLOutputFactory getXMLOutputFactory() throws XMLRegistryException {
      XMLOutputFactory factory = null;

      for(int i = 0; i < this.registryPath.length; ++i) {
         factory = this.registryPath[i].getXMLOutputFactory();
         if (factory != null) {
            break;
         }
      }

      return factory;
   }

   public XMLEventFactory getXMLEventFactory() throws XMLRegistryException {
      XMLEventFactory factory = null;

      for(int i = 0; i < this.registryPath.length; ++i) {
         factory = this.registryPath[i].getXMLEventFactory();
         if (factory != null) {
            break;
         }
      }

      return factory;
   }

   public String getHandleEntityInvalidation(String publicId, String systemId) throws XMLRegistryException {
      if (publicId == null && systemId == null) {
         return null;
      } else {
         String invalidation = null;

         for(int i = 0; i < this.getRegistryPath().length; ++i) {
            if (this.registryPath[i].hasHandleEntityInvalidationSetSupport()) {
               invalidation = this.registryPath[i].getHandleEntityInvalidation(publicId, systemId);
               if (invalidation != null) {
                  break;
               }
            }
         }

         return invalidation;
      }
   }

   public String getHandleEntityInvalidation() throws XMLRegistryException {
      String invalidation = null;

      for(int i = 0; i < this.getRegistryPath().length; ++i) {
         if (this.registryPath[i].hasHandleEntityInvalidationSetSupport()) {
            invalidation = this.registryPath[i].getHandleEntityInvalidation();
            if (invalidation != null) {
               break;
            }
         }
      }

      return invalidation;
   }

   public boolean hasHandleEntityInvalidationSetSupport() {
      for(int i = 0; i < this.getRegistryPath().length; ++i) {
         if (this.registryPath[i].hasHandleEntityInvalidationSetSupport()) {
            return true;
         }
      }

      return false;
   }

   public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
      boolean useDummy = this.status == null;
      if (useDummy) {
         this.status = new ReParsingStatus();
      }

      InputSource var4;
      try {
         this.status.usedRegistryResolver = true;
         if (this.status.lastEntity != null) {
            InputSourceUtil.resetInputSource(this.status.lastEntity);
         }

         var4 = this.status.lastEntity = this.resolveEntityI(publicId, systemId, true);
      } finally {
         if (useDummy) {
            this.status = null;
         }

      }

      return var4;
   }

   private InputSource resolveEntityI(String publicId, String systemId, boolean cacheIt) throws SAXException, IOException {
      InputSource s = null;
      String descr = Tools.getEntityDescriptor(publicId, systemId, (String)null);
      this.status.usedCache = true;

      for(int index = 0; index < this.registryPath.length; ++index) {
         XMLRegistry registry = this.registryPath[index];
         ResolveContext context = new ResolveContext();
         context.desc = descr;

         try {
            s = this.getEntityFromCache(registry, publicId, systemId, false, context);
            if (s == null && context.expired && this.status.isReParsing()) {
               s = this.getEntityFromCache(registry, publicId, systemId, true, context);
            }

            if (!this.status.isReParsing() && s == null || this.status.isReParsing() && InputSourceUtil.isEqual(s, this.status.lastEntity)) {
               RefreshCacheLock lock = registry.getLock();

               try {
                  lock.lock(publicId, systemId);
                  s = this.getEntityFromCache(registry, publicId, systemId, false, context);
                  if (s == null && context.expired && this.status.isReParsing()) {
                     s = this.getEntityFromCache(registry, publicId, systemId, true, context);
                  }

                  if (!this.status.isReParsing() && s == null || this.status.isReParsing() && InputSourceUtil.isEqual(s, this.status.lastEntity)) {
                     XMLRegistry.ResolvedEntity resolvedEntity = this.getEntityFromNonCache(registry, publicId, systemId, context);
                     s = resolvedEntity != null ? resolvedEntity.inputSource() : null;
                     if (s != null) {
                        this.status.usedCache = false;
                        if (cacheIt && resolvedEntity.isSubjectToCaching()) {
                           this.addToCache(registry, resolvedEntity, publicId, systemId, context);
                        }
                     }
                  }
               } finally {
                  lock.unlock(publicId, systemId);
               }
            }

            if (s == null && context.expired) {
               s = this.getEntityFromCache(registry, publicId, systemId, true, context);
            }

            if (s == null && context.remoteAccessException != null) {
               throw context.remoteAccessException;
            }
         } catch (XMLRegistryException var16) {
            throw new SAXException(var16);
         } catch (RuntimeException var17) {
            throw var17;
         }

         if (s != null) {
            break;
         }
      }

      if (this.status.isReParsing() && InputSourceUtil.isEqual(s, this.status.lastEntity)) {
         throw new ReParsingEntityNotChangedException();
      } else {
         return s;
      }
   }

   private InputSource getEntityFromCache(XMLRegistry registry, String publicId, String systemId, boolean renew, ResolveContext context) throws XMLRegistryException, IOException {
      InputSource s = null;
      EntityCache cache = registry.getCache();
      if (cache == null) {
         return s;
      } else {
         if (renew) {
            cache.renew(publicId, systemId);
         }

         try {
            s = cache.get(publicId, systemId);
            InputSourceUtil.transformInputSource(s);
         } catch (XMLRegistryExceptionCacheEntryExpired var9) {
            context.expired = true;
         } catch (XMLRegistryException var10) {
            XMLLogger.logEntityCacheBroken();
         }

         return s;
      }
   }

   private XMLRegistry.ResolvedEntity getEntityFromNonCache(XMLRegistry registry, String publicId, String systemId, ResolveContext context) throws XMLRegistryException, IOException {
      try {
         XMLRegistry.ResolvedEntity entity = registry.getEntity(publicId, systemId);
         if (entity != null) {
            InputSourceUtil.transformInputSource(entity.inputSource());
         }

         return entity;
      } catch (XMLRegistryRemoteAccessException var6) {
         context.remoteAccessException = var6;
         return null;
      }
   }

   private void addToCache(XMLRegistry registry, XMLRegistry.ResolvedEntity resolvedEntity, String publicId, String systemId, ResolveContext context) throws IllegalArgumentException, IOException {
      try {
         EntityCache cache = registry.getCache();
         if (cache == null) {
            return;
         }

         InputSource is = resolvedEntity.inputSource();
         int timeoutInterval = registry.getCacheTimeoutInterval(resolvedEntity.getEntry());
         boolean persistent = true;
         if (resolvedEntity.isLocal()) {
            persistent = false;
         } else {
            persistent = true;
         }

         try {
            cache.add(is, persistent, timeoutInterval * 1000);
         } catch (OutOfMemoryError var17) {
         } catch (CX.EntryTooLarge var18) {
         } catch (XMLRegistryException var19) {
            XMLLogger.logEntityCacheBroken();
         } finally {
            InputSourceUtil.resetInputSource(is);
         }
      } catch (XMLRegistryException var21) {
      }

   }

   public void hookStatus(ReParsingStatus status) {
      this.status = status;
   }

   private class ResolveContext {
      public String desc;
      public boolean expired;
      public XMLRegistryRemoteAccessException remoteAccessException;

      private ResolveContext() {
         this.desc = null;
         this.expired = false;
         this.remoteAccessException = null;
      }

      // $FF: synthetic method
      ResolveContext(Object x1) {
         this();
      }
   }
}
