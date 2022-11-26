package org.python.icu.util;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import org.python.icu.impl.ICUResourceBundle;
import org.python.icu.impl.ICUResourceBundleReader;
import org.python.icu.impl.ResourceBundleWrapper;

public abstract class UResourceBundle extends ResourceBundle {
   private static Map ROOT_CACHE = new ConcurrentHashMap();
   public static final int NONE = -1;
   public static final int STRING = 0;
   public static final int BINARY = 1;
   public static final int TABLE = 2;
   public static final int INT = 7;
   public static final int ARRAY = 8;
   public static final int INT_VECTOR = 14;

   public static UResourceBundle getBundleInstance(String baseName, String localeName) {
      return getBundleInstance(baseName, localeName, ICUResourceBundle.ICU_DATA_CLASS_LOADER, false);
   }

   public static UResourceBundle getBundleInstance(String baseName, String localeName, ClassLoader root) {
      return getBundleInstance(baseName, localeName, root, false);
   }

   protected static UResourceBundle getBundleInstance(String baseName, String localeName, ClassLoader root, boolean disableFallback) {
      return instantiateBundle(baseName, localeName, root, disableFallback);
   }

   public static UResourceBundle getBundleInstance(ULocale locale) {
      if (locale == null) {
         locale = ULocale.getDefault();
      }

      return getBundleInstance("org/python/icu/impl/data/icudt59b", locale.getBaseName(), ICUResourceBundle.ICU_DATA_CLASS_LOADER, false);
   }

   public static UResourceBundle getBundleInstance(String baseName) {
      if (baseName == null) {
         baseName = "org/python/icu/impl/data/icudt59b";
      }

      ULocale uloc = ULocale.getDefault();
      return getBundleInstance(baseName, uloc.getBaseName(), ICUResourceBundle.ICU_DATA_CLASS_LOADER, false);
   }

   public static UResourceBundle getBundleInstance(String baseName, Locale locale) {
      if (baseName == null) {
         baseName = "org/python/icu/impl/data/icudt59b";
      }

      ULocale uloc = locale == null ? ULocale.getDefault() : ULocale.forLocale(locale);
      return getBundleInstance(baseName, uloc.getBaseName(), ICUResourceBundle.ICU_DATA_CLASS_LOADER, false);
   }

   public static UResourceBundle getBundleInstance(String baseName, ULocale locale) {
      if (baseName == null) {
         baseName = "org/python/icu/impl/data/icudt59b";
      }

      if (locale == null) {
         locale = ULocale.getDefault();
      }

      return getBundleInstance(baseName, locale.getBaseName(), ICUResourceBundle.ICU_DATA_CLASS_LOADER, false);
   }

   public static UResourceBundle getBundleInstance(String baseName, Locale locale, ClassLoader loader) {
      if (baseName == null) {
         baseName = "org/python/icu/impl/data/icudt59b";
      }

      ULocale uloc = locale == null ? ULocale.getDefault() : ULocale.forLocale(locale);
      return getBundleInstance(baseName, uloc.getBaseName(), loader, false);
   }

   public static UResourceBundle getBundleInstance(String baseName, ULocale locale, ClassLoader loader) {
      if (baseName == null) {
         baseName = "org/python/icu/impl/data/icudt59b";
      }

      if (locale == null) {
         locale = ULocale.getDefault();
      }

      return getBundleInstance(baseName, locale.getBaseName(), loader, false);
   }

   public abstract ULocale getULocale();

   protected abstract String getLocaleID();

   protected abstract String getBaseName();

   protected abstract UResourceBundle getParent();

   public Locale getLocale() {
      return this.getULocale().toLocale();
   }

   private static RootType getRootType(String baseName, ClassLoader root) {
      RootType rootType = (RootType)ROOT_CACHE.get(baseName);
      if (rootType == null) {
         String rootLocale = baseName.indexOf(46) == -1 ? "root" : "";

         try {
            ICUResourceBundle.getBundleInstance(baseName, rootLocale, root, true);
            rootType = UResourceBundle.RootType.ICU;
         } catch (MissingResourceException var7) {
            try {
               ResourceBundleWrapper.getBundleInstance(baseName, rootLocale, root, true);
               rootType = UResourceBundle.RootType.JAVA;
            } catch (MissingResourceException var6) {
               rootType = UResourceBundle.RootType.MISSING;
            }
         }

         ROOT_CACHE.put(baseName, rootType);
      }

      return rootType;
   }

   private static void setRootType(String baseName, RootType rootType) {
      ROOT_CACHE.put(baseName, rootType);
   }

   protected static UResourceBundle instantiateBundle(String baseName, String localeName, ClassLoader root, boolean disableFallback) {
      RootType rootType = getRootType(baseName, root);
      switch (rootType) {
         case ICU:
            return ICUResourceBundle.getBundleInstance(baseName, localeName, root, disableFallback);
         case JAVA:
            return ResourceBundleWrapper.getBundleInstance(baseName, localeName, root, disableFallback);
         case MISSING:
         default:
            Object b;
            try {
               b = ICUResourceBundle.getBundleInstance(baseName, localeName, root, disableFallback);
               setRootType(baseName, UResourceBundle.RootType.ICU);
            } catch (MissingResourceException var7) {
               b = ResourceBundleWrapper.getBundleInstance(baseName, localeName, root, disableFallback);
               setRootType(baseName, UResourceBundle.RootType.JAVA);
            }

            return (UResourceBundle)b;
      }
   }

   public ByteBuffer getBinary() {
      throw new UResourceTypeMismatchException("");
   }

   public String getString() {
      throw new UResourceTypeMismatchException("");
   }

