package org.python.icu.impl;

import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.python.icu.util.ULocale;

public class ICULocaleService extends ICUService {
   private ULocale fallbackLocale;
   private String fallbackLocaleName;

   public ICULocaleService() {
   }

   public ICULocaleService(String name) {
      super(name);
   }

   public Object get(ULocale locale) {
      return this.get(locale, -1, (ULocale[])null);
   }

   public Object get(ULocale locale, int kind) {
      return this.get(locale, kind, (ULocale[])null);
   }

   public Object get(ULocale locale, ULocale[] actualReturn) {
      return this.get(locale, -1, actualReturn);
   }

   public Object get(ULocale locale, int kind, ULocale[] actualReturn) {
      ICUService.Key key = this.createKey(locale, kind);
      if (actualReturn == null) {
         return this.getKey(key);
      } else {
         String[] temp = new String[1];
         Object result = this.getKey(key, temp);
         if (result != null) {
            int n = temp[0].indexOf("/");
            if (n >= 0) {
               temp[0] = temp[0].substring(n + 1);
            }

            actualReturn[0] = new ULocale(temp[0]);
         }

         return result;
      }
   }

   public ICUService.Factory registerObject(Object obj, ULocale locale) {
      return this.registerObject(obj, locale, -1, true);
   }

   public ICUService.Factory registerObject(Object obj, ULocale locale, boolean visible) {
      return this.registerObject(obj, locale, -1, visible);
   }

   public ICUService.Factory registerObject(Object obj, ULocale locale, int kind) {
      return this.registerObject(obj, locale, kind, true);
   }

   public ICUService.Factory registerObject(Object obj, ULocale locale, int kind, boolean visible) {
      ICUService.Factory factory = new SimpleLocaleKeyFactory(obj, locale, kind, visible);
      return this.registerFactory(factory);
   }

   public Locale[] getAvailableLocales() {
      Set visIDs = this.getVisibleIDs();
      Locale[] locales = new Locale[visIDs.size()];
      int n = 0;

      Locale loc;
      for(Iterator var4 = visIDs.iterator(); var4.hasNext(); locales[n++] = loc) {
         String id = (String)var4.next();
         loc = LocaleUtility.getLocaleFromName(id);
      }

      return locales;
   }

   public ULocale[] getAvailableULocales() {
      Set visIDs = this.getVisibleIDs();
      ULocale[] locales = new ULocale[visIDs.size()];
      int n = 0;

      String id;
      for(Iterator var4 = visIDs.iterator(); var4.hasNext(); locales[n++] = new ULocale(id)) {
         id = (String)var4.next();
      }

      return locales;
   }

   public String validateFallbackLocale() {
      ULocale loc = ULocale.getDefault();
      if (loc != this.fallbackLocale) {
         synchronized(this) {
            if (loc != this.fallbackLocale) {
               this.fallbackLocale = loc;
               this.fallbackLocaleName = loc.getBaseName();
               this.clearServiceCache();
            }
         }
      }

      return this.fallbackLocaleName;
   }

   public ICUService.Key createKey(String id) {
      return ICULocaleService.LocaleKey.createWithCanonicalFallback(id, this.validateFallbackLocale());
   }

   public ICUService.Key createKey(String id, int kind) {
      return ICULocaleService.LocaleKey.createWithCanonicalFallback(id, this.validateFallbackLocale(), kind);
   }

   public ICUService.Key createKey(ULocale l, int kind) {
      return ICULocaleService.LocaleKey.createWithCanonical(l, this.validateFallbackLocale(), kind);
   }

   public static class ICUResourceBundleFactory extends LocaleKeyFactory {
      protected final String bundleName;

      public ICUResourceBundleFactory() {
         this("org/python/icu/impl/data/icudt59b");
      }

      public ICUResourceBundleFactory(String bundleName) {
         super(true);
         this.bundleName = bundleName;
      }

      protected Set getSupportedIDs() {
         return ICUResourceBundle.getFullLocaleNameSet(this.bundleName, this.loader());
      }

