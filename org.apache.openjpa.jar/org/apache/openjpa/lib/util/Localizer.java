package org.apache.openjpa.lib.util;

import java.security.AccessController;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class Localizer {
   private static final Map _localizers = new ConcurrentHashMap();
   private static final Collection _providers = new CopyOnWriteArraySet(Arrays.asList(new SimpleResourceBundleProvider(), new StreamResourceBundleProvider(), new ZipResourceBundleProvider()));
   private String _file;
   private String _pkg;
   private ResourceBundle _bundle = null;
   private Locale _locale;
   private ClassLoader _loader;

   public static Localizer forPackage(Class cls) {
      return forPackage(cls, (Locale)null);
   }

   public static Localizer forPackage(Class cls, Locale locale) {
      if (locale == null) {
         locale = Locale.getDefault();
      }

      int dot = cls == null ? -1 : cls.getName().lastIndexOf(46);
      String pkg;
      String file;
      if (dot == -1) {
         pkg = "";
         file = "localizer";
      } else {
         pkg = cls.getName().substring(0, dot);
         file = pkg + ".localizer";
      }

      String key = file + locale.toString();
      Localizer loc = (Localizer)_localizers.get(key);
      if (loc != null) {
         return loc;
      } else {
         loc = new Localizer(pkg, file, locale, cls == null ? null : (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getClassLoaderAction(cls)));
         _localizers.put(key, loc);
         return loc;
      }
   }

   public static void addProvider(ResourceBundleProvider provider) {
      _providers.add(provider);
   }

   public static boolean removeProvider(ResourceBundleProvider provider) {
      return _providers.remove(provider);
   }

   private Localizer(String pkg, String f, Locale locale, ClassLoader loader) {
      this._pkg = pkg;
      this._file = f;
      this._locale = locale;
      this._loader = loader;
   }

   private ResourceBundle getBundle() {
      if (this._bundle == null) {
         for(Iterator itr = _providers.iterator(); itr.hasNext() && this._bundle == null; this._bundle = ((ResourceBundleProvider)itr.next()).findResource(this._file, this._locale, this._loader)) {
         }
      }

      return this._bundle;
   }

   public Message get(String key) {
      return this.get(key, (Object[])null);
   }

   public Message getFatal(String key) {
      return this.getFatal(key, (Object[])null);
   }

   public Message get(String key, Object sub) {
      return this.get(key, new Object[]{sub});
   }

   public Message getFatal(String key, Object sub) {
      return this.getFatal(key, new Object[]{sub});
   }

   public Message get(String key, Object sub1, Object sub2) {
      return this.get(key, new Object[]{sub1, sub2});
   }

   public Message getFatal(String key, Object sub1, Object sub2) {
      return this.getFatal(key, new Object[]{sub1, sub2});
   }

   public Message get(String key, Object sub1, Object sub2, Object sub3) {
      return this.get(key, new Object[]{sub1, sub2, sub3});
   }

   public Message get(String key, Object[] subs) {
      return new Message(this._pkg, this.getBundle(), key, subs, false);
   }

   public Message getFatal(String key, Object[] subs) {
      return new Message(this._pkg, this.getBundle(), key, subs, true);
   }

   public static class Message {
      private final String _pkg;
      private final String _key;
      private final Object[] _subs;
      private final String _localizedMessage;

      private Message(String packageName, ResourceBundle bundle, String key, Object[] subs, boolean fatal) {
         if (bundle == null && fatal) {
            throw new MissingResourceException(key, key, key);
         } else {
            this._pkg = packageName;
            this._key = key;
            this._subs = subs;
            if (bundle == null) {
               this._localizedMessage = key;
            } else {
               String localized = null;

               try {
                  localized = bundle.getString(key);
               } catch (MissingResourceException var8) {
                  if (fatal) {
                     throw var8;
                  }
               }

               this._localizedMessage = localized == null ? key : localized;
            }

         }
      }

      public String getMessage() {
         return MessageFormat.format(this._localizedMessage, this._subs);
      }

      public String getKey() {
         return this._key;
      }

      public Object[] getSubstitutions() {
         return this._subs;
      }

      public String getPackageName() {
         return this._pkg;
      }

      public String toString() {
         return this.getMessage();
      }

      // $FF: synthetic method
      Message(String x0, ResourceBundle x1, String x2, Object[] x3, boolean x4, Object x5) {
         this(x0, x1, x2, x3, x4);
      }
   }
}