   public String[] getStringArray() {
      throw new UResourceTypeMismatchException("");
   }

   public byte[] getBinary(byte[] ba) {
      throw new UResourceTypeMismatchException("");
   }

   public int[] getIntVector() {
      throw new UResourceTypeMismatchException("");
   }

   public int getInt() {
      throw new UResourceTypeMismatchException("");
   }

   public int getUInt() {
      throw new UResourceTypeMismatchException("");
   }

   public UResourceBundle get(String aKey) {
      UResourceBundle obj = this.findTopLevel(aKey);
      if (obj == null) {
         String fullName = ICUResourceBundleReader.getFullName(this.getBaseName(), this.getLocaleID());
         throw new MissingResourceException("Can't find resource for bundle " + fullName + ", key " + aKey, this.getClass().getName(), aKey);
      } else {
         return obj;
      }
   }

   /** @deprecated */
   @Deprecated
   protected UResourceBundle findTopLevel(String aKey) {
      for(UResourceBundle res = this; res != null; res = res.getParent()) {
         UResourceBundle obj = res.handleGet(aKey, (HashMap)null, this);
         if (obj != null) {
            return obj;
         }
      }

      return null;
   }

   public String getString(int index) {
      ICUResourceBundle temp = (ICUResourceBundle)this.get(index);
      if (temp.getType() == 0) {
         return temp.getString();
      } else {
         throw new UResourceTypeMismatchException("");
      }
   }

   public UResourceBundle get(int index) {
      UResourceBundle obj = this.handleGet(index, (HashMap)null, this);
      if (obj == null) {
         obj = this.getParent();
         if (obj != null) {
            obj = obj.get(index);
         }

         if (obj == null) {
            throw new MissingResourceException("Can't find resource for bundle " + this.getClass().getName() + ", key " + this.getKey(), this.getClass().getName(), this.getKey());
         }
      }

      return obj;
   }

   /** @deprecated */
   @Deprecated
   protected UResourceBundle findTopLevel(int index) {
      for(UResourceBundle res = this; res != null; res = res.getParent()) {
         UResourceBundle obj = res.handleGet(index, (HashMap)null, this);
         if (obj != null) {
            return obj;
         }
      }

      return null;
   }

   public Enumeration getKeys() {
      return Collections.enumeration(this.keySet());
   }

   /** @deprecated */
   @Deprecated
   public Set keySet() {
      Set keys = null;
      ICUResourceBundle icurb = null;
      if (this.isTopLevelResource() && this instanceof ICUResourceBundle) {
         icurb = (ICUResourceBundle)this;
         keys = icurb.getTopLevelKeySet();
      }

      if (keys == null) {
         if (!this.isTopLevelResource()) {
            return this.handleKeySet();
         }

         TreeSet newKeySet;
         if (this.parent == null) {
            newKeySet = new TreeSet();
         } else if (this.parent instanceof UResourceBundle) {
            newKeySet = new TreeSet(((UResourceBundle)this.parent).keySet());
         } else {
            newKeySet = new TreeSet();
            Enumeration parentKeys = this.parent.getKeys();

            while(parentKeys.hasMoreElements()) {
               newKeySet.add(parentKeys.nextElement());
            }
         }

         newKeySet.addAll(this.handleKeySet());
         keys = Collections.unmodifiableSet(newKeySet);
         if (icurb != null) {
            icurb.setTopLevelKeySet(keys);
         }
      }

      return keys;
   }

   /** @deprecated */
   @Deprecated
   protected Set handleKeySet() {
      return Collections.emptySet();
   }

   public int getSize() {
      return 1;
   }

   public int getType() {
      return -1;
   }

   public VersionInfo getVersion() {
      return null;
   }

   public UResourceBundleIterator getIterator() {
      return new UResourceBundleIterator(this);
   }

   public String getKey() {
      return null;
   }

   protected UResourceBundle handleGet(String aKey, HashMap aliasesVisited, UResourceBundle requested) {
      return null;
   }

   protected UResourceBundle handleGet(int index, HashMap aliasesVisited, UResourceBundle requested) {
      return null;
   }

   protected String[] handleGetStringArray() {
      return null;
   }

   protected Enumeration handleGetKeys() {
      return null;
   }

   protected Object handleGetObject(String aKey) {
      return this.handleGetObjectImpl(aKey, this);
   }

   private Object handleGetObjectImpl(String aKey, UResourceBundle requested) {
      Object obj = this.resolveObject(aKey, requested);
      if (obj == null) {
         UResourceBundle parentBundle = this.getParent();
         if (parentBundle != null) {
            obj = parentBundle.handleGetObjectImpl(aKey, requested);
         }

         if (obj == null) {
            throw new MissingResourceException("Can't find resource for bundle " + this.getClass().getName() + ", key " + aKey, this.getClass().getName(), aKey);
         }
      }

      return obj;
   }

   private Object resolveObject(String aKey, UResourceBundle requested) {
      if (this.getType() == 0) {
         return this.getString();
      } else {
         UResourceBundle obj = this.handleGet(aKey, (HashMap)null, requested);
         if (obj != null) {
            if (obj.getType() == 0) {
               return obj.getString();
            }

            try {
               if (obj.getType() == 8) {
                  return obj.handleGetStringArray();
               }
            } catch (UResourceTypeMismatchException var5) {
               return obj;
            }
         }

         return obj;
      }
   }

   /** @deprecated */
   @Deprecated
   protected boolean isTopLevelResource() {
      return true;
   }

   private static enum RootType {
      MISSING,
      ICU,
      JAVA;
   }
}
