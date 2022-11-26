package org.apache.openjpa.meta;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.util.StringDistance;
import serp.util.Strings;

public abstract class Extensions implements Serializable {
   public static final String OPENJPA = "openjpa";
   private static final Localizer _loc = Localizer.forPackage(Extensions.class);
   private Map _exts = null;
   private Map _embed = null;

   public boolean isEmpty() {
      return (this._exts == null || this._exts.isEmpty()) && (this._embed == null || this._embed.isEmpty());
   }

   public String[] getExtensionVendors() {
      if (this._exts != null && !this._exts.isEmpty()) {
         Set vendors = new TreeSet();
         Iterator itr = this._exts.keySet().iterator();

         while(itr.hasNext()) {
            vendors.add(this.getVendor(itr.next()));
         }

         return (String[])((String[])vendors.toArray(new String[vendors.size()]));
      } else {
         return new String[0];
      }
   }

   public String[] getExtensionKeys() {
      return this.getExtensionKeys("openjpa");
   }

   public String[] getExtensionKeys(String vendor) {
      if (this._exts != null && !this._exts.isEmpty()) {
         Collection keys = new TreeSet();
         Iterator itr = this._exts.keySet().iterator();

         while(itr.hasNext()) {
            Object key = itr.next();
            if (vendor.equals(this.getVendor(key))) {
               keys.add(this.getKey(key));
            }
         }

         return (String[])((String[])keys.toArray(new String[keys.size()]));
      } else {
         return new String[0];
      }
   }

   public boolean hasExtension(String key) {
      return this.hasExtension("openjpa", key);
   }

   public boolean hasExtension(String vendor, String key) {
      return this._exts != null && this._exts.containsKey(this.getHashKey(vendor, key));
   }

   public void addExtension(String key, Object value) {
      this.addExtension("openjpa", key, value);
   }

   public void addExtension(String vendor, String key, Object value) {
      if (this._exts == null) {
         this._exts = new HashMap();
      }

      this._exts.put(this.getHashKey(vendor, key), value);
   }

   public boolean removeExtension(String key) {
      return this.removeExtension("openjpa", key);
   }

   public boolean removeExtension(String vendor, String key) {
      if (this._exts != null && this._exts.remove(this.getHashKey(vendor, key)) != null) {
         this.removeEmbeddedExtensions(key);
         return true;
      } else {
         return false;
      }
   }

   public Object getObjectExtension(String key) {
      return this.getObjectExtension("openjpa", key);
   }

   public Object getObjectExtension(String vendor, String key) {
      return this._exts == null ? null : this._exts.get(this.getHashKey(vendor, key));
   }

   public String getStringExtension(String key) {
      return this.getStringExtension("openjpa", key);
   }

   public String getStringExtension(String vendor, String key) {
      Object val = this.getObjectExtension(vendor, key);
      return val == null ? null : val.toString();
   }

   public int getIntExtension(String key) {
      return this.getIntExtension("openjpa", key);
   }

   public int getIntExtension(String vendor, String key) {
      String str = this.getStringExtension(vendor, key);
      return str == null ? 0 : Integer.parseInt(str);
   }

   public double getDoubleExtension(String key) {
      return this.getDoubleExtension("openjpa", key);
   }

   public double getDoubleExtension(String vendor, String key) {
      String str = this.getStringExtension(vendor, key);
      return str == null ? 0.0 : Double.parseDouble(str);
   }

   public boolean getBooleanExtension(String key) {
      return this.getBooleanExtension("openjpa", key);
   }

   public boolean getBooleanExtension(String vendor, String key) {
      String str = this.getStringExtension(vendor, key);
      return str == null ? false : Boolean.valueOf(str);
   }

   public Extensions getEmbeddedExtensions(String key, boolean create) {
      return this.getEmbeddedExtensions("openjpa", key, create);
   }

   public Extensions getEmbeddedExtensions(String vendor, String key, boolean create) {
      if (this._embed == null && !create) {
         return null;
      } else {
         if (this._embed == null) {
            this._embed = new HashMap();
         }

         Object hk = this.getHashKey(vendor, key);
         Extensions exts = (Extensions)this._embed.get(hk);
         if (exts == null && !create) {
            return null;
         } else {
            if (exts == null) {
               exts = new EmbeddedExtensions(this);
               this._embed.put(hk, exts);
               if (this._exts == null) {
                  this._exts = new HashMap();
               }

               if (!this._exts.containsKey(hk)) {
                  this._exts.put(hk, (Object)null);
               }
            }

            return (Extensions)exts;
         }
      }
   }

   public boolean removeEmbeddedExtensions(String key) {
      return this.removeEmbeddedExtensions("openjpa", key);
   }

