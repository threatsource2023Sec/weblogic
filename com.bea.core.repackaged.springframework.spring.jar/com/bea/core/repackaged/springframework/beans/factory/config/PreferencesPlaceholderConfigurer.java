package com.bea.core.repackaged.springframework.beans.factory.config;

import com.bea.core.repackaged.springframework.beans.factory.BeanDefinitionStoreException;
import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.Properties;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class PreferencesPlaceholderConfigurer extends PropertyPlaceholderConfigurer implements InitializingBean {
   @Nullable
   private String systemTreePath;
   @Nullable
   private String userTreePath;
   private Preferences systemPrefs = Preferences.systemRoot();
   private Preferences userPrefs = Preferences.userRoot();

   public void setSystemTreePath(String systemTreePath) {
      this.systemTreePath = systemTreePath;
   }

   public void setUserTreePath(String userTreePath) {
      this.userTreePath = userTreePath;
   }

   public void afterPropertiesSet() {
      if (this.systemTreePath != null) {
         this.systemPrefs = this.systemPrefs.node(this.systemTreePath);
      }

      if (this.userTreePath != null) {
         this.userPrefs = this.userPrefs.node(this.userTreePath);
      }

   }

   protected String resolvePlaceholder(String placeholder, Properties props) {
      String path = null;
      String key = placeholder;
      int endOfPath = placeholder.lastIndexOf(47);
      if (endOfPath != -1) {
         path = placeholder.substring(0, endOfPath);
         key = placeholder.substring(endOfPath + 1);
      }

      String value = this.resolvePlaceholder(path, key, this.userPrefs);
      if (value == null) {
         value = this.resolvePlaceholder(path, key, this.systemPrefs);
         if (value == null) {
            value = props.getProperty(placeholder);
         }
      }

      return value;
   }

   @Nullable
   protected String resolvePlaceholder(@Nullable String path, String key, Preferences preferences) {
      if (path != null) {
         try {
            return preferences.nodeExists(path) ? preferences.node(path).get(key, (String)null) : null;
         } catch (BackingStoreException var5) {
            throw new BeanDefinitionStoreException("Cannot access specified node path [" + path + "]", var5);
         }
      } else {
         return preferences.get(key, (String)null);
      }
   }
}
