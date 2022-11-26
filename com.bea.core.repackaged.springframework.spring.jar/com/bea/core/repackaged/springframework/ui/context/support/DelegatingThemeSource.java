package com.bea.core.repackaged.springframework.ui.context.support;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.ui.context.HierarchicalThemeSource;
import com.bea.core.repackaged.springframework.ui.context.Theme;
import com.bea.core.repackaged.springframework.ui.context.ThemeSource;

public class DelegatingThemeSource implements HierarchicalThemeSource {
   @Nullable
   private ThemeSource parentThemeSource;

   public void setParentThemeSource(@Nullable ThemeSource parentThemeSource) {
      this.parentThemeSource = parentThemeSource;
   }

   @Nullable
   public ThemeSource getParentThemeSource() {
      return this.parentThemeSource;
   }

   @Nullable
   public Theme getTheme(String themeName) {
      return this.parentThemeSource != null ? this.parentThemeSource.getTheme(themeName) : null;
   }
}
