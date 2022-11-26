package org.apache.xmlbeans.impl.piccolo.xml;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.apache.xmlbeans.impl.piccolo.util.RecursionException;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class EntityManager {
   public static final int GENERAL = 0;
   public static final int PARAMETER = 1;
   private EntityResolver resolver;
   private Map[] entityMaps;
   private Map entitiesByURI;

   public EntityManager() {
      this((EntityResolver)null);
   }

   public EntityManager(EntityResolver resolver) {
      this.entityMaps = new HashMap[]{new HashMap(), new HashMap()};
      this.entitiesByURI = new HashMap();
      this.setResolver(resolver);
   }

   public void setResolver(EntityResolver resolver) {
      this.resolver = resolver;
   }

   public EntityResolver getResolver() {
      return this.resolver;
   }

   public boolean putInternal(String name, String value, int type) {
      if (this.entityMaps[type].get(name) == null) {
         this.entityMaps[type].put(name, new Entry(value));
         return true;
      } else {
         return false;
      }
   }

   public boolean putExternal(Entity context, String name, String pubID, String sysID, int type) throws MalformedURLException {
      if (this.entityMaps[type].get(name) == null) {
         sysID = resolveSystemID(context.getSystemID(), sysID);
         Entry e = new Entry(pubID, sysID);
         this.entityMaps[type].put(name, e);
         if (pubID != null && pubID.length() > 0) {
            this.entitiesByURI.put(pubID, e);
         }

         this.entitiesByURI.put(sysID, e);
         return true;
      } else {
         return false;
      }
   }

   public boolean putUnparsed(Entity context, String name, String pubID, String sysID, String ndata, int type) throws MalformedURLException {
      if (this.entityMaps[type].get(name) == null) {
         this.entityMaps[type].put(name, new Entry(pubID, sysID, ndata));
         return true;
      } else {
         return false;
      }
   }

   public void clear() {
      this.entityMaps[0].clear();
      this.entityMaps[1].clear();
      this.entitiesByURI.clear();
   }

   public Entity getByName(String name, int type) {
      return (Entry)this.entityMaps[type].get(name);
   }

   public Entity getByID(Entity context, String pubID, String sysID) throws MalformedURLException {
      Entity result = null;
      sysID = resolveSystemID(context.getSystemID(), sysID);
      result = (Entity)this.entitiesByURI.get(sysID);
      if (result != null) {
         return result;
      } else {
         if (pubID != null && pubID.length() > 0) {
            result = (Entity)this.entitiesByURI.get(pubID);
            if (result != null) {
               return result;
            }
         }

         Entity result = new Entry(pubID, sysID);
         if (pubID != null && pubID.length() > 0) {
            this.entitiesByURI.put(pubID, result);
         }

         this.entitiesByURI.put(sysID, result);
         return result;
      }
   }

   public static String resolveSystemID(String contextSysID, String sysID) throws MalformedURLException {
      URL url;
      if (contextSysID != null) {
         url = new URL(new URL(contextSysID), sysID);
      } else {
         url = new URL(sysID);
      }

      return url.toString();
   }

   private final class Entry implements Entity {
      boolean isOpen;
      char[] value;
      String pubID;
      String sysID;
      String ndata;
      XMLInputReader reader;
      boolean isStandalone;

      Entry(String value) {
         this.isOpen = false;
         this.reader = null;
         this.isStandalone = false;
         this.pubID = this.sysID = this.ndata = null;
         this.value = value.toCharArray();
      }

      Entry(String pubID, String sysID) {
         this(pubID, sysID, (String)null);
      }

      Entry(String pubID, String sysID, String ndata) {
         this.isOpen = false;
         this.reader = null;
         this.isStandalone = false;
         this.pubID = pubID;
         this.sysID = sysID;
         this.ndata = ndata;
      }

      public void open() throws RecursionException, SAXException, IOException {
         if (this.ndata != null) {
            throw new FatalParsingException("Cannot reference entity; unknown NDATA type '" + this.ndata + "'");
         } else if (this.isOpen) {
            throw new RecursionException();
         } else {
            if (!this.isInternal()) {
               if (EntityManager.this.resolver == null) {
                  this.reader = new XMLStreamReader((new URL(this.sysID)).openStream(), true);
               } else {
                  InputSource source = EntityManager.this.resolver.resolveEntity(this.pubID, this.sysID);
                  if (source == null) {
                     this.reader = new XMLStreamReader((new URL(this.sysID)).openStream(), true);
                  } else {
                     Reader r = source.getCharacterStream();
                     if (r != null) {
                        this.reader = new XMLReaderReader(r, true);
                     } else {
                        InputStream in = source.getByteStream();
                        if (in != null) {
                           this.reader = new XMLStreamReader(in, source.getEncoding(), true);
                        } else {
                           this.reader = new XMLStreamReader((new URL(source.getSystemId())).openStream(), source.getEncoding(), true);
                        }
                     }
                  }
               }

               this.isStandalone = this.reader.isXMLStandalone();
            }

            this.isOpen = true;
         }
      }

      public boolean isOpen() {
         return this.isOpen;
      }

      public void close() {
         this.isOpen = false;
         this.reader = null;
      }

      public String getSystemID() {
         return this.sysID;
      }

      public String getPublicID() {
         return this.pubID;
      }

      public boolean isStandalone() {
         return this.isStandalone;
      }

      public void setStandalone(boolean standalone) {
         this.isStandalone = standalone;
      }

      public boolean isInternal() {
         return this.sysID == null;
      }

      public boolean isParsed() {
         return this.ndata == null;
      }

      public String getDeclaredEncoding() {
         return this.reader != null ? this.reader.getXMLDeclaredEncoding() : null;
      }

      public boolean isStandaloneDeclared() {
         return this.reader != null ? this.reader.isXMLStandaloneDeclared() : false;
      }

      public String getXMLVersion() {
         return this.reader != null ? this.reader.getXMLVersion() : null;
      }

      public Reader getReader() {
         return (Reader)(this.isInternal() ? new CharArrayReader(this.value) : this.reader);
      }

      public String stringValue() {
         return new String(this.value);
      }

      public char[] charArrayValue() {
         return this.value;
      }
   }
}
