package org.python.icu.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;
import org.python.icu.util.ULocale;
import org.python.icu.util.UResourceBundle;
import org.python.icu.util.UResourceBundleIterator;
import org.python.icu.util.UResourceTypeMismatchException;

public class ICUResourceBundle extends UResourceBundle {
   public static final String NO_INHERITANCE_MARKER = "∅∅∅";
   public static final ClassLoader ICU_DATA_CLASS_LOADER = ClassLoaderUtil.getClassLoader(ICUData.class);
   protected static final String INSTALLED_LOCALES = "InstalledLocales";
   WholeBundle wholeBundle;
   private ICUResourceBundle container;
   private static CacheBase BUNDLE_CACHE = new SoftCache() {
      protected ICUResourceBundle createInstance(String unusedKey, Loader loader) {
         return loader.load();
      }
   };
   private static final String ICU_RESOURCE_INDEX = "res_index";
   private static final String DEFAULT_TAG = "default";
   private static final String FULL_LOCALE_NAMES_LIST = "fullLocaleNames.lst";
   private static final boolean DEBUG = ICUDebug.enabled("localedata");
   private static CacheBase GET_AVAILABLE_CACHE = new SoftCache() {
      protected AvailEntry createInstance(String key, ClassLoader loader) {
         return new AvailEntry(key, loader);
      }
   };
   protected String key;
   public static final int RES_BOGUS = -1;
   public static final int ALIAS = 3;
   public static final int TABLE32 = 4;
   public static final int TABLE16 = 5;
   public static final int STRING_V2 = 6;
   public static final int ARRAY16 = 9;
   private static final char RES_PATH_SEP_CHAR = '/';
   private static final String RES_PATH_SEP_STR = "/";
   private static final String ICUDATA = "ICUDATA";
   private static final char HYPHEN = '-';
   private static final String LOCALE = "LOCALE";

   public static final ULocale getFunctionalEquivalent(String baseName, ClassLoader loader, String resName, String keyword, ULocale locID, boolean[] isAvailable, boolean omitDefault) {
      String kwVal = locID.getKeywordValue(keyword);
      String baseLoc = locID.getBaseName();
      String defStr = null;
      ULocale parent = new ULocale(baseLoc);
      ULocale defLoc = null;
      boolean lookForDefault = false;
      ULocale fullBase = null;
      int defDepth = 0;
      int resDepth = 0;
      if (kwVal == null || kwVal.length() == 0 || kwVal.equals("default")) {
         kwVal = "";
         lookForDefault = true;
      }

      ICUResourceBundle r = null;
      r = (ICUResourceBundle)UResourceBundle.getBundleInstance(baseName, parent);
      if (isAvailable != null) {
         isAvailable[0] = false;
         ULocale[] availableULocales = getAvailEntry(baseName, loader).getULocaleList();

         for(int i = 0; i < availableULocales.length; ++i) {
            if (parent.equals(availableULocales[i])) {
               isAvailable[0] = true;
               break;
            }
         }
      }

      ICUResourceBundle irb;
      do {
         try {
            irb = (ICUResourceBundle)r.get(resName);
            defStr = irb.getString("default");
            if (lookForDefault) {
               kwVal = defStr;
               lookForDefault = false;
            }

            defLoc = r.getULocale();
         } catch (MissingResourceException var21) {
         }

         if (defLoc == null) {
            r = r.getParent();
            ++defDepth;
         }
      } while(r != null && defLoc == null);

      parent = new ULocale(baseLoc);
      r = (ICUResourceBundle)UResourceBundle.getBundleInstance(baseName, parent);

      do {
         try {
            irb = (ICUResourceBundle)r.get(resName);
            irb.get(kwVal);
            fullBase = irb.getULocale();
            if (fullBase != null && resDepth > defDepth) {
               defStr = irb.getString("default");
               defLoc = r.getULocale();
               defDepth = resDepth;
            }
         } catch (MissingResourceException var20) {
         }

         if (fullBase == null) {
            r = r.getParent();
            ++resDepth;
         }
      } while(r != null && fullBase == null);

      if (fullBase == null && defStr != null && !defStr.equals(kwVal)) {
         kwVal = defStr;
         parent = new ULocale(baseLoc);
         r = (ICUResourceBundle)UResourceBundle.getBundleInstance(baseName, parent);
         resDepth = 0;

         do {
            try {
               irb = (ICUResourceBundle)r.get(resName);
               ICUResourceBundle urb = (ICUResourceBundle)irb.get(kwVal);
               fullBase = r.getULocale();
               if (!fullBase.getBaseName().equals(urb.getULocale().getBaseName())) {
                  fullBase = null;
               }

               if (fullBase != null && resDepth > defDepth) {
                  defStr = irb.getString("default");
                  defLoc = r.getULocale();
                  defDepth = resDepth;
               }
            } catch (MissingResourceException var19) {
            }

            if (fullBase == null) {
               r = r.getParent();
               ++resDepth;
            }
         } while(r != null && fullBase == null);
      }

      if (fullBase == null) {
         throw new MissingResourceException("Could not find locale containing requested or default keyword.", baseName, keyword + "=" + kwVal);
      } else {
         return omitDefault && defStr.equals(kwVal) && resDepth <= defDepth ? fullBase : new ULocale(fullBase.getBaseName() + "@" + keyword + "=" + kwVal);
      }
   }

