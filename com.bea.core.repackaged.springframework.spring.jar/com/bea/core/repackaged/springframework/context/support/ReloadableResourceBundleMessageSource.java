package com.bea.core.repackaged.springframework.context.support;

import com.bea.core.repackaged.springframework.context.ResourceLoaderAware;
import com.bea.core.repackaged.springframework.core.io.DefaultResourceLoader;
import com.bea.core.repackaged.springframework.core.io.Resource;
import com.bea.core.repackaged.springframework.core.io.ResourceLoader;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.DefaultPropertiesPersister;
import com.bea.core.repackaged.springframework.util.PropertiesPersister;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;

public class ReloadableResourceBundleMessageSource extends AbstractResourceBasedMessageSource implements ResourceLoaderAware {
   private static final String PROPERTIES_SUFFIX = ".properties";
   private static final String XML_SUFFIX = ".xml";
   @Nullable
   private Properties fileEncodings;
   private boolean concurrentRefresh = true;
   private PropertiesPersister propertiesPersister = new DefaultPropertiesPersister();
   private ResourceLoader resourceLoader = new DefaultResourceLoader();
   private final ConcurrentMap cachedFilenames = new ConcurrentHashMap();
   private final ConcurrentMap cachedProperties = new ConcurrentHashMap();
   private final ConcurrentMap cachedMergedProperties = new ConcurrentHashMap();

   public void setFileEncodings(Properties fileEncodings) {
      this.fileEncodings = fileEncodings;
   }

   public void setConcurrentRefresh(boolean concurrentRefresh) {
      this.concurrentRefresh = concurrentRefresh;
   }

   public void setPropertiesPersister(@Nullable PropertiesPersister propertiesPersister) {
      this.propertiesPersister = (PropertiesPersister)(propertiesPersister != null ? propertiesPersister : new DefaultPropertiesPersister());
   }

   public void setResourceLoader(@Nullable ResourceLoader resourceLoader) {
      this.resourceLoader = (ResourceLoader)(resourceLoader != null ? resourceLoader : new DefaultResourceLoader());
   }

   protected String resolveCodeWithoutArguments(String code, Locale locale) {
      String result;
      if (this.getCacheMillis() < 0L) {
         PropertiesHolder propHolder = this.getMergedProperties(locale);
         result = propHolder.getProperty(code);
         if (result != null) {
            return result;
         }
      } else {
         Iterator var10 = this.getBasenameSet().iterator();

         while(var10.hasNext()) {
            result = (String)var10.next();
            List filenames = this.calculateAllFilenames(result, locale);
            Iterator var6 = filenames.iterator();

            while(var6.hasNext()) {
               String filename = (String)var6.next();
               PropertiesHolder propHolder = this.getProperties(filename);
               String result = propHolder.getProperty(code);
               if (result != null) {
                  return result;
               }
            }
         }
      }

      return null;
   }

   @Nullable
   protected MessageFormat resolveCode(String code, Locale locale) {
      if (this.getCacheMillis() < 0L) {
         PropertiesHolder propHolder = this.getMergedProperties(locale);
         MessageFormat result = propHolder.getMessageFormat(code, locale);
         if (result != null) {
            return result;
         }
      } else {
         Iterator var10 = this.getBasenameSet().iterator();

         while(var10.hasNext()) {
            String basename = (String)var10.next();
            List filenames = this.calculateAllFilenames(basename, locale);
            Iterator var6 = filenames.iterator();

            while(var6.hasNext()) {
               String filename = (String)var6.next();
               PropertiesHolder propHolder = this.getProperties(filename);
               MessageFormat result = propHolder.getMessageFormat(code, locale);
               if (result != null) {
                  return result;
               }
            }
         }
      }

      return null;
   }

   protected PropertiesHolder getMergedProperties(Locale locale) {
      PropertiesHolder mergedHolder = (PropertiesHolder)this.cachedMergedProperties.get(locale);
      if (mergedHolder != null) {
         return mergedHolder;
      } else {
         Properties mergedProps = this.newProperties();
         long latestTimestamp = -1L;
         String[] basenames = StringUtils.toStringArray((Collection)this.getBasenameSet());

         for(int i = basenames.length - 1; i >= 0; --i) {
            List filenames = this.calculateAllFilenames(basenames[i], locale);

            for(int j = filenames.size() - 1; j >= 0; --j) {
               String filename = (String)filenames.get(j);
               PropertiesHolder propHolder = this.getProperties(filename);
               if (propHolder.getProperties() != null) {
                  mergedProps.putAll(propHolder.getProperties());
                  if (propHolder.getFileTimestamp() > latestTimestamp) {
                     latestTimestamp = propHolder.getFileTimestamp();
                  }
               }
            }
         }

         mergedHolder = new PropertiesHolder(mergedProps, latestTimestamp);
         PropertiesHolder existing = (PropertiesHolder)this.cachedMergedProperties.putIfAbsent(locale, mergedHolder);
         if (existing != null) {
            mergedHolder = existing;
         }

         return mergedHolder;
      }
   }

