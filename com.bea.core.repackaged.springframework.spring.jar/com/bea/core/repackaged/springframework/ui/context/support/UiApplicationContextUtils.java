package com.bea.core.repackaged.springframework.ui.context.support;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.context.ApplicationContext;
import com.bea.core.repackaged.springframework.ui.context.HierarchicalThemeSource;
import com.bea.core.repackaged.springframework.ui.context.ThemeSource;

public abstract class UiApplicationContextUtils {
   public static final String THEME_SOURCE_BEAN_NAME = "themeSource";
   private static final Log logger = LogFactory.getLog(UiApplicationContextUtils.class);

   public static ThemeSource initThemeSource(ApplicationContext context) {
      if (context.containsLocalBean("themeSource")) {
         ThemeSource themeSource = (ThemeSource)context.getBean("themeSource", ThemeSource.class);
         if (context.getParent() instanceof ThemeSource && themeSource instanceof HierarchicalThemeSource) {
            HierarchicalThemeSource hts = (HierarchicalThemeSource)themeSource;
            if (hts.getParentThemeSource() == null) {
               hts.setParentThemeSource((ThemeSource)context.getParent());
            }
         }

         if (logger.isDebugEnabled()) {
            logger.debug("Using ThemeSource [" + themeSource + "]");
         }

         return themeSource;
      } else {
         HierarchicalThemeSource themeSource = null;
         if (context.getParent() instanceof ThemeSource) {
            themeSource = new DelegatingThemeSource();
            ((HierarchicalThemeSource)themeSource).setParentThemeSource((ThemeSource)context.getParent());
         } else {
            themeSource = new ResourceBundleThemeSource();
         }

         if (logger.isDebugEnabled()) {
            logger.debug("Unable to locate ThemeSource with name 'themeSource': using default [" + themeSource + "]");
         }

         return (ThemeSource)themeSource;
      }
   }
}