   public static final String[] getKeywordValues(String baseName, String keyword) {
      Set keywords = new HashSet();
      ULocale[] locales = getAvailEntry(baseName, ICU_DATA_CLASS_LOADER).getULocaleList();

      for(int i = 0; i < locales.length; ++i) {
         try {
            UResourceBundle b = UResourceBundle.getBundleInstance(baseName, locales[i]);
            ICUResourceBundle irb = (ICUResourceBundle)((ICUResourceBundle)b.getObject(keyword));
            Enumeration e = irb.getKeys();

            while(e.hasMoreElements()) {
               String s = (String)e.nextElement();
               if (!"default".equals(s) && !s.startsWith("private-")) {
                  keywords.add(s);
               }
            }
         } catch (Throwable var9) {
         }
      }

      return (String[])keywords.toArray(new String[0]);
   }

   public ICUResourceBundle getWithFallback(String path) throws MissingResourceException {
      ICUResourceBundle result = findResourceWithFallback(path, this, (UResourceBundle)null);
      if (result == null) {
         throw new MissingResourceException("Can't find resource for bundle " + this.getClass().getName() + ", key " + this.getType(), path, this.getKey());
      } else if (result.getType() == 0 && result.getString().equals("∅∅∅")) {
         throw new MissingResourceException("Encountered NO_INHERITANCE_MARKER", path, this.getKey());
      } else {
         return result;
      }
   }

   public ICUResourceBundle at(int index) {
      return (ICUResourceBundle)this.handleGet(index, (HashMap)null, this);
   }

   public ICUResourceBundle at(String key) {
      return this instanceof ICUResourceBundleImpl.ResourceTable ? (ICUResourceBundle)this.handleGet(key, (HashMap)null, this) : null;
   }

   public ICUResourceBundle findTopLevel(int index) {
      return (ICUResourceBundle)super.findTopLevel(index);
   }

   public ICUResourceBundle findTopLevel(String aKey) {
      return (ICUResourceBundle)super.findTopLevel(aKey);
   }

   public ICUResourceBundle findWithFallback(String path) {
      return findResourceWithFallback(path, this, (UResourceBundle)null);
   }

   public String findStringWithFallback(String path) {
      return findStringWithFallback(path, this, (UResourceBundle)null);
   }

   public String getStringWithFallback(String path) throws MissingResourceException {
      String result = findStringWithFallback(path, this, (UResourceBundle)null);
      if (result == null) {
         throw new MissingResourceException("Can't find resource for bundle " + this.getClass().getName() + ", key " + this.getType(), path, this.getKey());
      } else if (result.equals("∅∅∅")) {
         throw new MissingResourceException("Encountered NO_INHERITANCE_MARKER", path, this.getKey());
      } else {
         return result;
      }
   }

   public void getAllItemsWithFallback(String path, UResource.Sink sink) throws MissingResourceException {
      int numPathKeys = countPathKeys(path);
      ICUResourceBundle rb;
      if (numPathKeys == 0) {
         rb = this;
      } else {
         int depth = this.getResDepth();
         String[] pathKeys = new String[depth + numPathKeys];
         getResPathKeys(path, numPathKeys, pathKeys, depth);
         rb = findResourceWithFallback(pathKeys, depth, this, (UResourceBundle)null);
         if (rb == null) {
            throw new MissingResourceException("Can't find resource for bundle " + this.getClass().getName() + ", key " + this.getType(), path, this.getKey());
         }
      }

      UResource.Key key = new UResource.Key();
      ICUResourceBundleReader.ReaderValue readerValue = new ICUResourceBundleReader.ReaderValue();
      rb.getAllItemsWithFallback(key, readerValue, sink);
   }