   protected List calculateAllFilenames(String basename, Locale locale) {
      Map localeMap = (Map)this.cachedFilenames.get(basename);
      if (localeMap != null) {
         List filenames = (List)((Map)localeMap).get(locale);
         if (filenames != null) {
            return filenames;
         }
      }

      List filenames = new ArrayList(7);
      filenames.addAll(this.calculateFilenamesForLocale(basename, locale));
      if (this.isFallbackToSystemLocale()) {
         Locale defaultLocale = Locale.getDefault();
         if (!locale.equals(defaultLocale)) {
            List fallbackFilenames = this.calculateFilenamesForLocale(basename, defaultLocale);
            Iterator var7 = fallbackFilenames.iterator();

            while(var7.hasNext()) {
               String fallbackFilename = (String)var7.next();
               if (!filenames.contains(fallbackFilename)) {
                  filenames.add(fallbackFilename);
               }
            }
         }
      }

      filenames.add(basename);
      if (localeMap == null) {
         localeMap = new ConcurrentHashMap();
         Map existing = (Map)this.cachedFilenames.putIfAbsent(basename, localeMap);
         if (existing != null) {
            localeMap = existing;
         }
      }

      ((Map)localeMap).put(locale, filenames);
      return filenames;
   }

   protected List calculateFilenamesForLocale(String basename, Locale locale) {
      List result = new ArrayList(3);
      String language = locale.getLanguage();
      String country = locale.getCountry();
      String variant = locale.getVariant();
      StringBuilder temp = new StringBuilder(basename);
      temp.append('_');
      if (language.length() > 0) {
         temp.append(language);
         result.add(0, temp.toString());
      }

      temp.append('_');
      if (country.length() > 0) {
         temp.append(country);
         result.add(0, temp.toString());
      }

      if (variant.length() > 0 && (language.length() > 0 || country.length() > 0)) {
         temp.append('_').append(variant);
         result.add(0, temp.toString());
      }

      return result;
   }

   protected PropertiesHolder getProperties(String filename) {
      PropertiesHolder propHolder = (PropertiesHolder)this.cachedProperties.get(filename);
      long originalTimestamp = -2L;
      PropertiesHolder existingHolder;
      if (propHolder != null) {
         originalTimestamp = propHolder.getRefreshTimestamp();
         if (originalTimestamp == -1L || originalTimestamp > System.currentTimeMillis() - this.getCacheMillis()) {
            return propHolder;
         }
      } else {
         propHolder = new PropertiesHolder();
         existingHolder = (PropertiesHolder)this.cachedProperties.putIfAbsent(filename, propHolder);
         if (existingHolder != null) {
            propHolder = existingHolder;
         }
      }

      if (this.concurrentRefresh && propHolder.getRefreshTimestamp() >= 0L) {
         if (!propHolder.refreshLock.tryLock()) {
            return propHolder;
         }
      } else {
         propHolder.refreshLock.lock();
      }

      PropertiesHolder var6;
      try {
         existingHolder = (PropertiesHolder)this.cachedProperties.get(filename);
         if (existingHolder == null || existingHolder.getRefreshTimestamp() <= originalTimestamp) {
            var6 = this.refreshProperties(filename, propHolder);
            return var6;
         }

         var6 = existingHolder;
      } finally {
         propHolder.refreshLock.unlock();
      }

      return var6;
   }

   protected PropertiesHolder refreshProperties(String filename, @Nullable PropertiesHolder propHolder) {
      long refreshTimestamp = this.getCacheMillis() < 0L ? -1L : System.currentTimeMillis();
      Resource resource = this.resourceLoader.getResource(filename + ".properties");
      if (!resource.exists()) {
         resource = this.resourceLoader.getResource(filename + ".xml");
      }

      if (resource.exists()) {
         long fileTimestamp = -1L;
         if (this.getCacheMillis() >= 0L) {
            try {
               fileTimestamp = resource.lastModified();
               if (propHolder != null && propHolder.getFileTimestamp() == fileTimestamp) {
                  if (this.logger.isDebugEnabled()) {
                     this.logger.debug("Re-caching properties for filename [" + filename + "] - file hasn't been modified");
                  }

                  propHolder.setRefreshTimestamp(refreshTimestamp);
                  return propHolder;
               }
            } catch (IOException var10) {
               if (this.logger.isDebugEnabled()) {
                  this.logger.debug(resource + " could not be resolved in the file system - assuming that it hasn't changed", var10);
               }

               fileTimestamp = -1L;
            }
         }

         try {
            Properties props = this.loadProperties(resource, filename);
            propHolder = new PropertiesHolder(props, fileTimestamp);
         } catch (IOException var9) {
            if (this.logger.isWarnEnabled()) {
               this.logger.warn("Could not parse properties file [" + resource.getFilename() + "]", var9);
            }

            propHolder = new PropertiesHolder();
         }
      } else {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("No properties file found for [" + filename + "] - neither plain properties nor XML");
         }

         propHolder = new PropertiesHolder();
      }

