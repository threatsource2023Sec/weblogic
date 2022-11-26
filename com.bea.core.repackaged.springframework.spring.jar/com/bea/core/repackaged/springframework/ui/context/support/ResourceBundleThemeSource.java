package com.bea.core.repackaged.springframework.ui.context.support;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanClassLoaderAware;
import com.bea.core.repackaged.springframework.context.HierarchicalMessageSource;
import com.bea.core.repackaged.springframework.context.MessageSource;
import com.bea.core.repackaged.springframework.context.support.ResourceBundleMessageSource;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.ui.context.HierarchicalThemeSource;
import com.bea.core.repackaged.springframework.ui.context.Theme;
import com.bea.core.repackaged.springframework.ui.context.ThemeSource;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ResourceBundleThemeSource implements HierarchicalThemeSource, BeanClassLoaderAware {
   protected final Log logger = LogFactory.getLog(this.getClass());
   @Nullable
   private ThemeSource parentThemeSource;
   private String basenamePrefix = "";
   @Nullable
   private String defaultEncoding;
   @Nullable
   private Boolean fallbackToSystemLocale;
   @Nullable
   private ClassLoader beanClassLoader;
   private final Map themeCache = new ConcurrentHashMap();

   public void setParentThemeSource(@Nullable ThemeSource parent) {
      this.parentThemeSource = parent;
      synchronized(this.themeCache) {
         Iterator var3 = this.themeCache.values().iterator();

         while(var3.hasNext()) {
            Theme theme = (Theme)var3.next();
            this.initParent(theme);
         }

      }
   }

   @Nullable
   public ThemeSource getParentThemeSource() {
      return this.parentThemeSource;
   }

   public void setBasenamePrefix(@Nullable String basenamePrefix) {
      this.basenamePrefix = basenamePrefix != null ? basenamePrefix : "";
   }

   public void setDefaultEncoding(@Nullable String defaultEncoding) {
      this.defaultEncoding = defaultEncoding;
   }

   public void setFallbackToSystemLocale(boolean fallbackToSystemLocale) {
      this.fallbackToSystemLocale = fallbackToSystemLocale;
   }

   public void setBeanClassLoader(@Nullable ClassLoader beanClassLoader) {
      this.beanClassLoader = beanClassLoader;
   }

   @Nullable
   public Theme getTheme(String themeName) {
      Theme theme = (Theme)this.themeCache.get(themeName);
      if (theme == null) {
         synchronized(this.themeCache) {
            theme = (Theme)this.themeCache.get(themeName);
            if (theme == null) {
               String basename = this.basenamePrefix + themeName;
               MessageSource messageSource = this.createMessageSource(basename);
               theme = new SimpleTheme(themeName, messageSource);
               this.initParent((Theme)theme);
               this.themeCache.put(themeName, theme);
               if (this.logger.isDebugEnabled()) {
                  this.logger.debug("Theme created: name '" + themeName + "', basename [" + basename + "]");
               }
            }
         }
      }

      return (Theme)theme;
   }

   protected MessageSource createMessageSource(String basename) {
      ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
      messageSource.setBasename(basename);
      if (this.defaultEncoding != null) {
         messageSource.setDefaultEncoding(this.defaultEncoding);
      }

      if (this.fallbackToSystemLocale != null) {
         messageSource.setFallbackToSystemLocale(this.fallbackToSystemLocale);
      }

      if (this.beanClassLoader != null) {
         messageSource.setBeanClassLoader(this.beanClassLoader);
      }

      return messageSource;
   }

   protected void initParent(Theme theme) {
      if (theme.getMessageSource() instanceof HierarchicalMessageSource) {
         HierarchicalMessageSource messageSource = (HierarchicalMessageSource)theme.getMessageSource();
         if (this.getParentThemeSource() != null && messageSource.getParentMessageSource() == null) {
            Theme parentTheme = this.getParentThemeSource().getTheme(theme.getName());
            if (parentTheme != null) {
               messageSource.setParentMessageSource(parentTheme.getMessageSource());
            }
         }
      }

   }
}