   private void getAllItemsWithFallback(UResource.Key key, ICUResourceBundleReader.ReaderValue readerValue, UResource.Sink sink) {
      ICUResourceBundleImpl impl = (ICUResourceBundleImpl)this;
      readerValue.reader = impl.wholeBundle.reader;
      readerValue.res = impl.getResource();
      key.setString(this.key != null ? this.key : "");
      sink.put(key, readerValue, this.parent == null);
      if (this.parent != null) {
         ICUResourceBundle parentBundle = (ICUResourceBundle)this.parent;
         int depth = this.getResDepth();
         ICUResourceBundle rb;
         if (depth == 0) {
            rb = parentBundle;
         } else {
            String[] pathKeys = new String[depth];
            this.getResPathKeys(pathKeys, depth);
            rb = findResourceWithFallback(pathKeys, 0, parentBundle, (UResourceBundle)null);
         }

         if (rb != null) {
            rb.getAllItemsWithFallback(key, readerValue, sink);
         }
      }

   }

   public static Set getAvailableLocaleNameSet(String bundlePrefix, ClassLoader loader) {
      return getAvailEntry(bundlePrefix, loader).getLocaleNameSet();
   }

   public static Set getFullLocaleNameSet() {
      return getFullLocaleNameSet("org/python/icu/impl/data/icudt59b", ICU_DATA_CLASS_LOADER);
   }

   public static Set getFullLocaleNameSet(String bundlePrefix, ClassLoader loader) {
      return getAvailEntry(bundlePrefix, loader).getFullLocaleNameSet();
   }

   public static Set getAvailableLocaleNameSet() {
      return getAvailableLocaleNameSet("org/python/icu/impl/data/icudt59b", ICU_DATA_CLASS_LOADER);
   }

   public static final ULocale[] getAvailableULocales(String baseName, ClassLoader loader) {
      return getAvailEntry(baseName, loader).getULocaleList();
   }

   public static final ULocale[] getAvailableULocales() {
      return getAvailableULocales("org/python/icu/impl/data/icudt59b", ICU_DATA_CLASS_LOADER);
   }

   public static final Locale[] getAvailableLocales(String baseName, ClassLoader loader) {
      return getAvailEntry(baseName, loader).getLocaleList();
   }

   public static final Locale[] getAvailableLocales() {
      return getAvailEntry("org/python/icu/impl/data/icudt59b", ICU_DATA_CLASS_LOADER).getLocaleList();
   }

   public static final Locale[] getLocaleList(ULocale[] ulocales) {
      ArrayList list = new ArrayList(ulocales.length);
      HashSet uniqueSet = new HashSet();

      for(int i = 0; i < ulocales.length; ++i) {
         Locale loc = ulocales[i].toLocale();
         if (!uniqueSet.contains(loc)) {
            list.add(loc);
            uniqueSet.add(loc);
         }
      }

      return (Locale[])list.toArray(new Locale[list.size()]);
   }

   public Locale getLocale() {
      return this.getULocale().toLocale();
   }

   private static final ULocale[] createULocaleList(String baseName, ClassLoader root) {
      ICUResourceBundle bundle = (ICUResourceBundle)UResourceBundle.instantiateBundle(baseName, "res_index", root, true);
      bundle = (ICUResourceBundle)bundle.get("InstalledLocales");
      int length = bundle.getSize();
      int i = 0;
      ULocale[] locales = new ULocale[length];
      UResourceBundleIterator iter = bundle.getIterator();
      iter.reset();

      while(iter.hasNext()) {
         String locstr = iter.next().getKey();
         if (locstr.equals("root")) {
            locales[i++] = ULocale.ROOT;
         } else {
            locales[i++] = new ULocale(locstr);
         }
      }

      bundle = null;
      return locales;
   }

   private static final void addLocaleIDsFromIndexBundle(String baseName, ClassLoader root, Set locales) {
      ICUResourceBundle bundle;
      try {
         bundle = (ICUResourceBundle)UResourceBundle.instantiateBundle(baseName, "res_index", root, true);
         bundle = (ICUResourceBundle)bundle.get("InstalledLocales");
      } catch (MissingResourceException var6) {
         if (DEBUG) {
            System.out.println("couldn't find " + baseName + '/' + "res_index" + ".res");
            Thread.dumpStack();
         }

         return;
      }

      UResourceBundleIterator iter = bundle.getIterator();
      iter.reset();

      while(iter.hasNext()) {
         String locstr = iter.next().getKey();
         locales.add(locstr);
      }

   }

