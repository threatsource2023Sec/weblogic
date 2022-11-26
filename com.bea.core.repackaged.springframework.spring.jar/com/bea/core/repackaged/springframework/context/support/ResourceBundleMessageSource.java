package com.bea.core.repackaged.springframework.context.support;

import com.bea.core.repackaged.springframework.beans.factory.BeanClassLoaderAware;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ResourceBundleMessageSource extends AbstractResourceBasedMessageSource implements BeanClassLoaderAware {
   @Nullable
   private ClassLoader bundleClassLoader;
   @Nullable
   private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();
   private final Map cachedResourceBundles = new ConcurrentHashMap();
   private final Map cachedBundleMessageFormats = new ConcurrentHashMap();
   @Nullable
   private volatile MessageSourceControl control = new MessageSourceControl();

   public ResourceBundleMessageSource() {
      this.setDefaultEncoding("ISO-8859-1");
   }

   public void setBundleClassLoader(ClassLoader classLoader) {
      this.bundleClassLoader = classLoader;
   }

   @Nullable
   protected ClassLoader getBundleClassLoader() {
      return this.bundleClassLoader != null ? this.bundleClassLoader : this.beanClassLoader;
   }

   public void setBeanClassLoader(ClassLoader classLoader) {
      this.beanClassLoader = classLoader;
   }

   protected String resolveCodeWithoutArguments(String code, Locale locale) {
      Set basenames = this.getBasenameSet();
      Iterator var4 = basenames.iterator();

      while(var4.hasNext()) {
         String basename = (String)var4.next();
         ResourceBundle bundle = this.getResourceBundle(basename, locale);
         if (bundle != null) {
            String result = this.getStringOrNull(bundle, code);
            if (result != null) {
               return result;
            }
         }
      }

      return null;
   }

   @Nullable
   protected MessageFormat resolveCode(String code, Locale locale) {
      Set basenames = this.getBasenameSet();
      Iterator var4 = basenames.iterator();

      while(var4.hasNext()) {
         String basename = (String)var4.next();
         ResourceBundle bundle = this.getResourceBundle(basename, locale);
         if (bundle != null) {
            MessageFormat messageFormat = this.getMessageFormat(bundle, code, locale);
            if (messageFormat != null) {
               return messageFormat;
            }
         }
      }

      return null;
   }

   @Nullable
   protected ResourceBundle getResourceBundle(String basename, Locale locale) {
      if (this.getCacheMillis() >= 0L) {
         return this.doGetBundle(basename, locale);
      } else {
         Map localeMap = (Map)this.cachedResourceBundles.get(basename);
         ResourceBundle bundle;
         if (localeMap != null) {
            bundle = (ResourceBundle)((Map)localeMap).get(locale);
            if (bundle != null) {
               return bundle;
            }
         }

         try {
            bundle = this.doGetBundle(basename, locale);
            if (localeMap == null) {
               localeMap = new ConcurrentHashMap();
               Map existing = (Map)this.cachedResourceBundles.putIfAbsent(basename, localeMap);
               if (existing != null) {
                  localeMap = existing;
               }
            }

            ((Map)localeMap).put(locale, bundle);
            return bundle;
         } catch (MissingResourceException var6) {
            if (this.logger.isWarnEnabled()) {
               this.logger.warn("ResourceBundle [" + basename + "] not found for MessageSource: " + var6.getMessage());
            }

            return null;
         }
      }
   }

   protected ResourceBundle doGetBundle(String basename, Locale locale) throws MissingResourceException {
      ClassLoader classLoader = this.getBundleClassLoader();
      Assert.state(classLoader != null, "No bundle ClassLoader set");
      MessageSourceControl control = this.control;
      if (control != null) {
         try {
            return ResourceBundle.getBundle(basename, locale, classLoader, control);
         } catch (UnsupportedOperationException var7) {
            this.control = null;
            String encoding = this.getDefaultEncoding();
            if (encoding != null && this.logger.isInfoEnabled()) {
               this.logger.info("ResourceBundleMessageSource is configured to read resources with encoding '" + encoding + "' but ResourceBundle.Control not supported in current system environment: " + var7.getMessage() + " - falling back to plain ResourceBundle.getBundle retrieval with the platform default encoding. Consider setting the 'defaultEncoding' property to 'null' for participating in the platform default and therefore avoiding this log message.");
            }
         }
      }

      return ResourceBundle.getBundle(basename, locale, classLoader);
   }

   protected ResourceBundle loadBundle(Reader reader) throws IOException {
      return new PropertyResourceBundle(reader);
   }

   protected ResourceBundle loadBundle(InputStream inputStream) throws IOException {
      return new PropertyResourceBundle(inputStream);
   }

   @Nullable
   protected MessageFormat getMessageFormat(ResourceBundle bundle, String code, Locale locale) throws MissingResourceException {
      Map codeMap = (Map)this.cachedBundleMessageFormats.get(bundle);
      Map localeMap = null;
      if (codeMap != null) {
         localeMap = (Map)((Map)codeMap).get(code);
         if (localeMap != null) {
            MessageFormat result = (MessageFormat)((Map)localeMap).get(locale);
            if (result != null) {
               return result;
            }
         }
      }

      String msg = this.getStringOrNull(bundle, code);
      if (msg != null) {
         Map existing;
         if (codeMap == null) {
            codeMap = new ConcurrentHashMap();
            existing = (Map)this.cachedBundleMessageFormats.putIfAbsent(bundle, codeMap);
            if (existing != null) {
               codeMap = existing;
            }
         }

         if (localeMap == null) {
            localeMap = new ConcurrentHashMap();
            existing = (Map)((Map)codeMap).putIfAbsent(code, localeMap);
            if (existing != null) {
               localeMap = existing;
            }
         }

         MessageFormat result = this.createMessageFormat(msg, locale);
         ((Map)localeMap).put(locale, result);
         return result;
      } else {
         return null;
      }
   }

   @Nullable
   protected String getStringOrNull(ResourceBundle bundle, String key) {
      if (bundle.containsKey(key)) {
         try {
            return bundle.getString(key);
         } catch (MissingResourceException var4) {
         }
      }

      return null;
   }

   public String toString() {
      return this.getClass().getName() + ": basenames=" + this.getBasenameSet();
   }

   private class MessageSourceControl extends ResourceBundle.Control {
      private MessageSourceControl() {
      }

      @Nullable
      public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload) throws IllegalAccessException, InstantiationException, IOException {
         if (format.equals("java.properties")) {
            String bundleName = this.toBundleName(baseName, locale);
            String resourceName = this.toResourceName(bundleName, "properties");
            ClassLoader classLoader = loader;
            boolean reloadFlag = reload;

            InputStream inputStream;
            try {
               inputStream = (InputStream)AccessController.doPrivileged(() -> {
                  InputStream is = null;
                  if (reloadFlag) {
                     URL url = classLoader.getResource(resourceName);
                     if (url != null) {
                        URLConnection connection = url.openConnection();
                        if (connection != null) {
                           connection.setUseCaches(false);
                           is = connection.getInputStream();
                        }
                     }
                  } else {
                     is = classLoader.getResourceAsStream(resourceName);
                  }

                  return is;
               });
            } catch (PrivilegedActionException var41) {
               throw (IOException)var41.getException();
            }

            if (inputStream == null) {
               return null;
            } else {
               String encoding = ResourceBundleMessageSource.this.getDefaultEncoding();
               Throwable var13;
               ResourceBundle var14;
               if (encoding != null) {
                  InputStreamReader bundleReader = new InputStreamReader(inputStream, encoding);
                  var13 = null;

                  try {
                     var14 = ResourceBundleMessageSource.this.loadBundle((Reader)bundleReader);
                  } catch (Throwable var39) {
                     var13 = var39;
                     throw var39;
                  } finally {
                     if (bundleReader != null) {
                        if (var13 != null) {
                           try {
                              bundleReader.close();
                           } catch (Throwable var38) {
                              var13.addSuppressed(var38);
                           }
                        } else {
                           bundleReader.close();
                        }
                     }

                  }

                  return var14;
               } else {
                  InputStream bundleStream = inputStream;
                  var13 = null;

                  try {
                     var14 = ResourceBundleMessageSource.this.loadBundle(bundleStream);
                  } catch (Throwable var40) {
                     var13 = var40;
                     throw var40;
                  } finally {
                     if (inputStream != null) {
                        if (var13 != null) {
                           try {
                              bundleStream.close();
                           } catch (Throwable var37) {
                              var13.addSuppressed(var37);
                           }
                        } else {
                           inputStream.close();
                        }
                     }

                  }

                  return var14;
               }
            }
         } else {
            return super.newBundle(baseName, locale, format, loader, reload);
         }
      }

      @Nullable
      public Locale getFallbackLocale(String baseName, Locale locale) {
         return ResourceBundleMessageSource.this.isFallbackToSystemLocale() ? super.getFallbackLocale(baseName, locale) : null;
      }

      public long getTimeToLive(String baseName, Locale locale) {
         long cacheMillis = ResourceBundleMessageSource.this.getCacheMillis();
         return cacheMillis >= 0L ? cacheMillis : super.getTimeToLive(baseName, locale);
      }

      public boolean needsReload(String baseName, Locale locale, String format, ClassLoader loader, ResourceBundle bundle, long loadTime) {
         if (super.needsReload(baseName, locale, format, loader, bundle, loadTime)) {
            ResourceBundleMessageSource.this.cachedBundleMessageFormats.remove(bundle);
            return true;
         } else {
            return false;
         }
      }

      // $FF: synthetic method
      MessageSourceControl(Object x1) {
         this();
      }
   }
}
