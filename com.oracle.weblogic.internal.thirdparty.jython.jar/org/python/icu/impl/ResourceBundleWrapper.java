package org.python.icu.impl;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import org.python.icu.util.ULocale;
import org.python.icu.util.UResourceBundle;

public final class ResourceBundleWrapper extends UResourceBundle {
   private ResourceBundle bundle;
   private String localeID;
   private String baseName;
   private List keys;
   private static CacheBase BUNDLE_CACHE = new SoftCache() {
      protected ResourceBundleWrapper createInstance(String unusedKey, Loader loader) {
         return loader.load();
      }
   };
   private static final boolean DEBUG = ICUDebug.enabled("resourceBundleWrapper");

   private ResourceBundleWrapper(ResourceBundle bundle) {
      this.bundle = null;
      this.localeID = null;
      this.baseName = null;
      this.keys = null;
      this.bundle = bundle;
   }

   protected Object handleGetObject(String aKey) {
      ResourceBundleWrapper current = this;
      Object obj = null;

      while(current != null) {
         try {
            obj = current.bundle.getObject(aKey);
            break;
         } catch (MissingResourceException var5) {
            current = (ResourceBundleWrapper)current.getParent();
         }
      }

      if (obj == null) {
         throw new MissingResourceException("Can't find resource for bundle " + this.baseName + ", key " + aKey, this.getClass().getName(), aKey);
      } else {
         return obj;
      }
   }

   public Enumeration getKeys() {
      return Collections.enumeration(this.keys);
   }

   private void initKeysVector() {
      ResourceBundleWrapper current = this;

      for(this.keys = new ArrayList(); current != null; current = (ResourceBundleWrapper)current.getParent()) {
         Enumeration e = current.bundle.getKeys();

         while(e.hasMoreElements()) {
            String elem = (String)e.nextElement();
            if (!this.keys.contains(elem)) {
               this.keys.add(elem);
            }
         }
      }

   }

   protected String getLocaleID() {
      return this.localeID;
   }

   protected String getBaseName() {
      return this.bundle.getClass().getName().replace('.', '/');
   }

   public ULocale getULocale() {
      return new ULocale(this.localeID);
   }

   public UResourceBundle getParent() {
      return (UResourceBundle)this.parent;
   }

   public static ResourceBundleWrapper getBundleInstance(String baseName, String localeID, ClassLoader root, boolean disableFallback) {
      if (root == null) {
         root = ClassLoaderUtil.getClassLoader();
      }

      ResourceBundleWrapper b;
      if (disableFallback) {
         b = instantiateBundle(baseName, localeID, (String)null, root, disableFallback);
      } else {
         b = instantiateBundle(baseName, localeID, ULocale.getDefault().getBaseName(), root, disableFallback);
      }

      if (b == null) {
         String separator = "_";
         if (baseName.indexOf(47) >= 0) {
            separator = "/";
         }

         throw new MissingResourceException("Could not find the bundle " + baseName + separator + localeID, "", "");
      } else {
         return b;
      }
   }

   private static boolean localeIDStartsWithLangSubtag(String localeID, String lang) {
      return localeID.startsWith(lang) && (localeID.length() == lang.length() || localeID.charAt(lang.length()) == '_');
   }

   private static ResourceBundleWrapper instantiateBundle(final String baseName, final String localeID, final String defaultID, final ClassLoader root, final boolean disableFallback) {
      final String name = localeID.isEmpty() ? baseName : baseName + '_' + localeID;
      String cacheKey = disableFallback ? name : name + '#' + defaultID;
      return (ResourceBundleWrapper)BUNDLE_CACHE.getInstance(cacheKey, new Loader() {
         public ResourceBundleWrapper load() {
            ResourceBundleWrapper parent = null;
            int i = localeID.lastIndexOf(95);
            boolean loadFromProperties = false;
            boolean parentIsRoot = false;
            if (i != -1) {
               String locName = localeID.substring(0, i);
               parent = ResourceBundleWrapper.instantiateBundle(baseName, locName, defaultID, root, disableFallback);
            } else if (!localeID.isEmpty()) {
               parent = ResourceBundleWrapper.instantiateBundle(baseName, "", defaultID, root, disableFallback);
               parentIsRoot = true;
            }

            ResourceBundleWrapper b = null;

            try {
               Class cls = root.loadClass(name).asSubclass(ResourceBundle.class);
               ResourceBundle bx = (ResourceBundle)cls.newInstance();
               b = new ResourceBundleWrapper(bx);
               if (parent != null) {
                  b.setParent(parent);
               }

               b.baseName = baseName;
               b.localeID = localeID;
            } catch (ClassNotFoundException var24) {
               loadFromProperties = true;
            } catch (NoClassDefFoundError var25) {
               loadFromProperties = true;
            } catch (Exception var26) {
               if (ResourceBundleWrapper.DEBUG) {
                  System.out.println("failure");
               }

               if (ResourceBundleWrapper.DEBUG) {
                  System.out.println(var26);
               }
            }

            if (loadFromProperties) {
               try {
                  final String resName = name.replace('.', '/') + ".properties";
                  InputStream stream = (InputStream)AccessController.doPrivileged(new PrivilegedAction() {
                     public InputStream run() {
                        return root.getResourceAsStream(resName);
                     }
                  });
                  if (stream != null) {
                     InputStream streamx = new BufferedInputStream(stream);

                     try {
                        b = new ResourceBundleWrapper(new PropertyResourceBundle(streamx));
                        if (parent != null) {
                           b.setParent(parent);
                        }

                        b.baseName = baseName;
                        b.localeID = localeID;
                     } catch (Exception var21) {
                     } finally {
                        try {
                           streamx.close();
                        } catch (Exception var20) {
                        }

                     }
                  }

                  if (b == null && !disableFallback && !localeID.isEmpty() && localeID.indexOf(95) < 0 && !ResourceBundleWrapper.localeIDStartsWithLangSubtag(defaultID, localeID)) {
                     b = ResourceBundleWrapper.instantiateBundle(baseName, defaultID, defaultID, root, disableFallback);
                  }

                  if (b == null && (!parentIsRoot || !disableFallback)) {
                     b = parent;
                  }
               } catch (Exception var23) {
                  if (ResourceBundleWrapper.DEBUG) {
                     System.out.println("failure");
                  }

                  if (ResourceBundleWrapper.DEBUG) {
                     System.out.println(var23);
                  }
               }
            }

            if (b != null) {
               b.initKeysVector();
            } else if (ResourceBundleWrapper.DEBUG) {
               System.out.println("Returning null for " + baseName + "_" + localeID);
            }

            return b;
         }
      });
   }

   // $FF: synthetic method
   ResourceBundleWrapper(ResourceBundle x0, Object x1) {
      this(x0);
   }

   private abstract static class Loader {
      private Loader() {
      }

      abstract ResourceBundleWrapper load();

      // $FF: synthetic method
      Loader(Object x0) {
         this();
      }
   }
}
