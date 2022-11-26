package weblogic.xml.registry;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import org.xml.sax.InputSource;
import weblogic.xml.XMLLogger;
import weblogic.xml.util.InputSourceUtil;
import weblogic.xml.util.XMLConstants;
import weblogic.xml.util.cache.entitycache.CX;
import weblogic.xml.util.cache.entitycache.CacheEntry;

public final class EntityCache implements XMLConstants {
   XMLRegistry registry = null;
   weblogic.xml.util.cache.entitycache.EntityCache underlyingCache = null;
   private weblogic.xml.util.cache.entitycache.EntityCache cache = null;

   public EntityCache(XMLRegistry registry, weblogic.xml.util.cache.entitycache.EntityCache underlyingCache) {
      this.registry = registry;
      this.underlyingCache = underlyingCache;
   }

   private weblogic.xml.util.cache.entitycache.EntityCache getCache() throws XMLRegistryException {
      return this.underlyingCache;
   }

   public void setMemorySize(int newInterval) throws XMLRegistryException {
      if (this.underlyingCache != null) {
         try {
            this.underlyingCache.setMemoryFootprint((long)newInterval);
         } catch (CX.EntityCacheException var3) {
            throw new XMLRegistryException("Can't set Cache Memory Size", var3);
         }
      }
   }

   public void setDiskSize(int newInterval) throws XMLRegistryException {
      if (this.underlyingCache != null) {
         try {
            this.underlyingCache.setDiskFootprint((long)newInterval);
         } catch (CX.EntityCacheException var3) {
            throw new XMLRegistryException("Can't set Cache Disk Size", var3);
         }
      }
   }

   public void add(InputSource ins, boolean isPersistent, int timeoutInterval) throws XMLRegistryException, CX.EntryTooLarge {
      if (this.underlyingCache != null) {
         CacheEntry entry = null;
         byte[] data = null;
         CacheKey key = null;

         try {
            byte[] data = InputSourceUtil.forceGetInputByteData(ins);
            key = new CacheKey(this.registry.getName(), ins.getPublicId(), ins.getSystemId());
            entry = new CacheEntry(this.getCache(), key, data, (long)data.length, (Object)null, (long)timeoutInterval);
            this.getCache().addEntry(key, entry, isPersistent);
         } catch (IOException var8) {
            throw new XMLRegistryException(var8);
         } catch (CX.EntryTooLarge var9) {
            XMLLogger.logEntityCacheRejection("" + key, String.valueOf(((Object[])data).length), String.valueOf(this.getCache().getMemoryFootprint()));
            throw var9;
         } catch (CX.EntityCacheException var10) {
            throw new XMLRegistryException(var10);
         } catch (Exception var11) {
            throw new XMLRegistryException(var11);
         }
      }
   }

   public InputSource get(String publicId, String systemId) throws XMLRegistryException, XMLRegistryExceptionCacheEntryExpired {
      if (this.underlyingCache == null) {
         return null;
      } else {
         InputSource ins = null;
         byte[] data = null;
         CacheKey key = null;

         try {
            if (publicId != null) {
               key = new CacheKey(this.registry.getName(), publicId, (String)null);
               data = (byte[])((byte[])this.getCache().getData(key));
            }

            if (data == null && systemId != null) {
               key = new CacheKey(this.registry.getName(), (String)null, systemId);
               data = (byte[])((byte[])this.getCache().getData(key));
            }
         } catch (CX.EntryExpired var7) {
            throw new XMLRegistryExceptionCacheEntryExpired(var7);
         } catch (CX.EntityCacheException var8) {
            throw new XMLRegistryException("Failure getting: " + key + " from cache.", var8);
         }

         return data == null ? null : this.getInputSource(publicId, systemId, data);
      }
   }

   public void renew(String publicId, String systemId) throws XMLRegistryException {
      if (this.underlyingCache != null) {
         CacheKey key = null;

         try {
            Object o = null;
            if (publicId != null) {
               key = new CacheKey(this.registry.getName(), publicId, (String)null);
               o = this.getCache().renewLease(key);
            }

            if (o == null && systemId != null) {
               key = new CacheKey(this.registry.getName(), (String)null, systemId);
               this.getCache().renewLease(key);
            }

         } catch (CX.EntityCacheException var5) {
            throw new XMLRegistryException("Failure renewing: " + key, var5);
         }
      }
   }

   public void remove(String publicId, String systemId) throws XMLRegistryException {
      if (this.underlyingCache != null) {
         CacheKey key = null;

         try {
            Object o = null;
            if (publicId != null) {
               key = new CacheKey(this.registry.getName(), publicId, (String)null);
               o = this.getCache().removeEntry(key);
            }

            if (o == null && systemId != null) {
               key = new CacheKey(this.registry.getName(), (String)null, systemId);
               this.getCache().removeEntry(key);
            }

         } catch (CX.EntityCacheException var5) {
            throw new XMLRegistryException("Failure removing: " + key, var5);
         }
      }
   }

   public void putrify(String publicId, String systemId) throws XMLRegistryException {
      if (this.underlyingCache != null) {
         CacheKey key = null;

         try {
            Object o = null;
            if (publicId != null) {
               key = new CacheKey(this.registry.getName(), publicId, (String)null);
               o = this.getCache().putrify(key);
            }

            if (o == null && systemId != null) {
               key = new CacheKey(this.registry.getName(), (String)null, systemId);
               this.getCache().putrify(key);
            }

         } catch (CX.EntityCacheException var5) {
            throw new XMLRegistryException("Failure putrifying: " + key, var5);
         }
      }
   }

   protected InputSource getInputSource(String publicId, String systemId, byte[] data) {
      InputSource ins = new InputSource();
      ins.setPublicId(publicId);
      ins.setSystemId(systemId);
      ins.setByteStream(new ByteArrayInputStream(data));
      return ins;
   }

   String getDescription(Serializable key) {
      if (key == null) {
         return "UNKNOWN (KEY NOT SET)";
      } else {
         String keyS = key.toString();
         return keyS.substring(keyS.indexOf(":"));
      }
   }
}