   private static final void addBundleBaseNamesFromClassLoader(final String bn, final ClassLoader root, final Set names) {
      AccessController.doPrivileged(new PrivilegedAction() {
         public Void run() {
            try {
               Enumeration urls = root.getResources(bn);
               if (urls == null) {
                  return null;
               }

               URLHandler.URLVisitor v = new URLHandler.URLVisitor() {
                  public void visit(String s) {
                     if (s.endsWith(".res")) {
                        String locstr = s.substring(0, s.length() - 4);
                        names.add(locstr);
                     }

                  }
               };

               while(urls.hasMoreElements()) {
                  URL url = (URL)urls.nextElement();
                  URLHandler handler = URLHandler.get(url);
                  if (handler != null) {
                     handler.guide(v, false);
                  } else if (ICUResourceBundle.DEBUG) {
                     System.out.println("handler for " + url + " is null");
                  }
               }
            } catch (IOException var5) {
               if (ICUResourceBundle.DEBUG) {
                  System.out.println("ouch: " + var5.getMessage());
               }
            }

            return null;
         }
      });
   }

   private static void addLocaleIDsFromListFile(String bn, ClassLoader root, Set locales) {
      try {
         InputStream s = root.getResourceAsStream(bn + "fullLocaleNames.lst");
         if (s != null) {
            BufferedReader br = new BufferedReader(new InputStreamReader(s, "ASCII"));

            String line;
            try {
               while((line = br.readLine()) != null) {
                  if (line.length() != 0 && !line.startsWith("#")) {
                     locales.add(line);
                  }
               }
            } finally {
               br.close();
            }
         }
      } catch (IOException var10) {
      }

   }

   private static Set createFullLocaleNameSet(String baseName, ClassLoader loader) {
      String bn = baseName.endsWith("/") ? baseName : baseName + "/";
      Set set = new HashSet();
      String skipScan = ICUConfig.get("org.python.icu.impl.ICUResourceBundle.skipRuntimeLocaleResourceScan", "false");
      if (!skipScan.equalsIgnoreCase("true")) {
         addBundleBaseNamesFromClassLoader(bn, loader, set);
         if (baseName.startsWith("org/python/icu/impl/data/icudt59b")) {
            String folder;
            if (baseName.length() == "org/python/icu/impl/data/icudt59b".length()) {
               folder = "";
            } else if (baseName.charAt("org/python/icu/impl/data/icudt59b".length()) == '/') {
               folder = baseName.substring("org/python/icu/impl/data/icudt59b".length() + 1);
            } else {
               folder = null;
            }

            if (folder != null) {
               ICUBinary.addBaseNamesInFileFolder(folder, ".res", set);
            }
         }

         set.remove("res_index");
         Iterator iter = set.iterator();

         label53:
         while(true) {
            String name;
            do {
               if (!iter.hasNext()) {
                  break label53;
               }

               name = (String)iter.next();
            } while(name.length() != 1 && name.length() <= 3);

            if (name.indexOf(95) < 0) {
               iter.remove();
            }
         }
      }

      if (set.isEmpty()) {
         if (DEBUG) {
            System.out.println("unable to enumerate data files in " + baseName);
         }

         addLocaleIDsFromListFile(bn, loader, set);
      }

      if (set.isEmpty()) {
         addLocaleIDsFromIndexBundle(baseName, loader, set);
      }

      set.remove("root");
      set.add(ULocale.ROOT.toString());
      return Collections.unmodifiableSet(set);
   }

   private static Set createLocaleNameSet(String baseName, ClassLoader loader) {
      HashSet set = new HashSet();
      addLocaleIDsFromIndexBundle(baseName, loader, set);
      return Collections.unmodifiableSet(set);
   }

   private static AvailEntry getAvailEntry(String key, ClassLoader loader) {
      return (AvailEntry)GET_AVAILABLE_CACHE.getInstance(key, loader);
   }

   private static final ICUResourceBundle findResourceWithFallback(String path, UResourceBundle actualBundle, UResourceBundle requested) {
      if (path.length() == 0) {
         return null;
      } else {
         ICUResourceBundle base = (ICUResourceBundle)actualBundle;
         int depth = base.getResDepth();
         int numPathKeys = countPathKeys(path);

         assert numPathKeys > 0;

         String[] keys = new String[depth + numPathKeys];
         getResPathKeys(path, numPathKeys, keys, depth);
         return findResourceWithFallback(keys, depth, base, requested);
      }
   }

   private static final ICUResourceBundle findResourceWithFallback(String[] keys, int depth, ICUResourceBundle base, UResourceBundle requested) {
      if (requested == null) {
         requested = base;
      }

      while(true) {
         while(true) {
            String subKey = keys[depth++];
            ICUResourceBundle sub = (ICUResourceBundle)base.handleGet(subKey, (HashMap)null, (UResourceBundle)requested);
            if (sub == null) {
               --depth;
               ICUResourceBundle nextBase = base.getParent();
               if (nextBase == null) {
                  return null;
               }

               int baseDepth = base.getResDepth();
               if (depth != baseDepth) {
                  String[] newKeys = new String[baseDepth + (keys.length - depth)];
                  System.arraycopy(keys, depth, newKeys, baseDepth, keys.length - depth);
                  keys = newKeys;
               }

               base.getResPathKeys(keys, baseDepth);
               base = nextBase;
               depth = 0;
            } else {
               if (depth == keys.length) {
                  return sub;
               }

               base = sub;
            }
         }
      }
   }

