package org.apache.openjpa.lib.conf;

import java.io.File;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.JavaVersions;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.util.Services;

public class ProductDerivations {
   private static final Localizer _loc = Localizer.forPackage(ProductDerivations.class);
   private static final ProductDerivation[] _derivations;
   private static final String[] _derivationNames;
   private static final Throwable[] _derivationErrors;
   private static String[] _prefixes;

   public static ProductDerivation[] getProductDerivations() {
      return _derivations;
   }

   public static String[] getConfigurationPrefixes() {
      return _prefixes;
   }

   static void setConfigurationPrefixes(String[] prefixes) {
      _prefixes = prefixes;
   }

   public static String getConfigurationKey(String partialKey, Map map) {
      String firstKey = null;

      for(int i = 0; map != null && i < _prefixes.length; ++i) {
         String fullKey = _prefixes[i] + "." + partialKey;
         if (map.containsKey(fullKey)) {
            if (firstKey != null) {
               throw new IllegalStateException(_loc.get("dup-with-different-prefixes", firstKey, fullKey).getMessage());
            }

            firstKey = fullKey;
         }
      }

      return firstKey == null ? _prefixes[0] + "." + partialKey : firstKey;
   }

   public static void beforeConfigurationConstruct(ConfigurationProvider cp) {
      for(int i = 0; i < _derivations.length; ++i) {
         try {
            _derivations[i].beforeConfigurationConstruct(cp);
         } catch (BootstrapException var3) {
            if (var3.isFatal()) {
               throw var3;
            }
         } catch (Exception var4) {
            var4.printStackTrace();
         }
      }

   }

   public static void beforeConfigurationLoad(Configuration conf) {
      for(int i = 0; i < _derivations.length; ++i) {
         try {
            _derivations[i].beforeConfigurationLoad(conf);
         } catch (BootstrapException var3) {
            if (var3.isFatal()) {
               throw var3;
            }
         } catch (Exception var4) {
            var4.printStackTrace();
         }
      }

   }

   public static void afterSpecificationSet(Configuration conf) {
      for(int i = 0; i < _derivations.length; ++i) {
         try {
            _derivations[i].afterSpecificationSet(conf);
         } catch (BootstrapException var3) {
            if (var3.isFatal()) {
               throw var3;
            }
         } catch (Exception var4) {
            var4.printStackTrace();
         }
      }

   }

   public static void beforeClose(Configuration conf) {
      for(int i = 0; i < _derivations.length; ++i) {
         try {
            _derivations[i].beforeConfigurationClose(conf);
         } catch (Exception var3) {
            conf.getConfigurationLog().warn(_loc.get("before-close-ex"), var3);
         }
      }

   }

   public static ConfigurationProvider load(String resource, String anchor, ClassLoader loader) {
      if (StringUtils.isEmpty(resource)) {
         return null;
      } else {
         if (loader == null) {
            loader = (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getContextClassLoaderAction());
         }

         ConfigurationProvider provider = null;
         StringBuffer errs = null;
         Throwable err = null;

         for(int i = _derivations.length - 1; i >= 0; --i) {
            try {
               provider = _derivations[i].load(resource, anchor, loader);
               if (provider != null) {
                  return provider;
               }
            } catch (Throwable var8) {
               err = var8;
               errs = errs == null ? new StringBuffer() : errs.append("\n");
               errs.append(_derivations[i].getClass().getName() + ":" + var8);
            }
         }

         reportErrors(errs, resource, err);
         String rsrc = resource + "#" + anchor;
         throw (MissingResourceException)JavaVersions.initCause(new MissingResourceException(rsrc, ProductDerivations.class.getName(), rsrc), err);
      }
   }

   public static ConfigurationProvider load(File file, String anchor, ClassLoader loader) {
      if (file == null) {
         return null;
      } else {
         if (loader == null) {
            loader = (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getContextClassLoaderAction());
         }

         ConfigurationProvider provider = null;
         StringBuffer errs = null;
         Throwable err = null;

         for(int i = _derivations.length - 1; i >= 0; --i) {
            try {
               provider = _derivations[i].load(file, anchor);
               if (provider != null) {
                  return provider;
               }
            } catch (Throwable var8) {
               err = var8;
               errs = errs == null ? new StringBuffer() : errs.append("\n");
               errs.append(_derivations[i].getClass().getName() + ":" + var8);
            }
         }

         String aPath = (String)AccessController.doPrivileged(J2DoPrivHelper.getAbsolutePathAction(file));
         reportErrors(errs, aPath, err);
         String rsrc = aPath + "#" + anchor;
         throw (MissingResourceException)JavaVersions.initCause(new MissingResourceException(rsrc, ProductDerivations.class.getName(), rsrc), err);
      }
   }

   public static ConfigurationProvider loadDefaults(ClassLoader loader) {
      return load(loader, false);
   }

   public static ConfigurationProvider loadGlobals(ClassLoader loader) {
      return load(loader, true);
   }

   private static ConfigurationProvider load(ClassLoader loader, boolean globals) {
      if (loader == null) {
         loader = (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getContextClassLoaderAction());
      }

      ConfigurationProvider provider = null;
      StringBuffer errs = null;
      String type = globals ? "globals" : "defaults";
      Throwable err = null;

      for(int i = _derivations.length - 1; i >= 0; --i) {
         try {
            provider = globals ? _derivations[i].loadGlobals(loader) : _derivations[i].loadDefaults(loader);
            if (provider != null) {
               return provider;
            }
         } catch (Throwable var8) {
            err = var8;
            errs = errs == null ? new StringBuffer() : errs.append("\n");
            errs.append(_derivations[i].getClass().getName() + ":" + var8);
         }
      }

      reportErrors(errs, type, err);
      return null;
   }