      public void updateVisibleIDs(Map result) {
         Set visibleIDs = ICUResourceBundle.getAvailableLocaleNameSet(this.bundleName, this.loader());
         Iterator var3 = visibleIDs.iterator();

         while(var3.hasNext()) {
            String id = (String)var3.next();
            result.put(id, this);
         }

      }

      protected Object handleCreate(ULocale loc, int kind, ICUService service) {
         return ICUResourceBundle.getBundleInstance(this.bundleName, loc, this.loader());
      }

      protected ClassLoader loader() {
         return ClassLoaderUtil.getClassLoader(this.getClass());
      }

      public String toString() {
         return super.toString() + ", bundle: " + this.bundleName;
      }
   }

   public static class SimpleLocaleKeyFactory extends LocaleKeyFactory {
      private final Object obj;
      private final String id;
      private final int kind;

      public SimpleLocaleKeyFactory(Object obj, ULocale locale, int kind, boolean visible) {
         this(obj, locale, kind, visible, (String)null);
      }

      public SimpleLocaleKeyFactory(Object obj, ULocale locale, int kind, boolean visible, String name) {
         super(visible, name);
         this.obj = obj;
         this.id = locale.getBaseName();
         this.kind = kind;
      }

      public Object create(ICUService.Key key, ICUService service) {
         if (!(key instanceof LocaleKey)) {
            return null;
         } else {
            LocaleKey lkey = (LocaleKey)key;
            if (this.kind != -1 && this.kind != lkey.kind()) {
               return null;
            } else {
               return !this.id.equals(lkey.currentID()) ? null : this.obj;
            }
         }
      }

      protected boolean isSupportedID(String idToCheck) {
         return this.id.equals(idToCheck);
      }

      public void updateVisibleIDs(Map result) {
         if (this.visible) {
            result.put(this.id, this);
         } else {
            result.remove(this.id);
         }

      }

      public String toString() {
         StringBuilder buf = new StringBuilder(super.toString());
         buf.append(", id: ");
         buf.append(this.id);
         buf.append(", kind: ");
         buf.append(this.kind);
         return buf.toString();
      }
   }

   public abstract static class LocaleKeyFactory implements ICUService.Factory {
      protected final String name;
      protected final boolean visible;
      public static final boolean VISIBLE = true;
      public static final boolean INVISIBLE = false;

      protected LocaleKeyFactory(boolean visible) {
         this.visible = visible;
         this.name = null;
      }

      protected LocaleKeyFactory(boolean visible, String name) {
         this.visible = visible;
         this.name = name;
      }

      public Object create(ICUService.Key key, ICUService service) {
         if (this.handlesKey(key)) {
            LocaleKey lkey = (LocaleKey)key;
            int kind = lkey.kind();
            ULocale uloc = lkey.currentLocale();
            return this.handleCreate(uloc, kind, service);
         } else {
            return null;
         }
      }

      protected boolean handlesKey(ICUService.Key key) {
         if (key != null) {
            String id = key.currentID();
            Set supported = this.getSupportedIDs();
            return supported.contains(id);
         } else {
            return false;
         }
      }

      public void updateVisibleIDs(Map result) {
         Set cache = this.getSupportedIDs();
         Iterator var3 = cache.iterator();

         while(var3.hasNext()) {
            String id = (String)var3.next();
            if (this.visible) {
               result.put(id, this);
            } else {
               result.remove(id);
            }
         }

      }

      public String getDisplayName(String id, ULocale locale) {
         if (locale == null) {
            return id;
         } else {
            ULocale loc = new ULocale(id);
            return loc.getDisplayName(locale);
         }
      }

      protected Object handleCreate(ULocale loc, int kind, ICUService service) {
         return null;
      }

      protected boolean isSupportedID(String id) {
         return this.getSupportedIDs().contains(id);
      }

      protected Set getSupportedIDs() {
         return Collections.emptySet();
      }