   private static final String findStringWithFallback(String path, UResourceBundle actualBundle, UResourceBundle requested) {
      if (path.length() == 0) {
         return null;
      } else if (!(actualBundle instanceof ICUResourceBundleImpl.ResourceContainer)) {
         return null;
      } else {
         if (requested == null) {
            requested = actualBundle;
         }

         ICUResourceBundle base = (ICUResourceBundle)actualBundle;
         ICUResourceBundleReader reader = base.wholeBundle.reader;
         int res = -1;
         int baseDepth = base.getResDepth();
         int depth = baseDepth;
         int numPathKeys = countPathKeys(path);

         assert numPathKeys > 0;

         String[] keys = new String[baseDepth + numPathKeys];
         getResPathKeys(path, numPathKeys, keys, baseDepth);

         while(true) {
            while(true) {
               label82: {
                  int type;
                  Object readerContainer;
                  if (res == -1) {
                     type = base.getType();
                     if (type != 2 && type != 8) {
                        break label82;
                     }

                     readerContainer = ((ICUResourceBundleImpl.ResourceContainer)base).value;
                  } else {
                     type = ICUResourceBundleReader.RES_GET_TYPE(res);
                     if (ICUResourceBundleReader.URES_IS_TABLE(type)) {
                        readerContainer = reader.getTable(res);
                     } else {
                        if (!ICUResourceBundleReader.URES_IS_ARRAY(type)) {
                           res = -1;
                           break label82;
                        }

                        readerContainer = reader.getArray(res);
                     }
                  }

                  String subKey = keys[depth++];
                  res = ((ICUResourceBundleReader.Container)readerContainer).getResource(reader, subKey);
                  if (res != -1) {
                     ICUResourceBundle sub;
                     if (ICUResourceBundleReader.RES_GET_TYPE(res) == 3) {
                        base.getResPathKeys(keys, baseDepth);
                        sub = getAliasedResource(base, keys, depth, subKey, res, (HashMap)null, requested);
                     } else {
                        sub = null;
                     }

                     if (depth == keys.length) {
                        if (sub != null) {
                           return sub.getString();
                        }

                        String s = reader.getString(res);
                        if (s == null) {
                           throw new UResourceTypeMismatchException("");
                        }

                        return s;
                     }

                     if (sub != null) {
                        base = sub;
                        reader = sub.wholeBundle.reader;
                        res = -1;
                        baseDepth = sub.getResDepth();
                        if (depth != baseDepth) {
                           String[] newKeys = new String[baseDepth + (keys.length - depth)];
                           System.arraycopy(keys, depth, newKeys, baseDepth, keys.length - depth);
                           keys = newKeys;
                           depth = baseDepth;
                        }
                     }
                     continue;
                  }

                  --depth;
               }

               ICUResourceBundle nextBase = base.getParent();
               if (nextBase == null) {
                  return null;
               }

               base.getResPathKeys(keys, baseDepth);
               base = nextBase;
               reader = nextBase.wholeBundle.reader;
               baseDepth = 0;
               depth = 0;
            }
         }
      }
   }

   private int getResDepth() {
      return this.container == null ? 0 : this.container.getResDepth() + 1;
   }

   private void getResPathKeys(String[] keys, int depth) {
      ICUResourceBundle b = this;

      do {
         if (depth <= 0) {
            return;
         }

         --depth;
         keys[depth] = b.key;
         b = b.container;
      } while($assertionsDisabled || depth == 0 == (b.container == null));

      throw new AssertionError();
   }

   private static int countPathKeys(String path) {
      if (path.isEmpty()) {
         return 0;
      } else {
         int num = 1;

         for(int i = 0; i < path.length(); ++i) {
            if (path.charAt(i) == '/') {
               ++num;
            }
         }

         return num;
      }
   }

   private static void getResPathKeys(String path, int num, String[] keys, int start) {
      if (num != 0) {
         if (num == 1) {
            keys[start] = path;
         } else {
            int i = 0;

            while(true) {
               int j = path.indexOf(47, i);

               assert j >= i;

               keys[start++] = path.substring(i, j);
               if (num == 2) {
                  assert path.indexOf(47, j + 1) < 0;

                  keys[start] = path.substring(j + 1);
                  return;
               }

               i = j + 1;
               --num;
            }
         }
      }
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else {
         if (other instanceof ICUResourceBundle) {
            ICUResourceBundle o = (ICUResourceBundle)other;
            if (this.getBaseName().equals(o.getBaseName()) && this.getLocaleID().equals(o.getLocaleID())) {
               return true;
            }
         }

         return false;
      }
   }