      propHolder.setRefreshTimestamp(refreshTimestamp);
      this.cachedProperties.put(filename, propHolder);
      return propHolder;
   }

   protected Properties loadProperties(Resource resource, String filename) throws IOException {
      Properties props = this.newProperties();
      InputStream is = resource.getInputStream();
      Throwable var5 = null;

      Properties var18;
      try {
         String resourceFilename = resource.getFilename();
         if (resourceFilename != null && resourceFilename.endsWith(".xml")) {
            if (this.logger.isDebugEnabled()) {
               this.logger.debug("Loading properties [" + resource.getFilename() + "]");
            }

            this.propertiesPersister.loadFromXml(props, is);
         } else {
            String encoding = null;
            if (this.fileEncodings != null) {
               encoding = this.fileEncodings.getProperty(filename);
            }

            if (encoding == null) {
               encoding = this.getDefaultEncoding();
            }

            if (encoding != null) {
               if (this.logger.isDebugEnabled()) {
                  this.logger.debug("Loading properties [" + resource.getFilename() + "] with encoding '" + encoding + "'");
               }

               this.propertiesPersister.load(props, (Reader)(new InputStreamReader(is, encoding)));
            } else {
               if (this.logger.isDebugEnabled()) {
                  this.logger.debug("Loading properties [" + resource.getFilename() + "]");
               }

               this.propertiesPersister.load(props, is);
            }
         }

         var18 = props;
      } catch (Throwable var16) {
         var5 = var16;
         throw var16;
      } finally {
         if (is != null) {
            if (var5 != null) {
               try {
                  is.close();
               } catch (Throwable var15) {
                  var5.addSuppressed(var15);
               }
            } else {
               is.close();
            }
         }

      }

      return var18;
   }

   protected Properties newProperties() {
      return new Properties();
   }

   public void clearCache() {
      this.logger.debug("Clearing entire resource bundle cache");
      this.cachedProperties.clear();
      this.cachedMergedProperties.clear();
   }

   public void clearCacheIncludingAncestors() {
      this.clearCache();
      if (this.getParentMessageSource() instanceof ReloadableResourceBundleMessageSource) {
         ((ReloadableResourceBundleMessageSource)this.getParentMessageSource()).clearCacheIncludingAncestors();
      }

   }

   public String toString() {
      return this.getClass().getName() + ": basenames=" + this.getBasenameSet();
   }

   protected class PropertiesHolder {
      @Nullable
      private final Properties properties;
      private final long fileTimestamp;
      private volatile long refreshTimestamp = -2L;
      private final ReentrantLock refreshLock = new ReentrantLock();
      private final ConcurrentMap cachedMessageFormats = new ConcurrentHashMap();

      public PropertiesHolder() {
         this.properties = null;
         this.fileTimestamp = -1L;
      }

      public PropertiesHolder(Properties properties, long fileTimestamp) {
         this.properties = properties;
         this.fileTimestamp = fileTimestamp;
      }

      @Nullable
      public Properties getProperties() {
         return this.properties;
      }

      public long getFileTimestamp() {
         return this.fileTimestamp;
      }

      public void setRefreshTimestamp(long refreshTimestamp) {
         this.refreshTimestamp = refreshTimestamp;
      }

      public long getRefreshTimestamp() {
         return this.refreshTimestamp;
      }

      @Nullable
      public String getProperty(String code) {
         return this.properties == null ? null : this.properties.getProperty(code);
      }

      @Nullable
      public MessageFormat getMessageFormat(String code, Locale locale) {
         if (this.properties == null) {
            return null;
         } else {
            Map localeMap = (Map)this.cachedMessageFormats.get(code);
            if (localeMap != null) {
               MessageFormat resultx = (MessageFormat)((Map)localeMap).get(locale);
               if (resultx != null) {
                  return resultx;
               }
            }

            String msg = this.properties.getProperty(code);
            if (msg != null) {
               if (localeMap == null) {
                  localeMap = new ConcurrentHashMap();
                  Map existing = (Map)this.cachedMessageFormats.putIfAbsent(code, localeMap);
                  if (existing != null) {
                     localeMap = existing;
                  }
               }

               MessageFormat result = ReloadableResourceBundleMessageSource.this.createMessageFormat(msg, locale);
               ((Map)localeMap).put(locale, result);
               return result;
            } else {
               return null;
            }
         }
      }
   }
}