      public String toString() {
         StringBuilder buf = new StringBuilder(super.toString());
         if (this.name != null) {
            buf.append(", name: ");
            buf.append(this.name);
         }

         buf.append(", visible: ");
         buf.append(this.visible);
         return buf.toString();
      }
   }

   public static class LocaleKey extends ICUService.Key {
      private int kind;
      private int varstart;
      private String primaryID;
      private String fallbackID;
      private String currentID;
      public static final int KIND_ANY = -1;

      public static LocaleKey createWithCanonicalFallback(String primaryID, String canonicalFallbackID) {
         return createWithCanonicalFallback(primaryID, canonicalFallbackID, -1);
      }

      public static LocaleKey createWithCanonicalFallback(String primaryID, String canonicalFallbackID, int kind) {
         if (primaryID == null) {
            return null;
         } else {
            String canonicalPrimaryID = ULocale.getName(primaryID);
            return new LocaleKey(primaryID, canonicalPrimaryID, canonicalFallbackID, kind);
         }
      }

      public static LocaleKey createWithCanonical(ULocale locale, String canonicalFallbackID, int kind) {
         if (locale == null) {
            return null;
         } else {
            String canonicalPrimaryID = locale.getName();
            return new LocaleKey(canonicalPrimaryID, canonicalPrimaryID, canonicalFallbackID, kind);
         }
      }

      protected LocaleKey(String primaryID, String canonicalPrimaryID, String canonicalFallbackID, int kind) {
         super(primaryID);
         this.kind = kind;
         if (canonicalPrimaryID != null && !canonicalPrimaryID.equalsIgnoreCase("root")) {
            int idx = canonicalPrimaryID.indexOf(64);
            if (idx == 4 && canonicalPrimaryID.regionMatches(true, 0, "root", 0, 4)) {
               this.primaryID = canonicalPrimaryID.substring(4);
               this.varstart = 0;
               this.fallbackID = null;
            } else {
               this.primaryID = canonicalPrimaryID;
               this.varstart = idx;
               if (canonicalFallbackID != null && !this.primaryID.equals(canonicalFallbackID)) {
                  this.fallbackID = canonicalFallbackID;
               } else {
                  this.fallbackID = "";
               }
            }
         } else {
            this.primaryID = "";
            this.fallbackID = null;
         }

         this.currentID = this.varstart == -1 ? this.primaryID : this.primaryID.substring(0, this.varstart);
      }

      public String prefix() {
         return this.kind == -1 ? null : Integer.toString(this.kind());
      }

      public int kind() {
         return this.kind;
      }

      public String canonicalID() {
         return this.primaryID;
      }

      public String currentID() {
         return this.currentID;
      }

      public String currentDescriptor() {
         String result = this.currentID();
         if (result != null) {
            StringBuilder buf = new StringBuilder();
            if (this.kind != -1) {
               buf.append(this.prefix());
            }

            buf.append('/');
            buf.append(result);
            if (this.varstart != -1) {
               buf.append(this.primaryID.substring(this.varstart, this.primaryID.length()));
            }

            result = buf.toString();
         }

         return result;
      }

      public ULocale canonicalLocale() {
         return new ULocale(this.primaryID);
      }

      public ULocale currentLocale() {
         return this.varstart == -1 ? new ULocale(this.currentID) : new ULocale(this.currentID + this.primaryID.substring(this.varstart));
      }

      public boolean fallback() {
         int x = this.currentID.lastIndexOf(95);
         if (x == -1) {
            if (this.fallbackID != null) {
               this.currentID = this.fallbackID;
               if (this.fallbackID.length() == 0) {
                  this.fallbackID = null;
               } else {
                  this.fallbackID = "";
               }

               return true;
            } else {
               this.currentID = null;
               return false;
            }
         } else {
            do {
               --x;
            } while(x >= 0 && this.currentID.charAt(x) == '_');

            this.currentID = this.currentID.substring(0, x + 1);
            return true;
         }
      }

      public boolean isFallbackOf(String id) {
         return LocaleUtility.isFallbackOf(this.canonicalID(), id);
      }
   }
}