   public int hashCode() {
      assert false : "hashCode not designed";

      return 42;
   }

   public static ICUResourceBundle getBundleInstance(String baseName, String localeID, ClassLoader root, boolean disableFallback) {
      return getBundleInstance(baseName, localeID, root, disableFallback ? ICUResourceBundle.OpenType.DIRECT : ICUResourceBundle.OpenType.LOCALE_DEFAULT_ROOT);
   }

   public static ICUResourceBundle getBundleInstance(String baseName, ULocale locale, OpenType openType) {
      if (locale == null) {
         locale = ULocale.getDefault();
      }

      return getBundleInstance(baseName, locale.getBaseName(), ICU_DATA_CLASS_LOADER, openType);
   }

   public static ICUResourceBundle getBundleInstance(String baseName, String localeID, ClassLoader root, OpenType openType) {
      if (baseName == null) {
         baseName = "org/python/icu/impl/data/icudt59b";
      }

      localeID = ULocale.getBaseName(localeID);
      ICUResourceBundle b;
      if (openType == ICUResourceBundle.OpenType.LOCALE_DEFAULT_ROOT) {
         b = instantiateBundle(baseName, localeID, ULocale.getDefault().getBaseName(), root, openType);
      } else {
         b = instantiateBundle(baseName, localeID, (String)null, root, openType);
      }

      if (b == null) {
         throw new MissingResourceException("Could not find the bundle " + baseName + "/" + localeID + ".res", "", "");
      } else {
         return b;
      }
   }

   private static boolean localeIDStartsWithLangSubtag(String localeID, String lang) {
      return localeID.startsWith(lang) && (localeID.length() == lang.length() || localeID.charAt(lang.length()) == '_');
   }

   private static ICUResourceBundle instantiateBundle(final String baseName, final String localeID, final String defaultID, final ClassLoader root, final OpenType openType) {
      assert localeID.indexOf(64) < 0;

      assert defaultID == null || defaultID.indexOf(64) < 0;

      final String fullName = ICUResourceBundleReader.getFullName(baseName, localeID);
      char openTypeChar = (char)(48 + openType.ordinal());
      String cacheKey = openType != ICUResourceBundle.OpenType.LOCALE_DEFAULT_ROOT ? fullName + '#' + openTypeChar : fullName + '#' + openTypeChar + '#' + defaultID;
      return (ICUResourceBundle)BUNDLE_CACHE.getInstance(cacheKey, new Loader() {
         public ICUResourceBundle load() {
            if (ICUResourceBundle.DEBUG) {
               System.out.println("Creating " + fullName);
            }

            String rootLocale = baseName.indexOf(46) == -1 ? "root" : "";
            String localeName = localeID.isEmpty() ? rootLocale : localeID;
            ICUResourceBundle b = ICUResourceBundle.createBundle(baseName, localeName, root);
            if (ICUResourceBundle.DEBUG) {
               System.out.println("The bundle created is: " + b + " and openType=" + openType + " and bundle.getNoFallback=" + (b != null && b.getNoFallback()));
            }

            if (openType != ICUResourceBundle.OpenType.DIRECT && (b == null || !b.getNoFallback())) {
               if (b == null) {
                  int ix = localeName.lastIndexOf(95);
                  if (ix != -1) {
                     String temp = localeName.substring(0, ix);
                     b = ICUResourceBundle.instantiateBundle(baseName, temp, defaultID, root, openType);
                  } else if (openType == ICUResourceBundle.OpenType.LOCALE_DEFAULT_ROOT && !ICUResourceBundle.localeIDStartsWithLangSubtag(defaultID, localeName)) {
                     b = ICUResourceBundle.instantiateBundle(baseName, defaultID, defaultID, root, openType);
                  } else if (openType != ICUResourceBundle.OpenType.LOCALE_ONLY && !rootLocale.isEmpty()) {
                     b = ICUResourceBundle.createBundle(baseName, rootLocale, root);
                  }
               } else {
                  UResourceBundle parent = null;
                  localeName = b.getLocaleID();
                  int i = localeName.lastIndexOf(95);
                  String parentLocaleName = ((ICUResourceBundleImpl.ResourceTable)b).findString("%%Parent");
                  if (parentLocaleName != null) {
                     parent = ICUResourceBundle.instantiateBundle(baseName, parentLocaleName, defaultID, root, openType);
                  } else if (i != -1) {
                     parent = ICUResourceBundle.instantiateBundle(baseName, localeName.substring(0, i), defaultID, root, openType);
                  } else if (!localeName.equals(rootLocale)) {
                     parent = ICUResourceBundle.instantiateBundle(baseName, rootLocale, defaultID, root, openType);
                  }

                  if (!b.equals(parent)) {
                     b.setParent(parent);
                  }
               }

               return b;
            } else {
               return b;
            }
         }
      });
   }

