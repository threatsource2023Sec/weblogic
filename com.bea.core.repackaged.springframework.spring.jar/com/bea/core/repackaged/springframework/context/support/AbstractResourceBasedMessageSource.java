package com.bea.core.repackaged.springframework.context.support;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import java.util.LinkedHashSet;
import java.util.Set;

public abstract class AbstractResourceBasedMessageSource extends AbstractMessageSource {
   private final Set basenameSet = new LinkedHashSet(4);
   @Nullable
   private String defaultEncoding;
   private boolean fallbackToSystemLocale = true;
   private long cacheMillis = -1L;

   public void setBasename(String basename) {
      this.setBasenames(basename);
   }

   public void setBasenames(String... basenames) {
      this.basenameSet.clear();
      this.addBasenames(basenames);
   }

   public void addBasenames(String... basenames) {
      if (!ObjectUtils.isEmpty((Object[])basenames)) {
         String[] var2 = basenames;
         int var3 = basenames.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String basename = var2[var4];
            Assert.hasText(basename, "Basename must not be empty");
            this.basenameSet.add(basename.trim());
         }
      }

   }

   public Set getBasenameSet() {
      return this.basenameSet;
   }

   public void setDefaultEncoding(@Nullable String defaultEncoding) {
      this.defaultEncoding = defaultEncoding;
   }

   @Nullable
   protected String getDefaultEncoding() {
      return this.defaultEncoding;
   }

   public void setFallbackToSystemLocale(boolean fallbackToSystemLocale) {
      this.fallbackToSystemLocale = fallbackToSystemLocale;
   }

   protected boolean isFallbackToSystemLocale() {
      return this.fallbackToSystemLocale;
   }

   public void setCacheSeconds(int cacheSeconds) {
      this.cacheMillis = (long)(cacheSeconds * 1000);
   }

   public void setCacheMillis(long cacheMillis) {
      this.cacheMillis = cacheMillis;
   }

   protected long getCacheMillis() {
      return this.cacheMillis;
   }
}