   private static void reportErrors(StringBuffer errs, String resource, Throwable nested) {
      if (errs != null) {
         throw (MissingResourceException)JavaVersions.initCause(new MissingResourceException(errs.toString(), ProductDerivations.class.getName(), resource), nested);
      }
   }

   public static List getFullyQualifiedAnchorsInPropertiesLocation(String propertiesLocation) {
      List fqAnchors = new ArrayList();
      StringBuffer errs = null;
      Throwable err = null;

      for(int i = _derivations.length - 1; i >= 0; --i) {
         try {
            if (propertiesLocation == null) {
               String loc = _derivations[i].getDefaultResourceLocation();
               addAll(fqAnchors, loc, _derivations[i].getAnchorsInResource(loc));
            } else {
               File f = new File(propertiesLocation);
               if ((Boolean)J2DoPrivHelper.isFileAction(f).run()) {
                  addAll(fqAnchors, propertiesLocation, _derivations[i].getAnchorsInFile(f));
               } else {
                  f = new File("META-INF" + File.separatorChar + propertiesLocation);
                  if ((Boolean)J2DoPrivHelper.isFileAction(f).run()) {
                     addAll(fqAnchors, propertiesLocation, _derivations[i].getAnchorsInFile(f));
                  } else {
                     addAll(fqAnchors, propertiesLocation, _derivations[i].getAnchorsInResource(propertiesLocation));
                  }
               }
            }
         } catch (Throwable var6) {
            err = var6;
            errs = errs == null ? new StringBuffer() : errs.append("\n");
            errs.append(_derivations[i].getClass().getName() + ":" + var6);
         }
      }

      reportErrors(errs, propertiesLocation, err);
      return fqAnchors;
   }

   private static void addAll(Collection collection, String base, Collection newMembers) {
      if (newMembers != null && collection != null) {
         Iterator iter = newMembers.iterator();

         while(iter.hasNext()) {
            String fqLoc = base + "#" + iter.next();
            if (!collection.contains(fqLoc)) {
               collection.add(fqLoc);
            }
         }

      }
   }

   public static void main(String[] args) {
      System.err.println(derivationErrorsToString());
   }

   private static String derivationErrorsToString() {
      StringBuffer buf = new StringBuffer();
      buf.append("ProductDerivations: ").append(_derivationNames.length);

      for(int i = 0; i < _derivationNames.length; ++i) {
         buf.append("\n").append(i + 1).append(". ").append(_derivationNames[i]).append(": ");
         if (_derivationErrors[i] == null) {
            buf.append("OK");
         } else {
            buf.append(_derivationErrors[i].toString());
         }
      }

      return buf.toString();
   }

   static {
      ClassLoader l = (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getClassLoaderAction(ProductDerivation.class));
      _derivationNames = Services.getImplementors(ProductDerivation.class, l);
      _derivationErrors = new Throwable[_derivationNames.length];
      List derivations = new ArrayList(_derivationNames.length);

      int i;
      for(i = 0; i < _derivationNames.length; ++i) {
         try {
            ProductDerivation d = (ProductDerivation)AccessController.doPrivileged(J2DoPrivHelper.newInstanceAction(Class.forName(_derivationNames[i], true, l)));
            d.validate();
            derivations.add(d);
         } catch (Throwable var5) {
            Throwable t = var5;
            if (var5 instanceof PrivilegedActionException) {
               t = ((PrivilegedActionException)var5).getException();
            }

            _derivationErrors[i] = (Throwable)t;
         }
      }

      if (derivations.isEmpty()) {
         throw new MissingResourceException(_loc.get("no-product-derivations", ProductDerivation.class.getName(), derivationErrorsToString()).getMessage(), ProductDerivations.class.getName(), "derivations");
      } else {
         for(i = 0; i < _derivationErrors.length; ++i) {
            if (_derivationErrors[i] != null) {
               System.err.println(_loc.get("bad-product-derivations", (Object)ProductDerivations.class.getName()));
               break;
            }
         }

         Collections.sort(derivations, new ProductDerivationComparator());
         _derivations = (ProductDerivation[])((ProductDerivation[])derivations.toArray(new ProductDerivation[derivations.size()]));
         List prefixes = new ArrayList(2);

         for(int i = 0; i < _derivations.length; ++i) {
            if (_derivations[i].getConfigurationPrefix() != null && !"openjpa".equals(_derivations[i].getConfigurationPrefix())) {
               prefixes.add(_derivations[i].getConfigurationPrefix());
            }
         }

         String[] prefixArray = new String[1 + prefixes.size()];
         prefixArray[0] = "openjpa";

         for(int i = 0; i < prefixes.size(); ++i) {
            prefixArray[i + 1] = (String)prefixes.get(i);
         }

         setConfigurationPrefixes(prefixArray);
      }
   }

   private static class ProductDerivationComparator implements Comparator {
      private ProductDerivationComparator() {
      }

      public int compare(Object o1, Object o2) {
         int type1 = ((ProductDerivation)o1).getType();
         int type2 = ((ProductDerivation)o2).getType();
         return type1 != type2 ? type1 - type2 : o1.getClass().getName().compareTo(o2.getClass().getName());
      }

      // $FF: synthetic method
      ProductDerivationComparator(Object x0) {
         this();
      }
   }
}