   ICUResourceBundle get(String aKey, HashMap aliasesVisited, UResourceBundle requested) {
      ICUResourceBundle obj = (ICUResourceBundle)this.handleGet(aKey, aliasesVisited, requested);
      if (obj == null) {
         obj = this.getParent();
         if (obj != null) {
            obj = obj.get(aKey, aliasesVisited, requested);
         }

         if (obj == null) {
            String fullName = ICUResourceBundleReader.getFullName(this.getBaseName(), this.getLocaleID());
            throw new MissingResourceException("Can't find resource for bundle " + fullName + ", key " + aKey, this.getClass().getName(), aKey);
         }
      }

      return obj;
   }

   public static ICUResourceBundle createBundle(String baseName, String localeID, ClassLoader root) {
      ICUResourceBundleReader reader = ICUResourceBundleReader.getReader(baseName, localeID, root);
      return reader == null ? null : getBundle(reader, baseName, localeID, root);
   }

   protected String getLocaleID() {
      return this.wholeBundle.localeID;
   }

   protected String getBaseName() {
      return this.wholeBundle.baseName;
   }

   public ULocale getULocale() {
      return this.wholeBundle.ulocale;
   }

   public boolean isRoot() {
      return this.wholeBundle.localeID.isEmpty() || this.wholeBundle.localeID.equals("root");
   }

   public ICUResourceBundle getParent() {
      return (ICUResourceBundle)this.parent;
   }

   protected void setParent(ResourceBundle parent) {
      this.parent = parent;
   }

   public String getKey() {
      return this.key;
   }

   private boolean getNoFallback() {
      return this.wholeBundle.reader.getNoFallback();
   }

   private static ICUResourceBundle getBundle(ICUResourceBundleReader reader, String baseName, String localeID, ClassLoader loader) {
      int rootRes = reader.getRootResource();
      if (ICUResourceBundleReader.URES_IS_TABLE(ICUResourceBundleReader.RES_GET_TYPE(rootRes))) {
         WholeBundle wb = new WholeBundle(baseName, localeID, loader, reader);
         ICUResourceBundleImpl.ResourceTable rootTable = new ICUResourceBundleImpl.ResourceTable(wb, rootRes);
         String aliasString = rootTable.findString("%%ALIAS");
         return (ICUResourceBundle)(aliasString != null ? (ICUResourceBundle)UResourceBundle.getBundleInstance(baseName, aliasString) : rootTable);
      } else {
         throw new IllegalStateException("Invalid format error");
      }
   }

   protected ICUResourceBundle(WholeBundle wholeBundle) {
      this.wholeBundle = wholeBundle;
   }

   protected ICUResourceBundle(ICUResourceBundle container, String key) {
      this.key = key;
      this.wholeBundle = container.wholeBundle;
      this.container = container;
      this.parent = container.parent;
   }