   public boolean removeEmbeddedExtensions(String vendor, String key) {
      return this._embed != null && this._embed.remove(this.getHashKey(vendor, key)) != null;
   }

   protected void copy(Extensions exts) {
      if (!exts.isEmpty()) {
         Map.Entry entry;
         if (exts._exts != null && !exts._exts.isEmpty()) {
            if (this._exts == null) {
               this._exts = new HashMap();
            }

            Iterator itr = exts._exts.entrySet().iterator();

            while(itr.hasNext()) {
               entry = (Map.Entry)itr.next();
               if (!this._exts.containsKey(entry.getKey())) {
                  this._exts.put(entry.getKey(), entry.getValue());
               }
            }
         }

         if (exts._embed != null && !exts._embed.isEmpty()) {
            if (this._embed == null) {
               this._embed = new HashMap();
            }

            Object embedded;
            for(Iterator itr = exts._embed.entrySet().iterator(); itr.hasNext(); ((Extensions)embedded).copy((Extensions)entry.getValue())) {
               entry = (Map.Entry)itr.next();
               embedded = (Extensions)this._embed.get(entry.getKey());
               if (embedded == null) {
                  embedded = new EmbeddedExtensions(this);
                  this._embed.put(entry.getKey(), embedded);
               }
            }
         }

      }
   }

   public void validateExtensionKeys() {
      if (this._exts != null && !this._exts.isEmpty()) {
         OpenJPAConfiguration conf = this.getRepository().getConfiguration();
         Log log = conf.getLog("openjpa.MetaData");
         if (log.isWarnEnabled()) {
            Collection validNames = new TreeSet();
            this.addExtensionKeys(validNames);
            String prefixes = _loc.get("extension-datastore-prefix").getMessage();
            String[] allowedPrefixes = null;
            if (prefixes != null) {
               allowedPrefixes = Strings.split(prefixes, ",", 0);
            }

            Iterator itr = this._exts.keySet().iterator();

            while(true) {
               String key;
               label54:
               while(true) {
                  do {
                     Object next;
                     do {
                        if (!itr.hasNext()) {
                           return;
                        }

                        next = itr.next();
                     } while(!"openjpa".equals(this.getVendor(next)));

                     key = this.getKey(next);
                  } while(validNames.contains(key));

                  if (allowedPrefixes == null) {
                     break;
                  }

                  int j = 0;

                  while(true) {
                     if (j >= allowedPrefixes.length) {
                        break label54;
                     }

                     if (key.startsWith(allowedPrefixes[j]) && !this.validateDataStoreExtensionPrefix(allowedPrefixes[j])) {
                        break;
                     }

                     ++j;
                  }
               }

               String closestName = StringDistance.getClosestLevenshteinDistance(key, (Collection)validNames, 0.5F);
               if (closestName == null) {
                  log.warn(_loc.get("unrecognized-extension", this, key, validNames));
               } else {
                  log.warn(_loc.get("unrecognized-extension-hint", new Object[]{this, key, validNames, closestName}));
               }
            }
         }
      }
   }

   protected void addExtensionKeys(Collection exts) {
   }

   protected boolean validateDataStoreExtensionPrefix(String prefix) {
      return false;
   }

   public abstract MetaDataRepository getRepository();

   private Object getHashKey(String vendor, String key) {
      return "openjpa".equals(vendor) ? key : new HashKey(vendor, key);
   }

   private String getVendor(Object hashKey) {
      return hashKey instanceof String ? "openjpa" : ((HashKey)hashKey).vendor;
   }

   private String getKey(Object hashKey) {
      return hashKey instanceof String ? (String)hashKey : ((HashKey)hashKey).key;
   }

   private static class EmbeddedExtensions extends Extensions {
      private final Extensions _parent;

      public EmbeddedExtensions(Extensions parent) {
         this._parent = parent;
      }

      public MetaDataRepository getRepository() {
         return this._parent.getRepository();
      }
   }

   private static class HashKey implements Serializable {
      public final String vendor;
      public final String key;

      public HashKey(String vendor, String key) {
         this.vendor = vendor;
         this.key = key;
      }

      public int hashCode() {
         int i = 0;
         if (this.vendor != null) {
            i = this.vendor.hashCode();
         }

         if (this.key != null) {
            i += 17 * this.key.hashCode();
         }

         return i;
      }

      public boolean equals(Object other) {
         if (other == this) {
            return true;
         } else {
            HashKey hk = (HashKey)other;
            return StringUtils.equals(this.vendor, hk.vendor) && StringUtils.equals(this.key, hk.key);
         }
      }
   }
}