   protected static ICUResourceBundle getAliasedResource(ICUResourceBundle base, String[] keys, int depth, String key, int _resource, HashMap aliasesVisited, UResourceBundle requested) {
      WholeBundle wholeBundle = base.wholeBundle;
      ClassLoader loaderToUse = wholeBundle.loader;
      String keyPath = null;
      String rpath = wholeBundle.reader.getAlias(_resource);
      if (aliasesVisited == null) {
         aliasesVisited = new HashMap();
      }

      if (aliasesVisited.get(rpath) != null) {
         throw new IllegalArgumentException("Circular references in the resource bundles");
      } else {
         aliasesVisited.put(rpath, "");
         int i;
         String bundleName;
         String locale;
         int numKeys;
         if (rpath.indexOf(47) == 0) {
            i = rpath.indexOf(47, 1);
            int j = rpath.indexOf(47, i + 1);
            bundleName = rpath.substring(1, i);
            if (j < 0) {
               locale = rpath.substring(i + 1);
            } else {
               locale = rpath.substring(i + 1, j);
               keyPath = rpath.substring(j + 1, rpath.length());
            }

            if (bundleName.equals("ICUDATA")) {
               bundleName = "org/python/icu/impl/data/icudt59b";
               loaderToUse = ICU_DATA_CLASS_LOADER;
            } else if (bundleName.indexOf("ICUDATA") > -1) {
               numKeys = bundleName.indexOf(45);
               if (numKeys > -1) {
                  bundleName = "org/python/icu/impl/data/icudt59b/" + bundleName.substring(numKeys + 1, bundleName.length());
                  loaderToUse = ICU_DATA_CLASS_LOADER;
               }
            }
         } else {
            i = rpath.indexOf(47);
            if (i != -1) {
               locale = rpath.substring(0, i);
               keyPath = rpath.substring(i + 1);
            } else {
               locale = rpath;
            }

            bundleName = wholeBundle.baseName;
         }

         ICUResourceBundle bundle = null;
         ICUResourceBundle sub = null;
         if (bundleName.equals("LOCALE")) {
            bundleName = wholeBundle.baseName;
            keyPath = rpath.substring("LOCALE".length() + 2, rpath.length());

            for(bundle = (ICUResourceBundle)requested; bundle.container != null; bundle = bundle.container) {
            }

            sub = findResourceWithFallback(keyPath, bundle, (UResourceBundle)null);
         } else {
            bundle = getBundleInstance(bundleName, locale, loaderToUse, false);
            if (keyPath != null) {
               numKeys = countPathKeys(keyPath);
               if (numKeys > 0) {
                  keys = new String[numKeys];
                  getResPathKeys(keyPath, numKeys, keys, 0);
               }
            } else if (keys != null) {
               numKeys = depth;
            } else {
               depth = base.getResDepth();
               numKeys = depth + 1;
               keys = new String[numKeys];
               base.getResPathKeys(keys, depth);
               keys[depth] = key;
            }

            if (numKeys > 0) {
               sub = bundle;

               for(int i = 0; sub != null && i < numKeys; ++i) {
                  sub = sub.get(keys[i], aliasesVisited, requested);
               }
            }
         }

         if (sub == null) {
            throw new MissingResourceException(wholeBundle.localeID, wholeBundle.baseName, key);
         } else {
            return sub;
         }
      }
   }

   /** @deprecated */
   @Deprecated
   public final Set getTopLevelKeySet() {
      return this.wholeBundle.topLevelKeys;
   }

   /** @deprecated */
   @Deprecated
   public final void setTopLevelKeySet(Set keySet) {
      this.wholeBundle.topLevelKeys = keySet;
   }

   protected Enumeration handleGetKeys() {
      return Collections.enumeration(this.handleKeySet());
   }

   protected boolean isTopLevelResource() {
      return this.container == null;
   }

   public static enum OpenType {
      LOCALE_DEFAULT_ROOT,
      LOCALE_ROOT,
      LOCALE_ONLY,
      DIRECT;
   }

   private static final class AvailEntry {
      private String prefix;
      private ClassLoader loader;
      private volatile ULocale[] ulocales;
      private volatile Locale[] locales;
      private volatile Set nameSet;
      private volatile Set fullNameSet;

      AvailEntry(String prefix, ClassLoader loader) {
         this.prefix = prefix;
         this.loader = loader;
      }

      ULocale[] getULocaleList() {
         if (this.ulocales == null) {
            synchronized(this) {
               if (this.ulocales == null) {
                  this.ulocales = ICUResourceBundle.createULocaleList(this.prefix, this.loader);
               }
            }
         }

         return this.ulocales;
      }

      Locale[] getLocaleList() {
         if (this.locales == null) {
            this.getULocaleList();
            synchronized(this) {
               if (this.locales == null) {
                  this.locales = ICUResourceBundle.getLocaleList(this.ulocales);
               }
            }
         }

         return this.locales;
      }

      Set getLocaleNameSet() {
         if (this.nameSet == null) {
            synchronized(this) {
               if (this.nameSet == null) {
                  this.nameSet = ICUResourceBundle.createLocaleNameSet(this.prefix, this.loader);
               }
            }
         }

         return this.nameSet;
      }

      Set getFullLocaleNameSet() {
         if (this.fullNameSet == null) {
            synchronized(this) {
               if (this.fullNameSet == null) {
                  this.fullNameSet = ICUResourceBundle.createFullLocaleNameSet(this.prefix, this.loader);
               }
            }
         }

         return this.fullNameSet;
      }
   }

   private abstract static class Loader {
      private Loader() {
      }

      abstract ICUResourceBundle load();

      // $FF: synthetic method
      Loader(Object x0) {
         this();
      }
   }

   protected static final class WholeBundle {
      String baseName;
      String localeID;
      ULocale ulocale;
      ClassLoader loader;
      ICUResourceBundleReader reader;
      Set topLevelKeys;

      WholeBundle(String baseName, String localeID, ClassLoader loader, ICUResourceBundleReader reader) {
         this.baseName = baseName;
         this.localeID = localeID;
         this.ulocale = new ULocale(localeID);
         this.loader = loader;
         this.reader = reader;
      }
   }
}
